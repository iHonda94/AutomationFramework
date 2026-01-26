package Tests.api;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ApiUtils;
import utils.ReportUtils;
import utils.TestListener;
import utils.Validations;

import java.util.List;
import java.util.Map;

/**
 * Example API tests using RestAssured.
 * Uses JSONPlaceholder (free fake API) for demonstration.
 * 
 * API Documentation: https://jsonplaceholder.typicode.com/
 */
@Listeners(TestListener.class)
@Epic("API Testing")
@Feature("REST API")
public class ApiExampleTests {

    @BeforeClass
    public void setup() {
        // Set the base URL for all API requests
        ApiUtils.setBaseUrl("https://jsonplaceholder.typicode.com");
    }

    @Test
    @Story("GET Request")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify GET request returns user data")
    public void testGetUser() {
        ReportUtils.step("Send GET request to /users/1", () -> {
            Response response = ApiUtils.get("/users/1");
            
            // Validate status code
            ApiUtils.validateStatusCode(response, 200);
            
            // Validate response fields
            ApiUtils.validateJsonField(response, "id", 1);
            ApiUtils.validateJsonFieldContains(response, "name", "Leanne");
            
            // Get values from response
            String email = ApiUtils.getJsonValue(response, "email");
            ReportUtils.attachText("User Email", email);
        });
    }

    @Test
    @Story("GET Request")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify GET request with query parameters")
    public void testGetUsersWithParams() {
        ReportUtils.step("Send GET request with query params", () -> {
            // Get posts by user ID
            Response response = ApiUtils.get("/posts", Map.of("userId", "1"));
            
            ApiUtils.validateStatusCode(response, 200);
            
            // Validate we got a list of posts
            List<Object> posts = response.jsonPath().getList("$");
            Validations.validateTrue(posts.size() > 0, "Should return posts for user 1");
            
            ReportUtils.attachText("Number of posts", String.valueOf(posts.size()));
        });
    }

    @Test
    @Story("POST Request")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify POST request creates a new resource")
    public void testCreatePost() {
        ReportUtils.step("Send POST request to create a new post", () -> {
            // Create request body
            Map<String, Object> newPost = Map.of(
                    "title", "My Test Post",
                    "body", "This is the post content",
                    "userId", 1
            );
            
            Response response = ApiUtils.post("/posts", newPost);
            
            // Validate status code (201 = Created)
            ApiUtils.validateStatusCode(response, 201);
            
            // Validate response contains our data
            ApiUtils.validateJsonField(response, "title", "My Test Post");
            ApiUtils.validateJsonField(response, "userId", 1);
            
            // Get the created post ID
            Integer postId = ApiUtils.getJsonValue(response, "id");
            Validations.validateTrue(postId != null && postId > 0, "Post ID should be generated");
            
            ReportUtils.attachText("Created Post ID", String.valueOf(postId));
        });
    }

    @Test
    @Story("PUT Request")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify PUT request updates a resource")
    public void testUpdatePost() {
        ReportUtils.step("Send PUT request to update post", () -> {
            Map<String, Object> updatedPost = Map.of(
                    "id", 1,
                    "title", "Updated Title",
                    "body", "Updated content",
                    "userId", 1
            );
            
            Response response = ApiUtils.put("/posts/1", updatedPost);
            
            ApiUtils.validateStatusCode(response, 200);
            ApiUtils.validateJsonField(response, "title", "Updated Title");
        });
    }

    @Test
    @Story("DELETE Request")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify DELETE request removes a resource")
    public void testDeletePost() {
        ReportUtils.step("Send DELETE request", () -> {
            Response response = ApiUtils.delete("/posts/1");
            
            // JSONPlaceholder returns 200 for successful delete
            ApiUtils.validateStatusCode(response, 200);
        });
    }

    @Test
    @Story("Error Handling")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify 404 response for non-existent resource")
    public void testNotFound() {
        ReportUtils.step("Request non-existent resource", () -> {
            Response response = ApiUtils.get("/users/999999");
            
            // Should return 404
            ApiUtils.validateStatusCode(response, 404);
        });
    }
}
