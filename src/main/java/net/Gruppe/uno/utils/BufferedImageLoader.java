package net.Gruppe.uno.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Klasse zum Laden von Bildern
 */
public class BufferedImageLoader {


    /**
     * Lade ein Bild, mithilfe des Pfades, dass im Resource Ordner liegt
     */
    public BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(getClass().getResource(path));
    }
}
