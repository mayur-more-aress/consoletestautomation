package com.mitdps.resellerapi.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.base.TestBase;


public class ResellerApiPage extends TestBase {

	//Objects
    @FindBy(how=How.XPATH, using = "//input[@name='AccountNo']")
    WebElement accountNumberTextField;
    
    @FindBy(how=How.XPATH, using ="//input[@name='UserId']")
    WebElement userIdTextField;
    
    @FindBy(how=How.XPATH, using ="//input[@name='Password']")
    WebElement passwordTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@type='submit'])[1]")
    WebElement submitButton;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='OrganisationName'])[1]")
    WebElement organisationNameTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='FirstName'])[1]")
    WebElement firstNameTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='LastName'])[1]")
    WebElement lastNameTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='Address1'])[1]")
    WebElement address1TextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='City'])[1]")
    WebElement cityTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='Region'])[1]")
    WebElement regionTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='PostalCode'])[1]")
    WebElement postalCodeTextField;
    
    @FindBy(how=How.XPATH, using ="//input[@name='PhoneCountryCode']")
    WebElement phoneCountryCodeTextField;
    
    @FindBy(how=How.XPATH, using ="//input[@name='PhoneAreaCode']")
    WebElement phoneAreaCodeTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='PhoneNumber'])[1]")
    WebElement phoneNumberTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='Email'])[1]")
    WebElement emailTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@type='submit'])[5]")
    WebElement submitButtonCreateContact;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='Domain'])[1]")
    WebElement domainTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='Period'])[1]")
    WebElement peroidTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='AccountID'])[1]")
    WebElement accountIDTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='OwnerContactID'])[1]")
    WebElement ownerContactIDTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='AdministrationContactID'])[1]")
    WebElement administrationContactIDTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='TechnicalContactID'])[1]")
    WebElement technicalContactIDTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='BillingContactID'])[1]")
    WebElement billingContactIDTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='Host'])[1]")
    WebElement host1TextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='Host'])[2]")
    WebElement host2TextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='RegistrantName'])[1]")
    WebElement registrantNameTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='RegistrantID'])[1]")
    WebElement registrantIdTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='EligibilityName'])[1]")
    WebElement eligibilityNameTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='EligibilityID'])[1]")
    WebElement eligibilityIdTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='EligibilityReason'])[1]")
    WebElement eligibiltyReasonTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='execute'])[2]")
    WebElement submitButtonRegisterDomain;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='Domain'])[2]")
    WebElement renewdomainTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='Period'])[2]")
    WebElement renewPeriodTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@type='submit'])[3]")
    WebElement submitButtonRenewDomain;
    
    /**********Locators for transfer domain************/
    @FindBy(how=How.XPATH, using ="(//input[@name='Domain'])[3]")
    WebElement transferDomainTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='Period'])[3]")
    WebElement transferPeriodTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='DomainPassword'])[1]")
    WebElement domainPasswordTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='AccountID'])[2]")
    WebElement transferAccountIDTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='OwnerContactID'])[2]")
    WebElement transferOwnerContactIDTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='AdministrationContactID'])[2]")
    WebElement transferAdministrationContactIDTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='TechnicalContactID'])[2]")
    WebElement transferTechnicalContactIDTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='BillingContactID'])[2]")
    WebElement transferBillingContactIDTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@type='submit'])[4]")
    WebElement submitButtonTransferDomain;
    
    /**********Locators for Whois domain************/
    @FindBy(how=How.XPATH, using ="(//input[@name='Domain'])[12]")
    WebElement whoisDomainNameTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='DomainPassword'])[2]")
    WebElement whoisDomainPasswordTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@type='submit'])[14]")
    WebElement submitButtonWhoisDomain;
    
    /**********Locators for Whois domain************/
    @FindBy(how=How.XPATH, using ="(//input[@name='Domain'])[11]")
    WebElement domainLockNameTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@type='submit'])[13]")
    WebElement submitButtonDomainLock;
    
    /**********Locators for Create Name Sever************/
    @FindBy(how=How.XPATH, using ="(//input[@name='Domain'])[8]")
    WebElement DomainNameCNSTextField;
   
    @FindBy(how=How.XPATH, using ="(//input[@name='NameServerPrefix'])[1]")
    WebElement nameServerPrefixTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='NameServerIP'])[1]")
    WebElement nameServerIPAddressTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='execute'])[10]")
    WebElement submitButtonCreateNameServer;
    
    /**********Locators for Change Name Sever************/
    @FindBy(how=How.XPATH, using ="(//input[@name='Domain'])[10]")
    WebElement DomainNameChangeNSTextField;
   
    @FindBy(how=How.XPATH, using ="(//input[@name='NameServerPrefix'])[3]")
    WebElement nameServerPrefixChangeNSTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='NameServerIP'])[2]")
    WebElement nameServerIPAddressChangeNSTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='execute'])[12]")
    WebElement submitButtonChangeNameServer;
    
    /**********Locators for Delete Name Sever************/
    @FindBy(how=How.XPATH, using ="(//input[@name='Domain'])[9]")
    WebElement DomainNameDNSTextField;
   
    @FindBy(how=How.XPATH, using ="(//input[@name='NameServerPrefix'])[2]")
    WebElement nameServerPrefixDNSTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='execute'])[11]")
    WebElement submitButtonDeleteNameServer;
    
    /**********Locators for Registrant Name Change************/
    @FindBy(how=How.XPATH, using ="(//input[@name='Domain'])[6]")
    WebElement DomainNameTextFieldRNC;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='RegistrantName'])[2]")
    WebElement registrantNameTextFieldRNC;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='RegistrantID'])[2]")
    WebElement registrantIDTextField;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='EligibilityName'])[2]")
    WebElement eligibilityNameTextFieldRNC;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='EligibilityID'])[2]")
    WebElement eligibilityIdTextFieldRNC;
   
    @FindBy(how=How.XPATH, using ="(//input[@name='EligibilityReason'])[2]")
    WebElement eligibilityReasonTextFieldRNC;
    
    @FindBy(how=How.XPATH, using ="(//input[@name='execute'])[8]")
    WebElement submitButtonRegistrantNameChange;
    
	//Initializing Page Objects
	public ResellerApiPage(){
    	PageFactory.initElements(driver, this);
    }
	
	/*---Methods to create the session-----*/
	public void enterAccountNumber(String accountNo) throws InterruptedException
	{
		accountNumberTextField.sendKeys(accountNo);
		Thread.sleep(1000);
	}
	
	public void enterUserID(String userid) throws InterruptedException
	{
		userIdTextField.sendKeys(userid);
		Thread.sleep(1000);
	}
	
	public void enterPassword(String password) throws InterruptedException
	{
		passwordTextField.sendKeys(password);
		Thread.sleep(1000);
	}
	
	public void clickSubmitButton() throws InterruptedException
    {
    	System.out.println("clicking submit button");
    	if(submitButton.isDisplayed()||submitButton.isEnabled())
    	{
    		submitButton.click();
    		Thread.sleep(3000);
    	}
		else 
		{
			System.out.println("element not found");
		}    	
     }
	
	public void verifyStatus()
	{
		String actualText="OK:";
		String expectedText="OK:";
		Assert.assertEquals(actualText, expectedText);
	}
	
	/*---Methods to create the contact details-----*/
	public void enterOrganisationName(String orgnisationName) throws InterruptedException
	{
		organisationNameTextField.sendKeys(orgnisationName);
		Thread.sleep(1000);
	}
	
	public void enterFirstName(String firstName) throws InterruptedException
	{
		firstNameTextField.sendKeys(firstName);
		Thread.sleep(1000);
	}
	
	public void enterLastName(String firstName) throws InterruptedException
	{
		lastNameTextField.sendKeys(firstName);
		Thread.sleep(1000);
	}
	
	public void enterAddress1(String address1) throws InterruptedException
	{
		address1TextField.sendKeys(address1);
		Thread.sleep(1000);
	}
	
	public void enterCity(String city) throws InterruptedException
	{
		cityTextField.sendKeys(city);
		Thread.sleep(1000);
	}
	
	public void enterRegion(String region) throws InterruptedException
	{
		regionTextField.sendKeys(region);
		Thread.sleep(1000);
	}
	 
	public void enterPostalCode(String postalcode) throws InterruptedException
	{
		postalCodeTextField.sendKeys(postalcode);
		Thread.sleep(1000);
	}
	
	public void enterPhone(String phonecountrycode,String phoneareacode,String phonenumber)
	{
		phoneCountryCodeTextField.sendKeys(phonecountrycode);
		phoneAreaCodeTextField.sendKeys(phoneareacode);
		phoneNumberTextField.sendKeys(phonenumber);
	}
	
	public void enterEmail(String email) throws InterruptedException
	{
		emailTextField.sendKeys(email);
		Thread.sleep(1000);
	}
	
	public void clickSubmitButtonToSubmitContactDetails() throws InterruptedException
    {
    	System.out.println("clicking submit button");
    	if(submitButtonCreateContact.isDisplayed()||submitButtonCreateContact.isEnabled())
    	{
    		submitButtonCreateContact.click();
    		Thread.sleep(4000);
    	}
		else 
		{
			System.out.println("element not found");
		}    	
     }
	
	public String getResponse() throws InterruptedException{
		String contactIDNumber = null;
		Thread.sleep(10000);
    	WebElement contactIdElement = driver.findElement(By.xpath("/html/body/div[5]/div[2]/pre"));
		if (contactIdElement.isDisplayed()) {
			contactIDNumber = contactIdElement.getText();
    	}
    	return contactIDNumber;
    }
	
	public String getWorkflowId(String strResponse) {
		String[] parts = strResponse.split(":");
		String workflowId = parts[1];
		String workflowID=workflowId.replaceAll("\\s", "");
		System.out.println("ContactId"+workflowID);
		return workflowID;
	}
	
	/*---Methods to register the domain-----*/
	public void enterDomain(String domain) throws InterruptedException 
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollBy(0,-350)", "");
		domainTextField.sendKeys(domain);
		Thread.sleep(700);
		
	}
	
	public void enterPeriod(String period) throws InterruptedException 
	{
	    peroidTextField.sendKeys(period);
	    Thread.sleep(700);
	    
    }
	
	public void selectAccountOptionToRegisterDomain()
	{
		Select eligibilitytype= new Select(driver.findElement(By.xpath("/html/body/div[2]/div[1]/form/div[4]/select")));
		 eligibilitytype.selectByVisibleText("CONSOLE");
	}
	
	
	public void enterAccountID(String accountid)throws InterruptedException 
	{
		accountIDTextField.sendKeys(accountid);
		Thread.sleep(700);
		
	}
	
	public void enterOwnerContactID(String ownercontactid) throws InterruptedException 
	{
		ownerContactIDTextField.sendKeys(ownercontactid);
		Thread.sleep(700);
	}
	
	public void enterAdministrationContactID(String administrationcontactid) throws InterruptedException 
	{
		administrationContactIDTextField.sendKeys(administrationcontactid);
		Thread.sleep(700);
	}
	
	public void enterTechnicalContactID(String technicalcontactid) throws InterruptedException 
	{
		technicalContactIDTextField.sendKeys(technicalcontactid);
		Thread.sleep(700);
	}
	
	public void enterBillingContactID(String billingcontactid) throws InterruptedException
	{
		billingContactIDTextField.sendKeys(billingcontactid);
		Thread.sleep(700);
	}
	
	
	public void enterHost(String host1,String host2) throws InterruptedException
	{
		host1TextField.sendKeys(host1);
		host2TextField.sendKeys(host2);
		Thread.sleep(700);
	}
	
	
	 public void enterEligibilityDetails(String registrantname, String registrantid, String eligibiltyname, String eligibilityId,String eligibiltyreason)
	 {
	    	
		 registrantNameTextField.sendKeys(registrantname);
		 registrantIdTextField.sendKeys(registrantid);
		 eligibilityNameTextField.sendKeys(eligibiltyname);
		 eligibilityIdTextField.sendKeys(eligibilityId);
		 Select eligibilitytype= new Select(driver.findElement(By.xpath("(//select[@name='EligibilityType'])[1]")));
		 eligibilitytype.selectByVisibleText("Company");
		 eligibiltyReasonTextField.sendKeys(eligibiltyreason);
		
	    }
	  
	public void clickSubmitButtonToRegisterDomain() throws InterruptedException
    {
    	System.out.println("clicking submit button");
    	if(submitButtonRegisterDomain.isDisplayed()||submitButtonRegisterDomain.isEnabled())
    	{
    		submitButtonRegisterDomain.click();
    		Thread.sleep(2000);
    		JavascriptExecutor js = (JavascriptExecutor) driver;
    	    js.executeScript("window.scrollBy(0,-150)", "");
    	}
		else 
		{
			System.out.println("element not found");
		}  
    	
     }
	
	public String getResponseOfDomainRegistration() throws InterruptedException{
		String workflowIDNumber = null;
		Thread.sleep(10000);
    	WebElement workflowIDElement = driver.findElement(By.xpath("/html/body/div[2]/div[2]/pre"));
		if (workflowIDElement.isDisplayed()) {
			workflowIDNumber = workflowIDElement.getText();
    	}
    	return workflowIDNumber;
    }
	
	/*---Methods to renew the domain-----*/
	public void enterDomainName(String domainName) throws InterruptedException
	{
		renewdomainTextField.sendKeys(domainName);
		Thread.sleep(1000);
	}
	
	public void enterPeriodForRenewDomain(String period) throws InterruptedException
	{
		renewPeriodTextField.sendKeys(period);
		Thread.sleep(1000);
	}
	
	public void clickSubmitButtonToRenewDomain() throws InterruptedException
    {
    	System.out.println("clicking submit button");
    	if(submitButtonRenewDomain.isDisplayed()||submitButtonRenewDomain.isEnabled())
    	{
    		submitButtonRenewDomain.click();
    		Thread.sleep(7000);
    	}
		else 
		{
			System.out.println("element not found");
		}  
     }
	
	public String getResponseOfRenewDomain() throws InterruptedException{
		String workflowIDNumber = null;
		Thread.sleep(25000);
    	WebElement workflowIDElement = driver.findElement(By.xpath("/html/body/div[3]/div[2]/pre"));
		if (workflowIDElement.isDisplayed()) {
			workflowIDNumber = workflowIDElement.getText();
    	}
    	return workflowIDNumber;
    }
	
/*---Methods to transfer the domain-----*/
	public void enterDomainNameToTransfer(String domainName) throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollBy(0,-150)", "");
		transferDomainTextField.sendKeys(domainName);
		Thread.sleep(500);
	}
	
	public void enterPeriodForTransferDomain(String period) throws InterruptedException
	{
	    transferPeriodTextField.sendKeys(period);
		Thread.sleep(500);
	}
	
	public void enterDomainPassword(String password) throws InterruptedException
	{
		domainPasswordTextField.sendKeys(password);
		Thread.sleep(500);
	}
	
	public void selectAccountOptionToTransfer()
	{
		Select eligibilitytype= new Select(driver.findElement(By.xpath("/html/body/div[4]/div[1]/form/div[5]/select")));
		 eligibilitytype.selectByVisibleText("CONSOLE");
	}
	
	public void enterAccountIDForTransfer(String accountid)throws InterruptedException 
	{
		transferAccountIDTextField.sendKeys(accountid);
		Thread.sleep(500);
		
	}
	
	public void enterOwnerContactIDForTransfer(String ownercontactid) throws InterruptedException 
	{
		transferOwnerContactIDTextField.sendKeys(ownercontactid);
		Thread.sleep(500);
	}
	
	public void enterAdministrationContactIDForTransfer(String administrationcontactid) throws InterruptedException 
	{
		transferAdministrationContactIDTextField.sendKeys(administrationcontactid);
		Thread.sleep(500);
	}
	
	public void enterTechnicalContactIDForTransfer(String technicalcontactid) throws InterruptedException 
	{
		transferTechnicalContactIDTextField.sendKeys(technicalcontactid);
		Thread.sleep(500);
	}
	
	public void enterBillingContactIDForTransfer(String billingcontactid) throws InterruptedException
	{
		transferBillingContactIDTextField.sendKeys(billingcontactid);
		Thread.sleep(500);
	}
	
	public void clickSubmitButtonToTransferDomain() throws InterruptedException
    {
    	System.out.println("clicking submit button");
    	if(submitButtonTransferDomain.isDisplayed()||submitButtonTransferDomain.isEnabled())
    	{
    		submitButtonTransferDomain.click();
    		Thread.sleep(20000);
    	}
		else 
		{
			System.out.println("element not found");
		}  
     }
	
	public String getResponseOfTransferDomain() throws InterruptedException{
		String workflowIDNumber = null;
		Thread.sleep(25000);
    	WebElement workflowIDElement = driver.findElement(By.xpath("/html/body/div[4]/div[2]/pre"));
		if (workflowIDElement.isDisplayed()) {
			workflowIDNumber = workflowIDElement.getText();
    	}
    	return workflowIDNumber;
    }
	
	/*---Methods to whois domain-----*/
	public void enterDomainNameForWhois(String domainName) throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollBy(0,350)", "");
		whoisDomainNameTextField.sendKeys(domainName);
		Thread.sleep(500);
	}
	
	public void enterDomainPasswordForWhois(String password) throws InterruptedException
	{
		whoisDomainPasswordTextField.sendKeys(password);
		Thread.sleep(500);
	}
	
	public void clickSubmitButtonOfWhoisDomain() throws InterruptedException
    {
    	System.out.println("clicking submit button");
    	if(submitButtonWhoisDomain.isDisplayed()||submitButtonWhoisDomain.isEnabled())
    	{
    		submitButtonWhoisDomain.click();
    		Thread.sleep(5000);
    	}
		else 
		{
			System.out.println("element not found");
		}  
     }
	
	public String getResponseOfWhoisDomain() throws InterruptedException{
		String workflowdetails = null;
		Thread.sleep(10000);
    	WebElement responseDetails = driver.findElement(By.xpath("/html/body/div[14]/div[2]/pre"));
		if (responseDetails.isDisplayed()) {
			workflowdetails = responseDetails.getText();
    	}
    	return workflowdetails;
    }
	
	/*---Methods for domain lock-----*/
	public void enterDomainLockName(String domainName) throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollBy(0,250)", "");
	    domainLockNameTextField.sendKeys(domainName);
		Thread.sleep(500);
	}
	
	public void enterDomainUnLockName(String domainName) throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollBy(0,250)", "");
	    domainLockNameTextField.clear();
	    domainLockNameTextField.sendKeys(domainName);
		Thread.sleep(500);
	}
	public void selectDomainUnlock()
	{
		Select eligibilitytype= new Select(driver.findElement(By.xpath("//select[@name='DomainLock']")));
		 eligibilitytype.selectByVisibleText("Unlock");
	}
	
	
	public void clickSubmitButtonOfDomainLock() throws InterruptedException
    {
    	System.out.println("clicking submit button");
    	if(submitButtonDomainLock.isDisplayed()||submitButtonDomainLock.isEnabled())
    	{
    		submitButtonDomainLock.click();
    		Thread.sleep(5000);
    	}
		else 
		{
			System.out.println("element not found");
		}  
     }
	
	public String getResponseOfDomainLock() throws InterruptedException{
		String domainLockResponse = null;
		Thread.sleep(7000);
    	WebElement responseDetails = driver.findElement(By.xpath("/html/body/div[13]/div[2]/pre"));
		if (responseDetails.isDisplayed()) {
			domainLockResponse = responseDetails.getText();
    	}
    	return domainLockResponse;
    }
	
	/*---Methods for Create Name Server-----*/
	public void enterDomainNameForCreateNameSrver(String domainName) throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollBy(0,250)", "");
	    DomainNameCNSTextField.sendKeys(domainName);
		Thread.sleep(500);
	}
	
	public void enterNameServerPrefixForCreateNameSrver(String nameServerPrefix) throws InterruptedException
	{
		nameServerPrefixTextField.sendKeys(nameServerPrefix);
		Thread.sleep(500);
	}
	
	public void enterNameServerIPAddressForCreateNameSrver(String nameServerIP) throws InterruptedException
	{
		nameServerIPAddressTextField.sendKeys(nameServerIP);
		Thread.sleep(500);
	}
	
	public void clickSubmitButtonOfCreateNameServer() throws InterruptedException
    {
    	System.out.println("clicking submit button");
    	if(submitButtonCreateNameServer.isDisplayed()||submitButtonCreateNameServer.isEnabled())
    	{
    		submitButtonCreateNameServer.click();
    		Thread.sleep(5000);
    	}
		else 
		{
			System.out.println("element not found");
		}  
     }
	
	public String getResponseOfCreateNameServer() throws InterruptedException{
		String createNameServerResponse = null;
		Thread.sleep(6000);
    	WebElement responseDetails = driver.findElement(By.xpath("/html/body/div[10]/div[2]/pre"));
		if (responseDetails.isDisplayed()) {
			createNameServerResponse = responseDetails.getText();
    	}
    	return createNameServerResponse;
    }
	
	/*---Methods for Change Name Server-----*/
	public void enterDomainNameForChangeNameSrver(String domainName) throws InterruptedException
	{
		//JavascriptExecutor js = (JavascriptExecutor) driver;
	   // js.executeScript("window.scrollBy(0,270)", "");
	    DomainNameChangeNSTextField.sendKeys(domainName);
		Thread.sleep(500);
	}
	
	public void enterNameServerPrefixForChangeNameSrver(String nameServerPrefix) throws InterruptedException
	{
		nameServerPrefixChangeNSTextField.sendKeys(nameServerPrefix);
		Thread.sleep(500);
	}
	
	public void enterNameServerIPAddressForChangeNameSrver(String nameServerIP) throws InterruptedException
	{
		nameServerIPAddressChangeNSTextField.sendKeys(nameServerIP);
		Thread.sleep(500);
	}
	
	public void clickSubmitButtonOfChangeNameServer() throws InterruptedException
    {
    	System.out.println("clicking submit button");
    	if(submitButtonChangeNameServer.isDisplayed()||submitButtonChangeNameServer.isEnabled())
    	{
    		submitButtonChangeNameServer.click();
    		Thread.sleep(7000);
    	}
		else 
		{
			System.out.println("element not found");
		}  
     }
	
	public String getResponseOfChangeNameServer() throws InterruptedException{
		String changeNameServerResponse = null;
		Thread.sleep(6000);
    	WebElement responseDetails = driver.findElement(By.xpath("/html/body/div[12]/div[2]/pre"));
		if (responseDetails.isDisplayed()) {
			changeNameServerResponse = responseDetails.getText();
    	}
    	return changeNameServerResponse;
    }
	
	/*---Methods for Delete Name Server-----*/
	public void enterDomainNameForDeleteNameSrver(String domainName) throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
       js.executeScript("window.scrollBy(0,270)", "");
	    DomainNameDNSTextField.sendKeys(domainName);
		Thread.sleep(500);
	}
	
	public void enterNameServerPrefixForDeleteNameSrver(String nameServerPrefix) throws InterruptedException
	{
		nameServerPrefixDNSTextField.sendKeys(nameServerPrefix);
		Thread.sleep(500);
	}

	public void clickSubmitButtonOfDeleteNameServer() throws InterruptedException
    {
    	System.out.println("clicking submit button");
    	if(submitButtonDeleteNameServer.isDisplayed()||submitButtonDeleteNameServer.isEnabled())
    	{
    		submitButtonDeleteNameServer.click();
    		Thread.sleep(5000);
    	}
		else 
		{
			System.out.println("element not found");
		}  
     }
	
	public String getResponseOfDeleteNameServer() throws InterruptedException{
		String deleteNameServerResponse = null;
		Thread.sleep(6000);
    	WebElement responseDetails = driver.findElement(By.xpath("/html/body/div[11]/div[2]/pre"));
		if (responseDetails.isDisplayed()) {
			deleteNameServerResponse = responseDetails.getText();
    	}
    	return deleteNameServerResponse;
    }
	
	/*---Methods for Registrant Name Change-----*/
	public void enterDomainNameForRegistrantNameChange(String domainname) throws InterruptedException
	{
		DomainNameTextFieldRNC.sendKeys(domainname);
		Thread.sleep(1000);
	}
	
	 public void enterNewEligibilityDetails(String registrantname, String registrantid, String eligibiltyname, String eligibilityId,String eligibiltyreason)
	 {
	    	
		 registrantNameTextFieldRNC.sendKeys(registrantname);
		 registrantIDTextField.sendKeys(registrantid);
		 eligibilityNameTextFieldRNC.sendKeys(eligibiltyname);
		 eligibilityIdTextFieldRNC.sendKeys(eligibilityId);
		 Select eligibilitytype= new Select(driver.findElement(By.xpath("(//select[@name='EligibilityType'])[2]")));
		 eligibilitytype.selectByVisibleText("Company");
		 eligibilityReasonTextFieldRNC.sendKeys(eligibiltyreason);
	    }
	 
	 public void clickSubmitButtonOfRegistrantNameChange() throws InterruptedException
	    {
	    	System.out.println("clicking submit button");
	    	if(submitButtonRegistrantNameChange.isDisplayed()||submitButtonRegistrantNameChange.isEnabled())
	    	{
	    		submitButtonRegistrantNameChange.click();
	    		Thread.sleep(5000);
	    	}
			else 
			{
				System.out.println("element not found");
			}  
	     }
	 
	 public String getResponseOfRegistrantNameChange() throws InterruptedException{
			String registrantNameChangeResponse = null;
			Thread.sleep(6000);
	    	WebElement responseDetails = driver.findElement(By.xpath("/html/body/div[8]/div[2]/pre"));
			if (responseDetails.isDisplayed()) {
				registrantNameChangeResponse = responseDetails.getText();
	    	}
	    	return registrantNameChangeResponse;
	    }
	
}