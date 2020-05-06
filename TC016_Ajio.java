package phase1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC016_Ajio {

	public static void main(String[] args) throws InterruptedException {
		
//		1) Go to https://www.ajio.com/
		System.setProperty("webdriver.chrome.driver", "./drivers/Chromedriver/chromedriver.exe");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://www.ajio.com/shop/sale");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
//		2) Enter Bags in the Search field and Select Bags in Women Handbags
		driver.findElementByXPath("//input[@placeholder='Search AJIO']").sendKeys("Bags");
		driver.findElementByXPath("(//span[text()='Women Handbags']/parent::a)[1]").click();
		Thread.sleep(3000);
		
//		3) Click on five grid and Select SORT BY as "What's New"
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//div[@class='five-grid-container ']")));
		driver.findElementByXPath("//div[@class='five-grid-container ']").click();
		driver.findElementByXPath("//div[@class='filter-dropdown']/select").click();
		driver.findElementByXPath("//option[@value='newn']").click();
		
//		4) Enter Price Range Min as 2500 and Max as 5000
		driver.findElementByXPath("//span[text()='price']").click();
		driver.findElementByXPath("//input[@id='minPrice']").sendKeys("2500");
		driver.findElementByXPath("//input[@id='maxPrice']").sendKeys("5000",Keys.ENTER);
		Thread.sleep(3000);
		
//		5) Click on the product "Puma Ferrari LS Shoulder Bag"
		driver.findElementByXPath("//img[@alt='Puma Red Shoulder Ferrari LS Shoulder Bag']").click();
		Set<String> id = driver.getWindowHandles();
		List<String> windowId = new ArrayList<>(id);
		driver.switchTo().window(windowId.get(1));
		
//		6) Verify the Coupon code for the price above 2690 is applicable for your product, if applicable the get the Coupon Code and Calculate the discount price for the coupon
		String price = driver.findElementByXPath("//div[@class='prod-sp']").getText().replaceAll("[^0-9]", "");
		double productPrice = Double.parseDouble(price);
		double  discount=0; 
		if (productPrice>=2690) {
			System.out.println("the price is above 2690 coupon code applicable");
			String couponCode = driver.findElementByXPath("//div[@class='promo-title']").getText();
			System.out.println("for coupon "+couponCode);
		} else {
			System.out.println("coupon not applicable");
		}
		discount=(productPrice*28)/100;
		int discountAmount = (int) Math.round(discount);
		System.out.println("discount price: "+discountAmount);
		
//		7) Check the availability of the product for pincode 560043, print the expected delivery date if it is available
		driver.findElementByXPath("//span[text()='Enter pin-code to know estimated delivery date.']").click();
		driver.findElementByXPath("//input[@class='edd-pincode-modal-text']").sendKeys("560043");
		driver.findElementByXPath("//button[@class='edd-pincode-modal-submit-btn']").click();
		boolean pinCodeCheck = driver.findElementByXPath("//div[@class='edd-message-success']").isDisplayed();
		if (pinCodeCheck==true) {
			String date = driver.findElementByXPath("//ul[@class='edd-message-success-details']/li/span").getText();
			System.out.println("Expected Delivery: "+date);
		} else {
			System.out.println("product not avilable for the pincode");
		}
		
//		8) Click on Other Informations under Product Details and Print the Customer Care address, phone and email
		driver.findElementByXPath("//div[@class='other-info-toggle']").click();
		String customerCareAddress = driver.findElementByXPath("//span[text()='Customer Care Address']/following::span[2]").getText();
		System.out.println(customerCareAddress);
		
//		9) Click on ADD TO BAG and then GO TO BAG
		driver.findElementByXPath("//div[@class='pdp-addtocart-button']/div").click();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//div[text()='PROCEED TO BAG']")));
		driver.findElementByXPath("//div[text()='PROCEED TO BAG']").click();
		
//		10) Check the Order Total before apply coupon
		String orderTotal = driver.findElementByXPath("//section[@id='orderTotal']/span[2]").getText().replaceAll("[a-z]", "");
		if (orderTotal.contains(price)) {
			System.out.println("order total verified");
		} else {
			System.out.println("order total is not verified");
		}
		
//		11) Enter Coupon Code and Click Apply
		driver.findElementByXPath("//input[@id='EPIC']").click();
		driver.findElementByXPath("//button[text()='Apply']").click();
		Thread.sleep(3000);
		
//		12) Verify the Coupon Savings amount(round off if it in decimal) under Order Summary and the matches the amount calculated in Product details
		String couponDiscount = driver.findElementByXPath("//section[@id='couponDiscount']/span[2]").getText().replaceAll("Rs. ", "");
		double couponSavings = Double.parseDouble(couponDiscount);
		int couponSavingsAmount = (int) Math.round(couponSavings);
		System.out.println(couponSavingsAmount);
		if (couponSavingsAmount==discountAmount) {
			System.out.println("coupon savings amount is verified");
		} else {
			System.out.println("coupon savings amount is not verified");
		}
		
//		13) Click on Delete and Delete the item from Bag
		driver.findElementByXPath("//div[@class='delete-btn']").click();
		driver.findElementByXPath("//div[text()='DELETE']").click();
		
//		14) Close all the browsers
		driver.quit();
		
	}

}
