package nl.amsta09.app;

import java.sql.SQLException;
import java.util.ListIterator;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import nl.amsta09.data.SqlConnector; 
import nl.amsta09.data.SqlConnector.ThemeNotFoundException; 
import nl.amsta09.driver.MainApp; 
import nl.amsta09.model.Photo; 
import nl.amsta09.model.Theme; 

public class SlideShowController {
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
		view = new SlideShowView(stackPane, this);
		this.stage = stage;
		timer = new Timer(this);
	}

	/**
	 * Initialiseer de media die getoond wordt en zorg ervoor dat het scherm verschijnt.
	 */
	public void initialize() {
		setRandomTheme();
		view.setKeyListener();
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
			setTheme(conn.getActiveThemeById(1)); //dit is voor nu even het thema waar alle foto's aan toegevoegd worden
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
	 * Deze class dient voor het timen van de de duratie dat een foto getoond wordt.
	 */
	private class Timer {
		private int secondsToGo;
		private SlideShowController slideShowController;
		Thread timerThread;
		private boolean isRunning;

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
			Runnable runnable = new Runnable(){
				public void run(){
					while(isRunning()){
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
			};
			isRunning = true;
			timerThread = new Thread(runnable);
			timerThread.start();
		}

		/**
		 * Reset het aantal seconden dat de timer nog te gaan heeft
		 */
		public void reset(){
			secondsToGo = MainApp.SECONDS;
		}

		public boolean isRunning(){
			return isRunning;
		}

		public void stop(){
			isRunning = false;
		}
	}
}
