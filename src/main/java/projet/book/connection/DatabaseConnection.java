package projet.book.connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/bookdbb";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    static {
    	try { 
    		Class.forName("com.mysql.cj.jdbc.Driver"); 
    		} catch (ClassNotFoundException e) { 
    			e.printStackTrace(); } 
    	}

    public static java.sql.Connection getConnection() throws SQLException { 
    	return DriverManager.getConnection(URL, USER, PASSWORD); }
}
