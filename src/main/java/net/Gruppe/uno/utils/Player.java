package net.Gruppe.uno.utils;

import net.Gruppe.uno.exceptions.UNOException;
import net.Gruppe.uno.server.ClientHandler;

import java.util.ArrayList;

/** Spieler Objekt */
public class Player {
    private final String username;
    private ArrayList<Card> hand;

    private ClientHandler ch;

    /** Konstruktor des Spielers, der den übergebenen Namen speichert */
    public Player(String username) {
        this.username = username;
    }
    /** Konstruktor des Spielers,  der den übergebenen Namen und dazugehörigen Clienthandler speichert */
    public Player(String username, ClientHandler ch) {
        this.username = username;
        this.ch = ch;
    }

    /** Wandelt den gegebenen String wieder in ein Spieler (Player) Objekt */
    public static Player StringToPlayer(String string) throws UNOException {
        StringBuffer sb = new StringBuffer(string);
        String username = sb.substring(0, sb.indexOf("|"));
        ArrayList<Card> hand = new ArrayList<>();
        String[] handStr = string.substring(string.indexOf("|") + 1).split(",");
        for (String hstr : handStr) {
            hand.add(Card.StringToCard(hstr));
        }
        Player p = new Player(username);
        p.setHand(hand);
        return p;
    }

    /** ### GETTER und SETTER ### */
    public String getUsername() {
        return username;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> newHand) {
        hand = newHand;
    }

    public ClientHandler getCh() {
        return ch;
    }

    /** Gibt den Spieler in einem String format wieder aus */
    public String toString() {
       String str = username + "|";
        for(Card c : hand) {
            str += c.toString() + ",";
        }
        return str.substring(0,str.length()-1);
    }
}
