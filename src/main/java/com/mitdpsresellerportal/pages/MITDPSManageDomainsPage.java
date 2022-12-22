package com.mitdpsresellerportal.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class MITDPSManageDomainsPage extends TestBase {

	// Initializing Page Objects
	public MITDPSManageDomainsPage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public String verifyManageDomainsPageisDisplayed() throws InterruptedException
	{
		Thread.sleep(4000);
		String verManageDomains=driver.findElement(By.xpath("//h1[contains(text(),'Manage Domains')]")).getText();
		return verManageDomains;
	}
}
