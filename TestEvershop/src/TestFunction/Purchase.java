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
	
	public double checkoutPrice() {
        List<WebElement> productRows = driver.findElements(By.cssSelector(".checkout-summary .items-table tbody tr"));
        double calculatedTotal = 0.0;

        for (WebElement row : productRows) {
            int quantity = Integer.parseInt(row.findElement(By.cssSelector(".qty")).getText());

            String priceText = row.findElement(By.cssSelector("td:nth-child(3) span")).getText().replace("$", "");
            double price = Double.parseDouble(priceText);

            calculatedTotal += quantity * price;
        }
        return calculatedTotal;
	}
	
	public double orderPrice() {
		 List<WebElement> productRows = driver.findElements(By.cssSelector(".items-table tbody tr"));
	        double calculatedTotal = 0.0;
	        for (WebElement row : productRows) {
	            String priceText = row.findElement(By.cssSelector("td:nth-child(2) .sale-price")).getText().replace("$", "");
	            double price = Double.parseDouble(priceText);
	            int quantity = Integer.parseInt(row.findElement(By.cssSelector("td:nth-child(3) span")).getText());
	            calculatedTotal += quantity * price;
	        }
        return calculatedTotal;
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
		 
	}
	public void inputAddressAndPaymentMethod(String fullName, String telephone, String address, String city, String country, String province, String postcode,String shippingMethod) {
		 inputAdress(fullName, telephone, address, city, country, province, postcode, shippingMethod);
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
	
	public void addNewProductToCart(String productName) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));    
	    WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='product-name product-list-name mt-1 mb-025']/a/span[text()='" + productName + "']")));
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
	
	@Test(priority=1, enabled=true)
	public void paymentByCash() {
		login("minh_test_4@gmail.com", "Bb@123456");
		driver.get(URL_dashBoard);
		
	    addNewProductToCart("Denim Skinny Jeans");
	    
	    // View cart
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement iconLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.mini-cart-icon")));
	    iconLink.click();
	    
	    //Check order price
	    double orderPrice = orderPrice();
	    String displayedTotalOrderText = driver.findElement(By.cssSelector(".summary .grand-total-value")).getText().replace("$", "");
        double displayedTotalOrder = Double.parseDouble(displayedTotalOrderText);
        Assert.assertEquals(orderPrice, displayedTotalOrder);
	    
	    // Click button checkout
	    WebElement buttonCheckOut = driver.findElement(By.cssSelector("a[href='/checkout'].button.primary"));
	    buttonCheckOut.click();
	    
	    //Check check out summary
        String displayedTotalCheckoutText = driver.findElement(By.cssSelector(".grand-total-value")).getText().replace("$", "");
        double displayedTotalCheckout = Double.parseDouble(displayedTotalCheckoutText);
        Assert.assertEquals(displayedTotalCheckout, displayedTotalOrder);
	    
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
			
			inputAddressAndPaymentMethod(fullName, telephone, address, city, country, province, postcode, shippingMethod);
			 
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
	
	@Test(priority=2, enabled=false)
	public void paymentByCard() {
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
	        
	        inputAddressAndPaymentMethod(fullName, telephone, address, city, country, province, postcode, shippingMethod);
	        
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
	
	@Test(priority = 3, enabled=false)
	public void paymentHasNoAddress() {
		login("minh_test_6@gmail.com", "Bb@123456");
		driver.get(URL_dashBoard);
		
		addNewProductToCart();
	    
	    // View cart
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement iconLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.mini-cart-icon")));
	    iconLink.click();
	    
	    //Check order price
	    double orderPrice = orderPrice();
	    String displayedTotalOrderText = driver.findElement(By.cssSelector(".summary .grand-total-value")).getText().replace("$", "");
        double displayedTotalOrder = Double.parseDouble(displayedTotalOrderText);
        Assert.assertEquals(orderPrice, displayedTotalOrder);
	    
	    // Click button checkout
	    WebElement buttonCheckOut = driver.findElement(By.cssSelector("a[href='/checkout'].button.primary"));
	    buttonCheckOut.click();
	    
	    // NOT Input shipping address
        // Click Continue Payment Button
	    WebElement continuePaymentButton = driver.findElement(By.className("button"));
		 continuePaymentButton.click();
		
        //Find list error message
        List<WebElement> errorElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[contains(text(), 'required')]")));
		Assert.assertEquals(errorElements.get(1).getText(), "Full name is required");
		Assert.assertEquals(errorElements.get(2).getText(), "Telephone is required");
		Assert.assertEquals(errorElements.get(3).getText(), "Address is required");
		Assert.assertEquals(errorElements.get(4).getText(), "City is required");
		Assert.assertEquals(errorElements.get(5).getText(), "Country is required");
		Assert.assertEquals(errorElements.get(6).getText(), "Postcode is required");
	}
	
	@Test(priority = 4, enabled=false)
	public void paymentHasNoShippingMethod() {
		login("minh_test_7@gmail.com", "Bb@123456");
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
        
        // NOT Input Shipping method
        // Click Continue Payment Button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'GH Delivery - $9.00')]")));
	    WebElement continuePaymentButton = driver.findElement(By.className("button"));
		continuePaymentButton.click();
		
		//Find error message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'not be empty')]")));
		List<WebElement> errorMessages = driver.findElements(By.xpath("//*[contains(text(), 'not be empty')]"));
		Assert.assertEquals(errorMessages.get(0).getText(), "This field can not be empty");
	}
	
	@Test(priority = 5, enabled=false)
	public void paymentHasNoPaymentMethod() {
		login("minh_test_8@gmail.com", "Bb@123456");
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
			
			inputAddressAndPaymentMethod(fullName, telephone, address, city, country, province, postcode, shippingMethod);
			 
			 //payment by cash
			 WebElement paymentMethodTitle = driver.findElement(By.xpath("//h4[@class='mb-1 mt-3' and contains(text(), 'Payment Method')]"));
			 Assert.assertTrue(paymentMethodTitle.isDisplayed());
			 
			 WebElement placeOrderButton = driver.findElement(By.className("button"));
			 placeOrderButton.click();
			  
			 //Check error message 
			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Please select a payment method')]")));
			 List<WebElement> errorMessages = driver.findElements(By.xpath("//*[contains(text(), 'Please select a payment method')]"));
			 Assert.assertFalse(errorMessages.isEmpty());
			 
	    } catch (Exception e) {
	        // If shipping address is not displayed, assert that payment method is displayed
	        WebElement paymentMethodTitle = driver.findElement(By.xpath("//h4[@class='mb-1 mt-3' and contains(text(), 'Payment Method')]"));
	        Assert.assertTrue(paymentMethodTitle.isDisplayed());
	        
	        // Click place order button
	        WebElement placeOrderButton = driver.findElement(By.className("button"));
	        placeOrderButton.click();
	        
	      //Check error message
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Please select a payment method')]")));
			List<WebElement> errorMessages = driver.findElements(By.xpath("//*[contains(text(), 'Please select a payment method')]"));
			Assert.assertFalse(errorMessages.isEmpty());
	    }
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}