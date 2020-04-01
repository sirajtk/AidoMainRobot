package aido_example_1;


import org.ros.internal.message.Message;

public interface AidoExample1 extends Message {
    String _TYPE = "aido_example_1/AidoExample1";
    String _DEFINITION = "int64 a\n" +
            "int64 b\n" +
            "---\n" +
            "int64 sum";

    String getData();

    void setData(String var1);
}
