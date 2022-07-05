package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends CommonPageClass {

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public String getDate() {
		WebElement dateElement = driver.findElement(By.xpath(".//form[@action='login.php']//b"));
		String dateDisplayedonLoginPage = dateElement.getText().toString();
		return dateDisplayedonLoginPage;
	}

	public void enterUserName(String userName) {
		driver.findElement(By.name("userName")).sendKeys(userName);
	}

	public void enterPassword(String password) {
		driver.findElement(By.name("password")).sendKeys(password);
	}

	public void clickSignIn() {
		driver.findElement(By.name("login")).click();
	}

	public boolean inValidSignOnPageDisplayed() {
		WebElement elm = driver.findElement(By.xpath("//table/img[@src='/images/masts/mast_signon.gif']"));
		if (elm.isDisplayed()) {
			return true;
		} else
			return false;
	}

}
