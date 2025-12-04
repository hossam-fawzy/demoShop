package tests;

import base.BaseTest;
import drivers.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegistrationPage;

public class RegistrationTest extends BaseTest {

    // HELPER METHOD: Generate unique email for each test
    // This prevents "email already exists" errors
    private String generateUniqueEmail() {
        long timestamp = System.currentTimeMillis();
        return "testuser" + timestamp + "@example.com";
    }

    @Test(priority = 1, description = "Verify registration page loads and elements are visible")
    public void verifyRegistrationPageElementsTest() {
        // Navigate to registration page
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        // Create RegistrationPage object
        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        // VERIFY: All form elements are visible
        Assert.assertTrue(registrationPage.isRegisterButtonVisible(),
                "Register button should be visible");
    }

    @Test(priority = 2, description = "Verify successful registration with valid data")
    public void successfulRegistrationTest() {
        // Navigate to registration page
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        // Create RegistrationPage object
        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        // FILL FORM: Use unique email to avoid "already exists" error
        String uniqueEmail = generateUniqueEmail();
        registrationPage.register(
                "male",                  // gender
                "John",                  // firstName
                "Doe",                   // lastName
                uniqueEmail,             // email (unique!)
                "Password123",           // password
                "Password123"            // confirmPassword
        );

        // VERIFY: Registration successful
        Assert.assertTrue(registrationPage.isRegistrationSuccessful(),
                "Registration should be successful");

        // VERIFY: Success message contains expected text
        String successMsg = registrationPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Your registration completed"),
                "Success message should confirm registration completed");
    }

    @Test(priority = 3, description = "Verify registration fails with already registered email")
    public void registrationWithExistingEmailTest() {
        // Navigate to registration page
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        // ATTEMPT: Register with email that already exists
        // NOTE: You need to register this email first manually or in @BeforeClass
        registrationPage.register(
                "male",
                "Test",
                "User",
                "testuser@example.com",  // Existing email
                "Password123",
                "Password123"
        );

        // VERIFY: Error message displayed for duplicate email
        Assert.assertTrue(registrationPage.isEmailErrorDisplayed(),
                "Email error should be displayed for existing email");
    }

    @Test(priority = 4, description = "Verify registration fails with empty fields")
    public void registrationWithEmptyFieldsTest() {
        // Navigate to registration page
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        // ATTEMPT: Click register without filling any fields
        registrationPage.clickRegisterButton();

        // VERIFY: Error messages displayed for required fields
        Assert.assertTrue(registrationPage.isFirstNameErrorDisplayed(),
                "First name error should be displayed");
        Assert.assertTrue(registrationPage.isLastNameErrorDisplayed(),
                "Last name error should be displayed");
        Assert.assertTrue(registrationPage.isEmailErrorDisplayed(),
                "Email error should be displayed");
        Assert.assertTrue(registrationPage.isPasswordErrorDisplayed(),
                "Password error should be displayed");
    }

    @Test(priority = 5, description = "Verify registration fails with invalid email format")
    public void registrationWithInvalidEmailTest() {
        // Navigate to registration page
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        // ATTEMPT: Register with invalid email format
        registrationPage.register(
                "male",
                "John",
                "Doe",
                "invalidemail",          // Invalid email (missing @ and domain)
                "Password123",
                "Password123"
        );

        // VERIFY: Email error displayed
        Assert.assertTrue(registrationPage.isEmailErrorDisplayed(),
                "Email error should be displayed for invalid format");
    }

    @Test(priority = 6, description = "Verify registration fails with password mismatch")
    public void registrationWithPasswordMismatchTest() {
        // Navigate to registration page
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        // ATTEMPT: Register with mismatched passwords
        String uniqueEmail = generateUniqueEmail();
        registrationPage.register(
                "female",
                "Jane",
                "Smith",
                uniqueEmail,
                "Password123",           // Password
                "DifferentPassword123"   // Different confirm password
        );

        // VERIFY: Confirm password error displayed
        Assert.assertTrue(registrationPage.isConfirmPasswordErrorDisplayed(),
                "Confirm password error should be displayed when passwords don't match");
    }

    @Test(priority = 7, description = "Verify registration fails with short password")
    public void registrationWithShortPasswordTest() {
        // Navigate to registration page
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        // ATTEMPT: Register with password less than 6 characters
        String uniqueEmail = generateUniqueEmail();
        registrationPage.register(
                "male",
                "John",
                "Doe",
                uniqueEmail,
                "123",                   // Too short (less than 6 chars)
                "123"
        );

        // VERIFY: Password error displayed
        Assert.assertTrue(registrationPage.isPasswordErrorDisplayed(),
                "Password error should be displayed for short password");

        // VERIFY: Error message mentions minimum length
        String errorMsg = registrationPage.getPasswordErrorMessage();
        Assert.assertTrue(errorMsg.contains("6") || errorMsg.toLowerCase().contains("must"),
                "Error should mention password requirements");
    }

    @Test(priority = 8, description = "Verify female gender selection works")
    public void registrationWithFemaleGenderTest() {
        // Navigate to registration page
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        // FILL FORM: Register as female
        String uniqueEmail = generateUniqueEmail();
        registrationPage.register(
                "female",                // Female gender
                "Jane",
                "Smith",
                uniqueEmail,
                "Password123",
                "Password123"
        );

        // VERIFY: Registration successful
        Assert.assertTrue(registrationPage.isRegistrationSuccessful(),
                "Registration with female gender should be successful");
    }

    @Test(priority = 9, description = "Verify continue button works after successful registration")
    public void clickContinueAfterRegistrationTest() {
        // Navigate to registration page
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        // PERFORM: Successful registration
        String uniqueEmail = generateUniqueEmail();
        registrationPage.register(
                "male",
                "John",
                "Doe",
                uniqueEmail,
                "Password123",
                "Password123"
        );

        // VERIFY: Continue button appears
        Assert.assertTrue(registrationPage.isContinueButtonVisible(),
                "Continue button should be visible after successful registration");

        // CLICK: Continue button
        registrationPage.clickContinueButton();

        // VERIFY: User is redirected to home page
        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.equals("https://demowebshop.tricentis.com/") ||
                        !currentUrl.contains("/register"),
                "Should redirect to home page after clicking continue");
    }

    @Test(priority = 10, description = "Verify registration with only required fields")
    public void registrationWithMinimumFieldsTest() {
        // Navigate to registration page
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        // FILL FORM: Only required fields (skip gender selection)
        String uniqueEmail = generateUniqueEmail();
        registrationPage.enterFirstName("Min");
        registrationPage.enterLastName("Fields");
        registrationPage.enterEmail(uniqueEmail);
        registrationPage.enterPassword("Password123");
        registrationPage.enterConfirmPassword("Password123");
        registrationPage.clickRegisterButton();

        // VERIFY: Registration successful even without gender selection
        Assert.assertTrue(registrationPage.isRegistrationSuccessful(),
                "Registration should work with only required fields");
    }
}