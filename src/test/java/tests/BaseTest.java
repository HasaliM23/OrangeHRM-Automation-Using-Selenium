package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected static ExtentReports extent;
    protected ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("reports/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Tester", "QA Automation Engineer");
    }

    @BeforeMethod
    public void setup(ITestResult result) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://opensource-demo.orangehrmlive.com/");

        test = extent.createTest(result.getMethod().getMethodName());
    }

    // ✅ එකම එක නිවැරදි tearDown මෙතඩ් එක (IOException එක හසුරුවා ඇත)
    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            // 1. Screenshot එකක් ගන්නවා
            String screenshotRelativePath = captureScreenshot(result.getMethod().getMethodName());

            // 2. Extent Report එකට Screenshot එක ඇටෑච් කරනවා
            test.fail("Test Failed: " + result.getThrowable());
            test.fail("Failure Screenshot: ",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotRelativePath).build());

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed Successfully!");
        } else {
            test.skip("Test Skipped!");
        }

        if (driver != null) {
            driver.quit();
        }
    }


    // 📸 Screenshot එකක් අරන් සේව් කරන සුපිරිම මෙතඩ් එක
    public String captureScreenshot(String testName) {
        String screenshotFolder = "reports/screenshots/";
        String screenshotPath = screenshotFolder + testName + "_" + System.currentTimeMillis() + ".png";

        try {

            File folder = new File(screenshotFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File destination = new File(screenshotPath);
            FileHandler.copy(source, destination);
            System.out.println("Screenshot එක සාර්ථකව සේව් වුණා: " + screenshotPath);
        } catch (IOException e) {
            System.out.println("Screenshot එක ගන්න බැරි වුණා: " + e.getMessage());
        }
        return "screenshots/" + new File(screenshotPath).getName();
    }

    @AfterSuite
    public void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}