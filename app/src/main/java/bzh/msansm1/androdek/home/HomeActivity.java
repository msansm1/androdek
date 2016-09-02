package bzh.msansm1.androdek.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        Bundle b = getIntent().getExtras();
        if(b != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFragment, HomeFragment.getFragment(b.getString("token"))).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFragment, HomeFragment.getFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_albums:
                onAlbumsClick();
                return true;
            case R.id.action_books:
                onBooksClick();
                return true;
            case R.id.action_movies:
                onMoviesClick();
                return true;
            case R.id.action_tvshows:
                onTVShowsClick();
                return true;
            case R.id.action_friends:
                onFriendsClick();
                return true;
            case R.id.action_loans:
                onLoansClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    protected void onAlbumsClick() {
        Toast.makeText(getBaseContext(), "Albums", Toast.LENGTH_SHORT).show();
    }

    protected void onBooksClick() {
        Toast.makeText(getBaseContext(), "Books", Toast.LENGTH_SHORT).show();
    }

    protected void onMoviesClick() {
        Toast.makeText(getBaseContext(), "Movies", Toast.LENGTH_SHORT).show();
    }

    protected void onTVShowsClick() {
        Toast.makeText(getBaseContext(), "TV Shows", Toast.LENGTH_SHORT).show();
    }

    protected void onFriendsClick() {
        Toast.makeText(getBaseContext(), "Friends", Toast.LENGTH_SHORT).show();
    }

    protected void onLoansClick() {
        Toast.makeText(getBaseContext(), "Loans", Toast.LENGTH_SHORT).show();
    }

}
