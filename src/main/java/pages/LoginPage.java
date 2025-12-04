package pages;

import base.BasePage;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    // Locators (By instead of @FindBy)
    private By emailInput = By.id("Email");
    private By passwordInput = By.id("Password");
    private By loginButton = By.xpath("//input[@value='Log in']");
    private By errorMessage = By.xpath("//span[contains(text(),'Login was unsuccessful')]");
    private By logoutButton = By.xpath("//a[normalize-space()='Log out']");

    // Actions
    public void enterEmail(String email) {
        type(emailInput, email);
    }

    public void enterPassword(String password) {
        type(passwordInput, password);
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
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }
}
