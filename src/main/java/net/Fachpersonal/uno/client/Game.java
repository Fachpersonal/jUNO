package net.Fachpersonal.uno.client;

import net.Fachpersonal.uno.utils.BufferedImageLoader;
import net.Fachpersonal.uno.utils.Player;
import net.Fachpersonal.uno.utils.SpriteSheet;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game {


    private boolean turn_clockwise;
    private int turnIndex;
    private Player[] players;

    private BufferedImage spriteSheet;

    public Game() {
        init();
        startGame();
    }

    private void init() {
        {
            BufferedImageLoader loader = new BufferedImageLoader();
            try {
                spriteSheet = loader.loadImage("/default.png");
                SpriteSheet ss = new SpriteSheet(spriteSheet);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } // spriteSheet
        {
            Client.client.write("#requestPlayers");
            try {
                players = (Player[]) Client.client.readObject();
                turn_clockwise = (boolean) Client.client.readObject();
                turnIndex = (int) Client.client.readObject();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } // init players
    }

    private void startGame() {

    }

    private void stopGame() {

    }
}
