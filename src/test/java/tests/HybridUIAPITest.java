package tests;

import base.BaseTest;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import org.apache.http.params.CoreConnectionPNames;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import drivers.DriverFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("Hybrid Testing")
@Feature("UI + API Integration")
public class HybridUIAPITest extends BaseTest {



    // ==================================================
    // TEST 1 — UI + API (DemoWebShop)
    // ==================================================
    @Test(priority = 1)
    @Story("Product Verification")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyProductDataConsistency() {

        Allure.step("API call to DemoWebShop", () -> {

            RestAssured.baseURI = "https://demowebshop.tricentis.com";

            Response apiResponse = given()
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("addtocart_31.EnteredQuantity", "1")
                    .post("/addproducttocart/details/31/1")
                    .then()
                    .statusCode(200)
                    .body("success", equalTo(true))
                    .extract()
                    .response();

            Allure.addAttachment("API Response", apiResponse.prettyPrint());
        });

        Allure.step("Validate product page in UI", () -> {
            DriverFactory.getDriver().get("https://demowebshop.tricentis.com/14-1-inch-laptop");

            Assert.assertTrue(
                    DriverFactory.getDriver().getPageSource().contains("14.1-inch Laptop"),
                    "Product name should be visible"
            );
        });
    }
}
