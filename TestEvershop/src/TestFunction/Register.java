package TestFunction;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Register {
	String URL_register = "http://localhost:3000/account/register";
	String URL_dashBoard = "http://localhost:3000/";
	String registerWrongMess = "Email is already used";
	String registerBlankMess = "This field can not be empty";
	int numEmail = 8;
	
	WebDriver driver;
	
	public void register(String fullname, String email, String password) {	
		driver.findElement(By.name("full_name")).sendKeys(fullname);
		driver.findElement(By.name("email")).sendKeys(email);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button.button")).click();
	}
	
	public String emailAcc(int num) {	
		String newEmail = "minh_test_"+ Integer.toString(num) + "@gmail.com";
		return newEmail;
	}
	
	@BeforeMethod
	public void setUp(){
		driver = new ChromeDriver();
		driver.get(URL_register);
	}
	
	@Test
	public void registerByGuest() {
		String newEmail = emailAcc(numEmail);
		register("minh test", newEmail, "Bb@123456");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.or(ExpectedConditions.urlToBe(URL_dashBoard)));
		
		Assert.assertEquals(driver.getCurrentUrl(), URL_dashBoard);
	}
	
	@Test
	public void registerByExistedUser() {
		String oldEmail = "minh_test_1@gmail.com";
		register("minh test", oldEmail, "Bb@123456");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));	
		WebElement registerDuplicateEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-critical.mb-1")));
		
		String registerWrong = registerDuplicateEmail.getText().trim();
		
		Assert.assertEquals(registerWrong, registerWrongMess);
	}
	
	@Test
	public void registerWithBlankField() {
		register("", "", "");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'not be empty')]")));
		
		List<WebElement> errorMessages = driver.findElements(By.xpath("//*[contains(text(), 'not be empty')]"));
		
		Assert.assertEquals(errorMessages.size(), 3);
		
		for (WebElement errorMessage : errorMessages) {
            Assert.assertEquals(errorMessage.getText(), registerBlankMess);
        }
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
