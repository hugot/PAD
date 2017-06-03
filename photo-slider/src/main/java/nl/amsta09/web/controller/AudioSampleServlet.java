package nl.amsta09.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.model.Audio;
import nl.amsta09.web.html.HtmlAudio;
import nl.amsta09.web.html.HtmlSection;
import nl.amsta09.web.util.RequestWrapper;

public class AudioSampleServlet extends HttpServlet{
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		RequestWrapper requestWrapper = new RequestWrapper(request);
		Audio audio;
		try{
			String audioId = requestWrapper.getParameter(RequestWrapper.SELECTED_AUDIO_ID);
			audio = requestWrapper.getSqlConnector().getMusicById(Integer.parseInt(audioId));
		} catch (NumberFormatException | NullPointerException | SQLException e){
			System.out.println("sound id niet gevonden poeplap");
			return;
		}
		System.out.println("audioId");
		response.setContentType("text/html");
		response.getWriter().write(new HtmlSection()
				.addContent("<h1> Wordt nu afgespeeld: " + audio.getName() + " </h1>")
				.addElement(new HtmlAudio(
						"playdatshit", audio.getRelativePath()))
				.generateHtml());
		response.flushBuffer();
	}
}
