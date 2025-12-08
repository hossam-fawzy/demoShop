package base;

import drivers.DriverFactory;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import utils.WaitUtils;

import java.util.List;

/**
 * Base Page class providing reusable methods for all Page Objects.
 * Implements Page Object Model (POM) pattern with best practices:
 * - Centralized element interaction methods
 * - Proper error handling and logging
 * - Wait utilities integration
 * - Thread-safe WebDriver management
 * 
 * All page objects should extend this class to inherit common functionality.
 * 
 * @author QA Team
 * @version 2.0
 */
public class BasePage {

    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    protected WebDriver driver;

    /**
     * Default constructor that gets driver from DriverFactory.
     * Use this when you want to use the current thread's driver instance.
     */
    public BasePage() {
        this.driver = DriverFactory.getDriver();
        logger.debug("BasePage initialized with driver from DriverFactory");
    }

    /**
     * Constructor that accepts a WebDriver instance.
     * Use this when you need to pass a specific driver instance.
     * 
     * @param driver WebDriver instance to use
     * @throws IllegalArgumentException if driver is null
     */
    public BasePage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        this.driver = driver;
        logger.debug("BasePage initialized with provided driver");
    }

    // ---------------------------------------------------------
    // WRAPPED ELEMENT OPERATIONS WITH WAITS
    // ---------------------------------------------------------

    protected WebElement find(By locator) {
        return WaitUtils.waitForPresence(driver, locator);
    }

    /**
     * Clicks on an element after waiting for it to be clickable.
     * Includes error handling and logging.
     * 
     * @param locator Element locator
     * @throws TimeoutException if element is not clickable
     * @throws ElementNotInteractableException if element cannot be clicked
     */
    protected void click(By locator) {
        try {
            WebElement element = WaitUtils.waitForClickable(driver, locator);
            element.click();
            logger.debug("Successfully clicked element: {}", locator);
        } catch (TimeoutException e) {
            logger.error("Element not clickable: {}", locator);
            throw new TimeoutException("Element not clickable within timeout: " + locator, e);
        } catch (ElementNotInteractableException e) {
            logger.error("Element not interactable: {}", locator);
            throw new ElementNotInteractableException("Element not interactable: " + locator, e);
        }
    }

    /**
     * Writes text to an input field after clearing it.
     * Includes error handling and logging.
     * 
     * @param locator Element locator
     * @param text Text to write
     * @throws TimeoutException if element is not visible
     */
    protected void write(By locator, String text) {
        try {
            WebElement element = WaitUtils.waitForVisibility(driver, locator);
            element.clear();
            element.sendKeys(text);
            logger.debug("Successfully entered text '{}' into element: {}", text, locator);
        } catch (TimeoutException e) {
            logger.error("Element not visible for writing: {}", locator);
            throw new TimeoutException("Element not visible within timeout: " + locator, e);
        } catch (Exception e) {
            logger.error("Failed to write text to element: {}", locator, e);
            throw new RuntimeException("Failed to write text to element: " + locator, e);
        }
    }

    protected String getText(By locator) {
        return WaitUtils.waitForVisibility(driver, locator).getText();
    }

    protected String getAttribute(By locator, String attribute) {
        return find(locator).getAttribute(attribute);
    }

    protected String getCssValue(By locator, String css) {
        return find(locator).getCssValue(css);
    }

    protected String getInputValue(By locator) {
        return getAttribute(locator, "value");
    }

    // ---------------------------------------------------------
    // BOOLEAN CHECKS
    // ---------------------------------------------------------

    protected boolean isVisible(By locator) {
        try {
            WaitUtils.waitForVisibility(driver, locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isPresent(By locator) {
        try {
            WaitUtils.waitForPresence(driver, locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isClickable(By locator) {
        try {
            WaitUtils.waitForClickable(driver, locator);
            return true;
        } catch (Exception e) {
            return false;
        }
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

    protected boolean isDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ---------------------------------------------------------
    // LIST ELEMENTS
    // ---------------------------------------------------------

    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    protected int getElementCount(By locator) {
        return findElements(locator).size();
    }

    protected boolean elementExists(By locator) {
        return driver.findElements(locator).size() > 0;
    }

    // ---------------------------------------------------------
    // PAGE / URL HELPERS
    // ---------------------------------------------------------

    protected void refreshPage() {
        driver.navigate().refresh();
    }

    protected void navigateBack() {
        driver.navigate().back();
    }

    protected void navigateForward() {
        driver.navigate().forward();
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    // ---------------------------------------------------------
    // JS & SCROLL ACTIONS
    // ---------------------------------------------------------

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

    // ---------------------------------------------------------
    // ALERTS
    // ---------------------------------------------------------

    protected void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    protected void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    protected String getAlertText() {
        return driver.switchTo().alert().getText();
    }

    // ---------------------------------------------------------
    // ACTIONS (mouse interactions)
    // ---------------------------------------------------------

    protected void hoverOverElement(By locator) {
        WebElement element = find(locator);
        new org.openqa.selenium.interactions.Actions(driver)
                .moveToElement(element)
                .perform();
    }

    protected void doubleClick(By locator) {
        WebElement element = find(locator);
        new org.openqa.selenium.interactions.Actions(driver)
                .doubleClick(element)
                .perform();
    }

    protected void rightClick(By locator) {
        WebElement element = find(locator);
        new org.openqa.selenium.interactions.Actions(driver)
                .contextClick(element)
                .perform();
    }
}
