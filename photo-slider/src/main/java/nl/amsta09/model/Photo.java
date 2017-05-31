package nl.amsta09.model;

import java.io.File;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

/**
 * Model voor een foto
 */
public class Photo extends Media {
	public static final String DIRECTORY = "Resources" + File.separator + "Foto" +  File.separator;
        
    private Audio soundeffect;
    private boolean playedSoundEffect;
    private int width;
    private int height;
    
    public Photo(String relativePath, String name, int id) {
        super(relativePath, name, id);
        
        BufferedImage readImage = null;
        try {
            readImage = ImageIO.read(new File(relativePath));
            height = readImage.getHeight();
            width = readImage.getWidth();
            correctPhotoResolution();
        } catch (Exception e) {
        readImage = null;
        }
    }

    public Photo(String relativePath, String name, int id, String theme) {
        super(relativePath, name, id,  theme);
        
        BufferedImage readImage = null;
        try {
            readImage = ImageIO.read(new File(relativePath));
            int height = readImage.getHeight();
            int width = readImage.getWidth();
            correctPhotoResolution();
        } catch (Exception e) {
        readImage = null;
        }
    }
        
    public void setSoundEffect(Audio soundObject){
        soundeffect = soundObject;
    }
    
    public Audio getSoundEffect(){
        return soundeffect;
    }
    
    public void setPlayedSoundEffect(boolean set){
        playedSoundEffect = set;
    }
    
    public boolean getPlayedSoundEffect(){
        return playedSoundEffect;
    }
    
    public void correctPhotoResolution(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int)screenSize.getWidth();
        int screenHeight = (int)screenSize.getHeight();
        float imageResolutionRatio = width / height;
        float screenResolutionRatio = screenWidth / screenHeight;
        
        if(imageResolutionRatio > screenResolutionRatio){
            width = screenWidth;
            height = (int)(width / imageResolutionRatio);
        }else{
            height = screenHeight;
            width = (int)(height * imageResolutionRatio);
        }
    }
}
