package nl.amsta09.web.html;

/**
 * Deze class dient voor het aanmaken van een html foto.
 * 
 * @author Hugo Thunnissen
 */
public class HtmlImage extends HtmlElement<HtmlImage> implements HtmlElementInterface {

	/**
	 * maak het img element aan met een hoogte en breedte.
	 * @param src
	 * @param width
	 * @param height
	 */
	public HtmlImage(String src, int width, int height){
		super("img");
		setWidth(width);
		setHeight(height);
		setSource(src);
	}
	/**
	 * Maak het element aan.
	 * @param id
	 * @param elementClass
	 * @param src
	 */
	public HtmlImage(String id, String elementClass, String src) {
		super(id, elementClass, "img");
		setSource(src);
	}

	public HtmlImage(String src){
		super("img");
		setSource(src);
	}

	/**
	 * Stel de source in van het foto bestand.
	 * @param src
	 */
	public HtmlImage setSource(String src){
		addAttribute("src", src);
		return this;
	}

	/**
	 * Stel de hoogte van de foto in.
	 * @param height
	 */
	public HtmlImage setHeight(int height){
		addAttribute("height", "" + height);
		return this;
	}

	/**
	 * Stel de breedte van de foto in.
	 * @param width
	 */
	public HtmlImage setWidth(int width){
		addAttribute("width", "" + width);
		return this;
	}

	@Override
	protected String generateBottom() {
		return "";
	}
}
