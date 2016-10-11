package bzh.msansm1.androdek.media.album;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import bzh.msansm1.androdek.persistence.media.AlbumSearch;
import bzh.msansm1.discogsapi.DiscogsApi;
import bzh.msansm1.discogsapi.DiscogsApiRetrofit;
import bzh.msansm1.discogsapi.json.DiscogsError;
import bzh.msansm1.discogsapi.json.release.Release;
import bzh.msansm1.discogsapi.json.release.Track;
import bzh.msansm1.discogsapi.json.search.SearchResponse;
import bzh.msansm1.medekapi.MedekApi;
import bzh.msansm1.medekapi.RetrofitManager;
import bzh.msansm1.medekapi.json.JsonError;
import bzh.msansm1.medekapi.json.album.JsonAddSearch;
import bzh.msansm1.medekapi.json.album.JsonAlbum;
import bzh.msansm1.medekapi.json.album.JsonTrack;
import bzh.msansm1.medekapi.json.artist.JsonArtist;

/**
 * Created by ronan on 03/10/2016.
 */

public class ScanResultDetailsFragment extends MediaFragment {

    public static final String ALBUM_ID="albumId";

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

    private String discogsToken;
    private Release albumRelease;

    public static ScanResultDetailsFragment getFragment() {
        return new ScanResultDetailsFragment();
    }

    public static ScanResultDetailsFragment getFragment(Integer id){
        ScanResultDetailsFragment fragment = new ScanResultDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ALBUM_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.fragment_album_result_details, container, false);
        ButterKnife.bind(this, convertView);
        discogsToken = mActivity.getRealm().where(MedekConfig.class).findFirst().getDiscogsToken();
        if (getArguments() != null && getArguments().containsKey(ALBUM_ID)) {
            Integer id = getArguments().getInt(ALBUM_ID);
            getArguments().remove(ALBUM_ID);
            initData(id);
        }
        return convertView;

    }

    private void initData(Integer id) {
        DiscogsApi.getInstance().getRelease(discogsToken, id, new DiscogsApiRetrofit.DiscogsCallBack<Release>() {
            @Override
            public void success(Release release) {
                albumRelease = release;
                artistText.setText(release.getArtists().get(0).getName());
                titleText.setText(release.getTitle());
                countryText.setText(release.getCountry());
                yearText.setText(release.getYear()+"");
                genreText.setText(release.getGenres().get(0));
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
                position = Integer.valueOf(t.getPosition());
            }
            JsonTrack track = new JsonTrack(null, null, t.getTitle(), position, t.getDuration(),
                    (t.getArtists() == null)?albumRelease.getArtists().get(0).getName():t.getArtists().get(0).getName(), null);
            tracks.add(track);
        }
        a.setTracks(tracks);
        MedekApi.getInstance().createUpdateAlbum(a, new RetrofitManager.MedekCallBack<JsonAlbum>() {
            @Override
            public void success(JsonAlbum album) {
                //a.setId(album.getId());
                Snackbar.make(getView(), "Added "+a.getTitle(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void failure(JsonError error) {
                Snackbar.make(getView(), error.getTitle(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}