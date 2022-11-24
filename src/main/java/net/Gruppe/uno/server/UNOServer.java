package net.Gruppe.uno.server;

import net.Gruppe.uno.exceptions.UNOException;
import net.Gruppe.uno.utils.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//import net.Fachpersonal.Main;

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
            game.startGame();
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
        connectedPlayers++;
        System.out.println("Client connected!");
        ClientHandler ch = new ClientHandler(s);
        clients.add(ch);
        new Thread(ch).start();
    }

    public void broadcast(String message) {
        for (ClientHandler ch : clients) {
            ch.write(message);
        }
        System.out.println("[@ALL] " + message);
    }

    public int getMAX_PLAYERS() {
        return MAX_PLAYERS;
    }
}
