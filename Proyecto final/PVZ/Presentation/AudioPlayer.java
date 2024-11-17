package Presentation;
    import javax.sound.sampled.*;
    import java.io.File;

public class AudioPlayer {

    private Clip audioClip;

    public AudioPlayer(String filepath) {
        try {
            File audioFile = new File(filepath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void playMusic() {
        if (audioClip != null) {
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            audioClip.start();
        }
    }
    public void stopMusic() {
        if (audioClip != null) {
            audioClip.stop();
        }
    }
}