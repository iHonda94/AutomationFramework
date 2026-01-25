# Mobile & Web Automation Framework

A simple, clean test automation framework for Mobile (Android/iOS) and Web applications.

---

## Project Structure

```
src/
├── main/java/
│   ├── base/                       # Base classes
│   │   ├── mobile/
│   │   │   └── BaseTest.java       # Mobile driver setup (Appium)
│   │   └── web/
│   │       └── BaseTest.java       # Web driver setup (Selenium)
│   │
│   ├── pages/                      # Page Object classes
│   │   ├── mobile/
│   │   │   ├── HomePage.java       # Mobile home screen
│   │   │   └── LoginPage.java      # Mobile login screen
│   │   └── web/
│   │       └── GooglePage.java     # Google webpage
│   │
│   └── utils/                      # Utility classes
│       ├── Config.java             # Reads application.properties
│       ├── Constants.java          # All constant values
│       ├── DriverManager.java      # Centralized driver management
│       ├── Actions.java            # UI actions (click, type, etc.)
│       ├── Validations.java        # Assertions and verifications
│       ├── ReportUtils.java        # Allure reporting utilities
│       └── TestListener.java       # TestNG listener
│
├── main/resources/
│   ├── application.properties      # Configuration
│   ├── logback.xml                 # Logging configuration
│   └── allure.properties           # Allure configuration
│
└── test/java/
    └── Tests/                      # Test classes
        ├── mobile/
        │   └── LoginPageTests.java
        └── web/
            └── GoogleTests.java
```

---

## Class Relationships

```
┌─────────────────────────────────────────────────────────────────┐
│                    FRAMEWORK ARCHITECTURE                        │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│   TEST CLASSES                    PAGE CLASSES                   │
│   ────────────                    ────────────                   │
│   GoogleTests ──extends──> BaseTest    GooglePage                │
│   LoginPageTests ─extends─> BaseTest   LoginPage, HomePage       │
│                                                                  │
│                         UTILITY CLASSES                          │
│                         ───────────────                          │
│   ┌─────────────┐    ┌─────────────┐    ┌──────────────┐        │
│   │DriverManager│◄───│   Actions   │    │ Validations  │        │
│   │ (holds      │◄───│ (click,type)│    │ (assertions) │        │
│   │  driver)    │    └─────────────┘    └──────────────┘        │
│   └─────────────┘                                                │
│         ▲                                                        │
│         │                                                        │
│   ┌─────────────┐                                                │
│   │  BaseTest   │ ← Creates driver, sets in DriverManager        │
│   └─────────────┘                                                │
│                                                                  │
└─────────────────────────────────────────────────────────────────┘
```

---

## How It Works

### Test Execution Flow

```
1. TestNG starts
       ↓
2. BaseTest.setUp()
   → Creates driver (Chrome/Firefox/Appium)
   → DriverManager.setDriver(driver)
       ↓
3. Test class @BeforeMethod
   → Creates page objects (GooglePage, LoginPage)
   → PageFactory finds all @FindBy elements
       ↓
4. @Test method runs
   → Page methods call Actions (click, type)
   → Actions get driver from DriverManager
       ↓
5. Test finishes (pass/fail)
       ↓
6. BaseTest.tearDown()
   → If failed: capture screenshot for Allure
   → driver.quit()
   → DriverManager.clearDriver()
```

---

## Key Classes Explained

### BaseTest
**Job:** Create and destroy the browser/app driver

```java
public class BaseTest {
    public static WebDriver driver;
    
    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        DriverManager.setDriver(driver);  // Save for everyone
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ReportUtils.captureScreenshot(driver, "Failure");
        }
        driver.quit();
        DriverManager.clearDriver();
    }
}
```

### DriverManager
**Job:** Hold the driver so all classes can access it without passing it around

```java
DriverManager.setDriver(driver);   // BaseTest calls this
DriverManager.getDriver();         // Actions/Validations call this
DriverManager.clearDriver();       // Cleanup after test
```

### Page Classes (GooglePage, LoginPage, etc.)
**Job:** Represent a screen/page with its elements and actions

```java
public class GooglePage {
    
    @FindBy(name = "q")
    private WebElement searchBox;
    
    public GooglePage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }
    
    public void searchFor(String text) {
        Actions.type(searchBox, text);
        Actions.pressEnter(searchBox);
    }
}
```

### Actions
**Job:** Common UI actions - no need to pass driver!

```java
Actions.click(element);
Actions.type(element, "text");
Actions.navigateTo("https://google.com");
Actions.getPageTitle();
```

### Validations
**Job:** Assertions and checks - no need to pass driver!

```java
Validations.validateTrue(condition, "message");
Validations.validatePageTitleContains("Google");
Validations.isDisplayed(element);
```

---

## Design Patterns Used

| Pattern | Where | Purpose |
|---------|-------|---------|
| **Page Object Model** | Page classes | Separate page structure from test logic |
| **Factory Pattern** | PageFactory.initElements() | Create and initialize page elements |
| **Singleton (Partial)** | DriverManager, BaseTest.driver | Single shared driver instance |

---

## SOLID Principles Explained

### S - Single Responsibility Principle ✓

**Rule:** Each class should do ONE thing only.

| Class | Its ONE Job |
|-------|-------------|
| `Config.java` | Read settings from properties file |
| `Constants.java` | Store constant values |
| `DriverManager.java` | Hold the driver |
| `Actions.java` | Perform UI actions (click, type) |
| `Validations.java` | Check if things are correct |
| `BaseTest.java` | Open and close the browser |
| `GooglePage.java` | Handle Google page only |
| `GoogleTests.java` | Run Google tests only |

**Example - Why this is good:**
```
If you need to change how clicking works:
→ You only change Actions.java
→ You don't touch any other file!
```

---

### O - Open/Closed Principle ✓

**Rule:** Add NEW code without CHANGING existing code.

```
Want to add FacebookPage?
─────────────────────────
✓ CREATE: FacebookPage.java (new file)
✗ DON'T MODIFY: GooglePage.java, Actions.java, BaseTest.java

Want to add FacebookTests?
──────────────────────────
✓ CREATE: FacebookTests.java extends BaseTest (new file)
✗ DON'T MODIFY: GoogleTests.java, BaseTest.java
```

**The framework is OPEN for adding new pages/tests, but CLOSED for modification.**

---

### D - Dependency Inversion Principle ✓ (Partial)

**Rule:** High-level code should not know low-level details.

```
TEST (high-level)
      │
      │  uses
      ▼
PAGE OBJECT (abstraction)
      │
      │  hides
      ▼
WEBELEMENT (low-level detail)
```

**Bad Example:**
```java
// Test knows TOO MUCH about how to find elements
@Test
public void testSearch() {
    WebElement searchBox = driver.findElement(By.name("q"));  // Low-level!
    searchBox.sendKeys("hello");
}
```

**Good Example (Your Framework):**
```java
// Test only knows about page methods
@Test
public void testSearch() {
    googlePage.searchFor("hello");  // Clean! No WebElement details
}
```

---

### Principles NOT Implemented

| Principle | Why Not |
|-----------|---------|
| **L - Liskov Substitution** | Not applicable - we don't substitute classes |
| **I - Interface Segregation** | Framework doesn't use interfaces (kept simple) |

---

## How to Run

### Web Tests
```bash
# From IDE: Right-click on GoogleTests → Run

# From command line:
mvn test -Dtest=GoogleTests
```

### Mobile Tests (requires Appium)
```bash
# Start Appium server first
appium

# Run tests
mvn test -Dtest=LoginPageTests
```

### View Allure Report
```bash
# After running tests, double-click:
open-report.bat

# Or run:
allure serve target/allure-results
```

---

## Configuration

### application.properties
```properties
# Mobile platform (Android or iOS)
platformName=iOS

# Web browser (chrome, firefox, edge)
browser=chrome
```

---

## Adding New Pages

### Web Page
```java
package pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Actions;
import utils.DriverManager;

public class NewPage {
    
    @FindBy(id = "myElement")
    private WebElement myElement;
    
    public NewPage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }
    
    public void doSomething() {
        Actions.click(myElement);
    }
}
```

### Mobile Page
```java
package pages.mobile;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.Actions;
import utils.DriverManager;
import java.time.Duration;

public class NewPage {
    
    @AndroidFindBy(accessibility = "my element")
    private WebElement myElement;
    
    public NewPage() {
        PageFactory.initElements(
            new AppiumFieldDecorator(DriverManager.getDriver(), Duration.ofSeconds(10)), 
            this
        );
    }
    
    public void doSomething() {
        Actions.click(myElement);
    }
}
```

---

## Adding New Tests

```java
package Tests.web;

import base.web.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.*;
import pages.web.NewPage;
import utils.ReportUtils;
import utils.TestListener;
import utils.Validations;

@Listeners(TestListener.class)
@Epic("Web Testing")
@Feature("New Feature")
public class NewTests extends BaseTest {
    
    NewPage newPage;
    
    @BeforeMethod
    public void initPage() {
        newPage = new NewPage();
    }
    
    @Test
    @Story("New Story")
    @Description("Test description")
    public void testSomething() {
        ReportUtils.step("Do something", () -> {
            newPage.doSomething();
        });
        
        ReportUtils.step("Verify result", () -> {
            Validations.validateTrue(true, "Should pass");
        });
    }
}
```

---

## Wrapper Classes Reference

### Actions.java
```java
// Clicks
Actions.click(element);
Actions.doubleClick(element);
Actions.rightClick(element);

// Typing
Actions.type(element, "text");
Actions.appendText(element, "text");
Actions.pressEnter(element);
Actions.clearText(element);

// Navigation
Actions.navigateTo("url");
Actions.refreshPage();
Actions.goBack();
Actions.goForward();

// Getters
Actions.getPageTitle();
Actions.getCurrentUrl();
Actions.getText(element);
Actions.getAttribute(element, "attr");

// Dropdowns
Actions.selectByText(dropdown, "Option");
Actions.selectByValue(dropdown, "value");
Actions.selectByIndex(dropdown, 0);

// Other
Actions.hover(element);
Actions.scrollToElement(element);
Actions.waitForVisible(element, 10);
Actions.waitForClickable(element, 10);
```

### Validations.java
```java
// Element validations
Validations.validateElementIsDisplayed(element, "name");
Validations.validateElementIsEnabled(element, "name");
Validations.validateElementIsSelected(element, "name");

// Text validations
Validations.validateTextEquals(element, "expected", "name");
Validations.validateTextContains(element, "expected", "name");

// Page validations
Validations.validatePageTitle("title");
Validations.validatePageTitleContains("text");
Validations.validateUrl("url");
Validations.validateUrlContains("text");

// Boolean validations
Validations.validateTrue(condition, "message");
Validations.validateFalse(condition, "message");
Validations.validateEquals(actual, expected, "message");

// Non-asserting checks (return boolean)
Validations.isDisplayed(element);
Validations.isEnabled(element);
Validations.isDisplayedWithWait(element, 10);
```

---

## Technologies Used

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17 | Programming language |
| Appium | 8.5.1 | Mobile automation |
| Selenium | 4.8.3 | Web automation |
| TestNG | 7.7.0 | Test framework |
| Allure | 2.24.0 | Test reporting |
| SLF4J + Logback | 2.0.9 | Logging |
| WebDriverManager | 5.6.2 | Auto browser driver management |

---

## Naming Conventions

| Category | Convention | Example |
|----------|------------|---------|
| Classes | PascalCase | `LoginPage`, `GoogleTests` |
| Methods | camelCase | `typeUserName()`, `clickLogin()` |
| Variables | camelCase | `searchBox`, `loginButton` |
| Packages | lowercase | `pages.mobile`, `tests.web` |
| Constants | SCREAMING_SNAKE | `GOOGLE_URL`, `DEFAULT_TIMEOUT` |
