package client;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by NEHAL on 11/28/2016.
 */
public class Recieve implements Runnable {

    Socket socket;
    DataInputStream dataInputStream;
    Client c;

    public Recieve(Socket socket,Client c) {
        this.socket = socket;
        this.c=c;
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
                if(s.startsWith("PlayerList")) c.updateStatus(s);
                else if(s.startsWith("START1")){
                    c.updateStatus("Game Will be started within 10s");
                    c.setUpUsers(s);
                }
                else if(s.startsWith("LINE")){
                    c.updateStatus("Game Started");
                    c.setUpLines(s);
                }
                else if(s.startsWith("UPDATE")) c.updatePos(s);
                else if(s.startsWith("Pos")) c.updateStatus(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
}
