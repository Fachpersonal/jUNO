package net.fachpersonal.uno.utils;

import net.fachpersonal.uno.exceptions.UNOException;

public class Card {
    public enum COLOR {
        RED,
        GREEN,
        BLUE,
        YELLOW,
        SPECIAL;

        public final static COLOR colors[] = values();
    }

    public enum TYPE {
        REVERSE,
        PLUS2,
        PLUS4,
        CHANGE_COLOR,
        SKIP,
        NORMAL;

        public final static TYPE types[] = values();
    }

    private COLOR color;
    private TYPE type;


    public Card (COLOR color, int index) {
        this.color = color;
        this.type = TYPE.NORMAL;

    }
    public Card (COLOR color, TYPE type) throws UNOException {
        switch (type) {
            case REVERSE, PLUS2, SKIP -> {
                this.color = color;
                this.type = type;
                break;
            }
            case CHANGE_COLOR, PLUS4 -> {
                this.color = COLOR.SPECIAL;
                this.type = type;
                break;
            }
            case NORMAL -> {
                throw new UNOException(UNOException.CustomException.CARD_MISSING_INDEX);
            }
        }
    }

    public static Card newCard() throws UNOException {
        TYPE type = TYPE.types[(int)Math.floor(Math.random() * 5)];
        switch (type) {
            case NORMAL -> {
                return new Card(COLOR.colors[(int) Math.floor(Math.random() * 4)],(int) Math.floor(Math.random()*9));
            }
            case SKIP,PLUS2,REVERSE -> {
                return new Card(COLOR.colors[(int) Math.floor(Math.random() * 4)],type);
            }
            case PLUS4, CHANGE_COLOR -> {
                return new Card(COLOR.SPECIAL, type);
            }
        }
        return null;
    }

}
