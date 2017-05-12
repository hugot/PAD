/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amsta09.model;

import  java.io.*;
import javax.sound.sampled.*;
/**
 *
 * @author Space
 */
public class Audio extends Media {
    private AudioInputStream audioIn;
    private Clip clip;
    
    public Audio(String relativePath, String name, String theme){
        super(relativePath, name, theme);
    }
    
    public Audio(String relativePath, String name) {
        super(relativePath, name);
    }
    
    public void playSound(){
        try{
            audioIn = AudioSystem.getAudioInputStream(super.getURL());
            clip = AudioSystem.getClip();
            clip.open(audioIn);
         clip.start();
        }catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    public void stopSound(){
        clip.stop();
    }
}
