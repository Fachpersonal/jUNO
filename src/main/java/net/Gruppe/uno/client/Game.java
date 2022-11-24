package net.Gruppe.uno.client;

import net.Gruppe.uno.exceptions.UNOException;
import net.Gruppe.uno.utils.Card;
import net.Gruppe.uno.utils.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    private Window window;
    private boolean turn_clockwise;
    private int turnIndex;
    private Player player0 = null;
    private Player player1 = null;
    private Player player2 = null;
    private Card middleCard = null;
    private Client client;

    public Game(Client client) {
        this.client = client;
        init();
    };

    private void init() {
        try {
            {
                client.write("#init");

                String x = client.readLine();
                player0 = Player.StringToPlayer(x);
                x = client.readLine();
                String[] Splayers = x.split(";");
                Player[] ps = new Player[Splayers.length];
                for (int i = 0; i < ps.length; i++) {
                    ps[i] = Player.StringToPlayer(Splayers[i]);
                }
                for (int i = 0; i < ps.length; i++) {
                    if (player1 == null && ps[i] != player0) {
                        player1 = ps[i];
                    } else if (player2 == null && ps[i] != player0) {
                        player2 = ps[i];
                    }
                }
                x = client.readLine();
                turn_clockwise = Boolean.valueOf(x);
                x = client.readLine();
                turnIndex = Integer.valueOf(x);
            } // init players
            window = new Window();
            String line;
            do {
                line = Client.client.readLine();

            } while (!line.equals("#startGame"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UNOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public Card getMiddleCard() {
        return middleCard;
    }

    public void startGame() {
        try {
            middleCard = Card.StringToCard(client.readLine());
            System.out.println(middleCard.toString());
            window.frame.repaint();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UNOException e) {
            throw new RuntimeException(e);
        }
        String read;
        while (true) {
            try {
                read = client.readLine();
                if(read.equals("#sync")) {
                    read = client.readLine();
                    if (read.equals("#" + player1.getUsername() + "-")) {
                        player1.getHand().remove(0);
                    } else if (read.equals("#" + player2.getUsername() + "-")) {
                        player2.getHand().remove(0);
                    }
                    read = client.readLine();
                    turnIndex = Integer.parseInt(read);
                    read = client.readLine();
                    turn_clockwise = Boolean.valueOf(read);
                    read = client.readLine();
                    middleCard = Card.StringToCard(read);
                }

                //read = Client.client.readLine();
                else if (!read.equals("#yourTurn")) {
                    continue;
                }
                Card c;
                do {
                    selectCard();
                    Thread.sleep(5000);
                    if(_temp == null) {

                    }
                    c = _temp;
                    System.out.println(c.toString() + " AND NOW THE CHECK!");
                    if(checkCard(c)) {
                        break;
                    }
                } while(true);
                client.game.player0.getHand().remove(c);
                client.write(c.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (UNOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean checkCard(Card c) {
        if(c.getColor() != middleCard.getColor()) {
            System.out.println("COLLAR AINT RIGHT");
            if(c.getIndex() != middleCard.getIndex()) {
                return false;
            }
            return true;
        }
        System.out.println("COLLAR RIGHT!");
        return true;

    }

    Card _temp;

    private Card setTempCard(String str) {
        try {
            _temp = Card.StringToCard(str);
            System.out.println("_temp="+_temp);
            return _temp;
        } catch (UNOException e) {
            throw new RuntimeException(e);
        }
    }
    private void selectCard() {
        ArrayList<Card> hand = player0.getHand();
        JFrame select = new JFrame("Select Card");
        select.setResizable(false);
        select.setLayout(new FlowLayout());
        select.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        JPanel jp = new JPanel();
        HashMap<String,JButton> jButtons = new HashMap<>();
        jp.setLayout(new FlowLayout());
        jp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        for (int i = 0; i < hand.size(); i++) {
            ImageIcon icon = new ImageIcon(window.frame.getSpriteSheet().getImage(hand.get(i)));
            JButton jb = new JButton(icon);
            //jb.setText(hand.get(i).toString());
            jButtons.put(hand.get(i).toString(),jb);
            jb.setBorder(BorderFactory.createEmptyBorder());
            jb.setContentAreaFilled(false);
            int finalI = i;
            jb.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    System.out.println("CLICKED DA MOUSE " + hand.get(finalI).toString());
                    if(hand.get(finalI).getColor() == Card.Color.SPECIAL) {
                        try {
                            chooseSpecialCard(hand.get(finalI));
                        } catch (UNOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    setTempCard(hand.get(finalI).toString());
                    select.dispose();
                }
            });
            jp.add(jb);
            jb.setVisible(true);
        }
        select.add(jp);
        select.pack();
        select.setVisible(true);
    }

    private Card chooseSpecialCard(Card c) throws UNOException {
        JFrame select = new JFrame("Choose Special Card");
        select.setResizable(false);
        select.setLayout(new FlowLayout());
        select.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        JPanel jp = new JPanel();
        HashMap<JButton,String> jButtons = new HashMap<>();
        jp.setLayout(new FlowLayout());
        jp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        int row;
        if(c.getType() == Card.Type.PLUS4) {
            row = 13;
        } else {
            row = 12;
        }
        for (int i = 0; i < 4; i++) {
            ImageIcon icon;
            icon = new ImageIcon(window.frame.getSpriteSheet().grabImage(i+1, row));

            JButton jb = new JButton(icon);
            //jb.setText(hand.get(i).toString());
            switch (i) {
                case 0:
                    if(c.getType() == Card.Type.PLUS4) {
                        jButtons.put(jb, new Card(Card.Type.PLUS4,Card.Color.RED).toString());
                    } else {
                        jButtons.put(jb, new Card(Card.Type.CHANGE_COLOR,Card.Color.RED).toString());
                    }
                    break;
                case 1:
                    if(c.getType() == Card.Type.PLUS4) {
                        jButtons.put(jb, new Card(Card.Type.PLUS4,Card.Color.YELLOW).toString());
                    } else {
                        jButtons.put(jb, new Card(Card.Type.CHANGE_COLOR,Card.Color.YELLOW).toString());
                    }
                    break;
                case 2:
                    if(c.getType() == Card.Type.PLUS4) {
                        jButtons.put(jb, new Card(Card.Type.PLUS4,Card.Color.GREEN).toString());
                    } else {
                        jButtons.put(jb, new Card(Card.Type.CHANGE_COLOR,Card.Color.GREEN).toString());
                    }
                    break;
                case 3:
                    if(c.getType() == Card.Type.PLUS4) {
                        jButtons.put(jb, new Card(Card.Type.PLUS4,Card.Color.BLUE).toString());
                    } else {
                        jButtons.put(jb, new Card(Card.Type.CHANGE_COLOR, Card.Color.BLUE).toString());
                    }
                    break;
            }
            jb.setBorder(BorderFactory.createEmptyBorder());
            jb.setContentAreaFilled(false);
            int finalI = i;
            jb.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    System.out.println("CLICKED DA SPECIALE MOUSE " + jButtons.get(jb));
                    setTempCard(jButtons.get(jb));
                    select.dispose();
                }
            });
            jp.add(jb);
            jb.setVisible(true);
        }
        select.add(jp);
        select.pack();
        select.setVisible(true);
        while (true) {
            if (_temp != null) {
                return _temp;
            }
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
