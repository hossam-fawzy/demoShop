package tests;

import base.BaseTest;
import drivers.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PDPPage;

public class PDPTest extends BaseTest {

    private static final String BOOK_PRODUCT_URL = "https://demowebshop.tricentis.com/computing-and-internet";

    @BeforeMethod
    public void navigateToProductPage() {
        DriverFactory.getDriver().get(BOOK_PRODUCT_URL);
    }

    @Test(priority = 1, description = "Verify PDP page loads and all main elements are visible")
    public void verifyPDPElementsVisibleTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Assert.assertTrue(pdpPage.isProductNameDisplayed(), "Product name should be visible");
        Assert.assertTrue(pdpPage.isProductPriceDisplayed(), "Product price should be visible");
        Assert.assertTrue(pdpPage.isProductImageDisplayed(), "Product image should be visible");
        Assert.assertTrue(pdpPage.isAddToCartButtonDisplayed(), "Add to cart button should be visible");
        Assert.assertTrue(pdpPage.isQuantityInputDisplayed(), "Quantity input should be visible");
        Assert.assertTrue(pdpPage.isAddToWishlistButtonDisplayed(), "Add to wishlist button should be visible");
    }

    @Test(priority = 2, description = "Verify product information is displayed correctly")
    public void verifyProductInformationTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        String productName = pdpPage.getProductName();
        String productPrice = pdpPage.getProductPrice();
        String productSKU = pdpPage.getProductSKU();

        Assert.assertFalse(productName.isEmpty(), "Product name should not be empty");
        Assert.assertFalse(productPrice.isEmpty(), "Product price should not be empty");
        Assert.assertFalse(productSKU.isEmpty(), "Product SKU should not be empty");

        Assert.assertEquals(productName, "Computing and Internet", "Product name should match");

        Assert.assertTrue(productPrice.matches(".*\\d+.*"), "Price should contain numbers");

        System.out.println("Product Name: " + productName);
        System.out.println("Product Price: " + productPrice);
        System.out.println("Product SKU: " + productSKU);
    }

    @Test(priority = 3, description = "Verify default quantity is 1")
    public void verifyDefaultQuantityTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        String currentQuantity = pdpPage.getCurrentQuantity();

        Assert.assertEquals(currentQuantity, "1", "Default quantity should be 1");
    }

    @Test(priority = 4, description = "Verify quantity can be increased")
    public void increaseQuantityTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        pdpPage.increaseQuantityByTimes(3);

        String updatedQuantity = pdpPage.getCurrentQuantity();

        Assert.assertEquals(updatedQuantity, "4", "Quantity should be 4 after increasing 3 times");
    }

    @Test(priority = 5, description = "Verify quantity can be decreased")
    public void decreaseQuantityTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        pdpPage.increaseQuantityByTimes(4); // Now it's 5
        pdpPage.decreaseQuantityByTimes(2); // Now it's 3

        String updatedQuantity = pdpPage.getCurrentQuantity();

        Assert.assertEquals(updatedQuantity, "3", "Quantity should be 3");
    }

    @Test(priority = 6, description = "Verify quantity can be set directly")
    public void setQuantityDirectlyTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        pdpPage.setQuantity("10");

        String updatedQuantity = pdpPage.getCurrentQuantity();

        Assert.assertEquals(updatedQuantity, "10", "Quantity should be set to 10");
    }

    @Test(priority = 7, description = "Verify add to cart with default quantity works")
    public void addToCartDefaultQuantityTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        pdpPage.clickAddToCart();

        boolean notificationAppeared = pdpPage.waitForNotification();
        Assert.assertTrue(notificationAppeared, "Notification should appear after adding to cart");

        Assert.assertTrue(pdpPage.isNotificationBarVisible(), "Notification bar should be visible");

        String successMessage = pdpPage.getSuccessMessage();
        Assert.assertTrue(successMessage.contains("The product has been added to your"),
                "Success message should confirm product added");

        System.out.println("Success Message: " + successMessage);
    }

    @Test(priority = 8, description = "Verify add to cart with custom quantity works")
    public void addToCartCustomQuantityTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        pdpPage.addToCartWithQuantity("5");

        pdpPage.waitForNotification();

        Assert.assertTrue(pdpPage.isNotificationBarVisible(),
                "Notification should appear after adding 5 items to cart");

        String successMessage = pdpPage.getSuccessMessage();
        Assert.assertTrue(successMessage.contains("added to your"),
                "Success message should confirm items added");
    }


    @Test(priority = 10, description = "Verify breadcrumb navigation is displayed")
    public void verifyBreadcrumbTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Assert.assertTrue(pdpPage.isBreadcrumbVisible(), "Breadcrumb should be visible");

        String breadcrumbText = pdpPage.getBreadcrumbText();

        Assert.assertTrue(breadcrumbText.contains("Home"), "Breadcrumb should contain Home");
        Assert.assertTrue(breadcrumbText.contains("Books"), "Breadcrumb should contain Books");

        System.out.println("Breadcrumb: " + breadcrumbText);
    }

    @Test(priority = 11, description = "Verify clicking Home in breadcrumb navigates to homepage")
    public void clickHomeInBreadcrumbTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        pdpPage.clickHomeInBreadcrumb();

        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.equals("https://demowebshop.tricentis.com/") ||
                        currentUrl.equals("https://demowebshop.tricentis.com"),
                "Should navigate to homepage");
    }

    @Test(priority = 12, description = "Verify clicking Books in breadcrumb navigates to Books category")
    public void clickBooksInBreadcrumbTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        pdpPage.clickBooksInBreadcrumb();

        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/books"), "Should navigate to Books category");
    }

    @Test(priority = 13, description = "Verify Add to Cart button is clickable")
    public void verifyAddToCartButtonClickableTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Assert.assertTrue(pdpPage.isAddToCartButtonClickable(),
                "Add to Cart button should be clickable");
    }

    @Test(priority = 14, description = "Verify product page URL is correct")
    public void verifyProductPageUrlTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Assert.assertTrue(pdpPage.isOnProductPage(), "Should be on a product page");

        String pageUrl = pdpPage.getPageUrl();
        System.out.println("Product Page URL: " + pageUrl);

        Assert.assertTrue(pageUrl.contains("computing-and-internet"),
                "URL should contain product identifier");
    }

    @Test(priority = 15, description = "Verify all main elements check method works")
    public void verifyAllMainElementsMethodTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Assert.assertTrue(pdpPage.areAllMainElementsVisible(),
                "All main PDP elements should be visible");
    }
}