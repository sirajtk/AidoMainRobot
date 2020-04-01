package com.whitesun.aidoface.settings.companion;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.whitesun.aidoface.AidoFace;
import com.whitesuntech.aidohomerobot.R;

import java.util.EnumMap;
import java.util.Map;

import aido.common.CommonlyUsed;

public class CompanionAppBitMapPair extends AppCompatActivity {


    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companion_app_bit_map_pair);


        setTitle("Scan QR Code to Pair Companion App");

        ImageView imageView = findViewById(R.id.imageView);

        try {

            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
            String aidoskypename = settings.getString("aidoskypename", "");

            if(CommonlyUsed.stringIsNullOrEmpty(aidoskypename))
            {
                CommonlyUsed.showMsg(this,"First set skype ID of Aido");
                finish();
            }




            bitmap = encodeAsBitmap(AidoFace.fire_aidoid +"||" + aidoskypename ); //TextToImageEncode("This is a trial");

            imageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }


    }


    public static int WHITE = 0xFFFFFFFF;
    public static int BLACK = 0xFF000000;
    public final static int WIDTH=500;


    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, WIDTH, 0, 0, w, h);
        return bitmap;
    }

    Bitmap TextToImageEncode(String Value) throws WriterException {


        Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        // Now with zxing version 3.2.1 you could change border size (white border size to just 1)
        hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);



        BitMatrix bitMatrix;
        try {

            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            bitMatrix = qrCodeWriter.encode(Value, BarcodeFormat.QR_CODE, QRcodeWidth, QRcodeWidth, hintMap);
                    /*
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );*/



        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }



        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

}
