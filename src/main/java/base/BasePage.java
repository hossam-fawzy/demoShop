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
 * Now fully integrated with WaitUtils for clean architecture.
 */
public class BasePage {

    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    protected WebDriver driver;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
    }

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    // ---------------------------------------------------------
    // WRAPPED ELEMENT OPERATIONS WITH WAITS
    // ---------------------------------------------------------

    protected WebElement find(By locator) {
        return WaitUtils.waitForPresence(driver, locator);
    }

    protected void click(By locator) {
        WebElement element = WaitUtils.waitForClickable(driver, locator);
        element.click();
    }

    protected void write(By locator, String text) {
        WebElement element = WaitUtils.waitForVisibility(driver, locator);
        element.clear();
        element.sendKeys(text);
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
