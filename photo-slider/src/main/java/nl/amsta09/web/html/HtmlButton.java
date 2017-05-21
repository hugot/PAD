package nl.amsta09.web.html;

public class HtmlButton extends HtmlElement implements HtmlElementInterface {
	
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
	 * Stel het button type in.
	 * @param type
	 */
	public void setType(String type){
		addAttribute("type", type);
	}

	@Override
	protected String generateBottom() {
		return BOTTOM;
	}

}
