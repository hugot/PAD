package nl.amsta09.web.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.model.Media;
import nl.amsta09.model.Theme;
import nl.amsta09.web.util.RequestWrapper;
import nl.amsta09.web.html.HtmlPopup;

/**
 * Deze servlet dient voor het toevoegen van media aan een thema.
 * 
 * @author Hugo Thunnissen
 */
public class AddMediaToThemeServlet extends HttpServlet {
                
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		RequestWrapper requestWrapper = new RequestWrapper(request);

		// Stuur door naar index als geen mediasessie actief is.
		if(requestWrapper.redirectToIndexIf(
					!requestWrapper.getSession().hasMediaSession(), response))
			return;

		// Thema dat op dit moment beheerd wordt.
		Theme theme = requestWrapper.getSession().getMediaSession().getManagedTheme();

		// Voeg alle media die in de request wordt vermeldt toe aan het thema.
		try {
			for(String mediaId : requestWrapper.parseParametersByName(
				RequestWrapper.SELECTED_MEDIA_ID)){
					int mediaIdInt = Integer.parseInt(mediaId);
					requestWrapper.getSqlConnector().addMediaToTheme(
						requestWrapper.getSession().getMediaSession().getManagedTheme().getId()
						, new Media(){{this.setId(mediaIdInt);}});
			}
		} catch(SQLException | NullPointerException | NumberFormatException e){ 
			requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("error", 
						"Fout bij verwerken van request", 
						"Het is niet gelukt om de door u geselecteerde bestand te vinden, probeer het " + 
						"alstublieft opnieuw"));
			new ThemeManagementServlet().doGet(requestWrapper, response);
			e.printStackTrace();
			return;
		}
		new ThemeManagementServlet().doGet(requestWrapper, response);
	}
}
