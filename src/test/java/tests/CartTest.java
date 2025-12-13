package tests;

import base.BaseTest;
import drivers.DriverFactory;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.PDPPage;

@Epic("E-Commerce Platform")
@Feature("Shopping Cart")
public class CartTest extends BaseTest {

    private static final String BOOK_PRODUCT_URL = "https://demowebshop.tricentis.com/computing-and-internet";
    private models.CartData cartData;

    @BeforeMethod
    public void addItemToCart() {
        // Load test data
        try {
            java.util.List<models.CartData> dataList = utils.JsonDataReader.readData("src/test/resources/testdata/cart_data.json", models.CartData.class);
            this.cartData = dataList.get(0);
        } catch (java.io.IOException e) {
             throw new RuntimeException("Failed to load cart data", e);
        }

        DriverFactory.getDriver().get(BOOK_PRODUCT_URL);
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());
        pdpPage.clickAddToCart();
        pdpPage.waitForNotification();

        DriverFactory.getDriver().get("https://demowebshop.tricentis.com/cart");
    }

    @Test(priority = 1, description = "Verify cart page loads and all main elements are visible", groups = {"smoke", "ui", "cart", "critical"})
    @Story("Cart Page UI")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that shopping cart page loads successfully with all essential elements visible including buttons, checkboxes, and totals")
    public void verifyCartPageElementsTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        Assert.assertTrue(cartPage.isOnCartPage(),
                "Expected to be on cart page, but URL does not contain '/cart'");

        Assert.assertTrue(cartPage.isPageTitleDisplayed(),
                "Page title should be visible on cart page");

        String pageTitle = cartPage.getPageTitle();
        Assert.assertEquals(pageTitle, "Shopping cart",
                "Expected page title to be 'Shopping cart', but got: " + pageTitle);

        Assert.assertTrue(cartPage.isUpdateCartButtonDisplayed(),
                "Update cart button should be visible");
        Assert.assertTrue(cartPage.isContinueShoppingButtonDisplayed(),
                "Continue shopping button should be visible");
        Assert.assertTrue(cartPage.isCheckoutButtonDisplayed(),
                "Checkout button should be visible");
        Assert.assertTrue(cartPage.isTermsOfServiceCheckboxDisplayed(),
                "Terms of service checkbox should be visible");
    }

    @Test(priority = 2, description = "Verify cart is not empty after adding product", groups = {"smoke", "regression", "ui", "cart", "positive"})
    @Story("Cart Content Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that cart contains items after adding a product and cart table is displayed correctly")
    public void verifyCartNotEmptyTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        Assert.assertFalse(cartPage.isCartEmpty(),
                "Cart should not be empty after adding product in setup");

        Assert.assertTrue(cartPage.isCartTableDisplayed(),
                "Cart table should be displayed when items exist in cart");

        Assert.assertTrue(cartPage.hasItemsInCart(),
                "Cart should have items based on combined validation");
    }

    @Test(priority = 3, description = "Verify product information is displayed in cart", groups = {"smoke", "regression", "ui", "cart", "positive"})
    @Story("Cart Product Display")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that all product information (name, price, quantity, subtotal) is displayed correctly in the cart")
    public void verifyProductInformationInCartTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        String productName = cartPage.getFirstProductName();
        String productPrice = cartPage.getFirstProductPrice();
        String productQuantity = cartPage.getFirstProductQuantity();
        String productSubtotal = cartPage.getFirstProductSubtotal();

        Assert.assertFalse(productName.isEmpty(),
                "Product name should not be empty in cart");
        Assert.assertFalse(productPrice.isEmpty(),
                "Product price should not be empty in cart");
        Assert.assertFalse(productQuantity.isEmpty(),
                "Product quantity should not be empty in cart");
        Assert.assertFalse(productSubtotal.isEmpty(),
                "Product subtotal should not be empty in cart");

        Assert.assertEquals(productName, "Computing and Internet",
                "Expected product name to be 'Computing and Internet', but got: " + productName);

        Assert.assertEquals(productQuantity, "1",
                "Expected default quantity to be 1, but got: " + productQuantity);

        Assert.assertTrue(cartPage.hasValidFirstProductPrice(),
                "Product price should be in valid format");

        Allure.addAttachment("Product Name", productName);
        Allure.addAttachment("Product Price", productPrice);
        Allure.addAttachment("Product Quantity", productQuantity);
        Allure.addAttachment("Product Subtotal", productSubtotal);
    }

    @Test(priority = 4, description = "Verify cart total is displayed", groups = {"smoke", "regression", "ui", "cart", "critical"})
    @Story("Cart Totals")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that cart total is displayed correctly and contains valid numeric values")
    public void verifyCartTotalDisplayedTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        Assert.assertTrue(cartPage.isCartTotalDisplayed(),
                "Cart total should be displayed on cart page");

        String cartTotal = cartPage.getCartTotal();

        Assert.assertFalse(cartTotal.isEmpty(),
                "Cart total should not be empty");

        Assert.assertTrue(cartTotal.matches(".*\\d+.*"),
                "Cart total should contain numbers, but got: " + cartTotal);

        double totalValue = cartPage.getCartTotalAsDouble();
        Assert.assertTrue(totalValue > 0,
                "Cart total should be greater than zero, but got: " + totalValue);

        Allure.addAttachment("Cart Total", cartTotal);
    }

    @Test(priority = 5, description = "Verify quantity can be updated", groups = {"regression", "ui", "cart", "positive"})
    @Story("Cart Quantity Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that users can update product quantity in cart and changes are reflected correctly")
    public void updateQuantityTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        String originalQuantity = cartPage.getFirstProductQuantity();
        Assert.assertEquals(originalQuantity, "1",
                "Expected original quantity to be 1, but got: " + originalQuantity);

        cartPage.updateQuantityAndRefresh("3");
        cartPage.waitForCartPageLoad();

        String updatedQuantity = cartPage.getFirstProductQuantity();

        Assert.assertEquals(updatedQuantity, "3",
                "Expected quantity to be updated to 3, but got: " + updatedQuantity);

        int quantityInt = cartPage.getFirstProductQuantityAsInt();
        Assert.assertEquals(quantityInt, 3,
                "Quantity should be 3 when parsed as integer");

        Allure.addAttachment("Original Quantity", originalQuantity);
        Allure.addAttachment("Updated Quantity", updatedQuantity);
    }

    @Test(priority = 6, description = "Verify subtotal updates when quantity changes", groups = {"regression", "ui", "cart", "critical"})
    @Story("Cart Calculations")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that product subtotal is recalculated correctly when quantity is changed")
    public void verifySubtotalUpdatesTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        String originalPrice = cartPage.getFirstProductPrice();
        String originalSubtotal = cartPage.getFirstProductSubtotal();

        cartPage.updateQuantityAndRefresh("2");
        cartPage.waitForCartPageLoad();

        String newSubtotal = cartPage.getFirstProductSubtotal();

        Assert.assertNotEquals(newSubtotal, originalSubtotal,
                "Expected subtotal to change when quantity increases. Original: " + originalSubtotal + ", New: " + newSubtotal);

        double originalSubtotalValue = Double.parseDouble(originalSubtotal.replaceAll("[^0-9.]", ""));
        double newSubtotalValue = Double.parseDouble(newSubtotal.replaceAll("[^0-9.]", ""));

        Assert.assertTrue(newSubtotalValue > originalSubtotalValue,
                "New subtotal should be greater than original when quantity increases");

        Allure.addAttachment("Original Price", originalPrice);
        Allure.addAttachment("Original Subtotal (Qty 1)", originalSubtotal);
        Allure.addAttachment("New Subtotal (Qty 2)", newSubtotal);
    }

    @Test(priority = 7, description = "Verify remove checkbox can be checked", groups = {"regression", "ui", "cart"})
    @Story("Cart Item Removal")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that remove checkbox can be selected for products in the cart")
    public void checkRemoveCheckboxTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        Assert.assertFalse(cartPage.isFirstProductRemoveChecked(),
                "Remove checkbox should be unchecked initially");

        cartPage.checkRemoveFirstProduct();

        Assert.assertTrue(cartPage.isFirstProductRemoveChecked(),
                "Remove checkbox should be checked after clicking");
    }

    @Test(priority = 8, description = "Verify product can be removed from cart", groups = {"smoke", "regression", "ui", "cart", "critical"})
    @Story("Cart Item Removal")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that users can successfully remove products from cart and cart becomes empty")
    public void removeProductFromCartTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        Assert.assertFalse(cartPage.isCartEmpty(),
                "Cart should have items before removal");
        Assert.assertTrue(cartPage.hasItemsInCart(),
                "Cart should contain items before removal");

        cartPage.removeFirstProductFromCart();
        cartPage.waitForCartPageLoad();

        Assert.assertTrue(cartPage.isCartEmpty(),
                "Cart should be empty after removing all items");
        Assert.assertFalse(cartPage.hasItemsInCart(),
                "Cart should not have items after removal");
    }

    @Test(priority = 9, description = "Verify Terms of Service checkbox can be checked", groups = {"smoke", "regression", "ui", "cart", "checkout", "critical"})
    @Story("Checkout Prerequisites")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that Terms of Service checkbox can be checked and unchecked as required for checkout")
    public void checkTermsOfServiceTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        Assert.assertFalse(cartPage.isTermsOfServiceChecked(),
                "Terms checkbox should be unchecked initially");

        cartPage.checkTermsOfService();

        Assert.assertTrue(cartPage.isTermsOfServiceChecked(),
                "Terms checkbox should be checked after clicking");

        cartPage.uncheckTermsOfService();

        Assert.assertFalse(cartPage.isTermsOfServiceChecked(),
                "Terms checkbox should be unchecked after unchecking");

        Assert.assertTrue(cartPage.isTermsOfServiceEnabled(),
                "Terms of Service checkbox should be enabled");
    }

    @Test(priority = 10, description = "Verify checkout button is clickable when terms are accepted", groups = {"smoke", "regression", "ui", "cart", "checkout", "e2e", "critical"})
    @Story("Checkout Flow")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that checkout button is displayed, enabled, and clickable for proceeding to checkout")
    public void verifyCheckoutButtonClickableTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        Assert.assertTrue(cartPage.isCheckoutButtonDisplayed(),
                "Checkout button should be displayed on cart page");

        Assert.assertTrue(cartPage.isCheckoutButtonClickable(),
                "Checkout button should be clickable");

        Assert.assertTrue(cartPage.isCheckoutAvailable(),
                "Checkout should be available for proceeding");
    }

    @Test(priority = 11, description = "Verify Continue Shopping button works", groups = {"regression", "ui", "cart", "navigation"})
    @Story("Cart Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that Continue Shopping button navigates user away from cart page")
    public void continueShoppingTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        cartPage.clickContinueShopping();

        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertFalse(currentUrl.contains("/cart"),
                "Expected to navigate away from cart page, but URL still contains '/cart': " + currentUrl);

        Allure.addAttachment("Navigated URL", currentUrl);
    }

    @Test(priority = 12, description = "Verify estimate shipping section is displayed", groups = {"regression", "ui", "cart"})
    @Story("Shipping Estimation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that shipping estimation section is visible on cart page")
    public void verifyEstimateShippingDisplayedTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        Assert.assertTrue(cartPage.isEstimateShippingDisplayed(),
                "Estimate shipping section should be visible on cart page");
    }

    @Test(priority = 13, description = "Verify all main elements method works", groups = {"smoke", "regression", "ui", "cart"})
    @Story("Cart Page UI")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that bulk validation method correctly identifies when all main cart elements are visible")
    public void verifyAllMainElementsMethodTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        Assert.assertTrue(cartPage.areAllMainElementsVisible(),
                "All main cart page elements should be visible");
    }

    @Test(priority = 14, description = "Verify discount code input is displayed", groups = {"regression", "ui", "cart"})
    @Story("Cart Discounts")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that discount code input field is functional and accepts user input")
    public void verifyDiscountCodeInputTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        Assert.assertTrue(cartPage.isDiscountCodeInputDisplayed(),
                "Discount code input field should be displayed");

        cartPage.enterDiscountCode(cartData.getDiscountCode());

        Allure.addAttachment("Test Discount Code", cartData.getDiscountCode());
    }

    @Test(priority = 15, description = "Verify gift card code input is displayed", groups = {"regression", "ui", "cart"})
    @Story("Cart Gift Cards")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that gift card code input field is functional and accepts user input")
    public void verifyGiftCardInputTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        Assert.assertTrue(cartPage.isGiftCardInputDisplayed(),
                "Gift card input field should be displayed");

        cartPage.enterGiftCardCode(cartData.getGiftCardCode());

        Allure.addAttachment("Test Gift Card Code", cartData.getGiftCardCode());
    }

    @Test(priority = 16, description = "Verify zip code can be entered for shipping estimate", groups = {"regression", "ui", "cart"})
    @Story("Shipping Estimation")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that zip code input field accepts user input for shipping estimation")
    public void enterZipCodeTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        String testZipCode = cartData.getZipCode();
        cartPage.enterZipCode(testZipCode);

        Allure.addAttachment("Test Zip Code", testZipCode);
    }

    @Test(priority = 17, description = "Verify updating quantity to zero removes item", groups = {"regression", "ui", "cart", "negative"})
    @Story("Cart Item Removal")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that setting product quantity to zero effectively removes the item from cart")
    public void updateQuantityToZeroTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        Assert.assertTrue(cartPage.hasItemsInCart(),
                "Cart should have items before setting quantity to zero");

        cartPage.updateQuantityAndRefresh("0");
        cartPage.waitForCartPageLoad();

        Assert.assertTrue(cartPage.isCartEmpty(),
                "Cart should be empty after setting quantity to 0");
    }

    @Test(priority = 18, description = "Verify page URL is correct", groups = {"regression", "ui", "cart"})
    @Story("Cart Page UI")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that cart page URL is correct and contains expected path")
    public void verifyCartPageUrlTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        String pageUrl = cartPage.getPageUrl();

        Assert.assertTrue(pageUrl.contains("/cart"),
                "Expected URL to contain '/cart', but got: " + pageUrl);

        Allure.addAttachment("Cart Page URL", pageUrl);
    }

    @Test(priority = 19, description = "Verify subtotal calculation is mathematically correct", groups = {"regression", "ui", "cart", "critical"})
    @Story("Cart Calculations")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that subtotal is calculated correctly as price × quantity")
    public void verifySubtotalCalculationTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        boolean isCalculationCorrect = cartPage.isSubtotalCalculationCorrect();

        Assert.assertTrue(isCalculationCorrect,
                "Subtotal calculation (price × quantity) should be mathematically correct");

        String price = cartPage.getFirstProductPrice();
        String quantity = cartPage.getFirstProductQuantity();
        String subtotal = cartPage.getFirstProductSubtotal();

        Allure.addAttachment("Unit Price", price);
        Allure.addAttachment("Quantity", quantity);
        Allure.addAttachment("Calculated Subtotal", subtotal);
    }

    @Test(priority = 20, description = "Verify product exists in cart by name", groups = {"regression", "ui", "cart"})
    @Story("Cart Product Verification")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that specific product can be found in cart by name")
    public void verifyProductExistsInCartTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        String expectedProductName = "Computing and Internet";
        boolean productExists = cartPage.isProductInCart(expectedProductName);

        Assert.assertTrue(productExists,
                "Expected product '" + expectedProductName + "' should exist in cart");
    }

    @Test(priority = 21, description = "Verify all cart functionalities are available", groups = {"regression", "ui", "cart"})
    @Story("Cart Page UI")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that all cart functionality elements (update, checkout, discounts, gift cards) are present")
    public void verifyAllCartFunctionalitiesTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        Assert.assertTrue(cartPage.areAllCartFunctionalitiesAvailable(),
                "All cart functionality elements should be available on the page");
    }

    @Test(priority = 22, description = "Verify cart page loads completely", groups = {"smoke", "regression", "ui", "cart", "critical"})
    @Story("Cart Page UI")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that cart page waits for all elements to load before allowing user interaction")
    public void verifyCartPageLoadTest() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        cartPage.waitForPageToLoad();

        Assert.assertTrue(cartPage.isPageTitleDisplayed(),
                "Page title should be loaded and visible");
        Assert.assertTrue(cartPage.isCheckoutButtonDisplayed(),
                "Checkout button should be loaded and visible");
    }
}