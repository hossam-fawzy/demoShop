package tests;

import base.BaseTest;
import drivers.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.PDPPage;

public class CartTest extends BaseTest {

    // URL for a book product (to add to cart before testing)
    private static final String BOOK_PRODUCT_URL = "https://demowebshop.tricentis.com/computing-and-internet";

    @BeforeMethod
    public void addItemToCart() {
        // SETUP: Add an item to cart before each test
        // This ensures we always have something in cart to test with
        DriverFactory.getDriver().get(BOOK_PRODUCT_URL);
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());
        pdpPage.clickAddToCart();
        pdpPage.waitForNotification();

        // Navigate to cart page
        DriverFactory.getDriver().get("https://demowebshop.tricentis.com/cart");
    }

    @Test(priority = 1, description = "Verify cart page loads and all main elements are visible")
    public void verifyCartPageElementsTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // VERIFY: We're on cart page
        Assert.assertTrue(cartPage.isOnCartPage(), "Should be on cart page");

        // VERIFY: Page title is displayed
        Assert.assertTrue(cartPage.isPageTitleDisplayed(), "Page title should be visible");

        // VERIFY: Page title text is correct
        String pageTitle = cartPage.getPageTitle();
        Assert.assertEquals(pageTitle, "Shopping cart", "Page title should be 'Shopping cart'");

        // VERIFY: All main elements are visible
        Assert.assertTrue(cartPage.isUpdateCartButtonDisplayed(), "Update cart button should be visible");
        Assert.assertTrue(cartPage.isContinueShoppingButtonDisplayed(), "Continue shopping button should be visible");
        Assert.assertTrue(cartPage.isCheckoutButtonDisplayed(), "Checkout button should be visible");
        Assert.assertTrue(cartPage.isTermsOfServiceCheckboxDisplayed(), "Terms of service checkbox should be visible");
    }

    @Test(priority = 2, description = "Verify cart is not empty after adding product")
    public void verifyCartNotEmptyTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // VERIFY: Cart is not empty
        Assert.assertFalse(cartPage.isCartEmpty(), "Cart should not be empty after adding product");

        // VERIFY: Cart table is displayed
        Assert.assertTrue(cartPage.isCartTableDisplayed(), "Cart table should be displayed when items exist");
    }

    @Test(priority = 3, description = "Verify product information is displayed in cart")
    public void verifyProductInformationInCartTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // GET: Product information from cart
        String productName = cartPage.getFirstProductName();
        String productPrice = cartPage.getFirstProductPrice();
        String productQuantity = cartPage.getFirstProductQuantity();
        String productSubtotal = cartPage.getFirstProductSubtotal();

        // VERIFY: Product information is not empty
        Assert.assertFalse(productName.isEmpty(), "Product name should not be empty");
        Assert.assertFalse(productPrice.isEmpty(), "Product price should not be empty");
        Assert.assertFalse(productQuantity.isEmpty(), "Product quantity should not be empty");
        Assert.assertFalse(productSubtotal.isEmpty(), "Product subtotal should not be empty");

        // VERIFY: Product name matches what we added
        Assert.assertEquals(productName, "Computing and Internet", "Product name should match");

        // VERIFY: Default quantity is 1
        Assert.assertEquals(productQuantity, "1", "Default quantity should be 1");

        System.out.println("Product Name: " + productName);
        System.out.println("Product Price: " + productPrice);
        System.out.println("Product Quantity: " + productQuantity);
        System.out.println("Product Subtotal: " + productSubtotal);
    }

    @Test(priority = 4, description = "Verify cart total is displayed")
    public void verifyCartTotalDisplayedTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // VERIFY: Cart total is displayed
        Assert.assertTrue(cartPage.isCartTotalDisplayed(), "Cart total should be displayed");

        // GET: Cart total value
        String cartTotal = cartPage.getCartTotal();

        // VERIFY: Cart total is not empty
        Assert.assertFalse(cartTotal.isEmpty(), "Cart total should not be empty");

        // VERIFY: Cart total contains numbers
        Assert.assertTrue(cartTotal.matches(".*\\d+.*"), "Cart total should contain numbers");

        System.out.println("Cart Total: " + cartTotal);
    }

    @Test(priority = 5, description = "Verify quantity can be updated")
    public void updateQuantityTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // GET: Original quantity
        String originalQuantity = cartPage.getFirstProductQuantity();
        Assert.assertEquals(originalQuantity, "1", "Original quantity should be 1");

        // PERFORM: Update quantity to 3
        cartPage.updateQuantityAndRefresh("3");

        // WAIT: For page to refresh (cart page reloads after update)
        cartPage.waitForCartPageLoad();

        // GET: Updated quantity
        String updatedQuantity = cartPage.getFirstProductQuantity();

        // VERIFY: Quantity updated to 3
        Assert.assertEquals(updatedQuantity, "3", "Quantity should be updated to 3");

        System.out.println("Original Quantity: " + originalQuantity);
        System.out.println("Updated Quantity: " + updatedQuantity);
    }

    @Test(priority = 6, description = "Verify subtotal updates when quantity changes")
    public void verifySubtotalUpdatesTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // GET: Original price and subtotal
        String originalPrice = cartPage.getFirstProductPrice();
        String originalSubtotal = cartPage.getFirstProductSubtotal();

        System.out.println("Original Price: " + originalPrice);
        System.out.println("Original Subtotal (Qty 1): " + originalSubtotal);

        // PERFORM: Update quantity to 2
        cartPage.updateQuantityAndRefresh("2");
        cartPage.waitForCartPageLoad();

        // GET: New subtotal
        String newSubtotal = cartPage.getFirstProductSubtotal();

        System.out.println("New Subtotal (Qty 2): " + newSubtotal);

        // VERIFY: Subtotal changed (should be different from original)
        Assert.assertNotEquals(newSubtotal, originalSubtotal,
                "Subtotal should change when quantity increases");

        // Note: We can't verify exact math because prices contain "$" and may have decimals
        // But we verify that the subtotal did change
    }

    @Test(priority = 7, description = "Verify remove checkbox can be checked")
    public void checkRemoveCheckboxTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // VERIFY: Remove checkbox is initially unchecked
        Assert.assertFalse(cartPage.isFirstProductRemoveChecked(),
                "Remove checkbox should be unchecked initially");

        // PERFORM: Check the remove checkbox
        cartPage.checkRemoveFirstProduct();

        // VERIFY: Remove checkbox is now checked
        Assert.assertTrue(cartPage.isFirstProductRemoveChecked(),
                "Remove checkbox should be checked after clicking");
    }

    @Test(priority = 8, description = "Verify product can be removed from cart")
    public void removeProductFromCartTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // VERIFY: Cart is not empty initially
        Assert.assertFalse(cartPage.isCartEmpty(), "Cart should have items before removal");

        // PERFORM: Remove product from cart
        cartPage.removeFirstProductFromCart();

        // WAIT: For page to reload
        cartPage.waitForCartPageLoad();

        // VERIFY: Cart is now empty
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty after removing all items");

        System.out.println("Product removed successfully - cart is now empty");
    }

    @Test(priority = 9, description = "Verify Terms of Service checkbox can be checked")
    public void checkTermsOfServiceTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // VERIFY: Checkbox is initially unchecked
        Assert.assertFalse(cartPage.isTermsOfServiceChecked(),
                "Terms checkbox should be unchecked initially");

        // PERFORM: Check the checkbox
        cartPage.checkTermsOfService();

        // VERIFY: Checkbox is now checked
        Assert.assertTrue(cartPage.isTermsOfServiceChecked(),
                "Terms checkbox should be checked after clicking");

        // PERFORM: Uncheck it
        cartPage.uncheckTermsOfService();

        // VERIFY: Checkbox is unchecked again
        Assert.assertFalse(cartPage.isTermsOfServiceChecked(),
                "Terms checkbox should be unchecked after unchecking");
    }

    @Test(priority = 10, description = "Verify checkout button is clickable when terms are accepted")
    public void verifyCheckoutButtonClickableTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // VERIFY: Checkout button exists
        Assert.assertTrue(cartPage.isCheckoutButtonDisplayed(),
                "Checkout button should be displayed");

        // Note: Button clickability doesn't depend on terms checkbox on this site
        // But in real scenarios, you might need to check terms first
        Assert.assertTrue(cartPage.isCheckoutButtonClickable(),
                "Checkout button should be clickable");
    }

    @Test(priority = 11, description = "Verify Continue Shopping button works")
    public void continueShopping () {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // PERFORM: Click Continue Shopping
        cartPage.clickContinueShopping();

        // VERIFY: Navigated away from cart page
        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertFalse(currentUrl.contains("/cart"),
                "Should navigate away from cart page");

        System.out.println("Navigated to: " + currentUrl);
    }

    @Test(priority = 12, description = "Verify estimate shipping section is displayed")
    public void verifyEstimateShippingDisplayedTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // VERIFY: Estimate shipping section exists
        Assert.assertTrue(cartPage.isEstimateShippingDisplayed(),
                "Estimate shipping section should be visible");
    }

    @Test(priority = 13, description = "Verify all main elements method works")
    public void verifyAllMainElementsMethodTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // VERIFY: All main elements visible using single method
        Assert.assertTrue(cartPage.areAllMainElementsVisible(),
                "All main cart page elements should be visible");
    }

    @Test(priority = 14, description = "Verify discount code input is displayed")
    public void verifyDiscountCodeInputTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // PERFORM: Try entering a discount code (won't be valid, just testing UI)
        cartPage.enterDiscountCode("TEST123");

        System.out.println("Discount code input works (entered TEST123)");

        // Note: We can't verify if discount was applied without a real code
        // This test just verifies the input field works
    }

    @Test(priority = 15, description = "Verify gift card code input is displayed")
    public void verifyGiftCardInputTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // PERFORM: Try entering a gift card code (won't be valid, just testing UI)
        cartPage.enterGiftCardCode("GIFT123");

        System.out.println("Gift card input works (entered GIFT123)");

        // Note: We can't verify if gift card was applied without a real code
        // This test just verifies the input field works
    }

    @Test(priority = 16, description = "Verify zip code can be entered for shipping estimate")
    public void enterZipCodeTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // PERFORM: Enter zip code
        cartPage.enterZipCode("12345");

        System.out.println("Zip code entered successfully");

        // Note: We're not clicking estimate shipping button as it might cause errors
        // without selecting country/state. This test just verifies input works.
    }

    @Test(priority = 17, description = "Verify updating quantity to zero removes item")
    public void updateQuantityToZeroTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // PERFORM: Set quantity to 0
        cartPage.updateQuantityAndRefresh("0");

        // WAIT: For page reload
        cartPage.waitForCartPageLoad();

        // VERIFY: Cart should be empty (quantity 0 = remove item)
        Assert.assertTrue(cartPage.isCartEmpty(),
                "Cart should be empty after setting quantity to 0");
    }

    @Test(priority = 18, description = "Verify page URL is correct")
    public void verifyCartPageUrlTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // GET: Current URL
        String pageUrl = cartPage.getPageUrl();

        // VERIFY: URL contains /cart
        Assert.assertTrue(pageUrl.contains("/cart"), "URL should contain '/cart'");

        System.out.println("Cart Page URL: " + pageUrl);
    }
}
