package com.salesdb.testcases;


import java.awt.AWTException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
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
import com.netregistryoldwebsite.pages.NRGEligibilityDetailsPage;
import com.netregistryoldwebsite.pages.NRGHostingAndExtrasPage;
import com.netregistryoldwebsite.pages.NRGOnlineOrderPage;
import com.netregistryoldwebsite.pages.NRGOrderCompletePage;
import com.netregistryoldwebsite.pages.NRGRegistrantContactPage;
import com.netregistryoldwebsite.pages.NRGWebHostingPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class RefundTransactionFromSalesDBProduction extends TestBase{

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
	NRGEligibilityDetailsPage nrgeligibilitydetailspage;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;	
	CAInvoicesPage cainvoicespage;
	CATaxInvoicePage cataxinvoicepage;
	
	CSLoginPage csloginpage;
	CSNrCRMPage csnrcrmpage;
	CSCreateDomainWindowPage cscreatedomainwindowpage;
	CSRegistrantDetailsPage csregistrantdetailspage;
	CSProcessTransactionPage csprocesstransactionpage;
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;
	
	String strDomainName = null;
	String strTld = null;
	String strCardOwnerName = null;
	String strCardNumber = null;
    String strCardExpiryMonth = null;
    String strCardExpiryYear = null;
    String strCardSecurityCode = null;

	public RefundTransactionFromSalesDBProduction() {
		super();
	}

	@Parameters({ "environment"})
	@Test
	public void refundTransactionFromSalesDBInProduction(String environment) throws InterruptedException, AWTException {
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strWorkflowId = null;
		String strAccountReference = null;
		String strWorkflowStatus = null;
		//String tld[] = {".com", ".net", ".com.au", ".co.nz"};
		String tld[] = {".com.au"};
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
			test.log(LogStatus.INFO, "Enter domain name and select Tld "+tlds);
			nrgonlineorderpage.setDomainNameAndTld(strDomainName, tlds);
			test.log(LogStatus.INFO, "Clicking on search button "+tlds);
			nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
			test.log(LogStatus.INFO, "Clicking on continue to checkout button "+tlds);
			nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
			nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
			test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
			nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
			test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
			nrgaccountcontactpage.setReturningCustomerContacts("ARQ-45", "comein22");
			nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
			test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
			nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
			nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
			if(tlds==".com.au") {
				nrgeligibilitydetailspage = new NRGEligibilityDetailsPage();
				test.log(LogStatus.INFO, "Entering eligibility details "+tlds);
				nrgeligibilitydetailspage.setEligibilityDetails("ABN", "21073716793", "Arq Group Limited", "Company");
				test.log(LogStatus.INFO, "Tick on terms and conditions "+tlds);
				nrgeligibilitydetailspage.tickCertifyDomainHasCloseAndSubstantialConnection();
				nrgeligibilitydetailspage.tickTermsAndConditions();
				test.log(LogStatus.INFO, "Click on continue button on eligibility details page "+tlds);
				nrgeligibilitydetailspage.clickContinueButton();
			}
			test.log(LogStatus.INFO, "Entering credit card information "+tlds);
			nrgbillingpage.selectExistingCreditCardOption("Number: 4111********1111 Expiry: 02/2021");
			test.log(LogStatus.INFO, "Tick on terms and conditions "+tlds);
	        nrgbillingpage.tickTermsAndConditions();
	        test.log(LogStatus.INFO, "Click on continue button on billing page "+tlds);
	        nrgordercompletepage = nrgbillingpage.clickContinueButton(); 
	      //  Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
			strWorkflowId = nrgordercompletepage.getSingleReferenceID();
			strAccountReference = nrgordercompletepage.getAccountReferenceID();
			System.out.println("Account Reference:" + strAccountReference);	
			System.out.println("Reference ID[0]:" + strWorkflowId);	
			driver.quit();
			
			//Step 2: Verify domain status in Console Admin
			Thread.sleep(60000);
			initialization(environment, "consoleadmin");
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.setDefaultLoginDetails(environment);
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
			String arr[] = caworkflowadminpage.clickOnWorkflowIdInProduction();
			String invoiceNumber = arr[0].toString();
			String invoiceAmount = arr[1].toString();
			System.out.println(arr[0]);
			System.out.println(arr[1]);
			driver.quit();
			
			//Step 3: Refund transaction from sales DB 
			initialization(environment, "salesdburl");
			csloginpage = new CSLoginPage();
			csloginpage.setDefaultLoginDetails(environment);
			csnrcrmpage = csloginpage.clickLoginButton();
			csprocesstransactionpage = csnrcrmpage.clickAccountTab();
			csprocesstransactionpage.setProcessTransactionDetails(strAccountReference, invoiceNumber, "REFUND", invoiceAmount, "", "Credit Card");
			Assert.assertEquals(csprocesstransactionpage.getConfirmationMessage(), "Item Successfully Added");
			driver.close();
			
			//Step 4: Credit off from Console Admin
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
			driver.close();
			
			DeleteDomainFromAPI.deleteDomainFromAPIProduction(strDomainName+tlds);
		}
	}
}