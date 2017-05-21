package nl.amsta09.web.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ListIterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.data.SqlConnector;
import nl.amsta09.model.Theme;
import nl.amsta09.web.Content;
import nl.amsta09.web.html.HtmlButton;
import nl.amsta09.web.html.HtmlForm;
import nl.amsta09.web.html.HtmlList;

public class ThemeManagementServlet extends HttpServlet {
	private final String JSP = "/WEB-INF/themes.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Content content = new Content(request, response);
		SqlConnector conn = new SqlConnector();

		// Check of er een sessionId is, zoniet, dan wordt er een sessie aangemaakt
		content.parseSession();

		ListIterator<Theme> themes;
		try {
			themes = conn.getAllThemes().listIterator();
		} catch (SQLException e) {
			content.add("message","Het is niet gelukt om de thema's op te halen uit de database, " +
					"probeer de pagina te vernieuwen");
			content.sendUsing(JSP);
			return;
		}

		HtmlList themeList = new HtmlList("", "");
		themes.forEachRemaining((Theme theme)-> {
			HtmlForm form = new HtmlForm("theme", "" + theme.getId(), "/thememanagement", "post");
			form.addInput("hidden", "themeName", theme.getName());
			form.addInput("hidden", "themeId", "" + theme.getId());
			form.addElement(new HtmlButton("select-theme-button", "submit", theme.getName()));
			themeList.addItem(form);
		});

		System.out.println(themeList.getHtml());
		content.add("themes", themeList);
		content.add("selectedThemeId", "" + parseSelectedThemeId(request));
		content.sendUsing(JSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		System.out.println(request.getParameter("themename").toString());
	}

	protected static int parseSelectedThemeId(HttpServletRequest request){
		int themeId;
		try {
			themeId = Integer.parseInt(request.getAttribute("selectedTheme").toString());
		} catch(NumberFormatException | NullPointerException e){
			themeId = 1;
		}
		return themeId;
	}

}
