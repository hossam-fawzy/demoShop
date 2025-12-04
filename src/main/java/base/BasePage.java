package base;

import drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // CONSTRUCTOR 1: No-argument constructor - gets driver from DriverFactory
    // Use this when you create page objects like: new HomePage()
    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // CONSTRUCTOR 2: Constructor with driver parameter - receives driver directly
    // Use this when you create page objects like: new HomePage(driver)
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ≡ CORE METHODS

    /**
     * Find element with explicit wait
     * Waits until element is present in DOM (not necessarily visible)
     * @param locator - By locator for the element
     * @return WebElement
     */
    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Click element with explicit wait
     * Waits until element is clickable before clicking
     * @param locator - By locator for the element to click
     */
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /**
     * Type text into element with explicit wait
     * Clears existing text first, then types new text
     * @param locator - By locator for the input field
     * @param text - Text to type
     */
    protected void write(By locator, String text) {
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get text from element with explicit wait
     * Waits until element is visible before getting text
     * @param locator - By locator for the element
     * @return String - text content of element
     */
    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    /**
     * Check if element is visible on page
     * Returns true if visible, false if not found or not visible
     * @param locator - By locator for the element
     * @return boolean - true if visible, false otherwise
     */
    protected boolean isVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ≡ ADDITIONAL USEFUL METHODS (ADDED FOR BETTER FUNCTIONALITY)

    /**
     * Check if element is present in DOM (not necessarily visible)
     * Useful for checking if element exists even if it's hidden
     * @param locator - By locator for the element
     * @return boolean - true if present, false otherwise
     */
    protected boolean isPresent(By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if element is clickable
     * Useful before performing click actions
     * @param locator - By locator for the element
     * @return boolean - true if clickable, false otherwise
     */
    protected boolean isClickable(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get attribute value from element
     * Useful for getting values like "value", "href", "class", etc.
     * Example: getAttribute(locator, "value") for input field values
     * @param locator - By locator for the element
     * @param attribute - attribute name (e.g., "value", "href", "class")
     * @return String - attribute value
     */
    protected String getAttribute(By locator, String attribute) {
        return find(locator).getAttribute(attribute);
    }

    /**
     * Check if element is enabled (not disabled)
     * Useful for checking if buttons/inputs are enabled
     * @param locator - By locator for the element
     * @return boolean - true if enabled, false if disabled
     */
    protected boolean isEnabled(By locator) {
        try {
            return find(locator).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if element is selected (for checkboxes/radio buttons)
     * @param locator - By locator for the element
     * @return boolean - true if selected, false otherwise
     */
    protected boolean isSelected(By locator) {
        try {
            return find(locator).isSelected();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for element to be visible
     * Useful when you need to wait before performing actions
     * @param locator - By locator for the element
     * @param timeoutSeconds - how many seconds to wait
     * @return boolean - true if element becomes visible, false if timeout
     */
    protected boolean waitForVisibility(By locator, int timeoutSeconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for element to disappear
     * Useful for waiting for loading spinners to disappear
     * @param locator - By locator for the element
     * @param timeoutSeconds - how many seconds to wait
     * @return boolean - true if element disappears, false if still visible after timeout
     */
    protected boolean waitForInvisibility(By locator, int timeoutSeconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            customWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get current page URL
     * @return String - current URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Get current page title
     * @return String - page title
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Scroll to element
     * Useful when element is not in viewport
     * @param locator - By locator for the element
     */
    protected void scrollToElement(By locator) {
        WebElement element = find(locator);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Click using JavaScript (useful when normal click doesn't work)
     * @param locator - By locator for the element
     */
    protected void clickUsingJS(By locator) {
        WebElement element = find(locator);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
     * Clear text field (without typing anything)
     * @param locator - By locator for the input field
     */
    protected void clear(By locator) {
        find(locator).clear();
    }

    /**
     * Get value from input field
     * Useful for verifying what's typed in input fields
     * @param locator - By locator for the input field
     * @return String - value attribute of input field
     */
    protected String getInputValue(By locator) {
        return getAttribute(locator, "value");
    }
}