package bzh.msansm1.androdek.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import bzh.msansm1.androdek.R;
import bzh.msansm1.androdek.config.ConfigActivity;
import bzh.msansm1.androdek.home.HomeActivity;
import bzh.msansm1.androdek.persistence.MedekConfig;
import bzh.msansm1.androdek.utils.Constants;
import bzh.msansm1.androdek.utils.MedekActivity;
import bzh.msansm1.medekapi.MedekApi;
import bzh.msansm1.medekapi.RetrofitManager;
import bzh.msansm1.medekapi.json.JsonError;
import bzh.msansm1.medekapi.json.auth.JsonAuth;
import bzh.msansm1.medekapi.json.auth.JsonLogin;
import io.realm.RealmResults;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * Login screen (login / password)
 */
public class LoginActivity extends MedekActivity {

    @BindView(R.id.login)
    TextView loginText;
    @BindView(R.id.password)
    EditText passwordText;

    public static Intent getIntent(Context ctx){
        return  new Intent(ctx,LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        RealmResults<MedekConfig> conf = realm.where(MedekConfig.class).findAll();
        if (!conf.isEmpty()) {
            if (conf.first().getToken() != null) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                Bundle b = new Bundle();
                b.putString(Constants.KEY_TOKEN, conf.first().getToken());
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        }
    }

    @OnClick(R.id.login_button)
    protected void login() {
        RealmResults<MedekConfig> cfg = realm.where(MedekConfig.class).findAll();
        if (cfg.isEmpty()) {
            Toast.makeText(getBaseContext(), "Configure API URL", Toast.LENGTH_SHORT).show();
        } else {
            MedekApi.getInstance().login(new JsonLogin(loginText.getText().toString(),
                    passwordText.getText().toString()), new RetrofitManager.MedekCallBack<JsonAuth>() {
                @Override
                public void success(JsonAuth jsonAuth) {
                    realm.beginTransaction();
                    RealmResults<MedekConfig> conf = realm.where(MedekConfig.class).findAll();
                    if (!conf.isEmpty()) {
                        conf.first().setToken(jsonAuth.getToken());
                        conf.first().setUserId(jsonAuth.getId());
                    } else {
                        MedekConfig config = realm.createObject(MedekConfig.class);
                        config.setToken(jsonAuth.getToken());
                        config.setUserId(jsonAuth.getId());
                    }
                    realm.commitTransaction();
                    startActivity(HomeActivity.getIntent(LoginActivity.this));
                }

                @Override
                public void failure(JsonError error) {
                    Toast.makeText(getBaseContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @OnClick(R.id.ico_config)
    protected void clickConfig() {
        startActivity(ConfigActivity.getIntent(LoginActivity.this));
    }
}

