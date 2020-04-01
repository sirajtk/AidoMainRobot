package aido.properties;


import com.whitesuntech.aidohomerobot.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by sumeendranath on 08/06/16.
 */
public class VoiceProperties {

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


        Map<String,Integer> treeMap = new TreeMap<>(
                new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return Integer.compare(s2.length(), s1.length());
                    }
                }
        );

        treeMap.putAll(aMap);



        PROP_VOICE_HASH = Collections.unmodifiableMap(aMap);

    }

}
