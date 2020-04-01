package aido.kiosk;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


import com.whitesuntech.aidobox.AidoMain;

import java.util.List;

import aido.properties.PermittedApps;


public class KioskService extends Service {

    private static final long INTERVAL = 1;//TimeUnit.SECONDS.toMillis(1); // periodic interval to check in seconds -> 2 seconds
    private static final String TAG = KioskService.class.getSimpleName();

    private Thread t = null;
    private Context ctx = null;
    private boolean running = false;

    @Override
    public void onDestroy() {
        Log.i(TAG, "Stopping service 'KioskService'");
        running =false;
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Starting service 'KioskService'");
        running = true;
        ctx = this;

        // start a thread that periodically checks if your app is in the foreground
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    handleKioskMode();
                    try {
                        Thread.sleep(INTERVAL);
                    } catch (InterruptedException e) {
                        Log.i(TAG, "Thread interrupted: 'KioskService'");
                    }
                }while(running);
                stopSelf();
            }
        });

        t.start();
        return Service.START_NOT_STICKY;
    }

    private void handleKioskMode() {
        // is Kiosk Mode active?

//        Toast.makeText(getApplicationContext(),"Checking",Toast.LENGTH_SHORT).show();
        if(PrefUtils.isKioskModeActive(ctx)) {
            // is App in background?
            if(isInBackground()) {
                restoreApp(); // restore!
            }
        }
    }

    private boolean isInBackground() {
       ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        ComponentName componentInfo = taskInfo.get(0).baseActivity;


        if(PermittedApps.PROP_PERMITTEDAPPS_HASH.containsKey(componentInfo.getPackageName())) //  componentInfo.getPackageName() )
        {
            return false;
        }

        return (!ctx.getApplicationContext().getPackageName().equals(componentInfo.getPackageName()));
       // return true;
    }

    private void restoreApp() {
        // Restart activity
        Intent i = new Intent(ctx, AidoMain.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(i);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}