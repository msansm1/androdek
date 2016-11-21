package bzh.msansm1.androdek.media.album;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import bzh.msansm1.androdek.R;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by ronan on 18/11/2016.
 */

public class AlbumListMoreItem extends AbstractFlexibleItem<AlbumListMoreItem.MoreItemViewHolder> {

    public AlbumListMoreItem() {
        setEnabled(true);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AlbumListMoreItem) {
            return true;
        }
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_album_more;
    }

    @Override
    public MoreItemViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater,
                                                      ViewGroup parent) {
        return new MoreItemViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, MoreItemViewHolder holder, final int position,
                               List payloads) {
    }

    public class MoreItemViewHolder extends FlexibleViewHolder {

        public Context mContext;

        public MoreItemViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            mContext = view.getContext();
        }

    }
}
