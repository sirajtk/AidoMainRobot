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
import android.util.Log;
import android.widget.Toast;

import org.ros.concurrent.CancellableLoop;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;

import java.text.SimpleDateFormat;
import java.util.Date;

import std_msgs.String;

public class SimpleSubscriberNode extends AbstractNodeMain implements NodeMain {





    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("SimpleSubscriber/TimeLoopNode");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {

        final Subscriber<std_msgs.String> subscriber = connectedNode.newSubscriber(GraphName.of("fromlinux"), std_msgs.String._TYPE);


        subscriber.addMessageListener(new MessageListener<std_msgs.String>() {
            @Override
            public void onNewMessage(std_msgs.String string) {

                Log.i("ROSLOG", "GOT the message : " + string.getData());

                onReceivedMessage(string.getData());

                try {
                   // Toast.makeText(_maincontext, "GOT:" + string, Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex)
                {

                }

            }
        });


    }


    protected void onReceivedMessage(java.lang.String message)
    {

    }

}
