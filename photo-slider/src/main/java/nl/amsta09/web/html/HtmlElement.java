package nl.amsta09.web.html;

import java.util.HashMap;

/**
 * Deze class dient voor het aanmaken van een html element.
 *
 * @author Hugo Thunnissen
 */
public abstract class HtmlElement {
	protected final String openingTag = "<";
	protected String closingTag = ">";

	private StringBuilder middle = new StringBuilder();
	private String elementName;
	private HashMap<String,String> attributes;

	

	/**
	 * Maak het element aan zonder id of class.
	 * @param elementName
	 */
	public HtmlElement(String elementName){
		attributes = new HashMap<String,String>();
		this.elementName = elementName;
	}

	/**
	 * Maak het element aan.
	 * @param id
	 * @param elementClass
	 */
	public HtmlElement(String id, String elementClass, String elementName){
		attributes = new HashMap<String,String>();
		addAttribute("id", id);
		addAttribute("class", elementClass);
		this.elementName = elementName;
	}

	/**
	 * Voeg inhoud aan het element toe.
	 * @param content
	 */
	public void addContent(String content){
		middle.append(content);
	}

	/**
	 * Voeg een attribuut aan het element toe (bijvoorbeeld type of style).
	 * @param attribute
	 * @param value
	 */
	public void addAttribute(String attribute, String value){
		attributes.put(attribute, "=\"" + value + "\"");
	}

	/**
	 * Stel de styling van het element in.
	 * @param style
	 */
	public void setStyle(String style){
		addAttribute("style", style);
	}

	/**
	 * Voeg een html element aan de inhoud van het element toe.
	 * @param element
	 */
	public void addElement(HtmlElementInterface element){
		middle.append(element.generateHtml());
	}
	
	/**
	 * Haal een string op van de volledige html van het element.
	 * @return html
	 */
	public String generateHtml(){
		StringBuilder sb = new StringBuilder(openingTag);
		sb.append(elementName);
		attributes.forEach((String attribute, String value) -> {
			sb.append(" ")
				.append(attribute)
				.append(value)
				.append(" ");
		});
		sb.append(closingTag)
			.append(middle)
			.append(generateBottom());
		return sb.toString();
	}

	/**
	 * Stel de class van het element in.
	 * @param elementClass
	 */
	public void setClass(String elementClass){
		addAttribute("class", elementClass);
	}

	/**
	 * stel de id van het element in.
	 * @param id
	 */
	public void setId(String id){
		addAttribute("id", id);
	}

	protected abstract String generateBottom();
}
