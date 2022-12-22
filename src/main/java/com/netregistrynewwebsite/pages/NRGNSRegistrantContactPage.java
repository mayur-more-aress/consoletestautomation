package com.netregistrynewwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class NRGNSRegistrantContactPage extends TestBase{

	
	//Objects        
	@FindBy(how=How.XPATH, using = "//div[@class='btn btnBg green']")
    public static WebElement continueButton;
	
	@FindBy(how=How.XPATH, using = "//div[@class='contact-list cell-content']/div[2]/div[2]/div")
    public static WebElement createButton;
	
	    
    //Initializing Page Objects
    public NRGNSRegistrantContactPage() throws InterruptedException{
   
    	PageFactory.initElements(driver, this);
    }


    //Methods   
    public void clickDomainInformation(String domaininformation) throws InterruptedException {
    	
    	Thread.sleep(5000);
    	//driver.findElement(By.xpath("//input[@value='"+domaininformation+"']")).click();
    	//driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/div/div/div[1]/div[2]/div[3]/label/input")).click();
    	driver.findElement(By.xpath("//*[@id='body']/div[1]/div[1]/div/div/div/div/div[1]/div[2]/div[5]/label/input")).click();
    }
    
 public void clickDomainInfo(String domaininformation) throws InterruptedException {
    	//About to build website
    	Thread.sleep(5000);
    	driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/div/div/div[1]/div[2]/div[2]/label/input")).click();
    }
    
    public void clickRegistrantContact( ) throws InterruptedException 
    {
    	Thread.sleep(5000);
    	driver.findElement(By.xpath("//input[@name='detail']")).click();
    }
  
    public NRGNSAddServicesToYourDomainPage clickContinueButton() throws InterruptedException {

    	Thread.sleep(5000);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
    	continueButton.click();
    	
    	return new NRGNSAddServicesToYourDomainPage();
    }
    
    public NRGNSEligibilityDetailsPage clickContinueButtonToAuEligibility() throws InterruptedException 
	{

    	Thread.sleep(5000);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
    	continueButton.click();
    	
    	return new NRGNSEligibilityDetailsPage();
    }
    
    public NRGNSReviewAndPaymentPage clickContinueButtonToPayment() throws InterruptedException 
   	{
       	Thread.sleep(5000);
       	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
       	continueButton.click();
       	
       	return new NRGNSReviewAndPaymentPage();
    }
    
    public void refreshRegistrantPage() throws InterruptedException {
    
    	int maximumNumberOfRetry = 5;
    	String currentURL = null;
	
    	for (int i = 1; i <= maximumNumberOfRetry; i++) {
	    		
    		Thread.sleep(10000);
    		driver.navigate().refresh();
    		System.out.println("Refresh Retry: " + i);
    		
    		//Add delay to check if registrant page is loaded after refresh
    		Thread.sleep(10000);
    		currentURL = driver.getCurrentUrl();
    		
    		System.out.println("Current URL: " + currentURL);
    		
    		if (currentURL.contains("/registrant/")) {
    			
    			System.out.println("Registrant Page loaded");
    			break;
    		}
    		else if (i == maximumNumberOfRetry) {
    			
    			System.out.println("Registrant Page not loading after several retries");
    		}
    		
	    }
	} 
}

