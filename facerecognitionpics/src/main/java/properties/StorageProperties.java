package properties;

import android.os.Environment;

import java.io.File;

/**
 * Created by sumeendranath on 30/06/16.
 */
public class StorageProperties {



    public static String getMemoryCard()
    {
        return Environment.getExternalStorageDirectory() + getSeparator();
    }

    public static String getRootDirectory()
    {
        String dirName =  getMemoryCard() + getSeparator() + "AIDO" + getSeparator();
        File tempf = new File(dirName);

        if(!tempf.exists())
        {
            tempf.mkdirs();
        }

        return dirName;
    }

    public static String getSeparator()
    {
        return File.separator;
    }

    public static String facereczipfile()
    {
        String filename = getFolderSharePath() + "faces.zip" ;


        return filename;
    }

    public static String getFaceRecGalleryPath()
    {
        String dirName = getRootDirectory() + "facerecgallery" + getSeparator();
        File tempf = new File(dirName);

        if(!tempf.exists())
        {
            tempf.mkdirs();
        }

        return dirName;
    }

    public static String getFolderSharePath()
    {
        String dirName = getRootDirectory() + "share" + getSeparator();
        File tempf = new File(dirName);

        if(!tempf.exists())
        {
            tempf.mkdirs();
        }

        return dirName;
    }


}