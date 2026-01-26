package Tests.db;

import io.qameta.allure.*;
import org.testng.SkipException;
import org.testng.annotations.*;
import utils.Constants;
import utils.DatabaseUtils;
import utils.TestListener;
import utils.Validations;

import java.util.List;
import java.util.Map;

/**
 * Database Tests for SQL queries.
 * 
 * Note: Azure AD MFA authentication requires token-based connection.
 * Test is skipped by default.
 */
@Listeners(TestListener.class)
@Epic("Database Testing")
@Feature("SQL Queries")
public class DatabaseTests {

    private DatabaseUtils db;

    @BeforeMethod
    public void connectToDatabase() {
        // Skip test - Azure AD MFA not supported by JDBC
        // Remove this line when using standard SQL authentication
        throw new SkipException("Skipped: Azure AD MFA authentication requires token-based connection");
        
        // Connect using application.properties
        // db = new DatabaseUtils();
    }

    @AfterMethod
    public void closeConnection() {
        if (db != null) {
            db.close();
        }
    }

    @Test
    @Description("Verify SELECT query returns plans created by user")
    public void testSelectPlans() {
        // Execute query with parameter
        List<Map<String, Object>> results = db.executeQuery(
            Constants.DB_QUERY_PLANS,
            Constants.DB_CREATED_BY
        );
        
        // Validate we got results
        Validations.validateTrue(results.size() > 0, "Should return at least one plan");
        
        // Validate first row has expected creator
        String createdBy = (String) results.get(0).get("CreatedBy");
        Validations.validateEquals(createdBy, Constants.DB_CREATED_BY, "CreatedBy should match");
    }
}
