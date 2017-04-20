/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amsta09.model;

import java.net.URL;

/**
 *
 * @author Hugo Thunnissen
 */
public abstract class Media {
	private URL filePath;
	private String name;
	private String theme;

	public Media(URL filePath, String name, String theme){
		this.filePath = filePath;
		this.name = name;
		this.theme = theme;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getTheme(){
		return theme;
	}

	public void setTheme(String theme){
		this.theme = theme;
	}
    
    public URL getFilePath(){
    	return this.filePath;
    }
}
