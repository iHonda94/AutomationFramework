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
import java.util.List;

/**
 * Page Object for Cart screen.
 * Contains elements and actions for cart management, price verification, and checkout.
 */
public class CartPage {

    @AndroidFindBy(accessibility = "cart screen")
    @iOSXCUITFindBy(accessibility = "cart screen")
    private WebElement cartScreen;

    @AndroidFindBy(accessibility = "Proceed To Checkout button")
    @iOSXCUITFindBy(accessibility = "Proceed To Checkout button")
    private WebElement proceedToCheckoutButton;

    @AndroidFindBy(accessibility = "remove item")
    @iOSXCUITFindBy(accessibility = "remove item")
    private WebElement removeItemButton;

    // List of all remove item buttons (for multiple products)
    @AndroidFindBy(accessibility = "remove item")
    @iOSXCUITFindBy(accessibility = "remove item")
    private List<WebElement> removeItemButtons;

    @AndroidFindBy(accessibility = "counter plus button")
    @iOSXCUITFindBy(accessibility = "counter plus button")
    private WebElement counterPlusButton;

    @AndroidFindBy(accessibility = "counter minus button")
    @iOSXCUITFindBy(accessibility = "counter minus button")
    private WebElement counterMinusButton;

    // Counter amount (quantity in cart)
    @AndroidFindBy(accessibility = "counter amount")
    @iOSXCUITFindBy(accessibility = "counter amount")
    private WebElement counterAmount;

    @AndroidFindBy(accessibility = "cart badge")
    @iOSXCUITFindBy(accessibility = "cart badge")
    private WebElement cartBadge;

    // Total price element in checkout footer
    @AndroidFindBy(accessibility = "total price")
    @iOSXCUITFindBy(accessibility = "total price")
    private WebElement totalPriceElement;

    // Total number of items
    @AndroidFindBy(accessibility = "total number")
    @iOSXCUITFindBy(accessibility = "total number")
    private WebElement totalNumberElement;

    // Product labels in cart
    @AndroidFindBy(accessibility = "product label")
    @iOSXCUITFindBy(accessibility = "product label")
    private List<WebElement> productLabels;

    // Product prices in cart
    @AndroidFindBy(accessibility = "product price")
    @iOSXCUITFindBy(accessibility = "product price")
    private List<WebElement> productPrices;

    // Product rows (for counting items)
    @AndroidFindBy(accessibility = "product row")
    @iOSXCUITFindBy(accessibility = "product row")
    private List<WebElement> productRows;

    // No items in cart message
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'No Items')]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[contains(@name, 'No Items')]")
    private WebElement noItemsMessage;

    // Go Shopping button (shown when cart is empty)
    @AndroidFindBy(accessibility = "Go Shopping button")
    @iOSXCUITFindBy(accessibility = "Go Shopping button")
    private WebElement goShoppingButton;

    public CartPage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver(), Duration.ofSeconds(10)), this);
    }

    public void tapProceedToCheckout() {
        Actions.click(proceedToCheckoutButton);
    }

    public void tapRemoveItem() {
        Actions.click(removeItemButton);
    }

    /**
     * Remove all items from cart one by one
     */
    public void removeAllItems() {
        while (hasItemsInCart()) {
            tapRemoveItem();
            try { Thread.sleep(500); } catch (Exception ignored) {}
        }
    }

    public void increaseQuantity() {
        Actions.click(counterPlusButton);
    }

    public void decreaseQuantity() {
        Actions.click(counterMinusButton);
    }

    public void tapGoShopping() {
        Actions.click(goShoppingButton);
    }

    public void tapCartBadge() {
        Actions.click(cartBadge);
    }

    public boolean isCartScreenDisplayed() {
        return Validations.isDisplayed(cartScreen);
    }

    public boolean isProceedToCheckoutDisplayed() {
        return Validations.isDisplayed(proceedToCheckoutButton);
    }

    public boolean isRemoveItemDisplayed() {
        return Validations.isDisplayed(removeItemButton);
    }

    public boolean isCartEmpty() {
        try {
            return Validations.isDisplayed(noItemsMessage) || Validations.isDisplayed(goShoppingButton);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasItemsInCart() {
        try {
            return Validations.isDisplayed(removeItemButton);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the total price displayed in cart (e.g. "$39.98")
     * @return total price as string
     */
    public String getTotalPrice() {
        return Actions.getText(totalPriceElement);
    }

    /**
     * Get the total price as a numeric value
     * @return total price as double (e.g. 39.98)
     */
    public double getTotalPriceValue() {
        String priceText = getTotalPrice().replace("$", "").replace(",", "");
        return Double.parseDouble(priceText);
    }

    /**
     * Get the total number of items text (e.g. "2 items")
     * @return items count text
     */
    public String getTotalItemsText() {
        return Actions.getText(totalNumberElement);
    }

    /**
     * Get the number of items in cart
     * @return count of items
     */
    public int getTotalItemsCount() {
        String text = getTotalItemsText().replaceAll("[^0-9]", "");
        return Integer.parseInt(text);
    }

    /**
     * Get the quantity of the first item in cart
     * @return quantity as string
     */
    public String getItemQuantity() {
        return Actions.getText(counterAmount);
    }

    /**
     * Get the number of distinct product rows in cart
     * @return number of product rows
     */
    public int getProductRowCount() {
        return productRows.size();
    }

    /**
     * Get the name of a product at specific index (0-based)
     * @param index product index
     * @return product name
     */
    public String getProductNameAt(int index) {
        if (index < productLabels.size()) {
            return Actions.getText(productLabels.get(index));
        }
        return "";
    }

    /**
     * Get the price of a product at specific index (0-based)
     * @param index product index
     * @return product price as string
     */
    public String getProductPriceAt(int index) {
        if (index < productPrices.size()) {
            return Actions.getText(productPrices.get(index));
        }
        return "";
    }

    /**
     * Verify if a specific product is in cart by name
     * @param productName name to search
     * @return true if found
     */
    public boolean isProductInCart(String productName) {
        for (WebElement label : productLabels) {
            if (Actions.getText(label).contains(productName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verify total price matches expected calculation
     * @param expectedTotal expected total price
     * @return true if matches (with tolerance of 0.01)
     */
    public boolean verifyTotalPrice(double expectedTotal) {
        double actualTotal = getTotalPriceValue();
        return Math.abs(actualTotal - expectedTotal) < 0.01;
    }
}
