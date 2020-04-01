package aido.properties;

import java.io.File;

/**
 * Created by sumeendranath on 30/05/16.
 */
public class SettingProperties {
    public static String getSettingsDir()
    {
        String name= StorageProperties.getRootDirectory() + "settings" + StorageProperties.getSeparator();
        File tempf = new File(name);

        if(!tempf.exists())
        {
            tempf.mkdirs();
        }

        return name;

    }

    public static String getTvSettingsFile()
    {
        String name= getSettingsDir() + "tvsettings.txt";

        return name;

    }

    public static String getPhoneSettingsFile()
    {
        String name= getSettingsDir() + "tabletsettings.txt";

        return name;

    }

    public static String getHomeApplianceSettingsFile()
    {
        String name= getSettingsDir() + "homeappliance.txt";

        return name;

    }
    public static String getUniversalRemoteSettingsFile()
    {
        String name= getSettingsDir() + "universalremote.txt";

        return name;

    }

    public static String getSocialSettingsFile()
    {
        String name= getSettingsDir() + "socialsettings.txt";

        return name;

    }


}
