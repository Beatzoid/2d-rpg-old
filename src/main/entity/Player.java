package main.entity;

import main.Constants;
import main.GamePanel;
import main.GameState;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Manages all player-related logic
 */
public class Player extends Entity {
    public final int screenX;
    public final int screenY;

    private final GamePanel gamePanel;
    private final KeyHandler keyHandler;

    private int standCounter;
    private boolean isMoving = false;
    private int pixelCounter;

    /**
     * The Player class manages all player-related logic
     * @param gamePanel The game panel
     * @param keyHandler The key handler
     */
    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);

        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = Constants.SCREEN_WIDTH / 2 - (Constants.TILE_SIZE / 2);
        screenY = Constants.SCREEN_HEIGHT / 2 - (Constants.TILE_SIZE / 2);

        worldX = Constants.TILE_SIZE * 23;
        worldY = Constants.TILE_SIZE * 21;

        hitbox = new Rectangle();
        hitbox.x = 1;
        hitbox.y = 1;
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
        hitbox.width = 46;
        hitbox.height = 46;

        speed = 4;
        direction = "down";

        getPlayerImages();
    }

    public void update() {
        if (!isMoving) {
            // Not moving

            if (keyHandler.upPressed || keyHandler.downPressed
                    || keyHandler.leftPressed || keyHandler.rightPressed
            || keyHandler.enterPressed) {

                if (keyHandler.upPressed) {
                    direction = "up";
                } else if (keyHandler.downPressed) {
                    direction = "down";
                } else if (keyHandler.leftPressed) {
                    direction = "left";
                } else if (keyHandler.rightPressed) {
                    direction = "right";
                }

                isMoving = true;

                // Check tile collision
                hasCollided = false;
                gamePanel.collisionChecker.checkTile(this);

                // Check object collision
                int objectIndex = gamePanel.collisionChecker.checkObject(this,true);
                interactWithObject(objectIndex);

                // Check NPC collision
                int npcIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.npcs);
                interactWithNPC(npcIndex);
            } else {
                // Transition from moving sprite to standing sprite
                // smoothly
                standCounter++;

                if (standCounter == 15) {
                    standCounter = 0;
                    currentSpriteNum = 1;
                }
            }
        }

        if (isMoving) {
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
            // Every 12 frames change the player sprite
            if (currentSpriteImageCounter > 12) {
                if (currentSpriteNum == 1) currentSpriteNum = 2;
                else if (currentSpriteNum == 2) currentSpriteNum = 1;

                currentSpriteImageCounter = 0;
            }

            // Tile grid movement
            pixelCounter += speed;

            if (pixelCounter == Constants.TILE_SIZE) {
                isMoving = false;
                pixelCounter = 0;
            }
        }
    }

    private void interactWithNPC(int npcIndex) {
        if (npcIndex == -1) return;

        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = GameState.DIALOG;
            gamePanel.npcs[npcIndex].speak();
        }
    }

    public void interactWithObject(int objectIndex) {
        if (objectIndex == -1) return;
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

        g2.drawImage(image, screenX, screenY, Constants.TILE_SIZE, Constants.TILE_SIZE, null);

        // Display hitbox
//         g2.setColor(Color.red);
//         g2.drawRect(screenX + hitbox.x, screenY + hitbox.y, hitbox.width, hitbox.height);
    }

    private void getPlayerImages() {
        up1Img = getImage("/player/walk/boy_up_1");
        up2Img = getImage("/player/walk/boy_up_2");
        down1Img = getImage("/player/walk/boy_down_1");
        down2Img = getImage("/player/walk/boy_down_2");
        left1Img = getImage("/player/walk/boy_left_1");
        left2Img = getImage("/player/walk/boy_left_2");
        right1Img = getImage("/player/walk/boy_right_1");
        right2Img = getImage("/player/walk/boy_right_2");
    }
}
