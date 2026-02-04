package Tests.mobile;

import base.mobile.BaseTest;
import constants.Colors;
import constants.Products;
import io.qameta.allure.*;
import pages.mobile.*;
import utils.ReportUtils;
import utils.TestListener;
import utils.Validations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * Comprehensive Cart Test Suite
 * Tests shopping cart functionality including:
 * - Adding multiple products with different colors
 * - Price calculations and verification
 * - Quantity management
 * - Cart item removal
 * - End-to-end checkout flow
 */
@Listeners(TestListener.class)
@Epic("Mobile Testing")
@Feature("Shopping Cart")
public class CartTests extends BaseTest {

    ProductsPage productsPage;
    ProductDetailsPage productDetailsPage;
    CartPage cartPage;
    LoginPage loginPage;
    CheckoutPage checkoutPage;

    @BeforeMethod
    public void initPages() {
        productsPage = new ProductsPage();
        productDetailsPage = new ProductDetailsPage();
        cartPage = new CartPage();
        loginPage = new LoginPage();
        checkoutPage = new CheckoutPage();
    }

    // ========== PRICE CALCULATION TESTS ==========

    @Test
    @Story("Price Calculation")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify total price is calculated correctly for Backpack + Bike Light")
    public void verifyPriceCalculationTwoProducts() {
        double expectedTotal = Products.BACKPACK.getPrice() + Products.BIKE_LIGHT.getPrice(); // 29.99 + 9.99 = 39.98

        ReportUtils.step("Adding Backpack to cart", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.tapAddToCart();
            productDetailsPage.goBackToProducts();
        });

        ReportUtils.step("Adding Bike Light to cart", () -> {
            productsPage.tapOnProduct(Products.BIKE_LIGHT);
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        ReportUtils.step("Verifying total price: $" + expectedTotal, () -> {
            Validations.validateTrue(cartPage.verifyTotalPrice(expectedTotal),
                "Total price should be $" + expectedTotal + " but was " + cartPage.getTotalPrice());
        });

        ReportUtils.step("Verifying item count is 2", () -> {
            Validations.validateTrue(cartPage.getTotalItemsCount() == 2,
                "Cart should show 2 items");
        });
    }

    @Test
    @Story("Price Calculation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify total price for three different products")
    public void verifyPriceCalculationThreeProducts() {
        double expectedTotal = Products.BACKPACK.getPrice() + Products.BOLT_TSHIRT.getPrice() + Products.BIKE_LIGHT.getPrice();
        // 29.99 + 15.99 + 9.99 = 55.97

        ReportUtils.step("Adding Backpack to cart", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.tapAddToCart();
            productDetailsPage.goBackToProducts();
        });

        ReportUtils.step("Adding T-Shirt to cart", () -> {
            productsPage.tapOnProduct(Products.BOLT_TSHIRT);
            productDetailsPage.tapAddToCart();
            productDetailsPage.goBackToProducts();
        });

        ReportUtils.step("Adding Bike Light to cart", () -> {
            productsPage.tapOnProduct(Products.BIKE_LIGHT);
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        ReportUtils.step("Verifying total price: $" + expectedTotal, () -> {
            Validations.validateTrue(cartPage.verifyTotalPrice(expectedTotal),
                "Total should be $" + expectedTotal + ", actual: " + cartPage.getTotalPrice());
        });

        ReportUtils.step("Verifying 3 items in cart", () -> {
            Validations.validateTrue(cartPage.getTotalItemsCount() == 3,
                "Should have 3 items in cart");
        });
    }

    @Test
    @Story("Price Calculation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify price calculation with quantity of 2")
    public void verifyPriceWithQuantityTwo() {
        double expectedTotal = Products.BACKPACK.getPrice() * 2; // 29.99 * 2 = 59.98

        ReportUtils.step("Adding Backpack with quantity 2", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.increaseQuantity(1); // Increase from 1 to 2
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        ReportUtils.step("Verifying total price for 2x Backpack: $" + expectedTotal, () -> {
            Validations.validateTrue(cartPage.verifyTotalPrice(expectedTotal),
                "Total for 2x Backpack should be $" + expectedTotal);
        });

        ReportUtils.step("Verifying item count shows 2", () -> {
            Validations.validateTrue(cartPage.getTotalItemsCount() == 2,
                "Should show 2 items");
        });
    }

    // ========== MULTI-PRODUCT TESTS WITH COLORS ==========

    @Test
    @Story("Multi-Product Cart")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify adding Backpack with all 4 color options")
    public void verifyAddBackpackWithAllColors() {
        ReportUtils.step("Adding Backpack with black color", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.selectColor(Colors.BLACK);
            productDetailsPage.tapAddToCart();
            productDetailsPage.goBackToProducts();
        });

        ReportUtils.step("Adding Backpack with blue color", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.selectColor(Colors.BLUE);
            productDetailsPage.tapAddToCart();
            productDetailsPage.goBackToProducts();
        });

        ReportUtils.step("Adding Backpack with gray color", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.selectColor(Colors.GRAY);
            productDetailsPage.tapAddToCart();
            productDetailsPage.goBackToProducts();
        });

        ReportUtils.step("Adding Backpack with red color", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.selectColor(Colors.RED);
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        double expectedTotal = Products.BACKPACK.getPrice() * 4; // 29.99 * 4 = 119.96

        ReportUtils.step("Verifying 4 items in cart", () -> {
            Validations.validateTrue(cartPage.getTotalItemsCount() == 4,
                "Should have 4 Backpacks in cart");
        });

        ReportUtils.step("Verifying total price: $" + expectedTotal, () -> {
            Validations.validateTrue(cartPage.verifyTotalPrice(expectedTotal),
                "Total should be $" + expectedTotal);
        });
    }

    @Test
    @Story("Multi-Product Cart")
    @Severity(SeverityLevel.NORMAL)
    @Description("Add 4 different products to cart and verify all appear")
    public void verifyAddFourDifferentProducts() {
        ReportUtils.step("Adding Backpack to cart", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.tapAddToCart();
            productDetailsPage.goBackToProducts();
        });

        ReportUtils.step("Adding Bike Light to cart", () -> {
            productsPage.tapOnProduct(Products.BIKE_LIGHT);
            productDetailsPage.tapAddToCart();
            productDetailsPage.goBackToProducts();
        });

        ReportUtils.step("Adding T-Shirt to cart", () -> {
            productsPage.tapOnProduct(Products.BOLT_TSHIRT);
            productDetailsPage.tapAddToCart();
            productDetailsPage.goBackToProducts();
        });

        ReportUtils.step("Adding Fleece Jacket to cart", () -> {
            productsPage.tapOnProduct(Products.FLEECE_JACKET);
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        double expectedTotal = Products.BACKPACK.getPrice() + Products.BIKE_LIGHT.getPrice() 
            + Products.BOLT_TSHIRT.getPrice() + Products.FLEECE_JACKET.getPrice();
        // 29.99 + 9.99 + 15.99 + 49.99 = 105.96

        ReportUtils.step("Verifying 4 items in cart", () -> {
            Validations.validateTrue(cartPage.getTotalItemsCount() == 4,
                "Should have 4 items in cart, found: " + cartPage.getTotalItemsCount());
        });

        ReportUtils.step("Verifying total: $" + expectedTotal, () -> {
            Validations.validateTrue(cartPage.verifyTotalPrice(expectedTotal),
                "Total should be $" + expectedTotal);
        });
    }

    // ========== QUANTITY MANAGEMENT TESTS ==========

    @Test
    @Story("Quantity Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify quantity can be increased in cart and price updates")
    public void verifyIncreaseQuantityInCart() {
        ReportUtils.step("Adding Bike Light to cart", () -> {
            productsPage.tapOnProduct(Products.BIKE_LIGHT);
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        double initialPrice = Products.BIKE_LIGHT.getPrice();
        ReportUtils.step("Verifying initial price: $" + initialPrice, () -> {
            Validations.validateTrue(cartPage.verifyTotalPrice(initialPrice),
                "Initial price should be $" + initialPrice);
        });

        ReportUtils.step("Increasing quantity to 2", () -> {
            cartPage.increaseQuantity();
        });

        double expectedNewPrice = initialPrice * 2;
        ReportUtils.step("Verifying updated price: $" + expectedNewPrice, () -> {
            Validations.validateTrue(cartPage.verifyTotalPrice(expectedNewPrice),
                "Price after increase should be $" + expectedNewPrice);
        });
    }

    @Test
    @Story("Quantity Management")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify quantity can be decreased in cart")
    public void verifyDecreaseQuantityInCart() {
        ReportUtils.step("Adding Backpack with quantity 3", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.increaseQuantity(2); // qty = 3
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        double initialTotal = Products.BACKPACK.getPrice() * 3;
        ReportUtils.step("Verifying initial total: $" + initialTotal, () -> {
            Validations.validateTrue(cartPage.verifyTotalPrice(initialTotal),
                "Initial total should be $" + initialTotal);
        });

        ReportUtils.step("Decreasing quantity by 1", () -> {
            cartPage.decreaseQuantity();
        });

        double expectedTotal = Products.BACKPACK.getPrice() * 2;
        ReportUtils.step("Verifying updated total: $" + expectedTotal, () -> {
            Validations.validateTrue(cartPage.verifyTotalPrice(expectedTotal),
                "Total after decrease should be $" + expectedTotal);
        });
    }

    // ========== CART REMOVAL TESTS ==========

    @Test
    @Story("Cart Removal")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify removing single item makes cart empty")
    public void verifySingleItemRemoval() {
        ReportUtils.step("Adding Backpack to cart", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        ReportUtils.step("Verifying item is in cart", () -> {
            Validations.validateTrue(cartPage.hasItemsInCart(),
                "Cart should have Backpack");
        });

        ReportUtils.step("Removing item from cart", () -> {
            cartPage.tapRemoveItem();
        });

        ReportUtils.step("Verifying cart is now empty", () -> {
            Validations.validateTrue(cartPage.isCartEmpty(),
                "Cart should be empty after removal");
        });
    }

    @Test
    @Story("Cart Removal")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify removing all items from cart one by one")
    public void verifyRemoveAllItems() {
        ReportUtils.step("Adding 3 products to cart", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.tapAddToCart();
            productDetailsPage.goBackToProducts();

            productsPage.tapOnProduct(Products.BIKE_LIGHT);
            productDetailsPage.tapAddToCart();
            productDetailsPage.goBackToProducts();

            productsPage.tapOnProduct(Products.BOLT_TSHIRT);
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        ReportUtils.step("Verifying 3 items in cart", () -> {
            Validations.validateTrue(cartPage.getTotalItemsCount() == 3,
                "Should have 3 items before removal");
        });

        ReportUtils.step("Removing all items one by one", () -> {
            cartPage.removeAllItems();
        });

        ReportUtils.step("Verifying cart is empty", () -> {
            Validations.validateTrue(cartPage.isCartEmpty(),
                "Cart should be empty after removing all items");
        });
    }

    // ========== CHECKOUT FLOW TESTS ==========

    @Test
    @Story("Checkout Flow")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify complete checkout flow from cart to address screen")
    public void verifyCheckoutFlowToAddress() {
        ReportUtils.step("Adding Backpack to cart", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        ReportUtils.step("Proceeding to checkout", () -> {
            cartPage.tapProceedToCheckout();
        });

        ReportUtils.step("Verifying login screen appears", () -> {
            Validations.validateTrue(loginPage.isLoginScreenDisplayed(),
                "Login screen should be displayed for checkout");
        });

        ReportUtils.step("Logging in with valid credentials", () -> {
            loginPage.loginWithValidCredentials();
        });

        ReportUtils.step("Verifying address screen is displayed", () -> {
            Validations.validateTrue(checkoutPage.isAddressScreenDisplayed(),
                "Checkout address screen should be displayed after login");
        });
    }

    @Test
    @Story("Checkout Flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify checkout with multiple products proceeds to address")
    public void verifyCheckoutWithMultipleProducts() {
        ReportUtils.step("Adding Backpack and Bike Light to cart", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.selectColor(Colors.BLUE);
            productDetailsPage.tapAddToCart();
            productDetailsPage.goBackToProducts();

            productsPage.tapOnProduct(Products.BIKE_LIGHT);
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        ReportUtils.step("Verifying 2 items in cart", () -> {
            Validations.validateTrue(cartPage.getTotalItemsCount() == 2,
                "Cart should have 2 items");
        });

        ReportUtils.step("Proceeding to checkout", () -> {
            cartPage.tapProceedToCheckout();
        });

        ReportUtils.step("Completing login", () -> {
            loginPage.tapBobAutofill();
            loginPage.tapOnLoginButton();
        });

        ReportUtils.step("Verifying address screen", () -> {
            Validations.validateTrue(checkoutPage.isAddressScreenDisplayed(),
                "Should reach address screen");
        });
    }

    // ========== CART NAVIGATION TESTS ==========

    @Test
    @Story("Cart Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify Go Shopping button returns to products when cart is empty")
    public void verifyGoShoppingFromEmptyCart() {
        ReportUtils.step("Opening cart from products page", () -> {
            productsPage.tapOnCartBadge();
        });

        ReportUtils.step("Verifying cart is empty", () -> {
            Validations.validateTrue(cartPage.isCartEmpty(),
                "Cart should be empty initially");
        });

        ReportUtils.step("Tapping Go Shopping", () -> {
            cartPage.tapGoShopping();
        });

        ReportUtils.step("Verifying products screen is displayed", () -> {
            Validations.validateTrue(productsPage.isProductsScreenDisplayed(),
                "Should return to products screen");
        });
    }

    @Test
    @Story("Cart Badge")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify cart badge is visible and can be tapped to open cart")
    public void verifyCartBadgeNavigation() {
        ReportUtils.step("Adding item to cart", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.tapAddToCart();
        });

        ReportUtils.step("Verifying cart badge is visible", () -> {
            Validations.validateTrue(productDetailsPage.isAddToCartButtonDisplayed(),
                "Add to cart button should be visible");
        });

        ReportUtils.step("Tapping cart badge", () -> {
            productDetailsPage.tapOnCartBadge();
        });

        ReportUtils.step("Verifying cart screen is displayed", () -> {
            Validations.validateTrue(cartPage.isCartScreenDisplayed(),
                "Cart screen should be displayed");
        });
    }

    // ========== PRICE VERIFICATION TESTS (Details vs Cart) ==========

    @Test
    @Story("Price Verification")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify product price on details page matches price in cart")
    public void verifyPriceMatchesBetweenDetailsAndCart() {
        final double[] detailsPrice = new double[1];

        ReportUtils.step("Navigating to Backpack and getting price", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            detailsPrice[0] = productDetailsPage.getProductPriceValue();
        });

        ReportUtils.step("Verifying details price matches expected ($29.99)", () -> {
            Validations.validateTrue(Math.abs(detailsPrice[0] - Products.BACKPACK.getPrice()) < 0.01,
                "Details price should be $" + Products.BACKPACK.getPrice() + ", found: $" + detailsPrice[0]);
        });

        ReportUtils.step("Adding to cart and navigating to cart", () -> {
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        ReportUtils.step("Verifying cart price matches details page price", () -> {
            double cartPrice = cartPage.getTotalPriceValue();
            Validations.validateTrue(Math.abs(cartPrice - detailsPrice[0]) < 0.01,
                "Cart price ($" + cartPrice + ") should match details price ($" + detailsPrice[0] + ")");
        });
    }

    @Test
    @Story("Price Verification")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify individual product prices in cart match catalog prices")
    public void verifyIndividualProductPricesInCart() {
        ReportUtils.step("Adding Backpack to cart", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.tapAddToCart();
            productDetailsPage.goBackToProducts();
        });

        ReportUtils.step("Adding Bike Light to cart", () -> {
            productsPage.tapOnProduct(Products.BIKE_LIGHT);
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        ReportUtils.step("Verifying first product price in cart is Backpack price", () -> {
            String firstPrice = cartPage.getProductPriceAt(0);
            Validations.validateTrue(firstPrice.contains("29.99"),
                "First product should show $29.99, found: " + firstPrice);
        });

        ReportUtils.step("Verifying second product price is Bike Light price", () -> {
            String secondPrice = cartPage.getProductPriceAt(1);
            Validations.validateTrue(secondPrice.contains("9.99"),
                "Second product should show $9.99, found: " + secondPrice);
        });
    }

    // ========== CART BADGE COUNT TESTS ==========

    @Test
    @Story("Cart Badge Count")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify cart badge shows correct count after adding one item")
    public void verifyCartBadgeCountAfterAddingOneItem() {
        ReportUtils.step("Adding Backpack to cart", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.tapAddToCart();
        });

        ReportUtils.step("Verifying cart badge shows 1", () -> {
            Validations.validateTrue(productDetailsPage.verifyCartBadgeCount(1),
                "Cart badge should show 1, found: " + productDetailsPage.getCartBadgeCount());
        });
    }

    @Test
    @Story("Cart Badge Count")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify cart badge shows correct count after adding multiple items")
    public void verifyCartBadgeCountAfterAddingMultipleItems() {
        ReportUtils.step("Adding Backpack with quantity 2", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.increaseQuantity(1); // qty = 2
            productDetailsPage.tapAddToCart();
        });

        ReportUtils.step("Verifying cart badge shows 2", () -> {
            Validations.validateTrue(productDetailsPage.verifyCartBadgeCount(2),
                "Cart badge should show 2, found: " + productDetailsPage.getCartBadgeCount());
        });

        ReportUtils.step("Going back and adding another product", () -> {
            productDetailsPage.goBackToProducts();
            productsPage.tapOnProduct(Products.BIKE_LIGHT);
            productDetailsPage.tapAddToCart();
        });

        ReportUtils.step("Verifying cart badge shows 3", () -> {
            Validations.validateTrue(productDetailsPage.verifyCartBadgeCount(3),
                "Cart badge should show 3, found: " + productDetailsPage.getCartBadgeCount());
        });
    }

    @Test
    @Story("Cart Badge Count")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify cart badge count matches total items text in cart")
    public void verifyCartBadgeMatchesTotalItems() {
        ReportUtils.step("Adding 3 products to cart", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.tapAddToCart();
            productDetailsPage.goBackToProducts();

            productsPage.tapOnProduct(Products.BIKE_LIGHT);
            productDetailsPage.tapAddToCart();
            productDetailsPage.goBackToProducts();

            productsPage.tapOnProduct(Products.BOLT_TSHIRT);
            productDetailsPage.tapAddToCart();
        });

        final int[] badgeCount = new int[1];
        ReportUtils.step("Getting cart badge count", () -> {
            badgeCount[0] = productDetailsPage.getCartBadgeCount();
        });

        ReportUtils.step("Opening cart and verifying total items matches badge", () -> {
            productDetailsPage.tapOnCartBadge();
            int totalItems = cartPage.getTotalItemsCount();
            Validations.validateTrue(badgeCount[0] == totalItems,
                "Badge count (" + badgeCount[0] + ") should match total items (" + totalItems + ")");
        });
    }

    // ========== FULL CHECKOUT WITH LOGIN TESTS ==========

    @Test
    @Story("Full Checkout Flow")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Complete end-to-end checkout flow: Add product -> Cart -> Login -> Address")
    public void verifyFullCheckoutFlowWithLogin() {
        ReportUtils.step("Adding Backpack with blue color to cart", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.selectColor(Colors.BLUE);
            productDetailsPage.tapAddToCart();
        });

        ReportUtils.step("Verifying cart badge shows 1", () -> {
            Validations.validateTrue(productDetailsPage.verifyCartBadgeCount(1),
                "Badge should show 1 item");
        });

        ReportUtils.step("Opening cart", () -> {
            productDetailsPage.tapOnCartBadge();
        });

        ReportUtils.step("Verifying correct price in cart ($29.99)", () -> {
            Validations.validateTrue(cartPage.verifyTotalPrice(Products.BACKPACK.getPrice()),
                "Total should be $29.99");
        });

        ReportUtils.step("Proceeding to checkout", () -> {
            cartPage.tapProceedToCheckout();
        });

        ReportUtils.step("Verifying login screen is required for checkout", () -> {
            Validations.validateTrue(loginPage.isLoginScreenDisplayed(),
                "Login screen should appear before checkout");
        });

        ReportUtils.step("Logging in with valid user (bob@example.com)", () -> {
            loginPage.tapBobAutofill();
            loginPage.tapOnLoginButton();
        });

        ReportUtils.step("Verifying checkout address screen appears after login", () -> {
            Validations.validateTrue(checkoutPage.isAddressScreenDisplayed(),
                "Should proceed to address screen after successful login");
        });
    }

    @Test
    @Story("Full Checkout Flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Checkout with multiple products and verify address screen shows after login")
    public void verifyCheckoutMultipleProductsWithLogin() {
        double expectedTotal = Products.BACKPACK.getPrice() + Products.FLEECE_JACKET.getPrice();
        // 29.99 + 49.99 = 79.98

        ReportUtils.step("Adding Backpack to cart", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.tapAddToCart();
            productDetailsPage.goBackToProducts();
        });

        ReportUtils.step("Adding Fleece Jacket to cart", () -> {
            productsPage.tapOnProduct(Products.FLEECE_JACKET);
            productDetailsPage.tapAddToCart();
        });

        ReportUtils.step("Verifying cart badge shows 2", () -> {
            Validations.validateTrue(productDetailsPage.verifyCartBadgeCount(2),
                "Badge should show 2 items");
        });

        ReportUtils.step("Opening cart", () -> {
            productDetailsPage.tapOnCartBadge();
        });

        ReportUtils.step("Verifying total price: $" + expectedTotal, () -> {
            Validations.validateTrue(cartPage.verifyTotalPrice(expectedTotal),
                "Total should be $" + expectedTotal);
        });

        ReportUtils.step("Proceeding to checkout and logging in", () -> {
            cartPage.tapProceedToCheckout();
            loginPage.loginWithValidCredentials();
        });

        ReportUtils.step("Verifying on checkout address screen", () -> {
            Validations.validateTrue(checkoutPage.isAddressScreenDisplayed(),
                "Should be on address screen after login");
        });
    }

    @Test
    @Story("Checkout Requires Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify checkout cannot proceed without logging in")
    public void verifyCheckoutRequiresLogin() {
        ReportUtils.step("Adding product to cart", () -> {
            productsPage.tapOnProduct(Products.BIKE_LIGHT);
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        ReportUtils.step("Attempting to proceed to checkout", () -> {
            cartPage.tapProceedToCheckout();
        });

        ReportUtils.step("Verifying login screen appears (checkout blocked)", () -> {
            Validations.validateTrue(loginPage.isLoginScreenDisplayed(),
                "Login should be required to proceed with checkout");
        });

        ReportUtils.step("Verifying login button is available", () -> {
            Validations.validateTrue(loginPage.verifyThatLoginButtonIsDisplayed(),
                "Login button should be visible to allow user to authenticate");
        });
    }

    // ========== EDGE CASE TESTS ==========

    @Test
    @Story("Edge Cases")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify same product with different colors appears as separate items")
    public void verifySameProductDifferentColorsInCart() {
        ReportUtils.step("Adding Backpack with black color", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.selectColor(Colors.BLACK);
            productDetailsPage.tapAddToCart();
            productDetailsPage.goBackToProducts();
        });

        ReportUtils.step("Adding Backpack with red color", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.selectColor(Colors.RED);
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        ReportUtils.step("Verifying 2 items in cart", () -> {
            Validations.validateTrue(cartPage.getTotalItemsCount() == 2,
                "Should have 2 separate items for different colors");
        });

        double expectedTotal = Products.BACKPACK.getPrice() * 2;
        ReportUtils.step("Verifying total is 2x Backpack price ($" + expectedTotal + ")", () -> {
            Validations.validateTrue(cartPage.verifyTotalPrice(expectedTotal),
                "Total should be $" + expectedTotal);
        });
    }

    @Test
    @Story("Edge Cases")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify removing item and re-adding works correctly")
    public void verifyRemoveAndReaddItem() {
        ReportUtils.step("Adding Backpack to cart", () -> {
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        ReportUtils.step("Removing item from cart", () -> {
            cartPage.tapRemoveItem();
        });

        ReportUtils.step("Verifying cart is empty", () -> {
            Validations.validateTrue(cartPage.isCartEmpty(),
                "Cart should be empty");
        });

        ReportUtils.step("Going back to shopping and re-adding same product", () -> {
            cartPage.tapGoShopping();
            productsPage.tapOnProduct(Products.BACKPACK);
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        ReportUtils.step("Verifying item is back in cart with correct price", () -> {
            Validations.validateTrue(cartPage.verifyTotalPrice(Products.BACKPACK.getPrice()),
                "Re-added Backpack should show $29.99");
        });
    }

    @Test
    @Story("Edge Cases")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify adding maximum quantity (high number) product to cart")
    public void verifyHighQuantityInCart() {
        int quantity = 5;
        double expectedTotal = Products.BIKE_LIGHT.getPrice() * quantity; // 9.99 * 5 = 49.95

        ReportUtils.step("Adding Bike Light with quantity " + quantity, () -> {
            productsPage.tapOnProduct(Products.BIKE_LIGHT);
            productDetailsPage.increaseQuantity(quantity - 1); // increase from 1 to 5
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        ReportUtils.step("Verifying cart shows " + quantity + " items", () -> {
            Validations.validateTrue(cartPage.getTotalItemsCount() == quantity,
                "Should show " + quantity + " items");
        });

        ReportUtils.step("Verifying total price: $" + expectedTotal, () -> {
            Validations.validateTrue(cartPage.verifyTotalPrice(expectedTotal),
                "Total should be $" + expectedTotal);
        });
    }

    @Test
    @Story("Product Verification")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify product name in cart matches the added product")
    public void verifyProductNameInCart() {
        ReportUtils.step("Adding Fleece Jacket to cart", () -> {
            productsPage.tapOnProduct(Products.FLEECE_JACKET);
            productDetailsPage.tapAddToCart();
            productDetailsPage.tapOnCartBadge();
        });

        ReportUtils.step("Verifying product name in cart", () -> {
            Validations.validateTrue(cartPage.isProductInCart("Sauce Labs Fleece Jacket"),
                "Cart should contain Fleece Jacket");
        });
    }
}
