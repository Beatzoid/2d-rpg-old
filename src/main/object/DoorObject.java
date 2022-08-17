package main.object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class DoorObject extends SuperObject {
    public DoorObject() {
        this.name = "Door";

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
