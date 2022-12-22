package com.tppresellerportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class TPPTabPage extends TestBase {

	// Objects
	@FindBy(how = How.XPATH, using = "//*[@id=\"menu\"]/ul/li[1]/h3")
	WebElement domainsTab;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"menu\"]/ul/li[2]/h3")
	WebElement productsTab;
	
	/* Domains Tab List */
	@FindBy(how = How.XPATH, using = "//*[@id=\"menu\"]/ul/li[1]/ul/li[1]/a")
	WebElement registerLink;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"menu\"]/ul/li[1]/ul/li[2]/a")
	WebElement bulkRegisterLink;
	
	@FindBy (how = How.XPATH, using = "//*[@id='menu']/ul/li[1]/ul/li[3]")
	WebElement domainTransferLink;
	
	@FindBy (how = How.XPATH, using = "//*[@id='menu']/ul/li[1]/ul/li[5]")
	WebElement domainRenewLink;

	@FindBy (how = How.XPATH, using = "//*[@id='menu']/ul/li[1]/ul/li[6]/a")
	WebElement auRegistrantNameChangeLink;
	
	// Initializing Page Objects
	public TPPTabPage() {
		PageFactory.initElements(driver, this);
	}

	
	// Methods
	public void clickDomainsTab() throws InterruptedException {

		
		new WebDriverWait(driver, 40).until(ExpectedConditions.urlContains("execute/home"));
		System.out.println("clicking domains tab");
		if (domainsTab.isDisplayed() || domainsTab.isEnabled()) {
			domainsTab.click();
		} else {
			System.out.println("element not found");
		}
	
	}
	
	public TPPRegisterADomainPage clickRegisterLink() throws InterruptedException {

		//Thread.sleep(3000);
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".first .navContent"))));
		System.out.println("clicking register link");
		if (registerLink.isDisplayed() || registerLink.isEnabled()) {
			registerLink.click();
		} else {
			System.out.println("element not found");
		}
		
		return new TPPRegisterADomainPage();
	
	}
	
	public TPPBulkRegisterPage clickBulkRegisterLink() throws InterruptedException {

		Thread.sleep(3000);
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath("//div[@id='menu']/ul/li[1]/h3"))).build().perform();
				
		System.out.println("clicking bulk register link");
		if (bulkRegisterLink.isDisplayed() || bulkRegisterLink.isEnabled()) {
			bulkRegisterLink.click();
		} else {
			System.out.println("element not found");
		}
		Thread.sleep(1000);
		return new TPPBulkRegisterPage();
	
	}

	public TPPTransferDomainsPage clickDomainTransferLink() throws InterruptedException {
		
		Thread.sleep(3000);
		System.out.println("Clicking domain transfer link");
		if (domainTransferLink.isDisplayed() || domainTransferLink.isEnabled()) {
			domainTransferLink.click();
		} else {
			System.out.println("element not found");
		}
		
		return new TPPTransferDomainsPage();
	}
	
	public TPPRenewDomainsPage clickDomainRenewLink() throws InterruptedException {
		Thread.sleep(3000);
		System.out.println("clicking register link");
		if (domainRenewLink.isDisplayed() || domainRenewLink.isEnabled()) {
			domainRenewLink.click();
		} else {
			System.out.println("element not found");
		}
		
		return new TPPRenewDomainsPage();
	}
	
	public TPPRegistrantNameChangePage clickAuNameChangeLink() throws InterruptedException {
		Thread.sleep(3000);
		System.out.println("clicking .au registrant name change link");
		if (auRegistrantNameChangeLink.isDisplayed() || auRegistrantNameChangeLink.isEnabled()) {
			auRegistrantNameChangeLink.click();
		} else {
			System.out.println("element not found");
		}
		
		return new TPPRegistrantNameChangePage();
	}

}
