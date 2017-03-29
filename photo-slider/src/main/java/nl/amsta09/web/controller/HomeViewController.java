package nl.amsta09.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "HomeViewController", urlPatterns = "/processview")
public class HomeViewController extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException{
		String url = "/";
		if (request.getParameter("addPhoto") != null){
			request.getRequestDispatcher("webroot/addPhoto.jsp").forward(request, response);
		}
		request.setAttribute("greeting", "Hello!");
	}
}
