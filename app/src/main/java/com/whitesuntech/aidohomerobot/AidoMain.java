package com.whitesuntech.aidohomerobot;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


import com.whitesun.aidoface.memoryDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import aido.common.CommonlyUsed;
import aido.file.fileReadWrite;
import aido.properties.ConfigProperties;
import aido.properties.PermittedApps;
import aido.properties.StorageProperties;

public class AidoMain extends AppCompatActivity {

    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP));
    private Button hiddenExitButton;


    Iterator entries;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //String message = intent.getStringExtra(HttpProperties.HTTPDOWNLOAD_MESSSAGEFIELD);



            //Toast.makeText(getApplicationContext(), "received: " + message, Toast.LENGTH_SHORT).show();
        }
    };


    private PackageReceiver _packagereceiver = new PackageReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            if(_installpackages)
            {
                if(!installNextPackage())
                {
                    _installpackages = false;
                }
            }

        }
    };



    boolean _installpackages = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aido_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if ( !fileReadWrite.fileExists(StorageProperties.getIpFile())) {
            fileReadWrite.writeIntoFile(ConfigProperties.DEFAULT_JSON, StorageProperties.getIpFile());
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Install Apps", Snackbar.LENGTH_LONG)
                        .setAction("CLICK TO INSTALL!", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                _installpackages = true;

                               // startActivity(new Intent(AidoMain.this, HoundVoiceSearchExampleActivity.class));

                                //Toast.makeText(getApplicationContext(),"Implementation is pending",Toast.LENGTH_SHORT).show();

                                installPackages();

                            }
                        }).show();
            }
        });


        /// launcher in kiosk mode
        // every time someone enters the kiosk mode, set the flag true
        //PrefUtils.setKioskModeActive(true, AidoMain.this);


        //Intent serviceIntent = new Intent(this, KioskService.class);
        //startService(serviceIntent);


        /////// start checking server instructions

       // HttpDownloadPolling httppoll = new HttpDownloadPolling();
       // httppoll.SetAlarm(AidoMain.this, HttpProperties.PROP_POLLFREQ_DOWNLOAD);


      /*  HttpDownloadHandlerPolling handlerpoll = new HttpDownloadHandlerPolling(AidoMain.this,HttpProperties.PROP_POLLFREQ_DOWNLOAD);
        handlerpoll.startRepeatingTask();


        CommonlyUsed.unmuteAudioOutput(AidoMain.this);*/


      //  Intent aidoface_intent = new Intent(this, AidoFace.class);
      //  aidoface_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      //  this.startActivity(aidoface_intent);




      //  IntentFilter filter = new IntentFilter();
       // filter.addAction(HttpProperties.PROP_HTTPDOWNLOAD_INTENTID);
       // registerReceiver(receiver, filter);

          IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_INSTALL);
        filter.addDataScheme("package");

        // filter.addAction(HttpProperties.PROP_HTTPDOWNLOAD_INTENTID);
         registerReceiver(_packagereceiver, filter);




    }


    void installPackages()
    {

        entries = PermittedApps.PROP_PERMITTEDAPPS_HASH.entrySet().iterator();

        installNextPackage();


    }

    boolean installNextPackage()
    {
        if (entries.hasNext())
        {
            Map.Entry<String,String> permittedpackage = (Map.Entry<String, String>) entries.next();


            //System.out.println(entry.getKey() + "/" + entry.getValue());
            if(!permittedpackage.getValue().equalsIgnoreCase("NA"))
            {
                Log.i("AIDOMAIN","Checking : " + permittedpackage.getKey() + " : " + permittedpackage.getValue());

                if(!CommonlyUsed.checkIfPackageIsInstalled(this,permittedpackage.getKey())) {
                    Log.i("AIDOMAIN","INSTALL : " + permittedpackage.getKey() + " : " + permittedpackage.getValue());

                    Intent goToMarket = new Intent(Intent.ACTION_VIEW)
                            .setData(Uri.parse("market://details?id=" + permittedpackage.getKey()));
                    startActivity(goToMarket);
                }
                else
                {
                    installNextPackage();
                }

            }
            else
            {
                installNextPackage();
            }
        }
        else
        {
            return false;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_aido_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    void redoThisActivity()
    {
        this.recreate();
        Intent i = new Intent(this, AidoMain.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.setAction(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(i);
    }




    @Override
    public void onBackPressed() {
        // get alert_exit.xml view
        //performPasswordCheck();

        super.onBackPressed();
    }



    @Override
    protected void onResume() {


       // PrefUtils.setKioskModeActive(true, getApplicationContext());
       // Toast.makeText(getApplicationContext(), "Entering Secure Environment!", Toast.LENGTH_SHORT).show();
        super.onResume();

    }


    boolean isMyLauncherDefault() {
        final IntentFilter filter = new IntentFilter(Intent.ACTION_MAIN);
        filter.addCategory(Intent.CATEGORY_HOME);

        List<IntentFilter> filters = new ArrayList<IntentFilter>();
        filters.add(filter);

        final String myPackageName = getPackageName();
        List<ComponentName> activities = new ArrayList<ComponentName>();
        final PackageManager packageManager = getPackageManager();

        // You can use name of your package here as third argument
        packageManager.getPreferredActivities(filters, activities, null);

        for (ComponentName activity : activities) {
            if (myPackageName.equals(activity.getPackageName())) {
                return true;
            }
        }
        return false;
    }


  /*  @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (blockedKeys.contains(event.getKeyCode())) {
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }*/

  /*  @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(!hasFocus) {
            // Close every kind of system dialog
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }*/

}
