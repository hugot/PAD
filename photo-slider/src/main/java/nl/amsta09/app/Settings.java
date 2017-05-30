/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amsta09.app;

/**
 *
 * @author Space
 */
public class Settings {
    
    private boolean soundOn;
    private boolean automaticSlider;
    private boolean buttonWorking;
    private float timer;
    
    public boolean getSound(){
        return soundOn;
    }
    
    public boolean getAutoSlider(){
        return automaticSlider;
    }
    
    public boolean getButton(){
        return buttonWorking;
    }
    
    public float getTimer(){
        return timer;
    }
    
    public void setSound(boolean set){
        soundOn = set;
    }
    
    public void setAutoSlider(boolean set){
        automaticSlider = set;
    }
    
    public void setButton(boolean set){
        buttonWorking = set;
    }
    
    public void setTimer(float time){
        //returns the timer in frames instead of seconds.
        timer = time * 60;
    }
}
