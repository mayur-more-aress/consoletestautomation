package com.NRG.OldShoppingCart.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import com.netregistryoldwebsite.pages.NRGEligibilityDetailsPage;
import com.netregistryoldwebsite.pages.NRGHostingAndExtrasPage;
import com.netregistryoldwebsite.pages.NRGOnlineOrderPage;
import com.netregistryoldwebsite.pages.NRGOrderCompletePage;
import com.netregistryoldwebsite.pages.NRGRegistrantContactPage;
import com.netregistryoldwebsite.pages.NRGWebHostingPage;
import com.obsidian.pages.ObsJobHistoryPage;
import com.obsidian.pages.ObsJobsPage;
import com.obsidian.pages.ObsLoginPage;
import com.obsidian.pages.ObsTabPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.DatabaseConnection;
import com.util.TestUtil;

public class ProductRenewal extends NRGOld_CommonFunctionality
{
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
	NRGOld_CommonFunctionality nrgcommonfunctionality;
	
	//Obsidian pages
	ObsLoginPage obsloginpage;
	ObsTabPage obstabpage;
	ObsJobsPage obsjobspage;
	ObsJobHistoryPage obsjobhistorypage;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;	
	TestUtil testUtil;
	DatabaseConnection dbconnect;
	String clienttoken;
	public static ExtentTest logger;
	
	public ProductRenewal() 
	{
		super();
	}
	
	// Initialization (Test Data Creation and Assignment)
	String strDomainName ;
	String strExistingCardNumber = null;
	String strCustomerAccountReference;
	String strAccountReference ;
	String strWorkflowId ;
	String strCompanyName = null;
	String strCustomerPassword ;
	String strEligibilityIDType = null;
	String strEligibilityIDNumber = null;
	String strEligibilityType = null;
	String strTld ;
	String strProductYear ;
	String obsUserName = null;
	String obsPassword = null;
	String strJobName = null;
	String strProductExpiryDate = null;
	String strProductCode = null;
	String Domaininformation ="Have a business idea and reserving a domain for the future";
	String arrTlds[] = {".com"}; 
	List<String> privacytlds = Arrays.asList(".com", ".net");
	
	/*
	 * Test case to verify domain registration and product order for Existing Customer Scenario Using Existing card
	 */
	@Parameters({ "environment" })
	@Test
	public void verifyDomainRegistrationAndProductForExistingCustomerUsingExistingCard(String environment) throws Exception 
	{
		strEligibilityIDType = "ABN";
		strEligibilityIDNumber = "21073716793";
		strEligibilityType = "Company";
		strCompanyName="Arq Group Limited";
		strExistingCardNumber = "Visa Test Number: 4111********1111 Expiry: 01/2024"; 
		strCustomerAccountReference = "ARQ-45";
		strCustomerPassword = "comein22";
		strProductYear = "1 Month";
		
	    // Iterating over an array of tld's to be tested
	    for (String tld : arrTlds) 
	    { 	
	    	strTld = tld;
	        DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
			Date d = new Date();
			strDomainName = "TestConsoleRegression" + df.format(d);
	    	System.out.println("Start Test: verifyDomainRegistrationAndProductForExistingCustomerUsingExistingCard for "+strTld+ " domain - STARTED");
			test.log(LogStatus.INFO, "Verify Domain Registration And Product For ExistingCustomer Using ExistingCard for "+strTld+ " domain - STARTED"); 		
			
			// Test Step 1: Navigate to domain search page of shopping cart and place an order for a test domain
			initialization(environment, "oldcart_domainsearchurl_netregistry");
			nrgonlineorderpage = new NRGOnlineOrderPage();
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED "+strTld);
			test.log(LogStatus.INFO, "Clearing default tld selections");
			nrgonlineorderpage.clearDefaultTldSelections();
			test.log(LogStatus.INFO, "Enter domain name and select Tld "+strTld);
			nrgonlineorderpage.setDomainNameAndTld(strDomainName, strTld);
			test.log(LogStatus.INFO, "Clicking on search button "+strTld);
			nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
			test.log(LogStatus.INFO, "Clicking on continue to checkout button "+strTld);
			nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
			
			//Test Step 2: Uncheck Domain privacy page for tld's
			if(privacytlds.contains(strTld))
			{
				nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
			}
			
			//Test Step 3: Select product
			test.log(LogStatus.INFO, "Select monthly Product"+strTld);
			nrghostingandextraspage.clickWebHostingRadioButton();
			nrghostingandextraspage.addCPanelStarter(strProductYear);
			test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+strTld);
			nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
			
			//Test Step 4:  Enter returning customer details 
			setReturningCustomer(strCustomerAccountReference,strCustomerPassword);
			nrgregistrantcontactpage=new NRGRegistrantContactPage();
			test.log(LogStatus.INFO, "Select have a business idea on domain information page "+strTld);
			nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+strTld);
			nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
			
			//Test Step 5: ENter eleigibility details
			if(tld==".com.au") 
			{
				setEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber, "strCompanyName", "strEligibilityType");
			}
			
			//Test Step 6: Input credit card details and submit the order 
			selectExistingCard(strExistingCardNumber);;
	        
	        //Test Step 7: Verify if order is completed,get workflow and refrence id 
			fetchRefrenceAndWorkflowId();
			driver.quit();
			
			//Test Step 8: Login to console, execute and check the workflow status
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
			caworkflowadminpage.processWF(NRGOld_CommonFunctionality.strWorkflowId, strTld);
			caworkflowadminpage.processProductSetupWF();
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
			String strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
			driver.quit();
	    	
			System.out.println("End Test: verifyDomainRegistrationAndProductForExistingCustomerUsingExistingCard for "+strTld+ " domain  - COMPLETED");
			test.log(LogStatus.INFO, "Verify Domain Registration And Product For ExistingCustomer Using ExistingCard for "+strTld+ " domain - COMPLETED");
			
	    } 
	}
	
    /*
	 * Test case to verify monthly product renewal 
	 */
	@Parameters({ "environment" })
	@Test(dependsOnMethods = "verifyDomainRegistrationAndProductForExistingCustomerUsingExistingCard")
	public void verifyMonthlyProductRenewal(String environment) throws Exception
	{
		obsUserName = "admin";
		obsPassword = "comein22";
		strJobName = "RunProductRenewalJob";
		strProductCode = "CPANEL-STARTER";
		System.out.println("Start Test: verifyProductRenewal - STARTED");
		test.log(LogStatus.INFO, "Verify Product Renewal - STARTED"); 
		
		//Test step 1: Edit the product expiry date in the database
		test.log(LogStatus.INFO, "Edit the product expiry date in the database - STARTED");
		DatabaseConnection.connectToDatabase();
		obsjobspage = new ObsJobsPage();
		strProductExpiryDate = obsjobspage.generateExpiryDate();
		System.out.println("Expiry date" +strProductExpiryDate);
		DatabaseConnection.editProductExpiryDate(strProductExpiryDate,strProductCode,strCustomerAccountReference);
		test.log(LogStatus.INFO, "Edit the product expiry date in the database - COMPLETED");
		
		//Test Step 2: In obsidian Submit one-time run for the job
		test.log(LogStatus.INFO, "Submit one-time run for the job - STARTED");
		initialization(environment, "obsidian_url");
		obsloginpage = new ObsLoginPage();
		obsloginpage.setLoginDetails(obsUserName, obsPassword);
		obstabpage = obsloginpage.clickLoginButton();
		obsjobspage = obstabpage.clickJobsLink();
		obsjobspage.searchJob(strJobName);
		obsjobspage.generateExpiryDate();
		obsjobspage.submitOneTimeRunForTheJob(strJobName);
		test.log(LogStatus.INFO,  "Submit one-time run for the job - COMPLETED");
		
		//Test Step 3: In obsidian Check the status of job
		test.log(LogStatus.INFO, "Check the status of job - STARTED");
		obsjobhistorypage = obstabpage.clickJobHistoryLink();
		String strJobStatus = obsjobhistorypage.checkJobStatus(strJobName);
		Assert.assertTrue(strJobStatus.equalsIgnoreCase("Completed"));
		test.log(LogStatus.INFO,  "Check the status of job - COMPLETED");
		driver.quit();
		
		//Test Step 4: Check Whether the ProductRenewal workflow is created in console and check the status
		test.log(LogStatus.INFO, "Check Whether the ProductRenewal workflow is created in console and check the status - STARTED");
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName+strTld);
		caworkflowadminpage.processProductRenewalWF("monthlyProductRenewal");
		caheaderpage.searchWorkflow(strDomainName+strTld);
		String strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("monthlyProductRenewal");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("renewed"));

		test.log(LogStatus.INFO,  "Check Whether the ProductRenewal workflow is created in console and check the status - COMPLETED");
		driver.quit();	
		System.out.println("End Test: verifyProductRenewal - COMPLETED");
		test.log(LogStatus.INFO, "Verify Product Renewal - COMPLETED");
	}
	
	 /*
	 * Test case to verify yearly product renewal 
	 */
	@Parameters({ "environment" })
	@Test(dependsOnMethods = "verifyDomainRegistrationAndProductForExistingCustomerUsingExistingCard")
	public void verifyYearlyProductRenewal(String environment) throws Exception
	{
		obsUserName = "admin";
		obsPassword = "comein22";
		strJobName = "RunProductRenewalJob";
		strProductCode = "CPANEL-STARTER";
		System.out.println("Start Test: verifyProductRenewal - STARTED");
		test.log(LogStatus.INFO, "Verify Product Renewal - STARTED"); 
		
		//Test step 1: Edit the product expiry date in the database
		test.log(LogStatus.INFO, "Edit the product expiry date in the database - STARTED");
		DatabaseConnection.connectToDatabase();
		obsjobspage = new ObsJobsPage();
		strProductExpiryDate = obsjobspage.generateExpiryDate();
		System.out.println("Expiry date" +strProductExpiryDate);
		DatabaseConnection.editProductExpiryDate(strProductExpiryDate,strProductCode,strCustomerAccountReference);
		test.log(LogStatus.INFO, "Edit the product expiry date in the database - COMPLETED");
		
		//Test Step 2: In obsidian Submit one-time run for the job
		test.log(LogStatus.INFO, "Submit one-time run for the job - STARTED");
		initialization(environment, "obsidian_url");
		obsloginpage = new ObsLoginPage();
		obsloginpage.setLoginDetails(obsUserName, obsPassword);
		obstabpage = obsloginpage.clickLoginButton();
		obsjobspage = obstabpage.clickJobsLink();
		obsjobspage.searchJob(strJobName);
		obsjobspage.generateExpiryDate();
		obsjobspage.submitOneTimeRunForTheJob(strJobName);
		test.log(LogStatus.INFO,  "Submit one-time run for the job - COMPLETED");
		
		//Test Step 3: In obsidian Check the status of job
		test.log(LogStatus.INFO, "Check the status of job - STARTED");
		obsjobhistorypage = obstabpage.clickJobHistoryLink();
		String strJobStatus = obsjobhistorypage.checkJobStatus(strJobName);
		Assert.assertTrue(strJobStatus.equalsIgnoreCase("Completed"));
		test.log(LogStatus.INFO,  "Check the status of job - COMPLETED");
		driver.quit();
		
		//Test Step 4: Check Whether the ProductRenewal workflow is created in console and check the status
		test.log(LogStatus.INFO, "Check Whether the ProductRenewal workflow is created in console and check the status - STARTED");
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName+strTld);
		caworkflowadminpage.processProductRenewalWF("productRenewal");
		caheaderpage.searchWorkflow(strDomainName+strTld);
		String strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("productRenewal");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("renewed"));
	
		test.log(LogStatus.INFO,  "Check Whether the ProductRenewal workflow is created in console and check the status - COMPLETED");
		driver.quit();
		System.out.println("End Test: verifyProductRenewal - COMPLETED");
		test.log(LogStatus.INFO, "Verify Product Renewal - COMPLETED");
	}
}
