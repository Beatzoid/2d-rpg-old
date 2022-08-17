package main.object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean hasCollision = false;
    public int worldX, worldY;

    public void draw(Graphics2D g2, GamePanel gamePanel) {
        int playerScreenX = gamePanel.player.screenX;
        int playerScreenY = gamePanel.player.screenY;
        int playerWorldX = gamePanel.player.worldX;
        int playerWorldY = gamePanel.player.worldY;

        int screenX = worldX - playerWorldX + playerScreenX;
        int screenY = worldY - playerWorldY + playerScreenY;

        if (worldX + gamePanel.TILE_SIZE > playerWorldX - playerScreenX &&
                worldX - gamePanel.TILE_SIZE < playerWorldX + playerScreenX &&
                worldY + gamePanel.TILE_SIZE > playerWorldY - playerScreenY &&
                worldY - gamePanel.TILE_SIZE < playerWorldY + playerWorldY) {
            g2.drawImage(image, screenX, screenY, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE, null);
        }
    }
}
