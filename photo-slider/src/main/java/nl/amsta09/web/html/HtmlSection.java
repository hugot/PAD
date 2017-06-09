package nl.amsta09.web.html;

/**
 * Deze class dient voor het aanmaken van een HTML sectie
 * 
 * @author Hugo Thunnissen
 */
public class HtmlSection extends HtmlElement<HtmlSection> implements HtmlElementInterface {
	private static final String BOTTOM = "</section>";

	public HtmlSection(){
		super("section");
	}

	public HtmlSection(String id, String elementClass){
		super(id, elementClass, "section");
	}

	@Override
	protected String generateBottom() {
		return BOTTOM;
	}
}
