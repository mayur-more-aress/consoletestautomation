package com.consolesalesdb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.base.TestBase;

public class CSAccountPage extends TestBase {

	// Objects
	@FindBy(how = How.XPATH, using = "//span[@class='x-tab-strip-text tab-icon-accounts']")
	WebElement tabAccounts;

	// Initializing Page Objects
	public CSAccountPage() {
		PageFactory.initElements(driver, this);
	}

	public CSAccountPage clickAccountTab() throws InterruptedException {
		System.out.println("Entering method");
		Thread.sleep(4000);
		
		driver.findElement(By.xpath("//span[@class='x-tab-strip-text tab-icon-accounts']")).click();
		return new CSAccountPage();
	}
}
