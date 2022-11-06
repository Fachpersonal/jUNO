package net.fachpersonal.uno.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    @Override
    public void run() {
        while(true) {
            break;
        }
        stop();
    }

    public void stop(){
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket s) throws IOException {
        this.socket = s;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new PrintWriter(s.getOutputStream());
    }

    public String readLine() throws IOException { // Reads what client sends to server
        return in.readLine();
    }

    public void write(String msg) { // Writes to client
        out.write(msg);
        out.flush();
    }
}
