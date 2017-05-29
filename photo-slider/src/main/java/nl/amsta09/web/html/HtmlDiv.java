package nl.amsta09.web.html;

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
