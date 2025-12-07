package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegistrationPage extends BasePage {

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

    // Action Methods

    @Step("Select male gender")
    public void selectMaleGender() {
        click(maleGenderRadio);
    }

    @Step("Select female gender")
    public void selectFemaleGender() {
        click(femaleGenderRadio);
    }

    @Step("Enter first name: {firstName}")
    public void enterFirstName(String firstName) {
        write(firstNameField, firstName);
    }

    @Step("Enter last name: {lastName}")
    public void enterLastName(String lastName) {
        write(lastNameField, lastName);
    }

    @Step("Enter email: {email}")
    public void enterEmail(String email) {
        write(emailField, email);
    }

    @Step("Enter password")
    public void enterPassword(String password) {
        write(passwordField, password);
    }

    @Step("Enter confirm password")
    public void enterConfirmPassword(String confirmPassword) {
        write(confirmPasswordField, confirmPassword);
    }

    @Step("Click register button")
    public void clickRegisterButton() {
        click(registerButton);
    }

    @Step("Click continue button")
    public void clickContinueButton() {
        click(continueButton);
    }

    @Step("Fill registration form with gender: {gender}, name: {firstName} {lastName}")
    public void fillRegistrationForm(String gender, String firstName, String lastName,
                                     String email, String password, String confirmPassword) {

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
    }

    @Step("Register new user with email: {email}")
    public void register(String gender, String firstName, String lastName,
                         String email, String password, String confirmPassword) {
        fillRegistrationForm(gender, firstName, lastName, email, password, confirmPassword);
        clickRegisterButton();
    }

    // Verification Methods

    @Step("Verify registration is successful")
    public boolean isRegistrationSuccessful() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return isVisible(successMessage);
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get success message text")
    public String getSuccessMessage() {
        return getText(successMessage);
    }

    @Step("Check if email error is displayed")
    public boolean isEmailErrorDisplayed() {
        return isVisible(emailErrorMessage);
    }

    @Step("Get email error message")
    public String getEmailErrorMessage() {
        return getText(emailErrorMessage);
    }

    @Step("Check if password error is displayed")
    public boolean isPasswordErrorDisplayed() {
        return isVisible(passwordErrorMessage);
    }

    @Step("Get password error message")
    public String getPasswordErrorMessage() {
        return getText(passwordErrorMessage);
    }

    @Step("Check if confirm password error is displayed")
    public boolean isConfirmPasswordErrorDisplayed() {
        return isVisible(confirmPasswordErrorMessage);
    }

    @Step("Get confirm password error message")
    public String getConfirmPasswordErrorMessage() {
        return getText(confirmPasswordErrorMessage);
    }

    @Step("Check if first name error is displayed")
    public boolean isFirstNameErrorDisplayed() {
        return isVisible(firstNameErrorMessage);
    }

    @Step("Get first name error message")
    public String getFirstNameErrorMessage() {
        return getText(firstNameErrorMessage);
    }

    @Step("Check if last name error is displayed")
    public boolean isLastNameErrorDisplayed() {
        return isVisible(lastNameErrorMessage);
    }

    @Step("Get last name error message")
    public String getLastNameErrorMessage() {
        return getText(lastNameErrorMessage);
    }

    @Step("Check if continue button is visible")
    public boolean isContinueButtonVisible() {
        return isVisible(continueButton);
    }

    @Step("Check if register button is visible")
    public boolean isRegisterButtonVisible() {
        return isVisible(registerButton);
    }

    // Additional Utility Methods for Real-World Scenarios

    @Step("Clear all registration form fields")
    public void clearRegistrationForm() {
        clear(firstNameField);
        clear(lastNameField);
        clear(emailField);
        clear(passwordField);
        clear(confirmPasswordField);
    }

    @Step("Verify all required fields are present on the page")
    public boolean areAllRequiredFieldsPresent() {
        return isVisible(firstNameField) &&
                isVisible(lastNameField) &&
                isVisible(emailField) &&
                isVisible(passwordField) &&
                isVisible(confirmPasswordField) &&
                isVisible(registerButton);
    }

    @Step("Check if any validation error is displayed")
    public boolean isAnyValidationErrorDisplayed() {
        return isFirstNameErrorDisplayed() ||
                isLastNameErrorDisplayed() ||
                isEmailErrorDisplayed() ||
                isPasswordErrorDisplayed() ||
                isConfirmPasswordErrorDisplayed();
    }

    @Step("Wait for registration page to load completely")
    public void waitForPageToLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(registerButton));
    }

    @Step("Get current page title")
    public String getPageTitle() {
        return driver.getTitle();
    }
}