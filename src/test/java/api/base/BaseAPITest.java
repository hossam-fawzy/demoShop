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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;

public class BaseAPITest {

    protected static final Logger logger = LoggerFactory.getLogger(BaseAPITest.class);

    protected static final String BASE_URI = "https://demowebshop.tricentis.com";

    protected RequestSpecification requestSpec;
    protected ResponseSpecification responseSpec;

    @BeforeClass
    public void setupAPI() {
        logger.info("Setting up REST Assured...");

        RestAssured.baseURI = BASE_URI;

        requestSpec = new RequestSpecBuilder()
                .setAccept(ContentType.ANY)        // Accept everything
                .addHeader("User-Agent", "RestAssured-Test")
                .addFilter(new AllureRestAssured())
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        responseSpec = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();

        logger.info("REST Assured ready");
    }
}
