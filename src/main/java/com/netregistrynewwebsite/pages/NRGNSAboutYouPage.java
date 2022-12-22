package com.netregistrynewwebsite.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.base.TestBase;


public class NRGNSAboutYouPage extends TestBase {

	// Objects
	@FindBy(how = How.ID, using = "organisation")
	WebElement companyName;

	@FindBy(how = How.ID, using = "usertype")
	WebElement industry;

	@FindBy(how = How.ID, using = "firstname")
	WebElement firstName;

	@FindBy(how = How.ID, using = "lastname")
	WebElement lastName;

	@FindBy(how = How.ID, using = "address")
	WebElement streetAddress;

	@FindBy(how = How.ID, using = "suburb")
	WebElement city;

	@FindBy(how = How.ID, using = "country")
	WebElement country;

	@FindBy(how = How.ID, using = "state")
	WebElement state;

	@FindBy(how = How.ID, using = "postcode")
	WebElement postcode;

	@FindBy(how = How.ID, using = "phone")
	WebElement phoneNumber;

	@FindBy(how = How.ID, using = "email")
	WebElement email;

	@FindBy(how = How.ID, using = "email2")
	WebElement emailConfirmation;

	@FindBy(how = How.ID, using = "individual")
	WebElement individual;

	@FindBy(how = How.ID, using = "business")
	WebElement business;

	@FindBy(how = How.CSS, using = "[name='createAccountForm'] button.btn.green")
	WebElement continueButton;

	@FindBy(how = How.ID, using = "login")
	WebElement userName;

	@FindBy(how = How.ID, using = "password")
	WebElement password;

	@FindBy(how = How.CSS, using = "[name='loginForm'] button.btn.green")
	public static WebElement loginButton;

	// Initializing Page Objects
	public NRGNSAboutYouPage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public void setDefaultBusinessCustomerDetails() {

		firstName.sendKeys("QA");
		lastName.sendKeys("Team");
		phoneNumber.sendKeys("0386242440");
		email.sendKeys("_qa-development@arq.group");
		emailConfirmation.sendKeys("_qa-development@arq.group");
		business.click();
    	companyName.sendKeys("Netregistry");
    	industry.sendKeys("Automotive"); 	
		streetAddress.sendKeys("505 Lt Collins Street");
		city.sendKeys("Melbourne");
		// country.sendKeys("Australia");
		state.sendKeys("Victoria");
		postcode.sendKeys("3000");

	}

	public void setDefaultIndividualCustomerDetails() {

		firstName.sendKeys("QA");
		lastName.sendKeys("Team");
		phoneNumber.sendKeys("0386242440");
		email.sendKeys("_qa-development@arq.group");
		emailConfirmation.sendKeys("_qa-development@arq.group");
		individual.click();
		streetAddress.sendKeys("505 Lt Collins Street");
		city.sendKeys("Melbourne");
		// country.sendKeys("Australia");
		state.sendKeys("Victoria");
		postcode.sendKeys("3000");

	}

	
	public void setReturningCustomerContacts(String customeraccountreference, String customerpassword)
			throws InterruptedException {

		Thread.sleep(3000);
		userName.sendKeys(customeraccountreference);
		Thread.sleep(3000);
		password.sendKeys(customerpassword);
		// Thread.sleep(3000);
		// password.sendKeys(Keys.ENTER);
	}

	public NRGNSRegistrantContactPage clickContinueButton() throws InterruptedException {

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
		continueButton.click();

		return new NRGNSRegistrantContactPage();

	}

	public NRGNSRegistrantContactPage clickLoginButton() throws InterruptedException {

		Thread.sleep(2000);
		System.out.println("clicking login button");
		if (loginButton.isDisplayed() || loginButton.isEnabled()) {

			loginButton.click();
			System.out.println("Login Button was clicked");
		} else {

			System.out.println("element not found");
		}

		Thread.sleep(5000);
		return new NRGNSRegistrantContactPage();
	}

}
