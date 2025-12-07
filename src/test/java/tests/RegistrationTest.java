package tests;

import base.BaseTest;
import drivers.DriverFactory;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegistrationPage;

@Epic("E-Commerce Platform")
@Feature("User Registration")
public class RegistrationTest extends BaseTest {

    private String generateUniqueEmail() {
        long timestamp = System.currentTimeMillis();
        return "testuser" + timestamp + "@example.com";
    }

    @Test(priority = 1, description = "Verify registration page loads and elements are visible")
    @Story("Registration Page UI")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that registration page loads successfully and all required elements are visible to users")
    public void verifyRegistrationPageElementsTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        Assert.assertTrue(registrationPage.isRegisterButtonVisible(),
                "Register button should be visible on registration page");
        Assert.assertTrue(registrationPage.areAllRequiredFieldsPresent(),
                "All required registration fields should be present on the page");
    }

    @Test(priority = 2, description = "Verify successful registration with valid data")
    @Story("User Registration Flow")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that users can successfully register with valid information and receive confirmation message")
    public void successfulRegistrationTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        String uniqueEmail = generateUniqueEmail();
        registrationPage.register(
                "male",
                "John",
                "Doe",
                uniqueEmail,
                "Password123",
                "Password123"
        );

        Assert.assertTrue(registrationPage.isRegistrationSuccessful(),
                "Registration should be successful with valid data");

        String successMsg = registrationPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Your registration completed"),
                "Expected success message to contain 'Your registration completed', but got: " + successMsg);
    }

    @Test(priority = 3, description = "Verify registration fails with already registered email")
    @Story("Registration Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that system prevents duplicate registration with an already registered email address")
    public void registrationWithExistingEmailTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        registrationPage.register(
                "male",
                "Test",
                "User",
                "testuser@example.com",
                "Password123",
                "Password123"
        );

        Assert.assertTrue(registrationPage.isEmailErrorDisplayed(),
                "Email error should be displayed when attempting to register with existing email");

        String emailError = registrationPage.getEmailErrorMessage();
        Assert.assertFalse(emailError.isEmpty(),
                "Email error message should not be empty");
    }

    @Test(priority = 4, description = "Verify registration fails with empty fields")
    @Story("Registration Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that all required field validations are triggered when attempting to register with empty fields")
    public void registrationWithEmptyFieldsTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        registrationPage.clickRegisterButton();

        Assert.assertTrue(registrationPage.isFirstNameErrorDisplayed(),
                "First name error should be displayed when field is empty");
        Assert.assertTrue(registrationPage.isLastNameErrorDisplayed(),
                "Last name error should be displayed when field is empty");
        Assert.assertTrue(registrationPage.isEmailErrorDisplayed(),
                "Email error should be displayed when field is empty");
        Assert.assertTrue(registrationPage.isPasswordErrorDisplayed(),
                "Password error should be displayed when field is empty");

        Assert.assertTrue(registrationPage.isAnyValidationErrorDisplayed(),
                "At least one validation error should be displayed");
    }

    @Test(priority = 5, description = "Verify registration fails with invalid email format")
    @Story("Registration Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that system validates email format and shows error for invalid email addresses")
    public void registrationWithInvalidEmailTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        registrationPage.register(
                "male",
                "John",
                "Doe",
                "invalidemail",
                "Password123",
                "Password123"
        );

        Assert.assertTrue(registrationPage.isEmailErrorDisplayed(),
                "Email error should be displayed for invalid email format");

        String emailError = registrationPage.getEmailErrorMessage();
        Assert.assertNotNull(emailError,
                "Email error message should not be null for invalid format");
    }

    @Test(priority = 6, description = "Verify registration fails with password mismatch")
    @Story("Registration Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that password and confirm password fields are validated for matching values")
    public void registrationWithPasswordMismatchTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        String uniqueEmail = generateUniqueEmail();
        registrationPage.register(
                "female",
                "Jane",
                "Smith",
                uniqueEmail,
                "Password123",
                "DifferentPassword123"
        );

        Assert.assertTrue(registrationPage.isConfirmPasswordErrorDisplayed(),
                "Confirm password error should be displayed when passwords don't match");

        String confirmPasswordError = registrationPage.getConfirmPasswordErrorMessage();
        Assert.assertFalse(confirmPasswordError.isEmpty(),
                "Confirm password error message should not be empty");
    }

    @Test(priority = 7, description = "Verify registration fails with short password")
    @Story("Registration Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that password length validation is enforced and appropriate error message is displayed")
    public void registrationWithShortPasswordTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        String uniqueEmail = generateUniqueEmail();
        registrationPage.register(
                "male",
                "John",
                "Doe",
                uniqueEmail,
                "123",
                "123"
        );

        Assert.assertTrue(registrationPage.isPasswordErrorDisplayed(),
                "Password error should be displayed for password shorter than minimum length");

        String errorMsg = registrationPage.getPasswordErrorMessage();
        Assert.assertTrue(errorMsg.contains("6") || errorMsg.toLowerCase().contains("must"),
                "Error message should mention password requirements, but got: " + errorMsg);
    }

    @Test(priority = 8, description = "Verify female gender selection works")
    @Story("User Registration Flow")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that users can successfully register by selecting female gender option")
    public void registrationWithFemaleGenderTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        String uniqueEmail = generateUniqueEmail();
        registrationPage.register(
                "female",
                "Jane",
                "Smith",
                uniqueEmail,
                "Password123",
                "Password123"
        );

        Assert.assertTrue(registrationPage.isRegistrationSuccessful(),
                "Registration with female gender selection should be successful");

        String successMsg = registrationPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Your registration completed"),
                "Success message should be displayed after registration");
    }

    @Test(priority = 9, description = "Verify continue button works after successful registration")
    @Story("Post-Registration Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that users can navigate to homepage using continue button after successful registration")
    public void clickContinueAfterRegistrationTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        String uniqueEmail = generateUniqueEmail();
        registrationPage.register(
                "male",
                "John",
                "Doe",
                uniqueEmail,
                "Password123",
                "Password123"
        );

        Assert.assertTrue(registrationPage.isContinueButtonVisible(),
                "Continue button should be visible after successful registration");

        registrationPage.clickContinueButton();

        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.equals("https://demowebshop.tricentis.com/") ||
                        !currentUrl.contains("/register"),
                "Should redirect to home page after clicking continue, but got: " + currentUrl);
    }

    @Test(priority = 10, description = "Verify registration with only required fields")
    @Story("User Registration Flow")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that users can successfully register by providing only mandatory fields without selecting gender")
    public void registrationWithMinimumFieldsTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        String uniqueEmail = generateUniqueEmail();
        registrationPage.enterFirstName("Min");
        registrationPage.enterLastName("Fields");
        registrationPage.enterEmail(uniqueEmail);
        registrationPage.enterPassword("Password123");
        registrationPage.enterConfirmPassword("Password123");
        registrationPage.clickRegisterButton();

        Assert.assertTrue(registrationPage.isRegistrationSuccessful(),
                "Registration should work successfully with only required fields (without gender selection)");

        String successMsg = registrationPage.getSuccessMessage();
        Assert.assertNotNull(successMsg,
                "Success message should not be null");
    }

    @Test(priority = 11, description = "Verify registration page title is correct")
    @Story("Registration Page UI")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that registration page displays correct page title")
    public void verifyRegistrationPageTitleTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        String pageTitle = registrationPage.getPageTitle();
        Assert.assertNotNull(pageTitle,
                "Page title should not be null");
        Assert.assertTrue(pageTitle.toLowerCase().contains("register"),
                "Page title should contain 'register', but got: " + pageTitle);
    }

    @Test(priority = 12, description = "Verify special characters in name fields")
    @Story("Registration Validation")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify system behavior when special characters are entered in name fields")
    public void registrationWithSpecialCharactersTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        String uniqueEmail = generateUniqueEmail();
        registrationPage.register(
                "male",
                "John@#$",
                "Doe123!",
                uniqueEmail,
                "Password123",
                "Password123"
        );

        // System may either accept or reject special characters - verify consistent behavior
        boolean hasError = registrationPage.isAnyValidationErrorDisplayed();
        boolean isSuccessful = registrationPage.isRegistrationSuccessful();

        Assert.assertTrue(hasError || isSuccessful,
                "System should either accept or reject special characters consistently");
    }
}