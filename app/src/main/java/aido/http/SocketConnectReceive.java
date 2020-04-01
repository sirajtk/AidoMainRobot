package aido.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import aido.common.CommonlyUsed;

/**
 * Created by sumeendranath on 09/09/16.
 */
public class SocketConnectReceive {

    private Socket socket;

    private  int SERVERPORT = 1001;
    private  String SERVER_IP = "198.162.0.2";

    public static final int BUFFER_SIZE = 2048;
    private BufferedReader in = null;


    public SocketConnectReceive(String ip, int port) {

        SERVERPORT = port;
        SERVER_IP = ip;

        new Thread(new ClientThread()).start();

    }


    public void sendMessage(String message)
    {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())),
                    true);
            out.println(message);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void onReceive(String Message)
    {

    }

    class ClientThread implements Runnable {

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

                socket = new Socket(serverAddr, SERVERPORT);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while(socket.isConnected())
                {
                    String message = receiveDataFromServer();
                    if(!CommonlyUsed.stringIsNullOrEmpty(message))
                    {
                        onReceive(message);
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }


    public String receiveDataFromServer() {
        try {
            String message = "";

            message = in.readLine();
            if (CommonlyUsed.stringIsNullOrEmpty(message)) {

                message = "";

            }



            return message;
        } catch (IOException e) {
            return "Error receiving response:  " + e.getMessage();
        }
    }


    public void close()
    {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
