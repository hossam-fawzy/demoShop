package tests;

import base.BaseTest;
import drivers.DriverFactory;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PDPPage;

@Epic("E-Commerce Platform")
@Feature("Product Details Page (PDP)")
public class PDPTest extends BaseTest {

    private static final String BOOK_PRODUCT_URL = "https://demowebshop.tricentis.com/computing-and-internet";

    @BeforeMethod
    public void navigateToProductPage() {
        DriverFactory.getDriver().get(BOOK_PRODUCT_URL);
    }

    @Test(priority = 1, description = "Verify PDP page loads and all main elements are visible")
    @Story("PDP UI Verification")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that product details page loads successfully with all essential elements visible including product info, images, and action buttons")
    public void verifyPDPElementsVisibleTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Assert.assertTrue(pdpPage.isProductNameDisplayed(),
                "Product name should be visible on PDP");
        Assert.assertTrue(pdpPage.isProductPriceDisplayed(),
                "Product price should be visible on PDP");
        Assert.assertTrue(pdpPage.isProductImageDisplayed(),
                "Product image should be visible on PDP");
        Assert.assertTrue(pdpPage.isAddToCartButtonDisplayed(),
                "Add to cart button should be visible on PDP");
        Assert.assertTrue(pdpPage.isQuantityInputDisplayed(),
                "Quantity input field should be visible on PDP");
        Assert.assertTrue(pdpPage.isAddToWishlistButtonDisplayed(),
                "Add to wishlist button should be visible on PDP");
    }

    @Test(priority = 2, description = "Verify product information is displayed correctly")
    @Story("Product Information Display")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that all product information (name, price, SKU) is displayed correctly and contains valid data")
    public void verifyProductInformationTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        String productName = pdpPage.getProductName();
        String productPrice = pdpPage.getProductPrice();
        String productSKU = pdpPage.getProductSKU();

        Assert.assertFalse(productName.isEmpty(),
                "Product name should not be empty");
        Assert.assertFalse(productPrice.isEmpty(),
                "Product price should not be empty");
        Assert.assertFalse(productSKU.isEmpty(),
                "Product SKU should not be empty");

        Assert.assertEquals(productName, "Computing and Internet",
                "Expected product name to be 'Computing and Internet', but got: " + productName);

        Assert.assertTrue(productPrice.matches(".*\\d+.*"),
                "Price should contain numbers, but got: " + productPrice);

        Assert.assertTrue(pdpPage.hasValidPrice(),
                "Product should have a valid price greater than zero");

        Allure.addAttachment("Product Name", productName);
        Allure.addAttachment("Product Price", productPrice);
        Allure.addAttachment("Product SKU", productSKU);
    }

    @Test(priority = 3, description = "Verify default quantity is 1")
    @Story("Quantity Management")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the default product quantity is set to 1 when the PDP page loads")
    public void verifyDefaultQuantityTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        String currentQuantity = pdpPage.getCurrentQuantity();

        Assert.assertEquals(currentQuantity, "1",
                "Expected default quantity to be 1, but got: " + currentQuantity);

        int quantityInt = pdpPage.getQuantityAsInt();
        Assert.assertEquals(quantityInt, 1,
                "Default quantity should be 1 when parsed as integer");
    }

    @Test(priority = 4, description = "Verify quantity buttons is visible")
    @Story("Quantity Management")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that product Quantity buttons is visible ")
    public void isQuantityButtonsVisibleTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Assert.assertTrue(pdpPage.isIncreaseButtonVisible(), "Increase button should be visible on PDP");
        Assert.assertTrue(pdpPage.isDecreaseButtonVisible(), "Decrease button should be visible on PDP");
    }

    @Test(priority = 6, description = "Verify quantity can be set directly")
    @Story("Quantity Management")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that users can manually enter a custom quantity value in the quantity input field")
    public void setQuantityDirectlyTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        String desiredQuantity = "10";
        pdpPage.setQuantity(desiredQuantity);
        String updatedQuantity = pdpPage.getCurrentQuantity();

        Assert.assertEquals(updatedQuantity, desiredQuantity,
                "Expected quantity to be " + desiredQuantity + " after setting directly, but got: " + updatedQuantity);
    }

    @Test(priority = 7, description = "Verify add to cart with default quantity works")
    @Story("Add to Cart Functionality")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that users can add a product to cart with default quantity and receive success notification")
    public void addToCartDefaultQuantityTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        pdpPage.clickAddToCart();

        boolean notificationAppeared = pdpPage.waitForNotification();
        Assert.assertTrue(notificationAppeared,
                "Notification should appear within timeout period after adding product to cart");

        Assert.assertTrue(pdpPage.isNotificationBarVisible(),
                "Notification bar should be visible after adding to cart");

        String successMessage = pdpPage.getSuccessMessage();
        Assert.assertTrue(successMessage.contains("The product has been added to your"),
                "Expected success message to contain 'The product has been added to your', but got: " + successMessage);

        Allure.addAttachment("Success Message", successMessage);
    }

    @Test(priority = 8, description = "Verify add to cart with custom quantity works")
    @Story("Add to Cart Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that users can add multiple quantities of a product to cart and receive appropriate confirmation")
    public void addToCartCustomQuantityTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        String customQuantity = "5";
        pdpPage.addToCartWithQuantity(customQuantity);

        pdpPage.waitForNotification();

        Assert.assertTrue(pdpPage.isNotificationBarVisible(),
                "Notification should appear after adding " + customQuantity + " items to cart");

        String successMessage = pdpPage.getSuccessMessage();
        Assert.assertTrue(successMessage.contains("added to your"),
                "Expected success message to contain 'added to your', but got: " + successMessage);

        Allure.addAttachment("Custom Quantity", customQuantity);
        Allure.addAttachment("Success Message", successMessage);
    }

    @Test(priority = 9, description = "Verify add to cart button is enabled and clickable")
    @Story("Add to Cart Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that Add to Cart button is in an enabled and clickable state for product purchase")
    public void verifyAddToCartButtonStateTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Assert.assertTrue(pdpPage.isAddToCartButtonClickable(),
                "Add to Cart button should be clickable");
        Assert.assertTrue(pdpPage.isAddToCartButtonEnabled(),
                "Add to Cart button should be enabled");
        Assert.assertTrue(pdpPage.isProductAvailable(),
                "Product should be available for purchase");
    }

    @Test(priority = 10, description = "Verify breadcrumb navigation is displayed")
    @Story("Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that breadcrumb navigation is visible and displays correct hierarchy of pages")
    public void verifyBreadcrumbTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Assert.assertTrue(pdpPage.isBreadcrumbVisible(),
                "Breadcrumb navigation should be visible on PDP");

        String breadcrumbText = pdpPage.getBreadcrumbText();

        Assert.assertTrue(breadcrumbText.contains("Home"),
                "Breadcrumb should contain 'Home', but got: " + breadcrumbText);
        Assert.assertTrue(breadcrumbText.contains("Books"),
                "Breadcrumb should contain 'Books', but got: " + breadcrumbText);

        Allure.addAttachment("Breadcrumb Text", breadcrumbText);
    }

    @Test(priority = 11, description = "Verify clicking Home in breadcrumb navigates to homepage")
    @Story("Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that clicking the Home link in breadcrumb navigation successfully redirects to the homepage")
    public void clickHomeInBreadcrumbTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        pdpPage.clickHomeInBreadcrumb();

        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.equals("https://demowebshop.tricentis.com/") ||
                        currentUrl.equals("https://demowebshop.tricentis.com"),
                "Expected to navigate to homepage, but current URL is: " + currentUrl);
    }

    @Test(priority = 12, description = "Verify clicking Books in breadcrumb navigates to Books category")
    @Story("Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that clicking the Books link in breadcrumb navigation successfully redirects to Books category page")
    public void clickBooksInBreadcrumbTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        pdpPage.clickBooksInBreadcrumb();

        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/books"),
                "Expected URL to contain '/books', but got: " + currentUrl);
    }

    @Test(priority = 13, description = "Verify product page URL is correct")
    @Story("PDP UI Verification")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that the product page URL is correct and matches the expected pattern")
    public void verifyProductPageUrlTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Assert.assertTrue(pdpPage.isOnProductPage(),
                "Should be on a valid product details page");

        String pageUrl = pdpPage.getPageUrl();

        Assert.assertTrue(pageUrl.contains("computing-and-internet"),
                "Expected URL to contain product identifier 'computing-and-internet', but got: " + pageUrl);

        Allure.addAttachment("Product Page URL", pageUrl);
    }

    @Test(priority = 14, description = "Verify all main elements check method works")
    @Story("PDP UI Verification")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the bulk validation method correctly identifies when all main PDP elements are visible")
    public void verifyAllMainElementsMethodTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Assert.assertTrue(pdpPage.areAllMainElementsVisible(),
                "All main PDP elements (name, price, image, add to cart, quantity) should be visible");
    }

    @Test(priority = 15, description = "Verify product descriptions are displayed")
    @Story("Product Information Display")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that product short and full descriptions are displayed when available")
    public void verifyProductDescriptionsTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        if (pdpPage.isShortDescriptionDisplayed()) {
            String shortDesc = pdpPage.getShortDescription();
            Assert.assertFalse(shortDesc.isEmpty(),
                    "Short description should not be empty when displayed");
            Allure.addAttachment("Short Description", shortDesc);
        }

        if (pdpPage.isFullDescriptionDisplayed()) {
            String fullDesc = pdpPage.getFullDescription();
            Assert.assertFalse(fullDesc.isEmpty(),
                    "Full description should not be empty when displayed");
            Allure.addAttachment("Full Description", fullDesc);
        }
    }

    @Test(priority = 16, description = "Verify navigation to cart from notification")
    @Story("Add to Cart Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that users can navigate to shopping cart directly from the success notification")
    public void navigateToCartFromNotificationTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        pdpPage.clickAddToCart();
        pdpPage.waitForNotification();

        Assert.assertTrue(pdpPage.isNotificationBarVisible(),
                "Notification bar should be visible before clicking cart link");

        pdpPage.clickShoppingCartInNotification();

        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/cart"),
                "Expected to navigate to shopping cart, but current URL is: " + currentUrl);
    }

    @Test(priority = 17, description = "Verify closing notification works")
    @Story("PDP UI Verification")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that users can close the success notification using the close button")
    public void closeNotificationTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        pdpPage.clickAddToCart();
        pdpPage.waitForNotification();

        Assert.assertTrue(pdpPage.isNotificationBarVisible(),
                "Notification should be visible before closing");

        pdpPage.closeNotification();

        boolean notificationDisappeared = pdpPage.waitForNotificationToDisappear();
        Assert.assertTrue(notificationDisappeared,
                "Notification should disappear after clicking close button");
    }

    @Test(priority = 18, description = "Verify all action buttons are visible")
    @Story("PDP UI Verification")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that all product action buttons (Add to Cart, Wishlist, Compare, Email) are visible on PDP")
    public void verifyAllActionButtonsTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Assert.assertTrue(pdpPage.areAllActionButtonsVisible(),
                "All action buttons should be visible on product details page");
    }

    @Test(priority = 19, description = "Verify PDP page loads completely before interaction")
    @Story("PDP UI Verification")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that product details page waits for all elements to load before allowing user interaction")
    public void verifyPageLoadTest() {
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        pdpPage.waitForPageToLoad();

        Assert.assertTrue(pdpPage.isProductNameDisplayed(),
                "Product name should be loaded and visible");
        Assert.assertTrue(pdpPage.isAddToCartButtonDisplayed(),
                "Add to cart button should be loaded and visible");
    }
}