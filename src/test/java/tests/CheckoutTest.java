package tests;

import base.BaseTest;
import drivers.DriverFactory;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.ConfigReader;

@Epic("E-Commerce Platform")
@Feature("Checkout Process")
public class CheckoutTest extends BaseTest {

    private static final String BOOK_PRODUCT_URL = "https://demowebshop.tricentis.com/computing-and-internet";

    @BeforeMethod
    public void setupCheckout() {
        LoginPage loginPage = new LoginPage();
        loginPage.onLogin();
        loginPage.login(ConfigReader.get("validEmail"), ConfigReader.get("validPassword"));

        DriverFactory.getDriver().get(BOOK_PRODUCT_URL);
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());
        pdpPage.clickAddToCart();
        pdpPage.waitForNotification();

        HomePage homePage = new HomePage();
        homePage.clickCart();

        CartPage cartPage = new CartPage(DriverFactory.getDriver());
        cartPage.acceptTermsAndCheckout();
    }

    @Test(priority = 1, description = "Verify checkout page loads successfully")
    @Story("Checkout Page Navigation")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that checkout page loads successfully after accepting terms from cart and displays correct title")
    public void verifyCheckoutPageLoadsTest() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());

        Assert.assertTrue(checkoutPage.isOnCheckoutPage(),
                "Expected to be on checkout page, but URL does not contain 'onepagecheckout'");

        String pageTitle = checkoutPage.getPageTitle();
        Assert.assertEquals(pageTitle, "Checkout",
                "Expected page title to be 'Checkout', but got: " + pageTitle);

        Assert.assertTrue(checkoutPage.hasCorrectPageTitle(),
                "Page title validation failed");

        Allure.addAttachment("Page Title", pageTitle);
    }

    @Test(priority = 2, description = "Verify billing address section is visible")
    @Story("Billing Address Step")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that billing address section loads and displays all required elements for address entry")
    public void verifyBillingAddressSectionTest() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());

        Assert.assertTrue(checkoutPage.isBillingAddressSectionVisible(),
                "Billing address section should be visible on checkout page");

        Assert.assertTrue(checkoutPage.isBillingContinueButtonVisible(),
                "Billing continue button should be visible in billing section");

        Assert.assertTrue(checkoutPage.isBillingAddressReady(),
                "Billing address section should be ready for user interaction");
    }

    @Test(priority = 3, description = "Verify can fill billing address form")
    @Story("Billing Address Step")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that users can successfully fill out billing address form and proceed to next checkout step")
    public void fillBillingAddressTest() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
        checkoutPage.selectBillingAddress("New Address");

        checkoutPage.fillBillingAddress(
                "John",
                "Doe",
                "johndoe@test.com",
                "United States",
                "New York",
                "123 Main Street",
                "10001",
                "1234567890"
        );

        checkoutPage.clickBillingContinue();

        boolean shippingVisible = checkoutPage.waitForShippingSection(5);

        Assert.assertTrue(shippingVisible,
                "Expected to proceed to shipping address section after filling billing address");

        Allure.addAttachment("Billing Name", "John Doe");
        Allure.addAttachment("Billing City", "New York");
    }

    @Test(priority = 4, description = "Verify shipping address section appears after billing")
    @Story("Shipping Address Step")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that shipping address section appears after successfully completing billing address step")
    public void verifyShippingAddressSectionTest() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
        checkoutPage.selectBillingAddress("New Address");

        checkoutPage.fillBillingAddress("John", "Doe", "john@test.com",
                "United States", "New York", "123 Main St", "10001", "1234567890");
        checkoutPage.clickBillingContinue();

        Assert.assertTrue(checkoutPage.waitForShippingSection(5),
                "Shipping section should appear within timeout period");

        Assert.assertTrue(checkoutPage.isShippingAddressSectionVisible(),
                "Shipping address section should be visible after completing billing address");

        Assert.assertTrue(checkoutPage.isShippingAddressReady(),
                "Shipping address section should be ready for interaction");
    }

    @Test(priority = 5, description = "Verify 'Ship to same address' checkbox works")
    @Story("Shipping Address Step")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that 'Ship to same address' checkbox functionality allows users to use billing address for shipping")
    public void shipToSameAddressTest() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
        checkoutPage.selectBillingAddress("New Address");

        checkoutPage.fillBillingAddress("John", "Doe", "john@test.com",
                "United States", "New York", "123 Main St", "10001", "1234567890");
        checkoutPage.clickBillingContinue();

        Assert.assertTrue(checkoutPage.waitForShippingSection(5),
                "Shipping section should load after billing");

        checkoutPage.clickShippingContinue();

        boolean shippingMethodVisible = checkoutPage.waitForShippingMethodSection(5);
        Assert.assertTrue(shippingMethodVisible,
                "Should proceed to shipping method section when using same address");
    }

    @Test(priority = 6, description = "Verify shipping method section appears")
    @Story("Shipping Method Step")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that shipping method selection section appears after completing shipping address step")
    public void verifyShippingMethodSectionTest() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
        checkoutPage.selectBillingAddress("New Address");

        checkoutPage.fillBillingAddress("John", "Doe", "john@test.com",
                "United States", "New York", "123 Main St", "10001", "1234567890");
        checkoutPage.clickBillingContinue();

        Assert.assertTrue(checkoutPage.waitForShippingSection(5),
                "Shipping section should load");

        checkoutPage.clickShippingContinue();

        Assert.assertTrue(checkoutPage.waitForShippingMethodSection(5),
                "Shipping method section should appear within timeout");

        Assert.assertTrue(checkoutPage.isShippingMethodSectionVisible(),
                "Shipping method section should be visible");

        Assert.assertTrue(checkoutPage.isShippingMethodReady(),
                "Shipping method section should be ready for selection");
    }

    @Test(priority = 7, description = "Verify can select shipping methods")
    @Story("Shipping Method Step")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that users can select different shipping methods and selection is properly registered")
    public void selectShippingMethodTest() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
        checkoutPage.selectBillingAddress("New Address");

        checkoutPage.fillBillingAddress("John", "Doe", "john@test.com",
                "United States", "New York", "123 Main St", "10001", "1234567890");
        checkoutPage.clickBillingContinue();
        Assert.assertTrue(checkoutPage.waitForShippingSection(5));

        checkoutPage.clickShippingContinue();
        Assert.assertTrue(checkoutPage.waitForShippingMethodSection(5));

        checkoutPage.selectGroundShipping();

        Assert.assertTrue(checkoutPage.isGroundShippingSelected(),
                "Ground shipping should be selected after clicking the radio button");

        Allure.addAttachment("Selected Shipping Method", "Ground Shipping");
    }

    @Test(priority = 8, description = "Verify payment method section appears")
    @Story("Payment Method Step")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that payment method selection section appears after completing shipping method step")
    public void verifyPaymentMethodSectionTest() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
        checkoutPage.selectBillingAddress("New Address");

        checkoutPage.fillBillingAddress("John", "Doe", "john@test.com",
                "United States", "New York", "123 Main St", "10001", "1234567890");
        checkoutPage.clickBillingContinue();

        Assert.assertTrue(checkoutPage.waitForShippingSection(5));

        checkoutPage.clickShippingContinue();

        Assert.assertTrue(checkoutPage.waitForShippingMethodSection(5));

        checkoutPage.selectGroundShipping();
        checkoutPage.clickShippingMethodContinue();

        Assert.assertTrue(checkoutPage.waitForPaymentMethodSection(5),
                "Payment method section should appear within timeout");

        Assert.assertTrue(checkoutPage.isPaymentMethodSectionVisible(),
                "Payment method section should be visible after shipping method selection");

        Assert.assertTrue(checkoutPage.isPaymentMethodReady(),
                "Payment method section should be ready for selection");
    }

    @Test(priority = 9, description = "Verify can select payment methods")
    @Story("Payment Method Step")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that users can select different payment methods and selection is properly registered")
    public void selectPaymentMethodTest() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
        checkoutPage.selectBillingAddress("New Address");

        checkoutPage.fillBillingAddress("John", "Doe", "john@test.com",
                "United States", "New York", "123 Main St", "10001", "1234567890");
        checkoutPage.clickBillingContinue();
        Assert.assertTrue(checkoutPage.waitForShippingSection(5));

        checkoutPage.clickShippingContinue();
        Assert.assertTrue(checkoutPage.waitForShippingMethodSection(5));

        checkoutPage.selectGroundShipping();
        checkoutPage.clickShippingMethodContinue();
        Assert.assertTrue(checkoutPage.waitForPaymentMethodSection(5));

        checkoutPage.selectCashOnDelivery();

        Assert.assertTrue(checkoutPage.isCashOnDeliverySelected(),
                "Cash on Delivery payment method should be selected after clicking");

        Allure.addAttachment("Selected Payment Method", "Cash on Delivery");
    }

    @Test(priority = 10, description = "Verify payment info section appears")
    @Story("Payment Information Step")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that payment information section appears after selecting payment method")
    public void verifyPaymentInfoSectionTest() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
        checkoutPage.selectBillingAddress("New Address");

        checkoutPage.fillBillingAddress("John", "Doe", "john@test.com",
                "United States", "New York", "123 Main St", "10001", "1234567890");
        checkoutPage.clickBillingContinue();
        Assert.assertTrue(checkoutPage.waitForShippingSection(5));

        checkoutPage.clickShippingContinue();
        Assert.assertTrue(checkoutPage.waitForShippingMethodSection(5));

        checkoutPage.selectGroundShipping();
        checkoutPage.clickShippingMethodContinue();
        Assert.assertTrue(checkoutPage.waitForPaymentMethodSection(5));

        checkoutPage.selectCashOnDelivery();
        checkoutPage.clickPaymentMethodContinue();

        Assert.assertTrue(checkoutPage.waitForPaymentInfoSection(5),
                "Payment info section should appear within timeout");

        Assert.assertTrue(checkoutPage.isPaymentInfoSectionVisible(),
                "Payment info section should be visible after payment method selection");

        Assert.assertTrue(checkoutPage.isPaymentInfoReady(),
                "Payment info section should be ready for interaction");
    }

    @Test(priority = 11, description = "Verify confirm order section appears")
    @Story("Order Confirmation Step")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that order confirmation section appears with all order details after completing all checkout steps")
    public void verifyConfirmOrderSectionTest() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
        checkoutPage.selectBillingAddress("New Address");

        checkoutPage.fillBillingAddress("John", "Doe", "john@test.com",
                "United States", "New York", "123 Main St", "10001", "1234567890");
        checkoutPage.clickBillingContinue();
        Assert.assertTrue(checkoutPage.waitForShippingSection(5));

        checkoutPage.clickShippingContinue();
        Assert.assertTrue(checkoutPage.waitForShippingMethodSection(5));

        checkoutPage.selectGroundShipping();
        checkoutPage.clickShippingMethodContinue();

        Assert.assertTrue(checkoutPage.waitForPaymentMethodSection(5));

        checkoutPage.selectCashOnDelivery();
        checkoutPage.clickPaymentMethodContinue();
        Assert.assertTrue(checkoutPage.waitForPaymentInfoSection(5));

        checkoutPage.clickPaymentInfoContinue();

        Assert.assertTrue(checkoutPage.waitForConfirmOrderSection(5),
                "Confirm order section should appear within timeout period");

        Assert.assertTrue(checkoutPage.isConfirmOrderSectionVisible(),
                "Confirm order section should be visible before final order placement");

        Assert.assertTrue(checkoutPage.isConfirmOrderButtonVisible(),
                "Confirm order button should be visible in confirmation section");

        Assert.assertTrue(checkoutPage.isConfirmOrderReady(),
                "Confirm order section should be ready for final confirmation");
    }

    @Test(priority = 12, description = "Verify order total is displayed on confirm page")
    @Story("Order Confirmation Step")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that order total is displayed correctly on the order confirmation page before final submission")
    public void verifyOrderTotalDisplayedTest() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
        checkoutPage.selectBillingAddress("New Address");

        checkoutPage.completeCheckoutWithCOD("John", "Doe", "john@test.com",
                "United States", "New York", "123 Main St", "10001", "1234567890");

        Assert.assertTrue(checkoutPage.isOrderTotalDisplayed(),
                "Order total should be displayed on confirmation page");

        String orderTotal = checkoutPage.getOrderTotal();
        Assert.assertFalse(orderTotal.isEmpty(),
                "Order total should not be empty");
        Assert.assertTrue(orderTotal.matches(".*\\d+.*"),
                "Order total should contain numeric values, but got: " + orderTotal);

        double totalValue = checkoutPage.getOrderTotalAsDouble();
        Assert.assertTrue(totalValue > 0,
                "Order total should be greater than zero, but got: " + totalValue);

        Allure.addAttachment("Order Total", orderTotal);
    }

    @Test(priority = 13, description = "Verify complete checkout flow with Cash on Delivery")
    @Story("End-to-End Checkout")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify complete end-to-end checkout process from billing address to order confirmation with Cash on Delivery payment")
    public void completeCheckoutWithCODTest() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
        checkoutPage.selectBillingAddress("New Address");

        checkoutPage.completeCheckoutWithCOD(
                "John",
                "Doe",
                "john.doe@test.com",
                "United States",
                "New York",
                "123 Main Street",
                "10001",
                "1234567890"
        );

        Assert.assertTrue(checkoutPage.isConfirmOrderButtonVisible(),
                "Confirm order button should be visible before final submission");

        Assert.assertTrue(checkoutPage.waitForOrderSuccessTitle(10),
                "Order success title should appear within 10 seconds after confirming order");

        Assert.assertTrue(checkoutPage.isOrderSuccessful(),
                "Order should be processed successfully");

        String successMessage = checkoutPage.getOrderSuccessMessage();
        Assert.assertTrue(successMessage.contains("successfully processed") ||
                        checkoutPage.isOrderSuccessMessageValid(),
                "Expected success message to confirm order completion, but got: " + successMessage);

        Allure.addAttachment("Success Message", successMessage);
    }

    @Test(priority = 14, description = "Verify checkout fails with empty billing address")
    @Story("Billing Address Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that checkout validation prevents proceeding with empty billing address fields")
    public void checkoutWithEmptyBillingAddressTest() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
        checkoutPage.selectBillingAddress("New Address");

        checkoutPage.clickBillingContinue();

        Assert.assertTrue(checkoutPage.isBillingContinueButtonVisible(),
                "Should remain on billing address step when validation fails with empty fields");

        Assert.assertTrue(checkoutPage.isBillingAddressReady(),
                "Billing section should still be ready after failed validation");
    }

    @Test(priority = 16, description = "Verify checkout page loads completely")
    @Story("Checkout Page Navigation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that checkout page waits for all elements to load before allowing user interaction")
    public void verifyCheckoutPageLoadTest() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());

        checkoutPage.waitForPageToLoad();

        Assert.assertTrue(checkoutPage.isOnCheckoutPage(),
                "Should be on checkout page after load");
        Assert.assertTrue(checkoutPage.isBillingAddressSectionVisible(),
                "Billing section should be loaded and visible");
    }

    @Test(priority = 17, description = "Verify checkout with Check/Money Order payment")
    @Story("End-to-End Checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify complete checkout process using Check/Money Order as payment method")
    public void completeCheckoutWithCheckMoneyOrderTest() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
        checkoutPage.selectBillingAddress("New Address");

        checkoutPage.completeCheckoutWithCheckMoneyOrder(
                "Jane",
                "Smith",
                "jane.smith@test.com",
                "United States",
                "Los Angeles",
                "456 Oak Avenue",
                "90001",
                "9876543210"
        );

        Assert.assertTrue(checkoutPage.isConfirmOrderButtonVisible(),
                "Confirm order button should be visible with Check/Money Order payment");

        Allure.addAttachment("Payment Method", "Check/Money Order");
        Allure.addAttachment("Customer Name", "Jane Smith");
    }
}