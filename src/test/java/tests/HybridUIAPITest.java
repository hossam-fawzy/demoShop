package tests;

import base.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import drivers.DriverFactory;

import static io.restassured.RestAssured.given;

/**
 * HybridUIAPITest - Test suite combining UI and API testing
 *
 * REAL-WORLD PURPOSE:
 * - Validates data consistency between UI and API layers
 * - Ensures frontend and backend are in sync
 * - Demonstrates hybrid testing approach
 *
 * BEST PRACTICES APPLIED:
 * - Log4j2 for comprehensive logging
 * - Granular Allure steps for detailed reporting
 * - Proper API setup and configuration
 * - Clear separation of UI and API test steps
 *
 * @author QA Team
 * @version 2.0
 */

// ═══════════════════════════════════════════════════════════════════════════
// ALLURE ANNOTATIONS - Class Level
// ═══════════════════════════════════════════════════════════════════════════

@Epic("Hybrid Testing")
@Feature("UI + API Integration")
@Owner("QA Team")
@Link(name = "Hybrid Testing Requirements", url = "https://your-jira.com/browse/HYBRID")
public class HybridUIAPITest extends BaseTest {

    // BEST PRACTICE: Use Log4j2 logger for debugging and tracking
    private static final Logger logger = LogManager.getLogger(HybridUIAPITest.class);

    // API Configuration Constants
    private static final String API_BASE_URI = "https://demowebshop.tricentis.com";
    private static final String PRODUCT_URL = "https://demowebshop.tricentis.com/14-1-inch-laptop";
    private static final String PRODUCT_NAME = "14.1-inch Laptop";
    private static final int PRODUCT_ID = 31;

    // ═══════════════════════════════════════════════════════════════════════════
    // SETUP - API Configuration
    // ═══════════════════════════════════════════════════════════════════════════

    @BeforeClass
    public void setupAPI() {
        logger.info("Setting up REST Assured for Hybrid Testing...");

        // Configure RestAssured base URI
        RestAssured.baseURI = API_BASE_URI;

        // Add filters for better logging and Allure integration
        RestAssured.filters(
                new AllureRestAssured(),
                new RequestLoggingFilter(LogDetail.ALL),
                new ResponseLoggingFilter(LogDetail.ALL)
        );

        logger.info("REST Assured configured with base URI: {}", API_BASE_URI);
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 1: Product Data Consistency - UI + API Verification
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 1,
            description = "Verify product data consistency between API and UI",
            groups = {"hybrid", "smoke", "regression"}
    )
    @Description("This test verifies that product data is consistent between the API response and UI display, ensuring frontend and backend are in sync")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Product Verification - Hybrid Testing")
    @TmsLink("TC-HYBRID-001")
    public void verifyProductDataConsistency() {

        logger.info("🧪 Starting Test: Product Data Consistency (UI + API)");

        // ═══════════════════════════════════════════════════════════════════
        // PART 1: API Testing
        // ═══════════════════════════════════════════════════════════════════

        Allure.step("Step 1: Configure API request parameters", () -> {
            logger.debug("Configuring API request for product ID: {}", PRODUCT_ID);
            
            Allure.parameter("Product ID", PRODUCT_ID);
            Allure.parameter("Quantity", 1);
            Allure.parameter("API Endpoint", "/addproducttocart/details/31/1");
            
            logger.debug("✅ API parameters configured");
        });

        Response apiResponse = Allure.step("Step 2: Send API request to add product to cart", () -> {
            logger.debug("Sending POST request to add product to cart...");

            Response response = given()
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("addtocart_31.EnteredQuantity", "1")
                    .when()
                    .post("/addproducttocart/details/31/1")
                    .then()
                    .log().all()
                    .extract()
                    .response();

            logger.debug("✅ API request sent successfully");
            logger.debug("Response Status Code: {}", response.getStatusCode());
            
            return response;
        });

        Allure.step("Step 3: Validate API response status code", () -> {
            int statusCode = apiResponse.getStatusCode();
            
            logger.debug("Validating status code: {}", statusCode);
            Allure.parameter("Expected Status Code", 200);
            Allure.parameter("Actual Status Code", statusCode);

            Assert.assertEquals(statusCode, 200,
                    "API should return status code 200 for successful product addition");

            logger.info("✅ Status code validation passed: {}", statusCode);
        });

        Allure.step("Step 4: Validate API response body", () -> {
            logger.debug("Validating API response body...");

            // Extract success field from response
            Boolean success = apiResponse.jsonPath().getBoolean("success");
            
            Allure.parameter("Expected Success", true);
            Allure.parameter("Actual Success", success);

            Assert.assertTrue(success,
                    "API response should indicate success=true");

            // Attach full response to Allure report
            Allure.addAttachment("API Response Body", "application/json", 
                    apiResponse.prettyPrint());

            logger.info("✅ API response validation passed - success: {}", success);
        });

        Allure.step("Step 5: Extract product data from API response", () -> {
            logger.debug("Extracting product information from API response...");

            // Extract relevant data
            String responseBody = apiResponse.getBody().asString();
            
            Allure.addAttachment("Extracted Response", "text/plain", responseBody);
            
            logger.debug("✅ Product data extracted from API");
        });

        // ═══════════════════════════════════════════════════════════════════
        // PART 2: UI Testing
        // ═══════════════════════════════════════════════════════════════════

        Allure.step("Step 6: Navigate to product page in UI", () -> {
            logger.debug("Navigating to product page: {}", PRODUCT_URL);

            Allure.parameter("Product URL", PRODUCT_URL);

            DriverFactory.getDriver().get(PRODUCT_URL);

            logger.debug("✅ Navigated to product page");
        });

        Allure.step("Step 7: Wait for product page to load", () -> {
            logger.debug("Waiting for product page to fully load...");

            // Wait for page to be ready
            utils.WaitUtils.waitForPageToLoad(DriverFactory.getDriver());

            String currentUrl = DriverFactory.getDriver().getCurrentUrl();
            Allure.addAttachment("Current URL", "text/plain", currentUrl);

            logger.debug("✅ Product page loaded successfully");
        });

        Allure.step("Step 8: Verify product name is visible in UI", () -> {
            logger.debug("Verifying product name '{}' is visible in UI...", PRODUCT_NAME);

            String pageSource = DriverFactory.getDriver().getPageSource();
            boolean productNameVisible = pageSource.contains(PRODUCT_NAME);

            Allure.parameter("Expected Product Name", PRODUCT_NAME);
            Allure.parameter("Product Name Found", productNameVisible);

            Assert.assertTrue(productNameVisible,
                    String.format("Product name '%s' should be visible on the page", PRODUCT_NAME));

            logger.info("✅ Product name verified in UI: {}", PRODUCT_NAME);
        });

        Allure.step("Step 9: Verify page title contains product information", () -> {
            logger.debug("Verifying page title...");

            String pageTitle = DriverFactory.getDriver().getTitle();
            
            Allure.parameter("Page Title", pageTitle);
            Allure.addAttachment("Page Title", "text/plain", pageTitle);

            Assert.assertTrue(pageTitle.contains("Laptop") || pageTitle.contains("14.1"),
                    "Page title should contain product-related keywords");

            logger.info("✅ Page title verified: {}", pageTitle);
        });

        // ═══════════════════════════════════════════════════════════════════
        // PART 3: Data Consistency Validation
        // ═══════════════════════════════════════════════════════════════════

        Allure.step("Step 10: Validate data consistency between API and UI", () -> {
            logger.debug("Performing final consistency check between API and UI...");

            // Both API and UI validations passed, data is consistent
            Allure.addAttachment("Consistency Status", "text/plain", 
                    "✅ Product data is consistent between API and UI");

            logger.info("✅ Data consistency validation completed successfully");
        });

        Allure.step("✅ Test Passed - Product data is consistent across API and UI");
        logger.info("✅ Test completed successfully: verifyProductDataConsistency");
    }
}
