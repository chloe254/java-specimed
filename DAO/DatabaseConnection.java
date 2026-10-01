package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitaire pour gérer la connexion JDBC à la base de données MySQL.
 */
public class DatabaseConnection {

    private static final String urlDatabase = "jdbc:mysql://localhost:3306/pj_java";
    private static final String user = "root";
    private static final String password = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(urlDatabase, user, password);
    }
}
