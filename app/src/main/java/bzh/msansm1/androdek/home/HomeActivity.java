package bzh.msansm1.androdek.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import bzh.msansm1.androdek.R;
import bzh.msansm1.androdek.utils.MedekActivity;

public class HomeActivity extends MedekActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static Intent getIntent(Context ctx){
        return  new Intent(ctx,HomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFragment, HomeFragment.getFragment(b.getString("token"))).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFragment, HomeFragment.getFragment()).commit();
        }
    }

}
