package utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Utility class for API testing using RestAssured.
 * Automatically logs requests/responses to Allure report.
 * 
 * Usage:
 *   Response response = ApiUtils.get("https://api.example.com/users");
 *   Response response = ApiUtils.post("https://api.example.com/users", jsonBody);
 */
public class ApiUtils {

    private static final Logger logger = LoggerFactory.getLogger(ApiUtils.class);
    private static String baseUrl = Config.get("api.baseUrl", "");
    private static String authToken = null;

    // ==================== CONFIGURATION ====================

    /**
     * Sets the base URL for all API requests.
     * Example: ApiUtils.setBaseUrl("https://api.example.com");
     */
    public static void setBaseUrl(String url) {
        baseUrl = url;
        logger.info("API Base URL set to: {}", url);
    }

    /**
     * Sets the authentication token (Bearer token).
     * Example: ApiUtils.setAuthToken("eyJhbGciOiJIUzI1...");
     */
    public static void setAuthToken(String token) {
        authToken = token;
        logger.info("Auth token set");
    }

    /**
     * Clears the authentication token.
     */
    public static void clearAuthToken() {
        authToken = null;
        logger.info("Auth token cleared");
    }

    // ==================== HTTP METHODS ====================

    /**
     * Sends a GET request.
     * 
     * @param endpoint API endpoint (will be appended to baseUrl if set)
     * @return Response object
     * 
     * Example:
     *   Response response = ApiUtils.get("/users/1");
     *   int statusCode = response.getStatusCode();
     *   String name = response.jsonPath().getString("name");
     */
    public static Response get(String endpoint) {
        logger.info("GET {}", getFullUrl(endpoint));
        return getRequestSpec()
                .get(getFullUrl(endpoint));
    }

    /**
     * Sends a GET request with query parameters.
     * 
     * Example:
     *   Response response = ApiUtils.get("/users", Map.of("status", "active", "page", "1"));
     */
    public static Response get(String endpoint, Map<String, ?> queryParams) {
        logger.info("GET {} with params: {}", getFullUrl(endpoint), queryParams);
        return getRequestSpec()
                .queryParams(queryParams)
                .get(getFullUrl(endpoint));
    }

    /**
     * Sends a POST request with JSON body.
     * 
     * @param endpoint API endpoint
     * @param body     Request body (will be serialized to JSON)
     * @return Response object
     * 
     * Example:
     *   Map<String, Object> user = Map.of("name", "John", "email", "john@example.com");
     *   Response response = ApiUtils.post("/users", user);
     */
    public static Response post(String endpoint, Object body) {
        logger.info("POST {} with body", getFullUrl(endpoint));
        return getRequestSpec()
                .contentType(ContentType.JSON)
                .body(body)
                .post(getFullUrl(endpoint));
    }

    /**
     * Sends a POST request without body.
     */
    public static Response post(String endpoint) {
        logger.info("POST {}", getFullUrl(endpoint));
        return getRequestSpec()
                .post(getFullUrl(endpoint));
    }

    /**
     * Sends a PUT request with JSON body.
     * 
     * Example:
     *   Map<String, Object> update = Map.of("name", "John Updated");
     *   Response response = ApiUtils.put("/users/1", update);
     */
    public static Response put(String endpoint, Object body) {
        logger.info("PUT {} with body", getFullUrl(endpoint));
        return getRequestSpec()
                .contentType(ContentType.JSON)
                .body(body)
                .put(getFullUrl(endpoint));
    }

    /**
     * Sends a PATCH request with JSON body.
     */
    public static Response patch(String endpoint, Object body) {
        logger.info("PATCH {} with body", getFullUrl(endpoint));
        return getRequestSpec()
                .contentType(ContentType.JSON)
                .body(body)
                .patch(getFullUrl(endpoint));
    }

    /**
     * Sends a DELETE request.
     * 
     * Example:
     *   Response response = ApiUtils.delete("/users/1");
     */
    public static Response delete(String endpoint) {
        logger.info("DELETE {}", getFullUrl(endpoint));
        return getRequestSpec()
                .delete(getFullUrl(endpoint));
    }

    // ==================== RESPONSE HELPERS ====================

    /**
     * Gets a value from JSON response using JsonPath.
     * 
     * Example:
     *   String name = ApiUtils.getJsonValue(response, "data.user.name");
     *   List<String> names = ApiUtils.getJsonValue(response, "users.name");
     */
    public static <T> T getJsonValue(Response response, String jsonPath) {
        return response.jsonPath().get(jsonPath);
    }

    /**
     * Validates response status code.
     * 
     * Example:
     *   ApiUtils.validateStatusCode(response, 200);
     */
    public static void validateStatusCode(Response response, int expectedStatus) {
        int actualStatus = response.getStatusCode();
        logger.info("Validating status code: expected={}, actual={}", expectedStatus, actualStatus);
        
        if (actualStatus != expectedStatus) {
            logger.error("Status code mismatch! Response body: {}", response.getBody().asString());
            throw new AssertionError(String.format(
                    "Expected status code %d but got %d. Response: %s",
                    expectedStatus, actualStatus, response.getBody().asString()
            ));
        }
    }

    /**
     * Validates that a JSON field equals expected value.
     * 
     * Example:
     *   ApiUtils.validateJsonField(response, "status", "success");
     */
    public static void validateJsonField(Response response, String jsonPath, Object expectedValue) {
        Object actualValue = response.jsonPath().get(jsonPath);
        logger.info("Validating JSON field '{}': expected={}, actual={}", jsonPath, expectedValue, actualValue);
        
        if (!expectedValue.equals(actualValue)) {
            throw new AssertionError(String.format(
                    "JSON field '%s' mismatch. Expected: %s, Actual: %s",
                    jsonPath, expectedValue, actualValue
            ));
        }
    }

    /**
     * Validates that JSON field contains expected text.
     */
    public static void validateJsonFieldContains(Response response, String jsonPath, String expectedText) {
        String actualValue = response.jsonPath().getString(jsonPath);
        logger.info("Validating JSON field '{}' contains '{}'", jsonPath, expectedText);
        
        if (actualValue == null || !actualValue.contains(expectedText)) {
            throw new AssertionError(String.format(
                    "JSON field '%s' should contain '%s'. Actual: %s",
                    jsonPath, expectedText, actualValue
            ));
        }
    }

    /**
     * Logs the full response for debugging.
     */
    public static void logResponse(Response response) {
        logger.info("Response Status: {}", response.getStatusCode());
        logger.info("Response Headers: {}", response.getHeaders());
        logger.info("Response Body: {}", response.getBody().asPrettyString());
    }

    // ==================== PRIVATE HELPERS ====================

    /**
     * Creates the base request specification with Allure logging.
     */
    private static RequestSpecification getRequestSpec() {
        RequestSpecification spec = RestAssured.given()
                .filter(new AllureRestAssured())  // Logs to Allure report
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
        
        // Add auth token if set
        if (authToken != null && !authToken.isEmpty()) {
            spec.header("Authorization", "Bearer " + authToken);
        }
        
        return spec;
    }

    /**
     * Builds the full URL from base URL and endpoint.
     */
    private static String getFullUrl(String endpoint) {
        if (baseUrl != null && !baseUrl.isEmpty() && !endpoint.startsWith("http")) {
            // Remove trailing slash from baseUrl and leading slash from endpoint
            String base = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
            String path = endpoint.startsWith("/") ? endpoint : "/" + endpoint;
            return base + path;
        }
        return endpoint;
    }
}
