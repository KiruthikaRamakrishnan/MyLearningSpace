package phase1;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC008_Pepperfry {

	public static void main(String[] args) throws InterruptedException, IOException {
		
//		1) Go to https://www.pepperfry.com/
		System.setProperty("webdriver.chrome.driver", "./drivers/Chromedriver/chromedriver.exe");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notification");
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://www.pepperfry.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
//		2) Mouseover on Furniture and click Office Chairs under Chairs
		try {
			driver.findElementByXPath("//div[@id='regPopUp']/a").click();
		} catch (NoSuchElementException e) {
			
		}
		WebElement furniture = driver.findElementByXPath("//a[@rel='meta-furniture']");
		Actions builder = new Actions(driver);
		builder.moveToElement(furniture).perform();
		driver.findElementByXPath("//a[contains(@href,'office-furniture-chairs') and text()='Office Chairs']").click();
		Thread.sleep(3000);
		
//		3) click Executive Chairs
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//h5[text()='Executive Chairs']")));
		driver.findElementByXPath("//h5[text()='Executive Chairs']").click();
		Thread.sleep(3000);
		
//		4) Change the minimum Height as 50 in under Dimensions
		driver.findElementByXPath("(//input[@class='clipFilterDimensionHeightValue'])[1]").clear();
		driver.findElementByXPath("(//input[@class='clipFilterDimensionHeightValue'])[1]").sendKeys("50",Keys.ENTER);
		Thread.sleep(3000);
		
//		5) Add "Poise Executive Chair in Black Colour" chair to Wishlist
		driver.findElementByXPath("//a[@data-productname='Poise Executive Chair in Black Colour']").click();
		
//		6) Mouseover on Homeware and Click Pressure Cookers under Cookware
		WebElement homeWare = driver.findElementByXPath("//a[@rel='meta-homeware']");
		builder.moveToElement(homeWare).perform();
		driver.findElementByXPath("//a[contains(@href,'kitchen-dining-pressure-cookers') and text()='Pressure Cookers']").click();
		Thread.sleep(1000);
		
//		7) Select Prestige as Brand
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//label[@for='brandsnamePrestige' and text()='Prestige']")));
		driver.findElementByXPath("//label[@for='brandsnamePrestige' and text()='Prestige']").click();
		Thread.sleep(1000);
		
//		8) Select Capacity as 1-3 Ltr
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//label[@for='capacity_db1_Ltr_-_3_Ltr' and text()='1 Ltr - 3 Ltr']")));
		driver.findElementByXPath("//label[@for='capacity_db1_Ltr_-_3_Ltr' and text()='1 Ltr - 3 Ltr']").click();
		Thread.sleep(1000);
		
//		9) Add "Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr" to Wishlist
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//a[@data-productname='Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr']")));
		driver.executeScript("window.scrollBy(0, 350)");
		driver.findElementByXPath("//a[@data-productname='Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr']").click();
		Thread.sleep(3000);
		
//		10) Verify the number of items in Wishlist
		String wishListNumber = driver.findElementByXPath("//a[@data-tooltip='Wishlist']/following::span[1]").getText();
		System.out.println("items in wishlist: "+wishListNumber);
		if (wishListNumber.equalsIgnoreCase("2")) {
			System.out.println("the number of items in Wishlist is verified");
		} else {
			System.out.println("the number of items in Wishlist is not verified");
		}
		Thread.sleep(1000);
		
//		11) Navigate to Wishlist
		driver.findElementByXPath("//a[@data-tooltip='Wishlist']").click();
		Thread.sleep(1000);
		
//		12) Move Pressure Cooker only to Cart from Wishlist
		driver.executeScript("window.scrollBy(0, 250)");
		driver.findElementByXPath("//a[text()='Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr By...']/../following::div/a[@class='addtocart_icon']").click();
		Thread.sleep(3000);
		
//		13) Check for the availability for Pincode 600128
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//div[@class='pin_block']/input")));
		driver.findElementByXPath("//div[@class='pin_block']/input").sendKeys("600128",Keys.ENTER);
		Thread.sleep(3000);
		
//		14) Click Proceed to Pay Securely
		driver.findElementByXPath("//a[@class='proceed_cta']").click();
		Thread.sleep(3000);
		
//		15 Click Proceed to Pay
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[@class='ck-proceed-btn-wrap']/a[1]")));
		driver.findElementByXPath("//span[@class='ck-proceed-btn-wrap']/a[1]").click();
		Thread.sleep(3000);
		
//		16) Capture the screenshot of the item under Order Item
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[text()='ORDER SUMMARY']")));
		driver.findElementByXPath("//span[text()='ORDER SUMMARY']").click();
		WebElement itemForScreenShot = driver.findElementByXPath("//li[contains(@id,'payment_cart')]");
		File src = itemForScreenShot.getScreenshotAs(OutputType.FILE);
		File dst = new File("./snaps/pepperfry.png");
		FileUtils.copyFile(src, dst);
		
//		17) Close the browser
		driver.close();
		
	}

}
