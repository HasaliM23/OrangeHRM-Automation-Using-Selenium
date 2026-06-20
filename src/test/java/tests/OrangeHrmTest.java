package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.OrangeAdminPage;
import pages.OrangeDashboardPage;
import pages.OrangeLoginPage;
import pages.OrangePimPage;

public class OrangeHrmTest {

    WebDriver driver;
    OrangeLoginPage loginPage;
    OrangeDashboardPage dashboardPage;
    OrangePimPage pimPage;
    OrangeAdminPage adminPage;

    @BeforeMethod
    public void setup (){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        loginPage = new OrangeLoginPage(driver);
        dashboardPage = new OrangeDashboardPage(driver);
        pimPage = new OrangePimPage(driver);
        adminPage = new OrangeAdminPage(driver);

    }

    @Test
    public void testAdminUserSearchFlow() {
        // Step 1: Login
        loginPage.loginToOrangeHRM("Admin", "admin123");

        // Assert dashboard loaded properly
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard failed to load!");

        // Step 2: Navigate and Search
        dashboardPage.navigateToAdminModule();
        dashboardPage.searchUserByUsername("Admin");

        // (Wade lassanata check karanna script eka complete wenna methana dashboard verify assetion danna puluwan)
    }

    @Test

    public void verifyLoginWithInvalidCredentials() {

        loginPage.loginToOrangeHRM("Admin", "12345");
        String actualMessage = loginPage.getErrorMessageText();
        String expectedErrorMessage = "Invalid credentials";

        Assert.assertEquals(actualMessage, expectedErrorMessage,
                "BUG! Correct error message for invalid credentials not displayed.");

    }

    @Test
    public void verifyLoginWithEmptyFields() {

        loginPage.clickLogin();
        var errors = loginPage.getRequiredErrorElements();

        // 3. Check user name and psswrod
        Assert.assertEquals(errors.size(), 2, "BUG! Two required error messages should display.");

        // 4. check error msg has requied
        Assert.assertEquals(errors.get(0).getText(), "Required", "Username error text mismatch!");
        Assert.assertEquals(errors.get(1).getText(), "Required", "Password error text mismatch!");

    }


    @Test

    public void verifyAddEmployeeFlow(){

        // 1. Login to the system

        loginPage.loginToOrangeHRM("Admin","admin123");

        // 2. Navigate to PIM and click Add Employee

        pimPage.navigateToPimModule();
        pimPage.clickAddEmployee();

        // 3. Add new employee details

        String fname = "Lionel";
        String lname="Messi";

        pimPage.addNewEmployee(fname,lname);

        // 4. Verification: Check if profile created successfully with the correct name
        String expectedFullName = fname + " " + lname;
        String actualProfileName = pimPage.getProfileHeaderName();

        Assert.assertEquals(actualProfileName, expectedFullName,
                "BUG! Employee profile header name does not match the created employee.");

    }

    @Test

    public void verifyAddEmployeeAndJobTitleFlow() {

        // 1. Login to the system
        loginPage.loginToOrangeHRM("Admin", "admin123");

        // 2. Navigate to PIM and click Add Employee

        pimPage.navigateToPimModule();
        pimPage.clickAddEmployee();

        // 3. Add new employee details

        String fname = "Lionel";
        String lname="Messi";
        pimPage.addNewEmployee(fname, lname);

       // Verification 1: Profile Created successfully
        String expectedFullName = fname + " " + lname;
        String actualProfileName = pimPage.getProfileHeaderName();
        Assert.assertEquals(actualProfileName, expectedFullName, "Profile header name mismatch!");

        pimPage.navigateToJobSection();
        pimPage.updateJobTitle("QA Engineer");



    }

    @Test

    public void verifyAddNewJobTitle(){

        loginPage.loginToOrangeHRM("Admin","admin123");
        adminPage.navigateToJobTitles();

        String uniqueJobTitle = "QA Automation Lead 2026";
        adminPage.addNewJobTitle(
                uniqueJobTitle,
                "Responsible for designing and maintaining the automation framework.",
                "Created via Selenium Automation Script."
        );


    }






    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}
