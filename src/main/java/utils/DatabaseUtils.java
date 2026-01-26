package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for database operations.
 * Supports MySQL, PostgreSQL, and SQL Server.
 * 
 * Usage:
 *   DatabaseUtils db = new DatabaseUtils("mysql", "localhost", "3306", "mydb", "user", "pass");
 *   List<Map<String, Object>> results = db.executeQuery("SELECT * FROM users WHERE id = ?", 1);
 *   db.close();
 */
public class DatabaseUtils {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);
    private Connection connection;
    private String dbType;

    /**
     * Creates a database connection.
     * 
     * @param dbType   Database type: "mysql", "postgresql", or "sqlserver"
     * @param host     Database host (e.g., "localhost")
     * @param port     Database port (e.g., "3306" for MySQL)
     * @param database Database name
     * @param username Database username
     * @param password Database password
     */
    public DatabaseUtils(String dbType, String host, String port, String database, String username, String password) {
        this.dbType = dbType.toLowerCase();
        String url = buildConnectionUrl(this.dbType, host, port, database);
        connect(url, username, password);
    }

    /**
     * Creates a database connection using application.properties.
     * Reads: db.type, db.host, db.port, db.name, db.username, db.password
     */
    public DatabaseUtils() {
        this.dbType = Config.get("db.type", "mysql").toLowerCase();
        String host = Config.get("db.host", "localhost");
        String port = Config.get("db.port", getDefaultPort(this.dbType));
        String database = Config.get("db.name", "test");
        String username = Config.get("db.username", "root");
        String password = Config.get("db.password", "");
        
        String url = buildConnectionUrl(this.dbType, host, port, database);
        connect(url, username, password);
    }

    /**
     * Builds the JDBC connection URL based on database type.
     */
    private String buildConnectionUrl(String dbType, String host, String port, String database) {
        switch (dbType) {
            case "mysql":
                return String.format("jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true", host, port, database);
            case "postgresql":
            case "postgres":
                return String.format("jdbc:postgresql://%s:%s/%s", host, port, database);
            case "sqlserver":
            case "mssql":
                return String.format("jdbc:sqlserver://%s:%s;databaseName=%s;trustServerCertificate=true", host, port, database);
            default:
                throw new IllegalArgumentException("Unsupported database type: " + dbType);
        }
    }

    /**
     * Gets the default port for each database type.
     */
    private String getDefaultPort(String dbType) {
        switch (dbType) {
            case "mysql": return "3306";
            case "postgresql":
            case "postgres": return "5432";
            case "sqlserver":
            case "mssql": return "1433";
            default: return "3306";
        }
    }

    /**
     * Establishes the database connection.
     */
    private void connect(String url, String username, String password) {
        try {
            logger.info("Connecting to database: {}", url.replaceAll("password=.*", "password=***"));
            connection = DriverManager.getConnection(url, username, password);
            logger.info("Database connection established successfully");
        } catch (SQLException e) {
            logger.error("Failed to connect to database: {}", e.getMessage());
            throw new RuntimeException("Database connection failed", e);
        }
    }

    /**
     * Executes a SELECT query and returns results as a list of maps.
     * 
     * @param query  SQL query with ? placeholders for parameters
     * @param params Parameters to bind to the query
     * @return List of rows, each row is a Map of column name to value
     * 
     * Example:
     *   List<Map<String, Object>> users = db.executeQuery(
     *       "SELECT * FROM users WHERE status = ? AND age > ?", 
     *       "active", 18
     *   );
     */
    public List<Map<String, Object>> executeQuery(String query, Object... params) {
        List<Map<String, Object>> results = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Bind parameters
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            
            logger.info("Executing query: {}", query);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                results.add(row);
            }
            
            logger.info("Query returned {} rows", results.size());
            
        } catch (SQLException e) {
            logger.error("Query execution failed: {}", e.getMessage());
            throw new RuntimeException("Query execution failed", e);
        }
        
        return results;
    }

    /**
     * Executes a single-value query (e.g., COUNT, SUM, single column).
     * 
     * @param query  SQL query
     * @param params Parameters to bind
     * @return Single value result, or null if no results
     * 
     * Example:
     *   int count = (int) db.executeScalar("SELECT COUNT(*) FROM users WHERE status = ?", "active");
     */
    public Object executeScalar(String query, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            
            logger.info("Executing scalar query: {}", query);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getObject(1);
            }
            return null;
            
        } catch (SQLException e) {
            logger.error("Scalar query failed: {}", e.getMessage());
            throw new RuntimeException("Scalar query failed", e);
        }
    }

    /**
     * Executes an INSERT, UPDATE, or DELETE statement.
     * 
     * @param query  SQL statement
     * @param params Parameters to bind
     * @return Number of rows affected
     * 
     * Example:
     *   int updated = db.executeUpdate("UPDATE users SET status = ? WHERE id = ?", "inactive", 5);
     */
    public int executeUpdate(String query, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            
            logger.info("Executing update: {}", query);
            int rowsAffected = stmt.executeUpdate();
            logger.info("Rows affected: {}", rowsAffected);
            
            return rowsAffected;
            
        } catch (SQLException e) {
            logger.error("Update execution failed: {}", e.getMessage());
            throw new RuntimeException("Update execution failed", e);
        }
    }

    /**
     * Executes an INSERT and returns the generated key (auto-increment ID).
     * 
     * @param query  INSERT statement
     * @param params Parameters to bind
     * @return Generated key (ID), or -1 if none
     */
    public long executeInsert(String query, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            
            logger.info("Executing insert: {}", query);
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                long id = rs.getLong(1);
                logger.info("Generated ID: {}", id);
                return id;
            }
            return -1;
            
        } catch (SQLException e) {
            logger.error("Insert execution failed: {}", e.getMessage());
            throw new RuntimeException("Insert execution failed", e);
        }
    }

    /**
     * Checks if the connection is valid.
     */
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed() && connection.isValid(5);
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Closes the database connection.
     */
    public void close() {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Database connection closed");
            } catch (SQLException e) {
                logger.error("Failed to close connection: {}", e.getMessage());
            }
        }
    }

    /**
     * Gets the underlying JDBC connection for advanced operations.
     */
    public Connection getConnection() {
        return connection;
    }
}
