/*
 * Copyright (C) 2014 Oliver Degener.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.ollide.rosandroid;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import org.ros.address.InetAddressFactory;
import org.ros.android.RosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMain;
import org.ros.node.NodeMainExecutor;

public class MainActivity extends RosActivity {

    BroadcastToLinux receiver;
    static java.lang.String prevmsg = "0";


    public MainActivity() {
        super("RosAndroidExample", "RosAndroidExample");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("SUB", "oncreate done " );
        //moveTaskToBack(true);



    }

//    @Override
//    protected void init(NodeMainExecutor nodeMainExecutor) {
//
//
//        final SimplePublisherNode node = new SimplePublisherNode();
//        receiver = new BroadcastToLinux()
//        {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                super.onReceive(context, intent);
//                if(intent.getAction().equalsIgnoreCase(RosBackgroundServiceToLinux.PROP_HTTPDOWNLOAD_INTENTID)) {
//
//                    String message = intent.getStringExtra(RosBackgroundServiceToLinux.HTTPDOWNLOAD_MESSSAGEFIELD);
//
//
//                    node.setMessageToPublish(message);
//
//                }
//
//            }
//        };
//
//        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(InetAddressFactory.newNonLoopback().getHostAddress());
//        nodeConfiguration.setMasterUri(getMasterUri());
//
//        nodeConfiguration.setNodeName("tolinuxpub");
//
//        nodeMainExecutor.execute(node, nodeConfiguration);
//
//
//
//        IntentFilter filter_msgtolinux = new IntentFilter();
//        filter_msgtolinux.addAction(RosBackgroundServiceToLinux.PROP_HTTPDOWNLOAD_INTENTID);
//        registerReceiver(receiver, filter_msgtolinux);
//
//
//
//        ////// SUB
//
//
//        NodeMain node2 = new SimpleSubscriberNode() {
//
//            @Override
//            protected void onReceivedMessage(java.lang.String message) {
//                super.onReceivedMessage(message);
//
//                if(message.length() > 1)
//                {
//                    if(!prevmsg.equals(message))
//                    {
//
////                                 Toast.makeText(getApplicationContext(), "Inst ! " + message + " :" + prevmsg, Toast.LENGTH_SHORT).show();
//                        prevmsg = message;
//
//
//                        Intent intent = new Intent();
//                        intent.setAction(RosBackgroundService.PROP_HTTPDOWNLOAD_INTENTID);
//                        intent.putExtra(RosBackgroundService.HTTPDOWNLOAD_MESSSAGEFIELD, message);
//                        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//                        sendBroadcast(intent);
//
//
//                    }
//                }
//            }
//        };
//
//
//
//
//        NodeConfiguration nodeConfiguration2 = NodeConfiguration.newPublic(InetAddressFactory.newNonLoopback().getHostAddress());
//        nodeConfiguration2.setMasterUri(getMasterUri());
//
//        nodeConfiguration2.setNodeName("fromlinuxsub");
//
//        nodeMainExecutor.execute(node2, nodeConfiguration2);
//
//
//        //moveTaskToBack(true);
//
//        moveTaskToBack(true);


       // NodeConfiguration configuration = NodeConfiguration.newPublic(androidhostIP, uriOfROSMachine);


       /* NodeMain node = new TTSROS();

        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(InetAddressFactory.newNonLoopback().getHostAddress());
        nodeConfiguration.setMasterUri(getMasterUri());

        nodeConfiguration.setNodeName("android_tss");
        nodeMainExecutor.execute(node, nodeConfiguration);*/







//    }




    @Override
    protected void onDestroy() {

        closeActivity();

        super.onDestroy();
    }

    @Override
    protected void init(org.ros.node.NodeMainExecutor nodeMainExecutor) {

    }
}
