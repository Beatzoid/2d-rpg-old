package main.gameobject;

import javax.imageio.ImageIO;
import java.io.IOException;

public class BootsObject extends GameObject {
    public BootsObject() {
        name = "Boots";

        loadImage();
    }

    private void loadImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
