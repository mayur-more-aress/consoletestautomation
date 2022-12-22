package com.tppresellerportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class TPPRenewDomainsOrderCompletePage extends TestBase {

	@FindBy(how = How.XPATH, using = "//*[@class='odd processed']/td[1]")
	WebElement getRenewedDomainName;
	
	@FindBy(how = How.XPATH, using = "//*[@class='orderId']")
	WebElement getWorkflowIdOfDomain;
	
	@FindBy(how = How.XPATH, using = "//*[@class='odd processed']/td[1]/following::span[1]")
	WebElement getErrorMessageFromOrderCompletePage;
	
	// Initializing Page Objects
	public TPPRenewDomainsOrderCompletePage() {
		PageFactory.initElements(driver, this);
	}
	
	public String getRenewedDomainName() throws InterruptedException{
		Thread.sleep(10000);
		return getRenewedDomainName.getText();
	}
	
	public String getWorkflowIdOfRenewedDomain(){
		WebDriverWait wait = new WebDriverWait(driver,50);
		wait.until(ExpectedConditions.visibilityOf(getWorkflowIdOfDomain));
		return getWorkflowIdOfDomain.getText();
	}
	
	public String getErrorMessageFromOrderCompletePage(){
		WebDriverWait wait = new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.visibilityOf(getErrorMessageFromOrderCompletePage));
		return getErrorMessageFromOrderCompletePage.getText();
	}
}
