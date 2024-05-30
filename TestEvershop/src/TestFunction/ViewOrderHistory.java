package TestFunction;

import java.time.Duration;
import java.util.List;

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

public class ViewOrderHistory {
	String URL_login = "http://localhost:3000/account/login";
	String URL_dashBoard = "http://localhost:3000/";
	String loginWrongMess = "Invalid email or password";
	String loginBlankMess = "This field can not be empty";
	
	WebDriver driver;
	
	public void login(String email, String password) {	
		driver.findElement(By.name("email")).sendKeys(email);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button.button")).click();
	}
	
	@BeforeMethod
	public void setUp(){
		driver = new ChromeDriver();
		driver.get(URL_login);
	}
	
	@Test(priority=1, enabled=true)
	public void viewOrderHistoryHasNoProduct() {
		login("minh_test_10@gmail.com", "Bb@123456");
		
		//Click view order list
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement iconLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/account']")));
		iconLink.click();
		
		// Get text "Order History"
	    WebElement orderHistoryHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Order History']")));
	    Assert.assertTrue(orderHistoryHeading.isDisplayed());

	    // Get text "You have not placed any orders yet"
	    WebElement emptyOrderHistoryMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.order-history-empty")));
	    Assert.assertTrue(emptyOrderHistoryMessage.isDisplayed());
	    Assert.assertTrue(emptyOrderHistoryMessage.isDisplayed());
	}
	
	@Test(priority=2, enabled=true)
	public void viewOrderHistoryHasProduct() {
		login("minh_test_4@gmail.com", "Bb@123456");
		
		//Click view order list
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement iconLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/account']")));
		iconLink.click();
		
		// Get text "Order History"
	    WebElement orderHistoryHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Order History']")));
	    Assert.assertTrue(orderHistoryHeading.isDisplayed());

	    // Get element has class "order-number"
	    List<WebElement> orderNumbers = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".order-number")));
	    Assert.assertFalse(orderNumbers.isEmpty());
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
