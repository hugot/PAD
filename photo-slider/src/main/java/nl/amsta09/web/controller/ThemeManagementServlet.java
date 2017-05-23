package nl.amsta09.web.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.data.SqlConnector;
import nl.amsta09.data.SqlConnector.ThemeNotFoundException;
import nl.amsta09.driver.MainApp;
import nl.amsta09.model.Photo;
import nl.amsta09.model.Theme;
import nl.amsta09.web.Content;
import nl.amsta09.web.SessionManager.Session;
import nl.amsta09.web.html.HtmlPopup;

public class ThemeManagementServlet extends HttpServlet {
	private static final String JSP = "/WEB-INF/themes.jsp";
	private static final String SELECTED_THEME_ID = "selectedThemeId";
	private static final String THEME = "theme";
	private static final int STARTING_THEME = 0;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
			System.out.println("-----GET-----");
		Content content = new Content(request, response);
		SqlConnector conn = new SqlConnector();
		Session session = content.parseSession();

		ArrayList<Theme> themes;
		try {
			themes = conn.getAllThemes();
		} catch (SQLException e) {
			content.add("message","Het is niet gelukt om de thema's op te halen uit de database, " +
					"probeer de pagina te vernieuwen");
			content.sendUsing(JSP);
			return;
		}


		Theme startingTheme = themes.get(STARTING_THEME);
		content.addThemeList(themes, request.getServletPath());
		content.add(SELECTED_THEME_ID, "" + startingTheme.getId()); 
		content.addToSession(THEME, startingTheme.getName());

		//Maak een lijst met alle foto's van de theme
		ArrayList<Photo> photos;
		try {
			photos = conn.getAllPhotosFromTheme(startingTheme);
		} catch (SQLException e) {
			HtmlPopup popup = new HtmlPopup("error", "Fout bij ophalen foto's", 
					"Er is iets fout gegaan bij het ophalen van de foto's, probeer het alstublieft opnieuw");
			content.add("popup", popup);
			content.sendUsing(JSP);
			return;
		}

		content.addPhotoList(photos);
		content.add(SELECTED_THEME_ID, "" + startingTheme.getId());
		content.sendUsing(JSP);
		
		session.setManagedTheme(themes.get(STARTING_THEME));
		MainApp.getSessionManager().updateSession(session);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
			System.out.println("-----POST-----");
		Content content = new Content(request, response);
		Session session = content.parseSession();
		SqlConnector conn = new SqlConnector();
		
		// Achterhaal welk thema is aangeklikt
		int themeId;
		try {
			themeId = parseSelectedThemeId(request);
		}
		catch (NullPointerException | NumberFormatException e){
			HtmlPopup popup = new HtmlPopup("error", "Er is iets misgegaan", 
					"Probeer de pagina opnieuw te laden.");
			content.add("popup", popup);
			content.sendUsing(JSP);
			e.printStackTrace();
			return;
		}

		// Haal het thema op uit de database
		Theme theme;
		try {
			theme = conn.getThemeById(themeId);
		} catch (SQLException | ThemeNotFoundException e) {
			HtmlPopup popup = new HtmlPopup("error", "Thema niet gevonden", 
					"Het thema bestaat niet of er is iets misgegaan, probeer het in dat geval opnieuw.");
			content.add("popup", popup);
			content.sendUsing(JSP);
			return;
		}

		session.setManagedTheme(theme);
		MainApp.getSessionManager().updateSession(session);

		// Haal foto's op uit de database
		ArrayList<Photo> photos;
		try {
			photos = conn.getAllPhotosFromTheme(theme);
		} catch (SQLException e) {
			HtmlPopup popup = new HtmlPopup("error", "Fout bij ophalen foto's", 
					"Er is iets fout gegaan bij het ophalen van de foto's, probeer het alstublieft opnieuw");
			content.add("popup", popup);
			content.sendUsing(JSP);
			return;
		}

		content.addPhotoList(photos);
		content.add(SELECTED_THEME_ID, "" + theme.getId());
		content.sendUsing(JSP);
	}

	protected static int parseSelectedThemeId(HttpServletRequest request) throws NumberFormatException,
			  NullPointerException {
		int themeId;
		themeId = Integer.parseInt(request.getParameter(SELECTED_THEME_ID).toString());
		return themeId;
	}

}
