
public class modifiedCode {
	
	

		@Test(dataProvider = "testexcel", dataProviderClass = TestData.class)
		public void openAmazon(String firstname, String middlename, String lastname, String dob) throws IOException {

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

			// Click on the "My Info" section
			driver.findElement(By.xpath("//span[text()='My Info']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Date of Birth']/ancestor::div[@class='oxd-input-group__label-wrapper']/following-sibling::div/div/div/input")));
			
			// Split the date of birth from Excel into day, month, year
			String[] dobParts = dob.split("-");
			String day = dobParts[0];      // Day part (17)
			String month = dobParts[1];    // Month part (12)
			String year = dobParts[2];     // Year part (1995)

			// Click on the calendar input to open it
			WebElement calendarbtn = driver.findElement(By.xpath(
					"//label[text()='Date of Birth']/ancestor::div[@class='oxd-input-group__label-wrapper']/following-sibling::div/div/div/input"));
			calendarbtn.click();
			calendarbtn.sendKeys(Keys.chord(Keys.CONTROL, "a")); // Select all
			calendarbtn.sendKeys(Keys.BACK_SPACE); // Delete selected text

			// Select the year
			WebElement yearDropdown = driver.findElement(By.xpath("//div[contains(@class, 'oxd-calendar-selector-year')]"));
			yearDropdown.click();
			driver.findElement(By.xpath("//li[text()='" + year + "']")).click(); // Select the correct year

			// Select the month
			WebElement monthDropdown = driver.findElement(By.xpath("//div[contains(@class, 'oxd-calendar-selector-month-selected')]"));
			monthDropdown.click();
			String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
			String monthText = months[Integer.parseInt(month) - 1];  // Get month name from number
			driver.findElement(By.xpath("//li[text()='" + monthText + "']")).click(); // Select the correct month

			// Select the day
			driver.findElement(By.xpath("//div[text()='" + day + "']")).click(); // Select the correct day
			
			System.out.println("Date of birth selected: " + day + " " + monthText + " " + year);
		}

	}


}
