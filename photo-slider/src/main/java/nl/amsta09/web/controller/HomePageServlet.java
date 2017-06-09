package nl.amsta09.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Deze class dient voor het tonen van de homepage.
 * 
 * @author Hugo Thunnissen
 */
public class HomePageServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
	IOException, ServletException {
		request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
	}

}
