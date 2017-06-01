package nl.amsta09.web.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.data.SqlConnector.ThemeNotFoundException;
import nl.amsta09.model.Photo;
import nl.amsta09.model.Theme;
import nl.amsta09.model.Audio;
import nl.amsta09.web.html.HtmlAudio;
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
		// Zorg dat de sessie een mediasessie is.
		requestWrapper.getSession().setMediaSession();

		// Laad lijst met thema's
		if(!refreshThemeList()) return;
		requestWrapper.getContent().addThemeList(themes, MAPPING);

		// Geselecteerde thema
		if(requestWrapper.getSession().hasManagedTheme()){
			selectedTheme = requestWrapper.getSession().getMediaSession()
				.getManagedTheme();
		}
		else {
			try{
				selectedTheme = themes.get(STARTING_THEME); // Default thema
				requestWrapper.getSession().setManagedTheme(selectedTheme);
			}
			catch (IndexOutOfBoundsException e){
				requestWrapper.respondUsing(RequestWrapper.THEME_MANAGEMENT_JSP, response);
				return;
			}
		}

		//Maak een lijst met alle foto's van de theme
		if(!refreshPhotoList()) return;
		requestWrapper.getContent().addPhotoList(photos);

		//Laad lijst met muziek die bij thema hoort.
		if(!refreshMusicList()) return;

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

		//Redirect naar GET methode als er nog geen mediaSessie actief is.
		if(!requestWrapper.getSession().hasMediaSession()){
			doGet(request, response);
			return;
		} 
		
		// Achterhaal welk thema is aangeklikt en haal het op uit de database
		int themeId;
		try {
			themeId = Integer.parseInt(
					requestWrapper.getParameter(
						RequestWrapper.SELECTED_THEME_ID).toString());
			selectedTheme = requestWrapper.getSqlConnector().getThemeById(themeId);
			requestWrapper.getSession().setManagedTheme(selectedTheme);
		} catch (NullPointerException | NumberFormatException | SQLException | 
				ThemeNotFoundException e) {
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
				musicList.addItem(
						new HtmlAudio(
							"" + audio.getId(), audio.getRelativePath()));
			});
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
}
