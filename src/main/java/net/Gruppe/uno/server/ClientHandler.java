package net.Gruppe.uno.server;

import net.Gruppe.uno.utils.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/** ClientHandler der jeden einzelnen Client des sich verbunden hat verwaltet */
public class ClientHandler implements Runnable {

    private final Socket socket;
    private final BufferedReader input;
    private final PrintWriter output;

    private Player p;

    /** Representiert die Clients, auf Server ebene */
    public ClientHandler(Socket s) throws IOException {
        this.socket = s;

        /** Manifestierung der Ein- und Ausgänge des Clients in Variablen */
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(socket.getOutputStream());

        /** Erstellt ein neues Spieler-Objekt */
        p = new Player(readLine(), this);
        System.out.println("New Player Connected : " + p.getUsername() + " [ " + UNOServer.UNO.getConnectedPlayers() + " / " + UNOServer.UNO.getMAX_PLAYERS() + " ]");
    }

    /**
     * Reflektiert alle ungewollten Eingaben
     */
    @Override
    public void run() {
        try {
            String line = readLine();
            while (true) {
                line = readLine();
                System.out.println(line);
                write("ECHO:" + line);
            }
        } catch (IOException e) {
            stop();
        }
    }

    /** Stoppt den Client und trennt sich mit diesem */
    public void stop() {
        try {
            UNOServer.UNO.setConnectedPlayers(UNOServer.UNO.getConnectedPlayers() - 1);
            System.out.println("Player disconnected! | " + UNOServer.UNO.getConnectedPlayers() + " left");
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Methode um die Nachrichten die vom Client kommen zu lesen
     */
    public String readLine() throws IOException { // Reads what client sends to server
        return input.readLine();
    }

    /**
     * Methode um eine Nachricht an den Client zu schreiben
     */
    public void write(String msg) { // Writes to client
        output.println(msg);
        output.flush();
    }

    /** ### GETTER UND SETTER ### */
    public Player getP() {
        return p;
    }
}
