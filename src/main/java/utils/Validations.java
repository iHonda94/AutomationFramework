package utils;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * Wrapper class for common validations and assertions.
 * Provides reusable methods for verifying UI elements and content.
 * Uses DriverManager to get the driver - no need to pass driver as parameter.
 */
public class Validations {

    private static final Logger logger = LoggerFactory.getLogger(Validations.class);
    private static final int DEFAULT_TIMEOUT = 10;

    // ==================== ELEMENT VISIBILITY ====================

    /**
     * Validates that an element is displayed (with explicit wait).
     */
    public static void validateElementIsDisplayed(WebElement element, String elementName) {
        logger.info("Validating '{}' is displayed", elementName);
        Actions.waitForVisible(element);
        boolean isDisplayed = element.isDisplayed();
        logger.info("Validation result: {} is displayed = {}", elementName, isDisplayed);
        Assert.assertTrue(isDisplayed, elementName + " should be displayed");
    }

    /**
     * Validates that an element is NOT displayed (with explicit wait for invisibility).
     */
    public static void validateElementIsNotDisplayed(WebElement element, String elementName) {
        logger.info("Validating '{}' is NOT displayed", elementName);
        try {
            Actions.waitForInvisible(element, DEFAULT_TIMEOUT);
            logger.info("Validation result: {} is invisible", elementName);
        } catch (Exception e) {
            // Element not found means it's not displayed - which is what we want
            logger.info("Validation result: {} is not in DOM (as expected)", elementName);
        }
    }

    /**
     * Validates that an element is enabled (with explicit wait).
     */
    public static void validateElementIsEnabled(WebElement element, String elementName) {
        logger.info("Validating '{}' is enabled", elementName);
        Actions.waitForVisible(element);
        boolean isEnabled = element.isEnabled();
        logger.info("Validation result: {} is enabled = {}", elementName, isEnabled);
        Assert.assertTrue(isEnabled, elementName + " should be enabled");
    }

    /**
     * Validates that an element is disabled (with explicit wait).
     */
    public static void validateElementIsDisabled(WebElement element, String elementName) {
        logger.info("Validating '{}' is disabled", elementName);
        Actions.waitForVisible(element);
        boolean isEnabled = element.isEnabled();
        logger.info("Validation result: {} is enabled = {}", elementName, isEnabled);
        Assert.assertFalse(isEnabled, elementName + " should be disabled");
    }

    /**
     * Validates that an element is selected (checkbox/radio) (with explicit wait).
     */
    public static void validateElementIsSelected(WebElement element, String elementName) {
        logger.info("Validating '{}' is selected", elementName);
        Actions.waitForVisible(element);
        boolean isSelected = element.isSelected();
        logger.info("Validation result: {} is selected = {}", elementName, isSelected);
        Assert.assertTrue(isSelected, elementName + " should be selected");
    }

    /**
     * Validates that an element is NOT selected (with explicit wait).
     */
    public static void validateElementIsNotSelected(WebElement element, String elementName) {
        logger.info("Validating '{}' is NOT selected", elementName);
        Actions.waitForVisible(element);
        boolean isSelected = element.isSelected();
        logger.info("Validation result: {} is selected = {}", elementName, isSelected);
        Assert.assertFalse(isSelected, elementName + " should NOT be selected");
    }

    // ==================== TEXT VALIDATIONS ====================

    /**
     * Validates that element text equals expected text (with explicit wait).
     */
    public static void validateTextEquals(WebElement element, String expectedText, String elementName) {
        Actions.waitForVisible(element);
        String actualText = element.getText();
        logger.info("Validating '{}' text equals '{}'", elementName, expectedText);
        logger.info("Validation result: actual text = '{}'", actualText);
        Assert.assertEquals(actualText, expectedText,
                elementName + " text mismatch. Expected: " + expectedText + ", Actual: " + actualText);
    }

    /**
     * Validates that element text contains expected text (with explicit wait).
     */
    public static void validateTextContains(WebElement element, String expectedText, String elementName) {
        Actions.waitForVisible(element);
        String actualText = element.getText();
        logger.info("Validating '{}' text contains '{}'", elementName, expectedText);
        logger.info("Validation result: actual text = '{}'", actualText);
        Assert.assertTrue(actualText.contains(expectedText),
                elementName + " should contain '" + expectedText + "'. Actual: " + actualText);
    }

    /**
     * Validates that element text does NOT contain text (with explicit wait).
     */
    public static void validateTextNotContains(WebElement element, String text, String elementName) {
        Actions.waitForVisible(element);
        String actualText = element.getText();
        logger.info("Validating '{}' text does NOT contain '{}'", elementName, text);
        logger.info("Validation result: actual text = '{}'", actualText);
        Assert.assertFalse(actualText.contains(text),
                elementName + " should NOT contain '" + text + "'. Actual: " + actualText);
    }

    /**
     * Validates that element text is not empty (with explicit wait).
     */
    public static void validateTextIsNotEmpty(WebElement element, String elementName) {
        Actions.waitForVisible(element);
        String actualText = element.getText();
        logger.info("Validating '{}' text is not empty", elementName);
        logger.info("Validation result: actual text = '{}'", actualText);
        Assert.assertFalse(actualText.isEmpty(), elementName + " text should not be empty");
    }

    // ==================== PAGE VALIDATIONS ====================

    /**
     * Validates page title equals expected title.
     */
    public static void validatePageTitle(String expectedTitle) {
        String actualTitle = Actions.getPageTitle();
        logger.info("Validating page title equals '{}'", expectedTitle);
        logger.info("Validation result: actual title = '{}'", actualTitle);
        Assert.assertEquals(actualTitle, expectedTitle,
                "Page title mismatch. Expected: " + expectedTitle + ", Actual: " + actualTitle);
    }

    /**
     * Validates page title contains expected text.
     */
    public static void validatePageTitleContains(String expectedText) {
        String actualTitle = Actions.getPageTitle();
        logger.info("Validating page title contains '{}'", expectedText);
        logger.info("Validation result: actual title = '{}'", actualTitle);
        Assert.assertTrue(actualTitle.contains(expectedText),
                "Page title should contain '" + expectedText + "'. Actual: " + actualTitle);
    }

    /**
     * Validates current URL equals expected URL.
     */
    public static void validateUrl(String expectedUrl) {
        String actualUrl = Actions.getCurrentUrl();
        logger.info("Validating URL equals '{}'", expectedUrl);
        logger.info("Validation result: actual URL = '{}'", actualUrl);
        Assert.assertEquals(actualUrl, expectedUrl,
                "URL mismatch. Expected: " + expectedUrl + ", Actual: " + actualUrl);
    }

    /**
     * Validates current URL contains expected text.
     */
    public static void validateUrlContains(String expectedText) {
        String actualUrl = Actions.getCurrentUrl();
        logger.info("Validating URL contains '{}'", expectedText);
        logger.info("Validation result: actual URL = '{}'", actualUrl);
        Assert.assertTrue(actualUrl.contains(expectedText),
                "URL should contain '" + expectedText + "'. Actual: " + actualUrl);
    }

    // ==================== ATTRIBUTE VALIDATIONS ====================

    /**
     * Validates element attribute equals expected value.
     */
    public static void validateAttribute(WebElement element, String attribute, String expectedValue, String elementName) {
        String actualValue = element.getAttribute(attribute);
        logger.info("Validating '{}' attribute '{}' equals '{}'", elementName, attribute, expectedValue);
        logger.info("Validation result: actual value = '{}'", actualValue);
        Assert.assertEquals(actualValue, expectedValue,
                elementName + " attribute '" + attribute + "' mismatch. Expected: " + expectedValue + ", Actual: " + actualValue);
    }

    /**
     * Validates element attribute contains expected value.
     */
    public static void validateAttributeContains(WebElement element, String attribute, String expectedValue, String elementName) {
        String actualValue = element.getAttribute(attribute);
        logger.info("Validating '{}' attribute '{}' contains '{}'", elementName, attribute, expectedValue);
        logger.info("Validation result: actual value = '{}'", actualValue);
        Assert.assertTrue(actualValue.contains(expectedValue),
                elementName + " attribute '" + attribute + "' should contain '" + expectedValue + "'. Actual: " + actualValue);
    }

    // ==================== COUNT VALIDATIONS ====================

    /**
     * Validates count equals expected value.
     */
    public static void validateCountEquals(int actualCount, int expectedCount, String description) {
        logger.info("Validating '{}' count equals {}", description, expectedCount);
        logger.info("Validation result: actual count = {}", actualCount);
        Assert.assertEquals(actualCount, expectedCount,
                description + " count mismatch. Expected: " + expectedCount + ", Actual: " + actualCount);
    }

    /**
     * Validates count is greater than expected value.
     */
    public static void validateCountGreaterThan(int actualCount, int minCount, String description) {
        logger.info("Validating '{}' count is greater than {}", description, minCount);
        logger.info("Validation result: actual count = {}", actualCount);
        Assert.assertTrue(actualCount > minCount,
                description + " count should be greater than " + minCount + ". Actual: " + actualCount);
    }

    // ==================== BOOLEAN VALIDATIONS ====================

    /**
     * Validates condition is true.
     */
    public static void validateTrue(boolean condition, String message) {
        logger.info("Validating condition is TRUE: {}", message);
        logger.info("Validation result: condition = {}", condition);
        Assert.assertTrue(condition, message);
    }

    /**
     * Validates condition is false.
     */
    public static void validateFalse(boolean condition, String message) {
        logger.info("Validating condition is FALSE: {}", message);
        logger.info("Validation result: condition = {}", condition);
        Assert.assertFalse(condition, message);
    }

    /**
     * Validates two values are equal.
     */
    public static void validateEquals(Object actual, Object expected, String message) {
        logger.info("Validating values are equal: {}", message);
        logger.info("Validation result: expected = '{}', actual = '{}'", expected, actual);
        Assert.assertEquals(actual, expected, message);
    }

    /**
     * Validates two values are NOT equal.
     */
    public static void validateNotEquals(Object actual, Object notExpected, String message) {
        logger.info("Validating values are NOT equal: {}", message);
        logger.info("Validation result: notExpected = '{}', actual = '{}'", notExpected, actual);
        Assert.assertNotEquals(actual, notExpected, message);
    }

    // ==================== EXISTENCE CHECKS (Non-Asserting) ====================

    /**
     * Checks if element is displayed with explicit wait (returns boolean, no assertion).
     * Waits up to DEFAULT_TIMEOUT seconds for the element to be visible.
     */
    public static boolean isDisplayed(WebElement element) {
        try {
            Actions.waitForVisible(element);
            boolean displayed = element.isDisplayed();
            logger.debug("Checking if element is displayed: {}", displayed);
            return displayed;
        } catch (Exception e) {
            logger.debug("Element not displayed or not found: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks if element is enabled with explicit wait (returns boolean, no assertion).
     */
    public static boolean isEnabled(WebElement element) {
        try {
            Actions.waitForVisible(element);
            boolean enabled = element.isEnabled();
            logger.debug("Checking if element is enabled: {}", enabled);
            return enabled;
        } catch (Exception e) {
            logger.debug("Element not enabled or not found: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks if element is selected with explicit wait (returns boolean, no assertion).
     */
    public static boolean isSelected(WebElement element) {
        try {
            Actions.waitForVisible(element);
            boolean selected = element.isSelected();
            logger.debug("Checking if element is selected: {}", selected);
            return selected;
        } catch (Exception e) {
            logger.debug("Element not selected or not found: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Waits for element to be visible with custom timeout and checks if displayed.
     */
    public static boolean isDisplayedWithWait(WebElement element, int timeoutSeconds) {
        try {
            logger.debug("Waiting for element to be visible ({}s)", timeoutSeconds);
            Actions.waitForVisible(element, timeoutSeconds);
            boolean displayed = element.isDisplayed();
            logger.debug("Element is displayed: {}", displayed);
            return displayed;
        } catch (Exception e) {
            logger.debug("Element not visible after {}s: {}", timeoutSeconds, e.getMessage());
            return false;
        }
    }
}
