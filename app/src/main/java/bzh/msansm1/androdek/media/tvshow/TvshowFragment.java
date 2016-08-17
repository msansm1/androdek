package bzh.msansm1.androdek.media.tvshow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bzh.msansm1.androdek.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class TvshowFragment extends Fragment {

    public TvshowFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album, container, false);
    }
}
