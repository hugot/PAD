package nl.amsta09.web.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ListIterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.data.SqlConnector;
import nl.amsta09.driver.MainApp;
import nl.amsta09.model.Theme;
import nl.amsta09.web.html.Popup;

public class ThemeManagementServlet extends HttpServlet {
	private String html= "";

	protected void doGet(HttpServletRequest request, HttpServletResponse response){
		SqlConnector conn = new SqlConnector();

		// Check of er een sessionId is, zoniet, dan wordt er een sessie aangemaakt
		if(request.getAttribute("sessionId") == null){
			request.setAttribute("sessionId",MainApp.getSessionManager().newSession().getId());
		}

		ListIterator<Theme> themes;
		try {
			themes = conn.getAllThemes().listIterator();
		} catch (SQLException e1) {
			request.setAttribute("message","Het is niet gelukt om de thema's op te halen uit de database, " +
					"probeer de pagina te vernieuwen");
			return;
		}

		themes.forEachRemaining((Theme theme)-> {
			html += String.format("<li><form action=\"/thememanagement\" method=\"post\"" +
					" class=\"listitem\"> " +
					" <input type=\"hidden\" name=\"themename\" id=\"themename\" value=\"%s\"/>" +
					" <button type=\"submit\" name=\"submit\" id=\"submit\" class=\"listitembutton\">" +
					"%s</button> </form></li>\n", theme.getName(), theme.getId());
		});

		 System.out.println(html);
	 

		request.setAttribute("themes", html);

		Popup popup = new Popup("heey het lukte!", "wat mooi zeg", "hihi");

		request.setAttribute("popup", popup.getHtml());

		// Antwoord met de "themes" servlet. 
		sendContent(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		System.out.println(request.getParameter("themename").toString());
	}

	private void sendContent(HttpServletRequest request, HttpServletResponse response){
		try {
			request.getRequestDispatcher("/WEB-INF/themes.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
