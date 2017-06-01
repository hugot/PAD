package nl.amsta09.app;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
	public void initialize() throws SqlConnector.ThemeNotFoundException {
		setFirstTheme();
		view.setKeyListener();
		timer.start();
                stage.setFullScreen(true);
		stage.setScene(view);
                view.setFill(Color.BLACK);
		stage.show();
		showNextImage();
	}

	public void pause(){
		timer.stop();
	}

	public void start(){
		timer.start();
	}


	/**
	 * Haal een random thema op om foto's van weer te geven.
	 */
	public void setFirstTheme() throws SqlConnector.ThemeNotFoundException{
		try {
                        setTheme(conn.getFirstTheme());
		} catch (SQLException e) {
			theme = new Theme("emergency", 1);
			File dir = new File("Resources/default/Foto/");
			ArrayList<Photo> photos = new ArrayList<>();
			for(File file : dir.listFiles()){
				photos.add(new Photo(file.getPath(), file.getName(), 1));
			}
			theme.setPhotoList(photos);
			setTheme(theme);
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
	public void showNextImage() throws SqlConnector.ThemeNotFoundException{
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
	public void setNextTheme() throws SqlConnector.ThemeNotFoundException{
		try {
			System.out.println("setting next theme");
			setTheme(conn.getRandomThemeThatIsNot(theme));
                        if(theme.getMusic() != null && settings.getSound())
                        theme.getMusic().playSound();
			showNextImage();
		} catch (SQLException e) {
			setFirstTheme();
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
                                            try {
                                                slideShowController.showNextImage();
                                            } catch (SqlConnector.ThemeNotFoundException ex) {
                                                Logger.getLogger(SlideShowController.class.getName()).log(Level.SEVERE, null, ex);
                                            }
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
