package nl.amsta09.web.html;

public class HtmlList extends HtmlElement {
	private final String TOP = "<ul id=\"%s\" class=\"%s\" >";
	private final String BOTTOM = "</ul>";

	/**
	 * Maak een html lijst aan
	 * @param id
	 * @param element
	 */
	public HtmlList(String id, String elementClass) {
		super(id, elementClass);
		top = String.format(TOP, id, elementClass);
		bottom = BOTTOM;
	}

	/**
	 * Voeg een item toe met tekst.
	 * @param content
	 */
	public void additem(String content){
		middle += new ListItem(content).getHtml();
	}

	/**
	 * Voeg een item toe met een html element.
	 * @param element
	 */
	public void addItem(HtmlElement element){
		middle += new ListItem(element).getHtml();
	}

	private class ListItem extends HtmlElement {
		private final String TOP = "<li>";
		private final String BOTTOM = "</li>";

		/**
		 * Instantieer een list item met tekst erin.
		 * @param content
		 */
		public ListItem(String content){
			super("", "");
			top = TOP;
			bottom = BOTTOM;
			middle = content;
		}

		/**
		 * Instantieer een list item met een html element erin.
		 * @param element
		 */
		public ListItem(HtmlElement element){
			super("", "");
			top = TOP;
			bottom = BOTTOM;
			middle = element.getHtml();
		}
	}
}
