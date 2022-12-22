package com.tppcustomerportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class TPPAccountContactPage extends TestBase{

	//Objects         
    @FindBy(how=How.NAME, using = "contact.organisation")
    @CacheLookup
    WebElement organisation;
    
    @FindBy(how=How.NAME, using = "contact.firstName")
    @CacheLookup
    WebElement firstName;
    
    @FindBy(how=How.NAME, using = "contact.lastName")
    @CacheLookup
    WebElement lastName;
    
    @FindBy(how=How.NAME, using = "contact.address.address1")
    WebElement address;
    
    @FindBy(how=How.NAME, using = "contact.address.city")
    WebElement city;
    
    @FindBy(how=How.NAME, using = "contact.address.country")
    WebElement country;
    
    @FindBy(how=How.NAME, using = "contact.address.state")
    WebElement state;
    
    @FindBy(how=How.NAME, using = "contact.address.postcode")
    WebElement postcode;
    
    @FindBy(how=How.NAME, using = "contact.phone")
    WebElement phoneNumber;
    
    @FindBy(how=How.NAME, using = "contact.email")
    WebElement email;
    
    @FindBy(how=How.NAME, using = "contact.emailConfirm")
    WebElement emailConfirmation;
    
    @FindBy(how=How.NAME, using = "saveContact")
    WebElement continueButton;
    
    @FindBy(how=How.NAME, using = "username")
    WebElement userName;
    
    @FindBy(how=How.NAME, using = "password")
    WebElement password;
    
    @FindBy(how=How.NAME, using = "login")
    WebElement loginButton;
    
    
    //Initializing Page Objects
    public TPPAccountContactPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public void setCustomerOrganisation(String organisationname){
    	organisation.sendKeys(organisationname);
    }
    
    public void setCustomerFullName(String firstname, String lastname){
    	firstName.sendKeys(firstname);
    	lastName.sendKeys(firstname);
    }
    
    public void setCustomerFullAddress(String addressname, String cityname, String countryname, String statename, String postcodename ){
    	address.sendKeys(addressname);
    	city.sendKeys(cityname);
    	country.sendKeys(countryname);
    	if (countryname.equals("AUSTRALIA")) {
    		driver.findElement(By.xpath("//tr[@class='au-state']/td[2]/input")).sendKeys(statename);
    	}
    	else if (countryname.equals("UNITED STATES")) {
    		driver.findElement(By.xpath("//tr[@class='us-state']/td[2]/input")).sendKeys(statename);	
    	}
    	else {
    		driver.findElement(By.xpath("//tr[@class='other-state']/td[2]/input")).sendKeys(statename);
    	}
    	postcode.sendKeys(postcodename);
    }
    
    public void setCustomerFullContacts(String phonenumber, String emailaddress, String emailaddressconfirmation){
    	phoneNumber.sendKeys(phonenumber);
    	email.sendKeys(emailaddress);
    	emailConfirmation.sendKeys(emailaddressconfirmation);
    }
    
    public void setCustomerDefaultInformation(){
    	organisation.sendKeys("Test Console Regression");
    	firstName.sendKeys("QA");
    	lastName.sendKeys("Team");
    	address.sendKeys("505 Lt Collins Street");
    	city.sendKeys("Melbourne");
    	country.sendKeys("AUSTRALIA");
    	state.sendKeys("VIC");
    	postcode.sendKeys("0626");
    	phoneNumber.sendKeys("064411111111");
    	email.sendKeys("_qa-development@arq.group");
    	emailConfirmation.sendKeys("_qa-development@arq.group");
    }
   
    public void setReturningCustomerContacts(String customeraccountreference, String customerpassword){
    	userName.sendKeys(customeraccountreference);
    	password.sendKeys(customerpassword);
    }
    
   public TPPRegistrantContactPage clickContinueButton(){
    	System.out.println("clicking continue button");
    	if(continueButton.isDisplayed()||continueButton.isEnabled()) {
    		continueButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new TPPRegistrantContactPage();
    }
   
   public TPPRegistrantContactPage clickLoginButton(){
	   System.out.println("clicking login button");
   		if(loginButton.isDisplayed()||loginButton.isEnabled()) {
   			loginButton.click();
   		}
   		else {
			System.out.println("element not found");
		}
   		return new TPPRegistrantContactPage();
   }
	
}
