package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();

        // Close the window when the "X" (close) button is pressed
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Adventure");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        // Window will be displayed at the center of the screen
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
