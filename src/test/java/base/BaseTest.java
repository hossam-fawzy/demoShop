package base;

import drivers.DriverFactory;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

import java.io.ByteArrayInputStream;

/**
 * Base test class providing common setup and teardown for all test classes.
 * Handles driver initialization, navigation, and screenshot capture on failure.
 */
public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setup() {
        logger.info("Starting test setup");
        
        // Initialize driver if not already initialized
        if (!DriverFactory.isDriverInitialized()) {
            DriverFactory.initDriver();
        }
        
        // Get the driver instance
        WebDriver driver = DriverFactory.getDriver();
        
        // Navigate to base URL
        String baseUrl = ConfigReader.get("baseUrl");
        if (baseUrl == null || baseUrl.isEmpty()) {
            logger.error("Base URL is not configured in config.properties");
            throw new RuntimeException("Base URL is not configured. Please set 'baseUrl' in config.properties");
        }
        
        logger.info("Navigating to: {}", baseUrl);
        try {
            driver.get(baseUrl);
            logger.info("Successfully navigated to: {}", baseUrl);
            
            // Wait for page to load
            utils.WaitUtils.waitForPageToLoad(driver);
        } catch (Exception e) {
            logger.error("Failed to navigate to {}: {}", baseUrl, e.getMessage(), e);
            throw new RuntimeException("Failed to navigate to base URL: " + baseUrl, e);
        }
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        String testName = result.getName();

        if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("Test '{}' FAILED", testName);
            takeScreenshot(testName);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.info("Test '{}' PASSED", testName);
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.warn("Test '{}' SKIPPED", testName);
        }

        logger.info("Tearing down test");
        DriverFactory.quitDriver();
    }

    /**
     * Captures screenshot and attaches it to Allure report
     *
     * @param testName name of the failed test
     */
    protected void takeScreenshot(String testName) {
        try {
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                    .getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment(
                    testName + " - Failure",
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    "png"
            );

            logger.info("Screenshot captured for test: {}", testName);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot for test '{}': {}", testName, e.getMessage());
        }
    }
}