/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Space
 */
public class ShowThemeFirstServlet extends HttpServlet {
    
    private RequestWrapper requestWrapper;
    private Theme selectedTheme;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        System.out.println("hallooo");
                requestWrapper = new RequestWrapper(request);
		// Zorg dat de sessie een mediasessie is.
		requestWrapper.getSession().setMediaSession();

		// Geselecteerde thema
		if(requestWrapper.getSession().hasManagedTheme()){
			selectedTheme = requestWrapper.getSession().getMediaSession().getManagedTheme();
                }
                
                try{
                    requestWrapper.getSqlConnector().setThemeFirst(selectedTheme);
                    requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("succes", "thema als eerste gezet", "Dit thema word nu als eerste in de Slideshow getoond wanneer het wordt opgestart."));
                } catch (SQLException ex) {
                    requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("error", 
						"Fout bij verbinding met de database", 
						"Het is niet gelukt om verbinding te maken met de database, probeer het " +
						"alstublieft opnieuw"));
                } catch (ClassNotFoundException ex) {
                    requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("error", 
						"Fout bij verbinding met de database", 
						"Het is niet gelukt om verbinding te maken met de database, probeer het " +
						"alstublieft opnieuw"));
                }
            new ThemeManagementServlet().doGet(requestWrapper, response);
            
	}
}
