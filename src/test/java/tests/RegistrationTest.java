package tests;

import base.BaseTest;
import drivers.DriverFactory;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegistrationPage;
import models.RegistrationData;
import org.testng.annotations.DataProvider;
import utils.JsonDataReader;
import java.io.IOException;

/**
 * RegistrationTest - Test suite for user registration functionality
 *
 * REAL-WORLD PURPOSE:
 * - Validates registration feature across multiple scenarios
 * - Covers happy path, negative cases, and edge cases
 * - Data-driven testing for comprehensive coverage
 * - Integrated with Allure for detailed reporting
 *
 * BEST PRACTICES APPLIED:
 * - Page Object Model pattern
 * - Granular Allure steps for detailed reporting
 * - Comprehensive logging with Log4j2
 * - Clear test organization and naming
 * - Security-conscious (no password logging)
 * - Independent, atomic tests
 *
 * @author QA Team
 * @version 2.0
 */

@Epic("E-Commerce Platform")
@Feature("User Registration")
@Owner("QA Team")
@Link(name = "Registration Requirements", url = "https://your-jira.com/browse/REG")
public class RegistrationTest extends BaseTest {

    // BEST PRACTICE: Use Log4j2 logger for debugging and tracking
    private static final Logger logger = LogManager.getLogger(RegistrationTest.class);

    /**
     * Generate unique email for registration tests
     * @return Unique email address with timestamp
     */
    private String generateUniqueEmail() {
        long timestamp = System.currentTimeMillis();
        String email = "testuser" + timestamp + "@example.com";
        logger.debug("Generated unique email: {}", email);
        return email;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 1: Verify Registration Page Elements
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 1,
            description = "Verify registration page loads and elements are visible",
            groups = {"smoke", "ui", "registration"}
    )
    @Story("Registration Page UI")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that registration page loads successfully and all required elements are visible to users")
    @TmsLink("TC-REG-001")
    public void verifyRegistrationPageElementsTest() {

        logger.info("🧪 Starting Test: Verify Registration Page Elements");

        Allure.step("Step 1: Navigate to registration page", () -> {
            logger.debug("Navigating to registration page...");
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            homePage.clickRegister();
            logger.info("✅ Navigated to registration page");
        });

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        Allure.step("Step 2: Verify register button is visible", () -> {
            logger.debug("Checking register button visibility...");
            boolean isVisible = registrationPage.isRegisterButtonVisible();
            
            Allure.parameter("Register Button Visible", isVisible);
            
            Assert.assertTrue(isVisible,
                    "Register button should be visible on registration page");
            logger.info("✅ Register button verified as visible");
        });

        Allure.step("Step 3: Verify all required fields are present", () -> {
            logger.debug("Checking all required fields...");
            boolean allPresent = registrationPage.areAllRequiredFieldsPresent();
            
            Allure.parameter("All Fields Present", allPresent);
            
            Assert.assertTrue(allPresent,
                    "All required registration fields should be present on the page");
            logger.info("✅ All required fields verified as present");
        });

        Allure.step("✅ Test Passed - Registration page elements verified");
        logger.info("✅ Test completed successfully: verifyRegistrationPageElementsTest");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 2: Successful Registration
    // ═══════════════════════════════════════════════════════════════════════════

    @DataProvider(name = "registrationData")
    public Object[][] getData() throws IOException {
        String dataFilePath = "src/test/resources/testdata/registration_data.json";
        return JsonDataReader.getData(dataFilePath, RegistrationData.class);
    }

    @Test(
            priority = 2,
            description = "Verify successful registration with valid data",
            groups = {"smoke", "regression", "registration"},
            dataProvider = "registrationData"
    )
    @Story("User Registration Flow")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that users can successfully register with valid information and receive confirmation message")
    @TmsLink("TC-REG-002")
    public void successfulRegistrationTest(RegistrationData data) {

        logger.info("🧪 Starting Test: Successful Registration with Valid Data - " + data.getDescription());

        Allure.step("Step 1: Navigate to registration page", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            homePage.clickRegister();
            logger.info("Navigated to registration page");
        });

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());
        
        // Handle auto-generated email if specified in data
        String emailToUse = data.getEmail();
        if ("auto_generate".equalsIgnoreCase(emailToUse)) {
            emailToUse = generateUniqueEmail();
        }

        final String finalEmail = emailToUse;

        Allure.step("Step 2: Fill registration form with valid data", () -> {
            logger.info("Filling registration form with valid data");
            
            Allure.parameter("Gender", data.getGender());
            Allure.parameter("First Name", data.getFirstName());
            Allure.parameter("Last Name", data.getLastName());
            Allure.parameter("Email", finalEmail);
            Allure.parameter("Password", "***MASKED***");
            
            registrationPage.register(
                    data.getGender(),
                    data.getFirstName(),
                    data.getLastName(),
                    finalEmail,
                    data.getPassword(),
                    data.getConfirmPassword()
            );
            
            logger.info("✅ Registration form submitted");
        });

        Allure.step("Step 3: Verify registration success", () -> {
            logger.debug("Verifying registration success...");
            boolean isSuccessful = registrationPage.isRegistrationSuccessful();
            
            Allure.parameter("Registration Successful", isSuccessful);
            
            Assert.assertTrue(isSuccessful,
                    "Registration should be successful with valid data");
            logger.info("✅ Registration verified as successful");
        });

        Allure.step("Step 4: Verify success message content", () -> {
            String successMsg = registrationPage.getSuccessMessage();
            
            Allure.parameter("Success Message", successMsg);
            Allure.addAttachment("Success Message", "text/plain", successMsg);
            
            Assert.assertTrue(successMsg.contains("Your registration completed"),
                    "Expected success message to contain 'Your registration completed', but got: " + successMsg);
            logger.info("✅ Success message verified: {}", successMsg);
        });

        Allure.step("✅ Test Passed - User registered successfully");
        logger.info("✅ Test completed successfully: successfulRegistrationTest");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 3: Registration with Existing Email
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 3,
            description = "Verify registration fails with already registered email",
            groups = {"regression", "negative", "registration"}
    )
    @Story("Registration Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that system prevents duplicate registration with an already registered email address")
    @TmsLink("TC-REG-003")
    public void registrationWithExistingEmailTest() {

        logger.info("🧪 Starting Test: Registration with Existing Email");

        Allure.step("Step 1: Navigate to registration page", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            homePage.clickRegister();
            logger.info("Navigated to registration page");
        });

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        Allure.step("Step 2: Attempt registration with existing email", () -> {
            logger.info("Attempting registration with existing email");
            
            Allure.parameter("Email", "testuser@example.com");
            Allure.parameter("Expected Result", "Email error displayed");
            
            registrationPage.register(
                    "male",
                    "Test",
                    "User",
                    "testuser@example.com",
                    "Password123",
                    "Password123"
            );
            
            logger.info("Registration submitted with existing email");
        });

        Allure.step("Step 3: Verify email error is displayed", () -> {
            boolean errorDisplayed = registrationPage.isEmailErrorDisplayed();
            
            Allure.parameter("Email Error Displayed", errorDisplayed);
            
            Assert.assertTrue(errorDisplayed,
                    "Email error should be displayed when attempting to register with existing email");
            logger.info("✅ Email error verified as displayed");
        });

        Allure.step("Step 4: Verify error message content", () -> {
            String emailError = registrationPage.getEmailErrorMessage();
            
            Allure.parameter("Error Message", emailError);
            Allure.addAttachment("Email Error Message", "text/plain", emailError);
            
            Assert.assertFalse(emailError.isEmpty(),
                    "Email error message should not be empty");
            logger.info("✅ Error message verified: {}", emailError);
        });

        Allure.step("✅ Test Passed - Duplicate email prevented");
        logger.info("✅ Test completed successfully: registrationWithExistingEmailTest");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 4: Registration with Empty Fields
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 4,
            description = "Verify registration fails with empty fields",
            groups = {"regression", "negative", "registration"}
    )
    @Story("Registration Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that all required field validations are triggered when attempting to register with empty fields")
    @TmsLink("TC-REG-004")
    public void registrationWithEmptyFieldsTest() {

        logger.info("🧪 Starting Test: Registration with Empty Fields");

        Allure.step("Step 1: Navigate to registration page", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            homePage.clickRegister();
            logger.info("Navigated to registration page");
        });

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        Allure.step("Step 2: Click register button without filling fields", () -> {
            logger.info("Clicking register button with empty fields");
            registrationPage.clickRegisterButton();
            logger.info("Register button clicked");
        });

        Allure.step("Step 3: Verify first name error is displayed", () -> {
            boolean errorDisplayed = registrationPage.isFirstNameErrorDisplayed();
            Allure.parameter("First Name Error", errorDisplayed);
            
            Assert.assertTrue(errorDisplayed,
                    "First name error should be displayed when field is empty");
            logger.info("✅ First name error verified");
        });

        Allure.step("Step 4: Verify last name error is displayed", () -> {
            boolean errorDisplayed = registrationPage.isLastNameErrorDisplayed();
            Allure.parameter("Last Name Error", errorDisplayed);
            
            Assert.assertTrue(errorDisplayed,
                    "Last name error should be displayed when field is empty");
            logger.info("✅ Last name error verified");
        });

        Allure.step("Step 5: Verify email error is displayed", () -> {
            boolean errorDisplayed = registrationPage.isEmailErrorDisplayed();
            Allure.parameter("Email Error", errorDisplayed);
            
            Assert.assertTrue(errorDisplayed,
                    "Email error should be displayed when field is empty");
            logger.info("✅ Email error verified");
        });

        Allure.step("Step 6: Verify password error is displayed", () -> {
            boolean errorDisplayed = registrationPage.isPasswordErrorDisplayed();
            Allure.parameter("Password Error", errorDisplayed);
            
            Assert.assertTrue(errorDisplayed,
                    "Password error should be displayed when field is empty");
            logger.info("✅ Password error verified");
        });

        Allure.step("Step 7: Verify at least one validation error exists", () -> {
            boolean hasError = registrationPage.isAnyValidationErrorDisplayed();
            Allure.parameter("Has Validation Errors", hasError);
            
            Assert.assertTrue(hasError,
                    "At least one validation error should be displayed");
            logger.info("✅ Validation errors confirmed");
        });

        Allure.step("✅ Test Passed - Empty field validation working");
        logger.info("✅ Test completed successfully: registrationWithEmptyFieldsTest");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 5: Registration with Invalid Email
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 5,
            description = "Verify registration fails with invalid email format",
            groups = {"regression", "negative", "registration"}
    )
    @Story("Registration Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that system validates email format and shows error for invalid email addresses")
    @TmsLink("TC-REG-005")
    public void registrationWithInvalidEmailTest() {

        logger.info("🧪 Starting Test: Registration with Invalid Email Format");

        Allure.step("Step 1: Navigate to registration page", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            homePage.clickRegister();
        });

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        Allure.step("Step 2: Fill form with invalid email format", () -> {
            logger.info("Filling form with invalid email");
            
            Allure.parameter("Email", "invalidemail");
            Allure.parameter("Expected Result", "Email validation error");
            
            registrationPage.register(
                    "male",
                    "John",
                    "Doe",
                    "invalidemail",
                    "Password123",
                    "Password123"
            );
        });

        Allure.step("Step 3: Verify email error is displayed", () -> {
            boolean errorDisplayed = registrationPage.isEmailErrorDisplayed();
            Allure.parameter("Email Error Displayed", errorDisplayed);
            
            Assert.assertTrue(errorDisplayed,
                    "Email error should be displayed for invalid email format");
            logger.info("✅ Email error verified");
        });

        Allure.step("Step 4: Verify error message is not null", () -> {
            String emailError = registrationPage.getEmailErrorMessage();
            Allure.addAttachment("Email Error", "text/plain", emailError);
            
            Assert.assertNotNull(emailError,
                    "Email error message should not be null for invalid format");
            logger.info("✅ Error message: {}", emailError);
        });

        Allure.step("✅ Test Passed - Invalid email format rejected");
        logger.info("✅ Test completed successfully: registrationWithInvalidEmailTest");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 6: Registration with Password Mismatch
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 6,
            description = "Verify registration fails with password mismatch",
            groups = {"regression", "negative", "registration"}
    )
    @Story("Registration Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that password and confirm password fields are validated for matching values")
    @TmsLink("TC-REG-006")
    public void registrationWithPasswordMismatchTest() {

        logger.info("🧪 Starting Test: Registration with Password Mismatch");

        Allure.step("Step 1: Navigate to registration page", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            homePage.clickRegister();
        });

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());
        String uniqueEmail = generateUniqueEmail();

        Allure.step("Step 2: Fill form with mismatched passwords", () -> {
            logger.info("Filling form with mismatched passwords");
            
            Allure.parameter("Email", uniqueEmail);
            Allure.parameter("Password", "***MASKED***");
            Allure.parameter("Confirm Password", "***DIFFERENT***");
            
            registrationPage.register(
                    "female",
                    "Jane",
                    "Smith",
                    uniqueEmail,
                    "Password123",
                    "DifferentPassword123"
            );
        });

        Allure.step("Step 3: Verify confirm password error is displayed", () -> {
            boolean errorDisplayed = registrationPage.isConfirmPasswordErrorDisplayed();
            Allure.parameter("Confirm Password Error", errorDisplayed);
            
            Assert.assertTrue(errorDisplayed,
                    "Confirm password error should be displayed when passwords don't match");
            logger.info("✅ Password mismatch error verified");
        });

        Allure.step("Step 4: Verify error message content", () -> {
            String confirmPasswordError = registrationPage.getConfirmPasswordErrorMessage();
            Allure.addAttachment("Error Message", "text/plain", confirmPasswordError);
            
            Assert.assertFalse(confirmPasswordError.isEmpty(),
                    "Confirm password error message should not be empty");
            logger.info("✅ Error message: {}", confirmPasswordError);
        });

        Allure.step("✅ Test Passed - Password mismatch detected");
        logger.info("✅ Test completed successfully: registrationWithPasswordMismatchTest");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 7: Registration with Short Password
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 7,
            description = "Verify registration fails with short password",
            groups = {"regression", "negative", "registration"}
    )
    @Story("Registration Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that password length validation is enforced and appropriate error message is displayed")
    @TmsLink("TC-REG-007")
    public void registrationWithShortPasswordTest() {

        logger.info("🧪 Starting Test: Registration with Short Password");

        Allure.step("Step 1: Navigate to registration page", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            homePage.clickRegister();
        });

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());
        String uniqueEmail = generateUniqueEmail();

        Allure.step("Step 2: Fill form with short password", () -> {
            logger.info("Filling form with short password");
            
            Allure.parameter("Email", uniqueEmail);
            Allure.parameter("Password Length", 3);
            
            registrationPage.register(
                    "male",
                    "John",
                    "Doe",
                    uniqueEmail,
                    "123",
                    "123"
            );
        });

        Allure.step("Step 3: Verify password error is displayed", () -> {
            boolean errorDisplayed = registrationPage.isPasswordErrorDisplayed();
            Allure.parameter("Password Error Displayed", errorDisplayed);
            
            Assert.assertTrue(errorDisplayed,
                    "Password error should be displayed for password shorter than minimum length");
            logger.info("✅ Password error verified");
        });

        Allure.step("Step 4: Verify error message mentions requirements", () -> {
            String errorMsg = registrationPage.getPasswordErrorMessage();
            Allure.addAttachment("Password Error", "text/plain", errorMsg);
            
            Assert.assertTrue(errorMsg.contains("6") || errorMsg.toLowerCase().contains("must"),
                    "Error message should mention password requirements, but got: " + errorMsg);
            logger.info("✅ Error message verified: {}", errorMsg);
        });

        Allure.step("✅ Test Passed - Short password rejected");
        logger.info("✅ Test completed successfully: registrationWithShortPasswordTest");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 8: Registration with Female Gender
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 8,
            description = "Verify female gender selection works",
            groups = {"regression", "registration"}
    )
    @Story("User Registration Flow")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that users can successfully register by selecting female gender option")
    @TmsLink("TC-REG-008")
    public void registrationWithFemaleGenderTest() {

        logger.info("🧪 Starting Test: Registration with Female Gender");

        Allure.step("Step 1: Navigate to registration page", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            homePage.clickRegister();
        });

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());
        String uniqueEmail = generateUniqueEmail();

        Allure.step("Step 2: Fill form with female gender selected", () -> {
            logger.info("Filling form with female gender");
            
            Allure.parameter("Gender", "female");
            Allure.parameter("Email", uniqueEmail);
            
            registrationPage.register(
                    "female",
                    "Jane",
                    "Smith",
                    uniqueEmail,
                    "Password123",
                    "Password123"
            );
        });

        Allure.step("Step 3: Verify registration is successful", () -> {
            boolean isSuccessful = registrationPage.isRegistrationSuccessful();
            Allure.parameter("Registration Successful", isSuccessful);
            
            Assert.assertTrue(isSuccessful,
                    "Registration with female gender selection should be successful");
            logger.info("✅ Registration successful");
        });

        Allure.step("Step 4: Verify success message", () -> {
            String successMsg = registrationPage.getSuccessMessage();
            Allure.addAttachment("Success Message", "text/plain", successMsg);
            
            Assert.assertTrue(successMsg.contains("Your registration completed"),
                    "Success message should be displayed after registration");
            logger.info("✅ Success message verified");
        });

        Allure.step("✅ Test Passed - Female gender registration successful");
        logger.info("✅ Test completed successfully: registrationWithFemaleGenderTest");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 9: Continue Button After Registration
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 9,
            description = "Verify continue button works after successful registration",
            groups = {"regression", "registration"}
    )
    @Story("Post-Registration Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that users can navigate to homepage using continue button after successful registration")
    @TmsLink("TC-REG-009")
    public void clickContinueAfterRegistrationTest() {

        logger.info("🧪 Starting Test: Continue Button After Registration");

        Allure.step("Step 1: Navigate to registration page", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            homePage.clickRegister();
        });

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());
        String uniqueEmail = generateUniqueEmail();

        Allure.step("Step 2: Complete registration successfully", () -> {
            logger.info("Completing registration");
            Allure.parameter("Email", uniqueEmail);
            
            registrationPage.register(
                    "male",
                    "John",
                    "Doe",
                    uniqueEmail,
                    "Password123",
                    "Password123"
            );
        });

        Allure.step("Step 3: Verify continue button is visible", () -> {
            boolean isVisible = registrationPage.isContinueButtonVisible();
            Allure.parameter("Continue Button Visible", isVisible);
            
            Assert.assertTrue(isVisible,
                    "Continue button should be visible after successful registration");
            logger.info("✅ Continue button verified");
        });

        Allure.step("Step 4: Click continue button", () -> {
            logger.info("Clicking continue button");
            registrationPage.clickContinueButton();
        });

        Allure.step("Step 5: Verify navigation to home page", () -> {
            String currentUrl = DriverFactory.getDriver().getCurrentUrl();
            Allure.parameter("Current URL", currentUrl);
            Allure.addAttachment("URL After Continue", "text/plain", currentUrl);
            
            Assert.assertTrue(currentUrl.equals("https://demowebshop.tricentis.com/") ||
                            !currentUrl.contains("/register"),
                    "Should redirect to home page after clicking continue, but got: " + currentUrl);
            logger.info("✅ Navigation verified: {}", currentUrl);
        });

        Allure.step("✅ Test Passed - Continue button navigation works");
        logger.info("✅ Test completed successfully: clickContinueAfterRegistrationTest");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 10: Registration with Minimum Fields
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 10,
            description = "Verify registration with only required fields",
            groups = {"regression", "registration"}
    )
    @Story("User Registration Flow")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that users can successfully register by providing only mandatory fields without selecting gender")
    @TmsLink("TC-REG-010")
    public void registrationWithMinimumFieldsTest() {

        logger.info("🧪 Starting Test: Registration with Minimum Fields");

        Allure.step("Step 1: Navigate to registration page", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            homePage.clickRegister();
        });

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());
        String uniqueEmail = generateUniqueEmail();

        Allure.step("Step 2: Fill only required fields (no gender)", () -> {
            logger.info("Filling only required fields");
            
            Allure.parameter("Gender", "Not selected");
            Allure.parameter("Email", uniqueEmail);
            
            registrationPage.enterFirstName("Min");
            registrationPage.enterLastName("Fields");
            registrationPage.enterEmail(uniqueEmail);
            registrationPage.enterPassword("Password123");
            registrationPage.enterConfirmPassword("Password123");
        });

        Allure.step("Step 3: Submit registration", () -> {
            registrationPage.clickRegisterButton();
            logger.info("Registration submitted");
        });

        Allure.step("Step 4: Verify registration is successful", () -> {
            boolean isSuccessful = registrationPage.isRegistrationSuccessful();
            Allure.parameter("Registration Successful", isSuccessful);
            
            Assert.assertTrue(isSuccessful,
                    "Registration should work successfully with only required fields (without gender selection)");
            logger.info("✅ Registration successful");
        });

        Allure.step("Step 5: Verify success message exists", () -> {
            String successMsg = registrationPage.getSuccessMessage();
            Allure.addAttachment("Success Message", "text/plain", successMsg);
            
            Assert.assertNotNull(successMsg,
                    "Success message should not be null");
            logger.info("✅ Success message verified");
        });

        Allure.step("✅ Test Passed - Minimum fields registration successful");
        logger.info("✅ Test completed successfully: registrationWithMinimumFieldsTest");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 11: Verify Registration Page Title
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 11,
            description = "Verify registration page title is correct",
            groups = {"smoke", "ui", "registration"}
    )
    @Story("Registration Page UI")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that registration page displays correct page title")
    @TmsLink("TC-REG-011")
    public void verifyRegistrationPageTitleTest() {

        logger.info("🧪 Starting Test: Verify Registration Page Title");

        Allure.step("Step 1: Navigate to registration page", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            homePage.clickRegister();
        });

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());

        Allure.step("Step 2: Get page title", () -> {
            String pageTitle = registrationPage.getPageTitle();
            
            Allure.parameter("Page Title", pageTitle);
            Allure.addAttachment("Page Title", "text/plain", pageTitle);
            
            logger.info("Page title: {}", pageTitle);
        });

        Allure.step("Step 3: Verify title is not null", () -> {
            String pageTitle = registrationPage.getPageTitle();
            
            Assert.assertNotNull(pageTitle,
                    "Page title should not be null");
            logger.info("✅ Title verified as not null");
        });

        Allure.step("Step 4: Verify title contains 'register'", () -> {
            String pageTitle = registrationPage.getPageTitle();
            
            Assert.assertTrue(pageTitle.toLowerCase().contains("register"),
                    "Page title should contain 'register', but got: " + pageTitle);
            logger.info("✅ Title content verified");
        });

        Allure.step("✅ Test Passed - Page title verified");
        logger.info("✅ Test completed successfully: verifyRegistrationPageTitleTest");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 12: Registration with Special Characters
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 12,
            description = "Verify special characters in name fields",
            groups = {"regression", "negative", "registration"}
    )
    @Story("Registration Validation")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify system behavior when special characters are entered in name fields")
    @TmsLink("TC-REG-012")
    public void registrationWithSpecialCharactersTest() {

        logger.info("🧪 Starting Test: Registration with Special Characters");

        Allure.step("Step 1: Navigate to registration page", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            homePage.clickRegister();
        });

        RegistrationPage registrationPage = new RegistrationPage(DriverFactory.getDriver());
        String uniqueEmail = generateUniqueEmail();

        Allure.step("Step 2: Fill form with special characters in names", () -> {
            logger.info("Filling form with special characters");
            
            Allure.parameter("First Name", "John@#$");
            Allure.parameter("Last Name", "Doe123!");
            Allure.parameter("Email", uniqueEmail);
            
            registrationPage.register(
                    "male",
                    "John@#$",
                    "Doe123!",
                    uniqueEmail,
                    "Password123",
                    "Password123"
            );
        });

        Allure.step("Step 3: Check for validation errors", () -> {
            boolean hasError = registrationPage.isAnyValidationErrorDisplayed();
            Allure.parameter("Has Validation Errors", hasError);
            logger.info("Validation errors present: {}", hasError);
        });

        Allure.step("Step 4: Check for successful registration", () -> {
            boolean isSuccessful = registrationPage.isRegistrationSuccessful();
            Allure.parameter("Registration Successful", isSuccessful);
            logger.info("Registration successful: {}", isSuccessful);
        });

        Allure.step("Step 5: Verify consistent behavior", () -> {
            boolean hasError = registrationPage.isAnyValidationErrorDisplayed();
            boolean isSuccessful = registrationPage.isRegistrationSuccessful();
            
            Allure.parameter("Final Result", hasError ? "Rejected" : "Accepted");
            
            Assert.assertTrue(hasError || isSuccessful,
                    "System should either accept or reject special characters consistently");
            logger.info("✅ Consistent behavior verified");
        });

        Allure.step("✅ Test Passed - Special character handling verified");
        logger.info("✅ Test completed successfully: registrationWithSpecialCharactersTest");
    }
}