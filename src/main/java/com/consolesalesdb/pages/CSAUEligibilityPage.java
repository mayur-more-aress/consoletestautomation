package com.consolesalesdb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CSAUEligibilityPage extends TestBase {

	//Objects  
    @FindBy(how=How.ID, using = "eligibility-form-name-field-1")
    WebElement registrantName;
    
    @FindBy(how=How.ID, using = "eligibility-form-firstname-field-1")
    WebElement firstName;
    
    @FindBy(how=How.ID, using = "eligibility-form-lastname-field-1")
    WebElement lastName;
    
    @FindBy(how=How.ID, using = "eligibility-form-position-field-1")
    WebElement position;
    
    @FindBy(how=How.ID, using = "eligibility-form-address1-field-1")
    WebElement address1;
    
    @FindBy(how=How.ID, using = "eligibility-form-address2-field-1")
    WebElement address2;
    
    @FindBy(how=How.ID, using = "eeligibility-form-address3-field-1")
    WebElement address3;
    
    @FindBy(how=How.ID, using = "eligibility-form-email-field-1")
    WebElement email;
    
    @FindBy(how=How.ID, using = "eligibility-form-city-field-1")
    WebElement city;
    
    @FindBy(how=How.ID, using = "eligibility-form-state-combo-1")
    WebElement state;
    
    @FindBy(how=How.ID, using = "eligibility-form-postcode-field-1")
    WebElement postcode;
    
    @FindBy(how=How.ID, using = "eligibility-form-country-combo-1")
    WebElement country;
    
    @FindBy(how=How.ID, using = "eligibility-form-tel-field-1")
    WebElement telephoneNumber;
   
    @FindBy(how=How.ID, using = "eligibility-form-fax-field-1")
    WebElement faxNumber;
    
    @FindBy(how=How.ID, using = "eligibility-form-comments-field-1")
    WebElement comments;
    
    @FindBy(how=How.ID, using = "eligibility-au-number-field")
    WebElement registrantNumber;
    
    @FindBy(how=How.XPATH, using = "//table[@id='eligibility-form-update-button-1']/tbody/tr/td[2]/em/button")
    WebElement updateButton;
    
    
    
    @FindBy(how=How.XPATH, using = "//div[@id='x-form-el-eligibility-au-typeId-field']/div/img") 
    WebElement registrantTypeDropdownButton;
    
    //Initializing Page Objects
    public CSAUEligibilityPage() throws InterruptedException {
        PageFactory.initElements(driver, this);
    }

    //Methods
    public CSNrCRMPage setContactAndEligibilityDetails(String strRegistrantName, String strPhoneNumber, String strRegistrantType, String strRegistrantNumber) throws InterruptedException {
    
    	System.out.println("Method: setContactAndEligibilityDetails");
    	registrantName.clear();
		Thread.sleep(2000);
		registrantName.clear();
		registrantName.sendKeys(strRegistrantName);
		//Thread.sleep(2000);
		telephoneNumber.clear();
		telephoneNumber.sendKeys(strPhoneNumber);
		//Thread.sleep(2000);
    	//driver.findElement(By.xpath("//div[@id='x-form-el-eligibility-au-number-field']/input")).sendKeys(strRegistrantNumber);
		registrantNumber.clear();
		registrantNumber.sendKeys(strRegistrantNumber);
		//Thread.sleep(2000);
		registrantTypeDropdownButton.click();
		//Thread.sleep(2000);
    	driver.findElement(By.xpath("//div[@class='x-combo-list-inner']/*[contains(text(),'"+strRegistrantType+"')]")).click();    	
    	//Thread.sleep(5000);
    	Thread.sleep(1000);
    	updateButton.click();
    	//Thread.sleep(5000);
    	Thread.sleep(1000);
    	
		
		return new CSNrCRMPage();
    
    }
       
}
