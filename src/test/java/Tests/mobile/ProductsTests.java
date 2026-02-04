package Tests.mobile;

import base.mobile.BaseTest;
import constants.Colors;
import constants.Products;
import io.qameta.allure.*;
import pages.mobile.HomePage;
import pages.mobile.ProductsPage;
import pages.mobile.ProductDetailsPage;
import pages.mobile.CartPage;
import utils.ReportUtils;
import utils.TestListener;
import utils.Validations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * Comprehensive Products Test Suite
 * Tests product catalog functionality including:
 * - Product listing and display
 * - Sorting options (name, price)
 * - Product details navigation
 * - Color selection for products
 * - Quantity management
 */
@Listeners(TestListener.class)
@Epic("Mobile Testing")
@Feature("Products Catalog")
public class ProductsTests extends BaseTest {

    ProductsPage productsPage;
    ProductDetailsPage productDetailsPage;
    CartPage cartPage;
    HomePage homePage;

    @BeforeMethod
    public void initPages() {
        productsPage = new ProductsPage();
        productDetailsPage = new ProductDetailsPage();
        cartPage = new CartPage();
        homePage = new HomePage();
    }

    // ========== PRODUCT LISTING TESTS ==========

    @Test
    @Story("Product Listing")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify products are displayed on the catalog screen")
    public void verifyProductsAreDisplayed() {
        ReportUtils.step("Verifying products screen is displayed", () -> {
            Validations.validateTrue(productsPage.isProductsScreenDisplayed(),
                "Products screen should be displayed on app launch");
        });

        ReportUtils.step("Verifying products exist", () -> {
            Validations.validateTrue(productsPage.hasProducts(),
                "Products should be displayed on the catalog screen");
        });

        ReportUtils.step("Verifying sort button is available", () -> {
            Validations.validateTrue(productsPage.isSortButtonDisplayed(),
                "Sort button should be visible");
        });

        ReportUtils.step("Verifying cart badge is visible", () -> {
            Validations.validateTrue(productsPage.isCartBadgeDisplayed(),
                "Cart badge should be visible");
        });
    }

    @Test
    @Story("Product Listing")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify multiple products are shown in catalog")
    public void verifyMultipleProductsDisplayed() {
        ReportUtils.step("Counting products on catalog", () -> {
            int productCount = productsPage.getProductCount();
            Validations.validateTrue(productCount >= 4,
                "Should display at least 4 products, found: " + productCount);
        });
    }

    // ========== SORTING TESTS ==========

    @Test
    @Story("Product Sorting")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify products can be sorted by name ascending")
    public void verifySortByNameAscending() {
        ReportUtils.step("Tapping sort button and selecting Name Ascending", () -> {
            productsPage.sortByNameAscending();
        });

        ReportUtils.step("Verifying products screen is still displayed", () -> {
            Validations.validateTrue(productsPage.hasProducts(),
                "Products should still be visible after sorting by name A-Z");
        });
    }

    @Test
    @Story("Product Sorting")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify products can be sorted by name descending")
    public void verifySortByNameDescending() {
        ReportUtils.step("Sorting by name descending", () -> {
            productsPage.sortByNameDescending();
        });

        ReportUtils.step("Verifying products are displayed", () -> {
            Validations.validateTrue(productsPage.hasProducts(),
                "Products should be visible after sorting by name Z-A");
        });
    }

    @Test
    @Story("Product Sorting")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify products can be sorted by price low to high")
    public void verifySortByPriceLowToHigh() {
        ReportUtils.step("Sorting by price low to high", () -> {
            productsPage.sortByPriceLowToHigh();
        });

        ReportUtils.step("Verifying products are displayed", () -> {
            Validations.validateTrue(productsPage.hasProducts(),
                "Products should be visible after sorting by price ascending");
        });

        ReportUtils.step("Tapping first product (should be cheapest)", () -> {
            productsPage.tapOnFirstProduct();
        });

        ReportUtils.step("Verifying product details screen is shown", () -> {
            Validations.validateTrue(productDetailsPage.isProductScreenDisplayed(),
                "Product details should be displayed for cheapest product");
        });
    }

    @Test
    @Story("Product Sorting")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify products can be sorted by price high to low")
    public void verifySortByPriceHighToLow() {
        ReportUtils.step("Sorting by price high to low", () -> {
            productsPage.sortByPriceHighToLow();
        });

        ReportUtils.step("Verifying products are displayed", () -> {
            Validations.validateTrue(productsPage.hasProducts(),
                "Products should be visible after sorting by price descending");
        });

        ReportUtils.step("Tapping first product (should be most expensive)", () -> {
            productsPage.tapOnFirstProduct();
        });

        ReportUtils.step("Verifying product details screen is shown", () -> {
            Validations.validateTrue(productDetailsPage.isProductScreenDisplayed(),
                "Product details should be displayed for most expensive product");
        });
    }

    // ========== PRODUCT NAVIGATION TESTS ==========

    @Test
    @Story("Product Navigation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify user can navigate to product details by tapping on a product")
    public void verifyNavigationToProductDetails() {
        ReportUtils.step("Tapping on Backpack", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
        });

        ReportUtils.step("Verifying product details screen is displayed", () -> {
            Validations.validateTrue(productDetailsPage.isProductScreenDisplayed(),
                "Product details screen should be displayed");
        });

        ReportUtils.step("Verifying Add To Cart button is available", () -> {
            Validations.validateTrue(productDetailsPage.isAddToCartButtonDisplayed(),
                "Add To Cart button should be visible");
        });
    }

    @Test
    @Story("Product Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify navigating to each of the first 4 products")
    public void verifyNavigationToAllProducts() {
        for (int i = 1; i <= 4; i++) {
            final int productIndex = i;
            ReportUtils.step("Navigating to product " + productIndex, () -> {
                productsPage.tapOnProduct(productIndex);
            });

            ReportUtils.step("Verifying product " + productIndex + " details screen", () -> {
                Validations.validateTrue(productDetailsPage.isProductScreenDisplayed(),
                    "Product " + productIndex + " details should be displayed");
            });

            ReportUtils.step("Going back to products", () -> {
                productDetailsPage.goBackToProducts();
            });
        }

        ReportUtils.step("Verifying back on products screen", () -> {
            Validations.validateTrue(productsPage.hasProducts(),
                "Should be back on products screen");
        });
    }

    @Test
    @Story("Product Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify back navigation from product details returns to catalog")
    public void verifyBackNavigationFromDetails() {
        ReportUtils.step("Navigating to Bike Light", () -> {
            productsPage.tapOnProduct(Products.BIKE_LIGHT);
        });

        ReportUtils.step("Verifying on product details", () -> {
            Validations.validateTrue(productDetailsPage.isProductScreenDisplayed(),
                "Should be on product details screen");
        });

        ReportUtils.step("Going back to products", () -> {
            productDetailsPage.goBackToProducts();
        });

        ReportUtils.step("Verifying back on products catalog", () -> {
            Validations.validateTrue(productsPage.hasProducts(),
                "Should return to products catalog");
        });
    }

    // ========== PRODUCT DETAILS TESTS ==========

    @Test
    @Story("Product Details")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify Backpack has all 4 color options")
    public void verifyBackpackColorOptions() {
        ReportUtils.step("Navigating to Backpack details", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
        });

        ReportUtils.step("Verifying black color option is displayed", () -> {
            Validations.validateTrue(productDetailsPage.isBlackColorDisplayed(),
                "Black color option should be visible");
        });

        ReportUtils.step("Verifying blue color option is displayed", () -> {
            Validations.validateTrue(productDetailsPage.isBlueColorDisplayed(),
                "Blue color option should be visible");
        });

        ReportUtils.step("Selecting black color", () -> {
            productDetailsPage.selectColor(Colors.BLACK);
        });

        ReportUtils.step("Selecting blue color", () -> {
            productDetailsPage.selectColor(Colors.BLUE);
        });

        ReportUtils.step("Selecting gray color", () -> {
            productDetailsPage.selectColor(Colors.GRAY);
        });

        ReportUtils.step("Selecting red color", () -> {
            productDetailsPage.selectColor(Colors.RED);
        });

        ReportUtils.step("Verifying Add To Cart button still visible", () -> {
            Validations.validateTrue(productDetailsPage.isAddToCartButtonDisplayed(),
                "Add To Cart should remain visible after color changes");
        });
    }

    @Test
    @Story("Product Quantity")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify user can increase product quantity before adding to cart")
    public void verifyQuantityIncrement() {
        ReportUtils.step("Navigating to Backpack details", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
        });

        ReportUtils.step("Verifying initial quantity is 1", () -> {
            String qty = productDetailsPage.getQuantityText();
            Validations.validateTrue(qty.equals("1"),
                "Initial quantity should be 1, found: " + qty);
        });

        ReportUtils.step("Increasing product quantity to 3", () -> {
            productDetailsPage.increaseQuantity(2);
        });

        ReportUtils.step("Verifying quantity is now 3", () -> {
            String qty = productDetailsPage.getQuantityText();
            Validations.validateTrue(qty.equals("3"),
                "Quantity should be 3 after incrementing, found: " + qty);
        });
    }

    @Test
    @Story("Product Quantity")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify quantity can be decreased after increasing")
    public void verifyQuantityDecrement() {
        ReportUtils.step("Navigating to Bike Light", () -> {
            productsPage.tapOnProduct(Products.BIKE_LIGHT);
        });

        ReportUtils.step("Increasing quantity to 3", () -> {
            productDetailsPage.increaseQuantity(2);
        });

        ReportUtils.step("Decreasing quantity by 1", () -> {
            productDetailsPage.decreaseQuantity();
        });

        ReportUtils.step("Verifying quantity is 2", () -> {
            String qty = productDetailsPage.getQuantityText();
            Validations.validateTrue(qty.equals("2"),
                "Quantity should be 2 after decrementing, found: " + qty);
        });
    }

    // ========== ADD TO CART FROM DETAILS TESTS ==========

    @Test
    @Story("Add To Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify adding product from details updates cart badge")
    public void verifyAddToCartUpdatesCartBadge() {
        ReportUtils.step("Adding Backpack to cart", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.tapAddToCart();
        });

        ReportUtils.step("Navigating to cart", () -> {
            productDetailsPage.tapOnCartBadge();
        });

        ReportUtils.step("Verifying cart has the product", () -> {
            Validations.validateTrue(cartPage.hasItemsInCart(),
                "Cart should contain the added Backpack");
        });
    }

    @Test
    @Story("Add To Cart")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify adding product with selected color and quantity")
    public void verifyAddProductWithColorAndQuantity() {
        ReportUtils.step("Navigating to Backpack", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
        });

        ReportUtils.step("Selecting red color", () -> {
            productDetailsPage.selectColor(Colors.RED);
        });

        ReportUtils.step("Setting quantity to 2", () -> {
            productDetailsPage.increaseQuantity(1);
        });

        ReportUtils.step("Adding to cart", () -> {
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        double expectedTotal = Products.BACKPACK.getPrice() * 2; // 29.99 * 2

        ReportUtils.step("Verifying cart total: $" + expectedTotal, () -> {
            Validations.validateTrue(cartPage.verifyTotalPrice(expectedTotal),
                "Cart total should be $" + expectedTotal);
        });
    }
}
