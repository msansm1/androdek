package bzh.msansm1.androdek.media.book;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import bzh.msansm1.androdek.R;
import bzh.msansm1.androdek.media.MediaFragment;
import bzh.msansm1.androdek.media.book.BookFragment;
import bzh.msansm1.androdek.media.book.BookItem;
import bzh.msansm1.androdek.persistence.MedekConfig;
import bzh.msansm1.medekapi.MedekApi;
import bzh.msansm1.medekapi.RetrofitManager;
import bzh.msansm1.medekapi.json.JsonError;
import bzh.msansm1.medekapi.json.book.JsonBook;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * A placeholder fragment containing a simple view.
 */
public class BookFragment extends MediaFragment {

    @BindView(R.id.add_book)
    FloatingActionButton addBook;

    @BindView(R.id.booksList)
    RecyclerView booksList;

    @BindView(R.id.booksEmpty)
    TextView booksEmpty;

    private RecyclerView.LayoutManager mLayoutManager;
    private FlexibleAdapter<IFlexible> adapter;

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

        mLayoutManager = new LinearLayoutManager(mActivity);
        booksList.setLayoutManager(mLayoutManager);

        final MedekConfig conf = mActivity.getRealm().where(MedekConfig.class).findFirst();

        final List<IFlexible> bookItems = new ArrayList<>();
        MedekApi.getInstance().getAllBooks(0, 50, "title", "asc", new RetrofitManager.MedekCallBack<List<JsonBook>>() {
            @Override
            public void success(List<JsonBook> jsonBooks) {
                if (!jsonBooks.isEmpty()) {
                    booksEmpty.setVisibility(View.GONE);
                    List<IFlexible> newItems = new ArrayList<>();
                    for (JsonBook b : jsonBooks) {
                        newItems.add(new BookItem(b.getId(), b.getTitle(), b.getAuthor(), conf.getApiUrl()+"medekimg/book/"+b.getId()+"/cover.jpg"));
                    }
                    adapter.updateDataSet(newItems);
                }
            }

            @Override
            public void failure(JsonError error) {
                Snackbar.make(getView(), "Get Book Error", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (!bookItems.isEmpty()) {
            booksEmpty.setVisibility(View.GONE);
        }

        //Initialize the Adapter
        adapter = new FlexibleAdapter<>(bookItems);

        adapter.setAnimationOnScrolling(true)
                .setAnimationOnReverseScrolling(true);
        adapter.setMode(SelectableAdapter.MODE_SINGLE);

        //Initialize the RecyclerView and attach the Adapter to it as usual
        booksList.setAdapter(adapter);

        adapter.setSwipeEnabled(true);



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
