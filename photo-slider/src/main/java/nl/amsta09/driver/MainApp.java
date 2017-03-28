package nl.amsta09.driver;

/**
 * Mainclass van de applicatie.
 *
 */

import nl.amsta09.app.Slideshow;
import nl.amsta09.web.JettyServer;

public class MainApp {
    
    public static void main( String[] args ) throws Exception
    {
        //Maak server aan en start de server (default port 4848)
    	JettyServer jettyServer = new JettyServer();
    	jettyServer.setHandler();
    	jettyServer.start();

    	//start slideshow
        new Slideshow();
    }
}
