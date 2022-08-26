package main.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

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
    public Rectangle hitbox;
    public int hitboxDefaultX, hitboxDefaultY;
    public boolean hasCollided = false;
}
