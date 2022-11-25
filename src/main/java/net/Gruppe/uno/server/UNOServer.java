package net.Gruppe.uno.server;

import net.Gruppe.uno.exceptions.UNOException;
import net.Gruppe.uno.utils.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Server
 */
public class UNOServer {

    public static UNOServer UNO;
    private final ArrayList<ClientHandler> clients;
    private final int MAX_PLAYERS;
    public Game game;
    private ServerSocket ss;
    private int connectedPlayers = 0;

    /**
     * Erstellt einen Server unter den Attributen port und setzt die maximale anzahl an Clients die sich
     * mit dem Server verbinden, können
     */
    public UNOServer(int port, int max_players) throws IOException, UNOException {
        UNO = this;
        MAX_PLAYERS = max_players;
        connectedPlayers = 0;
        clients = new ArrayList<>();
        ss = new ServerSocket(port);

        /** loop der die anzahl der Spieler kontrolliert */
        while (connectedPlayers < max_players) {
            assignClient();
        }

        /** fügt alle erstellten Spieler in eine Liste und startet ein Spiel mit dieser Liste */
        {
            Player[] players = new Player[max_players];
            for (int i = 0; i < max_players; i++) {
                players[i] = clients.get(i).getP();
            }
            game = new Game(players);
            game.startGame();
        }
    }

    /** Standard UNO Server */
    public static UNOServer DefaultUNOServer() throws IOException, UNOException {
        return new UNOServer(12345, 3);
    }

    /**
     * Erstellt einen ClientHandler und weist diesem einen Socket (client) hinzu
     */
    private void assignClient() throws IOException, UNOException {
        Socket s = ss.accept();
        connectedPlayers++;
        System.out.println("Client connected!");
        ClientHandler ch = new ClientHandler(s);
        clients.add(ch);
        new Thread(ch).start();
    }

    /**
     * Sendet an alle verbundenen Clients eine Nachricht
     */
    public void broadcast(String message) {
        for (ClientHandler ch : clients) {
            ch.write(message);
        }
        System.out.println("[@ALL] " + message);
    }

    /** ### GETTER und SETTER ### */

    public int getMAX_PLAYERS() {
        return MAX_PLAYERS;
    }

    public int getConnectedPlayers() {
        return connectedPlayers;
    }

    public void setConnectedPlayers(int n) {
        connectedPlayers = n;
    }
}
