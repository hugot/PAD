package nl.amsta09.web.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.data.SqlConnector.ThemeNotFoundException;
import nl.amsta09.model.Media;
import nl.amsta09.model.Photo;
import nl.amsta09.model.Theme;
import nl.amsta09.web.html.HtmlButton;
import nl.amsta09.web.html.HtmlDiv;
import nl.amsta09.web.html.HtmlPopup;
import nl.amsta09.web.util.RequestWrapper;

/**
 * Deze class dient voor het toevoegen van de media die tijdens een sessie is toegevoegd
 * aan een thema.
 *
 * @author Hugo Thunnissen
 */
public class AddSessionToThemeServlet extends HttpServlet {

	/**
	 * Laad een lijst met beschikbare thema's om de foto's aan toe te voegen.
	 * @param request
	 * @param response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		RequestWrapper requestWrapper = new RequestWrapper(request);
		
		// Check of er een geldige sessie is voor de gebruiker.
		if(requestWrapper.getSession().hasMediaSession()){
			try {
				StringBuilder sb = new StringBuilder();
				requestWrapper.getSqlConnector().getAllThemes()
					.listIterator().forEachRemaining((Theme theme) -> {
						sb.append(new HtmlButton()
							.setClass("big-button")
							.setId(theme.getName() + theme.getId())
								.setOnClick("selectTheme('" + theme.getName() + theme.getId() + 
									"', '" + theme.getId() + "');")
								.addContent(theme.getName())
								.generateHtml()
						);
					});
				sb.append(new HtmlDiv()
					.setClass("bottom-bar")
					.addElement(new HtmlButton()
						.addText("Voeg toe aan thema('s)")
						.setOnClick("sendSelectedThemesTo('/addsessiontotheme');")
						.setClass("big-button"))
					.generateHtml()
				);
				requestWrapper.getContent().add("themes", sb.toString());
				requestWrapper.sendContentByWriter(response);
			}
			catch(SQLException e){
				requestWrapper.getContent().add("popup", new HtmlPopup("error", "Database probleem",
							"Er heeft zich een probleem voorgedaan waardoor de thema's" +
							"niet uit de database opgehaald kunnen worden, herlaad de pagina " +
							"om het opnieuw te proberen"));
				requestWrapper.sendContentByWriter(response);
				e.printStackTrace();
				return;
			}
		}
		else {
			requestWrapper.respondUsing(RequestWrapper.INDEX_JSP, response);
		}
	}

	/**
	 * Achterhaal welk thema gekozen is en voeg de media hieraan toe.
	 * @param request
	 * @param response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		RequestWrapper requestWrapper = new RequestWrapper(request);

		//Check of er een geldige sessie actief is voor de gebruiker.
		if(!requestWrapper.getSession().hasMediaSession()){
			requestWrapper.respondUsing(RequestWrapper.INDEX_JSP, response);
			return;
		}

		// Achterhaal welk thema gekozen is
		ArrayList<String> themeIds = requestWrapper.parseParametersByName(RequestWrapper.SELECTED_THEME_ID);
		try {
			for (String themeId : themeIds) {
				Theme theme = requestWrapper.getSqlConnector().getThemeById(Integer.parseInt(themeId));
				for (Media media : requestWrapper.getSession().getMediaSession().getAddedMedia()) {
					requestWrapper.getSqlConnector().addMediaToTheme(theme.getId(), media);
				}
			}
		} catch (SQLException | NullPointerException | NumberFormatException | ThemeNotFoundException e) {
			requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("error", "Er is iets misgegaan", 
					"Probeer de pagina opnieuw te laden."));
			requestWrapper.sendContentByWriter(response);
			e.printStackTrace();
			return;
		}
		requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("confirmation", "Media toegevoegd", 
				"De media is toegevoegd aan het thema."));
		requestWrapper.sendContentByWriter(response);
	}
}
