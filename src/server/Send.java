package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by NEHAL on 11/28/2016.
 */
public class Send {
    Socket socket;
    DataOutputStream dataOutputStream;

    public Send(Socket socket) {
        this.socket = socket;
        try {
            dataOutputStream=new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void send(String f){

        try {
            dataOutputStream.writeUTF(f);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
