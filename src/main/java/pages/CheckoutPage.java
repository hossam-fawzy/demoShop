package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage extends BasePage {

    // Page Title & Steps Locators
    private final By pageTitle = By.xpath("//h1[normalize-space()='Checkout']");
    private final By checkoutProgressBar = By.xpath("//ol[@id='checkout-progress']");

    // Step 1: Billing Address Section Locators
    private final By billingAddressSection = By.id("billing-buttons-container");
    private final By billingAddressDropdown = By.id("billing-address-select");
    private final By newAddressButton = By.xpath("//input[@onclick='Billing.newAddress(event)']");

    // Billing Address Form Fields Locators
    private final By billingFirstName = By.id("BillingNewAddress_FirstName");
    private final By billingLastName = By.id("BillingNewAddress_LastName");
    private final By billingEmail = By.id("BillingNewAddress_Email");
    private final By billingCompany = By.id("BillingNewAddress_Company");
    private final By billingCountryDropdown = By.xpath("//select[@id='BillingNewAddress_CountryId']");
    private final By billingStateDropdown = By.id("BillingNewAddress_StateProvinceId");
    private final By billingCity = By.id("BillingNewAddress_City");
    private final By billingAddress1 = By.id("BillingNewAddress_Address1");
    private final By billingAddress2 = By.id("BillingNewAddress_Address2");
    private final By billingZipCode = By.id("BillingNewAddress_ZipPostalCode");
    private final By billingPhoneNumber = By.id("BillingNewAddress_PhoneNumber");
    private final By billingFaxNumber = By.id("BillingNewAddress_FaxNumber");

    // Billing Continue Button Locator
    private final By billingContinueButton = By.xpath("//input[@onclick='Billing.save()']");

    // Step 2: Shipping Address Section Locators
    private final By shippingAddressSection = By.id("shipping-buttons-container");
    private final By shippingAddressDropdown = By.id("shipping-address-select");
    private final By shipToSameAddressCheckbox = By.id("ShipToSameAddress");
    private final By pickupInStoreCheckbox = By.id("PickUpInStore");

    // Shipping Address Form Fields Locators
    private final By shippingFirstName = By.id("ShippingNewAddress_FirstName");
    private final By shippingLastName = By.id("ShippingNewAddress_LastName");
    private final By shippingEmail = By.id("ShippingNewAddress_Email");
    private final By shippingCompany = By.id("ShippingNewAddress_Company");
    private final By shippingCountryDropdown = By.id("ShippingNewAddress_CountryId");
    private final By shippingStateDropdown = By.id("ShippingNewAddress_StateProvinceId");
    private final By shippingCity = By.id("ShippingNewAddress_City");
    private final By shippingAddress1 = By.id("ShippingNewAddress_Address1");
    private final By shippingAddress2 = By.id("ShippingNewAddress_Address2");
    private final By shippingZipCode = By.id("ShippingNewAddress_ZipPostalCode");
    private final By shippingPhoneNumber = By.id("ShippingNewAddress_PhoneNumber");

    // Shipping Continue Button Locator
    private final By shippingContinueButton = By.xpath("//input[@onclick='Shipping.save()']");

    // Step 3: Shipping Method Section Locators
    private final By shippingMethodSection = By.id("shipping-method-buttons-container");
    private final By groundShippingRadio = By.id("shippingoption_0");
    private final By nextDayAirRadio = By.id("shippingoption_1");
    private final By secondDayAirRadio = By.id("shippingoption_2");
    private final By shippingMethodContinueButton = By.xpath("//input[@class='button-1 shipping-method-next-step-button']");

    // Step 4: Payment Method Section Locators
    private final By paymentMethodSection = By.id("payment-method-buttons-container");
    private final By cashOnDeliveryRadio = By.id("paymentmethod_0");
    private final By checkMoneyOrderRadio = By.id("paymentmethod_1");
    private final By creditCardRadio = By.id("paymentmethod_2");
    private final By purchaseOrderRadio = By.id("paymentmethod_3");
    private final By paymentMethodContinueButton = By.xpath("//input[@class='button-1 payment-method-next-step-button']");

    // Step 5: Payment Information Section Locators
    private final By paymentInfoSection = By.id("payment-info-buttons-container");
    private final By paymentInfoContinueButton = By.xpath("//input[@class='button-1 payment-info-next-step-button']");

    // Credit Card Fields Locators
    private final By cardTypeDropdown = By.id("CreditCardType");
    private final By cardHolderName = By.id("CardholderName");
    private final By cardNumber = By.id("CardNumber");
    private final By expirationMonth = By.id("ExpireMonth");
    private final By expirationYear = By.id("ExpireYear");
    private final By cardCode = By.id("CardCode");

    // Step 6: Confirm Order Section Locators
    private final By confirmOrderSection = By.xpath("//div[@class='order-summary-content']");
    private final By billingAddressConfirm = By.xpath("//div[@class='billing-info']");
    private final By shippingAddressConfirm = By.xpath("//div[@class='shipping-info']");
    private final By paymentMethodConfirm = By.xpath("//div[@class='payment-method-info']");
    private final By shippingMethodConfirm = By.xpath("//div[@class='shipping-method-info']");

    // Order Total Locator
    private final By orderTotal = By.xpath("//span[@class='product-price order-total']//strong");

    // Confirm Order Button Locator
    private final By confirmOrderButton = By.xpath("//input[@value='Confirm']");

    // Order Completion Page Locators
    private final By orderSuccessTitle = By.xpath("//div[@class='title']/strong");
    private final By orderNumber = By.xpath("//div[@class='details']//li[1]");
    private final By orderSuccessMessage = By.xpath("//div[@class='title']");
    private final By continueButton = By.xpath("//input[@value='Continue']");

    // Error Messages Locators
    private final By validationErrorMessage = By.xpath("//div[@class='message-error']");
    private final By fieldErrorMessage = By.xpath("//span[@class='field-validation-error']");

    // Constructors
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage() {
        super();
    }

    // Wait Methods for Each Checkout Step

    @Step("Wait for shipping section to load")
    public boolean waitForShippingSection(int timeout) {
        return waitForVisibility(shippingContinueButton, timeout);
    }

    @Step("Wait for shipping method section to load")
    public boolean waitForShippingMethodSection(int timeout) {
        return waitForVisibility(shippingMethodSection, timeout);
    }

    @Step("Wait for payment method section to load")
    public boolean waitForPaymentMethodSection(int timeout) {
        return waitForVisibility(paymentMethodSection, timeout);
    }

    @Step("Wait for payment info section to load")
    public boolean waitForPaymentInfoSection(int timeout) {
        return waitForVisibility(paymentInfoSection, timeout);
    }

    @Step("Wait for confirm order section to load")
    public boolean waitForConfirmOrderSection(int timeout) {
        return waitForVisibility(confirmOrderSection, timeout);
    }

    @Step("Wait for order success title to appear")
    public boolean waitForOrderSuccessTitle(int timeout) {
        return waitForVisibility(orderSuccessTitle, timeout);
    }

    // Page Verification Methods

    @Step("Verify user is on checkout page")
    public boolean isOnCheckoutPage() {
        String url = getCurrentUrl();
        return url.contains("onepagecheckout");
    }

    @Step("Get checkout page title")
    public String getPageTitle() {
        return getText(pageTitle);
    }

    @Step("Check if checkout progress bar is visible")
    public boolean isCheckoutProgressBarVisible() {
        return isVisible(checkoutProgressBar);
    }

    // STEP 1: Billing Address Methods

    @Step("Check if billing address section is visible")
    public boolean isBillingAddressSectionVisible() {
        return isVisible(billingAddressSection);
    }

    @Step("Select existing billing address: {addressText}")
    public void selectBillingAddress(String addressText) {
        Select select = new Select(driver.findElement(billingAddressDropdown));
        select.selectByVisibleText(addressText);
    }

    @Step("Click New Address button in billing section")
    public void clickNewBillingAddress() {
        click(newAddressButton);
    }

    @Step("Fill billing address form")
    public void fillBillingAddress(String firstName, String lastName, String email,
                                   String country, String city, String address1,
                                   String zipCode, String phoneNumber) {
        write(billingFirstName, firstName);
        write(billingLastName, lastName);
        write(billingEmail, email);

        Select countrySelect = new Select(driver.findElement(billingCountryDropdown));
        countrySelect.selectByVisibleText(country);

        write(billingCity, city);
        write(billingAddress1, address1);
        write(billingZipCode, zipCode);
        write(billingPhoneNumber, phoneNumber);
    }

    @Step("Enter billing first name: {firstName}")
    public void enterBillingFirstName(String firstName) {
        write(billingFirstName, firstName);
    }

    @Step("Enter billing last name: {lastName}")
    public void enterBillingLastName(String lastName) {
        write(billingLastName, lastName);
    }

    @Step("Enter billing email: {email}")
    public void enterBillingEmail(String email) {
        write(billingEmail, email);
    }

    @Step("Select billing country: {country}")
    public void selectBillingCountry(String country) {
        Select select = new Select(driver.findElement(billingCountryDropdown));
        select.selectByVisibleText(country);
    }

    @Step("Enter billing city: {city}")
    public void enterBillingCity(String city) {
        write(billingCity, city);
    }

    @Step("Enter billing address: {address}")
    public void enterBillingAddress1(String address) {
        write(billingAddress1, address);
    }

    @Step("Enter billing zip code: {zipCode}")
    public void enterBillingZipCode(String zipCode) {
        write(billingZipCode, zipCode);
    }

    @Step("Enter billing phone number: {phoneNumber}")
    public void enterBillingPhoneNumber(String phoneNumber) {
        write(billingPhoneNumber, phoneNumber);
    }

    @Step("Click Continue button in billing section")
    public void clickBillingContinue() {
        click(billingContinueButton);
    }

    @Step("Check if billing continue button is visible")
    public boolean isBillingContinueButtonVisible() {
        return isVisible(billingContinueButton);
    }

    // STEP 2: Shipping Address Methods

    @Step("Check if shipping address section is visible")
    public boolean isShippingAddressSectionVisible() {
        return isVisible(shippingAddressSection);
    }

    @Step("Check 'Ship to same address' checkbox")
    public void checkShipToSameAddress() {
        if (!isSelected(shipToSameAddressCheckbox)) {
            click(shipToSameAddressCheckbox);
        }
    }

    @Step("Uncheck 'Ship to same address' checkbox")
    public void uncheckShipToSameAddress() {
        if (isSelected(shipToSameAddressCheckbox)) {
            click(shipToSameAddressCheckbox);
        }
    }

    @Step("Check if 'Ship to same address' is selected")
    public boolean isShipToSameAddressChecked() {
        return isSelected(shipToSameAddressCheckbox);
    }

    @Step("Check 'Pick up in store' checkbox")
    public void checkPickupInStore() {
        if (!isSelected(pickupInStoreCheckbox)) {
            click(pickupInStoreCheckbox);
        }
    }

    @Step("Check if 'Pick up in store' checkbox is visible")
    public boolean isPickupInStoreVisible() {
        return isVisible(pickupInStoreCheckbox);
    }

    @Step("Fill shipping address form")
    public void fillShippingAddress(String firstName, String lastName, String email,
                                    String country, String city, String address1,
                                    String zipCode, String phoneNumber) {
        write(shippingFirstName, firstName);
        write(shippingLastName, lastName);
        write(shippingEmail, email);

        Select countrySelect = new Select(driver.findElement(shippingCountryDropdown));
        countrySelect.selectByVisibleText(country);

        write(shippingCity, city);
        write(shippingAddress1, address1);
        write(shippingZipCode, zipCode);
        write(shippingPhoneNumber, phoneNumber);
    }

    @Step("Click Continue button in shipping section")
    public void clickShippingContinue() {
        click(shippingContinueButton);
    }

    @Step("Check if shipping continue button is visible")
    public boolean isShippingContinueButtonVisible() {
        return isVisible(shippingContinueButton);
    }

    // STEP 3: Shipping Method Methods

    @Step("Check if shipping method section is visible")
    public boolean isShippingMethodSectionVisible() {
        return isVisible(shippingMethodSection);
    }

    @Step("Select Ground shipping method")
    public void selectGroundShipping() {
        click(groundShippingRadio);
    }

    @Step("Select Next Day Air shipping method")
    public void selectNextDayAir() {
        click(nextDayAirRadio);
    }

    @Step("Select 2nd Day Air shipping method")
    public void selectSecondDayAir() {
        click(secondDayAirRadio);
    }

    @Step("Check if Ground shipping is selected")
    public boolean isGroundShippingSelected() {
        return isSelected(groundShippingRadio);
    }

    @Step("Click Continue button in shipping method section")
    public void clickShippingMethodContinue() {
        click(shippingMethodContinueButton);
    }

    @Step("Check if shipping method continue button is visible")
    public boolean isShippingMethodContinueButtonVisible() {
        return isVisible(shippingMethodContinueButton);
    }

    // STEP 4: Payment Method Methods

    @Step("Check if payment method section is visible")
    public boolean isPaymentMethodSectionVisible() {
        return isVisible(paymentMethodSection);
    }

    @Step("Select Cash On Delivery payment method")
    public void selectCashOnDelivery() {
        click(cashOnDeliveryRadio);
    }

    @Step("Select Check/Money Order payment method")
    public void selectCheckMoneyOrder() {
        click(checkMoneyOrderRadio);
    }

    @Step("Select Credit Card payment method")
    public void selectCreditCard() {
        click(creditCardRadio);
    }

    @Step("Select Purchase Order payment method")
    public void selectPurchaseOrder() {
        click(purchaseOrderRadio);
    }

    @Step("Check if Cash On Delivery is selected")
    public boolean isCashOnDeliverySelected() {
        return isSelected(cashOnDeliveryRadio);
    }

    @Step("Click Continue button in payment method section")
    public void clickPaymentMethodContinue() {
        click(paymentMethodContinueButton);
    }

    @Step("Check if payment method continue button is visible")
    public boolean isPaymentMethodContinueButtonVisible() {
        return isVisible(paymentMethodContinueButton);
    }

    // STEP 5: Payment Information Methods

    @Step("Check if payment info section is visible")
    public boolean isPaymentInfoSectionVisible() {
        return isVisible(paymentInfoSection);
    }

    @Step("Fill credit card information")
    public void fillCreditCardInfo(String cardType, String cardHolder, String cardNum,
                                   String expMonth, String expYear, String cvv) {
        Select cardTypeSelect = new Select(driver.findElement(cardTypeDropdown));
        cardTypeSelect.selectByVisibleText(cardType);

        write(cardHolderName, cardHolder);
        write(cardNumber, cardNum);

        Select monthSelect = new Select(driver.findElement(expirationMonth));
        monthSelect.selectByVisibleText(expMonth);

        Select yearSelect = new Select(driver.findElement(expirationYear));
        yearSelect.selectByVisibleText(expYear);

        write(cardCode, cvv);
    }

    @Step("Click Continue button in payment info section")
    public void clickPaymentInfoContinue() {
        click(paymentInfoContinueButton);
    }

    @Step("Check if payment info continue button is visible")
    public boolean isPaymentInfoContinueButtonVisible() {
        return isVisible(paymentInfoContinueButton);
    }

    // STEP 6: Confirm Order Methods

    @Step("Check if confirm order section is visible")
    public boolean isConfirmOrderSectionVisible() {
        return isVisible(confirmOrderSection);
    }

    @Step("Get order total from confirm page")
    public String getOrderTotal() {
        return getText(orderTotal);
    }

    @Step("Check if order total is displayed")
    public boolean isOrderTotalDisplayed() {
        return isVisible(orderTotal);
    }

    @Step("Click Confirm Order button")
    public void clickConfirmOrder() {
        click(confirmOrderButton);
    }

    @Step("Check if confirm order button is visible")
    public boolean isConfirmOrderButtonVisible() {
        return isVisible(confirmOrderButton);
    }

    @Step("Check if confirm order button is clickable")
    public boolean isConfirmOrderButtonClickable() {
        return isClickable(confirmOrderButton);
    }

    // Order Success Page Methods

    @Step("Verify order was completed successfully")
    public boolean isOrderSuccessful() {
        return isVisible(orderSuccessTitle);
    }

    @Step("Get order success message")
    public String getOrderSuccessMessage() {
        return getText(orderSuccessMessage);
    }

    @Step("Get order number from success page")
    public String getOrderNumber() {
        return getText(orderNumber);
    }

    @Step("Click Continue button on order success page")
    public void clickContinueAfterOrder() {
        click(continueButton);
    }

    // Error Handling Methods

    @Step("Check if validation error message is displayed")
    public boolean isValidationErrorDisplayed() {
        return isVisible(validationErrorMessage);
    }

    @Step("Get validation error message text")
    public String getValidationErrorMessage() {
        return getText(validationErrorMessage);
    }

    @Step("Check if field-level error is displayed")
    public boolean isFieldErrorDisplayed() {
        return isVisible(fieldErrorMessage);
    }

    // Complete Checkout Flow Methods

    @Step("Complete full checkout with Cash on Delivery")
    public void completeCheckoutWithCOD(String firstName, String lastName, String email,
                                        String country, String city, String address,
                                        String zipCode, String phoneNumber) {
        fillBillingAddress(firstName, lastName, email, country, city, address, zipCode, phoneNumber);
        clickBillingContinue();

        waitForVisibility(shippingContinueButton, 5);
        clickShippingContinue();

        waitForVisibility(shippingMethodContinueButton, 5);
        selectGroundShipping();
        clickShippingMethodContinue();

        waitForVisibility(paymentMethodContinueButton, 5);
        selectCashOnDelivery();
        clickPaymentMethodContinue();

        waitForVisibility(paymentInfoContinueButton, 5);
        clickPaymentInfoContinue();

        waitForVisibility(confirmOrderButton, 5);
        clickConfirmOrder();
    }

    // Helper Methods

    @Step("Get current checkout page URL")
    public String getPageUrl() {
        return getCurrentUrl();
    }

    @Step("Scroll to confirm order button")
    public void scrollToConfirmOrderButton() {
        scrollToElement(confirmOrderButton);
    }

    @Step("Wait for checkout page to load completely")
    public boolean waitForCheckoutPageLoad() {
        return waitForVisibility(pageTitle, 10);
    }

    // Additional Real-World Utility Methods

    @Step("Wait for checkout page to be fully loaded with explicit wait")
    public void waitForPageToLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
        wait.until(ExpectedConditions.visibilityOfElementLocated(billingAddressSection));
    }

    @Step("Get order total as double")
    public double getOrderTotalAsDouble() {
        try {
            String total = getOrderTotal();
            String numericValue = total.replaceAll("[^0-9.]", "");
            return Double.parseDouble(numericValue);
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Step("Verify billing address section is ready")
    public boolean isBillingAddressReady() {
        return isBillingAddressSectionVisible() && isBillingContinueButtonVisible();
    }

    @Step("Verify shipping address section is ready")
    public boolean isShippingAddressReady() {
        return isShippingAddressSectionVisible() && isShippingContinueButtonVisible();
    }

    @Step("Verify shipping method section is ready")
    public boolean isShippingMethodReady() {
        return isShippingMethodSectionVisible() && isShippingMethodContinueButtonVisible();
    }

    @Step("Verify payment method section is ready")
    public boolean isPaymentMethodReady() {
        return isPaymentMethodSectionVisible() && isPaymentMethodContinueButtonVisible();
    }

    @Step("Verify payment info section is ready")
    public boolean isPaymentInfoReady() {
        return isPaymentInfoSectionVisible() && isPaymentInfoContinueButtonVisible();
    }

    @Step("Verify confirm order section is ready")
    public boolean isConfirmOrderReady() {
        return isConfirmOrderSectionVisible() && isConfirmOrderButtonClickable();
    }

    @Step("Extract order number from text")
    public String extractOrderNumberOnly() {
        String fullText = getOrderNumber();
        // Extract only the numeric part from text like "Order number: 123456"
        return fullText.replaceAll("[^0-9]", "");
    }

    @Step("Verify order success message contains expected text")
    public boolean isOrderSuccessMessageValid() {
        if (!isOrderSuccessful()) {
            return false;
        }
        String message = getOrderSuccessMessage();
        return message.toLowerCase().contains("success") ||
                message.toLowerCase().contains("completed") ||
                message.toLowerCase().contains("processed");
    }

    @Step("Check if billing address continue button is enabled")
    public boolean isBillingContinueButtonEnabled() {
        try {
            return driver.findElement(billingContinueButton).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Check if shipping continue button is enabled")
    public boolean isShippingContinueButtonEnabled() {
        try {
            return driver.findElement(shippingContinueButton).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Verify all checkout steps are accessible")
    public boolean areAllCheckoutStepsAccessible() {
        return isBillingAddressSectionVisible() &&
                isBillingContinueButtonVisible();
    }

    @Step("Complete checkout with Check/Money Order payment")
    public void completeCheckoutWithCheckMoneyOrder(String firstName, String lastName, String email,
                                                    String country, String city, String address,
                                                    String zipCode, String phoneNumber) {
        fillBillingAddress(firstName, lastName, email, country, city, address, zipCode, phoneNumber);
        clickBillingContinue();

        waitForVisibility(shippingContinueButton, 5);
        clickShippingContinue();

        waitForVisibility(shippingMethodContinueButton, 5);
        selectGroundShipping();
        clickShippingMethodContinue();

        waitForVisibility(paymentMethodContinueButton, 5);
        selectCheckMoneyOrder();
        clickPaymentMethodContinue();

        waitForVisibility(paymentInfoContinueButton, 5);
        clickPaymentInfoContinue();

        waitForVisibility(confirmOrderButton, 5);
        clickConfirmOrder();
    }

    @Step("Check if any checkout validation error exists")
    public boolean hasAnyValidationError() {
        return isValidationErrorDisplayed() || isFieldErrorDisplayed();
    }

    @Step("Wait for billing section to be interactive")
    public boolean waitForBillingSectionInteractive() {
        return waitForVisibility(billingContinueButton, 10) &&
                isBillingContinueButtonEnabled();
    }

    @Step("Scroll to billing continue button")
    public void scrollToBillingContinueButton() {
        scrollToElement(billingContinueButton);
    }

    @Step("Scroll to shipping continue button")
    public void scrollToShippingContinueButton() {
        scrollToElement(shippingContinueButton);
    }

    @Step("Check if page title matches 'Checkout'")
    public boolean hasCorrectPageTitle() {
        String title = getPageTitle();
        return title != null && title.equalsIgnoreCase("Checkout");
    }
}