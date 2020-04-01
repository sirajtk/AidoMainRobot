package aido.properties;

import java.io.File;

/**
 * Created by sumeendranath on 26/01/17.
 */
public class BehaveProperties {

    public static String getUiVideosDir()
    {
        String name= StorageProperties.getRootDirectory() + "uivideos" + StorageProperties.getSeparator();
        File tempf = new File(name);

        if(!tempf.exists())
        {
            tempf.mkdirs();
        }

        return name;

    }

    public static String getUiVideosDirLeaf()
    {
        String name=   "uivideos" + StorageProperties.getSeparator();

        return name;

    }


    public static String getRawBehaviorDirLeaf()
    {
        String name=  "rawbehavior" + StorageProperties.getSeparator();

        return name;
    }

    public static String getRawBehaviorDir()
    {

        String name= StorageProperties.getRootDirectory() + getRawBehaviorDirLeaf();
                File tempf = new File(name);

        if(!tempf.exists())
        {
            tempf.mkdirs();
        }

        return name;
    }

}
