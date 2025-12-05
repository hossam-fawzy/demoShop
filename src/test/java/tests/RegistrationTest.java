package tests;

import base.BaseTest;
import drivers.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegistrationPage;

public class RegistrationTest extends BaseTest {


    private String generateUniqueEmail() {
        long timestamp = System.currentTimeMillis();
        return "testuser" + timestamp + "@example.com";
    }

    @Test(priority = 1, description = "Verify registration page loads and elements are visible")
    public void verifyRegistrationPageElementsTest() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        Assert.assertTrue(registrationPage.isRegisterButtonVisible(),
                "Register button should be visible");
    }

    @Test(priority = 2, description = "Verify successful registration with valid data")
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
                "Registration should be successful");


        String successMsg = registrationPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Your registration completed"),
                "Success message should confirm registration completed");
    }

    @Test(priority = 3, description = "Verify registration fails with already registered email")
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
                "Email error should be displayed for existing email");
    }

    @Test(priority = 4, description = "Verify registration fails with empty fields")
    public void registrationWithEmptyFieldsTest() {

        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.clickRegister();

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        registrationPage.clickRegisterButton();

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
                "Email error should be displayed for invalid format");
    }

    @Test(priority = 6, description = "Verify registration fails with password mismatch")
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
    }

    @Test(priority = 7, description = "Verify registration fails with short password")
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
                "Password error should be displayed for short password");

        String errorMsg = registrationPage.getPasswordErrorMessage();
        Assert.assertTrue(errorMsg.contains("6") || errorMsg.toLowerCase().contains("must"),
                "Error should mention password requirements");
    }

    @Test(priority = 8, description = "Verify female gender selection works")
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
                "Registration with female gender should be successful");
    }

    @Test(priority = 9, description = "Verify continue button works after successful registration")
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
                "Should redirect to home page after clicking continue");
    }

    @Test(priority = 10, description = "Verify registration with only required fields")
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
                "Registration should work with only required fields");
    }
}