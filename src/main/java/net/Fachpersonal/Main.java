package net.Fachpersonal;

import net.Fachpersonal.uno.exceptions.UNOException;
import net.Fachpersonal.uno.server.UNOServer;

import java.io.IOException;

public class Main {

    public static boolean debug = false;
    public static void main(String[] args) throws IOException, UNOException {
        if(args[0].equalsIgnoreCase("-d")) {
            debug = true;
            System.out.println("debug enabled");
        }
        console("Hello world!");
        UNOServer.DefaultUNOServer();
    }

    public static void console(String message) {
        if(debug) {
            System.out.println(message);
        }
    }
}