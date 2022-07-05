package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SelectFlightPage extends CommonPageClass {

	public SelectFlightPage(WebDriver driver) {
		this.driver = driver;
	}
	
	
	public ArrayList<String> getPrice() {
		List<WebElement> options = driver.findElements(By.xpath(".//td[@class='data_center_mono']//font"));
		ArrayList<String> values = new ArrayList<String>();
		for(WebElement we : options) {
			values.add(we.getText());
			
		}
		return values;
	}
	
	public String getBackgroundColor() {
		String bgColor = driver.findElement(By.xpath(".//td[@frame_header_info']")).getCssValue("bgcolor");
		return bgColor;
	}
	
	public String getFlightNumber() {
		String flightNumber = driver.findElement(By.xpath(".//td[@class='data_left']//b[1]")).getText();
		return flightNumber;
	}
	
	public void clickContinue() {
		driver.findElement(By.name("reserveFlights")).click();
	}
	
	
}
