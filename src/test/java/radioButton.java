import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class radioButton {
	public Properties loadProperties() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\RestAPI\\AtulProjectTesting\\config.properties"); // Path to your
																											// config.properties
		prop.load(fis);
		return prop;
	}

	@Test
	public void clickRadiobtn() throws IOException, InterruptedException {
		Properties prop = loadProperties();

		// Get ChromeDriver path from properties file
		String chromeDriverPath = prop.getProperty("chromeDriverPath");

		// Set up the WebDriver with the ChromeDriver path from the properties file
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);

		// Set up the WebDriver

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		WebDriver driver = new ChromeDriver(options);

		driver.get(prop.getProperty("url"));

		// Set implicit wait and maximize window
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();

		// Open the URL

		// Explicit wait for the username and password
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement usernameElement = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Username : Admin']")));
		WebElement passwordElement = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Password : admin123']")));

		// Login
		String username = usernameElement.getText().split(":")[1].trim();
		String password = passwordElement.getText().split(":")[1].trim();
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
		driver.findElement(By.xpath("//button[contains(@class,'button')]")).click();

		// Navigate to 'My Info'
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='My Info']"))).click();

		// to click gender radio button
		List<WebElement> genderBtn = driver.findElements(By.xpath("//span[contains(@class, 'oxd-radio-input')]"));
		for (WebElement radiobutton : genderBtn) {
			// Click the span that corresponds to the radio button
			WebElement inputElement = driver.findElement(By.xpath(
					"//span[contains(@class,'oxd-radio-input')]/preceding-sibling::input[@value='2']/following-sibling::span"));
			if (inputElement != null) {
				wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				boolean value = inputElement.isSelected();
				System.out.println(value);
				wait = new WebDriverWait(driver, Duration.ofSeconds(5));
				if (inputElement.isSelected()) {
					inputElement.click(); // Click the span that visually represents the radio button

					/*
					 * wait = new WebDriverWait(driver, Duration.ofSeconds(10));
					 * 
					 * WebElement outputelement = driver.findElement(By .xpath(
					 * "//span[contains(@class,'oxd-radio-input')]/preceding-sibling::input[@value='1']/following-sibling::span"
					 * )); wait = new WebDriverWait(driver, Duration.ofSeconds(10)); boolean value1
					 * = outputelement.isSelected(); System.out.println(value1); wait = new
					 * WebDriverWait(driver, Duration.ofSeconds(5));
					 */
					break;
				}

			} else {
				System.out.println("helloworld");
			}
		}

	}

}
