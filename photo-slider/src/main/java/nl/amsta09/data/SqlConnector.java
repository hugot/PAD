package nl.amsta09.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import nl.amsta09.model.Media;
import nl.amsta09.model.Photo;
import nl.amsta09.model.Theme;
import nl.amsta09.model.Audio;

public class SqlConnector {

    private static Connection connection;

    public SqlConnector() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://supersudonator.filmencode.nl/photoslider?"
                    + "user=PAD&password=fissafissaheey123&useUnicode=true&useJDBCCompliantTimezoneShift=true"
                    + "&useLegacyDatetimeCode=false&serverTimezone=UTC");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
     * Insert een theme naar de database
     */
    public void insertTheme(String themeName) throws SQLException, ClassNotFoundException {
        System.out.println("\n----------insertTheme commencing----------");

        Statement addThemeStatement = connection.createStatement();
        //Insert de gegevens in de database met een unieke ID
        String on = "1";
        String sql =  String.format("INSERT INTO theme (name, `on`) VALUES ('%s', %s)", themeName, on);

        addThemeStatement.execute(sql);

    }

    /*
     * Verwijdert het gekozen Thema uit de database
     */
    public void deleteTheme(int idTheme) throws SQLException, ClassNotFoundException {
        Statement deleteThemeStatement = connection.createStatement();
        //Verwijdert de thema uit de tabel Thema
        String sql = ("DELETE FROM Thema"
                + "WHERE id = " + idTheme);

        deleteThemeStatement.execute(sql);
    }

    /*
     * Insert een media file naar de database
     */
    public void insertMedia(String filePath, String Media_Name) throws SQLException, ClassNotFoundException {

//        Class.forName("com.mysql.jdbc.Driver");
//        connection = DriverManager.getConnection("jdbc:mysql://localhost/photoslider", "root", "Aapjes-14");
        filePath = filePath.replace("\\", "\\\\\\\\");

        Statement addMediaStatement = connection.createStatement();
        //Insert een media file in de database
        String sql = ("INSERT INTO Media (name, filePath) VALUES ('" + Media_Name + "','" + filePath + "')");

        addMediaStatement.execute(sql);

        insertIntoMediaType(Media_Name);
    }

    /**
     * Voeg een media bestand toe aan de database.
     *
     * @param media
     */
    public void insertMedia(Media media) throws SQLException {
        executeUpdate(String.format("INSERT INTO media (name, filePath) VALUES ('%s','%s')", media.getName(), media.getRelativePath()));
        insertIntoMediaType(media.getName());
    }

    /*
    Verwijdert de gekozen Media uit de database
     */
    public void deleteMedia(URL MediafilePath) throws SQLException, ClassNotFoundException {

        Statement deleteMediaStatement = connection.createStatement();

        String sql = ("DELETE FROM Media"
                + "WHERE filePath = " + "'" + MediafilePath + "'");

        deleteMediaStatement.execute(sql);
    }

    /*
     * Activeert het gekozen media file
     */
    public void activateMedia(Media media) throws SQLException {

        Statement activateMediaStatement = connection.createStatement();
        //Activeert de gekozen media file
        String sql = ("UPDATE Media"
                + "SET active = 'true'"
                + "WHERE filePath ='" + media.getName() + "'");

        activateMediaStatement.execute(sql);
    }

    /*
     * Deactiveerd het gekozen media file
     */
    public void disableMedia(Media media) throws SQLException {

        Statement disableMediaStatement = connection.createStatement();
        //Deactiveerd de gekozen media file
        String sql = ("UPDATE Media"
                + "SET active = 'disable'"
                + "WHERE filePath ='" + media.getName() + "'");

        disableMediaStatement.execute(sql);
    }

    /*
    Voegt de Soundeffect toe aan de gekozen Foto
     */
    public void addSoundeffectToPhoto(Photo photo, Audio soundeffect) throws SQLException, ClassNotFoundException {
        Statement addSoundeffectStatement = connection.createStatement();
        //Voegt de Soundeffect id toe aan de Foto
        String sql = ("INSERT INTO Photo VALUES ('" + photo.getId() + ", " + soundeffect.getId() + "')");

        addSoundeffectStatement.execute(sql);
    }

    /*
     * Activeert het gekozen thema
     */
    public void activateTheme(String Theme_Name) throws SQLException {

        Statement activateThemaStatement = connection.createStatement();
        //Activeert het gekozen thema
        String sql = ("UPDATE Thema \n"
                + "SET on/off = 'true'\n"
                + "WHERE name ='" + Theme_Name + "'");

        activateThemaStatement.execute(sql);
    }

    /*
     * Deactiveerd het gekozen thema
     */
    public void disableTheme(String Theme_Name) throws SQLException {

        Statement disableThemaStatement = connection.createStatement();
        //Deactiveerd het gekozen thema
        String sql = ("UPDATE Thema \n"
                + "SET on/off = 'false'\n"
                + "WHERE name ='" + Theme_Name + "'");

        disableThemaStatement.execute(sql);
    }

    /*
     * Voegt het gekozen media file toe aan een gekozen thema
     */
    public void addMediaToTheme(int Theme_Id, int Media_Id) throws SQLException {

        Statement addMediaToThemeStatement = connection.createStatement();
        //Voegt gekozen media toe aan gekozen thema
        String sql = ("INSERT INTO theme_has_media VALUES ('" + Theme_Id + "','" + Media_Id + "')");
        System.out.println(sql);

        addMediaToThemeStatement.execute(sql);
    }

    public ArrayList<Photo> getAllPhoto(ArrayList<Photo> photoList) throws SQLException, ClassNotFoundException {
        System.out.println("\n----------getAllPhoto commencing----------");
        ResultSet result;
        Photo photo;

        Statement getAllMediaStatement = connection.createStatement();
        //Krijgt alle media uit de database
        String sql = ("SELECT A.name, A.filePath , B.id\n"
                + "FROM media A \n"
                + "WHERE EXISTS (SELECT 1 \n"
                + "FROM   photo B \n"
                + "WHERE  A.Id = B.Id)");

        result = getAllMediaStatement.executeQuery(sql);
        System.out.println("test1");
        while (result.next()) {
            photo = new Photo(result.getString("filePath"), result.getString("name"), result.getInt("id"));

            System.out.println(photo.getURL());
            photoList.add(photo);
        }
        System.out.println("test2");
        return photoList;

    }

    /*
     * Zet een media file in de correcte type tabel (photo, sound, soundeffect)
     */
    public void insertIntoMediaType(String Name) throws SQLException {
        String sql;
        Set<String> imageTypes = new HashSet<>();
        imageTypes.add("png");
        imageTypes.add("jpg");
        Set<String> soundTypes = new HashSet<>();
        soundTypes.add("wav");
        soundTypes.add("mp3");

        String fileType = null;
        int i = Name.lastIndexOf('.');
        if (i > 0) {
            fileType = Name.substring(i + 1);
        }

        if (imageTypes.contains(fileType)) {
            sql = ("INSERT INTO photo (id) VALUES ((SELECT id AS lastID FROM media ORDER BY id DESC LIMIT 1))");
        } else {
            sql = ("INSERT INTO song (id) VALUES ('LAST_INSERT_ID')");
        }
        Statement addIntoMediaType = connection.createStatement();

        addIntoMediaType.execute(sql);
    }

    /**
     * check of een bestand met een bepaalde naam al bestaat, om te voorkomen
     * dat het bestand overschreven wordt.
     *
     * @param media
     */
    public boolean mediaInDatabase(Media media) {
        try {
            ResultSet set = executeQuery(String.format("SELECT name FROM media WHERE filePath = '%s';", media.getRelativePath()));
            if (set.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

	public int getMediaIdFrom(Media media) throws SQLException{
	   	ResultSet set = executeQuery(String.format("SELECT id FROM media WHERE name = '%s';", media.getName()));
	   	set.next();
		return set.getInt("id");
    }

	/**
	 * De hoogste theme id die in de database aanwezig is.
	 * @return maxThemeId
	 */
    public int getMaxThemeId() throws SQLException, ThemeNotFoundException {
		ResultSet set = executeQuery("Select id FROM theme ORDER BY id DESC LIMIT 1;");
    	set.next();
    	return set.getInt("id");
    }

	/**
	 * Theme dat bij de id hoort.
	 * @param id
	 * @return theme;
	 */
    public Theme getThemeById(int id)throws SQLException, ThemeNotFoundException {
		Theme theme;
		ResultSet themeSet = executeQuery("SELECT * FROM theme WHERE id = " + id);
		if(themeSet.next()){
			theme = new Theme(themeSet.getString("theme.name"), themeSet.getInt("theme.id"));
		}
		else{
			throw new ThemeNotFoundException("Thema niet gevonden");
		}
		theme.setPhotoList(getAllPhotosFromTheme(theme));
		return theme;
    }

	/**
	 * Haal een actief theme op.
	 * @param id
	 * @return theme
	 */
	public Theme getActiveThemeById(int id) throws ThemeNotFoundException, SQLException {
		Theme theme;
		ResultSet themeSet = executeQuery("SELECT * FROM theme INNER JOIN theme_has_media ON theme.id = theme_has_media.theme_id WHERE theme.id =" + id);
		if(themeSet.next()){
			theme = new Theme(themeSet.getString("theme.name"), themeSet.getInt("theme.id"));
		}
		else{
			throw new ThemeNotFoundException("Thema niet gevonden");
		}
		theme.setPhotoList(getAllPhotosFromTheme(theme));
		return theme;
    }

	/**
	 * Haal alle foto;s van een bepaald theme op
	 * @param theme
	 * @return photos
	 */
    public ArrayList<Photo> getAllPhotosFromTheme(Theme theme) throws SQLException {
    	ArrayList<Photo> photos = new ArrayList<>();
		ResultSet set = executeQuery(String.format("SELECT * FROM photo INNER JOIN media ON photo.id = media.id WHERE media.id IN (SELECT media_id FROM " +
					"theme_has_media WHERE theme_id = %s);", theme.getId()));
		while(set.next()){
			photos.add(new Photo(set.getString("media.filePath"), set.getString("media.name"), set.getInt("media.id"), theme.getName()));
		}
		return photos;
    }

	/**
	 * Haal een foto uit de database aan de hand van de opgegeven id.
	 * @param id
	 * @return photo
	 */
    public Photo getPhotoById(int id) throws SQLException {
		ResultSet set = executeQuery(String.format("SELECT * FROM photo INNER JOIN media ON photo.id = media.id WHERE media.id = %s;",id));
		set.next();
		return new Photo(set.getString("media.filePath"), set.getString("media.name"), set.getInt("media.id"), set.getString("media.id"));
    }

    /**
     * Voer een update uit
     *
     * @param update
     */
    public static void executeUpdate(String update) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(update);

    }

    /**
     * Voer een query uit
     *
     * @param query
     * @return set
     */
    public static ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(query);
        return set;
    }

    public class ThemeNotFoundException extends Exception {

    	public ThemeNotFoundException(String message){
    		super(message);
    	}

    }
}

