package net.Fachpersonal.uno.server;

import net.Fachpersonal.uno.utils.Card;
import net.Fachpersonal.uno.utils.Player;

import java.util.ArrayList;

public class Game {

    private Player[] players;
    public Game(Player[] players) {
        this.players = players;
        startGame();
        gameLoop();
        stopGame();
    }

    private void startGame(){
        UNOServer.UNO.command("startGame");
        String playerNameCommand = "";
        for(Player p : players) {
            playerNameCommand += p.getUsername() +",";
            ArrayList<Card> hand = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                int x = (int)Math.floor(Math.random() * Card.gameCards.length);
                if(!Card.usedCards.contains(x)) {
                    hand.add(Card.gameCards[x]);
                    Card.usedCards.add(x);
                } else {
                    i--;
                }
            }
            p.setHand(hand);
        }
        UNOServer.UNO.broadcast(playerNameCommand);
        UNOServer.UNO.broadcast("Welcome to UNO!\n\t  The game is starting!");
//        UNOServer.UNO.broadcast("Every player now has his cards!");
        UNOServer.UNO.broadcast(players[0].getUsername() + " begins!");

    }
    private void stopGame(){}
    private void gameLoop(){
        UNOServer.UNO.command("begin");
        UNOServer.UNO.broadcast(players[0].getUsername());
        UNOServer.UNO.command("gameloop");

        boolean game_over = false;
        while(!game_over) {

        }
    }
}
