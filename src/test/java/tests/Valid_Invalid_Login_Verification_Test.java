package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.OrangeLoginPage;

public class Valid_Invalid_Login_Verification_Test {

    WebDriver driver;
    WebDriverWait wait;
    OrangeLoginPage loginPage;
    @BeforeMethod

    public void setup(){

        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().window().maximize();

        loginPage = new OrangeLoginPage(driver);
    }

    @Test
    public void verifyValidLogin(){

        loginPage.loginToOrangeHRM("Admin","admin123");

        String expectedTitle = "OrangeHRM";
        String actualTilte =driver.getTitle();
    }

    @Test

    public void verifyLoginWithInvalidCredentials() {

        loginPage.loginToOrangeHRM("Admin", "12345");
        String actualMessage = loginPage.getErrorMessageText();
        String expectedErrorMessage = "Invalid credentials";

        Assert.assertEquals(actualMessage, expectedErrorMessage,
                "BUG! Correct error message for invalid credentials not displayed.");

    }

    @AfterMethod
    public void tearDown() {
        // 5. Test eka iwara unama automatic browser eka close karanna
        if (driver != null) {
            driver.quit();
        }
    }




}
