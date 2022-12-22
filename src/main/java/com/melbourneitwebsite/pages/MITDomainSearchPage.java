package com.melbourneitwebsite.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.base.TestBase;


public class MITDomainSearchPage extends TestBase{

	//Objects     
    @FindBy(how=How.ID, using = "continueCart")
    WebElement continueToCheckoutButton;
	
	//Initializing Page Objects
    public MITDomainSearchPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    
    public void checkStatusAndAddDomain(String domainName, String tldName) throws Exception 
    {
    	//Get all the domains in search result
    	int index = 2;
    	Thread.sleep(8000);
    	List<WebElement> domains = driver.findElements(By.xpath("//table/tbody/tr/td[1]"));
       	for(WebElement domain: domains)
       	{
       		String resultDomainName = domain.getText();
    		System.out.println("This the domain name with extension " + resultDomainName);
    		
    		if (resultDomainName.equalsIgnoreCase(domainName+tldName))
    		{
    			Thread.sleep(8000);
    			int pos = index++;
    			String domainNameAvailable = driver.findElement(By.xpath("//table/tbody/tr["+pos+"]/td[2]")).getText();
				if(domainNameAvailable.equalsIgnoreCase("Backorder"))
				{
					Thread.sleep(8000);
					System.out.println("The domain " + domainName + tldName+ " is in backorder");
					driver.findElement(By.xpath("//table/tbody/tr["+pos+"]/td[4]/form/button")).click();
					break;
				}
				
				else
				{
					domainNameAvailable="Domain is not in backorder";
					throw new Exception("Domain name is not in backorder");
				}
				
    		}
    	} 
       	
    }
    
    public MITAddDomainPrivacyPage clickContinueToCheckout() throws InterruptedException{
    	Thread.sleep(8000);
    	System.out.println("clicking continue to checkout");
    	if(continueToCheckoutButton.isDisplayed()||continueToCheckoutButton.isEnabled()) {
    		continueToCheckoutButton.click();
    	}
		else {
			System.out.println("element not found");
		}
    	return new MITAddDomainPrivacyPage();
    }
    
    public MITHostingAndExtrasPage clickContinueToCheckoutWithoutDomainPrivacy(){
    	System.out.println("clicking continue to checkout");
    	if(continueToCheckoutButton.isDisplayed()||continueToCheckoutButton.isEnabled()) {
    		continueToCheckoutButton.click();
    	}
		else {
			System.out.println("element not found");
		}
    	return new MITHostingAndExtrasPage();
    }
    
    public String checkDomainNameAvailabilty(String domainName, String tldName) throws Exception 
    {
    	//Get all the domains in search result
    	List<WebElement> domains = driver.findElements(By.cssSelector(".searchResults .domain"));
       	for(WebElement domain: domains)
       	{
       		String resultDomainName = domain.getText();
    		System.out.println("This the domain name with extension " + resultDomainName);
    		
    		if (resultDomainName.equalsIgnoreCase(domainName+tldName))
    		{
    			Thread.sleep(8000);
    			String domainNameAvailable = driver.findElement(By.xpath("//*[@id='domainSearchResults']//table/tbody/tr/td[2]")).getText();
				if(domainNameAvailable.equalsIgnoreCase("Available"))
				{
					System.out.println("The domain " + domainName + tldName+ " is available");
				}
				else if(domainNameAvailable.equalsIgnoreCase("Unavailable") || domainNameAvailable.equalsIgnoreCase("Backorder"))
				{
					domainNameAvailable = "Unavailable";
					System.out.println("The domain " + domainName + tldName+ " is not available");
				}
				else if(domainNameAvailable.equalsIgnoreCase("Premium Name - Call 1300 793 248 to secure"))
				{
					System.out.println("The domain " + domainName + tldName+ " is Premium");
				}
				else
				{
					domainNameAvailable="Unknown error occured";
					System.out.println("Unknown error occured : Registry connection failed, please try again");
				}
				return domainNameAvailable;
    		}
    	} 
       	throw new Exception("Domain name is not available");
    }
    
}
