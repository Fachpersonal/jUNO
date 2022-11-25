package net.Gruppe.uno.server;

import net.Gruppe.uno.exceptions.UNOException;
import net.Gruppe.uno.utils.Card;
import net.Gruppe.uno.utils.Player;

import java.io.IOException;
import java.util.ArrayList;

/** Server seites Game-Objekt */
public class Game {

    private boolean turn_clockwise;

    private int turnIndex;
    private Player[] players;

    private Card middle;

    /** Konstruktor vom Spiel
     * initialisiert alle Variablen mit default werten */
    public Game(Player[] players) throws IOException, UNOException {
        this.turn_clockwise = false;
        this.turnIndex = 0;
        this.players = players;
    }

    /**
     * Startet das Spiel
     */
    public void startGame() {
        UNOServer.UNO.broadcast("#initGame");
        middle = Card.getRandomCard();
        {
            /** Gibt jedem Spieler 7 Karten */
            for (int k = 0; k < players.length; k++) {
                ArrayList<Card> hand = new ArrayList<>();
                for (int i = 0; i < 7; i++) {
                    hand.add(Card.getRandomCard());
                }
                players[k].setHand(hand);
            }
        }
        {
            /** Schreibt jedem Spieler sein zugehöriges Objekt als String */
            for(Player p : players) {
                p.getCh().write(p.toString());
            }

            /** Sendet jedem Spieler die ganze Liste an spielern */
            String playersStr = "";
            for (Player px : players) {
                playersStr += px.toString() + ";";

            }

            /** Sendet die fehlenden Daten/Variablen zur synchronisation*/
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

    /** Beendet das Spiel */
    private void stopGame() {
    }

    /**
     * Hier findet die Kontrolle der Karten sowie die allgemeine Spielkontrolle statt
     */
    private void gameLoop() throws UNOException, IOException {
        while(true){
            /** Schreibt dem spieler an stelle players[turnIndex] das start Kommando "yourTurn" */
            players[turnIndex].getCh().write("#yourTurn");

            /** Liest die vom Spieler ausgewählte Karte und entfernt diese von seinem Deck */
            String playersResponse = players[turnIndex].getCh().readLine();
            Card c = Card.StringToCard(playersResponse);
            players[turnIndex].getHand().remove(c);

            /** Ersetzt die mittlere Karte und entfernt diese aus der Card.usedCard Liste */
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
            /** updated den turnIndex */
            turnIndex += (turn_clockwise?1:-1);
            if(turnIndex > 2) {
                turnIndex = 0;
            } else if (turnIndex < 0) {
                turnIndex = 2;
            }
            syncData();
        }
    }

    /**
     * Synchronisiert alle Daten
     */
    private void syncData() {
        UNOServer.UNO.broadcast("#sync");
        UNOServer.UNO.broadcast(""+players[turnIndex].getUsername()+"-");
        UNOServer.UNO.broadcast(""+turnIndex);
        UNOServer.UNO.broadcast(""+turn_clockwise);
        UNOServer.UNO.broadcast(""+middle.toString());
    }
}
