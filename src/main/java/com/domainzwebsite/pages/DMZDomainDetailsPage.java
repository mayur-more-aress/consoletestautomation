package com.domainzwebsite.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class DMZDomainDetailsPage  extends TestBase
{
	 @FindBy(how=How.XPATH, using = "//span[@id='domain-expiry']/a")
	    WebElement renewLink;
	 
	//Initializing Page Objects
		public DMZDomainDetailsPage(){
	        PageFactory.initElements(driver, this);
	    }
		
		public DMZBillingPage cickRenewLink()
		{
			renewLink.click();
			return new DMZBillingPage();
		}
}
