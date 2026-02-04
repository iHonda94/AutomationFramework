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

    @AndroidFindBy(accessibility = "menu item log in")
    @iOSXCUITFindBy(xpath = "//*[@label='Log In']")
    private WebElement menuItemLogIn;

    @AndroidFindBy(accessibility = "menu item log out")
    @iOSXCUITFindBy(accessibility = "menu item log out")
    private WebElement menuItemLogOut;

    @AndroidFindBy(accessibility = "menu item catalog")
    @iOSXCUITFindBy(accessibility = "menu item catalog")
    private WebElement menuItemCatalog;

    @AndroidFindBy(accessibility = "menu item about")
    @iOSXCUITFindBy(accessibility = "menu item about")
    private WebElement menuItemAbout;

    @AndroidFindBy(accessibility = "menu item reset app")
    @iOSXCUITFindBy(accessibility = "menu item reset app")
    private WebElement menuItemResetApp;

    @AndroidFindBy(accessibility = "longpress reset app")
    @iOSXCUITFindBy(accessibility = "longpress reset app")
    private WebElement longPressResetApp;

    public void tapOnMenuButton() {
        Actions.click(openMenuButton);
    }

    public void tapOnMenuItemLogin() {
        Actions.click(openMenuButton);
        Actions.click(menuItemLogIn);
    }

    public void tapOnMenuItemLogout() {
        Actions.click(openMenuButton);
        Actions.click(menuItemLogOut);
    }

    public void tapOnMenuItemCatalog() {
        Actions.click(openMenuButton);
        Actions.click(menuItemCatalog);
    }

    public void tapOnMenuItemAbout() {
        Actions.click(openMenuButton);
        Actions.click(menuItemAbout);
    }

    public void tapOnMenuItemResetApp() {
        Actions.click(openMenuButton);
        Actions.click(menuItemResetApp);
    }

    public void longPressToResetApp() {
        Actions.click(longPressResetApp);
    }

    public boolean isMenuButtonDisplayed() {
        return Validations.isDisplayed(openMenuButton);
    }
}
