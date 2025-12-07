package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {

    // Page Title & Header Locators
    private final By pageTitle = By.xpath("//div[@class='page-title']/h1");
    private final By orderSummary = By.xpath("//div[@class='order-summary-content']");

    // Cart Table Elements Locators
    private final By cartTable = By.xpath("//table[@class='cart']");
    private final By removeFromCartCheckbox = By.xpath("//input[@name='removefromcart']");
    private final By productName = By.xpath("//a[@class='product-name']");
    private final By unitPrice = By.xpath("//span[@class='product-unit-price']");
    private final By quantityInput = By.xpath("//input[@class='qty-input']");
    private final By subtotal = By.xpath("//span[@class='product-subtotal']");

    // First Product Specific Locators
    private final By firstProductName = By.xpath("(//a[@class='product-name'])[1]");
    private final By firstProductPrice = By.xpath("(//span[@class='product-unit-price'])[1]");
    private final By firstProductQuantity = By.xpath("(//input[@class='qty-input'])[1]");
    private final By firstProductSubtotal = By.xpath("(//span[@class='product-subtotal'])[1]");
    private final By firstProductRemoveCheckbox = By.xpath("(//input[@name='removefromcart'])[1]");

    // Action Button Locators
    private final By updateCartButton = By.xpath("//input[@name='updatecart']");
    private final By continueShoppingButton = By.xpath("//input[@name='continueshopping']");
    private final By checkoutButton = By.xpath("//button[@id='checkout']");

    // Estimate Shipping Section Locators
    private final By estimateShippingSection = By.xpath("//div[@class='estimate-shipping']");
    private final By countryDropdown = By.id("CountryId");
    private final By stateDropdown = By.id("StateProvinceId");
    private final By zipCodeInput = By.id("ZipPostalCode");
    private final By estimateShippingButton = By.xpath("//input[@name='estimateshipping']");

    // Discount & Gift Card Section Locators
    private final By discountCodeInput = By.xpath("//input[@name='discountcouponcode']");
    private final By applyDiscountButton = By.xpath("//input[@name='applydiscountcouponcode']");
    private final By giftCardCodeInput = By.xpath("//input[@name='giftcardcouponcode']");
    private final By applyGiftCardButton = By.xpath("//input[@name='applygiftcardcouponcode']");

    // Order Totals Section Locators
    private final By cartTotalLabel = By.xpath("//span[normalize-space()='Sub-Total:']");
    private final By cartTotalValue = By.xpath("//span[@class='value-summary']//strong");
    private final By termsOfServiceCheckbox = By.id("termsofservice");

    // Empty Cart Message Locator
    private final By emptyCartMessage = By.xpath("//div[@class='order-summary-content']");

    // Success/Error Message Locators
    private final By updateSuccessMessage = By.xpath("//div[@class='bar-notification success']");
    private final By errorMessage = By.xpath("//div[@class='message-error']");

    // Constructors
    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartPage() {
        super();
    }

    // Page Verification Methods

    @Step("Verify user is on cart page")
    public boolean isOnCartPage() {
        String url = getCurrentUrl();
        return url.contains("/cart");
    }

    @Step("Get cart page title")
    public String getPageTitle() {
        return getText(pageTitle);
    }

    @Step("Check if page title is displayed")
    public boolean isPageTitleDisplayed() {
        return isVisible(pageTitle);
    }

    // Cart Content Methods

    @Step("Check if shopping cart is empty")
    public boolean isCartEmpty() {
        try {
            String message = getText(emptyCartMessage);
            return message.contains("Your Shopping Cart is empty!");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Check if cart table is displayed")
    public boolean isCartTableDisplayed() {
        return isVisible(cartTable);
    }

    @Step("Get first product name from cart")
    public String getFirstProductName() {
        return getText(firstProductName);
    }

    @Step("Get first product price from cart")
    public String getFirstProductPrice() {
        return getText(firstProductPrice);
    }

    @Step("Get first product quantity from cart")
    public String getFirstProductQuantity() {
        return getInputValue(firstProductQuantity);
    }

    @Step("Get first product subtotal from cart")
    public String getFirstProductSubtotal() {
        return getText(firstProductSubtotal);
    }

    // Quantity Management Methods

    @Step("Update first product quantity to: {newQuantity}")
    public void updateFirstProductQuantity(String newQuantity) {
        clear(firstProductQuantity);
        write(firstProductQuantity, newQuantity);
    }

    @Step("Click Update Cart button")
    public void clickUpdateCart() {
        click(updateCartButton);
    }

    @Step("Update quantity to {newQuantity} and refresh cart")
    public void updateQuantityAndRefresh(String newQuantity) {
        updateFirstProductQuantity(newQuantity);
        clickUpdateCart();
    }

    // Remove Item Methods

    @Step("Select remove checkbox for first product")
    public void checkRemoveFirstProduct() {
        click(firstProductRemoveCheckbox);
    }

    @Step("Remove first product from cart")
    public void removeFirstProductFromCart() {
        checkRemoveFirstProduct();
        clickUpdateCart();
    }

    @Step("Check if first product remove checkbox is selected")
    public boolean isFirstProductRemoveChecked() {
        return isSelected(firstProductRemoveCheckbox);
    }

    // Action Button Methods

    @Step("Click Continue Shopping button")
    public void clickContinueShopping() {
        click(continueShoppingButton);
    }

    @Step("Click Checkout button")
    public void clickCheckout() {
        click(checkoutButton);
    }

    @Step("Check Terms of Service checkbox")
    public void checkTermsOfService() {
        if (!isSelected(termsOfServiceCheckbox)) {
            click(termsOfServiceCheckbox);
        }
    }

    @Step("Uncheck Terms of Service checkbox")
    public void uncheckTermsOfService() {
        if (isSelected(termsOfServiceCheckbox)) {
            click(termsOfServiceCheckbox);
        }
    }

    @Step("Verify if Terms of Service is checked")
    public boolean isTermsOfServiceChecked() {
        return isSelected(termsOfServiceCheckbox);
    }

    @Step("Accept terms of service and proceed to checkout")
    public void acceptTermsAndCheckout() {
        checkTermsOfService();
        clickCheckout();
    }

    // Order Total Methods

    @Step("Get cart total value")
    public String getCartTotal() {
        return getText(cartTotalValue);
    }

    @Step("Check if cart total is displayed")
    public boolean isCartTotalDisplayed() {
        return isVisible(cartTotalValue);
    }

    // Discount & Gift Card Methods

    @Step("Enter discount code: {discountCode}")
    public void enterDiscountCode(String discountCode) {
        write(discountCodeInput, discountCode);
    }

    @Step("Click Apply Discount button")
    public void clickApplyDiscount() {
        click(applyDiscountButton);
    }

    @Step("Apply discount code: {discountCode}")
    public void applyDiscountCode(String discountCode) {
        enterDiscountCode(discountCode);
        clickApplyDiscount();
    }

    @Step("Enter gift card code: {giftCardCode}")
    public void enterGiftCardCode(String giftCardCode) {
        write(giftCardCodeInput, giftCardCode);
    }

    @Step("Click Apply Gift Card button")
    public void clickApplyGiftCard() {
        click(applyGiftCardButton);
    }

    @Step("Apply gift card code: {giftCardCode}")
    public void applyGiftCardCode(String giftCardCode) {
        enterGiftCardCode(giftCardCode);
        clickApplyGiftCard();
    }

    // Estimate Shipping Methods

    @Step("Check if estimate shipping section is displayed")
    public boolean isEstimateShippingDisplayed() {
        return isVisible(estimateShippingSection);
    }

    @Step("Enter zip code: {zipCode}")
    public void enterZipCode(String zipCode) {
        write(zipCodeInput, zipCode);
    }

    @Step("Click Estimate Shipping button")
    public void clickEstimateShipping() {
        click(estimateShippingButton);
    }

    // Verification/Validation Methods

    @Step("Check if Update Cart button is displayed")
    public boolean isUpdateCartButtonDisplayed() {
        return isVisible(updateCartButton);
    }

    @Step("Check if Continue Shopping button is displayed")
    public boolean isContinueShoppingButtonDisplayed() {
        return isVisible(continueShoppingButton);
    }

    @Step("Check if Checkout button is displayed")
    public boolean isCheckoutButtonDisplayed() {
        return isVisible(checkoutButton);
    }

    @Step("Check if Checkout button is clickable")
    public boolean isCheckoutButtonClickable() {
        return isClickable(checkoutButton);
    }

    @Step("Check if Terms of Service checkbox is displayed")
    public boolean isTermsOfServiceCheckboxDisplayed() {
        return isVisible(termsOfServiceCheckbox);
    }

    @Step("Check if update success message is displayed")
    public boolean isUpdateSuccessMessageDisplayed() {
        return isVisible(updateSuccessMessage);
    }

    @Step("Get error message text")
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    @Step("Check if error message is displayed")
    public boolean isErrorMessageDisplayed() {
        return isVisible(errorMessage);
    }

    @Step("Verify all main cart page elements are visible")
    public boolean areAllMainElementsVisible() {
        return isPageTitleDisplayed()
                && isUpdateCartButtonDisplayed()
                && isContinueShoppingButtonDisplayed()
                && isCheckoutButtonDisplayed()
                && isTermsOfServiceCheckboxDisplayed();
    }

    // Helper Methods

    @Step("Get current cart page URL")
    public String getPageUrl() {
        return getCurrentUrl();
    }

    @Step("Wait for cart page to load completely")
    public boolean waitForCartPageLoad() {
        return waitForVisibility(pageTitle, 10);
    }

    @Step("Scroll to checkout button")
    public void scrollToCheckoutButton() {
        scrollToElement(checkoutButton);
    }

    @Step("Scroll to Terms of Service checkbox")
    public void scrollToTermsOfService() {
        scrollToElement(termsOfServiceCheckbox);
    }

    // Additional Real-World Utility Methods

    @Step("Wait for cart page to be fully loaded with explicit wait")
    public void waitForPageToLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutButton));
    }

    @Step("Get first product quantity as integer")
    public int getFirstProductQuantityAsInt() {
        try {
            String quantity = getFirstProductQuantity();
            return Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Step("Verify cart has items")
    public boolean hasItemsInCart() {
        return !isCartEmpty() && isCartTableDisplayed();
    }

    @Step("Get cart total as double")
    public double getCartTotalAsDouble() {
        try {
            String total = getCartTotal();
            // Remove currency symbols and parse
            String numericValue = total.replaceAll("[^0-9.]", "");
            return Double.parseDouble(numericValue);
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Step("Verify checkout is available")
    public boolean isCheckoutAvailable() {
        return isCheckoutButtonDisplayed() && isCheckoutButtonClickable();
    }

    @Step("Verify first product price is valid")
    public boolean hasValidFirstProductPrice() {
        String price = getFirstProductPrice();
        return price != null && !price.isEmpty() && price.matches(".*\\d+.*");
    }

    @Step("Clear cart by removing all items")
    public void clearCart() {
        if (hasItemsInCart()) {
            checkRemoveFirstProduct();
            clickUpdateCart();
        }
    }

    @Step("Verify product exists in cart by name: {expectedProductName}")
    public boolean isProductInCart(String expectedProductName) {
        if (!hasItemsInCart()) {
            return false;
        }
        String actualProductName = getFirstProductName();
        return actualProductName.equalsIgnoreCase(expectedProductName);
    }

    @Step("Update quantity and verify cart updates successfully")
    public boolean updateQuantityAndVerify(String newQuantity) {
        String oldQuantity = getFirstProductQuantity();
        updateQuantityAndRefresh(newQuantity);

        // Wait for update to complete
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String updatedQuantity = getFirstProductQuantity();
        return !updatedQuantity.equals(oldQuantity);
    }

    @Step("Verify Terms of Service checkbox is enabled")
    public boolean isTermsOfServiceEnabled() {
        try {
            return driver.findElement(termsOfServiceCheckbox).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Check if discount code input is displayed")
    public boolean isDiscountCodeInputDisplayed() {
        return isVisible(discountCodeInput);
    }

    @Step("Check if gift card input is displayed")
    public boolean isGiftCardInputDisplayed() {
        return isVisible(giftCardCodeInput);
    }

    @Step("Verify all cart functionality elements are present")
    public boolean areAllCartFunctionalitiesAvailable() {
        return isUpdateCartButtonDisplayed() &&
                isContinueShoppingButtonDisplayed() &&
                isCheckoutButtonDisplayed() &&
                isTermsOfServiceCheckboxDisplayed() &&
                isDiscountCodeInputDisplayed() &&
                isGiftCardInputDisplayed();
    }

    @Step("Get first product subtotal as double")
    public double getFirstProductSubtotalAsDouble() {
        try {
            String subtotal = getFirstProductSubtotal();
            String numericValue = subtotal.replaceAll("[^0-9.]", "");
            return Double.parseDouble(numericValue);
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Step("Verify subtotal calculation is correct")
    public boolean isSubtotalCalculationCorrect() {
        try {
            String priceText = getFirstProductPrice();
            String quantityText = getFirstProductQuantity();
            String subtotalText = getFirstProductSubtotal();

            double price = Double.parseDouble(priceText.replaceAll("[^0-9.]", ""));
            int quantity = Integer.parseInt(quantityText);
            double subtotal = Double.parseDouble(subtotalText.replaceAll("[^0-9.]", ""));

            double expectedSubtotal = price * quantity;
            return Math.abs(subtotal - expectedSubtotal) < 0.01; // Allow small floating point difference
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Navigate to checkout without accepting terms")
    public void attemptCheckoutWithoutTerms() {
        uncheckTermsOfService();
        clickCheckout();
    }

    @Step("Wait for update success message to appear")
    public boolean waitForUpdateSuccessMessage() {
        return waitForVisibility(updateSuccessMessage, 5);
    }

    @Step("Wait for error message to appear")
    public boolean waitForErrorMessage() {
        return waitForVisibility(errorMessage, 5);
    }
}