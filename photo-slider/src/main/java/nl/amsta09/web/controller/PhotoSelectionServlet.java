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
import nl.amsta09.web.html.HtmlDiv;
import nl.amsta09.web.html.HtmlImage;

/**
 * Deze class genereert een lljst met alle foto's die niet in het gegeven thema
 * staan en de HTML elementen waarmee deze geselecteerd kunnen worden door de
 * gebruiker.
 * 
 * @author Hugo Thunnissen
 */
public class PhotoSelectionServlet extends HttpServlet {
	private RequestWrapper requestWrapper;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		requestWrapper = new RequestWrapper(request);
		
		//check of er een media sessie actief is voor de gebruiker.
		if(!requestWrapper.getSession().hasMediaSession()){
			requestWrapper.respondUsing(RequestWrapper.INDEX_JSP, response);
			return;
		}

		ArrayList<Photo>  photoList;
		try {
			photoList = requestWrapper.getSqlConnector().getAllPhotosNotInTheme(
				requestWrapper.getSession().getMediaSession().getManagedTheme()
			);
		} catch (SQLException e) {
			System.out.println("gefaald");
			requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("error",
					"Database fout", "het is niet gelukt om de foto's op" +
					"te halen uit de database. Probeer de pagina opnieuw te laden"));
			new ThemeManagementServlet().doGet(requestWrapper, response);
			e.printStackTrace();
			return;
		}


		// Maak de html elementen aan voor alle foto's
		StringBuilder sb = new StringBuilder();
		photoList.listIterator().forEachRemaining((Photo photo) -> {
			sb.append(new HtmlButton()
					.setClass("floating-image")
					.setId("image-div" + photo.getId())
					.addElement(new HtmlDiv()
						.setClass("photo-container")
						.addElement(new HtmlImage("image" + photo.getId(), "photo", photo.getRelativePath())
						.setHeight(150)))
					.setOnClick("selectMedia('image-div" + photo.getId() + "', '"+ photo.getId()+ "');")
					.addContent("<h4>" + photo.getName() + "</h4>")
					.generateHtml()
			);
		});
		requestWrapper.getContent().add("bottom-bar", new StringBuilder()
			.append(new HtmlSection()
				.setClass("bottom-bar")
				.addElement(new HtmlButton()
					.setClass("big-button")
					.setOnClick("addMediaToTheme();")
					.setContent("Voeg toe aan thema"))
				.generateHtml())
			.toString()
		);
		requestWrapper.getContent().add(RequestWrapper.PHOTO_LIST, sb.toString());
		requestWrapper.sendContentByWriter(response);
	}
}
