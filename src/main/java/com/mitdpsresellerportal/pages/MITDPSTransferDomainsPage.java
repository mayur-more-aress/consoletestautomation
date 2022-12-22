package com.mitdpsresellerportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.base.TestBase;

public class MITDPSTransferDomainsPage extends TestBase {

	//Objects
	@FindBy(how = How.NAME, using = "domainName")
	WebElement enterDomainName;
	
	@FindBy(how = How.NAME, using = "domainTld")
	WebElement selectDomainNamespace;
	
	@FindBy(how = How.NAME, using = "domainPassword")
	WebElement enterDomainAuthCode;
	
	@FindBy(how = How.NAME, using = "addSingleSelection")
	WebElement clickOnAdd;
	
	@FindBy(how = How.XPATH, using = "//*[@id='domainsTransferList']/table/tbody/tr[2]/td[2]")
	WebElement getStatusOfDomain;
	
	@FindBy(how = How.XPATH, using = "//*[contains(@id,'existingContact')]")
	WebElement selectRegistrantContact;
	
	@FindBy(how = How.XPATH, using = "//*[@id='update-contact-form']/div[1]/div[1]/div/input")
	WebElement enterOrganisationName;
	
	@FindBy(how = How.XPATH, using = "//*[@id='update-contact-form']/div[1]/div[2]/div[1]/input")
	WebElement enterFirstName;
	
	@FindBy(how = How.XPATH, using = "//*[@id='update-contact-form']/div[1]/div[2]/div[2]/input")
	WebElement enterLastName;
	
	@FindBy(how = How.XPATH, using = "//*[@id='update-contact-form']/div[1]/div[3]/div[1]/input")
	WebElement enterAddress;
	
	@FindBy(how = How.XPATH, using = "//*[@id='update-contact-form']/div[1]/div[4]/div[1]/input")
	WebElement enterCity;
	
	@FindBy(how = How.XPATH, using = "//*[@id='update-contact-form']/div[1]/div[4]/div[2]/select")
	WebElement selectCountry;
	
	@FindBy(how = How.XPATH, using = "//*[@id='update-contact-form']/div[1]/div[4]/div[3]/select")
	WebElement selectState;
	
	@FindBy(how = How.XPATH, using = "//*[@id='update-contact-form']/div[1]/div[4]/div[6]/input")
	WebElement enterPostCode;
	
	@FindBy(how = How.XPATH, using = "//*[@id='update-contact-form']/div[1]/div[5]/div[1]/input")
	WebElement enterPhoneNumber;
	
	@FindBy(how = How.XPATH, using = "//*[@id='update-contact-form']/div[1]/div[5]/div[3]/input")
	WebElement enterEmail;
	
	@FindBy(how = How.NAME, using = "termsConditions")
	WebElement agreeTermsAndConditions;
	
	@FindBy(how = How.ID, using = "transferDomainSubmit")
	WebElement clickOnTransferDomains;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"domainsTransferList\"]/table/tbody/tr[2]/td[2]")
	WebElement domainStatus;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"domainsTransferList\"]/table/tbody/tr[2]/td[6]/input")
	WebElement selectDomainCheckBox;
	
	//Initializing Page Objects
	public MITDPSTransferDomainsPage(){
		PageFactory.initElements(driver, this);
	}
	
	//Methods
	public void enterDomainPrefix(String domainprefix) throws InterruptedException{
		Thread.sleep(4000);
		enterDomainName.sendKeys(domainprefix);
		Thread.sleep(2000);
	}
	
	public void selectDomainNamespace(String namespace) throws InterruptedException{
		Thread.sleep(2000);
		selectDomainNamespace.sendKeys(namespace);
	}
	
	public void enterAuthCode(String authCode) throws InterruptedException{
		Thread.sleep(2000);
		enterDomainAuthCode.sendKeys(authCode);
	}
	
	public void clickOnAddLink() throws InterruptedException{
		clickOnAdd.click();
		Thread.sleep(3000);
	}
	
	public String getDomainStatus() throws InterruptedException{
		Thread.sleep(2000);
		String statusOfDomain = getStatusOfDomain.getText();
		return statusOfDomain;
	}
	
	public void selectRegistrantContactInfo(String registrantName) throws InterruptedException{
		Thread.sleep(4000);
		selectRegistrantContact.sendKeys(registrantName);
	}
	
	public void enterOrganisationName(String organisationName){
		enterOrganisationName.clear();
		enterOrganisationName.sendKeys(organisationName);
	}
	
	public void enterFirstName(String firstName){
		enterFirstName.clear();
		enterFirstName.sendKeys(firstName);
	}
	
	public void enterLastName(String lastName){
		enterLastName.clear();
		enterLastName.sendKeys(lastName);
	}
	
	public void enterAddress(String address){
		enterAddress.clear();
		enterAddress.sendKeys(address);
	}
	
	public void enterCityName(String cityName){
		enterCity.clear();
		enterCity.sendKeys(cityName);
	}
	
	public void selectCountry(String countryName){
		selectCountry.sendKeys(countryName);
	}
	
	public void selectState(String stateName){
		selectState.sendKeys(stateName);
	}
	
	public void enterPostalCode(String postcode){
		enterPostCode.clear();
		enterPostCode.sendKeys(postcode);
	}
	
	public void enterPhoneNumber(String phoneNumber){
		enterPhoneNumber.clear();
		enterPhoneNumber.sendKeys(phoneNumber);
	}
	
	public void enterEmail(String emailId){
		enterEmail.clear();
		enterEmail.sendKeys(emailId);
	}
	
	public void checkTermsAndConditions(){
		agreeTermsAndConditions.click();
	}
	
	public MITDPSTransferDomainsOrderCompletePage clickOnTransferDomains(){
		clickOnTransferDomains.click();
		return new MITDPSTransferDomainsOrderCompletePage();
	}
	
	public boolean waitforDomainOkStatus() throws InterruptedException {
		Thread.sleep(10000);
		String dStatus = domainStatus.getText();
		if(dStatus.equals("Ok")) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public void selectDomainCheckBox() {
		if(selectDomainCheckBox.isDisplayed()||selectDomainCheckBox.isEnabled()) {
			selectDomainCheckBox.click();
		}else {
			System.out.println("Could not find checkbox");
		}
	}
}
