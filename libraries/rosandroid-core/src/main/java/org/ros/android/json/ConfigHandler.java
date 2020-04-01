package org.ros.android.json;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;


/**
 * Created by sumeendranath on 17/08/16.
 */
public class ConfigHandler {


    static JsonHandle _json = new JsonHandle(fileReadWrite.readStringFromFile(StorageProperties.getConfigFile()));


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


}
