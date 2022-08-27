package main;

import main.entity.Player;
import main.gameobject.ObjectManager;
import main.gameobject.GameObject;
import main.sound.SoundManager;
import main.sound.Sounds;
import main.tiles.TileManager;
import main.ui.UI;

import javax.swing.*;
import java.awt.*;

/**
 * Main game logic
 */
public class GamePanel extends JPanel implements Runnable {
    // System
    public CollisionChecker collisionChecker;
    public Player player;
    public TileManager tileManager;
    public KeyHandler keyHandler;
    public Thread gameThread;
    public SoundManager musicManager;
    public SoundManager soundEffectsManager;
    public UI ui;

    // Entities and objects
    public GameObject[] objects;
    public ObjectManager objectManager;

    /**
     * The GamePanel class manages all core game logic
     */
    public GamePanel() {
        ui = new UI(this);
        musicManager = new SoundManager();
        soundEffectsManager = new SoundManager();
        collisionChecker = new CollisionChecker(this);
        keyHandler = new KeyHandler();
        tileManager = new TileManager(this);
        objects = new GameObject[10];
        objectManager = new ObjectManager(this);
        player = new Player(this, keyHandler);

        this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setup() {
        this.objectManager.setObject();

        playMusic(Sounds.MAIN_THEME);
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
        double drawInterval = (float)1000000000 / Constants.FPS; // 0.01666 seconds
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

        for (GameObject obj : objects) {
            if (obj != null) {
                obj.draw(g2, this);
            }
        }

        player.draw(g2);

        ui.draw(g2);

        g2.dispose();
    }

    public void playMusic(Sounds soundName) {
        musicManager.setActiveFile(soundName);
        musicManager.play();
        musicManager.loop();
    }

    public void playSoundEffect(Sounds soundName) {
        soundEffectsManager.setActiveFile(soundName);
        soundEffectsManager.play();
    }

    public void stopMusic() {
        musicManager.stop();
    }
}

