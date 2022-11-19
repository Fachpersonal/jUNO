package net.Fachpersonal.uno.server;

import net.Fachpersonal.uno.exceptions.UNOException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class UNOServer {

    private final ArrayList<ClientHandler> clients;
    private ServerSocket ss;

    private final int MAX_PLAYERS;
    private int connectedPlayers = 0;

    public int getConnectedPlayers() {
        return connectedPlayers;
    }
    public void setConnectedPlayers(int n) {
        connectedPlayers = n;
    }

    public UNOServer(int port, int max_players) throws IOException, UNOException {
        MAX_PLAYERS = max_players;
        connectedPlayers = 0;
        clients = new ArrayList<>();
        ss = new ServerSocket(port);
        if (max_players == -1) {
            while (true) {
                assignClient();
            }
        } else {
            for (int i = 0; i < max_players; i++) {
                assignClient();
            }
        }
    }

    public static UNOServer DefaultUNOServer() throws IOException, UNOException {
        return new UNOServer(12345, 3);
    }

    private void assignClient() throws IOException, UNOException {
        Socket s = ss.accept();
        System.out.println("Client connected!");
        ClientHandler ch = new ClientHandler(s);
        clients.add(ch);
        new Thread(ch).start();
        connectedPlayers++;
    }

    public int getMAX_PLAYERS() {
        return MAX_PLAYERS;
    }
}
