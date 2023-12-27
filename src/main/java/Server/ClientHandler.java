package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable {
//    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Map<String, ChatRoom> chatRooms;
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private String clientUsername = null;
    private String chatRoomId = null;
    String spliter = "<21127089>";

    public ClientHandler(Socket socket, Map<String, ChatRoom> chatRooms) {
        try {
            this.socket = socket;
            this.chatRooms = chatRooms;
            // get the output stream and convert it into a character stream
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

//            this.clientUsername = bufferedReader.readLine();
//            this.roomId = bufferedReader.readLine();



//            chatRooms.computeIfAbsent(roomId, k -> new ChatRoom(roomId));
//            User user = new User(clientUsername);
//            user.setPrintWriter(printWriter);
//            chatRooms.get(roomId).join(user);
//            clientHandlers.add(this);
//            broadcastMessage("SERVER: " + clientUsername + " has entered the chat with ID "+chatRoomId);

//            this.clientUsername = bufferedReader.readLine();
//            System.out.println(clientUsername);
//
//
//            for (String chatRoomId : chatRooms.keySet()) {
//                String msg = "online"+spliter+clientUsername+spliter+chatRoomId;
//                chatRooms.get(chatRoomId).broadcastMessage(clientUsername, msg);
//            }
        } catch (IOException e) {
            closeEverything();
        }
    }

    @Override
    public void run() {
        String messageFromClient;
        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                String[] msgSplit = messageFromClient.split(spliter);
//                System.out.println(msgSplit.length);
                System.out.println("at ClientHandler: "+ messageFromClient);
                if (msgSplit[0] != "message") {
                    handleCommands(msgSplit);
                    continue;
                }
//                broadcastMessage(messageFromClient);
                chatRooms.get(chatRoomId).broadcastMessage(clientUsername,messageFromClient);
            } catch (IOException e) {
                closeEverything();
                break;
            }
        }
    }
    void handleCommands(String[] msgSplit) {
//      command - myUsername - content - sentAt - chatRoomId
        String command = msgSplit[0];

        if (command.equals("login")) {
            this.clientUsername = msgSplit[1];
            System.out.println(clientUsername + " just logged in");
            String lobby = "lobby";

            chatRooms.computeIfAbsent(lobby, k -> new ChatRoom(lobby));
            User user = new User(clientUsername,printWriter);
            chatRooms.get(lobby).join(user);

            for (String chatRoomId : chatRooms.keySet()) {
                String msg = "online"+spliter+clientUsername+spliter+chatRoomId;
                chatRooms.get(chatRoomId).broadcastMessage(clientUsername, msg);
            }
            return;
        }
        if (command.equals("joinRoom")) {
            if (this.chatRoomId != null)
                chatRooms.get(this.chatRoomId).remove(clientUsername);
            this.chatRoomId = msgSplit[4];
//            System.out.println("ádfa");
            chatRooms.computeIfAbsent(chatRoomId, k -> new ChatRoom(chatRoomId));
            User user = new User(clientUsername,printWriter);
            chatRooms.get(chatRoomId).join(user);
            return;
        }
        if (command.equals("logout")) {
            System.out.println(clientUsername+ " just logged out");
            for (String chatRoomId : chatRooms.keySet()) {
                String msg = "offline"+spliter+clientUsername+spliter+chatRoomId;
                chatRooms.get(chatRoomId).broadcastMessage(clientUsername, msg);
            }
            closeEverything();
            return;
        }
        if (command.equals("block")) {
            // block - chatroomid
//            System.out.println("at ClientHandler: " + chatRoomId);
            String msg = "block" + spliter;
            chatRooms.get(msgSplit[1]).broadcastMessage(clientUsername,msg);
            return;
        }
        if (command.equals("unblock")) {
            String msg = "unblock" + spliter;
            chatRooms.get(msgSplit[1]).broadcastMessage(clientUsername,msg);
            return;
        }
    }
    public void closeEverything() {
//        removeClientHandler();
        for (String chatRoomId : chatRooms.keySet()) {
            chatRooms.get(chatRoomId).remove(clientUsername);
        }
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
}
