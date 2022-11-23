package net.Fachpersonal.uno.server;

import net.Fachpersonal.Main;
import net.Fachpersonal.uno.exceptions.UNOException;
import net.Fachpersonal.uno.utils.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class UNOServer {

    public static UNOServer UNO;
    private final ArrayList<ClientHandler> clients;
    private final int MAX_PLAYERS;
    public Game game;
    private ServerSocket ss;
    private int connectedPlayers = 0;

    public UNOServer(int port, int max_players) throws IOException, UNOException {
        UNO = this;
        MAX_PLAYERS = max_players;
        connectedPlayers = 0;
        clients = new ArrayList<>();
        ss = new ServerSocket(port);
        while (connectedPlayers < max_players) {
            assignClient();
        }
        {
            Player[] players = new Player[max_players];
            for (int i = 0; i < max_players; i++) {
                players[i] = clients.get(i).getP();
            }
            game = new Game(players);
        }
    }

    public static UNOServer DefaultUNOServer() throws IOException, UNOException {
        return new UNOServer(12345, 3);
    }

    public int getConnectedPlayers() {
        return connectedPlayers;
    }

    public void setConnectedPlayers(int n) {
        connectedPlayers = n;
    }

    private void assignClient() throws IOException, UNOException {
        Socket s = ss.accept();
        System.out.println("Client connected!");
        ClientHandler ch = new ClientHandler(s);
        clients.add(ch);
        new Thread(ch).start();
        connectedPlayers++;
    }

    public void broadcast(String message) {
        for (ClientHandler ch : clients) {
            ch.write(message);
        }
        Main.console("[@ALL] " + message);
    }

    public void command(String command) {
        for(ClientHandler ch : clients) {
            ch.write("_."+command);
        }
        Main.console("[#CMD] " + command);
    }

    public int getMAX_PLAYERS() {
        return MAX_PLAYERS;
    }
}
