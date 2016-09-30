package bzh.msansm1.androdek.media.album;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import bzh.msansm1.androdek.R;
import bzh.msansm1.androdek.media.MediaFragment;
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

    public static AlbumScanFragment getFragment() {
        return new AlbumScanFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.fragment_album_scan, container, false);
        ButterKnife.bind(this, convertView);
        scanText.setText(getString(R.string.scan_album_code));
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
        liveView.setHudImageResource(R.drawable.scan_layer);
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
        showErrorMessage();
    }

    @Override
    public void onCodeScanned(String data) {
        if (this.isDetached() || scanOK) return;
        if (data.length() == 10) {
            scanOK = true;
            scanText.setText(getString(R.string.search_discogs));
            // call discogs
        } else {
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