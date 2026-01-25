package utils;

/**
 * Constants class containing all test data, URLs, timeouts, and messages.
 * Centralizes hardcoded values for easy maintenance.
 */
public final class Constants {

    // Private constructor to prevent instantiation
    private Constants() {
        throw new UnsupportedOperationException("Constants class cannot be instantiated");
    }

    // ==================== URLS ====================
    
    public static final String GOOGLE_URL = "https://www.google.com";
    public static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723/";

    // ==================== TEST DATA - CREDENTIALS ====================
    
    public static final String VALID_USERNAME = "standard_user";
    public static final String VALID_PASSWORD = "secret_sauce";
    public static final String INVALID_USERNAME = "Ahmed";
    public static final String INVALID_PASSWORD = "Mahmoud";

    // ==================== TEST DATA - SEARCH ====================
    
    public static final String SEARCH_TERM_SELENIUM = "Selenium WebDriver";
    public static final String SEARCH_TERM_APPIUM = "Appium Mobile Testing";

    // ==================== TIMEOUTS (in seconds) ====================
    
    public static final int DEFAULT_TIMEOUT = 10;
    public static final int SHORT_TIMEOUT = 5;
    public static final int LONG_TIMEOUT = 30;
    public static final int PAGE_LOAD_TIMEOUT = 60;

    // ==================== VALIDATION MESSAGES ====================
    
    public static final String MSG_SEARCH_BOX_VISIBLE = "Google search box should be visible";
    public static final String MSG_LOGIN_BUTTON_VISIBLE = "Login button should be visible after entering wrong credentials";
    public static final String MSG_LOGIN_BUTTON_NOT_VISIBLE = "Login button should NOT be visible after successful login";
    public static final String MSG_PAGE_TITLE_CONTAINS = "Page title should contain expected text";

    // ==================== PAGE TITLES ====================
    
    public static final String GOOGLE_PAGE_TITLE = "Google";
    public static final String GOOGLE_SEARCH_RESULTS_TITLE = "Google Search";

    // ==================== MOBILE - DEVICE NAMES ====================
    
    public static final String ANDROID_DEVICE_NAME = "emulator-5544";
    public static final String IOS_DEVICE_NAME = "iPhone 16 Pro";
    public static final String IOS_PLATFORM_VERSION = "18.2";

    // ==================== MOBILE - APP PATHS ====================
    
    public static final String ANDROID_APP_PATH = "/src/main/resources/AndroidDemo.apk";
    public static final String IOS_APP_PATH = "/src/main/resources/iOSDemo.app";

    // ==================== BROWSER NAMES ====================
    
    public static final String BROWSER_CHROME = "chrome";
    public static final String BROWSER_FIREFOX = "firefox";
    public static final String BROWSER_EDGE = "edge";

    // ==================== PLATFORM NAMES ====================
    
    public static final String PLATFORM_ANDROID = "Android";
    public static final String PLATFORM_IOS = "iOS";
}
