package base;

import drivers.DriverFactory;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;
import java.util.List;

/**
 * Base Page class providing reusable methods for all Page Objects.
 * All existing methods remain unchanged for backward compatibility.
 */
public class BasePage {
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        int explicitWait = ConfigReader.getInt("explicitWait", 10);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
    }

    public BasePage(WebDriver driver) {
        this.driver = driver;
        int explicitWait = ConfigReader.getInt("explicitWait", 10);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
    }

    // ==========================================
    // EXISTING CORE METHODS (UNCHANGED)
    // ==========================================

    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void write(By locator, String text) {
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    protected boolean isVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isPresent(By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isClickable(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected String getAttribute(By locator, String attribute) {
        return find(locator).getAttribute(attribute);
    }

    protected boolean isEnabled(By locator) {
        try {
            return find(locator).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isSelected(By locator) {
        try {
            return find(locator).isSelected();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean waitForVisibility(By locator, int timeoutSeconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean waitForInvisibility(By locator, int timeoutSeconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            customWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected void scrollToElement(By locator) {
        WebElement element = find(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void clickUsingJS(By locator) {
        WebElement element = find(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void clear(By locator) {
        find(locator).clear();
    }

    protected String getInputValue(By locator) {
        return getAttribute(locator, "value");
    }

    // ==========================================
    // NEW UTILITY METHODS (OPTIONAL TO USE)
    // ==========================================

    /**
     * Find multiple elements matching the locator
     * Useful for working with lists, tables, or repeating elements
     */
    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    /**
     * Get count of elements matching the locator
     * Useful for validation (e.g., "cart should have 3 items")
     */
    protected int getElementCount(By locator) {
        return findElements(locator).size();
    }

    /**
     * Check if element exists in DOM (without waiting)
     * Faster than isPresent() for negative checks
     */
    protected boolean elementExists(By locator) {
        return driver.findElements(locator).size() > 0;
    }

    /**
     * Wait for URL to contain specific text
     * Useful after navigation or redirects
     */
    protected boolean waitForUrlContains(String urlFragment, int timeoutSeconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            return customWait.until(ExpectedConditions.urlContains(urlFragment));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for page title to contain specific text
     * Useful for page load verification
     */
    protected boolean waitForTitleContains(String title, int timeoutSeconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            return customWait.until(ExpectedConditions.titleContains(title));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get CSS value of an element
     * Useful for style/appearance validation
     */
    protected String getCssValue(By locator, String propertyName) {
        return find(locator).getCssValue(propertyName);
    }

    /**
     * Check if element is displayed (without waiting)
     * Faster for elements that should already be visible
     */
    protected boolean isDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Refresh the current page
     */
    protected void refreshPage() {
        driver.navigate().refresh();
    }

    /**
     * Navigate back in browser history
     */
    protected void navigateBack() {
        driver.navigate().back();
    }

    /**
     * Navigate forward in browser history
     */
    protected void navigateForward() {
        driver.navigate().forward();
    }

    /**
     * Switch to alert and accept it
     */
    protected void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).accept();
    }

    /**
     * Switch to alert and dismiss it
     */
    protected void dismissAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).dismiss();
    }

    /**
     * Get alert text
     */
    protected String getAlertText() {
        return wait.until(ExpectedConditions.alertIsPresent()).getText();
    }

    /**
     * Hover over an element
     * Useful for dropdown menus or tooltips
     */
    protected void hoverOverElement(By locator) {
        WebElement element = find(locator);
        org.openqa.selenium.interactions.Actions actions =
                new org.openqa.selenium.interactions.Actions(driver);
        actions.moveToElement(element).perform();
    }

    /**
     * Double click on an element
     */
    protected void doubleClick(By locator) {
        WebElement element = find(locator);
        org.openqa.selenium.interactions.Actions actions =
                new org.openqa.selenium.interactions.Actions(driver);
        actions.doubleClick(element).perform();
    }

    /**
     * Right click (context click) on an element
     */
    protected void rightClick(By locator) {
        WebElement element = find(locator);
        org.openqa.selenium.interactions.Actions actions =
                new org.openqa.selenium.interactions.Actions(driver);
        actions.contextClick(element).perform();
    }
}