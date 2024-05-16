package TestFunction;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Purchase {
	String URL_login = "http://localhost:3000/account/login";
	String URL_dashBoard = "http://localhost:3000/";
	String URL_cart = "http://localhost:3000/cart";
	String URL_checkout = "http://localhost:3000/checkout";
	
	//Input
	String fullName, telephone, address, city, country, province, postcode, shippingMethod;
	
	WebDriver driver;
	
	public void login(String email, String password) {	
		driver.findElement(By.name("email")).sendKeys(email);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button.button")).click();
	}
	
	public void inputAdress(String fullName, String telephone, String address, String city, String country, String province, String postcode,String shippingMethod) {
		 WebElement shippingAddressElement = driver.findElement(By.xpath("//h4[contains(text(), 'Shipping Address')]"));
		 Assert.assertTrue(shippingAddressElement.isDisplayed());
		 
		 WebElement inputFullName = driver.findElement(By.name("address[full_name]"));
		 inputFullName.sendKeys(fullName); 
		 
		 WebElement inputTelephone = driver.findElement(By.name("address[telephone]"));
		 inputTelephone.sendKeys(telephone); 
		 
		 WebElement inputAddress = driver.findElement(By.name("address[address_1]"));
		 inputAddress.sendKeys(address); 
		 
		 WebElement inputCity = driver.findElement(By.name("address[city]"));
		 inputCity.sendKeys(city); 
		 
		 WebElement countryDropdown = driver.findElement(By.id("address[country]"));
		 Select select = new Select(countryDropdown);
		 select.selectByVisibleText(country);
		 
		 WebElement inputProvince = driver.findElement(By.id("address[province]"));
		 Select select2 = new Select(inputProvince);
		 select2.selectByVisibleText(province);
		 
		 WebElement inputPostcode = driver.findElement(By.name("address[postcode]"));
		 inputPostcode.sendKeys(postcode);
		 
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		 WebElement shippingMethodElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'GH Delivery - $9.00')]")));
		 shippingMethodElement.click();
		 
		 WebElement continuePaymentButton = driver.findElement(By.className("button"));
		 continuePaymentButton.click();
		 driver.get(URL_checkout);
		 driver.get(URL_checkout);
	}
	
	public void addNewProductToCart() {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.product-name a")));
        productLink.click();
        
        WebElement addToCartButton = driver.findElement(By.className("button"));
        addToCartButton.click();
	     
	     driver.get(URL_dashBoard);
	     driver.get(URL_dashBoard);
	}	
	
	@BeforeMethod
	public void setUp(){
		driver = new ChromeDriver();
		driver.get(URL_login);
//		login("minh_test_4@gmail.com", "Bb@123456");
//		driver.get(URL_dashBoard);
	}
	
	@Test(enabled=false)
	public void paymentByCash() {
		login("minh_test_4@gmail.com", "Bb@123456");
		driver.get(URL_dashBoard);
		
	    addNewProductToCart();
	    
	    // View cart
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement iconLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.mini-cart-icon")));
	    iconLink.click();
	    
	    // Click button checkout
	    WebElement buttonCheckOut = driver.findElement(By.cssSelector("a[href='/checkout'].button.primary"));
	    buttonCheckOut.click();
	    
	    // Input shipping address
	    try {
			//input shipping address
			fullName = "minh nguyen";
			telephone = "0123456789";
			address = "Hoai Duc";
			city = "Ha Noi";
			country = "Vietnam";
			province = "Ha Noi";
			postcode = "123456";
			shippingMethod = "";
			
			inputAdress(fullName, telephone, address, city, country, province, postcode, shippingMethod);
			 
			 //payment by cash
			 WebElement paymentMethodTitle = driver.findElement(By.xpath("//h4[@class='mb-1 mt-3' and contains(text(), 'Payment Method')]"));
			 Assert.assertTrue(paymentMethodTitle.isDisplayed());
			 
			 WebElement paymentByCash = driver.findElement(By.cssSelector("div.flex.justify-start.items-center.gap-1 > a"));
			 paymentByCash.click();
			 
			 WebElement placeOrderButton = driver.findElement(By.className("button"));
			 placeOrderButton.click();
			 
			 //Check to order successfully screen
			 WebElement thankYouElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3.thank-you")));
			 String actualText = thankYouElement.getText();
			 String expectedText = "Thank you";
			 Assert.assertTrue(actualText.contains(expectedText));
	    } catch (Exception e) {
	        // If shipping address is not displayed, assert that payment method is displayed
	        WebElement paymentMethodTitle = driver.findElement(By.xpath("//h4[@class='mb-1 mt-3' and contains(text(), 'Payment Method')]"));
	        Assert.assertTrue(paymentMethodTitle.isDisplayed());
	        
	        // Click payment by card
	        List<WebElement> paymentMethods = driver.findElements(By.cssSelector("div.flex.justify-start.items-center.gap-1 > a"));
	        WebElement paymentByCard = paymentMethods.get(0);
	        paymentByCard.click();
	        
	        // Click place order button
	        WebElement placeOrderButton = driver.findElement(By.className("button"));
	        placeOrderButton.click();
	        
	        // Check order success screen
	        WebElement thankYouElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3.thank-you")));
	        String actualText = thankYouElement.getText();
	        String expectedText = "Thank you";
	        Assert.assertTrue(actualText.contains(expectedText));
	    }
	}
	
	@Test(enabled=false)
	public void paymentByCard() {
		login("minh_test_4@gmail.com", "Bb@123456");
		driver.get(URL_dashBoard);
		
	    addNewProductToCart();
	    
	    // View cart
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement iconLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.mini-cart-icon")));
	    iconLink.click();
	    
	    // Click button checkout
	    WebElement buttonCheckOut = driver.findElement(By.cssSelector("a[href='/checkout'].button.primary"));
	    buttonCheckOut.click();
	    
	    // Input shipping address
	    try {
	        // Attempt to input shipping address
	        fullName = "minh nguyen 2";
	        telephone = "0123456789";
	        address = "Dan Phuong";
	        city = "Ha Noi";
	        country = "Vietnam";
	        province = "Ha Noi";
	        postcode = "123457";
	        shippingMethod = "";
	        
	        inputAdress(fullName, telephone, address, city, country, province, postcode, shippingMethod);
	        
	        // If shipping address is not displayed, assert that payment method is displayed
	        WebElement paymentMethodTitle = driver.findElement(By.xpath("//h4[@class='mb-1 mt-3' and contains(text(), 'Payment Method')]"));
	        Assert.assertTrue(paymentMethodTitle.isDisplayed());
	        
	        // Click payment by card
	        List<WebElement> paymentMethods = driver.findElements(By.cssSelector("div.flex.justify-start.items-center.gap-1 > a"));
	        WebElement paymentByCard = paymentMethods.get(1);
	        paymentByCard.click();
	        
	        // Assert that card number field is displayed
	        WebElement cardNumberElement = driver.findElement(By.xpath("//label[contains(text(), 'Card Number')]"));
	        Assert.assertTrue(cardNumberElement.isDisplayed());
	        
	        // Click place order button
	        WebElement placeOrderButton = driver.findElement(By.className("button"));
	        placeOrderButton.click();
	        
	        // Check order success screen
	        WebElement thankYouElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3.thank-you")));
	        String actualText = thankYouElement.getText();
	        String expectedText = "Thank you";
	        Assert.assertTrue(actualText.contains(expectedText));
	    } catch (Exception e) {
	        // If shipping address is not displayed, assert that payment method is displayed
	        WebElement paymentMethodTitle = driver.findElement(By.xpath("//h4[@class='mb-1 mt-3' and contains(text(), 'Payment Method')]"));
	        Assert.assertTrue(paymentMethodTitle.isDisplayed());
	        
	        // Click payment by card
	        List<WebElement> paymentMethods = driver.findElements(By.cssSelector("div.flex.justify-start.items-center.gap-1 > a"));
	        WebElement paymentByCard = paymentMethods.get(1);
	        paymentByCard.click();
	        
	        // Assert that card number field is displayed
	        WebElement cardNumberElement = driver.findElement(By.xpath("//label[contains(text(), 'Card Number')]"));
	        Assert.assertTrue(cardNumberElement.isDisplayed());
	        
	        // Click place order button
	        WebElement placeOrderButton = driver.findElement(By.className("button"));
	        placeOrderButton.click();
	        
	        // Check order success screen
	        WebElement thankYouElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3.thank-you")));
	        String actualText = thankYouElement.getText();
	        String expectedText = "Thank you";
	        Assert.assertTrue(actualText.contains(expectedText));
	    }
	}
	
	@Test(priority = 3)
	public void paymentHasNoAddress() {
		login("minh_test_5@gmail.com", "Bb@123456");
		driver.get(URL_dashBoard);
		
		addNewProductToCart();
	    
	    // View cart
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement iconLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.mini-cart-icon")));
	    iconLink.click();
	    
	    // Click button checkout
	    WebElement buttonCheckOut = driver.findElement(By.cssSelector("a[href='/checkout'].button.primary"));
	    buttonCheckOut.click();
	    
	    // NOT Input shipping address
        // Click place order button
        WebElement placeOrderButton = driver.findElement(By.className("button"));
        placeOrderButton.click();
        
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'required')]")));
		
		List<WebElement> errorMessages = driver.findElements(By.xpath("//*[contains(text(), 'required')]"));
		
//		Assert.assertEquals(errorMessages.size(), errorMessages.size());
		
		Assert.assertEquals(errorMessages.get(0).getText(), "Full name is required");
        
        // Check order success screen
        
	}
	
//	@Test(priority = 4)
//	public void paymentHasNoShippingMethod() {
//		
//	}
//	
//	@Test(priority = 5)
//	public void paymentHasNoPaymentMethod() {
//		
//	}
	
//	@AfterMethod
//	public void tearDown() {
//		driver.quit();
//	}
}