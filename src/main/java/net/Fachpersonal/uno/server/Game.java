package net.Fachpersonal.uno.server;

import net.Fachpersonal.uno.exceptions.UNOException;
import net.Fachpersonal.uno.utils.Card;
import net.Fachpersonal.uno.utils.Player;

import java.io.IOException;
import java.util.ArrayList;

public class Game {

    private boolean turn_clockwise;

    private int turnIndex;
    private Player[] players;

    private Card middle;

    public Game(Player[] players) throws IOException, UNOException {
        this.turn_clockwise = false;
        this.turnIndex = 0;
        this.players = players;
        startGame();
        gameLoop();
        stopGame();
    }

    private void startGame() throws IOException, UNOException {
        middle = Card.getRandomCard();
        {
            for (int k = 0; k < players.length; k++) {
                ArrayList<Card> hand = new ArrayList<>();
                for (int i = 0; i < 7; i++) {
                    hand.add(Card.getRandomCard());
                }
                players[k].setHand(hand);
            }
        }
        for (Player p : players) {
            ClientHandler ch = p.getCh();
            if(ch.readLine().equals("#requestPlayers")) {
                ch.writeOBJ(ch.getP());
                ch.writeOBJ(players);
                ch.writeOBJ(turn_clockwise);
                ch.writeOBJ(turnIndex);
            }
        }
    }
    private void stopGame(){}
    private void gameLoop(){
        UNOServer.UNO.command("begin");
        UNOServer.UNO.broadcast(players[0].getUsername());
        UNOServer.UNO.command("gameloop");

    }
}
