package net.Fachpersonal.uno.client;

import net.Fachpersonal.uno.exceptions.UNOException;
import net.Fachpersonal.uno.utils.BufferedImageLoader;
import net.Fachpersonal.uno.utils.Card;
import net.Fachpersonal.uno.utils.SpriteSheet;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Window extends JFrame {

    public final static int HEIGHT = 700;
    public final static int WIDTH = 700;

    public Window() throws IOException {
        this.setTitle("ASD");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame frame = new Frame();
        this.add(frame);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setIconImage(new BufferedImageLoader().loadImage("/icon.png"));
//        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new Window();
    }
}

class Frame extends JComponent {
    private int[] player0;
    private int[] player1;
    private int[] player2;

    private int[] middle;
    private int[] deck;

    private final int CARD_WINDOW_OFFSET = 12;
    private final int CARD_SIZE_INDEX = 2;
    private SpriteSheet spriteSheet;

    public Frame() {
        init();
        player0 = new int[]{
                Window.WIDTH / 2,
                (Window.HEIGHT - (spriteSheet.grabImage(0, 0).getHeight() * (CARD_SIZE_INDEX + 1))) - CARD_WINDOW_OFFSET
        };
        player1 = new int[]{
                Window.WIDTH / 2,
                CARD_WINDOW_OFFSET
        };
        player2 = new int[]{
                (int) (Window.WIDTH - (spriteSheet.grabImage(0, 0).getHeight() * (CARD_SIZE_INDEX + 0.5))) - CARD_WINDOW_OFFSET,
                Window.HEIGHT / 2
        };
        middle = new int[]{
                Window.WIDTH / 2,
                Window.HEIGHT / 2
        };
        deck = new int[]{
                CARD_WINDOW_OFFSET,
                Window.HEIGHT / 2
        };
    }

    public void init() {
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            BufferedImage ss = loader.loadImage("/default.png");
            spriteSheet = new SpriteSheet(ss);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(drawScreen(Client.client.game),0,0,this);
    }

    private BufferedImage drawScreen(Game game) {
        BufferedImage screen = new BufferedImage(Window.WIDTH, Window.HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = screen.createGraphics();

        g.drawLine(Window.WIDTH / 2, 0, Window.WIDTH / 2, Window.HEIGHT);
        g.drawLine(0, Window.HEIGHT / 2, Window.WIDTH, Window.HEIGHT / 2);
//        g.drawImage(spriteSheet.grabImage(0,0),player0[0],player0[1],this);
//        g.drawImage(spriteSheet.grabImage(1,0),player1[0],player1[1],this);
//        g.drawImage(spriteSheet.grabImage(4,8),player2[0],player2[1],this);

//        ArrayList<Card> hand = new ArrayList<>();
//        try {
//            hand.add(new Card(Card.Color.GREEN, Card.Type.SKIP));
//            hand.add(new Card(Card.Color.SPECIAL, Card.Type.CHANGE_COLOR));
//            hand.add(new Card(Card.Color.GREEN, Card.Type.SKIP));
//            hand.add(new Card(Card.Color.GREEN, 3));
//            hand.add(new Card(Card.Color.YELLOW, 5));
//            hand.add(new Card(Card.Color.RED, Card.Type.SKIP));
//            hand.add(new Card(Card.Color.RED, Card.Type.PLUS2));
//            hand.add(new Card(Card.Color.BLUE, Card.Type.SKIP));
//            hand.add(new Card(Card.Color.SPECIAL, Card.Type.CHANGE_COLOR));
//            hand.add(new Card(Card.Color.RED, Card.Type.SKIP));
//            hand.add(new Card(Card.Color.YELLOW, 3));
//            hand.add(new Card(Card.Color.YELLOW, 5));
//            hand.add(new Card(Card.Color.YELLOW, Card.Type.SKIP));
//            hand.add(new Card(Card.Color.BLUE, Card.Type.PLUS2));
//        } catch (UNOException e) {
//            throw new RuntimeException(e);
//        }

        // ### Draw Player 0 Cards ###
        {
            int cardsPerHalf = 10;
            for (Card c : game.getPlayer0().getHand()) {
                BufferedImage oimg = resizeImage(spriteSheet.getImage(c));
                g.drawImage(oimg, player0[0] - (10 * CARD_SIZE_INDEX * cardsPerHalf), player0[1], this);
                cardsPerHalf--;
            }
        }
        // ### Draw Player 1 Cards ###
        {
            int cardsPerHalf = 10;
            BufferedImage oimg = resizeImage(spriteSheet.grabImage(0, 0));
            for (int i = 0; i < game.getPlayer1().getHand().size(); i++) {
                g.drawImage(oimg, player1[0] - (10 * CARD_SIZE_INDEX * cardsPerHalf), player1[1], this);
                cardsPerHalf--;
            }
        }
        // ### Draw Player 2 Cards ###
        {
            int cardsPerHalf = 10;

            BufferedImage oimg = rotate(resizeImage(spriteSheet.grabImage(0, 0)), -90.0);

            for (int i = 0; i < game.getPlayer1().getHand().size(); i++) {
                g.drawImage(oimg, player2[0], player2[1] - (10 * CARD_SIZE_INDEX * cardsPerHalf), this);
                cardsPerHalf--;
            }
        }
        // ### Draw middle cards ###
        {
            BufferedImage oimg = resizeImage(spriteSheet.grabImage(1, 6));
            g.drawImage(oimg, middle[0] - (oimg.getWidth() / 2), middle[1] - (oimg.getHeight() / 2), this);

        }
        // ### Draw deck of cards ###
        {
            BufferedImage oimg = resizeImage(spriteSheet.grabImage(0, 0));
            g.drawImage(oimg, deck[0], deck[1] - (oimg.getHeight() / 2), this);
        }
        g.dispose();
        return screen;
    }

    private BufferedImage resizeImage(BufferedImage oimg) {
        BufferedImage rimg = new BufferedImage(oimg.getWidth() * CARD_SIZE_INDEX, oimg.getHeight() * CARD_SIZE_INDEX, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = rimg.createGraphics();
        g2.drawImage(oimg, 0, 0, oimg.getWidth() * CARD_SIZE_INDEX, oimg.getHeight() * CARD_SIZE_INDEX, null);
        g2.dispose();
        return rimg;
    }

    public static BufferedImage rotate(BufferedImage bimg, Double angle) {
        double sin = Math.abs(Math.sin(Math.toRadians(angle))),
                cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int w = bimg.getWidth();
        int h = bimg.getHeight();
        int neww = (int) Math.floor(w * cos + h * sin),
                newh = (int) Math.floor(h * cos + w * sin);
        BufferedImage rotated = new BufferedImage(neww, newh, bimg.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.translate((neww - w) / 2, (newh - h) / 2);
        graphic.rotate(Math.toRadians(angle), w / 2, h / 2);
        graphic.drawRenderedImage(bimg, null);
        graphic.dispose();
        return rotated;
    }
}