package nl.amsta09.web;

import java.util.ArrayList;

import nl.amsta09.driver.MainApp;
import nl.amsta09.model.Media;

/**
 * Deze class dient voor het instantieren van de session manager die de sessies bijhoudt van bezoekers
 * van de web interface
 *
 * @author Hugo Thunnissen
 */
public class SessionManager {
	ArrayList<Session> sessions;
	
	/**
	 * Deze methode instantieert de SessionManager.
	 */
	public SessionManager(){
		sessions = new ArrayList<Session>();
	}

	/**
	 * Deze methode maakt een nieuwe sessie aan en voegt deze toe aan de lijst met sessies,
	 * alvorens de sessie te returnen.
	 * 
	 * @return session
	 */
	public Session newSession(){
	 	Session session = new Session(firstUnsetIndexOf(sessions), this);
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
	public void updateSession(Session session){
		sessions.set(session.getId(), session);
	}

	/**
	 * Return een session aan de hand van de gegeven id.
	 * @param sessionId
	 * @return session;
	 */
	public Session getSessionById(int sessionId){
		return sessions.get(sessionId);
	}

	/**
	 * Sluit een sessie.
	 * @param session
	 */
	public void closeSession(Session session){
		sessions.set(session.getId(), null);
	}

	/**
	 * Deze methode returnt de index van de eerste null instantie die gevonden wordt.
	 * Zijn er geen null instanties in de lijst, dan wordt de grootte van de lijst gereturnd
	 * @param sessions
	 * @return firstNullInstance
	 */
	private int firstUnsetIndexOf(ArrayList<Session> sessions){
		for(int i = 0; i < (sessions.size() - 1); i++){
			if(sessions.get(i) == null){
				return i;
			}
		}
		return sessions.size();
	}
	
	/**
	 * Deze class dient voor het instantieren van een sessie door de session manager
	 */
	public class Session {
		private int sessionId;
		private ArrayList<Media> addedMedia;
		private SessionManager sessionManager;
		private Thread expirationCounter;

		public Session(int sessionId, SessionManager sessionManager){
			this.sessionId = sessionId;
			addedMedia = new ArrayList<Media>();
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
					sessionManager.closeSession(getSession());
				}
			};
			expirationCounter = new Thread(counter);
			expirationCounter.start();
		}

		/**
		 * Haal ArrayList op met alle media die tijdens deze sessie is toegevoegd.
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
		private Session getSession(){
			return this;
		}
	}
}
