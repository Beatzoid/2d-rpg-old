package main.gameobject;

import main.GamePanel;

public class ObjectManager {
    GamePanel gamePanel;

    public ObjectManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        gamePanel.objects[0] = new KeyObject();
        gamePanel.objects[0].worldX = 23 * gamePanel.TILE_SIZE;
        gamePanel.objects[0].worldY = 7 * gamePanel.TILE_SIZE;

        gamePanel.objects[1] = new KeyObject();
        gamePanel.objects[1].worldX = 23 * gamePanel.TILE_SIZE;
        gamePanel.objects[1].worldY = 40 * gamePanel.TILE_SIZE;

        gamePanel.objects[2] = new KeyObject();
        gamePanel.objects[2].worldX = 38 * gamePanel.TILE_SIZE;
        gamePanel.objects[2].worldY = 8 * gamePanel.TILE_SIZE;

        gamePanel.objects[3] = new DoorObject();
        gamePanel.objects[3].worldX = 10 * gamePanel.TILE_SIZE;
        gamePanel.objects[3].worldY = 11 * gamePanel.TILE_SIZE;

        gamePanel.objects[4] = new DoorObject();
        gamePanel.objects[4].worldX = 8 * gamePanel.TILE_SIZE;
        gamePanel.objects[4].worldY = 28 * gamePanel.TILE_SIZE;

        gamePanel.objects[5] = new DoorObject();
        gamePanel.objects[5].worldX = 12 * gamePanel.TILE_SIZE;
        gamePanel.objects[5].worldY = 22 * gamePanel.TILE_SIZE;

        gamePanel.objects[6] = new ChestObject();
        gamePanel.objects[6].worldX = 10 * gamePanel.TILE_SIZE;
        gamePanel.objects[6].worldY = 7 * gamePanel.TILE_SIZE;
    }
}
