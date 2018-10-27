package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * create table useraccount(
 *   username varchar(55),
 *   password varchar(55),
 *   firstname varchar(55),
 *   lastname varchar(55),
 *   email varchar(55),
 *   gender char,
 *   dob DATE
 * );
 */

public class ConnectionManager {

    private static final String DRIVER = "com.mysql.jdbc.Driver";

    private static final String URL = "jdbc:mysql://localhost:3306/Calendar";

    public static Connection GetConnection() throws ClassNotFoundException, SQLException
    {
        Connection connection = null;
        try
        {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, "root", "12345");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Class: \"com.mysql.jdbc.Driver\" not found");
            e.printStackTrace();
            System.exit(-1);
        }
        catch(SQLException e)
        {
            System.out.println("Failed to connect to database : \"jdbc:mysql://localhost:3306\"");
            e.printStackTrace();
            System.exit(-1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Connection Established!");
        return connection;
    }
}
