package net.Fachpersonal.uno.utils;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage image;

    private final int width = 32;
    private final int height = 48;


    public SpriteSheet(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage grabImage(int col, int row) {
        return image.getSubimage((col * width), (row * height), width, height);
    }

}
