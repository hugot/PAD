package nl.amsta09.model;

import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Model voor een foto
 */
public class Photo extends Media {
	public static final String DIRECTORY = "Resources" + "/" + "Foto" +  "/";
        
    private Audio soundeffect;
    private boolean playedSoundEffect;
    
    public Photo(String relativePath, String name, int id) {
        super(relativePath, name, id);
        
        
    }

    public Photo(String relativePath, String name, int id, String theme) {
        super(relativePath, name, id,  theme);
        
        BufferedImage readImage = null;
        try {
            readImage = ImageIO.read(new File(relativePath));
            int height = readImage.getHeight();
            int width = readImage.getWidth();
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
    
}
