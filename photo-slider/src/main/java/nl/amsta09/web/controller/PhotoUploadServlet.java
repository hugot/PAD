package nl.amsta09.web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

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
public class PhotoUploadServlet extends HttpServlet { 

	/**
	 * Haal een bestand op uit de html form en plaats dit in de "Resources/Foto" map
	 *
	 * @param request
	 * @param response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
		response.setContentType("text/html;charset=UTF-8");

		//Haal het bestand en de bestandsnaam op
		final Part filePart = request.getPart("file");
		final String fileName = filePart.getName();
		final String destinationdir = "Resources" + File.separator + "Foto" + File.separator;

		OutputStream out= null;
		InputStream fileContent = null;
		final PrintWriter writer = response.getWriter();

		try {
			out = new FileOutputStream(new File(destinationdir + fileName));
			fileContent = filePart.getInputStream();
			
			int read = 0;
			final byte[] bytes = new byte[1024];

			while ((read = fileContent.read(bytes)) != -1){
				out.write(bytes, 0, read);
			}
			writer.println("Nieuw Bestand: " + fileName + "Gemaakt in : " + destinationdir);
		} catch (FileNotFoundException e ){
			System.out.println("DEBUG: Bestand niet gevonden");
			e.printStackTrace();
			writer.println("Bestand uploaden niet gelukt"); 
		} finally {
			if(	out != null ) {
				out.close(); 
			}
			if( fileContent != null ) {
				fileContent.close();
			}
			if( writer != null ) {
				writer.close();
			}
		}
	}
}
