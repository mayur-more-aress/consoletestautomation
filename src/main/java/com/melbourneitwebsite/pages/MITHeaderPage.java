package com.melbourneitwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;
import com.mitcustomerportal.pages.MITAllServicesPage;
import com.mitcustomerportal.pages.MITOrderTabPage;

public class MITHeaderPage extends TestBase{

	//Objects
    @FindBy(how=How.XPATH, using = ".//*[@id='wrap']/div[2]/div[2]/div[1]/ul/li[1]/a/span")
    WebElement overviewTab;
    
    @FindBy(how=How.XPATH, using = ".//*[@id='wrap']/div[2]/div[2]/div[1]/ul/li[2]/a/span")
    WebElement orderTab;
    
    @FindBy(how=How.CSS, using = ".nav-tabs .billing ")
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
    
    @FindBy(how=How.XPATH, using = "//*[@id='summary']/p/a[2]")
	WebElement allServicesLink;
    
	//Initializing Page Objects
	public MITHeaderPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public MITAccountPage clickAccountTab() throws InterruptedException {

    	System.out.println("clicking account tab");
    	if(accountTab.isDisplayed()||accountTab.isEnabled()) {
    		Thread.sleep(3000);
    		accountTab.click();
    	}
		else {
			System.out.println("element not found");
		}

    	return new MITAccountPage();
    	
    }
    
    public MITDomainDetailsPage viewDomain(String domainName) throws InterruptedException
    {
    	Thread.sleep(4000);
    	System.out.println("Entering the domain and clicking the view domain button");
    	domainTextBox.sendKeys(domainName);
    	viewButton.click();
    	return new MITDomainDetailsPage();
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
	

	public MITBillingPage clickBillingTab() throws InterruptedException {
		
		System.out.println("Clicking billing tab");
		billingTab.click();

		return new MITBillingPage();
	}
	
	public MITOrderTabPage clickOnOrderTab() throws InterruptedException 
	{
		System.out.println("clicking order tab");
		Thread.sleep(6000);
		orderTab.click();
		Thread.sleep(6000);
		return new MITOrderTabPage();
	}
	
	public MITAllServicesPage clickOnAllServicesLink() throws InterruptedException {
		allServicesLink.click();
		Thread.sleep(2000);	
		return new MITAllServicesPage();
	}
	public String verifyOverviewPageisDisplayed() throws InterruptedException {
		Thread.sleep(5000);
		String getMessage = null;
		getMessage = driver.findElement(By.xpath("//*[@id='overview']/div/div[2]/div[1]/h1")).getText();
		return getMessage;

	}
}
