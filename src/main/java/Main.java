import client.ClientGUI;
import server.ServerWindow;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerWindow server = new ServerWindow();
        new ClientGUI(server);
        new ClientGUI(server);
    }
}
