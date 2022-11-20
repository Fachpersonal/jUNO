package net.Fachpersonal.uno.utils;

import net.Fachpersonal.uno.exceptions.UNOERR;
import net.Fachpersonal.uno.exceptions.UNOException;

import java.util.HashMap;

public class Card {

    public static final HashMap<Card, Boolean> gameCards = new HashMap<>();
    static {

        try {
            gameCards.put(new Card(Color.SPECIAL, Type.NONE), false);
            gameCards.put(new Card(Color.RED, 0), false);
            gameCards.put(new Card(Color.RED, 1), false);
            gameCards.put(new Card(Color.RED, 2), false);
            gameCards.put(new Card(Color.RED, 3), false);
            gameCards.put(new Card(Color.RED, 4), false);
            gameCards.put(new Card(Color.RED, 5), false);
            gameCards.put(new Card(Color.RED, 6), false);
            gameCards.put(new Card(Color.RED, 7), false);
            gameCards.put(new Card(Color.RED, 8), false);
            gameCards.put(new Card(Color.RED, 9), false);
            gameCards.put(new Card(Color.YELLOW, 0), false);
            gameCards.put(new Card(Color.YELLOW, 1), false);
            gameCards.put(new Card(Color.YELLOW, 2), false);
            gameCards.put(new Card(Color.YELLOW, 3), false);
            gameCards.put(new Card(Color.YELLOW, 4), false);
            gameCards.put(new Card(Color.YELLOW, 5), false);
            gameCards.put(new Card(Color.YELLOW, 6), false);
            gameCards.put(new Card(Color.YELLOW, 7), false);
            gameCards.put(new Card(Color.YELLOW, 8), false);
            gameCards.put(new Card(Color.YELLOW, 9), false);
            gameCards.put(new Card(Color.GREEN, 0), false);
            gameCards.put(new Card(Color.GREEN, 1), false);
            gameCards.put(new Card(Color.GREEN, 2), false);
            gameCards.put(new Card(Color.GREEN, 3), false);
            gameCards.put(new Card(Color.GREEN, 4), false);
            gameCards.put(new Card(Color.GREEN, 5), false);
            gameCards.put(new Card(Color.GREEN, 6), false);
            gameCards.put(new Card(Color.GREEN, 7), false);
            gameCards.put(new Card(Color.GREEN, 8), false);
            gameCards.put(new Card(Color.GREEN, 9), false);
            gameCards.put(new Card(Color.BLUE, 0), false);
            gameCards.put(new Card(Color.BLUE, 1), false);
            gameCards.put(new Card(Color.BLUE, 2), false);
            gameCards.put(new Card(Color.BLUE, 3), false);
            gameCards.put(new Card(Color.BLUE, 4), false);
            gameCards.put(new Card(Color.BLUE, 5), false);
            gameCards.put(new Card(Color.BLUE, 6), false);
            gameCards.put(new Card(Color.BLUE, 7), false);
            gameCards.put(new Card(Color.BLUE, 8), false);
            gameCards.put(new Card(Color.BLUE, 9), false);
            gameCards.put(new Card(Color.RED, Type.SKIP), false);
            gameCards.put(new Card(Color.YELLOW, Type.SKIP), false);
            gameCards.put(new Card(Color.GREEN, Type.SKIP), false);
            gameCards.put(new Card(Color.BLUE, Type.SKIP), false);
            gameCards.put(new Card(Color.RED, Type.REVERSE), false);
            gameCards.put(new Card(Color.YELLOW, Type.REVERSE), false);
            gameCards.put(new Card(Color.GREEN, Type.REVERSE), false);
            gameCards.put(new Card(Color.BLUE, Type.REVERSE), false);
            gameCards.put(new Card(Color.RED, Type.PLUS2), false);
            gameCards.put(new Card(Color.YELLOW, Type.PLUS2), false);
            gameCards.put(new Card(Color.GREEN, Type.PLUS2), false);
            gameCards.put(new Card(Color.BLUE, Type.PLUS2), false);
            gameCards.put(new Card(Color.RED, Type.PLUS2), false);
            gameCards.put(new Card(Color.YELLOW, Type.SWITCH), false);
            gameCards.put(new Card(Color.GREEN, Type.SWITCH), false);
            gameCards.put(new Card(Color.BLUE, Type.SWITCH), false);
            gameCards.put(new Card(Color.RED, Type.SWITCH), false);
            gameCards.put(new Card(Color.SPECIAL, Type.PLUS4), false);
            gameCards.put(new Card(Color.SPECIAL, Type.CHANGE_COLOR), false);
        } catch (UNOException e) {
            throw new RuntimeException(e);
        }
    }

    private Color color;
    private Type type;
    private int index;

    public Card(Color color, int index) {
        this.color = color;
        this.type = Type.NORMAL;
        this.index = index;

    }

    public Card(Color color, Type type) throws UNOException {
        switch (type) {
            case REVERSE, PLUS2, SKIP, SWITCH -> {
                this.color = color;
                this.type = type;
            }
            case CHANGE_COLOR, PLUS4 -> {
                this.color = Color.SPECIAL;
                this.type = type;
            }
            case NORMAL -> throw new UNOException(UNOERR.CARD_MISSING_INDEX);
        }
    }

    public Color getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public enum Color {
        RED, GREEN, BLUE, YELLOW, SPECIAL;

        public final static Color[] colors = values();
    }

    public enum Type {
        REVERSE, PLUS2, PLUS4, CHANGE_COLOR, SKIP, SWITCH, NONE, NORMAL;

        public final static Type[] types = values();
    }

}
