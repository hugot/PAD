package nl.amsta09.driver;

import nl.amsta09.app.SlideShowController;
import nl.amsta09.app.Slideshow;
import nl.amsta09.app.SlideshowView;
import nl.amsta09.data.SqlConnector;
import nl.amsta09.web.JettyServer;

/**
 * Mainclass van de applicatie.
 *
 */


public class MainApp {
    
    public static void main( String[] args ) throws Exception
    {
        //Maak server aan en start de server (default port 4848)
    	JettyServer jettyServer = new JettyServer();
    	jettyServer.setHandler();
    	jettyServer.start();

   	new SqlConnector();

    	//start slideshow

    	//SlideShowController slideshowController = new SlideShowController(new SlideshowView());
	//	slideshowController.initialize();

		//new Slideshow();
    }
}
