package nl.amsta09.web.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.amsta09.model.Theme;
import nl.amsta09.web.html.HtmlPopup;
import nl.amsta09.web.util.RequestWrapper;

public class DeleteThemeServlet extends HttpServlet {
    
    private RequestWrapper requestWrapper;
    private Theme selectedTheme;
    private HttpServletResponse response;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
                requestWrapper = new RequestWrapper(request);
		this.response = response;
		// Zorg dat de sessie een mediasessie is.
		requestWrapper.getSession().setMediaSession();

		// Geselecteerde thema
		if(requestWrapper.getSession().hasManagedTheme()){
			selectedTheme = requestWrapper.getSession().getMediaSession().getManagedTheme();
        }

            HtmlPopup popup;
            try {
                requestWrapper.getSqlConnector().deleteTheme(selectedTheme);
                popup = new HtmlPopup("succes", "thema verwijdert", "Het thema is succesvol verwijdert");
            } catch (SQLException ex) {
                Logger.getLogger(DeleteThemeServlet.class.getName()).log(Level.SEVERE, null, ex);
                popup = new HtmlPopup("error", "thema niet verwijdert", "Het is niet gelukt het thema te verwijderen");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DeleteThemeServlet.class.getName()).log(Level.SEVERE, null, ex);
                popup = new HtmlPopup("error", "thema niet verwijdert", "Het is niet gelukt het thema te verwijderen");

            }
            requestWrapper.getContent().add(HtmlPopup.CLASS, popup);
            requestWrapper.sendContentByWriter(response);
	}
}
