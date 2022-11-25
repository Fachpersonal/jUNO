package net.Gruppe;

import net.Gruppe.uno.exceptions.UNOException;
import net.Gruppe.uno.server.UNOServer;

import java.io.IOException;

/** Klasse zum Starten des UNO Servers */
public class Main {

    public static boolean debug = false;

    /** MAIN METHODE (STAAAARRRTTT) */
    public static void main(String[] args) throws IOException, UNOException {
        if(args[0].equalsIgnoreCase("-d")) {
            debug = true;
            System.out.println("debug enabled");
        }
        console("Hello world!");
        UNOServer.DefaultUNOServer();
    }


    /**
     * Ausgabe für den Debug Mode aktiv ist
     */
    public static void console(String message) {
        if(debug) {
            System.out.println(message);
        }
    }
}