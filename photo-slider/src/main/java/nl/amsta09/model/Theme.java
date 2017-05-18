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
    private ArrayList<Photo> photos;
    
    public Theme(String name){
        photos = new ArrayList<Photo>();
        name = this.name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        name = this.name;
    }
    
    public void addPhoto(Photo photo){
        photos.add(photo);
    }
    
    public ArrayList getPhotoList(){
        return photos;
    }
    
    public void setPhotoList(ArrayList list){
        photos = list;
    }
}
