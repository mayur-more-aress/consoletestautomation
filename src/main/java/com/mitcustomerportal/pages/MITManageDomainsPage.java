package com.mitcustomerportal.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class MITManageDomainsPage extends TestBase {

	// Initializing Page Objects
	public MITManageDomainsPage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public String verifyManageDomainsPageisDisplayed() throws InterruptedException
	{
		Thread.sleep(4000);
		String verManageDomains=driver.findElement(By.xpath("//h1[contains(text(),'Overview')]")).getText();
		return verManageDomains;
	}
}
