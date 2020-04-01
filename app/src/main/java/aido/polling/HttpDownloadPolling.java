package aido.polling;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.Toast;

import aido.http.asynchttp;
import aido.properties.HttpProperties;
import aido.properties.RequestCodeProperties;

/**
 * Created by sumeendranath on 31/05/16.
 */
public class HttpDownloadPolling extends BroadcastReceiver {


    static String prevmsg = "0";
    @Override
    public void onReceive(final Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();


        Intent intent2 = new Intent();
        intent2.setAction(HttpProperties.PROP_HTTPDOWNLOAD_INTENTID);
        intent2.putExtra(HttpProperties.HTTPDOWNLOAD_MESSSAGEFIELD, "ihsihsi");
        context.sendBroadcast(intent2);


        /*
        asynchttp httpdownload = new asynchttp(context);
        httpdownload.download(HttpProperties.PROP_URL_DATAFROMSERVER);
        httpdownload.setOnDownloadCompletedListener(new asynchttp.OnDownloadCompletedListener() {
            @Override
            public void onDownloadIsCompleted(String downloadstring) {

                //if(downloadstring.length() != 1)
                {
                    //if(!prevmsg.equals(downloadstring))
                    {

                       // Toast.makeText(context, "Inst ! " + downloadstring + " :" + prevmsg, Toast.LENGTH_SHORT).show();
                        prevmsg = downloadstring;


                        Intent intent = new Intent();
                        intent.setAction(HttpProperties.PROP_HTTPDOWNLOAD_INTENTID);
                        intent.putExtra(HttpProperties.HTTPDOWNLOAD_MESSSAGEFIELD, downloadstring);
                        context.sendBroadcast(intent);
                    }
                }
            }
        });*/


        wl.release();
    }

    public void SetAlarm(Context context, long time)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, HttpDownloadPolling.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);

        cancelAlarmIfExists(context, RequestCodeProperties.REQCODE_HTTP_POLLING,i);


        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), time, pi); // Millisec * Second * Minute
    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, HttpDownloadPolling.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, RequestCodeProperties.REQCODE_HTTP_POLLING, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void cancelAlarmIfExists(Context mContext,int requestCode,Intent intent){
        try{
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requestCode, intent,0);
            AlarmManager am=(AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
            am.cancel(pendingIntent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
