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

public class ViewAccountDetails {
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
	}
	
	@Test
	public void viewAccountDetailCheck() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement iconLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/account']")));
		iconLink.click();
		
		//find name
        WebElement parentNameDiv = driver.findElement(By.cssSelector("div.account-details-name"));
        String name = parentNameDiv.findElement(By.xpath("./div[last()]")).getText().trim();
        
        //find email
        WebElement parentEmailDiv = driver.findElement(By.cssSelector("div.account-details-email"));
        String email = parentEmailDiv.findElement(By.xpath("./div[last()]")).getText().trim();
        
        Assert.assertEquals(name, "minh nguyen");
        Assert.assertEquals(email, "minh_test_1@gmail.com");
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
