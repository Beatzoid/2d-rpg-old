package main.ui;

import main.Constants;
import main.GamePanel;
import main.GameState;
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
    Graphics2D g2;
    DecimalFormat decimalFormat;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        decimalFormat = new DecimalFormat("#0.00");

        loadFont();
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(font);
        g2.setColor(Color.WHITE);

        if (gamePanel.gameState == GameState.PLAY) {

        }

        if (gamePanel.gameState == GameState.PAUSE) drawPauseScreen();
    }

    public void drawPauseScreen() {
        g2.setFont(font.deriveFont(Font.PLAIN, 80f));

        String text = "PAUSED";

        int x = getXForCenteredText(text);
        int y = Constants.SCREEN_HEIGHT / 2;

        g2.drawString(text, x, y);
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
