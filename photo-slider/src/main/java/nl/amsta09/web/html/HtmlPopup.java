package nl.amsta09.web.html;

/**
 * Deze class dient voor het aanmaken van een html popup.
 * 
 * @author Hugo Thunnissen
 */
public class HtmlPopup extends HtmlForm implements HtmlElementInterface {
	public static final String CLASS = " popup";
	private final String HEADER = "<header> <h3>%s</h3> </header>";

	/**
	 * Maak een popup aan met deze methode.
	 * @param title
	 * @param text
	 * @param button
	 */
	public HtmlPopup(String id, String title, String text, String buttonText){
		setId(id);
		setClass(CLASS);
		addContent(String.format(HEADER, title));
		addContent("<br>" + text + "<br>");
		HtmlButton button = new HtmlButton("popup-confirmation", "hidePopup('" + id + "');", buttonText);
		button.setType("button");
		addElement(button);
	}

	/**
	 * Maak een popup met een oke knop.
	 * @param title
	 * @param text
	 * @param button
	 */
	public HtmlPopup(String id, String title, String text){
		setId(id);
		setClass(CLASS);
		addContent(String.format(HEADER, title));
		addContent("<br>" + text + "<br>");
		HtmlButton button = new HtmlButton("big-button", "hidePopup('" + id + "');", "OK");
		button.setType("button");
		addElement(button);
	}

}
