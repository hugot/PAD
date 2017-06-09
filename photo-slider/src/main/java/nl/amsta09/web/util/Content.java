package nl.amsta09.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import nl.amsta09.model.Photo;
import nl.amsta09.model.Theme;
import nl.amsta09.web.html.HtmlButton;
import nl.amsta09.web.html.HtmlDiv;
import nl.amsta09.web.html.HtmlElementInterface;
import nl.amsta09.web.html.HtmlImage;
	
/**
 * Deze class dient voor het beheren van de content die als gevolg van een httpreqeust
 * wordt opgestuurd naar de gebruiker in een response.
 * 
 * @author Hugo Thunnissen
 */
public class Content {
	private HttpServletRequest request;
	private HashMap<String,String> content;

	/**
	 * Instantieer de content.
	 * @param request
	 * @param response
	 */
	public Content(RequestWrapper request){
		this.request = request;
		this.content = new HashMap<String,String>();
	}

	/**
	 * Voeg een attribuut toe aan de content.
	 * @param attribute
	 * @param value
	 */
	public void add(String attribute, String value){
		content.put(attribute, value);
	}

	/**
	 * Voeg een html element toe aan de content.
	 * @param attribute
	 * @param element
	 */
	public void add(String attribute, HtmlElementInterface element){
		content.put(attribute, element.generateHtml());
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
	 * Haal alle content op in de vorm van een String.
	 * @return content
	 */
	protected String getAll(){
		StringBuilder sb = new StringBuilder();
		content.forEach((String name, String value) -> {
			sb.append(value);
		});
		return sb.toString();
	}

	/**
	 * Voeg alle attrubiten toe aan de request.
	 */
	protected void appendAttributes(){
		content.forEach((String name, String value) -> {
			request.setAttribute(name, value);
		});
	}

	/**
	 * Genereer html elementen met thema's en voeg deze aan de content toe.
	 * @param themes
	 */
	public void addThemeList(ArrayList<Theme> themes, String formAction){
		StringBuilder sb = new StringBuilder();
		themes.listIterator().forEachRemaining((Theme theme)-> {
			sb.append(new HtmlButton()
					.addContent(theme.getName())
					.setOnClick("setActiveTheme('" + theme.getId() + "')")
					.setId("" + theme.getId())
					.generateHtml());
		});
		add(RequestWrapper.THEME_LIST, sb.toString());
	}

	/**
	 * Genereer een html lijst met foto's en voeg deze aan de content toe.
	 * @param photos.
	 */
	public void addPhotoList(ArrayList<Photo> photos){
		StringBuilder sb = new StringBuilder();
		photos.listIterator().forEachRemaining((Photo photo) -> {
				sb.append(new HtmlButton()
					.setClass("floating-image")
					.addElement(new HtmlDiv()
						.setClass("photo-container")
						.addElement(new HtmlImage("image" + photo.getId(), "photo", photo.getRelativePath())
						.setHeight(150)))
					.setOnClick("showImage('" + photo.getId() + "');")
					.addContent("<h4>" + photo.getName() + "</h4>")
					.generateHtml()
					);
		});
		add(RequestWrapper.PHOTO_LIST, sb.toString());
	}

	/**
	 * Genereer html elementen waarmee de foto's uit de gegeven lijst
	 * geselecteerd kunnen worden.
	 * @param photos
	 */
	public void addPhotoSelectionList(ArrayList<Photo> photos){
		StringBuilder sb = new StringBuilder();
		photos.listIterator().forEachRemaining((Photo photo) -> {
			sb.append(new HtmlButton()
					.setClass("floating-image")
					.setId("image-div" + photo.getId())
					.addElement(new HtmlDiv()
						.setClass("photo-container")
						.addElement(new HtmlImage("image" + photo.getId(), "photo", photo.getRelativePath())
						.setHeight(150)))
					.setOnClick("selectMedia('image-div" + photo.getId() + "', '"+ photo.getId()+ "');")
					.addContent("<h4>" + photo.getName() + "</h4>")
					.generateHtml()
			);
		});
		add(RequestWrapper.PHOTO_LIST, sb.toString());
	}
}
