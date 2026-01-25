package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

/**
 * Utility class for Allure reporting.
 * Provides methods for capturing screenshots and adding attachments.
 */
public class ReportUtils {

    private static final Logger logger = LoggerFactory.getLogger(ReportUtils.class);

    /**
     * Captures a screenshot and attaches it to the Allure report.
     * Uses direct Allure API for reliability in listeners.
     */
    public static void captureScreenshot(WebDriver driver, String name) {
        logger.info("Capturing screenshot: {}", name);
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), ".png");
            logger.info("Screenshot captured successfully: {}", name);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
        }
    }

    /**
     * Captures a screenshot on test failure.
     */
    public static void captureScreenshotOnFailure(WebDriver driver) {
        captureScreenshot(driver, "Failure Screenshot");
    }

    /**
     * Attaches text content to the Allure report.
     */
    public static void attachText(String name, String content) {
        logger.info("Attaching text: {}", name);
        try {
            Allure.addAttachment(name, "text/plain", content);
        } catch (Exception e) {
            logger.error("Failed to attach text: {}", e.getMessage());
        }
    }

    /**
     * Attaches HTML content to the Allure report.
     */
    public static void attachHtml(String name, String htmlContent) {
        logger.info("Attaching HTML: {}", name);
        try {
            Allure.addAttachment(name, "text/html", htmlContent);
        } catch (Exception e) {
            logger.error("Failed to attach HTML: {}", e.getMessage());
        }
    }

    /**
     * Adds a step to the Allure report.
     * Use this for simple logging steps.
     */
    public static void addStep(String stepDescription) {
        Allure.step(stepDescription);
        logger.info("Step: {}", stepDescription);
    }

    /**
     * Executes an action inside an Allure step.
     * If the action fails, the step will be marked as failed.
     */
    public static void step(String stepDescription, Runnable action) {
        logger.info("Step: {}", stepDescription);
        Allure.step(stepDescription, () -> {
            action.run();
        });
    }

    /**
     * Attaches page source to the report.
     */
    public static void attachPageSource(WebDriver driver) {
        logger.info("Attaching page source");
        try {
            String pageSource = driver.getPageSource();
            Allure.addAttachment("Page Source", "text/html", pageSource);
        } catch (Exception e) {
            logger.error("Failed to get page source: {}", e.getMessage());
        }
    }

    /**
     * Attaches current URL to the report.
     */
    public static void attachCurrentUrl(WebDriver driver) {
        try {
            String url = driver.getCurrentUrl();
            attachText("Current URL", url);
        } catch (Exception e) {
            logger.error("Failed to get current URL: {}", e.getMessage());
        }
    }
}
