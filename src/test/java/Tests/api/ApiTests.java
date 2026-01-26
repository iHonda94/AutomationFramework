package Tests.api;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ApiUtils;
import utils.Constants;
import utils.TestListener;
import utils.Validations;

/**
 * API Tests for Prospect endpoints.
 */
@Listeners(TestListener.class)
@Epic("API Testing")
@Feature("Prospect API")
public class ApiTests {

    @BeforeClass
    public void setup() {
        // Set base URL from Constants
        ApiUtils.setBaseUrl(Constants.API_BASE_URL);
        
        // Set authentication token from Constants
        ApiUtils.setAuthToken(Constants.API_BEARER_TOKEN);
        
        // Set custom header from Constants
        ApiUtils.setHeader("ocp-apim-subscription-key", Constants.API_SUBSCRIPTION_KEY);
    }

    @Test
    @Description("Verify GET prospect returns correct data")
    public void testGetProspect() {
        // Send GET request
        Response response = ApiUtils.get("/prospects/" + Constants.PROSPECT_ID + "/get");
        
        // Validate status code
        ApiUtils.validateStatusCode(response, 200);
        
        // Validate response data
        int id = ApiUtils.getJsonValue(response, "data.id");
        String accountName = ApiUtils.getJsonValue(response, "data.accountName");
        String city = ApiUtils.getJsonValue(response, "data.address.city");
        String state = ApiUtils.getJsonValue(response, "data.address.stateCode");
        
        // Assert values match expected
        Validations.validateEquals(id, Constants.EXPECTED_PROSPECT_ID, "Prospect ID should match");
        Validations.validateEquals(accountName, Constants.EXPECTED_ACCOUNT_NAME, "Account name should match");
        Validations.validateEquals(city, Constants.EXPECTED_CITY, "City should match");
        Validations.validateEquals(state, Constants.EXPECTED_STATE, "State should match");
        
        // Log the response
        ApiUtils.logResponse(response);
    }
}
