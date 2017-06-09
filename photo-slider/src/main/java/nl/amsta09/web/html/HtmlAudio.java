package nl.amsta09.web.html;

/**
 * Met deze class kan een HTML5 audio element worden aangemaakt.
 * 
 * @author Hugo Thunnissen.
 */
public class HtmlAudio extends HtmlElement<HtmlAudio> implements HtmlElementInterface {
	private final static String BOTTOM = "</audio>";
	private GenericHtmlElement sourceElement;

	public HtmlAudio(String elementId, String source) {
		super(elementId, "audio-player", "audio controls autoplay");
		sourceElement = new GenericHtmlElement("source") {

			@Override
			protected String generateBottom() {
				return "</source>";
			}
			
		};
		sourceElement.addAttribute("src", source);
		addElement(sourceElement);
	}

	public HtmlAudio addPlayButton(){
		addElement(new HtmlButton("play-button", 
					"playSound(" + getAttribute("id") + ");", 
					"play"));
		return this;
	}

	public HtmlAudio addPauseButton(){
		addElement(new HtmlButton("play-button", 
					"pauseSound(" + getAttribute("id") + ");", 
					"play"));
		return this;
	}

	@Override
	protected String generateBottom() {
		return BOTTOM;
	}
}
