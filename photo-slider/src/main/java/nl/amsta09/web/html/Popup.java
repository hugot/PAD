package nl.amsta09.web.html;

public class Popup extends Form {

	private final String HEADER = "<header> <h3>%s</h3> </header>";

	/**
	 * Maak een popup aan met deze methode.
	 * @param title
	 * @param text
	 * @param button
	 */
	public Popup(String title, String text, String button){
		super("", "popup", "", "");
		this.addContent(String.format(HEADER, title));
		this.addContent("<br>" + text);
		this.addElement(new Button("popupConfirmation", "hideCreateThemePopup();", "OK"));
	}

}
