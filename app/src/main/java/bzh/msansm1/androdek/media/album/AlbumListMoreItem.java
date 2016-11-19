package bzh.msansm1.androdek.media.album;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
            AlbumListMoreItem inItem = (AlbumListMoreItem) o;
            return this.equals(inItem);
        }
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_album_more;
    }

    @Override
    public AlbumListMoreItem.MoreItemViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater,
                                                      ViewGroup parent) {
        return new AlbumListMoreItem.MoreItemViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    public class MoreItemViewHolder extends FlexibleViewHolder {

        public Context mContext;

        public MoreItemViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            mContext = view.getContext();
        }

    }
}
