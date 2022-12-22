package com.domainzwebsite.pages;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class DMZEligibilityDetailsPage extends TestBase {

	//Objects 
	@FindBy(how=How.ID, using = "comAuOwner-btn-by-number")
	WebElement businessNumberButton;
	
	@FindBy(how=How.ID, using = "comAuOwner-btn-by-name")
	WebElement businessNameButton;
	
	@FindBy(how=How.XPATH, using = "//*[@id='eligibilityForm']/form/div[1]/div/div/table/tbody/tr[1]/td/table/tbody/tr/td[1]/input")
	WebElement certifyDomainHasCloseAndSubstantialConnection;
	
	@FindBy(how=How.XPATH, using = "//*[@id='eligibilityForm']/form/div[1]/div/div/table/tbody/tr[2]/td/table/tbody/tr/td[1]/input")
	WebElement agreeTermsAndConditions;
	
	@FindBy(how=How.XPATH, using = "//*[@id='eligibilityForm']/form/div[2]/input")
	WebElement continueButton;
	
	@FindBy(how=How.NAME, using = "comAuOwner.registrantidtype")
	WebElement eligibilityIdType;
	
	@FindBy(how=How.NAME, using = "comAuOwner.registrantidnumber")
	WebElement eligibilityIdNumber;
	
	@FindBy(how=How.NAME, using = "comAuOwner.registrantname")
	WebElement companyName;
	
	@FindBy(how=How.NAME, using = "comAuOwner.eligibilitytype")
	WebElement eligibilityType;


    //Initializing Page Objects
    public DMZEligibilityDetailsPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods    
    public void clickBusinessNumberButton(){
    	
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", businessNumberButton);
    	businessNumberButton.click();
    	    	
    }
    
    public void clickBusinessNameButton(){
    	
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", businessNameButton);
    	businessNameButton.click();
    	    	
    }
    
    public void setEligibilityDetails(String eligibilityidtype, String eligibilityidnumber, String companyname, String eligibilitytype){
    	
    	eligibilityIdType.sendKeys(eligibilityidtype);
    	eligibilityIdNumber.sendKeys(eligibilityidnumber);
    	companyName.sendKeys(companyname);
    	eligibilityType.sendKeys(eligibilitytype);
    }
    
    public void tickCertifyDomainHasCloseAndSubstantialConnection() throws InterruptedException{
       	System.out.println("tick certify domain has close and substantial connection");
       	Thread.sleep(2000);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", certifyDomainHasCloseAndSubstantialConnection);
       	if(certifyDomainHasCloseAndSubstantialConnection.isDisplayed()||certifyDomainHasCloseAndSubstantialConnection.isEnabled()) {
       		certifyDomainHasCloseAndSubstantialConnection.click();
       	}
    	else {
    		System.out.println("element not found");
    	}
    }
    
    public void tickTermsAndConditions(){
       	System.out.println("tick agree to terms and conditions");
       	if(agreeTermsAndConditions.isDisplayed()||agreeTermsAndConditions.isEnabled()) {
       		agreeTermsAndConditions.click();
       	}
    	else {
    		System.out.println("element not found");
    	}
    }
    
    public DMZBillingPage clickContinueButton(){
    	System.out.println("clicking continue button");
    	if(continueButton.isDisplayed()||continueButton.isEnabled()) {
    		continueButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new DMZBillingPage();
    }
    

	
}
