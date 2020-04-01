package aido.frameanimation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import aido.properties.StorageProperties;

/**
 * Created by sumeendranath on 31/05/16.
 */
public class ResourceManager {

    Resources _resources;
    Context _maincontext;

    public ResourceManager(Context context) {
        _maincontext = context;
        _resources = context.getResources();
    }


    public int getResourceID(String name)
    {
        return      _resources.getIdentifier(name, "drawable",
            _maincontext.getPackageName());

    }

    public Drawable getResourceDrawable(String name)
    {
        return    _resources.getDrawable(getResourceID(name));

    }

    public Drawable getBitmapDrawable(String name)
    {

       // Bitmap bitmap =  decodeSampledBitmapFromResource(_resources,getResourceID(name),640,320); //BitmapFactory.decodeResource(_resources, getResourceID(name));


        Bitmap bitmap =  decodeBitmapFromFile(StorageProperties.getImageDir() + name,640,384);
        BitmapDrawable frame = new BitmapDrawable (bitmap);

        return frame;
    }


    public static Bitmap decodeBitmapFromFile(String pathOfInputImage, int dstWidth, int dstHeight)
    {
        try
        {
            int inWidth = 0;
            int inHeight = 0;

            InputStream in = new FileInputStream(pathOfInputImage);

            // decode image size (decode metadata only, not the whole image)
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            in = null;

            // save width and height
            inWidth = options.outWidth;
            inHeight = options.outHeight;

            // decode full image pre-resized
            in = new FileInputStream(pathOfInputImage);
            options = new BitmapFactory.Options();
            // calc rought re-size (this is no exact resize)
            options.inSampleSize = Math.max(inWidth/dstWidth, inHeight/dstHeight);
            // decode full image
            Bitmap roughBitmap = BitmapFactory.decodeStream(in, null, options);

            // calc exact destination size
            Matrix m = new Matrix();
            RectF inRect = new RectF(0, 0, roughBitmap.getWidth(), roughBitmap.getHeight());
            RectF outRect = new RectF(0, 0, dstWidth, dstHeight);
            m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
            float[] values = new float[9];
            m.getValues(values);

            // resize bitmap
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(roughBitmap, (int) (roughBitmap.getWidth() * values[0]), (int) (roughBitmap.getHeight() * values[4]), true);

            return resizedBitmap;
        }
        catch (IOException e)
        {
            Log.e("Image", e.getMessage(), e);
        }

        return Bitmap.createBitmap(10,10,Bitmap.Config.ARGB_8888);
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
