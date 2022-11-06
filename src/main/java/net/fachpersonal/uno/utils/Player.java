package net.fachpersonal.uno.utils;

import net.fachpersonal.uno.exceptions.UNOException;

import java.util.ArrayList;

public class Player {
    private final String username;
    private ArrayList<Card> hand;

    public Player(String username) {
        this.username = username;
    }

    public void takeNewCard(int number) throws UNOException {
        for (int i = 0; i < number; i++) {
            hand.add(Card.newCard());
        }
    }

    public void takeNewCard() throws UNOException {takeNewCard(0);}

    public String getUsername() {return username;}

    public ArrayList<Card> getHand() {return hand;}
    public void setHand(ArrayList<Card> newHand) {hand = newHand;}
}
