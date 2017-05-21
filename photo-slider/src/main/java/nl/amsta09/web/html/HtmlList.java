package nl.amsta09.web.html;

/**
 * Deze class dient voor het aanmaken van een lijst in html.
 *
 * @author Hugo Thunnissen
 */
public class HtmlList extends HtmlElement implements HtmlElementInterface {
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
	public void addItem(String content){
		addElement(new ListItem(content));
	}

	/**
	 * Voeg een item toe met een html element.
	 * @param element
	 */
	public void addItem(HtmlElementInterface element){
		addElement(new ListItem(element));
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
	private class ListItem extends HtmlElement implements HtmlElementInterface {
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
