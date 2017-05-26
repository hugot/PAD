package nl.amsta09.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.driver.MainApp;
import nl.amsta09.model.Photo;
import nl.amsta09.model.Theme;
import nl.amsta09.web.SessionManager.Session;
import nl.amsta09.web.SessionManager.SessionNotFoundException;
import nl.amsta09.web.html.HtmlButton;
import nl.amsta09.web.html.HtmlElementInterface;
import nl.amsta09.web.html.HtmlForm;
import nl.amsta09.web.html.HtmlImage;
import nl.amsta09.web.html.HtmlList;
	
/**
 * Deze class dient voor het beheren van de content die als gevolg van een httpreqeust
 * wordt opgestuurd naar de gebruiker in een response.
 * 
 * @author Hugo Thunnissen
 */
public class Content {
	public static final String SELECTED_THEME_ID = "selectedThemeId";
	public static final String SELECTED_PHOTO_ID = "selectedPhotoId";
        public static final String SELECTED_AUDIO_ID = "selectedAudioId";

	public static final String THEME_MANAGEMENT_JSP = "/WEB-INF/themes.jsp";
	public static final String INDEX_JSP = "/WEB-INF/index.jsp";

	private Session session;
	private HttpServletRequest request;
	private HttpServletResponse response;

	/**
	 * Instantieer de content.
	 * @param request
	 * @param response
	 */
	public Content(HttpServletRequest request, HttpServletResponse response){
		this.request = request;
		this.response = response;
	}

	/**
	 * Stel de sessie in waarvoor de content gemaakt wordt.
	 */
	public Session parseSession(){
		Integer sessionId;
		try {
			sessionId = Integer.parseInt(request.getSession().getAttribute("sessionId").toString());
			//sessionId = Integer.parseInt(request.getAttribute("sessionId").toString());
			session = MainApp.getSessionManager().getSessionById(sessionId);
		}
		catch(NullPointerException | SessionNotFoundException | NumberFormatException e){
			session = MainApp.getSessionManager().newSession();
			request.getSession().setAttribute("sessionId", session.getId());
		}
		return session;
	}

	/**
	 * Check of de request een sessie id bevat.
	 * @return hasSession
	 */
	public boolean hasSession(){
		if(session != null){
			return true;
		}
		else {
			try {
				int sessionId = Integer.parseInt(request.getSession().getAttribute("sessionId").toString());
				MainApp.getSessionManager().getSessionById(sessionId);
				return true;
			} catch(SessionNotFoundException | NullPointerException | NumberFormatException e){
				return false;
			}
		}
	}

	/**
	 * Voeg een attribuut toe aan de servlet sessie.
	 * @param attribute
	 * @param value
	 */
	public void addToSession(String attribute, String value){
		request.getSession().setAttribute(attribute, value);
	}

	/**
	 * Voeg een attribuut toe aan de content.
	 * @param attribute
	 * @param value
	 */
	public void add(String attribute, String value){
		request.setAttribute(attribute, value);
	}

	/**
	 * Voeg een html element toe aan de content.
	 * @param attribute
	 * @param element
	 */
	public void add(String attribute, HtmlElementInterface element){
		request.setAttribute(attribute, element.generateHtml());
	}

	
	/**
	 * Voeg alle attributen aan een request to, en forward deze vervolgens naar de response.
	 * @param attributes
	 */
	public void addAllElements(Hashtable<String,HtmlElementInterface> attributes){
		attributes.forEach((String attribute, HtmlElementInterface value) -> {
			add(attribute, value);
		});
	}

	/**
	 * Voeg alle attributen aan een request to, en forward deze vervolgens naar de response.
	 * @param attributes
	 */
	public void addAll(Hashtable<String,String> attributes){
		attributes.forEach((String attribute, String value) -> {
			add(attribute, value);
		});
	}

	/**
	 * Voeg alle gegeven attributen aan de Servlet sessie toe.
	 * @param attributes
	 */
	public void addAllToSession(Hashtable<String,String> attributes){
		attributes.forEach((String attribute, String value) -> {
			addToSession(attribute, value);
		});
	}

	/**
	 * Stuur de content naar de gebruiker in een httpresponse, gebruikmakend van de
	 * gegeven jsp.
	 * @param jsp
	 */
	public void sendUsing(String jsp) throws ServletException, IOException{
		request.getRequestDispatcher(jsp).forward(request, response);
	}

	/**
	 * Genereer een html lijst van themes en voeg deze aan de content toe.
	 * @param themes
	 */
	public void addThemeList(ArrayList<Theme> themes, String formAction){
		HtmlList themeList = new HtmlList();
		themes.listIterator().forEachRemaining((Theme theme)-> {
			HtmlForm form = new HtmlForm("" + theme.getId(), "theme", "post", formAction); 
			form.addInput("hidden", "themeName", theme.getName());
			form.addInput("hidden", "selectedThemeId", "" + theme.getId());
			form.addElement(new HtmlButton("select-theme-button", "submit", theme.getName()));
			themeList.addItem(form);
		});
		addToSession("themes", themeList.generateHtml());
	}

	public void addPhotoList(ArrayList<Photo> photos){
		HtmlList photoList = new HtmlList("photo-list", "photo-list");
		photos.listIterator().forEachRemaining((Photo photo) -> {
			HtmlForm form = new HtmlForm("post", "/managephoto");
			form.setClass("photo");
			form.addInput("hidden", "photoId", "" + photo.getId());
			HtmlImage image = new HtmlImage(photo.getRelativePath());
			image.setHeight(150);
			form.addElement(image);

			HtmlButton soundSwitch = new HtmlButton("photo-setting-button", "submit", "aan/uit");
			soundSwitch.setId("soundswitch");
			soundSwitch.addAttribute("value", "soundswitch");
			form.addElement(soundSwitch);

			HtmlButton addSound = new HtmlButton("photo-setting-button", "submit", "Voeg een geluid toe");
			addSound.setId("addsound");
			addSound.addAttribute("value", "addsound");
			form.addElement(addSound);

			photoList.addItem(form);
		});
		add("photos", photoList);
	}

	/**
	 * De reuest.
	 * @return request
	 */
	public HttpServletRequest getRequest(){
		return request;
	}

	/**
	 * De response.
	 * @return response
	 */
	public HttpServletResponse getResponse(){
		return response;
	}
}
