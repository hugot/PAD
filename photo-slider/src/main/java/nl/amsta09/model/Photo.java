package nl.amsta09.model;

/**
 * Model voor een foto
 */
public class Photo extends Media {
        
    private Audio soundeffect;
    private int id;
    
    public Photo(String relativePath, String name, int id) {
        super(relativePath, name);
        id = this.id;
    }

    public Photo(String relativePath, String name, int id, String theme) {
        super(relativePath, name, theme);
        id = this.id;
    }
        
    public void setSoundEffect(Audio soundObject){
        soundeffect = soundObject;
    }
    
    public Audio getSoundEffect(){
        return soundeffect;
    }
    
    public void setId(int id){
        id = this.id;
    }
    
    public int getId(){
        return id;
    }
}
