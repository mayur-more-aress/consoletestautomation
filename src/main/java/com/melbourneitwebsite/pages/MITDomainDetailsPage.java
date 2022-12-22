package com.melbourneitwebsite.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class MITDomainDetailsPage extends TestBase
{
	 @FindBy(how=How.XPATH, using = "//span[@id='domain-expiry']/a")
	    WebElement renewLink;
	 
	//Initializing Page Objects
		public MITDomainDetailsPage(){
	        PageFactory.initElements(driver, this);
	    }
		
		public MITBillingPage cickRenewLink() throws InterruptedException
		{
			Thread.sleep(2000);
			renewLink.click();
			return new MITBillingPage();
		}
}
