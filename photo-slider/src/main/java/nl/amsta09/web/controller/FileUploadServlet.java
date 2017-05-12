package nl.amsta09.web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import nl.amsta09.model.Audio;
import nl.amsta09.model.Media;
import nl.amsta09.model.Photo;

/**
 *
 * @author Hugo Thunnissen
 *
 * Deze class maakt het voor de client mogelijk om een foto te uploaden naar
 * de server.
 * 
 */

@WebServlet("/uploadphoto")
@MultipartConfig
public class FileUploadServlet extends HttpServlet { 

	/**
	 * Haal een bestand op uit de html form en plaats dit in de daarvoor bestemde app
	 *
	 * @param request
	 * @param response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
		response.setContentType("text/html;charset=UTF-8");

		// Het media object en de attributen waar het mee geinstantieerd wordt
		boolean savingFileSucceeded = false;
		Part filePart = null;
		String fileName;
		String destinationdir = "Resources" + File.separator;
		Media media;
		// De in en outputstream voor het lezen en schrijven van het bestand
		OutputStream out= null;
		InputStream fileContent = null;


		// Check om wat voor bestand het gaat, instantieer vervolgens een object van het juiste type
		if(request.getPart("file") != null){
			 System.out.println("Je gebruikt nog de standaardwaarde *file* voor de filepart naam");
			 return;
		}
		else if(request.getPart("photo") != null){
			filePart = request.getPart("photo");
			destinationdir += "Foto" + File.separator;;
			fileName = getFilenameFromFilePart(filePart);
			media = new Photo(new URL(destinationdir + fileName), fileName);
		}
		else if(request.getPart("sound") != null){
			filePart = request.getPart("sound");
			destinationdir += "Audio" + File.separator;;
			fileName = getFilenameFromFilePart(filePart);
			media = new Audio(new URL(destinationdir + fileName), fileName);
		}
		else {
			System.out.println("geen filepart met een geldige naam gevonden.");
			return;
		}

		// Schrijf het bestand naar de juiste map
		try {
			out = new FileOutputStream(new File(media.getFilePath().toString()));
			fileContent = filePart.getInputStream();
			int read = 0;
			final byte[] bytes = new byte[1024];
			while ((read = fileContent.read(bytes)) != -1){
				out.write(bytes, 0, read);
			}
			savingFileSucceeded = true;
		} catch (FileNotFoundException e ){
			e.printStackTrace();
		} finally {
			if(	out != null ) {
				out.close(); 
			}
			if( fileContent != null ) {
				fileContent.close();
			}
		}

		// Voeg bestand toe aan de database
		if(savingFileSucceeded){
			if(media instanceof Photo){
				//TODO: Voer query uit om foto aan database toe te voegen 
			}
			if(media  instanceof Audio){

				//TODO: Voer query uit om audio aan database toe te voegen 
			}
		}
		else {
			//TODO: doe hier iets nuttigs
			return;
		}

		request.getRequestDispatcher("/WEB-INF/addPhoto.jsp").forward(request, response);
	}
	
	/**
	 * Deze methode haalt de naam van het bestand uit de content-disposition header van
	 * het filePart object
	 * @param filePart
	 * @return fileName
	 */
	private static String getFilenameFromFilePart(Part filePart) {
		
		String fileName = null;
		String header = filePart.getHeader("content-disposition");
		Pattern filenamePattern = Pattern.compile("filename=\"(.*)\"");
		Matcher matcher = filenamePattern.matcher(header);
		if(matcher.find()){
			fileName = matcher.group(1);
		}
		else {
			//TODO schrijf een query die de databaseID gebruikt als naam
			return "default";
		}
		return fileName;
	}
}
