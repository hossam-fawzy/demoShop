package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for Login Page.
 * Handles all login-related operations and validations.
 * Follows Page Object Model (POM) best practices with proper encapsulation.
 * 
 * @author QA Team
 * @version 2.0
 */
public class LoginPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    public LoginPage(WebDriver driver) {
        super(driver);
    }
    public LoginPage() {
        super();
    }

    // ==========================================
    // LOCATORS
    // ==========================================
    private final By loginLink = By.xpath("//a[normalize-space()='Log in']");
    private final By emailInput = By.id("Email");
    private final By passwordInput = By.id("Password");
    private final By rememberMeCheckbox = By.id("RememberMe");
    private final By loginButton = By.xpath("//input[@value='Log in']");
    private final By errorMessage = By.xpath("//span[contains(text(),'Login was unsuccessful')]");
    private final By logoutButton = By.xpath("//a[normalize-space()='Log out']");
    private final By forgetPasswordButton = By.xpath("//a[normalize-space()='Forgot password?']");

    // ==========================================
    // NAVIGATION ACTIONS
    // ==========================================

    @Step("Navigate to login page")
    public void onLogin() {
        click(loginLink);
        logger.info("Navigated to login page");
    }

    // ==========================================
    // INPUT ACTIONS
    // ==========================================

    @Step("Enter email: {email}")
    public void enterEmail(String email) {
        write(emailInput, email);
        logger.info("Entered email: {}", email);
    }

    @Step("Enter password")
    public void enterPassword(String password) {
        write(passwordInput, password);
        logger.info("Password entered");
    }

    @Step("Click login button")
    public void clickLogin() {
        click(loginButton);
        logger.info("Clicked login button");
    }

    @Step("Check 'Remember Me' checkbox")
    public void checkRememberMe() {
        if (isVisible(rememberMeCheckbox) && !isRememberMeChecked()) {
            click(rememberMeCheckbox);
            logger.info("'Remember Me' checkbox checked");
        }
    }

    @Step("Uncheck 'Remember Me' checkbox")
    public void uncheckRememberMe() {
        if (isVisible(rememberMeCheckbox) && isRememberMeChecked()) {
            click(rememberMeCheckbox);
            logger.info("'Remember Me' checkbox unchecked");
        }
    }

    // ==========================================
    // COMPOSITE ACTIONS
    // ==========================================

    @Step("Login with credentials")
    public void login(String email, String password) {
        onLogin();
        enterEmail(email);
        enterPassword(password);
        clickLogin();
        logger.info("Login performed for user: {}", email);
    }

    @Step("Login with Remember Me enabled")
    public void loginWithRememberMe(String email, String password) {
        onLogin();
        enterEmail(email);
        enterPassword(password);
        checkRememberMe();
        clickLogin();
        logger.info("Login with 'Remember Me' performed for user: {}", email);
    }

    // ==========================================
    // VALIDATION METHODS
    // ==========================================

    @Step("Get error message text")
    public String getErrorMessage() {
        String error = getText(errorMessage);
        logger.info("Error message retrieved: {}", error);
        return error;
    }

    @Step("Verify login was successful")
    public boolean isLogoutButtonVisible() {
        boolean isVisible = isVisible(logoutButton);
        logger.info("Logout button visible: {}", isVisible);
        return isVisible;
    }

    @Step("Check if login button is visible")
    public boolean isLoginButtonVisible() {
        return isVisible(loginButton);
    }

    @Step("Check if error message is displayed")
    public boolean isErrorMessageDisplayed() {
        return isVisible(errorMessage);
    }

    @Step("Check if 'Remember Me' is selected")
    public boolean isRememberMeChecked() {
        try {
            boolean isChecked = isSelected(rememberMeCheckbox);
            logger.debug("Remember Me checked status: {}", isChecked);
            return isChecked;
        } catch (Exception e) {
            logger.warn("Failed to check 'Remember Me' status: {}", e.getMessage());
            return false;
        }
    }

    @Step("Verify user is logged in successfully")
    public boolean isLoginSuccessful() {
        boolean isSuccess = isVisible(logoutButton);
        logger.info("Login success status: {}", isSuccess);
        return isSuccess;
    }

    public void clickLoginButton() {
        clickLogin();
        logger.info("Clicked login button");
    }

    public boolean isEmailFieldVisible() {
        boolean isVisible = isVisible(emailInput);
        logger.info("Email field visible: {}", isVisible);
        return isVisible;
    }

    public boolean isPasswordFieldVisible() {
        boolean isVisible = isVisible(passwordInput);
        logger.info("Password field visible: {}", isVisible);
        return isVisible;
    }

    public boolean isRememberMeCheckboxVisible() {
        boolean isVisible = isVisible(rememberMeCheckbox);
        logger.info("Remember Me checkbox visible: {}", isVisible);
        return isVisible;
    }

    public boolean isForgotPasswordLinkVisible() {
        boolean isVisible = isVisible(forgetPasswordButton);
        logger.info("Forgot password link visible: {}", isVisible);
        return isVisible;
    }

    public void clickForgotPassword() {
        click(forgetPasswordButton);
    }

    /**
     * Get current page URL
     * @return Current URL as string
     */
    public String getPageUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Get page title
     * @return Page title as string
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
}