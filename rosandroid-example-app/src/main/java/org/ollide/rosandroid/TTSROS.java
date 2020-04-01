package org.ollide.rosandroid;

import org.ros.exception.ServiceException;
import org.ros.internal.node.service.Service;
import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeMain;
import org.ros.node.service.ServiceResponseBuilder;

import android.util.Log;
import android.widget.Toast;

import com.google.common.base.Preconditions;

import std_msgs.Empty;
import std_msgs.Int64;
import std_msgs.String;

public class TTSROS implements NodeMain {

   // private final String TAG = "TestServiceServer";


    @Override
    public void onStart(ConnectedNode node) {
    /*ServiceServer<org.ros.message.std_msgs.Empty, org.ros.message.std_msgs.String> server = */
//        Preconditions.checkState(node == null);

        node.newServiceServer("callme", CustomTTSMsg._TYPE, new ServiceResponseBuilder<Service.Request, Service.Response>() {
            @Override
            public void build(Service.Request request, Service.Response response) throws ServiceException {
                Log.i("TestServiceServer", "request: " + request.toString());
            }


            /*
            @Override
            public void build(Service.Request request, Service.Response response) throws ServiceException {
               // Log.i("TestServiceServer", "request: " + request.toString());


                response.setInt64("num",123);

            }*/

        });


    }




    @Override
    public void onError(Node node, Throwable throwable) {

    }

    @Override
    public void onShutdown(Node arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onShutdownComplete(Node arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public GraphName getDefaultNodeName() {
        // TODO Auto-generated method stub
        return null;
    }

}