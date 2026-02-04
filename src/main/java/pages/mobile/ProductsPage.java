package pages.mobile;

import constants.Products;
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
 * Page Object for Products/Catalog screen.
 * Contains elements and actions for the product listing functionality.
 */
public class ProductsPage {

    @AndroidFindBy(accessibility = "products screen")
    @iOSXCUITFindBy(accessibility = "products screen")
    private WebElement productsScreen;

    @AndroidFindBy(accessibility = "sort button")
    @iOSXCUITFindBy(accessibility = "sort button")
    private WebElement sortButton;

    @AndroidFindBy(accessibility = "cart badge")
    @iOSXCUITFindBy(accessibility = "cart badge")
    private WebElement cartBadge;

    // Cart badge count (number displayed on cart icon)
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='cart badge']/android.widget.TextView")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='cart badge']/XCUIElementTypeStaticText")
    private WebElement cartBadgeCount;

    @AndroidFindBy(accessibility = "store item")
    @iOSXCUITFindBy(accessibility = "store item")
    private List<WebElement> storeItems;

    @AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc='store item'])[1]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeOther[@name='store item'])[1]")
    private WebElement firstProduct;

    @AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc='store item'])[2]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeOther[@name='store item'])[2]")
    private WebElement secondProduct;

    @AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc='store item'])[3]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeOther[@name='store item'])[3]")
    private WebElement thirdProduct;

    @AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc='store item'])[4]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeOther[@name='store item'])[4]")
    private WebElement fourthProduct;

    // Sort modal options
    @AndroidFindBy(accessibility = "nameAsc")
    @iOSXCUITFindBy(accessibility = "nameAsc")
    private WebElement sortNameAscending;

    @AndroidFindBy(accessibility = "nameDesc")
    @iOSXCUITFindBy(accessibility = "nameDesc")
    private WebElement sortNameDescending;

    @AndroidFindBy(accessibility = "priceAsc")
    @iOSXCUITFindBy(accessibility = "priceAsc")
    private WebElement sortPriceLowToHigh;

    @AndroidFindBy(accessibility = "priceDesc")
    @iOSXCUITFindBy(accessibility = "priceDesc")
    private WebElement sortPriceHighToLow;

    public ProductsPage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver(), Duration.ofSeconds(10)), this);
    }

    public void tapOnSortButton() {
        Actions.click(sortButton);
    }

    public void tapOnCartBadge() {
        Actions.click(cartBadge);
    }

    public void tapOnFirstProduct() {
        Actions.click(firstProduct);
    }

    public void tapOnSecondProduct() {
        Actions.click(secondProduct);
    }

    public void tapOnThirdProduct() {
        Actions.click(thirdProduct);
    }

    public void tapOnFourthProduct() {
        Actions.click(fourthProduct);
    }

    /**
     * Tap on a product by index (1-based)
     */
    public void tapOnProduct(int index) {
        switch (index) {
            case 1: tapOnFirstProduct(); break;
            case 2: tapOnSecondProduct(); break;
            case 3: tapOnThirdProduct(); break;
            case 4: tapOnFourthProduct(); break;
            default: tapOnFirstProduct();
        }
    }

    /**
     * Tap on a product using the Products enum
     * @param product The product to select from the catalog
     */
    public void tapOnProduct(Products product) {
        tapOnProduct(product.getIndex());
    }

    public void sortByNameAscending() {
        tapOnSortButton();
        Actions.click(sortNameAscending);
    }

    public void sortByNameDescending() {
        tapOnSortButton();
        Actions.click(sortNameDescending);
    }

    public void sortByPriceLowToHigh() {
        tapOnSortButton();
        Actions.click(sortPriceLowToHigh);
    }

    public void sortByPriceHighToLow() {
        tapOnSortButton();
        Actions.click(sortPriceHighToLow);
    }

    public boolean isProductsScreenDisplayed() {
        return Validations.isDisplayed(productsScreen);
    }

    public boolean isSortButtonDisplayed() {
        return Validations.isDisplayed(sortButton);
    }

    public boolean isCartBadgeDisplayed() {
        return Validations.isDisplayed(cartBadge);
    }

    public int getProductCount() {
        return storeItems.size();
    }

    public boolean hasProducts() {
        return !storeItems.isEmpty();
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
