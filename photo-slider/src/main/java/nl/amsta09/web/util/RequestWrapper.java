package nl.amsta09.web.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequestWrapper;

import nl.amsta09.driver.MainApp;
import nl.amsta09.data.SqlConnector;
import nl.amsta09.web.util.SessionWrapper;

public class RequestWrapper extends HttpServletRequestWrapper {
	public static final String SELECTED_THEME_ID = "selectedThemeId";
	public static final String SELECTED_PHOTO_ID = "selectedPhotoId";
	public static final String SELECTED_AUDIO_ID = "selectedAudioId";

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

	private HttpServletRequest httpServletRequest;
	public RequestWrapper(HttpServletRequest request) {
		super(request);
		httpServletRequest = request;
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
		getRequestDispatcher(jsp).forward(getRequest(), response);
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

	public HttpServletRequest getHttpServletRequest(){
		return httpServletRequest;
	}
}
