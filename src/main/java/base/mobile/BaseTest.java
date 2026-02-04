package base.mobile;

import utils.Config;
import utils.DriverManager;
import utils.ReportUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {
    public static AppiumDriver driver;
    private static String platformName = Config.get("platformName", "ZZZ");
    private static String appPackage;

    @BeforeSuite
    public static void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", platformName);

        if (platformName.equalsIgnoreCase("Android")) {
            String appPath = System.getProperty("user.dir") + "/src/main/resources/AndroidDemo.apk";
            appPackage = "com.saucelabs.mydemoapp.rn";
            caps.setCapability("deviceName", "emulator-5554");
            caps.setCapability("automationName", "UIAutomator2");
            caps.setCapability("app", appPath);
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), caps);
        } else if (platformName.equalsIgnoreCase("iOS")) {
            String appPath = System.getProperty("user.dir") + "/src/main/resources/iOSDemo.app";
            appPackage = "com.saucelabs.mydemoapp.rn";
            caps.setCapability("deviceName", "iPhone 16 Pro");
            caps.setCapability("automationName", "XCUITest");
            caps.setCapability("platformVersion", "18.2");
            caps.setCapability("app", appPath);
            driver = new IOSDriver(new URL("http://127.0.0.1:4723/"), caps);
        }

        // Set driver in DriverManager for Actions/Validations
        DriverManager.setDriver(driver);
    }

    @BeforeMethod
    public void resetApp() {
        // Reset app to initial state before each test
        if (driver != null && appPackage != null) {
            try {
                if (driver instanceof AndroidDriver) {
                    AndroidDriver androidDriver = (AndroidDriver) driver;
                    // Clear app data and reopen
                    androidDriver.executeScript("mobile: clearApp", java.util.Map.of("appId", appPackage));
                    androidDriver.activateApp(appPackage);
                } else if (driver instanceof IOSDriver) {
                    IOSDriver iosDriver = (IOSDriver) driver;
                    iosDriver.terminateApp(appPackage);
                    iosDriver.activateApp(appPackage);
                }
            } catch (Exception e) {
                // Fallback: just restart the app without clearing
                if (driver instanceof AndroidDriver) {
                    ((AndroidDriver) driver).terminateApp(appPackage);
                    ((AndroidDriver) driver).activateApp(appPackage);
                } else if (driver instanceof IOSDriver) {
                    ((IOSDriver) driver).terminateApp(appPackage);
                    ((IOSDriver) driver).activateApp(appPackage);
                }
            }
        }
    }

    @AfterMethod
    public void captureOnFailure(ITestResult result) {
        // Capture screenshot on failure
        if (result.getStatus() == ITestResult.FAILURE && driver != null) {
            ReportUtils.captureScreenshot(driver, "Failure Screenshot");
        }
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            DriverManager.clearDriver();
        }
    }
}
