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
            String read = ch.readLine();
            if (read.equals("#init")) {
                ch.write(ch.getP().toString());
                String playersStr ="";
                for (Player px : players) {
                    playersStr += px.toString()+";";

                }
                ch.write(playersStr);
                ch.write(turn_clockwise+"");
                ch.write(turnIndex+"");
            }
            do {
                read = ch.readLine();
            } while (!read.equals("#ready"));
        }
        UNOServer.UNO.broadcast("#startGame");
        UNOServer.UNO.broadcast(middle.toString());
        players[turnIndex].getCh().write("#yourTurn");
        String playersResponse = players[turnIndex].getCh().readLine();
        Card c = Card.StringToCard(playersResponse);
        middle = c;
        players[turnIndex].getHand().remove(c);
        for (int i = 0; i < Card.usedCards.size(); i++) {
            int cindex = Card.getCardIndex(c);
            if(cindex < Card.gameCards.length-9) {
                if(Card.usedCards.get(i) == cindex || Card.usedCards.get(i) == cindex+1) {
                    Card.usedCards.remove(i);
                }
            } else {
                if(Card.usedCards.get(i) == cindex || Card.usedCards.get(i) == cindex+1 || Card.usedCards.get(i) == cindex+2 || Card.usedCards.get(i) == cindex+3) {
                    Card.usedCards.remove(i);
                }
            }
        }
        UNOServer.UNO.broadcast("#"+players[turnIndex].getUsername()+"-");
    }

    private void stopGame() {
    }

    private void gameLoop() {

    }
}
