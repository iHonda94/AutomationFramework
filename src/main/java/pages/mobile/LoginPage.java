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

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeTextField")
    @AndroidFindBy(accessibility = "Username input field")
    private WebElement userNameField;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeSecureTextField")
    @AndroidFindBy(accessibility = "Password input field")
    private WebElement passwordField;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@label='Login']")
    @AndroidFindBy(accessibility = "Login button")
    private WebElement loginButton;

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

    public boolean verifyThatLoginButtonIsDisplayed() {
        return Validations.isDisplayed(loginButton);
    }

    public String getUserNameText() {
        return Actions.getText(userNameField);
    }
}
