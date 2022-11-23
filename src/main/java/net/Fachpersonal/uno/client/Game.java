package net.Fachpersonal.uno.client;

import net.Fachpersonal.uno.exceptions.UNOException;
import net.Fachpersonal.uno.utils.BufferedImageLoader;
import net.Fachpersonal.uno.utils.Card;
import net.Fachpersonal.uno.utils.Player;
import net.Fachpersonal.uno.utils.SpriteSheet;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Game {

    private Window window;
    private boolean turn_clockwise;
    private int turnIndex;
    private Player player0 = null;
    private Player player1 = null;

    private Player player2 = null;

    private Card middleCard = null;

    public Game() {
        init();
        startGame();
    }

    private void init() {
        {
            Client.client.write("#init");
            try {
                player0 = Player.StringToPlayer(Client.client.readLine());
                String[] Splayers = Client.client.readLine().split(";");
                Player[] ps = new Player[Splayers.length];
                for (int i = 0; i < ps.length; i++) {
                    ps[i] = Player.StringToPlayer(Splayers[i]);
                }
                for (int i = 0; i < ps.length; i++) {
                    if(player1 == null && ps[i] != player0){
                        player1 = ps[i];
                    } else if(player2 == null && ps[i] != player0){
                        player2 = ps[i];
                    }
                }
                turn_clockwise = Boolean.valueOf(Client.client.readLine());
                turnIndex = Integer.valueOf(Client.client.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (UNOException e) {
                throw new RuntimeException(e);
            }
        } // init players
        try {
            window = new Window();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Client.client.write("#ready");
        String line;
        do {
            try {
                line = Client.client.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while(!line.equals("#startGame"));
    }

    public Card getMiddleCard() {
        return middleCard;
    }

    private void startGame() {
        try {
            middleCard = Card.StringToCard(Client.client.readLine());
            window.frame.repaint();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UNOException e) {
            throw new RuntimeException(e);
        }
        String read;
        while(true) {
            try {
                read = Client.client.readLine();
                if(!read.equals("#yourTurn")) {
                    continue;
                }
                Card c = selectCard();
                Client.client.game.player0.getHand().remove(c);
                Client.client.write(c.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Card selectCard() {
        ArrayList<Card> hand = player0.getHand();
        final Card[] c = new Card[1];
        JFrame select = new JFrame("Select Card");
        select.setResizable(false);
        for (int i = 0; i < hand.size(); i++) {
            ImageIcon icon = new ImageIcon(window.frame.getSpriteSheet().getImage(hand.get(i)));
            JButton jb = new JButton(icon);
            jb.setBorder(BorderFactory.createEmptyBorder());
            jb.setContentAreaFilled(false);
            int finalI = i;
            jb.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    c[0] = hand.get(finalI);
                }
            });
            select.add(jb);
        }
        select.setVisible(true);
        while(true){
            if(c[0]!=null)
                return c[0];
        }
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
