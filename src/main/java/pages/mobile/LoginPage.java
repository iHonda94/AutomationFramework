package pages.mobile;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.Actions;
import utils.DriverManager;
import utils.Validations;

import java.time.Duration;

/**
 * Page Object for Login screen.
 * Contains elements and actions for mobile login functionality.
 */
public class LoginPage {

    // Test credentials
    public static final String VALID_USERNAME = "bob@example.com";
    public static final String VALID_PASSWORD = "10203040";
    public static final String LOCKED_USERNAME = "alice@example.com";

    @AndroidFindBy(accessibility = "login screen")
    @iOSXCUITFindBy(accessibility = "login screen")
    private WebElement loginScreen;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeTextField")
    @AndroidFindBy(accessibility = "Username input field")
    private WebElement userNameField;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeSecureTextField")
    @AndroidFindBy(accessibility = "Password input field")
    private WebElement passwordField;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@label='Login']")
    @AndroidFindBy(accessibility = "Login button")
    private WebElement loginButton;

    // Autofill buttons for quick login
    @AndroidFindBy(accessibility = "bob@example.com-autofill")
    @iOSXCUITFindBy(accessibility = "bob@example.com-autofill")
    private WebElement bobAutofill;

    @AndroidFindBy(accessibility = "alice@example.com (locked out)-autofill")
    @iOSXCUITFindBy(accessibility = "alice@example.com (locked out)-autofill")
    private WebElement aliceAutofill;

    // Error messages
    @AndroidFindBy(accessibility = "Username-error-message")
    @iOSXCUITFindBy(accessibility = "Username-error-message")
    private WebElement usernameErrorMessage;

    @AndroidFindBy(accessibility = "Password-error-message")
    @iOSXCUITFindBy(accessibility = "Password-error-message")
    private WebElement passwordErrorMessage;

    @AndroidFindBy(accessibility = "generic-error-message")
    @iOSXCUITFindBy(accessibility = "generic-error-message")
    private WebElement genericErrorMessage;

    public LoginPage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver(), Duration.ofSeconds(10)), this);
    }

    public void typeUserName(String userName) {
        Actions.type(userNameField, userName);
    }

    public void typePassword(String password) {
        Actions.type(passwordField, password);
    }

    public void tapOnLoginButton() {
        Actions.click(loginButton);
    }

    /**
     * Quick login using Bob's autofill button
     */
    public void tapBobAutofill() {
        Actions.click(bobAutofill);
    }

    /**
     * Quick login using Alice's autofill button (locked out user)
     */
    public void tapAliceAutofill() {
        Actions.click(aliceAutofill);
    }

    /**
     * Complete login with valid credentials
     */
    public void loginWithValidCredentials() {
        tapBobAutofill();
        tapOnLoginButton();
    }

    /**
     * Complete login with custom credentials
     */
    public void login(String username, String password) {
        typeUserName(username);
        typePassword(password);
        tapOnLoginButton();
    }

    public boolean isLoginScreenDisplayed() {
        return Validations.isDisplayed(loginScreen);
    }

    public boolean verifyThatLoginButtonIsDisplayed() {
        return Validations.isDisplayed(loginButton);
    }

    public String getUserNameText() {
        return Actions.getText(userNameField);
    }

    public boolean isUsernameErrorDisplayed() {
        try {
            return Validations.isDisplayed(usernameErrorMessage);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPasswordErrorDisplayed() {
        try {
            return Validations.isDisplayed(passwordErrorMessage);
        } catch (Exception e) {
            return false;
        }
    }
}
