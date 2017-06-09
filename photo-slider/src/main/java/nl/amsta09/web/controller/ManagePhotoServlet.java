package nl.amsta09.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;


import nl.amsta09.model.Theme;
import nl.amsta09.web.html.HtmlPopup;
import nl.amsta09.web.util.RequestWrapper;

/**
 * Deze class verwijdert een foto uit een thema
 *
 * @author Marco Bergsma, Hugo Thunnissen
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

        // Verwijder de foto's uit het thema dat op dit moment beheerd wordt.
        try {
            Theme theme = requestWrapper.getSession().getMediaSession().getManagedTheme();
            for(String selectedMediaId : 
                requestWrapper.parseParametersByName(RequestWrapper.SELECTED_MEDIA_ID)){
                    requestWrapper.getSqlConnector().deleteMediaFromTheme(theme.getId()
                        , Integer.parseInt(selectedMediaId)
                     );
                }
        } catch (SQLException | NullPointerException | NumberFormatException e) {
            requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("error",
                    "Fout bij verwerken van request",
                    "Het is niet gelukt om de door u geselecteerde foto te vinden, probeer het"
                    + "alstublieft opnieuw"));
            e.printStackTrace();
        }
        requestWrapper.sendContentByWriter(response);
    }
}
