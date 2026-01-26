package Tests.api;

import io.qameta.allure.*;
import org.testng.annotations.*;
import utils.DatabaseUtils;
import utils.ReportUtils;
import utils.TestListener;
import utils.Validations;

import java.util.List;
import java.util.Map;

/**
 * Example Database tests.
 * 
 * NOTE: These tests require a running database.
 * Update application.properties with your database connection details:
 * 
 *   db.type=mysql
 *   db.host=localhost
 *   db.port=3306
 *   db.name=testdb
 *   db.username=root
 *   db.password=yourpassword
 * 
 * Or pass them via command line:
 *   mvn test -Ddb.host=localhost -Ddb.name=mydb -Ddb.username=root -Ddb.password=pass
 */
@Listeners(TestListener.class)
@Epic("Database Testing")
@Feature("SQL Queries")
public class DatabaseExampleTests {

    private DatabaseUtils db;

    // Skip these tests if no database is configured
    // Remove this to run actual database tests
    @BeforeClass
    public void checkDatabaseConfig() {
        String dbHost = System.getProperty("db.host");
        if (dbHost == null || dbHost.isEmpty()) {
            throw new org.testng.SkipException(
                "Database tests skipped. Configure db.* properties to run them."
            );
        }
    }

    @BeforeMethod
    public void connectToDatabase() {
        // Connect using application.properties or system properties
        db = new DatabaseUtils();
    }

    @AfterMethod
    public void closeConnection() {
        if (db != null) {
            db.close();
        }
    }

    @Test
    @Story("SELECT Query")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify SELECT query returns data")
    public void testSelectQuery() {
        ReportUtils.step("Execute SELECT query", () -> {
            // Example: Get all users
            List<Map<String, Object>> users = db.executeQuery(
                "SELECT * FROM users WHERE status = ?", 
                "active"
            );
            
            Validations.validateTrue(users.size() > 0, "Should return at least one user");
            
            // Log the first user
            if (!users.isEmpty()) {
                ReportUtils.attachText("First User", users.get(0).toString());
            }
        });
    }

    @Test
    @Story("Scalar Query")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify COUNT query returns correct count")
    public void testCountQuery() {
        ReportUtils.step("Execute COUNT query", () -> {
            // Example: Count active users
            Object count = db.executeScalar(
                "SELECT COUNT(*) FROM users WHERE status = ?", 
                "active"
            );
            
            Validations.validateTrue(count != null, "Count should not be null");
            
            int userCount = ((Number) count).intValue();
            ReportUtils.attachText("Active Users Count", String.valueOf(userCount));
        });
    }

    @Test
    @Story("INSERT Query")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify INSERT creates a new record")
    public void testInsertQuery() {
        ReportUtils.step("Execute INSERT query", () -> {
            // Example: Insert a new user
            long newId = db.executeInsert(
                "INSERT INTO users (name, email, status) VALUES (?, ?, ?)",
                "Test User", "test@example.com", "active"
            );
            
            Validations.validateTrue(newId > 0, "Should return generated ID");
            ReportUtils.attachText("New User ID", String.valueOf(newId));
            
            // Clean up: Delete the test user
            db.executeUpdate("DELETE FROM users WHERE id = ?", newId);
        });
    }

    @Test
    @Story("UPDATE Query")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify UPDATE modifies records")
    public void testUpdateQuery() {
        ReportUtils.step("Execute UPDATE query", () -> {
            // First, insert a test record
            long testId = db.executeInsert(
                "INSERT INTO users (name, email, status) VALUES (?, ?, ?)",
                "Update Test", "update@example.com", "active"
            );
            
            // Update the record
            int rowsAffected = db.executeUpdate(
                "UPDATE users SET status = ? WHERE id = ?",
                "inactive", testId
            );
            
            Validations.validateEquals(rowsAffected, 1, "Should update 1 row");
            
            // Verify the update
            List<Map<String, Object>> result = db.executeQuery(
                "SELECT status FROM users WHERE id = ?", testId
            );
            
            Validations.validateEquals(result.get(0).get("status"), "inactive", 
                "Status should be updated");
            
            // Clean up
            db.executeUpdate("DELETE FROM users WHERE id = ?", testId);
        });
    }

    @Test
    @Story("Data Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify UI data matches database")
    public void testUiDatabaseMatch() {
        ReportUtils.step("Validate data consistency", () -> {
            // Example: After creating a user via UI, verify in database
            // This is a common pattern in E2E testing
            
            String userEmail = "test.user@example.com";
            
            // Query database for the user
            List<Map<String, Object>> users = db.executeQuery(
                "SELECT * FROM users WHERE email = ?", 
                userEmail
            );
            
            // Validate user exists
            Validations.validateTrue(users.size() == 1, 
                "User should exist in database");
            
            // Validate user data
            Map<String, Object> user = users.get(0);
            Validations.validateEquals(user.get("email"), userEmail, 
                "Email should match");
        });
    }
}
