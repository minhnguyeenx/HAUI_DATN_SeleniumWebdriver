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

public class Login {
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
	public void loginByUser() {
		login("minh_test_1@gmail.com", "Bb@123456");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.or(ExpectedConditions.urlToBe(URL_dashBoard)));
		
		Assert.assertEquals(driver.getCurrentUrl(), URL_dashBoard);
	}
	
	@Test(priority=2, enabled=true)
	public void loginWrongEmail() {
		login("minh_test_111@gmail.com", "Bb@123456");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement loginWrongElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-critical.mb-1")));
		String loginWrong = loginWrongElement.getText().trim();
		
		Assert.assertEquals(loginWrong, loginWrongMess);
	}
	
	@Test(priority=3, enabled=true)
	public void loginWrongPassword() {
		login("minh_test_1@gmail.com", "Bb@1234567");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement loginWrongElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-critical.mb-1")));
		String loginWrong = loginWrongElement.getText().trim();
		
		Assert.assertEquals(loginWrong, loginWrongMess);
	}

	@Test(priority=4, enabled=true)
	public void loginWithBlankField() {
		login("", "");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'not be empty')]")));
		
		List<WebElement> errorMessages = driver.findElements(By.xpath("//*[contains(text(), 'not be empty')]"));
		
		Assert.assertEquals(errorMessages.size(), errorMessages.size());
		
		for (WebElement errorMessage : errorMessages) {
            Assert.assertEquals(errorMessage.getText(), loginBlankMess);
        }
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
