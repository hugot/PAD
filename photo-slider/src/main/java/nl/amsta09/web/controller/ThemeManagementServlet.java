package nl.amsta09.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.util.PatternMatcher;

import nl.amsta09.data.SqlConnector.ThemeNotFoundException;
import nl.amsta09.model.Photo;
import nl.amsta09.model.Theme;
import nl.amsta09.model.Audio;
import nl.amsta09.web.html.GenericHtmlElement;
import nl.amsta09.web.html.HtmlAudio;
import nl.amsta09.web.html.HtmlButton;
import nl.amsta09.web.html.HtmlForm;
import nl.amsta09.web.html.HtmlList;
import nl.amsta09.web.html.HtmlPopup;
import nl.amsta09.web.util.RequestWrapper;

/**
 * Deze class laadt en update de theme management pagina.
 *
 * @author: Hugo Thunnissen
 */
public class ThemeManagementServlet extends HttpServlet {
	private static final String MAPPING = "/thememanagement";
	private static final String THEME_LIST_MAPPING = "/getthemes";
	private static final String PHOTO_LIST_MAPPING = "/getphotos";
	private static final String ADD_THEME_MAPPING = "/addtheme";
	private static final int STARTING_THEME = 0;
	private RequestWrapper requestWrapper;
	private ArrayList<Theme> themes;
	private HttpServletResponse response;
	private ArrayList<Photo> photos;
	private Theme selectedTheme;

	/**
	 * Deze methode rendert de theme management pagina met een lijst van thema's en een lijst
	 * van de foto's van de geselecteerde thema's.
	 * @param request
	 * @param response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		requestWrapper = new RequestWrapper(request);
		this.response = response;
		System.out.println("request ontvangen!");
		// Zorg dat de sessie een mediasessie is.
		requestWrapper.getSession().setMediaSession();

		// Antwoord met alleen de themalijst
		if(requestWrapper.getServletPath().equals(THEME_LIST_MAPPING) || 
			requestWrapper.getServletPath().equals(ADD_THEME_MAPPING)){
			if(!refreshThemeList()) return;
			respondWithThemeList();
			return;
		}

		// Laad lijst met thema's
		if(!refreshThemeList()) return;

		// Geselecteerde thema
		if(requestWrapper.getSession().hasManagedTheme()){
			selectedTheme = requestWrapper.getSession().getMediaSession()
				.getManagedTheme();
		} else {
			try{
				selectedTheme = themes.get(STARTING_THEME); // Default thema
				requestWrapper.getSession().setManagedTheme(selectedTheme);
				System.out.println("thema gevonden");
			}
			catch (IndexOutOfBoundsException e){
				requestWrapper.respondUsing(RequestWrapper.THEME_MANAGEMENT_JSP, response);
				System.out.println("geen thema gevonden");
				return;
			}
		}

		//Maak een lijst met alle foto's van de theme
		if(!refreshPhotoList()) return;
		if(requestWrapper.getServletPath().equals(PHOTO_LIST_MAPPING)) {
			respondWithPhotoList();
			System.out.println("antwoord met fotolijst");
			return;
		}

		//Laad lijst met muziek die bij thema hoort.
		if(!refreshMusicList()) return;

		requestWrapper.getContent().addThemeList(themes, PHOTO_LIST_MAPPING);
		requestWrapper.getContent().addPhotoList(photos);
		requestWrapper.respondUsing(RequestWrapper.THEME_MANAGEMENT_JSP, response);
	}

	/**
	 * Deze methode achterhaalt het thema dat geselecteerd is in de theme management
	 * pagina en laad hiervoor de bijbehorende foto's.
	 * @param request
	 * @param response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		RequestWrapper requestWrapper = new RequestWrapper(request);
		this.response = response;
		System.out.println("POST");

		//Redirect naar GET methode als er nog geen mediaSessie actief is.
		if(!requestWrapper.getSession().hasMediaSession()){
			doGet(request, response);
			return;
		} 
		
		// Achterhaal welk thema is aangeklikt en haal het op uit de database
		int themeId;
		try {
			themeId = Integer.parseInt(requestWrapper.parseParameter(RequestWrapper.SELECTED_THEME_ID));
			selectedTheme = requestWrapper.getSqlConnector().getThemeById(themeId);
			requestWrapper.getSession().setManagedTheme(selectedTheme);
			System.out.println("dit doet het wel");
		} catch (NullPointerException | NumberFormatException | SQLException | 
				ThemeNotFoundException e) {
			System.out.println("kutleven");
			HtmlPopup popup = new HtmlPopup("error", "Thema niet gevonden", 
					"Het thema bestaat niet of er is iets misgegaan, " +
					"probeer het in dat geval opnieuw.");
			requestWrapper.getContent().add(HtmlPopup.CLASS, popup);
		}
		doGet(requestWrapper, response);
	}

	/**
	 * (Her)laadt de thema's. 
	 * @return refreshed geeft aan of de actie geslaagd is of niet.
	 */
	private boolean refreshThemeList()
			throws ServletException, IOException{
		try {
			themes = requestWrapper.getSqlConnector().getAllThemes();
			return true;
		} catch (SQLException e) {
			requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("error", 
						"Fout bij ophalen Thema's", 
						"Er is iets fout gegaan bij het ophalen van de thema's, " + 
						"probeer het alstublieft opnieuw"));
			requestWrapper.respondUsing(
					RequestWrapper.THEME_MANAGEMENT_JSP, response);
			return false;
		}
	}

	/**
	 * (Her)laadt de foto's die bij het geselecteerde thema horen.
	 * @return refreshed geeft aan of de actie geslaagd is of niet.
	 */
	private boolean refreshPhotoList() throws ServletException, IOException{
		try {
			photos = requestWrapper.getSqlConnector()
				.getAllPhotosFromTheme(selectedTheme);
		} catch (SQLException e) {
			HtmlPopup popup = new HtmlPopup("error", "Fout bij ophalen foto's", 
					"Er is iets fout gegaan bij het ophalen van de foto's, " + 
					"probeer het alstublieft opnieuw");
			requestWrapper.getContent().add("popup", popup);
			requestWrapper.respondUsing(RequestWrapper.THEME_MANAGEMENT_JSP, response);
			return false;
		}
		return true;
	}

	/**
	 * (Her)laadt de muziek die in het thema hoort.
	 * @return refreshed geeft aan of de actie geslaagd is of niet.
	 */
	private boolean refreshMusicList() throws ServletException, IOException{
		try {
			ArrayList<Audio> music = requestWrapper.getSqlConnector()
				.getAllMusicsFromTheme(selectedTheme);
			HtmlList musicList = new HtmlList("music-list", "music-list");
			music.listIterator().forEachRemaining((Audio audio) -> {
				musicList.addItem(new HtmlForm()
						.setAction("/audiosample")
						.setMethod("POST")
						.addAttribute("target", "music-player")
						.addContent("<p>" + audio.getName() + "</p>")
						.addHiddenValue(RequestWrapper.SELECTED_AUDIO_ID, "" + audio.getId())
						.addInput("submit", "voobeeld"));
			});
			GenericHtmlElement iframe = new GenericHtmlElement("iframe") {
				@Override
				protected String generateBottom() {
					return "</iframe>";
				}
			};
			requestWrapper.getContent().add("musicFrame", iframe
					.setId("music-player")
					.addAttribute("name", "music-player")
					.addAttribute("src","/audiosample"));
			requestWrapper.getContent().add("music", musicList);
		} catch (SQLException e) {
			HtmlPopup popup = new HtmlPopup("error", "Fout bij ophalen foto's", 
					"Er is iets fout gegaan bij het ophalen van de foto's, " +
					"probeer het alstublieft opnieuw");
			requestWrapper.getContent().add("popup", popup);
			requestWrapper.respondUsing(RequestWrapper.THEME_MANAGEMENT_JSP, response);
			return false;
		}
		return true;
	}

	private void respondWithThemeList() throws IOException{
		requestWrapper.getContent().addThemeList(themes, MAPPING);
		requestWrapper.sendContentByWriter(response);
	}

	private void respondWithPhotoList() throws IOException{
		requestWrapper.getContent().addPhotoList(photos);
		requestWrapper.sendContentByWriter(response);
	}
}
