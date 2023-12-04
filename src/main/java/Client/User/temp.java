//package Server;
//
//import java.io.*;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Set;
//
//public class ClientHandler implements Runnable {
//    public static ArrayList<ChatRoom> clientHandlers = new ArrayList<>();
//    private Socket socket;
//    private BufferedReader bufferedReader;
//    private BufferedWriter bufferedWriter;
//    private String clientUsername;
//    private Set<String> joinedChatRooms;
//
//    public ClientHandler(Socket socket) {
//        try {
//            this.socket = socket;
//            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            this.clientUsername = bufferedReader.readLine();
//            this.joinedChatRooms = new HashSet<>();
//            clientHandlers.add(this);
//            broadcastMessage("SERVER: " + clientUsername + " has entered the chat");
//        } catch (IOException e) {
//            closeEverything();
//        }
//    }
//
//    @Override
//    public void run() {
//        String messageFromClient;
//        while (socket.isConnected()) {
//            try {
//                messageFromClient = bufferedReader.readLine();
//                handleIncomingMessage(messageFromClient);
//            } catch (IOException e) {
//                closeEverything();
//                break;
//            }
//        }
//    }
//
//    private void handleIncomingMessage(String message) {
//        if (message.startsWith("/join")) {
//            String[] parts = message.split(" ");
//            if (parts.length == 2) {
//                joinChatRoom(parts[1]);
//            }
//        } else if (message.startsWith("/leave")) {
//            String[] parts = message.split(" ");
//            if (parts.length == 2) {
//                leaveChatRoom(parts[1]);
//            }
//        } else {
//            broadcastMessage(message);
//        }
//    }
//
//    private void joinChatRoom(String chatRoom) {
//        joinedChatRooms.add(chatRoom);
//        broadcastMessage("SERVER: " + clientUsername + " has joined the chat room: " + chatRoom);
//    }
//
//    private void leaveChatRoom(String chatRoom) {
//        joinedChatRooms.remove(chatRoom);
//        broadcastMessage("SERVER: " + clientUsername + " has left the chat room: " + chatRoom);
//    }
//
//    public void broadcastMessage(String messageToSend) {
//        for (ChatRoom clientHandler : clientHandlers) {
//            try {
//                if (!clientHandler.clientUsername.equals(clientUsername) && clientHandler.isInSameChatRooms(joinedChatRooms)) {
//                    clientHandler.bufferedWriter.write(messageToSend);
//                    clientHandler.bufferedWriter.newLine();
//                    clientHandler.bufferedWriter.flush();
//                }
//            } catch (IOException e) {
//                closeEverything();
//            }
//        }
//    }
//
//    private boolean isInSameChatRooms(Set<String> otherChatRooms) {
//        for (String chatRoom : joinedChatRooms) {
//            if (otherChatRooms.contains(chatRoom)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void removeClientHandler() {
//        clientHandlers.remove(this);
//        broadcastMessage("SERVER: " + clientUsername + " has left the chat!");
//    }
//
//    public void closeEverything() {
//        removeClientHandler();
//        try {
//            if (bufferedReader != null)
//                bufferedReader.close();
//            if (bufferedWriter != null)
//                bufferedWriter.close();
//            if (socket != null)
//                socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
