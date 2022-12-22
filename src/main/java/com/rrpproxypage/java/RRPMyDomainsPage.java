package com.rrpproxypage.java;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class RRPMyDomainsPage extends TestBase{

	//Objects
	@FindBy(how = How.XPATH, using = "//*[text()='Search']/preceding::input[3]")
	WebElement enterDomainName;
	
	@FindBy(how = How.XPATH, using = "//*[text()='Search']")
	WebElement clickOnSearchField;
	
	@FindBy(how = How.XPATH, using = "//a[@class='main']")
	WebElement getRenewedDomainName;
	
	@FindBy(how = How.XPATH, using = "(//td/div[contains(@class,'helper')]/span)[2]")
	WebElement getExpiryDateOfRenewedDomain;

	//Initializing Page Objects
	public RRPMyDomainsPage(){
		PageFactory.initElements(driver, this);
	}
	
	//Methods
	public void enterADomainInSearchField(String strDomainName){
		enterDomainName.sendKeys(strDomainName);
	}
	
	public void clickOnSearchButton(){
		clickOnSearchField.click();
	}
	
	public String getRenewedDomainName(){
		return getRenewedDomainName.getText();
	}
	
	public String getExpiryDateOfRenewedDomain(){
		return getExpiryDateOfRenewedDomain.getText();
	}
}
