package bzh.msansm1.androdek.media;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import bzh.msansm1.androdek.R;
import bzh.msansm1.androdek.utils.MedekActivity;

public class MediaActivity extends MedekActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static Intent getIntent(Context ctx){
        return  new Intent(ctx,MediaActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
    }

}
