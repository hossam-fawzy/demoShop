package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Utility class for Selenium WebDriver wait operations.
 * Provides explicit waits with configurable timeouts from configuration.
 * Follows best practices with proper error handling and logging.
 * 
 * @author QA Team
 * @version 2.0
 */
public class WaitUtils {

    private static final Logger logger = LogManager.getLogger(WaitUtils.class);
    
    /**
     * Gets default timeout from configuration or uses fallback value.
     * Reads from config.properties: explicitWait (default: 30 seconds)
     */
    private static long getDefaultTimeout() {
        try {
            return ConfigReader.getInt("explicitWait", 8);
        } catch (Exception e) {
            logger.warn("Failed to read explicitWait from config, using default: 8 seconds", e);
            return 8;
        }
    }

    /**
     * Private constructor to prevent instantiation.
     * This is a utility class with static methods only.
     */
    private WaitUtils() {
        throw new IllegalStateException("Utility class - cannot be instantiated");
    }

    // ---------------------------------------------------------
    // BASIC WAITS (Default Timeout from Configuration)
    // ---------------------------------------------------------

    /**
     * Waits for element to be visible on the page.
     * Uses default timeout from configuration.
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @return Visible WebElement
     * @throws TimeoutException if element is not visible within timeout
     */
    public static WebElement waitForVisibility(WebDriver driver, By locator) {
        long timeout = getDefaultTimeout();
        logger.debug("Waiting for element visibility: {} (timeout: {}s)", locator, timeout);
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element not visible within {} seconds: {}", timeout, locator);
            throw e;
        }
    }

    /**
     * Waits for element to be clickable.
     * Uses default timeout from configuration.
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @return Clickable WebElement
     * @throws TimeoutException if element is not clickable within timeout
     */
    public static WebElement waitForClickable(WebDriver driver, By locator) {
        long timeout = getDefaultTimeout();
        logger.debug("Waiting for element to be clickable: {} (timeout: {}s)", locator, timeout);
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            logger.error("Element not clickable within {} seconds: {}", timeout, locator);
            throw e;
        }
    }

    /**
     * Waits for element to become invisible.
     * Uses default timeout from configuration.
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @return true if element becomes invisible
     * @throws TimeoutException if element remains visible within timeout
     */
    public static boolean waitForInvisibility(WebDriver driver, By locator) {
        long timeout = getDefaultTimeout();
        logger.debug("Waiting for element invisibility: {} (timeout: {}s)", locator, timeout);
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element still visible after {} seconds: {}", timeout, locator);
            throw e;
        }
    }

    /**
     * Waits for element to be present in DOM (may not be visible).
     * Uses default timeout from configuration.
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @return Present WebElement
     * @throws TimeoutException if element is not present within timeout
     */
    public static WebElement waitForPresence(WebDriver driver, By locator) {
        long timeout = getDefaultTimeout();
        logger.debug("Waiting for element presence: {} (timeout: {}s)", locator, timeout);
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element not present within {} seconds: {}", timeout, locator);
            throw e;
        }
    }

    /**
     * Waits for page title to contain specified text.
     * Uses default timeout from configuration.
     * 
     * @param driver WebDriver instance
     * @param title Text that should be in page title
     * @throws TimeoutException if title doesn't contain text within timeout
     */
    public static void waitForPageTitle(WebDriver driver, String title) {
        long timeout = getDefaultTimeout();
        logger.debug("Waiting for page title to contain: '{}' (timeout: {}s)", title, timeout);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.titleContains(title));
        } catch (TimeoutException e) {
            logger.error("Page title doesn't contain '{}' within {} seconds", title, timeout);
            throw e;
        }
    }

    // ---------------------------------------------------------
    // CUSTOM TIMEOUT WAITS (Overloaded Methods)
    // ---------------------------------------------------------

    /**
     * Waits for element to be visible with custom timeout.
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @param timeout Timeout in seconds
     * @return Visible WebElement
     * @throws TimeoutException if element is not visible within timeout
     */
    public static WebElement waitForVisibility(WebDriver driver, By locator, long timeout) {
        logger.debug("Waiting for element visibility: {} (timeout: {}s)", locator, timeout);
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element not visible within {} seconds: {}", timeout, locator);
            throw e;
        }
    }

    /**
     * Waits for element to be clickable with custom timeout.
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @param timeout Timeout in seconds
     * @return Clickable WebElement
     * @throws TimeoutException if element is not clickable within timeout
     */
    public static WebElement waitForClickable(WebDriver driver, By locator, long timeout) {
        logger.debug("Waiting for element to be clickable: {} (timeout: {}s)", locator, timeout);
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            logger.error("Element not clickable within {} seconds: {}", timeout, locator);
            throw e;
        }
    }

    /**
     * Waits for element to become invisible with custom timeout.
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @param timeout Timeout in seconds
     * @return true if element becomes invisible
     * @throws TimeoutException if element remains visible within timeout
     */
    public static boolean waitForInvisibility(WebDriver driver, By locator, long timeout) {
        logger.debug("Waiting for element invisibility: {} (timeout: {}s)", locator, timeout);
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element still visible after {} seconds: {}", timeout, locator);
            throw e;
        }
    }

    /**
     * Waits for element to be present in DOM with custom timeout.
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @param timeout Timeout in seconds
     * @return Present WebElement
     * @throws TimeoutException if element is not present within timeout
     */
    public static WebElement waitForPresence(WebDriver driver, By locator, long timeout) {
        logger.debug("Waiting for element presence: {} (timeout: {}s)", locator, timeout);
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element not present within {} seconds: {}", timeout, locator);
            throw e;
        }
    }

    // ---------------------------------------------------------
    // TEXT & ATTRIBUTE WAITS
    // ---------------------------------------------------------

    public static boolean waitForText(WebDriver driver, By locator, String expectedText) {
        long timeout = getDefaultTimeout();
        logger.debug("Waiting for text '{}' in element: {} (timeout: {}s)", expectedText, locator, timeout);
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText));
        } catch (TimeoutException e) {
            logger.error("Text '{}' not found in element {} within {} seconds", expectedText, locator, timeout);
            throw e;
        }
    }

    public static boolean waitForAttribute(WebDriver driver, By locator, String attribute, String value) {
        long timeout = getDefaultTimeout();
        logger.debug("Waiting for attribute '{}'='{}' in element: {} (timeout: {}s)", attribute, value, locator, timeout);
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.attributeContains(locator, attribute, value));
        } catch (TimeoutException e) {
            logger.error("Attribute '{}'='{}' not found in element {} within {} seconds", attribute, value, locator, timeout);
            throw e;
        }
    }

    // ---------------------------------------------------------
    // URL WAITS
    // ---------------------------------------------------------

    public static boolean waitForUrlContains(WebDriver driver, String partialUrl) {
        long timeout = getDefaultTimeout();
        logger.debug("Waiting for URL to contain: '{}' (timeout: {}s)", partialUrl, timeout);
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.urlContains(partialUrl));
        } catch (TimeoutException e) {
            logger.error("URL doesn't contain '{}' within {} seconds. Current URL: {}", 
                    partialUrl, timeout, driver.getCurrentUrl());
            throw e;
        }
    }

    public static boolean waitForUrlToBe(WebDriver driver, String url) {
        long timeout = getDefaultTimeout();
        logger.debug("Waiting for URL to be: '{}' (timeout: {}s)", url, timeout);
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.urlToBe(url));
        } catch (TimeoutException e) {
            logger.error("URL is not '{}' within {} seconds. Current URL: {}", 
                    url, timeout, driver.getCurrentUrl());
            throw e;
        }
    }

    // ---------------------------------------------------------
    // PAGE LOAD COMPLETE (JS readyState)
    // ---------------------------------------------------------

    /**
     * Waits for page to load completely by checking document.readyState.
     * Uses default timeout from configuration.
     * 
     * @param driver WebDriver instance
     * @throws TimeoutException if page doesn't load within timeout
     */
    public static void waitForPageToLoad(WebDriver driver) {
        long timeout = getDefaultTimeout();
        logger.debug("Waiting for page to load completely (timeout: {}s)", timeout);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until((ExpectedCondition<Boolean>) wd -> {
                        String readyState = ((JavascriptExecutor) wd)
                                .executeScript("return document.readyState").toString();
                        return "complete".equals(readyState);
                    });
            logger.debug("Page loaded successfully");
        } catch (TimeoutException e) {
            logger.error("Page did not load completely within {} seconds", timeout);
            throw e;
        }
    }

    // ---------------------------------------------------------
    // FLUENT WAIT (Advanced)
    // ---------------------------------------------------------

    /**
     * Advanced fluent wait with custom timeout and polling interval.
     * Ignores NoSuchElementException and StaleElementReferenceException.
     * 
     * @param driver WebDriver instance
     * @param locator Element locator
     * @param timeoutSeconds Total timeout in seconds
     * @param pollingMillis Polling interval in milliseconds
     * @return Visible WebElement
     * @throws TimeoutException if element is not found within timeout
     */
    public static WebElement fluentWait(WebDriver driver, By locator, long timeoutSeconds, long pollingMillis) {
        logger.debug("Fluent wait for element: {} (timeout: {}s, polling: {}ms)", locator, timeoutSeconds, pollingMillis);
        try {
            return new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(timeoutSeconds))
                    .pollingEvery(Duration.ofMillis(pollingMillis))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element not found with fluent wait: {} (timeout: {}s)", locator, timeoutSeconds);
            throw e;
        }
    }
}
