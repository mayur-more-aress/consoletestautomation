package com.domainzwebsite.pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;


public class DMZHostingAndExtrasPage extends TestBase{

	//Objects         
    @FindBy(how=How.XPATH, using = "//div[@class='next']/form/input")
    WebElement continueButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='domainAdd']/form/input[2]")
    WebElement addHostingButton;
    
    @FindBy(how=How.XPATH, using = "//div[@class='domainAdd']/form/input[3]")
    WebElement addExtrasButton;
    
    @FindBy(how=How.XPATH, using = "//*[@id='O365-EESEN-QTY']/div[2]/form/div/div[1]")
    WebElement o365EmailEssentials;
           
    @FindBy(how=How.XPATH, using = "//*[@id='O365-BESSEN-QTY']/div[2]/form/div/div[1]")
    WebElement o365BusinessEssentials;
    
    @FindBy(how=How.XPATH, using = "//*[@id='O365-BPREM-QTY']/div[2]/form/div/div[1]")
    WebElement o365BusinessPremium;
    
    @FindBy(how=How.XPATH, using = "//*[@id='O365-BUSINESS']/div[2]/form/div/div[1]")
    WebElement o365Business;
    
    @FindBy(how=How.NAME, using = "showHosting")
    WebElement hostingButton;
    
    //Initializing Page Objects
    public DMZHostingAndExtrasPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public DMZAddHostingPage clickAddHostingButton(){
    	System.out.println("clicking add hosting button");
    	if(addHostingButton.isDisplayed()||addHostingButton.isEnabled()) {
    		addHostingButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new DMZAddHostingPage();
    }
    
    public DMZAddExtrasPage clickAddExtrasButton(){
    	System.out.println("clicking add extras button");
    	if(addExtrasButton.isDisplayed()||addExtrasButton.isEnabled()) {
    		addExtrasButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new DMZAddExtrasPage();
    }
    
    public DMZAccountContactPage clickContinueButton(){
    	System.out.println("clicking continue button");
    	if(continueButton.isDisplayed()||continueButton.isEnabled()) {
    		continueButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new DMZAccountContactPage();
    }
    
    public DMZRegistrantContactPage clickContinueButtonReturningCustomer(){
    	System.out.println("clicking continue button");
    	if(continueButton.isDisplayed()||continueButton.isEnabled()) {
    		continueButton.click();
    	}
		else {
			System.out.println("element not found");
		}
    	return new DMZRegistrantContactPage();
    }
        
    public void clickHostingButton(){
    	System.out.println("clicking email hosting radio button");
    	if(hostingButton.isDisplayed()||hostingButton.isEnabled()) {
    		hostingButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    }
    
}
