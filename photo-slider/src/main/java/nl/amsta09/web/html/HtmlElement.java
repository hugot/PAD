package nl.amsta09.web.html;

import java.util.HashMap;

/**
 * Deze class dient voor het aanmaken van een html element.
 *
 * @author Hugo Thunnissen
 */
public abstract class HtmlElement <Element extends HtmlElement<Element>> implements HtmlElementInterface{
	protected final String openingTag = "<";
	protected String closingTag = ">";

	private StringBuilder middle;
	private String elementName;
	private HashMap<String,String> attributes;

	

	/**
	 * Maak het element aan zonder id of class.
	 * @param elementName
	 */
	public HtmlElement(String elementName){
		attributes = new HashMap<String,String>();
		this.elementName = elementName;
		middle = new StringBuilder();
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
		middle = new StringBuilder();
	}

	/**
	 * Voeg inhoud aan het element toe.
	 * @param content
	 */
	public Element addContent(String content){
		middle.append(content);
		return (Element)this;
	}

	public Element setContent(String content){
		middle = new StringBuilder(content);
		return (Element)this;
	}

	/**
	 * Voeg een attribuut aan het element toe (bijvoorbeeld type of style).
	 * @param attribute
	 * @param value
	 */
	public Element addAttribute(String attribute, String value){
		attributes.put(attribute, "=\"" + value + "\"");
		return (Element)this;
	}

	/**
	 * Stel de styling van het element in.
	 * @param style
	 */
	public Element setStyle(String style){
		addAttribute("style", style);
		return (Element)this;
	}

	/**
	 * Voeg een html element aan de inhoud van het element toe.
	 * @param element
	 */
	public Element addElement(HtmlElementInterface element){
		middle.append(element.generateHtml());
		return (Element)this;
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
	 * De gegenereerde html (Synoniem voor generatehtml).
	 * @return html
	 */
	public String toString(){
		return generateHtml();
	}

	/**
	 * Stel de class van het element in.
	 * @param elementClass
	 */
	public Element setClass(String elementClass){
		addAttribute("class", elementClass);
		return (Element)this;
	}

	/**
	 * stel de id van het element in.
	 * @param id
	 */
	public Element setId(String id){
		addAttribute("id", id);
		return (Element)this;
	}

	/**
	 * Vraag een attribuut op aan de hand van de naam.
	 */
	public String getAttribute(String attributeName){
		return attributes.get(attributeName);
	}

	/**
	 * Genereer de onderkant van het element.
	 * @return bottom
	 */
	protected abstract String generateBottom();
}
