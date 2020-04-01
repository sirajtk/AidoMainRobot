package properties;



import com.whitesuntech.texttospeech.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by sumeendranath on 08/06/16.
 */
public class VoiceProperties {


    public static String PROP_STT_BROADCAST_INTENTID = "com.whitesuntech.stt";
    public static String PROP_STT_RECEIVER_INTENTID = "com.whitesuntech.speechtotext";
    public static String PROP_SPEECHTOTEXT_MESSSAGEFIELD = "message";


    public static String PROP_TTS_RECEIVER_INTENTID = "com.whitesuntech.texttospeech";




    public static String PROP_TTS_INTENTID = "com.whitesuntech.tts";
    public static String TTS_MESSSAGEFIELD = "message";


    public static final Map<String, Integer> PROP_VOICE_HASH;
    static {
        Map<String, Integer> aMap = new HashMap<String, Integer>();
        aMap.put("Hi, I am emma, one of the available high quality text to speech voices. Select download now, to install my voice. Back to. ",
                R.raw.emma);

        aMap.put("Aido", R.raw.aido);
        aMap.put("aidoping", R.raw.ping);
        aMap.put("crazylaugh", R.raw.crazylaugh);
        aMap.put("textmessage", R.raw.textmessage);
       // aMap.put("ifeel", R.raw.file1);
        aMap.put("ifeel", R.raw.file11);
       // aMap.put("seeappliances", R.raw.file2);
        aMap.put("seeappliances", R.raw.file22);
//
        aMap.put("suffringfrom", R.raw.file33);
        aMap.put("traffic", R.raw.file44);
        aMap.put("weather", R.raw.file55);
        aMap.put("searchrecipe", R.raw.file66);
        aMap.put("nearbyhospitals", R.raw.file777);
        aMap.put("howtomake", R.raw.file88);
        aMap.put("surroundings", R.raw.file99);
        aMap.put("manypeople", R.raw.newfile10);
        aMap.put("cold", R.raw.filem1414);
        aMap.put("yourhealth", R.raw.filem1515);
        aMap.put("humid", R.raw.filem1616);
        aMap.put("doyouhaveenoughjuice", R.raw.filem1717);
        aMap.put("newapps", R.raw.newappsnew);
        aMap.put("seeallnotes", R.raw.filem2020);
        aMap.put("somedrinks", R.raw.filem2121);
        aMap.put("bills", R.raw.filem2222);
        aMap.put("settimer", R.raw.filem2323);
        aMap.put("seehealthreminder", R.raw.filem2424);
        aMap.put("seemedication", R.raw.filem2525);
        aMap.put("sideeffects", R.raw.filem2626);
        aMap.put("takemedicine", R.raw.filem2727);
        aMap.put("prescribedmedicine", R.raw.filem2424);
        aMap.put("symptoms", R.raw.filem2929);
        aMap.put("homereminder", R.raw.filem3030);
        aMap.put("homereceipts", R.raw.filem3131);
        aMap.put("vendordirectory", R.raw.filem3232);
       aMap.put("wine", R.raw.filem3636);

        aMap.put("drink", R.raw.file3737);
        aMap.put("devmp3", R.raw.msrrydave);

        aMap.put("ambiencebright", R.raw.ambiencebright);
        aMap.put("ambiencedark", R.raw.ambiencedark);
        aMap.put("ambienceextremlybright", R.raw.ambienceextremlybright);
        aMap.put("ambienceprrretydim", R.raw.ambienceprrretydim);

        aMap.put("coldbitcold", R.raw.coldcoldnew);
        aMap.put("coldfreezing", R.raw.coldfreezingnew);
        aMap.put("coldhot1", R.raw.coldhotnew);
        aMap.put("coldnicewarmday", R.raw.coldwarmdaynew);

        aMap.put("feelsweaty", R.raw.feelsweatynew);
        aMap.put("bithumid", R.raw.bithumidnew);
        aMap.put("verydry", R.raw.notsohumidnew);


        aMap.put("angry", R.raw.angrynew11);
        aMap.put("surprise", R.raw.surprisednew11);
        aMap.put("neutral", R.raw.neutralnew11);
        aMap.put("happy", R.raw.happynew11);

        aMap.put("bg0", R.raw.iamnotsureifollowyou1);
        aMap.put("bg1", R.raw.icannotunderstandyou1);
        aMap.put("bg2", R.raw.sorrycanyourepeatthat1);
        aMap.put("bg3", R.raw.myhearingisabitweak);
        aMap.put("bg4", R.raw.pardonme1);
        //aMap.put("bg5", R.raw.i);
        aMap.put("bg5", R.raw.sorrylostuthrere1);
        aMap.put("bg6", R.raw.iamunabletoundersatnd1);



























        Map<String,Integer> treeMap = new TreeMap<>(
                new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return compareInts(s2.length(), s1.length());
                    }
                }
        );

        treeMap.putAll(aMap);



        PROP_VOICE_HASH = Collections.unmodifiableMap(aMap);

    }


    public static int compareInts(int x, int y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

}
