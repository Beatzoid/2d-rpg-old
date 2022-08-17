package main;

import main.entity.Player;
import main.object.ObjectManager;
import main.object.SuperObject;
import main.tiles.TileManager;

import javax.swing.*;
import java.awt.*;

/**
 * Main game logic
 */
public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    public final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
    public final int SCALE = 3;
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48x48 tile
    public final int MAX_SCREEN_COL = 16; // 16 tiles horizontally
    public final int MAX_SCREEN_ROW = 12; // 12 tiles vertically
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 pixels
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 pixels
    public final int FPS = 60; // 60 frames per second
    public final int MAX_WORLD_COL = 50;
    public final int MAX_WORLD_ROW = 50;
    public final int WORLD_WIDTH = TILE_SIZE * MAX_WORLD_COL;
    public final int WORLD_HEIGHT = TILE_SIZE * MAX_WORLD_ROW;

    public CollisionChecker collisionChecker;
    public Player player;
    public TileManager tileManager;
    public KeyHandler keyHandler;
    public Thread gameThread;
    public SuperObject[] objects;
    public ObjectManager objectManager;

    /**
     * The GamePanel class manages all core game logic
     */
    public GamePanel() {
        collisionChecker = new CollisionChecker(this);
        keyHandler = new KeyHandler();
        tileManager = new TileManager(this);
        objects = new SuperObject[10];
        objectManager = new ObjectManager(this);
        player = new Player(this, keyHandler);

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setup() {
        this.objectManager.setObject();
    }

    /**
     * Start the main game thread
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (float)1000000000 / FPS; // 0.01666 seconds
        double deltaTime = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            // (currentTime - lastTime) = how much time has passed since the last frame
            deltaTime += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (deltaTime >= 1) {
                update();
                repaint();

                deltaTime--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    /**
     * Update the game
     */
    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);

        for (SuperObject obj : objects) {
            if (obj != null) {
                obj.draw(g2, this);
            }
        }

        player.draw(g2);

        g2.dispose();
    }
}

