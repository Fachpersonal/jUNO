package net.Fachpersonal.uno.server;

import net.Fachpersonal.uno.exceptions.UNOException;
import net.Fachpersonal.uno.utils.UNOPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private final BufferedReader input;
    private final PrintWriter output;

    public ClientHandler(Socket s) throws IOException, UNOException {
        this.socket = s;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            String line = readLine();
            UNOPackage unop = new UNOPackage(line);
            System.out.println("New Player Connected : "+unop.getValue());
            while(true) {
                line = readLine();
                System.out.println(line);
                write("ECHO:"+line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UNOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            System.out.println("Client disconnected!");
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