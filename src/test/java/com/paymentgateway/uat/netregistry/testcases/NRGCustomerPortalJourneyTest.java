package com.paymentgateway.uat.netregistry.testcases;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.commonfunctionality.NRGOld_CommonFunctionality;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.netregistryoldwebsite.pages.NRGAccountContactPage;
import com.netregistryoldwebsite.pages.NRGAddDomainPrivacyPage;
import com.netregistryoldwebsite.pages.NRGAddExtrasPage;
import com.netregistryoldwebsite.pages.NRGAddHostingPage;
import com.netregistryoldwebsite.pages.NRGBillingPage;
import com.netregistryoldwebsite.pages.NRGDomainSearchPage;
import com.netregistryoldwebsite.pages.NRGHostingAndExtrasPage;
import com.netregistryoldwebsite.pages.NRGOnlineOrderPage;
import com.netregistryoldwebsite.pages.NRGOrderCompletePage;
import com.netregistryoldwebsite.pages.NRGRegistrantContactPage;
import com.netregistryoldwebsite.pages.NRGWebHostingPage;
import com.relevantcodes.extentreports.LogStatus;

public class NRGCustomerPortalJourneyTest extends NRGOld_CommonFunctionality{

	NRGOnlineOrderPage nrgonlineorderpage;
	NRGDomainSearchPage nrgdomainsearchpage;	
	NRGAddDomainPrivacyPage nrgadddomainprivacypage;
	NRGHostingAndExtrasPage nrghostingandextraspage;
	NRGWebHostingPage nrgwebhostingpage;
	NRGAddHostingPage nrgaddhostingpage;
	NRGAddExtrasPage nrgaddextraspage;
	NRGAccountContactPage nrgaccountcontactpage;
	NRGRegistrantContactPage nrgregistrantcontactpage;
	NRGBillingPage nrgbillingpage;
	NRGOrderCompletePage nrgordercompletepage;


	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;	

	public NRGCustomerPortalJourneyTest() 
	{
		super();
	}


	@Parameters({ "environment"})
	@Test
	public void verifyDomainRegistrationOrderForNewBTCustomerUsingNewCardInCustomerPortal(String environment) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String tld[] = {".com", ".net", ".com.au", ".co.nz"};
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);

		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");

		for(String tlds : tld){
			initialization(environment, "oldcart_domainsearchurl_netregistry");
			nrgonlineorderpage = new NRGOnlineOrderPage();
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED "+tlds);
			test.log(LogStatus.INFO, "Clearing default tld selections");
			nrgonlineorderpage.clearDefaultTldSelections();
			if(tlds==".co.nz") {
				nrgonlineorderpage.selectNzTld();
			}
			enterDomainwithNoPrivacy(strDomainName,tlds);
			nrghostingandextraspage=new NRGHostingAndExtrasPage();
			test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
			nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
			test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
			setDefaultCustomer();
			nrgregistrantcontactpage=new NRGRegistrantContactPage();
			test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
			nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
			nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
			if(tlds==".com.au") {
				setEligibilityDetails("ABN", "21073716793", "Arq Group Limited", "Company");
			}
			//Test Step 2: Input credit card details and submit the order 
			createNewCreditCardDetailsforNewCustomer("Test Console Regression", "4111111111111111", "11", "2022", "123");

			//Test Step 3: Verify if recaptcha challenge is dislayed 
			test.log(LogStatus.INFO, "Verify if recaptcha is displayed on click of continue button "+tlds);
			Assert.assertTrue(nrgbillingpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
			test.log(LogStatus.INFO, "Verification of doamain registration - COMPLETED "+tlds);
			driver.quit();
		}
	}

	@Parameters({ "environment"})
	@Test
	public void verifyDomainRegistrationOrderForExistingBTCustomerUsingNewCardInCustomerPortal(String environment) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String tld[] = {".com", ".net", ".com.au", ".co.nz"};
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);

		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");

		for(String tlds : tld){
			initialization(environment, "oldcart_domainsearchurl_netregistry");
			nrgonlineorderpage = new NRGOnlineOrderPage();
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED "+tlds);
			test.log(LogStatus.INFO, "Clearing default tld selections");
			nrgonlineorderpage.clearDefaultTldSelections();
			if(tlds==".co.nz") {
				nrgonlineorderpage.selectNzTld();
			}
			enterDomainwithNoPrivacy(strDomainName,tlds);
			nrghostingandextraspage=new NRGHostingAndExtrasPage();
			test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
			nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
			test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
			setReturningCustomer("PAY-376", "comein22");
			nrgregistrantcontactpage=new NRGRegistrantContactPage();
			test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
			nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
			nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
			if(tlds==".com.au") {
				setEligibilityDetails("ABN", "21073716793", "Arq Group Limited", "Company");
			}
			//Test Step 2: Input credit card details and submit the order 
			createNewCreditCardDetailsforExistingCustomer("Test Console Regression", "4111111111111111", "11", "2022", "123");

			//Test Step 3: Verify if recaptcha challenge is dislayed 
			test.log(LogStatus.INFO, "Verify if recaptcha is displayed on click of continue button "+tlds);
			Assert.assertTrue(nrgbillingpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
			test.log(LogStatus.INFO, "Verification of doamain registration - COMPLETED "+tlds);
			driver.quit();
		}
	}

	@Parameters({"environment", "tld"})
	@Test
	public void verifyDomainandMultipleProductOrderForReturningBTCustomerUsingNewCardInCustomerPortal(String environment, String tld) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strCardOwnerName = "Returning Customer";
		String strCardNumber = "5555555555554444";
		String strCardExpiryMonth = "05";
		String strCardExpiryYear = "2028";
		String strCardSecurityCode = "331";

		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		String strDomainName = "TestConsoleRegression" + df.format(d);

		//Test Step 1: Login to customer portal and place an order for domain registration and domain privacy
		System.out.println("Start Test: verifyDomainandMultipleProductOrderForReturningCustomerInCustomerPortal");
		initialization(environment, "oldcart_domainsearchurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.clearDefaultTldSelections();
		nrgonlineorderpage.setDomainNameAndTld(strDomainName, tld);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		nrghostingandextraspage.addCPanelStarter1Month("1 Month");
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		setReturningCustomer("ARQ-01", "comein22");
		nrgregistrantcontactpage=new NRGRegistrantContactPage();
		nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();

		//Test Step 2: Input credit card details and submit the order 
		createNewCreditCardDetailsforExistingCustomer(strCardOwnerName, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);

		//Test Step 3: Verify if recaptcha challenge is dislayed 
		Assert.assertTrue(nrgbillingpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
		driver.quit();

	}
}
