import org.postgresql.util.PGobject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
 
public class PgJSONExample {
 
        public static void main(String[] argv) throws SQLException
        {
 
                System.out.println("-------- PostgreSQL "
                                + "JDBC Connection Testing ------------");
 
                try {
 
                        Class.forName("org.postgresql.Driver");
 
                } catch (ClassNotFoundException e) {
 
                        System.out.println("Where is your PostgreSQL JDBC Driver? "
                                        + "Include in your library path!");
                        e.printStackTrace();
                        return;
 
                }
 
                System.out.println("PostgreSQL JDBC Driver Registered!");
 
                Connection connection = null;
 
                try {
 
                        connection = DriverManager.getConnection(
                                        "jdbc:postgresql://localhost:5432/postgres", "denish", "omniti");
 
                } catch (SQLException e) {
 
                        System.out.println("Connection Failed! Check output console");
                        e.printStackTrace();
                        return;
 
                }
 
                if (connection != null) {
                        System.out.println("You made it, take control your database now!");
                } else {
                        System.out.println("Failed to make connection!");
                }

                int id = 2;
		String data = "{\"username\":\"robert\",\"posts\":100122,\"emailaddress\":\"robert@omniti.com\"}";

            PGobject dataObject = new PGobject();
            dataObject.setType("json");
            dataObject.setValue(data);

                try { 
			PreparedStatement stmt = connection.prepareStatement("insert into sample (id, data) values (?, ?)");
                        stmt.setInt(1, id);
			stmt.setObject(2, dataObject); 
                        
                        stmt.executeUpdate(); 

                        stmt.close();
                } catch (SQLException sqle) {
                	System.err.println("Something exploded running the insert: " + sqle.getMessage());
                }
        }
 
}
