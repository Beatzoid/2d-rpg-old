package main.entity;

import main.Constants;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Stores data for players, monsters, NPCs and any other entities
 */
public class Entity {
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1Img, up2Img, down1Img, down2Img, left1Img, left2Img, right1Img, right2Img;
    public String direction;

    public int currentSpriteImageCounter = 0;
    public int currentSpriteNum = 1;
    public Rectangle hitbox = new Rectangle(0, 0, 48, 48);
    public int hitboxDefaultX, hitboxDefaultY;
    public boolean hasCollided = false;

    public int actionLockCounter = 0;

    protected String[] dialogTexts = new String[20];
    protected int dialogIndex = 0;

    protected final GamePanel gamePanel;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setAction() { }

    public void speak() {
        if (dialogTexts[dialogIndex] == null) dialogIndex = 0;

        gamePanel.ui.currentDialogText = dialogTexts[dialogIndex];
        dialogIndex++;

        switch (gamePanel.player.direction) {
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }

    public void update() {
        // NPC animation
        setAction();

        hasCollided = false;

        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkPlayer(this);

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

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        int playerScreenX = gamePanel.player.screenX;
        int playerScreenY = gamePanel.player.screenY;
        int playerWorldX = gamePanel.player.worldX;
        int playerWorldY = gamePanel.player.worldY;

        int screenX = worldX - playerWorldX + playerScreenX;
        int screenY = worldY - playerWorldY + playerScreenY;

        if (worldX + Constants.TILE_SIZE > playerWorldX - playerScreenX &&
                worldX - Constants.TILE_SIZE < playerWorldX + playerScreenX &&
                worldY + Constants.TILE_SIZE > playerWorldY - playerScreenY &&
                worldY - Constants.TILE_SIZE < playerWorldY + playerWorldY) {
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
        }
    }

    public BufferedImage getImage(String imagePath) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream( imagePath + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
