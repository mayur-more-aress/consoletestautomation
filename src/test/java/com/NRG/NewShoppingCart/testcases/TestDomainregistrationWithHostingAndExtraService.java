package com.NRG.NewShoppingCart.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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

public class TestDomainregistrationWithHostingAndExtraService extends TestBase
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
	
	// Console A2 admin pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;

	TestUtil testUtil;
	static Environment testenvironment;
	public static ExtentTest logger;

	public TestDomainregistrationWithHostingAndExtraService()  
	{
		super();	
	}
	
	// Initialization (Test Data Creation and Assignment)
	String strDomainName = null;
	String strMaskedCardNumber = null;
	String strCustomerAccountReference = null;
	String strAccountReference = null;
	String strWorkflowId = null;
	String strCustomerPassword = null;
	String strEligibilityIDType = null;
	String strEligibilityIDNumber = null;
	String strEligibilityType = null;
	String strTld = null;
	String strEmailService = null;
	String strServiceProductYear = null;
	String strService = null;
	String strServiceProduct = null;
	String strWorkflowStatus = null;
	String Domaininformation ="Have a business idea and reserving a domain for the future";
	String arrTlds[] = {".com"}; 
	List<String> privacytlds = Arrays.asList(".com", ".net");
	ArrayList<String> lstWorkflowId = null;
	ArrayList<String> lstDomainNames = null;
	SoftAssert softAssert = new SoftAssert();
	
	/*
	 * Test case to purchase domain and Domain manager product via shopping cart
	 * Product : Domain Manager
	 * Scenario : Existing customer using existing credit card
	 */
	@Parameters({ "environment"})
	@Test 
	public void verifyDomainRegistrationWithHostingAndExtraServiceForExistingCustomerUsingExistingCard(String environment) throws Exception 
	{           
		strEligibilityIDType = "ABN";
		strEligibilityIDNumber = "13080859721";
		strEligibilityType = "Company";
		strMaskedCardNumber = "************4444"; 
		strCustomerAccountReference = "TES-2168";
		strCustomerPassword = "comein22";
		strService ="Domain Manager";
		strServiceProduct ="Domain Manager";
		strServiceProductYear = "1 Year";
		
	    // Iterating over an array of tld's to be tested
	    for (String tld : arrTlds) 
	    { 	
	    	strTld = tld;
	        DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
			Date d = new Date();
			strDomainName = "DomainManagertest" + df.format(d);
	    	System.out.println("Start Test: verifyDomainRegistrationWithHostingAndExtraServiceForExistingCustomerUsingExistingCard for "+strTld+ " domain and " 
			        +strServiceProduct+ " product - STARTED");
			test.log(LogStatus.INFO, "VerifyDomainRegistrationWithHostingAndExtraServiceForExistingCustomerUsingExistingCard for "+strTld+ " domain and "
					+strServiceProduct+ " product - STARTED"); 		
			
			// Test Step 1: Navigate to domain search page of new shopping cart and place an order for a test domain
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED");
			initialization(environment, "newcart_domainsearchurl_netregistry");
			nrgnssearchadddomainspage = new NRGNSSearchAddDomainsPage();
			nrgnssearchadddomainspage.setDomainNameAndTld(strDomainName, strTld);
			nrgnssearchadddomainspage.clickSearchButton();
			nrgnssearchadddomainspage.addDomainName(strDomainName, strTld);
			test.log(LogStatus.INFO, "Navigate to domain search page -COMPLETED");
			
			//Test Step 2: Domain privacy page for tld's
			if(privacytlds.contains(strTld))
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
			nrgnsregistrantcontactpage.clickDomainInfo(Domaininformation);
			nrgnsregistrantcontactpage.clickRegistrantContact();
			testStepResultVerification(NRGNSRegistrantContactPage.continueButton);
			test.log(LogStatus.INFO, "Login as returning customer -COMPLETED");
			
			//Test Step 5: Check and enter .au eligibility details
			if(strTld.equals(".com.au"))
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
			 
			// Test Step 6: Navigate to Services page and add the service
			test.log(LogStatus.INFO, "Process the add services page -STARTED");
			nrgnsaddservicestoyourdomainpage.addHostingAndExtrasProduct(strService, strServiceProduct, strServiceProductYear);
			nrgnsreviewandpaymentpage = nrgnsaddservicestoyourdomainpage.clickContinueButton();
			test.log(LogStatus.INFO, "Process the add services page -COMPLETED");

			// Test Step 7: Select existing credit card and complete the order
			test.log(LogStatus.INFO, "Select existing credit card -STARTED");
			nrgnsreviewandpaymentpage.selectExistingCreditCard(strMaskedCardNumber);
			nrgnsreviewandpaymentpage.tickTermsAndConditions();
			testStepResultVerification(NRGNSReviewAndPaymentPage.completeOrderButton);
			nrgnsordercompletepage = nrgnsreviewandpaymentpage.clickCompleteOrder();
			test.log(LogStatus.INFO, "Select existing credit card  -COMPLETED");

			// Test Step 8: Verify if the order is completed, get workflow id and account reference.
			test.log(LogStatus.INFO, "Verify if the order is completed -STARTED");
			Assert.assertTrue(nrgnsordercompletepage.isOrderComplete(), "Order is not completed");
			strWorkflowId = nrgnsordercompletepage.getSingleReferenceID();
			testStepResultVerification(NRGNSOrderCompletePage.accountReferenceElement);
			test.log(LogStatus.INFO, "Verify if the order is completed  -COMPLETED");
			strAccountReference = nrgnsordercompletepage.getAccountReferenceID();
			System.out.println("Account Reference:" + strAccountReference);
			System.out.println("Reference ID[0]:" + strWorkflowId);
			driver.quit();

			// Test Step 9: Process the domain registration order in console admin
			initialization(environment, "consoleadmin");
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.setDefaultLoginDetails(environment);
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			
			// Process and verify if domain registration workflow is completed in console admin
			if(caworkflowadminpage.getWorkflowStatus("domainregistration2").equalsIgnoreCase("pending"))
			{
				caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, strTld);
			}
			caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + strTld);
			Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"),
					"domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));

			// Process and verify if productsetup2 workflow is approved in console admin
			if(caworkflowadminpage.getWorkflowStatus("productSetup2").equalsIgnoreCase("pending"))
			{
				caworkflowadminpage.processProductSetup2();
			}
			caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + strTld);
			Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "approved",caworkflowadminpage.getWorkflowStatus("productsetup2"));
	    	driver.quit();
	    	
	    	test.log(LogStatus.INFO, "VerifyDomainRegistrationWithHostingAndExtraServiceForExistingCustomerUsingExistingCard for "+strTld+ " domain and "
					+strServiceProduct+ " product - COMPLETED");
	    	System.out.println("Start Test: verifyDomainRegistrationWithHostingAndExtraServiceForExistingCustomerUsingExistingCard for "+strTld+ " domain and " 
			        +strServiceProduct+ " product - COMPLETED");
	    } 
	}
	
	/*
	 * Test case to purchase domain and web hosting product via shopping cart
	 * Product : cPanel Starter (Web Hosting), cPanel Business, cPanel Premium+
	 * Scenario : Existing customer using pre-paid credit
	 */
	@Parameters({ "environment"})
	@Test 
	public void verifyDomainRegistrationAndWebHostingProduct(String environment) throws Exception 
	{           
		strService ="Web Hosting";
		strServiceProduct ="cPanel Starter";
		strServiceProductYear = "1 Year";
		Domaininformation = "About to build a website";
		strCustomerAccountReference = "TES-2168";
		strCustomerPassword = "comein22";
		
	    // Iterating over an array of tld's to be tested
	    for (String tld : arrTlds) 
	    { 	
	    	strTld = tld;
	        DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
			Date d = new Date();
			strDomainName = "WebHostingtest" + df.format(d);
	    	System.out.println("Start Test: verifyDomainRegistrationWithHostingAndExtraServiceForExistingCustomerUsingExistingCard for "+strTld+ " domain and " 
			        +strServiceProduct+ " product - STARTED");
			test.log(LogStatus.INFO, "VerifyDomainRegistrationWithHostingAndExtraServiceForExistingCustomerUsingExistingCard for "+strTld+ " domain and "
					+strServiceProduct+ " product - STARTED"); 		
			
			// Test Step 1: Navigate to domain search page of new shopping cart and place an order for a test domain
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED");
			initialization(environment, "newcart_domainsearchurl_netregistry");
			nrgnssearchadddomainspage = new NRGNSSearchAddDomainsPage();
			nrgnssearchadddomainspage.setDomainNameAndTld(strDomainName, strTld);
			nrgnssearchadddomainspage.clickSearchButton();
			nrgnssearchadddomainspage.addDomainName(strDomainName, strTld);
			test.log(LogStatus.INFO, "Navigate to domain search page -COMPLETED");
			
			//Test Step 2: Domain privacy page for tld's
			if(privacytlds.contains(strTld))
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
			nrgnsregistrantcontactpage.clickDomainInfo(Domaininformation);
			nrgnsregistrantcontactpage.clickRegistrantContact();
			testStepResultVerification(NRGNSRegistrantContactPage.continueButton);
			test.log(LogStatus.INFO, "Login as returning customer -COMPLETED");
			
			//Test Step 5: Check and enter .au eligibility details
			if(strTld.equals(".com.au"))
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
			 
			// Test Step 6: Navigate to Services page and add the service
			test.log(LogStatus.INFO, "Process the add services page -STARTED");
			nrgnsaddservicestoyourdomainpage.addHostingAndExtrasProduct(strService, strServiceProduct, strServiceProductYear);
			nrgnsreviewandpaymentpage = nrgnsaddservicestoyourdomainpage.clickContinueButton();
			test.log(LogStatus.INFO, "Process the add services page -COMPLETED");

			// Test Step 7: Select existing credit card and complete the order
			test.log(LogStatus.INFO, "Select existing prepaid card -STARTED");
			nrgnsreviewandpaymentpage.selectPrepaidAccount();
			nrgnsreviewandpaymentpage.tickTermsAndConditions();
			testStepResultVerification(NRGNSReviewAndPaymentPage.completeOrderButton);
			nrgnsordercompletepage = nrgnsreviewandpaymentpage.clickCompleteOrder();
			test.log(LogStatus.INFO, "Select existing prepaid card  -COMPLETED");

			// Test Step 8: Verify if the order is completed, get workflow id and account reference.
			test.log(LogStatus.INFO, "Verify if the order is completed -STARTED");
			Assert.assertTrue(nrgnsordercompletepage.isOrderComplete(), "Order is not completed");
			strWorkflowId = nrgnsordercompletepage.getSingleReferenceID();
			testStepResultVerification(NRGNSOrderCompletePage.accountReferenceElement);
			test.log(LogStatus.INFO, "Verify if the order is completed  -COMPLETED");
			strAccountReference = nrgnsordercompletepage.getAccountReferenceID();
			System.out.println("Account Reference:" + strAccountReference);
			System.out.println("Reference ID[0]:" + strWorkflowId);
			driver.quit();

			// Test Step 9: Process the domain registration order in console admin
			initialization(environment, "consoleadmin");
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.setDefaultLoginDetails(environment);
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			
			// Process and verify if domain registration workflow is completed and productsetup2 workflow is approved in console admin
			caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, strTld);
			caworkflowadminpage.processProductSetup2();
			caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + strTld);
			Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "approved",caworkflowadminpage.getWorkflowStatus("productsetup2"));
	    	driver.quit();
	    	
	    	test.log(LogStatus.INFO, "VerifyDomainRegistrationWithHostingAndExtraServiceForExistingCustomerUsingExistingCard for "+strTld+ " domain and "
					+strServiceProduct+ " product - COMPLETED");
	    	System.out.println("Start Test: verifyDomainRegistrationWithHostingAndExtraServiceForExistingCustomerUsingExistingCard for "+strTld+ " domain and " 
			        +strServiceProduct+ " product - COMPLETED");
	    } 
	}
	
	@Parameters({ "environment"})
	@Test 
	public void verifyMultiDomainRegistrationAndWebHostingProduct(String environment) throws Exception 
	{  
		strService ="Web Hosting";
		strServiceProduct ="cPanel Starter";
		strServiceProductYear = "Monthly";
		Domaininformation = "About to build a website";
		ArrayList<String> lstDomainNames = new ArrayList<String>();
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		test.log(LogStatus.INFO, "verifyMultiDomainRegistrationAndWebHostingProduct  - STARTED");
    	System.out.println("Start Test: verifyMultiDomainRegistrationAndWebHostingProduct - STARTED");
		
		// Test Step 1: Navigate to domain search page of new shopping cart and place an order for multiple test domain
		test.log(LogStatus.INFO, "Navigate to domain search page -STARTED");
		initialization(environment, "newcart_domainsearchurl_netregistry");
		nrgnssearchadddomainspage = new NRGNSSearchAddDomainsPage();
		// Add multiple domains in shopping cart.
		for (String tld : arrTlds) 
		 { 
			strTld = tld;
			System.out.print(strTld + " "); 
			nrgnssearchadddomainspage.setDomainNameAndTld(strDomainName, strTld);
			nrgnssearchadddomainspage.clickSearchButton();
			nrgnssearchadddomainspage.addDomainName(strDomainName, strTld);
			lstDomainNames.add(strDomainName+strTld);
		 }
		test.log(LogStatus.INFO, "Navigate to domain search page -COMPLETED");
		
		//Test Step 2: Domain privacy page for tld's
		if(Arrays.asList(arrTlds).contains(".com") || Arrays.asList(arrTlds).contains(".net"))
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
		if(Arrays.asList(arrTlds).contains(".com.au"))
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
		 
		// Test Step 6: Navigate to Services page and add the service
		test.log(LogStatus.INFO, "Process the add services page -STARTED");
		for(String domainname : lstDomainNames)
		{
			System.out.println("List of domains registered" +lstDomainNames);
			System.out.println("selecting domain :" +domainname);
			nrgnsaddservicestoyourdomainpage.selectDomainName(domainname);
			nrgnsaddservicestoyourdomainpage.addHostingAndExtrasProduct(strService, strServiceProduct, strServiceProductYear);
		}
		nrgnsreviewandpaymentpage = nrgnsaddservicestoyourdomainpage.clickContinueButton();
		test.log(LogStatus.INFO, "Process the add services page -COMPLETED");

		// Test Step 7: Select prepaid credit card and complete the order
		test.log(LogStatus.INFO, "Select prepaid credit card -STARTED");
		nrgnsreviewandpaymentpage.selectPrepaidAccount();
		nrgnsreviewandpaymentpage.tickTermsAndConditions();
		testStepResultVerification(NRGNSReviewAndPaymentPage.completeOrderButton);
		nrgnsordercompletepage = nrgnsreviewandpaymentpage.clickCompleteOrder();
		test.log(LogStatus.INFO, "Select prepaid credit card  -COMPLETED");
		
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
	
		
		//Test Step 9: Verify if domain registration workflow is completed in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		for(String refID : lstWorkflowId)
		{
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caworkflowadminpage = caheaderpage.searchWorkflow(refID);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			// Process and verify if domain registration workflow is completed and productsetup2 workflow is approved in console admin
			if(strWorkflowStatus.equalsIgnoreCase("register domain failed"))
			{
				System.out.println("domainregistration2 WF is failed for Id :" +refID);
			}
			else
			{
				caworkflowadminpage.processMultipleWF(refID);
				caworkflowadminpage.processProductSetup2();
				caworkflowadminpage = caheaderpage.searchWorkflow(refID);
				strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			}
			softAssert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("product workflow failed")
					|| strWorkflowStatus.equalsIgnoreCase("update star rating"),"The domain registration for " +refID+ " FAILED");
		}
    	
		softAssert.assertAll();
		driver.quit();
    	test.log(LogStatus.INFO, "verifyMultiDomainRegistrationAndWebHostingProduct for "+strTld+ " domain and "
				+strServiceProduct+ " product - COMPLETED");
    	System.out.println("Start Test: verifyMultiDomainRegistrationAndWebHostingProduct for "+strTld+ " domain and " 
		        +strServiceProduct+ " product - COMPLETED");
	}
}
