package nl.amsta09.web.html;

/**
 * Deze class dient voor het aanmaken van een lijst in html.
 *
 * @author Hugo Thunnissen
 */
public class HtmlList extends HtmlElement<HtmlList> implements HtmlElementInterface {
	private final String BOTTOM = "</ul>";

	/**
	 * Maak de lijst aan.
	 */
	public HtmlList(){
		super("ul");
	}

	/**
	 * Maak een html lijst aan
	 * @param id
	 * @param element
	 */
	public HtmlList(String id, String elementClass) {
		super(id, elementClass, "ul");
	}

	/**
	 * Voeg een item toe met tekst.
	 * @param content
	 */
	public HtmlList addItem(String content){
		addElement(new ListItem(content));
		return this;
	}

	/**
	 * Voeg een item toe met een html element.
	 * @param element
	 */
	public HtmlList addItem(HtmlElementInterface element){
		addElement(new ListItem(element));
		return this;
	}

	/**
	 * {@InheritDoc}
	 */
	@Override
	protected String generateBottom() {
		return BOTTOM;
	}

	/**
	 * Deze class dient voor het aanmaken van een item in een html lijst.
	 */
	private class ListItem extends HtmlElement<ListItem> implements HtmlElementInterface {
		private final String BOTTOM = "</li>";

		/**
		 * Instantieer een list item met tekst erin.
		 * @param content
		 */
		public ListItem(String content){
			super("li");
			addContent(content);
		}

		/**
		 * Instantieer een list item met een html element erin.
		 * @param element
		 */
		public ListItem(HtmlElementInterface element){
			super("li");
			addElement(element);
		}

		@Override
		protected String generateBottom() {
			return BOTTOM;
		}
	}

}
