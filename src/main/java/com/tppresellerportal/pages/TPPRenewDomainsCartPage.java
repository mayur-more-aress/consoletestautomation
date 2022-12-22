package com.tppresellerportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class TPPRenewDomainsCartPage extends TestBase {

	@FindBy(how = How.XPATH, using = "//*[@id='select-form']/table/tbody/tr[2]/td[1]")
	WebElement getDomainName;
	
	@FindBy(how = How.XPATH, using = "//*[@id='select-form']/table/tbody/tr[2]/td[5]")
	WebElement getExpiryDate;

	@FindBy(how = How.NAME, using = "termsConditions")
	WebElement selectTermsAndConditions;

	@FindBy(how = How.NAME, using = "billing.id")
	WebElement selectPaymentMethod;

	@FindBy(how = How.XPATH, using = "//input[contains(@name,'renew')]")
	WebElement clickOnRenewDomain;

	// Initializing Page Objects
	public TPPRenewDomainsCartPage() {
		PageFactory.initElements(driver, this);
	}

	public String getDomainNameToBeRenewed(){
		return getDomainName.getText();
	}
	
	public String getExpiryDateOfDomain() throws InterruptedException{
		Thread.sleep(2000);
		return getExpiryDate.getText();
	}

	public void checkTermsAndConditions() throws InterruptedException{
		Thread.sleep(2000);
		selectTermsAndConditions.click();
	}

	public void selectPaymentMethod(String paymentMethod) throws InterruptedException{
		Thread.sleep(2000);
		selectPaymentMethod.sendKeys(paymentMethod);
	}

	public TPPRenewDomainsOrderCompletePage clickOnRenewDomain(){
		clickOnRenewDomain.click();
		return new TPPRenewDomainsOrderCompletePage();
	}
}
