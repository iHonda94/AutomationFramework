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
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {
    public static AppiumDriver driver;
    private static String platformName = Config.get("platformName", "ZZZ");

    @BeforeSuite
    public static void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", platformName);

        if (platformName.equalsIgnoreCase("Android")) {
            String appPath = System.getProperty("user.dir") + "/src/main/resources/AndroidDemo.apk";
            caps.setCapability("deviceName", "emulator-5544");
            caps.setCapability("automationName", "UIAutomator2");
            caps.setCapability("app", appPath);
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), caps);
        } else if (platformName.equalsIgnoreCase("iOS")) {
            String appPath = System.getProperty("user.dir") + "/src/main/resources/iOSDemo.app";
            caps.setCapability("deviceName", "iPhone 16 Pro");
            caps.setCapability("automationName", "XCUITest");
            caps.setCapability("platformVersion", "18.2");
            caps.setCapability("app", appPath);
            driver = new IOSDriver(new URL("http://127.0.0.1:4723/"), caps);
        }

        // Set driver in DriverManager for Actions/Validations
        DriverManager.setDriver(driver);
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
