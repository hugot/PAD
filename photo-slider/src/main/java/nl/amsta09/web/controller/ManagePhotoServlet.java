package nl.amsta09.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;

import javax.servlet.http.HttpServlet;

import nl.amsta09.model.Theme;
import nl.amsta09.web.html.HtmlPopup;
import nl.amsta09.web.util.RequestWrapper;

/**
 *
 *
 *
 * @author Marco Bergsma
 *
 */
public class ManagePhotoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestWrapper requestWrapper = new RequestWrapper(request);

        // Stuur door naar index als geen mediasessie actief is.
        if (requestWrapper.redirectToIndexIf(
                !requestWrapper.getSession().hasMediaSession(), response)) {

            return;

        }

        // Thema dat op dit moment beheerd wordt.
        Theme theme = requestWrapper.getSession().getMediaSession().getManagedTheme();

        int selectedPhotoId;

        try {

            selectedPhotoId = Integer.parseInt(requestWrapper.getParameter(
                    RequestWrapper.SELECTED_PHOTO_ID));

            requestWrapper.getSqlConnector().getPhotoById(selectedPhotoId);

        } catch (SQLException | NullPointerException | NumberFormatException e) {

            requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("error",
                    "Fout bij verwerken van request",
                    "Het is niet gelukt om de door u geselecteerde foto te vinden, probeer het"
                    + "alstublieft opnieuw"));

            new ThemeManagementServlet().doGet(requestWrapper, response);

            e.printStackTrace();

            return;

        }

        try {

            requestWrapper.getSqlConnector().deleteMediaFromTheme(theme.getId(), selectedPhotoId);

        } catch (SQLException e) {

            requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("error",
                    "Fout bij verbinding met de database",
                    "Het is niet gelukt om verbinding te maken met de database, probeer het "
                    + "alstublieft opnieuw"));

            new ThemeManagementServlet().doGet(requestWrapper, response);

            e.printStackTrace();

            return;

        }

        requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("succes", "Succes!",
                "De foto is van het thema " + theme.getName() + " verwijderd"));

        new ThemeManagementServlet().doGet(requestWrapper, response);

    }
}
