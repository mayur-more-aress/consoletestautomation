package com.rrpproxypage.java;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.base.TestBase;

public class RRPDomainsPage extends TestBase {

	//Objects
	@FindBy(how = How.XPATH, using = "//*[@id='submenuDomains']/li[4]/a")
	WebElement clickOnPendingTransfers;
		
	//Initializing Page Objects
	public RRPDomainsPage(){
		PageFactory.initElements(driver, this);
	}
		
	//Methods
	public RRPIncomingTransfersPage clickOnPendingTransfers(){
		clickOnPendingTransfers.click();
		return new RRPIncomingTransfersPage();
	}
}
