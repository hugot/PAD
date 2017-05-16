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

    private URL url;
    private String relativePath;
    private String name;
    private String theme;

    public Media(String relativePath, String name) {
        try {
            this.url = new File(relativePath).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.relativePath = relativePath;
        this.name = name;
    }

    public Media(String relativePath, String name, String theme) {
        this(relativePath, name);
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

    @Override
    public String toString() {
        String result;

        result = ("URL: " + getURL()
                + "\nName: " + getName()
                + "]nTheme: " + getTheme());
        return result;
    }
}
