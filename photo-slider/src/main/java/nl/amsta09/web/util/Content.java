package nl.amsta09.web.util;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import nl.amsta09.model.Photo;
import nl.amsta09.model.Theme;
import nl.amsta09.web.html.HtmlButton;
import nl.amsta09.web.html.HtmlElementInterface;
import nl.amsta09.web.html.HtmlForm;
import nl.amsta09.web.html.HtmlImage;
import nl.amsta09.web.html.HtmlList;
	
/**
 * Deze class dient voor het beheren van de content die als gevolg van een httpreqeust
 * wordt opgestuurd naar de gebruiker in een response.
 * 
 * @author Hugo Thunnissen
 */
public class Content {
	private HttpServletRequest request;

	/**
	 * Instantieer de content.
	 * @param request
	 * @param response
	 */
	public Content(RequestWrapper request){
		this.request = request;
	}

	/**
	 * Voeg een attribuut toe aan de content.
	 * @param attribute
	 * @param value
	 */
	public void add(String attribute, String value){
		request.setAttribute(attribute, value);
	}

	/**
	 * Voeg een html element toe aan de content.
	 * @param attribute
	 * @param element
	 */
	public void add(String attribute, HtmlElementInterface element){
		request.setAttribute(attribute, element.generateHtml());
	}

	/**
	 * Voeg alle attributen aan een request to, en forward deze vervolgens naar de response.
	 * @param attributes
	 */
	public void addAllElements(Hashtable<String,HtmlElementInterface> attributes){
		attributes.forEach((String attribute, HtmlElementInterface value) -> {
			add(attribute, value);
		});
	}

	/**
	 * Voeg alle attributen aan een request to, en forward deze vervolgens naar de response.
	 * @param attributes
	 */
	public void addAll(Hashtable<String,String> attributes){
		attributes.forEach((String attribute, String value) -> {
			add(attribute, value);
		});
	}

	/**
	 * Genereer een html lijst van themes en voeg deze aan de content toe.
	 * @param themes
	 */
	public void addThemeList(ArrayList<Theme> themes, String formAction){
		HtmlList themeList = new HtmlList();
		themes.listIterator().forEachRemaining((Theme theme)-> {
			themeList.addItem(new HtmlForm("" + theme.getId(), "theme", "post", formAction)
					.addInput("hidden", "themeName", theme.getName())
					.addInput("hidden", "selectedThemeId", "" + theme.getId())
					.addElement(new HtmlButton("select-theme-button", "submit", theme.getName()))
					);
		});
		add(RequestWrapper.THEME_LIST, themeList.generateHtml());
	}

	/**
	 * Genereer een html lijst met foto's en voeg deze aan de content toe.
	 * @param photos.
	 */
	public void addPhotoList(ArrayList<Photo> photos){
		HtmlList photoList = new HtmlList("photo-list", "photo-list");
		photos.listIterator().forEachRemaining((Photo photo) -> {
			photoList.addItem(new HtmlForm("post", "/managephoto")
					.setClass("photo")
					.addInput("hidden", "photoId", "" + photo.getId())
					.addElement(new HtmlImage(photo.getRelativePath())
						.setHeight(150))
					.addElement(new HtmlButton("photo-setting-button", "submit", "aan/uit")
						.setId("soundswitch")
						.addAttribute("value", "soundswitch"))
					.addElement(new HtmlButton("photo-setting-button", "submit", "voeg geluid toe")
						.setId("addsound")
						.addAttribute("value", "addsound"))
					);
		});
		add(RequestWrapper.PHOTO_LIST, photoList);
	}

}
