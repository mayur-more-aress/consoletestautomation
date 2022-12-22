package com.domainzwebsite.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class DMZDomainSearchPage extends TestBase{

	//Objects     
    @FindBy(how=How.ID, using = "continueCart")
    WebElement continueToCheckoutButton;
	
	//Initializing Page Objects
    public DMZDomainSearchPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public DMZAddDomainPrivacyPage clickContinueToCheckout() throws InterruptedException{
    	Thread.sleep(4000);
    	System.out.println("clicking continue to checkout");
    	if(continueToCheckoutButton.isDisplayed()||continueToCheckoutButton.isEnabled()) {
    		continueToCheckoutButton.click();
    	}
		else {
			System.out.println("element not found");
		}
    	return new DMZAddDomainPrivacyPage();
    }
    
    public DMZHostingAndExtrasPage clickContinueToCheckoutWithoutDomainPrivacy(){
    	System.out.println("clicking continue to checkout");
    	if(continueToCheckoutButton.isDisplayed()||continueToCheckoutButton.isEnabled()) {
    		continueToCheckoutButton.click();
    	}
		else {
			System.out.println("element not found");
		}
    	return new DMZHostingAndExtrasPage();
    }
    
}

