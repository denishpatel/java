import java.sql.*;
 
public class PgJSONExample {
 
        public static void main(String[] argv) {
 
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
                                        "jdbc:postgresql://localhost:5432/postgres", "denish",
                                        "omniti");
 
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

                int id = 1;
		String data = "{\"username\":\"denish\",\"posts\":10122,\"emailaddress\":\"denish@omniti.com\"}";

                try { // I show a preparedstatement example here because it's probably more generally useful than the plain statement approach.
			PreparedStatement stmt = connection.prepareStatement("insert into sample (id, data) values (?,CAST(? as json) )");
                        stmt.setInt(1, id); // placeholder index is 1-based for jdbc (and raw jdbc only does numeric placeholder references sadly. spring's jdbctemplate can handle named params)
			stmt.setString(2, data); // I am not 100% sure this'll work w/ json datatype.  My server at home is 9.1, otherwise I'd test for sure.  Guessing it's ok?
                        
                        stmt.executeUpdate(); 
// executeQuery is for selects and throws sqlexception if no resultset is generated, executeUpdate is for things that wouldn't return e.g. insert w/out a returning clause

                        stmt.close();
                } catch (SQLException sqle) {
                	System.err.println("Something exploded running the insert: " + sqle.getMessage());
                }
        }
 
}
