package net.Fachpersonal.uno.client;

import net.Fachpersonal.uno.exceptions.UNOException;

import javax.swing.*;
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

    public static boolean debug = false;

    private String username;

    public Client(String ip, int port) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Whats your name? ");
        String line = sc.nextLine();
        System.out.println("\n");

        while(line.length() > MAX_USERNAME_LENGTH || line.length() < MIN_USERNAME_LENGTH) {
            System.out.println("Sorry but your username is either too long or to short! [min. 4 & max. 8 characters!]");
            System.out.print("Whats your name? ");
            line = sc.nextLine();
            System.out.println("\n");
        }
        Socket s = new Socket(ip, port);

        System.out.println("New Player with name " + line);
        input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        output = new PrintWriter(s.getOutputStream());

        write(line);
        username = line;

        while((line = readLine()) != null){
            if(isCommand(line)) {
                executeCommand(line.substring(2));
            } else {
                displayMessage(line);
            }
        }
    }

    private boolean isCommand(String line) {
        return ((line.charAt(0) == '_') && (line.charAt(1) == '.'));
    }

    private void executeCommand(String cmd) throws IOException {
        console("#"+cmd);
        switch(cmd) {
            case "startGame":
                startGame();
                break;
            case "gameloop":
                gameloop();
                break;
            case "stopGame":
                stopGame();
                break;
        }
    }

    private void startGame() throws IOException {
        String[] playerN = readLine().split(",");
        Player[] players = new Player[playerN.length];
        for (int i = 0; i < playerN.length; i++) {
            players[i] = new Player(playerN[i], 7);
        }
        Player[] new_players = new Player[players.length-1];
        int x = 0;
        for (int i = 0; i < players.length; i++) {
            if(!players[i].getUsername().equals(username)) {
                new_players[x] = players[i];
                x++;
            }
        }
        new Game(new_players);
    }
    private void gameloop() throws IOException {
        if(readLine().equals(username)) {
            selectCard();
        } else {
            // wait
        }

        boolean end = false;
        while(!end) {
            // punishment/layCard
            // wait
        }
    }
    private void stopGame(){}

    private void displayMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    private void selectCard() {
        highlightCards();
    }

    private void highlightCards() {

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
