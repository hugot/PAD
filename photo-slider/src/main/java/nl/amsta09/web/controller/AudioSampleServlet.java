package nl.amsta09.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.model.Audio;
import nl.amsta09.model.Theme;
import nl.amsta09.web.html.HtmlAudio;
import nl.amsta09.web.html.HtmlButton;
import nl.amsta09.web.html.HtmlSection;
import nl.amsta09.web.util.RequestWrapper;

public class AudioSampleServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		RequestWrapper requestWrapper = new RequestWrapper(request);
		Theme managedTheme = requestWrapper.getSession().getMediaSession().getManagedTheme();

		try{
			StringBuilder sb = new StringBuilder();
			requestWrapper.getSqlConnector()
				.getAllMusicsFromTheme(managedTheme)
				.listIterator().forEachRemaining((Audio audio) -> {
					sb.append(new HtmlButton()
						.addContent(audio.getName())
						.setOnClick("playMusic('" + audio.getId() + "');")
						.generateHtml());
				});
				requestWrapper.getContent().add("audio", sb.toString());
		}
		catch (SQLException e){
			requestWrapper.getContent().add("message","<p>audio is niet beschikbaar</p>");
		}
		requestWrapper.sendContentByWriter(response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		RequestWrapper requestWrapper = new RequestWrapper(request);
		Audio audio;
		try{
			String audioId = requestWrapper.parseParameter(RequestWrapper.SELECTED_AUDIO_ID);
			audio = requestWrapper.getSqlConnector().getMusicById(Integer.parseInt(audioId));
		} catch (NumberFormatException | NullPointerException | SQLException e){
			System.out.println("sound id niet gevonden poeplap");
			return;
		}
		System.out.println("audioId");
		response.setContentType("text/html");
		response.getWriter().write(new HtmlAudio(
						"playdatshit", audio.getRelativePath())
				.generateHtml());
		response.flushBuffer();
	}
}
