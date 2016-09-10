package bzh.msansm1.androdek.media.album;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
import bzh.msansm1.medekapi.MedekApi;
import bzh.msansm1.medekapi.RetrofitManager;
import bzh.msansm1.medekapi.json.JsonError;
import bzh.msansm1.medekapi.json.album.JsonAlbum;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * A placeholder fragment containing a simple view.
 */
public class AlbumFragment extends MediaFragment {

    @BindView(R.id.add_album)
    FloatingActionButton addAlbum;

    @BindView(R.id.albumsList)
    RecyclerView albumsList;

    @BindView(R.id.albumsEmpty)
    TextView albumsEmpty;

    private RecyclerView.LayoutManager mLayoutManager;
    private FlexibleAdapter<IFlexible> adapter;

    public AlbumFragment() {
    }

    public static AlbumFragment getFragment(){
        return new AlbumFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        ButterKnife.bind(this,view);

        mLayoutManager = new LinearLayoutManager(mActivity);
        albumsList.setLayoutManager(mLayoutManager);

        final List<IFlexible> albumItems = new ArrayList<>();
        MedekApi.getInstance().getAllAlbums(0, 50, "title", "asc", new RetrofitManager.MedekCallBack<List<JsonAlbum>>() {
            @Override
            public void success(List<JsonAlbum> jsonAlbums) {
                if (!jsonAlbums.isEmpty()) {
                    albumsEmpty.setVisibility(View.GONE);
                } else {
                    for (JsonAlbum a : jsonAlbums) {
                        albumItems.add(new AlbumItem(a.getId(), a.getTitle(), a.getArtist(), a.getCover()));
                    }
                    adapter = new FlexibleAdapter<>(albumItems);
                    adapter.setAnimationOnScrolling(true)
                            .setAnimationOnReverseScrolling(true);
                    adapter.setMode(SelectableAdapter.MODE_SINGLE);
                    albumsList.setAdapter(adapter);
                    adapter.setSwipeEnabled(true);
                }
            }

            @Override
            public void failure(JsonError error) {
                Snackbar.make(getView(), "Get Album Error", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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



        addAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add Album", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }
}
