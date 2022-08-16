package main;

import main.entity.Entity;

public class CollisionChecker {
    private GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int leftWorldX = entity.worldX + entity.hitbox.x;
        int rightWorldX = entity.worldX + entity.hitbox.x + entity.hitbox.width;
        int topWorldY = entity.worldY + entity.hitbox.y;
        int bottomWorldY = entity.worldY + entity.hitbox.y + entity.hitbox.height;

        int leftCol = leftWorldX / gamePanel.TILE_SIZE;
        int rightCol = rightWorldX / gamePanel.TILE_SIZE;
        int topRow = topWorldY / gamePanel.TILE_SIZE;
        int bottomRow = topWorldY / gamePanel.TILE_SIZE;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                topRow = (topWorldY - entity.speed) / gamePanel.TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapTileNum[leftCol][topRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[rightCol][bottomRow];

                if (gamePanel.tileManager.tiles[tileNum1].collision
                        || gamePanel.tileManager.tiles[tileNum2].collision) {
                    entity.hasCollided = true;
                }

                break;
            case "down":
                bottomRow = (bottomWorldY + entity.speed) / gamePanel.TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapTileNum[leftCol][bottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[rightCol][bottomRow];

                if (gamePanel.tileManager.tiles[tileNum1].collision
                        || gamePanel.tileManager.tiles[tileNum2].collision) {
                    entity.hasCollided = true;
                }

                break;
            case "left":
                leftCol = (leftWorldX - entity.speed) / gamePanel.TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapTileNum[leftCol][topRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[leftCol][bottomRow];

                if (gamePanel.tileManager.tiles[tileNum1].collision
                        || gamePanel.tileManager.tiles[tileNum2].collision) {
                    entity.hasCollided = true;
                }

                break;
            case "right":
                rightCol = (rightWorldX + entity.speed) / gamePanel.TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapTileNum[rightCol][topRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[rightCol][bottomRow];

                if (gamePanel.tileManager.tiles[tileNum1].collision
                        || gamePanel.tileManager.tiles[tileNum2].collision) {
                    entity.hasCollided = true;
                }

                break;
        }
    }
}
