package bzh.msansm1.androdek.media.book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bzh.msansm1.androdek.R;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by ronan on 03/09/2016.
 */
public class BookItem extends AbstractFlexibleItem<BookItem.BookViewHolder> {

    private Integer id;
    private String title;
    private String artist;
    private String coverURL;

    public BookItem() {
        setSwipeable(true);
        setEnabled(true);
    }

    public BookItem(Integer id, String title, String artist, String coverURL) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.coverURL = coverURL;
        setSwipeable(true);
        setEnabled(true);
    }

    public BookItem setValues(Integer id, String title, String artist, String coverURL) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.coverURL = coverURL;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BookItem) {
            BookItem inItem = (BookItem) o;
            return this.id.equals(inItem.id);
        }
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_book;
    }

    @Override
    public BookViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater,
                                             ViewGroup parent) {
        return new BookViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, BookViewHolder holder, final int position,
                                          List payloads) {
        holder.title.setText(title);
        holder.artist.setText(artist);
        if (!coverURL.isEmpty()) {
            Picasso.with(holder.mContext).load(coverURL).into(holder.cover);
        }
    }


    public class BookViewHolder extends FlexibleViewHolder {

        public TextView title;
        public TextView artist;
        public ImageView cover;
        public Context mContext;
        private View frontView;
        private View rearLeftView;
        private View rearRightView;

        public BookViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            title = (TextView) view.findViewById(R.id.item_book_title);
            artist = (TextView) view.findViewById(R.id.item_book_artist);
            cover = (ImageView) view.findViewById(R.id.item_book_cover);
            mContext = view.getContext();
            this.frontView = view.findViewById(R.id.book_front_view);
            this.rearLeftView = view.findViewById(R.id.book_rear_left_view);
            this.rearRightView = view.findViewById(R.id.book_rear_right_view);
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
