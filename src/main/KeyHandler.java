package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Manages all keyboard-related events
 */
public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    private final GamePanel gamePanel;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            upPressed = true;
        }

        if (keyCode == KeyEvent.VK_S) {
            downPressed = true;
        }

        if (keyCode == KeyEvent.VK_A) {
            leftPressed = true;
        }

        if (keyCode == KeyEvent.VK_D) {
            rightPressed = true;
        }

        if (keyCode == KeyEvent.VK_P) {
            if (gamePanel.gameState == GameState.PLAY) {
                gamePanel.gameState = GameState.PAUSE;
            } else if (gamePanel.gameState == GameState.PAUSE) {
                gamePanel.gameState = GameState.PLAY;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            upPressed = false;
        }

        if (keyCode == KeyEvent.VK_S) {
            downPressed = false;
        }

        if (keyCode == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if (keyCode == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
