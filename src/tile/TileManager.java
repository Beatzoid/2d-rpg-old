package tile;

import main.GamePanel;
import main.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tiles;
    int[][] mapTileNum;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tiles = new Tile[10];
        mapTileNum = new int[gamePanel.WORLD_WIDTH][gamePanel.WORLD_HEIGHT];

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/old/grass.png"));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/old/wall.png"));

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/old/water.png"));

            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/old/earth.png"));

            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/old/tree.png"));

            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/old/sand.png"));
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
