package api.tests;

import api.base.BaseAPITest;
import api.models.UserRequest;
import api.models.UserResponse;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * SampleAPITest - Comprehensive API testing suite
 *
 * REAL-WORLD PURPOSE:
 * - Demonstrates REST API testing with different endpoints
 * - Shows how to use POJOs (models) for request/response handling
 * - Validates both HTML and JSON API responses
 * - Provides examples of CRUD operations via API
 *
 * BEST PRACTICES APPLIED:
 * - Log4j2 for comprehensive logging
 * - Granular Allure steps for detailed reporting
 * - POJO models for type-safe API testing
 * - Proper assertions with clear error messages
 * - Separation of concerns (models, base class, tests)
 *
 * TEST COVERAGE:
 * - HTML endpoint testing (DemoWebShop)
 * - JSON endpoint testing (DemoWebShop cart API)
 * - RESTful API testing with models (JSONPlaceholder)
 * - POST request with serialization/deserialization
 *
 * @author QA Team
 * @version 2.0
 */

// ═══════════════════════════════════════════════════════════════════════════
// ALLURE ANNOTATIONS - Class Level
// ═══════════════════════════════════════════════════════════════════════════

@Epic("API Testing")
@Feature("Public APIs")
@Owner("QA Team")
@Link(name = "API Testing Requirements", url = "https://your-jira.com/browse/API")
public class SampleAPITest extends BaseAPITest {

    // BEST PRACTICE: Use Log4j2 logger for debugging and tracking
    private static final Logger logger = LogManager.getLogger(SampleAPITest.class);

    // API Configuration Constants
    private static final String DEMO_WEBSHOP_BASE_URI = "https://demowebshop.tricentis.com";
    private static final String JSONPLACEHOLDER_BASE_URI = "https://reqres.in/api";

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 1: HTML Endpoint - DemoWebShop Page Load
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 1,
            description = "Verify Demo Web Shop HTML page loads correctly",
            groups = {"api", "smoke", "html"}
    )
    @Story("HTML Endpoints")
    @Severity(SeverityLevel.NORMAL)
    @Description("This test verifies that the DemoWebShop catalog page loads correctly and returns valid HTML content")
    @TmsLink("TC-API-001")
    public void testDemoWebShopPageLoads() {

        logger.info("🧪 Starting Test: DemoWebShop HTML Page Load");

        Allure.step("Step 1: Configure API request for HTML endpoint", () -> {
            logger.debug("Setting base URI: {}", DEMO_WEBSHOP_BASE_URI);
            RestAssured.baseURI = DEMO_WEBSHOP_BASE_URI;

            Allure.parameter("Base URI", DEMO_WEBSHOP_BASE_URI);
            Allure.parameter("Endpoint", "/computing-and-internet");
            Allure.parameter("Expected Content Type", "text/html");

            logger.debug("✅ API request configured");
        });

        Response response = Allure.step("Step 2: Send GET request to catalog page", () -> {
            logger.debug("Sending GET request to /computing-and-internet...");

            Response resp = given()
                    .when()
                    .get("/computing-and-internet")
                    .then()
                    .log().all()
                    .extract()
                    .response();

            logger.debug("✅ GET request sent successfully");
            logger.debug("Response Status Code: {}", resp.getStatusCode());

            return resp;
        });

        Allure.step("Step 3: Validate HTTP status code", () -> {
            int statusCode = response.getStatusCode();

            logger.debug("Validating status code: {}", statusCode);
            Allure.parameter("Expected Status Code", 200);
            Allure.parameter("Actual Status Code", statusCode);

            Assert.assertEquals(statusCode, 200,
                    "HTML page should return status code 200");

            logger.info("✅ Status code validation passed: {}", statusCode);
        });

        Allure.step("Step 4: Validate content type is HTML", () -> {
            String contentType = response.getContentType();

            logger.debug("Validating content type: {}", contentType);
            Allure.parameter("Expected Content Type", "text/html");
            Allure.parameter("Actual Content Type", contentType);

            Assert.assertTrue(contentType.contains("text/html"),
                    "Response should be HTML content type");

            logger.info("✅ Content type validation passed: {}", contentType);
        });

        Allure.step("Step 5: Validate page content contains expected text", () -> {
            String pageContent = response.asString();
            String expectedText = "Computing and Internet";

            logger.debug("Validating page contains text: '{}'", expectedText);
            Allure.parameter("Expected Text", expectedText);
            Allure.parameter("Text Found", pageContent.contains(expectedText));

            Assert.assertTrue(pageContent.contains(expectedText),
                    "Page should contain the correct title: " + expectedText);

            // Attach HTML response to Allure report
            Allure.addAttachment("HTML Response", "text/html", pageContent);

            logger.info("✅ Page content validation passed");
        });

        Allure.step("✅ Test Passed - HTML page loaded successfully");
        logger.info("✅ Test completed successfully: testDemoWebShopPageLoads");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 2: JSON Endpoint - Add to Cart API
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 2,
            description = "Test adding item to cart returns valid JSON response",
            groups = {"api", "smoke", "json", "regression"}
    )
    @Story("JSON Endpoints")
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test verifies that the add-to-cart endpoint returns a valid JSON response with success status")
    @TmsLink("TC-API-002")
    public void testAddToCartAPI() {

        logger.info("🧪 Starting Test: DemoWebShop Add to Cart API");

        Allure.step("Step 1: Configure API request for add-to-cart endpoint", () -> {
            logger.debug("Setting base URI: {}", DEMO_WEBSHOP_BASE_URI);
            RestAssured.baseURI = DEMO_WEBSHOP_BASE_URI;

            Allure.parameter("Base URI", DEMO_WEBSHOP_BASE_URI);
            Allure.parameter("Endpoint", "/addproducttocart/details/31/1");
            Allure.parameter("Product ID", 31);
            Allure.parameter("Quantity", 1);
            Allure.parameter("Content Type", "application/x-www-form-urlencoded");

            logger.debug("✅ API request configured");
        });

        Response response = Allure.step("Step 2: Send POST request to add product to cart", () -> {
            logger.debug("Sending POST request to add product to cart...");

            Response resp = given()
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("addtocart_31.EnteredQuantity", "1")
                    .when()
                    .post("/addproducttocart/details/31/1")
                    .then()
                    .log().all()
                    .extract()
                    .response();

            logger.debug("✅ POST request sent successfully");
            logger.debug("Response Status Code: {}", resp.getStatusCode());

            return resp;
        });

        Allure.step("Step 3: Validate HTTP status code", () -> {
            int statusCode = response.getStatusCode();

            logger.debug("Validating status code: {}", statusCode);
            Allure.parameter("Expected Status Code", 200);
            Allure.parameter("Actual Status Code", statusCode);

            Assert.assertEquals(statusCode, 200,
                    "Add to cart API should return status code 200");

            logger.info("✅ Status code validation passed: {}", statusCode);
        });

        Allure.step("Step 4: Validate content type is JSON", () -> {
            String contentType = response.getContentType();

            logger.debug("Validating content type: {}", contentType);
            Allure.parameter("Expected Content Type", "application/json");
            Allure.parameter("Actual Content Type", contentType);

            Assert.assertTrue(contentType.contains("application/json"),
                    "Response should be JSON content type");

            logger.info("✅ Content type validation passed: {}", contentType);
        });

        Allure.step("Step 5: Validate JSON response body contains success=true", () -> {
            Boolean success = response.jsonPath().getBoolean("success");

            logger.debug("Validating success field: {}", success);
            Allure.parameter("Expected Success", true);
            Allure.parameter("Actual Success", success);

            Assert.assertTrue(success,
                    "API response should indicate success=true");

            logger.info("✅ JSON response validation passed - success: {}", success);
        });

        Allure.step("Step 6: Attach response to Allure report", () -> {
            String jsonResponse = response.prettyPrint();
            Allure.addAttachment("Add To Cart JSON Response", "application/json", jsonResponse);

            logger.debug("Response attached to Allure report");
            logger.debug("Response: {}", jsonResponse);
        });

        Allure.step("✅ Test Passed - Add to cart API works correctly");
        logger.info("✅ Test completed successfully: testAddToCartAPI");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEST 3: RESTful API with Models - Create User (NEW)
    // Demonstrates POJO usage for request/response handling
    // ═══════════════════════════════════════════════════════════════════════════

    @Test(
            priority = 3,
            description = "Create user via API using request/response models",
            groups = {"api", "regression", "models", "crud"}
    )
    @Story("RESTful API with Models")
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test demonstrates creating a user via REST API using POJO models for type-safe request/response handling")
    @TmsLink("TC-API-003")
    public void testCreateUserWithModels() {

        logger.info("🧪 Starting Test: Create User with Models (RESTful API)");

        UserRequest userRequest = Allure.step("Step 1: Build UserRequest object using Builder pattern", () -> {
            logger.debug("Creating UserRequest object...");

            UserRequest request = UserRequest.builder()
                    .name("John Doe")
                    .job("QA Automation Engineer")
                    .build();

            Allure.parameter("User Name", request.getName());
            Allure.parameter("User Job", request.getJob());

            logger.debug("✅ UserRequest created: {}", request);
            logger.debug("Name: {}, Job: {}", request.getName(), request.getJob());

            return request;
        });

        Allure.step("Step 2: Configure API request for user creation", () -> {
            logger.debug("Setting base URI: {}", JSONPLACEHOLDER_BASE_URI);
            RestAssured.baseURI = JSONPLACEHOLDER_BASE_URI;

            Allure.parameter("Base URI", JSONPLACEHOLDER_BASE_URI);
            Allure.parameter("Endpoint", "/users");
            Allure.parameter("HTTP Method", "POST");
            Allure.parameter("Content Type", "application/json");

            logger.debug("✅ API request configured");
        });

        Response response = Allure.step("Step 3: Send POST request with UserRequest body", () -> {
            logger.debug("Sending POST request to create user...");

            Response resp = given()
                    .contentType("application/json")
                    .body(userRequest)  // RestAssured automatically serializes POJO to JSON
                    .when()
                    .post("/users")
                    .then()
                    .log().all()
                    .extract()
                    .response();

            logger.debug("✅ POST request sent successfully");
            logger.debug("Response Status Code: {}", resp.getStatusCode());

            return resp;
        });

        Allure.step("Step 4: Validate HTTP status code", () -> {
            int statusCode = response.getStatusCode();

            logger.debug("Validating status code: {}", statusCode);
            Allure.parameter("Expected Status Code", 201);
            Allure.parameter("Actual Status Code", statusCode);

            Assert.assertEquals(statusCode, 201,
                    "Create user API should return status code 201 (Created)");

            logger.info("✅ Status code validation passed: {}", statusCode);
        });

        UserResponse userResponse = Allure.step("Step 5: Deserialize response to UserResponse model", () -> {
            logger.debug("Deserializing JSON response to UserResponse object...");

            // RestAssured automatically deserializes JSON to POJO
            UserResponse resp = response.as(UserResponse.class);

            logger.debug("✅ Response deserialized successfully");
            logger.debug("UserResponse: {}", resp);

            return resp;
        });

        Allure.step("Step 6: Validate UserResponse contains expected data", () -> {
            logger.debug("Validating UserResponse fields...");

            // Validate name
            Assert.assertEquals(userResponse.getName(), userRequest.getName(),
                    "Response name should match request name");
            logger.debug("✅ Name validated: {}", userResponse.getName());

            // Validate job
            Assert.assertEquals(userResponse.getJob(), userRequest.getJob(),
                    "Response job should match request job");
            logger.debug("✅ Job validated: {}", userResponse.getJob());

            // Validate ID is generated
            Assert.assertNotNull(userResponse.getId(),
                    "Response should contain generated user ID");
            logger.debug("✅ User ID generated: {}", userResponse.getId());

            // Validate createdAt timestamp exists
            Assert.assertNotNull(userResponse.getCreatedAt(),
                    "Response should contain createdAt timestamp");
            logger.debug("✅ CreatedAt timestamp: {}", userResponse.getCreatedAt());

            Allure.parameter("User ID", userResponse.getId());
            Allure.parameter("Created At", userResponse.getCreatedAt());

            logger.info("✅ All UserResponse validations passed");
        });

        Allure.step("Step 7: Attach request and response to Allure report", () -> {
            // Attach request
            Allure.addAttachment("User Request (JSON)", "application/json",
                    String.format("{\"name\": \"%s\", \"job\": \"%s\"}",
                            userRequest.getName(), userRequest.getJob()));

            // Attach response
            Allure.addAttachment("User Response (JSON)", "application/json",
                    response.prettyPrint());

            logger.debug("Request and response attached to Allure report");
        });

        Allure.step("✅ Test Passed - User created successfully using models");
        logger.info("✅ Test completed successfully: testCreateUserWithModels");
    }
}
