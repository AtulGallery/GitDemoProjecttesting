import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

@Test
public class seleniumPractice {

	public Properties loadProperties() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\RestAPI\\AtulProjectTesting\\config.properties"); // Path to your
																											// config.properties
		prop.load(fis);
		return prop;
	}

	public void getDropdownvalues() throws IOException {
		
		

		Properties prop = loadProperties();

		// Get ChromeDriver path from properties file
		String chromeDriverPath = prop.getProperty("chromeDriverPath");

		// Set up the WebDriver with the ChromeDriver path from the properties file
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);

		 WebDriver driver = new ChromeDriver();

	        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
	        driver.manage().window().maximize();
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        // Create a HashMap to store course names and prices
	        HashMap<String, String> coursePriceMap = new HashMap<>();

	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("window.scrollBy(0,500)");
	        
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@name='courses']")));

	        
	        
	        // Locate the table rows
	        List<WebElement> rows = driver.findElements(By.xpath("//table[@name='courses']/tbody/tr"));

	        // Loop through each row and extract course name and price
	        for (WebElement row : rows) {
	            // Extract course name and price from each row
	            String course = row.findElement(By.xpath("./td[2]")).getText();  // Adjusted to use relative XPath
	            String price = row.findElement(By.xpath("./td[3]")).getText();   // Adjusted to use relative XPath

	            // Store the course name and price in the HashMap
	            coursePriceMap.put(course, price);
	        }

	        // Print the extracted course names and prices from the HashMap
	        for (String courses : coursePriceMap.keySet()) {
	            System.out.println("Course: " + courses + ", Price: " + coursePriceMap.get(courses));
	        }

	      // driver.quit();  // Don't forget to quit the WebDriver session

		/*
		 * JavascriptExecutor js = (JavascriptExecutor) driver;
		 * 
		 * js.executeScript("window.scrollBy(0,500)");
		 * 
		 * driver.findElement(By.id("country")).click(); List<WebElement> countriesname
		 * = driver.findElements(By.xpath("//select[@id='country']/option"));
		 * 
		 * //to store country names in list , we have to create a new arraylist
		 * 
		 * ArrayList<String> countryList = new ArrayList<>();
		 * 
		 * 
		 * for (WebElement name : countriesname) {
		 * 
		 * String countryName = name.getAttribute("value");
		 * countryList.add(countryName); }
		 * 
		 * Collections.sort(countryList);
		 * 
		 * for(String country : countryList) { System.out.println(country); }
		 */

	}

}
