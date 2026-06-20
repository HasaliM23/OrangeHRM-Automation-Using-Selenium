package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrangeLoginPage {

    WebDriver driver;
    WebDriverWait wait;

    private By usernamefield = By.xpath("//input[@placeholder='Username']");
    private By passwordfeild = By.xpath("//input[@placeholder='Password']");
    private By loginbtnfeild = By.xpath("//button[normalize-space()='Login']");
    private By errormessage = By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']");
    private By requiredErrors = By.xpath("//span[contains(@class,'oxd-input-group__message')]");


    public OrangeLoginPage(WebDriver driver){

        this.driver = driver;
        this.wait= new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    public void enteruser(String username){
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernamefield)).sendKeys(username);
    }

    public void enterPSW(String Password){
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordfeild)).sendKeys(Password);
    }

    public void clickLogin(){
        wait.until(ExpectedConditions.elementToBeClickable(loginbtnfeild)).click();
    }

    public String getErrorMessageText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errormessage)).getText();
    }

    public java.util.List<org.openqa.selenium.WebElement> getRequiredErrorElements(){
        wait.until(ExpectedConditions.presenceOfElementLocated(requiredErrors));
        return driver.findElements(requiredErrors);
    }

    // Combined business logic method
    public void loginToOrangeHRM(String username, String password) {
        this.enteruser(username);
        this.enterPSW(password);
        this.clickLogin();
    }


}
