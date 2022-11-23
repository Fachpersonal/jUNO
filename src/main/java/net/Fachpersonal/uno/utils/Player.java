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
}
