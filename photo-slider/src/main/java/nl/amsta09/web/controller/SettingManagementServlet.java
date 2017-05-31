package nl.amsta09.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.amsta09.web.util.RequestWrapper;

public class SettingManagementServlet extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		RequestWrapper requestWrapper = new RequestWrapper(request);
		requestWrapper.respondUsing("/WEB-INF/settings.jsp", response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response){

	}
}
