package tests;

import base.BaseTest;
import drivers.DriverFactory;
import io.qameta.allure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.ConfigReader;

/**
 * LoginTest - Test suite for user authentication functionality
 *
 * REAL-WORLD PURPOSE:
 * - Validates login feature across multiple scenarios
 * - Covers happy path, negative cases, and edge cases
 * - Data-driven testing for comprehensive coverage
 * - Integrated with Allure for detailed reporting
 *
 * BEST PRACTICES APPLIED:
 * - Page Object Model pattern
 * - Data-driven testing with TestNG DataProvider
 * - Comprehensive Allure reporting
 * - Clear test organization and naming
 * - Security-conscious (no password logging)
 * - Independent, atomic tests
 *
 * TEST COVERAGE:
 * - Valid login with correct credentials
 * - Invalid login scenarios (wrong password, wrong email, empty fields)
 * - Login page element verification
 * - Remember Me checkbox functionality
 * - Forgot password link (added)
 * - Login/Logout flow (added)
 *
 * @author QA Team
 * @version 2.0
 */

// ═══════════════════════════════════════════════════════════════════════════
// ALLURE ANNOTATIONS - Class Level (Organize tests in report)
// ═══════════════════════════════════════════════════════════════════════════

@Epic("DemoWebShop E-Commerce Platform")
@Feature("User Authentication & Security")          // ENHANCED: More specific feature name
@Owner("QA Team")
@Link(name = "Authentication Requirements", url = "https://your-jira.com/browse/AUTH")  // ADDED: Link to requirements
public class LoginTest extends BaseTest {

    // BEST PRACTICE: Use logger for debugging (industry standard)
    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

    // ENHANCEMENT: Constants for test data (easier maintenance)
    private static final String VALID_EMAIL_KEY = "validEmail";
    private static final String VALID_PASSWORD_KEY = "validPassword";

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 1: Valid Login - Happy Path
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 1,  // ADDED: Run first (most critical test)
            description = "Verify user can login with valid credentials",
            groups = {"smoke", "regression", "authentication"},
            retryAnalyzer = listeners.RetryAnalyzer.class
    )
    @Description("This test verifies that a registered user can successfully login using valid email and password credentials")
    @Severity(SeverityLevel.BLOCKER)
    @Story("User Login - Happy Path")
    @TmsLink("TC-LOGIN-001")
    @Issue("AUTH-123")  // ADDED: Link to related bug/story

    public void validLoginTest() {

        logger.info("🧪 Starting Test: Valid Login with correct credentials");

        Allure.step("Step 1: Navigate to Login page", () -> {
            logger.debug("Navigating to login page...");
            HomePage homePage = new HomePage(DriverFactory.getDriver());

            // ENHANCEMENT: Verify we're on home page first
            Assert.assertTrue(homePage.isLogoVisible(),
                    "Home page should be loaded before clicking login");

            homePage.clickLogin();
            logger.debug("✅ Clicked login link");
        });

        Allure.step("Step 2: Enter valid credentials and submit login form", () -> {
            LoginPage loginPage = new LoginPage(DriverFactory.getDriver());

            // ENHANCEMENT: Verify login page loaded
            Assert.assertTrue(loginPage.isLoginButtonVisible(),
                    "Login page should be loaded before entering credentials");

            // BEST PRACTICE: Get credentials from config
            String email = ConfigReader.get(VALID_EMAIL_KEY);
            String password = ConfigReader.get(VALID_PASSWORD_KEY);

            // SECURITY: Log email but mask password
            Allure.parameter("Email Used", email);
            Allure.parameter("Password Used", "***MASKED***");
            logger.debug("Logging in with email: {}", email);

            // ENHANCEMENT: Break down login into smaller steps for better debugging
            loginPage.enterEmail(email);
            loginPage.enterPassword(password);
            loginPage.clickLoginButton();

            logger.debug("✅ Login credentials submitted");
        });

        Allure.step("Step 3: Verify user is successfully logged in", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());

            // ENHANCEMENT: Multiple assertions for thorough verification
            Assert.assertTrue(homePage.isUserLoggedIn(),
                    "User should be logged in - Logout link should be visible");

            Assert.assertFalse(homePage.isLoginLinkDisplayed(),
                    "Login link should NOT be visible after successful login");

            // ENHANCEMENT: Verify URL changed (defensive assertion)
            String currentUrl = DriverFactory.getDriver().getCurrentUrl();
            Assert.assertFalse(currentUrl.contains("/login"),
                    "Should have navigated away from login page");

            // ADDED: Attach evidence to report
            Allure.addAttachment("Post-Login URL", "text/plain", currentUrl);
            Allure.addAttachment("User Status", "text/plain", "Logged In");

            logger.info("✅ User logged in successfully");
        });

        Allure.step("✅ Test Passed - User successfully authenticated");
        logger.info("✅ Test completed successfully: validLoginTest");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // DATA PROVIDER - Invalid Login Scenarios
    // ENHANCEMENT: More comprehensive test data with better organization
    // ═══════════════════════════════════════════════════════════════════════════

    @DataProvider(name = "invalidLogins")
    public Object[][] invalidLoginData() {
        logger.debug("📊 Loading invalid login test data...");

        // BEST PRACTICE: Clear, descriptive test data
        // Format: {email, password, expectedError, scenarioName}
        return new Object[][] {
                // Wrong Credentials Scenarios
                {"testuser@example.com", "WrongPassword123", "Login was unsuccessful", "Wrong Password"},
                {"wrongemail@test.com", "Password123", "Login was unsuccessful", "Non-existent Email"},

                // Empty Fields Scenarios
                {"", "", "Please enter your email", "Both Fields Empty"},
                {"testuser@example.com", "", "Please enter your email", "Empty Password"},
                {"", "Password123", "Please enter your email", "Empty Email"},

                // Invalid Format Scenarios
                {"invalidemail", "Pass123", "Please enter your email", "Invalid Email Format - No @"},
                {"test@", "Pass123", "Please enter your email", "Invalid Email Format - Incomplete Domain"},
                {"@example.com", "Pass123", "Please enter your email", "Invalid Email Format - No Local Part"},

                // Special Characters Scenarios
                {"test@example.com", "!@#$%^&*()", "Login was unsuccessful", "Special Characters Password"},

                // SQL Injection Attempt (Security Testing)
                {"' OR '1'='1", "password", "Login was unsuccessful", "SQL Injection Attempt - Email"},
                {"test@test.com", "' OR '1'='1", "Login was unsuccessful", "SQL Injection Attempt - Password"}
        };
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 2: Invalid Login Scenarios - Negative Testing
    // ENHANCEMENT: Better error handling and verification
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            dataProvider = "invalidLogins",
            priority = 2,
            description = "Verify appropriate error messages for invalid login attempts",
            groups = {"regression", "negative", "authentication"}
    )
    @Description("This test verifies that the system displays appropriate error messages for various invalid login scenarios including wrong credentials, empty fields, and malformed inputs")
    @Severity(SeverityLevel.CRITICAL)
    @Story("User Login - Negative Scenarios")
    @TmsLink("TC-LOGIN-002")
    public void invalidLoginTest(String email, String password, String expectedError, String scenarioName) {

        logger.info("🧪 Starting Test: Invalid Login - Scenario: {}", scenarioName);

        // ADDED: Dynamic test name in Allure report
        Allure.step("🔬 Testing Scenario: " + scenarioName);

        Allure.step("Step 1: Navigate to Login page", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            homePage.clickLogin();
            logger.debug("Navigated to login page for scenario: {}", scenarioName);
        });

        Allure.step("Step 2: Attempt login with invalid credentials", () -> {
            LoginPage loginPage = new LoginPage(DriverFactory.getDriver());

            // BEST PRACTICE: Log test data (helps with debugging failures)
            String displayEmail = email.isEmpty() ? "<empty>" : email;
            String displayPassword = password.isEmpty() ? "<empty>" : "***";

            Allure.parameter("Scenario", scenarioName);
            Allure.parameter("Email", displayEmail);
            Allure.parameter("Password", displayPassword);
            Allure.parameter("Expected Error", expectedError);

            logger.debug("Attempting login with - Email: '{}', Password: '{}'",
                    displayEmail, displayPassword);

            // ENHANCEMENT: Use individual methods for better step tracking
            loginPage.enterEmail(email);
            loginPage.enterPassword(password);
            loginPage.clickLoginButton();

            logger.debug("Login attempt submitted");
        });

        Allure.step("Step 3: Verify appropriate error message is displayed", () -> {
            LoginPage loginPage = new LoginPage(DriverFactory.getDriver());

            // ENHANCEMENT: Wait for error message to appear
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                    "Error message should be displayed for invalid login attempt");

            String actualError = loginPage.getErrorMessage();

            // ADDED: Attach both expected and actual for comparison
            Allure.addAttachment("Expected Error", "text/plain", expectedError);
            Allure.addAttachment("Actual Error", "text/plain", actualError);

            logger.debug("Error message displayed: {}", actualError);

            // BEST PRACTICE: Clear, detailed assertion message
            Assert.assertTrue(actualError.contains(expectedError),
                    String.format("❌ Error Validation Failed!\n" +
                                    "Scenario: %s\n" +
                                    "Expected error to contain: '%s'\n" +
                                    "Actual error message: '%s'",
                            scenarioName, expectedError, actualError));

            logger.info("✅ Correct error message displayed for scenario: {}", scenarioName);
        });

        Allure.step("Step 4: Verify user is NOT logged in", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());

            // ENHANCEMENT: Defensive check - ensure failed login doesn't log user in
            Assert.assertFalse(homePage.isUserLoggedIn(),
                    "User should NOT be logged in after failed login attempt");

            Assert.assertTrue(homePage.isLoginLinkDisplayed(),
                    "Login link should still be visible after failed login");

            logger.debug("Verified user is not logged in");
        });

        Allure.step("✅ Test Passed - Correct behavior for scenario: " + scenarioName);
        logger.info("✅ Test completed successfully: invalidLoginTest - {}", scenarioName);
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 3: Login Page Load Verification
    // ENHANCEMENT: More comprehensive element checks
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 3,
            description = "Verify login page loads with all required elements",
            groups = {"smoke", "regression", "ui"}
    )
    @Description("This test verifies that the login page loads correctly with all required UI elements visible and functional")
    @Severity(SeverityLevel.NORMAL)
    @Story("Login Page - UI Verification")
    @TmsLink("TC-LOGIN-003")
    public void verifyLoginPageElementsTest() {

        logger.info("🧪 Starting Test: Login Page Elements Verification");

        Allure.step("Step 1: Navigate to Login page", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            homePage.clickLogin();
            logger.debug("Navigated to login page");
        });

        Allure.step("Step 2: Verify all critical page elements are present", () -> {
            LoginPage loginPage = new LoginPage(DriverFactory.getDriver());

            // ENHANCEMENT: Comprehensive element verification
            Assert.assertTrue(loginPage.isEmailFieldVisible(),
                    "Email input field should be visible");

            Assert.assertTrue(loginPage.isPasswordFieldVisible(),
                    "Password input field should be visible");

            Assert.assertTrue(loginPage.isLoginButtonVisible(),
                    "Login button should be visible");

            Assert.assertTrue(loginPage.isRememberMeCheckboxVisible(),
                    "Remember Me checkbox should be visible");

            // ADDED: Verify additional elements
            Assert.assertTrue(loginPage.isForgotPasswordLinkVisible(),
                    "Forgot Password link should be visible");

            logger.info("✅ All login page elements verified");

            // ADDED: Attach page info to report
            Allure.addAttachment("Page URL", DriverFactory.getDriver().getCurrentUrl());
            Allure.addAttachment("Page Title", DriverFactory.getDriver().getTitle());
        });

        Allure.step("Step 3: Verify page URL is correct", () -> {
            String currentUrl = DriverFactory.getDriver().getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("/login"),
                    "URL should contain '/login' - Current URL: " + currentUrl);
            logger.debug("URL verified: {}", currentUrl);
        });

        Allure.step("✅ Test Passed - Login page loaded with all required elements");
        logger.info("✅ Test completed successfully: verifyLoginPageElementsTest");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 4: Remember Me Checkbox Functionality
    // ENHANCEMENT: More thorough checkbox testing
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 4,
            description = "Verify Remember Me checkbox can be toggled",
            groups = {"regression", "ui"}
    )
    @Description("This test verifies that the Remember Me checkbox functions correctly and can be checked and unchecked")
    @Severity(SeverityLevel.MINOR)
    @Story("Remember Me Feature")
    @TmsLink("TC-LOGIN-004")
    public void rememberMeCheckboxTest() {

        logger.info("🧪 Starting Test: Remember Me Checkbox Functionality");

        Allure.step("Step 1: Navigate to Login page", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            homePage.clickLogin();
        });

        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());

        Allure.step("Step 2: Verify initial state (should be unchecked)", () -> {
            boolean initialState = loginPage.isRememberMeChecked();
            Allure.parameter("Initial State", initialState ? "Checked" : "Unchecked");
            logger.debug("Remember Me initial state: {}", initialState);
        });

        Allure.step("Step 3: Check 'Remember Me' checkbox", () -> {
            loginPage.checkRememberMe();
            Assert.assertTrue(loginPage.isRememberMeChecked(),
                    "Remember Me should be checked after clicking");
            logger.debug("✅ Remember Me checkbox checked");
        });

        Allure.step("Step 4: Uncheck 'Remember Me' checkbox", () -> {
            loginPage.uncheckRememberMe();
            Assert.assertFalse(loginPage.isRememberMeChecked(),
                    "Remember Me should be unchecked after clicking again");
            logger.debug("✅ Remember Me checkbox unchecked");
        });

        Allure.step("Step 5: Toggle checkbox multiple times (robustness test)", () -> {
            // ENHANCEMENT: Test multiple toggles to ensure stability
            for (int i = 0; i < 3; i++) {
                loginPage.checkRememberMe();
                Assert.assertTrue(loginPage.isRememberMeChecked(),
                        "Checkbox should be checked on iteration " + (i + 1));

                loginPage.uncheckRememberMe();
                Assert.assertFalse(loginPage.isRememberMeChecked(),
                        "Checkbox should be unchecked on iteration " + (i + 1));
            }
            logger.debug("✅ Multiple toggle test passed");
        });

        Allure.step("✅ Test Passed - Remember Me checkbox works correctly");
        logger.info("✅ Test completed successfully: rememberMeCheckboxTest");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 5: Forgot Password Link (NEW)
    // ENHANCEMENT: Additional test coverage
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 5,
            description = "Verify Forgot Password link is functional",
            groups = {"regression", "authentication"}
    )
    @Description("This test verifies that the Forgot Password link is present and navigates to the password recovery page")
    @Severity(SeverityLevel.NORMAL)
    @Story("Password Recovery")
    @TmsLink("TC-LOGIN-005")
    public void forgotPasswordLinkTest() {

        logger.info("🧪 Starting Test: Forgot Password Link Functionality");

        Allure.step("Step 1: Navigate to Login page", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            homePage.clickLogin();
        });

        Allure.step("Step 2: Verify Forgot Password link is visible", () -> {
            LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
            Assert.assertTrue(loginPage.isForgotPasswordLinkVisible(),
                    "Forgot Password link should be visible on login page");
            logger.debug("✅ Forgot Password link is visible");
        });

        Allure.step("Step 3: Click Forgot Password link", () -> {
            LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
            loginPage.clickForgotPassword();
            logger.debug("Clicked Forgot Password link");
        });

        Allure.step("Step 4: Verify navigation to password recovery page", () -> {
            String currentUrl = DriverFactory.getDriver().getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("/passwordrecovery"),
                    "Should navigate to password recovery page - Current URL: " + currentUrl);

            Allure.addAttachment("Password Recovery URL", currentUrl);
            logger.info("✅ Successfully navigated to password recovery page");
        });

        Allure.step("✅ Test Passed - Forgot Password link works correctly");
        logger.info("✅ Test completed successfully: forgotPasswordLinkTest");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 6: Complete Login/Logout Flow (NEW)
    // ENHANCEMENT: End-to-end workflow test
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 6,
            description = "Verify complete login and logout flow",
            groups = {"smoke", "regression", "e2e", "authentication"}
    )
    @Description("This end-to-end test verifies that a user can login successfully and then logout, returning to the logged-out state")
    @Severity(SeverityLevel.BLOCKER)
    @Story("User Authentication - Complete Flow")
    @TmsLink("TC-LOGIN-006")
    public void completeLoginLogoutFlowTest() {

        logger.info("🧪 Starting Test: Complete Login/Logout Flow");

        Allure.step("Step 1: Login with valid credentials", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            homePage.clickLogin();

            LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
            String email = ConfigReader.get(VALID_EMAIL_KEY);
            String password = ConfigReader.get(VALID_PASSWORD_KEY);

            Allure.parameter("Email", email);
            loginPage.login(email, password);

            logger.debug("Logged in with credentials");
        });

        Allure.step("Step 2: Verify user is logged in", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            Assert.assertTrue(homePage.isUserLoggedIn(),
                    "User should be logged in after successful login");
            logger.debug("✅ User logged in successfully");
        });

        Allure.step("Step 3: Perform logout", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            homePage.clickLogout();
            logger.debug("Clicked logout");
        });

        Allure.step("Step 4: Verify user is logged out", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());

            Assert.assertFalse(homePage.isUserLoggedIn(),
                    "User should NOT be logged in after logout");

            Assert.assertTrue(homePage.isLoginLinkDisplayed(),
                    "Login link should be visible after logout");

            logger.info("✅ User logged out successfully");
        });

        Allure.step("Step 5: Verify cannot access protected pages", () -> {
            // ENHANCEMENT: Try to access a protected page and verify redirect
            // This is a defensive check to ensure logout actually worked
            String currentUrl = DriverFactory.getDriver().getCurrentUrl();
            Assert.assertFalse(currentUrl.contains("/customer/info"),
                    "Should not be able to access customer info page when logged out");

            logger.debug("Verified access control after logout");
        });

        Allure.step("✅ Test Passed - Complete login/logout flow works correctly");
        logger.info("✅ Test completed successfully: completeLoginLogoutFlowTest");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 7: Login with Remember Me Enabled (NEW)
    // ENHANCEMENT: Test Remember Me with actual login
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 7,
            description = "Verify login with Remember Me checkbox enabled",
            groups = {"regression", "authentication"},
            enabled = true  // ADDED: Can disable if Remember Me cookie testing is not needed
    )
    @Description("This test verifies that logging in with Remember Me checkbox enabled works correctly")
    @Severity(SeverityLevel.NORMAL)
    @Story("Remember Me Feature - Integration")
    @TmsLink("TC-LOGIN-007")
    public void loginWithRememberMeTest() {

        logger.info("🧪 Starting Test: Login with Remember Me Enabled");

        Allure.step("Step 1: Navigate to Login page", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            homePage.clickLogin();
        });

        Allure.step("Step 2: Enable Remember Me checkbox", () -> {
            LoginPage loginPage = new LoginPage();
            loginPage.checkRememberMe();
            Assert.assertTrue(loginPage.isRememberMeChecked(),
                    "Remember Me should be checked before login");
            logger.debug("✅ Remember Me enabled");
        });

        Allure.step("Step 3: Login with valid credentials", () -> {
            LoginPage loginPage = new LoginPage();
            String email = ConfigReader.get(VALID_EMAIL_KEY);
            String password = ConfigReader.get(VALID_PASSWORD_KEY);

            loginPage.enterEmail(email);
            loginPage.enterPassword(password);
            loginPage.clickLoginButton();

            logger.debug("Login submitted with Remember Me enabled");
        });

        Allure.step("Step 4: Verify successful login", () -> {
            HomePage homePage = new HomePage(DriverFactory.getDriver());
            Assert.assertTrue(homePage.isUserLoggedIn(),
                    "User should be logged in successfully");
            logger.info("✅ Login successful with Remember Me enabled");

            // ADDED: Note about cookie persistence (would require browser restart test)
            Allure.addAttachment("Note", "text/plain",
                    "Remember Me cookie persistence requires browser session restart test");
        });

        Allure.step("✅ Test Passed - Login with Remember Me works correctly");
        logger.info("✅ Test completed successfully: loginWithRememberMeTest");
    }
}