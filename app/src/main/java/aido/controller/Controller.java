package aido.controller;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import aido.common.CommonlyUsed;
import aido.http.asynchttp;
import aido.motor.SystemCommand;
import aido.properties.ControllerProperties;

/**
 * Created by sumeendranath on 21/02/17.
 */
public class Controller {



    public  static void reset()
    {
        SystemCommand.exec(ControllerProperties.CONTROLLER_RESET);
    }

    static boolean running = false;
    public static void run(Context context,String piip, Integer controllerfield)
    {

        final String pi_piip = piip + "/cmd.php";

        if(!ControllerProperties.CONTROLLER_ENABLED)
        {
            return;
        }

        if(ControllerProperties.CONTROLLERFUNCTIONS.containsKey(controllerfield))
        {
            String command = ControllerProperties.CONTROLLERFUNCTIONS.get(controllerfield);

            if(ControllerProperties.EXECUTION_IN_ODROID) {
                SystemCommand.exec(command);
            }
            else {
                /// linux execution
                running = true;

                final asynchttp http = new asynchttp(context);
                http.setOnDownloadCompletedListener(new asynchttp.OnDownloadCompletedListener() {
                    @Override
                    public void onDownloadIsCompleted(String downloadstring) {

                        Log.i("CONTROLLER"," COMPLETED " + pi_piip  + " exec:" + http.getDownloadedString());
                        running = false;
                    }
                });

                String uri = Uri.parse(pi_piip)
                        .buildUpon()
                        .appendQueryParameter("exe", command)
                        .build().toString();

                Log.i("CONTROLLER"," PREPARING " + uri);

                http.download(uri);

            }



        }

    }








}
