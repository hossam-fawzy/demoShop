package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage extends BasePage {

    // ≡ Locators for Registration Form Fields

    // Gender radio buttons
    private final By maleGenderRadio = By.id("gender-male");
    private final By femaleGenderRadio = By.id("gender-female");

    // Personal information fields
    private final By firstNameField = By.id("FirstName");
    private final By lastNameField = By.id("LastName");
    private final By emailField = By.id("Email");

    // Account credentials
    private final By passwordField = By.id("Password");
    private final By confirmPasswordField = By.id("ConfirmPassword");

    // Register button
    private final By registerButton = By.id("register-button");

    // Success/Error messages
    private final By successMessage = By.xpath("//div[@class='result']");
    private final By emailErrorMessage = By.xpath("//span[@for='Email']");
    private final By passwordErrorMessage = By.xpath("//span[@for='Password']");
    private final By confirmPasswordErrorMessage = By.xpath("//span[@for='ConfirmPassword']");
    private final By firstNameErrorMessage = By.xpath("//span[@for='FirstName']");
    private final By lastNameErrorMessage = By.xpath("//span[@for='LastName']");

    // Continue button (appears after successful registration)
    private final By continueButton = By.xpath("//input[@value='Continue']");

    // ≡ Constructor
    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    // ≡ Actions / Methods

    /** Select Male gender */
    public void selectMaleGender() {
        click(maleGenderRadio);
    }

    /** Select Female gender */
    public void selectFemaleGender() {
        click(femaleGenderRadio);
    }

    /** Enter first name */
    public void enterFirstName(String firstName) {
        write(firstNameField, firstName);
    }

    /** Enter last name */
    public void enterLastName(String lastName) {
        write(lastNameField, lastName);
    }

    /** Enter email address */
    public void enterEmail(String email) {
        write(emailField, email);
    }

    /** Enter password */
    public void enterPassword(String password) {
        write(passwordField, password);
    }

    /** Enter confirm password */
    public void enterConfirmPassword(String confirmPassword) {
        write(confirmPasswordField, confirmPassword);
    }

    /** Click Register button */
    public void clickRegisterButton() {
        click(registerButton);
    }

    /** Click Continue button (after successful registration) */
    public void clickContinueButton() {
        click(continueButton);
    }

    /**
     * Complete registration form with all details
     * This is a helper method that fills the entire form at once
     * @param gender - "male" or "female"
     * @param firstName - user's first name
     * @param lastName - user's last name
     * @param email - user's email
     * @param password - user's password
     * @param confirmPassword - password confirmation
     */
    public void fillRegistrationForm(String gender, String firstName, String lastName,
                                     String email, String password, String confirmPassword) {
        // Select gender
        if (gender.equalsIgnoreCase("male")) {
            selectMaleGender();
        } else if (gender.equalsIgnoreCase("female")) {
            selectFemaleGender();
        }

        // Fill personal information
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);

        // Fill account credentials
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
    }

    /**
     * Complete full registration process
     * Fills form and clicks register button
     */
    public void register(String gender, String firstName, String lastName,
                         String email, String password, String confirmPassword) {
        fillRegistrationForm(gender, firstName, lastName, email, password, confirmPassword);
        clickRegisterButton();
    }

    // ≡ Verification Methods

    /** Check if registration was successful by looking for success message */
    public boolean isRegistrationSuccessful() {
        return isVisible(successMessage);
    }

    /** Get the success message text */
    public String getSuccessMessage() {
        return getText(successMessage);
    }

    /** Check if email error message is displayed */
    public boolean isEmailErrorDisplayed() {
        return isVisible(emailErrorMessage);
    }

    /** Get email error message text */
    public String getEmailErrorMessage() {
        return getText(emailErrorMessage);
    }

    /** Check if password error message is displayed */
    public boolean isPasswordErrorDisplayed() {
        return isVisible(passwordErrorMessage);
    }

    /** Get password error message text */
    public String getPasswordErrorMessage() {
        return getText(passwordErrorMessage);
    }

    /** Check if confirm password error message is displayed */
    public boolean isConfirmPasswordErrorDisplayed() {
        return isVisible(confirmPasswordErrorMessage);
    }

    /** Get confirm password error message text */
    public String getConfirmPasswordErrorMessage() {
        return getText(confirmPasswordErrorMessage);
    }

    /** Check if first name error message is displayed */
    public boolean isFirstNameErrorDisplayed() {
        return isVisible(firstNameErrorMessage);
    }

    /** Get first name error message text */
    public String getFirstNameErrorMessage() {
        return getText(firstNameErrorMessage);
    }

    /** Check if last name error message is displayed */
    public boolean isLastNameErrorDisplayed() {
        return isVisible(lastNameErrorMessage);
    }

    /** Get last name error message text */
    public String getLastNameErrorMessage() {
        return getText(lastNameErrorMessage);
    }

    /** Check if continue button is visible (shown after successful registration) */
    public boolean isContinueButtonVisible() {
        return isVisible(continueButton);
    }

    /** Check if register button is visible */
    public boolean isRegisterButtonVisible() {
        return isVisible(registerButton);
    }
}