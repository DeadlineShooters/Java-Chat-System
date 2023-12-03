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
    private String roomId;

    public ClientHandler(Socket socket, Map<String, ChatRoom> chatRooms) {
        try {
            this.socket = socket;
            this.chatRooms = chatRooms;
            // get the output stream and convert it into a character stream
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.clientUsername = bufferedReader.readLine();
            this.roomId = bufferedReader.readLine();



            chatRooms.computeIfAbsent(roomId, k -> new ChatRoom(roomId));
            User user = new User(clientUsername);
            user.setPrintWriter(printWriter);
            chatRooms.get(roomId).join(user);
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
//                broadcastMessage(messageFromClient);
                chatRooms.get(roomId).broadcastMessage(clientUsername,messageFromClient);
            } catch (IOException e) {
                closeEverything();
                break;
            }
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
