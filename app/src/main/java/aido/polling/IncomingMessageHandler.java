package aido.polling;

import android.content.Context;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import aido.common.CommonlyUsed;
import aido.http.asynchttp;
import aido.properties.HttpProperties;

/**
 * Created by sumeendranath on 24/06/16.
 */
public class IncomingMessageHandler {


    public static String SEP_MSG_FIRSTLEVEL = "#";
    public static String SEP_MSG_SECONDLEVEL = ",";

    public static int FIELD_MSG_TYPE = 0;
    public static int FIELD_MSG_VALUE = 1;
    public static int FIELD_MSG_ACKNEEDED = 2;
    public static int FIELD_MSG_ID = 3;

    public static String VAL_ACKNEEDED_YES = "1";
    public static String VAL_ACKNEEDED_NO = "0";





    String _msg = "";
    List<String> firstleveltokens = new ArrayList<String>();

    public IncomingMessageHandler(String msg) {
        _msg = msg;
        processMsgFirstLevel();
    }

    public void processMsgFirstLevel()
    {
        firstleveltokens = CommonlyUsed.splitToList(_msg, SEP_MSG_FIRSTLEVEL);
    }

    public String getId()
    {
        if(firstleveltokens.size() > FIELD_MSG_ID) {
            return firstleveltokens.get(FIELD_MSG_ID);
        }
        return "";
    }
    public String getTask()
    {
        if(firstleveltokens.size() > FIELD_MSG_TYPE) {

            return firstleveltokens.get(FIELD_MSG_TYPE);
        }
        return "";

    }
    public String getTextValue()
    {
        if(firstleveltokens.size() > FIELD_MSG_VALUE) {

            return firstleveltokens.get(FIELD_MSG_VALUE);
        }
        return "";
    }

    public boolean getAckRequired()
    {
        if(firstleveltokens.size() <= FIELD_MSG_VALUE) {
            return false;
        }
        return firstleveltokens.get(FIELD_MSG_ACKNEEDED).equalsIgnoreCase("yes");
    }

    public List<String> getSecondLevel(int msgfield)
    {
        return CommonlyUsed.splitToList(firstleveltokens.get(msgfield), SEP_MSG_SECONDLEVEL);

    }



    public void setTaskAsComplete(Context context) {
        if (getAckRequired()) {
            asynchttp httpdownload = new asynchttp(context);
            Hashtable<String, String> postvalues = new Hashtable<String, String>();
            postvalues.put(HttpProperties.PROP_POST_MSG_ID, getId());
            postvalues.put(HttpProperties.PROP_POST_MSG_STATUS, HttpProperties.VAL_MSG_STATUS_PASSED);
            httpdownload.upload(HttpProperties.PROP_URL_NOTIFYTASKCOMPLETE, postvalues);
            httpdownload.setOnUploadCompletedListener(new asynchttp.OnUploadCompletedListener() {
                @Override
                public void onUploadIsCompleted() {

                }
            });
        }
    }

    public void postValueToServer(Context context, String value) {
        /*if (getAckRequired()) {
            asynchttp httpdownload = new asynchttp(context);
            Hashtable<String, String> postvalues = new Hashtable<String, String>();
            postvalues.put(HttpProperties.PROP_POST_MSG_ID, getId());
            postvalues.put(HttpProperties.PROP_POST_MSG_STATUS, HttpProperties.VAL_MSG_STATUS_PASSED);
            postvalues.put(HttpProperties.PROP_POST_MSG_TEXT, value);
            httpdownload.upload(HttpProperties.PROP_URL_UPLOADTOLINUX, postvalues);
            httpdownload.setOnUploadCompletedListener(new asynchttp.OnUploadCompletedListener() {
                @Override
                public void onUploadIsCompleted() {

                }
            });
        }*/
    }
}
