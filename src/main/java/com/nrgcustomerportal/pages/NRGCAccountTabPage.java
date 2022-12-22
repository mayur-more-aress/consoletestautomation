package com.nrgcustomerportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class NRGCAccountTabPage extends TestBase
{
	//Objects
    @FindBy(how=How.XPATH, using = "(//span[contains(text(),'Account')])[1]")
    WebElement AccountTabLink;

    @FindBy(how=How.XPATH, using = "//a[contains(text(),'Cancel services')]")
    WebElement cancelServicesLink;

	//Initializing Page Objects
	public NRGCAccountTabPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void clickAccountTabLink() throws InterruptedException {
    	AccountTabLink.click();
	    Thread.sleep(2000);	
    }
    
    public NRGCCancelServicesPage clickOnCancelServicesLink() throws InterruptedException {

    	System.out.println("clicking on cancel services link");
    	if(cancelServicesLink.isDisplayed()||cancelServicesLink.isEnabled()) {
    		cancelServicesLink.click();
    		Thread.sleep(2000);
    	}
		else {
			System.out.println("element not found");
		}

    	return new NRGCCancelServicesPage();
    	
    }
}
