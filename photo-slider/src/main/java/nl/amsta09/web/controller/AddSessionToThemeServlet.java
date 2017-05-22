package nl.amsta09.web.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.data.SqlConnector;
import nl.amsta09.data.SqlConnector.ThemeNotFoundException;
import nl.amsta09.driver.MainApp;
import nl.amsta09.model.Media;
import nl.amsta09.model.Photo;
import nl.amsta09.model.Theme;
import nl.amsta09.web.Content;
import nl.amsta09.web.SessionManager.Session;
import nl.amsta09.web.SessionManager.SessionNotFoundException;
import nl.amsta09.web.html.HtmlPopup;

public class AddSessionToThemeServlet extends HttpServlet {
	private static final String HOME_JSP = "/WEB-INF/index.jsp";
	private static final String THEME_SELECTION_JSP = "/WEB-INF/theme-selection.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Content content = new Content(request, response);
		SqlConnector conn = new SqlConnector();
		
		// Check of er een geldige sessie is voor de gebruiker.
		if(content.hasSession()){
			try {
				content.addThemeList(conn.getAllThemes(), request.getServletPath());
				content.sendUsing(THEME_SELECTION_JSP);
				return;
			}
			catch(SQLException e){
				content.add("popup", new HtmlPopup("error", "Database probleem",
							"Er heeft zich een probleem voorgedaan waardoor de thema's" +
							"niet uit de database opgehaald kunnen worden, herlaad de pagina " +
							"om het opnieuw te proberen"));
				content.sendUsing(THEME_SELECTION_JSP);
				e.printStackTrace();
				return;
			}
		}
		else {
			content.sendUsing(HOME_JSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Content content = new Content(request, response);
		SqlConnector conn = new SqlConnector();

		//Check of er een geldige sessie actief is voor de gebruiker.
		if(!content.hasSession()){
			content.sendUsing(HOME_JSP);
			return;
		}

		// De sessie van de gebruiker
		Session session = content.parseSession();

		String themeId = request.getParameter(Content.SELECTED_THEME_ID);
		Theme theme;
		try {
			theme = conn.getThemeById(Integer.parseInt(themeId));
		} catch (SQLException | NullPointerException | NumberFormatException | ThemeNotFoundException e) {
			HtmlPopup popup = new HtmlPopup("error", "Er is iets misgegaan", 
					"Probeer de pagina opnieuw te laden.");
			content.add("popup", popup);
			content.sendUsing(THEME_SELECTION_JSP);
			e.printStackTrace();
			return;
		}


		if(theme != null){
			for(Media media : session.getAddedMedia()){
				if(media instanceof Photo){
					try {
						conn.addMediaToTheme(theme.getId(), media.getId());
						request.setAttribute("message", "media toegevoegd aan thema");
					} catch (SQLException e) {
						HtmlPopup popup = new HtmlPopup("error", "Er is iets misgegaan", 
								"Het is niet gelukt om verbinding te maken met de database.");
						content.add("popup", popup);
						content.sendUsing(THEME_SELECTION_JSP);
						e.printStackTrace();
						return;
					}
				}
			}
		}
		
		HtmlPopup popup = new HtmlPopup("confirmation", "Media toegevoegd", 
				"De media is toegevoegd aan het thema.");
		content.add("popup", popup);
		content.sendUsing(THEME_SELECTION_JSP);
	}
}
