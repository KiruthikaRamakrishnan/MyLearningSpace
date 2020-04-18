package phase1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

public class TC003_MakeMyTrip {

	public static void main(String[] args) throws InterruptedException  {
		
//		1) Go to https://www.makemytrip.com/
		System.setProperty("webdriver.chrome.driver", "./drivers/Chromedriver/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notification");
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://www.makemytrip.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
//		2) Click Hotels
		driver.findElementByXPath("//span[text()='Hotels']").click();
		
//		3) Enter city as Goa, and choose Goa, India
		driver.findElementByXPath("//span[text()='City / Hotel / Area / Building']").click();
		driver.findElementById("city").sendKeys("goa", Keys.TAB);
		
//		4) Enter Check in date as Next month 15th (May 15) and Check out as start date+5
		driver.findElementById("checkin").click();
		String checkInDate = driver.findElementByXPath("(//div[text()='15'])[2]").getText();
		driver.findElementByXPath("(//div[text()='15'])[2]").click();
		int checkOutDate = Integer.parseInt(checkInDate)+5;
		driver.findElementByXPath("(//div[text()='"+checkOutDate+"'])[2]").click();
		
//		5) Click on ROOMS & GUESTS and click 2 Adults and one Children(age 12). Click Apply Button.
		driver.findElementById("guest").click();
		driver.findElementByXPath("(//li[text()='2'])[1]").click();
		driver.findElementByXPath("(//li[text()='1'])[2]").click();
		WebElement ageSelectBox = driver.findElementByClassName("ageSelectBox");
		Select ageDropDown = new Select(ageSelectBox);
		ageDropDown.selectByVisibleText("12");
		driver.findElementByXPath("//button[text()='APPLY']").click();
		
//		6) Click Search button
		driver.findElementById("hsw_search_button").click();
		
//		7) Select locality as Baga
		driver.findElementByXPath("//div[contains(@class, 'mmBackdrop wholeBlack')]").click();
		driver.findElementByXPath("//label[text()='Baga']").click();
		Thread.sleep(3000);
		
//		8) Select 5 start in Star Category under Select Filters
		driver.findElementByXPath("//label[text()='5 Star']").click();
		
//		9) Click on the first resulting hotel and go to the new window
		driver.findElementByXPath("(//p[@id='hlistpg_hotel_name'])[1]").click();
		Set<String> id = driver.getWindowHandles();
		List<String> windowId = new ArrayList<String>(id);
		driver.switchTo().window(windowId.get(1));		
		
//		10) Print the Hotel Name
		String hotelName = driver.findElementById("detpg_hotel_name").getText();
		System.out.println("hotel name: "+hotelName);
		
//		11) Click MORE OPTIONS link and Select 3Months plan and close
		driver.findElementByXPath("//span[text()='MORE OPTIONS']").click();
		WebElement table = driver.findElementByXPath("//table[@class='tblEmiOption']");
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		List<WebElement> secondColData = rows.get(1).findElements(By.tagName("td"));
		secondColData.get(secondColData.size()-1).click();
		driver.findElementByXPath("//span[@class='close']").click();
		
//		12) Click on BOOK THIS NOW
		driver.findElementById("detpg_headerright_book_now").click();
		
//		13) Print the Total Payable amount
		String totalPayableAmount = driver.findElementById("revpg_total_payable_amt").getText();
		System.out.println("total payable amount: "+totalPayableAmount);
		
//		14) Close the browser
		driver.quit();
				
	}

}
