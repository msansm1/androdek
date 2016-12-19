package bzh.msansm1.androdek.media.album;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import bzh.msansm1.androdek.common.ProgressItem;
import bzh.msansm1.androdek.media.MediaFragment;
import bzh.msansm1.androdek.persistence.MedekConfig;
import bzh.msansm1.medekapi.MedekApi;
import bzh.msansm1.medekapi.RetrofitManager;
import bzh.msansm1.medekapi.json.JsonError;
import bzh.msansm1.medekapi.json.JsonSimpleResponse;
import bzh.msansm1.medekapi.json.album.JsonAlbum;
import bzh.msansm1.medekapi.json.album.JsonMyAlbum;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * A placeholder fragment containing a simple view.
 */
public class AlbumListFragment extends MediaFragment implements FlexibleAdapter.EndlessScrollListener  {

    @BindView(R.id.add_album)
    FloatingActionButton addAlbum;

    @BindView(R.id.albumsList)
    RecyclerView albumsList;

    @BindView(R.id.albumsLoading)
    TextView albumsLoading;

    @BindView(R.id.albumsEmpty)
    TextView albumsEmpty;

    private RecyclerView.LayoutManager mLayoutManager;
    private FlexibleAdapter<IFlexible> adapter;

    private Boolean myList = false;
    private int listIndex = 0;
    private int pageSize = 50;

    public AlbumListFragment() {
    }

    public static AlbumListFragment getFragment(){
        return new AlbumListFragment();
    }

    public static AlbumListFragment getFragment(Boolean mylist) {
        AlbumListFragment frag = new AlbumListFragment();
        Bundle args = new Bundle();
        args.putBoolean("mylist", mylist);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_list, container, false);
        ButterKnife.bind(this,view);

        if (getArguments() != null && getArguments().containsKey("mylist")) {
            myList = getArguments().getBoolean("mylist");
        }

        if (myList) {
            mActivity.getSupportActionBar().setTitle(getString(R.string.my_albums));
        } else {
            mActivity.getSupportActionBar().setTitle(getString(R.string.albums));
        }

        mLayoutManager = new LinearLayoutManager(mActivity);
        albumsList.setLayoutManager(mLayoutManager);

        final List<IFlexible> albumItems = new ArrayList<>();

        //Initialize the Adapter
        adapter = new FlexibleAdapter<>(albumItems);

        adapter.setAnimationOnScrolling(true)
                .setAnimationOnReverseScrolling(true);
        adapter.setMode(SelectableAdapter.MODE_SINGLE);

        //Initialize the RecyclerView and attach the Adapter to it as usual
        albumsList.setAdapter(adapter);

        adapter.setSwipeEnabled(true);
        adapter.setEndlessScrollListener(this, new ProgressItem());
        adapter.setEndlessScrollThreshold(1);

        addAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.getSupportFragmentManager().beginTransaction().add(R.id.mediaFragment, AlbumScanFragment.getFragment()).addToBackStack("albumscan").commit();
            }
        });
        getAlbumItems(true);

        return view;
    }

    @NonNull
    private void getAlbumItems(final boolean init) {
        final MedekConfig conf = mActivity.getRealm().where(MedekConfig.class).findFirst();

        if (myList) {
            MedekApi.getInstance().getUserAlbums(listIndex, pageSize, "title", "asc", conf.getUserId(),
                    new RetrofitManager.MedekCallBack<List<JsonAlbum>>() {
                @Override
                public void success(List<JsonAlbum> jsonAlbums) {
                    if (jsonAlbums != null) {
                        if (!jsonAlbums.isEmpty()) {
                            albumsLoading.setVisibility(View.GONE);
                            List<IFlexible> items = convertToAlbumItems(jsonAlbums, conf);
                            if (init) {
                                adapter.updateDataSet(items, true);
                            } else {
                                adapter.onLoadMoreComplete(items);
                            }
                        } else {
                            if (adapter.getItemCount() == 0) {
                                albumsEmpty.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

                @Override
                public void failure(JsonError error) {
                    Snackbar.make(getView(), error.getTitle(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        } else {
            MedekApi.getInstance().getAllAlbums(listIndex, pageSize, "title", "asc", new RetrofitManager.MedekCallBack<List<JsonAlbum>>() {
                @Override
                public void success(List<JsonAlbum> jsonAlbums) {
                    if (!jsonAlbums.isEmpty()) {
                        albumsLoading.setVisibility(View.GONE);
                        List<IFlexible> items = convertToAlbumItems(jsonAlbums, conf);
                        if (init) {
                            adapter.updateDataSet(items, true);
                        } else {
                            adapter.onLoadMoreComplete(items);
                        }
                    } else {
                        if (adapter.getItemCount() == 0) {
                            albumsEmpty.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void failure(JsonError error) {
                    Snackbar.make(getView(), error.getTitle(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }

    @NonNull
    private List<IFlexible> convertToAlbumItems(List<JsonAlbum> jsonAlbums, MedekConfig conf) {
        List<IFlexible> newItems = new ArrayList<>();
        for (JsonAlbum a : jsonAlbums) {
            if (a.getCover().startsWith("https://")) {
                if (myList) {
                    newItems.add(new AlbumMyListItem(a.getId(), a.getTitle(), a.getArtist(), a.getCover()));
                } else {
                    newItems.add(new AlbumListItem(a.getId(), a.getTitle(), a.getArtist(), a.getCover()));
                }
            } else {
                if (myList) {
                    newItems.add(new AlbumMyListItem(a.getId(), a.getTitle(), a.getArtist(), conf.getApiUrl() + "medekimg/album/" + a.getId() + "/cover.jpg"));
                } else {
                    newItems.add(new AlbumListItem(a.getId(), a.getTitle(), a.getArtist(), conf.getApiUrl() + "medekimg/album/" + a.getId() + "/cover.jpg"));
                }
            }
        }
        return newItems;
    }

    public Boolean getMyList() {
        return myList;
    }

    @Override
    public void onLoadMore() {
        listIndex += pageSize;
        getAlbumItems(false);
    }

    public class SwipeListener implements FlexibleAdapter.OnItemSwipeListener {

        @Override
        public void onItemSwipe(final int position, int direction) {
            if (myList) {
                final AlbumMyListItem item = (AlbumMyListItem) adapter.getItem(position);
            //    final ReportModel model = reports.get(position).clone();
            /*    Snackbar sb = Snackbar.make(getView(), getContext().getString(R.string.deleted_coupon), Snackbar.LENGTH_LONG)
                        .setAction(getContext().getString(R.string.cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                adapter.addItem(position, item);
                                mActivity.getRealm().beginTransaction();
                                mActivity.getRealm().copyToRealmOrUpdate(model);
                                mActivity.getRealm().commitTransaction();
                            }
                        });
                sb.setActionTextColor(getContext().getResources().getColor(R.color.colorPrimary));
                View sbView = sb.getView();
                sbView.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
                textView.setTextColor(getContext().getResources().getColor(R.color.white));
                sb.show(); */
                adapter.removeItem(position);
            } else {
                final AlbumListItem item = (AlbumListItem) adapter.getItem(position);
                MedekApi.getInstance().addAlbumToMyCollec(new JsonMyAlbum(item.getId(),
                                mActivity.getRealm().where(MedekConfig.class).findFirst().getUserId(), 1, "", false),
                        new RetrofitManager.MedekCallBack<JsonSimpleResponse>() {
                    @Override
                    public void success(JsonSimpleResponse s) {
                        if (s.getOk().equals("true")) {
                            Snackbar.make(getView(), "Added to my collection", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
                            Snackbar.make(getView(), "Add to collection failed", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }

                    @Override
                    public void failure(JsonError error) {
                        Snackbar.make(getView(), "Add to collection failed", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }
        }

        @Override
        public void onActionStateChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        }
    }

}
