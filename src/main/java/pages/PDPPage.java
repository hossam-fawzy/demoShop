package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PDPPage extends BasePage {

    // ≡ Locators for Product Details


    private final By productName = By.xpath("//div[@class='product-name']/h1");
    private final By productPrice = By.xpath("//span[@class='price-value-13']");
    private final By productSKU = By.xpath("//span[@class='sku-number']");
    private final By productImage = By.xpath("//img[@class='picture']");

    private final By shortDescription = By.xpath("//div[@class='short-description']");
    private final By fullDescription = By.xpath("//div[@class='full-description']");

    private final By quantityInput = By.xpath("//input[@class='qty-input']");
    private final By increaseQuantityButton = By.xpath("//input[@value='+']");
    private final By decreaseQuantityButton = By.xpath("//input[@value='-']");


    private final By addToCartButton = By.xpath("//input[@id='add-to-cart-button-13']");
    private final By addToWishlistButton = By.xpath("//input[@id='add-to-wishlist-button-13']");
    private final By addToCompareButton = By.xpath("//input[@class='button-2 add-to-compare-list-button']");
    private final By emailFriendButton = By.xpath("//input[@class='button-2 email-a-friend-button']");

    private final By addToCartSuccessMessage = By.xpath("//p[@class='content']");
    private final By notificationBar = By.xpath("//div[@id='bar-notification']");
    private final By closeNotificationButton = By.xpath("//span[@class='close']");

    private final By shoppingCartLinkInNotification = By.xpath("//a[normalize-space()='shopping cart']");

    private final By productRating = By.xpath("//div[@class='product-review-box']");


    private final By breadcrumb = By.xpath("//div[@class='breadcrumb']");
    private final By homeInBreadcrumb = By.xpath("//div[@class='breadcrumb']//a[text()='Home']");
    private final By booksInBreadcrumb = By.xpath("//div[@class='breadcrumb']//a[text()='Books']");


    public PDPPage(WebDriver driver) {
        super(driver);
    }


    public PDPPage() {
        super();
    }


    // ≡ Product Information Methods

    public String getProductName() {
        return getText(productName);
    }

    public String getProductPrice() {
        return getText(productPrice);
    }


    public String getProductSKU() {
        return getText(productSKU);
    }


    public String getShortDescription() {
        return getText(shortDescription);
    }


    public String getFullDescription() {
        return getText(fullDescription);
    }

    // ≡ Quantity Management Methods


    public String getCurrentQuantity() {
        return getInputValue(quantityInput);
    }


    public void setQuantity(String quantity) {
        clear(quantityInput);
        write(quantityInput, quantity);
    }


    public void increaseQuantity() {
        click(increaseQuantityButton);
    }


    public void decreaseQuantity() {
        click(decreaseQuantityButton);
    }


    public void increaseQuantityByTimes(int times) {
        for (int i = 0; i < times; i++) {
            increaseQuantity();
        }
    }


    public void decreaseQuantityByTimes(int times) {
        for (int i = 0; i < times; i++) {
            decreaseQuantity();
        }
    }

    // ≡ Action Button Methods


    public void clickAddToCart() {
        click(addToCartButton);
    }


    public void clickAddToWishlist() {
        click(addToWishlistButton);
    }


    public void clickAddToCompare() {
        click(addToCompareButton);
    }


    public void clickEmailFriend() {
        click(emailFriendButton);
    }


    public void addToCartWithQuantity(String quantity) {
        setQuantity(quantity);
        clickAddToCart();
    }

    // ≡ Notification/Success Message Methods


    public boolean isNotificationBarVisible() {
        return isVisible(notificationBar);
    }


    public String getSuccessMessage() {
        return getText(addToCartSuccessMessage);
    }


    public void closeNotification() {
        click(closeNotificationButton);
    }


    public void clickShoppingCartInNotification() {
        click(shoppingCartLinkInNotification);
    }


    public boolean waitForNotification() {
        return waitForVisibility(notificationBar, 5);
    }

    public boolean waitinvisible() {
        return waitForInvisibility(notificationBar, 5);
    }

    // ≡ Verification Methods


    public boolean isProductNameDisplayed() {
        return isVisible(productName);
    }


    public boolean isProductPriceDisplayed() {
        return isVisible(productPrice);
    }


    public boolean isProductImageDisplayed() {
        return isVisible(productImage);
    }


    public boolean isAddToCartButtonDisplayed() {
        return isVisible(addToCartButton);
    }


    public boolean isAddToWishlistButtonDisplayed() {
        return isVisible(addToWishlistButton);
    }


    public boolean isQuantityInputDisplayed() {
        return isVisible(quantityInput);
    }


    public boolean isAddToCartButtonClickable() {
        return isClickable(addToCartButton);
    }


    public boolean areAllMainElementsVisible() {
        return isProductNameDisplayed()
                && isProductPriceDisplayed()
                && isProductImageDisplayed()
                && isAddToCartButtonDisplayed()
                && isQuantityInputDisplayed();
    }

    // ≡ Breadcrumb Navigation Methods


    public boolean isBreadcrumbVisible() {
        return isVisible(breadcrumb);
    }


    public String getBreadcrumbText() {
        return getText(breadcrumb);
    }


    public void clickHomeInBreadcrumb() {
        click(homeInBreadcrumb);
    }


    public void clickBooksInBreadcrumb() {
        click(booksInBreadcrumb);
    }


    // ≡ Helper Methods

    public String getPageUrl() {
        return getCurrentUrl();
    }


    public boolean isOnProductPage() {
        String url = getCurrentUrl();
        return url.contains("/computing-and-internet") || url.contains("/books/");
    }


    public void scrollToAddToCartButton() {
        scrollToElement(addToCartButton);
    }
}