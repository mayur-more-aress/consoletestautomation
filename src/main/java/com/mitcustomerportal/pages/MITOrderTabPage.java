package com.mitcustomerportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class MITOrderTabPage extends TestBase
{
	//Objects
    @FindBy(how=How.XPATH, using = "//span[contains(text(),'Order')]")
    WebElement orderTabLink;

    @FindBy(how=How.XPATH, using = "//*[@id='register-domain']/ul/li[1]/a")
    WebElement registrantnamechangeLink;

	//Initializing Page Objects
	public MITOrderTabPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void clickOrderTabLink() throws InterruptedException {
    	orderTabLink.click();
	    Thread.sleep(2000);	
    }
    
    public MITRegistrantNameChangePage clickOnDomainOwnershipLink() throws InterruptedException {

    	System.out.println("clicking registrant name change link");
    	if(registrantnamechangeLink.isDisplayed()||registrantnamechangeLink.isEnabled()) {
    		registrantnamechangeLink.click();
    		Thread.sleep(4000);
    	}
		else {
			System.out.println("element not found");
		}

    	return new MITRegistrantNameChangePage();
    	
    }
}
