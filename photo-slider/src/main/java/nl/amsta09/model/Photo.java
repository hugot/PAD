package nl.amsta09.model;

import java.io.File;

/**
 * Model voor een foto
 */
public class Photo extends Media {
	public static final String DIRECTORY = "Resources" + File.separator + "Foto" +  File.separator;
        
    private Audio soundeffect;
    private boolean playedSoundEffect;
    
    public Photo(String relativePath, String name, int id) {
        super(relativePath, name, id);
    }

    public Photo(String relativePath, String name, int id, String theme) {
        super(relativePath, name, id,  theme);
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
