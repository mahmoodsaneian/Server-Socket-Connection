package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * this class can get message from multiple clients.
 * this class has a subclass that handle a client.
 * this class has a pool of thread that execute multiple threads with together.
 *
 * @author  mahmood-saneian
 * @since   2021-5-30
 * @version 15.0.2
 */
public class Server {

    public static void main(String[] args) {
        //number of client
        int clientNumber = 0;
        ExecutorService executorService = Executors.newCachedThreadPool();
        //create an object from server socket class
        try (ServerSocket serverSocket = new ServerSocket(6000)) {
            System.out.println("server is started");
            //wait until a client join to server
            while (true){
                Socket client = serverSocket.accept();
                clientNumber++;
                System.out.println("client "+clientNumber +" is connected to server on port : 6000");
                executorService.execute(new clientHandler(client, clientNumber));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * this is a static class that handle client's message.
     * store messages in file and show all mesages to client.
     */
    public static class clientHandler implements Runnable{
        private Socket socket;
        private int    clientNumber;

        public clientHandler(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
        }

        @Override
        public void run() {
            try (DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream())){
                while (true){
                    FileOutputStream fileOutputStream = new FileOutputStream("userInformation"+clientNumber+".bin",true);
                    FileInputStream fileInputStream = new FileInputStream("userInformation"+clientNumber+".bin");
                    String input = dataInputStream.readUTF();
                    if (input.equals("over")) {
                        System.out.println("client " + clientNumber + " goodbye.see you later");
                        break;
                    }
                    String output = "";
                    int i;
                    while ((i = fileInputStream.read()) != -1)
                        output += (char) i;
                    dataOutputStream.writeUTF(output + input);
                    input += " ";
                    fileOutputStream.write(input.getBytes(StandardCharsets.UTF_8));
                }
            }catch (FileNotFoundException f){
                System.out.println(f.getStackTrace());
            }catch (IOException e){
                System.out.println(e.getStackTrace());
            }
        }
    }
}

