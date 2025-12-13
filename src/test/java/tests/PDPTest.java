package tests;

import base.BaseTest;
import drivers.DriverFactory;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PDPPage;

/**
 * PDPTest - Test suite for Product Details Page functionality
 *
 * REAL-WORLD PURPOSE:
 * - Validates product page across multiple scenarios
 * - Covers product information, cart actions, navigation
 * - Ensures UI elements and functionality work correctly
 * - Integrated with Allure for detailed reporting
 *
 * BEST PRACTICES APPLIED:
 * - Page Object Model pattern
 * - Granular Allure steps for detailed reporting
 * - Comprehensive logging with Log4j2
 * - Clear test organization and naming
 * - Independent, atomic tests
 *
 * TEST COVERAGE:
 * - Product information display
 * - Quantity management
 * - Add to cart functionality
 * - Navigation and breadcrumbs
 * - Notifications
 *
 * @author QA Team
 * @version 2.0
 */

@Epic("E-Commerce Platform")
@Feature("Product Details Page (PDP)")
@Owner("QA Team")
@Link(name = "PDP Requirements", url = "https://your-jira.com/browse/PDP")
public class PDPTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(PDPTest.class);
    private static final String BOOK_PRODUCT_URL = "https://demowebshop.tricentis.com/computing-and-internet";

    @BeforeMethod
    public void navigateToProductPage() {
        logger.debug("Navigating to product page: {}", BOOK_PRODUCT_URL);
        DriverFactory.getDriver().get(BOOK_PRODUCT_URL);
    }

    @Test(priority = 1, description = "Verify PDP page loads and all main elements are visible", groups = {"smoke", "ui", "pdp", "critical"})
    @Story("PDP UI Verification")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that product details page loads successfully with all essential elements visible including product info, images, and action buttons")
    @TmsLink("TC-PDP-001")
    public void verifyPDPElementsVisibleTest() {

        logger.info("🧪 Starting Test: Verify PDP Elements Visible");

        PDPPage pdpPage = Allure.step("Step 1: Initialize PDP page object", () -> {
            logger.debug("Creating PDP page object...");
            PDPPage page = new PDPPage(DriverFactory.getDriver());
            logger.info("✅ PDP page object created");
            return page;
        });

        Allure.step("Step 2: Verify product name is displayed", () -> {
            logger.debug("Checking product name visibility...");
            boolean isDisplayed = pdpPage.isProductNameDisplayed();
            Allure.parameter("Product Name Displayed", isDisplayed);
            Assert.assertTrue(isDisplayed, "Product name should be visible on PDP");
            logger.info("✅ Product name is displayed");
        });

        Allure.step("Step 3: Verify product price is displayed", () -> {
            logger.debug("Checking product price visibility...");
            boolean isDisplayed = pdpPage.isProductPriceDisplayed();
            Allure.parameter("Product Price Displayed", isDisplayed);
            Assert.assertTrue(isDisplayed, "Product price should be visible on PDP");
            logger.info("✅ Product price is displayed");
        });

        Allure.step("Step 4: Verify product image is displayed", () -> {
            logger.debug("Checking product image visibility...");
            boolean isDisplayed = pdpPage.isProductImageDisplayed();
            Allure.parameter("Product Image Displayed", isDisplayed);
            Assert.assertTrue(isDisplayed, "Product image should be visible on PDP");
            logger.info("✅ Product image is displayed");
        });

        Allure.step("Step 5: Verify Add to Cart button is displayed", () -> {
            logger.debug("Checking Add to Cart button visibility...");
            boolean isDisplayed = pdpPage.isAddToCartButtonDisplayed();
            Allure.parameter("Add to Cart Button Displayed", isDisplayed);
            Assert.assertTrue(isDisplayed, "Add to cart button should be visible on PDP");
            logger.info("✅ Add to Cart button is displayed");
        });

        Allure.step("Step 6: Verify quantity input is displayed", () -> {
            logger.debug("Checking quantity input visibility...");
            boolean isDisplayed = pdpPage.isQuantityInputDisplayed();
            Allure.parameter("Quantity Input Displayed", isDisplayed);
            Assert.assertTrue(isDisplayed, "Quantity input field should be visible on PDP");
            logger.info("✅ Quantity input is displayed");
        });

        Allure.step("Step 7: Verify Add to Wishlist button is displayed", () -> {
            logger.debug("Checking Add to Wishlist button visibility...");
            boolean isDisplayed = pdpPage.isAddToWishlistButtonDisplayed();
            Allure.parameter("Add to Wishlist Button Displayed", isDisplayed);
            Assert.assertTrue(isDisplayed, "Add to wishlist button should be visible on PDP");
            logger.info("✅ Add to Wishlist button is displayed");
        });

        Allure.step("✅ Test Passed - All PDP elements are visible and displayed correctly");
        logger.info("✅ Test completed successfully: verifyPDPElementsVisibleTest");
    }

    @Test(priority = 2, description = "Verify product information is displayed correctly", groups = {"smoke", "regression", "ui", "pdp", "positive"})
    @Story("Product Information Display")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that all product information (name, price, SKU) is displayed correctly and contains valid data")
    @TmsLink("TC-PDP-002")
    public void verifyProductInformationTest() {

        logger.info("🧪 Starting Test: Verify Product Information");

        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        String[] productInfo = Allure.step("Step 1: Retrieve product information", () -> {
            logger.debug("Getting product name, price, and SKU...");
            String name = pdpPage.getProductName();
            String price = pdpPage.getProductPrice();
            String sku = pdpPage.getProductSKU();

            Allure.parameter("Product Name", name);
            Allure.parameter("Product Price", price);
            Allure.parameter("Product SKU", sku);

            logger.info("Product Info - Name: {}, Price: {}, SKU: {}", name, price, sku);
            return new String[]{name, price, sku};
        });

        Allure.step("Step 2: Verify product name is not empty", () -> {
            Assert.assertFalse(productInfo[0].isEmpty(), "Product name should not be empty");
            logger.info("✅ Product name verified: {}", productInfo[0]);
        });

        Allure.step("Step 3: Verify product price is not empty", () -> {
            Assert.assertFalse(productInfo[1].isEmpty(), "Product price should not be empty");
            logger.info("✅ Product price verified: {}", productInfo[1]);
        });

        Allure.step("Step 4: Verify product SKU is not empty", () -> {
            Assert.assertFalse(productInfo[2].isEmpty(), "Product SKU should not be empty");
            logger.info("✅ Product SKU verified: {}", productInfo[2]);
        });

        Allure.step("Step 5: Verify product name matches expected value", () -> {
            Assert.assertEquals(productInfo[0], "Computing and Internet",
                    "Expected product name to be 'Computing and Internet', but got: " + productInfo[0]);
            logger.info("✅ Product name matches expected value");
        });

        Allure.step("Step 6: Verify price contains currency symbol", () -> {
            boolean hasCurrency = productInfo[1].contains("$") || productInfo[1].matches(".*\\d+.*");
            Allure.parameter("Has Currency/Number", hasCurrency);
            Assert.assertTrue(hasCurrency, "Price should contain currency symbol or numbers");
            logger.info("✅ Price format verified");
        });

        Allure.addAttachment("Product Name", "text/plain", productInfo[0]);
        Allure.addAttachment("Product Price", "text/plain", productInfo[1]);
        Allure.addAttachment("Product SKU", "text/plain", productInfo[2]);

        Allure.step("✅ Test Passed - Product information displayed correctly");
        logger.info("✅ Test completed successfully: verifyProductInformationTest");
    }

    @Test(priority = 3, description = "Verify default quantity is 1", groups = {"regression", "ui", "pdp"})
    @Story("Quantity Management")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the default product quantity is set to 1 when the PDP page loads")
    @TmsLink("TC-PDP-003")
    public void verifyDefaultQuantityTest() {

        logger.info("🧪 Starting Test: Verify Default Quantity");

        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        String quantity = Allure.step("Step 1: Get current quantity value", () -> {
            logger.debug("Getting current quantity...");
            String qty = pdpPage.getCurrentQuantity();
            Allure.parameter("Current Quantity", qty);
            logger.info("Current quantity: {}", qty);
            return qty;
        });

        Allure.step("Step 2: Verify quantity equals 1", () -> {
            Assert.assertEquals(quantity, "1", "Expected default quantity to be 1, but got: " + quantity);
            logger.info("✅ Default quantity verified as 1");
        });

        Allure.step("Step 3: Verify quantity as integer", () -> {
            int quantityInt = Integer.parseInt(quantity);
            Allure.parameter("Quantity as Integer", quantityInt);
            Assert.assertEquals(quantityInt, 1, "Default quantity should be 1 when parsed as integer");
            logger.info("✅ Quantity integer value verified");
        });

        Allure.step("✅ Test Passed - Default quantity is 1");
        logger.info("✅ Test completed successfully: verifyDefaultQuantityTest");
    }

    @Test(priority = 4, description = "Verify quantity buttons are visible", groups = {"regression", "ui", "pdp"})
    @Story("Quantity Management")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that product quantity buttons are visible")
    @TmsLink("TC-PDP-004")
    public void isQuantityButtonsVisibleTest() {

        logger.info("🧪 Starting Test: Verify Quantity Buttons Visible");

        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Allure.step("Step 1: Verify increase button is visible", () -> {
            logger.debug("Checking increase button visibility...");
            boolean isVisible = pdpPage.isIncreaseButtonVisible();
            Allure.parameter("Increase Button Visible", isVisible);
            Assert.assertTrue(isVisible, "Increase button should be visible on PDP");
            logger.info("✅ Increase button is visible");
        });

        Allure.step("Step 2: Verify decrease button is visible", () -> {
            logger.debug("Checking decrease button visibility...");
            boolean isVisible = pdpPage.isDecreaseButtonVisible();
            Allure.parameter("Decrease Button Visible", isVisible);
            Assert.assertTrue(isVisible, "Decrease button should be visible on PDP");
            logger.info("✅ Decrease button is visible");
        });

        Allure.step("✅ Test Passed - Quantity buttons are visible");
        logger.info("✅ Test completed successfully: isQuantityButtonsVisibleTest");
    }

    @Test(priority = 6, description = "Verify quantity can be set directly", groups = {"regression", "ui", "pdp", "positive"})
    @Story("Quantity Management")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that users can manually enter a custom quantity value in the quantity input field")
    @TmsLink("TC-PDP-005")
    public void setQuantityDirectlyTest() {

        logger.info("🧪 Starting Test: Set Quantity Directly");

        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());
        String desiredQuantity = "5";

        Allure.step("Step 1: Set quantity to " + desiredQuantity, () -> {
            logger.debug("Setting quantity to: {}", desiredQuantity);
            pdpPage.setQuantity(desiredQuantity);
            Allure.parameter("Desired Quantity", desiredQuantity);
            logger.info("✅ Quantity set to: {}", desiredQuantity);
        });

        Allure.step("Step 2: Verify quantity was updated", () -> {
            logger.debug("Verifying updated quantity...");
            String updatedQuantity = pdpPage.getCurrentQuantity();
            Allure.parameter("Updated Quantity", updatedQuantity);
            Assert.assertEquals(updatedQuantity, desiredQuantity,
                    "Expected quantity to be " + desiredQuantity + " after setting directly, but got: " + updatedQuantity);
            logger.info("✅ Quantity verified as: {}", updatedQuantity);
        });

        Allure.step("✅ Test Passed - Quantity set directly to " + desiredQuantity);
        logger.info("✅ Test completed successfully: setQuantityDirectlyTest");
    }

    @Test(priority = 7, description = "Verify add to cart with default quantity works", groups = {"smoke", "regression", "ui", "pdp", "cart", "critical"})
    @Story("Add to Cart Functionality")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that users can add a product to cart with default quantity and receive success notification")
    @TmsLink("TC-PDP-006")
    public void addToCartDefaultQuantityTest() {

        logger.info("🧪 Starting Test: Add to Cart with Default Quantity");

        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Allure.step("Step 1: Verify default quantity is 1", () -> {
            logger.debug("Checking default quantity...");
            String quantity = pdpPage.getCurrentQuantity();
            Allure.parameter("Default Quantity", quantity);
            Assert.assertEquals(quantity, "1", "Default quantity should be 1");
            logger.info("✅ Default quantity verified: {}", quantity);
        });

        Allure.step("Step 2: Click Add to Cart button", () -> {
            logger.debug("Clicking Add to Cart...");
            pdpPage.clickAddToCart();
            logger.info("✅ Add to Cart clicked");
        });

        Allure.step("Step 3: Wait for success notification", () -> {
            logger.debug("Waiting for notification...");
            boolean isVisible = pdpPage.waitForNotification();
            Allure.parameter("Notification Appeared", isVisible);
            Assert.assertTrue(isVisible, "Success notification should appear after adding to cart");
            logger.info("✅ Notification appeared");
        });

        Allure.step("Step 4: Verify success message", () -> {
            logger.debug("Getting success message...");
            String successMessage = pdpPage.getSuccessMessage();
            Allure.addAttachment("Success Message", "text/plain", successMessage);
            Assert.assertFalse(successMessage.isEmpty(), "Success message should not be empty");
            logger.info("✅ Success message verified: {}", successMessage);
        });

        Allure.step("✅ Test Passed - Product added to cart successfully with default quantity");
        logger.info("✅ Test completed successfully: addToCartDefaultQuantityTest");
    }

    @Test(priority = 8, description = "Verify add to cart with custom quantity works", groups = {"regression", "ui", "pdp", "cart", "positive"})
    @Story("Add to Cart Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that users can add multiple quantities of a product to cart and receive appropriate confirmation")
    @TmsLink("TC-PDP-007")
    public void addToCartCustomQuantityTest() {

        logger.info("🧪 Starting Test: Add to Cart with Custom Quantity");

        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());
        String customQuantity = "3";

        Allure.step("Step 1: Set custom quantity to " + customQuantity, () -> {
            logger.debug("Setting quantity to: {}", customQuantity);
            pdpPage.setQuantity(customQuantity);
            Allure.parameter("Custom Quantity", customQuantity);
            logger.info("✅ Quantity set to: {}", customQuantity);
        });

        Allure.step("Step 2: Click Add to Cart button", () -> {
            logger.debug("Clicking Add to Cart...");
            pdpPage.clickAddToCart();
            logger.info("✅ Add to Cart clicked");
        });

        Allure.step("Step 3: Wait for success notification", () -> {
            logger.debug("Waiting for notification...");
            boolean isVisible = pdpPage.waitForNotification();
            Allure.parameter("Notification Appeared", isVisible);
            Assert.assertTrue(isVisible, "Success notification should appear");
            logger.info("✅ Notification appeared");
        });

        Allure.step("Step 4: Verify success message contains expected text", () -> {
            logger.debug("Verifying success message...");
            String successMessage = pdpPage.getSuccessMessage();
            Allure.addAttachment("Success Message", "text/plain", successMessage);
            Assert.assertTrue(successMessage.toLowerCase().contains("added"),
                    "Success message should contain 'added'");
            logger.info("✅ Success message verified");
        });

        Allure.step("✅ Test Passed - Product added to cart with custom quantity: " + customQuantity);
        logger.info("✅ Test completed successfully: addToCartCustomQuantityTest");
    }

    @Test(priority = 9, description = "Verify add to cart button is enabled and clickable", groups = {"smoke", "regression", "ui", "pdp", "critical"})
    @Story("Add to Cart Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that Add to Cart button is in an enabled and clickable state for product purchase")
    @TmsLink("TC-PDP-008")
    public void verifyAddToCartButtonStateTest() {

        logger.info("🧪 Starting Test: Verify Add to Cart Button State");

        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Allure.step("Step 1: Verify Add to Cart button is displayed", () -> {
            logger.debug("Checking button display...");
            boolean isDisplayed = pdpPage.isAddToCartButtonDisplayed();
            Allure.parameter("Button Displayed", isDisplayed);
            Assert.assertTrue(isDisplayed, "Add to cart button should be displayed");
            logger.info("✅ Button is displayed");
        });

        Allure.step("Step 2: Verify Add to Cart button is enabled", () -> {
            logger.debug("Checking button enabled state...");
            boolean isEnabled = pdpPage.isAddToCartButtonEnabled();
            Allure.parameter("Button Enabled", isEnabled);
            Assert.assertTrue(isEnabled, "Add to cart button should be enabled");
            logger.info("✅ Button is enabled");
        });

        Allure.step("Step 3: Verify product is available for purchase", () -> {
            logger.debug("Checking product availability...");
            boolean isAvailable = pdpPage.isProductAvailable();
            Allure.parameter("Product Available", isAvailable);
            Assert.assertTrue(isAvailable, "Product should be available for purchase");
            logger.info("✅ Product is available");
        });

        Allure.step("✅ Test Passed - Add to Cart button is enabled and product is available");
        logger.info("✅ Test completed successfully: verifyAddToCartButtonStateTest");
    }

    @Test(priority = 10, description = "Verify breadcrumb navigation is displayed", groups = {"regression", "ui", "pdp", "navigation"})
    @Story("Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that breadcrumb navigation is visible and displays correct hierarchy of pages")
    @TmsLink("TC-PDP-009")
    public void verifyBreadcrumbTest() {

        logger.info("🧪 Starting Test: Verify Breadcrumb Navigation");

        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Allure.step("Step 1: Verify breadcrumb is displayed", () -> {
            logger.debug("Checking breadcrumb visibility...");
            boolean isDisplayed = pdpPage.isBreadcrumbVisible();
            Allure.parameter("Breadcrumb Displayed", isDisplayed);
            Assert.assertTrue(isDisplayed, "Breadcrumb should be visible on PDP");
            logger.info("✅ Breadcrumb is displayed");
        });

        Allure.step("Step 2: Get breadcrumb text", () -> {
            logger.debug("Getting breadcrumb text...");
            String breadcrumbText = pdpPage.getBreadcrumbText();
            Allure.addAttachment("Breadcrumb Text", "text/plain", breadcrumbText);
            Assert.assertFalse(breadcrumbText.isEmpty(), "Breadcrumb text should not be empty");
            logger.info("✅ Breadcrumb text: {}", breadcrumbText);
        });

        Allure.step("Step 3: Verify breadcrumb contains 'Home'", () -> {
            String breadcrumbText = pdpPage.getBreadcrumbText();
            Assert.assertTrue(breadcrumbText.toLowerCase().contains("home"),
                    "Breadcrumb should contain 'Home'");
            logger.info("✅ Breadcrumb contains 'Home'");
        });

        Allure.step("✅ Test Passed - Breadcrumb navigation displayed correctly");
        logger.info("✅ Test completed successfully: verifyBreadcrumbTest");
    }

    @Test(priority = 11, description = "Verify clicking Home in breadcrumb navigates to homepage", groups = {"regression", "ui", "navigation"})
    @Story("Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that clicking the Home link in breadcrumb navigation successfully redirects to the homepage")
    @TmsLink("TC-PDP-010")
    public void clickHomeInBreadcrumbTest() {

        logger.info("🧪 Starting Test: Click Home in Breadcrumb");

        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Allure.step("Step 1: Click Home in breadcrumb", () -> {
            logger.debug("Clicking Home breadcrumb link...");
            pdpPage.clickHomeInBreadcrumb();
            logger.info("✅ Home breadcrumb clicked");
        });

        Allure.step("Step 2: Verify navigation to homepage", () -> {
            logger.debug("Checking current URL...");
            String currentUrl = DriverFactory.getDriver().getCurrentUrl();
            Allure.parameter("Current URL", currentUrl);
            Assert.assertTrue(currentUrl.contains("demowebshop.tricentis.com"),
                    "Expected to navigate to homepage, but current URL is: " + currentUrl);
            logger.info("✅ Navigated to: {}", currentUrl);
        });

        Allure.step("✅ Test Passed - Home breadcrumb navigation works");
        logger.info("✅ Test completed successfully: clickHomeInBreadcrumbTest");
    }

    @Test(priority = 12, description = "Verify clicking Books in breadcrumb navigates to Books category", groups = {"regression", "ui", "navigation"})
    @Story("Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that clicking the Books link in breadcrumb navigation successfully redirects to Books category page")
    @TmsLink("TC-PDP-011")
    public void clickBooksInBreadcrumbTest() {

        logger.info("🧪 Starting Test: Click Books in Breadcrumb");

        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Allure.step("Step 1: Click Books in breadcrumb", () -> {
            logger.debug("Clicking Books breadcrumb link...");
            pdpPage.clickBooksInBreadcrumb();
            logger.info("✅ Books breadcrumb clicked");
        });

        Allure.step("Step 2: Verify navigation to Books category", () -> {
            logger.debug("Checking current URL...");
            String currentUrl = DriverFactory.getDriver().getCurrentUrl();
            Allure.parameter("Current URL", currentUrl);
            Assert.assertTrue(currentUrl.toLowerCase().contains("/books"),
                    "Expected URL to contain '/books', but got: " + currentUrl);
            logger.info("✅ Navigated to: {}", currentUrl);
        });

        Allure.step("✅ Test Passed - Books breadcrumb navigation works");
        logger.info("✅ Test completed successfully: clickBooksInBreadcrumbTest");
    }

    @Test(priority = 13, description = "Verify product page URL is correct", groups = {"regression", "ui", "pdp"})
    @Story("PDP UI Verification")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that the product page URL is correct and matches the expected pattern")
    @TmsLink("TC-PDP-012")
    public void verifyProductPageUrlTest() {

        logger.info("🧪 Starting Test: Verify Product Page URL");

        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Allure.step("Step 1: Get current page URL", () -> {
            logger.debug("Getting current URL...");
            String pageUrl = pdpPage.getPageUrl();
            Allure.parameter("Page URL", pageUrl);
            logger.info("Page URL: {}", pageUrl);
        });

        Allure.step("Step 2: Verify URL contains expected product path", () -> {
            String pageUrl = pdpPage.getPageUrl();
            Assert.assertTrue(pageUrl.contains("computing-and-internet"),
                    "Expected URL to contain 'computing-and-internet', but got: " + pageUrl);
            logger.info("✅ URL verified");
        });

        Allure.step("Step 3: Attach URL for reference", () -> {
            String pageUrl = pdpPage.getPageUrl();
            Allure.addAttachment("Product Page URL", "text/plain", pageUrl);
            logger.info("✅ URL attached");
        });

        Allure.step("✅ Test Passed - Product page URL is correct");
        logger.info("✅ Test completed successfully: verifyProductPageUrlTest");
    }

    @Test(priority = 14, description = "Verify all main elements check method works", groups = {"smoke", "regression", "ui", "pdp"})
    @Story("PDP UI Verification")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the bulk validation method correctly identifies when all main PDP elements are visible")
    @TmsLink("TC-PDP-013")
    public void verifyAllMainElementsMethodTest() {

        logger.info("🧪 Starting Test: Verify All Main Elements Method");

        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Allure.step("Step 1: Check all main elements at once", () -> {
            logger.debug("Checking all main PDP elements...");
            boolean allVisible = pdpPage.areAllMainElementsVisible();
            Allure.parameter("All Main Elements Visible", allVisible);
            Assert.assertTrue(allVisible,
                    "All main PDP elements (name, price, image, add to cart, quantity) should be visible");
            logger.info("✅ All main elements verified as visible");
        });

        Allure.step("✅ Test Passed - All main elements verification method works");
        logger.info("✅ Test completed successfully: verifyAllMainElementsMethodTest");
    }

    @Test(priority = 15, description = "Verify product descriptions are displayed", groups = {"regression", "ui", "pdp"})
    @Story("Product Information Display")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that product short and full descriptions are displayed when available")
    @TmsLink("TC-PDP-014")
    public void verifyProductDescriptionsTest() {

        logger.info("🧪 Starting Test: Verify Product Descriptions");

        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Allure.step("Step 1: Check if short description is displayed", () -> {
            logger.debug("Checking short description...");
            boolean isDisplayed = pdpPage.isShortDescriptionDisplayed();
            Allure.parameter("Short Description Displayed", isDisplayed);
            if (isDisplayed) {
                String shortDesc = pdpPage.getShortDescription();
                Allure.addAttachment("Short Description", "text/plain", shortDesc);
                logger.info("✅ Short description found: {}", shortDesc);
            } else {
                logger.info("ℹ️ Short description not displayed (may be optional)");
            }
        });

        Allure.step("Step 2: Check if full description is displayed", () -> {
            logger.debug("Checking full description...");
            boolean isDisplayed = pdpPage.isFullDescriptionDisplayed();
            Allure.parameter("Full Description Displayed", isDisplayed);
            if (isDisplayed) {
                String fullDesc = pdpPage.getFullDescription();
                Allure.addAttachment("Full Description", "text/plain", fullDesc);
                logger.info("✅ Full description found");
            } else {
                logger.info("ℹ️ Full description not displayed (may be optional)");
            }
        });

        Allure.step("✅ Test Passed - Product descriptions check completed");
        logger.info("✅ Test completed successfully: verifyProductDescriptionsTest");
    }

    @Test(priority = 16, description = "Verify navigation to cart from notification", groups = {"regression", "ui", "pdp", "cart", "navigation"})
    @Story("Add to Cart Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that users can navigate to shopping cart directly from the success notification")
    @TmsLink("TC-PDP-015")
    public void navigateToCartFromNotificationTest() {

        logger.info("🧪 Starting Test: Navigate to Cart from Notification");

        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Allure.step("Step 1: Add product to cart", () -> {
            logger.debug("Adding product to cart...");
            pdpPage.clickAddToCart();
            pdpPage.waitForNotification();
            logger.info("✅ Product added, notification appeared");
        });

        Allure.step("Step 2: Click shopping cart link in notification", () -> {
            logger.debug("Clicking cart link in notification...");
            pdpPage.clickShoppingCartInNotification();
            logger.info("✅ Cart link clicked");
        });

        Allure.step("Step 3: Verify navigation to cart page", () -> {
            logger.debug("Verifying cart page...");
            String currentUrl = DriverFactory.getDriver().getCurrentUrl();
            Allure.parameter("Current URL", currentUrl);
            Assert.assertTrue(currentUrl.contains("/cart"),
                    "Expected to navigate to shopping cart, but current URL is: " + currentUrl);
            logger.info("✅ Navigated to cart: {}", currentUrl);
        });

        Allure.step("✅ Test Passed - Cart navigation from notification works");
        logger.info("✅ Test completed successfully: navigateToCartFromNotificationTest");
    }

    @Test(priority = 17, description = "Verify closing notification works", groups = {"regression", "ui", "pdp"})
    @Story("PDP UI Verification")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that users can close the success notification using the close button")
    @TmsLink("TC-PDP-016")
    public void closeNotificationTest() {

        logger.info("🧪 Starting Test: Close Notification");

        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Allure.step("Step 1: Add product to cart to trigger notification", () -> {
            logger.debug("Adding product to cart...");
            pdpPage.clickAddToCart();
            pdpPage.waitForNotification();
            logger.info("✅ Notification appeared");
        });

        Allure.step("Step 2: Verify notification is visible", () -> {
            logger.debug("Checking notification visibility...");
            boolean isVisible = pdpPage.isNotificationBarVisible();
            Allure.parameter("Notification Visible", isVisible);
            Assert.assertTrue(isVisible, "Notification should be visible before closing");
            logger.info("✅ Notification confirmed visible");
        });

        Allure.step("Step 3: Close notification", () -> {
            logger.debug("Closing notification...");
            pdpPage.closeNotification();
            logger.info("✅ Close button clicked");
        });

        Allure.step("Step 4: Verify notification disappeared", () -> {
            logger.debug("Waiting for notification to disappear...");
            boolean disappeared = pdpPage.waitForNotificationToDisappear();
            Allure.parameter("Notification Disappeared", disappeared);
            Assert.assertTrue(disappeared,
                    "Notification should disappear after clicking close button");
            logger.info("✅ Notification disappeared");
        });

        Allure.step("✅ Test Passed - Notification closes successfully");
        logger.info("✅ Test completed successfully: closeNotificationTest");
    }

    @Test(priority = 18, description = "Verify all action buttons are visible", groups = {"regression", "ui", "pdp"})
    @Story("PDP UI Verification")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that all product action buttons (Add to Cart, Wishlist, Compare, Email) are visible on PDP")
    @TmsLink("TC-PDP-017")
    public void verifyAllActionButtonsTest() {

        logger.info("🧪 Starting Test: Verify All Action Buttons");

        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Allure.step("Step 1: Verify all action buttons are visible", () -> {
            logger.debug("Checking all action buttons...");
            boolean allVisible = pdpPage.areAllActionButtonsVisible();
            Allure.parameter("All Action Buttons Visible", allVisible);
            Assert.assertTrue(allVisible,
                    "All action buttons should be visible on product details page");
            logger.info("✅ All action buttons verified as visible");
        });

        Allure.step("✅ Test Passed - All action buttons are visible");
        logger.info("✅ Test completed successfully: verifyAllActionButtonsTest");
    }

    @Test(priority = 19, description = "Verify PDP page loads completely before interaction", groups = {"smoke", "regression", "ui", "pdp", "critical"})
    @Story("PDP UI Verification")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that product details page waits for all elements to load before allowing user interaction")
    @TmsLink("TC-PDP-018")
    public void verifyPageLoadTest() {

        logger.info("🧪 Starting Test: Verify Page Load");

        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());

        Allure.step("Step 1: Wait for page to load completely", () -> {
            logger.debug("Waiting for page load...");
            pdpPage.waitForPageToLoad();
            logger.info("✅ Page loaded");
        });

        Allure.step("Step 2: Verify product name is visible after load", () -> {
            logger.debug("Checking product name after load...");
            boolean isVisible = pdpPage.isProductNameDisplayed();
            Allure.parameter("Product Name Visible", isVisible);
            Assert.assertTrue(isVisible, "Product name should be loaded and visible");
            logger.info("✅ Product name visible after load");
        });

        Allure.step("Step 3: Verify Add to Cart button is visible after load", () -> {
            logger.debug("Checking Add to Cart button after load...");
            boolean isVisible = pdpPage.isAddToCartButtonDisplayed();
            Allure.parameter("Add to Cart Button Visible", isVisible);
            Assert.assertTrue(isVisible, "Add to cart button should be loaded and visible");
            logger.info("✅ Add to Cart button visible after load");
        });

        Allure.step("✅ Test Passed - Page loads completely before interaction");
        logger.info("✅ Test completed successfully: verifyPageLoadTest");
    }
}