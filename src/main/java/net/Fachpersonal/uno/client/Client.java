package net.Fachpersonal.uno.client;

import net.Fachpersonal.uno.utils.Player;
import net.Fachpersonal.uno.utils.UNOPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private final BufferedReader input;
    private final PrintWriter output;

    public Client(String ip, int port) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Whats your name? ");
        String username = sc.nextLine();
        Socket s = new Socket(ip, port);

        Player p = new Player(username);
        System.out.println("New Player with name " + p.getUsername());
        input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        output = new PrintWriter(s.getOutputStream());
        UNOPackage unop = new UNOPackage(UNOPackage.Type.INIT,p.getUsername());
        System.out.println(unop.toString());
        write(unop.toString());
        System.out.println(readLine());
    }

    public static void main(String[] args) throws IOException {
        new Client("172.30.3.38", 25565);
    }

    private void write(String msg) { // writes to server
        output.println(msg);
        output.flush();
    }

    private String readLine() throws IOException { // Reads messages from server
        return input.readLine();
    }
}
