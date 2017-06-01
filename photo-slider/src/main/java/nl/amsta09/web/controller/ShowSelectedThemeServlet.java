/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amsta09.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.amsta09.driver.MainApp;
import nl.amsta09.model.Theme;
import nl.amsta09.web.html.HtmlPopup;
import nl.amsta09.web.util.RequestWrapper;

/**
 *
 * @author Space
 */
public class ShowSelectedThemeServlet extends HttpServlet {
    
    private RequestWrapper requestWrapper;
    private Theme selectedTheme;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
                requestWrapper = new RequestWrapper(request);
		// Zorg dat de sessie een mediasessie is.
		requestWrapper.getSession().setMediaSession();

		// Geselecteerde thema
		if(requestWrapper.getSession().hasManagedTheme()){
			selectedTheme = requestWrapper.getSession().getMediaSession().getManagedTheme();
                }

            HtmlPopup popup;
            MainApp.getSlideShowController().setTheme(selectedTheme);
            popup = new HtmlPopup("succes", "thema getoond", "Dit thema word nu in de Slideshow getoond.");
                
            requestWrapper.getContent().add(HtmlPopup.CLASS, popup);
            new ThemeManagementServlet().doGet(requestWrapper, response);
            
	}
}
