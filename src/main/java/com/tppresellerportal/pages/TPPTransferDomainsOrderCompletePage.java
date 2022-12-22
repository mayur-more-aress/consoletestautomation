package com.tppresellerportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class TPPTransferDomainsOrderCompletePage extends TestBase {

	//Objects
	@FindBy(how = How.XPATH, using = "//*[@id='content']/table/tbody/tr[2]/td[2]")
	WebElement getworkflowId;
	
	@FindBy(how = How.XPATH, using = "//*[@id='content']/table/tbody/tr[2]/td[1]")
	WebElement getDomainNameWhichIsTransferred;
	
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'There is already an outstanding')]")
	WebElement verifyErrorMessage;
	
	//Initializing Page Objects
	public TPPTransferDomainsOrderCompletePage(){
		PageFactory.initElements(driver, this);
	}
	
	//Methods
	public String getDomainNameWhichIsTransferred(){
		return getDomainNameWhichIsTransferred.getText();
	}
	
	public String getWorkflowIdOfTransferredDomain(){
		return getworkflowId.getText();
	}
	
	public String verifyErrorMessageForAlreadyTransferredDomain(){
		return verifyErrorMessage.getText();
	}
}
