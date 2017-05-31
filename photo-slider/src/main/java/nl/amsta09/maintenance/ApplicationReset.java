package nl.amsta09.maintenance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import nl.amsta09.data.SqlConnector;
import nl.amsta09.data.SqlConnector.ThemeNotFoundException;
import nl.amsta09.driver.MainApp;
import nl.amsta09.model.Audio;
import nl.amsta09.model.Media;
import nl.amsta09.model.Photo;

public class ApplicationReset {
	public static final String DEFAULT_PHOTO_DIRECTORY = "Resources/default/Foto/";
	public static final String DEFAULT_AUDIO_DIRECTORY = "Resources/default/Audio/";

	IOException ioException;
	SqlConnector conn;
	String[] mediaDirectoryPaths = {Photo.DIRECTORY, Audio.DIRECTORY};
	String[] defaultMediaDirectoryPaths = {DEFAULT_PHOTO_DIRECTORY, DEFAULT_AUDIO_DIRECTORY};

	public ApplicationReset(){
		conn = new SqlConnector();
	}

	/**
	 * Reset de applicatie.
	 *
	 * @throws SQLException
	 * @throws IOException
	 * @throws ThemeNotFoundException
	 * @throws ClassNotFoundException
	 */
	public void execute() throws SQLException, IOException, ThemeNotFoundException, ClassNotFoundException{
		System.out.println("-------------Starting reset--------------");
		MainApp.getSlideShowController().pause();
		emptyMediaFolders();
		emptyDatabase();
		createDatabaseTables();
		copyDefaultMedia();
		createDefaultTheme();
		insertDefaultMedia();
		MainApp.getSlideShowController().start();
	}

	/**
	 * Maak de mappen met media leeg.
	 */
	private void emptyMediaFolders(){
		System.out.println("-------------Emptying media folders--------------");
		for(String path: mediaDirectoryPaths){
			File dir = new File(path);
			for(File file : dir.listFiles()){
				file.delete();
			}
		}
	}

	/**
	 * Delete alle tabellen in de database.
	 *
	 * @throws SQLException
	 */
	private void emptyDatabase() throws SQLException{
		System.out.println("-------------Emptying database--------------");
		conn.executeUpdate("DROP TABLE IF EXISTS photo,song,soundeffect," + 
				"theme_has_media,media,theme;");
	}

	/**
	 * Maak de tabellen opnieuw aan in de database.
	 *
	 * @throws SQLException
	 * @throws FileNotFoundException
	 */
	private void createDatabaseTables() throws FileNotFoundException, SQLException{
		System.out.println("-------------Creating new database tables--------------");
		File sqlScript = new File("WEB-INF/SQL/tablecreation.sql");
		String sql = "";
		ArrayList<String> statements = new ArrayList<String>(){
			@Override
			public boolean add(String s){
				try {
					conn.executeUpdate(s);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return super.add(s);
			}
		};
		Scanner lines = new Scanner(sqlScript);
		while(lines.hasNext()){
			String line = lines.nextLine();
			if(line.matches("^-- *")){
				if(!sql.isEmpty()) statements.add(sql);
				sql = "";
			}
			else {
				sql += line;
			}
		}
		lines.close();
		if(!sql.isEmpty()) conn.executeUpdate(sql);
	}

	/**
	 * Kopieer de standaard media bestanden naar de juiste map.
	 *
	 * @throws IOException
	 */
	private void copyDefaultMedia() throws IOException{
		System.out.println("-------------Copying default media--------------");
		HashMap<String,String> directories = new HashMap<>();
		directories.put(mediaDirectoryPaths[0], defaultMediaDirectoryPaths[0]);
		directories.put(mediaDirectoryPaths[1], defaultMediaDirectoryPaths[1]);
		directories.forEach((String mediaDir, String defaultMediaDir) -> {
			File dir = new File(defaultMediaDir);
			for(File file : dir.listFiles()){
				try {
					Files.copy(file.toPath(), new File(mediaDir + file.getName()).toPath(),
							StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					ioException = e;
					break;
				}
			}
		});
		if(ioException != null){
			throw ioException;
		}
	}

	/**
	 * Maak het standaard thema aan.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ThemeNotFoundException
	 */
	private void createDefaultTheme() throws SQLException, ClassNotFoundException {
		System.out.println("-------------inserting standard theme--------------");
		conn.insertTheme("Default theme");
	}

	/**
	 * Voeg de standaard media toe aan de database.
	 * @throws ThemeNotFoundException
	 */
	private void insertDefaultMedia() throws SQLException, ThemeNotFoundException{
		System.out.println("------- inserting default media --------");
		for(String path : mediaDirectoryPaths){
			boolean photo;
			if(path.equals(Photo.DIRECTORY)) photo = true;
			else photo = false;
			File dir = new File(path);
			for(File file : dir.listFiles()){
				Media media;
				if(photo) media = new Photo(file.getPath(), file.getName(), 1);
				else media = new Audio(file.getPath(), file.getName(), 1);
				conn.insertMedia(media);
				media.setId(conn.getMediaIdFrom(media));
				conn.addMediaToTheme(conn.getMaxThemeId(), media);
			}
		}
	}
	
}
