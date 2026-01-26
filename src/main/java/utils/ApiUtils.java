package utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple utility class for API testing using RestAssured.
 */
public class ApiUtils {

    private static final Logger logger = LoggerFactory.getLogger(ApiUtils.class);
    private static String baseUrl = "";
    private static String authToken = null;
    private static Map<String, String> customHeaders = new HashMap<>();

    // ==================== CONFIGURATION ====================

    /**
     * Sets the base URL for all API requests.
     */
    public static void setBaseUrl(String url) {
        baseUrl = url;
        logger.info("API Base URL set to: {}", url);
    }

    /**
     * Sets the Bearer authentication token.
     */
    public static void setAuthToken(String token) {
        authToken = token;
        logger.info("Auth token set");
    }

    /**
     * Adds a custom header to all requests.
     */
    public static void setHeader(String name, String value) {
        customHeaders.put(name, value);
        logger.info("Header set: {}", name);
    }

    // ==================== HTTP METHODS ====================

    /**
     * Sends a GET request.
     */
    public static Response get(String endpoint) {
        logger.info("GET {}", getFullUrl(endpoint));
        return getRequestSpec().get(getFullUrl(endpoint));
    }

    /**
     * Sends a POST request with JSON body.
     */
    public static Response post(String endpoint, Object body) {
        logger.info("POST {}", getFullUrl(endpoint));
        return getRequestSpec()
                .body(body)
                .post(getFullUrl(endpoint));
    }

    /**
     * Sends a PUT request with JSON body.
     */
    public static Response put(String endpoint, Object body) {
        logger.info("PUT {}", getFullUrl(endpoint));
        return getRequestSpec()
                .body(body)
                .put(getFullUrl(endpoint));
    }

    /**
     * Sends a DELETE request.
     */
    public static Response delete(String endpoint) {
        logger.info("DELETE {}", getFullUrl(endpoint));
        return getRequestSpec().delete(getFullUrl(endpoint));
    }

    // ==================== VALIDATIONS ====================

    /**
     * Validates response status code.
     */
    public static void validateStatusCode(Response response, int expectedStatus) {
        int actualStatus = response.getStatusCode();
        logger.info("Status code: expected={}, actual={}", expectedStatus, actualStatus);
        
        if (actualStatus != expectedStatus) {
            throw new AssertionError("Expected status " + expectedStatus + " but got " + actualStatus);
        }
    }

    /**
     * Gets a value from JSON response.
     */
    public static <T> T getJsonValue(Response response, String jsonPath) {
        return response.jsonPath().get(jsonPath);
    }

    /**
     * Logs the response for debugging.
     */
    public static void logResponse(Response response) {
        logger.info("Response Status: {}", response.getStatusCode());
        logger.info("Response Body: {}", response.getBody().asPrettyString());
    }

    // ==================== PRIVATE HELPERS ====================

    private static RequestSpecification getRequestSpec() {
        RequestSpecification spec = RestAssured.given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
        
        // Add Bearer token if set
        if (authToken != null) {
            spec.header("Authorization", "Bearer " + authToken);
        }
        
        // Add custom headers
        for (Map.Entry<String, String> header : customHeaders.entrySet()) {
            spec.header(header.getKey(), header.getValue());
        }
        
        return spec;
    }

    private static String getFullUrl(String endpoint) {
        if (!baseUrl.isEmpty() && !endpoint.startsWith("http")) {
            String base = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
            String path = endpoint.startsWith("/") ? endpoint : "/" + endpoint;
            return base + path;
        }
        return endpoint;
    }
}
