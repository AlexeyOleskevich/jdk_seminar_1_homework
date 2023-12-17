package client;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ClientGUI extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextArea log;

    private final JPanel panelTop;
    private final JTextField ipAdress;
    private final JTextField port;
    private final JTextField login;
    private final JTextField password;
    private final JButton btnLogin;

    private final JPanel panelBot;
    private final JTextField messageField;
    private final JButton btnSend;

    public ClientGUI() throws IOException {
        setTitle("Chat client");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);



        ipAdress = new JTextField(getClientIp());
        port = new JTextField("8189");
        login = new JTextField("ivan_igorevich");
        password = new JPasswordField("123456");
        btnLogin = new JButton("Login");

        panelTop = new JPanel(new GridLayout(2, 3));
        panelTop.add(ipAdress);
        panelTop.add(port);
        panelTop.add(login);
        panelTop.add(password);
        panelTop.add(btnLogin);
        add(panelTop, BorderLayout.NORTH);

        panelBot = new JPanel(new BorderLayout());
        messageField = new JTextField();
        btnSend = new JButton("Send");
        panelBot.add(messageField, BorderLayout.CENTER);
        panelBot.add(btnSend, BorderLayout.EAST);
        add(panelBot, BorderLayout.SOUTH);

        log = new JTextArea();
        log.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(log);
        add(scrollLog);

        setVisible(true);

    }

    private String getClientIp() throws IOException {
        URL ipChecker;
        try {
            ipChecker = new URL("http://checkip.amazonaws.com");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(
                ipChecker.openStream()));

        return in.readLine();
    }




}
