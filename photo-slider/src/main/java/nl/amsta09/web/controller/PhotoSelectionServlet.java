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
import nl.amsta09.web.html.HtmlDiv;
import nl.amsta09.web.html.HtmlForm;
import nl.amsta09.web.html.HtmlImage;

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
			photoList = requestWrapper.getSqlConnector().getAllPhoto();
		} catch (ClassNotFoundException | SQLException e) {
			requestWrapper.getContent().add(HtmlPopup.CLASS, new HtmlPopup("error",
					"Database fout", "het is niet gelukt om de foto's op" +
					"te halen uit de database. Probeer de pagina opnieuw te laden"));
			new ThemeManagementServlet().doGet(requestWrapper.getHttpServletRequest(), response);
			e.printStackTrace();
			return;
		}


		// Maak de html elementen aan voor alle foto's
		HtmlSection photoSection = new HtmlSection("main-section", "main-section");
		photoList.listIterator().forEachRemaining((Photo photo) -> {
			photoSection.addElement(new HtmlForm("" + photo.getId(), "floating-image", "post", "/addmediatotheme")
					.addElement(new HtmlDiv()
						.setClass("photo-container")
						.addElement(new HtmlImage("" + photo.getId(), "photo", photo.getRelativePath())
						.setHeight(150)))
					.addContent("<p>" + photo.getName() + "</p>")
					.addHiddenValue(RequestWrapper.SELECTED_PHOTO_ID, "" + photo.getId())
					.addInput("checkbox", "kies" ,"kies")
					);
		});
		requestWrapper.getContent().add(RequestWrapper.PHOTO_LIST, photoSection);
		requestWrapper.respondUsing(RequestWrapper.PHOTO_SELECTION_JSP, response);
	}
}
