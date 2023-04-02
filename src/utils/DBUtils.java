package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/jdbcdb?useSSL=false&allowPublicKeyRetrieval=true";

        if(connection == null){
            connection = DriverManager.getConnection(url,"root", "sysdba");
        }
        return connection;
    }
}
