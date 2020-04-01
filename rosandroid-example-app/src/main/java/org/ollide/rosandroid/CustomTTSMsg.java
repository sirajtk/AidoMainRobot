package org.ollide.rosandroid;


import org.ros.internal.message.Message;


import org.ros.internal.message.Message;

public interface CustomTTSMsg extends Message {
    java.lang.String _TYPE = "aido_example_1/AidoExample1";
    java.lang.String _DEFINITION = "string data\n";

    java.lang.String getData();

    void setData(java.lang.String var1);
}
