package nl.amsta09.web.html;

/**
 * Deze class dient voor het aanmaken van een HTML div element.
 * 
 * @author Hugo Thunnissen
 */
public class HtmlDiv extends HtmlElement<HtmlDiv> implements HtmlElementInterface {
	private static final String BOTTOM = "</div>";

	public HtmlDiv() {
		super("div");
	}

	public HtmlDiv(String id, String elementClass) {
		super("div");
		setId(id);
		setClass(elementClass);
	}

	@Override
	protected String generateBottom() {
		return BOTTOM;
	}

}
