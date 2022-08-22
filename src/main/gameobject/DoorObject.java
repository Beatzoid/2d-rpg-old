package main.gameobject;

import javax.imageio.ImageIO;
import java.io.IOException;

public class DoorObject extends GameObject {
    public DoorObject() {
        this.name = "Door";
        hasCollision = true;

        loadImage();
    }

    private void loadImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
