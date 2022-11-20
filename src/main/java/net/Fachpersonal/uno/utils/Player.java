package net.Fachpersonal.uno.utils;

import net.Fachpersonal.uno.exceptions.UNOException;

import java.util.ArrayList;

public class Player {
    private final String username;
    private ArrayList<Card> hand;

    public Player(String username) {
        this.username = username;
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
}
