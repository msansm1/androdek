package bzh.msansm1.androdek.media.tvshow;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import bzh.msansm1.androdek.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class TvshowFragment extends Fragment {

    @BindView(R.id.add_tvshow)
    FloatingActionButton addTvShow;

    public TvshowFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        ButterKnife.bind(this,view);

        addTvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add Tv Show", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }
}
