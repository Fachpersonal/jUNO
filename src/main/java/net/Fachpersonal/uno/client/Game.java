package net.Fachpersonal.uno.client;

import net.Fachpersonal.uno.utils.BufferedImageLoader;
import net.Fachpersonal.uno.utils.Player;
import net.Fachpersonal.uno.utils.SpriteSheet;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game {


    private boolean turn_clockwise;
    private int turnIndex;
    private Player player0 = null;
    private Player player1 = null;

    private Player player2 = null;

    private BufferedImage spriteSheet;

    public Game() {
        init();
        startGame();
    }

    private void init() {
        {
            Client.client.write("#requestPlayers");
            try {
                player0 = (Player) Client.client.readObject();
                Player[] ps = (Player[]) Client.client.readObject();
                int index = -1;
                for (int i = 0; i < ps.length; i++) {
                    if(player1 != null && ps[i] != player0){
                        player1 = ps[i];
                    } else if(player2 != null && ps[i] != player0){
                        player2 = ps[i];
                    }
                }

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

    public Player getPlayer0() {
        return player0;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
