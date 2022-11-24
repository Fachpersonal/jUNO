package net.Gruppe.uno.client;

import net.Gruppe.uno.exceptions.UNOERR;
import net.Gruppe.uno.exceptions.UNOException;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static boolean debug = false;
    public static Client client = null;
    private final int MAX_USERNAME_LENGTH = 8;
    private final int MIN_USERNAME_LENGTH = 4;
    private final BufferedReader input;
    private final PrintWriter output;
    public Game game = null;

    public Client(String ip, int port) throws IOException, UNOException {
        if (client == null) {
            client = this;
        } else {
            throw new UNOException(UNOERR.CANNOT_CREATE_CLIENT);
        }

        String username;

        Scanner sc = new Scanner(System.in);
        System.out.print("Whats your name? ");
        username = sc.nextLine();
        System.out.println("\n");

        while (username.length() > MAX_USERNAME_LENGTH || username.length() < MIN_USERNAME_LENGTH) {
            System.out.println("Sorry but your username is either too long or to short! [min. 4 & max. 8 characters!]");
            System.out.print("Whats your name? ");
            username = sc.nextLine();
            System.out.println("\n");
        }
        Socket s = new Socket(ip, port);

        console("New player [" + username + "]");

        input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        output = new PrintWriter(s.getOutputStream());
        write(username);

        String x = readLine();
        if (x.equals("#initGame")) {
            if (game == null) {
                game = new Game(this);
                game.startGame();
            } else {
                throw new UNOException(UNOERR.GAME_STARTED);
            }
        }
        s.close();
    }

    public static void main(String[] args) throws IOException, UNOException {
        if (args[0].equalsIgnoreCase("-d")) {
            debug = true;
            System.out.println("debug enabled");
        }
        new Client("localhost", 12345);
    }

    public static void console(String message) {
        if (debug) {
            System.out.println(message);
        }
    }

    public void write(String msg) throws IOException { // writes to server
        output.println(msg);
        System.out.println("WRITE>>"+msg);
        output.flush();
    }

    public String readLine() throws IOException { // Reads messages from server
        return input.readLine();
    }

}
