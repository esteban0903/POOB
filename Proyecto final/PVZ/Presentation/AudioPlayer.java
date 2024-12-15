
// CODIGO GENERADO POR GPT PARA LEER UN AUDIO Y REPRODUCIRLO
package Presentation;
    import java.io.File;
    import javax.sound.sampled.*;

/**
 * Handles the loading and playing of audio files for sound effects and music in the application.
 */

public class AudioPlayer {

    private Clip audioClip;

    /**
     * Constructor that initializes the AudioPlayer with a specified audio file.
     *
     * @param filepath The path to the audio file that will be loaded into the player.
     */

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

    /**
     * Plays the loaded audio clip in a continuous loop.
     * This method is typically used for background music.
     */

    public void playMusic() {
        if (audioClip != null) {
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            audioClip.start();
        }
    }

    /**
     * Stops the playback of the audio clip.
     * This method is used to stop music or long-running sound effects.
     */
    
    public void stopMusic() {
        if (audioClip != null) {
            audioClip.stop();
        }
    }

    /**
     * Plays the loaded audio clip once from the beginning.
     * This method is suitable for short sound effects.
     */

    public void playSoundOnce() {
        if (audioClip != null) {
            audioClip.setFramePosition(0); 
            audioClip.start(); 

            audioClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    audioClip.stop();
                }
            });
        }
    }
}