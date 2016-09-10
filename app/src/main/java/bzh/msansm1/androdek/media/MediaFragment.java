package bzh.msansm1.androdek.media;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by ronan on 03/09/2016.
 */
public class MediaFragment extends Fragment {

    protected MediaActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MediaActivity) getActivity();
    }
}
