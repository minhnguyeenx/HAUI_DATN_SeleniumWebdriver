package TestFunction;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.Keys;
import java.util.List;

public class Search {
	String URL_register = "http://localhost:3000/account/register";
	String URL_dashBoard = "http://localhost:3000/";
	
	WebDriver driver;
	
	@BeforeMethod
	public void setUp(){
		driver = new ChromeDriver();
		driver.get(URL_dashBoard);
	}
	
	@Test
	public void searchWithCorrectName() {
		//Click icon search
		WebElement searchIconLink = driver.findElement(By.cssSelector("a.search-icon"));
		searchIconLink.click();
		
		//Enter name
		WebElement searchInput = driver.findElement(By.cssSelector("input[type='text']"));
		searchInput.sendKeys("Striped Cotton Sweater");
		
		//Kick Enter
		searchInput.sendKeys(Keys.ENTER);
		
		//Check list product
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement searchResultsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(@class, 'search-name') and contains(text(), 'Search results for')]")));
		List<WebElement> productList = driver.findElements(By.cssSelector("div.grid-cols-2.md\\:grid-cols-4.gap-2 > div.listing-tem"));
		
		Assert.assertTrue(searchResultsElement.isDisplayed());
		Assert.assertEquals(searchResultsElement.getText(), "Search results for \"Striped Cotton Sweater\"");
		Assert.assertTrue(!productList.isEmpty());
	}
	
	@Test
	public void searchWithIncorrectName() {
		//Click icon search
		WebElement searchIconLink = driver.findElement(By.cssSelector("a.search-icon"));
		searchIconLink.click();
		
		//Enter name
		WebElement searchInput = driver.findElement(By.cssSelector("input[type='text']"));
		searchInput.sendKeys("Striped Cotton Sweater2");
		
		//Kick Enter
		searchInput.sendKeys(Keys.ENTER);
		
		WebElement noProductElement = driver.findElement(By.xpath("//div[@class='product-list']//div[contains(text(), 'There is no product to display')]"));
		Assert.assertTrue(noProductElement.isDisplayed());
	}
	
	@Test
	public void searchWithNoName() {
		//Click icon search
		WebElement searchIconLink = driver.findElement(By.cssSelector("a.search-icon"));
		searchIconLink.click();
		
		//Enter name
		WebElement searchInput = driver.findElement(By.cssSelector("input[type='text']"));
		searchInput.sendKeys("");
		
		//Kick Enter
		searchInput.sendKeys(Keys.ENTER);
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.or(ExpectedConditions.urlToBe(URL_dashBoard)));
		
		Assert.assertEquals(driver.getCurrentUrl(), URL_dashBoard);
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
