package net.Gruppe.uno.utils;

import net.Gruppe.uno.exceptions.UNOERR;
import net.Gruppe.uno.exceptions.UNOException;

import java.util.ArrayList;

/** Objekt einer Karte */
public class Card {

    public static final Card[] gameCards;
    public static final ArrayList<Integer> usedCards;

    /** Erstellt eine List mit benutzten Karten und erstellt eine Liste mit allen Karten die im Spiel sind */
    static {
        try {
            usedCards = new ArrayList<>();
            gameCards = new Card[]{new Card(Color.SPECIAL, Type.NONE),
                new Card(Color.RED, 0),
                new Card(Color.RED, 0),
                new Card(Color.RED, 1),
                new Card(Color.RED, 1),
                new Card(Color.RED, 2),
                new Card(Color.RED, 2),
                new Card(Color.RED, 3),
                new Card(Color.RED, 3),
                new Card(Color.RED, 4),
                new Card(Color.RED, 4),
                new Card(Color.RED, 5),
                new Card(Color.RED, 5),
                new Card(Color.RED, 6),
                new Card(Color.RED, 6),
                new Card(Color.RED, 7),
                new Card(Color.RED, 7),
                new Card(Color.RED, 8),
                new Card(Color.RED, 8),
                new Card(Color.RED, 9),
                new Card(Color.RED, 9),
                new Card(Color.YELLOW, 0),
                new Card(Color.YELLOW, 0),
                new Card(Color.YELLOW, 1),
                new Card(Color.YELLOW, 1),
                new Card(Color.YELLOW, 2),
                new Card(Color.YELLOW, 2),
                new Card(Color.YELLOW, 3),
                new Card(Color.YELLOW, 3),
                new Card(Color.YELLOW, 4),
                new Card(Color.YELLOW, 4),
                new Card(Color.YELLOW, 5),
                new Card(Color.YELLOW, 5),
                new Card(Color.YELLOW, 6),
                new Card(Color.YELLOW, 6),
                new Card(Color.YELLOW, 7),
                new Card(Color.YELLOW, 7),
                new Card(Color.YELLOW, 8),
                new Card(Color.YELLOW, 8),
                new Card(Color.YELLOW, 9),
                new Card(Color.YELLOW, 9),
                new Card(Color.GREEN, 0),
                new Card(Color.GREEN, 0),
                new Card(Color.GREEN, 1),
                new Card(Color.GREEN, 1),
                new Card(Color.GREEN, 2),
                new Card(Color.GREEN, 2),
                new Card(Color.GREEN, 3),
                new Card(Color.GREEN, 3),
                new Card(Color.GREEN, 4),
                new Card(Color.GREEN, 4),
                new Card(Color.GREEN, 5),
                new Card(Color.GREEN, 5),
                new Card(Color.GREEN, 6),
                new Card(Color.GREEN, 6),
                new Card(Color.GREEN, 7),
                new Card(Color.GREEN, 7),
                new Card(Color.GREEN, 8),
                new Card(Color.GREEN, 8),
                new Card(Color.GREEN, 9),
                new Card(Color.GREEN, 9),
                new Card(Color.BLUE, 0),
                new Card(Color.BLUE, 0),
                new Card(Color.BLUE, 1),
                new Card(Color.BLUE, 1),
                new Card(Color.BLUE, 2),
                new Card(Color.BLUE, 2),
                new Card(Color.BLUE, 3),
                new Card(Color.BLUE, 3),
                new Card(Color.BLUE, 4),
                new Card(Color.BLUE, 4),
                new Card(Color.BLUE, 5),
                new Card(Color.BLUE, 5),
                new Card(Color.BLUE, 6),
                new Card(Color.BLUE, 6),
                new Card(Color.BLUE, 7),
                new Card(Color.BLUE, 7),
                new Card(Color.BLUE, 8),
                new Card(Color.BLUE, 8),
                new Card(Color.BLUE, 9),
                new Card(Color.BLUE, 9),
                new Card(Color.RED, Type.SKIP),
                new Card(Color.RED, Type.SKIP),
                new Card(Color.YELLOW, Type.SKIP),
                new Card(Color.YELLOW, Type.SKIP),
                new Card(Color.GREEN, Type.SKIP),
                new Card(Color.GREEN, Type.SKIP),
                new Card(Color.BLUE, Type.SKIP),
                new Card(Color.BLUE, Type.SKIP),
                new Card(Color.RED, Type.REVERSE),
                new Card(Color.RED, Type.REVERSE),
                new Card(Color.YELLOW, Type.REVERSE),
                new Card(Color.YELLOW, Type.REVERSE),
                new Card(Color.GREEN, Type.REVERSE),
                new Card(Color.GREEN, Type.REVERSE),
                new Card(Color.BLUE, Type.REVERSE),
                new Card(Color.BLUE, Type.REVERSE),
                new Card(Color.RED, Type.PLUS2),
                new Card(Color.RED, Type.PLUS2),
                new Card(Color.YELLOW, Type.PLUS2),
                new Card(Color.YELLOW, Type.PLUS2),
                new Card(Color.GREEN, Type.PLUS2),
                new Card(Color.GREEN, Type.PLUS2),
                new Card(Color.BLUE, Type.PLUS2),
                new Card(Color.BLUE, Type.PLUS2),
                new Card(Color.RED, Type.PLUS2),
                new Card(Color.RED, Type.PLUS2),
                new Card(Color.SPECIAL, Type.PLUS4),
                new Card(Color.SPECIAL, Type.PLUS4),
                new Card(Color.SPECIAL, Type.PLUS4),
                new Card(Color.SPECIAL, Type.PLUS4),
                new Card(Color.SPECIAL, Type.CHANGE_COLOR),
                new Card(Color.SPECIAL, Type.CHANGE_COLOR),
                new Card(Color.SPECIAL, Type.CHANGE_COLOR),
                new Card(Color.SPECIAL, Type.CHANGE_COLOR)
            };

        } catch (UNOException e) {
            throw new RuntimeException(e);
        }
    }

    private Color color;
    private Type type;
    private int index;

    /** Erstelle eine normale Karte mit Farbe und Zahl */
    public Card(Color color, int index) {
        this.color = color;
        this.type = Type.NORMAL;
        this.index = index;

    }

    /** Erstelle eine Speziele (Aktionskarte) mithilfe von Farbe und dem Karten Type */
    public Card(Color color, Type type) throws UNOException {
        switch (type) {
            case REVERSE, PLUS2, SKIP -> {
                this.color = color;
                this.type = type;
                this.index = -1;
            }
            case CHANGE_COLOR, PLUS4 -> {
                this.color = Color.SPECIAL;
                this.type = type;
                this.index = -1;
            }
            case NORMAL -> throw new UNOException(UNOERR.CARD_MISSING_INDEX);
        }
    }

    /** Spezieller Konstruktor für Karten vom Typ PLUS4 sowie CHANGE_COLOR */
    public Card(Type type, Color color) {
        this.color = color;
        this.type = type;
        this.index = -1;
    }

    /** Gibt eine zufällige Karte aus */
    public static Card getRandomCard() {
        Card card = null;
        int rnd;
        do{
            /** Math.floor zum runden
             *  Math.random gibt eine Kommazahl zwischen 0 und 1
             *  Math.random() * $ZAHL - $ZAHL ist die Maximale Zahl Bsp.: Math.random()= 1 * 5 also besteht eine range von 0 bis 5
             */
            rnd = (int)(Math.floor(Math.random() * gameCards.length));
            if(!usedCards.contains(rnd)){
                usedCards.add(rnd);
                return gameCards[rnd];
            }
        } while (true);
    }

    /** Wandelt den String wieder in ein Karten Objekt um */
    public static Card StringToCard(String string) throws UNOException {
        StringBuffer sb = new StringBuffer(string);
        String[] split = string.split(":");
        Type type = Type.valueOf(split[0]);
        Color color = Color.valueOf(split[1]);
        int index = Integer.valueOf(split[2]);
        if(type == Type.NORMAL)
            return new Card(color, index);
        return new Card(color,type);
    }

    /** Gibt den Index der dazugehörigen Karte aus */
    public static int getCardIndex(Card c) {
        for (int i = 0; i < gameCards.length; i++) {
            if(gameCards[i].toString().equals(c.toString())) {
                return i;
            }
        }
        return -1;
    }

    /** ### GETTER und SETTER */

    public Color getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    /** Alle möglichen Karten Farben */
    public enum Color {
        RED, GREEN, BLUE, YELLOW, SPECIAL;

        public final static Color[] colors = values();
    }

    /** Alle möglichen Karten Typen */
    public enum Type {
        REVERSE, PLUS2, PLUS4, CHANGE_COLOR, SKIP, NONE, NORMAL;

        public final static Type[] types = values();
    }

    /** Gibt die Karte in einem String Format wieder zurück */
    public String toString(){
        return type.toString()+":"+color.toString()+":"+index;
    }
}
