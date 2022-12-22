package com.consolesmui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CSMUITabPage extends TestBase {

	// Objects
	@FindBy(how = How.CSS, using = "a[href*=\"dashboard\"]")
	WebElement dashboardTab;

	@FindBy(how = How.ID, using = "domainname-tab-link")
	WebElement domainNameTab;

	@FindBy(how = How.ID, using = "zonemanager-tab-link")
	WebElement zoneManagerTab;

	@FindBy(how = How.ID, using = "delegate-domain-tab-link")
	WebElement delegateDomainTab;

	@FindBy(how = How.ID, using = "domain-redirector-tab-link")
	WebElement delegateRedirectorTab;

	@FindBy(how = How.ID, using = "o365-tab-link")
	WebElement office365Tab;

	// Initializing Page Objects
	public CSMUITabPage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public CSMUIDashboardPage clickDashoardTab() {

		System.out.println("Clicking Dashboard tab");
		if (dashboardTab.isDisplayed() || dashboardTab.isEnabled()) {
			dashboardTab.click();
		} else {
			System.out.println("element not found");
		}
		return new CSMUIDashboardPage();
	}

	public CSMUIDomainNamePage clickDomainNameTab() throws InterruptedException {

		Thread.sleep(5000);
		System.out.println("Clicking Domain Name tab");
		if (domainNameTab.isDisplayed() || domainNameTab.isEnabled()) {
			domainNameTab.click();
		} else {
			System.out.println("element not found");
		}
		return new CSMUIDomainNamePage();
	}

	public CSMUIZoneManagerPage clickZoneManagerTab() {

		System.out.println("Clicking Zone Manager tab");
		if (zoneManagerTab.isDisplayed() || zoneManagerTab.isEnabled()) {
			zoneManagerTab.click();
		} else {
			System.out.println("element not found");
		}
		return new CSMUIZoneManagerPage();
	}

	public CSMUIDelegateDomainPage clickDelegateDomainTab() {

		System.out.println("Clicking Domain Delegation tab");
		if (delegateDomainTab.isDisplayed() || delegateDomainTab.isEnabled()) {
			delegateDomainTab.click();
		} else {
			System.out.println("element not found");
		}
		return new CSMUIDelegateDomainPage();
	}

	public CSMUIDelegateRedirectPage clickDelegateRedirectorTab() {

		System.out.println("Clicking Domain Redirector tab");
		if (delegateRedirectorTab.isDisplayed() || delegateRedirectorTab.isEnabled()) {
			delegateRedirectorTab.click();
		} else {
			System.out.println("element not found");
		}
		return new CSMUIDelegateRedirectPage();
	}

	public CSMUIManageO365MainPage clickOffice365Tab() throws InterruptedException {

		System.out.println("Clicking Office 365 tab");
		if (office365Tab.isDisplayed() || office365Tab.isEnabled()) {
			office365Tab.click();
			Thread.sleep(5000);
			driver.switchTo().frame(driver.findElement(By.name("o365_frame")));
		} else {
			System.out.println("element not found");
		}
		return new CSMUIManageO365MainPage();
	}
	
	public String verifyDashboardPageisDisplayed() throws InterruptedException
	{
		Thread.sleep(10000);
		String verDashboard=driver.findElement(By.xpath("//h1[contains(text(),'Dashboard')]")).getText();
		return verDashboard;
	
	}

}
