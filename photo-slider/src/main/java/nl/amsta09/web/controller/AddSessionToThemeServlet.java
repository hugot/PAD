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
import nl.amsta09.web.SessionManager.Session;
import nl.amsta09.web.SessionManager.SessionNotFoundException;

public class AddSessionToThemeServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		SqlConnector conn = new SqlConnector();
		// De sessie van de gebruiker
		String sessionId = request.getParameter("sessionId");
		Session session = null;
		try {
			session = MainApp.getSessionManager().getSessionById(Integer.parseInt(sessionId));
		} catch(SessionNotFoundException e){
			//Doe niets
		}

		String themeId = request.getParameter("themeId");
		System.out.println("here we goo");
		System.out.println("themeId:" + themeId);
		Theme theme = null;
		try {
			theme = conn.getThemeById(Integer.parseInt(themeId));
			request.setAttribute("message", "media toegevoegd aan thema");
		} catch (NumberFormatException | SQLException | ThemeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("message", "Media toevoegen aan thema mislukt omdat themeid niet bekend is");
		}


		if(theme != null){
			for(Media media : session.getAddedMedia()){
				if(media instanceof Photo){
					try {
						conn.addMediaToTheme(theme.getId(), media.getId());
						request.setAttribute("message", "media toegevoegd aan thema");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						request.setAttribute("message", "Media toevoegen aan thema mislukt");
					}
				}
			}
		}
		try {
			request.getRequestDispatcher("/WEB-INF/addPhoto.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
