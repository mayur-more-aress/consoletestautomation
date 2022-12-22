package com.consoleregression.workflowtest.testcases;

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
import com.util.TestUtil;

public class TestDomainRegistration2WorkflowForComAu extends TestBase{

	NRGOnlineOrderPage nrgonlineorderpage;
	NRGDomainSearchPage nrgdomainsearchpage;	
	NRGAddDomainPrivacyPage nrgadddomainprivacypage;
	NRGHostingAndExtrasPage nrghostingandextraspage;
	NRGWebHostingPage nrgwebhostingpage;
	NRGAddHostingPage nrgaddhostingpage;
	NRGAddExtrasPage nrgaddextraspage;
	NRGAccountContactPage nrgaccountcontactpage;
	NRGRegistrantContactPage nrgregistrantcontactpage;
	NRGEligibilityDetailsPage nrgeligibilitydetailspage;
	NRGBillingPage nrgbillingpage;
	NRGOrderCompletePage nrgordercompletepage;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	
	TestUtil testUtil;
	public static ExtentTest logger;

	public TestDomainRegistration2WorkflowForComAu() {
		super();
	}


	@Parameters({"environment", "virtualization"})
	@Test
	public void testDomainRegistration2WorkflowForComAu(String environment, String virtualization) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strAccountReference = null;
		String strPassword = null;
		String strEligibilityIDType = null;
		String strEligibilityIDNumber = null;
		String strCompanyName = null;
		String strEligibilityType = null;
		String strWorkflowId = null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYY-hhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		
		if ((environment.equals("uat1"))&&(virtualization.equals("netregistry"))) {
			strAccountReference = "TES-2168";
			strPassword = "comein22";
			strTld = "com.au";
			strEligibilityIDType = "ABN";
			strEligibilityIDNumber = "13080859721";
			strCompanyName = "Netregistry";
			strEligibilityType = "Company";
			
			
		}
			
		//Test Step 1: Login to customer portal and place a domain registration order for .com.au tld
		System.out.println("Start Test: testDomainRegistration2WorkflowForComAu");
		initialization(environment, "customerportalurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.clearDefaultTldSelections();
		nrgonlineorderpage.setDomainNameAndTld(strDomainName, "." + strTld);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setReturningCustomerContacts(strAccountReference, strPassword);
		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
		nrgeligibilitydetailspage = nrgregistrantcontactpage.clickContinueButtonForEligibilityDetails();
		nrgeligibilitydetailspage.clickBusinessNumberButton();
		nrgeligibilitydetailspage.setEligibilityDetails(strEligibilityIDType, strEligibilityIDNumber, strCompanyName, strEligibilityType);
		nrgeligibilitydetailspage.tickCertifyDomainHasCloseAndSubstantialConnection();
		nrgeligibilitydetailspage.tickTermsAndConditions();
		
		nrgbillingpage = nrgeligibilitydetailspage.clickContinueButton();
		
		//Test Step 2: Select existing credit card details and submit the order 
		nrgbillingpage.selectExistingCreditCardOption("Number: 5555********4444 Expiry: 03/2021");
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickContinueButton();
		
		//Test Step 3: Verify if order is completed
		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = nrgordercompletepage.getSingleReferenceID();
		strAccountReference = nrgordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
		driver.close();
		
		//Test Step 4: Process the domain registration order in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, strTld);
		caworkflowadminpage.processFraudCheck();
		
		//Test Step 5: Verify if domain registration workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
		
		driver.close();
		System.out.println("End Test: testDomainRegistration2WorkflowForComAu");
				
	}
	

	

}
