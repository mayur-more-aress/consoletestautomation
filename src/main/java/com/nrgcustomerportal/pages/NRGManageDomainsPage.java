package com.nrgcustomerportal.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class NRGManageDomainsPage extends TestBase {

	// Initializing Page Objects
	public NRGManageDomainsPage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public String verifyManageDomainsPageisDisplayed() throws InterruptedException
	{
		
		
		String verManageDomains=driver.findElement(By.xpath("//h1[contains(text(),'Overview')]")).getText();
		return verManageDomains;
	
	}
}
