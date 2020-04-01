package aido.properties;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sumeendranath on 30/05/16.
 */
public class PermittedApps {

    public static final Map<String, String> PROP_PERMITTEDAPPS_HASH;
    static {
        Map<String, String> aMap = new HashMap<String, String>();

        aMap.put("com.facebook.katana","https://play.google.com/store/apps/details?id=com.facebook.katana&hl=en");
        aMap.put("com.rovio.baba", "https://play.google.com/store/apps/details?id=com.rovio.baba&hl=en");
        aMap.put("com.featherweightgames.skiiing", "https://play.google.com/store/apps/details?id=com.featherweightgames.skiiing&hl=en");
        aMap.put("com.noodlecake.brickies", "https://play.google.com/store/apps/details?id=com.noodlecake.brickies&hl=en");
       // aMap.put("com.superevilmegacorp.game", "https://play.google.com/store/apps/details?id=com.superevilmegacorp.game&hl=en");
        aMap.put("com.bulkypix.wiredefuser", "https://play.google.com/store/apps/details?id=com.bulkypix.wiredefuser&hl=en");
        aMap.put("com.zynga.wwf2.free", "https://play.google.com/store/apps/details?id=com.zynga.wwf2.free&hl=en");
        aMap.put("zok.android.dots", "https://play.google.com/store/apps/details?id=zok.android.dots&hl=en");
        aMap.put("com.appledore", "https://play.google.com/store/apps/details?id=com.appledore&hl=en ");
        aMap.put("zok.android.numbers", "https://play.google.com/store/apps/details?id=zok.android.numbers&hl=en");
        aMap.put("com.oki.colors", "https://play.google.com/store/apps/details?id=com.oki.colors&hl=en");
        aMap.put("air.fisherprice.com.Puppy", "https://play.google.com/store/apps/details?id=air.fisherprice.com.Puppy&hl=en");
        aMap.put("com.soundtouch.lite", "https://play.google.com/store/apps/details?id=com.soundtouch.lite&hl=en");
        aMap.put("org.videolan.vlc", "https://play.google.com/store/apps/details?id=org.videolan.vlc&hl=en");
        aMap.put("com.pipcamera.activity", "https://play.google.com/store/apps/details?id=com.pipcamera.activity&hl=en");
        aMap.put("com.infomarvel.istorybooks", "https://play.google.com/store/apps/details?id=com.infomarvel.istorybooks&hl=en");
        aMap.put("com.edjing.edjingdjturntable", "https://play.google.com/store/apps/details?id=com.edjing.edjingdjturntable&hl=en");
        aMap.put("com.google.android.apps.maps", "https://play.google.com/store/apps/details?id=com.google.android.apps.maps&hl=en");
        aMap.put("com.android.vending","NA");
        aMap.put("de.androidpit.app","NA");
        aMap.put("com.whitesun.aidoface","NA");
        aMap.put("com.whitesun.aidohomerobot","NA");
        aMap.put("com.llamalab.automate","https://play.google.com/store/apps/details?id=com.llamalab.automate&hl=en");
        aMap.put("com.example.archi.health","NA");
        aMap.put("com.archirayan.kitchen","NA");
        aMap.put("com.rathore.newsweathertraffic","NA");
        aMap.put("com.example.anil.temperature","NA");
        aMap.put("com.rathore.evernoteapi","NA");
        aMap.put("com.rathore.aidoalertsystem","NA");
        aMap.put("com.example.anil.mobilityapp","NA");

        aMap.put("com.example.archi.homemaintenance","NA");
        aMap.put("com.rathore.socialtelecast","NA");
        aMap.put("com.example.madan.lircdemo3","NA");
        aMap.put("com.example.lenovo.next_song","NA");

        aMap.put("com.example.madan.universalremote","NA");
        aMap.put("com.example.ajeet.app_recomended","NA ");

        aMap.put("com.rathore.medicinetableview","NA ");
        aMap.put("com.rathore.aidoalldrinkstable","NA ");
        aMap.put("com.rathore.aidohealthmedicinetableview","NA ");
        //aMap.put("com.rathore.aidoreceipetable","NA ");
        aMap.put("com.rathore.aidoreceipetable","NA ");
        aMap.put("com.rathore.aidogrocerytable","NA ");
        aMap.put("com.rathore.aidovirtualwinemanagertable","NA ");
        //aMap.put("com.rathore.aidovirtualwinemanagertable","NA ");
        aMap.put("com.rathore.aidohealthtableview","NA ");
        aMap.put("com.rathore.aidodiettableview","NA ");
        aMap.put("com.example.anil.videoplayer", "NA");
        aMap.put("com.example.newmenu","NA");
        aMap.put("com.example.newreminder","NA");
        aMap.put("techheromanish.example.com.videochatapp","NA");
        aMap.put("com.example.anil.alrammanagerexample","NA");
        aMap.put("com.example.memorygame","NA");

        aMap.put("com.yusufcakmak.exoplayersample","NA");



        //aMap.put("com.example.archi.health.Activity.ActivityDrugInformation","NA");
       // aMap.put("com.example.ajeet.facerecognition1","NA");
        //aMap.put("com.example.ajeet.facerecognition1","NA");
        PROP_PERMITTEDAPPS_HASH = Collections.unmodifiableMap(aMap);
    }


    public static final Map<String, String> PROP_APPNAMES;
    static {
        Map<String, String> aMap = new HashMap<String, String>();

        aMap.put("com.facebook.katana","facebook");
        aMap.put("com.rovio.baba", "Angry Birds");
        aMap.put("com.featherweightgames.skiiing", "Skiing Yeti Mountain");
        aMap.put("com.noodlecake.brickies", "brickies");
        // aMap.put("com.superevilmegacorp.game", "https://play.google.com/store/apps/details?id=com.superevilmegacorp.game&hl=en");
        aMap.put("com.bulkypix.wiredefuser", "Wire Defuser");
        aMap.put("com.zynga.wwf2.free", "Words With Friends");
        aMap.put("zok.android.dots", "Kids Connect the Dots");
        aMap.put("com.appledore", "Toddler World Learn");
        aMap.put("zok.android.numbers", "Kids Numbers and Math");
        aMap.put("com.oki.colors", "Learning colors for kids");
        aMap.put("air.fisherprice.com.Puppy", "Learning Letters Puppy");
        aMap.put("com.soundtouch.lite", "Sound Touch Lite");
        aMap.put("org.videolan.vlc", "vlc");
        aMap.put("com.pipcamera.activity", "pip camera");
        aMap.put("com.infomarvel.istorybooks", "istory");
        aMap.put("com.edjing.edjingdjturntable", "dj music");
        aMap.put("com.google.android.apps.maps", "maps");
        aMap.put("com.android.vending","NA");
        aMap.put("de.androidpit.app","NA");
        aMap.put("com.whitesun.aidoface","NA");
        aMap.put("com.example.anil.videoplayer", "NA");

        aMap.put("com.whitesun.aidohomerobot","box");
        aMap.put("com.example.archi.health","health");
        aMap.put("com.archirayan.kitchen","kitchen");
        aMap.put("com.example.anil.mobilityapp","Mobility");
        aMap.put("com.example.anil.temperature","temperature");
        aMap.put("com.example.lenovo.next_song","next_song");

       // aMap.put("com.example.archi.health.Activity.ActivityDrugInformation","medicine");


        aMap.put("com.llamalab.automate.Automate","automate");

        aMap.put("com.rathore.newsweathertraffic","News");
     //   aMap.put("com.example.ajeet.sensor","sensor");
        aMap.put("com.rathore.evernoteapi","Personal Assistance");
        aMap.put("com.rathore.aidoalertsystem","Alert");

        aMap.put("com.example.archi.homemaintenance","home maintenance");
        aMap.put("com.rathore.socialtelecast","social telecast");

        aMap.put("com.example.madan.universalremote","universal remote");
        aMap.put("com.example.madan.lircdemo3","lirc");
        aMap.put("com.example.ajeet.app_recomended","app recomender ");

        aMap.put("com.rathore.medicinetableview"," medicine record ");
        aMap.put("com.rathore.aidoalldrinkstable","drink record ");
        aMap.put("com.rathore.aidohealthmedicinetableview","health reminder record ");
        aMap.put("com.rathore.aidoreceipetable","receipe record ");
        // aMap.put("com.rathore.aidoreceipetable","NA ");
        aMap.put("com.rathore.aidogrocerytable","grocery record ");
        aMap.put("com.rathore.aidovirtualwinemanagertable","virtual wine manager record ");
        // aMap.put("com.rathore.aidovirtualwinemanagertable","NA ");
        aMap.put("com.rathore.aidohealthtableview","health record ");
        aMap.put("com.rathore.aidodiettableview","diet record ");
        aMap.put("com.example.ajeet.facerecognition1","feel");
        aMap.put("com.example.newmenu","menu");
        aMap.put("com.example.newreminder","reminder");
        aMap.put("techheromanish.example.com.videochatapp","videochat");
        aMap.put("com.example.anil.alrammanagerexample","alarm");
        aMap.put("com.example.memorygame","memory");

        //aMap.put("com.example.ajeet.facerecognition1"," I feel");
        //aMap.put("com.example.ajeet.facerecognition1","feel");

        aMap.put("com.yusufcakmak.exoplayersample","radio");

        PROP_APPNAMES = Collections.unmodifiableMap(aMap);
    }
}
