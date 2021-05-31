package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * this is a class that get an input from user and send to server
 * then receive all messages that user send to server and show in console.
 * this class just has a main method.
 *
 * @author  mahmood-saneian
 * @since   2021-5-30
 * @version 15.0.2
 */
public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //create a socket and connect to server on port : 6000
        try (Socket socket = new Socket("127.0.0.1",6000);
             DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())){

            System.out.println("client is started");
            String input ="";
            while (!(input.equals("over"))){
                System.out.println("enter your word");
                input = scanner.next();
                if (input.equals("over"))
                    break;
                dataOutputStream.writeUTF(input);
                System.out.println(dataInputStream.readUTF());
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
