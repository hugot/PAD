package nl.amsta09.web.html;

public abstract class GenericHtmlElement extends HtmlElement<GenericHtmlElement> 
	implements HtmlElementInterface{

	public GenericHtmlElement(String elementName) {
		super(elementName);
	}
}
