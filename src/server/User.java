package server;

import java.net.Socket;

/**
 * Created by NEHAL on 11/28/2016.
 */
public class User {
    int wordCont;
    Send send;
    Recieve recieve;
    Socket socket;
    int pos=-1;
    Server server;
    String name;

    public User(Socket socket, Server server) {
        this.socket = socket;

        this.server = server;
        send=new Send(socket);
        recieve=new Recieve(socket,this);
    }

    public void setName(String name) {
        this.name = name;
        System.out.println(name);
        server.sendPlayerList();
    }


    public void setProg(int i){
        wordCont+=i;
        if(wordCont==server.line.length()) {
            pos=server.currentPos;
            server.currentPos++;
            server.updatePos();
        }
    }
}
