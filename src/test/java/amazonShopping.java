import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*@author: Atul Kumar Pathak
TestCase 02: validate Pricing from low to high of lg sound bar on amazon.com
Date: 13/09/2024*/

public class amazonShopping {

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver", "C:\\browsedriver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		driver.get("https://www.amazon.in/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		WebElement searchboxtb = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
		searchboxtb.sendKeys("lg soundbar");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		WebElement submitbtn = driver.findElement(By.id("nav-search-submit-button"));
		submitbtn.click();

		List<WebElement> productNames = driver
				.findElements(By.cssSelector("span.a-size-medium.a-color-base.a-text-normal"));
		List<WebElement> productPrices = driver.findElements(By.cssSelector("span.a-price-whole"));

		HashMap<String, Integer> products = new HashMap<>();

		for (int i = 0; i < productNames.size() && i < productPrices.size(); i++) {
			String soundbarname = productNames.get(i).getText();
			String soundbarprice = productPrices.get(i).getText().replace(",", "");
			int soundbarprices = Integer.parseInt(soundbarprice);
			products.put(soundbarname, soundbarprices);
		}

		List<Map.Entry<String, Integer>> sortedProducts = new ArrayList<>(products.entrySet());
		sortedProducts.sort(new Comparator<Map.Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				// TODO Auto-generated method stub
				return 0;
			}

			/*
			 * public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer>
			 * e2) { return e1.getValue().compareTo(e2.getValue()); }
			 */
		});

		for (Map.Entry<String, Integer> entry : sortedProducts) {

			System.out.println(entry.getValue() + " " + entry.getKey());
		}

		driver.quit();
	}
}
