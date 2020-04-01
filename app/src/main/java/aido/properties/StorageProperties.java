package aido.properties;

import android.os.Environment;

import java.io.File;

import aido.file.fileReadWrite;

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


    public static String getRecipeImage()
    {
        String filename = getRootDirectory() + "recipe.jpg";

        return filename;
    }


    public static String getImageDir()
    {
        String dirName =  getRootDirectory() + getSeparator() + "images" + getSeparator();
        File tempf = new File(dirName);

        if(!tempf.exists())
        {
            tempf.mkdirs();
        }

        return dirName;

    }

    public static String getSceneDir()
    {
        String dirName =  getRootDirectory() + getSeparator() + "scene" + getSeparator();
        File tempf = new File(dirName);

        if(!tempf.exists())
        {
            tempf.mkdirs();
        }

        return dirName;

    }

    public static String getConfigFile()
    {
        String filename = getRootDirectory() + "config.json.txt";

        return filename;
    }
    public static String getIpFile(){
        String filename = getRootDirectory() + "ip.json.txt";

        return filename;

    }



    public static String getBehaviorDir()
    {
        String filename = getRootDirectory() + "behave" + getSeparator();

        return filename;
    }

    public static String getBehaviorFile(String name)
    {
        String filename = getBehaviorDir() + name;

        return filename;
    }

    public static String getMovieFile1()
    {
        String filename = getRootDirectory() + "movie1.mp4";

        return filename;
    }
    public static String getMovieFile2()
    {
        String filename = getRootDirectory() + "movie2.mp4";

        return filename;
    }
    public static String getMovieFile3()
    {
        String filename = getRootDirectory() + "movie3.mp4";

        return filename;
    }
    public static String getMovieFile4()
    {
        String filename = getRootDirectory() + "movie4.mp4";

        return filename;
    }
    public static String getMovieFile5()
    {
        String filename = getRootDirectory() + "movie5.mp4";

        return filename;
    }
    public static String getMovieFile6()
    {
        String filename = getRootDirectory() + "movie6.mp4";

        return filename;
    }
    public static String getMovieFile7()
    {
        String filename = getRootDirectory() + "movie7.mp4";

        return filename;
    }
    public static String getMovieFile8()
    {
        String filename = getRootDirectory() + "movie8.mp4";

        return filename;
    }
    public static String getMovieFile9()
    {
        String filename = getRootDirectory() + "movie9.mp4";

        return filename;
    }

    public static String getMovieFile(int num)
    {
        String filename = getRootDirectory() + "movie" + num + ".mp4";

        return filename;
    }

    public static String getTestMovieFile(int num)
    {
        String filename = getRootDirectory() + "m" + num + ".m4v";

        return filename;
    }

    public static String getDemoMovieFile(String name)
    {
        String filename = getRootDirectory() + name;

        return filename;
    }

    public static String getDemoFile()
    {
        String filename = getRootDirectory() + "demo.txt";

        return filename;
    }
    public static String getDemoFile(String file)
    {
        String filename = getRootDirectory() + file;

        return filename;
    }

    public static String getSceneRecogPhotoPath(String name)
    {
        String filename = getSceneDir() + name;

        return filename;
    }
}
