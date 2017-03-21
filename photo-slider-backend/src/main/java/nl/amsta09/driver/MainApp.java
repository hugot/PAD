package nl.amsta09.driver;

/**
 * Hello world!
 *
 */
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import  org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.webapp.WebAppContext;

import jdk.internal.org.xml.sax.helpers.DefaultHandler;

public class MainApp extends AbstractHandler {
    
//	@Override
//    public void handle( String target,
//                        Request baseRequest,
//                        HttpServletRequest request,
//                        HttpServletResponse response ) throws IOException,
//                                                      ServletException
//    {
//        // Declare response encoding and types
//        response.setContentType("text/html; charset=utf-8");
//
//        // Declare response status code
//        response.setStatus(HttpServletResponse.SC_OK);
//
//        // Write back response
//        response.getWriter().println("<h1>Hello World</h1>");
//
//        // Inform jetty that this request has now been handled
//        baseRequest.setHandled(true);
//    }

    public static void main( String[] args ) throws Exception
    {
		ContextHandlerCollection contexts = new ContextHandlerCollection();

    	contexts.setHandlers(new Handler[] {webAppContext()});
    	
        Server server = new Server(8080);
        server.setHandler(contexts);

        server.start();
        server.join();
    }
	
	private static WebAppContext webAppContext (){
		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setDescriptor(webAppContext + "WEB-INF/web.xml");
		webAppContext.setResourceBase(".");
		webAppContext.setContextPath("/runJetty");
		return webAppContext;
	}
	
}
