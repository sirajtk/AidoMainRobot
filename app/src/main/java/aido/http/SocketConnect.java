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

/**
 * Created by sumeendranath on 09/09/16.
 */
public class SocketConnect {

    private Socket socket;

    private  int SERVERPORT = 1001;
    private  String SERVER_IP = "198.162.0.2";

    public static final int BUFFER_SIZE = 2048;
    private BufferedReader in = null;


    public SocketConnect(String ip, int port) {

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

    class ClientThread implements Runnable {

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

                socket = new Socket(serverAddr, SERVERPORT);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

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
            int charsRead = 0;
            char[] buffer = new char[BUFFER_SIZE];

            while ((charsRead = in.read(buffer)) != -1) {
                message += new String(buffer).substring(0, charsRead);
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
