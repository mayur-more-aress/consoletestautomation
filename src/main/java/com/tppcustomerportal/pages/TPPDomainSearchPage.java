package com.tppcustomerportal.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.base.TestBase;



public class TPPDomainSearchPage extends TestBase {

	// Objects

	@FindBy(how = How.XPATH, using = "//div[@class='orderBoxWrapper domainSearchTable']//table//tbody/tr[2]/td[2]")
	WebElement domainStatus;

	@FindBy(how = How.ID, using = "continueCart")
	WebElement continueToCheckoutButton;

	// Initializing Page Objects
	public TPPDomainSearchPage() {
		PageFactory.initElements(driver, this);
	}
	// Methods

	public Boolean checkStatus() throws InterruptedException {
		Boolean flag = false;
		Thread.sleep(1000);
		if (domainStatus.isDisplayed() || domainStatus.isEnabled()) {
			System.out.println(domainStatus.getText());
			flag = true;

		} else {
			System.out.println("element not found");
		}

		return flag;
	}

		public TPPHostingAndExtrasPage clickContinueToCheckoutWithoutDomainPrivacy() {

		new WebDriverWait(driver, 30)
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#shopping-cart .order")));
		System.out.println("clicking continue to checkout");
		if (continueToCheckoutButton.isDisplayed() || continueToCheckoutButton.isEnabled()) {
			continueToCheckoutButton.click();
		} else {
			System.out.println("element not found");
		}
		return new TPPHostingAndExtrasPage();
	}

	public TPPAddDomainPrivacyPage clickContinueToCheckoutWithDomainPrivacy() {

		new WebDriverWait(driver, 30)
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#shopping-cart .order")));
		System.out.println("clicking continue to checkout");
		if (continueToCheckoutButton.isDisplayed() || continueToCheckoutButton.isEnabled()) {
			continueToCheckoutButton.click();
		} else {
			System.out.println("element not found");
		}
		return new TPPAddDomainPrivacyPage();
	}
	
	public String checkDomainNameAvailabilty(String domainName, String tldName) throws Exception 
    {
    	//Get all the domains in search result
    	List<WebElement> domains = driver.findElements(By.cssSelector(".searchResults .domain"));
       	for(WebElement domain: domains)
       	{
       		String resultDomainName = domain.getText();
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
				else if(domainNameAvailable.equalsIgnoreCase("Premium Name - Contact us to secure"))
				{
					System.out.println("The domain " + domainName + tldName+ " is Premium");
				}
				else
				{
					domainNameAvailable="Unknown error occured";
					System.out.println("An Unknown error occured ");
				}
    			return domainNameAvailable;
    		}
    	} 
       	throw new Exception("Domain name is not available");
    }
	
	 
	    
}
