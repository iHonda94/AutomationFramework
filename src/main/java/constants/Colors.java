package constants;

/**
 * Enum representing all available color options for products.
 * The accessibilityId matches the content-desc attribute in the app.
 */
public enum Colors {
    BLACK("black circle"),
    BLUE("blue circle"),
    GRAY("gray circle"),
    RED("red circle");

    private final String accessibilityId;

    Colors(String accessibilityId) {
        this.accessibilityId = accessibilityId;
    }

    public String getAccessibilityId() {
        return accessibilityId;
    }
}
