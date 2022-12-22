package com.tppcustomerportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.base.TestBase;

public class TPPHeaderPage extends TestBase {

	// Objects

	@FindBy(how = How.XPATH, using = ".//*[@id='wrap']/div[2]/div[2]/div[1]/ul/li[2]/a/span")
	WebElement orderTab;

	@FindBy(how = How.LINK_TEXT, using = "all domains")
	WebElement allDomainsLink;

	// Initializing Page Objects
	public TPPHeaderPage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public TPPOrderPage clickOrderTab() throws InterruptedException {
		System.out.println("clicking order tab");
		if (orderTab.isDisplayed() || orderTab.isEnabled()) {
			orderTab.click();
		} else {
			System.out.println("element not found");
		}

		return new TPPOrderPage();

	}

	public TPPSummaryOfAllDomainsPage clickAllDomainsLink() {

		System.out.println("Click all domains link");
		allDomainsLink.click();
		
		return new TPPSummaryOfAllDomainsPage();

	}

}
