package pages.mobile;

import constants.Colors;
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
 * Page Object for Product Details screen.
 * Contains elements and actions for viewing and adding products to cart.
 */
public class ProductDetailsPage {

    @AndroidFindBy(accessibility = "product screen")
    @iOSXCUITFindBy(accessibility = "product screen")
    private WebElement productScreen;

    @AndroidFindBy(accessibility = "Add To Cart button")
    @iOSXCUITFindBy(accessibility = "Add To Cart button")
    private WebElement addToCartButton;

    @AndroidFindBy(accessibility = "counter plus button")
    @iOSXCUITFindBy(accessibility = "counter plus button")
    private WebElement counterPlusButton;

    @AndroidFindBy(accessibility = "counter minus button")
    @iOSXCUITFindBy(accessibility = "counter minus button")
    private WebElement counterMinusButton;

    @AndroidFindBy(accessibility = "counter amount")
    @iOSXCUITFindBy(accessibility = "counter amount")
    private WebElement counterAmount;

    // Color options
    @AndroidFindBy(accessibility = "black circle")
    @iOSXCUITFindBy(accessibility = "black circle")
    private WebElement blackColorOption;

    @AndroidFindBy(accessibility = "blue circle")
    @iOSXCUITFindBy(accessibility = "blue circle")
    private WebElement blueColorOption;

    @AndroidFindBy(accessibility = "gray circle")
    @iOSXCUITFindBy(accessibility = "gray circle")
    private WebElement grayColorOption;

    @AndroidFindBy(accessibility = "red circle")
    @iOSXCUITFindBy(accessibility = "red circle")
    private WebElement redColorOption;

    // Review stars
    @AndroidFindBy(accessibility = "review star 1")
    @iOSXCUITFindBy(accessibility = "review star 1")
    private WebElement reviewStar1;

    @AndroidFindBy(accessibility = "review star 5")
    @iOSXCUITFindBy(accessibility = "review star 5")
    private WebElement reviewStar5;

    @AndroidFindBy(accessibility = "cart badge")
    @iOSXCUITFindBy(accessibility = "cart badge")
    private WebElement cartBadge;

    // Cart badge count (number displayed on cart icon)
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='cart badge']/android.widget.TextView")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='cart badge']/XCUIElementTypeStaticText")
    private WebElement cartBadgeCount;

    // Product price on details page
    @AndroidFindBy(accessibility = "product price")
    @iOSXCUITFindBy(accessibility = "product price")
    private WebElement productPrice;

    // Product name on details page
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='container header']/android.widget.TextView")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='container header']/XCUIElementTypeStaticText")
    private WebElement productName;

    public ProductDetailsPage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver(), Duration.ofSeconds(10)), this);
    }

    public void tapAddToCart() {
        Actions.click(addToCartButton);
    }

    public void increaseQuantity() {
        Actions.click(counterPlusButton);
    }

    public void increaseQuantity(int times) {
        for (int i = 0; i < times; i++) {
            Actions.click(counterPlusButton);
        }
    }

    public void decreaseQuantity() {
        Actions.click(counterMinusButton);
    }

    public void selectBlackColor() {
        Actions.click(blackColorOption);
    }

    public void selectBlueColor() {
        Actions.click(blueColorOption);
    }

    public void selectGrayColor() {
        Actions.click(grayColorOption);
    }

    public void selectRedColor() {
        Actions.click(redColorOption);
    }

    /**
     * Select a color using the Colors enum
     * @param color The color to select
     */
    public void selectColor(Colors color) {
        switch (color) {
            case BLACK: selectBlackColor(); break;
            case BLUE: selectBlueColor(); break;
            case GRAY: selectGrayColor(); break;
            case RED: selectRedColor(); break;
        }
    }

    public void giveOneStarReview() {
        Actions.click(reviewStar1);
    }

    public void giveFiveStarReview() {
        Actions.click(reviewStar5);
    }

    public void tapOnCartBadge() {
        Actions.click(cartBadge);
    }

    public void goBackToProducts() {
        DriverManager.getDriver().navigate().back();
    }

    public boolean isProductScreenDisplayed() {
        return Validations.isDisplayed(productScreen);
    }

    public boolean isAddToCartButtonDisplayed() {
        return Validations.isDisplayed(addToCartButton);
    }

    public boolean isBlackColorDisplayed() {
        return Validations.isDisplayed(blackColorOption);
    }

    public boolean isBlueColorDisplayed() {
        return Validations.isDisplayed(blueColorOption);
    }

    public String getQuantityText() {
        return Actions.getText(counterAmount);
    }

    /**
     * Get the product price displayed on details page (e.g. "$29.99")
     * @return price as string
     */
    public String getProductPrice() {
        return Actions.getText(productPrice);
    }

    /**
     * Get the product price as a numeric value
     * @return price as double (e.g. 29.99)
     */
    public double getProductPriceValue() {
        String priceText = getProductPrice().replace("$", "").replace(",", "");
        return Double.parseDouble(priceText);
    }

    /**
     * Get the product name displayed on details page
     * @return product name
     */
    public String getProductName() {
        return Actions.getText(productName);
    }

    /**
     * Get the cart badge count (number of items in cart)
     * @return cart item count as int, or 0 if badge not visible
     */
    public int getCartBadgeCount() {
        try {
            String countText = Actions.getText(cartBadgeCount);
            return Integer.parseInt(countText);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Check if cart badge shows specific count
     * @param expectedCount expected number
     * @return true if matches
     */
    public boolean verifyCartBadgeCount(int expectedCount) {
        return getCartBadgeCount() == expectedCount;
    }
}
