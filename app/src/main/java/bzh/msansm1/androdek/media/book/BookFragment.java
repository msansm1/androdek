package bzh.msansm1.androdek.media.book;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import bzh.msansm1.androdek.R;
import bzh.msansm1.androdek.media.MediaFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class BookFragment extends MediaFragment {

    @BindView(R.id.add_book)
    FloatingActionButton addBook;

    public BookFragment() {
    }

    public static BookFragment getFragment(){
        return new BookFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        ButterKnife.bind(this,view);

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add Book", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }
}
