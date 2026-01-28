# Automation Framework

A test automation framework for **Web**, **Mobile**, **API**, and **Database** testing with CI/CD integration.

---

## Features

| Feature | Description |
|---------|-------------|
| Web Testing | Selenium WebDriver with Chrome, Firefox, Edge |
| Mobile Testing | Appium for Android and iOS |
| API Testing | RestAssured with Allure reporting |
| Database Testing | JDBC for SQL Server, MySQL, PostgreSQL |
| Allure Reporting | Interactive HTML test reports |
| CI/CD Pipeline | GitHub Actions with automatic execution |
| Headless Mode | Run without browser UI for CI/CD |

---

## Project Structure

```
AutomationFramework/
├── .github/workflows/
│   └── test.yml                  # CI/CD pipeline
│
├── src/main/java/
│   ├── base/                     # Base test classes
│   │   ├── mobile/BaseTest.java
│   │   └── web/BaseTest.java
│   │
│   ├── pages/                    # Page Objects
│   │   ├── mobile/
│   │   │   ├── HomePage.java
│   │   │   └── LoginPage.java
│   │   └── web/GooglePage.java
│   │
│   └── utils/                    # Utilities
│       ├── Actions.java          # UI actions
│       ├── ApiUtils.java         # API utilities
│       ├── Config.java           # Configuration reader
│       ├── Constants.java        # Test data & constants
│       ├── DatabaseUtils.java    # Database utilities
│       ├── DriverManager.java    # WebDriver management
│       ├── ReportUtils.java      # Allure reporting
│       ├── TestListener.java     # TestNG listener
│       └── Validations.java      # Assertions
│
├── src/main/resources/
│   ├── application.properties    # Configuration
│   └── allure.properties
│
├── src/test/java/Tests/
│   ├── api/ApiTests.java         # API tests
│   ├── db/DatabaseTests.java     # Database tests
│   ├── mobile/LoginPageTests.java # Mobile tests
│   └── web/GoogleTests.java      # Web tests
│
├── testng.xml                    # All tests
├── testng-web.xml                # Web tests only
├── testng-mobile.xml             # Mobile tests only
├── testng-api.xml                # API tests only
├── testng-db.xml                 # Database tests only
└── pom.xml                       # Maven dependencies
```

---

## Quick Start

### Run Web Tests
```bash
mvn test -Dtest=GoogleTests

# Headless mode
mvn test -Dtest=GoogleTests -Dheadless=true
```

### Run Mobile Tests
```bash
# Start Appium server first
appium

# Run mobile tests (requires emulator/device)
mvn test -Dtest=LoginPageTests
```

### Run API Tests
```bash
mvn test -Dtest=ApiTests
```

### Run Database Tests
```bash
mvn test -Dtest=DatabaseTests
```

### View Allure Report
```bash
allure serve target/allure-results
```

---

## Configuration

### application.properties
```properties
# Mobile
platformName=iOS

# Web
browser=chrome
headless=false

# API
api.baseUrl=https://api.example.com

# Database
db.type=sqlserver
db.host=your-server
db.port=1433
db.name=your-database
db.username=your-user
db.password=your-password
```

### Mobile Configuration (in Constants.java)
```java
// Device names
ANDROID_DEVICE_NAME = "emulator-5544"
IOS_DEVICE_NAME = "iPhone 16 Pro"
IOS_PLATFORM_VERSION = "18.2"

// App paths
ANDROID_APP_PATH = "/src/main/resources/AndroidDemo.apk"
IOS_APP_PATH = "/src/main/resources/iOSDemo.app"

// Appium server
APPIUM_SERVER_URL = "http://127.0.0.1:4723/"
```

### Constants.java
All test data is centralized in `Constants.java`:
- URLs and endpoints
- Credentials and tokens
- Expected values for validation

---

## Test Examples

### Web Test
```java
@Test
public void verifyGoogleSearch() {
    googlePage.openGoogle();
    googlePage.searchFor("Selenium");
    Validations.validatePageTitleContains("Selenium");
}
```

### Mobile Test
```java
@Test
public void verifyLogin() {
    loginPage.enterUsername("standard_user");
    loginPage.enterPassword("secret_sauce");
    loginPage.clickLogin();
    Validations.validateTrue(homePage.isDisplayed(), "Home page should be visible");
}
```

### API Test
```java
@Test
public void testGetProspect() {
    Response response = ApiUtils.get("/prospects/971124/get");
    ApiUtils.validateStatusCode(response, 200);
    
    String name = ApiUtils.getJsonValue(response, "data.accountName");
    Validations.validateEquals(name, "3 STAR BEER & WINE", "Name should match");
}
```

### Database Test
```java
@Test
public void testSelectPlans() {
    List<Map<String, Object>> results = db.executeQuery(
        "SELECT TOP 10 * FROM [MobileData].[t_Plan] WHERE CreatedBy = ?",
        "neaq5h"
    );
    Validations.validateTrue(results.size() > 0, "Should return results");
}
```

---

## CI/CD Pipeline

Tests run automatically on GitHub when you push code.

### Pipeline Flow
```
Push to GitHub → GitHub Actions → Run Tests → Generate Report → Publish to GitHub Pages
```

### View Results
- **GitHub Actions**: See pass/fail status
- **Allure Report**: https://honda1994.github.io/AutomationFramework/

---

## Utilities

### ApiUtils
```java
ApiUtils.setBaseUrl("https://api.example.com");
ApiUtils.setAuthToken("your-token");
ApiUtils.setHeader("key", "value");

Response response = ApiUtils.get("/endpoint");
ApiUtils.validateStatusCode(response, 200);
String value = ApiUtils.getJsonValue(response, "data.field");
```

### DatabaseUtils
```java
DatabaseUtils db = new DatabaseUtils();
List<Map<String, Object>> results = db.executeQuery("SELECT * FROM table WHERE id = ?", 1);
db.close();
```

### Validations
```java
Validations.validateTrue(condition, "message");
Validations.validateEquals(actual, expected, "message");
Validations.validatePageTitleContains("text");
```

---

## Technologies

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17 | Language |
| Selenium | 4.8.3 | Web automation |
| Appium | 8.5.1 | Mobile automation |
| RestAssured | 5.4.0 | API testing |
| TestNG | 7.7.0 | Test framework |
| Allure | 2.24.0 | Reporting |
| GitHub Actions | - | CI/CD |

---

## Live Report

https://honda1994.github.io/AutomationFramework/
