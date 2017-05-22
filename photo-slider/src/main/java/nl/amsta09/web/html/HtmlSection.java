package nl.amsta09.web.html;

public class HtmlSection extends HtmlElement implements HtmlElementInterface {
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
