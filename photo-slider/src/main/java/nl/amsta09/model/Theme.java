/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amsta09.model;

import java.util.ArrayList;

/**
 *
 * @author Space
 */
public class Theme {
    
    private String name;
    private boolean active;
    private int id;
    private ArrayList<Photo> photos;
    private ArrayList<Audio> musics;
    private Audio music;
    
    public Theme(String name, int id){
        photos = new ArrayList<Photo>();
        this.name = name;
        this.id = id;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public boolean getActive(){
        return active;
    }
    
    public void setActive(boolean set){
        active = set;
    }
    
    public void addPhoto(Photo photo){
        photos.add(photo);
    }
    
    public ArrayList<Photo> getPhotoList(){
        return photos;
    }
    
    public void setPhotoList(ArrayList<Photo> list){
        photos = list;
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public Audio getMusic(){
        return music;
    }
    
    public void setMusic(Audio music){
        this.music = music;
    }
    
    public ArrayList<Audio> getMusicList(){
        return musics;
    }
    
    public void setMusicList(ArrayList<Audio> list){
        musics = list;
    }
}
