package net.Fachpersonal.uno.utils;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage image;

    public final int SPRITE_WIDTH = 32;
    public final int SPRITE_HEIGHT = 48;


    public SpriteSheet(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage grabImage(int col, int row) {
        return image.getSubimage((col * SPRITE_WIDTH), (row * SPRITE_HEIGHT), SPRITE_WIDTH, SPRITE_HEIGHT);
    }

    public BufferedImage getImage(Card c) {
        int col=0,row=0;
        switch (c.getColor()) {
            case RED -> {
                switch (c.getType()) {
                    case NORMAL -> {
                        col = 1; row = c.getIndex();
                    }
                    case SKIP -> {
                        col = 0; row = 1;
                    }
                    case PLUS2 -> {
                        col = 1; row = 10;
                    }
                    case PLUS4 -> {
                        col = 1; row = 13;
                    }
                    case SWITCH -> {
                        col = 1; row = 11;
                    }
                    case REVERSE -> {
                        col = 0; row = 5;
                    }
                    case CHANGE_COLOR -> {
                        col = 1; row = 12;
                    }
                }
            }
            case BLUE -> {
                switch (c.getType()) {
                    case NORMAL -> {
                        col = 4; row = c.getIndex();
                    }
                    case SKIP -> {
                        col = 0; row = 4;
                    }
                    case PLUS2 -> {
                        col = 4; row = 10;
                    }
                    case PLUS4 -> {
                        col = 4; row = 13;
                    }
                    case SWITCH -> {
                        col = 4; row = 11;
                    }
                    case REVERSE -> {
                        col = 0; row = 8;
                    }
                    case CHANGE_COLOR -> {
                        col = 4; row = 12;
                    }
                }
            }
            case GREEN -> {
                switch (c.getType()) {
                    case NORMAL -> {
                        col = 3; row = c.getIndex();
                    }
                    case SKIP -> {
                        col = 0; row = 3;
                    }
                    case PLUS2 -> {
                        col = 3; row = 10;
                    }
                    case PLUS4 -> {
                        col = 3; row = 13;
                    }
                    case SWITCH -> {
                        col = 3; row = 11;
                    }
                    case REVERSE -> {
                        col = 0; row = 7;
                    }
                    case CHANGE_COLOR -> {
                        col = 3; row = 12;
                    }
                }
            }
            case YELLOW -> {
                switch (c.getType()) {
                    case NORMAL -> {
                        col = 2; row = c.getIndex();
                    }
                    case SKIP -> {
                        col = 0; row = 2;
                    }
                    case PLUS2 -> {
                        col = 2; row = 10;
                    }
                    case PLUS4 -> {
                        col = 2; row = 13;
                    }
                    case SWITCH -> {
                        col = 2; row = 11;
                    }
                    case REVERSE -> {
                        col = 0; row = 6;
                    }
                    case CHANGE_COLOR -> {
                        col = 2; row = 12;
                    }
                }
            }
            case SPECIAL -> {
                switch (c.getType()) {
                    case CHANGE_COLOR -> {
                        col = 0; row = 12;
                    }
                    case PLUS4 -> {
                        col = 0; row = 13;
                    }
                }
            }
        }
        return grabImage(col,row);
    }

}
