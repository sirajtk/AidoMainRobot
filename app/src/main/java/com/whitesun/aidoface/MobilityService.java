package com.whitesun.aidoface;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

public class MobilityService extends Service {
    public MobilityService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... params) {
                try {
                    executeRemoteCommand("sarath", "sks123", "192.168.0.128", 22);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1);
        return super.onStartCommand(intent, flags, startId);
    }
    public static String executeRemoteCommand(String username, String password, String hostname, int port)
            throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, hostname, port);
        session.setPassword(password);

        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);

        session.connect();

        // SSH Channel
        ChannelExec channelssh = (ChannelExec)
                session.openChannel("exec");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        channelssh.setOutputStream(baos);

        // Execute command
        channelssh.setCommand("echo > /home/sarath/test2.txt hello make changes");
        channelssh.connect();
        // Log.i(TAG, "executeRemoteCommand: ");
        channelssh.disconnect();

        return baos.toString();
    }
}
