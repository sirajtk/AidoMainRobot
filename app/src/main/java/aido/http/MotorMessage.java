package aido.http;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.whitesun.aidoface.AidoFace;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.util.Properties;

import aido.properties.StorageProperties;

/**
 * Created by sumeendranath on 09/09/16.
 */
public class MotorMessage {

    private Socket socket;

    private  String SERVER_IP = "";


    Context _maincontext;

    boolean running = false;
    boolean notrunning = true;
    final int[] count1 = {1};


    private String filename = "SampleFile.txt";
    private String filepath = "MyFileStorage";
    File myExternalFile;
    String myData = "";
    final int[] count = {0};

    public MotorMessage(Context context,String ip) {

        _maincontext = context;
        SERVER_IP = ip + "/motor.php";


    }


    public void run(String pan,String tilt,String speed)

    {
//        if(ControllerProperties.MOTOR_CONTROLLER_ANDROID)
//        {
//            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(_maincontext);
//
//            int defaultprevpan = ControllerProperties.HOME_PAN -2;
//            int defaultprevtilt = ControllerProperties.HOME_TILT -2;
//
//            String prevpan = settings.getString(ControllerProperties.PREVPAN_SHAREDPREF, "" + defaultprevpan);
//            String prevtilt = settings.getString(ControllerProperties.PREVTILT_SHAREDPREF, "" + defaultprevtilt);
//
//            Log.i(ControllerProperties.LOGTAG,String.format("Fired : prevpan=%s, pan=%s, prevtilt=%s, tilt=%s",prevpan, pan, prevtilt, tilt));
//
//            MotorExecute ME = new MotorExecute(prevpan,prevtilt, pan, tilt,  speed);
//            ME.run();
//
//            SharedPreferences.Editor editor = settings.edit();
//            editor.putString(ControllerProperties.PREVPAN_SHAREDPREF,ME.getPanForStorage());
//            editor.putString(ControllerProperties.PREVTILT_SHAREDPREF,ME.getTiltForStorage());
//            editor.commit();
//
//            Log.i(ControllerProperties.LOGTAG,String.format("stored pan=%s, tilt=%s",ME.getPanForStorage(), ME.getTiltForStorage()));
//
//        }
//        else
//        {
            runPi(pan, tilt);
        //}
    }


    public void runLocal(String pan,String tilt, String speed)
    {

    }


    public void runPi(final String pan, final String tilt)
    {
        Log.i("inside runpi", "runPi: "+pan+" "+tilt);
        int pan1 = Integer.parseInt(pan);
        int tilt1 = Integer.parseInt(tilt);
        running = true;
        final asynchttp http = new asynchttp(_maincontext);
        http.setOnDownloadCompletedListener(new asynchttp.OnDownloadCompletedListener() {
            @Override
            public void onDownloadIsCompleted(String downloadstring) {

                Log.i("MOTOR"," " + SERVER_IP  + " exec:" + http.getDownloadedString());
                running = false;
            }
        });
        http.download(SERVER_IP + "?pan=" + pan1 + "&tilt=" + tilt1 );

//        if(pan1 == 130 && tilt1 == 130) {
//            isnotRunning();
//            Log.i("else", "runPi: do nothing");
//
//        }else{
//            running = true;
//            final asynchttp http = new asynchttp(_maincontext);
//            http.setOnDownloadCompletedListener(new asynchttp.OnDownloadCompletedListener() {
//                @SuppressLint("StaticFieldLeak")
//                @Override
//                public void onDownloadIsCompleted(String downloadstring) {

                    // appendLog(pan,tilt);
//                    String spli = http.getDownloadedString();
//                    String[] a = spli.split(" ", 5);
//                    // (String b : a)
//                    Log.i("SPlit", "onDownloadIsCompleted: " + a[0]);
//                    if (a[0].equals("error")) {
//
////                        Snackbar snackbar = Snackbar
////                                .make(findViewById(), "www.journaldev.com", Snackbar.LENGTH_LONG);
////                        snackbar.show();
//
//                        new AsyncTask<Integer, Void, Void>() {
//                            @Override
//                            protected Void doInBackground(Integer... params) {
//                                try {
//                                    String s = "Robot needs to do service";
//                                    String s1 = "servo_reset";
//                                    //AidoFace.setSnackBar(s);
//                                    //  Toast.makeText(_maincontext, "Robot needs to do service", Toast.LENGTH_SHORT).show();
//                                    executeRemoteCommand(s1);
//                                    System.out.println(executeRemoteCommand(s1));
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                                return null;
//                            }
//
//                        }.execute(1);
//
//                    }
//                    if (a[0].equals("Driver")) {
//
//                        //AidoFace.setSnackBar(v1,"Caution! Robot break down contact support immediatel");
//                        if (count[0] == 0) {
//                            String s = "Caution! Robot break down. contact support immediately" +
//                                    "Error Found:Driver Module";
//                            // AidoFace.setSnackBar(s);
//                            AlertDialog alertDialog = new AlertDialog.Builder(_maincontext).create();
//                            alertDialog.setTitle("Alert");
//                            alertDialog.setMessage(s);
//                            alertDialog.setCancelable(false);
//                            alertDialog.setCanceledOnTouchOutside(false);
//                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//
//                                            count[0] = 0;
//                                        }
//                                    });
//                            alertDialog.show();
//                            count[0] = 1;
//                        }
//                    }
//                    if (a[0].equals("python")) {
//                        count[0] = 0;
//                    }

//                    Log.i("MOTOR", " " + SERVER_IP + " exec:" + http.getDownloadedString());
//                    Log.i("pan&Tilt", "onDownloadIsCompleted: " + pan + "" + tilt);
//                    System.out.println(http.getDownloadedString());
//                    running = false;
//                    count1[0] = 0;
//                    Log.i("count", "runPi: "+count1[0]);
//
//                }
//            });
//            http.download(SERVER_IP + "?pan=" + pan + "&tilt=" + tilt);
//
//        }

    }


    public boolean isRunning()
    {
        System.out.println(running);
        return running;
    }
    @SuppressLint("StaticFieldLeak")
    public void isnotRunning()
    {

            new AsyncTask<Integer, Void, Void>() {
                @Override
                protected Void doInBackground(Integer... params) {
                    try {
                        String s = "servo_motoroff";
                        //  Toast.makeText(_maincontext, "Robot needs to do service", Toast.LENGTH_SHORT).show();
                        executeRemoteCommand(s);
                        Log.i("motoroff", "isnotRunning: called");
                        System.out.println(executeRemoteCommand(s));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

            }.execute(1);
        }


    private void appendLog(String pan, String tilt)
    {
        String copy = "pan"+pan+"Tilt"+tilt;

            System.out.println("file write called");
try{
            File file = new File(StorageProperties.getIpFile());
            if(!file.exists()){
                file.createNewFile();
            }

            //Here true is to append the content to file
            FileWriter fw = new FileWriter(file,true);
            //BufferedWriter writer give better performance
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(copy);
            //Closing BufferedWriter Stream
            bw.close();

            System.out.println("Data successfully appended at the end of file");

        }catch(IOException ioe){
        System.out.println("Exception occurred:");
        ioe.printStackTrace();
    }
        }





//        logFile = new File(fileReadWrite.getDirectoryOfFile()"sdcard/log.file");
//        if (!logFile.exists())
//        {
//            try
//            {
//                logFile.createNewFile();
//            }
//            catch (IOException e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        try
//        {
//            //BufferedWriter for performance, true to set append to file flag
//            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
//
//            buf.append(pan).append("...values..").append(tilt);
//            buf.newLine();
//            buf.close();
//        }
//        catch (IOException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

    private static boolean isExternalStorageReadOnly()
    {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    private static boolean isExternalStorageAvailable()
    {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }
    private String executeRemoteCommand(String filename)
            throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession("pi", "192.168.0.128", 22);
        session.setPassword("sks123");

        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);

        session.connect();
        //Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show();
        System.out.println("COnnected");

        // SSH Channel
        ChannelExec channelssh = (ChannelExec)
                session.openChannel("exec");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        channelssh.setOutputStream(baos);
        Log.i("hrl", "connected: ");

        // Execute command
        channelssh.setCommand("python /home/pi/motor/"+filename+".py");
        channelssh.connect();
        //Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show();
        Log.i("hmn", "executeRemoteCommand: "+baos.toString());
        //channelssh.disconnect();

        return baos.toString();
    }


}
