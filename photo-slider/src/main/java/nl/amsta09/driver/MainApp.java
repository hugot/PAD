package nl.amsta09.driver;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.util.Optional;

import javafx.application.Application;

import nl.amsta09.app.SlideShowController;
import nl.amsta09.web.JettyServer;

/**
 * Mainclass van de applicatie.
 *
 */
public class MainApp extends Application{


	@Override
	public void start(Stage primaryStage) throws MalformedURLException {
		
		new SlideShowController(primaryStage);
		
        //Maak server aan en start de server (default port 4848)
        JettyServer jettyServer = new JettyServer();
        jettyServer.setHandler();
        try {
			jettyServer.start();
        }
        catch (Exception e){
        	die("Opstarten webserver mislukt", "Het opstarten van de ingebouwde webserver is mislukt." +
				" Start het apparaat alstublieft opnieuw op.");
        }
	}
    public static void main(String[] args) {
        //start slideshow
        launch(args);
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
