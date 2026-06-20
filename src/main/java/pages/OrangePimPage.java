package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrangePimPage {

    WebDriver driver;
    WebDriverWait wait;

    // 1. Locators

    private By pimMenu = By.xpath("//span[text()='PIM']");
    private By addEmployeeButton = By.xpath("//button[@class='oxd-button oxd-button--medium oxd-button--secondary']");
    private By firstNameField = By.xpath("//input[@placeholder = 'First Name']");
    private By lastNameField =By.xpath("//input[@placeholder='Last Name']");
    private By saveButton = By.xpath("//button[.=' Save ']");
    private By profileHeaderName = By.xpath("//div[contains(@class,'orangehrm-edit-employee-name')]//h6");

    private By jobSideMenu = By.xpath("//a[text()='Job']");


    private By jobTitleDropDown = By.xpath("//label[text()='Job Title']/following::div[1]");


    private By dropDownOptions = By.xpath("//div[@role='listbox']//div[@role='option']");

    private By jobSaveButton = By.xpath("//button[@type='submit']");


    // 2. Constructor

    public OrangePimPage(WebDriver driver){

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));


    }

    // 3. Actions

    public void navigateToPimModule(){
        wait.until(ExpectedConditions.elementToBeClickable(pimMenu)).click();

    }
    public void clickAddEmployee(){
        wait.until(ExpectedConditions.elementToBeClickable(addEmployeeButton)).click();

    }

    public void addNewEmployee(String firstName, String lastName){
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).sendKeys(firstName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameField)).sendKeys(lastName);
        By formLoader = By.xpath("//div[@class='oxd-form-loader']");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(formLoader));
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();

    }

    public String getProfileHeaderName() {

        org.openqa.selenium.WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(profileHeaderName));

        wait.until(driver -> !header.getText().trim().isEmpty());

        return header.getText();
    }

    public void navigateToJobSection(){
        wait.until(ExpectedConditions.elementToBeClickable(jobSideMenu)).click();
    }

    public  void updateJobTitle(String jobTitleName) {

        wait.until(ExpectedConditions.elementToBeClickable(jobTitleDropDown)).click();


        String dynamicXpath = "//div[@role='listbox']//span[text()='" + jobTitleName + "']";

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dynamicXpath))).click();


        wait.until(ExpectedConditions.elementToBeClickable(jobSaveButton)).click();


    }







}
