package main.tiles;

import main.GamePanel;
import main.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    public int[][] mapTileNum;
    public Tile[] tiles;

    GamePanel gamePanel;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tiles = new Tile[50];
        mapTileNum = new int[gamePanel.WORLD_WIDTH][gamePanel.WORLD_HEIGHT];

        getTileImages();
        loadMap("/maps/worldV2.txt");
    }

    public void getTileImages() {
        // Placeholders
        addTile(0, "grass00", false);
        addTile(1, "grass00", false);
        addTile(2, "grass00", false);
        addTile(3, "grass00", false);
        addTile(4, "grass00", false);
        addTile(5, "grass00", false);
        addTile(6, "grass00", false);
        addTile(7, "grass00", false);
        addTile(8, "grass00", false);
        addTile(9, "grass00", false);

        addTile(10, "grass00", false);
        addTile(11, "grass01", false);
        addTile(12, "water00", true);
        addTile(13, "water01", true);
        addTile(14, "water02", true);
        addTile(15, "water03", true);
        addTile(16, "water04", true);
        addTile(17, "water05", true);
        addTile(18, "water06", true);
        addTile(19, "water07", true);
        addTile(20, "water08", true);
        addTile(21, "water09", true);
        addTile(22, "water10", true);
        addTile(23, "water11", true);
        addTile(24, "water12", true);
        addTile(25, "water13", true);
        addTile(26, "road00", false);
        addTile(27, "road01", false);
        addTile(28, "road02", false);
        addTile(29, "road03", false);
        addTile(30, "road04", false);
        addTile(31, "road05", false);
        addTile(32, "road06", false);
        addTile(33, "road07", false);
        addTile(34, "road08", false);
        addTile(35, "road09", false);
        addTile(36, "road10", false);
        addTile(37, "road11", false);
        addTile(38, "road12", false);
        addTile(39, "earth", false);
        addTile(40, "wall", true);
        addTile(41, "tree", true);
    }

    public void addTile(int index, String tileName, boolean hasCollision) {
        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/new/" + tileName + ".png"));
            tiles[index].hasCollision = hasCollision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapPath) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(mapPath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;

            while (col < gamePanel.MAX_WORLD_COL && row < gamePanel.MAX_WORLD_ROW) {
                String line = bufferedReader.readLine();

                while (col < gamePanel.MAX_WORLD_COL) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if (col == gamePanel.MAX_WORLD_COL) {
                    col = 0;
                    row++;
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.MAX_WORLD_COL && worldRow < gamePanel.MAX_WORLD_ROW) {
            int tileNum = mapTileNum[worldCol][worldRow];

            Player player = gamePanel.player;
            int playerScreenX = player.screenX;
            int playerScreenY = player.screenY;
            int playerWorldX = player.worldX;
            int playerWorldY = player.worldY;

            int worldX = worldCol * gamePanel.TILE_SIZE;
            int worldY = worldRow * gamePanel.TILE_SIZE;
            int screenX = worldX - playerWorldX + playerScreenX;
            int screenY = worldY - playerWorldY + playerScreenY;

            if (worldX + gamePanel.TILE_SIZE > playerWorldX - playerScreenX &&
                    worldX - gamePanel.TILE_SIZE < playerWorldX + playerScreenX &&
                    worldY + gamePanel.TILE_SIZE > playerWorldY - playerScreenY &&
                    worldY - gamePanel.TILE_SIZE < playerWorldY + playerWorldY) {
                g2.drawImage(tiles[tileNum].image, screenX, screenY, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE, null);
            }

            worldCol++;

            if (worldCol == gamePanel.MAX_WORLD_COL) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
