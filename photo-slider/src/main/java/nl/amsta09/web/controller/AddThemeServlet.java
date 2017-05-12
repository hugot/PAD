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

        //Haalt de naam op		
        final String ThemeName = request.getParameter("name");
        Connection connection = null;

        System.out.println("INPUT: " + ThemeName);

        try {

            new SqlConnector().insertTheme(ThemeName);
//            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://localhost/photoslider", "root", "Aapjes-14");
//
//            Statement addThemeStatement = connection.createStatement();
//            //Insert de gegevens in de database met een unieke ID
//            String sql = ("insert into theme (name) VALUES ('" + ThemeName + "')");
//
//            addThemeStatement.execute(sql);
        } catch (SQLException e) {
            System.out.println("DEBUG: fout in query");
            e.printStackTrace();
            final String message = "Thema toevoegen mislukt";
            request.setAttribute("message", message);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddThemeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getRequestDispatcher("/WEB-INF/themes.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        System.out.println("end my suffering pls");
    }
}
