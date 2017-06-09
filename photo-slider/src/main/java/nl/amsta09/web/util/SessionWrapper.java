package nl.amsta09.web.util;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import nl.amsta09.model.Theme;
import nl.amsta09.web.util.MediaSessionManager;
import nl.amsta09.web.util.MediaSessionManager.MediaSession;
import nl.amsta09.web.util.MediaSessionManager.MediaSessionNotFoundException;
/**
 * Dit is een wrapper voor HttpSessions, voor gebruik in combinatie met de 
 * HttpServletRequestWrapper.
 * 
 * @author Hugo Thunnissen
 */

public class SessionWrapper implements HttpSession {
	private static final String MEDIA_SESSION_ID = "mediaSessionId";

	private HttpSession session;
	private MediaSession mediaSession;
	private MediaSessionManager mediaSessionManager;

	/**
	 * Instantieer de wrapper met een sessie.
	 * @param session de sessie die door jetty is aangemaakt voor de gebruiker.
	 */
	public SessionWrapper(HttpServletRequest request, MediaSessionManager mediaSessionManager){
		this.session = request.getSession();
		this.mediaSessionManager = mediaSessionManager;
		mediaSession = parseMediaSession();
	}

	/**
	 * De media sessie die voor deze sessie is aangemaakt.
	 * @return mediaSession
	 */
	public MediaSession getMediaSession(){
		return mediaSession;
	}

	/**
	 * Voeg een media sessie attribuut toe aan de sessie.
	 */
	public void setMediaSession(){
		if(mediaSession == null){
			mediaSession = mediaSessionManager.newSession();
			setAttribute(MEDIA_SESSION_ID, mediaSession.getId());
		}
	}

	/**
	 * Stel de sessie in waarvoor de content gemaakt wordt.
	 * @return mediaSession
	 */
	private MediaSession parseMediaSession(){
		try {
			int sessionId = Integer.parseInt(getAttribute(MEDIA_SESSION_ID).toString());
			return mediaSessionManager.getSessionById(sessionId);
		}
		catch(NullPointerException | MediaSessionNotFoundException | NumberFormatException e){
			return null;
		}
	}

	/**
	 * Check of de request een sessie id bevat.
	 * @return hasSession
	 */
	public boolean hasMediaSession(){
		if(mediaSession != null){
			return true;
		}
		return false;
	}

	/**
	 * Stel het thema dat tijdens de sessie beheerd wordt in.
	 * @param theme
	 */
	public void setManagedTheme(Theme theme){
		setAttribute(RequestWrapper.SELECTED_THEME_ID, "" + theme.getId());
		setAttribute(RequestWrapper.SELECTED_THEME, theme.getName());
		mediaSession.setManagedTheme(theme);
	}

	/** 
	 * geef aan of er een thema is ingesteld om te beheren tijdens de sessie.
	 * @return hasManagedTheme
	 */
	public boolean hasManagedTheme(){
		if(mediaSession.getManagedTheme() != null) return true;
		else return false;
	}

	@Override
	public Object getAttribute(String attributeName) {
		return session.getAttribute(attributeName);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return session.getAttributeNames();
	}

	@Override
	public long getCreationTime() {
		return session.getCreationTime();
	}

	@Override
	public String getId() {
		return session.getId();
	}

	@Override
	public long getLastAccessedTime() {
		return session.getLastAccessedTime();
	}

	@Override
	public int getMaxInactiveInterval() {
		return session.getMaxInactiveInterval();
	}

	@Override
	public ServletContext getServletContext() {
		return session.getServletContext();
	}

	@Override
	public HttpSessionContext getSessionContext() {
		return session.getSessionContext();
	}

	@Override
	public Object getValue(String valueName) {
		return session.getValue(valueName);
	}

	@Override
	public String[] getValueNames() {
		return session.getValueNames();
	}

	@Override
	public void invalidate() {
		session.invalidate();
	}

	@Override
	public boolean isNew() {
		return session.isNew();
	}

	@Override
	public void putValue(String valueName, Object object) {
		session.putValue(valueName, object);
	}

	@Override
	public void removeAttribute(String attributeName) {
		session.removeAttribute(attributeName);

	}

	@Override
	public void removeValue(String valueName) {
		session.removeValue(valueName);
	}

	@Override
	public void setAttribute(String attributeName, Object object) {
		session.setAttribute(attributeName, object);
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		session.setMaxInactiveInterval(interval);
	}

}
