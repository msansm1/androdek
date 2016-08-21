package bzh.msansm1.androdek.home;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import bzh.msansm1.androdek.R;
import bzh.msansm1.medekapi.MedekApi;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {

    @BindView(R.id.myCollecAlbums)
    TextView myAlbums;
    @BindView(R.id.myCollecBooks)
    TextView myBooks;
    @BindView(R.id.myCollecMovies)
    TextView myMovies;
    @BindView(R.id.myCollecTvshows)
    TextView myTvshows;

    public HomeActivityFragment() {
    }

    public static HomeActivityFragment getFragment(){
        return new HomeActivityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);

        return view;
    }

    private void initData() {
        // MedekApi.getInstance().
    }

}
