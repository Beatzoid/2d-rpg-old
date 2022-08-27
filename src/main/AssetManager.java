package main;

import main.entity.OldManNPC;

public class AssetManager {
    GamePanel gamePanel;

    public AssetManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setupObjects() {

    }

    public void setupNPCs() {
        gamePanel.npcs[0] = new OldManNPC(gamePanel);
        gamePanel.npcs[0].worldX = Constants.TILE_SIZE * 21;
        gamePanel.npcs[0].worldY = Constants.TILE_SIZE * 21;
    }
}
