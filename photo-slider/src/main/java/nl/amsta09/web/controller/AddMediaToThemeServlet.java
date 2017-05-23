package nl.amsta09.web.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.data.SqlConnector;
import nl.amsta09.model.Photo;
import nl.amsta09.model.Theme;
import nl.amsta09.web.Content;
import nl.amsta09.web.html.HtmlPopup;

public class AddMediaToThemeServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Content content = new Content(request, response);
		SqlConnector conn = new SqlConnector();
		Theme theme = content.parseSession().getManagedTheme();
		int selectedPhotoId;
		Photo selectedPhoto;

		try {
			selectedPhotoId = Integer.parseInt(request.getParameter(Content.SELECTED_PHOTO_ID));
			selectedPhoto = conn.getPhotoById(selectedPhotoId);
		} catch(SQLException | NullPointerException | NumberFormatException e){
			HtmlPopup popup = new HtmlPopup("error", "Fout bij verwerken van request", 
					"Het is niet gelukt om uw request te verwerken, probeer het alstublieft opnieuw");
			content.add("popup", popup);
			content.sendUsing(Content.THEME_MANAGEMENT_JSP);
			e.printStackTrace();
			return;
		}

		try {
			conn.addMediaToTheme(theme.getId(), selectedPhotoId);
		} catch (SQLException e){
			HtmlPopup popup = new HtmlPopup("error", "Fout bij verbinding met de database", 
					"Het is niet gelukt om verbinding te maken met de database, probeer het alstublieft opnieuw");
			content.add("popup", popup);
			content.sendUsing(Content.THEME_MANAGEMENT_JSP);
			e.printStackTrace();
			return;
		}
		
			HtmlPopup popup = new HtmlPopup("succes", "Succes!", 
					"De foto is aan het thema " + theme.getName() + " toegevoegd");
			content.add("popup", popup);
			new ThemeManagementServlet().doGet(content.getRequest(), content.getResponse());
	}
}
