package net.fachpersonal.uno.server;

import com.almasb.fxgl.net.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class UNOServer {

    private ServerSocket ss;
    private ArrayList<ClientHandler> clients;

    public UNOServer(int port, int max_players) throws IOException {
        clients = new ArrayList<ClientHandler>();
        ServerSocket ss = new ServerSocket(port);
        if(max_players == -1) {
            while(true){
                assignClient();
            }
        } else {
            for (int i = 0; i < max_players; i++) {
                assignClient();
            }
        }
    }

    public static UNOServer DefaultUNOServer() throws IOException {
        return new UNOServer(12345, 3);
    }

    private void assignClient() throws IOException {
        Socket s = ss.accept();
        ClientHandler ch = new ClientHandler(s);
        new Thread(ch).start();
        clients.add(ch);
    }
}
