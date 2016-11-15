package bzh.msansm1.androdek.media.album;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import bzh.msansm1.androdek.R;
import bzh.msansm1.androdek.media.MediaFragment;
import bzh.msansm1.androdek.persistence.MedekConfig;
import bzh.msansm1.androdek.persistence.media.AlbumSearch;
import bzh.msansm1.discogsapi.DiscogsApi;
import bzh.msansm1.discogsapi.DiscogsApiRetrofit;
import bzh.msansm1.discogsapi.json.DiscogsError;
import bzh.msansm1.discogsapi.json.search.SearchResponse;
import bzh.msansm1.discogsapi.json.search.SearchResult;
import bzh.msansm1.medekapi.RetrofitManager;
import bzh.msansm1.medekapi.json.JsonError;
import eu.livotov.labs.android.camview.ScannerLiveView;

import static android.Manifest.permission.CAMERA;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * Created by ronan on 29/09/2016.
 */

public class AlbumScanFragment extends MediaFragment implements ScannerLiveView.ScannerViewEventListener {

    @BindView(R.id.album_scan_preview)
    ScannerLiveView liveView;

    @BindView(R.id.album_scan_text)
    TextView scanText;

    private static final int REQUEST_CAMERA_STAT = 1;
    private boolean scanOK = false;
    private String discogsToken;

    public static AlbumScanFragment getFragment() {
        return new AlbumScanFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.fragment_album_scan, container, false);
        ButterKnife.bind(this, convertView);
        discogsToken = mActivity.getRealm().where(MedekConfig.class).findFirst().getDiscogsToken();
        scanText.setText(getString(R.string.scan_album_code));
        mActivity.getRealm().beginTransaction();
        mActivity.getRealm().delete(AlbumSearch.class);
        mActivity.getRealm().commitTransaction();
        mActivity.getSupportActionBar().setTitle("Album scan");
        return convertView;

    }

    @Override
    public void onResume() {
        super.onResume();

        if (hasPermissions()) {
            loadScanner();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        liveView.stopScanner();
    }

    private boolean hasPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(getContext(), CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(CAMERA)) {

        } else {
            requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA_STAT);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (grantResults.length > 0) {
            switch (requestCode) {
                case REQUEST_CAMERA_STAT: {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        new AlertDialog.Builder(getContext())
                                .setMessage(getString(R.string.permission_denied))
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //TODO
                                    }
                                })
                                .show();
                    } else {
                        loadScanner();
                    }
                    break;
                }
            }
        }
    }

    private void loadScanner() {
        AlbumBarDecoder decoder = new AlbumBarDecoder();
        decoder.setScanAreaPercent(0.47);
        liveView.setDecoder(decoder);
        liveView.setSameCodeRescanProtectionTime(10000);
        liveView.setDecodeThrottleMillis(500);
        liveView.setScannerViewEventListener(this);
        liveView.setHudImageResource(R.drawable.scan_area);
        liveView.startScanner();

    }

    @Override
    public void onScannerStarted(ScannerLiveView scanner) {

    }

    @Override
    public void onScannerStopped(ScannerLiveView scanner) {

    }

    @Override
    public void onScannerError(Throwable err) {
        Log.e("SCANNER", "Error album scan", err);
        err.printStackTrace();
        showErrorMessage();
    }

    @Override
    public void onCodeScanned(String data) {
        if (this.isDetached() || scanOK) return;
        if (data.length() == 13) {
            scanOK = true;
            scanText.setText(getString(R.string.search_discogs)+" "+data);
            DiscogsApi.getInstance().searchAlbumByBarcode(discogsToken, data, new DiscogsApiRetrofit.DiscogsCallBack<SearchResponse>() {
                @Override
                public void success(SearchResponse searchResponse) {
                    Snackbar.make(getView(), "Results : "+searchResponse.getResults().size(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mActivity.getRealm().beginTransaction();
                    for (SearchResult res : searchResponse.getResults()) {
                        AlbumSearch albumSearch = new AlbumSearch();
                        albumSearch.setId(res.getId());
                        albumSearch.setTitle(res.getTitle());
                        albumSearch.setCountry(res.getCountry());
                        albumSearch.setYear(res.getYear());
                        albumSearch.setCoverURL(res.getThumb());
                        albumSearch.setResourceURL(res.getResource_url());
                        mActivity.getRealm().copyToRealm(albumSearch);
                    }
                    mActivity.getRealm().commitTransaction();
                    mActivity.getSupportFragmentManager().beginTransaction().add(R.id.mediaFragment, AlbumScanResultFragment.getFragment()).addToBackStack("albumscanresults").commit();
                }

                @Override
                public void failure(DiscogsError error) {
                    Snackbar.make(getView(), error.getTitle(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        } else {
            Log.e("Barcode error", "length not 10 : "+data.length());
            showErrorMessage();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void showErrorMessage() {
        scanText.setText(getString(R.string.error_scan));
    }
}