package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    // ≡ Locators for Cart Page Elements

    // Page Title & Header
    private final By pageTitle = By.xpath("//div[@class='page-title']/h1");
    private final By orderSummary = By.xpath("//div[@class='order-summary-content']");

    // Cart Table Elements
    private final By cartTable = By.xpath("//table[@class='cart']");
    private final By removeFromCartCheckbox = By.xpath("//input[@name='removefromcart']");
    private final By productName = By.xpath("//a[@class='product-name']");
    private final By unitPrice = By.xpath("//span[@class='product-unit-price']");
    private final By quantityInput = By.xpath("//input[@class='qty-input']");
    private final By subtotal = By.xpath("//span[@class='product-subtotal']");

    // Specific product locators (for first item in cart)
    private final By firstProductName = By.xpath("(//a[@class='product-name'])[1]");
    private final By firstProductPrice = By.xpath("(//span[@class='product-unit-price'])[1]");
    private final By firstProductQuantity = By.xpath("(//input[@class='qty-input'])[1]");
    private final By firstProductSubtotal = By.xpath("(//span[@class='product-subtotal'])[1]");
    private final By firstProductRemoveCheckbox = By.xpath("(//input[@name='removefromcart'])[1]");

    // Action Buttons
    private final By updateCartButton = By.xpath("//input[@name='updatecart']");
    private final By continueShoppingButton = By.xpath("//input[@name='continueshopping']");
    private final By checkoutButton = By.xpath("//button[@id='checkout']");

    // Estimate Shipping Section
    private final By estimateShippingSection = By.xpath("//div[@class='estimate-shipping']");
    private final By countryDropdown = By.id("CountryId");
    private final By stateDropdown = By.id("StateProvinceId");
    private final By zipCodeInput = By.id("ZipPostalCode");
    private final By estimateShippingButton = By.xpath("//input[@name='estimateshipping']");

    // Discount & Gift Card Section
    private final By discountCodeInput = By.xpath("//input[@name='discountcouponcode']");
    private final By applyDiscountButton = By.xpath("//input[@name='applydiscountcouponcode']");
    private final By giftCardCodeInput = By.xpath("//input[@name='giftcardcouponcode']");
    private final By applyGiftCardButton = By.xpath("//input[@name='applygiftcardcouponcode']");

    // Order Totals Section
    private final By cartTotalLabel = By.xpath("//span[normalize-space()='Sub-Total:']");
    private final By cartTotalValue = By.xpath("//span[@class='value-summary']//strong");
    private final By termsOfServiceCheckbox = By.id("termsofservice");

    // Empty Cart Message
    private final By emptyCartMessage = By.xpath("//div[@class='order-summary-content']");

    // Success/Error Messages
    private final By updateSuccessMessage = By.xpath("//div[@class='bar-notification success']");
    private final By errorMessage = By.xpath("//div[@class='message-error']");

    // ≡ Constructor
    public CartPage(WebDriver driver) {
        super(driver);
    }

    // Alternative: No-argument constructor
    public CartPage() {
        super();
    }

    // ≡ Page Verification Methods

    /**
     * Check if we're on the cart page
     * @return boolean - true if on cart page
     */
    public boolean isOnCartPage() {
        String url = getCurrentUrl();
        return url.contains("/cart");
    }

    /**
     * Get page title text
     * @return String - page title (should be "Shopping cart")
     */
    public String getPageTitle() {
        return getText(pageTitle);
    }

    /**
     * Check if page title is displayed
     * @return boolean - true if visible
     */
    public boolean isPageTitleDisplayed() {
        return isVisible(pageTitle);
    }

    // ≡ Cart Content Methods

    /**
     * Check if cart is empty
     * @return boolean - true if cart is empty
     */
    public boolean isCartEmpty() {
        try {
            String message = getText(emptyCartMessage);
            return message.contains("Your Shopping Cart is empty!");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if cart table is displayed (means cart has items)
     * @return boolean - true if cart has items
     */
    public boolean isCartTableDisplayed() {
        return isVisible(cartTable);
    }

    /**
     * Get first product name in cart
     * @return String - product name
     */
    public String getFirstProductName() {
        return getText(firstProductName);
    }

    /**
     * Get first product price
     * @return String - product price
     */
    public String getFirstProductPrice() {
        return getText(firstProductPrice);
    }

    /**
     * Get first product quantity
     * @return String - quantity value
     */
    public String getFirstProductQuantity() {
        return getInputValue(firstProductQuantity);
    }

    /**
     * Get first product subtotal
     * @return String - subtotal amount
     */
    public String getFirstProductSubtotal() {
        return getText(firstProductSubtotal);
    }

    // ≡ Quantity Management Methods

    /**
     * Update quantity for first product
     * @param newQuantity - new quantity value
     */
    public void updateFirstProductQuantity(String newQuantity) {
        clear(firstProductQuantity);
        write(firstProductQuantity, newQuantity);
    }

    /**
     * Click Update Cart button
     * Call this after changing quantities
     */
    public void clickUpdateCart() {
        click(updateCartButton);
    }

    /**
     * Update quantity and click update cart button
     * @param newQuantity - new quantity to set
     */
    public void updateQuantityAndRefresh(String newQuantity) {
        updateFirstProductQuantity(newQuantity);
        clickUpdateCart();
    }

    // ≡ Remove Item Methods

    /**
     * Check the remove checkbox for first product
     */
    public void checkRemoveFirstProduct() {
        click(firstProductRemoveCheckbox);
    }

    /**
     * Remove first product from cart
     * Checks remove checkbox and clicks update cart
     */
    public void removeFirstProductFromCart() {
        checkRemoveFirstProduct();
        clickUpdateCart();
    }

    /**
     * Check if remove checkbox is selected for first product
     * @return boolean - true if checked
     */
    public boolean isFirstProductRemoveChecked() {
        return isSelected(firstProductRemoveCheckbox);
    }

    // ≡ Action Button Methods

    /**
     * Click Continue Shopping button
     * Returns to homepage or last visited category
     */
    public void clickContinueShopping() {
        click(continueShoppingButton);
    }

    /**
     * Click Checkout button to proceed to checkout
     */
    public void clickCheckout() {
        click(checkoutButton);
    }

    /**
     * Check Terms of Service checkbox
     * Required before checkout
     */
    public void checkTermsOfService() {
        if (!isSelected(termsOfServiceCheckbox)) {
            click(termsOfServiceCheckbox);
        }
    }

    /**
     * Uncheck Terms of Service checkbox
     */
    public void uncheckTermsOfService() {
        if (isSelected(termsOfServiceCheckbox)) {
            click(termsOfServiceCheckbox);
        }
    }

    /**
     * Check if Terms of Service checkbox is selected
     * @return boolean - true if checked
     */
    public boolean isTermsOfServiceChecked() {
        return isSelected(termsOfServiceCheckbox);
    }

    /**
     * Accept terms and proceed to checkout
     * Checks terms checkbox and clicks checkout button
     */
    public void acceptTermsAndCheckout() {
        checkTermsOfService();
        clickCheckout();
    }

    // ≡ Order Total Methods

    /**
     * Get cart total value
     * @return String - total cart value
     */
    public String getCartTotal() {
        return getText(cartTotalValue);
    }

    /**
     * Check if cart total is displayed
     * @return boolean - true if visible
     */
    public boolean isCartTotalDisplayed() {
        return isVisible(cartTotalValue);
    }

    // ≡ Discount & Gift Card Methods

    /**
     * Enter discount code
     * @param discountCode - discount code to apply
     */
    public void enterDiscountCode(String discountCode) {
        write(discountCodeInput, discountCode);
    }

    /**
     * Click Apply Discount button
     */
    public void clickApplyDiscount() {
        click(applyDiscountButton);
    }

    /**
     * Apply discount code
     * Enters code and clicks apply
     * @param discountCode - discount code
     */
    public void applyDiscountCode(String discountCode) {
        enterDiscountCode(discountCode);
        clickApplyDiscount();
    }

    /**
     * Enter gift card code
     * @param giftCardCode - gift card code to apply
     */
    public void enterGiftCardCode(String giftCardCode) {
        write(giftCardCodeInput, giftCardCode);
    }

    /**
     * Click Apply Gift Card button
     */
    public void clickApplyGiftCard() {
        click(applyGiftCardButton);
    }

    /**
     * Apply gift card code
     * Enters code and clicks apply
     * @param giftCardCode - gift card code
     */
    public void applyGiftCardCode(String giftCardCode) {
        enterGiftCardCode(giftCardCode);
        clickApplyGiftCard();
    }

    // ≡ Estimate Shipping Methods

    /**
     * Check if estimate shipping section is displayed
     * @return boolean - true if visible
     */
    public boolean isEstimateShippingDisplayed() {
        return isVisible(estimateShippingSection);
    }

    /**
     * Enter zip code for shipping estimate
     * @param zipCode - zip/postal code
     */
    public void enterZipCode(String zipCode) {
        write(zipCodeInput, zipCode);
    }

    /**
     * Click Estimate Shipping button
     */
    public void clickEstimateShipping() {
        click(estimateShippingButton);
    }

    // ≡ Verification/Validation Methods

    /**
     * Check if Update Cart button is displayed
     * @return boolean - true if visible
     */
    public boolean isUpdateCartButtonDisplayed() {
        return isVisible(updateCartButton);
    }

    /**
     * Check if Continue Shopping button is displayed
     * @return boolean - true if visible
     */
    public boolean isContinueShoppingButtonDisplayed() {
        return isVisible(continueShoppingButton);
    }

    /**
     * Check if Checkout button is displayed
     * @return boolean - true if visible
     */
    public boolean isCheckoutButtonDisplayed() {
        return isVisible(checkoutButton);
    }

    /**
     * Check if checkout button is clickable
     * @return boolean - true if clickable
     */
    public boolean isCheckoutButtonClickable() {
        return isClickable(checkoutButton);
    }

    /**
     * Check if Terms of Service checkbox is displayed
     * @return boolean - true if visible
     */
    public boolean isTermsOfServiceCheckboxDisplayed() {
        return isVisible(termsOfServiceCheckbox);
    }

    /**
     * Check if update success message is displayed
     * @return boolean - true if visible
     */
    public boolean isUpdateSuccessMessageDisplayed() {
        return isVisible(updateSuccessMessage);
    }

    /**
     * Get error message text
     * @return String - error message
     */
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    /**
     * Check if error message is displayed
     * @return boolean - true if visible
     */
    public boolean isErrorMessageDisplayed() {
        return isVisible(errorMessage);
    }

    /**
     * Verify all cart page main elements are visible
     * @return boolean - true if all main elements visible
     */
    public boolean areAllMainElementsVisible() {
        return isPageTitleDisplayed()
                && isUpdateCartButtonDisplayed()
                && isContinueShoppingButtonDisplayed()
                && isCheckoutButtonDisplayed()
                && isTermsOfServiceCheckboxDisplayed();
    }

    // ≡ Helper Methods

    /**
     * Get current page URL
     * @return String - current URL
     */
    public String getPageUrl() {
        return getCurrentUrl();
    }

    /**
     * Wait for cart page to load
     * @return boolean - true if page loaded
     */
    public boolean waitForCartPageLoad() {
        return waitForVisibility(pageTitle, 10);
    }

    /**
     * Scroll to checkout button
     * Useful if button is below the fold
     */
    public void scrollToCheckoutButton() {
        scrollToElement(checkoutButton);
    }

    /**
     * Scroll to Terms of Service checkbox
     */
    public void scrollToTermsOfService() {
        scrollToElement(termsOfServiceCheckbox);
    }
}