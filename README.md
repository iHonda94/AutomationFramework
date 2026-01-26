# Automation Framework

A professional test automation framework for **Web** and **Mobile** applications with built-in CI/CD integration.

---

## Features

| Feature | Description |
|---------|-------------|
| **Web Testing** | Selenium WebDriver with Chrome, Firefox, Edge support |
| **Mobile Testing** | Appium for Android and iOS applications |
| **Page Object Model** | Clean separation of test logic and page elements |
| **Allure Reporting** | Beautiful, interactive HTML test reports |
| **CI/CD Ready** | GitHub Actions pipeline with automatic test execution |
| **Headless Mode** | Run tests without browser UI (for servers) |
| **Auto Driver Management** | WebDriverManager handles browser drivers automatically |
| **Screenshot on Failure** | Automatic screenshots when tests fail |

---

## Project Structure

```
AutomationFramework/
├── .github/
│   └── workflows/
│       └── test.yml              # CI/CD pipeline configuration
│
├── src/
│   ├── main/java/
│   │   ├── base/                 # Base test classes
│   │   │   ├── mobile/
│   │   │   │   └── BaseTest.java     # Mobile driver setup (Appium)
│   │   │   └── web/
│   │   │       └── BaseTest.java     # Web driver setup (Selenium)
│   │   │
│   │   ├── pages/                # Page Object classes
│   │   │   ├── mobile/
│   │   │   │   ├── HomePage.java
│   │   │   │   └── LoginPage.java
│   │   │   └── web/
│   │   │       └── GooglePage.java
│   │   │
│   │   └── utils/                # Utility classes
│   │       ├── Actions.java          # UI actions (click, type, etc.)
│   │       ├── Config.java           # Configuration reader
│   │       ├── Constants.java        # Constant values
│   │       ├── DriverManager.java    # Centralized driver access
│   │       ├── ReportUtils.java      # Allure reporting utilities
│   │       ├── TestListener.java     # TestNG event listener
│   │       └── Validations.java      # Assertions and verifications
│   │
│   ├── main/resources/
│   │   ├── application.properties    # Configuration settings
│   │   ├── allure.properties         # Allure configuration
│   │   └── logback.xml               # Logging configuration
│   │
│   └── test/java/
│       └── Tests/                # Test classes
│           ├── mobile/
│           │   └── LoginPageTests.java
│           └── web/
│               └── GoogleTests.java
│
├── testng.xml                    # Full test suite (web + mobile)
├── testng-web.xml                # Web-only test suite (for CI/CD)
├── pom.xml                       # Maven dependencies
└── open-report.bat               # Opens Allure report locally
```

---

## Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Chrome, Firefox, or Edge browser
- (For mobile) Appium server and Android SDK / Xcode

### Run Web Tests

```bash
# Run with browser visible
mvn test

# Run in headless mode (no browser window)
mvn test -Dheadless=true

# Run specific test class
mvn test -Dtest=GoogleTests
```

### Run Mobile Tests

```bash
# Start Appium server first
appium

# Run mobile tests
mvn test -Dtest=LoginPageTests
```

### View Allure Report

```bash
# Option 1: Use the batch file (Windows)
open-report.bat

# Option 2: Command line
allure serve target/allure-results
```

---

## Configuration

### application.properties

```properties
# Browser: chrome, firefox, edge
browser=chrome

# Headless mode: true = no browser window
headless=false

# Mobile platform: Android, iOS
platformName=iOS
```

### Command Line Overrides

You can override any property from command line:

```bash
mvn test -Dbrowser=firefox -Dheadless=true
```

---

## CI/CD Pipeline

This framework includes a GitHub Actions pipeline that automatically runs tests when you push code.

### How It Works

```
┌─────────────────────────────────────────────────────────────┐
│   1. You push code to GitHub                                │
│                    ↓                                        │
│   2. GitHub Actions automatically triggers                  │
│                    ↓                                        │
│   3. A virtual machine runs your tests                      │
│                    ↓                                        │
│   4. Allure report is generated and published               │
│                    ↓                                        │
│   5. Report is available at:                                │
│      https://honda1994.github.io/AutomationFramework/       │
└─────────────────────────────────────────────────────────────┘
```

### Pipeline Triggers

| Trigger | When It Runs |
|---------|--------------|
| Push to main/master | Automatically |
| Pull Request | Automatically |
| Manual | Click "Run workflow" in Actions tab |

### View Results

1. Go to your repository on GitHub
2. Click **Actions** tab
3. Click on the latest workflow run
4. Download **allure-report** artifact, or
5. View online at: https://honda1994.github.io/AutomationFramework/

---

## Writing Tests

### Example Test Class

```java
package Tests.web;

import base.web.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.*;
import pages.web.GooglePage;
import utils.*;

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
    @Story("Search Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify search returns results")
    public void verifyGoogleSearch() {
        ReportUtils.step("Open Google", () -> {
            googlePage.openGoogle();
        });
        
        ReportUtils.step("Search for term", () -> {
            googlePage.searchFor("Selenium");
        });
        
        ReportUtils.step("Verify results", () -> {
            Validations.validatePageTitleContains("Selenium");
        });
    }
}
```

### Example Page Object

```java
package pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Actions;
import utils.DriverManager;

public class GooglePage {
    
    @FindBy(name = "q")
    private WebElement searchBox;
    
    public GooglePage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }
    
    public void openGoogle() {
        Actions.navigateTo("https://www.google.com");
    }
    
    public void searchFor(String text) {
        Actions.type(searchBox, text);
        Actions.pressEnter(searchBox);
    }
    
    public boolean isSearchBoxDisplayed() {
        return searchBox.isDisplayed();
    }
}
```

---

## Utility Classes

### Actions (UI Interactions)

```java
// Navigation
Actions.navigateTo("https://example.com");
Actions.refreshPage();
Actions.goBack();

// Clicks
Actions.click(element);
Actions.doubleClick(element);
Actions.rightClick(element);

// Typing
Actions.type(element, "text");
Actions.clearText(element);
Actions.pressEnter(element);

// Dropdowns
Actions.selectByText(dropdown, "Option");
Actions.selectByValue(dropdown, "value");
Actions.selectByIndex(dropdown, 0);

// Waits
Actions.waitForVisible(element, 10);
Actions.waitForClickable(element, 10);

// Getters
String title = Actions.getPageTitle();
String url = Actions.getCurrentUrl();
String text = Actions.getText(element);
```

### Validations (Assertions)

```java
// Element validations
Validations.validateElementIsDisplayed(element, "Login button");
Validations.validateElementIsEnabled(element, "Submit button");
Validations.validateTextEquals(element, "Welcome", "Header");
Validations.validateTextContains(element, "Hello", "Greeting");

// Page validations
Validations.validatePageTitle("Google");
Validations.validatePageTitleContains("Search");
Validations.validateUrl("https://example.com");
Validations.validateUrlContains("/dashboard");

// Boolean validations
Validations.validateTrue(condition, "Should be true");
Validations.validateFalse(condition, "Should be false");
Validations.validateEquals(actual, expected, "Values should match");

// Non-asserting checks (return boolean)
boolean visible = Validations.isDisplayed(element);
boolean enabled = Validations.isEnabled(element);
```

### ReportUtils (Allure Reporting)

```java
// Add steps to report
ReportUtils.step("Click login button", () -> {
    loginPage.clickLogin();
});

// Attach data to report
ReportUtils.attachText("Response", jsonResponse);
ReportUtils.captureScreenshot(driver, "Current State");
```

---

## Technologies

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17 | Programming language |
| Selenium | 4.8.3 | Web automation |
| Appium | 8.5.1 | Mobile automation |
| TestNG | 7.7.0 | Test framework |
| Allure | 2.24.0 | Test reporting |
| WebDriverManager | 5.6.2 | Auto driver management |
| Maven | 3.6+ | Build tool |
| GitHub Actions | - | CI/CD pipeline |

---

## Email Notifications

To receive email notifications when tests fail:

1. Go to: https://github.com/settings/notifications
2. Under **"Actions"**, select your notification preference:
   - **Failed workflows only** (recommended)
   - **All workflows**
3. Save changes

You'll now receive emails whenever your pipeline fails!

---

## Live Report

View your latest test report online:

**https://honda1994.github.io/AutomationFramework/**

---

## License

This project is for learning and demonstration purposes.
