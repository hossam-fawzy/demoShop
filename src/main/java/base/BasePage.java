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


    protected void clear(By locator) {
        find(locator).clear();
    }


    protected String getInputValue(By locator) {
        return getAttribute(locator, "value");
    }
}