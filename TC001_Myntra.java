package phase1;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class TC001_Myntra {

	public static void main(String[] args) throws InterruptedException {
		
//		1) Open https://www.myntra.com/
		System.setProperty("webdriver.chrome.driver", "./drivers/Chromedriver/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://www.myntra.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//		2) Mouse over on WOMEN
		WebElement women = driver.findElementByXPath("//a[text()='Women']");
		Actions builder = new Actions(driver);
		builder.moveToElement(women).perform();
		
//		3) Click Jackets & Coats
		driver.findElementByLinkText("Jackets & Coats").click();
		
//		4) Find the total count of item (top)
		String strItemCount = driver.findElementByXPath("//div[@class='title-container']").getText().replaceAll("[^0-9]", "");
		int totalItemsCount = Integer.parseInt(strItemCount);
		System.out.println("Total coats and jackets for women: "+totalItemsCount);
		
//		5) Validate the sum of categories count matches
		String jackets = driver.findElementByXPath("//label[text()='Jackets']").getText().replaceAll("[^0-9]", "");
		int jacketsCount = Integer.parseInt(jackets);
		System.out.println("Total jackets for women: "+jacketsCount);
		String coats = driver.findElementByXPath("//label[text()='Coats']").getText().replaceAll("[^0-9]", "");
		int coatsCount = Integer.parseInt(coats);
		System.out.println("Total coats for women: "+coatsCount);
		if (totalItemsCount==jacketsCount+coatsCount) {
			System.out.println("the sum of categories count matches");
		} else {
			System.out.println("the sum of categories count dosen't match");
		}
		
//		6) Check Coats
		driver.findElementByXPath("//label[text()='Coats']").click();
		
//		7) Click + More option under BRAND
		driver.findElementByXPath("//div[@class='brand-more']").click();
		
//		8) Type MANGO and click checkbox
		driver.findElementByXPath("//input[@placeholder='Search brand']").sendKeys("MANGO");
		driver.findElementByXPath("//label[@class=' common-customCheckbox']//div").click();
		
//		9) Close the pop-up x
		driver.findElementByXPath("//span[contains(@class,'myntraweb-sprite FilterDirectory-close')]").click();
		Thread.sleep(3000);
		
//		10) Confirm all the Coats are of brand MANGO
		int count=0;
		List<WebElement> listOfBrandName = driver.findElementsByXPath("//div[@class='product-productMetaInfo']//h3[1]");
		for (WebElement brand : listOfBrandName) {
			String brandName = brand.getText();
			if (brandName.equalsIgnoreCase("mango")) {
				count=count+1;
			} 
		}
		if (count==listOfBrandName.size()) {
			System.out.println("it is confirm all the coats are of brand MANGO");
		} else {
			System.out.println("it is confirm that not all the coats are of brand MANGO");
		}
		
//		11) Sort by Better Discount
		WebElement sortBy = driver.findElementByXPath("//div[@class='sort-sortBy']");
		builder.moveToElement(sortBy).perform();
		driver.findElementByXPath("//label[text()='Better Discount']").click();
		Thread.sleep(3000);
		
//		12) Find the price of first displayed item
		String priceOfFirstDisplayedItem = driver.findElementByXPath("(//span[@class='product-discountedPrice'])[1]").getText().replaceAll("[^0-9]", "");
		System.out.println("price of the first displayed item is: "+priceOfFirstDisplayedItem);
		
//		13) Mouse over on size of the first item
		WebElement firstItem = driver.findElementByXPath("(//li[@class='product-base'])[1]");
		WebElement sizeOfFirstItem = driver.findElementByXPath("(//h4[@class='product-sizes'])[1]//span");
		builder.moveToElement(firstItem).moveToElement(sizeOfFirstItem).perform();
		
//		14) Click on WishList Now
		driver.findElementByXPath("(//span[text()='wishlist now'])[1]").click();

//		15) Close Browser
		driver.close();
	}

}
