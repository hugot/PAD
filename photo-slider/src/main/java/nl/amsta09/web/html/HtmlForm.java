package nl.amsta09.web.html;

	public class HtmlForm extends HtmlElement<HtmlForm> implements HtmlElementInterface {
		private final String BOTTOM = "</form>";

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
		public HtmlForm setAction(String action){
			addAttribute("action", action);
			return this;
		}

		/**
		 * Verander de methode die het formulier aanroept (GET of POST).
		 * @param method
		 */
		public HtmlForm setMethod(String method){
			addAttribute("method", method);
			return this;
		}

		/**
		 * Voeg een input veld toe aan het formulier.
		 * @param type
		 * @param name
		 */
		public HtmlForm addInput(String type, String name){
			addElement(new Input(type, name));
			return this;
		}

		/**
		 * Voeg een zichtbaar inputveld toe met een waarde.
		 * @param type
		 * @param name
		 * @param value
		 */
		public HtmlForm addInput(String type, String name, String value){
			Input input = new Input(type, name);
			input.setId(name);
			input.addAttribute("value", value);
			addElement(input);
			return this;
		}

		/**
		 * Voeg een waarde toe aan het formulier die niet zichtbaar is
		 * voor de gebruiker.
		 * @param name
		 * @param value
		 */
		public HtmlForm addHiddenValue(String name, String value){
			Input input = new Input("hidden", name);
			input.addAttribute("value", value);
			input.setId(name);
			addElement(input);
			return this;
		}

	@Override
	protected String generateBottom() {
		return BOTTOM;
	}

	private class Input extends HtmlElement<Input> implements HtmlElementInterface {

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
		protected String generateBottom(){
			return "";
		}

	}

}
