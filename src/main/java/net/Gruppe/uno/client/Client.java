package net.Gruppe.uno.client;

import net.Gruppe.uno.exceptions.UNOERR;
import net.Gruppe.uno.exceptions.UNOException;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Programm für die Nutzer
 */
public class Client {

    public static boolean debug = false;
    public static Client client = null;
    private final int MAX_USERNAME_LENGTH = 8;
    private final int MIN_USERNAME_LENGTH = 4;
    private final BufferedReader input;
    private final PrintWriter output;
    public Game game = null;

    /**
     * Konstruktor der den Client startet.
     * Dieser lässt einen eigen ausgesuchten Namen eingeben und prüft ob dieser den Bedingungen entspricht (MAX & MIN).
     * Des Weiteren verbindet sich dieser Client mit dem dafür vorgesehen den ServerSocket mithilfe der $ip und $port Argumente, die mit übergeben werden.
     * Sobald die Verbindung zwischen Server und Client stabil ist, werden die Ein- und Ausgänge in Variablen manifestiert.
     */
    public Client(String ip, int port) throws IOException, UNOException {
        /** Sorgt dafür das nur eine Instanz von client existiert.
         *  Dies wird erreicht durch die zuweisung der Client Instanz auf eine statische Variable, die von überall zugänglich ist*/
        if (client == null) {
            client = this;
        } else {
            throw new UNOException(UNOERR.CANNOT_CREATE_CLIENT);
        }

        String username;

        /** Eingabe über Konsole */
        Scanner sc = new Scanner(System.in);
        System.out.print("Whats your name? ");

        /** Wartet bis der Nutzer einen Namen eingetippt hat */
        username = sc.nextLine();
        System.out.println("\n");

        /** Einfache Abfrage, ob der Nutzername zwischen 4 und 8 Buchstaben lang ist. Wenn nicht, dann wiederhole den Nutzernamen eingabe Prozess */
        while (username.length() > MAX_USERNAME_LENGTH || username.length() < MIN_USERNAME_LENGTH) {
            System.out.println("Sorry but your username is either too long or to short! [min. 4 & max. 8 characters!]");
            System.out.print("Whats your name? ");
            username = sc.nextLine();
            System.out.println("\n");
        }

        /** Anschlusspunkt das die Verbindung zwischen Server und Client hält */
        Socket s = new Socket(ip, port);

        console("New player [" + username + "]");

        /** Manifestierung der Ein- und Ausgänge des Clients in Variablen */
        input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        output = new PrintWriter(s.getOutputStream());

        /** Sendet den Nutzernamen an den Server */
        write(username);

        /** Wartet auf Antwort des Servers */
        String x = readLine();

        /** Wenn der Server mit "#initGame" antwortet, wird ein neues Spiel gestartet */
        if (x.equals("#initGame")) {
            if (game == null) {
                game = new Game();
                game.startGame();
            } else {
                throw new UNOException(UNOERR.GAME_STARTED);
            }
        }
        s.close();
    }

    /**
     * Start des Programms.
     * Mögliche Argumente: -d | Aktiviert den debug mode, welcher Entwickler ausgaben anzeigt
     */
    public static void main(String[] args) throws IOException, UNOException {
        /** Abfrage, ob das erste übergebende Argument -d ist, wenn dies wahr ist dann wird der debug mode aktiviert */
        if (args[0].equalsIgnoreCase("-d")) {
            debug = true;
            System.out.println("debug enabled");
        }
        /** erstellt eine Instanz eines Clients */
        new Client("localhost", 12345);
    }

    /**
     * Ausgabe für den Debug Mode aktiv ist
     */
    public static void console(String message) {
        if (debug) {
            System.out.println(message);
        }
    }

    /**
     * Methode um eine Nachricht an den Server zu schreiben
     */
    public void write(String msg) throws IOException {
        output.println(msg);
        console("WRITE>>"+msg);
        output.flush();
    }

    /**
     * Methode um die Nachrichten die vom Server kommen zu lesen
     */
    public String readLine() throws IOException {
        return input.readLine();
    }

}
