package main.ui;

import main.Constants;
import main.GamePanel;
import main.GameState;
import main.Utils;

import java.awt.*;
import java.io.IOException;

public class UI {
    public boolean shouldDisplayMessage = false;
    public String message = "";
    public String currentDialogText = "";
    public int currentTitleScreenSelectionIndex = 0;

    GamePanel gamePanel;
    Font font;
    Graphics2D g2;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        loadFont();
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(font);
        g2.setColor(Color.WHITE);

        if (gamePanel.gameState == GameState.TITLE_STATE) drawTitleScreen();
        if (gamePanel.gameState == GameState.PAUSE) drawPauseScreen();
        if (gamePanel.gameState == GameState.DIALOG) drawDialogScreen();
    }

    public void drawDialogScreen() {
        int x = Constants.TILE_SIZE * 2;
        int y = Constants.TILE_SIZE / 2;
        int width = Constants.SCREEN_WIDTH - (Constants.TILE_SIZE * 4);
        int height = Constants.TILE_SIZE * 4;

        drawSubMenu(x, y, width, height);

        x += Constants.TILE_SIZE;
        y += Constants.TILE_SIZE;

        g2.setFont(font.deriveFont(32f));

        for (String line : currentDialogText.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawPauseScreen() {
        g2.setFont(font.deriveFont(Font.PLAIN, 80f));

        String text = "PAUSED";

        int x = getXForCenteredText(text);
        int y = Constants.SCREEN_HEIGHT / 2;

        g2.drawString(text, x, y);
    }

    public void drawTitleScreen() {
        currentTitleScreenSelectionIndex = Utils.clamp(currentTitleScreenSelectionIndex, 0, 2);

        // Semi-transparent background
        g2.setColor(new Color(0, 0, 0, 0.75f));
        g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        // Game name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96f));
        String text = "Blue Boy Adventure";
        int x = getXForCenteredText(text);
        int y = Constants.TILE_SIZE * 3;

        // Text shadow
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, y + 5);

        // Draw game name
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // Character image
        x = Constants.SCREEN_WIDTH / 2 - (Constants.TILE_SIZE * 2) / 2;
        y += Constants.TILE_SIZE * 2;

        g2.drawImage(gamePanel.player.down1Img,
                x, y, Constants.TILE_SIZE * 2, Constants.TILE_SIZE * 2,
                null);

        // Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));

        text = "New game";
        x = getXForCenteredText(text);
        y += Constants.TILE_SIZE * 3.5;
        g2.drawString(text, x, y);

        if (currentTitleScreenSelectionIndex == 0) {
            g2.drawString(">", x - Constants.TILE_SIZE, y);
        }

        text = "Load game";
        x = getXForCenteredText(text);
        y += Constants.TILE_SIZE * 1.3;
        g2.drawString(text, x, y);

        if (currentTitleScreenSelectionIndex == 1) {
            g2.drawString(">", x - Constants.TILE_SIZE, y);
        }

        text = "Quit";
        x = getXForCenteredText(text);
        y += Constants.TILE_SIZE * 1.3;
        g2.drawString(text, x, y);

        if (currentTitleScreenSelectionIndex == 2) {
            g2.drawString(">", x - Constants.TILE_SIZE, y);
        }
    }

    public void drawSubMenu(int x, int y, int width, int height) {
        // Background rectangle
        g2.setColor(new Color(0, 0, 0, 210));
        g2.fillRoundRect(x, y, width, height, 35, 35);

        // Border
        g2.setColor(Color.WHITE);
        // Stroke width of 5px
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXForCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return Constants.SCREEN_WIDTH / 2 - length / 2;
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
