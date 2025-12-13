package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.WaitUtils;

/**
 * Page Object for Registration Page.
 * Handles all registration-related operations and validations.
 * Follows Page Object Model (POM) best practices with proper encapsulation.
 * 
 * @author QA Team
 * @version 2.0
 */
public class RegistrationPage extends BasePage {

    // BEST PRACTICE: Use Log4j2 logger for debugging and tracking
    private static final Logger logger = LogManager.getLogger(RegistrationPage.class);

    // Locators for Registration Form Fields
    private final By maleGenderRadio = By.id("gender-male");
    private final By femaleGenderRadio = By.id("gender-female");

    private final By firstNameField = By.id("FirstName");
    private final By lastNameField = By.id("LastName");
    private final By emailField = By.id("Email");

    private final By passwordField = By.id("Password");
    private final By confirmPasswordField = By.id("ConfirmPassword");

    private final By registerButton = By.id("register-button");

    // Success and Error Message Locators
    private final By successMessage = By.xpath("//div[@class='result']");
    private final By emailErrorMessage = By.xpath("//span[@for='Email']");
    private final By passwordErrorMessage = By.xpath("//span[@for='Password']");
    private final By confirmPasswordErrorMessage = By.xpath("//span[@for='ConfirmPassword']");
    private final By firstNameErrorMessage = By.xpath("//span[@for='FirstName']");
    private final By lastNameErrorMessage = By.xpath("//span[@for='LastName']");

    private final By continueButton = By.xpath("//input[@value='Continue']");

    // Constructor
    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    // ==========================================
    // ACTION METHODS
    // ==========================================

    @Step("Select male gender")
    public void selectMaleGender() {
        click(maleGenderRadio);
        logger.info("Selected male gender");
    }

    @Step("Select female gender")
    public void selectFemaleGender() {
        click(femaleGenderRadio);
        logger.info("Selected female gender");
    }

    @Step("Enter first name: {firstName}")
    public void enterFirstName(String firstName) {
        write(firstNameField, firstName);
        logger.info("Entered first name: {}", firstName);
    }

    @Step("Enter last name: {lastName}")
    public void enterLastName(String lastName) {
        write(lastNameField, lastName);
        logger.info("Entered last name: {}", lastName);
    }

    @Step("Enter email: {email}")
    public void enterEmail(String email) {
        write(emailField, email);
        logger.info("Entered email: {}", email);
    }

    @Step("Enter password")
    public void enterPassword(String password) {
        write(passwordField, password);
        logger.info("Password entered");  // SECURITY: Don't log password value
    }

    @Step("Enter confirm password")
    public void enterConfirmPassword(String confirmPassword) {
        write(confirmPasswordField, confirmPassword);
        logger.info("Confirm password entered");  // SECURITY: Don't log password value
    }

    @Step("Click register button")
    public void clickRegisterButton() {
        click(registerButton);
        logger.info("Clicked register button");
    }

    @Step("Click continue button")
    public void clickContinueButton() {
        click(continueButton);
        logger.info("Clicked continue button");
    }

    @Step("Fill registration form with gender: {gender}, name: {firstName} {lastName}")
    public void fillRegistrationForm(String gender, String firstName, String lastName,
                                     String email, String password, String confirmPassword) {

        logger.info("Filling registration form for user: {} {}", firstName, lastName);

        if (gender != null && !gender.isEmpty()) {
            if (gender.equalsIgnoreCase("male")) {
                selectMaleGender();
            } else if (gender.equalsIgnoreCase("female")) {
                selectFemaleGender();
            }
        }

        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);

        logger.info("Registration form filled successfully");
    }

    @Step("Register new user with email: {email}")
    public void register(String gender, String firstName, String lastName,
                         String email, String password, String confirmPassword) {
        logger.info("Starting registration process for: {} {}", firstName, lastName);
        fillRegistrationForm(gender, firstName, lastName, email, password, confirmPassword);
        clickRegisterButton();
        logger.info("Registration submitted for email: {}", email);
    }

    // ==========================================
    // VERIFICATION METHODS
    // ==========================================

    @Step("Verify registration is successful")
    public boolean isRegistrationSuccessful() {
        try {
            WaitUtils.waitForVisibility(driver, successMessage,10);
            boolean isSuccessful = isVisible(successMessage);
            logger.info("Registration successful: {}", isSuccessful);
            return isSuccessful;
        } catch (Exception e) {
            logger.warn("Registration success check failed: {}", e.getMessage());
            return false;
        }
    }

    @Step("Get success message text")
    public String getSuccessMessage() {
        String message = getText(successMessage);
        logger.info("Success message retrieved: {}", message);
        return message;
    }

    @Step("Check if email error is displayed")
    public boolean isEmailErrorDisplayed() {
        boolean isDisplayed = isVisible(emailErrorMessage);
        logger.info("Email error displayed: {}", isDisplayed);
        return isDisplayed;
    }

    @Step("Get email error message")
    public String getEmailErrorMessage() {
        String error = getText(emailErrorMessage);
        logger.info("Email error message: {}", error);
        return error;
    }

    @Step("Check if password error is displayed")
    public boolean isPasswordErrorDisplayed() {
        boolean isDisplayed = isVisible(passwordErrorMessage);
        logger.info("Password error displayed: {}", isDisplayed);
        return isDisplayed;
    }

    @Step("Get password error message")
    public String getPasswordErrorMessage() {
        String error = getText(passwordErrorMessage);
        logger.info("Password error message: {}", error);
        return error;
    }

    @Step("Check if confirm password error is displayed")
    public boolean isConfirmPasswordErrorDisplayed() {
        boolean isDisplayed = isVisible(confirmPasswordErrorMessage);
        logger.info("Confirm password error displayed: {}", isDisplayed);
        return isDisplayed;
    }

    @Step("Get confirm password error message")
    public String getConfirmPasswordErrorMessage() {
        String error = getText(confirmPasswordErrorMessage);
        logger.info("Confirm password error message: {}", error);
        return error;
    }

    @Step("Check if first name error is displayed")
    public boolean isFirstNameErrorDisplayed() {
        boolean isDisplayed = isVisible(firstNameErrorMessage);
        logger.info("First name error displayed: {}", isDisplayed);
        return isDisplayed;
    }

    @Step("Get first name error message")
    public String getFirstNameErrorMessage() {
        String error = getText(firstNameErrorMessage);
        logger.info("First name error message: {}", error);
        return error;
    }

    @Step("Check if last name error is displayed")
    public boolean isLastNameErrorDisplayed() {
        boolean isDisplayed = isVisible(lastNameErrorMessage);
        logger.info("Last name error displayed: {}", isDisplayed);
        return isDisplayed;
    }

    @Step("Get last name error message")
    public String getLastNameErrorMessage() {
        String error = getText(lastNameErrorMessage);
        logger.info("Last name error message: {}", error);
        return error;
    }

    @Step("Check if continue button is visible")
    public boolean isContinueButtonVisible() {
        boolean isVisible = isVisible(continueButton);
        logger.info("Continue button visible: {}", isVisible);
        return isVisible;
    }

    @Step("Check if register button is visible")
    public boolean isRegisterButtonVisible() {
        boolean isVisible = isVisible(registerButton);
        logger.info("Register button visible: {}", isVisible);
        return isVisible;
    }

    // ==========================================
    // ADDITIONAL UTILITY METHODS
    // ==========================================

    @Step("Clear all registration form fields")
    public void clearRegistrationForm() {
        logger.info("Clearing all registration form fields");
        clear(firstNameField);
        clear(lastNameField);
        clear(emailField);
        clear(passwordField);
        clear(confirmPasswordField);
        logger.info("All registration form fields cleared");
    }

    @Step("Verify all required fields are present on the page")
    public boolean areAllRequiredFieldsPresent() {
        boolean allPresent = isVisible(firstNameField) &&
                isVisible(lastNameField) &&
                isVisible(emailField) &&
                isVisible(passwordField) &&
                isVisible(confirmPasswordField) &&
                isVisible(registerButton);
        
        logger.info("All required fields present: {}", allPresent);
        return allPresent;
    }

    @Step("Check if any validation error is displayed")
    public boolean isAnyValidationErrorDisplayed() {
        boolean hasError = isFirstNameErrorDisplayed() ||
                isLastNameErrorDisplayed() ||
                isEmailErrorDisplayed() ||
                isPasswordErrorDisplayed() ||
                isConfirmPasswordErrorDisplayed();
        
        logger.info("Validation error(s) displayed: {}", hasError);
        return hasError;
    }

    @Step("Wait for registration page to load completely")
    public void waitForPageToLoad() {
        logger.debug("Waiting for registration page to load...");
        WaitUtils.waitForVisibility(driver,registerButton,10);
        logger.info("Registration page loaded successfully");
    }

    @Step("Get current page title")
    public String getPageTitle() {
        String title = driver.getTitle();
        logger.info("Current page title: {}", title);
        return title;
    }
}