package bzh.msansm1.androdek.media.tvshow;

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
import bzh.msansm1.androdek.media.tvshow.TvshowFragment;
import bzh.msansm1.androdek.media.tvshow.TvshowItem;
import bzh.msansm1.androdek.persistence.MedekConfig;
import bzh.msansm1.medekapi.MedekApi;
import bzh.msansm1.medekapi.RetrofitManager;
import bzh.msansm1.medekapi.json.JsonError;
import bzh.msansm1.medekapi.json.tvshow.JsonShow;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * A placeholder fragment containing a simple view.
 */
public class TvshowFragment extends MediaFragment {

    @BindView(R.id.add_tvshow)
    FloatingActionButton addTvshow;

    @BindView(R.id.tvshowsList)
    RecyclerView tvshowsList;

    @BindView(R.id.tvshowsEmpty)
    TextView tvshowsEmpty;

    private RecyclerView.LayoutManager mLayoutManager;
    private FlexibleAdapter<IFlexible> adapter;

    public TvshowFragment() {
    }

    public static TvshowFragment getFragment(){
        return new TvshowFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        ButterKnife.bind(this,view);

        mLayoutManager = new LinearLayoutManager(mActivity);
        tvshowsList.setLayoutManager(mLayoutManager);

        final MedekConfig conf = mActivity.getRealm().where(MedekConfig.class).findFirst();

        final List<IFlexible> tvshowItems = new ArrayList<>();
        MedekApi.getInstance().getAllTvshows(0, 50, "title", "asc", new RetrofitManager.MedekCallBack<List<JsonShow>>() {
            @Override
            public void success(List<JsonShow> jsonTvshows) {
                if (!jsonTvshows.isEmpty()) {
                    tvshowsEmpty.setVisibility(View.GONE);
                    List<IFlexible> newItems = new ArrayList<>();
                    for (JsonShow s : jsonTvshows) {
                        newItems.add(new TvshowItem(s.getId(), s.getTitle(), s.getSeason()+"", conf.getApiUrl()+"medekimg/tvshow/"+s.getId()+"/cover.jpg"));
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

        if (!tvshowItems.isEmpty()) {
            tvshowsEmpty.setVisibility(View.GONE);
        }

        //Initialize the Adapter
        adapter = new FlexibleAdapter<>(tvshowItems);

        adapter.setAnimationOnScrolling(true)
                .setAnimationOnReverseScrolling(true);
        adapter.setMode(SelectableAdapter.MODE_SINGLE);

        //Initialize the RecyclerView and attach the Adapter to it as usual
        tvshowsList.setAdapter(adapter);

        adapter.setSwipeEnabled(true);



        addTvshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add Tvshow", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }
}
