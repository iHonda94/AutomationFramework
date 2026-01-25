package utils;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Centralized driver management.
 * Holds the WebDriver instance so it can be accessed from anywhere
 * without passing it as a parameter.
 */
public class DriverManager {

    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static WebDriver driver;

    /**
     * Sets the WebDriver instance.
     * Called by BaseTest during setup.
     */
    public static void setDriver(WebDriver webDriver) {
        driver = webDriver;
        logger.debug("Driver set in DriverManager");
    }

    /**
     * Gets the current WebDriver instance.
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            logger.warn("Driver is null - make sure BaseTest.setUp() was called");
        }
        return driver;
    }

    /**
     * Clears the driver reference.
     * Called by BaseTest during teardown.
     */
    public static void clearDriver() {
        driver = null;
        logger.debug("Driver cleared from DriverManager");
    }

    /**
     * Checks if driver is available.
     */
    public static boolean hasDriver() {
        return driver != null;
    }
}
