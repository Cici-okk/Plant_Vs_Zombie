package model;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * The Sound class handles audio playback for the game, including
 * playing individual sounds and looping audio clips.
 */
public class Sound {
    /**
     * Plays a sound effect from the given file path.
     * This method uses a separate thread to play the sound.
     *
     * @param strPath The path to the sound file.
     */

    //for individual wav sounds (not looped)
    //http://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
    public static synchronized void playSound(final String strPath) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clp = AudioSystem.getClip();

                    AudioInputStream aisStream =
                            AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream(strPath));


                    clp.open(aisStream);
                    clp.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }


    //for looping wav clips
    //http://stackoverflow.com/questions/4875080/music-loop-in-java

    /**
     * Creates a looping audio clip from the given file path.
     *
     * @param strPath The path to the audio file.
     * @return The Clip object that can be looped.
     */
    public static Clip clipForLoopFactory(String strPath){

        Clip clp = null;

        // this line caused the original exceptions

        try {
            AudioInputStream aisStream =
                    AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream(strPath));
            clp = AudioSystem.getClip();
            clp.open( aisStream );

        } catch (UnsupportedAudioFileException exp) {

            exp.printStackTrace();
        } catch (IOException exp) {

            exp.printStackTrace();
        } catch (LineUnavailableException exp) {

            exp.printStackTrace();

            //the next three lines were added to catch all exceptions generated
        }catch(Exception exp){
            System.out.println("error");
        }

        return clp;

    }
}
