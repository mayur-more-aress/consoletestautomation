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
import com.melbourneitwebsite.pages.MITAccountContactPage;
import com.melbourneitwebsite.pages.MITAddDomainPrivacyPage;
import com.melbourneitwebsite.pages.MITAddExtrasPage;
import com.melbourneitwebsite.pages.MITAddHostingPage;
import com.melbourneitwebsite.pages.MITBillingPage;
import com.melbourneitwebsite.pages.MITDomainSearchPage;
import com.melbourneitwebsite.pages.MITEligibilityDetailsPage;
import com.melbourneitwebsite.pages.MITHostingAndExtrasPage;
import com.melbourneitwebsite.pages.MITOnlineOrderPage;
import com.melbourneitwebsite.pages.MITOrderCompletePage;
import com.melbourneitwebsite.pages.MITRegistrantContactPage;
import com.melbourneitwebsite.pages.MITWebHostingPage;
import com.relevantcodes.extentreports.LogStatus;
import com.util.PropertyFileOperations;

public class ShoppingCart_MIT extends TestBase
{
	public ShoppingCart_MIT()
	{
		super();
	}

	//MIT Shopping cart pages
	MITOnlineOrderPage mitonlineorderpage;
	MITDomainSearchPage mitdomainsearchpage;	
	MITAddDomainPrivacyPage mitadddomainprivacypage;
	MITHostingAndExtrasPage mithostingandextraspage;
	MITWebHostingPage mitwebhostingpage;
	MITAddHostingPage mitaddhostingpage;
	MITAddExtrasPage mitaddextraspage;
	MITAccountContactPage mitaccountcontactpage;
	MITRegistrantContactPage mitregistrantcontactpage;
	MITBillingPage mitbillingpage;
	MITOrderCompletePage mitordercompletepage;
	MITEligibilityDetailsPage miteligibilitydetailspage;
	
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
	 *Test case : to verify Bulk domain registration from MIT Shopping cart
	 *Scenario : Existing customer and Existing credit card
	 *Tld's to be register : "com", "net", "org", "biz", "info", "us", "com.au", "net.au", "nz"
	 */
	@Parameters({ "environment","numberofdomains"})
	@Test
	public void verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard(String environment,int numberOfDomains) throws InterruptedException, AWTException, IOException
	{
		strCustomerAccountReference = PropertyFileOperations.readProperties("mit_Greencode");
		strDomainName = "Bulkdomainreg" + df.format(d);
			
		System.out.println("Start Test: verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard");
		
		//Test Step 1: Login to shopping cart
		test.log(LogStatus.INFO, "NAvigate to shopping cart and place order");
		initialization(environment, "oldcart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		
		// Test Step 2: Navigate to Bulk Domain page and place order for multiple domains
		test.log(LogStatus.INFO, "Enter multiple domains to register");
		mitonlineorderpage.getMultipleDomainUrl();
		for(int i= 0; i < numberOfDomains ; i++)
	    {
	    	strTld = arrTldsForReg[i % arrTldsForReg.length];
			System.out.print(strTld + " "); 
			mitonlineorderpage.setMultipleDomainNameandTld(strDomainName.concat(Integer.toString(i)), strTld);
	    }
		mitdomainsearchpage = mitonlineorderpage.clickMultipleDomainSearchButton();
		
		// Test Step 3: Navigate to Web hosting page and click continue
		test.log(LogStatus.INFO, "Clicking on continue to checkout button ");
		mitadddomainprivacypage = mitdomainsearchpage.clickContinueToCheckout();
		mithostingandextraspage= mitadddomainprivacypage.clickNoThanks();
		test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page ");
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		
		// Test Step 4: Enter the existing customer details
		test.log(LogStatus.INFO, "Enter default customer information on account contact page  ");
		mitaccountcontactpage.setReturningCustomerContacts(strCustomerAccountReference,strCustomerPassword);
		mitregistrantcontactpage = mitaccountcontactpage.clickLoginButton();
		test.log(LogStatus.INFO, "Select have a business idea on domain information page ");
		mitregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page ");
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		
		// Test Step 5: if array of tlds contains .au domains, enter au eligibility details
		if(Arrays.asList(arrTldsForReg).contains("com.au") || Arrays.asList(arrTldsForReg).contains("net.au"))
		{
			miteligibilitydetailspage = new MITEligibilityDetailsPage();
			test.log(LogStatus.INFO, "Entering eligibility details "+strTld);
			miteligibilitydetailspage.setEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
			test.log(LogStatus.INFO, "Tick on terms and conditions "+strTld);
			miteligibilitydetailspage.tickCertifyDomainHasCloseAndSubstantialConnection();
			miteligibilitydetailspage.tickTermsAndConditions();
			test.log(LogStatus.INFO, "Click on continue button on eligibility details page "+strTld);
			miteligibilitydetailspage.clickContinueButton();
		}
		
		//Test Step 6: Input credit card details and submit the order
		test.log(LogStatus.INFO, "Entering credit card information ");
		mitbillingpage.selectExistingCreditCardOption(strCardType);
		test.log(LogStatus.INFO, "Tick on terms and conditions "+strTld);
		mitbillingpage.tickTermsAndConditions();
        test.log(LogStatus.INFO, "Click on continue button on billing page ");
        mitordercompletepage = mitbillingpage.clickContinueButton();
        
        //Test Step 3: Verify if the order is completed, get workflow id and account reference
       	test.log(LogStatus.INFO, "Verify if the order is completed -STARTED");
		Assert.assertTrue(mitordercompletepage.isOrderComplete(), "Order is not completed");
		lstWorkflowId = mitordercompletepage.getMultipleReferenceID();
		strAccountReference = mitordercompletepage.getAccountReferenceID();
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
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
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
	public void generateARenewalWorkflowForDomainsFromConsoleAdmin(String environment,int numberOfDomains) throws InterruptedException, IOException
	{
		strCustomerAccountReference = PropertyFileOperations.readProperties("mit_Greencode");
		strDomainName = "Bulkdomainreg" + df.format(d);
			
		System.out.println("Start Test: generateARenewalWorkflowForDomainsFromConsoleAdmin");
		
		//Test Step 1: Login to shopping cart
		test.log(LogStatus.INFO, "NAvigate to shopping cart and place order");
		initialization(environment, "oldcart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		
		// Test Step 2: Navigate to Bulk Domain page and place order for multiple domains
		test.log(LogStatus.INFO, "Enter multiple domains to register");
		mitonlineorderpage.getMultipleDomainUrl();
		for(int i= 0; i < numberOfDomains ; i++)
	    {
	    	strTld = arrTldsForRenewal[i % arrTldsForRenewal.length];
			System.out.print(strTld + " "); 
			mitonlineorderpage.setMultipleDomainNameandTld(strDomainName.concat(Integer.toString(i)), strTld);
	    }
		mitdomainsearchpage = mitonlineorderpage.clickMultipleDomainSearchButton();
		
		// Test Step 3: Navigate to Web hosting page and click continue
		test.log(LogStatus.INFO, "Clicking on continue to checkout button ");
		mitadddomainprivacypage = mitdomainsearchpage.clickContinueToCheckout();
		mithostingandextraspage= mitadddomainprivacypage.clickNoThanks();
		test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page ");
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		
		// Test Step 4: Enter the existing customer details
		test.log(LogStatus.INFO, "Enter default customer information on account contact page  ");
		mitaccountcontactpage.setReturningCustomerContacts(strCustomerAccountReference,strCustomerPassword);
		mitregistrantcontactpage = mitaccountcontactpage.clickLoginButton();
		test.log(LogStatus.INFO, "Select have a business idea on domain information page ");
		mitregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page ");
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		
		// Test Step 5: if array of tlds contains .au domains, enter au eligibility details
		if(Arrays.asList(arrTldsForRenewal).contains("com.au") || Arrays.asList(arrTldsForRenewal).contains("net.au"))
		{
			miteligibilitydetailspage = new MITEligibilityDetailsPage();
			test.log(LogStatus.INFO, "Entering eligibility details "+strTld);
			miteligibilitydetailspage.setEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
			test.log(LogStatus.INFO, "Tick on terms and conditions "+strTld);
			miteligibilitydetailspage.tickCertifyDomainHasCloseAndSubstantialConnection();
			miteligibilitydetailspage.tickTermsAndConditions();
			test.log(LogStatus.INFO, "Click on continue button on eligibility details page "+strTld);
			miteligibilitydetailspage.clickContinueButton();
		}
		
		//Test Step 6: Input credit card details and submit the order
		test.log(LogStatus.INFO, "Entering credit card information ");
		mitbillingpage.selectExistingCreditCardOption(strCardType);
		test.log(LogStatus.INFO, "Tick on terms and conditions "+strTld);
		mitbillingpage.tickTermsAndConditions();
        test.log(LogStatus.INFO, "Click on continue button on billing page ");
        mitordercompletepage = mitbillingpage.clickContinueButton();
        
        //Test Step 3: Verify if the order is completed, get workflow id and account reference
       	test.log(LogStatus.INFO, "Verify if the order is completed -STARTED");
		Assert.assertTrue(mitordercompletepage.isOrderComplete(), "Order is not completed");
		lstWorkflowId = mitordercompletepage.getMultipleReferenceID();
		strAccountReference = mitordercompletepage.getAccountReferenceID();
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
				System.out.println("Domain registration failed for RefId: "+ refID+"Cant generate renewal workflow");
				test.log(LogStatus.INFO,"Domain registration failed for RefId: "+ refID+"Cant generate renewal workflow");
			}
		}
		softAssert.assertAll();
		driver.quit();
		System.out.println("End Test: generateARenewalWorkflowForDomainsFromConsoleAdmin");
	}
}
