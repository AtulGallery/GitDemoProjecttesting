import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class nykaShopping {
	@FindBy(xpath = "//ul[@class='MegaDropdownHeading']/li[1]")
	public static WebElement btn_makeup;

	@FindBy(xpath = "//a[text()='Lipstick']")
	public static WebElement btn_Lipstick;

	@FindBy(xpath = "//div[@class='product-listing']/div[@id='product-list-wrap']/div")
	public static WebElement list_ProductType;

	@FindBy(xpath = "//a[@id='category']")
	public static WebElement btn_Categories;
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "C:\\browsedriver\\chromedriver.exe");

		/*
		 * ChromeOptions options = new ChromeOptions();
		 * options.addArguments("--incognito");
		 */
		// WebDriver driver = new ChromeDriver(options);
		WebDriver driver = new ChromeDriver();

		driver.get("https://www.nykaa.com/");
		driver.manage().window().maximize();

		PageFactory.initElements(driver, nykaShopping.class);
		btn_Categories.click();

		Actions act = new Actions(driver);
		act.moveToElement(btn_makeup).perform();
		act.click(btn_Lipstick);

		String lipstic = driver.findElements((By) list_ProductType).toString();
		System.out.println(lipstic);

	}

}
