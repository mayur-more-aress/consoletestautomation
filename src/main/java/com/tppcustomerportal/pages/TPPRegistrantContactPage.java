package com.tppcustomerportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class TPPRegistrantContactPage extends TestBase {

	//Objects             
    @FindBy(how=How.NAME, using = "saveRegistrant")
    WebElement continueButton;
    
    @FindBy(how=How.NAME, using = "contact.organisation")
    WebElement txtOrganisation;
    
    @FindBy(how=How.NAME, using = "contact.firstName")
    WebElement txtFirstName;
    
    @FindBy(how=How.NAME, using = "contact.lastName")
    WebElement txtLastName;
    
    @FindBy(how=How.NAME, using = "contact.address.address1")
    WebElement txtAddress;
    
    @FindBy(how=How.NAME, using = "contact.address.city")
    WebElement txtCity;
    
    @FindBy(how=How.NAME, using = "contact.address.postcode")
    WebElement txtPostalCode;
    
    @FindBy(how=How.NAME, using = "contact.phone")
    WebElement txtPhone;
    
    @FindBy(how=How.NAME, using = "contact.email")
    WebElement txtEmail;
    
    @FindBy(how=How.NAME, using = "contact.emailConfirm")
    WebElement txtEmailConfirmation;
    

    //Initializing Page Objects
    public TPPRegistrantContactPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods    
    public TPPBillingPage clickContinueButton(){
    	System.out.println("clicking continue button");
    	if(continueButton.isDisplayed()||continueButton.isEnabled()) {
    		continueButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new TPPBillingPage();
    }
    
    public TPPEligibilityDetailsPage clickContinueButtonForEligibilityDetails(){
    	System.out.println("clicking continue button for eligibility details");
    	if(continueButton.isDisplayed()||continueButton.isEnabled()) {
    		continueButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new TPPEligibilityDetailsPage();
    }
    
    public void clickDomainInformation(String domaininformation) throws InterruptedException {
    	
    	Thread.sleep(3000);
    	driver.findElement(By.xpath("//div[@class='instore']/div/div/form//div[1]/input[@value='"+ domaininformation + "']")).click();
    }
    
    public void enterEligibilityDetails(String organisation, String firstName, String lastName, String address, String city, String postalCode, String phone, String email) throws InterruptedException {
    	
    	Thread.sleep(2000);
    	if(txtOrganisation.isDisplayed()||txtOrganisation.isEnabled()) {
    		txtOrganisation.sendKeys(organisation);
    		}else {
    		System.out.println("Could not find element");
    	}
    	txtFirstName.sendKeys(firstName);
    	txtLastName.sendKeys(lastName);
    	txtAddress.sendKeys(address);
    	txtCity.sendKeys(city);
    	txtPostalCode.sendKeys(postalCode);
    	txtPhone.sendKeys(phone);
    	txtEmail.sendKeys(email);
    	txtEmailConfirmation.sendKeys(email);
    }	
}
