package main;

import main.entity.Entity;
import main.gameobject.GameObject;

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

        int leftCol = leftWorldX / Constants.TILE_SIZE;
        int rightCol = rightWorldX / Constants.TILE_SIZE;
        int topRow = topWorldY / Constants.TILE_SIZE;
        int bottomRow = topWorldY / Constants.TILE_SIZE;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up" -> {
                topRow = (topWorldY - entity.speed) / Constants.TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapTileNum[leftCol][topRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[rightCol][bottomRow];

                if (gamePanel.tileManager.tiles[tileNum1].hasCollision
                        || gamePanel.tileManager.tiles[tileNum2].hasCollision) {
                    entity.hasCollided = true;
                }
            }
            case "down" -> {
                bottomRow = (bottomWorldY + entity.speed) / Constants.TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapTileNum[leftCol][bottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[rightCol][bottomRow];

                if (gamePanel.tileManager.tiles[tileNum1].hasCollision
                        || gamePanel.tileManager.tiles[tileNum2].hasCollision) {
                    entity.hasCollided = true;
                }
            }
            case "left" -> {
                leftCol = (leftWorldX - entity.speed) / Constants.TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapTileNum[leftCol][topRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[leftCol][bottomRow];

                if (gamePanel.tileManager.tiles[tileNum1].hasCollision
                        || gamePanel.tileManager.tiles[tileNum2].hasCollision) {
                    entity.hasCollided = true;
                }
            }
            case "right" -> {
                rightCol = (rightWorldX + entity.speed) / Constants.TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapTileNum[rightCol][topRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[rightCol][bottomRow];

                if (gamePanel.tileManager.tiles[tileNum1].hasCollision
                        || gamePanel.tileManager.tiles[tileNum2].hasCollision) {
                    entity.hasCollided = true;
                }
            }
        }
    }

    public int checkObject(Entity entity, boolean isPlayer) {
        int objectIndex = -1;
//
        for (int i = 0; i < gamePanel.objects.length; i++) {
            GameObject gameObject = gamePanel.objects[i];
            if (gameObject != null) {
//                // Get entity's hitbox position
                entity.hitbox.x = entity.worldX + entity.hitbox.x;
                entity.hitbox.y = entity.worldY + entity.hitbox.y;
//
//                // Get the game object's hitbox position
                gameObject.hitbox.x = gameObject.worldX + gameObject.hitbox.x;
                gameObject.hitbox.y = gameObject.worldY + gameObject.hitbox.y;

                // We modify the entity's hitbox y position to simulate
                // them moving, so we get where they'll be and not
                // where they are currently
                switch (entity.direction) {
                    case "up" -> entity.hitbox.y -= entity.speed;
                    case "down" -> entity.hitbox.y += entity.speed;
                    case "left" -> entity.hitbox.x -= entity.speed;
                    case "right" -> entity.hitbox.x += entity.speed;
                }

                if (entity.hitbox.intersects(gameObject.hitbox)) {
                    if (gameObject.hasCollision) entity.hasCollided = true;

                    if (isPlayer) objectIndex = i;
                }
//
                entity.hitbox.x = entity.hitboxDefaultX;
                entity.hitbox.y = entity.hitboxDefaultY;
//
                gameObject.hitbox.x = gameObject.hitboxDefaultX;
                gameObject.hitbox.y = gameObject.hitboxDefaultY;
            }
        }

        return objectIndex;
    }

    public int checkEntity(Entity entity, Entity[] targets) {
        int entityIndex = -1;
//
        for (int i = 0; i < targets.length; i++) {
            Entity target = targets[i];
            if (target != null) {
//                // Get entity's hitbox position
                entity.hitbox.x = entity.worldX + entity.hitbox.x;
                entity.hitbox.y = entity.worldY + entity.hitbox.y;
//
//                // Get the game object's hitbox position
                target.hitbox.x = target.worldX + target.hitbox.x;
                target.hitbox.y = target.worldY + target.hitbox.y;

                // We modify the entity's hitbox y position to simulate
                // them moving, so we get where they'll be and not
                // where they are currently
                switch (entity.direction) {
                    case "up" -> entity.hitbox.y -= entity.speed;
                    case "down" -> entity.hitbox.y += entity.speed;
                    case "left" -> entity.hitbox.x -= entity.speed;
                    case "right" -> entity.hitbox.x += entity.speed;
                }

                if (entity.hitbox.intersects(target.hitbox)) {
                    entity.hasCollided = true;
                    entityIndex = i;
                }
//
                entity.hitbox.x = entity.hitboxDefaultX;
                entity.hitbox.y = entity.hitboxDefaultY;
//
                target.hitbox.x = target.hitboxDefaultX;
                target.hitbox.y = target.hitboxDefaultY;
            }
        }

        return entityIndex;
    }

    public void checkPlayer(Entity entity) {
        // Get entity's hitbox position
        entity.hitbox.x = entity.worldX + entity.hitbox.x;
        entity.hitbox.y = entity.worldY + entity.hitbox.y;
//
//                // Get the game object's hitbox position
        gamePanel.player.hitbox.x = gamePanel.player.worldX + gamePanel.player.hitbox.x;
        gamePanel.player.hitbox.y = gamePanel.player.worldY + gamePanel.player.hitbox.y;

        // We modify the entity's hitbox y position to simulate
        // them moving, so we get where they'll be and not
        // where they are currently
        switch (entity.direction) {
            case "up" -> entity.hitbox.y -= entity.speed;
            case "down" -> entity.hitbox.y += entity.speed;
            case "left" -> entity.hitbox.x -= entity.speed;
            case "right" -> entity.hitbox.x += entity.speed;
        }

        if (entity.hitbox.intersects(gamePanel.player.hitbox)) {
            entity.hasCollided = true;
        }
//
        entity.hitbox.x = entity.hitboxDefaultX;
        entity.hitbox.y = entity.hitboxDefaultY;
//
        gamePanel.player.hitbox.x = gamePanel.player.hitboxDefaultX;
        gamePanel.player.hitbox.y = gamePanel.player.hitboxDefaultY;
    }
}
