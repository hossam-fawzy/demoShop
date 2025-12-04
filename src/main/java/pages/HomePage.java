package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    // Locators
    private By loginLink = By.className("ico-login");
    private By logoutLink = By.className("ico-logout");
    private By registerLink = By.className("ico-register");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Actions
    public void clickLogin() {
        click(loginLink);
    }

    public void clickRegister() {
        click(registerLink);
    }

    public boolean isLogoutVisible() {
        return isVisible(logoutLink);
    }
}
