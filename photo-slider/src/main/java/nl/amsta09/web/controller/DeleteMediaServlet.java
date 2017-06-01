/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amsta09.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import nl.amsta09.model.Audio;
import nl.amsta09.model.Media;
import nl.amsta09.model.Photo;
import nl.amsta09.web.util.RequestWrapper;

/**
 *
 * @author janma
 */
public class DeleteMediaServlet{
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ClassNotFoundException{
		System.out.println("whaddup1");

		RequestWrapper requestWrapper = new RequestWrapper(request);
		System.out.println("whaddup");

		// Verzeker dat de sessie een mediaSessie is.
		requestWrapper.getSession().setMediaSession();

		System.out.println(requestWrapper.getSession().getMediaSession().getId());

		// Het media object en de attributen waar het mee geinstantieerd wordt
		boolean savingFileSucceeded = false;
		Part filePart = null;
		String fileName;
		String destinationDir;
		Media media;

		// De in- en outputstream voor het lezen en schrijven van het bestand
		OutputStream out= null;
		InputStream fileContent = null;

		// Check om wat voor bestand het gaat, instantieer vervolgens een object van het juiste type
		if(request.getPart("file") != null){
			 System.out.println("DEBUG: Je gebruikt nog de standaardwaarde *file* voor de filepart naam");
			 return;
		}
		else if(request.getPart("photo") != null){
			filePart = request.getPart("photo");
			destinationDir = Photo.DIRECTORY;
			fileName = getFilenameFromFilePart(filePart);
			media = new Photo(destinationDir + fileName, fileName, 1);
		}
		else if(request.getPart("sound") != null){
			filePart = request.getPart("sound");
			destinationDir = Audio.DIRECTORY;
			fileName = getFilenameFromFilePart(filePart);
			media = new Audio(destinationDir + fileName, fileName, 1);
		}
		else {
			System.out.println("DEBUG: geen filepart met een geldige naam gevonden.");
			return;
		}
                
                try{
				requestWrapper.getSqlConnector().deleteMedia(media.getId());
				media.setId(requestWrapper.getSqlConnector().getMediaIdFrom(media));
				requestWrapper.getSession().getMediaSession().getAddedMedia().add(media);
			}
			catch(SQLException e){
				sendErrorMessage(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Het is niet gelukt om bestand " + media.getName() + " aan de database toe te voegen");
				e.printStackTrace();
			}
    }
                private static String getFilenameFromFilePart(Part filePart) {
		String fileName = null;
		String header = filePart.getHeader("content-disposition");
		Pattern filenamePattern = Pattern.compile("filename=\"(.*)\"");
		Matcher matcher = filenamePattern.matcher(header);
		if(matcher.find()){
			fileName = matcher.group(1);
		}
		else {
			fileName = "onbekend";
		}
		return fileName;
	}
                
                private static void sendErrorMessage(HttpServletResponse response, int status, String message) throws IOException {
		response.setStatus(status);
		response.getWriter().write(message);
		response.flushBuffer();
	}

}