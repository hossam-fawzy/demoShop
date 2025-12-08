package listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import utils.ConfigReader;

/**
 * Retry analyzer for TestNG tests.
 * Retries failed tests based on configuration settings.
 * Follows best practices with proper logging and configuration-driven retry count.
 * 
 * @author QA Team
 * @version 2.0
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger logger = LogManager.getLogger(RetryAnalyzer.class);
    private int retryCount = 0;

    /**
     * Gets maximum retry count from configuration.
     * Reads from config.properties: maxRetryCount (default: 1)
     * 
     * @return Maximum number of retry attempts
     */
    private int getMaxRetryCount() {
        try {
            boolean retryEnabled = ConfigReader.getBoolean("retryFailedTests", true);
            if (!retryEnabled) {
                logger.debug("Retry is disabled in configuration");
                return 0;
            }
            int maxRetry = ConfigReader.getInt("maxRetryCount", 1);
            logger.debug("Max retry count from configuration: {}", maxRetry);
            return maxRetry;
        } catch (Exception e) {
            logger.warn("Failed to read retry configuration, using default: 1", e);
            return 1;
        }
    }

    /**
     * Determines if a test should be retried.
     * 
     * @param result The test result
     * @return true if test should be retried, false otherwise
     */
    @Override
    public boolean retry(ITestResult result) {
        int maxRetry = getMaxRetryCount();
        
        if (retryCount < maxRetry) {
            retryCount++;
            String testName = result.getMethod().getMethodName();
            String className = result.getTestClass().getName();
            logger.warn("Retrying test: {}.{} (Attempt {}/{})", 
                    className, testName, retryCount, maxRetry);
            return true;
        }
        
        logger.error("Test failed after {} retry attempts: {}.{}", 
                maxRetry, result.getTestClass().getName(), result.getMethod().getMethodName());
        return false;
    }
}
