package pages.mobile;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.Actions;
import utils.DriverManager;
import utils.Validations;

import java.time.Duration;

/**
 * Page Object for Checkout flow screens.
 * Contains elements and actions for address, payment, and order confirmation.
 */
public class CheckoutPage {

    // ========== CHECKOUT ADDRESS SCREEN ==========
    @AndroidFindBy(accessibility = "checkout address screen")
    @iOSXCUITFindBy(accessibility = "checkout address screen")
    private WebElement checkoutAddressScreen;

    @AndroidFindBy(accessibility = "Full Name* input field")
    @iOSXCUITFindBy(accessibility = "Full Name* input field")
    private WebElement fullNameInput;

    @AndroidFindBy(accessibility = "Address Line 1* input field")
    @iOSXCUITFindBy(accessibility = "Address Line 1* input field")
    private WebElement addressLine1Input;

    @AndroidFindBy(accessibility = "Address Line 2 input field")
    @iOSXCUITFindBy(accessibility = "Address Line 2 input field")
    private WebElement addressLine2Input;

    @AndroidFindBy(accessibility = "City* input field")
    @iOSXCUITFindBy(accessibility = "City* input field")
    private WebElement cityInput;

    @AndroidFindBy(accessibility = "State/Region input field")
    @iOSXCUITFindBy(accessibility = "State/Region input field")
    private WebElement stateInput;

    @AndroidFindBy(accessibility = "Zip Code* input field")
    @iOSXCUITFindBy(accessibility = "Zip Code* input field")
    private WebElement zipCodeInput;

    @AndroidFindBy(accessibility = "Country* input field")
    @iOSXCUITFindBy(accessibility = "Country* input field")
    private WebElement countryInput;

    @AndroidFindBy(accessibility = "To Payment button")
    @iOSXCUITFindBy(accessibility = "To Payment button")
    private WebElement toPaymentButton;

    // ========== CHECKOUT PAYMENT SCREEN ==========
    @AndroidFindBy(accessibility = "checkout payment screen")
    @iOSXCUITFindBy(accessibility = "checkout payment screen")
    private WebElement checkoutPaymentScreen;

    @AndroidFindBy(accessibility = "Full Name* input field")
    @iOSXCUITFindBy(accessibility = "Full Name* input field")
    private WebElement cardHolderInput;

    @AndroidFindBy(accessibility = "Card Number* input field")
    @iOSXCUITFindBy(accessibility = "Card Number* input field")
    private WebElement cardNumberInput;

    @AndroidFindBy(accessibility = "Expiration Date* input field")
    @iOSXCUITFindBy(accessibility = "Expiration Date* input field")
    private WebElement expirationDateInput;

    @AndroidFindBy(accessibility = "Security Code* input field")
    @iOSXCUITFindBy(accessibility = "Security Code* input field")
    private WebElement securityCodeInput;

    @AndroidFindBy(accessibility = "Review Order button")
    @iOSXCUITFindBy(accessibility = "Review Order button")
    private WebElement reviewOrderButton;

    // ========== CHECKOUT COMPLETE SCREEN ==========
    @AndroidFindBy(accessibility = "checkout complete screen")
    @iOSXCUITFindBy(accessibility = "checkout complete screen")
    private WebElement checkoutCompleteScreen;

    @AndroidFindBy(accessibility = "Continue Shopping button")
    @iOSXCUITFindBy(accessibility = "Continue Shopping button")
    private WebElement continueShoppingButton;

    public CheckoutPage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver(), Duration.ofSeconds(10)), this);
    }

    // ========== ADDRESS SCREEN ACTIONS ==========
    public boolean isAddressScreenDisplayed() {
        return Validations.isDisplayed(checkoutAddressScreen);
    }

    public void enterFullName(String name) {
        Actions.type(fullNameInput, name);
    }

    public void enterAddressLine1(String address) {
        Actions.type(addressLine1Input, address);
    }

    public void enterAddressLine2(String address) {
        Actions.type(addressLine2Input, address);
    }

    public void enterCity(String city) {
        Actions.type(cityInput, city);
    }

    public void enterState(String state) {
        Actions.type(stateInput, state);
    }

    public void enterZipCode(String zip) {
        Actions.type(zipCodeInput, zip);
    }

    public void enterCountry(String country) {
        Actions.type(countryInput, country);
    }

    public void tapToPayment() {
        Actions.click(toPaymentButton);
    }

    /**
     * Fill shipping address with default values and proceed to payment
     */
    public void completeAddressWithDefaults() {
        // Default values are already filled, just tap To Payment
        tapToPayment();
    }

    // ========== PAYMENT SCREEN ACTIONS ==========
    public boolean isPaymentScreenDisplayed() {
        try {
            return Validations.isDisplayed(checkoutPaymentScreen);
        } catch (Exception e) {
            return false;
        }
    }

    public void enterCardHolder(String name) {
        Actions.type(cardHolderInput, name);
    }

    public void enterCardNumber(String number) {
        Actions.type(cardNumberInput, number);
    }

    public void enterExpirationDate(String date) {
        Actions.type(expirationDateInput, date);
    }

    public void enterSecurityCode(String code) {
        Actions.type(securityCodeInput, code);
    }

    public void tapReviewOrder() {
        Actions.click(reviewOrderButton);
    }

    /**
     * Fill payment details with test card and proceed to review
     */
    public void completePaymentWithTestCard() {
        enterCardHolder("Test User");
        enterCardNumber("4111111111111111");
        enterExpirationDate("12/25");
        enterSecurityCode("123");
        tapReviewOrder();
    }

    // ========== CHECKOUT COMPLETE SCREEN ACTIONS ==========
    public boolean isCheckoutCompleteDisplayed() {
        try {
            return Validations.isDisplayed(checkoutCompleteScreen);
        } catch (Exception e) {
            return false;
        }
    }

    public void tapContinueShopping() {
        Actions.click(continueShoppingButton);
    }
}
