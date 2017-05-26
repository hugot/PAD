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
import nl.amsta09.model.Audio;
import nl.amsta09.web.html.HtmlPopup;
import nl.amsta09.web.html.HtmlSection;
import nl.amsta09.web.html.HtmlDiv;
import nl.amsta09.web.html.HtmlForm;
import nl.amsta09.web.html.HtmlImage;

public class AudioSelectionServlet extends HttpServlet {
	private static final String AUDIO_SELECTION_JSP = "/WEB-INF/audio-selection.jsp";
	private static final String SELECTED_AUDIO_ID = "selectedAudioId";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		SqlConnector conn = new SqlConnector();
		Content content = new Content(request, response);
		
		//check of er een sessie actief is voor de gebruiker.
		if(!content.hasSession()){
			content.sendUsing(Content.INDEX_JSP);
			return;
		}
		ArrayList<Audio>  audioList;
		try {
			audioList = conn.getAllAudio();
		} catch (ClassNotFoundException | SQLException e) {
			HtmlPopup popup = new HtmlPopup("error",
					"Database fout", "het is niet gelukt om de foto's op" +
					"te halen uit de database. Probeer de pagina opnieuw te laden");
			content.add("popup", popup);
			content.sendUsing(AUDIO_SELECTION_JSP);
			e.printStackTrace();
			return;
		}


		// Maak de html elementen aan voor alle audio.
		HtmlSection audioSection = new HtmlSection("main-section", "main-section");
		audioList.listIterator().forEachRemaining((Audio audio) -> {
			HtmlForm form = new HtmlForm("" + audio.getId(), "floating-image", "post", "/addmediatotheme");
			HtmlImage image = new HtmlImage("" + audio.getId(), "audio", audio.getRelativePath());
			image.setHeight(150);
			HtmlDiv audioDiv = new HtmlDiv();
			audioDiv.setClass("audio-container");
			audioDiv.addElement(image);
			form.addElement(audioDiv);
			form.addContent("<p>" + audio.getName() + "</p>");
			form.addHiddenValue(SELECTED_AUDIO_ID, "" + audio.getId());
			form.addInput("submit", "kies" ,"kies");
			audioSection.addElement(form);
		});
		content.add("audio", audioSection);
		content.sendUsing(AUDIO_SELECTION_JSP);
	}
}

