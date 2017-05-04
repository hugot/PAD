package nl.amsta09.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlConnector {
	
	public SqlConnector(){
		Connection connection = null;
		Statement hello;
		ResultSet set;
		try{
			connection = DriverManager.getConnection("jdbc:mysql://localhost/photoslider?"
					+ "user=PAD&password=fissa&useUnicode=true&useJDBCCompliantTimezoneShift=true"
					+ "&useLegacyDatetimeCode=false&serverTimezone=UTC");
			hello = connection.createStatement();
			set = hello.executeQuery("SELECT * FROM theme;");
			ResultSetMetaData setMeta = set.getMetaData();
			int columns = setMeta.getColumnCount();
			
			while(set.next()){
				for(int i = 1; i < columns; i++){
					String value = set.getString(i);
					System.out.print(value);
				}
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
}
