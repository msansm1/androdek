package bzh.msansm1.androdek.config;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import bzh.msansm1.androdek.R;
import bzh.msansm1.androdek.persistence.MedekConfig;
import bzh.msansm1.androdek.utils.MedekActivity;
import bzh.msansm1.medekapi.MedekApi;
import io.realm.RealmResults;

public class ConfigActivity extends MedekActivity {

    @BindView(R.id.api_url)
    EditText apiUrl;

    @BindView(R.id.dicogs_token)
    EditText discogsToken;

    private MedekConfig config;

    public static Intent getIntent(Context ctx){
        return  new Intent(ctx,ConfigActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        ButterKnife.bind(this);
        RealmResults<MedekConfig> res = realm.where(MedekConfig.class).findAll();
        if (!res.isEmpty()) {
            config = res.first();
            apiUrl.setText(config.getApiUrl());
            discogsToken.setText(config.getDiscogsToken());
        }
    }

    @OnClick(R.id.config_save)
    protected void saveConfig() {
        realm.beginTransaction();

        if(config==null){
            config = realm.createObject(MedekConfig.class);
        }

        config.setApiUrl(apiUrl.getText().toString().trim());
        config.setDiscogsToken(discogsToken.getText().toString().trim());
        realm.commitTransaction();

        try {
            MedekApi.getInstance().initApi();
        }catch (IllegalArgumentException e){
            Toast.makeText(this,"Invalid configuration", Toast.LENGTH_SHORT).show();
        }
    }
}
