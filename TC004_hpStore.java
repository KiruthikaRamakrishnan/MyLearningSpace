package phase1;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class TC004_hpStore {

	public static void main(String[] args) throws InterruptedException {
		
//		1) Go to https://store.hp.com/in-en/
		System.setProperty("webdriver.chrome.driver", "./drivers/Chromedriver/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notification");
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://store.hp.com/in-en/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
//		2) Mouse over on Laptops menu and click on Pavilion		
			try {
				driver.findElementByXPath("//span[@class='optly-modal-close close-icon']").click();
			} catch (NoSuchElementException e) {
				
			}
		WebElement laptops = driver.findElementByXPath("//span[text()='Laptops']");
		WebElement pavilion = driver.findElementByXPath("//span[text()='Pavilion']");
		Actions builder = new Actions(driver);
		builder.moveToElement(laptops).click(pavilion).perform();
		try {
			driver.findElementByXPath("//div[@class='inside_closeButton fonticon icon-hclose']").click();
		} catch (NoSuchElementException e) {
			
		}
				
//		3) Under SHOPPING OPTIONS -->Processor -->Select Intel Core i7
		driver.executeScript("window.scrollBy(0, 250)");
		driver.findElementByXPath("(//span[text()='Processor'])[2]").click();
		driver.findElementByXPath("//span[text()='Intel Core i7']").click();
		Thread.sleep(3000);
		
//		4) Hard Drive Capacity -->More than 1TB
		driver.executeScript("window.scrollBy(0, 550)");
		driver.findElementByXPath("//span[text()='More than 1 TB']").click();
		Thread.sleep(3000);
		
//		5) Select Sort By: Price: Low to High
		WebElement sorter = driver.findElementByXPath("//select[@id='sorter']");
		Select sorterDropDown = new Select(sorter);
		sorterDropDown.selectByVisibleText("Price : Low to High");
		
//		6) Print the First resulting Product Name and Price
		String productName = driver.findElementByXPath("(//a[@class='product-item-link'])[1]").getText();
		String price = driver.findElementByXPath("(//span[@class='price'])[2]").getText().replaceAll("[^0-9]", "");
		System.out.println("the first resulting product name: "+productName);
		System.out.println(" it's price: "+price);
		
//		7) Click on Add to Cart
		Thread.sleep(3000);
		driver.executeScript("window.scrollBy(0, 750)");
		driver.findElementByXPath("(//button[@title='Add To Cart'])[1]").click();
		
//		8) Click on Shopping Cart icon --> Click on View and Edit Cart
		Thread.sleep(3000);
		driver.findElementByXPath("//a[@class='action showcart']").click();
		driver.findElementByXPath("//span[text()='View and edit cart']").click();
		
//		9) Check the Shipping Option --> Check availability at Pincode
		driver.findElementByName("pincode").sendKeys("620014",Keys.ENTER);
		boolean availability = driver.findElementByXPath("//span[@class='available']").isDisplayed();
		if (availability) {
			String estimate = driver.findElementByXPath("//div[@class='estimate']").getText();
			String shipping = driver.findElementByXPath("//span[@class='available']//following-sibling::span").getText();
			System.out.println(estimate+" "+shipping);
		} else {
			System.out.println("laptop is not available");
		}
		
//		10) Verify the order Total against the product price
		String orderTotal = driver.findElementByXPath("(//td[@class='amount'])[3]").getText().replaceAll("[^0-9]", "");
		System.out.println("order total: "+orderTotal);
		
//		11) Proceed to Checkout if Order Total and Product Price matches
		if (orderTotal.equalsIgnoreCase(price)) {
			System.out.println("Order Total and Product Price matches");
			driver.findElementByXPath("(//span[text()='Proceed to Checkout'])[1]").click();
		} else {
			System.out.println("Order Total and Product Price dosen't match");
		}
		
//		12) Click on Place Order
		Thread.sleep(3000);
		driver.executeScript("window.scrollBy(0, 350)");
		driver.findElementByXPath("//div[@class='place-order-primary']//button").click();
		
//		13) Capture the Error message and Print
		String errorMessage = driver.findElementByXPath("//div[@class='message notice']").getText();
		System.err.println("error message: "+errorMessage);
		
//		14) Close Browser
		driver.close();
		
	}

}
