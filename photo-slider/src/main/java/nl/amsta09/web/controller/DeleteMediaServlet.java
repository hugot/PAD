package nl.amsta09.web.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.model.Media;
import nl.amsta09.model.Theme;
import nl.amsta09.web.util.RequestWrapper;
import nl.amsta09.web.html.HtmlPopup;

public class DeleteMediaServlet extends HttpServlet {
    int selectedMediaId;
    Media media;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			
            throws ServletException, IOException{
        
		RequestWrapper requestWrapper = new RequestWrapper(request);
                try{
                     selectedMediaId = Integer.parseInt(requestWrapper.getParameter(
		RequestWrapper.SELECTED_PHOTO_ID));
		media = requestWrapper.getSqlConnector().getPhotoById(selectedMediaId);

	requestWrapper.getSqlConnector().getPhotoById(selectedMediaId);
                requestWrapper.getSqlConnector().deleteMedia(media.getId());
                }
             catch(SQLException | NullPointerException | NumberFormatException e){ 
			requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("error", 
						"Fout bij verwerken van request", 
						"Het is niet gelukt om de door u geselecteerde bestand te vinden, probeer het " + 
						"alstublieft opnieuw"));
			new DeleteMediaServlet().doGet(requestWrapper.getHttpServletRequest(), response);
			e.printStackTrace();
			return;
		} catch (ClassNotFoundException e) {
                    requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("error", 
						"Fout bij verwerken van request", 
						"Het is niet gelukt om de door u geselecteerde bestand te vinden, probeer het " + 
						"alstublieft opnieuw"));
			new DeleteMediaServlet().doGet(requestWrapper.getHttpServletRequest(), response);
			e.printStackTrace();
			return;
           
        }
               
     new DeletePhotoSelection().doGet(requestWrapper.getHttpServletRequest(), response);
}

}
