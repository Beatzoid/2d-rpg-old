package main.gameobject;

import javax.imageio.ImageIO;
import java.io.IOException;

public class KeyObject extends GameObject {
    public KeyObject() {
        this.name = "Key";

        loadImage();
    }

    private void loadImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
