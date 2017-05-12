package nl.amsta09.model;

import java.net.URL;

/**
 * Model voor een foto
 */
public class Photo extends Media {


	/**
	 *
	 */
	public Photo(URL url, String name) {
		super(url, name);
	}

	/**
	 *
	 */
	public Photo(URL url, String name, String theme) {
		super(url, name, theme);
	}
}
