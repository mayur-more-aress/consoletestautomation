package com.NRG.NewShoppingCart.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.base.Environment;
import com.base.TestBase;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.netregistrynewwebsite.pages.NRGNSAboutYouPage;
import com.netregistrynewwebsite.pages.NRGNSAddServicesToYourDomainPage;
import com.netregistrynewwebsite.pages.NRGNSDomainPrivacyPage;
import com.netregistrynewwebsite.pages.NRGNSEligibilityDetailsPage;
import com.netregistrynewwebsite.pages.NRGNSEmailAndOffice365PackagesPage;
import com.netregistrynewwebsite.pages.NRGNSOffice365LicenseQuantityPage;
import com.netregistrynewwebsite.pages.NRGNSOrderCompletePage;
import com.netregistrynewwebsite.pages.NRGNSRegistrantContactPage;
import com.netregistrynewwebsite.pages.NRGNSReviewAndPaymentPage;
import com.netregistrynewwebsite.pages.NRGNSSearchAddDomainsPage;
import com.netregistrynewwebsite.pages.NRGNSSearchFieldPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class TestExistingCustomerScenarioUsingExistingCard extends TestBase 
{
	// Netregistry New Shopping Cart Pages
	NRGNSAboutYouPage nrgnsaboutyoupage;
	NRGNSAddServicesToYourDomainPage nrgnsaddservicestoyourdomainpage;
	NRGNSDomainPrivacyPage nrgnsdomainprivacypage;
	NRGNSEmailAndOffice365PackagesPage nrgnsemailandoffice365packagespage;
	NRGNSOffice365LicenseQuantityPage nrgnsoffice365licensequantitypage;
	NRGNSRegistrantContactPage nrgnsregistrantcontactpage;
	NRGNSReviewAndPaymentPage nrgnsreviewandpaymentpage;
	NRGNSSearchAddDomainsPage nrgnssearchadddomainspage;
	NRGNSSearchFieldPage nrgnssearchfieldpage;
	NRGNSOrderCompletePage nrgnsordercompletepage;
	NRGNSEligibilityDetailsPage nrgnseligibilitypage;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	
	TestUtil testUtil;
	static Environment testenvironment;
	public static ExtentTest logger;

	public TestExistingCustomerScenarioUsingExistingCard()
	{
		super();
	}

	// Initialization (Test Data Creation and Assignment)
	String strDomainName = null;
	String strTld = null;
	String strAccountReference = null;
	String strMaskedCardNumber = null;
	String strCustomerAccountReference = null;
	String strCustomerPassword = null;
	String strEligibilityIDType = null;
	String strEligibilityIDNumber = null;
	String strEligibilityType = null;
	String strWorkflowStatus = null;
	ArrayList<String> lstWorkflowId = null;
	String Domaininformation ="Have a business idea and reserving a domain for the future";
	String arrtlds[] = {".net",".com"}; 

	@Parameters({"environment"})
	@Test
	public void testExistingCustomerScenarioUsingExistingCard(String environment) throws Exception
	{
		strEligibilityIDType = "ABN";
		strEligibilityIDNumber = "13080859721";
		strEligibilityType = "Company";
		strMaskedCardNumber = "************4444";
		strCustomerAccountReference = "TES-2168";
		strCustomerPassword = "comein22";
	    
        DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		System.out.println("Start Test: testExistingCustomerScenarioUsingExistingCard");
		test.log(LogStatus.INFO, "TestExistingCustomerScenarioUsingExistingCard for multiple domain -STARTED");
		
		// Test Step 1: Navigate to domain search page of new shopping cart and place an order for multiple test domain
		test.log(LogStatus.INFO, "Navigate to domain search page -STARTED");
		initialization(environment, "newcart_domainsearchurl_netregistry");
		nrgnssearchadddomainspage = new NRGNSSearchAddDomainsPage();
		// Add multiple domains in shopping cart.
		for (String tld : arrtlds) 
		 { 
			strTld = tld;
			System.out.print(strTld + " "); 
			nrgnssearchadddomainspage.setDomainNameAndTld(strDomainName, strTld);
			nrgnssearchadddomainspage.clickSearchButton();
			nrgnssearchadddomainspage.addDomainName(strDomainName, strTld);
		 }
		test.log(LogStatus.INFO, "Navigate to domain search page -COMPLETED");
		
		//Test Step 2: Domain privacy page for tld's
		if(Arrays.asList(arrtlds).contains(".com") || Arrays.asList(arrtlds).contains(".net"))
		{
		    test.log(LogStatus.INFO, "Navigate to domain privacy page -STARTED");
			nrgnsdomainprivacypage = nrgnssearchadddomainspage.clickContinueButton();
			testStepResultVerification(NRGNSDomainPrivacyPage.checkBox);
			nrgnsdomainprivacypage.clickCheckBox();
			nrgnsemailandoffice365packagespage = nrgnsdomainprivacypage.clickContinueButton();
			test.log(LogStatus.INFO, "Navigate to domain privacy page -COMPLETED");
		}
		else 
		{
			nrgnsemailandoffice365packagespage = nrgnssearchadddomainspage.clickContinueButtonToEmailHosting();
		}
		
	    // Test Step 3: Navigate to Email hosting page
		test.log(LogStatus.INFO, "Process the order page -STARTED");
		nrgnsaboutyoupage = nrgnsemailandoffice365packagespage.clickContinueButton();
		testStepResultVerification(NRGNSAboutYouPage.loginButton);
		test.log(LogStatus.INFO, "Process the order page -COMPLETED");

		// Test Step 4: Login as returning or existing netregistry customer
		test.log(LogStatus.INFO, " Login as returning customer -STARTED");
		nrgnsaboutyoupage.setReturningCustomerContacts(strCustomerAccountReference, strCustomerPassword);
		nrgnsregistrantcontactpage = nrgnsaboutyoupage.clickLoginButton();
		nrgnsregistrantcontactpage.clickDomainInformation(Domaininformation);
		nrgnsregistrantcontactpage.clickRegistrantContact();
		testStepResultVerification(NRGNSRegistrantContactPage.continueButton);
		test.log(LogStatus.INFO, "Login as returning customer -COMPLETED");
		
		//Test Step 5: Check and enter .au eligibility details
		if(Arrays.asList(arrtlds).contains(".com.au"))
		{
		    test.log(LogStatus.INFO, "Navigate to add eligibility details page -STARTED");
			nrgnseligibilitypage = nrgnsregistrantcontactpage.clickContinueButtonToAuEligibility();
			nrgnseligibilitypage.setEligibilityDetails(strEligibilityIDType, strEligibilityIDNumber, strEligibilityType);
			nrgnseligibilitypage.tickCertifyDomainHasCloseAndSubstantialConnection();
			nrgnseligibilitypage.tickTermsAndConditions();
			nrgnsaddservicestoyourdomainpage = nrgnseligibilitypage.clickContinueButton();
			test.log(LogStatus.INFO, "Navigate to add eligibility details page -COMPLETED");
		}
		else
		{
			 nrgnsaddservicestoyourdomainpage = nrgnsregistrantcontactpage.clickContinueButton();
		}
		 
		// Test Step 6: Navigate to Services page
		test.log(LogStatus.INFO, "Process the add services page -STARTED");
		nrgnsreviewandpaymentpage = nrgnsaddservicestoyourdomainpage.clickContinueButton();
		test.log(LogStatus.INFO, "Process the add services page -COMPLETED");

		// Test Step 7: Select existing credit card and complete the order
		test.log(LogStatus.INFO, "Select existing credit card -STARTED");
		nrgnsreviewandpaymentpage.selectExistingCreditCard(strMaskedCardNumber);
		nrgnsreviewandpaymentpage.tickTermsAndConditions();
		testStepResultVerification(NRGNSReviewAndPaymentPage.completeOrderButton);
		nrgnsordercompletepage = nrgnsreviewandpaymentpage.clickCompleteOrder();
		test.log(LogStatus.INFO, "Select existing credit card  -COMPLETED");
		
		if (environment.equals("uat1") || environment.equals("dev2"))
		{
			// Test Step 8: Verify if the order is completed, get workflow id and account reference.
			test.log(LogStatus.INFO, "Verify if the order is completed -STARTED");
			Assert.assertTrue(nrgnsordercompletepage.isOrderComplete(), "Order is not completed");
			lstWorkflowId = nrgnsordercompletepage.getMultipleReferenceID();
			testStepResultVerification(NRGNSOrderCompletePage.accountReferenceElement);
			test.log(LogStatus.INFO, "Verify if the order is completed  -COMPLETED");

			strAccountReference = nrgnsordercompletepage.getAccountReferenceID();
			System.out.println("Account Reference:" + strAccountReference);
			for(String refID : lstWorkflowId)
			{
				System.out.println("Reference ID:" + refID);
			}
		}
		//Test Step 9: Verify if domain registration workflow is completed in console admin
	/*	initialization(environment, "consoleadminurl");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		for(String refID : lstWorkflowId)
		{
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caworkflowadminpage = caheaderpage.searchWorkflow(refID);
			caworkflowadminpage.processDomainRegistration2Workflow(refID, strTld);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed")|| strWorkflowStatus.equalsIgnoreCase("update star rating"));
		}*/

		driver.quit();
		test.log(LogStatus.INFO, "TestExistingCustomerScenarioUsingExistingCard for multiple domain -COMPLETED");
		System.out.println("End Test: testExistingCustomerScenarioUsingExistingCard");
	
	}
}
