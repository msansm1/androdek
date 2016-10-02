package bzh.msansm1.androdek.media.album;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bzh.msansm1.androdek.R;
import bzh.msansm1.androdek.media.MediaActivity;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by ronan on 03/09/2016.
 */
public class AlbumScanResultItem extends AbstractFlexibleItem<AlbumScanResultItem.AlbumViewHolder> {

    private Integer id;
    private String title;
    private String artist;
    private String year;
    private String country;
    private String coverURL;
    private String resourceURL;

    public AlbumScanResultItem() {
        setSwipeable(true);
        setEnabled(true);
    }

    public AlbumScanResultItem(Integer id, String title, String artist,
                               String year, String country, String coverURL, String resourceURL) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.country = country;
        this.coverURL = coverURL;
        this.resourceURL = resourceURL;
        setSwipeable(true);
        setEnabled(true);
    }

    public AlbumScanResultItem setValues(Integer id, String title, String artist,
                                         String year, String country, String coverURL) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.country = country;
        this.coverURL = coverURL;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AlbumScanResultItem) {
            AlbumScanResultItem inItem = (AlbumScanResultItem) o;
            return this.id.equals(inItem.id);
        }
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_album_scan_result;
    }

    @Override
    public AlbumViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater,
                                             ViewGroup parent) {
        return new AlbumViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, AlbumViewHolder holder, final int position,
                                          List payloads) {
        holder.title.setText(title);
        holder.artist.setText(artist);
        holder.year.setText(year);
        holder.country.setText(country);
        if (!coverURL.isEmpty()) {
            Picasso.with(holder.mContext).load(coverURL).into(holder.cover);
        }
    }


    public class AlbumViewHolder extends FlexibleViewHolder {

        public TextView title;
        public TextView artist;
        public TextView year;
        public TextView country;
        public ImageView cover;
        public Context mContext;

        public AlbumViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            title = (TextView) view.findViewById(R.id.item_album_scan_title);
            artist = (TextView) view.findViewById(R.id.item_album_scan_artist);
            year = (TextView) view.findViewById(R.id.item_album_scan_year);
            country = (TextView) view.findViewById(R.id.item_album_scan_country);
            cover = (ImageView) view.findViewById(R.id.item_album_scan_cover);
            mContext = view.getContext();
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            // frag details
        }
    }
}
