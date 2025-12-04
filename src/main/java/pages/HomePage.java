package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

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

    // ≡ Navigation Actions

    /** Navigate to Login page */
    public void clickLogin() {
        click(loginLink);
    }

    /**
     * Navigate to Register page
     * ADD THIS METHOD - This is what's missing!
     */
    public void clickRegister() {
        click(registerLink);
    }

    /** Click Logout link */
    public void clickLogout() {
        click(logoutLink);
    }

    /** Navigate to Wishlist */
    public void clickWishlist() {
        click(wishlist);
    }

    /** Navigate to Shopping Cart */
    public void clickCart() {
        click(cartLink);
    }

    /** Click on logo to return to home page */
    public void clickLogo() {
        click(logo);
    }

    // ≡ Search Actions

    /**
     * Perform a search - types text and clicks search button
     * @param searchInput - text to search for
     */
    public void search(String searchInput) {
        write(search, searchInput);
        click(searchButton);
    }

    /** Enter search text without clicking search */
    public void enterSearchText(String searchInput) {
        write(search, searchInput);
    }

    /** Click the search button */
    public void clickSearchButton() {
        click(searchButton);
    }

    // ≡ Category Navigation

    /** Navigate to Books category */
    public void clickBooksCategory() {
        click(booksCategory);
    }

    /** Navigate to Computers category */
    public void clickComputersCategory() {
        click(computersCategory);
    }

    /** Navigate to Electronics category */
    public void clickElectronicsCategory() {
        click(electronicsCategory);
    }

    /** Navigate to Apparel & Shoes category */
    public void clickApparelCategory() {
        click(apparelCategory);
    }

    /** Navigate to Digital Downloads category */
    public void clickDigitalCategory() {
        click(digitalCategory);
    }

    /** Navigate to Jewelry category */
    public void clickJewelryCategory() {
        click(jewelryCategory);
    }

    /** Navigate to Gift Cards category */
    public void clickGiftCategory() {
        click(giftCategory);
    }

    // ≡ Verification Methods

    /** Check if user is logged in by checking logout link visibility */
    public boolean isUserLoggedIn() {
        return isVisible(logoutLink);
    }

    /** Check if user is logged out by checking login link visibility */
    public boolean isLoginLinkDisplayed() {
        return isVisible(loginLink);
    }

    /** Check if logout link is displayed */
    public boolean isLogoutDisplayed() {
        return isVisible(logoutLink);
    }

    /** Check if register link is displayed */
    public boolean isRegisterLinkDisplayed() {
        return isVisible(registerLink);
    }

    /** Check if logo is visible on the page */
    public boolean isLogoVisible() {
        return isVisible(logo);
    }

    /** Check if search box is visible */
    public boolean isSearchBoxVisible() {
        return isVisible(search);
    }

    /** Check if cart link is visible */
    public boolean isCartVisible() {
        return isVisible(cartLink);
    }

    /** Check if wishlist is visible */
    public boolean isWishlistVisible() {
        return isVisible(wishlist);
    }

    /** Get the current page title */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /** Get cart item count */
    public String getCartItemCount() {
        return getText(cartLink);
    }
}