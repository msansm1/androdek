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
import bzh.msansm1.androdek.media.MediaActivity;
import bzh.msansm1.androdek.utils.Constants;
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
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFragment, HomeFragment.getFragment(b.getString(Constants.KEY_TOKEN))).commit();
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
        Intent intent = new Intent(HomeActivity.this, MediaActivity.class);
        Bundle b = new Bundle();
        b.putString(Constants.KEY_MEDIA, Constants.MEDIA_ALBUM);
        intent.putExtras(b);
        startActivity(intent);
    }

    protected void onBooksClick() {
        Intent intent = new Intent(HomeActivity.this, MediaActivity.class);
        Bundle b = new Bundle();
        b.putString(Constants.KEY_MEDIA, Constants.MEDIA_BOOK);
        intent.putExtras(b);
        startActivity(intent);
    }

    protected void onMoviesClick() {
        Intent intent = new Intent(HomeActivity.this, MediaActivity.class);
        Bundle b = new Bundle();
        b.putString(Constants.KEY_MEDIA, Constants.MEDIA_MOVIE);
        intent.putExtras(b);
        startActivity(intent);
    }

    protected void onTVShowsClick() {
        Intent intent = new Intent(HomeActivity.this, MediaActivity.class);
        Bundle b = new Bundle();
        b.putString(Constants.KEY_MEDIA, Constants.MEDIA_TVSHOW);
        intent.putExtras(b);
        startActivity(intent);
    }

    protected void onFriendsClick() {
        Toast.makeText(getBaseContext(), "Friends", Toast.LENGTH_SHORT).show();
    }

    protected void onLoansClick() {
        Toast.makeText(getBaseContext(), "Loans", Toast.LENGTH_SHORT).show();
    }

}
