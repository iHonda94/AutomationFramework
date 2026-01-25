package base.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.Config;
import utils.DriverManager;
import utils.ReportUtils;

public class BaseTest {
    public static WebDriver driver;
    private static String browser = Config.get("browser", "chrome");

    @BeforeMethod
    public void setUp() {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

        // Set driver in DriverManager for Actions/Validations
        DriverManager.setDriver(driver);
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Capture screenshot on failure
        if (result.getStatus() == ITestResult.FAILURE && driver != null) {
            ReportUtils.captureScreenshot(driver, "Failure Screenshot");
            ReportUtils.attachText("Failed URL", driver.getCurrentUrl());
        }
        
        if (driver != null) {
            driver.quit();
            DriverManager.clearDriver();
        }
    }
}
