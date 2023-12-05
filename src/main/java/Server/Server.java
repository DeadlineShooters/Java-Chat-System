package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private ServerSocket serverSocket;
    private static Map<String, ChatRoom> chatrooms = new HashMap<>();
    static int chatRoomId = 0;


    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    public void startServer() {
        try {
            while(!serverSocket.isClosed()) {
                System.out.println("Server listening...");
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected!");
                ClientHandler clientHandler = new ClientHandler(socket, chatrooms);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3001);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
