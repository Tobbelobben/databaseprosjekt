package database;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseManager {
	
	public static ArrayList<HashMap<String, String>> sendQuery(String query) {
		try {
			// Kobler til mySQL-server, og henter data derfra, avhengig av query-et som mates inn.
			Class.forName("com.mysql.cj.jdbc.Driver");
			String conString = "jdbc:mysql://mysql.stud.ntnu.no/tobiasio_datdat?serverTimezone=UTC";  
			Connection connection = DriverManager.getConnection(conString, "tobiasio_11", "gruppe11");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			// return-liste som inneholder hvert objekt som en hashmap mellom kolonne-overskrift og kolonne-verdi.
			ArrayList<HashMap<String, String>> resultArray = new ArrayList<HashMap<String, String>>(); 
			
			while (resultSet.next()) {
				HashMap<String, String> currentRow = new HashMap<String, String>();
			       for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			           currentRow.put(rsmd.getColumnName(i), resultSet.getString(i));
			       } // lagrer et objekt i hashmappet "currentRow"
			       resultArray.add(currentRow); // lagrer currentRow i return-lista
		    }
			connection.close();
			return resultArray;
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Connection failed.");
			e.printStackTrace();
			return null;
		}
	}
	
	public static int sendUpdate(String update) {
		try {
			// Kobler til mySQL-server, og henter data derfra, avhengig av update-n som mates inn.
			Class.forName("com.mysql.cj.jdbc.Driver");
			String conString = "jdbc:mysql://mysql.stud.ntnu.no/tobiasio_datdat?serverTimezone=UTC";  
			Connection connection = DriverManager.getConnection(conString, "tobiasio_11", "gruppe11");
			Statement statement = connection.createStatement();
			int rowsAffected = statement.executeUpdate(update);
			connection.close();
			
			
			return rowsAffected;
			
		} catch (ClassNotFoundException| SQLException e) {
			System.out.println("Connection failed.");
			e.printStackTrace();
			return 0;
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println(DatabaseManager.sendQuery("SELECT * FROM Machine"));
	}
}
