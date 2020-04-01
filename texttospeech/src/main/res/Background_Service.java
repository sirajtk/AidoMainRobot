import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by rathore on 19/10/17.
 */

public class Background_Service extends Service {
    Handler handler;
    Thread t;
    //DatabaseHandler databaseHandler;









    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                startJob();
            }
        });
        t.start();

        return START_STICKY;
    }

    public void startJob(){
        android.os.Handler handler1 = new android.os.Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
//

                Toast.makeText(getApplicationContext(),"Aido main Service run",Toast.LENGTH_SHORT).show();




            }
        };
        handler1.post(runnable);

//

        try {
            //Thread.sleep(5000);
            t.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Log.i("ThreadSleep","Sleeping");
        startJob();
    }

//

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getApplicationContext(),Service.class);
        startService(intent);
    }
}


}
