package com.domainzwebsite.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class DMZHeaderPage extends TestBase{

	//Objects
    @FindBy(how=How.XPATH, using = ".//*[@id='wrap']/div[2]/div[2]/div[1]/ul/li[1]/a/span")
    WebElement overviewTab;
    
    @FindBy(how=How.XPATH, using = "//*[@title='Order new services/domains and view outstanding/completed orders']")
    WebElement orderTab;
    
    //@FindBy(how=How.XPATH, using = ".//*[@id='wrap']/div[2]/div[2]/div[1]/ul/li[3]/a/span")
    @FindBy(how=How.LINK_TEXT, using = "Billing")
    WebElement billingTab;
    
    @FindBy(how=How.XPATH, using = ".//*[@id='wrap']/div[2]/div[2]/div[1]/ul/li[4]/a/span")
    WebElement supportTab;
    
    @FindBy(how=How.XPATH, using = ".//*[@id='wrap']/div[2]/div[2]/div[1]/ul/li[5]/a/span")
    WebElement notificationsTab;
    
    @FindBy(how=How.LINK_TEXT, using = "Account")
    WebElement accountTab;
    
    @FindBy(how=How.NAME, using = "domain")
    WebElement domainTextBox;
    
    @FindBy(how=How.NAME, using = "domainSearch")
    WebElement viewButton;
    
	//Initializing Page Objects
	public DMZHeaderPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public DMZAccountPage clickAccountTab() throws InterruptedException {

    	System.out.println("clicking account tab");
    	if(accountTab.isDisplayed()||accountTab.isEnabled()) {
    		accountTab.click();
    	}
		else {
			System.out.println("element not found");
		}

    	return new DMZAccountPage();
    	
    }
    
    public DMZDomainDetailsPage viewDomain(String domainName)
    {
    	System.out.println("Entering the domain and clicking the view domain button");
    	domainTextBox.sendKeys(domainName);
    	viewButton.click();
    	return new DMZDomainDetailsPage();
    }
//    
//    public ConsoleClientOrderPage clickOrderTab() throws InterruptedException {
//
//    	System.out.println("clicking order tab");
//    	if(orderTab.isDisplayed()||orderTab.isEnabled()) {
//    		orderTab.click();
//    	}
//		else {
//			System.out.println("element not found");
//		}
//
//    	return new ConsoleClientOrderPage(driver);
//    	
//    }
    
	public DMZBillingPage clickBillingTab() throws InterruptedException {
		
		System.out.println("clicking account tab");
		Thread.sleep(5000);
		billingTab.click();
		Thread.sleep(5000);
		return new DMZBillingPage();
	}
}
