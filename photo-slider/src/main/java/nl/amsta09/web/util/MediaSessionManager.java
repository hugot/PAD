package nl.amsta09.web.util;

import java.util.ArrayList;

import nl.amsta09.driver.MainApp;
import nl.amsta09.model.Media;
import nl.amsta09.model.Theme;

/**
 * Deze class dient voor het instantieren van de session manager die de sessies bijhoudt van bezoekers
 * van de web interface
 *
 * @author Hugo Thunnissen
 */
public class MediaSessionManager {
	ArrayList<MediaSession> sessions;
	
	/**
	 * Deze methode instantieert de MediaSessionManager.
	 */
	public MediaSessionManager(){
		sessions = new ArrayList<MediaSession>();
	}

	/**
	 * Deze methode maakt een nieuwe sessie aan en voegt deze toe aan de lijst met sessies,
	 * alvorens de sessie te returnen.
	 * 
	 * @return session
	 */
	public MediaSession newSession(){
	 	MediaSession session = new MediaSession(firstUnsetIndexOf(sessions), this);
	 	if(session.getId() == sessions.size()){
	 		sessions.add(session);
	 	}
		else {
			sessions.set(session.getId(), session);
		}
		return session;
	}

	/**
	 * Update een sessie met nieuwe data
	 * @param session
	 */
	public void updateSession(MediaSession session){
		sessions.set(session.getId(), session);
	}

	/**
	 * Return een session aan de hand van de gegeven id.
	 * @param sessionId
	 * @return session;
	 * @throws MediaSessionNotFoundException
	 */
	public MediaSession getSessionById(int sessionId) throws MediaSessionNotFoundException{
		MediaSession session;
		try {
			session = sessions.get(sessionId);
		}
		catch(IndexOutOfBoundsException e) {
			throw new MediaSessionNotFoundException();
		}
		if(session == null){
			throw new MediaSessionNotFoundException();
		}
		else {
			return sessions.get(sessionId);
		}
	}

	/**
	 * Sluit een sessie.
	 * @param session
	 */
	public void closeSession(MediaSession session){
		sessions.set(session.getId(), null);
	}

	/**
	 * Deze methode returnt de index van de eerste null instantie die gevonden wordt.
	 * Zijn er geen null instanties in de lijst, dan wordt de grootte van de lijst gereturnd
	 * @param sessions
	 * @return firstNullInstance
	 */
	private int firstUnsetIndexOf(ArrayList<MediaSession> sessions){
		for(int i = 0; i < (sessions.size() - 1); i++){
			if(sessions.get(i) == null){
				return i;
			}
		}
		return sessions.size();
	}

	/**
	 * reset de lijst met sessies.
	 */
	public void reset(){
		sessions = new ArrayList<MediaSession>();
	}
	
	/**
	 * Deze class dient voor het instantieren van een sessie door de session manager
	 */
	public class MediaSession {
		private int sessionId;
		private ArrayList<Media> addedMedia;
		private Theme managedTheme;
		private MediaSessionManager sessionManager;
		private Thread expirationCounter;

		/**
		 * Deze methode instantieert een media sessie.
		 * @param sessionId
		 * @param sessionManager
		 */
		public MediaSession(int sessionId, MediaSessionManager sessionManager){
			this.sessionId = sessionId;
			addedMedia = new ArrayList<Media>(){
				@Override
				public boolean add(Media m){
					if(super.add(m)){
						update();
						return true;
					}
					return false;
				}
			};
			this.sessionManager = sessionManager;
			countTillExpiration();
		}
		
		/**
		 * Tel totdat de sessie niet meer geldig is en stop de sessie dan.
		 */
		private void countTillExpiration(){
			Runnable counter = new Runnable(){
				@Override
				public void run(){
					int secondsToGo = MainApp.SESSIONEXPIRATIONTIME;
					while(secondsToGo > 0){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							System.out.println("session experiation counter thread is onderbroken");
						}
						secondsToGo--;
					}
					sessionManager.closeSession(getMediaSession());
				}
			};
			expirationCounter = new Thread(counter);
			expirationCounter.start();
		}

		/**
		 * Stel het thema in dat tijdens deze sessie beheerd wordt.
		 * @param managedTheme
		 */
		public void setManagedTheme(Theme managedTheme){
			this.managedTheme = managedTheme;
			update();
		}

		/**
		 * Het thema dat tijdens deze sessie beheerd wordt.
		 */
		public Theme getManagedTheme(){
			return managedTheme;
		}

		/**
		 * Haal ArrayList op met alle media die tijdens deze sessie is toegevoegd.
		 * NB: Alleen bij het toevoegen van media wordt de sessie autmatisch geupdate,
		 * bij andere acties zal dit handmatig gedaan moeten worden.
		 * @return addedMedia
		 */
		public ArrayList<Media> getAddedMedia(){
			return addedMedia;
		}

		/**
		 * De id van de sessie.
		 * @return sessionId
		 */
		public int getId(){
			return sessionId;
		}

		/** 
		 * De sessie zelf.
		 * @return this
		 */
		private MediaSession getMediaSession(){
			return this;
		}

		private void update(){
			sessionManager.updateSession(this);
		}

	}

	/**
	 * Exceptie als een sessie niet gevonden kan worden
	 */
	public class MediaSessionNotFoundException extends Exception {

		/**
		 * Instantieer de exceptie.
		 * @param message
		 */
		public MediaSessionNotFoundException(){
			super("Sessie niet gevonden!!");
		}
	}
}
