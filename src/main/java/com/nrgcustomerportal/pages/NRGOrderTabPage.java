package com.nrgcustomerportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class NRGOrderTabPage extends TestBase
{
	//Objects
    @FindBy(how=How.XPATH, using = "//span[contains(text(),'Order')]")
    WebElement orderTabLink;

    @FindBy(how=How.XPATH, using = "//*[@id='updown']/ul/li[3]/a")
    WebElement changeDomainOwnershipLink;

	//Initializing Page Objects
	public NRGOrderTabPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void clickOrderTabLink() throws InterruptedException {
    	orderTabLink.click();
	    Thread.sleep(2000);	
    }
    
    public NRGRegistrantNameChangePage clickOnDomainOwnershipLink() throws InterruptedException {

    	System.out.println("clicking submit button");
    	if(changeDomainOwnershipLink.isDisplayed()||changeDomainOwnershipLink.isEnabled()) {
    		changeDomainOwnershipLink.click();
    		Thread.sleep(2000);
    	}
		else {
			System.out.println("element not found");
		}

    	return new NRGRegistrantNameChangePage();
    	
    }
}
