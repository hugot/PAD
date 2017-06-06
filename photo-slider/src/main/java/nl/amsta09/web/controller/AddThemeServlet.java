package nl.amsta09.web.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.data.SqlConnector.ThemeNotFoundException;
import nl.amsta09.web.html.HtmlPopup;
import nl.amsta09.web.util.RequestWrapper;

/**
 *
 * @author Marco Bergsma, Hugo Thunnissen
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
        RequestWrapper requestWrapper = new RequestWrapper(request);

        //Haalt de naam op		
		String themeName;
        try {
			themeName = requestWrapper.parseParameter("theme");
            requestWrapper.getSqlConnector().insertTheme(themeName);
		} catch (NullPointerException | SQLException | ClassNotFoundException e) {
			requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("error", 
						"Fout bij verbinding met de database", 
						"Het is niet gelukt om verbinding te maken met de database, " + 
						"probeer het alstublieft opnieuw"));
			requestWrapper.respondUsing(RequestWrapper.THEME_MANAGEMENT_JSP, response);
            e.printStackTrace();
			return;
        }  

		requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("succes", "Succes!", 
				"Thema '" + themeName + "' is toegevoegd"));

		requestWrapper.getSession().setMediaSession();
		try {
			requestWrapper.getSession().setManagedTheme(
					requestWrapper.getSqlConnector().getThemeById(
						requestWrapper.getSqlConnector().getMaxThemeId()));
		} catch (SQLException | ThemeNotFoundException e) {
			// TODO doe hier iets nuttigs
			e.printStackTrace();
		}

		// Stuur de gebruiker door naar de theme management pagina
		new ThemeManagementServlet().doGet(requestWrapper, response);
    }

}
