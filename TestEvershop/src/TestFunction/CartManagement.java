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
	}
	
	@Test(priority=1, enabled=false)
	public void viewCartHasNoProduct() {
		login("minh_test_2@gmail.com", "123456");
		driver.get(URL_dashBoard);
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement iconLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.mini-cart-icon")));
		iconLink.click();
		
		WebElement emptyCartMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='mt-2 text-center']/span")));
		 
		 Assert.assertTrue(emptyCartMessage.isDisplayed());
	     Assert.assertEquals(emptyCartMessage.getText().trim(), "Your cart is empty!");
	}
	
	@Test(priority=2, enabled=true)
	public void addNewProductToCart() {
		login("minh_test_3@gmail.com", "Bb@123456");
		driver.get(URL_dashBoard);
		
//		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//         WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.product-name a")));
//
//         String productNameExpect = driver.findElement(By.cssSelector("div.product-name a span")).getText().trim();
//         productLink.click();
//         
//         WebElement addToCartButton = driver.findElement(By.className("button"));
//         addToCartButton.click();
		
		String productName = "Denim Skinny Jeans";
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));    
	    WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='product-name product-list-name mt-1 mb-025']/a/span[text()='" + productName + "']")));
	    productLink.click();
	    
	    WebElement addToCartButton = driver.findElement(By.className("button"));
	    addToCartButton.click();
		 
		 WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(5));
		 WebElement toastMiniCart = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-mini-cart")));
		 
		 WebElement messageElement = toastMiniCart.findElement(By.xpath(".//div[contains(@class, 'top-head')]"));
	     String message = messageElement.getText();
	     Assert.assertEquals(message, "JUST ADDED TO YOUR CART");

	     WebElement productNameElement = toastMiniCart.findElement(By.xpath(".//div[contains(@class, 'name')]//span[@class='font-bold']"));
	     String productNameInCart = productNameElement.getText();
	     Assert.assertEquals(productNameInCart, productName);
	     
	     driver.get(URL_dashBoard);
	}	
	
	@Test(priority=3, enabled=false)
	public void viewCartHasProduct() {
		login("minh_test_4@gmail.com", "Bb@123456");
		driver.get(URL_dashBoard);
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement iconLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.mini-cart-icon")));
		
		//test display quantity of cart
		WebElement cartIcon = driver.findElement(By.cssSelector("a.mini-cart-icon"));
		WebElement spanElement = cartIcon.findElement(By.tagName("span"));
		int spanText = Integer.parseInt(spanElement.getText());
		Assert.assertTrue(spanText >= 1);
		
		iconLink.click();
		
	    WebElement productNameElement = driver.findElement(By.cssSelector("a.name"));
//	    String productName = productNameElement.getText();
//	    Assert.assertEquals(productName, "Striped Cotton Sweater");
	    
	    Assert.assertTrue(productNameElement.isDisplayed());
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
