package com.tppresellerportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class TPPRenewDomainsPage extends TestBase {

	// Objects
	@FindBy(how = How.XPATH, using = "//div/input[@name='domain.domain']")
	WebElement searchDomainNameOnRenewDomainsPage;
	
	@FindBy(how = How.XPATH, using = "//div/input[@name='browse']")
	WebElement clickOnSearchButton;
	
	@FindBy(how = How.XPATH, using = "//*[@name='domainIds']")
	WebElement selectDomainCheckbox;
	
	@FindBy(how = How.XPATH, using = "(//*[@name='domainIds']/following::td/a)[1]")
	WebElement getDomainName;
	
	@FindBy(how = How.NAME, using = "addToSelection")
	WebElement addDomainsToCart;

	// Initializing Page Objects
	public TPPRenewDomainsPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void searchDomainNameToBeRenewed(String strDomainName) throws InterruptedException{
		Thread.sleep(3000);
		searchDomainNameOnRenewDomainsPage.sendKeys(strDomainName);
	}
	
	public void clickOnSearchButton() throws InterruptedException{
		clickOnSearchButton.click();
		Thread.sleep(2000);
	}
	
	public void selectDomainNameCheckbox() throws InterruptedException{
		Thread.sleep(2000);
		selectDomainCheckbox.click();
	}
	
	public String getDomainName(){
		return getDomainName.getText();
	}
	
	public TPPRenewDomainsCartPage clickOnAddDomainsToList(){
		addDomainsToCart.click();
		return new TPPRenewDomainsCartPage();
	}
}
