package main.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    Clip clip;
    Map<Sounds, URL> soundURLs;

    public SoundManager() {
        soundURLs = new HashMap<>();

        loadDefaultSounds();
    }

    public void setActiveFile(Sounds soundName) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURLs.get(soundName));
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
        soundURLs.put(Sounds.MAIN_THEME, getClass().getResource("/sounds/BlueBoyAdventure.wav"));
        soundURLs.put(Sounds.COIN, getClass().getResource("/sounds/coin.wav"));
        soundURLs.put(Sounds.POWERUP, getClass().getResource("/sounds/powerup.wav"));
        soundURLs.put(Sounds.UNLOCK, getClass().getResource("/sounds/unlock.wav"));
        soundURLs.put(Sounds.FANFARE, getClass().getResource("/sounds/fanfare.wav"));
    }
}
