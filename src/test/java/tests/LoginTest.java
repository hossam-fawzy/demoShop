package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ConfigReader;

public class LoginTest extends BaseTest {

    @Test(description = "Valid login with correct credentials")
    public void validLoginTest() {
        LoginPage loginPage = new LoginPage();
        loginPage.login(ConfigReader.get("validEmail"), ConfigReader.get("validPassword"));
        Assert.assertTrue(loginPage.isLogoutButtonVisible(), "User is not logged in");
    }

    @DataProvider(name = "invalidLogins")
    public Object[][] invalidLoginData() {
        return new Object[][] {
                {"testuser@example.com", "WrongPassword", "Login was unsuccessful"},
                {"", "", "Please enter your email"}
        };
    }

    @Test(dataProvider = "invalidLogins", description = "Invalid login scenarios")
    public void invalidLoginTest(String email, String password, String expectedError) {
        LoginPage loginPage = new LoginPage();
        loginPage.login(email, password);
        Assert.assertTrue(loginPage.getErrorMessage().contains(expectedError),
                "Expected error not displayed");
    }
}
