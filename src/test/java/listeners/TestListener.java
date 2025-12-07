package listeners;

import drivers.DriverFactory;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.*;

import java.io.ByteArrayInputStream;

public class TestListener implements ITestListener, ISuiteListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class);

    // ===============================
    // SUITE LEVEL LOGGING
    // ===============================
    @Override
    public void onStart(ISuite suite) {
        logger.info("===== TEST SUITE STARTED: {} =====", suite.getName());
    }

    @Override
    public void onFinish(ISuite suite) {
        logger.info("===== TEST SUITE FINISHED: {} =====", suite.getName());
    }

    // ===============================
    // TEST LEVEL LOGGING
    // ===============================

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("▶ STARTING TEST: {}", result.getMethod().getMethodName());
        Allure.step("🚀 Test Started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("✅ TEST PASSED: {}", result.getMethod().getMethodName());
        Allure.step("🟢 Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("❌ TEST FAILED: {}", result.getMethod().getMethodName());
        logger.error("❗ Reason: ", result.getThrowable());

        // ===== ATTACH FAILURE LOG TO ALLURE =====
        Allure.addAttachment(
                "Failure Reason",
                "text/plain",
                result.getThrowable().toString()
        );

        // ===== SCREENSHOT =====
        try {
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot on Failure",
                    new ByteArrayInputStream(screenshot));
        } catch (Exception e) {
            logger.error("Screenshot capture failed", e);
        }

        Allure.step("🔴 Test Failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("⚠ TEST SKIPPED: {}", result.getMethod().getMethodName());
        Allure.step("🟡 Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) { }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("🏁 FINISHED TEST SET: {}", context.getName());
    }
}
