package nl.amsta09.web.html;

	public class Form extends HtmlElement {
		private String method;
		private String action;

		private final String TOP = "<form id=\"%s\" method=\"%s\" action=\"%s\">\n";
		private final String BOTTOM = "</form>\n";
		private final String INPUT = "<input type=\"%s\" name=\"%s\" id=\"%s\" " +
			"value=\"%s\"/>";

		/**
		 * Deze methode maakt het formulier aan.
		 * @param elementClass
		 * @param method
		 * @param action
		 */
		public Form(String elementClass, String id, String method, String action){
			super(elementClass, id);
			this.method = method;
			this.action = action;
			top = String.format(TOP, id, method, action);
			bottom = BOTTOM;
		}

		/**
		 * Verander de actie die het formulier triggert.
		 * @param action
		 */
		public void setAction(String action){
			top = String.format(TOP, method, action);
		}

		/**
		 * Verander de methode die het formulier aanroept (GET of POST).
		 * @param method
		 */
		public void setMethod(String method){
			top = String.format(TOP, method, action);
		}

		/**
		 * Voeg een input veld toe aan het formulier.
		 * @param type
		 * @param name
		 */
		public void addInput(String type, String name){
			middle += String.format(INPUT, type, name, name, "");
		}

		/**
		 * Voeg een zichtbaar inputveld toe met een waarde.
		 * @param type
		 * @param name
		 * @param value
		 */
		public void addInput(String type, String name, String value){
			middle += String.format(INPUT, type, name, name, value);
		}

		/**
		 * Voeg een waarde toe aan het formulier die niet zichtbaar is
		 * voor de gebruiker.
		 * @param name
		 * @param value
		 */
		public void addValue(String name, String value){
			middle += String.format(INPUT, "hidden", name, name, value);
		}
}
