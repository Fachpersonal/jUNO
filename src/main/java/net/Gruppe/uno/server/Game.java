package net.Gruppe.uno.server;

import net.Gruppe.uno.exceptions.UNOException;
import net.Gruppe.uno.utils.Card;
import net.Gruppe.uno.utils.Player;

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
    }

    public void startGame() {
        UNOServer.UNO.broadcast("#initGame");
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
        {
            for(Player p : players) {
                p.getCh().write(p.toString());
            }
            String playersStr = "";
            for (Player px : players) {
                playersStr += px.toString() + ";";

            }
            UNOServer.UNO.broadcast(playersStr);
            UNOServer.UNO.broadcast(turn_clockwise+"");
            UNOServer.UNO.broadcast(turnIndex+"");
        }
        UNOServer.UNO.broadcast("#startGame");
        UNOServer.UNO.broadcast(middle.toString());

        try {
            gameLoop();
            stopGame();
        } catch (UNOException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void stopGame() {
    }

    private void gameLoop() throws UNOException, IOException {
        while(true){
            players[turnIndex].getCh().write("#yourTurn");
            String playersResponse = players[turnIndex].getCh().readLine();
            Card c = Card.StringToCard(playersResponse);
            players[turnIndex].getHand().remove(c);
            for (int i = 0; i < Card.usedCards.size(); i++) {
                int cindex = Card.getCardIndex(middle);
                if(middle.getColor() != Card.Color.SPECIAL) {
                    if(Card.usedCards.get(i) == cindex || Card.usedCards.get(i) == cindex+1) {
                        Card.usedCards.remove(i);
                    }
                } else {
                    if(Card.usedCards.get(i) == cindex || Card.usedCards.get(i) == cindex+1 || Card.usedCards.get(i) == cindex+2 || Card.usedCards.get(i) == cindex+3) {
                        Card.usedCards.remove(i);
                    }
                }
            }
            middle = c;
            turnIndex += (turn_clockwise?1:-1);
            if(turnIndex > 2) {
                turnIndex = 0;
            } else if (turnIndex < 0) {
                turnIndex = 2;
            }
            syncData();
        }
    }

    private void syncData() {
        UNOServer.UNO.broadcast("#sync");
        UNOServer.UNO.broadcast(""+players[turnIndex].getUsername()+"-");
        UNOServer.UNO.broadcast(""+turnIndex);
        UNOServer.UNO.broadcast(""+turn_clockwise);
        UNOServer.UNO.broadcast(""+middle.toString());
    }
}
