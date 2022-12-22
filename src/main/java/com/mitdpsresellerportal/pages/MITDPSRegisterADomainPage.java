package com.mitdpsresellerportal.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class MITDPSRegisterADomainPage extends TestBase {

	// Objects

	@FindBy(how = How.ID, using = "domain")
	WebElement newDomainSearchBox;

	@FindBy(how = How.XPATH, using = "//*[@id=\"availability\"]/div/span")
	WebElement searchResultsTextWithoutDomainPrivacy;

	@FindBy(how = How.XPATH, using = "//*[@id=\"availability\"]/div[2]/span")
	WebElement searchResultsTextWithDomainPrivacy;

	@FindBy(how = How.CSS, using = "#customer-details #searchExistingAccount")
	WebElement searchExistingCustomer;

	@FindBy(how = How.CSS, using = "#customer-details #createNewAccount")
	WebElement addNewCustomer;

	@FindBy(how = How.CSS, using = "#alternateRegistrant [name='contact.organisation']")
	WebElement newCustomerOrganization;

	@FindBy(how = How.CSS, using = "#alternateRegistrant [name='contact.firstName']")
	WebElement newCustomerFirstName;

	@FindBy(how = How.CSS, using = "#alternateRegistrant [name='contact.lastName']")
	WebElement newCustomerLastName;

	@FindBy(how = How.CSS, using = "#alternateRegistrant [name='contact.address.address1']")
	WebElement newCustomerAddress;

	@FindBy(how = How.CSS, using = "#alternateRegistrant [name='contact.address.city']")
	WebElement newCustomerCity;

	@FindBy(how = How.CSS, using = "#alternateRegistrant [name='contact.address.country']")
	WebElement newCustomerCountry;

	@FindBy(how = How.CSS, using = "#alternateRegistrant [name='contact.address.state']")
	WebElement newCustomerState;

	@FindBy(how = How.CSS, using = "#alternateRegistrant [name='contact.address.postcode']")
	WebElement newCustomerPostcode;

	@FindBy(how = How.CSS, using = "#alternateRegistrant [name='contact.phone']")
	WebElement newCustomerPhoneNumber;

	@FindBy(how = How.CSS, using = "#alternateRegistrant [name='contact.fax']")
	WebElement newCustomerFax;

	@FindBy(how = How.CSS, using = "#alternateRegistrant [name='contact.email']")
	WebElement newCustomerEmail;

	@FindBy(how = How.CSS, using = "#alternateRegistrant2 [name='contact.organisation']")
	WebElement registrantOrganization;

	@FindBy(how = How.CSS, using = "#alternateRegistrant2 [name='contact.firstName']")
	WebElement registrantFirstName;

	@FindBy(how = How.CSS, using = "#alternateRegistrant2 [name='contact.lastName']")
	WebElement registrantLastName;

	@FindBy(how = How.CSS, using = "#alternateRegistrant2 [name='contact.address.address1']")
	WebElement registrantAddress;

	@FindBy(how = How.CSS, using = "#alternateRegistrant2 [name='contact.address.city']")
	WebElement registrantCity;

	@FindBy(how = How.CSS, using = "#alternateRegistrant2 [name='contact.address.country']")
	WebElement registrantCountry;

	@FindBy(how = How.CSS, using = "#alternateRegistrant2 [name='contact.address.state']")
	WebElement registrantState;

	@FindBy(how = How.CSS, using = "#alternateRegistrant2 [name='contact.address.postcode']")
	WebElement registrantPostcode;

	@FindBy(how = How.CSS, using = "#alternateRegistrant2 [name='contact.phone']")
	WebElement nregistrantPhoneNumber;

	@FindBy(how = How.CSS, using = "#alternateRegistrant2 [name='contact.fax']")
	WebElement registrantFax;

	@FindBy(how = How.CSS, using = "#alternateRegistrant2 [name='contact.email']")
	WebElement registrantEmail;

	@FindBy(how = How.NAME, using = "updateContact")
	WebElement updateContactButton;

	@FindBy(how = How.CSS, using = "#searchHosting #hostingPriceId")
	WebElement hosting;

	@FindBy(how = How.NAME, using = "termsConditions")
	WebElement termsAndConditions;

	@FindBy(how = How.ID, using = "registerDomain")
	WebElement registerDomainButton;

	// Initializing Page Objects
	public MITDPSRegisterADomainPage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public void setDomainNameAndTld(String domainname, String tldname) throws Exception {

		Thread.sleep(4000);
		if(newDomainSearchBox.isDisplayed() || newDomainSearchBox.isEnabled()) {
		newDomainSearchBox.clear();
		newDomainSearchBox.sendKeys(domainname);
		}
		else {
			System.out.println("could not find element");
		}
		clickDomainExtension(tldname);

	}
	
	public ArrayList<String> getMultipleReferenceID() throws InterruptedException 
	 {
		ArrayList<String> referenceIDNumber = new ArrayList<String>();
		String strRefId = null;
		String strOrderMessage = null;
		Thread.sleep(5000);
		List<WebElement> referenceIdNumberElement = driver.findElements(By.xpath("//*[@id='content']/div/p"));
		for (WebElement refID : referenceIdNumberElement)
		{  	
			Thread.sleep(2000);
			strOrderMessage = refID.getText();
			if (refID.isDisplayed()) 
			{
				if(strOrderMessage.isEmpty())
				{
					System.out.println("The refid is empty ");
				}
				else
				{   
					strRefId = strOrderMessage.substring(strOrderMessage.lastIndexOf(" ")+1);
					System.out.println("Refid: "+strRefId);
					referenceIDNumber.add(strRefId);
				}	
			}
   	}
   	return referenceIDNumber;	
	}

	public void clickDomainExtension(String strDomainExtension) throws Exception {

		Thread.sleep(2000);
		Select dropdown = new Select(driver.findElement(By.id("tld")));
		Thread.sleep(2000);
		dropdown.selectByValue(strDomainExtension);

	}

	public String getSearchResultsMessage() throws InterruptedException {

		System.out.println("Return search result message");
		if (driver.findElements(By.className("buyWhoisPrivacy")).size() != 0) {

			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(searchResultsTextWithDomainPrivacy));
			return searchResultsTextWithDomainPrivacy.getText();
		}

		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(searchResultsTextWithoutDomainPrivacy));
		return searchResultsTextWithoutDomainPrivacy.getText();
	}

	public void selectExistingCustomer() {

		System.out.println("Select existing customer");
		searchExistingCustomer.click();

	}

	public void selectAddNewCustomer() {

		System.out.println("Add new customer");
		addNewCustomer.click();

	}
	
	public void setNewCustomerOrganisation(String organisation) {

		System.out.println("Enter New customer's organisation");
		newCustomerOrganization.sendKeys(organisation);

	}

	public void setNewCustomerFirstName(String firstName) {

		System.out.println("Enter New customer's first name");
		newCustomerFirstName.sendKeys(firstName);

	}

	public void setNewCustomerLastName(String lastName) {

		System.out.println("Enter New customer's last name");
		newCustomerLastName.sendKeys(lastName);

	}

	public void setNewCustomerAddress(String address) {

		System.out.println("Enter New customer's address");
		newCustomerAddress.sendKeys(address);

	}

	public void setNewCustomerCity(String city) {

		System.out.println("Enter New customer's city");
		newCustomerCity.sendKeys(city);

	}

	public void selectCountry(String country) {

		System.out.println("Select New customer's country");
		Select countryDropdown = new Select(newCustomerCountry);
		countryDropdown.selectByVisibleText(country);

	}

	public void selectState(String state) {

		System.out.println("Select New customer's state");
		Select countryDropdown = new Select(newCustomerState);
		countryDropdown.selectByVisibleText(state);

	}

	public void setNewCustomerPostcode(String postcode) {

		System.out.println("Enter New customer's postcode");
		newCustomerPostcode.sendKeys(postcode);

	}

	public void setNewCustomerPhoneNumber(String phoneNumber) {

		System.out.println("Enter New customer's phoneNumber");
		newCustomerPhoneNumber.sendKeys(phoneNumber);

	}

	public void setNewCustomerFax(String fax) {

		System.out.println("Enter New customer's fax");
		newCustomerFax.sendKeys(fax);

	}

	public void setNewCustomerEmail(String email) {

		System.out.println("Enter New customer's email");
		newCustomerEmail.sendKeys(email);

	}

	public void setNewCustomerDetails() {

		System.out.println("Enter New customer details");
		setNewCustomerOrganisation("Test Console Regression");
		setNewCustomerFirstName("QA");
		setNewCustomerLastName("Team");
		setNewCustomerAddress("505 Lt Collins Street");
		setNewCustomerCity("Melbourne");
		selectCountry("AUSTRALIA");
		selectState("VIC");
		setNewCustomerPostcode("0626");
		setNewCustomerPhoneNumber("+61.400000000");
		setNewCustomerFax("+61.400000000");
		setNewCustomerEmail("_qa-development@arq.group");

	}

	public void selectRegistranContact(String strRegistratContact) throws InterruptedException {
		Thread.sleep(2000);
		Select registrantContactDropdown = new Select(driver.findElement(By.cssSelector("#registrantSearch #existingContact")));
		registrantContactDropdown.selectByVisibleText(strRegistratContact);

	}

	public void setRegistrantOrganisation(String organisation) {

		System.out.println("Enter Registrant's organisation");
		registrantOrganization.sendKeys(organisation);

	}

	public void setRegistrantFirstName(String firstName) {

		System.out.println("Enter Registrant's first name");
		registrantFirstName.sendKeys(firstName);

	}

	public void setRegistrantLastName(String lastName) {

		System.out.println("Enter Registrant's last name");
		registrantLastName.sendKeys(lastName);

	}

	public void setRegistrantAddress(String address) {

		System.out.println("Enter Registrant's address");
		registrantAddress.sendKeys(address);

	}

	public void setRegistrantCity(String city) {

		System.out.println("Enter Registrant's city");
		registrantCity.sendKeys(city);

	}

	public void selectRegistrantCountry(String country) {

		System.out.println("Select Registrant's country");
		Select countryDropdown = new Select(registrantCountry);
		countryDropdown.selectByVisibleText(country);

	}

	public void selectRegistrantState(String state) {

		System.out.println("Select Registrant's state");
		Select countryDropdown = new Select(registrantState);
		countryDropdown.selectByVisibleText(state);

	}

	public void setRegistrantPostcode(String postcode) {

		System.out.println("Enter Registrant's postcode");
		registrantPostcode.sendKeys(postcode);

	}

	public void setRegistrantPhoneNumber(String phoneNumber) {

		System.out.println("Enter Registrant's phoneNumber");
		nregistrantPhoneNumber.sendKeys(phoneNumber);

	}

	public void setRegistrantFax(String fax) {

		System.out.println("Enter Registrant's fax");
		registrantFax.sendKeys(fax);

	}

	public void setRegistrantEmail(String email) {

		System.out.println("Enter Registrant's email");
		registrantEmail.sendKeys(email);

	}

	public void clickUpdateButton() {

		System.out.println("Click update contact button");
		updateContactButton.click();

	}

	public void updateRegistrantDetails() {

		System.out.println("Enter New customer details");
		setRegistrantOrganisation("Test Console Regression");
		setRegistrantFirstName("QA");
		setRegistrantLastName("Team");
		setRegistrantAddress("505 Lt Collins Street");
		setRegistrantCity("Melbourne");
		selectRegistrantCountry("AUSTRALIA");
		selectRegistrantState("VIC");
		setRegistrantPostcode("0626");
		setRegistrantPhoneNumber("+61.400000000");
		setRegistrantFax("+61.400000000");
		setRegistrantEmail("_qa-development@arq.group");
		clickUpdateButton();

	}

	public void selectHosting(String hostingProduct) {

		System.out.println("Select hosting product");
		Select hostingDropdown = new Select(hosting);
		hostingDropdown.selectByVisibleText(hostingProduct);

	}

	public void tickNameServerOptions(String strnameserverOption) throws InterruptedException {

		Thread.sleep(3000);

		if (strnameserverOption.contentEquals("Use our nameservers")) {
			driver.findElement(By.xpath("//*[@id='nameserverOptions']/tbody/tr/td[1]/label[1]")).click();
		} else if (strnameserverOption.contentEquals("Choose your nameservers")) {
			driver.findElement(By.xpath("//*[@id='nameserverOptions']/tbody/tr/td[1]/label[1]")).click();
		}
		System.out.println("Tick nameserver option");

	}

	public void clearNameServerFields() throws InterruptedException {

		Thread.sleep(3000);

		for (int i = 0; i < 4; i++) {
			driver.findElement(By.name("nameservers[" + i + "]")).clear();
		}

	}

	public void inputNameServerFields(String ... strNameServers) throws InterruptedException {
 
		Thread.sleep(2000);

		clearNameServerFields();
		

		Thread.sleep(2000);
		for (int i = 0; i < strNameServers.length; i++) {
			driver.findElement(By.name("nameservers[" + i + "]")).sendKeys(strNameServers[i]);
		}

	}

	public void tickTermsAndConditions() {

		System.out.println("Tick Terms and Conditions");
		termsAndConditions.click();
	}

	public boolean isOrderComplete(String message) {

		boolean flag = false;
		WebElement orderMessage = driver.findElement(By.className("messages"));

		if (orderMessage.getText().contains(message)) {
			System.out.println(orderMessage.getText());

			flag = true;
		}
		return flag;

	}

	public String getSingleReferenceID() throws InterruptedException {

		String strOrderReferenceID = null;
		String strOrderMessage = null;
		Thread.sleep(8000);
		WebElement orderMessage = driver.findElement(By.className("messages"));

		if (orderMessage.isDisplayed()) {
			strOrderMessage = orderMessage.getText();
			strOrderReferenceID = strOrderMessage.substring(strOrderMessage.lastIndexOf(" "));
			System.out.println("Message displayed. Returning the Order Reference ID: " + strOrderReferenceID);
		}

		System.out.println("Here's the Order Reference ID: " + strOrderReferenceID);
		return strOrderReferenceID.replaceAll("\\s","");
	}

	public void clickRegisterDomainButton() {

		System.out.println("Click Register Domain button");
		registerDomainButton.click();

	}

	public void selectEligibilityID(String eligibilityIDType) throws InterruptedException {
		
		Thread.sleep(5000);
		System.out.println("Select Eligibility ID Type");
		//Select eligibilityIDTypeDropdown = new Select(driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/div[5]/div[3]/div/div[1]/select")));
		Select eligibilityIDTypeDropdown = new Select(driver.findElement(By.xpath("//*[@id='registrantIdType']/select")));
		eligibilityIDTypeDropdown.selectByVisibleText(eligibilityIDType);
		
	}

	public void enterRegistrantIdNumber(String registrantIdNumber) {
		
		System.out.println("Enter Registrant ID number");
		//driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/div[5]/div[3]/div/div[2]/input")).sendKeys(registrantIdNumber);
		driver.findElement(By.xpath("//*[@id='registrantIdNumber']/input")).sendKeys(registrantIdNumber);
		
	}

	public void enterCompanyName(String registrantName) {
		
		System.out.println("Enter Registrant's name");
		//driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/div[5]/div[3]/div/div[3]/input")).sendKeys(registrantName);
		driver.findElement(By.xpath("//*[@id='auEligibility']/div[3]/input")).sendKeys(registrantName);
	}

	public void selectEligibilityType(String eligibilityType) {
		
		System.out.println("Select Eligibility Type");
		//Select eligibilityTypeDropdown = new Select(driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/div[5]/div[3]/div/div[4]/select")));
		Select eligibilityTypeDropdown = new Select(driver.findElement(By.xpath("//*[@id='auEligibility']/div[4]/select")));
		eligibilityTypeDropdown.selectByVisibleText(eligibilityType);
		
	}

	public void tickICertify() {
		
		System.out.println("Tick that I certify");
		//driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/div[5]/div[3]/div/div[5]/input")).click();
		driver.findElement(By.xpath("//*[@id='auEligibility']/div[5]/input")).click();
	}

	public void provideEligibilityDetailsForAu(String eligibilityIDType, String registrantIdNumber, String registrantName,
			String eligibilityType) throws InterruptedException {
		
		selectEligibilityID(eligibilityIDType);
		enterRegistrantIdNumber(registrantIdNumber);
		enterCompanyName(registrantName);
		selectEligibilityType(eligibilityType);
		tickICertify();
		
	}


	public void provideEligibilityDetailsForIdAu(String registrantName, String eligibilityType) {
		
		enterCompanyName(registrantName);
		selectEligibilityType(eligibilityType);
		tickICertify();
		
	}



}
