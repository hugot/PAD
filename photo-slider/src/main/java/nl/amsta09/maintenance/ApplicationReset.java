package nl.amsta09.maintenance;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

import nl.amsta09.data.SqlConnector;
import nl.amsta09.model.Audio;
import nl.amsta09.model.Photo;

public class ApplicationReset {
	SqlConnector conn;
	String[] directoryPaths = {Photo.DIRECTORY, Audio.DIRECTORY};

	public ApplicationReset(){
		conn = new SqlConnector();
	}

	/**
	 * Reset de applicatie.
	 * @throws SQLException
	 * @throws FileNotFoundException
	 */
	public void execute() throws SQLException, FileNotFoundException{
		emptyMediaFolders();
		emptyDatabase();
		createDatabaseTables();
		copyDefaultMedia();
		insertDefaultMedia();
	}

	/**
	 * Maak de mappen met media leeg.
	 */
	private void emptyMediaFolders(){
		for(String path: directoryPaths){
			File dir = new File(path);
			for(File file : dir.listFiles()){
				file.delete();
			}
		}
	}

	private void emptyDatabase() throws SQLException{
		conn.executeUpdate("DROP TABLE media,photo,song,soundeffect," + 
				"theme_has_media;");
	}

	private void createDatabaseTables() throws FileNotFoundException, SQLException{
		File sqlScript = new File("Resources/database/create-tables.sql");
		String sql = "";
		Scanner lines = new Scanner(sqlScript);
		while(lines.hasNext()){
			sql += lines.next();
		}
		lines.close();
		conn.executeUpdate(sql);
	}

	private void copyDefaultMedia(){
		for(String path : directoryPaths){
			File dir = new File(path){
				for(File file : dir.listFiles()){
					file.gt
				}
			}
		}
	}

	private void insertDefaultMedia(){

	}
}
