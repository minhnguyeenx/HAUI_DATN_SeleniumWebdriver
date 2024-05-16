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
import org.testng.annotations.Test;
import org.openqa.selenium.TimeoutException;

public class ViewProductDetail {
	String URL_login = "http://localhost:3000/account/login";
	String URL_dashBoard = "http://localhost:3000/";
	
	WebDriver driver;
	
	public void login(String email, String password) {	
		driver.findElement(By.name("email")).sendKeys(email);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button.button")).click();
	}
	
	@Test
	public void viewProductDetailNotLoggedIn() {
		//Notlogin-> Homepage
		driver = new ChromeDriver();
		driver.get(URL_dashBoard);
		
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.product-name a")));

            String productNameExpect = driver.findElement(By.cssSelector("div.product-name a span")).getText().trim();
            String productPriceExpect = driver.findElement(By.cssSelector("div.product-price-listing span.sale-price")).getText().trim();
            productLink.click();

            String productNameActual = driver.findElement(By.cssSelector("h1.product-single-name")).getText().trim();
            String productPriceActual = driver.findElement(By.cssSelector("h4.product-single-price span.sale-price")).getText().trim();

            Assert.assertEquals(productNameActual, productNameExpect);
            Assert.assertEquals(productPriceActual, productPriceExpect);
            
        } catch (TimeoutException e) {
            // Handle the case when the product is not found
            System.out.println("Product not found within 5 seconds.");
        }
	}
	
	@Test
	public void viewProductDetailLoggedIn() {
		//login
		driver = new ChromeDriver();
		driver.get(URL_login);
		login("minh_test_1@gmail.com", "Bb@123456");
		
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.product-name a")));

            String productNameExpect = driver.findElement(By.cssSelector("div.product-name a span")).getText().trim();
            String productPriceExpect = driver.findElement(By.cssSelector("div.product-price-listing span.sale-price")).getText().trim();
            productLink.click();

            String productNameActual = driver.findElement(By.cssSelector("h1.product-single-name")).getText().trim();
            String productPriceActual = driver.findElement(By.cssSelector("h4.product-single-price span.sale-price")).getText().trim();

            Assert.assertEquals(productNameActual, productNameExpect);
            Assert.assertEquals(productPriceActual, productPriceExpect);
            
        } catch (TimeoutException e) {
            // Handle the case when the product is not found
            System.out.println("Product not found within 5 seconds.");
        }
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
