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
import javafx.scene.media.AudioClip;

import nl.amsta09.data.SqlConnector; 
import nl.amsta09.driver.MainApp; 
import nl.amsta09.model.Photo;
import nl.amsta09.model.Audio;
import nl.amsta09.model.Theme; 

public class SlideShowController {
	private Theme theme;
	private ListIterator<Photo> photos;
        private ListIterator<Audio> musics;
	private SlideShowView view;
	private Stage stage;
	private SqlConnector conn;
	private Timer timer;
        private Settings settings;
        private AudioClip musicClip;
        private Thread musicThread;

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
	public void initialize(){
		setFirstTheme();
                /*try {
            conn.addSetting();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
                System.out.println("Dit is de Theme_id: " + theme.getId());
                setSettings();
		view.setKeyListener();
		timer.start();
                stage.setFullScreen(true);
		stage.setScene(view);
                view.setFill(Color.BLACK);
		stage.show();
                showNextImage();
                playNextMusic();
                runNextMusic();
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
    public void setFirstTheme() {
        try {
            setTheme(conn.getFirstTheme());
        } catch (SQLException e) {
            try {
                //e.printStackTrace();
                setTheme(conn.getRandomTheme());
                System.out.println("how bout dis");
            } catch (SQLException ex) {
                theme = new Theme("emergency", 1);
                File dir = new File("Resources/default/Foto/");
                ArrayList<Photo> photos = new ArrayList<>();
                for (File file : dir.listFiles()) {
                    photos.add(new Photo(file.getPath(), file.getName(), 1));
                }
                theme.setPhotoList(photos);
                setTheme(theme);
            }
        }
    }

	/**
	 * Verander het thema waarvan de foto's weergegeven worden.
	 * @param theme
	 */
	public void setTheme(Theme theme){
		this.theme = theme;
		this.photos = theme.getPhotoList().listIterator();
                this.musics = theme.getMusicList().listIterator();
	}
        
        public Theme getTheme(){
            return theme;
        }
        
        public Settings getSettings(){
            return settings;
        }
        /**
         * Bepaalt of het geluid aan of uit moet zijn
         */
        public void setSettings(){
            int on;
            try {
                on = conn.getSettingFromDatabase(theme);
                if(on == 1){
                    settings.setSound(true);
                } else {
                    settings.setSound(false);
                }                
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        
        public void insertSetting(){
            try {
                conn.insertSettings(theme, getSettings());
            } catch (SQLException e){
                e.printStackTrace();
            }
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
         * Bepaalt de muziek die gespeeld moet worden
         */
        public void playNextMusic(){
            if(settings.getSound() && musics.hasNext()){
                playMusic(musics.next());
            }
        }
        
        /**
         * Speelt de muziek af
         * @param music 
         */
        public void playMusic(Audio music){
            musicClip = new AudioClip(music.getURL().toString());
            musicClip.play();
            
        }
        
        public void stopMusic(){
            if(musicClip != null){
                musicClip.stop();
            }
        }

	/**
	 * Verander het thema naar een nieuw thema.
	 */
	public void setNextTheme(){
		try {
                        System.out.println("setting next theme");
			setTheme(conn.getRandomThemeThatIsNot(theme));;
                        setSettings();
			showNextImage();
                        stopMusic();
                        playNextMusic();
                        runNextMusic();
		} catch (SQLException e) {
			setFirstTheme();
		}
	}
        
        /**
         * Checkt of er een muziek wordt afgespeeld
         * Speelt een nieuwe muziek af wanneer dat niet zo is
         * en een muziek in de musics(ListIterator) staat
         */
        public void runNextMusic(){
                Runnable runMusic = new Runnable(){
                        public void run(){
                                if(musicClip != null){
                                        while(musicClip.isPlaying()){
                                            try{
                                                Thread.sleep(1000);
                                            } catch(InterruptedException e){
                                                break;
                                            }
                                        }
                                playNextMusic();
                                
                                }
                        }
                };
        musicThread = new Thread(runMusic);
        musicThread.start();
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
