package nl.amsta09.web;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.driver.MainApp;
import nl.amsta09.web.SessionManager.Session;
import nl.amsta09.web.SessionManager.SessionNotFoundException;
import nl.amsta09.web.html.HtmlElement;
	
/**
 * Deze class dient voor het beheren van de content die als gevolg van een httpreqeust
 * wordt opgestuurd naar de gebruiker in een response.
 * 
 * @author Hugo Thunnissen
 */
public class Content {
	private Session session;
	private HttpServletRequest request;
	private HttpServletResponse response;

	/**
	 * Instantieer de content.
	 * @param request
	 * @param response
	 */
	public Content(HttpServletRequest request, HttpServletResponse response){
		this.request = request;
		this.response = response;
	}

	/**
	 * Stel de sessie in waarvoor de content gemaakt wordt.
	 */
	public void parseSession(){
		Integer sessionId;
		try {
			sessionId = Integer.parseInt(request.getAttribute("sessionId").toString());
			session = MainApp.getSessionManager().getSessionById(sessionId);
		}
		catch(NullPointerException | SessionNotFoundException | NumberFormatException e){
			session = MainApp.getSessionManager().newSession();
		}
		request.setAttribute("sessionId", session.getId());
	}

	/**
	 * Voeg een attribuut toe aan de content.
	 * @param attribute
	 * @param value
	 */
	public void add(String attribute, String value){
		request.setAttribute(attribute, value);
	}

	public void add(String attribute, HtmlElement element){
		request.setAttribute(attribute, element.getHtml());
	}

	
	/**
	 * Voeg alle attributen aan een request to, en forward deze vervolgens naar de response.
	 * @param attributes
	 */
	public void addAllElements(Hashtable<String,HtmlElement> attributes){
		attributes.forEach((String attribute, HtmlElement value) -> {
			add(attribute, value);
		});
	}

	/**
	 * Voeg alle attributen aan een request to, en forward deze vervolgens naar de response.
	 * @param attributes
	 */
	public void addAll(Hashtable<String,String> attributes){
		attributes.forEach((String attribute, String value) -> {
			add(attribute, value);
		});
	}

	/**
	 * Stuur de content naar de gebruiker in een httpresponse, gebruikmakend van de
	 * gegeven jsp.
	 * @param jsp
	 */
	public void sendUsing(String jsp) throws ServletException, IOException{
		request.getRequestDispatcher(jsp).forward(request, response);
	}

}
