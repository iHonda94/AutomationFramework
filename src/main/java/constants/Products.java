package constants;

/**
 * Enum representing all available products in the app catalog.
 * Each product has an index (1-based) matching its position in the product list.
 */
public enum Products {
    BACKPACK(1, "Sauce Labs Backpack", 29.99),
    BIKE_LIGHT(2, "Sauce Labs Bike Light", 9.99),
    BOLT_TSHIRT(3, "Sauce Labs Bolt T-Shirt", 15.99),
    FLEECE_JACKET(4, "Sauce Labs Fleece Jacket", 49.99),
    ONESIE(5, "Sauce Labs Onesie", 7.99),
    TEST_TSHIRT(6, "Test.allTheThings() T-Shirt", 15.99);

    private final int index;
    private final String name;
    private final double price;

    Products(int index, String name, double price) {
        this.index = index;
        this.name = name;
        this.price = price;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
