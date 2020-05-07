package phase1;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC017_AzureMicrosoft {

	public static void main(String[] args) throws InterruptedException {
		
//		1) Go to https://azure.microsoft.com/en-in/
		System.setProperty("webdriver.chrome.driver", "./drivers/Chromedriver/chromedriver.exe");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://azure.microsoft.com/en-in/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
//		2) Click on Pricing
		driver.findElementByXPath("//a[@id='navigation-pricing']").click();
		Thread.sleep(3000);
		
//		3) Click on Pricing Calculator
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//a[contains(text(),'Pricing calculator')]")));
		driver.findElementByXPath("//a[contains(text(),'Pricing calculator')]").click();
		Thread.sleep(3000);
		
//		4) Click on Containers
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//button[@value='containers']")));
		driver.findElementByXPath("//button[@value='containers']").click();
		
//		5) Select Container Instances
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("(//button[@title='Container Instances'])[2]")));
		driver.findElementByXPath("(//button[@title='Container Instances'])[2]").click();
		
//		6) Click on Container Instance Added View
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//a[text()='View']")));
		driver.findElementByXPath("//a[text()='View']").click();
		
//		7) Select Region as "South India"
		WebElement region = driver.findElementByXPath("//select[@aria-label='Region']");
		Select regionDropDown = new Select(region);
		regionDropDown.selectByValue("south-india");
		
//		8) Set the Duration as 180000 seconds
		driver.findElementByXPath("//input[@name='seconds']").sendKeys(Keys.BACK_SPACE);
		driver.findElementByXPath("//input[@name='seconds']").sendKeys("80000");
		
//		9) Select the Memory as 4GB
		WebElement memory = driver.findElementByXPath("//select[@name='memory']");
		Select memoryDropDown = new Select(memory);
		memoryDropDown.selectByVisibleText("4 GB");
		
//		10) Enable SHOW DEV/TEST PRICING
		driver.findElementByXPath("//span[@class='toggler-label']").click();
		Thread.sleep(3000);
		
//		11) Select Indian Rupee  as currency
		WebElement currency = driver.findElementByXPath("//select[@class='select currency-dropdown']");
		Select currencyDropDown = new Select(currency);
		currencyDropDown.selectByValue("INR");
		
//		12) Print the Estimated monthly price
		String estimatedMonthlyPrice = driver.findElementByXPath("(//div[@class='row row-size1 column estimate-total']//div[contains(@class,'total')]//span[@class='numeric'])[2]/span").getText().replaceAll("₹", "");
		System.out.println("Estimated monthly price: "+estimatedMonthlyPrice);
		
//		13) Click on Export to download the estimate as excel
		driver.findElementByXPath("//button[@class='calculator-button button-transparent export-button']").click();
		Thread.sleep(3000);
		
//		14) Verify the downloded file in the local folder
		File downlodedFile = new File("C:\\Users\\Kishoore\\Downloads\\ExportedEstimate.xlsx"); 
        if (downlodedFile.exists()) {
        	System.out.println("the downloded ExportedEstimate file exists in the local folder");
		} else {
			System.out.println("the downloded ExportedEstimate file doesn't exists in the local folder");
		}

//		15) Navigate to Example Scenarios and Select CI/CD for Containers
        JavascriptExecutor js = (JavascriptExecutor)driver;
        WebElement exampleScenarios = driver.findElementByXPath("//a[text()='Example Scenarios']");
        js.executeScript("arguments[0].click();", exampleScenarios);
        WebElement containers = driver.findElementByXPath("//span[text()='CI/CD for Containers']");
        js.executeScript("arguments[0].click();", containers);
        
//		16) Click Add to Estimate
		WebElement addToEstimate = driver.findElementByXPath("//button[text()='Add to estimate']");
		js.executeScript("arguments[0].click();", addToEstimate);
        Thread.sleep(3000);
		
//		17) Change the Currency as Indian Rupee
		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElementByXPath("//select[@class='select currency-dropdown']")));
		WebElement currencyForEstimate = driver.findElementByXPath("//select[@class='select currency-dropdown']");
		Select currencyForEstimateDropDown = new Select(currencyForEstimate);
		currencyForEstimateDropDown.selectByValue("INR");
		
//		18) Enable SHOW DEV/TEST PRICING
		driver.findElementByXPath("//span[@class='toggler-label']").click();
		Thread.sleep(3000);
		
//		19) Export the Estimate
		driver.findElementByXPath("//button[@class='calculator-button button-transparent export-button']").click();
		Thread.sleep(3000);
		
//		20) Verify the downloded file in the local folder
		File downlodedEstimateFile = new File("C:\\Users\\Kishoore\\Downloads\\ExportedEstimate (1).xlsx"); 
        if (downlodedEstimateFile.exists()) {
        	System.out.println("the downloded ExportedEstimate (1) file exists in the local folder");
		} else {
			System.out.println("the downloded ExportedEstimate (1) file doesn't exists in the local folder");
		}
		
        driver.close();
	}

}
