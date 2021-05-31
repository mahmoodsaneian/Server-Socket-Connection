package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * this is a class that receive message from client
 * and append it to previous messages then send to client.
 * this class doesn't have any fields or constructor.
 * this just has a main method.
 *
 * @author  mahmood-saneian
 * @since   2021-5-30
 * @version 15.0.2
 */
public class Server {

    public static void main(String[] args) {

        //create an object from server socket class
        try (ServerSocket serverSocket = new ServerSocket(6000)) {
            System.out.println("server is started");
            //wait until a client join to server
            try(Socket socket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                FileOutputStream outputStream = new FileOutputStream("userinformation.bin",true);
                FileInputStream inputStream = new FileInputStream("userinformation.bin");) {

                System.out.println("client is connected");
                String input = "";
                String output = "";

                while (!(input.equals("over"))) {
                    input = dataInputStream.readUTF();
                    int i;
                    while ((i = inputStream.read()) != -1)
                        output += (char) i;
                    input += " ";
                    dataOutputStream.writeUTF(output + input);
                    byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
                    outputStream.write(bytes);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

