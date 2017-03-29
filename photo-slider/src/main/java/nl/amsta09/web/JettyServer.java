package nl.amsta09.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.TagLibConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

/**
 * Class voor het aanmaken van een simpele webserver
 */

public class JettyServer {

	private Server server;

	/**
	 * Instantieer de server, default port is 8080
	 */
	public JettyServer() {
		this(4848);
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
		webAppContext.setParentLoaderPriority(true);
		webAppContext.setConfigurations(new Configuration[] {
                new AnnotationConfiguration(), new WebXmlConfiguration(),
                new WebInfConfiguration(), new TagLibConfiguration(),
                new PlusConfiguration(), new MetaInfConfiguration(),
                new FragmentConfiguration(), new EnvConfiguration() }
				);
		return webAppContext;
	}

}
