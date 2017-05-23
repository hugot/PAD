package nl.amsta09.web.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.data.SqlConnector;
import nl.amsta09.web.Content;
import nl.amsta09.model.Photo;
import nl.amsta09.web.html.HtmlPopup;
import nl.amsta09.web.html.HtmlSection;
import nl.amsta09.web.html.HtmlDiv;
import nl.amsta09.web.html.HtmlForm;
import nl.amsta09.web.html.HtmlImage;

public class PhotoSelectionServlet extends HttpServlet {
	private static final String PHOTO_SELECTION_JSP = "/WEB-INF/photo-selection.jsp";
	private static final String SELECTED_PHOTO_ID = "selectedPhotoId";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		SqlConnector conn = new SqlConnector();
		Content content = new Content(request, response);
		
		//check of er een sessie actief is voor de gebruiker.
		if(!content.hasSession()){
			content.sendUsing(Content.INDEX_JSP);
			return;
		}
		ArrayList<Photo>  photoList;
		try {
			photoList = conn.getAllPhoto();
		} catch (ClassNotFoundException | SQLException e) {
			HtmlPopup popup = new HtmlPopup("error",
					"Database fout", "het is niet gelukt om de foto's op" +
					"te halen uit de database. Probeer de pagina opnieuw te laden");
			content.add("popup", popup);
			content.sendUsing(PHOTO_SELECTION_JSP);
			e.printStackTrace();
			return;
		}


		// Maak de html elementen aan voor alle foto's
		HtmlSection photoSection = new HtmlSection("main-section", "main-section");
		photoList.listIterator().forEachRemaining((Photo photo) -> {
			HtmlForm form = new HtmlForm("" + photo.getId(), "floating-image", "post", "/addmediatotheme");
			HtmlImage image = new HtmlImage("" + photo.getId(), "photo", photo.getRelativePath());
			image.setHeight(150);
			HtmlDiv photoDiv = new HtmlDiv();
			photoDiv.setClass("photo-container");
			photoDiv.addElement(image);
			form.addElement(photoDiv);
			form.addContent("<p>" + photo.getName() + "</p>");
			form.addHiddenValue(SELECTED_PHOTO_ID, "" + photo.getId());
			form.addInput("submit", "kies" ,"kies");
			photoSection.addElement(form);
		});
		content.add("photos", photoSection);
		content.sendUsing(PHOTO_SELECTION_JSP);
	}
}
