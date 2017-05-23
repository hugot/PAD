package nl.amsta09.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.data.SqlConnector;
import nl.amsta09.web.Content;
import nl.amsta09.web.SessionManager.Session;
import nl.amsta09.web.html.HtmlPopup;

/**
 *
 * @author Marco Bergsma
 *
 * Deze class maakt het voor de client mogelijk om een Thema toe te voegen
 *
 *
 */
@WebServlet("/addTheme")
@MultipartConfig
public class AddThemeServlet extends HttpServlet {

    /**
     * Krijgt een naam van de html pagina en maakt een gelijknamig thema in de
     * database
     *
     * @param request
     * @param response
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        SqlConnector conn = new SqlConnector();
        Content content = new Content(request, response);

        //Haalt de naam op		
        final String ThemeName = request.getParameter("name");


        try {
            conn.insertTheme(ThemeName);
		} catch (NullPointerException | SQLException | ClassNotFoundException e) {
			HtmlPopup popup = new HtmlPopup("error", "Fout bij verbinding met de database", 
					"Het is niet gelukt om verbinding te maken met de database, probeer het alstublieft opnieuw");
			content.add("popup", popup);
			content.sendUsing(Content.THEME_MANAGEMENT_JSP);
            e.printStackTrace();
			return;
        }  

		HtmlPopup popup = new HtmlPopup("succes", "Succes!", 
				"Thema '" + ThemeName + "' is toegevoegd");
		content.add("popup", popup);
		content.add("origin", "addTheme");
		new ThemeManagementServlet().doGet(content.getRequest(), content.getResponse());
    }

}
