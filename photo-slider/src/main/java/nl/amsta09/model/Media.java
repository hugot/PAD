/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amsta09.model;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Hugo Thunnissen
 */
public abstract class Media {
	public static String DIRECTORY;

    private URL url;
    private String relativePath;
    private String name;
    private String theme;
    private int id;
    private boolean valueOnOff;

    public Media(String relativePath, String name, int id) {
        try {
            this.url = new File(relativePath).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.relativePath = relativePath;
        this.name = name;
        this.id = id;
        System.out.println(relativePath);
    }

    public Media(String relativePath, String name,int id, String theme) {
        this(relativePath, name, id);
        this.theme = theme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setURL(URL url) {
        this.url = url;
    }

    public URL getURL() {
        return url;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public int getId(){
    	return id;
    }

    public void setId(int id){
    	this.id = id;
    }
    
    public boolean getValueOnOff(){
        return valueOnOff;
    }
    
    public void setValueOnOff(boolean set){
        valueOnOff = set;
    }

    @Override
    public String toString() {
        String result;

        result = ("URL: " + getURL()
                + "\nName: " + getName()
                + "]nTheme: " + getTheme());
        return result;
    }
    
    
    
}
