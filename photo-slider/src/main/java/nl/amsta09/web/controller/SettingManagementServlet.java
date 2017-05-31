package nl.amsta09.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.amsta09.driver.MainApp;
import nl.amsta09.web.html.HtmlPopup;
import nl.amsta09.web.util.RequestWrapper;

public class SettingManagementServlet extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		RequestWrapper requestWrapper = new RequestWrapper(request);
		requestWrapper.respondUsing("/WEB-INF/settings.jsp", response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
            RequestWrapper requestWrapper = new RequestWrapper(request);
            String requestMapping = request.getServletPath();
                if(requestMapping.equals("/turnAudioOnOff")){
                    MainApp.getSlideShowController().getSettings().setSound(!MainApp.getSlideShowController().getSettings().getSound());
                    HtmlPopup popup;
                    if(MainApp.getSlideShowController().getSettings().getSound()){
                        popup = new HtmlPopup("succes", "geluid aan", "Het geluid voor de slideshow staat nu aan");
                    }else{
                        popup = new HtmlPopup("succes", "geluid uit", "Het geluid voor de slideshow staat nu uit");
                    }
                    requestWrapper.getContent().add(HtmlPopup.CLASS, popup);
                    new ThemeManagementServlet().doGet(requestWrapper, response);
                }
	}
}
