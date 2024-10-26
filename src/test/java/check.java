import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.IOException;
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.time.Duration;
	import java.util.ArrayList;
	import java.util.Calendar;
	import java.util.Date;
	import java.util.Iterator;
	import java.util.List;

	import org.apache.poi.ss.usermodel.Cell;
	import org.apache.poi.ss.usermodel.Row;
	import org.apache.poi.xssf.usermodel.XSSFSheet;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.Keys;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.chrome.ChromeOptions;
	import org.openqa.selenium.interactions.Actions;
	import org.openqa.selenium.support.FindBy;
	import org.openqa.selenium.support.PageFactory;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.WebDriverWait;
	import org.testng.Assert;
	import org.testng.annotations.DataProvider;
	import org.testng.annotations.Test;

public class check {
	
	

	

	    @Test(dataProvider = "testexcel", dataProviderClass = TestData.class)
	    public void openAmazon(String firstname, String middlename, String lastname, String dob) throws IOException, InterruptedException, ParseException {

	        // Set up the WebDriver
	        System.setProperty("webdriver.chrome.driver", "C:\\browserdrivers\\chromedriver.exe");

	        ChromeOptions options = new ChromeOptions();
	        options.addArguments("--incognito");
	        WebDriver driver = new ChromeDriver(options);

	        // Set implicit wait for general cases (e.g., loading new pages)
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	        driver.manage().window().maximize();

	        // Open the URL
	        driver.get("https://opensource-demo.orangehrmlive.com/auth/login");

	        // Explicit wait to ensure page elements are ready
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        // Fetch and trim the username
	        WebElement usernameElement = wait
	                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Username : Admin']")));
	        String username = usernameElement.getText().split(":")[1].trim();
	        System.out.println("Username: " + username);

	        // Fetch and trim the password
	        WebElement passwordElement = wait
	                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Password : admin123']")));
	        String password = passwordElement.getText().split(":")[1].trim();
	        System.out.println("Password: " + password);

	        // Enter the username and password
	        driver.findElement(By.xpath("//input[@name='username']")).sendKeys(username);
	        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);

	        // Click the login button and wait for the dashboard to load
	        driver.findElement(By.xpath("//button[contains(@class,'button')]")).click();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='My Info']")));

	        // Click "My Info" section
	        driver.findElement(By.xpath("//span[text()='My Info']")).click();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Date of Birth']/ancestor::div[@class='oxd-input-group__label-wrapper']/following-sibling::div/div/div/input")));

	        // Retrieve the calendar element
	        WebElement calendarbtn = driver.findElement(By.xpath("//label[text()='Date of Birth']/ancestor::div[@class='oxd-input-group__label-wrapper']/following-sibling::div/div/div/input"));
	        
	        // Use JavaScript to retrieve multiple possible properties
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        String valueAttribute = (String) js.executeScript("return arguments[0].value;", calendarbtn);
	        String textContent = (String) js.executeScript("return arguments[0].textContent;", calendarbtn);
	        String innerText = (String) js.executeScript("return arguments[0].innerText;", calendarbtn);

	        // Log all possible values
	        System.out.println("Value attribute: " + valueAttribute);
	        System.out.println("Text Content: " + textContent);
	        System.out.println("Inner Text: " + innerText);

	        // Convert the fetched date (if available) to the expected format and compare with the date from Excel
	        if (valueAttribute != null && !valueAttribute.isEmpty()) {
	            // Assuming the date format is "yyyy-MM-dd"
	            String formattedSelectedDob = convertDateFormat(valueAttribute, "yyyy-MM-dd", "MM/dd/yy");
	            Assert.assertEquals(formattedSelectedDob, dob, "DOB does not match!");
	            System.out.println("DOB successfully selected: " + formattedSelectedDob);
	        } else {
	            System.out.println("No date found in the field!");
	        }

	        // Close the browser
	        driver.quit();
	    }

	    // Utility method to convert date format
	    private String convertDateFormat(String date, String currentFormat, String targetFormat) throws ParseException {
	        SimpleDateFormat currentSdf = new SimpleDateFormat(currentFormat);
	        SimpleDateFormat targetSdf = new SimpleDateFormat(targetFormat);
	        String formattedDate = "";

	        try {
	            Date parsedDate = currentSdf.parse(date); // Parse the date
	            formattedDate = targetSdf.format(parsedDate); // Format the date to the target format
	        } catch (ParseException e) {
	            System.out.println("Failed to parse the date: " + date);
	            throw e;  // Throw the exception so it can be handled
	        }
	        
	        return formattedDate;
	    }
}


