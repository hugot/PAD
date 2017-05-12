package nl.amsta09.model;

/**
 * Model voor een foto
 */
public class Photo extends Media {


	/**
	 *
	 */
	public Photo(String relativePath, String name) {
		super(relativePath, name);
	}

	/**
	 *
	 */
	public Photo(String relativePath, String name, String theme) {
		super(relativePath, name, theme);
	}
}
