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

    public String getUrl() {
        return getCurrentUrl();
    }

    // ≡ Navigation Actions


    public void clickLogin() {
        click(loginLink);
    }


    public void clickRegister() {
        click(registerLink);
    }

    public void clickLogout() {
        click(logoutLink);
    }

    public void clickWishlist() {
        click(wishlist);
    }


    public void clickCart() {
        click(cartLink);
    }


    public void clickLogo() {
        click(logo);
    }

    // ≡ Search Actions


    public void search(String searchInput) {
        write(search, searchInput);
        click(searchButton);
    }


    public void enterSearchText(String searchInput) {
        write(search, searchInput);
    }


    public void clickSearchButton() {
        click(searchButton);
    }

    // ≡ Category Navigation


    public void clickBooksCategory() {
        click(booksCategory);
    }


    public void clickComputersCategory() {
        click(computersCategory);
    }


    public void clickElectronicsCategory() {
        click(electronicsCategory);
    }


    public void clickApparelCategory() {
        click(apparelCategory);
    }


    public void clickDigitalCategory() {
        click(digitalCategory);
    }


    public void clickJewelryCategory() {
        click(jewelryCategory);
    }


    public void clickGiftCategory() {
        click(giftCategory);
    }

    // ≡ Verification Methods


    public boolean isUserLoggedIn() {
        return isVisible(logoutLink);
    }


    public boolean isLoginLinkDisplayed() {
        return isVisible(loginLink);
    }


    public boolean isRegisterLinkDisplayed() {
        return isVisible(registerLink);
    }


    public boolean isLogoVisible() {
        return isVisible(logo);
    }


    public boolean isSearchBoxVisible() {
        return isVisible(search);
    }


    public boolean isCartVisible() {
        return isVisible(cartLink);
    }


    public boolean isWishlistVisible() {
        return isVisible(wishlist);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }


}