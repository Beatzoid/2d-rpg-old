package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    Clip clip;
    Map<String, URL> soundURLs;

    public SoundManager() {
        soundURLs = new HashMap<>();

        loadDefaultSounds();
    }

    public void setActiveFile(String fileName) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURLs.get(fileName));
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    private void loadDefaultSounds() {
        soundURLs.put("main_theme", getClass().getResource("/sounds/BlueBoyAdventure.wav"));
        soundURLs.put("coin", getClass().getResource("/sounds/coin.wav"));
        soundURLs.put("powerup", getClass().getResource("/sounds/powerup.wav"));
        soundURLs.put("unlock", getClass().getResource("/sounds/unlock.wav"));
        soundURLs.put("fanfare", getClass().getResource("/sounds/fanfare.wav"));
    }
}
