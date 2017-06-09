package nl.amsta09.web.html;

/**
 * Met deze class kan een HTML button worden aangemaakt.
 * 
 * @author Hugo Thunnissen
 */
public class HtmlButton extends HtmlElement<HtmlButton> implements HtmlElementInterface {
	
	final String BOTTOM = "</button>";

	/**
	 * Maak een button aan.
	 * @param elementClass
	 * @param action
	 * @param text
	 */
	public HtmlButton(String elementClass, String action, String text){
		super("", elementClass, "button");
		addAttribute("onClick", action);
		addContent(text);
	}

	/**
	 * Maak button aan zonder attributen.
	 */
	public HtmlButton(){
		super("button");
	}

	/**
	 * stel onclick attribuut in.
	 * @return button
	 */
	public HtmlButton setOnClick(String action){
		addAttribute("onclick", action);
		return this;
	}

	/** 
	 * Voeg tekst toe aan de button.
	 * @param text
	 * @return button
	 */
	public HtmlButton addText(String text){
		addContent(text);
		return this;
	}

	/**
	 * Stel het button type in.
	 * @param type
	 */
	public HtmlButton setType(String type){
		addAttribute("type", type);
		return this;
	}

	@Override
	protected String generateBottom() {
		return BOTTOM;
	}

}
