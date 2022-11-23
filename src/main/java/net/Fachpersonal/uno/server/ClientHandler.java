package net.Fachpersonal.uno.server;

import net.Fachpersonal.uno.exceptions.UNOException;
import net.Fachpersonal.uno.utils.Player;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private final BufferedReader input;
    private final ObjectInputStream objInput;
    private final PrintWriter output;
    private final ObjectOutputStream objOutput;

    private Player p;

    public ClientHandler(Socket s) throws IOException, UNOException {
        this.socket = s;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.objInput = new ObjectInputStream(s.getInputStream());
        this.output = new PrintWriter(socket.getOutputStream());
        this.objOutput = new ObjectOutputStream(s.getOutputStream());

        p = new Player(readLine(),this);
        System.out.println("New Player Connected : " + p.getUsername() + " [ " + UNOServer.UNO.getConnectedPlayers() + " / " + UNOServer.UNO.getMAX_PLAYERS() + " ]");
    }

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

    public void stop() {
        try {
            UNOServer.UNO.setConnectedPlayers(UNOServer.UNO.getConnectedPlayers()-1);
            System.out.println("Player disconnected! | " + UNOServer.UNO.getConnectedPlayers() + " left");
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

    public Object readObject() throws IOException, ClassNotFoundException {
        return objInput.readObject();
    }

    public void write(String msg) { // Writes to client
        output.println(msg);
        output.flush();
    }

    public void writeOBJ(Object obj) throws IOException {
        objOutput.writeObject(obj);
        objOutput.flush();
    }

    public Player getP() {
        return p;
    }
}
