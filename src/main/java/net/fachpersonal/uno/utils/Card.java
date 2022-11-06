package net.Fachpersonal.uno.utils;

import net.Fachpersonal.uno.exceptions.UNOERR;
import net.Fachpersonal.uno.exceptions.UNOException;

public class Card {
    private Color color;

    public Color getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    private Type type;

    public int getIndex() {
        return index;
    }

    private int index;

    public Card(Color color, int index) {
        this.color = color;
        this.type = Type.NORMAL;
        this.index = index;

    }

    public Card(Color color, Type type) throws UNOException {
        switch (type) {
            case REVERSE, PLUS2, SKIP -> {
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

    public static Card newCard() throws UNOException {
        Type type = Type.types[(int) Math.floor(Math.random() * 5)];
        switch (type) {
            case NORMAL -> {
                return new Card(Color.colors[(int) Math.floor(Math.random() * 4)], (int) Math.floor(Math.random() * 9));
            }
            case SKIP, PLUS2, REVERSE -> {
                return new Card(Color.colors[(int) Math.floor(Math.random() * 4)], type);
            }
            case PLUS4, CHANGE_COLOR -> {
                return new Card(Color.SPECIAL, type);
            }
        }
        return null;
    }

    public enum Color {
        RED, GREEN, BLUE, YELLOW, SPECIAL;

        public final static Color[] colors = values();
    }

    public enum Type {
        REVERSE, PLUS2, PLUS4, CHANGE_COLOR, SKIP, NORMAL;

        public final static Type[] types = values();
    }

}
