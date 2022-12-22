package com.tppresellerportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import com.base.TestBase;

public class TPPRegistrantNameChangePage extends TestBase {

	// Objects
	@FindBy(how = How.XPATH, using = "//*[@id='content']/form[1]/div/div/div[1]/textarea")
	WebElement textDomainName;

	@FindBy(how = How.XPATH, using = "(//input[@type='submit'])[2]")
	WebElement buttonSubmit;
	
	@FindBy(how = How.XPATH, using = "//input[@name='greencode']")
	WebElement textExistingSubAccount;
	
	@FindBy(how = How.XPATH, using = "//input[@name='agreement']")
	WebElement checkboxICertify;
	
	@FindBy(how = How.XPATH, using = "//input[@name='termsConditions']")
	WebElement tickTermsAndConditions;
	
	@FindBy(how = How.XPATH, using = "//*[@id='completeOrder']/input")
	WebElement buttonSubmitAuNameChange;
	
	// Initializing Page Objects
	public TPPRegistrantNameChangePage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	
	public void enterAuDomainName(String domainname) throws InterruptedException {

		System.out.println("Enter the registered .au domain");
		Thread.sleep(1000);
		textDomainName.sendKeys(domainname);

	}
	
	public void clickOnSubmitButton() throws Exception {
		if(buttonSubmit.isDisplayed() || buttonSubmit.isEnabled()) {
			buttonSubmit.click();
			Thread.sleep(10000);
		}
		else 
		{
			System.out.println("could not find element");
		}
	}
	
	public void selectExistingCustomer(String greencode) throws InterruptedException 
	{
		System.out.println("Select existing customer");
		textExistingSubAccount.sendKeys(greencode);
		Thread.sleep(2000);

	}
	
public void selectEligibilityID(String eligibilityIDType) {
		System.out.println("Select Eligibility ID Type");
		Select eligibilityIDTypeDropdown = new Select(driver.findElement(By.xpath("//*[@id='comAuOwner-id-type']")));
		eligibilityIDTypeDropdown.selectByVisibleText(eligibilityIDType);
		
	}

	public void enterRegistrantIdNumber(String registrantIdNumber) {
		
		System.out.println("Enter Registrant ID number");
		driver.findElement(By.xpath("//*[@id='comAuOwner-id-num']")).sendKeys(registrantIdNumber);
		
	}

	public void enterCompanyName(String CompanyName) {
		
		System.out.println("Enter Company name");
		driver.findElement(By.xpath("//*[@id='comAuOwner-registrant-name']")).sendKeys(CompanyName);
	}

	public void selectEligibilityType(String eligibilityType) {
		
		System.out.println("Select Eligibility Type");
		Select eligibilityTypeDropdown = new Select(driver.findElement(By.xpath("//*[@id='comAuOwner-eligibility-type']")));
		eligibilityTypeDropdown.selectByVisibleText(eligibilityType);
		
	}

	public void provideNewEligibilityDetailsForAu(String eligibilityIDType, String registrantIdNumber, String CompanyName,
			String eligibilityType) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");
		selectEligibilityID(eligibilityIDType);
		enterRegistrantIdNumber(registrantIdNumber);
		enterCompanyName(CompanyName);
		selectEligibilityType(eligibilityType);
	}
	
public void tickIcertifyCheckbox() throws InterruptedException
{
	Thread.sleep(1000);
	checkboxICertify.click();
}
	
public void tickTermsAndConditionCheckbox() throws InterruptedException
{
	Thread.sleep(1000);
	tickTermsAndConditions.click();
}

public void clickOnSubmitButtonForAuNameChange() throws Exception {
	if(buttonSubmitAuNameChange.isDisplayed() || buttonSubmitAuNameChange.isEnabled()) {
		buttonSubmitAuNameChange.click();
		Thread.sleep(3000);
	}
	else 
	{
		System.out.println("could not find element");
	}
}

public String getAccountReferenceID() throws InterruptedException{
	String accountReference = null;
	Thread.sleep(10000);
	WebElement accountReferenceElement = driver.findElement(By.xpath("//*[@id='order-complete']/table/tbody/tr[2]/td[1]"));
	if (accountReferenceElement.isDisplayed()) {
		accountReference = accountReferenceElement.getText().substring(27);
	}
	return accountReference;
}
}
