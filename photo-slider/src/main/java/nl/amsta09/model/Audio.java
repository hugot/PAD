package nl.amsta09.model;

import java.net.URL;
import javafx.scene.media.AudioClip;

/**
 * Met deze class kan een Audio object aangemaakt worden
 * 
 * @author Hugo Thunnissen
 */
public class Audio extends Media{
	private AudioClip clip;

	/**
	 *
	 */
	public Audio(URL url, String name) {
		super(url, name);
	}

	/**
	 *
	 */
	public Audio(URL url, String name, String theme, AudioClip clip) {
		super(url, name, theme);
		this.clip = clip;
	}

	public AudioClip getClip(){
		return clip;
	}

	public void setClip(AudioClip clip){
		this.clip = clip;
	}
}
