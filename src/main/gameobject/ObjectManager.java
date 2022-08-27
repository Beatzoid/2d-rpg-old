package main.gameobject;

import main.Constants;
import main.GamePanel;

public class ObjectManager {
    GamePanel gamePanel;

    public ObjectManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        gamePanel.objects[0] = new KeyObject();
        gamePanel.objects[0].worldX = 23 * Constants.TILE_SIZE;
        gamePanel.objects[0].worldY = 7 * Constants.TILE_SIZE;

        gamePanel.objects[1] = new KeyObject();
        gamePanel.objects[1].worldX = 23 * Constants.TILE_SIZE;
        gamePanel.objects[1].worldY = 40 * Constants.TILE_SIZE;

        gamePanel.objects[2] = new KeyObject();
        gamePanel.objects[2].worldX = 38 * Constants.TILE_SIZE;
        gamePanel.objects[2].worldY = 8 * Constants.TILE_SIZE;

        gamePanel.objects[3] = new DoorObject();
        gamePanel.objects[3].worldX = 10 * Constants.TILE_SIZE;
        gamePanel.objects[3].worldY = 11 * Constants.TILE_SIZE;

        gamePanel.objects[4] = new DoorObject();
        gamePanel.objects[4].worldX = 8 * Constants.TILE_SIZE;
        gamePanel.objects[4].worldY = 28 * Constants.TILE_SIZE;

        gamePanel.objects[5] = new DoorObject();
        gamePanel.objects[5].worldX = 12 * Constants.TILE_SIZE;
        gamePanel.objects[5].worldY = 22 * Constants.TILE_SIZE;

        gamePanel.objects[6] = new ChestObject();
        gamePanel.objects[6].worldX = 10 * Constants.TILE_SIZE;
        gamePanel.objects[6].worldY = 7 * Constants.TILE_SIZE;

        gamePanel.objects[7] = new BootsObject();
        gamePanel.objects[7].worldX = 37 * Constants.TILE_SIZE;
        gamePanel.objects[7].worldY = 42 * Constants.TILE_SIZE;
    }
}
