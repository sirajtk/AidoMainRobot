package org.ollide.rosandroid;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import com.google.common.base.Preconditions;

import org.ros.address.InetAddressFactory;
import org.ros.android.NodeMainExecutorService;
import org.ros.android.NodeMainExecutorServiceListener;
import org.ros.exception.RosRuntimeException;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMain;
import org.ros.node.NodeMainExecutor;

import java.net.URI;
import java.net.URISyntaxException;

import std_msgs.String;

public class RosBackgroundService extends Service {


    private final ServiceConnection nodeMainExecutorServiceConnection;
    private final java.lang.String notificationTicker;
    private final java.lang.String notificationTitle;

    protected NodeMainExecutorService nodeMainExecutorService;
    static java.lang.String prevmsg = "0";


    public static java.lang.String PROP_HTTPDOWNLOAD_INTENTID = "com.whitesun.httpdownload";
    public static java.lang.String HTTPDOWNLOAD_MESSSAGEFIELD = "message";


    public static java.lang.String PROP_POST_MSG_ID = "ID";
    public static java.lang.String PROP_POST_MSG_STATUS = "STATUS";
    public static java.lang.String PROP_POST_MSG_TEXT = "TEXT";


    private final class NodeMainExecutorServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            nodeMainExecutorService = ((NodeMainExecutorService.LocalBinder) binder).getService();
            nodeMainExecutorService.addListener(new NodeMainExecutorServiceListener() {
                @Override
                public void onShutdown(NodeMainExecutorService nodeMainExecutorService) {
                   // if ( !isFinishing() ) {
                   //     RosBackgroundService.this.finish();
                   // }
                    nodeMainExecutorService.shutdown();

                }
            });
           // startMasterChooser();
            startRos();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }


    public RosBackgroundService() {

        this.notificationTicker = "";
        this.notificationTitle = "";
        nodeMainExecutorServiceConnection = new NodeMainExecutorServiceConnection();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startNodeMainExecutorService();

        return super.onStartCommand(intent, flags, startId);
    }


    private void startNodeMainExecutorService() {
        Intent intent = new Intent(this, NodeMainExecutorService.class);
        intent.setAction(NodeMainExecutorService.ACTION_START);
        intent.putExtra(NodeMainExecutorService.EXTRA_NOTIFICATION_TICKER, notificationTicker);
        intent.putExtra(NodeMainExecutorService.EXTRA_NOTIFICATION_TITLE, notificationTitle);
        startService(intent);
        Preconditions.checkState(
                bindService(intent, nodeMainExecutorServiceConnection, BIND_AUTO_CREATE),
                "Failed to bind NodeMainExecutorService.");
    }

    @Override
    public void onDestroy() {
        if (nodeMainExecutorService != null) {
            nodeMainExecutorService.shutdown();
            unbindService(nodeMainExecutorServiceConnection);
            // NOTE(damonkohler): The activity could still be restarted. In that case,
            // nodeMainExectuorService needs to be null for everything to be started
            // up again.
            nodeMainExecutorService = null;
        }
        Toast.makeText(this, notificationTitle + " shut down.", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }



    public URI getMasterUri() {
        Preconditions.checkNotNull(nodeMainExecutorService);
        return nodeMainExecutorService.getMasterUri();
    }


    public void startRos()
    {
        URI uri;
        final java.lang.String address="http://192.168.43.174:11311";
        try {
            uri = new URI(address);
        } catch (URISyntaxException e) {
            throw new RosRuntimeException(e);
        }
        nodeMainExecutorService.setMasterUri(uri);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                NodeMain node = new SimpleSubscriberNode()
                {

                    @Override
                    protected void onReceivedMessage(java.lang.String message) {
                        super.onReceivedMessage(message);

                        if(message.length() > 1)
                        {
                            if(!prevmsg.equals(message))
                            {

//                                 Toast.makeText(getApplicationContext(), "Inst ! " + message + " :" + prevmsg, Toast.LENGTH_SHORT).show();
                                prevmsg = message;


                                Intent intent = new Intent();
                                intent.setAction(PROP_HTTPDOWNLOAD_INTENTID);
                                intent.putExtra(HTTPDOWNLOAD_MESSSAGEFIELD, message);
                                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                                sendBroadcast(intent);


                            }
                        }
                    }
                };


                NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic("192.168.43.174");
                nodeConfiguration.setMasterUri(getMasterUri());


                nodeMainExecutorService.execute(node, nodeConfiguration);

                return null;
            }
        }.execute();


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



}
