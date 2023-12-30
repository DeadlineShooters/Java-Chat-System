package Server;

import Client.User.Repositories.ChatMemberRepo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;

public class ClientHandler implements Runnable {
//    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Map<String, ChatRoom> chatRooms;
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private String clientUsername = null;
    private String chatRoomId = "lobby";
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
                if (!msgSplit[0].equals("message")) {
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
//            String lobby = "lobby";

            chatRooms.computeIfAbsent(chatRoomId, k -> new ChatRoom(chatRoomId));
            User user = new User(clientUsername,printWriter);
            chatRooms.get(chatRoomId).join(user);

//            for (String chatRoomId : chatRooms.keySet()) {
                String msg = "online"+spliter+clientUsername+spliter+chatRoomId;
//                chatRooms.get(chatRoomId).broadcastMessage(clientUsername, msg);
//            }
            chatRooms.get("lobby").broadcastMessage(clientUsername, msg);
            return;
        }
        if (command.equals("joinRoom")) {
            if (this.chatRoomId != null) {
                System.out.println("at ClientHandler, previous chatroom: "+chatRoomId);
                if (!chatRoomId.equals("lobby"))
                    chatRooms.get(this.chatRoomId).remove(clientUsername);
            }
            this.chatRoomId = msgSplit[4];
            System.out.println("at clientHandler: " + chatRoomId);
            chatRooms.computeIfAbsent(chatRoomId, k -> new ChatRoom(chatRoomId));
            User user = new User(clientUsername,printWriter);
            chatRooms.get(chatRoomId).join(user);
            return;
        }
        if (command.equals("logout")) {
            System.out.println(clientUsername+ " just logged out");
//            for (String chatRoomId : chatRooms.keySet()) {
                String msg = "offline"+spliter+clientUsername+spliter+chatRoomId;
                chatRooms.get("lobby").broadcastMessage(clientUsername, msg);
//            }
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
        if (command.equals("addFriend")) {
            // addFriend - username
            String msg = "addFriend"+spliter+clientUsername;
            chatRooms.get("lobby").sendPrivateMessage(clientUsername, msgSplit[1], msg);
            return;
        }
        if (command.equals("unfriend")) {
            // addFriend - username
            String msg = "unfriend"+spliter+clientUsername;
            chatRooms.get("lobby").sendPrivateMessage(clientUsername, msgSplit[1], msg);
            return;
        }
        if (command.equals("editGroupName")) {
            // editGroupName - chatRoomId - newGroupName
            String msg = "editGroupName"+spliter+msgSplit[1]+spliter+msgSplit[2];
            HashSet<String> members = ChatMemberRepo.getChatMembers(msgSplit[1]);
//            System.out.println("chatroomid: "+msgSplit[1]);
            for (String member : members) {
//                System.out.println("member: "+member);
                if (member.equals(clientUsername))
                    continue;
                chatRooms.get("lobby").sendPrivateMessage(clientUsername, member, msg);
            }
            chatRooms.get(msgSplit[1]).broadcastMessage(clientUsername, msg);
            return;
        }
        if (command.equals("createGroup")) {
            // createGroup - groupChatId
            String msg = "createGroup"+spliter+msgSplit[1];
            HashSet<String> members = ChatMemberRepo.getChatMembers(msgSplit[1]);
            for (String member : members) {
//                System.out.println("member: "+member);
                if (member.equals(clientUsername))
                    continue;
                chatRooms.get("lobby").sendPrivateMessage(clientUsername, member, msg);
            }
            return;
        }
        if (command.equals("addMember")) {
            // addMember - usename
            String msg = "addMember"+spliter;
            chatRooms.get("lobby").sendPrivateMessage(clientUsername, msgSplit[1], msg);
            return;

        }
        if (command.equals("updateMemberList")) {
            // updateMemberList - chatRoomId
            String msg = command+spliter;
            chatRooms.get(msgSplit[1]).broadcastMessage(clientUsername, msg);
            return;
        }
        if (command.equals("removedFromGroup")) {
            // removedFromGroup - chatRoomId - username

            String msg = "removedFromGroup"+spliter+msgSplit[1];
            chatRooms.get("lobby").sendPrivateMessage(clientUsername, msgSplit[2], msg);
            return;
        }
        if (command.equals("assignAdmin")) {
            // assignAdmin - chatRoomId
            String msg = command+spliter;
            chatRooms.get(msgSplit[1]).broadcastMessage(clientUsername, msg);
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
