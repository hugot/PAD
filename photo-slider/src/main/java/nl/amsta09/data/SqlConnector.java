package nl.amsta09.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.URL;
import javax.swing.Action;

public class SqlConnector {

    Connection connection = null;
    Statement hello;
    ResultSet set;

    public SqlConnector() {
//        Connection connection = null;
//        Statement hello;
//        ResultSet set;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://supersudonator.filmencode.nl/photoslider?"
                    + "user=PAD&password=fissafissaheey123&useUnicode=true&useJDBCCompliantTimezoneShift=true"
                    + "&useLegacyDatetimeCode=false&serverTimezone=UTC");

            hello = connection.createStatement();
            set = hello.executeQuery("SELECT * FROM theme;");
            ResultSetMetaData setMeta = set.getMetaData();
            int columns = setMeta.getColumnCount();

            while (set.next()) {
                for (int i = 1; i < columns; i++) {
                    String value = set.getString(i);
                    System.out.print(value);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
     * Insert een theme naar de database
     */
    public void Execute_Insert_Theme(String Theme_Name) throws SQLException, ClassNotFoundException {

//            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://localhost/photoslider", "root", "Aapjes-14");
        Statement addThemeStatement = connection.createStatement();
        //Insert de gegevens in de database met een unieke ID
        String sql = ("insert into theme (name) VALUES ('" + Theme_Name + "')");

        addThemeStatement.execute(sql);
    }
    
    /*
    Verwijdert de gekozen Thema uit de database
    */
    public void Execute_Delete_Theme(int idTheme) throws SQLException, ClassNotFoundException{
        Statement deleteThemeStatement = connection.createStatement();
        //Verwijdert de thema uit de tabel Thema
        String sql = ("DELETE FROM Thema"
                + "WHERE id = " + idTheme);
        
        deleteThemeStatement.execute(sql);
    }
    
    public void Execute_Activate_Thema(String Theme_Name) throws SQLException {

        Statement activateThemaStatement = connection.createStatement();
        //Activeert het gekozen thema
        String sql = ("UPDATE Thema \n"
                + "SET on/off = 'false'\n"
                + "WHERE name ='" + Theme_Name + "'");

    }
    
    public void Execute_Insert_Media(String URL, String Media_Name) throws SQLException {

        Statement addMediaStatement = connection.createStatement();
        //Insert een media file in de database
        String sql = ("INSERT INTO Media (name, URL) VALUES ('" + Media_Name + "','" + URL + "')");

        addMediaStatement.execute(sql);
    }
    
    /*
    Verwijdert de gekozen Media uit de database
    */
    public void Execute_Delete_Media(URL MediaURL) throws SQLException, ClassNotFoundException{
        
        Statement deleteMediaStatement = connection.createStatement();
        
        String sql = ("DELETE FROM Media"
                + "WHERE URL = " + "'" + MediaURL + "'");
        
        deleteMediaStatement.execute(sql);
    }

    public void Execute_Activate_Media(String Media_Name) throws SQLException {

        Statement activateMediaStatement = connection.createStatement();
        //Activeert de gekozen media file
        String sql = ("UPDATE Media"
                + "SET active = 'true'"
                + "WHERE URL ='" + Media_Name + "'");

        activateMediaStatement.execute(sql);
    }

    public void Execute_Disable_Media(String Media_Name) throws SQLException {

        Statement disableMediaStatement = connection.createStatement();
        //Activeert de gekozen media file
        String sql = ("UPDATE Media"
                + "SET active = 'disable'"
                + "WHERE URL ='" + Media_Name + "'");

        disableMediaStatement.execute(sql);
    }
    
    /*
    Voegt de Soundeffect toe aan de gekozen Foto
    */
    public void Execute_Add_Soundeffect_To_Photo(int idPhoto, int idSoundeffect) throws SQLException, ClassNotFoundException{
        Statement addSoundeffectStatement = connection.createStatement();
        //Voegt de Soundeffect id toe aan de Foto
        String sql = ("INSERT INTO Photo VALUES ('" + idPhoto + ", " + idSoundeffect + "')");
        
        addSoundeffectStatement.execute(sql);
    }
}
