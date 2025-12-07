package base;

import drivers.DriverFactory;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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
        DriverFactory.initDriver();

        String baseUrl = ConfigReader.get("baseUrl");
        logger.info("Navigating to: {}", baseUrl);
        DriverFactory.getDriver().get(baseUrl);
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