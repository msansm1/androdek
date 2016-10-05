package bzh.msansm1.androdek.media.album;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import bzh.msansm1.androdek.R;
import bzh.msansm1.androdek.media.MediaFragment;
import bzh.msansm1.androdek.persistence.MedekConfig;
import bzh.msansm1.androdek.persistence.media.AlbumSearch;
import bzh.msansm1.discogsapi.DiscogsApi;
import bzh.msansm1.discogsapi.DiscogsApiRetrofit;
import bzh.msansm1.discogsapi.json.DiscogsError;
import bzh.msansm1.discogsapi.json.release.Release;
import bzh.msansm1.discogsapi.json.search.SearchResponse;

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
}
