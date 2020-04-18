package phase1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC002_Nykaa {

	public static void main(String[] args) throws InterruptedException {
		
//		1) Go to https://www.nykaa.com/
		System.setProperty("webdriver.chrome.driver", "./drivers/Chromedriver/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://www.nykaa.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
//		2) Mouseover on Brands and Mouseover on Popular
		WebElement brands = driver.findElementByXPath("//a[text()='brands']");
		WebElement popular = driver.findElementByXPath("//a[text()='Popular']");
		Actions builder = new Actions(driver);
		builder.moveToElement(brands).moveToElement(popular).perform();
		
//		3) Click L'Oreal Paris
		driver.findElementByXPath("(//a[starts-with(@href,'/brands/loreal-paris')])[1]").click();
		
//		4) Go to the newly opened window and check the title contains L'Oreal Paris
		Set<String> id = driver.getWindowHandles();
		List<String> windowId = new ArrayList<String>(id); 
		driver.switchTo().window(windowId.get(1));
		String newPageTitle = driver.getTitle();
		if (newPageTitle.contains("L'Oreal Paris")) {
			System.out.println("the title of new page contains L'Oreal Paris");
		} else {
			System.out.println("the title of new page dosen't contains L'Oreal Paris");
		}
		
//		5) Click sort By and select customer top rated
		driver.findElementByXPath("//span[text()='Sort By : ']").click();
		driver.findElementByXPath("//span[text()='customer top rated']").click();
		Thread.sleep(3000);
		
//		6) Click Category and click Shampoo
		driver.findElementByXPath("//div[text()='Category']").click();
		driver.findElementByXPath("//span[text()='Shampoo (21)']").click();
		Thread.sleep(3000);
		
//		7) check whether the Filter is applied with Shampoo
		boolean filter = driver.findElementByXPath("//span[text()='filters applied']").isDisplayed();
		String text = null;
		if (filter==true) {
			text = driver.findElementByXPath("//li[text()='Shampoo']").getText();
		} else {
			System.err.println("the Filter is not applied");
		}
		if (text.contains("Shampoo")) {
			System.out.println("the Filter is applied with Shampoo");
		} else {
			System.err.println("the Filter is not applied with Shampoo");
		}
		
//		8) Click on L'Oreal Paris Colour Protect Shampoo 
		driver.findElementByXPath("//span[contains(text(),'Oreal Paris Colour Protect Shampoo')]").click();
		
//		9) GO to the new window and select size as 175ml
		Set<String> newId = driver.getWindowHandles();
		List<String> newWindowsId = new ArrayList<>(newId);
		driver.switchTo().window(newWindowsId.get(2));
		driver.findElementByXPath("//span[text()='175ml']").click();
		
//		10) Print the MRP of the product
		String priceInfo = driver.findElementByXPath("(//div[@class='price-info'])[1]").getText().replaceAll("[^0-9]", "");
		System.out.println("the MRP of L'Oreal Paris Colour Protect Shampoo(175ml): "+priceInfo);
		
//		11) Click on ADD to BAG
		driver.findElementByXPath("(//button[text()='ADD TO BAG'])[1]").click();
		
//		12) Go to Shopping Bag
		driver.findElementByXPath("//div[@class='AddBagIcon']").click();
		
//		13) Print the Grand Total amount
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElementsByXPath("//div[text()='Shopping Bag']")));
		String grandTotal = driver.findElementByXPath("//div[text()='Grand Total:']//div").getText().replaceAll("[^0-9]", "");
		System.out.println("the Grand Total amount: "+grandTotal);
		
//		14) Click Proceed
		driver.findElementByXPath("//span[text()='Proceed']").click();
		
//		15) Click on Continue as Guest
		driver.findElementByXPath("//button[text()='CONTINUE AS GUEST']").click();
		
//		16) Print the warning message (delay in shipment)
		String warningMessage = driver.findElementByXPath("//i[starts-with(@class,'warning-icon')]//following-sibling::div").getText();
		System.out.println("warning message: "+warningMessage);
		
//		17) Close all windows
		driver.quit();
		

	}

}
