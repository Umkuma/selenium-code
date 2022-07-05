package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;

public class CommonTestClass {
	public WebDriver driver;
	public String applicationUrl;
	public String username;
	public String password;

	public CommonTestClass() {
		// read config file
		readConfigFile();

		// instantiate driver
		String chromeDriverPath = System.getProperty("user.dir") + "\\src\\test\\resources\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}

	public void readConfigFile() {
		Properties prop = new Properties();
		File file = new File("Config.properties");
		FileInputStream inputStream = null;

		try {
			inputStream = new FileInputStream(file);
			try {
				prop.load(inputStream);
				applicationUrl = prop.getProperty("applicationUrl");
				username = prop.getProperty("username");
				password = prop.getProperty("password");
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String getmonthAbbr(int month) {
		String monthAbr = null;

		switch (month) {

		case 1:
			monthAbr = "Jan";
			break;

		case 2:
			monthAbr = "Feb";
			break;
		case 3:
			monthAbr = "Mar";
			break;
		case 4:
			monthAbr = "Apr";
			break;

		default:
			monthAbr = "yetToDo";
			break;
		}

		return monthAbr;

	}

	@AfterSuite
	public void closeBrowser() {
		driver.quit();
	}
	
	public void loginToApplication() {
		
	}

}
