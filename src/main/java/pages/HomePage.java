package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    // ======= LOCATORS =======
    private By loginLink = By.cssSelector("a.ico-login");
    private By logoutLink = By.cssSelector("a.ico-logout");
    private By registerLink = By.cssSelector("a.ico-register");
    private By accountLink = By.cssSelector("a.account");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // ======= ACTIONS =======

    /** Navigate to Login Page */
//    public void clickLogin() {
//        click(loginLink);
//    }
//
//    /** Navigate to Register Page */
//    public void clickRegister() {
//        click(registerLink);
//    }
//
//    /** Check whether logout button is visible */
//    public boolean isLogoutDisplayed() {
//        return isVisible(logoutLink);
//    }
//
//    /** Check if user is logged in by checking the presence of the account link */
//    public boolean isUserLoggedIn() {
//        return isVisible(accountLink);
//    }
}
