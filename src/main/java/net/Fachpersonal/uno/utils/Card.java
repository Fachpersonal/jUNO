package net.Fachpersonal.uno.utils;

import net.Fachpersonal.uno.exceptions.UNOERR;
import net.Fachpersonal.uno.exceptions.UNOException;

import java.util.ArrayList;
import java.util.HashMap;

public class Card {

    public static final Card[] gameCards;
    public static final ArrayList<Integer> usedCards;

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
    public String toString(){
        return type.toString()+":"+color.toString()+":"+index;
    }
}
