package net.Fachpersonal.uno.client;

import net.Fachpersonal.uno.exceptions.UNOException;
import net.Fachpersonal.uno.utils.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private final int MAX_USERNAME_LENGTH = 8;
    private final int MIN_USERNAME_LENGTH = 4;

    private final BufferedReader input;
    private final PrintWriter output;

    private Player p;

    public Client(String ip, int port) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Whats your name? ");
        String username = sc.nextLine();
        System.out.println("\n");

        while(username.length() > MAX_USERNAME_LENGTH || username.length() < MIN_USERNAME_LENGTH) {
            System.out.println("Sorry but your username is either too long or to short! [min. 4 & max. 8 characters!]");
            System.out.print("Whats your name? ");
            username = sc.nextLine();
            System.out.println("\n");
        }
        Socket s = new Socket(ip, port);

        p = new Player(username);
        System.out.println("New Player with name " + p.getUsername());
        input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        output = new PrintWriter(s.getOutputStream());

        write(p.getUsername());
        gameLoop();
    }


    private void gameLoop() {

    }

    public static void main(String[] args) throws IOException {
        if(args[0].equalsIgnoreCase("-d")) {
            debug = true;
            System.out.println("debug enabled");

        }
        new Client("localhost", 12345);
    }

    private void write(String msg) { // writes to server
        output.println(msg);
        output.flush();
    }

    private String readLine() throws IOException { // Reads messages from server
        return input.readLine();
    }

    public static void console(String message) {
        if(debug) {
            System.out.println(message);
        }
    }
}
