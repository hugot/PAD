package nl.amsta09.driver;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

import nl.amsta09.app.SlideShowController;
import nl.amsta09.web.JettyServer;
import nl.amsta09.web.util.MediaSessionManager;

/**
 * Main class van de applicatie.
 *
 * @author Hugo Thunnissen
 */
public class MainApp extends Application{
	public static int SECONDS = 10;
	public static int SESSIONEXPIRATIONTIME = 1800;
	private static SlideShowController slideShowController;
	private static MediaSessionManager mediaSessionManager;
	private static JettyServer server;

	/**
	 * Start zowel de javafx applicatie als de webserver.
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) {
		// Maak slideshow aan en start deze
//		slideShowController = new SlideShowController(primaryStage);
//		slideShowController.initialize();
		
        // Maak server aan en start de server (default port 4848)
        server = new JettyServer();
        server.setHandler();
        try {
			server.start();
        }
        catch (Exception e){
        	die("Opstarten webserver mislukt", "Het opstarten van de ingebouwde webserver is mislukt." +
				" Start het apparaat alstublieft opnieuw op.");
        }

        // Instantieer de session manager
        mediaSessionManager = new MediaSessionManager();
	}

	/**
	 * Main methode.
	 * @param args
	 */
    public static void main(String[] args) {
        launch(args);
	}

	/**
	 * Toegang tot de slideShowController vanuit de rest van de applicatie.
	 * @return slideShowController
	 */
	public static SlideShowController getSlideShowController(){
		return slideShowController;
	}

	/**
	 * Toegang tot de sessionManager vanuit de rest van de applicatie.
	 * @return sessionManager
	 */
	public static MediaSessionManager getSessionManager(){
		return mediaSessionManager;
	}

	/**
	 * Toegang tot de webserer vanuit de rest van de applicatie.
	 * @return server
	 */
	public static JettyServer getServer(){
		return server;
	}

	/**
	 * Deze methode laat een error bericht zien en sluit vervolgens de applicatie met
	 * return value 1
	 * @param header
	 * @param content
	 */
    public static void die(String header, String content){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText(header);
		alert.setContentText(content);
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK){
			System.exit(1);
		}
	}

}
