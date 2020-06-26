package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by NEHAL on 11/28/2016.
 */
public class Recieve implements Runnable{

    Socket socket;
    User user;
    DataInputStream dataInputStream;

    public Recieve(Socket socket,User server) {
        this.socket = socket;
        this.user=server;
        InputStream inputStream= null;
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataInputStream=new DataInputStream(inputStream);

        Thread d=new Thread(this);
        d.start();
    }


    @Override
    public void run() {
        while(true){
            try {
                String s=dataInputStream.readUTF();
                System.out.println(s);
                if(s.startsWith("USER")) user.setName(s.split(" ")[1]);
                if(s.startsWith("UPDATE")){
                    user.server.updateProg(s);
                    user.setProg(Integer.parseInt(s.split(" ")[2]));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
}
