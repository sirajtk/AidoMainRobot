package aido.motor;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import aido.properties.ControllerProperties;

/**
 * Created by sumeendranath on 24/01/17.
 */
public class SystemCommand {



    public static void exec(String cmd)
    {
        Log.i(ControllerProperties.LOGTAG,"Exec : " + cmd);

        if(ControllerProperties.DEBUG)
        {
            return;
        }

        try
        {
            executeWithExeception(cmd);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

   public static String executeWithExeception(String cmd) throws Exception {

        StringBuffer output = new StringBuffer();
        Process p;


        try{
           /* Process su = Runtime.getRuntime().exec("su");
            DataOutputStream
                    outputStream = new DataOutputStream(su.getOutputStream());

            outputStream.writeBytes(cmd);
            outputStream.flush();

            outputStream.writeBytes("exit\n");
            outputStream.flush();


            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(su.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
                su.waitFor();
            }


            su.waitFor();*/



            p = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
                p.waitFor();
            }


        }catch(IOException e){
            throw new Exception(e);
        }catch(InterruptedException e){
            throw new Exception(e);
        }


        String response = output.toString();
        return response;

    }

}
