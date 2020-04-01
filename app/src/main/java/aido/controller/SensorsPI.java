package aido.controller;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import aido.http.asynchttp;

/**
 * Created by sumeendranath on 21/02/17.
 */
public class SensorsPI {


    public static String SENSOR_TEMPERATURE = "temperature";
    public static String SENSOR_PRESSURE = "pressure";
    public static String SENSOR_POWERREMAINING = "powerremain";
    public static String SENSOR_AIRQUALITY = "airquality";


    public static String DEFAULT_JSON =
                    "{\n" +
                    "  \""+ SENSOR_TEMPERATURE +"\":\"32\",\n" +
                    "  \"" + SENSOR_PRESSURE + "\":\"20\",\n" +
                    "  \"" + SENSOR_POWERREMAINING + "\":\"10%\",\n" +
                    "  \"" + SENSOR_AIRQUALITY + "\":\"97\"\n" +
                    "}";



    public static boolean RUNNING = false;


    public static HashMap<String,String> SENSORVALUESHASH = new HashMap<>();

    static {
        HashMap<String, String> aMap = new HashMap<String, String>();


        SENSORVALUESHASH.put(SENSOR_TEMPERATURE,"32");
        SENSORVALUESHASH.put(SENSOR_PRESSURE,"20");
        SENSORVALUESHASH.put(SENSOR_POWERREMAINING,"10%");
        SENSORVALUESHASH.put(SENSOR_AIRQUALITY,"97");



        SENSORVALUESHASH = aMap;
    }



    public static void updateSensorValues(Context context, String ip)
    {

        final String php = ip + "sensor.php";



        if(RUNNING)
        {
            return ;
        }


        RUNNING = true;
        final asynchttp http = new asynchttp(context);
        http.setOnDownloadCompletedListener(new asynchttp.OnDownloadCompletedListener() {
            @Override
            public void onDownloadIsCompleted(String downloadstring) {

                Log.i("SENSOR","exec: " + php  + " , got:" + http.getDownloadedString());


                    JSONObject JSON = null;
                    try {
                        JSON = new JSONObject(http.getDownloadedString());
                    } catch (JSONException ex) {
                        // edited, to include @Arthur's comment
                        // e.g. in case JSONArray is valid as well...

                        /// invalid json from http
                        try {
                            JSON = new JSONObject(http.getDownloadedString());
                        } catch (JSONException ex2) {

                            ex2.printStackTrace();
                            Log.i("SENSOR","Invalid json string " );

                        }

                    }


                if(JSON != null)
                {
                    try {

                        SENSORVALUESHASH.put(SENSOR_TEMPERATURE,JSON.getString(SENSOR_TEMPERATURE));
                        Log.i("SENSOR","setting temparature as : " + JSON.getString(SENSOR_TEMPERATURE));
                    }
                    catch (JSONException ex)
                    {

                    }catch (Exception ex)
                    {

                    }

                    try {

                        SENSORVALUESHASH.put(SENSOR_AIRQUALITY,JSON.getString(SENSOR_AIRQUALITY));
                    }
                    catch (JSONException ex)
                    {

                    }catch (Exception ex)
                    {

                    }

                    try {

                        SENSORVALUESHASH.put(SENSOR_POWERREMAINING,JSON.getString(SENSOR_POWERREMAINING));
                    }
                    catch (JSONException ex)
                    {

                    }catch (Exception ex)
                    {

                    }

                    try {

                        SENSORVALUESHASH.put(SENSOR_PRESSURE,JSON.getString(SENSOR_PRESSURE));
                    }
                    catch (JSONException ex)
                    {

                    }catch (Exception ex)
                    {

                    }

                }


                RUNNING = false;

            }
        });
        http.download(php);


    }

}
