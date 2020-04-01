package TextToSpeech;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import properties.VoiceProperties;


/**
 * Created by sumeendranath on 22/06/16.
 */
public class TextToSpeechFuncs {

    TextToSpeech t1;
    String _speechtotalk = "";

    Context _maincontext;


    boolean _ongoingspeech  = false;

    public TextToSpeechFuncs(Context context) {


        _maincontext = context;

        t1=new TextToSpeech(_maincontext, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    Set<String> a=new HashSet<>();
                    a.add("female");//here you
                    Voice v=new Voice("en-us-x-sfg#female_2-local",new Locale("en","US"),400,200,true,a);
                    t1.setVoice(v);
                    t1.setLanguage(Locale.UK);


                    Iterator prerec_voicelist = VoiceProperties.PROP_VOICE_HASH.entrySet().iterator();
                    while (prerec_voicelist.hasNext()) {
                        Map.Entry<String,Integer> pair = (Map.Entry)prerec_voicelist.next();

                        t1.addSpeech(pair.getKey(),_maincontext.getPackageName(),pair.getValue());

                    }

                    onReady();

                }
            }
        });



        isTTSSpeaking();

    }


    protected void onComplete()
    {

    }

    public void speak(String texttospeak)
    {
        _speechtotalk = texttospeak;

        onStart();

        List<String> listofspeechtext = processTextToSpeech(_speechtotalk);

        for(int i=0;i<listofspeechtext.size();i++)
        {
            _ongoingspeech = true;
            t1.speak(listofspeechtext.get(i), TextToSpeech.QUEUE_ADD, null);
        }

    }





    public void pauseSpeech()
    {
        if(t1 !=null){
            t1.stop();
        }

    }

    public void shutdown()
    {
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }

    }

    List<String> processTextToSpeech(String completetext)
    {
        List<String> retList = new ArrayList<String>();

        String sep = "|";


        Iterator prerec_voicelist = VoiceProperties.PROP_VOICE_HASH.entrySet().iterator();
        while (prerec_voicelist.hasNext()) {
            Map.Entry<String,Integer> pair = (Map.Entry)prerec_voicelist.next();

            completetext = completetext.replaceAll("\\b" + pair.getKey() + "\\b",sep + pair.getKey().replace(" ", "ABPPA**AA") + sep);

        }


        completetext = completetext.replace("ABPPA**AA"," ");

        retList = splitToList(completetext,sep);



        return retList;
    }






    public static List<String> splitToList(String original, String separator) {
        List<String> nodes = new ArrayList<String>();
        // Parse nodes into vector
        int index = original.indexOf(separator);
        while (index >= 0) {
            nodes.add(original.substring(0, index));
            original = original.substring(index + separator.length());
            index = original.indexOf(separator);
        }
        // Get the last node
        nodes.add(original);

        return nodes;
    }



    protected void inprogress()
    {

    }


    public boolean getTTSspeakingOrNot()
    {
        return _ongoingspeech;
    }


    protected void isTTSSpeaking(){

        final Handler h =new Handler();

        Runnable r = new Runnable() {

            public void run() {

                if (!t1.isSpeaking() && _ongoingspeech) {
                    if(_ongoingspeech) {
                        onComplete();
                        _ongoingspeech = false;

                    }
                }
                else
                {
                   inprogress();
                }
                h.postDelayed(this, 500);
            }
        };

        h.postDelayed(r, 500);
    }


    protected void onStart()
    {

    }


    protected void onReady()
    {

    }


}
