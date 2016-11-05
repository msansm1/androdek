package bzh.msansm1.androdek.media;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import bzh.msansm1.androdek.R;
import bzh.msansm1.androdek.home.HomeActivity;
import bzh.msansm1.androdek.home.HomeFragment;
import bzh.msansm1.androdek.media.album.AlbumFragment;
import bzh.msansm1.androdek.media.book.BookFragment;
import bzh.msansm1.androdek.media.movie.MovieFragment;
import bzh.msansm1.androdek.media.tvshow.TvshowFragment;
import bzh.msansm1.androdek.utils.Constants;
import bzh.msansm1.androdek.utils.MedekActivity;

public class MediaActivity extends MedekActivity {

    @BindView(R.id.toolbar_media)
    Toolbar toolbar;

    private String media;

    public static Intent getIntent(Context ctx){
        return  new Intent(ctx,MediaActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            media = b.getString(Constants.KEY_MEDIA);
            launchMediaFrag(false);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.mediaFragment, AlbumFragment.getFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_media, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_all:
                onAllClick();
                return true;
            case R.id.action_my_list:
                onMyListClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onAllClick() {
        launchMediaFrag(false);
    }

    protected void onMyListClick() {
        launchMediaFrag(true);
    }

    private void launchMediaFrag(Boolean mylist) {
        if (media.equals(Constants.MEDIA_ALBUM)) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mediaFragment, AlbumFragment.getFragment(mylist)).commit();
            getSupportActionBar().setTitle(getString(R.string.albums));
        } else if (media.equals(Constants.MEDIA_BOOK)) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mediaFragment, BookFragment.getFragment(mylist)).commit();
            getSupportActionBar().setTitle(getString(R.string.books));
        } else if (media.equals(Constants.MEDIA_MOVIE)) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mediaFragment, MovieFragment.getFragment(mylist)).commit();
            getSupportActionBar().setTitle(getString(R.string.movies));
        } else if (media.equals(Constants.MEDIA_TVSHOW)) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mediaFragment, TvshowFragment.getFragment(mylist)).commit();
            getSupportActionBar().setTitle(getString(R.string.tvshows));
        }
    }

}
