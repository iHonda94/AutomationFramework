package pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Actions;
import utils.Constants;
import utils.DriverManager;
import utils.Validations;

/**
 * Page Object for Google homepage.
 * Contains elements and actions for Google search page.
 */
public class GooglePage {

    @FindBy(name = "q")
    private WebElement searchBox;

    @FindBy(name = "btnK")
    private WebElement searchButton;

    @FindBy(name = "btnI")
    private WebElement feelingLuckyButton;

    public GooglePage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    public void openGoogle() {
        Actions.navigateTo(Constants.GOOGLE_URL);
    }

    public void searchFor(String text) {
        Actions.type(searchBox, text);
        Actions.pressEnter(searchBox);
    }

    public void clickSearchButton() {
        Actions.click(searchButton);
    }

    public boolean isSearchBoxDisplayed() {
        return Validations.isDisplayedWithWait(searchBox, 10);
    }

    public String getSearchBoxText() {
        return Actions.getAttribute(searchBox, "value");
    }

    public String getTitle() {
        return Actions.getPageTitle();
    }
}
