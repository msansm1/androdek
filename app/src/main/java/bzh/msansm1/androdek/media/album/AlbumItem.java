package bzh.msansm1.androdek.media.album;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;
import bzh.msansm1.androdek.R;

/**
 * Created by ronan on 03/09/2016.
 */
public class AlbumItem extends AbstractFlexibleItem<AlbumItem.AlbumViewHolder> {

    private Integer id;
    private String title;
    private String artist;
    private String coverURL;

    public AlbumItem() {
        setSwipeable(true);
        setEnabled(true);
    }

    public AlbumItem(Integer id, String title, String artist, String coverURL) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.coverURL = coverURL;
        setSwipeable(true);
        setEnabled(true);
    }

    public AlbumItem setValues(Integer id, String title, String artist, String coverURL) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.coverURL = coverURL;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AlbumItem) {
            AlbumItem inItem = (AlbumItem) o;
            return this.id.equals(inItem.id);
        }
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_album;
    }

    @Override
    public AlbumViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater,
                                             ViewGroup parent) {
        return new AlbumViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }


    public class AlbumViewHolder extends FlexibleViewHolder {

        public TextView title;
        public TextView artist;
        public ImageView cover;
        private Context mContext;
        private View frontView;
        private View rearLeftView;
        private View rearRightView;

        public AlbumViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            title = (TextView) view.findViewById(R.id.item_album_title);
            artist = (TextView) view.findViewById(R.id.item_album_artist);
            Picasso.with(mContext).load(coverURL).into(cover);
            mContext = view.getContext();
            this.frontView = view.findViewById(R.id.album_front_view);
            this.rearLeftView = view.findViewById(R.id.album_rear_left_view);
            this.rearRightView = view.findViewById(R.id.album_rear_right_view);
        }

        @Override
        public View getFrontView() {
            return frontView;//default itemView
        }

        @Override
        public View getRearLeftView() {
            return rearLeftView;//default null
        }

        @Override
        public View getRearRightView() {
            return rearRightView;//default null
        }
    }
}
