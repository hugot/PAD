package nl.amsta09.web.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.model.Photo;
import nl.amsta09.web.html.HtmlPopup;
import nl.amsta09.web.html.HtmlSection;
import nl.amsta09.web.util.RequestWrapper;
import nl.amsta09.web.html.HtmlButton;

/**
 * Deze class stelt een lijst samen van de foto's in een thema en genereert hier
 * HTML elementen voor.
 * 
 * @author Hugo Thunnissen
 */
public class DeletePhotoSelection extends HttpServlet {
	private RequestWrapper requestWrapper;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		requestWrapper = new RequestWrapper(request);
		
		if(!requestWrapper.getSession().hasMediaSession()){
			requestWrapper.respondUsing(RequestWrapper.INDEX_JSP, response);
			return;
		}

		ArrayList<Photo>  photoList;
		try {
			photoList = requestWrapper.getSqlConnector().getAllPhotosFromTheme(
				requestWrapper.getSession().getMediaSession().getManagedTheme()
			);
		} catch (SQLException e) {
			requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("error",
					"Database fout", "het is niet gelukt om de foto's op" +
					"te halen uit de database. Probeer de pagina opnieuw te laden"));
			requestWrapper.sendContentByWriter(response);
			e.printStackTrace();
			return;
                        
		}

		// Maak de html elementen aan voor alle foto's
		requestWrapper.getContent().addPhotoSelectionList(photoList);
		requestWrapper.getContent().add("bottom-bar", new StringBuilder()
			.append(new HtmlSection()
				.setClass("bottom-bar")
				.addElement(new HtmlButton()
					.setClass("big-button")
					.setOnClick("deleteMedia();")
					.setContent("Verwijder foto's"))
				.generateHtml())
			.toString()
		);
		requestWrapper.sendContentByWriter(response);
	}
}
