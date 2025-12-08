package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for Home Page.
 * Handles all home page operations including navigation, search, and category selection.
 * Follows Page Object Model (POM) best practices.
 * 
 * @author QA Team
 * @version 2.0
 */
public class HomePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(HomePage.class);

    // ≡ Locators
    private final By loginLink    = By.xpath("//a[normalize-space()='Log in']");
    private final By registerLink = By.xpath("//a[normalize-space()='Register']");
    private final By logoutLink   = By.xpath("//a[normalize-space()='Log out']");
    private final By cartLink     = By.xpath("//span[normalize-space()='Shopping cart']");
    private final By logo         = By.xpath("//img[@alt='Tricentis Demo Web Shop']");
    private final By wishlist     = By.xpath("//span[normalize-space()='Wishlist']");

    private final By search       = By.xpath("//input[@id='small-searchterms']");
    private final By searchButton = By.xpath("//input[@value='Search']");

    // Category links
    private final By booksCategory       = By.xpath("//ul[@class='top-menu']//a[normalize-space()='Books']");
    private final By computersCategory   = By.xpath("//ul[@class='top-menu']//a[normalize-space()='Computers']");
    private final By electronicsCategory = By.xpath("//ul[@class='top-menu']//a[normalize-space()='Electronics']");
    private final By apparelCategory     = By.xpath("//ul[@class='top-menu']//a[normalize-space()='Apparel & Shoes']");
    private final By digitalCategory     = By.xpath("//ul[@class='top-menu']//a[normalize-space()='Digital downloads']");
    private final By jewelryCategory     = By.xpath("//ul[@class='top-menu']//a[normalize-space()='Jewelry']");
    private final By giftCategory        = By.xpath("//ul[@class='top-menu']//a[normalize-space()='Gift Cards']");

    // ≡ Constructor
    public HomePage(WebDriver driver) {
        super(driver);
    }

    // ALTERNATIVE: No-argument constructor (if you prefer)
    public HomePage() {
        super(); // Uses DriverFactory.getDriver()
    }

    public String getUrl() {
        return getCurrentUrl();
    }

    // ≡ Navigation Actions

    @Step("Navigate to login page")
    public void clickLogin() {
        click(loginLink);
        logger.info("Clicking login button");
    }

    @Step("Navigate to register page")
    public void clickRegister() {
        click(registerLink);
        logger.info("Clicking register button");
    }

    @Step("Logout from application")
    public void clickLogout() {
        click(logoutLink);
        logger.info("Clicking logout button");
    }

    @Step("Open wishlist page")
    public void clickWishlist() {
        click(wishlist);
        logger.info("Clicking wishlist link");
    }

    @Step("Open shopping cart page")
    public void clickCart() {
        click(cartLink);
        logger.info("Clicking cart link");
    }

    @Step("Click on the website logo")
    public void clickLogo() {
        click(logo);
        logger.info("Clicking logo link");
    }


    // ≡ Search Actions

    @Step("Search for '{searchInput}'")
    public void search(String searchInput) {
        write(search, searchInput);
        logger.info("Entering '{}' in search box", searchInput);
        click(searchButton);
        logger.info("Clicking search button");
    }

    @Step("Enter '{searchInput}' in search box")
    public void enterSearchText(String searchInput) {
        write(search, searchInput);
        logger.info("Entering '{}' in search box", searchInput);
    }

    @Step("Click search button")
    public void clickSearchButton() {
        click(searchButton);
        logger.info("Clicking search button");
    }

    // ≡ Category Navigation

    @Step("Click on Books category")
    public void clickBooksCategory() {
        click(booksCategory);
        logger.info("Clicking Books category");
    }

    @Step("Click on Computers category")
    public void clickComputersCategory() {
        click(computersCategory);
        logger.info("Clicking Computers category");
    }

    @Step("Click on Electronics category")
    public void clickElectronicsCategory() {
        click(electronicsCategory);
        logger.info("Clicking Electronics category");
    }

    @Step("Click on Apparel category")
    public void clickApparelCategory() {
        click(apparelCategory);
        logger.info("Clicking Apparel category");
    }

    @Step("Click on Digital category")
    public void clickDigitalCategory() {
        click(digitalCategory);
        logger.info("Clicking Digital category");
    }

    @Step("Click on Jewelry category")
    public void clickJewelryCategory() {
        click(jewelryCategory);
        logger.info("Clicking Jewelry category");
    }

    @Step("Click on Gift category")
    public void clickGiftCategory() {
        click(giftCategory);
        logger.info("Clicking Gift category");
    }

    // ≡ Verification Methods

    @Step("Check if user is logged in")
    public boolean isUserLoggedIn() {
        boolean visible = isVisible(logoutLink);
        logger.info("User logged in: {}", visible);
        return visible;
    }

    @Step("Check if login link is displayed")
    public boolean isLoginLinkDisplayed() {
        boolean visible = isVisible(loginLink);
        logger.info("Login link displayed: {}", visible);
        return visible;
    }

    @Step("Check if register link is displayed")
    public boolean isRegisterLinkDisplayed() {
        boolean visible = isVisible(registerLink);
        logger.info("Register link displayed: {}", visible);
        return visible;
    }

    @Step("Check if logo is visible")
    public boolean isLogoVisible() {
        boolean visible = isVisible(logo);
        logger.info("Logo visible: {}", visible);
        return visible;
    }

    @Step("Check if search box is visible")
    public boolean isSearchBoxVisible() {
        boolean visible = isVisible(search);
        logger.info("Search box visible: {}", visible);
        return visible;
    }

    @Step("Check if cart is visible")
    public boolean isCartVisible() {
        boolean visible = isVisible(cartLink);
        logger.info("Cart visible: {}", visible);
        return visible;
    }

    @Step("Check if wishlist is visible")
    public boolean isWishlistVisible() {
        boolean visible = isVisible(wishlist);
        logger.info("Wishlist visible: {}", visible);
        return visible;
    }

    @Step("Get current page title")
    public String getPageTitle() {
        String title = driver.getTitle();
        logger.info("Current page title: {}", title);
        return title;
    }

}