package Client.User;

import Client.User.Repositories.UserRepo;
import Client.User.Views.Components.ChatPanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
//    private User currentUser;
    private String chatRoomId;
    String spliter = "<21127089>";
    private static Client client = null;
    String username = CurrentUser.getInstance().getUser().username();

    public static Client getInstance() {
        if (client == null)
            client = new Client();
        return client;
    }

    private Client() {
        try {
            this.socket = new Socket("localhost", 3001);
//            this.currentUser = currentUser;
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            this.username = username;
//            this.chatRoomId ;
            CurrentUser.getInstance().setPrintWriter(printWriter);
            printWriter.println("login"+spliter+username);
            UserRepo.setStatus(username, true);
            System.out.println("this is "+username);
        } catch (IOException e) {
            closeEverything();
        }
    }
    public void stopThread() {
        closeEverything();

    }

    public void closeEverything() {
        UserRepo.setStatus(username, false);
        System.out.println("at Client, closeEverything");
        CurrentUser.getInstance().sendMessage("logout"+spliter);
        try {
            if (socket != null) // closing a socket will also close the socket's input and output stream
                socket.close();
            if (bufferedReader != null) // the underlying streams are closed when you close the wrapper
                bufferedReader.close();
            if (printWriter != null)
                printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String msgFromGroupChat;
        while (socket.isConnected()) {
            try {
                msgFromGroupChat = bufferedReader.readLine();
                if (msgFromGroupChat == null)
                    continue;
                System.out.println("At client: "+msgFromGroupChat);
                System.out.println("client received: "+msgFromGroupChat);
                ChatPanel.getInstance().receiveMessage(msgFromGroupChat);
            } catch (IOException e) {
//                e.printStackTrace();
                System.out.println("at Client, run: client shut down");
                break;
            }
        }
//        closeEverything();

    }
}
