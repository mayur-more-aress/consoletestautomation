package com.mitdpscustomerportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class MITDPSHeaderPage extends TestBase
{
	@FindBy(how=How.LINK_TEXT, using = "Billing")
    WebElement billingTab;
	
	@FindBy(how=How.XPATH, using = "//*[@id='summary']/p/a[2]")
	WebElement allServicesLink;
	
	 @FindBy(how=How.LINK_TEXT, using = "Account")
	  WebElement accountTab;
    
	//Initializing Page Objects
	public MITDPSHeaderPage(){
        PageFactory.initElements(driver, this);
    }
	
	public MITDPSBillingPage clickBillingTab() throws InterruptedException {
		
		System.out.println("clicking account tab");
		Thread.sleep(5000);
		billingTab.click();
		Thread.sleep(5000);
		return new MITDPSBillingPage();
	}
	
	public MITDPSAllServicesPage clickOnAllServicesLink() throws InterruptedException {
		allServicesLink.click();
		Thread.sleep(2000);	
		return new MITDPSAllServicesPage();
	}
	
	public MITDPSAccountTabPage clickAccountTab() throws InterruptedException {

    	System.out.println("clicking account tab");
    	if(accountTab.isDisplayed()||accountTab.isEnabled()) {
    		accountTab.click();
    	}
		else {
			System.out.println("element not found");
		}

    	return new MITDPSAccountTabPage();
    	
    }
}
