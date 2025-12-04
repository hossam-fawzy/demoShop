package tests;

import base.BaseTest;
import drivers.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(description = "Valid login with correct credentials")
    public void validLoginTest() {
        LoginPage loginPage = new LoginPage();

        loginPage.login("hossamfawzy553@gmail.com", "01159763964Hho#");

        // Assuming LoginPage has a method to check if Logout button is visible
        Assert.assertTrue(loginPage.isLogoutButtonVisible(), "User is not logged in");

    }

    @Test(description = "Invalid login with incorrect password")
    public void invalidLoginTest() {
        LoginPage loginPage = new LoginPage();

        loginPage.login("testuser@example.com", "WrongPassword");

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Login was unsuccessful"), "Error message not displayed");
    }

    @Test(description = "Invalid login with empty fields")
    public void emptyFieldsLoginTest() {
        LoginPage loginPage = new LoginPage();

        loginPage.login("", "");

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Please enter your email"), "Error message for empty fields not displayed");
    }
}
