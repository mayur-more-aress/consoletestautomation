package com.netregistryoldwebsite.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class NRGDomainDetailsPage extends TestBase
{
	 @FindBy(how=How.XPATH, using = "//span[@id='domain-expiry']/a")
	    WebElement renewLink;
	 
	//Initializing Page Objects
		public NRGDomainDetailsPage(){
	        PageFactory.initElements(driver, this);
	    }
		
		public NRGBillingPage cickRenewLink() throws InterruptedException
		{
			Thread.sleep(8000);
			renewLink.click();
			return new NRGBillingPage();
		}
}
