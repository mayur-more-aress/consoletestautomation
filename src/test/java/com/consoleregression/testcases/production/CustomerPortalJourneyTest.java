package com.consoleregression.testcases.production;

import java.awt.AWTException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.consoleadmin.pages.CADomainLevelPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CAInvoicesPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CATaxInvoicePage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.consolesalesdb.pages.CSCreateDomainWindowPage;
import com.consolesalesdb.pages.CSLoginPage;
import com.consolesalesdb.pages.CSNrCRMPage;
import com.consolesalesdb.pages.CSProcessTransactionPage;
import com.consolesalesdb.pages.CSRegistrantDetailsPage;
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
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class CustomerPortalJourneyTest extends TestBase {

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
	CAInvoicesPage cainvoicespage;
	CATaxInvoicePage cataxinvoicepage;
	CADomainLevelPage cadomainlevelpage;
		
	//Salesdb pages
	CSLoginPage csloginpage;
	CSNrCRMPage csnrcrmpage;
	CSCreateDomainWindowPage cscreatedomainwindowpage;
	CSRegistrantDetailsPage csregistrantdetailspage;
	CSProcessTransactionPage csprocesstransactionpage;
	
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public CustomerPortalJourneyTest() {
		super();
	}

	@Parameters({ "environment" })
	@Test
	public void verifyDomainRegistrationOrderForNewCustomerInCustomerPortal(String environment)
			throws InterruptedException {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;

		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);

		if (environment.equals("prod")) {
			strTld = ".com";
		}

		// Test Step 1: Login to customer portal and place an order for domain
		// registration
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
		initialization(environment, "customerportalurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.clearDefaultTldSelections();
		nrgonlineorderpage.setDomainNameAndTld(strDomainName, strTld);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrghostingandextraspage = nrgdomainsearchpage.clickContinueToCheckoutWithoutDomainPrivacy();
		/*
		 * nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		 * nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		 */
		nrgaccountcontactpage = nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setCustomerDefaultInformation();
		nrgregistrantcontactpage = nrgaccountcontactpage.clickContinueButton();
		nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();

		// Test Step 2: Input credit card details and submit the order
		nrgbillingpage.setBTFormCreditCardDetails("Rachel Cottrell", "4715276660218185", "04", "2023", "094");
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickContinueButton();

		// Test Step 3: Verify if recaptcha challenge is dislayed
		Assert.assertTrue(nrgbillingpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
		driver.quit();
		
	}

	@Parameters({"environment", "pretest"})
	@Test
	public void verifyDomainandMultipleProductOrderForReturningCustomerInCustomerPortal(String environment,String pretest)
			throws InterruptedException, AWTException {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strAccountReference = "MEL-6007";
		String strTld = null;
		//String strWorkflowId="23860890";
		String strWorkflowId=null;
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);

		if (environment.equals("prod")) 
		{
			strTld = ".com";
		}

		if (pretest.equals("enabled")) 
		{
			// Test Step 1: Login to customer portal and place an order for domain
			// registration and domain privacy
			//System.out.println("Start Test: verifyDomainandMultipleProductOrderForReturningCustomerInCustomerPortal");
			initialization(environment, "customerportalurl_netregistry");
			nrgonlineorderpage = new NRGOnlineOrderPage();
			nrgonlineorderpage.clearDefaultTldSelections();
			nrgonlineorderpage.setDomainNameAndTld(strDomainName, strTld);
			nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
			nrghostingandextraspage = nrgdomainsearchpage.clickContinueToCheckoutWithoutDomainPrivacy();
			
			/*
			 * nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
			 * nrghostingandextraspage= nrgadddomainprivacypage.clickAddToCart();
			 */
			nrgaccountcontactpage = nrghostingandextraspage.clickContinueButton();
			nrgaccountcontactpage.setReturningCustomerContacts("MEL-6007", "comein22");
			nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
			nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
	
			// Test Step 2: Select existing credit card details and submit the order
	
			/*
			 * We will assign new credit card details once Finance Team provide a new one.
			 * nrgbillingpage.
			 * selectExistingCreditCardOption("Number: 4715********8185 Expiry: 04/2023");
			 */
	
			nrgbillingpage.selectExistingCreditCardOption("Prepaid credit:");
			nrgbillingpage.tickTermsAndConditions();
			nrgordercompletepage = nrgbillingpage.clickContinueButton();
	
			// Test Step 3: Verify if order is completed
			Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
			strWorkflowId = nrgordercompletepage.getSingleReferenceID();
			strAccountReference = nrgordercompletepage.getAccountReferenceID();
			System.out.println("Account Reference:" + strAccountReference);
			System.out.println("Reference ID[0]:" + strWorkflowId);
			driver.quit();
		}
		else
		{
			// Test Step 4: Fetch invoice number and invoice amount
			initialization(environment, "consoleadmin");
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.setDefaultLoginDetails(environment);
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			String domainName = caworkflowadminpage.getWorkflowEntity(strWorkflowId);
			System.out.println("Domain name : " +domainName);
			String arr[] = caworkflowadminpage.clickOnWorkflowIdInProduction();
			String invoiceNumber = arr[0].toString();
			String invoiceAmount = arr[1].toString();
			System.out.println(arr[0]);
			System.out.println(arr[1]);
			driver.quit();
			
			//Step 5: Refund Domain from salesDB
			initialization(environment, "salesdburl");
			csloginpage = new CSLoginPage();
			csloginpage.setDefaultLoginDetails(environment);
			csnrcrmpage = csloginpage.clickLoginButton();
			csprocesstransactionpage = csnrcrmpage.clickAccountTab();
			csprocesstransactionpage.setProcessTransactionDetails(strAccountReference, invoiceNumber, "REFUND", invoiceAmount, "", "Prepaid credit");
			Assert.assertEquals(csprocesstransactionpage.getConfirmationMessage(), "Item Successfully Added");
			driver.close();
			
			//Step 6: Credit off Domain from console admin
			initialization(environment, "consoleadmin");
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.setDefaultLoginDetails(environment);
			caheaderpage.searchAccountReference(strAccountReference);
			cainvoicespage = caheaderpage.clickViewInvoiceAndPrepaidDetail();
			cataxinvoicepage = cainvoicespage.clickInvoiceID(invoiceNumber);
			cataxinvoicepage.enterDescription("Refund to credit card");
			cataxinvoicepage.enterInvoiceAmount(invoiceAmount);
			cataxinvoicepage.clickSubmitButton();
			Assert.assertEquals(cataxinvoicepage.getRefundConfirmationMessage(), "Credit has been successfully entered.");
			
			//Step 7 : Cancel domain from console
			cadomainlevelpage = caheaderpage.searchDomainName(domainName);
			cadomainlevelpage.clickCancelDomain();
			cadomainlevelpage.clickCancelService();
			driver.quit();
			
			//Step 8 : Cancel domain from salesdb
			initialization(environment, "salesdburl");
			csloginpage = new CSLoginPage();
			csloginpage.setDefaultLoginDetails(environment);
			csnrcrmpage = csloginpage.clickLoginButton();
			csnrcrmpage.setDomainName(domainName);
			csnrcrmpage.setCanelDate();
			driver.quit();
			
			//Step 7: Delete domain from API
			//DeleteDomainFromAPI.deleteDomainFromAPIAfilias(strDomainName+strTld);	
		}
	}

}
