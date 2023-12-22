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
    private String clientUsername;
    private String chatRoomId;
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
                if (msgSplit.length >= 5) {
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
//        myUsername - content - sentAt - chatRoomId - command
        String command = msgSplit[4];

        if (command.equals("joinRoom")) {
            this.clientUsername = msgSplit[0];
            this.chatRoomId = msgSplit[3];
//            System.out.println("ádfa");
            chatRooms.computeIfAbsent(chatRoomId, k -> new ChatRoom(chatRoomId));
            User user = new User(clientUsername,printWriter);
            chatRooms.get(chatRoomId).join(user);
        }
    }

//    public void broadcastMessage(String messageToSend) {
//        for (ClientHandler clientHandler : clientHandlers) {
//            if (!clientHandler.clientUsername.equals(clientUsername)) {
//                clientHandler.printWriter.println(messageToSend);
//            }
//        }
//    }

//    public void removeClientHandler() {
//        clientHandlers.remove(this);
//        broadcastMessage("SERVER: "+clientUsername+" has left the chat!");
//    }
    public void closeEverything() {
//        removeClientHandler();
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
