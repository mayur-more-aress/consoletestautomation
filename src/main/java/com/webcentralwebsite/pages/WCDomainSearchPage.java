package com.webcentralwebsite.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.base.TestBase;


public class WCDomainSearchPage extends TestBase{

	//Objects     
    @FindBy(how=How.ID, using = "continueCart")
    WebElement continueToCheckoutButton;
	
	//Initializing Page Objects
    public WCDomainSearchPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public WCAddDomainPrivacyPage clickContinueToCheckout() throws InterruptedException{
    	Thread.sleep(2000);
    	System.out.println("clicking continue to checkout");
    	if(continueToCheckoutButton.isDisplayed()||continueToCheckoutButton.isEnabled()) {
    		continueToCheckoutButton.click();
    	}
		else {
			System.out.println("element not found");
		}
    	return new WCAddDomainPrivacyPage();
    }
    
    public WCHostingAndExtrasPage clickContinueToCheckoutWithoutDomainPrivacy(){
    	System.out.println("clicking continue to checkout");
    	if(continueToCheckoutButton.isDisplayed()||continueToCheckoutButton.isEnabled()) {
    		continueToCheckoutButton.click();
    	}
		else {
			System.out.println("element not found");
		}
    	return new WCHostingAndExtrasPage();
    }
    
}
