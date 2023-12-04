package Client.User;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private String username;
    private String chatRoomId;

    public Client(Socket socket, String username, String chatRoomId) {
        try {
            this.socket = socket;
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
            this.chatRoomId = chatRoomId;
        } catch (IOException e) {
            closeEverything();
        }
    }
    public void sendMessage() {
        printWriter.println(username);


        printWriter.println(chatRoomId);

        Scanner scanner = new Scanner(System.in);
        while (socket.isConnected()) {
            String messageToSend = scanner.nextLine();
            printWriter.println(username+": "+messageToSend);
        }
    }
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                while (socket.isConnected()) {
                    try {
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    } catch (IOException e) {
                        closeEverything();
                    }
                }
            }
        }).start();
    }
    public void closeEverything() {
        try {
            if (bufferedReader != null) // the underlying streams are closed when you close the wrapper
                bufferedReader.close();
            if (printWriter != null)
                printWriter.close();
            if (socket != null) // closing a socket will also close the socket's input and output stream
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username for the group chat: ");
        String username = scanner.nextLine();
        System.out.print("Enter the chat room ID: ");
        String chatRoomId = scanner.nextLine();
        System.out.println(chatRoomId);

        Socket socket = new Socket("localhost", 3001);
        Client client = new Client(socket, username, chatRoomId);
        client.listenForMessage();
        client.sendMessage();
    }
}
