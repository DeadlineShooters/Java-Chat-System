package Server;

import java.io.PrintWriter;

public class User {
    private String username;
    private PrintWriter printWriter;

    public User(String username, PrintWriter printWriter) {
        this.username = username;
        this.printWriter = printWriter;
    }

    public String getUsername() {
        return username;
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    public void sendMessage(String message) {
        printWriter.println(message);
    }
}
