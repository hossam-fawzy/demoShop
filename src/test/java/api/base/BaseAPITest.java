package api.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;

/**
 * BaseAPITest - Base class for all API test classes
 *
 * REAL-WORLD PURPOSE:
 * - Provides common setup and configuration for REST API testing
 * - Centralizes RestAssured configuration
 * - Ensures consistent logging and reporting across all API tests
 * - Reduces code duplication through inheritance
 *
 * BEST PRACTICES APPLIED:
 * - Log4j2 for comprehensive logging (consistent with UI tests)
 * - Allure integration for detailed API request/response reporting
 * - Request/Response specifications for reusable configurations
 * - Proper logging filters for debugging
 *
 * USAGE:
 * All API test classes should extend this base class to inherit
 * the common setup and configuration.
 *
 * @author QA Team
 * @version 2.0
 */
public class BaseAPITest {

    // BEST PRACTICE: Use Log4j2 logger (consistent with other test classes)
    protected static final Logger logger = LogManager.getLogger(BaseAPITest.class);

    // Default base URI for DemoWebShop API
    protected static final String BASE_URI = "https://demowebshop.tricentis.com";

    // Request specification - can be used by test classes for consistent configuration
    protected RequestSpecification requestSpec;

    // Response specification - can be used by test classes for consistent validation
    protected ResponseSpecification responseSpec;

    /**
     * Setup method executed before all tests in the class.
     * Configures RestAssured with default settings, filters, and specifications.
     */
    @BeforeClass
    public void setupAPI() {
        logger.info("═══════════════════════════════════════════════════════════");
        logger.info("Setting up REST Assured for API Testing...");
        logger.info("═══════════════════════════════════════════════════════════");

        // Set default base URI for RestAssured
        RestAssured.baseURI = BASE_URI;
        logger.info("Base URI configured: {}", BASE_URI);

        // Build request specification with common settings
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setAccept(ContentType.ANY)        // Accept any content type (HTML, JSON, XML, etc.)
                .addHeader("User-Agent", "RestAssured-Test")  // Identify requests as test traffic
                .addFilter(new AllureRestAssured())           // Integrate with Allure reporting
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))   // Log all request details
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))  // Log all response details
                .build();

        logger.debug("Request specification configured with:");
        logger.debug("  - Accept: ANY content type");
        logger.debug("  - User-Agent: RestAssured-Test");
        logger.debug("  - Filters: Allure, Request Logging, Response Logging");

        // Build response specification with logging
        responseSpec = new ResponseSpecBuilder()
                .log(LogDetail.ALL)  // Log all response details
                .build();

        logger.debug("Response specification configured with full logging");

        logger.info("✅ REST Assured setup completed successfully");
        logger.info("═══════════════════════════════════════════════════════════");
    }

    /**
     * Get the configured request specification.
     * Test classes can use this for consistent request configuration.
     *
     * @return RequestSpecification with pre-configured settings
     */
    protected RequestSpecification getRequestSpec() {
        return requestSpec;
    }

    /**
     * Get the configured response specification.
     * Test classes can use this for consistent response validation.
     *
     * @return ResponseSpecification with pre-configured settings
     */
    protected ResponseSpecification getResponseSpec() {
        return responseSpec;
    }
}
