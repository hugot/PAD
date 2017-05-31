package nl.amsta09.app;

import java.sql.SQLException;
import java.util.ListIterator;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import nl.amsta09.data.SqlConnector; 
import nl.amsta09.driver.MainApp; 
import nl.amsta09.model.Photo; 
import nl.amsta09.model.Theme; 
import nl.amsta09.app.Settings;

public class SlideShowController {
	private Theme theme;
	private ListIterator<Photo> photos;
	private SlideShowView view;
	private Stage stage;
	private SqlConnector conn;
	private Timer timer;
        private Settings settings;

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
                settings = new Settings();
	}

	/**
	 * Initialiseer de media die getoond wordt en zorg ervoor dat het scherm verschijnt.
	 */
	public void initialize() {
		setRandomTheme();
		view.setKeyListener();
		timer.start();
		stage.setScene(view);
		stage.show();
		showNextImage();
	}


	/**
	 * Haal een random thema op om foto's van weer te geven.
	 */
	public void setRandomTheme(){

		try {
			//TODO: zorg dat dit random wordt (kan niet aan de hand van id,
			//die telt nmlk ook door voor inactieve themes
			setTheme(conn.getRandomTheme());
		} catch (SQLException e) {
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
		this.photos = theme.getPhotoList().listIterator();
	}
        
        public Theme getTheme(){
            return theme;
        }
        
        public Settings getSettings(){
            return settings;
        }
	/**
	 * Verander welke foto er weergegeven wordt.
	 * @param photo
	 */
	public void setImage(Photo photo){
                
		view.setImage(photo);
		timer.reset();
	}

	/**
	 * Laat de volgende foto van het thema zien.
	 */
	public void showNextImage(){
		if(photos.hasNext()){
			setImage(photos.next());
		}
		else {
			setNextTheme();
		}
	}

	/**
	 * Verander het thema naar een nieuw thema.
	 */
	public void setNextTheme(){
		try {
                    System.out.println("setting next theme");
			setTheme(conn.getRandomThemeThatIsNot(theme));
                        if(theme.getMusic() != null && settings.getSound())
                        theme.getMusic().playSound();
			showNextImage();
		} catch (SQLException e) {
			//TODO: doe iets nuttigs.
			e.printStackTrace();
		}
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
						while(getSecondsToGo() > 0){
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

		public int getSecondsToGo(){
			return secondsToGo;
		}

		public void stop(){
			isRunning = false;
		}
	}
}
