package TestFunction;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;

public class ViewProductByCategory {
	String URL_register = "http://localhost:3000/account/register";
	String URL_dashBoard = "http://localhost:3000/";
	
	WebDriver driver;
	
	@BeforeMethod
	public void setUp(){
		driver = new ChromeDriver();
		driver.get(URL_dashBoard);
	}
	
	@Test
	public void categoryHasProduct() {
		//Click Men
		WebElement menLink = driver.findElement(By.xpath("//a[text()='Men']"));
		menLink.click();
		
		//Check display MEN
		 WebElement categoryNameElement = driver.findElement(By.className("category__name"));
		String menText = categoryNameElement.getText();
		Assert.assertEquals(menText, "MEN");
		
		List<WebElement> productList = driver.findElements(By.cssSelector("div.grid-cols-2.md\\:grid-cols-3.gap-2 > div.listing-tem"));
		Assert.assertTrue(!productList.isEmpty());
	}
	
	@Test
	public void categoryHasNoProduct() {
		//Click Kids
		WebElement kidsLink = driver.findElement(By.xpath("//a[text()='Kids']"));
		kidsLink.click();
		
		//Check
		WebElement messageElement = driver.findElement(By.cssSelector("div.product-list > div.text-center"));
		String messageText = messageElement.getText();
		Assert.assertEquals(messageText, "There is no product to display");
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
