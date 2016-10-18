package bzh.msansm1.androdek.media.album;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import bzh.msansm1.androdek.persistence.MedekConfig;
import bzh.msansm1.androdek.persistence.media.AlbumSearch;
import bzh.msansm1.discogsapi.json.search.SearchResult;
import bzh.msansm1.medekapi.MedekApi;
import bzh.msansm1.medekapi.RetrofitManager;
import bzh.msansm1.medekapi.json.JsonError;
import bzh.msansm1.medekapi.json.album.JsonAlbum;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by ronan on 02/10/2016.
 */

public class AlbumScanResultFragment  extends MediaFragment {

    @BindView(R.id.albumsResultList)
    RecyclerView albumsList;

    @BindView(R.id.albumResultsEmpty)
    TextView albumsEmpty;

    private RecyclerView.LayoutManager mLayoutManager;
    private FlexibleAdapter<IFlexible> adapter;

    public AlbumScanResultFragment() {
    }

    public static AlbumScanResultFragment getFragment(){
        return new AlbumScanResultFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_scan_result, container, false);
        ButterKnife.bind(this,view);

        mActivity.getSupportActionBar().setTitle("Album scan results");

        mLayoutManager = new LinearLayoutManager(mActivity);
        albumsList.setLayoutManager(mLayoutManager);

        final List<IFlexible> albumItems = new ArrayList<>();
        RealmResults<AlbumSearch> results = mActivity.getRealm().where(AlbumSearch.class).findAll();
        if (!results.isEmpty()) {
            albumsEmpty.setVisibility(View.GONE);
            for (AlbumSearch a : results) {
                albumItems.add(new AlbumScanResultItem(a.getId(), a.getTitle(), a.getTitle(), a.getYear(),
                        a.getCountry(), a.getCoverURL(), a.getResourceURL()));
            }
        }

        if (!albumItems.isEmpty()) {
            albumsEmpty.setVisibility(View.GONE);
        }

        //Initialize the Adapter
        adapter = new FlexibleAdapter<>(albumItems);

        adapter.setAnimationOnScrolling(true)
                .setAnimationOnReverseScrolling(true);
        adapter.setMode(SelectableAdapter.MODE_SINGLE);

        //Initialize the RecyclerView and attach the Adapter to it as usual
        albumsList.setAdapter(adapter);

        adapter.setSwipeEnabled(true);

        return view;
    }
}

