package Client.User;

import Client.Models.User;

import java.io.PrintWriter;

public class CurrentUser {
    private PrintWriter printWriter;
    private User user;
    static CurrentUser currentUser;
    private CurrentUser() {}
    public static CurrentUser getInstance() {
        if (currentUser == null)
            currentUser = new CurrentUser();
        return currentUser;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() { return user; }
    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }
    public void sendMessage(String message) {
        printWriter.println(message);
    }
}
