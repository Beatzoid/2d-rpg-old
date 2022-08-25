package main.gameobject;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ChestObject extends GameObject {
    public ChestObject() {
        name = "Chest";
        hasCollision = true;

        loadImage();
    }

    private void loadImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
