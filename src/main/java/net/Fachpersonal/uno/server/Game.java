package net.Fachpersonal.uno.server;

import net.Fachpersonal.uno.utils.Card;
import net.Fachpersonal.uno.utils.Player;

import java.io.IOException;
import java.util.ArrayList;

public class Game {

    private boolean turn_clockwise;

    private int turnIndex;
    private Player[] players;
    public Game(Player[] players) throws IOException {
        this.turn_clockwise = false;
        this.turnIndex = 0;
        this.players = players;
        startGame();
        gameLoop();
        stopGame();
    }

    private void startGame() throws IOException {
        for (Player p : players) {
            ClientHandler ch = p.getCh();
            if(ch.readLine().equals("#requestPlayers")) {
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

        boolean game_over = false;
        while(!game_over) {

        }
    }
}
