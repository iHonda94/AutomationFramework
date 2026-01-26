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
 */
public class DatabaseUtils {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);
    private Connection connection;

    /**
     * Creates a database connection using application.properties.
     */
    public DatabaseUtils() {
        String host = Config.get("db.host", "localhost");
        String port = Config.get("db.port", "1433");
        String database = Config.get("db.name", "test");
        String username = Config.get("db.username", "sa");
        String password = Config.get("db.password", "");
        
        String url = String.format("jdbc:sqlserver://%s:%s;databaseName=%s;trustServerCertificate=true", 
            host, port, database);
        
        try {
            logger.info("Connecting to database...");
            connection = java.sql.DriverManager.getConnection(url, username, password);
            logger.info("Database connected successfully");
        } catch (SQLException e) {
            logger.error("Connection failed: {}", e.getMessage());
            throw new RuntimeException("Database connection failed", e);
        }
    }

    /**
     * Executes a SELECT query and returns results.
     */
    public List<Map<String, Object>> executeQuery(String query, Object... params) {
        List<Map<String, Object>> results = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();
            
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(meta.getColumnName(i), rs.getObject(i));
                }
                results.add(row);
            }
            logger.info("Query returned {} rows", results.size());
            
        } catch (SQLException e) {
            logger.error("Query failed: {}", e.getMessage());
            throw new RuntimeException("Query failed", e);
        }
        return results;
    }

    /**
     * Closes the database connection.
     */
    public void close() {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Connection closed");
            } catch (SQLException e) {
                logger.error("Close failed: {}", e.getMessage());
            }
        }
    }
}
