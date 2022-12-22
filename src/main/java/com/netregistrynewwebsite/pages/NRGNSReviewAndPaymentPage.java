package com.netregistrynewwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import com.base.TestBase;

public class NRGNSReviewAndPaymentPage extends TestBase{

	
	//Objects        
    @FindBy(how=How.ID, using = "autoRenew")
    WebElement automaticRenew;
   
    
    
    @FindBy(how=How.CSS, using = "[for='termsAgree']")
    public static WebElement agreeTermsAndConditions;
    
    @FindBy(how=How.CSS, using = "button.btn.green")
   public static WebElement completeOrderButton;
    
    @FindBy(how=How.XPATH, using = "//body/input")
    public static WebElement recaptchaChallenge;
	
    
    //Initializing Page Objects
    public NRGNSReviewAndPaymentPage(){
    	PageFactory.initElements(driver, this);
    }

    
    //Methods    
    public void setBTFormCreditCardDetails(String cardOwner, String cardNumber, String cardExpiryMonth, String cardExpiryYear, String cardSecurityCode) throws InterruptedException{ 	
    	
    	WebElement elNameOnCard = driver.findElement(By.id("name"));
    	elNameOnCard.clear();
    	elNameOnCard.sendKeys(cardOwner);
    	
    	Thread.sleep(4000);
    	driver.switchTo().frame("braintree-hosted-field-number");
    	new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("credit-card-number"))));
    	WebElement elCardNumber = driver.findElement(By.id("credit-card-number"));	
    	elCardNumber.clear();
    	elCardNumber.sendKeys(cardNumber);
    	driver.switchTo().defaultContent();
    	
    	driver.switchTo().frame("braintree-hosted-field-expirationMonth");
    	new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("expiration-month"))));
    	WebElement elCardExpiryMonth = driver.findElement(By.id("expiration-month"));
    	elCardExpiryMonth.sendKeys(cardExpiryMonth);
    	driver.switchTo().defaultContent();
    	
    	driver.switchTo().frame("braintree-hosted-field-expirationYear");
    	new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("expiration-year"))));
    	WebElement elCardExpiryYear = driver.findElement(By.id("expiration-year"));
    	elCardExpiryYear.sendKeys(cardExpiryYear);
    	driver.switchTo().defaultContent();
    	
    	driver.switchTo().frame("braintree-hosted-field-cvv");
    	new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("cvv"))));
    	WebElement elCardSecurityCode = driver.findElement(By.id("cvv"));	
    	elCardSecurityCode.sendKeys(cardSecurityCode);
    	driver.switchTo().defaultContent();
    	
    	
    }
    
    public void tickTermsAndConditions(){
    	
       	System.out.println("I agree to the terms outlined in the Terms and Conditions");
       	if(agreeTermsAndConditions.isDisplayed()||agreeTermsAndConditions.isEnabled()) {
       		agreeTermsAndConditions.click();
       	}
    	else {
    		System.out.println("element not found");
    	}
    }
    
    public NRGNSOrderCompletePage clickCompleteOrder() throws InterruptedException{
    	
       	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", completeOrderButton);
       	System.out.println("clicking complete order button");
       	if(completeOrderButton.isDisplayed()||completeOrderButton.isEnabled()) {
       		completeOrderButton.click();
       	}
    	else {
    		System.out.println("element not found");
    	}  
       	
       	Thread.sleep(4000);
       	return new NRGNSOrderCompletePage();  	
    }
    
    
    public Boolean isReCaptchaChallengeDisplayed() throws InterruptedException{
    	
    	Boolean flag = false;
    	String recaptchatoken = null;
    	
    	Thread.sleep(3000);
    	System.out.println("Verify if recaptcha is displayed");
    	driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='recaptcha challenge']")));
    	((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('type', '')", recaptchaChallenge);
    	recaptchatoken = 	recaptchaChallenge.getAttribute("value");
    	    	
    	if(recaptchaChallenge != null) {
    		
    		System.out.println("Recaptch Token: " + recaptchatoken);
	    	System.out.println("Recaptcha Challenge Displayed!");
    		flag = true;
       	}
    	
    	Thread.sleep(10000);
    	
    	return flag;
    }
    
    public void selectNewCreditCardOption(){
    	
       	System.out.println("select new credit card option");
      //driver.findElement(By.xpath("//div[@class='cart-box']/div[2]/div/div[2]/div[2]/div/label")).click();
        driver.findElement(By.cssSelector("[for='new-billing']")).click();
    }
    
    public void selectExistingCreditCard(String cardnumber) throws InterruptedException{

    	System.out.println("select existing credit card");
    	driver.findElement(By.xpath("//span[@class='digits ng-binding'][text()='"+ cardnumber +"']")).click();
    	System.out.println("Existing credit card was clicked");
    	
    	Thread.sleep(5000);

    }
    
    public void selectPrepaidAccount() throws InterruptedException{

    	System.out.println("select prepaid account");
    	List<WebElement> lstCards = driver.findElements(By.xpath("//*[@class='cell cc-form']/div[2]/div/div/label/span[2]"));
    	for(WebElement card : lstCards )
    	{
    		if(card.getText().contains("Prepaid"))
    		{
    			card.click();
    			break;
    		}
    	}
    	Thread.sleep(5000);

    }
}

