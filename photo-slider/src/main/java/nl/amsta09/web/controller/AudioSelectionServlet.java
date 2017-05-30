package nl.amsta09.web.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.model.Audio;
import nl.amsta09.web.html.HtmlPopup;
import nl.amsta09.web.html.HtmlSection;
import nl.amsta09.web.util.RequestWrapper;
import nl.amsta09.web.html.HtmlDiv;
import nl.amsta09.web.html.HtmlForm;
import nl.amsta09.web.html.HtmlImage;

public class AudioSelectionServlet extends HttpServlet {
	private static final String AUDIO_SELECTION_JSP = "/WEB-INF/audio-selection.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		RequestWrapper requestWrapper = new RequestWrapper(request);
		
		//check of er een media sessie actief is voor de gebruiker.
		if(!requestWrapper.getSession().hasMediaSession()){
			requestWrapper.respondUsing(RequestWrapper.INDEX_JSP, response);
			return;
		}

		ArrayList<Audio>  audioList;
		try {
			audioList = requestWrapper.getSqlConnector().getAllAudio();
		} catch (ClassNotFoundException | SQLException e) {
			HtmlPopup popup = new HtmlPopup("error",
					"Database fout", "het is niet gelukt om de foto's op" +
					"te halen uit de database. Probeer de pagina opnieuw te laden");
			requestWrapper.getContent().add("popup", popup);
			requestWrapper.respondUsing(AUDIO_SELECTION_JSP, response);
			e.printStackTrace();
			return;
		}


		// Maak de html elementen aan voor alle audio.
		HtmlSection audioSection = new HtmlSection("main-section", "main-section");
		audioList.listIterator().forEachRemaining((Audio audio) -> {
			HtmlForm form = new HtmlForm("" + audio.getId(), "floating-image", "post", "/addmediatotheme");
			HtmlImage image = new HtmlImage("" + audio.getId(), "image", audio.getRelativePath());
			image.setHeight(150);
			HtmlDiv audioDiv = new HtmlDiv();
			audioDiv.setClass("audio-container");
			audioDiv.addElement(image);
			form.addElement(audioDiv);
			form.addContent("<p>" + audio.getName() + "</p>");
			form.addHiddenValue(RequestWrapper.SELECTED_AUDIO_ID, "" + audio.getId());
			form.addInput("submit", "kies" ,"kies");
			audioSection.addElement(form);
		});
		requestWrapper.getContent().add("audio", audioSection);
		requestWrapper.respondUsing(AUDIO_SELECTION_JSP, response);
	}
}

