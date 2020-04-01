package aido.polling;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import aido.http.asynchttp;
import aido.properties.HttpProperties;

/**
 * Created by sumeendranath on 21/06/16.
 */
public class HttpDownloadHandlerPolling {

    private Handler mHandler;
    Runnable mStatusChecker;

    long _interval = 10000;

    static String prevmsg = "0";


    Context _maincontext;
    public HttpDownloadHandlerPolling(Context context, long interval) {


        _maincontext = context;
        _interval = interval;


        mHandler = new Handler();

        mStatusChecker  = new Runnable() {
            @Override
            public void run() {
                try {

                    asynchttp httpdownload = new asynchttp(_maincontext);
                    httpdownload.download(HttpProperties.PROP_URL_DATAFROMSERVER);
                    httpdownload.setOnDownloadCompletedListener(new asynchttp.OnDownloadCompletedListener() {
                        @Override
                        public void onDownloadIsCompleted(String downloadstring) {

                            if(downloadstring.length() != 1)
                            {
                                if(!prevmsg.equals(downloadstring))
                                {

                                    // Toast.makeText(context, "Inst ! " + downloadstring + " :" + prevmsg, Toast.LENGTH_SHORT).show();
                                    prevmsg = downloadstring;


                                    Intent intent = new Intent();
                                    intent.setAction(HttpProperties.PROP_HTTPDOWNLOAD_INTENTID);
                                    intent.putExtra(HttpProperties.HTTPDOWNLOAD_MESSSAGEFIELD, downloadstring);
                                    _maincontext.sendBroadcast(intent);


                                }
                            }

                            mHandler.postDelayed(mStatusChecker, _interval);

                        }
                    });


                } finally {
                    // 100% guarantee that this always happens, even if
                    // your update method throws an exception
                    //mHandler.postDelayed(mStatusChecker, _interval);
                }
            }
        };
    }


    public void startRepeatingTask() {
        mStatusChecker.run();
    }

    public void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }
}
