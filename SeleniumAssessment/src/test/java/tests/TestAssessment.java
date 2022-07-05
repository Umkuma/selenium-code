package tests;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.BookFlightPage;
import pages.ConfirmationPage;
import pages.LoginPage;
import pages.SearchFlightPage;
import pages.SelectFlightPage;

public class TestAssessment extends CommonTestClass {
	String loginPageUrl = applicationUrl;
	LoginPage objLoginPage;
	SearchFlightPage objSearchFlightPage;
	SelectFlightPage objSelectFlightPage;
	BookFlightPage objBookFlightPage;
	ConfirmationPage objConfirmationPage;

	@BeforeTest
	public void initialSetup() {
		driver.get(loginPageUrl);
		objLoginPage = new LoginPage(driver);
	}

	// Case1 : On the login page, verify that current date is displayed on top
	// section
	@Test(priority = 1)
	public void verifyDateOnLoginPage() {

		// get date displayed on login page
		String dateDisplayedonLoginPage = objLoginPage.getDate();

		// get current date
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();
		String currentDate = getmonthAbbr(month) + " " + day + ", " + year;

		// compare dates
		Assert.assertEquals(currentDate, dateDisplayedonLoginPage);
	}

	@DataProvider
	public Object[][] testData() {
		return new String[][] { { "mercury1", "12345" }, { "mercury2", "545657" } };
	}

	/*
	 * Case#2 : 2) Verify that when user enters incorrect username and password then
	 * following section should be displayed. Also try for 2 invalid username and
	 * password (mercury1 – mercury2)
	 */

	@Test(dataProvider = "testData", priority = 2)
	public void verifyScreenDisplayedIfInvalidCred(String userName, String password) {
		objLoginPage.enterUserName(userName);
		objLoginPage.enterPassword(password);
		objLoginPage.clickSignIn();

		// verify in-Valid Sign-On Page is Displayed or not
		Assert.assertTrue(objLoginPage.inValidSignOnPageDisplayed());
	}

	// Case#3: Verify that when user enters valid username and password then search
	// flight page should be displayed.
	@Test(priority = 3)
	public void verifySearchFlightPageDisplayedIfValidCred() {
		// username and password readed from config (in superclass)
		objLoginPage.enterUserName(username);
		objLoginPage.enterPassword(password);
		objLoginPage.clickSignIn();

		// verify search flight page is Displayed or not
		String actualPageTitle = driver.getTitle();
		String expectedPageTitle = "Find a Flight: Mercury Tours: ";
		Assert.assertEquals(actualPageTitle, expectedPageTitle);
	}

	// Case#4: Search Flight Page - Under the field “Departing From” , validate if
	// “India” is there as an option if not fail the test case
	@Test(priority = 4)
	public void verifyIndiaInFromDropDown() {
		objSearchFlightPage = new SearchFlightPage(driver);
		ArrayList<String> values = objSearchFlightPage.getValuesInDepartingFromDropDown();
		if (values.contains("India")) {
			System.out.println("India displayed in drop-down");
		} else {
			System.out.println("India not displayed in drop-down");
		}

	}

	// Case#5: Search Flight Page - User should be able to do valid search based on
	// “from”, “to” and “Service Class”
	@Test(priority = 5)
	public void verifyvalidSearch() {
		objSearchFlightPage.selecteDepartingFrom("London");
		objSearchFlightPage.selecteArrivingIn("Paris");
		objSearchFlightPage.selectClass("Coach");
		objSearchFlightPage.clickContinue();
	}

	// Case#6: Select Flight Page - The order of the flights displayed should be
	// sorted on its price
	// Case#7: Select Flight Page - Verify that the background color of headers is
	// blue
	@Test(priority = 6)
	public void verifySelectFlight() {
		// verify ordering of list is sorted on price or not
		objSelectFlightPage = new SelectFlightPage(driver);
		ArrayList<String> actualPriceList = objSelectFlightPage.getPrice();
		ArrayList<String> sortedPriceList = new ArrayList(actualPriceList);
		Collections.sort(sortedPriceList);
		boolean sorted = sortedPriceList.equals(actualPriceList);

		if (sorted) {
			System.out.println("List is sorted on price");
		} else {
			System.out.println("List is not sorted on price");
		}

		// verify background color
		String getBackgroundColor = objSelectFlightPage.getBackgroundColor();
		Assert.assertEquals("#003399", getBackgroundColor);
	}

	// Case#8: Book a Flight page - Verify that details of the flight number
	// displayed is same as selected in “Select Flight Page”
	// Case#9: Book a Flight page - 2) Put a synchronization point for the summary
	// section to be appeared.

	@Test(priority = 7)
	public void verifyBookFlight() {
		String flightNumberOnSelectFlightPage = objSelectFlightPage.getFlightNumber();
		objSelectFlightPage.clickContinue();

		// book flight page
		// wait for summary section to be displayed
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath(".//table//font[contains(text(),'Summary')]")));

		// get flightNumber On BookFlightPage
		objBookFlightPage = new BookFlightPage(driver);
		String flightNumberOnBookFlightPage = objBookFlightPage.getFlightNumber();

		Assert.assertEquals(flightNumberOnBookFlightPage, flightNumberOnSelectFlightPage);

	}

	// Case#10: On confirmation page, validate booked flight is appearing and get
	// flight confirmation number.
	// Case#11: On confirmation page, 2) User should be able to sign off after the
	// booking is done.

	@Test(priority = 8)
	public void verifyConfirmationPage() {
		// get flightNumber On BookFlightPage
		objBookFlightPage = new BookFlightPage(driver);
		String flightNumberOnBookFlightPage = objBookFlightPage.getFlightNumber();

		// confirmation page
		objConfirmationPage = new ConfirmationPage(driver);
		String flightDetails = objConfirmationPage.getFlightDetails();
		if (flightDetails.contains(flightNumberOnBookFlightPage)) {
			System.out.println("correct details displayed on confirmation page");
		} else {
			System.out.println("correct details not displayed on confirmation page");
		}
		objConfirmationPage.clickLogOff();
	}
}