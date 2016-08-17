package bzh.msansm1.androdek.media.book;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bzh.msansm1.androdek.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class BookFragment extends Fragment {

    public BookFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album, container, false);
    }
}
