package com.netregistryoldwebsite.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;


public class NRGDomainSearchPage extends TestBase{

	//Objects     
    @FindBy(how=How.ID, using = "continueCart")
    WebElement continueToCheckoutButton;
	
	//Initializing Page Objects
    public NRGDomainSearchPage(){
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
    
    public NRGAddDomainPrivacyPage clickContinueToCheckout() throws InterruptedException{
    	Thread.sleep(5000);
		new WebDriverWait(driver, 30)
		.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#shopping-cart .order")));
    	System.out.println("clicking continue to checkout");
    	if(continueToCheckoutButton.isDisplayed()||continueToCheckoutButton.isEnabled()) {
    		continueToCheckoutButton.click();
    	}
		else {
			System.out.println("element not found");
		}
    	return new NRGAddDomainPrivacyPage();
    }
    
    public NRGHostingAndExtrasPage clickContinueToCheckoutWithoutDomainPrivacy() throws InterruptedException{
    	Thread.sleep(5000);
    	JavascriptExecutor js = ((JavascriptExecutor) driver);
    	js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    	//driver.findElement(By.xpath("//div/div/table/tbody/tr/td[@class='addToCart']")).click();
    	new WebDriverWait(driver, 30)
		.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#shopping-cart .order")));
    	System.out.println("clicking continue to checkout");
    	if(continueToCheckoutButton.isDisplayed()||continueToCheckoutButton.isEnabled()) {
    		continueToCheckoutButton.click();
    		WebElement nothanks=driver.findElement(By.xpath("//input[@class=\"no-thanks\"]"));
    		nothanks.click();
    	}
		else {
			System.out.println("element not found");
		}
    	return new NRGHostingAndExtrasPage();
    }
    
}
