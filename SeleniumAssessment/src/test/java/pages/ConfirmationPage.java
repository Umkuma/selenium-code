package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConfirmationPage extends CommonPageClass {

	public ConfirmationPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String getFlightDetails() {
		String flightDetails = driver.findElement(By.xpath(".//table//td[@class='frame_header_info']//font")).getText();
		return flightDetails;
	}
	
	public void clickLogOff() {
		driver.findElement(By.xpath(".//img[@src='/images/forms/Logout.gif']")).click();
	}
}
