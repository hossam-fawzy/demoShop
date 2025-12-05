package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage extends BasePage {

    // ≡ Locators for Checkout Page Elements

    // Page Title & Steps
    private final By pageTitle = By.xpath("//h1[normalize-space()='Checkout']");
    private final By checkoutProgressBar = By.xpath("//ol[@id='checkout-progress']");

    // Step 1: Billing Address Section
    private final By billingAddressSection = By.id("billing-buttons-container");
    private final By billingAddressDropdown = By.id("billing-address-select");
    private final By newAddressButton = By.xpath("//input[@onclick='Billing.newAddress(event)']");

    // Billing Address Form Fields
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

    // Billing Continue Button
    private final By billingContinueButton = By.xpath("//input[@onclick='Billing.save()']");

    // Step 2: Shipping Address Section
    private final By shippingAddressSection = By.id("shipping-buttons-container");
    private final By shippingAddressDropdown = By.id("shipping-address-select");
    private final By shipToSameAddressCheckbox = By.id("ShipToSameAddress");
    private final By pickupInStoreCheckbox = By.id("PickUpInStore");

    // Shipping Address Form Fields (similar to billing)
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

    // Shipping Continue Button
    private final By shippingContinueButton = By.xpath("//input[@onclick='Shipping.save()']");

    // Step 3: Shipping Method Section
    private final By shippingMethodSection = By.id("shipping-method-buttons-container");
    private final By groundShippingRadio = By.id("shippingoption_0");
    private final By nextDayAirRadio = By.id("shippingoption_1");
    private final By secondDayAirRadio = By.id("shippingoption_2");
    private final By shippingMethodContinueButton = By.xpath("//input[@class='button-1 shipping-method-next-step-button']");

    // Step 4: Payment Method Section
    private final By paymentMethodSection = By.id("payment-method-buttons-container");
    private final By cashOnDeliveryRadio = By.id("paymentmethod_0");
    private final By checkMoneyOrderRadio = By.id("paymentmethod_1");
    private final By creditCardRadio = By.id("paymentmethod_2");
    private final By purchaseOrderRadio = By.id("paymentmethod_3");
    private final By paymentMethodContinueButton = By.xpath("//input[@class='button-1 payment-method-next-step-button']");

    // Step 5: Payment Information Section
    private final By paymentInfoSection = By.id("payment-info-buttons-container");
    private final By paymentInfoContinueButton = By.xpath("//input[@class='button-1 payment-info-next-step-button']");

    // Credit Card Fields (when credit card is selected)
    private final By cardTypeDropdown = By.id("CreditCardType");
    private final By cardHolderName = By.id("CardholderName");
    private final By cardNumber = By.id("CardNumber");
    private final By expirationMonth = By.id("ExpireMonth");
    private final By expirationYear = By.id("ExpireYear");
    private final By cardCode = By.id("CardCode");

    // Step 6: Confirm Order Section
    private final By confirmOrderSection = By.xpath("//div[@class='order-summary-content']");
    private final By billingAddressConfirm = By.xpath("//div[@class='billing-info']");
    private final By shippingAddressConfirm = By.xpath("//div[@class='shipping-info']");
    private final By paymentMethodConfirm = By.xpath("//div[@class='payment-method-info']");
    private final By shippingMethodConfirm = By.xpath("//div[@class='shipping-method-info']");

    // Order Total in Confirm Section
    private final By orderTotal = By.xpath("//span[@class='product-price order-total']//strong");

    // Confirm Order Button
    private final By confirmOrderButton = By.xpath("//input[@value='Confirm']");

    // Order Completion Page
    private final By orderSuccessTitle = By.xpath("//div[@class='title']/strong");
    private final By orderNumber = By.xpath("//div[@class='details']//li[1]");
    private final By orderSuccessMessage = By.xpath("//div[@class='title']");
    private final By continueButton = By.xpath("//input[@value='Continue']");

    // Error Messages
    private final By validationErrorMessage = By.xpath("//div[@class='message-error']");
    private final By fieldErrorMessage = By.xpath("//span[@class='field-validation-error']");

    // ≡ Constructor
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    // Alternative: No-argument constructor
    public CheckoutPage() {
        super();
    }


    public boolean waitForShippingSection(int timeout) {
        return waitForVisibility(By.xpath("//input[@onclick='Shipping.save()']"), timeout);
    }

    public boolean waitForShippingMethodSection(int timeout) {
        return waitForVisibility(By.id("shipping-method-buttons-container"), timeout);
    }

    public boolean waitForPaymentMethodSection(int timeout) {
        return waitForVisibility(By.id("payment-method-buttons-container"), timeout);
    }
    public boolean waitForPaymentInfoSection(int timeout) {
        return waitForVisibility(By.id("payment-info-buttons-container"), timeout);
    }

    public boolean waitForConfirmOrderSection(int timeout) {
        return waitForVisibility(By.xpath("//div[@class='order-summary-content']"), timeout);
    }

    public boolean waitForOrderSuccessTitle(int timeout) {
        return waitForVisibility(By.xpath("//div[@class='title']/strong"), timeout);
    }



    // ≡ Page Verification Methods

    /**
     * Check if we're on the checkout page
     * @return boolean - true if on checkout page
     */
    public boolean isOnCheckoutPage() {
        String url = getCurrentUrl();
        return url.contains("onepagecheckout");
    }

    /**
     * Get page title
     * @return String - page title
     */
    public String getPageTitle() {
        return getText(pageTitle);
    }

    /**
     * Check if checkout progress bar is visible
     * @return boolean - true if visible
     */
    public boolean isCheckoutProgressBarVisible() {
        return isVisible(checkoutProgressBar);
    }

    // ≡ STEP 1: Billing Address Methods

    /**
     * Check if billing address section is visible
     * @return boolean - true if visible
     */
    public boolean isBillingAddressSectionVisible() {
        return isVisible(billingAddressSection);
    }

    /**
     * Select existing billing address from dropdown
     * @param addressText - visible text in dropdown
     */
    public void selectBillingAddress(String addressText) {
        Select select = new Select(driver.findElement(billingAddressDropdown));
        select.selectByVisibleText(addressText);
    }

    /**
     * Click New Address button in billing section
     */
    public void clickNewBillingAddress() {
        click(newAddressButton);
    }

    /**
     * Fill billing address form
     * @param firstName - first name
     * @param lastName - last name
     * @param email - email address
     * @param country - country name
     * @param city - city name
     * @param address1 - street address
     * @param zipCode - zip/postal code
     * @param phoneNumber - phone number
     */
    public void fillBillingAddress(String firstName, String lastName, String email,
                                   String country, String city, String address1,
                                   String zipCode, String phoneNumber) {
        write(billingFirstName, firstName);
        write(billingLastName, lastName);
        write(billingEmail, email);

        // Select country from dropdown
        Select countrySelect = new Select(driver.findElement(billingCountryDropdown));
        countrySelect.selectByVisibleText(country);

        write(billingCity, city);
        write(billingAddress1, address1);
        write(billingZipCode, zipCode);
        write(billingPhoneNumber, phoneNumber);
    }

    /**
     * Enter billing first name
     * @param firstName - first name
     */
    public void enterBillingFirstName(String firstName) {
        write(billingFirstName, firstName);
    }

    /**
     * Enter billing last name
     * @param lastName - last name
     */
    public void enterBillingLastName(String lastName) {
        write(billingLastName, lastName);
    }

    /**
     * Enter billing email
     * @param email - email address
     */
    public void enterBillingEmail(String email) {
        write(billingEmail, email);
    }

    /**
     * Select billing country
     * @param country - country name
     */
    public void selectBillingCountry(String country) {
        Select select = new Select(driver.findElement(billingCountryDropdown));
        select.selectByVisibleText(country);
    }

    /**
     * Enter billing city
     * @param city - city name
     */
    public void enterBillingCity(String city) {
        write(billingCity, city);
    }

    /**
     * Enter billing address line 1
     * @param address - street address
     */
    public void enterBillingAddress1(String address) {
        write(billingAddress1, address);
    }

    /**
     * Enter billing zip code
     * @param zipCode - zip/postal code
     */
    public void enterBillingZipCode(String zipCode) {
        write(billingZipCode, zipCode);
    }

    /**
     * Enter billing phone number
     * @param phoneNumber - phone number
     */
    public void enterBillingPhoneNumber(String phoneNumber) {
        write(billingPhoneNumber, phoneNumber);
    }

    /**
     * Click Continue button in billing section
     */
    public void clickBillingContinue() {
        click(billingContinueButton);
    }

    /**
     * Check if billing continue button is visible
     * @return boolean - true if visible
     */
    public boolean isBillingContinueButtonVisible() {
        return isVisible(billingContinueButton);
    }

    // ≡ STEP 2: Shipping Address Methods

    /**
     * Check if shipping address section is visible
     * @return boolean - true if visible
     */
    public boolean isShippingAddressSectionVisible() {
        return isVisible(shippingAddressSection);
    }

    /**
     * Check "Ship to same address" checkbox
     */
    public void checkShipToSameAddress() {
        if (!isSelected(shipToSameAddressCheckbox)) {
            click(shipToSameAddressCheckbox);
        }
    }

    /**
     * Uncheck "Ship to same address" checkbox
     */
    public void uncheckShipToSameAddress() {
        if (isSelected(shipToSameAddressCheckbox)) {
            click(shipToSameAddressCheckbox);
        }
    }

    /**
     * Check if "Ship to same address" is checked
     * @return boolean - true if checked
     */
    public boolean isShipToSameAddressChecked() {
        return isSelected(shipToSameAddressCheckbox);
    }

    /**
     * Check "Pick up in store" checkbox
     */
    public void checkPickupInStore() {
        if (!isSelected(pickupInStoreCheckbox)) {
            click(pickupInStoreCheckbox);
        }
    }

    /**
     * Check if "Pick up in store" checkbox is visible
     * @return boolean - true if visible
     */
    public boolean isPickupInStoreVisible() {
        return isVisible(pickupInStoreCheckbox);
    }

    /**
     * Fill shipping address form
     * Similar to billing address
     */
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

    /**
     * Click Continue button in shipping section
     */
    public void clickShippingContinue() {
        click(shippingContinueButton);
    }

    /**
     * Check if shipping continue button is visible
     * @return boolean - true if visible
     */
    public boolean isShippingContinueButtonVisible() {
        return isVisible(shippingContinueButton);
    }

    // ≡ STEP 3: Shipping Method Methods

    /**
     * Check if shipping method section is visible
     * @return boolean - true if visible
     */
    public boolean isShippingMethodSectionVisible() {
        return isVisible(shippingMethodSection);
    }

    /**
     * Select Ground shipping method
     */
    public void selectGroundShipping() {
        click(groundShippingRadio);
    }

    /**
     * Select Next Day Air shipping method
     */
    public void selectNextDayAir() {
        click(nextDayAirRadio);
    }

    /**
     * Select 2nd Day Air shipping method
     */
    public void selectSecondDayAir() {
        click(secondDayAirRadio);
    }

    /**
     * Check if Ground shipping is selected
     * @return boolean - true if selected
     */
    public boolean isGroundShippingSelected() {
        return isSelected(groundShippingRadio);
    }

    /**
     * Click Continue button in shipping method section
     */
    public void clickShippingMethodContinue() {
        click(shippingMethodContinueButton);
    }

    /**
     * Check if shipping method continue button is visible
     * @return boolean - true if visible
     */
    public boolean isShippingMethodContinueButtonVisible() {
        return isVisible(shippingMethodContinueButton);
    }

    // ≡ STEP 4: Payment Method Methods

    /**
     * Check if payment method section is visible
     * @return boolean - true if visible
     */
    public boolean isPaymentMethodSectionVisible() {
        return isVisible(paymentMethodSection);
    }

    /**
     * Select Cash On Delivery payment method
     */
    public void selectCashOnDelivery() {
        click(cashOnDeliveryRadio);
    }

    /**
     * Select Check/Money Order payment method
     */
    public void selectCheckMoneyOrder() {
        click(checkMoneyOrderRadio);
    }

    /**
     * Select Credit Card payment method
     */
    public void selectCreditCard() {
        click(creditCardRadio);
    }

    /**
     * Select Purchase Order payment method
     */
    public void selectPurchaseOrder() {
        click(purchaseOrderRadio);
    }

    /**
     * Check if Cash On Delivery is selected
     * @return boolean - true if selected
     */
    public boolean isCashOnDeliverySelected() {
        return isSelected(cashOnDeliveryRadio);
    }

    /**
     * Click Continue button in payment method section
     */
    public void clickPaymentMethodContinue() {
        click(paymentMethodContinueButton);
    }

    /**
     * Check if payment method continue button is visible
     * @return boolean - true if visible
     */
    public boolean isPaymentMethodContinueButtonVisible() {
        return isVisible(paymentMethodContinueButton);
    }

    // ≡ STEP 5: Payment Information Methods

    /**
     * Check if payment info section is visible
     * @return boolean - true if visible
     */
    public boolean isPaymentInfoSectionVisible() {
        return isVisible(paymentInfoSection);
    }

    /**
     * Fill credit card information
     * @param cardType - card type (Visa, MasterCard, etc.)
     * @param cardHolder - cardholder name
     * @param cardNum - card number
     * @param expMonth - expiration month
     * @param expYear - expiration year
     * @param cvv - CVV code
     */
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

    /**
     * Click Continue button in payment info section
     */
    public void clickPaymentInfoContinue() {
        click(paymentInfoContinueButton);
    }

    /**
     * Check if payment info continue button is visible
     * @return boolean - true if visible
     */
    public boolean isPaymentInfoContinueButtonVisible() {
        return isVisible(paymentInfoContinueButton);
    }

    // ≡ STEP 6: Confirm Order Methods

    /**
     * Check if confirm order section is visible
     * @return boolean - true if visible
     */
    public boolean isConfirmOrderSectionVisible() {
        return isVisible(confirmOrderSection);
    }

    /**
     * Get order total from confirm page
     * @return String - order total amount
     */
    public String getOrderTotal() {
        return getText(orderTotal);
    }

    /**
     * Check if order total is displayed
     * @return boolean - true if visible
     */
    public boolean isOrderTotalDisplayed() {
        return isVisible(orderTotal);
    }

    /**
     * Click Confirm Order button to complete purchase
     */
    public void clickConfirmOrder() {
        click(confirmOrderButton);
    }

    /**
     * Check if confirm order button is visible
     * @return boolean - true if visible
     */
    public boolean isConfirmOrderButtonVisible() {
        return isVisible(confirmOrderButton);
    }

    /**
     * Check if confirm order button is clickable
     * @return boolean - true if clickable
     */
    public boolean isConfirmOrderButtonClickable() {
        return isClickable(confirmOrderButton);
    }

    // ≡ Order Success Page Methods

    /**
     * Check if order was completed successfully
     * @return boolean - true if on success page
     */
    public boolean isOrderSuccessful() {
        return isVisible(orderSuccessTitle);
    }

    /**
     * Get order success message
     * @return String - success message text
     */
    public String getOrderSuccessMessage() {
        return getText(orderSuccessMessage);
    }

    /**
     * Get order number after successful order
     * @return String - order number
     */
    public String getOrderNumber() {
        // Extract just the number from text like "Order number: 123456"
        return getText(orderNumber);
    }

    /**
     * Click Continue button on order success page
     */
    public void clickContinueAfterOrder() {
        click(continueButton);
    }

    // ≡ Error Handling Methods

    /**
     * Check if validation error message is displayed
     * @return boolean - true if error visible
     */
    public boolean isValidationErrorDisplayed() {
        return isVisible(validationErrorMessage);
    }

    /**
     * Get validation error message text
     * @return String - error message
     */
    public String getValidationErrorMessage() {
        return getText(validationErrorMessage);
    }

    /**
     * Check if field-level error is displayed
     * @return boolean - true if error visible
     */
    public boolean isFieldErrorDisplayed() {
        return isVisible(fieldErrorMessage);
    }

    // ≡ Complete Checkout Flow Methods

    /**
     * Complete full checkout with Cash on Delivery
     * This method goes through all checkout steps
     * @param firstName - first name
     * @param lastName - last name
     * @param email - email address
     * @param country - country
     * @param city - city
     * @param address - street address
     * @param zipCode - zip code
     * @param phoneNumber - phone number
     */
    public void completeCheckoutWithCOD(String firstName, String lastName, String email,
                                        String country, String city, String address,
                                        String zipCode, String phoneNumber) {
        // Step 1: Billing Address
        fillBillingAddress(firstName, lastName, email, country, city, address, zipCode, phoneNumber);
        clickBillingContinue();

        // Step 2: Shipping Address (use same address)
        waitForVisibility(shippingContinueButton, 5);
        clickShippingContinue();

        // Step 3: Shipping Method
        waitForVisibility(shippingMethodContinueButton, 5);
        selectGroundShipping();
        clickShippingMethodContinue();

        // Step 4: Payment Method
        waitForVisibility(paymentMethodContinueButton, 5);
        selectCashOnDelivery();
        clickPaymentMethodContinue();

        // Step 5: Payment Info
        waitForVisibility(paymentInfoContinueButton, 5);
        clickPaymentInfoContinue();

        // Step 6: Confirm Order
        waitForVisibility(confirmOrderButton, 5);
        clickConfirmOrder();
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
     * Scroll to confirm order button
     */
    public void scrollToConfirmOrderButton() {
        scrollToElement(confirmOrderButton);
    }

    /**
     * Wait for checkout page to load
     * @return boolean - true if loaded
     */
    public boolean waitForCheckoutPageLoad() {
        return waitForVisibility(pageTitle, 10);
    }
}