package api.utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * API Helper class with common REST operations
 */
public class APIHelper {

    private static final Logger logger = LoggerFactory.getLogger(APIHelper.class);

    @Step("GET Request to: {endpoint}")
    public static Response get(String endpoint) {
        logger.info("🌐 GET Request: {}", endpoint);

        Response response = given()
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();

        logger.info("✅ Response Status: {}", response.getStatusCode());
        return response;
    }

    @Step("GET Request to: {endpoint} with params")
    public static Response get(String endpoint, Map<String, String> queryParams) {
        logger.info("🌐 GET Request: {} with params: {}", endpoint, queryParams);

        Response response = given()
                .queryParams(queryParams)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();

        logger.info("✅ Response Status: {}", response.getStatusCode());
        return response;
    }

    @Step("POST Request to: {endpoint}")
    public static Response post(String endpoint, Object body) {
        logger.info("🌐 POST Request: {}", endpoint);

        Response response = given()
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();

        logger.info("✅ Response Status: {}", response.getStatusCode());
        return response;
    }

    @Step("PUT Request to: {endpoint}")
    public static Response put(String endpoint, Object body) {
        logger.info("🌐 PUT Request: {}", endpoint);

        Response response = given()
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();

        logger.info("✅ Response Status: {}", response.getStatusCode());
        return response;
    }

    @Step("DELETE Request to: {endpoint}")
    public static Response delete(String endpoint) {
        logger.info("🌐 DELETE Request: {}", endpoint);

        Response response = given()
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();

        logger.info("✅ Response Status: {}", response.getStatusCode());
        return response;
    }

    @Step("PATCH Request to: {endpoint}")
    public static Response patch(String endpoint, Object body) {
        logger.info("🌐 PATCH Request: {}", endpoint);

        Response response = given()
                .body(body)
                .when()
                .patch(endpoint)
                .then()
                .extract()
                .response();

        logger.info("✅ Response Status: {}", response.getStatusCode());
        return response;
    }

    // Validation helpers
    @Step("Validate status code: {expectedStatusCode}")
    public static void validateStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        logger.info("Validating status code: Expected={}, Actual={}", expectedStatusCode, actualStatusCode);

        if (actualStatusCode != expectedStatusCode) {
            logger.error("❌ Status code mismatch!");
            throw new AssertionError(String.format(
                    "Expected status code %d but got %d",
                    expectedStatusCode,
                    actualStatusCode
            ));
        }

        logger.info("✅ Status code validation passed");
    }

    @Step("Extract value from JSON path: {jsonPath}")
    public static <T> T extractValue(Response response, String jsonPath, Class<T> type) {
        T value = response.jsonPath().getObject(jsonPath, type);
        logger.info("📝 Extracted value from '{}': {}", jsonPath, value);
        return value;
    }
}