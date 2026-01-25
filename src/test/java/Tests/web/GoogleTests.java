package Tests.web;

import base.web.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.web.GooglePage;
import utils.Constants;
import utils.ReportUtils;
import utils.TestListener;
import utils.Validations;

@Listeners(TestListener.class)
@Epic("Web Testing")
@Feature("Google Search")
public class GoogleTests extends BaseTest {

    GooglePage googlePage;

    @BeforeMethod
    public void initPage() {
        googlePage = new GooglePage();
    }

    @Test
    @Story("Homepage")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that Google homepage opens and search box is displayed")
    public void verifyGooglePageOpens() {
        ReportUtils.step("Opening Google homepage", () -> {
            googlePage.openGoogle();
        });
        
        ReportUtils.step("Verifying search box is displayed", () -> {
            Validations.validateTrue(googlePage.isSearchBoxDisplayed(), 
                Constants.MSG_SEARCH_BOX_VISIBLE);
        });
    }

    @Test
    @Story("Search Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that searching for a term shows correct results")
    public void verifyGoogleSearch() {
        ReportUtils.step("Opening Google homepage", () -> {
            googlePage.openGoogle();
        });
        
        ReportUtils.step("Searching for: " + Constants.SEARCH_TERM_SELENIUM, () -> {
            googlePage.searchFor(Constants.SEARCH_TERM_SELENIUM);
        });

        ReportUtils.step("Verifying page title contains search term", () -> {
            Validations.validatePageTitleContains(Constants.SEARCH_TERM_SELENIUM);
        });
    }

    @Test
    @Story("Homepage")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that Google page title contains 'Google'")
    public void verifyGoogleTitle() {
        ReportUtils.step("Opening Google homepage", () -> {
            googlePage.openGoogle();
        });
        
        ReportUtils.step("Verifying page title contains 'Google'", () -> {
            Validations.validatePageTitleContains(Constants.GOOGLE_PAGE_TITLE);
        });
    }
}
