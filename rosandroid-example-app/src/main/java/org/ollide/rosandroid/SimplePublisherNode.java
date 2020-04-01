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
import android.util.Log;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimplePublisherNode extends AbstractNodeMain implements NodeMain {

    private static final String TAG = "ROSPUB";//SimplePublisherNode.class.getSimpleName();

    private String _messageToPublish = "samplemsg6";

    private String prevmsg = "";

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("SimplePublisher/TimeLoopNode2");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {



        final Publisher<std_msgs.String> publisher = connectedNode.newPublisher(GraphName.of("tolinux"), std_msgs.String._TYPE);

        final CancellableLoop loop = new CancellableLoop() {
            @Override
            protected void loop() throws InterruptedException {
                // retrieve current system time
                String time = new SimpleDateFormat("HH:mm:ss").format(new Date());


                //if(prevmsg.equalsIgnoreCase(_messageToPublish))
                {
                   // _messageToPublish = "";
                   // prevmsg = _messageToPublish;
                }

                // create and publish a simple string message
                if(!prevmsg.equalsIgnoreCase(_messageToPublish))//!_messageToPublish.equalsIgnoreCase(""))
                {
                    std_msgs.String str = publisher.newMessage();
                    str.setData(_messageToPublish);

                    Log.i(TAG, "publishing " + _messageToPublish);

                    publisher.publish(str);
                }
                prevmsg = _messageToPublish;



                // go to sleep for one second
                Thread.sleep(100);
            }
        };
        connectedNode.executeCancellableLoop(loop);
    }


    void setMessageToPublish(String msg)
    {
        _messageToPublish = msg;
    }

}
