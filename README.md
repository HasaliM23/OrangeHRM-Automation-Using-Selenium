# OrangeHRM Automation Framework (Page Object Model)

This is a robust and scalable Web Automation Framework built using **Java**, **Selenium WebDriver**, and **TestNG** based on the **Page Object Model (POM)** architectural pattern.

## 🚀 Key Features Automaed
- **Authentication:** Valid/Invalid Login workflows.
- **PIM Module:** End-to-End Employee Creation and dynamic Profile verification.
- **Job Profile Management:** Updating individual employee job titles using dynamic element locators.
- **Admin Module:** Adding new company-wide Job Titles with dynamic table verification (eliminating flaky toast messages).

## 🛠️ Tech Stack Used
- **Language:** Java (JDK 24)
- **Automation Tool:** Selenium WebDriver (v4.35.0)
- **Testing Framework:** TestNG (v7.11.0)
- **Build Tool:** Maven
- **IDE:** IntelliJ IDEA

## 📂 Project Architecture
```text
src/
 ├── main/java/pages/        # Page Classes (Locators & Actions)
 │    ├── OrangeLoginPage.java
 │    ├── OrangeDashboardPage.java
 │    ├── OrangePimPage.java
 │    └── OrangeAdminPage.java
 └── test/java/tests/        # Test Classes (TestNG Assertions)
      └── OrangeHrmTest.java