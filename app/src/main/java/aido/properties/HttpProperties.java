package aido.properties;

import android.net.Uri;

import com.whitesuntech.aidohomerobot.R;

/**
 * Created by sumeendranath on 31/05/16.
 */
public class HttpProperties {

    public static String PROP_IP_ADDRESS = "http://192.168.0.2/aido/";

    public static long PROP_POLLFREQ_DOWNLOAD = 100;


    public static String PROP_URL_DATAFROMSERVER       = PROP_IP_ADDRESS + "getmessagefromlinux.php";
    public static String PROP_URL_NOTIFYTASKCOMPLETE   = PROP_IP_ADDRESS + "postandroidtaskcompletion.php";
    public static String PROP_URL_UPLOADTOLINUX        = PROP_IP_ADDRESS + "uploaddatatolinux.php";

    public static String PROP_HTTPDOWNLOAD_INTENTID = "com.whitesun.httpdownload";
    public static String HTTPDOWNLOAD_MESSSAGEFIELD = "message";

    public static String PROP_STT_BROADCAST_INTENTID = "com.whitesuntech.stt";
    public static String PROP_STT_RECEIVER_INTENTID = "com.whitesuntech.speechtotext";
    public static String PROP_TTS_RECEIVER_INTENTID = "com.whitesuntech.texttospeech";
    public static String PROP_SPEECHTOTEXT_MESSSAGEFIELD = "message";



    public static String PROP_POST_MSG_ID = "ID";
    public static String PROP_POST_MSG_STATUS = "STATUS";
    public static String PROP_POST_MSG_TEXT = "TEXT";


    public static String PROP_GETVAR_MSGID = "MSG_ID";
    public static String PROP_GETVAR_MSGSTATUS = "MSG_STATUS";
    public static String PROP_GETVAR_MSGVALUE = "VALUE";



    public static String VAL_MSG_STATUS_PASSED = "PASSED";
    public static String VAL_MSG_STATUS_FAILED = "FAILED";


    public static String PROP_FOOD2FORK_SEARCHAPI = "http://food2fork.com/api/search";
    public static String PROP_FOOD2FORK_RECIPEAPI = "http://food2fork.com/api/get";
    public static String PROP_FOOD2FORK_APIKEY = "8c56c6d0acebc8bbcc81fe6ab62851b5";



    public static String OUTPAN_KEY = "b797fd3588639614c4fa2423ecb31870";


    public static final String HTTP_PLAY_MEDIA_BODY = "MEDIA_BODY";
    public static final String HTTP_PLAY_MEDIA_HEAD = "MEDIA_HEAD";

    public static final String HTTP_SHOPPING = "SHOPPING";
    public static final String HTTP_FACEDETECT = "DETECT";
    public static final String HTTP_FACERECOG  = "FACERECOG";


    public static final String HTTP_SPEECH_TO_TEXT_TAG = "SPEECH_TO_TEXT";

    public static final String HTTP_TEXT_TO_SPEECH_TAG = "TEXT_TO_SPEECH";

    public static final String HTTP_EMOTION_DETECT_TAG = "EMOTION_DETECT";


    public static String getFoodToForkSearchApi(String APIKEY, String query)
    {

        return  Uri.parse(PROP_FOOD2FORK_SEARCHAPI)
                .buildUpon()
                .appendQueryParameter("key", APIKEY)
                .appendQueryParameter("sort","r" )
                .appendQueryParameter("page", "1")
                .appendQueryParameter("q", query)
                .build().toString();

    }

    public static String getFoodToForkRecipeApi(String APIKEY, String recipeId)
    {

        return  Uri.parse(PROP_FOOD2FORK_RECIPEAPI)
                .buildUpon()
                .appendQueryParameter("key", APIKEY)
                .appendQueryParameter("rId",recipeId)
                .build().toString();

    }




}
