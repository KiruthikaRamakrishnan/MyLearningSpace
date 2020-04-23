package phase1;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC007_Honda {

	public static void main(String[] args) throws InterruptedException {
		
//		1) Go to https://www.honda2wheelersindia.com/
		System.setProperty("webdriver.chrome.driver", "./drivers/Chromedriver/chromedriver.exe");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notification");
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://www.honda2wheelersindia.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
//		2) Click on scooters and click dio
		driver.findElementByXPath("//button[text()='×']").click();
		driver.findElementById("link_Scooter").click();
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElementByXPath("//a[contains(@href,'dio-BS-VI')]/..")));
		driver.findElementByXPath("//a[contains(@href,'dio-BS-VI')]/..").click();
		
//		3) Click on Specifications and mouseover on ENGINE
		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElementByXPath("//a[text()='Specifications']")));
		driver.findElementByXPath("//a[text()='Specifications']").click();
		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElementByXPath("//a[text()='ENGINE']")));
		WebElement dioEngine = driver.findElementByXPath("//a[text()='ENGINE']");
		Actions builder = new Actions(driver);
		builder.moveToElement(dioEngine).perform();
		Thread.sleep(3000);
		
//		4) Get Displacement value
		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElementByXPath("//span[text()='Displacement']/following::span")));
		String strDioDisp = driver.findElementByXPath("//span[text()='Displacement']/following::span").getText().replaceAll("[a-z]", "");
		float dioDisplacementValue = Float.parseFloat(strDioDisp);
		System.out.println("Displacement value of dio: "+dioDisplacementValue);
		Thread.sleep(3000);
		
//		5) Go to Scooters and click Activa 125
		driver.findElementById("link_Scooter").click();
		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElementByXPath(" //a[contains(@href,'activa125-BS-VI')]/..")));
		driver.findElementByXPath(" //a[contains(@href,'activa125-BS-VI')]/..").click();
		
//		6) Click on Specifications and mouseover on ENGINE
		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElementByXPath("//a[text()='Specifications']")));
		driver.findElementByXPath("//a[text()='Specifications']").click();
		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElementByXPath("//a[text()='ENGINE']")));
		WebElement activaEngine = driver.findElementByXPath("//a[text()='ENGINE']");
		builder.moveToElement(activaEngine).perform();
		Thread.sleep(3000);
		
//		7) Get Displacement value
		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElementByXPath("//span[text()='Displacement']/following::span")));
		String strActivaDisp = driver.findElementByXPath("//span[text()='Displacement']/following::span").getText().replaceAll("[a-z]", "");
		float activaDisplacementValue = Float.parseFloat(strActivaDisp);
		System.out.println("Displacement value of Activa 125: "+activaDisplacementValue);
		
//		8) Compare Displacement of Dio and Activa 125 and print the Scooter name having better Displacement.
		if (dioDisplacementValue>activaDisplacementValue) {
			System.out.println("Dio is having better Displacement: "+dioDisplacementValue);
		} else {
			System.out.println("Activa 125 is having better Displacement: "+activaDisplacementValue);
		}
		
//		9) Click FAQ from Menu
		driver.findElementByXPath("//a[text()='FAQ']").click();
		
//		10) Click Activa 125 BS-VI under Browse By Product
		driver.findElementByXPath("//a[text()='Activa 125 BS-VI']").click();
		
//		11) Click  Vehicle Price
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementById("li6")));
		driver.findElementById("li6").click();
		Thread.sleep(3000);
		
//		12) Make sure Activa 125 BS-VI selected and click submit
		String modelName = driver.findElementByXPath("//select[@id='ModelID6']/option[@selected='selected']").getText();
		if (modelName.contains("Activa 125 BS-VI")) {
			System.out.println("Activa 125 BS-VI is selected correctly");
			driver.findElementById("submit6").click();
		} else {
			System.out.println("Activa 125 BS-VI is not selected");
		}
		
//		13) click the price link
		driver.findElementByXPath("//table[@id='tblPriceMasterFilters']//td//a").click();
		
//		14)  Go to the new Window and select the state as Tamil Nadu and  city as Chennai
		Set<String> id = driver.getWindowHandles();
		List<String> windowId = new ArrayList<>(id);
		driver.switchTo().window(windowId.get(1));
		WebElement stateId = driver.findElementById("StateID");
		Select stateIdDropDown = new Select(stateId);
		stateIdDropDown.selectByVisibleText("Tamil Nadu");
		WebElement cityId = driver.findElementById("CityID");
		Select cityIdDropDown = new Select(cityId);
		cityIdDropDown.selectByVisibleText("Chennai");
		
//		15) Click Search
		driver.findElementByXPath("//button[text()='Search']").click();
		
//		16) Print all the 3 models and their prices
		List<WebElement> list = driver.findElementsByXPath("//table[@id='gvshow']/tbody/tr");
		int size = list.size();
		List<WebElement> model = driver.findElementsByXPath("//table[@id='gvshow']//tbody//tr//td[contains(text(),'ACTIVA')]");
		List<WebElement> price = driver.findElementsByXPath("//table[@id='gvshow']//tbody//tr//td[contains(text(),'Rs')]");
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < size; i++) {
			String nameOfModel = model.get(i).getText();
			String priceOfModel = price.get(i).getText();
			map.put(nameOfModel , priceOfModel);
		}
		System.out.println("all the 3 models and their respective prices");
		for (Entry<String, String> eachEntry : map.entrySet() ) {
			System.out.println(eachEntry.getKey()+"-->"+eachEntry.getValue());
		}
//		17) Close the Browser
		driver.quit();

	}

}
