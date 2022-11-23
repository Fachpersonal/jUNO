package net.Fachpersonal.uno.utils;

import net.Fachpersonal.uno.exceptions.UNOException;
import net.Fachpersonal.uno.server.ClientHandler;

import java.util.ArrayList;

public class Player {
    private final String username;
    private ArrayList<Card> hand;

    private ClientHandler ch;

    public Player(String username) {
        this.username = username;
    }
    public Player(String username, ClientHandler ch) {
        this.username = username;
        this.ch = ch;
    }
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

    public String toString() {
       String str = username + "|";
        for(Card c : hand) {
            str += c.toString() + ",";
        }
        return str.substring(0,str.length()-1);
    }

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
}
