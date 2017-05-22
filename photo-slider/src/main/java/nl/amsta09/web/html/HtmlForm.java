package nl.amsta09.web.html;

	public class HtmlForm extends HtmlElement implements HtmlElementInterface {
		private final String BOTTOM = "</form>\n";

		/**
		 * Maak een formulier aan zonder attributen.
		 */
		public HtmlForm(){
			super("form");
		}

		/**
		 * Maak een formulier aan met alleen maar de methode en de actie gedefineerd.
		 * @param method
		 * @param action
		 */
		public HtmlForm(String method, String action){
			super("form");
			addAttribute("method", method);
			addAttribute("action", action);
		}
		/**
		 * Deze methode maakt het formulier aan.
		 * @param elementClass
		 * @param method
		 * @param action
		 */
		public HtmlForm(String id, String elementClass, String method, String action){
			super(id, elementClass,"form");
			addAttribute("method", method);
			addAttribute("action", action);
		}

		/**
		 * Verander de actie die het formulier triggert.
		 * @param action
		 */
		public void setAction(String action){
			addAttribute("action", action);
		}

		/**
		 * Verander de methode die het formulier aanroept (GET of POST).
		 * @param method
		 */
		public void setMethod(String method){
			addAttribute("method", method);
		}

		/**
		 * Voeg een input veld toe aan het formulier.
		 * @param type
		 * @param name
		 */
		public void addInput(String type, String name){
			addElement(new Input(type, name));
		}

		/**
		 * Voeg een zichtbaar inputveld toe met een waarde.
		 * @param type
		 * @param name
		 * @param value
		 */
		public void addInput(String type, String name, String value){
			Input input = new Input(type, name);
			input.setId(name);
			input.addAttribute("value", value);
			addElement(input);
		}

		/**
		 * Voeg een waarde toe aan het formulier die niet zichtbaar is
		 * voor de gebruiker.
		 * @param name
		 * @param value
		 */
		public void addHiddenValue(String name, String value){
			Input input = new Input("hidden", name);
			input.addAttribute("value", value);
			input.setId(name);
			addElement(input);
	}

	@Override
	protected String generateBottom() {
		return BOTTOM;
	}

	private class Input extends HtmlElement implements HtmlElementInterface {

		/**
		 * Maak een input veld aan
		 */
		public Input(String type, String name){
			super("input");
			addAttribute("type", type);
			addAttribute("name", name);
			closingTag = "/" + closingTag;
		}

		/**
		 * {@InheritDoc}
		 */
		@Override
		public String generateHtml(){
			return super.generateHtml();
		}

		/**
		 * {@InheritDoc}
		 */
		protected String generateBottom(){
			return " ";
		}
	}
}
