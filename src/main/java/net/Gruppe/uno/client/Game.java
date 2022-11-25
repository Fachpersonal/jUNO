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

/**
 * Klasse die Spiel funktionalitäten beinhaltet
 */
public class Game {

    private Window window;
    private boolean turn_clockwise;
    private int turnIndex;
    private Player player0 = null;
    private Player player1 = null;
    private Player player2 = null;
    private Card middleCard = null;

    /**
     * Konstruktor der das Spiel initialisiert
     */
    public Game() {
        init();
    }

    /**
     * Methode die das Spiel initialisiert
     */
    private void init() {
        try {
            /** Synchronisation zwischen dem Spiel Objekt auf dem Server und dem Spiel Objekt auf dem Client */
            {
                /** Der Benutzer schickt ein "#init" an den Server um diesem zu zeigen, das der Nutzer bereit ist das Spiel zu initialisieren */
                Client.client.write("#init");

                /** Wartet auf die Antwort des Servers und die erwartete Antwort sollte ein Object des Spielers sein, die auf dem Server erstellt wurde */
                String x = Client.client.readLine();
                player0 = Player.StringToPlayer(x);

                /** Selbes prinzip wie oben, nur das diesmal alle Spieler angefordert werden.
                 *  Dies dient damit jeder Client weißt gegen wie viele Spieler man spielt */
                x = Client.client.readLine();
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

                /** Hier findet die Synchronisierung aller anderer wichtigen Variablen, die für das Spiel wichtig sind, statt */
                x = Client.client.readLine();
                turn_clockwise = Boolean.parseBoolean(x);
                x = Client.client.readLine();
                turnIndex = Integer.parseInt(x);

            }

            /** Nachdem alles Synchronisiert wurde, wird nun das Fenster erstellt */
            window = new Window();

            /** Es wird so lange gelesen bis der Server alle anderen Spieler synchronisiert und das "#startGame" commando los schickt */
            String line;
            do {
                line = Client.client.readLine();

            } while (!line.equals("#startGame"));
        } catch (IOException | UNOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Methode die das lokale Spiel startet.
     * Diese Methode wird beim initialisierung Prozess, erst nach der synchronisierung ausgeführt
     */
    public void startGame() {
        try {
            /** Sobald das Spiel startet, schickt der Server an alle welche Karte von anfang an in der Mitte liegt.
             *  Diese wird ausgelesen und das Fenster wird mit der repaint() funktion neu gezeichnet */
            String karte = Client.client.readLine();
            middleCard = Card.StringToCard(karte);
            System.out.println(karte);
            window.frame.repaint();
        } catch (IOException | UNOException e) {
            throw new RuntimeException(e);
        }
        String read;
        while (true) {
            try {
                read = Client.client.readLine();

                /** Wenn "#sync" gelesen wird, wird das lokale Spiel synchronisiert.
                 *  Dabei wird die anzahl an Karten die anderen Spieler haben aktualisiert
                 *  sowie als auch die Variablen wer als Nächstes dran ist als auch die Variable die angibt in welche richtung es weiter geht
                 */
                if(read.equals("#sync")) {
                    read = Client.client.readLine();
                    if (read.equals("#" + player1.getUsername() + "-")) {
                        player1.getHand().remove(0);
                    } else if (read.equals("#" + player2.getUsername() + "-")) {
                        player2.getHand().remove(0);
                    }
                    read = Client.client.readLine();
                    turnIndex = Integer.parseInt(read);
                    read = Client.client.readLine();
                    turn_clockwise = Boolean.valueOf(read);
                    read = Client.client.readLine();
                    middleCard = Card.StringToCard(read);
                }
                else if (!read.equals("#yourTurn")) {
                    continue;
                }

                /** DER PART DER NICHT FUNKTIONIERT */
                /** Der Spieler, der dran ist, soll eins seiner Karten auswählen.
                 *  Die auserwählte Karte wird geprüft und sollte diese Karte nicht gelegt werden so wiederhole diesen Prozess */
                Card c;
                do {
                    selectCard();
                    Thread.sleep(5000);
                    if(_temp == null) {
                        // TODO: Penalty
                    }
                    c = _temp;
                    Client.console(c.toString() + " AND NOW THE CHECK!");
                    if(checkCard(c)) {
                        break;
                    }
                } while(true);

                /** Sobald diese Karte gelegt wird, so wird diese aus der Hand des Nutzers gelöscht.
                 *  Dem Server wird die gelegte Karte nun geschickt. */
                Client.client.game.player0.getHand().remove(c);
                Client.client.write(c.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (UNOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Hier findet die Prüfung statt ob die gegebene Karte $c der Farbe oder der Nummer entspricht
     */
    private boolean checkCard(Card c) {
        if(c.getColor() != middleCard.getColor()) {
            Client.console("COLLAR AINT RIGHT");
            if(c.getIndex() != middleCard.getIndex()) {
                return false;
            }
            return true;
        }
        Client.console("COLLAR RIGHT!");
        return true;

    }

    Card _temp;
    /**
     * Diese Methode wird von den Buttons die Auswahl ermöglichen verwendet, um temporär eine Karte zu setzen, anhand des übergebenden Textes
     */
    private Card setTempCard(String str) {
        try {
            _temp = Card.StringToCard(str);
            Client.console("_temp="+_temp);
            return _temp;
        } catch (UNOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Diese Methode öffnet ein externes Fenster und zeigt alle verfügbaren Karten an.
     * Jeder der angezeigten Karten ist ein Knopf der gedrückt werden kann, der die Karte auswählt
     */
    private void selectCard() {
        /** Die anzuzeigende Hand */
        ArrayList<Card> hand = player0.getHand();

        /** Erstellung des Fensters */
        JFrame select = new JFrame("Select Card");
        select.setResizable(false);
        select.setLayout(new FlowLayout());
        select.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        /** Erstellung eines Panels das die Buttons beinhaltet */
        JPanel jp = new JPanel();

        /** Eine HashMap die es erlaubt die dynamischen Buttons zu speichern und ansprechbar zu machen */
        HashMap<String,JButton> jButtons = new HashMap<>();

        /** Hier wird festgelegt wie die Orientierung der Componenten festgelegt*/
        jp.setLayout(new FlowLayout());
        jp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        /** Hier werden die Buttons erstellt mit dem dazugehörigen Karten-Bild */
        for (int i = 0; i < hand.size(); i++) {
            ImageIcon icon = new ImageIcon(window.frame.getSpriteSheet().getImage(hand.get(i)));
            JButton jb = new JButton(icon);

            /** Speicherung des dynamischen Buttons */
            jButtons.put(hand.get(i).toString(),jb);

            /** Hier wird dafür gesorgt das die Buttons keinen Rand haben und das Bild denn ganzen Button belegt */
            jb.setBorder(BorderFactory.createEmptyBorder());
            jb.setContentAreaFilled(false);

            /** Hier wird ein finaler Integer erstellt der vom MouseAdapter genutzt wird */
            int finalI = i;

            /** Es wird ein Listener hinzugefügt der prüft ob die Maus auf dem Button betätigt wurde */
            jb.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    Client.console("CLICKED DA MOUSE " + hand.get(finalI).toString());

                    /** Hier wird geprüft, ob die angeklickte Karte eine Plus 4 oder Farbwechsel ist */
                    if(hand.get(finalI).getColor() == Card.Color.SPECIAL) {
                        try {
                            /** Ist dies der Fall dann wird ein neues Fenster geöffnet das die 4 Farboptionen anzeigt */
                            chooseSpecialCard(hand.get(finalI));
                        } catch (UNOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    /** Ausgabe der ausgewählten Karte */
                    setTempCard(hand.get(finalI).toString());
                    select.dispose();
                }
            });

            /** Hiermit wird der Button dem davor erstelltem Panel hinzugefügt */
            jp.add(jb);
            jb.setVisible(true);
        }

        /** Hiermit wird das davor erstellte Panel mit all seinem Inhalt dem Fenster hinzugefügt.
         *  Der ganze Inhalt des Fensters wird zusammen gepackt und wird zu guter Letzt angezeigt */
        select.add(jp);
        select.pack();
        select.setVisible(true);
    }

    /**
     * Diese Methode erstellt ein neues Fenster welches die Farbauswahl er Karten Plus4 und Farbe wechsel beinhaltet.
     * (Selber Aufbau wie selectCard())
     */
    private Card chooseSpecialCard(Card c) throws UNOException {
        /** Fenster, Panel und HashMap werden erstellt */
        JFrame select = new JFrame("Choose Special Card");
        select.setResizable(false);
        select.setLayout(new FlowLayout());
        select.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        JPanel jp = new JPanel();
        HashMap<JButton,String> jButtons = new HashMap<>();
        jp.setLayout(new FlowLayout());
        jp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        int row;

        /** Es wird geprüft um welche Karte es sich handelt und dem entsprechend wird die zeile festgelegt von den Karten (für StyleSheet) */
        if(c.getType() == Card.Type.PLUS4) {
            row = 13;
        } else {
            row = 12;
        }

        /** Erstellen der Buttons (bzw. Karten) */
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
                    Client.console("CLICKED DA SPECIALE MOUSE " + jButtons.get(jb));
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

        /** Prüfung, ob die temporäre Karte richtig gesetzt wurde. Die Karte wird dann ausgegeben */
        while (true) {
            if (_temp != null) {
                return _temp;
            }
        }
    }

    /**
     * Methode um das Lokale Spiel als beendet zu erklären
     */
    private void stopGame() {

    }

    /** ### GETTER UND SETTER ### */

    public Player getPlayer0() {
        return player0;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Card getMiddleCard() {
        return middleCard;
    }
}