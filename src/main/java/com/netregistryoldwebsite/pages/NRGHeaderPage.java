package com.netregistryoldwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;
import com.netregistrynewwebsite.pages.NRGNSOrderPage;
import com.nrgcustomerportal.pages.NRGAllServicesPage;
import com.nrgcustomerportal.pages.NRGOrderTabPage;

public class NRGHeaderPage extends TestBase{

	//Objects
    @FindBy(how=How.XPATH, using = ".//*[@id='wrap']/div[2]/div[2]/div[1]/ul/li[1]/a/span")
    WebElement overviewTab;
    
    @FindBy(how=How.XPATH, using = ".//*[@id='wrap']/div[2]/div[2]/div[1]/ul/li[2]/a/span")
    WebElement orderTab;
    
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
    
    @FindBy(how=How.XPATH, using = "//*[@id='summary']/p/a[2]")
	WebElement allServicesLink;
    
	//Initializing Page Objects
	public NRGHeaderPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public NRGAccountPage clickAccountTab() throws InterruptedException {

    	System.out.println("clicking account tab");
    	if(accountTab.isDisplayed()||accountTab.isEnabled()) {
    		accountTab.click();
    	}
		else {
			System.out.println("element not found");
		}

    	return new NRGAccountPage();
    	
    }
    
    public NRGDomainDetailsPage viewDomain(String domainName)
    {
    	System.out.println("Entering domain and clicking view domain");
    	domainTextBox.sendKeys(domainName);
    	viewButton.click();
    	return new NRGDomainDetailsPage();
    }
    
	public NRGNSOrderPage clickOrderTab() throws InterruptedException 
	{
		System.out.println("clicking order tab");
		Thread.sleep(5000);
		orderTab.click();
		Thread.sleep(10000);
		return new NRGNSOrderPage();
	}
	
	public NRGOrderTabPage clickOnOrderTab() throws InterruptedException 
	{
		System.out.println("clicking order tab");
		Thread.sleep(5000);
		orderTab.click();
		Thread.sleep(5000);
		return new NRGOrderTabPage();
	}
	
	public NRGBillingPage clickBillingTab() throws InterruptedException {
		
		System.out.println("clicking account tab");
		Thread.sleep(5000);
		billingTab.click();
		Thread.sleep(5000);
		return new NRGBillingPage();
	}
	
	public NRGAllServicesPage clickOnAllServicesLink() throws InterruptedException {
		allServicesLink.click();
		Thread.sleep(2000);	
		return new NRGAllServicesPage();
	}
	public String verifyOverviewPageisDisplayed() {
		String getMessage = null;
		getMessage = driver.findElement(By.xpath("//*[@id='overview']/div/div[2]/div[1]/h1")).getText();
		return getMessage;

	}
}
