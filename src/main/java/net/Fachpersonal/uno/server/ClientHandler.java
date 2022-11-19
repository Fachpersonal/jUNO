package net.Fachpersonal.uno.server;

import net.Fachpersonal.uno.exceptions.UNOException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private final UNOServer unos;
    private final BufferedReader input;
    private final PrintWriter output;

    public ClientHandler(Socket s, UNOServer unos) throws IOException, UNOException {
        this.socket = s;
        this.unos = unos;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            String line = readLine();
            System.out.println("New Player Connected : " + line + " [ " + unos.getConnectedPlayers() + " / " + unos.getMAX_PLAYERS() + " ]");
            while (true) {
                line = readLine();
                System.out.println(line);
                write("ECHO:" + line);
            }
        } catch (IOException e) {
            stop();
        }
    }

    public void stop() {
        try {
            unos.setConnectedPlayers(unos.getConnectedPlayers()-1);
            System.out.println("Player disconnected! | " + unos.getConnectedPlayers() + " left");
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readLine() throws IOException { // Reads what client sends to server
        return input.readLine();
    }

    public void write(String msg) { // Writes to client
        output.println(msg);
        output.flush();
    }
}
