package main.entity;

import main.GamePanel;
import main.GameState;

import java.util.Random;

public class OldManNPC extends Entity {

    public OldManNPC(GamePanel gamePanel) {
        super(gamePanel);

        direction = "down";
        speed = 1;

        dialogTexts[0] = "Hello lad!";
        dialogTexts[1] = "I've heard that you've come to this island to find \nthe long-lost treasure";
        dialogTexts[2] = "I used to be a great wizard, but now... I'm afraid \nI'm too old to go on adventures";
        dialogTexts[3] = "Anyway, good luck on your quest!";

        getNPCImages();
    }

    public void speak() {
        super.speak();
    }

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 120) {
            Random random = new Random();
            // Random number from 1-100
            int i = random.nextInt(100) + 1;

            // 25% chance per direction
            if (i < 25) {
                direction = "up";
            }

            if (i > 25 && i <= 50) {
                direction = "down";
            }

            if (i > 50 && i <= 75) {
                direction = "left";
            }

            if (i > 75 && i <= 100) {
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }

    public void getNPCImages() {
        up1Img = getImage("/NPCs/oldman_up_1");
        up2Img = getImage("/NPCs/oldman_up_2");
        down1Img = getImage("/NPCs/oldman_down_1");
        down2Img = getImage("/NPCs/oldman_down_2");
        left1Img = getImage("/NPCs/oldman_left_1");
        left2Img = getImage("/NPCs/oldman_left_2");
        right1Img = getImage("/NPCs/oldman_right_1");
        right2Img = getImage("/NPCs/oldman_right_2");
    }
}
