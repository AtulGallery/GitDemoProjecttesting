import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class calender {
    @Test
    public void openCalendar() {
        System.setProperty("webdriver.chrome.driver", "C:\\browsedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        String monthnumber = "6"; // Month index (June)
        String day = "15"; // Day of the month
        String year = "2030"; // Year to select
        String[] actuallist = { monthnumber, day, year };

        driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");
        driver.manage().window().maximize();

        // Click to open the calendar
        driver.findElement(By.xpath("//div[contains(@class,'react-date-picker__inputGroup')]")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the calendar to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(@class,'react-calendar__navigation__label')]")));

        // Select the year
        driver.findElement(By.xpath("//button[contains(@class,'react-calendar__navigation__label')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".react-calendar__navigation__label")));
        driver.findElement(By.cssSelector(".react-calendar__navigation__label")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='" + year + "']")));
        driver.findElement(By.xpath("//button[text()='" + year + "']")).click();

        // Select the month dynamically using the month number
        List<WebElement> months = driver.findElements(By.xpath("//div[@class='react-calendar__year-view__months']//button"));
        months.get(Integer.parseInt(monthnumber) - 1).click(); // Subtracting 1 as index is 0-based

        // Select the day dynamically
        driver.findElement(By.xpath("//abbr[text()='" + day + "']")).click();

        // Now check if the correct date is displayed in the input fields
        List<WebElement> expectedlist = driver.findElements(By.cssSelector(".react-date-picker__inputGroup__input"));

        System.out.println("Size of expectedlist: " + expectedlist.size());

        // Ensure the size of the expected list matches the actuallist
        if (expectedlist.size() != actuallist.length) {
            System.out.println("Error: Expected list size does not match actual list size.");
        } else {
            // Validate the values
            for (int i = 0; i < expectedlist.size(); i++) {
                System.out.println(expectedlist.get(i).getAttribute("value"));
                Assert.assertEquals(expectedlist.get(i).getAttribute("value"), actuallist[i]);
            }
        }

        // Close the driver
        driver.quit();
    }
}
