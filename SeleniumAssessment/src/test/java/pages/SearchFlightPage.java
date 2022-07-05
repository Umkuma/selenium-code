package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SearchFlightPage extends CommonPageClass{
	public SearchFlightPage(WebDriver driver) {
		this.driver = driver;
	}

	public ArrayList<String> getValuesInDepartingFromDropDown() {
		List<WebElement> fromDropDown = driver.findElements(By.xpath(".//select[@name='fromPort']"));
		ArrayList<String> dropDownValues = new ArrayList<String>();
		for(WebElement we : fromDropDown ) {
			dropDownValues.add(we.getText());
		}
		return dropDownValues;
	}
	
	public void selecteDepartingFrom(String option) {
		new Select(driver.findElement(By.xpath(".//select[@name='fromPort']"))).selectByValue(option);
	}
	
	public void selecteArrivingIn(String option) {
		new Select(driver.findElement(By.xpath(".//select[@name='toPort']"))).selectByValue(option);
	}
	
	public void selectClass(String option) {
		driver.findElement(By.xpath(".//input[@value='"+option+"']")).click();
	}
	
	public void clickContinue() {
		driver.findElement(By.name("findFlights")).click();
	}
}
