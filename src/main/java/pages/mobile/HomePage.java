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
 * Page Object for Home screen.
 * Contains elements and actions for the main mobile app screen.
 */
public class HomePage {

    public HomePage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver(), Duration.ofSeconds(10)), this);
    }

    @AndroidFindBy(accessibility = "open menu")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[contains(@label, 'Menu')]")
    private WebElement openMenuButton;

    @iOSXCUITFindBy(xpath = "//*[@label='Log In']")
    @AndroidFindBy(accessibility = "menu item log in")
    private WebElement menuItemLogIn;

    public void tapOnMenuButton() {
        Actions.click(openMenuButton);
    }

    public void tapOnMenuItemLogin() {
        Actions.click(openMenuButton);
        Actions.click(menuItemLogIn);
    }

    public boolean isMenuButtonDisplayed() {
        return Validations.isDisplayed(openMenuButton);
    }
}
