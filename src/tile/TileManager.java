package tile;

import main.GamePanel;

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
        mapTileNum = new int[gamePanel.MAX_SCREEN_COL][gamePanel.MAX_SCREEN_ROW];

        getTileImage();
        loadMap("/maps/mapTest.txt");
    }

    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/old/grass.png"));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/old/wall.png"));

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/old/water.png"));
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

            while (col < gamePanel.MAX_SCREEN_COL && row < gamePanel.MAX_SCREEN_ROW) {
                String line = bufferedReader.readLine();

                while (col < gamePanel.MAX_SCREEN_COL) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if (col == gamePanel.MAX_SCREEN_COL) {
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
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gamePanel.MAX_SCREEN_COL && row < gamePanel.MAX_SCREEN_ROW) {
            int tileNum = mapTileNum[col][row];

            g2.drawImage(tiles[tileNum].image, x, y, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE, null);

            col++;
            x += gamePanel.TILE_SIZE;

            if (col == gamePanel.MAX_SCREEN_COL) {
                col = 0;
                row++;
                x = 0;
                y += gamePanel.TILE_SIZE;
            }
        }
    }
}
