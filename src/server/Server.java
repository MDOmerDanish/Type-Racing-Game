package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by NEHAL on 11/28/2016.
 */
public class Server implements Runnable {
    List<User> users=new ArrayList<>();
    String line="A quick brown fox jumps over the lazy dog.";
    int currentPos=1;
    boolean flag=true;
    public Server() {
        Thread t=new Thread(this);
        try {
            ServerSocket serverSocket=new ServerSocket(8585);
            while(flag){
                Socket socket=serverSocket.accept();
                User user=new User(socket,this);
                users.add(user);
                if(users.size()==2) {
                    t.start();
                }


            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void sendPlayerList(){
        String playerName="PlayerList: ";
        for(User user: users) playerName+=user.name+" ";
        for(User user: users) user.send.send(playerName);
    }


    public void startGame(){
        String playerName="START1 ";
        for(User user: users) playerName+=user.name+" ";
        for(User user: users) user.send.send(playerName);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(User user: users) user.send.send("LINE##"+line);
    }


    public void updatePos(){
        String pos="Pos :";
        for(User user: users) if(user.pos!=-1) pos+=user.pos+":"+user.name+" ";
        for(User user: users) user.send.send(pos);
    }




    public static void main(String[] args) {
        try {
            InetAddress inetAddress=InetAddress.getLocalHost();
            System.out.println(inetAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        new Server();
    }

    public void updateProg(String str){
        for(User user: users) user.send.send(str);
    }

    @Override
    public void run() {
        System.out.println("Waiting for 10s");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag=false;
        startGame();

    }
}
