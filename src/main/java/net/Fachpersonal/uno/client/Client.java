package net.Fachpersonal.uno.client;

import net.Fachpersonal.uno.exceptions.UNOERR;
import net.Fachpersonal.uno.exceptions.UNOException;
import net.Fachpersonal.uno.utils.Card;
import net.Fachpersonal.uno.utils.Player;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    private final int MAX_USERNAME_LENGTH = 8;
    private final int MIN_USERNAME_LENGTH = 4;

    private final BufferedReader input;
    private final ObjectInputStream objInput;
    private final PrintWriter output;
    private final ObjectOutputStream objOutput;

    public static boolean debug = false;

    public static Client client = null;
    public Game game = null;

    public Client(String ip, int port) throws IOException, UNOException {
        if(client == null) {
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

        console("New player ["+username+"]");

        input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        objInput = new ObjectInputStream(s.getInputStream());
        output = new PrintWriter(s.getOutputStream());
        objOutput = new ObjectOutputStream(s.getOutputStream());

        if(readLine().substring(2).equals("startGame")) {
            if(game == null) {
                game = new Game();
            } else {

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

    public void write(String msg) { // writes to server
        output.println(msg);
        output.flush();
    }
    public void writeOBJ(Object obj) throws IOException {
        objOutput.writeObject(obj);
        objOutput.flush();
    }

    public String readLine() throws IOException { // Reads messages from server
        return input.readLine();
    }
    public Object readObject() throws IOException, ClassNotFoundException {
        return objInput.readObject();
    }

    public static void console(String message) {
        if (debug) {
            System.out.println(message);
        }
    }
}
