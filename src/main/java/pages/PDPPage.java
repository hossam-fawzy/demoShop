package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.WaitUtils;

public class PDPPage extends BasePage {

    // Locators for Product Details
    private final By productName = By.xpath("//div[@class='product-name']/h1");
    private final By productPrice = By.xpath("//span[@class='price-value-13']");
    private final By productSKU = By.xpath("//span[@class='value']");
    private final By productImage = By.xpath("//img[@id='main-product-img-13']");

    private final By shortDescription = By.xpath("//div[@class='short-description']");
    private final By fullDescription = By.xpath("//div[@class='full-description']");

    // Quantity Management Locators
    private final By quantityInput = By.xpath("//input[@id='addtocart_13_EnteredQuantity']");
    private final By increaseQuantityButton = By.xpath("//input[@value='+']");
    private final By decreaseQuantityButton = By.xpath("//input[@value='-']");

    // Action Button Locators
    private final By addToCartButton = By.xpath("//input[@id='add-to-cart-button-13']");
    private final By addToWishlistButton = By.xpath("//input[@id='add-to-wishlist-button-13']");
    private final By addToCompareButton = By.xpath("//input[@class='button-2 add-to-compare-list-button']");
    private final By emailFriendButton = By.xpath("//input[@class='button-2 email-a-friend-button']");

    // Notification Locators
    private final By addToCartSuccessMessage = By.xpath("//p[@class='content']");
    private final By notificationBar = By.xpath("//div[@id='bar-notification']");
    private final By closeNotificationButton = By.xpath("//span[@class='close']");
    private final By shoppingCartLinkInNotification = By.xpath("//a[normalize-space()='shopping cart']");

    // Additional Locators
    private final By productRating = By.xpath("//div[@class='product-review-box']");
    private final By breadcrumb = By.xpath("//div[@class='breadcrumb']");
    private final By homeInBreadcrumb = By.xpath("//span[contains(text(),'Home')]");
    private final By booksInBreadcrumb = By.xpath("//span[contains(text(),'Books')]");

    // Constructors
    public PDPPage(WebDriver driver) {
        super(driver);
    }

    public PDPPage() {
        super();
    }

    // Product Information Methods

    @Step("Get product name")
    public String getProductName() {
        return getText(productName);
    }

    @Step("Get product price")
    public String getProductPrice() {
        return getText(productPrice);
    }

    @Step("Get product SKU")
    public String getProductSKU() {
        return getText(productSKU);
    }

    @Step("Get product short description")
    public String getShortDescription() {
        return getText(shortDescription);
    }

    @Step("Get product full description")
    public String getFullDescription() {
        return getText(fullDescription);
    }

    // Quantity Management Methods

    @Step("Get current product quantity")
    public String getCurrentQuantity() {
        return getInputValue(quantityInput);
    }

    @Step("Set product quantity to: {quantity}")
    public void setQuantity(String quantity) {
        clear(quantityInput);
        write(quantityInput, quantity);
    }
    @Step("Is increase button visible")
    public boolean isIncreaseButtonVisible() {
        try {
        isVisible(increaseQuantityButton);
        return false;
        }catch(Exception e) {
            return false;
        }
    }
    @Step("Is decrease button visible")
    public boolean isDecreaseButtonVisible() {
        try {
        isVisible(decreaseQuantityButton);
        return false;
        }catch(Exception e) {
            return false;
        }
    }

    @Step("Increase quantity by 1")
    public void increaseQuantity() {
        click(increaseQuantityButton);
    }

    @Step("Decrease quantity by 1")
    public void decreaseQuantity() {
        click(decreaseQuantityButton);
    }

    @Step("Increase quantity {times} times")
    public void increaseQuantityByTimes(int times) {
        for (int i = 0; i < times; i++) {
            increaseQuantity();
        }
    }

    @Step("Decrease quantity {times} times")
    public void decreaseQuantityByTimes(int times) {
        for (int i = 0; i < times; i++) {
            decreaseQuantity();
        }
    }

    // Action Button Methods

    @Step("Click Add to Cart button")
    public void clickAddToCart() {
        click(addToCartButton);
    }

    @Step("Click Add to Wishlist button")
    public void clickAddToWishlist() {
        click(addToWishlistButton);
    }

    @Step("Click Add to Compare button")
    public void clickAddToCompare() {
        click(addToCompareButton);
    }

    @Step("Click Email a Friend button")
    public void clickEmailFriend() {
        click(emailFriendButton);
    }

    @Step("Add product to cart with quantity: {quantity}")
    public void addToCartWithQuantity(String quantity) {
        setQuantity(quantity);
        clickAddToCart();
    }

    // Notification/Success Message Methods

    @Step("Check if notification bar is visible")
    public boolean isNotificationBarVisible() {
        return isVisible(notificationBar);
    }

    @Step("Get success message from notification")
    public String getSuccessMessage() {
        return getText(addToCartSuccessMessage);
    }

    @Step("Close notification bar")
    public void closeNotification() {
        click(closeNotificationButton);
    }

    @Step("Click shopping cart link in notification")
    public void clickShoppingCartInNotification() {
        click(shoppingCartLinkInNotification);
    }

    @Step("Wait for notification bar to appear")
    public boolean waitForNotification() {
        return WaitUtils.waitForVisibility(driver, notificationBar, 5) != null;
    }

    @Step("Wait for notification bar to disappear")
    public boolean waitForNotificationToDisappear() {
        return WaitUtils.waitForInvisibility(driver, notificationBar, 5);
    }


    // Verification Methods

    @Step("Check if product name is displayed")
    public boolean isProductNameDisplayed() {
        return isVisible(productName);
    }

    @Step("Check if product price is displayed")
    public boolean isProductPriceDisplayed() {
        return isVisible(productPrice);
    }

    @Step("Check if product image is displayed")
    public boolean isProductImageDisplayed() {
        return isVisible(productImage);
    }

    @Step("Check if Add to Cart button is displayed")
    public boolean isAddToCartButtonDisplayed() {
        return isVisible(addToCartButton);
    }

    @Step("Check if Add to Wishlist button is displayed")
    public boolean isAddToWishlistButtonDisplayed() {
        return isVisible(addToWishlistButton);
    }

    @Step("Check if quantity input is displayed")
    public boolean isQuantityInputDisplayed() {
        return isVisible(quantityInput);
    }

    @Step("Check if Add to Cart button is clickable")
    public boolean isAddToCartButtonClickable() {
        return isClickable(addToCartButton);
    }

    @Step("Verify all main product elements are visible")
    public boolean areAllMainElementsVisible() {
        return isProductNameDisplayed()
                && isProductPriceDisplayed()
                && isProductImageDisplayed()
                && isAddToCartButtonDisplayed()
                && isQuantityInputDisplayed();
    }

    // Breadcrumb Navigation Methods

    @Step("Check if breadcrumb is visible")
    public boolean isBreadcrumbVisible() {
        return isVisible(breadcrumb);
    }

    @Step("Get breadcrumb text")
    public String getBreadcrumbText() {
        return getText(breadcrumb);
    }

    @Step("Click Home in breadcrumb")
    public void clickHomeInBreadcrumb() {
        click(homeInBreadcrumb);
    }

    @Step("Click Books in breadcrumb")
    public void clickBooksInBreadcrumb() {
        click(booksInBreadcrumb);
    }

    // Helper Methods

    @Step("Get current page URL")
    public String getPageUrl() {
        return getCurrentUrl();
    }

    @Step("Verify user is on product details page")
    public boolean isOnProductPage() {
        String url = getCurrentUrl();
        return url.contains("/computing-and-internet") || url.contains("/books/");
    }

    @Step("Scroll to Add to Cart button")
    public void scrollToAddToCartButton() {
        scrollToElement(addToCartButton);
    }

    // Additional Real-World Utility Methods

    @Step("Wait for product page to load completely")
    public void waitForPageToLoad() {
        WaitUtils.waitForVisibility(driver, productName, 10);
        WaitUtils.waitForVisibility(driver, addToCartButton, 10);
    }


    @Step("Verify product is available for purchase")
    public boolean isProductAvailable() {
        return isAddToCartButtonDisplayed() && isAddToCartButtonClickable();
    }

    @Step("Get quantity as integer")
    public int getQuantityAsInt() {
        try {
            String quantity = getCurrentQuantity();
            return Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            return 1; // Default quantity
        }
    }

    @Step("Verify Add to Cart button is enabled")
    public boolean isAddToCartButtonEnabled() {
        try {
            return driver.findElement(addToCartButton).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Check if product has rating")
    public boolean isProductRatingDisplayed() {
        return isVisible(productRating);
    }

    @Step("Check if short description is displayed")
    public boolean isShortDescriptionDisplayed() {
        return isVisible(shortDescription);
    }

    @Step("Check if full description is displayed")
    public boolean isFullDescriptionDisplayed() {
        return isVisible(fullDescription);
    }

    @Step("Verify product SKU is displayed")
    public boolean isProductSKUDisplayed() {
        return isVisible(productSKU);
    }

    @Step("Add product to cart and wait for confirmation")
    public void addToCartAndWaitForConfirmation() {
        clickAddToCart();
        waitForNotification();
    }

    @Step("Verify product price is not empty")
    public boolean hasValidPrice() {
        String price = getProductPrice();
        return price != null && !price.isEmpty() && !price.equals("0");
    }

    @Step("Check if all action buttons are visible")
    public boolean areAllActionButtonsVisible() {
        return isAddToCartButtonDisplayed() &&
                isAddToWishlistButtonDisplayed() &&
                isVisible(addToCompareButton) &&
                isVisible(emailFriendButton);
    }

    @Step("Navigate to shopping cart from notification")
    public void navigateToCartFromNotification() {
        waitForNotification();
        clickShoppingCartInNotification();
    }

    @Step("Get page title")
    public String getPageTitle() {
        return driver.getTitle();
    }
}