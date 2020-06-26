package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import server.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by NEHAL on 11/28/2016.
 */
public class Client extends Application{
    Label status;
    String line;
    Label[] wordlebel;
    String[]word;
    AnchorPane group;
    double rate;
    ImageView imageView;
    Image image;
    TextField tf;
    int indx;
    String user;
    Send send;
    HashMap<String,ImageView> stringImageViewHashMap=new HashMap<>();
    public static void main(String[] args) {

      Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        group=new AnchorPane();
        image=new Image(String.valueOf(getClass().getResource("c1.png")));
        TextField userName=new TextField();
        Button button=new Button("Connect");
        Client client=this;
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Socket socket=new Socket("127.0.0.1",8585);
                    Recieve recieve=new Recieve(socket,client);
                    send=new Send(socket);
                    user=userName.getText();
                    send.send("USER "+userName.getText());


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        userName.setLayoutX(200);
        button.setLayoutY(50);
        userName.setLayoutY(10);
        button.setLayoutX(200);
        status=new Label();
        status.setLayoutY(70);
        group.getChildren().add(status);
        group.getChildren().add(userName);
        group.getChildren().add(button);
        tf=new TextField();




       /* final int[] indx = {0};
        HBox hbox=new HBox();
        word=str.split(" ");
        wordlebel=new Label[word.length];
        for (int i = 0; i <word.length ; i++) {
            wordlebel[i]=new Label();
            if(i!=word.length-1) word[i]+=" ";
            wordlebel[i].setText(word[i]);
            hbox.getChildren().add(wordlebel[i]);

        }
        rate=400.0/str.length();
        wordlebel[indx[0]].setTextFill(Color.BLUE);
        TextField tf=new TextField();
        Label result=new Label();
        group.getChildren().add(userName);
        group.getChildren().add(button);
        group.getChildren().add(hbox);
        group.getChildren().add(tf);
        group.getChildren().add(result);
        hbox.setLayoutY(150);
        tf.setLayoutY(175);
        result.setLayoutY(200);
        tf=new TextField();
        tf.setVisible(false);
*/



        tf.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(tf.getText());
                if(word[indx].startsWith(tf.getText())){
                    wordlebel[indx].setTextFill(Color.GREEN);
                }
                else wordlebel[indx].setTextFill(Color.RED);
                if(tf.getText().equals(word[indx])){
                    tf.setText("");
                    //imageView.setLayoutX(imageView.getLayoutX()+word[indx[0]].length()*rate);
                    send.send("UPDATE "+user+" "+word[indx].length());
                    wordlebel[indx].setTextFill(Color.BLACK);
                    indx++;
                    if(indx==word.length) {
                        tf.setEditable(false);
                    }
                    else {
                        wordlebel[indx].setTextFill(Color.BLUE);
                    }

                }
            }
        });
        Scene scene =new Scene(group,700,400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    void updateStatus(String str){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                status.setText(str);
            }
        });
    }
    void setUpUsers(String str){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tf.setLayoutY(175);
                tf.setEditable(false);
                String []s=str.split(" ");
                group.getChildren().add(tf);
                System.out.println(s);
                for(int i=1;i<s.length;i++){
                    Label label=new Label(s[i]);
                    System.out.println(s[i]);
                    label.setLayoutX(20);
                    label.setLayoutY(250+(i-1)*50);
                    imageView=new ImageView(image);
                    imageView.setLayoutX(60);
                    imageView.setLayoutY(250+(i-1)*50);
                    group.getChildren().addAll(label,imageView);
                    stringImageViewHashMap.put(s[i],imageView);
                }
            }
        });

    }


    void setUpLines(String str){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                line=str.split("##")[1];
                HBox hbox=new HBox();
                word=line.split(" ");
                indx=0;
                wordlebel=new Label[word.length];
                for (int i = 0; i <word.length ; i++) {
                    wordlebel[i]=new Label();
                    if(i!=word.length-1) word[i]+=" ";
                    wordlebel[i].setText(word[i]);
                    hbox.getChildren().add(wordlebel[i]);

                }
                rate=400.0/str.length();
                wordlebel[indx].setTextFill(Color.BLUE);
                hbox.setLayoutY(150);
                group.getChildren().add(hbox);
                tf.setEditable(true);
            }
        });

    }


    void updatePos(String s){
        String []str=s.split(" ");
        int prog=Integer.parseInt(str[2]);
        ImageView imageView=stringImageViewHashMap.get(str[1]);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                imageView.setLayoutX(imageView.getLayoutX()+rate*prog);
            }
        });

    }


}
