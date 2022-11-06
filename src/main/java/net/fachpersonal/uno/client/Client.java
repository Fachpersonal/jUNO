package net.fachpersonal.uno.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        new Client("localhost", 12345);
    }

    private BufferedReader in;
    private PrintWriter out;

    public Client(String ip, int port) throws IOException {
        Socket s = new Socket(ip, port);
        Scanner sc = new Scanner(System.in);
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out = new PrintWriter(s.getOutputStream());

        /* PACKAGE
            type : [INIT,command]
            value : [username, cmds]
        */

        while(true){

        }
    }

    private void write(String msg) { // writes to server
        out.write(msg);
        out.flush();
    }

    private String readLine() throws IOException { // Reads messages from server
        return in.readLine();
    }
}
