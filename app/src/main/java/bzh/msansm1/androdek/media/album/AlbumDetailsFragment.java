package bzh.msansm1.androdek.media.album;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import bzh.msansm1.androdek.R;
import bzh.msansm1.androdek.media.MediaFragment;
import bzh.msansm1.androdek.persistence.MedekConfig;
import bzh.msansm1.discogsapi.DiscogsApi;
import bzh.msansm1.discogsapi.DiscogsApiRetrofit;
import bzh.msansm1.discogsapi.json.DiscogsError;
import bzh.msansm1.discogsapi.json.release.Release;
import bzh.msansm1.discogsapi.json.release.Track;
import bzh.msansm1.medekapi.MedekApi;
import bzh.msansm1.medekapi.RetrofitManager;
import bzh.msansm1.medekapi.json.JsonError;
import bzh.msansm1.medekapi.json.JsonSimpleResponse;
import bzh.msansm1.medekapi.json.album.JsonAlbum;
import bzh.msansm1.medekapi.json.album.JsonMyAlbum;
import bzh.msansm1.medekapi.json.album.JsonTrack;

/**
 * Created by ronan on 03/10/2016.
 */

public class AlbumDetailsFragment extends MediaFragment {

    public static final String ALBUM_ID="albumId";
    public static final String DISCOGS_ID="discogsId";

    @BindView(R.id.album_res_artist)
    TextView artistText;

    @BindView(R.id.album_res_title)
    TextView titleText;

    @BindView(R.id.album_res_country)
    TextView countryText;

    @BindView(R.id.album_res_year)
    TextView yearText;

    @BindView(R.id.album_res_genre)
    TextView genreText;

    @BindView(R.id.album_res_cover)
    ImageView coverImg;

    @BindView(R.id.album_res_tracks_title)
    TextView tracksTitleText;

    @BindView(R.id.album_res_tracks_duration)
    TextView tracksDurationText;

    @BindView(R.id.album_res_add)
    View addAlbumButton;

    private String discogsToken;
    private Release albumRelease;

    public static AlbumDetailsFragment getFragment() {
        return new AlbumDetailsFragment();
    }

    public static AlbumDetailsFragment getFragment(Integer id, boolean medek){
        AlbumDetailsFragment fragment = new AlbumDetailsFragment();
        Bundle args = new Bundle();
        if (medek) {
            args.putInt(ALBUM_ID, id);
        } else {
            args.putInt(DISCOGS_ID, id);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.fragment_album_details, container, false);
        ButterKnife.bind(this, convertView);
        discogsToken = mActivity.getRealm().where(MedekConfig.class).findFirst().getDiscogsToken();
        if (getArguments() != null) {
            if (getArguments().containsKey(ALBUM_ID)) {
                Integer id = getArguments().getInt(ALBUM_ID);
                getArguments().remove(ALBUM_ID);
                initMedekData(id);
            } else if (getArguments().containsKey(DISCOGS_ID)) {
                Integer id = getArguments().getInt(DISCOGS_ID);
                getArguments().remove(DISCOGS_ID);
                initDiscogsData(id);
                addAlbumButton.setVisibility(View.VISIBLE);
            }
        }

        return convertView;

    }

    private void initMedekData(Integer id) {

    }

    private void initDiscogsData(Integer id) {
        DiscogsApi.getInstance().getRelease(discogsToken, id, new DiscogsApiRetrofit.DiscogsCallBack<Release>() {
            @Override
            public void success(Release release) {
                albumRelease = release;
                artistText.setText(release.getArtists().get(0).getName());
                titleText.setText(release.getTitle());
                mActivity.getSupportActionBar().setTitle(release.getTitle());
                countryText.setText(release.getCountry());
                yearText.setText(release.getYear()+"");
                genreText.setText(release.getGenres().get(0));
                String tracks="";
                String durations="";
                for (Track t:release.getTracklist()) {
                    tracks += t.getTitle();
                    tracks += "\n";
                    durations += t.getDuration();
                    durations += "\n";
                }
                tracksTitleText.setText(tracks);
                tracksDurationText.setText(durations);
                Picasso.with(getContext()).load(release.getThumb()).into(coverImg);
            }

            @Override
            public void failure(DiscogsError error) {
                Snackbar.make(getView(), error.getTitle(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @OnClick(R.id.album_res_add)
    protected void addAlbum() {
        final JsonAlbum a = new JsonAlbum();
        a.setId(null);
        a.setTitle(albumRelease.getTitle());
        a.setArtist(albumRelease.getArtists().get(0).getName());
        a.setCover(albumRelease.getThumb());
        a.setGenre(albumRelease.getGenres().get(0));
        a.setMycollec(true);
        a.setNbTracks(albumRelease.getTracklist().size());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        try {
            a.setReleaseDate(Long.valueOf(sdf.parse(albumRelease.getYear()+"").getTime()));
        } catch (ParseException e) {
            Log.e("ALBUM", "PARSE YEAR", e);
        }
        List<JsonTrack> tracks = new ArrayList<>();
        for (Track t : albumRelease.getTracklist()) {
            Integer position = 0;
            if (t.getPosition().indexOf('-') > 0) {
                position = Integer.valueOf(t.getPosition().substring(t.getPosition().indexOf('-')+1, t.getPosition().length()));
            } else {
                if (t.getPosition().indexOf('.') > 0) {
                    position = Integer.valueOf(t.getPosition().substring(t.getPosition().indexOf('.')+1, t.getPosition().length()));
                } else {
                    if (isInteger(t.getPosition())) {
                        position = Integer.valueOf(t.getPosition());
                    }
                }
            }
            JsonTrack track = new JsonTrack(null, null, t.getTitle(), position, t.getDuration(),
                    (t.getArtists() == null)?albumRelease.getArtists().get(0).getName():t.getArtists().get(0).getName(), null);
            tracks.add(track);
        }
        a.setTracks(tracks);
        MedekApi.getInstance().createUpdateAlbum(a, new RetrofitManager.MedekCallBack<JsonAlbum>() {
            @Override
            public void success(JsonAlbum album) {
                a.setId(album.getId());
                Snackbar.make(getView(), "Added "+a.getTitle(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Integer userId = mActivity.getRealm().where(MedekConfig.class).findFirst().getUserId();
                addToMyCollecDialog(new JsonMyAlbum(album.getId(), userId, 1, "", false));
            }

            @Override
            public void failure(JsonError error) {
                Snackbar.make(getView(), error.getTitle(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void addToMyCollecDialog(final JsonMyAlbum myAlbum) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

        builder.setMessage("Add to my collection ?")
                .setTitle("Add to collection / wishlist");

        builder.setPositiveButton(getString(R.string.add_to_collec), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MedekApi.getInstance().addAlbumToMyCollec(myAlbum, new RetrofitManager.MedekCallBack<JsonSimpleResponse>() {
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
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("Add to whishlist", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
