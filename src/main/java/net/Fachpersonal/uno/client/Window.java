package net.Fachpersonal.uno.client;

import net.Fachpersonal.uno.utils.BufferedImageLoader;
import net.Fachpersonal.uno.utils.SpriteSheet;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Window extends JFrame {
    public Window() throws IOException {
        this.setTitle("ASD");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame frame = new Frame();
        this.add(frame);
        this.setSize(800,800);
        this.setIconImage(new BufferedImageLoader().loadImage("/icon.png"));
//        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new Window();
    }
}
class Frame extends JComponent {

    private final int HIGHLIGHT_SCALE_FACTOR = 2;
    private final int PLAYER_0_X = 400;
    private final int PLAYER_0_Y = 800;
    private final int PLAYER_1_X = 400;
    private final int PLAYER_1_Y = 0;
    private final int PLAYER_2_X = 800;
    private final int PLAYER_2_Y = 400;

    private SpriteSheet spriteSheet;
    public Frame() {
        init();
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
        g.drawLine(400,0,400,800);
        g.drawLine(0,400,800,400);
        g.drawImage(spriteSheet.grabImage(0,0),PLAYER_0_X,PLAYER_0_Y-100,this);
        g.drawImage(spriteSheet.grabImage(1,0),PLAYER_1_X,PLAYER_1_Y,this);
        g.drawImage(spriteSheet.grabImage(4,8),PLAYER_2_X-100,PLAYER_2_Y,this);
    }
}