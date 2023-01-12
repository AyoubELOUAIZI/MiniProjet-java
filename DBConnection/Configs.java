package DBConnection;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configs class is a singleton class that provides methods for
 * establishing a connection to a MySQL database and executing
 * queries and updates.
 */
public class Configs {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pos";

    // Database credentials
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    // Singleton instance of Configs
    private static Configs handler;
    // JDBC connection
    private static Connection con;

    /**
     * Private constructor to prevent instantiation
     */
    private Configs() {
        createConnection();
    }

    /**
     * Returns the singleton instance of Configs
     * @return Configs instance
     */
    public static Configs getInstance() {
        if (handler == null) {
            handler = new Configs();
        }
        return handler;
    }

    /**
     * Establishes a connection to the MySQL database
     */
    void createConnection() {
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Executes a prepared statement and returns the PreparedStatement object
     * @param query SQL query to be executed
     * @return Prepared Statement object
     */
    public PreparedStatement execQueryPrep(String query) {
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(query);
        } catch (SQLException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pst;
    }

    /**
     * Executes a SELECT query and returns the result set
     * @param query SQL SELECT query to be executed
     * @return ResultSet object
     */
    public ResultSet execQuery(String query) {
        ResultSet rs = null;
        try {
            // Create a statement
            Statement stmt = con.createStatement();

            // Execute the query and return the result set
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    /**
     * Executes an INSERT, UPDATE, or DELETE query
     * @param query SQL query to be executed
     */
    public boolean execAction(String query) {
        try {
            Statement stmt = con.createStatement();
            stmt.execute(query);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void closeConnection() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

///////////////////////////////////
/*
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configs {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "mysql";

    private static Configs handler;
    private static Connection con;

    private Configs() {
        createConnection();
    }

    public static Configs getInstance() {
        if (handler == null) {
            handler = new Configs();
        }
        return handler;
    }

    void createConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PreparedStatement execQueryPrep(String query) {
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(query);
        } catch (SQLException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pst;
    }

    public ResultSet execQuery(String query) {
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public boolean execAction(String query) {
        try {
            Statement stmt = con.createStatement();
            stmt.execute(query);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void closeConnection() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Configs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}



*/