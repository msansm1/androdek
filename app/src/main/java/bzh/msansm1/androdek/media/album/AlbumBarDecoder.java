package bzh.msansm1.androdek.media.album;

import android.util.Log;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

import eu.livotov.labs.android.camview.scanner.decoder.BarcodeDecoder;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.PlanarRotatedYUVLuminanceSource;

/**
 * Created by ronan on 29/09/2016.
 */

public class AlbumBarDecoder implements BarcodeDecoder {

    public enum AlbumBarcodeFormat { UPC_A, UPC_E, UPC_EAN_EXTENSION };
    protected Map<DecodeHintType, Object> hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);

    private MultiFormatReader reader;
    private double scanAreaPercent = 0.3;
    private double scanAreaPercentWidth= 0.8;

    public AlbumBarDecoder() {
        reader = new MultiFormatReader();

        hints.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.allOf(AlbumBarcodeFormat.class));
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        hints.put(DecodeHintType.TRY_HARDER, true);

        reader.setHints(hints);
    }

    public double getScanAreaPercent() {
        return scanAreaPercent;
    }

    public void setScanAreaPercent(double scanAreaPercent) {
        if (scanAreaPercent<0.1 || scanAreaPercent>1.0) {
            throw new IllegalArgumentException("Scan area percent must be between 0.1 (10%) to 1.0 (100%). Specified value was " + scanAreaPercent);
        }

        this.scanAreaPercent = scanAreaPercent;
    }

    public String decode(final byte[] image, final int width, final int height) {
        Result result = null;

        final int scanWidth = (int)(width * (width>height?scanAreaPercentWidth:scanAreaPercent));
        final int scanHeight = (int)(height * (width>height?scanAreaPercent:scanAreaPercentWidth));
        final int scanAreaLeft = width/2-scanWidth/2;
        final int scanAreaRight = width/2+scanWidth/2;
        final int scanAreaTop = height/2-scanHeight/2;
        final int scanAreaBottom = height/2+scanHeight/2;

        // Then try it 90 degrees rotated (works for 1D codes)
        try {
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new PlanarRotatedYUVLuminanceSource(image, width, height, scanAreaLeft, scanAreaTop, scanAreaRight, scanAreaBottom, true)));
            result = reader.decodeWithState(bitmap);

            if (result != null) {
                return result.getText();
            }
        } catch (Throwable re) {
            Log.e("Scan", "Album", re);
        } finally {
            reader.reset();
        }

        return null;
    }
}
