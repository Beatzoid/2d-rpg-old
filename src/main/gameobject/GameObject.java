package main.gameobject;

import main.Constants;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameObject {
    public BufferedImage image;
    public String name;
    public boolean hasCollision = false;
    public int worldX, worldY;
    public Rectangle hitbox = new Rectangle(0, 0, 48, 48);
    public int hitboxDefaultX = 0;
    public int hitboxDefaultY = 0;

    public void draw(Graphics2D g2, GamePanel gamePanel) {
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
            g2.drawImage(image, screenX, screenY, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
        }
    }
}
