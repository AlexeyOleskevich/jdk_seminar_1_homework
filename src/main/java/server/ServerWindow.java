package server;

import client.ClientGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServerWindow extends JFrame implements NotificatorUsersGUI {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JButton btnStart, btnStop;
    private final JPanel buttons;

    private final File logFile;
    private final JTextArea log;

    private final List<ClientGUI> clientGUIList;

    private boolean isServerWorking;

    public ServerWindow() throws IOException {
        logFile = createLogFile();
        clientGUIList = new ArrayList<>();
        isServerWorking = false;

        log = new JTextArea();

        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");
        setUpStopButtonListener();
        setUpStartButtonListener();

        buttons = new JPanel(new GridLayout(1, 2));
        buttons.add(btnStart);
        buttons.add(btnStop);
        add(buttons, BorderLayout.SOUTH);

        setUpDefaultWindowSettings();

        add(log);
    }

    private void setUpStartButtonListener() {
        btnStart.addActionListener(e -> {
            String message = "Server is working!\n";
            isServerWorking = true;
            log.append(message);
            logMessage(message);
        });
    }

    private void setUpStopButtonListener() {
        btnStop.addActionListener(e -> {
            isServerWorking = false;
            log.append("Server stopped!\n");
        });
    }


    public String getLog() {
        return log.getText();
    }

    public boolean isServerWorking() {
        return isServerWorking;
    }

    private File createLogFile() throws IOException {
        File logFile = new File("src/main/java/server/log.txt");
        if (!logFile.exists()) {
            logFile.createNewFile();
        }
        return logFile;
    }

    private void setUpDefaultWindowSettings() {
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
    }

    public void sendMessage(String message, ClientGUI currentUser) {
        if (isServerWorking) {
            log.append(message);
            logMessage(message);
            notifyUsers(message, currentUser);
        }
    }

    private void logMessage(String message) {
        try (FileWriter fileWriter = new FileWriter(logFile, true)) {
            int c;
            fileWriter.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            logFile.delete();
        }
    }

    @Override
    public boolean addUserGUI(ClientGUI user) {
        if (isServerWorking) {
            clientGUIList.add(user);
            return true;
        }
        return false;
    }

    @Override
    public void removeUserGUI(ClientGUI user) {
        clientGUIList.remove(user);
    }

    @Override
    public void notifyUsers(String message, ClientGUI currentUser) {
        for (ClientGUI user : clientGUIList) {
            if (!user.equals(currentUser)) {
                user.update(message);
            }
        }
    }
}
