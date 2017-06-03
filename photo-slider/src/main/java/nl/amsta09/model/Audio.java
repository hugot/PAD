/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amsta09.model;

import  java.io.*;
/**
 *
 * @author Space
 */
public class Audio extends Media {
	public static final String DIRECTORY = "Resources" + "/"+ "Audio" + "/";
    
    public Audio(String relativePath, String name, int id, String theme){
        super(relativePath, name, id, theme);
    }
    
    public Audio(String relativePath, String name, int id) {
        super(relativePath, name, id);
    }
}
