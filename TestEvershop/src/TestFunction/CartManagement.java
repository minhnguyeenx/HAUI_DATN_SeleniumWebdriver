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

public class CartManagement {
	String URL_login = "http://localhost:3000/account/login";
	String URL_dashBoard = "http://localhost:3000/";
	
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
		login("minh_test_1@gmail.com", "Bb@123456");
		driver.get(URL_dashBoard);
	}
	
	@Test(priority=1)
	public void viewCartHasNoProduct() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement iconLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.mini-cart-icon")));
		iconLink.click();
		
		WebElement emptyCartMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='mt-2 text-center']/span")));
		 
		 Assert.assertTrue(emptyCartMessage.isDisplayed());
	     Assert.assertEquals(emptyCartMessage.getText().trim(), "Your cart is empty!");
	}
	
	@Test(priority=2)
	public void addNewProductToCart() {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
         WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.product-name a")));

         String productNameExpect = driver.findElement(By.cssSelector("div.product-name a span")).getText().trim();
         productLink.click();
         
         WebElement addToCartButton = driver.findElement(By.className("button"));
         addToCartButton.click();
		 
		 WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(5));
		 WebElement toastMiniCart = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-mini-cart")));
		 
		 WebElement messageElement = toastMiniCart.findElement(By.xpath(".//div[contains(@class, 'top-head')]"));
	     String message = messageElement.getText();
	     Assert.assertEquals(message, "JUST ADDED TO YOUR CART");

	     WebElement productNameElement = toastMiniCart.findElement(By.xpath(".//div[contains(@class, 'name')]//span[@class='font-bold']"));
	     String productName = productNameElement.getText();
	     Assert.assertEquals(productName, productNameExpect);
	     
	     driver.get(URL_dashBoard);
	}	
	
	@Test(priority=3)
	public void viewCartHasProduct() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement iconLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.mini-cart-icon")));
		
		//test display quantity of cart
		WebElement cartIcon = driver.findElement(By.cssSelector("a.mini-cart-icon"));
		WebElement spanElement = cartIcon.findElement(By.tagName("span"));
		String spanText = spanElement.getText();
		Assert.assertEquals(spanText, "1");
		
		iconLink.click();
		
	    WebElement productNameElement = driver.findElement(By.cssSelector("a.name"));
	    String productName = productNameElement.getText();
	    Assert.assertEquals(productName, "Striped Cotton Sweater");
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
