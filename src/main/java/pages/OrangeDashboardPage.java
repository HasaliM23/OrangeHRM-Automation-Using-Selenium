package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrangeDashboardPage {

    WebDriver driver;
    WebDriverWait wait;

    private By adminMenu = By.xpath("//span[text()='Admin']");
    private By searchUserNameField = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");
    private By searchButton = By.xpath("//button[@type='submit']");
    private By systemUsersHeader = By.xpath("//h5[text()='System Users']");

    public OrangeDashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void navigateToAdminModule() {
        wait.until(ExpectedConditions.elementToBeClickable(adminMenu)).click();
    }

    public void searchUserByUsername(String username) {
        // Wait for system users dashboard view to load properly
        wait.until(ExpectedConditions.visibilityOfElementLocated(systemUsersHeader));
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchUserNameField)).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public boolean isDashboardLoaded() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(adminMenu)).isDisplayed();
    }


}
