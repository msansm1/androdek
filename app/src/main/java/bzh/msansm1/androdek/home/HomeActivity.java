package bzh.msansm1.androdek.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
        toolbar.inflateMenu(R.menu.menu_home);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFragment, HomeFragment.getFragment(b.getString("token"))).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFragment, HomeFragment.getFragment()).commit();
        }
    }

    @OnClick(R.id.action_albums)
    protected void onAlbumsClick() {
        Toast.makeText(getBaseContext(), "Albums", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.action_books)
    protected void onBookClick() {
        Toast.makeText(getBaseContext(), "Books", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.action_movies)
    protected void onMoviesClick() {
        Toast.makeText(getBaseContext(), "Movies", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.action_tvshows)
    protected void onTVShowsClick() {
        Toast.makeText(getBaseContext(), "TV Shows", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.action_friends)
    protected void onFriendsClick() {
        Toast.makeText(getBaseContext(), "Friends", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.action_loans)
    protected void onLoansClick() {
        Toast.makeText(getBaseContext(), "Loans", Toast.LENGTH_SHORT).show();
    }

}
