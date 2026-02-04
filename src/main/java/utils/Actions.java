package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Wrapper class for common UI actions.
 * Provides reusable methods for clicking, typing, scrolling, etc.
 * Uses DriverManager to get the driver - no need to pass driver as parameter.
 */
public class Actions {

    private static final Logger logger = LoggerFactory.getLogger(Actions.class);
    private static final int DEFAULT_TIMEOUT = 10;

    /**
     * Gets the driver from DriverManager.
     */
    private static WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    // ==================== CLICK ACTIONS ====================

    /**
     * Clicks on an element after waiting for it to be clickable.
     */
    public static void click(WebElement element) {
        logger.info("Clicking on element: {}", getElementDescription(element));
        waitForClickable(element, DEFAULT_TIMEOUT);
        element.click();
        logger.debug("Click successful");
    }

    /**
     * Clicks on an element with custom timeout.
     */
    public static void click(WebElement element, int timeoutSeconds) {
        logger.info("Clicking on element with {}s timeout: {}", timeoutSeconds, getElementDescription(element));
        waitForClickable(element, timeoutSeconds);
        element.click();
        logger.debug("Click successful");
    }

    /**
     * Double clicks on an element.
     */
    public static void doubleClick(WebElement element) {
        logger.info("Double clicking on element: {}", getElementDescription(element));
        waitForClickable(element, DEFAULT_TIMEOUT);
        org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(getDriver());
        actions.doubleClick(element).perform();
        logger.debug("Double click successful");
    }

    /**
     * Right clicks on an element.
     */
    public static void rightClick(WebElement element) {
        logger.info("Right clicking on element: {}", getElementDescription(element));
        waitForClickable(element, DEFAULT_TIMEOUT);
        org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(getDriver());
        actions.contextClick(element).perform();
        logger.debug("Right click successful");
    }

    // ==================== TYPE ACTIONS ====================

    /**
     * Types text into an element after clearing it.
     */
    public static void type(WebElement element, String text) {
        logger.info("Typing '{}' into element: {}", text, getElementDescription(element));
        waitForVisible(element, DEFAULT_TIMEOUT);
        element.clear();
        element.sendKeys(text);
        logger.debug("Type successful");
    }

    /**
     * Types text without clearing the field first.
     */
    public static void appendText(WebElement element, String text) {
        logger.info("Appending '{}' to element: {}", text, getElementDescription(element));
        waitForVisible(element, DEFAULT_TIMEOUT);
        element.sendKeys(text);
        logger.debug("Append text successful");
    }

    /**
     * Clears the text from an element.
     */
    public static void clearText(WebElement element) {
        logger.info("Clearing text from element: {}", getElementDescription(element));
        waitForVisible(element, DEFAULT_TIMEOUT);
        element.clear();
        logger.debug("Clear text successful");
    }

    /**
     * Presses Enter key on an element.
     */
    public static void pressEnter(WebElement element) {
        logger.info("Pressing ENTER on element: {}", getElementDescription(element));
        waitForVisible(element, DEFAULT_TIMEOUT);
        element.sendKeys(Keys.ENTER);
        logger.debug("Press ENTER successful");
    }

    /**
     * Presses Tab key on an element.
     */
    public static void pressTab(WebElement element) {
        logger.info("Pressing TAB on element: {}", getElementDescription(element));
        waitForVisible(element, DEFAULT_TIMEOUT);
        element.sendKeys(Keys.TAB);
        logger.debug("Press TAB successful");
    }

    // ==================== GET ACTIONS ====================

    /**
     * Gets the page title.
     */
    public static String getPageTitle() {
        String title = getDriver().getTitle();
        logger.info("Getting page title: '{}'", title);
        return title;
    }

    /**
     * Gets the current URL.
     */
    public static String getCurrentUrl() {
        String url = getDriver().getCurrentUrl();
        logger.info("Getting current URL: '{}'", url);
        return url;
    }

    /**
     * Gets text from an element.
     */
    public static String getText(WebElement element) {
        waitForVisible(element, DEFAULT_TIMEOUT);
        String text = element.getText();
        logger.info("Getting text from element: '{}' -> '{}'", getElementDescription(element), text);
        return text;
    }

    /**
     * Gets attribute value from an element.
     */
    public static String getAttribute(WebElement element, String attributeName) {
        waitForVisible(element, DEFAULT_TIMEOUT);
        String value = element.getAttribute(attributeName);
        logger.info("Getting attribute '{}' from element: '{}' -> '{}'", attributeName, getElementDescription(element), value);
        return value;
    }

    // ==================== NAVIGATION ACTIONS ====================

    /**
     * Navigates to a URL.
     */
    public static void navigateTo(String url) {
        logger.info("Navigating to URL: '{}'", url);
        getDriver().get(url);
        logger.debug("Navigation successful");
    }

    /**
     * Refreshes the current page.
     */
    public static void refreshPage() {
        logger.info("Refreshing page");
        getDriver().navigate().refresh();
        logger.debug("Page refresh successful");
    }

    /**
     * Navigates back in browser history.
     */
    public static void goBack() {
        logger.info("Navigating back");
        getDriver().navigate().back();
        logger.debug("Navigate back successful");
    }

    /**
     * Navigates forward in browser history.
     */
    public static void goForward() {
        logger.info("Navigating forward");
        getDriver().navigate().forward();
        logger.debug("Navigate forward successful");
    }

    // ==================== DROPDOWN ACTIONS ====================

    /**
     * Selects dropdown option by visible text.
     */
    public static void selectByText(WebElement element, String text) {
        logger.info("Selecting '{}' from dropdown: {}", text, getElementDescription(element));
        waitForVisible(element, DEFAULT_TIMEOUT);
        Select select = new Select(element);
        select.selectByVisibleText(text);
        logger.debug("Select by text successful");
    }

    /**
     * Selects dropdown option by value.
     */
    public static void selectByValue(WebElement element, String value) {
        logger.info("Selecting by value '{}' from dropdown: {}", value, getElementDescription(element));
        waitForVisible(element, DEFAULT_TIMEOUT);
        Select select = new Select(element);
        select.selectByValue(value);
        logger.debug("Select by value successful");
    }

    /**
     * Selects dropdown option by index.
     */
    public static void selectByIndex(WebElement element, int index) {
        logger.info("Selecting by index '{}' from dropdown: {}", index, getElementDescription(element));
        waitForVisible(element, DEFAULT_TIMEOUT);
        Select select = new Select(element);
        select.selectByIndex(index);
        logger.debug("Select by index successful");
    }

    // ==================== HOVER ACTIONS ====================

    /**
     * Hovers over an element.
     */
    public static void hover(WebElement element) {
        logger.info("Hovering over element: {}", getElementDescription(element));
        waitForVisible(element, DEFAULT_TIMEOUT);
        org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(getDriver());
        actions.moveToElement(element).perform();
        logger.debug("Hover successful");
    }

    // ==================== SCROLL ACTIONS ====================

    /**
     * Scrolls to an element.
     */
    public static void scrollToElement(WebElement element) {
        logger.info("Scrolling to element: {}", getElementDescription(element));
        org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(getDriver());
        actions.scrollToElement(element).perform();
        logger.debug("Scroll successful");
    }

    // ==================== WAIT HELPERS ====================

    /**
     * Waits for element to be visible.
     */
    public static void waitForVisible(WebElement element, int timeoutSeconds) {
        logger.debug("Waiting for element to be visible ({}s): {}", timeoutSeconds, getElementDescription(element));
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));
        logger.debug("Element is visible");
    }

    /**
     * Waits for element to be clickable.
     */
    public static void waitForClickable(WebElement element, int timeoutSeconds) {
        logger.debug("Waiting for element to be clickable ({}s): {}", timeoutSeconds, getElementDescription(element));
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        logger.debug("Element is clickable");
    }

    /**
     * Waits for element to disappear.
     */
    public static void waitForInvisible(WebElement element, int timeoutSeconds) {
        logger.debug("Waiting for element to disappear ({}s): {}", timeoutSeconds, getElementDescription(element));
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.invisibilityOf(element));
        logger.debug("Element is invisible");
    }

    /**
     * Waits for element to be visible with default timeout.
     */
    public static void waitForVisible(WebElement element) {
        waitForVisible(element, DEFAULT_TIMEOUT);
    }

    /**
     * Waits for element to be clickable with default timeout.
     */
    public static void waitForClickable(WebElement element) {
        waitForClickable(element, DEFAULT_TIMEOUT);
    }

    // ==================== HELPER METHODS ====================

    /**
     * Gets a description of the element for logging.
     */
    private static String getElementDescription(WebElement element) {
        try {
            String tagName = element.getTagName();
            String id = element.getAttribute("id");
            String name = element.getAttribute("name");
            String className = element.getAttribute("class");
            
            StringBuilder desc = new StringBuilder(tagName);
            if (id != null && !id.isEmpty()) {
                desc.append("#").append(id);
            } else if (name != null && !name.isEmpty()) {
                desc.append("[name=").append(name).append("]");
            } else if (className != null && !className.isEmpty()) {
                desc.append(".").append(className.split(" ")[0]);
            }
            return desc.toString();
        } catch (Exception e) {
            return "element";
        }
    }
}
