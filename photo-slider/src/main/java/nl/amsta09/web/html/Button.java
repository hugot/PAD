package nl.amsta09.web.html;

public class Button extends HtmlElement {
	
	final String TOP = "<button class=\"%s\" onClick=\"%s\">";
	final String BOTTOM = "</button>";

	/**
	 * Maak een button aan.
	 * @param elementClass
	 * @param action
	 * @param text
	 */
	public Button(String elementClass, String action, String text){
		super("", elementClass);
		top = String.format(TOP, elementClass, action);
		this.addContent(text);
		bottom = BOTTOM;
	}

}
