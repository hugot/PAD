package nl.amsta09.web.html;

/**
 * Met deze class kan een generiek html element worden aangemaakt.
 * 
 * @author Hugo Thunnissen.
 */
public abstract class GenericHtmlElement extends HtmlElement<GenericHtmlElement> 
	implements HtmlElementInterface{

	public GenericHtmlElement(String elementName) {
		super(elementName);
	}
}
