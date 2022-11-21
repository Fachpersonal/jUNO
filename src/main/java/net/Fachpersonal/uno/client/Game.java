package net.Fachpersonal.uno.client;

import net.Fachpersonal.uno.client.Player;
import net.Fachpersonal.uno.utils.BufferedImageLoader;
import net.Fachpersonal.uno.utils.SpriteSheet;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game {

    private Player[] players;

    private BufferedImage spriteSheet = null;
    public Game(Player[] players) {
        this.players = players;
        init();
    }

    public void init() {
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            spriteSheet = loader.loadImage("/default.png");
            SpriteSheet ss = new SpriteSheet(spriteSheet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
