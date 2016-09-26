package bzh.msansm1.androdek.media.movie;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import bzh.msansm1.androdek.R;
import bzh.msansm1.androdek.media.MediaFragment;
import bzh.msansm1.androdek.media.movie.MovieFragment;
import bzh.msansm1.androdek.media.movie.MovieItem;
import bzh.msansm1.androdek.persistence.MedekConfig;
import bzh.msansm1.medekapi.MedekApi;
import bzh.msansm1.medekapi.RetrofitManager;
import bzh.msansm1.medekapi.json.JsonError;
import bzh.msansm1.medekapi.json.movie.JsonMovie;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieFragment extends MediaFragment {

    @BindView(R.id.add_movie)
    FloatingActionButton addMovie;

    @BindView(R.id.moviesList)
    RecyclerView moviesList;

    @BindView(R.id.moviesEmpty)
    TextView moviesEmpty;

    private RecyclerView.LayoutManager mLayoutManager;
    private FlexibleAdapter<IFlexible> adapter;

    public MovieFragment() {
    }

    public static MovieFragment getFragment(){
        return new MovieFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this,view);

        mLayoutManager = new LinearLayoutManager(mActivity);
        moviesList.setLayoutManager(mLayoutManager);

        final MedekConfig conf = mActivity.getRealm().where(MedekConfig.class).findFirst();

        final List<IFlexible> movieItems = new ArrayList<>();
        MedekApi.getInstance().getAllMovies(0, 50, "title", "asc", new RetrofitManager.MedekCallBack<List<JsonMovie>>() {
            @Override
            public void success(List<JsonMovie> jsonMovies) {
                if (!jsonMovies.isEmpty()) {
                    moviesEmpty.setVisibility(View.GONE);
                    List<IFlexible> newItems = new ArrayList<>();
                    for (JsonMovie m : jsonMovies) {
                        newItems.add(new MovieItem(m.getId(), m.getTitle(), m.getRealisator(), conf.getApiUrl()+"medekimg/movie/"+m.getId()+"/cover.jpg"));
                    }
                    adapter.updateDataSet(newItems);
                }
            }

            @Override
            public void failure(JsonError error) {
                Snackbar.make(getView(), error.getTitle(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (!movieItems.isEmpty()) {
            moviesEmpty.setVisibility(View.GONE);
        }

        //Initialize the Adapter
        adapter = new FlexibleAdapter<>(movieItems);

        adapter.setAnimationOnScrolling(true)
                .setAnimationOnReverseScrolling(true);
        adapter.setMode(SelectableAdapter.MODE_SINGLE);

        //Initialize the RecyclerView and attach the Adapter to it as usual
        moviesList.setAdapter(adapter);

        adapter.setSwipeEnabled(true);



        addMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add Movie", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }
}
