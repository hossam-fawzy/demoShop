package tests;

import base.BaseTest;
import drivers.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.ConfigReader;

public class CheckoutTest extends BaseTest {

    private static final String BOOK_PRODUCT_URL = "https://demowebshop.tricentis.com/computing-and-internet";

    @BeforeMethod
    public void setupCheckout() {
        // SETUP: Add item to cart and proceed to checkout
        // This ensures we start at checkout page for each test

        LoginPage loginPage = new LoginPage();
        loginPage.onLogin();
        loginPage.login(ConfigReader.get("validEmail"), ConfigReader.get("validPassword"));

        // Add product to cart
        DriverFactory.getDriver().get(BOOK_PRODUCT_URL);
        PDPPage pdpPage = new PDPPage(DriverFactory.getDriver());
        pdpPage.clickAddToCart();
        pdpPage.waitForNotification();

        // Go to cart
        HomePage homePage = new HomePage();
        homePage.clickCart();


        CartPage cartPage = new CartPage(DriverFactory.getDriver());

        // Accept terms and go to checkout
        cartPage.acceptTermsAndCheckout();
    }

//    @Test(priority = 1, description = "Verify checkout page loads successfully")
//    public void verifyCheckoutPageLoadsTest() {
//        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
//
//        // VERIFY: We're on checkout page
//        Assert.assertTrue(checkoutPage.isOnCheckoutPage(), "Should be on checkout page");
//
//        // VERIFY: Page title is displayed
//        String pageTitle = checkoutPage.getPageTitle();
//        Assert.assertEquals(pageTitle, "Checkout", "Page title should be 'Checkout'");
//
//
//        System.out.println("Checkout page loaded successfully");
//    }
//
//    @Test(priority = 2, description = "Verify billing address section is visible")
//    public void verifyBillingAddressSectionTest() {
//        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
//
//        // VERIFY: Billing address section is visible
//        Assert.assertTrue(checkoutPage.isBillingAddressSectionVisible(),
//                "Billing address section should be visible");
//
//        // VERIFY: Billing continue button is visible
//        Assert.assertTrue(checkoutPage.isBillingContinueButtonVisible(),
//                "Billing continue button should be visible");
//
//        System.out.println("Billing address section is visible");
//    }
//
//    @Test(priority = 3, description = "Verify can fill billing address form")
//    public void fillBillingAddressTest() {
//        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
//        checkoutPage.selectBillingAddress("New Address");
//
//        // PERFORM: Fill billing address
//        checkoutPage.fillBillingAddress(
//                "John",                    // firstName
//                "Doe",                     // lastName
//                "johndoe@test.com",        // email
//                "United States",           // country
//                "New York",                // city
//                "123 Main Street",         // address
//                "10001",                   // zipCode
//                "1234567890"               // phoneNumber
//        );
//
//        System.out.println("Billing address filled successfully");
//
//        // VERIFY: Can proceed to next step
//        checkoutPage.clickBillingContinue();
//
//        // Wait for shipping section to appear
//        boolean shippingVisible = checkoutPage.waitForShippingSection(5);
//
//        Assert.assertTrue(shippingVisible, "Should proceed to shipping address section");
//    }
//
//    @Test(priority = 4, description = "Verify shipping address section appears after billing")
//    public void verifyShippingAddressSectionTest() {
//        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
//        checkoutPage.selectBillingAddress("New Address");
//
//        // PERFORM: Complete billing address
//        checkoutPage.fillBillingAddress("John", "Doe", "john@test.com",
//                "United States", "New York", "123 Main St", "10001", "1234567890");
//        checkoutPage.clickBillingContinue();
//
//        // WAIT: For shipping section to appear
//        Assert.assertTrue(checkoutPage.waitForShippingSection(5));
//
//        // VERIFY: Shipping address section is visible
//        Assert.assertTrue(checkoutPage.isShippingAddressSectionVisible(),
//                "Shipping address section should be visible after billing");
//
//        System.out.println("Shipping address section appeared");
//    }
//
//    @Test(priority = 5, description = "Verify 'Ship to same address' checkbox works")
//    public void shipToSameAddressTest() {
//        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
//        checkoutPage.selectBillingAddress("New Address");
//
//        // PERFORM: Complete billing and go to shipping
//        checkoutPage.fillBillingAddress("John", "Doe", "john@test.com",
//                "United States", "New York", "123 Main St", "10001", "1234567890");
//        checkoutPage.clickBillingContinue();
//
//        // WAIT: For shipping section
//        Assert.assertTrue(checkoutPage.waitForShippingSection(5));
//        checkoutPage.clickShippingContinue();
//
//        System.out.println("'Ship to same address' checkbox works correctly");
//    }
//
//    @Test(priority = 6, description = "Verify shipping method section appears")
//    public void verifyShippingMethodSectionTest() {
//        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
//        checkoutPage.selectBillingAddress("New Address");
//
//        // PERFORM: Complete billing and shipping
//        checkoutPage.fillBillingAddress("John", "Doe", "john@test.com",
//                "United States", "New York", "123 Main St", "10001", "1234567890");
//        checkoutPage.clickBillingContinue();
//
//        Assert.assertTrue(checkoutPage.waitForShippingSection(5));
//
//        checkoutPage.clickShippingContinue();
//
//        // WAIT: For shipping method section
//        Assert.assertTrue(checkoutPage.waitForShippingSection(5));
//
//        // VERIFY: Shipping method section is visible
//        Assert.assertTrue(checkoutPage.isShippingMethodSectionVisible(),
//                "Shipping method section should bevisible");
//        System.out.println("Shipping method section appeared");
//    }
//
//    @Test(priority = 7, description = "Verify can select shipping methods")
//    public void selectShippingMethodTest() {
//        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
//         checkoutPage.selectBillingAddress("New Address");
//        // PERFORM: Navigate to shipping method section
//        checkoutPage.fillBillingAddress("John", "Doe", "john@test.com",
//                "United States", "New York", "123 Main St", "10001", "1234567890");
//        checkoutPage.clickBillingContinue();
//        Assert.assertTrue(checkoutPage.waitForShippingSection(5));
//
//        checkoutPage.clickShippingContinue();
//
//        Assert.assertTrue(checkoutPage.waitForShippingSection(5));
//
//        // PERFORM: Select Ground shipping
//        checkoutPage.selectGroundShipping();
//
//        // VERIFY: Ground shipping is selected
//        Assert.assertTrue(checkoutPage.isGroundShippingSelected(),
//                "Ground shipping should be selected");
//
//        System.out.println("Shipping method selected successfully");
//    }
//
//    @Test(priority = 8, description = "Verify payment method section appears")
//    public void verifyPaymentMethodSectionTest() {
//        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
//         checkoutPage.selectBillingAddress("New Address");
//        // PERFORM: Navigate to payment method section
//        checkoutPage.fillBillingAddress("John", "Doe", "john@test.com",
//                "United States", "New York", "123 Main St", "10001", "1234567890");
//        checkoutPage.clickBillingContinue();
//
//        Assert.assertTrue(checkoutPage.waitForShippingSection(5));
//
//        checkoutPage.clickShippingContinue();
//
//        Assert.assertTrue(checkoutPage.waitForShippingMethodSection(5));
//
//        checkoutPage.selectGroundShipping();
//        checkoutPage.clickShippingMethodContinue();
//
//        // WAIT: For payment method section
//        Assert.assertTrue(checkoutPage.waitForPaymentMethodSection(5));
//
//        // VERIFY: Payment method section is visible
//        Assert.assertTrue(checkoutPage.isPaymentMethodSectionVisible(),
//                "Payment method section should be visible");
//
//        System.out.println("Payment method section appeared");
//    }

//    @Test(priority = 9, description = "Verify can select payment methods")
//    public void selectPaymentMethodTest() {
//        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
//         checkoutPage.selectBillingAddress("New Address");
//        // PERFORM: Navigate to payment method section
//        checkoutPage.fillBillingAddress("John", "Doe", "john@test.com",
//                "United States", "New York", "123 Main St", "10001", "1234567890");
//        checkoutPage.clickBillingContinue();
//        Assert.assertTrue(checkoutPage.waitForShippingSection(5));
//
//        checkoutPage.clickShippingContinue();
//        Assert.assertTrue(checkoutPage.waitForShippingMethodSection(5));
//
//        checkoutPage.selectGroundShipping();
//        checkoutPage.clickShippingMethodContinue();
//        Assert.assertTrue(checkoutPage.waitForPaymentMethodSection(5));
//
//        // PERFORM: Select Cash on Delivery
//        checkoutPage.selectCashOnDelivery();
//
//        // VERIFY: Cash on Delivery is selected
//        Assert.assertTrue(checkoutPage.isCashOnDeliverySelected(),
//                "Cash on Delivery should be selected");
//
//        System.out.println("Payment method selected successfully");
//    }
//
//    @Test(priority = 10, description = "Verify payment info section appears")
//    public void verifyPaymentInfoSectionTest() {
//        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
//         checkoutPage.selectBillingAddress("New Address");
//        // PERFORM: Navigate through all steps to payment info
//        checkoutPage.fillBillingAddress("John", "Doe", "john@test.com",
//                "United States", "New York", "123 Main St", "10001", "1234567890");
//        checkoutPage.clickBillingContinue();
//        Assert.assertTrue(checkoutPage.waitForShippingSection(5));
//
//
//        checkoutPage.clickShippingContinue();
//        Assert.assertTrue(checkoutPage.waitForShippingMethodSection(5));
//
//        checkoutPage.selectGroundShipping();
//        checkoutPage.clickShippingMethodContinue();
//        Assert.assertTrue(checkoutPage.waitForPaymentMethodSection(5));
//
//        checkoutPage.selectCashOnDelivery();
//        checkoutPage.clickPaymentMethodContinue();
//
//        // WAIT: For payment info section
//        Assert.assertTrue(checkoutPage.waitForPaymentInfoSection(5));
//
//        // VERIFY: Payment info section is visible
//        Assert.assertTrue(checkoutPage.isPaymentInfoSectionVisible(),
//                "Payment info section should be visible");
//
//        System.out.println("Payment info section appeared");
//    }
//
//    @Test(priority = 11, description = "Verify confirm order section appears")
//    public void verifyConfirmOrderSectionTest() {
//        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
//         checkoutPage.selectBillingAddress("New Address");
//        // PERFORM: Navigate through all steps to confirm order
//        checkoutPage.fillBillingAddress("John", "Doe", "john@test.com",
//                "United States", "New York", "123 Main St", "10001", "1234567890");
//        checkoutPage.clickBillingContinue();
//        Assert.assertTrue(checkoutPage.waitForShippingSection(5));
//
//        checkoutPage.clickShippingContinue();
//        Assert.assertTrue(checkoutPage.waitForShippingMethodSection(5));
//
//        checkoutPage.selectGroundShipping();
//        checkoutPage.clickShippingMethodContinue();
//
//        Assert.assertTrue(checkoutPage.waitForPaymentMethodSection(5));
//
//        checkoutPage.selectCashOnDelivery();
//        checkoutPage.clickPaymentMethodContinue();
//        Assert.assertTrue(checkoutPage.waitForPaymentInfoSection(5));
//
//
//        checkoutPage.clickPaymentInfoContinue();
//
//        // WAIT: For confirm order section
//        Assert.assertTrue(checkoutPage.waitForConfirmOrderSection(5),
//                "Confirm order section should be visible");
//
//
//        // VERIFY: Confirm order section is visible
//        Assert.assertTrue(checkoutPage.isConfirmOrderSectionVisible(),
//                "Confirm order section should be visible");
//
//        // VERIFY: Confirm order button is visible
//        Assert.assertTrue(checkoutPage.isConfirmOrderButtonVisible(),
//                "Confirm order button should be visible");
//
//        System.out.println("Confirm order section appeared");
//    }
//
//    @Test(priority = 12, description = "Verify order total is displayed on confirm page")
//    public void verifyOrderTotalDisplayedTest() {
//        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
//         checkoutPage.selectBillingAddress("New Address");
//        // PERFORM: Navigate to confirm order page
//        checkoutPage.completeCheckoutWithCOD("John", "Doe", "john@test.com",
//                "United States", "New York", "123 Main St", "10001", "1234567890");
//
//        // Note: completeCheckoutWithCOD goes to confirm page but doesn't click confirm
//
//        // VERIFY: Order total is displayed
//        Assert.assertTrue(checkoutPage.isOrderTotalDisplayed(),
//                "Order total should be displayed");
//
//        // GET: Order total value
//        String orderTotal = checkoutPage.getOrderTotal();
//        Assert.assertFalse(orderTotal.isEmpty(), "Order total should not be empty");
//
//        System.out.println("Order Total: " + orderTotal);
//    }
//
    @Test(priority = 13, description = "Verify complete checkout flow with Cash on Delivery")
    public void completeCheckoutWithCODTest() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
         checkoutPage.selectBillingAddress("New Address");
        // PERFORM: Complete full checkout process
        checkoutPage.completeCheckoutWithCOD(
                "John",                    // firstName
                "Doe",                     // lastName
                "john.doe@test.com",       // email
                "United States",           // country
                "New York",                // city
                "123 Main Street",         // address
                "10001",                   // zipCode
                "1234567890"               // phoneNumber
        );

        // At this point we're on confirm order page
        // VERIFY: Confirm order button is visible
        Assert.assertTrue(checkoutPage.isConfirmOrderButtonVisible(),
                "Confirm order button should be visible");



        // WAIT: For success page to load
        Assert.assertTrue(checkoutPage.waitForOrderSuccessTitle(10),
                "Order success title should be visible");




        // VERIFY: Success message is displayed
        String successMessage = checkoutPage.getOrderSuccessMessage();
        Assert.assertTrue(successMessage.contains("successfully processed"),
                "Success message should confirm order completion");

        // GET: Order number
//        String orderNumber = checkoutPage.getOrderNumber();
//        Assert.assertFalse(orderNumber.isEmpty(), "Order number should be generated");

        System.out.println("✅ Order completed successfully!");
        //System.out.println("Order Number: " + orderNumber);
        //System.out.println("Success Message: " + successMessage);
    }

    @Test(priority = 14, description = "Verify checkout fails with empty billing address")
    public void checkoutWithEmptyBillingAddressTest() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverFactory.getDriver());
         checkoutPage.selectBillingAddress("New Address");
        // PERFORM: Try to continue without filling billing address
        checkoutPage.clickBillingContinue();

        // VERIFY: Error message or validation appears
        // Note: Exact error handling varies by site
        // We verify that we're still on the same step (billing)
        Assert.assertTrue(checkoutPage.isBillingContinueButtonVisible(),
                "Should still be on billing address step when validation fails");

        System.out.println("Validation works - cannot proceed with empty fields");
    }


}