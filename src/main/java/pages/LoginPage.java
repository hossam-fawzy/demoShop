package pages;

import base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    // Constructor
    public LoginPage() {
        PageFactory.initElements(driver, this);
    }


    // Locators
    @FindBy(id = "Email")
    private WebElement emailInput;

    @FindBy(id = "Password")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[@value='Log in']")
    private WebElement loginButton;

    @FindBy(xpath = "//span[contains(text(),'Login was unsuccessful. Please correct the errors ')]")
    private WebElement errorMessage;

    // Locator for Logout button
    @FindBy(xpath = "//a[normalize-space()='Log out']")
    private WebElement logoutButton;

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
        try {
            waitForVisibility(logoutButton); // wait until it's visible
            return logoutButton.isDisplayed();
        } catch (Exception e) {
            return false; // if not found or not visible
        }
    }
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

}
