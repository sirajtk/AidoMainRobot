package aido.TextToSpeech;

import android.content.Context;
import android.content.Intent;

import aido.properties.AlertBehaviorProperties;
import aido.properties.VoiceProperties;

/**
 * Created by sumeendranath on 27/07/16.
 */
public class BroadcastTTS {
    Context _maincontext;


    public BroadcastTTS(Context context) {
        _maincontext = context;
    }

    public  void speak(String message)
    {
        Intent intent = new Intent();
        intent.setAction(VoiceProperties.PROP_TTS_INTENTID);
        message = makeSubstitutions(message);
        intent.putExtra(VoiceProperties.TTS_MESSSAGEFIELD, message);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        _maincontext.sendBroadcast(intent);


    }


    String replacestr(String msg, String rep, String with)
    {
        String defaultmsg = " I dont know ";

        try {

            if(rep == null)
            {
                return msg;
            }
            if(with == null)
            {
                msg = msg.replace(rep,
                        defaultmsg);
                return  msg;
            }

            msg = msg.replace(rep, with);



        }
        catch (Exception ex)
        {

        }

        return msg;
    }


    String makeSubstitutions(String message)
    {


        message = replacestr(message,AlertBehaviorProperties.ALERTBEHAVIORVARIABLE.get(AlertBehaviorProperties.FaceRecognition),
                    AlertBehaviorProperties._lastFaceRecognition);

        message = replacestr(message,AlertBehaviorProperties.ALERTBEHAVIORVARIABLE.get(AlertBehaviorProperties.FaceDetection),
                AlertBehaviorProperties._lastFaceDetection);
        message = replacestr(message,AlertBehaviorProperties.ALERTBEHAVIORVARIABLE.get(AlertBehaviorProperties.HELLO),
                AlertBehaviorProperties._lastEmotionRecognition);
//EMOtionrecognition
        message = replacestr(message,AlertBehaviorProperties.ALERTBEHAVIORVARIABLE.get(AlertBehaviorProperties.TemperatureSensor),
                AlertBehaviorProperties._lastTemperatureSensor);

        message = replacestr(message,AlertBehaviorProperties.ALERTBEHAVIORVARIABLE.get(AlertBehaviorProperties.PressureSensor),
                AlertBehaviorProperties._lastPressure);

        message = replacestr(message,AlertBehaviorProperties.ALERTBEHAVIORVARIABLE.get(AlertBehaviorProperties.Airquality),
                AlertBehaviorProperties._lastAirquality);

        message = replacestr(message,AlertBehaviorProperties.ALERTBEHAVIORVARIABLE.get(AlertBehaviorProperties.PowerRemaining),
                AlertBehaviorProperties._lastPowerRemaining);

        message = replacestr(message,AlertBehaviorProperties.ALERTBEHAVIORVARIABLE.get(AlertBehaviorProperties.ShowingNotifications),
                AlertBehaviorProperties._lastShowingNotifications);
        return message;
    }

    /*
        String makeSubstitutions(String message)
    {


        message = message.replace(AlertBehaviorProperties.ALERTBEHAVIORVARIABLE.get(AlertBehaviorProperties.FaceRecognition),
                    AlertBehaviorProperties._lastFaceRecognition);

        message = message.replace(AlertBehaviorProperties.ALERTBEHAVIORVARIABLE.get(AlertBehaviorProperties.FaceDetection),
                AlertBehaviorProperties._lastFaceDetection);
        message = message.replace(AlertBehaviorProperties.ALERTBEHAVIORVARIABLE.get(AlertBehaviorProperties.EmotionRecognition),
                AlertBehaviorProperties._lastEmotionRecognition);

        message = message.replace(AlertBehaviorProperties.ALERTBEHAVIORVARIABLE.get(AlertBehaviorProperties.TemperatureSensor),
                AlertBehaviorProperties._lastTemperatureSensor);

        message = message.replace(AlertBehaviorProperties.ALERTBEHAVIORVARIABLE.get(AlertBehaviorProperties.PressureSensor),
                AlertBehaviorProperties._lastPressure);

        message = message.replace(AlertBehaviorProperties.ALERTBEHAVIORVARIABLE.get(AlertBehaviorProperties.Airquality),
                AlertBehaviorProperties._lastAirquality);

        message = message.replace(AlertBehaviorProperties.ALERTBEHAVIORVARIABLE.get(AlertBehaviorProperties.PowerRemaining),
                AlertBehaviorProperties._lastPowerRemaining);

        message = message.replace(AlertBehaviorProperties.ALERTBEHAVIORVARIABLE.get(AlertBehaviorProperties.ShowingNotifications),
                AlertBehaviorProperties._lastShowingNotifications);
        return message;
    }

     */
}
