package nl.amsta09.web.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.model.Theme;
import nl.amsta09.web.html.HtmlPopup;
import nl.amsta09.web.util.RequestWrapper;

public class AddMusicToThemeServlet extends HttpServlet {
        
        
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		RequestWrapper requestWrapper = new RequestWrapper(request);
		Theme theme = requestWrapper.getSession().getMediaSession().getManagedTheme(); 
		int selectedAudioId;

		try {
			selectedAudioId = Integer.parseInt(request.getParameter(
						RequestWrapper.SELECTED_AUDIO_ID));
			requestWrapper.getSqlConnector().getMusicById(selectedAudioId);
		} catch(SQLException | NullPointerException | NumberFormatException e){
			HtmlPopup popup = new HtmlPopup("error", "Fout bij verwerken van request", 
					"Het is niet gelukt om uw request te verwerken, probeer het alstublieft opnieuw");
			requestWrapper.getContent().add("popup", popup);
			requestWrapper.respondUsing(RequestWrapper.THEME_MANAGEMENT_JSP, response);
			e.printStackTrace();
			return;
		}

		try {
			requestWrapper.getSqlConnector().addMediaToTheme(theme.getId(), selectedAudioId);
		} catch (SQLException e){
			HtmlPopup popup = new HtmlPopup("error", "Fout bij verbinding met de database", 
					"Het is niet gelukt om verbinding te maken met de database, probeer het alstublieft opnieuw");
			requestWrapper.getContent().add("popup", popup);
			requestWrapper.respondUsing(RequestWrapper.THEME_MANAGEMENT_JSP, response);
			e.printStackTrace();
			return;
		}
		
			HtmlPopup popup = new HtmlPopup("succes", "Succes!", 
					"De muziek is aan het thema " + theme.getName() + " toegevoegd");
			requestWrapper.getContent().add("popup", popup);
			new ThemeManagementServlet().doGet(requestWrapper.getHttpServletRequest(), response); 
	}
}

