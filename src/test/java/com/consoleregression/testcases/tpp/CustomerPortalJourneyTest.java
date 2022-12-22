package com.consoleregression.testcases.tpp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.tppcustomerportal.pages.TPPAccountContactPage;
import com.tppcustomerportal.pages.TPPAddExtrasPage;
import com.tppcustomerportal.pages.TPPBillingPage;
import com.tppcustomerportal.pages.TPPDomainSearchPage;
import com.tppcustomerportal.pages.TPPHostingAndExtrasPage;
import com.tppcustomerportal.pages.TPPOnlineOrderPage;
import com.tppcustomerportal.pages.TPPOrderCompletePage;
import com.tppcustomerportal.pages.TPPRegistrantContactPage;
import com.util.TestUtil;

public class CustomerPortalJourneyTest extends TestBase {

	TPPOnlineOrderPage tpponlineorderpage;
	TPPDomainSearchPage tppdomainsearchpage;
	TPPHostingAndExtrasPage tpphostingandextraspage;
	TPPAddExtrasPage tppaddextraspage;
	TPPAccountContactPage tppaccountcontactpage;
	TPPRegistrantContactPage tppregistrantcontactpage;
	TPPBillingPage tppbillingpage;
	TPPOrderCompletePage tppordercompletepage;

	// Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;

	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public CustomerPortalJourneyTest() {
		super();
	}

	@Parameters({ "environment" })
	@Test
	public void verifyDomainRegistrationWithProductOrderForNewCustomerInCustomerPortal(String environment)
			throws InterruptedException {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = ".com";

		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);

		// Test Step 1: Login to customer portal and place an order for domain
		// registration
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerUsingNewCardInCustomerPortal");
		initialization(environment, "customerportalurl_tpp");
		tpponlineorderpage = new TPPOnlineOrderPage();
		tpponlineorderpage.clearDefaultTldSelections();
		tpponlineorderpage.setDomainNameAndTld(strDomainName, strTld);
		tppdomainsearchpage = tpponlineorderpage.clickNewDomainSearchButton();
		tpphostingandextraspage = tppdomainsearchpage.clickContinueToCheckoutWithoutDomainPrivacy();
		tppaddextraspage = tpphostingandextraspage.clickAddExtras();
		tpphostingandextraspage = tppaddextraspage.clickAddProduct("Server Management - Linux");
		tppaccountcontactpage = tpphostingandextraspage.clickContinueButton();
		
		tppaccountcontactpage.setCustomerDefaultInformation();
		tppregistrantcontactpage = tppaccountcontactpage.clickContinueButton();
		tppregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		tppbillingpage = tppregistrantcontactpage.clickContinueButton();

		// Test Step 2: Input credit card details and submit the order
		tppbillingpage.setBTFormCreditCardDetails("Test Console Regression", "4111111111111111", "11", "2019", "123");
		tppbillingpage.tickTermsAndConditions();
		tppbillingpage.clickContinueButton();

		// Test Step 3: Verify if recaptcha challenge is dislayed
		Assert.assertTrue(tppbillingpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");

		driver.close();

	}
	
	@Parameters({"environment"})
	@Test
	public void verifyDomainRegistrationWithProductOrderForExistingCustomerInCustomerPortal(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strAccountReference = null;
		String strTld = ".com";
		String strWorkflowId = null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);

		//Test Step 1: Login to customer portal and place an order for domain registration and domain privacy
		System.out.println("Start Test: verifyDomainandMultipleProductOrderForReturningCustomerInCustomerPortal");
		initialization(environment, "oldcart_domainsearchurl_netregistry");
		tpponlineorderpage = new TPPOnlineOrderPage();
		tpponlineorderpage.clearDefaultTldSelections();
		tpponlineorderpage.setDomainNameAndTld(strDomainName, strTld);
		tpphostingandextraspage = tppdomainsearchpage.clickContinueToCheckoutWithoutDomainPrivacy();
		tppaddextraspage = tpphostingandextraspage.clickAddExtras();
		tpphostingandextraspage = tppaddextraspage.clickAddProduct("Server Management - Linux");
		tppaccountcontactpage = tpphostingandextraspage.clickContinueButton();
		tppaccountcontactpage.setReturningCustomerContacts("CIT-699", "Come!n22");
		tppregistrantcontactpage = tppaccountcontactpage.clickLoginButton();
		tppregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		tppbillingpage = tppregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Select existing credit card details and submit the order 
		tppbillingpage.selectExistingCreditCardOption("Number: 5555********4444 Expiry: 03/2021");	
		tppbillingpage.tickTermsAndConditions();
		tppordercompletepage = tppbillingpage.clickContinueButton();
		
		//Test Step 3: Verify if order is completed
		Assert.assertTrue(tppordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = tppordercompletepage.getSingleReferenceID();
		strAccountReference = tppordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
		
		driver.close();
				
	}

}
