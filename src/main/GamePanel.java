package main;

import main.entity.Entity;
import main.entity.Player;
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
    public AssetManager assetManager;
    public Entity[] npcs;

    public GameState gameState;

    /**
     * The GamePanel class manages all core game logic
     */
    public GamePanel() {
        ui = new UI(this);
        musicManager = new SoundManager();
        soundEffectsManager = new SoundManager();
        collisionChecker = new CollisionChecker(this);
        keyHandler = new KeyHandler(this);
        tileManager = new TileManager(this);
        objects = new GameObject[10];
        assetManager = new AssetManager(this);
        player = new Player(this, keyHandler);
        npcs = new Entity[10];

        this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setup() {
        assetManager.setupObjects();
        assetManager.setupNPCs();

        playMusic(Sounds.MAIN_THEME);

        gameState = GameState.PLAY;
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
        if (gameState == GameState.PLAY) {
            player.update();
            for (Entity npc : npcs) {
                if (npc != null) {
                    npc.update();
                }
            }
        }

        if (gameState == GameState.PAUSE) {
            // TODO: Pause State
        }
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

        for (Entity npc : npcs) {
            if (npc != null) {
                npc.draw(g2);
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

