package nl.amsta09.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Class voor het aanmaken van een simpele webserver
 */

public class JettyServer {

	private Server server;

	/**
	 * Instantieer de server, default port is 8080
	 */
	public JettyServer() {
		this(8080);
	}
	
	/**
	 * Instantieer de server op een andere port
	 * @param port
	 */
	public JettyServer(int port){
		server = new Server(port);
	}
	
	/**
	 * Stel standaardhanlers in
	 */
	public void setHandler(){
		ContextHandlerCollection contexts = new ContextHandlerCollection();
    	contexts.setHandlers(new Handler[] {defaultContext()});
		server.setHandler(contexts);
	}
	
	/**
	 * Stel custom handlers in
	 * @param contexts
	 */
	public void setHandler(ContextHandlerCollection contexts){
		server.setHandler(contexts);
	}
	
	/**
	 * start de server
	 * @throws Exception
	 */
	public void start() throws Exception{
		server.start();
	}
	
	/**
	 * Standaard webappcontex
	 * @return defaultContext
	 */
	private static WebAppContext defaultContext(){
		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setDescriptor(webAppContext + "WEB-INF/web.xml");
		webAppContext.setResourceBase(".");
		webAppContext.setContextPath("/");
		return webAppContext;
	}

}
