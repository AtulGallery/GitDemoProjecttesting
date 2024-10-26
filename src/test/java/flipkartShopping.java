import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class flipkartShopping {
	ExtentReports reports;
	
	WebDriver driver;
	
	 public Properties loadProperties() throws IOException {
	        Properties prop = new Properties();
	        FileInputStream fis = new FileInputStream("C:\\RestAPI\\AtulProjectTesting\\config.properties"); // Path to your config.properties
	        prop.load(fis);
	        return prop;
	    }
	 
	 //extent reports
	 //extentspark reporter
	 @BeforeTest
	 public void configextentreport() {
	String path =	 System.getProperty("user.dir") +"\\reports\\index.html";
	ExtentSparkReporter reporter = new ExtentSparkReporter(path);
	reporter.config().setReportName("Web Automation results");
	reporter.config().setDocumentTitle("Test results");
	
	 reports = new ExtentReports();
	reports.attachReporter(reporter);
	reports.setSystemInfo("reporter", "Atul");
	
	 }
	 
	 public void getScreenshot(String testcasename) throws IOException
	 {
		TakesScreenshot ts = (TakesScreenshot)driver;
	File source =	ts.getScreenshotAs(OutputType.FILE);
	File file = new File(System.getProperty("user.dir") + "//reports//" + testcasename + ".png");
	FileUtils.copyFile(source, file);
		 
	 }
    @Test(dataProvider = "testexcel", dataProviderClass = TestData.class)
    public void openAmazon(String firstname, String middlename, String lastname, String dob) throws IOException, InterruptedException {
    	configextentreport();
    	reports.createTest("open webpage");
    	
    	 Properties prop = loadProperties();

         // Get ChromeDriver path from properties file
         String chromeDriverPath = prop.getProperty("chromeDriverPath");

         // Set up the WebDriver with the ChromeDriver path from the properties file
         System.setProperty("webdriver.chrome.driver", chromeDriverPath);
         
        // Set up the WebDriver
       
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
         driver = new ChromeDriver(options);
        
        driver.get(prop.getProperty("url"));

        // Set implicit wait and maximize window
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Open the URL
      

        // Explicit wait for the username and password
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement usernameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Username : Admin']")));
        WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Password : admin123']")));

        // Login
        String username = usernameElement.getText().split(":")[1].trim();
        String password = passwordElement.getText().split(":")[1].trim();
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys(username);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[contains(@class,'button')]")).click();

        // Navigate to 'My Info'
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='My Info']"))).click();
        
       //to click gender radio button
        List<WebElement> genderBtn = driver.findElements(By.xpath("//span[contains(@class, 'oxd-radio-input')]"));
        for (WebElement radiobutton : genderBtn) {
            // Click the span that corresponds to the radio button
            WebElement inputElement = driver.findElement(By.xpath("//span[contains(@class,'oxd-radio-input')]/preceding-sibling::input[@value='2']"));
            if (inputElement != null) {
                radiobutton.click(); // Click the span that visually represents the radio button
                break;
            }
            else {
            	System.out.println("helloworld");
            }
        }
        

        driver.close();
        
        driver.findElement(By.xpath("//label[text()='Nationality']/ancestor::div[@class='oxd-input-group__label-wrapper']/following-sibling::div//div[@class='oxd-select-text--after']")).click();
        
     List<WebElement>  countriesname = driver.findElements(By.xpath("//div[@class='oxd-select-option']"));
     for(int i =0 ; i<countriesname.size(); i++) {
    String countryname =	 countriesname.get(i).getText();
    if(countryname.equalsIgnoreCase("Indian")) {
    	driver.findElement(By.xpath("//span[text()='Indian']")).click();
    }
    else {
    	System.out.println("India is not found");
    }
    	
     }

        // Process Date of Birth (DOB)
        if (dob != null && !dob.isEmpty() && dob.contains("-") && dob.split("-").length == 3) {
            String[] dobParts = dob.split("-");
            String day = dobParts[0]; // Month in number format
            String month = dobParts[1];   // Day
            String year = dobParts[2];  // Year
            
            System.out.println("Day: " + day + ", Month: " + month + ", Year: " + year);

            // Open the calendar
            WebElement calendarBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[text()='Date of Birth']/ancestor::div[@class='oxd-input-group__label-wrapper']/following-sibling::div/div/div/input")));
            calendarBtn.click();

            // Select month
            WebElement monthDropdown = driver.findElement(By.xpath("//div[contains(@class, 'oxd-calendar-selector-month')]"));
            monthDropdown.click();

            String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            System.out.println("Length of months array: " + months.length);

            // Convert month from string to index (e.g., "04" should map to index 3, April)
            int monthIndex = Integer.parseInt(month) - 1; // Ensure month is between 0 and 11
            if (monthIndex >= 0 && monthIndex < months.length) {
                WebElement monthOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//li[normalize-space(text())='" + months[monthIndex] + "']")));
                monthOption.click();
            } else {
                System.out.println("Error: Invalid month index " + monthIndex);
                return; // Stop execution if month index is invalid
            }

            // Select year
            WebElement yearDropdown = driver.findElement(By.xpath("//div[contains(@class, 'oxd-calendar-selector-year')]"));
            yearDropdown.click();
            WebElement yearOption = driver.findElement(By.xpath("//li[contains(text(), '" + year + "')]"));
            yearOption.click();

            // Select day
            WebElement datepicker = driver.findElement(By.xpath("//div[text()='" + day + "']"));
            datepicker.click();
        } else {
            System.out.println("Error: Invalid date of birth format or dob is null.");
            System.out.println("Received dob: " + dob);  // Debugging the dob value
        }
reports.flush();
        // Cleanup
        driver.quit();
    }
}
