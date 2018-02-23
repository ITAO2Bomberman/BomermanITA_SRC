/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ita.bombermangame;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author MichaL
 */
public class Sound {
    private String Musik;

    public Sound(String Musik) {
        this.Musik = Musik;
    }
    
    
    
        protected Clip playmusik(){
        AudioInputStream audioInputStream = null;
        Clip clip2;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(Musik));
            AudioFormat af = audioInputStream.getFormat();
            int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audio = new byte[size];
            DataLine.Info info = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);
            clip2 = (Clip) AudioSystem.getLine(info);
            clip2.open(af, audio, 0, size);
            return clip2;
            
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Charakter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Charakter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Charakter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                audioInputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(Charakter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    return null;
    }
}
