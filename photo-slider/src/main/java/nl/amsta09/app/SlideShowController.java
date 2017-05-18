package nl.amsta09.app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ListIterator;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import nl.amsta09.data.SqlConnector;
import nl.amsta09.data.SqlConnector.ThemeNotFoundException;
import nl.amsta09.driver.MainApp;
import nl.amsta09.model.Photo;
import nl.amsta09.model.Theme;

public class SlideShowController implements KeyListener {
	private Theme theme;
	private ListIterator<Photo> photos;
	private SlideShowView view;
	private Stage stage;
	private SqlConnector conn;
	private Timer timer;

	/**
	 * Instantieer de slideshow en zorg dat er een willekeurig thema of willekeurige foto te zien is.
	 * @param stage
	 */
	public SlideShowController(Stage stage){
		conn = new SqlConnector();
		StackPane stackPane = new StackPane();
		view = new SlideShowView(stackPane);
		this.stage = stage;
		timer = new Timer(this);
	}

	/**
	 * Initialiseer de media die getoond wordt en zorg ervoor dat het scherm verschijnt.
	 */
	public void initialize() {
		setRandomTheme();
		photos = theme.getPhotoList().listIterator();
		view.setImage(photos.next());
		timer.start();
		stage.setScene(view);
		stage.show();
	}

	/**
	 * Haal een random thema op om foto's van weer te geven.
	 */
	public void setRandomTheme(){
		try {
			//TODO: zorg dat dit random wordt (kan niet aan de hand van id,
			//die telt nmlk ook door voor inactieve themes
			setTheme(conn.getActiveThemeById(1));
		} catch (ThemeNotFoundException | SQLException e) {
			// TODO Doe hier iets nuttigs
			e.printStackTrace();
		}
	}

	/**
	 * Verander het thema waarvan de foto's weergegeven worden.
	 * @param theme
	 */
	public void setTheme(Theme theme){
		this.theme = theme;
	}

	/**
	 * Verander welke foto er weergegeven wordt.
	 * @param photo
	 */
	public void setImage(Photo photo){
		view.setImage(photo);
	}

	/**
	 * Laat de volgende foto van het thema zien.
	 */
	public void showNextImage(){
		setImage(photos.next());
	}

	/**
	 * Ga naar de volgende foto als er op een knop gedrukt wordt.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		showNextImage();
		timer.reset();
	}

	/**
	 * Deze class dient voor het timen van de de duratie dat een foto getoond wordt.
	 */
	private class Timer {
		private int secondsToGo;
		private SlideShowController slideShowController;

		/**
		 * Initialiseer de Timer.
		 * @param slideShowController
		 */
		public Timer(SlideShowController slideShowController){
			secondsToGo = MainApp.SECONDS;
			this.slideShowController = slideShowController;
			
		}

		/**
		 * Begin met timen.
		 */
		public void start(){
			while(true){
				while(secondsToGo > 0){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						break;
					}
					secondsToGo--;
				}
				slideShowController.showNextImage();
				reset();
			}
		}

		/**
		 * Reset het aantal seconden dat de timer nog te gaan heeft
		 */
		public void reset(){
			secondsToGo = MainApp.SECONDS;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Doe niets
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Doe niets
	}
}
