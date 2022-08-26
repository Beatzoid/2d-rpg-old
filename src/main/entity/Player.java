package main.entity;

import main.GamePanel;
import main.KeyHandler;
import main.sound.Sounds;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Manages all player-related logic
 */
public class Player extends Entity {
    public final int screenX;
    public final int screenY;

    public int keyCount = 0;
    public int keysRequired = 3;

    private final GamePanel gamePanel;
    private final KeyHandler keyHandler;

    private int standCounter;
    private boolean moving = false;
    private int pixelCounter;

    /**
     * The Player class manages all player-related logic
     * @param gamePanel The game panel
     * @param keyHandler The key handler
     */
    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.SCREEN_WIDTH / 2 - (gamePanel.TILE_SIZE / 2);
        screenY = gamePanel.SCREEN_HEIGHT / 2 - (gamePanel.TILE_SIZE / 2);

        worldX = gamePanel.TILE_SIZE * 23;
        worldY = gamePanel.TILE_SIZE * 21;

        hitbox = new Rectangle();
        hitbox.x = 1;
        hitbox.y = 1;
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
        hitbox.width = 46;
        hitbox.height = 46;

        speed = 4;
        direction = "down";

        getPlayerImage();
    }

    public void update() {
        if (!moving) {
            // Not moving
            if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {

                if (keyHandler.upPressed) {
                    direction = "up";
                } else if (keyHandler.downPressed) {
                    direction = "down";
                } else if (keyHandler.leftPressed) {
                    direction = "left";
                } else if (keyHandler.rightPressed) {
                    direction = "right";
                }

                moving = true;

                // Check tile collision
                gamePanel.collisionChecker.checkTile(this);

                // Check object collision
                int objectIndex = gamePanel.collisionChecker.checkObject(this,true);
                interactWithObject(objectIndex);

                if (!hasCollided) {
                    switch (direction) {
                        case "up" -> worldY -= speed;
                        case "down" -> worldY += speed;
                        case "left" -> worldX -= speed;
                        case "right" -> worldX += speed;
                    }
                }

                currentSpriteImageCounter++;
                // Every 12 frames
                if (currentSpriteImageCounter > 12) {
                    if (currentSpriteNum == 1) currentSpriteNum = 2;
                    else if (currentSpriteNum == 2) currentSpriteNum = 1;

                    currentSpriteImageCounter = 0;
                }
            } else {
                standCounter++;

                if (standCounter == 20) {
                    standCounter = 0;
                    currentSpriteNum = 1;
                }
            }
        } else {
            // Moving
            if (!hasCollided) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            currentSpriteImageCounter++;
            // Every 12 frames
            if (currentSpriteImageCounter > 12) {
                if (currentSpriteNum == 1) currentSpriteNum = 2;
                else if (currentSpriteNum == 2) currentSpriteNum = 1;

                currentSpriteImageCounter = 0;
            }
        }

        pixelCounter += speed;

        if (pixelCounter == gamePanel.TILE_SIZE) {
            moving = false;
            pixelCounter = 0;
        }
    }

    public void interactWithObject(int objectIndex) {
        if (objectIndex == -1) return;

        String objectName = gamePanel.objects[objectIndex].name;

        switch (objectName.toLowerCase()) {
            case "key" -> {
                keyCount++;

                gamePanel.playSoundEffect(Sounds.COIN);

                int keysRemaining = keysRequired - keyCount;
                if (keysRemaining == 0) {
                    gamePanel.ui.showMessage("You've found all the keys! Find the castle to get the treasure!");
                } else {
                    gamePanel.ui.showMessage("You got a key! Only " + keysRemaining + " more keys to go!");
                }

                gamePanel.objects[objectIndex] = null;
            }
            case "door" -> {
                if (keyCount > 0) {
                    gamePanel.objects[objectIndex] = null;
                    keyCount--;

                    gamePanel.playSoundEffect(Sounds.UNLOCK);
                } else {
                    gamePanel.ui.showMessage("You need a key to open this door!");
                }
                System.out.println("Key Count: " + keyCount);
            }
            case "boots" -> {
                speed += 2;

                gamePanel.ui.showMessage("Speed!");
                gamePanel.playSoundEffect(Sounds.POWERUP);

                gamePanel.objects[objectIndex] = null;
            }
            case "chest" -> {
                gamePanel.ui.gameFinished = true;

                gamePanel.stopMusic();
                gamePanel.playSoundEffect(Sounds.FANFARE);
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up" -> {
                if (currentSpriteNum == 1) image = up1Img;
                if (currentSpriteNum == 2) image = up2Img;
            }

            case "down" -> {
                if (currentSpriteNum == 1) image = down1Img;
                if (currentSpriteNum == 2) image = down2Img;
            }

            case "left" -> {
                if (currentSpriteNum == 1) image = left1Img;
                if (currentSpriteNum == 2) image = left2Img;
            }

            case "right" -> {
                if (currentSpriteNum == 1) image = right1Img;
                if (currentSpriteNum == 2) image = right2Img;
            }
        }

        g2.drawImage(image, screenX, screenY, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE, null);

        // Display hitbox
        // g2.setColor(Color.red);
        // g2.drawRect(screenX + hitbox.x, screenY + hitbox.y, hitbox.width, hitbox.height);
    }

    public void getPlayerImage() {
        try {
            up1Img = ImageIO.read(getClass().getResourceAsStream("/player/walk/boy_up_1.png"));
            up2Img = ImageIO.read(getClass().getResourceAsStream("/player/walk/boy_up_2.png"));
            down1Img = ImageIO.read(getClass().getResourceAsStream("/player/walk/boy_down_1.png"));
            down2Img = ImageIO.read(getClass().getResourceAsStream("/player/walk/boy_down_2.png"));
            left1Img = ImageIO.read(getClass().getResourceAsStream("/player/walk/boy_left_1.png"));
            left2Img = ImageIO.read(getClass().getResourceAsStream("/player/walk/boy_left_2.png"));
            right1Img = ImageIO.read(getClass().getResourceAsStream("/player/walk/boy_right_1.png"));
            right2Img = ImageIO.read(getClass().getResourceAsStream("/player/walk/boy_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
