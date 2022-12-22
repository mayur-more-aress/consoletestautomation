package com.rrpproxypage.java;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.base.TestBase;

public class RRPIncomingTransfersPage extends TestBase{

	//Objects
	@FindBy(how = How.XPATH, using = "//*[text()='Search']/preceding::input[3]")
	WebElement enterDomainName;
	
	@FindBy(how = How.XPATH, using = "//*[text()='Search']")
	WebElement clickOnSearchField;
	
	@FindBy(how = How.XPATH, using = "//td/div/a[contains(@class,'main')]")
	WebElement getDomainName;
			
	//Initializing Page Objects
	public RRPIncomingTransfersPage(){
		PageFactory.initElements(driver, this);
	}
			
	//Methods
	public void enterDomainNameInSearchField(String strdomainname){
		enterDomainName.sendKeys(strdomainname);
	}
	
	public void clickOnSearchButton(){
		clickOnSearchField.click();
	}
	
	public String getDomainNameOfTransferIntiated(){
		return getDomainName.getText();
	}
}
