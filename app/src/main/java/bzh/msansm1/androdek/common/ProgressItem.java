package bzh.msansm1.androdek.common;

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import bzh.msansm1.androdek.R;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by Ronan on 22/11/2016.
 */

public class ProgressItem extends AbstractFlexibleItem<ProgressItem.ProgressViewHolder> {

    @Override
    public boolean equals(Object o) {
        return this == o;//The default implementation
    }

    @Override
    public int getLayoutRes() {
        return R.layout.progress_item;
    }

    @Override
    public ProgressViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new ProgressViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, ProgressViewHolder holder, int position, List payloads) {
        //nothing to bind
    }

    public static class ProgressViewHolder extends FlexibleViewHolder {

        public ProgressBar progressBar;

        public ProgressViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        }

     /*   @Override
        public void scrollAnimators(@NonNull List<Animator> animators, int position, boolean isForward) {
            AnimatorHelper.scaleAnimator(animators, itemView, 0f);
        }*/
    }

}