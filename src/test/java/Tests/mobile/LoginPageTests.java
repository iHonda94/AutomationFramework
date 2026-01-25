package Tests.mobile;

import base.mobile.BaseTest;
import io.qameta.allure.*;
import pages.mobile.HomePage;
import pages.mobile.LoginPage;
import utils.Constants;
import utils.ReportUtils;
import utils.TestListener;
import utils.Validations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
@Epic("Mobile Testing")
@Feature("Login Functionality")
public class LoginPageTests extends BaseTest {

    LoginPage loginPage;
    HomePage homePage;

    @BeforeMethod
    public void initPage() {
        loginPage = new LoginPage();
        homePage = new HomePage();
    }

    @Test
    @Story("Invalid Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that login button remains visible when entering wrong credentials")
    public void verifyUsernameAndPasswordIsWrong() {
        ReportUtils.step("Navigating to login page", () -> {
            homePage.tapOnMenuItemLogin();
        });
        
        ReportUtils.step("Entering invalid username: " + Constants.INVALID_USERNAME, () -> {
            loginPage.typeUserName(Constants.INVALID_USERNAME);
        });
        
        ReportUtils.step("Entering invalid password", () -> {
            loginPage.typePassword(Constants.INVALID_PASSWORD);
        });
        
        ReportUtils.step("Verifying login button is still visible", () -> {
            Validations.validateTrue(loginPage.verifyThatLoginButtonIsDisplayed(), 
                Constants.MSG_LOGIN_BUTTON_VISIBLE);
        });
    }

    @Test
    @Story("Valid Login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that login button disappears after successful login")
    public void verifyUsernameAndPasswordIsCorrect() {
        ReportUtils.step("Navigating to login page", () -> {
            homePage.tapOnMenuItemLogin();
        });
        
        ReportUtils.step("Entering valid username: " + Constants.VALID_USERNAME, () -> {
            loginPage.typeUserName(Constants.VALID_USERNAME);
        });
        
        ReportUtils.step("Entering valid password", () -> {
            loginPage.typePassword(Constants.VALID_PASSWORD);
        });
        
        ReportUtils.step("Verifying login button is not visible after login", () -> {
            Validations.validateFalse(loginPage.verifyThatLoginButtonIsDisplayed(), 
                Constants.MSG_LOGIN_BUTTON_NOT_VISIBLE);
        });
    }

}
