package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.OrangeAdminPage;
import pages.OrangeDashboardPage;
import pages.OrangeLoginPage;
import pages.OrangePimPage;

public class OrangeHrmTest extends BaseTest {


    @Test
    public void testAdminUserSearchFlow() {
        OrangeLoginPage loginPage = new OrangeLoginPage(driver);
        OrangeDashboardPage dashboardPage = new OrangeDashboardPage(driver);

        loginPage.loginToOrangeHRM("Admin", "admin123");
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard failed to load!");

        dashboardPage.navigateToAdminModule();
        dashboardPage.searchUserByUsername("Admin");
    }

    @Test
    /*public void verifyLoginWithInvalidCredentials() {
        OrangeLoginPage loginPage = new OrangeLoginPage(driver);

        loginPage.loginToOrangeHRM("Admin", "12345");
        String actualMessage = loginPage.getErrorMessageText();
        String expectedErrorMessage = "Invalid credentials";

        Assert.assertEquals(actualMessage, expectedErrorMessage,
                "BUG! Correct error message for invalid credentials not displayed.");
    }
*/
    // 1. Create an array of invalid test data sets that need to be tested.
    @DataProvider(name = "invalidLoginData")
    public Object[][] getInvalidLoginData() {
        return new Object[][] {
                { "Admin", "12345" },       // 1 Wrong Password
                { "WrongUser", "admin123" }, // 2 Wrong Username)
                { "FakeUser", "FakePass" }   // 3 වBoth incorrect
        };
    }

    // 2. Link the DataProvider to this test method.
    @Test(dataProvider = "invalidLoginData")
    public void verifyLoginWithInvalidCredentials(String uname, String pword) {
        OrangeLoginPage loginPage = new OrangeLoginPage(driver);

        // Use the uname and pword values from the DataProvider instead of hardcoded values.
        loginPage.loginToOrangeHRM(uname, pword);
        String actualMessage = loginPage.getErrorMessageText();
        String expectedErrorMessage = "Invalid credentials";

        Assert.assertEquals(actualMessage, expectedErrorMessage,
                "BUG! Correct error message for invalid credentials not displayed.");
    }

    @Test
    public void verifyLoginWithEmptyFields() {
        OrangeLoginPage loginPage = new OrangeLoginPage(driver);

        loginPage.clickLogin();
        var errors = loginPage.getRequiredErrorElements();

        Assert.assertEquals(errors.size(), 2, "BUG! Two required error messages should display.");
        Assert.assertEquals(errors.get(0).getText(), "Required", "Username error text mismatch!");
        Assert.assertEquals(errors.get(1).getText(), "Required", "Password error text mismatch!");
    }

    @Test
    public void verifyAddEmployeeFlow() {
        OrangeLoginPage loginPage = new OrangeLoginPage(driver);
        OrangePimPage pimPage = new OrangePimPage(driver);

        loginPage.loginToOrangeHRM("Admi", "admin12");
        pimPage.navigateToPimModule();
        pimPage.clickAddEmployee();

        String fname = "Lionel";
        String lname = "Messi";
        pimPage.addNewEmployee(fname, lname);

        String expectedFullName = fname + " " + lname;
        String actualProfileName = pimPage.getProfileHeaderName();
        Assert.assertEquals(actualProfileName, expectedFullName,
                "BUG! Employee profile header name does not match the created employee.");
    }

    @Test
    public void verifyAddEmployeeAndJobTitleFlow() {
        OrangeLoginPage loginPage = new OrangeLoginPage(driver);
        OrangePimPage pimPage = new OrangePimPage(driver);

        loginPage.loginToOrangeHRM("Admin", "admin123");
        pimPage.navigateToPimModule();
        pimPage.clickAddEmployee();

        String fname = "Lionel";
        String lname = "Messi";
        pimPage.addNewEmployee(fname, lname);

        String expectedFullName = fname + " " + lname;
        String actualProfileName = pimPage.getProfileHeaderName();
        Assert.assertEquals(actualProfileName, expectedFullName, "Profile header name mismatch!");

        pimPage.navigateToJobSection();
        pimPage.updateJobTitle("QA Engineer");
    }

    @Test
    public void verifyAddNewJobTitle() {
        OrangeLoginPage loginPage = new OrangeLoginPage(driver);
        OrangeAdminPage adminPage = new OrangeAdminPage(driver);

        loginPage.loginToOrangeHRM("Admin", "admin123");
        adminPage.navigateToJobTitles();

        String uniqueJobTitle = "QA Automation Lead 2026";
        adminPage.addNewJobTitle(
                uniqueJobTitle,
                "Responsible for designing and maintaining the automation framework.",
                "Created via Selenium Automation Script."
        );
    }

    @Test
    public void verifyDeleteEmployeeFlow() {
        OrangeLoginPage loginPage = new OrangeLoginPage(driver);
        OrangeDashboardPage dashboardPage = new OrangeDashboardPage(driver);
        OrangePimPage pimPage = new OrangePimPage(driver);

        loginPage.loginToOrangeHRM("Admin", "admin123");
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard failed to load!");

        pimPage.navigateToPimModule();
        pimPage.clickAddEmployee();

        String empId = pimPage.addNewEmployee("Cristiano", "Ronaldo");
        pimPage.getProfileHeaderName();

        pimPage.deleteEmployeeById(empId);


    }
}