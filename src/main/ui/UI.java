package main.ui;

import main.GamePanel;
import main.gameobject.KeyObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

public class UI {
    public boolean shouldDisplayMessage = false;
    public String message = "";
    public boolean gameFinished = false;

    GamePanel gamePanel;
    Font font;
    BufferedImage image;
    int messageTimer = 0;
    double playTime;
    DecimalFormat decimalFormat;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        decimalFormat = new DecimalFormat("#0.00");

        KeyObject key = new KeyObject();
        image = key.image;

        loadFont();
    }

    public void draw(Graphics2D g2) {
        g2.setFont(font);
        g2.setColor(Color.WHITE);

        if (gameFinished) {
            String text;
            int textLength;
            int x;
            int y;

            g2.setColor(Color.YELLOW);
            text = "Congratulations!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gamePanel.SCREEN_WIDTH / 2 - textLength / 2;
            y = gamePanel.SCREEN_HEIGHT / 2 - (gamePanel.TILE_SIZE);

            g2.drawString(text, x, y);

            g2.setColor(Color.WHITE);
            text = "You found the treasure!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gamePanel.SCREEN_WIDTH / 2 - textLength / 2;
            y = gamePanel.SCREEN_HEIGHT / 2 + (gamePanel.TILE_SIZE + 3);

            g2.drawString(text, x, y);

            text = "Time: " + decimalFormat.format(playTime);
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gamePanel.SCREEN_WIDTH / 2 - textLength / 2;
            y = gamePanel.SCREEN_HEIGHT / 2 + (gamePanel.TILE_SIZE * 5);

            g2.drawString(text, x, y);

            gamePanel.gameThread = null;
            return;
        }

        g2.drawImage(image,
                gamePanel.TILE_SIZE / 2, gamePanel.TILE_SIZE / 2,
                gamePanel.TILE_SIZE, gamePanel.TILE_SIZE,
                null);
        g2.drawString("- " + gamePanel.player.keyCount, 74, 65);

        playTime += (double)1/60;
        g2.drawString("Time: " + decimalFormat.format(playTime), gamePanel.TILE_SIZE * 11, 65);

        if (shouldDisplayMessage) {
            g2.setFont(g2.getFont().deriveFont(30f));
            g2.drawString(message, gamePanel.TILE_SIZE / 2, gamePanel.TILE_SIZE * 5);

            messageTimer++;

            // 60 seconds per frame
            // 120 = 2 seconds
            if (messageTimer > 120) {
                messageTimer = 0;

                shouldDisplayMessage = false;
            }
        }
    }

    public void showMessage(String message) {
        this.message = message;
        shouldDisplayMessage = true;
    }

    private void loadFont() {
        GraphicsEnvironment ge;

        try {
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Orange_kid.TTF")));
        } catch(FontFormatException | IOException e) {
            e.printStackTrace();
        }

        font = new Font("Orange Kid", Font.PLAIN, 55);
    }
}
