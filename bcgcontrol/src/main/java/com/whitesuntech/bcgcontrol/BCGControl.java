package com.whitesuntech.bcgcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BCGControl extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bcgcontrol);

        Button bt_first = (Button) findViewById(R.id.button_first);
        Button bt_stop = (Button) findViewById(R.id.button_stop);
        Button bt_second = (Button) findViewById(R.id.button_second);
        Button bt_exit = (Button) findViewById(R.id.button_exit);


        bt_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sendMessage("first.txt");

            }
        });

        bt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMessage("stop");

            }
        });
        bt_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage("second.txt");

            }
        });
        bt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }



    void sendMessage(final String msg)
    {
        final SocketConnect socket;

        socket = new SocketConnect("192.168.0.128",1005);

        SetDelay sd2 = new SetDelay(500);
        sd2.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
            @Override
            public void onDelayCompleted() {
                socket.sendMessage(msg);
                socket.close();
            }
        });




    }
}
