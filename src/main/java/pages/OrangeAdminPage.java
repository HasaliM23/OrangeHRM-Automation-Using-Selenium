package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrangeAdminPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By adminModule = By.xpath("//span[text()='Admin']");
    private By jobMenu = By.xpath("//span[contains(text(),'Job')]");
    private By jobTitlesOption = By.xpath("//a[text()='Job Titles']");
    private By addBtn = By.xpath("//button[normalize-space()='Add']");
    private By jobTitleInput = By.xpath("//label[text()='Job Title']/following::input[1]");
    private By jobDescInput = By.xpath("//textarea[@placeholder='Type description here']");
    private By jobNoteInput = By.xpath("//textarea[@placeholder='Add note']");
    private By saveBtn = By.xpath("//button[@type='submit']");
    private By toastMessage = By.xpath("//div[@id='oxd-toast-container']//p[contains(@class,'toast-message')]");

    // Constructor
    public OrangeAdminPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    // 2. Actions / Methods

    public void navigateToJobTitles(){
        wait.until(ExpectedConditions.elementToBeClickable(adminModule)).click();
        wait.until(ExpectedConditions.elementToBeClickable(jobMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(jobTitlesOption)).click();
    }

    public void addNewJobTitle(String title, String description,String note ){
        wait.until(ExpectedConditions.elementToBeClickable(addBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(jobTitleInput)).sendKeys(title);
        wait.until(ExpectedConditions.visibilityOfElementLocated(jobDescInput)).sendKeys(description);
        wait.until(ExpectedConditions.visibilityOfElementLocated(jobNoteInput)).sendKeys(note);
        wait.until(ExpectedConditions.elementToBeClickable(saveBtn)).click();
    }


}
