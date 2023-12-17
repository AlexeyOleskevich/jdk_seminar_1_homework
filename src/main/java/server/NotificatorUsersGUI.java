package server;

import client.ClientGUI;

public interface NotificatorUsersGUI {
    boolean addUserGUI(ClientGUI user);
    void removeUserGUI(ClientGUI user);
    void notifyUsers(String message, ClientGUI currentUser);
}
