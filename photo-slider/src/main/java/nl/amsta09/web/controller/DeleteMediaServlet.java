package nl.amsta09.web.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.model.Media;
import nl.amsta09.web.util.RequestWrapper;
import nl.amsta09.web.html.HtmlPopup;

/**
 * Verwijder een mediabestand uit de database en uit de opslag met deze class.
 * 
 * @author Hugo Thunnissen
 */
public class DeleteMediaServlet extends HttpServlet {
    int selectedMediaId;
    Media media;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
		RequestWrapper requestWrapper = new RequestWrapper(request);
        try{
            for(String selectedMediaId : 
                requestWrapper.parseParametersByName(RequestWrapper.SELECTED_MEDIA_ID)){
					int mediaIdInt = Integer.parseInt(selectedMediaId);
					Media media = requestWrapper.getSqlConnector().getPhotoById(mediaIdInt);
					File mediaFile = new File(media.getRelativePath());
					mediaFile.delete();
	                requestWrapper.getSqlConnector().deleteMedia(mediaIdInt);
                }
         } catch(SQLException | NullPointerException | NumberFormatException e){ 
			requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("error", 
						"Fout bij verwerken van request", 
						"Het is niet gelukt om de door u geselecteerde bestand te vinden, probeer het " + 
						"alstublieft opnieuw"));
		 }
		 requestWrapper.sendContentByWriter(response);
	}
}
