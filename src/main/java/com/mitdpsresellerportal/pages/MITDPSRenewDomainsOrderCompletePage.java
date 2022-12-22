package com.mitdpsresellerportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class MITDPSRenewDomainsOrderCompletePage extends TestBase {

	@FindBy(how = How.XPATH, using = "//*[@class='odd processed']/td[1]")
	WebElement getRenewedDomainName;
	
	@FindBy(how = How.XPATH, using = "//*[@class='orderId']")
	WebElement getWorkflowIdOfDomain;
	
	@FindBy(how = How.XPATH, using = "//*[@class='odd processed']/td[1]/following::span[1]")
	WebElement getErrorMessageFromOrderCompletePage;
	
	// Initializing Page Objects
	public MITDPSRenewDomainsOrderCompletePage() {
		PageFactory.initElements(driver, this);
	}
	
	public String getRenewedDomainName(){
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOf(getRenewedDomainName));
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
