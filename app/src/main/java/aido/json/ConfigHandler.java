package aido.json;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.whitesuntech.aidohomerobot.R;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import aido.file.fileReadWrite;
import aido.properties.ConfigProperties;
import aido.properties.StorageProperties;

/**
 * Created by sumeendranath on 17/08/16.
 */
public class ConfigHandler {


    static JsonHandle _json = new JsonHandle(fileReadWrite.readStringFromFile(StorageProperties.getConfigFile()));
    static JsonHandle _json1 = new JsonHandle(fileReadWrite.readStringFromFile(StorageProperties.getIpFile()));

    public static String getModuleName(String field)
    {
        Log.i("JSON",field + " MODULE NAME : " +  _json.getValue(field));

        return _json.getValue(field);
    }

    public static String getPackageName(String field)
    {
        String modulename= _json.getValue(field);
        Log.i("JSON",field + " PACKAGE NAME : " + modulename.substring(0, modulename.lastIndexOf('.') ));
        return modulename.substring(0, modulename.lastIndexOf('.'));

    }

    public static void dumpDefaultConfig(boolean force) {
        if (force || !fileReadWrite.fileExists(StorageProperties.getConfigFile())) {
            fileReadWrite.writeIntoFile(ConfigProperties.DEFAULT_JSON, StorageProperties.getConfigFile());

        }
    }


    public static void copyDefaultUI(Context context, boolean force)
    {
        final R.drawable drawableResources = new R.drawable();
        final Class<R.drawable> c = R.drawable.class;
        final Field[] fields = c.getDeclaredFields();

        for (int i = 0, max = fields.length; i < max; i++) {
            final int resourceId;
            try {
                resourceId = fields[i].getInt(drawableResources);


                Bitmap bm = BitmapFactory.decodeResource( context.getResources(), resourceId);

                if(force || !fileReadWrite.fileExists(StorageProperties.getImageDir() + context.getResources().getResourceEntryName(resourceId))) {

                    File file = new File(StorageProperties.getImageDir(), context.getResources().getResourceEntryName(resourceId));
                    FileOutputStream outStream = new FileOutputStream(file);
                    bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                }



            } catch (Exception e) {
                continue;
            }
    /* make use of resourceId for accessing Drawables here */
        }
    }
}
