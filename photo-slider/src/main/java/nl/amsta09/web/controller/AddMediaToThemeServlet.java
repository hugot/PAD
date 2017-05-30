package nl.amsta09.web.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.data.SqlConnector;
import nl.amsta09.model.Media;
import nl.amsta09.model.Theme;
import nl.amsta09.web.util.RequestWrapper;
import nl.amsta09.web.html.HtmlPopup;

public class AddMediaToThemeServlet extends HttpServlet {
        
        Media media;
	int selectedMediaId;
                
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		RequestWrapper requestWrapper = new RequestWrapper(request);

		// Stuur door naar index als geen mediasessie actief is.
		if(requestWrapper.redirectToIndexIf(
					!requestWrapper.getSession().hasMediaSession(), response))
			return;

		// Thema dat op dit moment beheerd wordt.
		Theme theme = requestWrapper.getSession().getMediaSession().getManagedTheme();
                

		try {
                    if(requestWrapper.getParameter(RequestWrapper.SELECTED_PHOTO_ID) != null){
                        selectedMediaId = Integer.parseInt(requestWrapper.getParameter(
						RequestWrapper.SELECTED_PHOTO_ID));
                        media = requestWrapper.getSqlConnector().getPhotoById(selectedMediaId);
                        
			requestWrapper.getSqlConnector().getPhotoById(selectedMediaId);
                    }
                    if(requestWrapper.getParameter(RequestWrapper.SELECTED_AUDIO_ID) != null){
                        selectedMediaId = Integer.parseInt(requestWrapper.getParameter(
						RequestWrapper.SELECTED_AUDIO_ID));
                        media = requestWrapper.getSqlConnector().getMusicById(selectedMediaId);
			requestWrapper.getSqlConnector().getMusicById(selectedMediaId);
                    }
			
		} catch(SQLException | NullPointerException | NumberFormatException e){
			requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("error", 
						"Fout bij verwerken van request", 
						"Het is niet gelukt om de door u geselecteerde bestand te vinden, probeer het " + 
						"alstublieft opnieuw"));
			requestWrapper.respondUsing(RequestWrapper.THEME_MANAGEMENT_JSP, response);
			e.printStackTrace();
			return;
		}

		try {
			requestWrapper.getSqlConnector().addMediaToTheme(theme.getId(), media);
		} catch (SQLException e){
			requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("error", 
						"Fout bij verbinding met de database", 
						"Het is niet gelukt om verbinding te maken met de database, probeer het " +
						"alstublieft opnieuw"));
			requestWrapper.respondUsing(RequestWrapper.THEME_MANAGEMENT_JSP, response);
			e.printStackTrace();
			return;
		}
		
			requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("succes", "Succes!", 
					 media.getName() + " is aan het thema " + theme.getName() + " toegevoegd"));
			new ThemeManagementServlet().doGet(requestWrapper.getHttpServletRequest(), response);
	}
}
