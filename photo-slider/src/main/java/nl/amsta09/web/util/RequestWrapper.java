package nl.amsta09.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequestWrapper;

import nl.amsta09.driver.MainApp;
import nl.amsta09.data.SqlConnector;
import nl.amsta09.web.util.SessionWrapper;

/**
 * Dit is een wrapper class voor HttpServletRequests.
 * 
 * @author Hugo Thunnissen
 */
public class RequestWrapper extends HttpServletRequestWrapper {
	public static final String SELECTED_THEME_ID = "selectedThemeId";
	public static final String SELECTED_PHOTO_ID = "selectedPhotoId";
	public static final String SELECTED_AUDIO_ID = "selectedAudioId";
	public static final String SELECTED_MEDIA_ID = "selectedMediaId";

	public static final String THEME_LIST = "themes";
	public static final String PHOTO_LIST = "photos";

	public static final String SELECTED_THEME = "theme";

	public static final String THEME_SELECTION_JSP = "/WEB-INF/theme-selection.jsp";
	public static final String PHOTO_SELECTION_JSP = "/WEB-INF/photo-selection.jsp";
	public static final String PHOTO_UPLOAD_JSP = "/WEB-INF/addPhoto.jsp";
	public static final String AUDIO_UPLOAD_JSP = "/WEB-INF/addAudio.jsp";
	public static final String THEME_MANAGEMENT_JSP = "/WEB-INF/themes.jsp";
	public static final String INDEX_JSP = "/WEB-INF/index.jsp";

	private Content content;
	private SqlConnector conn;
	private SessionWrapper sessionWrapper;
	
	public RequestWrapper(HttpServletRequest request) {
		super(request);
		conn = new SqlConnector();
		sessionWrapper = new SessionWrapper(request, MainApp.getSessionManager());
		content = new Content(this);
	}


	/**
	 * Stuur de content naar de gebruiker in een httpresponse, gebruikmakend van de
	 * gegeven jsp.
	 * @param jsp
	 */
	public void respondUsing(String jsp, HttpServletResponse response) throws ServletException, IOException{
		content.appendAttributes();
		getRequestDispatcher(jsp).forward(getRequest(), response);
	}

	public void sendContentByWriter(HttpServletResponse response) throws IOException{
		response.getWriter().write(content.getAll());
		response.getWriter().flush();
	}

	/**
	 * De connectie met de database.
	 * @return conn
	 */
	public SqlConnector getSqlConnector(){
		return conn;
	}

	/**
	 * De content (attributen van de request)
	 * @return content
	 */
	public Content getContent(){
		return content;
	}

	@Override
	public SessionWrapper getSession(){
		return sessionWrapper;
	}

	public boolean redirectToIndexIf(boolean condition, HttpServletResponse response)
		throws ServletException, IOException{
		if(condition) {
			respondUsing(INDEX_JSP, response);
			return true;
		}
		return false;
	}

	/**
	 * Haal een parameter op uit een AJAX request.
	 * @param name
	 * @return parameter
	 */
	public String parseParameter(String name) throws IOException{
		String parameter;
		StringBuilder requestBody = new StringBuilder();
		InputStream requestInputStream = getInputStream();
		Scanner requestScanner= new Scanner(requestInputStream, "UTF-8");
		while(requestScanner.hasNext()){
			requestBody.append(requestScanner.nextLine());
		}
		requestScanner.close();
		requestInputStream.close();
		Pattern filenamePattern = Pattern.compile(name+"=(.*)");
		Matcher matcher = filenamePattern.matcher(requestBody.toString());
		if(matcher.find()){
			parameter = matcher.group(1);
		}
		else {
			parameter = null;
		}
		return parameter;
	}

	public ArrayList<String> parseParametersByName(String name) throws IOException{
		ArrayList<String> parameters = new ArrayList<>();
		StringBuilder requestBody = new StringBuilder();
		InputStream requestInputStream = getInputStream();
		Scanner requestScanner= new Scanner(requestInputStream, "UTF-8");
		while(requestScanner.hasNext()){
			requestBody.append(requestScanner.nextLine());
		}
		requestScanner.close();
		requestInputStream.close();
		Pattern filenamePattern = Pattern.compile(name+"=(.+?)&");
		Matcher matcher = filenamePattern.matcher(requestBody.toString());
		while(matcher.find()){
			parameters.add(matcher.group(1));
		}
		return parameters;
	}
}
