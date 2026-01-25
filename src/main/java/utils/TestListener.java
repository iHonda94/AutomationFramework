package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import java.io.File;

/**
 * TestNG Listener for Allure reporting.
 * Automatically captures screenshots on failure and logs test status.
 */
public class TestListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);
    private static final String SUITE_NAME = "Automation Test Suite";
    private static boolean resultsCleared = false;

    @Override
    public void onStart(ITestContext context) {
        // Clean old allure results (only once per test run)
        if (!resultsCleared) {
            cleanAllureResults();
            resultsCleared = true;
        }
        
        // Set proper suite name instead of random TestNG ID
        XmlSuite suite = context.getSuite().getXmlSuite();
        if (suite.getName().startsWith("TestNGTest-") || suite.getName().equals("Default Suite")) {
            suite.setName(SUITE_NAME);
        }
        context.getCurrentXmlTest().setName(context.getName());
        
        logger.info("========== Test Suite Started: {} ==========", SUITE_NAME);
    }
    
    /**
     * Cleans old allure results before new test run.
     */
    private void cleanAllureResults() {
        File allureResults = new File("target/allure-results");
        if (allureResults.exists()) {
            File[] files = allureResults.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
                logger.info("Cleaned old Allure results");
            }
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("========== Test Suite Finished: {} ==========", context.getName());
        logger.info("Passed: {}, Failed: {}, Skipped: {}",
                context.getPassedTests().size(),
                context.getFailedTests().size(),
                context.getSkippedTests().size());
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("---------- Test Started: {} ----------", result.getName());
        Allure.getLifecycle().updateTestCase(testResult ->
                testResult.setName(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("---------- Test PASSED: {} ----------", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("---------- Test FAILED: {} ----------", result.getName());
        logger.error("Failure reason: {}", result.getThrowable().getMessage());

        // Capture screenshot on failure
        WebDriver driver = getDriverFromTestClass(result);
        if (driver != null) {
            ReportUtils.captureScreenshotOnFailure(driver);
            ReportUtils.attachCurrentUrl(driver);
        }

        // Attach exception details
        ReportUtils.attachText("Exception", result.getThrowable().toString());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("---------- Test SKIPPED: {} ----------", result.getName());
        if (result.getThrowable() != null) {
            logger.warn("Skip reason: {}", result.getThrowable().getMessage());
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.warn("---------- Test Failed but within success percentage: {} ----------", result.getName());
    }

    /**
     * Gets the WebDriver from the test class.
     */
    private WebDriver getDriverFromTestClass(ITestResult result) {
        try {
            Object testClass = result.getInstance();
            
            // Try to get driver from web BaseTest
            if (testClass instanceof base.web.BaseTest) {
                return base.web.BaseTest.driver;
            }
            
            // Try to get driver from mobile BaseTest
            if (testClass instanceof base.mobile.BaseTest) {
                return base.mobile.BaseTest.driver;
            }
        } catch (Exception e) {
            logger.error("Failed to get driver from test class: {}", e.getMessage());
        }
        return null;
    }
}
