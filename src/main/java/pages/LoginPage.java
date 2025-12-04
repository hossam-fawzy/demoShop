package pages;

import base.BasePage;
import drivers.DriverFactory;
import org.openqa.selenium.By;
import utils.ConfigReader;

public class LoginPage extends BasePage {



    // Locators (By instead of @FindBy)
    private By emailInput = By.id("Email");
    private By passwordInput = By.id("Password");
    private By loginButton = By.xpath("//input[@value='Log in']");
    private By errorMessage = By.xpath("//span[contains(text(),'Login was unsuccessful')]");
    private By logoutButton = By.xpath("//a[normalize-space()='Log out']");
    private By login = By.xpath("//a[normalize-space()='Log in']");



    // Actions
    public void onLogin() {
        click(login);
    }

    public void enterEmail(String email) {
        write(emailInput, email);
    }

    public void enterPassword(String password) {
        write(passwordInput, password);
    }

    public void clickLogin() {
        click(loginButton);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isLogoutButtonVisible() {
        return isVisible(logoutButton);
    }

    public void login(String email, String password) {
        onLogin();
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }
}
