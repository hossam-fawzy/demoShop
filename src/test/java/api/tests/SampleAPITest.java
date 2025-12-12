package api.tests;

import api.base.BaseAPITest;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("API Testing")
@Feature("Public APIs")
public class SampleAPITest extends BaseAPITest {

    // -------------------------------------------------------------------------
    // 1. VALID TEST (Demo Web Shop — HTML page)
    // -------------------------------------------------------------------------
    @Test(priority = 1, description = "Verify Demo Web Shop HTML page loads")
    @Story("HTML Endpoints")
    @Severity(SeverityLevel.NORMAL)
    @Description("Check that the catalog page loads correctly")
    public void testDemoWebShopPageLoads() {

        logger.info("🧪 Testing GET HTML page from Demo Web Shop...");

        RestAssured.baseURI = "https://demowebshop.tricentis.com";

        Response response = given()
                .when()
                .get("/computing-and-internet")
                .then()
                .statusCode(200)
                .contentType(containsString("text/html"))
                .extract()
                .response();

        logger.info("HTML Page Title Extracted");

        Assert.assertTrue(response.asString().contains("Computing and Internet"),
                "Page should contain the correct title");

        Allure.addAttachment("HTML Response", "text/html", response.asString());
    }

    // -------------------------------------------------------------------------
    // 2. VALID TEST (Demo Web Shop — AJAX JSON endpoint)
    // -------------------------------------------------------------------------
    @Test(priority = 2, description = "Test adding item to cart (JSON response)")
    @Story("JSON Endpoints")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the add-to-cart endpoint returns JSON")
    public void testAddToCartAPI() {

        logger.info("🧪 Testing Demo Web Shop add-to-cart API...");

        RestAssured.baseURI = "https://demowebshop.tricentis.com";

        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("addtocart_31.EnteredQuantity", "1")
                .when()
                .post("/addproducttocart/details/31/1")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("success", equalTo(true))
                .extract()
                .response();

        logger.info("Add-to-cart JSON response: {}", response.prettyPrint());

        Allure.addAttachment("Add To Cart JSON", "application/json", response.prettyPrint());
    }


}
