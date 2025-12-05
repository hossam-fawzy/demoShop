package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage extends BasePage {

    // ≡ Locators for Registration Form Fields

    private final By maleGenderRadio = By.id("gender-male");
    private final By femaleGenderRadio = By.id("gender-female");

    private final By firstNameField = By.id("FirstName");
    private final By lastNameField = By.id("LastName");
    private final By emailField = By.id("Email");

    private final By passwordField = By.id("Password");
    private final By confirmPasswordField = By.id("ConfirmPassword");

    private final By registerButton = By.id("register-button");

    private final By successMessage = By.xpath("//div[@class='result']");
    private final By emailErrorMessage = By.xpath("//span[@for='Email']");
    private final By passwordErrorMessage = By.xpath("//span[@for='Password']");
    private final By confirmPasswordErrorMessage = By.xpath("//span[@for='ConfirmPassword']");
    private final By firstNameErrorMessage = By.xpath("//span[@for='FirstName']");
    private final By lastNameErrorMessage = By.xpath("//span[@for='LastName']");


    private final By continueButton = By.xpath("//input[@value='Continue']");


    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    // ≡ Actions / Methods


    public void selectMaleGender() {
        click(maleGenderRadio);
    }


    public void selectFemaleGender() {
        click(femaleGenderRadio);
    }


    public void enterFirstName(String firstName) {
        write(firstNameField, firstName);
    }


    public void enterLastName(String lastName) {
        write(lastNameField, lastName);
    }


    public void enterEmail(String email) {
        write(emailField, email);
    }


    public void enterPassword(String password) {
        write(passwordField, password);
    }


    public void enterConfirmPassword(String confirmPassword) {
        write(confirmPasswordField, confirmPassword);
    }


    public void clickRegisterButton() {
        click(registerButton);
    }


    public void clickContinueButton() {
        click(continueButton);
    }


    public void fillRegistrationForm(String gender, String firstName, String lastName,
                                     String email, String password, String confirmPassword) {

        if (gender.equalsIgnoreCase("male")) {
            selectMaleGender();
        } else if (gender.equalsIgnoreCase("female")) {
            selectFemaleGender();
        }

        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);

        enterPassword(password);
        enterConfirmPassword(confirmPassword);
    }


    public void register(String gender, String firstName, String lastName,
                         String email, String password, String confirmPassword) {
        fillRegistrationForm(gender, firstName, lastName, email, password, confirmPassword);
        clickRegisterButton();
    }

    // ≡ Verification Methods

    public boolean isRegistrationSuccessful() {
        return isVisible(successMessage);
    }


    public String getSuccessMessage() {
        return getText(successMessage);
    }


    public boolean isEmailErrorDisplayed() {
        return isVisible(emailErrorMessage);
    }


    public String getEmailErrorMessage() {
        return getText(emailErrorMessage);
    }


    public boolean isPasswordErrorDisplayed() {
        return isVisible(passwordErrorMessage);
    }


    public String getPasswordErrorMessage() {
        return getText(passwordErrorMessage);
    }


    public boolean isConfirmPasswordErrorDisplayed() {
        return isVisible(confirmPasswordErrorMessage);
    }


    public String getConfirmPasswordErrorMessage() {
        return getText(confirmPasswordErrorMessage);
    }


    public boolean isFirstNameErrorDisplayed() {
        return isVisible(firstNameErrorMessage);
    }


    public String getFirstNameErrorMessage() {
        return getText(firstNameErrorMessage);
    }


    public boolean isLastNameErrorDisplayed() {
        return isVisible(lastNameErrorMessage);
    }


    public String getLastNameErrorMessage() {
        return getText(lastNameErrorMessage);
    }


    public boolean isContinueButtonVisible() {
        return isVisible(continueButton);
    }


    public boolean isRegisterButtonVisible() {
        return isVisible(registerButton);
    }
}