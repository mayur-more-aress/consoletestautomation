package com.awstestdataInitialization.testcases;

import java.awt.AWTException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.base.TestBase;
import com.consoleadmin.pages.CADomainLevelPage;
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
import com.relevantcodes.extentreports.LogStatus;
import com.util.PropertyFileOperations;

public class ShoppingCart_NRG extends TestBase
{
	public ShoppingCart_NRG() 
	{
		super();
	}

	// NRG Shopping cart pages
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
	CADomainLevelPage cadomainlevelpage;

	//Data Initialization
	String strDomainName = null;
	String strTld = null;
    String strAccountReference = null;
	String strWorkflowStatus = null;
	String strCustomerAccountReference = null;
	String strCustomerPassword = "comein22";
	String strEligibilityIDType = "ABN";
	String strEligibilityIDNumber = "21073716793";
	String strEligibilityType = "Company";
	String strCompanyName = "Arq Group Limited";
	String strCardType = "Visa";
	ArrayList<String> lstWorkflowId = null;
	SoftAssert softAssert = new SoftAssert();
	String[] arrTldsForReg = {"com.au","com","net","org","biz","info","us"};
	String[] arrTldsForRenewal = {"com","net","org","biz","info","us"};
	DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
	Date d = new Date();
	
	/*
	 *Test case : to verify Bulk domain registration from NRG Shopping cart
	 *Scenario : Existing customer and Existing credit card
	 *Tld's to be register : "com", "net", "org", "biz", "info", "us", "com.au","nz"
	 */
	@Parameters({ "environment","numberofdomains"})
	@Test
	public void verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard(String environment, int numberOfDomains) throws InterruptedException, AWTException, IOException
	{
	
		strCustomerAccountReference = PropertyFileOperations.readProperties("nrg_Greencode");
		strDomainName = "Bulkdomainreg" + df.format(d);
			
		System.out.println("Start Test: verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard");
		
		//Test Step 1: Login to shopping cart  
		test.log(LogStatus.INFO, "NAvigate to shopping cart and place order");
		initialization(environment, "oldcart_domainsearchurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		
		// Test Step 2: Navigate to Bulk Domain page and place order for multiple domains
		test.log(LogStatus.INFO, "Enter multiple domains to register");
		nrgonlineorderpage.getMultipleDomainUrl();
		
		
		System.out.print("number of domains" +numberOfDomains);
	    for(int i= 0; i < numberOfDomains ; i++)
	    {
	    	strTld = arrTldsForReg[i % arrTldsForReg.length];
			System.out.print(strTld + " "); 
			nrgonlineorderpage.setMultipleDomainNameandTld(strDomainName.concat(Integer.toString(i)), strTld);
	    }
		nrgdomainsearchpage = nrgonlineorderpage.clickMultipleDomainSearchButton();
		
		// Test Step 3: Navigate to Web hosting page and click continue
		test.log(LogStatus.INFO, "Clicking on continue to checkout button ");
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page ");
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		
		// Test Step 4: Enter the existing customer details
		test.log(LogStatus.INFO, "Enter default customer information on account contact page  ");
		nrgaccountcontactpage.setReturningCustomerContacts(strCustomerAccountReference,strCustomerPassword);
		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
		test.log(LogStatus.INFO, "Select have a business idea on domain information page ");
		nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page ");
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		// Test Step 5: if array of tlds contains .au domains, enter au eligibility details
		 if(Arrays.asList(arrTldsForReg).contains("com.au") || Arrays.asList(arrTldsForReg).contains("net.au"))
		{
			nrgeligibilitydetailspage = new NRGEligibilityDetailsPage();
			test.log(LogStatus.INFO, "Entering eligibility details ");
			nrgeligibilitydetailspage.setEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
			test.log(LogStatus.INFO, "Tick on terms and conditions ");
			nrgeligibilitydetailspage.tickCertifyDomainHasCloseAndSubstantialConnection();
			nrgeligibilitydetailspage.tickTermsAndConditions();
			test.log(LogStatus.INFO, "Click on continue button on eligibility details page ");
			nrgeligibilitydetailspage.clickContinueButton();
		}
		
		//Test Step 6: Input credit card details and submit the order 
		test.log(LogStatus.INFO, "Entering credit card information ");
		nrgbillingpage.selectExistingCreditCardOption(strCardType);
		test.log(LogStatus.INFO, "Tick on terms and conditions ");
        nrgbillingpage.tickTermsAndConditions();
        test.log(LogStatus.INFO, "Click on continue button on billing page ");
        nrgordercompletepage = nrgbillingpage.clickContinueButton();
        
        //Test Step 7: Verify if the order is completed, get workflow id and account reference.
       	test.log(LogStatus.INFO, "Verify if the order is completed -STARTED");
		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
		lstWorkflowId = nrgordercompletepage.getMultipleReferenceID();
		strAccountReference = nrgordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);
		for(String refID : lstWorkflowId)
		{
			System.out.println("Reference ID:" + refID);
		}
		test.log(LogStatus.INFO, "Verify if the order is completed  -COMPLETED");
		driver.quit();
		
		//Test Step 8: Verify if domain registration workflow is completed in console admin
		test.log(LogStatus.INFO, "Login to admin and execute workflow");
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		for(String refID : lstWorkflowId)
		{
			caworkflowadminpage = caheaderpage.searchWorkflow(refID);
			caworkflowadminpage.processWorkFlow(refID, strTld);
			caheaderpage.searchWorkflow(refID);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			softAssert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("product workflow failed")
						|| strWorkflowStatus.equalsIgnoreCase("update star rating"),"The domain registration for " +refID+ " FAILED");
			
		}
		softAssert.assertAll();
		driver.quit();

		System.out.println("End Test: verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard");
	}
	
	/*
	 *Test case : to verify Bulk domain registration from DPS Reseller portal and generate Renewal WF from A2 admin
	 *Scenario : Existing customer and Existing credit card
	 *Tld's to be register : "com", "net", "org", "biz", "info", "us"
	 */
	@Parameters({ "environment","numberofdomains"})
	@Test
	public void generateARenewalWorkflowForDomainsFromConsoleAdmin(String environment, int numberOfDomains) throws  InterruptedException, AWTException, IOException
	{
		strCustomerAccountReference = PropertyFileOperations.readProperties("nrg_Greencode");
		strDomainName = "Bulkdomainreg" + df.format(d);
			
		System.out.println("Start Test: generateARenewalWorkflowForDomainsFromConsoleAdmin");
		
		//Test Step 1: Login to shopping cart  
		test.log(LogStatus.INFO, "NAvigate to shopping cart and place order");
		initialization(environment, "oldcart_domainsearchurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		
		// Test Step 2: Navigate to Bulk Domain page and place order for multiple domains
		test.log(LogStatus.INFO, "Enter multiple domains to register");
		nrgonlineorderpage.getMultipleDomainUrl();
		
		
		System.out.print("number of domains" +numberOfDomains);
	    for(int i= 0; i < numberOfDomains ; i++)
	    {
	    	strTld = arrTldsForRenewal[i % arrTldsForRenewal.length];
			System.out.print(strTld + " "); 
			nrgonlineorderpage.setMultipleDomainNameandTld(strDomainName.concat(Integer.toString(i)), strTld);
	    }
		nrgdomainsearchpage = nrgonlineorderpage.clickMultipleDomainSearchButton();
		
		// Test Step 3: Navigate to Web hosting page and click continue
		test.log(LogStatus.INFO, "Clicking on continue to checkout button ");
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page ");
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		
		// Test Step 4: Enter the existing customer details
		test.log(LogStatus.INFO, "Enter default customer information on account contact page  ");
		nrgaccountcontactpage.setReturningCustomerContacts(strCustomerAccountReference,strCustomerPassword);
		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
		test.log(LogStatus.INFO, "Select have a business idea on domain information page ");
		nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page ");
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		// Test Step 5: if array of tlds contains .au domains, enter au eligibility details
		 if(Arrays.asList(arrTldsForRenewal).contains("com.au") || Arrays.asList(arrTldsForRenewal).contains("net.au"))
		{
			nrgeligibilitydetailspage = new NRGEligibilityDetailsPage();
			test.log(LogStatus.INFO, "Entering eligibility details ");
			nrgeligibilitydetailspage.setEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
			test.log(LogStatus.INFO, "Tick on terms and conditions ");
			nrgeligibilitydetailspage.tickCertifyDomainHasCloseAndSubstantialConnection();
			nrgeligibilitydetailspage.tickTermsAndConditions();
			test.log(LogStatus.INFO, "Click on continue button on eligibility details page ");
			nrgeligibilitydetailspage.clickContinueButton();
		}
		
		//Test Step 6: Input credit card details and submit the order 
		test.log(LogStatus.INFO, "Entering credit card information ");
		nrgbillingpage.selectExistingCreditCardOption(strCardType);
		test.log(LogStatus.INFO, "Tick on terms and conditions ");
        nrgbillingpage.tickTermsAndConditions();
        test.log(LogStatus.INFO, "Click on continue button on billing page ");
        nrgordercompletepage = nrgbillingpage.clickContinueButton();
        
        //Test Step 7: Verify if the order is completed, get workflow id and account reference.
       	test.log(LogStatus.INFO, "Verify if the order is completed -STARTED");
		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
		lstWorkflowId = nrgordercompletepage.getMultipleReferenceID();
		strAccountReference = nrgordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);
		for(String refID : lstWorkflowId)
		{
			System.out.println("Reference ID:" + refID);
		}
		test.log(LogStatus.INFO, "Verify if the order is completed  -COMPLETED");
		driver.quit();
		
		//Test Step 8: Verify if domain registration workflow is completed in console admin
		test.log(LogStatus.INFO, "Login to admin and execute workflow");
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		test.log(LogStatus.INFO, "Login to admin and execute workflow");
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		for(String refID : lstWorkflowId)
		{
			caworkflowadminpage = caheaderpage.searchWorkflow(refID);
			caworkflowadminpage.processWorkFlow(refID, strTld);
			caheaderpage.searchWorkflow(refID);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			softAssert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("product workflow failed")
					|| strWorkflowStatus.equalsIgnoreCase("update star rating"),"The domain registration for " +refID+ " FAILED");
			if(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("product workflow failed"))
			{
				String domainName = caworkflowadminpage.getEntityName();
				String testdata[]=domainName.split("\\.",2);
				strDomainName = testdata[0];
				System.out.println("Domain Name: " + strDomainName);
				strTld = "."+testdata[1];
				System.out.println("Domain tld: " + strTld);
				if(strTld.contains("au"))
				{
					System.out.println("Renewal workflows can't be generated for .au domains");
				}
				else
				{
					cadomainlevelpage = caheaderpage.searchDomain(strDomainName+strTld);
					cadomainlevelpage.clickGenerateRenewalWorkflow();
					test.log(LogStatus.INFO, "Search a workflow ID in console admin and verify workflow status");
					caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName+strTld);
					String workflowType = caworkflowadminpage.searchWorkflowType("renewal2");
					if(workflowType.equalsIgnoreCase("renewal2"))
					{
						System.out.println("Renewal workflow created for domain: "+strDomainName+strTld);
						test.log(LogStatus.INFO,"Renewal workflow created for domain: "+strDomainName+strTld);
					}
					softAssert.assertEquals(workflowType, "renewal2", "renewal2 workflow is not created");
				}
			}
			else
			{
				System.out.println("Domain registration failed for RefId: "+ refID+" Cant generate renewal workflow");
				test.log(LogStatus.INFO,"Domain registration failed for RefId: "+ refID+" Cant generate renewal workflow");
			}
		}
		softAssert.assertAll();
		driver.quit();

		System.out.println("End Test: generateARenewalWorkflowForDomainsFromConsoleAdmin");

	}

	
	@Parameters({ "environment","greencode"})
	@Test
	public void verifyBulkProductProvisioning(String environment, String greencode) throws InterruptedException, AWTException{
	
		// Initialization (Test Data Creation and Assignment)
		String strWorkflowId = null;
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
			test.log(LogStatus.INFO, "Select monthly Product"+tlds);
			nrghostingandextraspage.addCPanelStarter1Month("1 Month");
			nrghostingandextraspage.selectAddOnCheckbox();
			nrghostingandextraspage.selectSecurityAddOn("1 Month");
			test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
			nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
			test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
			nrgaccountcontactpage.setReturningCustomerContacts(greencode, "comein22");
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
			//Test Step 2: Input credit card details and submit the order
			test.log(LogStatus.INFO, "Entering credit card information "+tlds);
			nrgbillingpage.selectExistingCreditCardOption("Visa");
			test.log(LogStatus.INFO, "Tick on terms and conditions "+tlds);
	        nrgbillingpage.tickTermsAndConditions();
	        test.log(LogStatus.INFO, "Click on continue button on billing page "+tlds);
	        nrgordercompletepage = nrgbillingpage.clickContinueButton();
	       
	        //Test Step 3: Verify if recaptcha challenge is dislayed
			strWorkflowId = nrgordercompletepage.getSingleReferenceID();
			strAccountReference = nrgordercompletepage.getAccountReferenceID();
			System.out.println("Account Reference:" + strAccountReference);	
			System.out.println("Reference ID[0]:" + strWorkflowId);	
			driver.quit();
			
			initialization(environment, "consoleadmin");
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.setDefaultLoginDetails(environment);
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			caworkflowadminpage.processWF(strWorkflowId, tlds);
			caworkflowadminpage.processProductSetupWF();
			// Test Step 6: Verify if domain registration workflow is completed
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caheaderpage.searchWorkflow(strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
			driver.quit();
		}
	}	

	@Parameters({ "environment", "greencode"})
	@Test
	public void verifyBulkProductProvisioningDomainManager(String environment, String greencode) throws InterruptedException, AWTException
	{
	
		// Initialization (Test Data Creation and Assignment)
		String strWorkflowId = null;
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
			test.log(LogStatus.INFO, "Select monthly Product"+tlds);
			nrghostingandextraspage.clickDomainManagerRadioButton();
			nrghostingandextraspage.addDomainManager("1 Year");
			nrghostingandextraspage.addSeoAddOn();
			nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
			test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
			nrgaccountcontactpage.setReturningCustomerContacts(greencode, "comein22");
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
			
			//Test Step 2: Input credit card details and submit the order
			test.log(LogStatus.INFO, "Entering credit card information "+tlds);
			nrgbillingpage.selectExistingCreditCardOption("Visa");
			test.log(LogStatus.INFO, "Tick on terms and conditions "+tlds);
	        nrgbillingpage.tickTermsAndConditions();
	        test.log(LogStatus.INFO, "Click on continue button on billing page "+tlds);
	        nrgordercompletepage = nrgbillingpage.clickContinueButton();
	       
	        //Test Step 3: Verify if recaptcha challenge is dislayed
			strWorkflowId = nrgordercompletepage.getSingleReferenceID();
			strAccountReference = nrgordercompletepage.getAccountReferenceID();
			System.out.println("Account Reference:" + strAccountReference);	
			System.out.println("Reference ID[0]:" + strWorkflowId);	
			driver.quit();
			
			initialization(environment, "consoleadmin");
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.setDefaultLoginDetails(environment);
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			String arr[] = caworkflowadminpage.processWF(strWorkflowId, tlds);
			caworkflowadminpage.processProductSetupWF();
			
			// Test Step 6: Verify if domain registration workflow is completed
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caheaderpage.searchWorkflow(strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
			driver.quit();
		}
		}
}
