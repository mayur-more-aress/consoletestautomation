package com.NRG.OldShoppingCart.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.commonfunctionality.NRGOld_CommonFunctionality;
import com.consoleadmin.pages.CADomainLevelPage;
import com.consoleadmin.pages.CAEditDNSPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.consolesmui.pages.CSMUIDomainNamePage;
import com.consolesmui.pages.CSMUITabPage;
import com.mitdpsresellerportal.pages.MITDPSLoginPage;
import com.mitdpsresellerportal.pages.MITDPSRegisterADomainPage;
import com.mitdpsresellerportal.pages.MITDPSTabPage;
import com.netregistryoldwebsite.pages.NRGAccountContactPage;
import com.netregistryoldwebsite.pages.NRGAddDomainToTransferPage;
import com.netregistryoldwebsite.pages.NRGBillingPage;
import com.netregistryoldwebsite.pages.NRGDomainTransferPage;
import com.netregistryoldwebsite.pages.NRGEligibilityDetailsPage;
import com.netregistryoldwebsite.pages.NRGOnlineOrderPage;
import com.netregistryoldwebsite.pages.NRGOrderCompletePage;
import com.netregistryoldwebsite.pages.NRGRegistrantContactPage;
import com.relevantcodes.extentreports.LogStatus;
import com.util.DatabaseConnection;

public class Domain_Transfer extends NRGOld_CommonFunctionality
{
	public Domain_Transfer() 
	{
		super();
	}
	
	//Netregistry Customer portal pages
	NRGOnlineOrderPage nrgonlineorderpage;
	NRGAccountContactPage nrgaccountcontactpage;
	NRGRegistrantContactPage nrgregistrantcontactpage;
	NRGBillingPage nrgbillingpage;
	NRGOrderCompletePage nrgordercompletepage;
	NRGEligibilityDetailsPage nrgeligibilitydetailspage;
	NRGDomainTransferPage nrgdomaintransferpage;
	NRGAddDomainToTransferPage nrgadddomaintotransferpage;
	NRGOld_CommonFunctionality nrgcommonfunctionality;
	
	// Reseller portal pages
	MITDPSLoginPage mitdpsLoginPage;
	MITDPSTabPage mitdpsTabPage;
	MITDPSRegisterADomainPage mitdpsRegisterADomainPage;
		
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	CADomainLevelPage cadomainlevelpage;
	CAEditDNSPage caeditdnspage;
	CSMUITabPage csmuitabpage;
	CSMUIDomainNamePage csmuidomainnamepage;
		
	// Initialization (Test Data Assignment)
	String strDomainName ;
	String strTld;
	String strDomainPassword;
	String strCustomerAccountReference;
	String strCustomerPassword;
	String strRefrenceId;
	String strAccountReference;
	String strWorkflowStatus = null;
	String strEligibilityIDType = null;
	String strEligibilityIDNumber = null;
	String strEligibilityType = null;
	String strCompanyName = null;
	
	/*
	 * Test case to register domain using existing customer and existing card via DPS reseller portal  
	 */
	@Parameters({ "environment" })
	@Test
	public void verifyDomainRegistrationInResellerPortal(String environment) throws Exception 
	{

		// Initialization (Test Data Creation and Assignment)
		strTld = ".com";
		strCustomerAccountReference = "DPS-34";
		strCustomerPassword = "comein22";
		strEligibilityIDType = "ABN";
		strEligibilityIDNumber = "54 109 565 095";
		strEligibilityType = "Company";
		strCompanyName="ARQ GROUP WHOLESALE PTY LTD";
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestDomainTransfer" + df.format(d);
		System.out.println("Start Test: verifyDomainRegistrationInResellerPortal for "+strTld+ " domain  - STARTED");
		test.log(LogStatus.INFO, "Verify domain registration from DPS Reseller for "+strTld+ " domain - STARTED"); 	
	   
		
		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal");
		initialization(environment, "resellerportalurl_mitdps");
		mitdpsLoginPage = new MITDPSLoginPage();
		mitdpsLoginPage.setLoginDetails(strCustomerAccountReference,strCustomerPassword);
		mitdpsTabPage = mitdpsLoginPage.clickLoginButton();

		// Test Step 2: Navigate to Register Domain
		test.log(LogStatus.INFO, "Navigate to Domains then Register and search for a domain");
		mitdpsTabPage.clickDomainsTab();
		mitdpsRegisterADomainPage = mitdpsTabPage.clickRegisterLink();

		// Test Step 3: Register a domain
		test.log(LogStatus.INFO, "Verify search result message");
		mitdpsRegisterADomainPage.setDomainNameAndTld(strDomainName, strTld);
		mitdpsRegisterADomainPage.selectExistingCustomer();
		mitdpsRegisterADomainPage.selectRegistranContact("Tim Coupland");

		if (strTld.contains(".au")) 
		{
			System.out.println("This is the namespace " + strTld + ". Eligibility details is requied.");
			mitdpsRegisterADomainPage.provideEligibilityDetailsForAu(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
		}

		mitdpsRegisterADomainPage.tickNameServerOptions("Choose your nameservers");
		mitdpsRegisterADomainPage.inputNameServerFields("ns1.partnerconsole.net", "ns2.partnerconsole.net");
		mitdpsRegisterADomainPage.tickTermsAndConditions();
		mitdpsRegisterADomainPage.clickRegisterDomainButton();

		// Test Step 4: Get the Order Reference ID
		test.log(LogStatus.INFO, "Verify if order is completed and get the reference ID");
		Boolean isOrderCompleted = mitdpsRegisterADomainPage.isOrderComplete("domain order for " + strDomainName+strTld + " created.");
		Assert.assertTrue(isOrderCompleted);
		strRefrenceId = mitdpsRegisterADomainPage.getSingleReferenceID();
		System.out.println("Reference ID:" + strRefrenceId);
		driver.quit();
		
		// Test Step 5: Process the domain registration order in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(strRefrenceId);
		caworkflowadminpage.processWorkFlow(strRefrenceId, strTld);

		// Test Step 6: Verify if domain registration workflow is completed
		test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
		caworkflowadminpage = caheaderpage.searchWorkflow(strRefrenceId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		cadomainlevelpage=caheaderpage.searchDomainName(strDomainName+strTld);
		caeditdnspage = cadomainlevelpage.clickEditDNSLink();
		strDomainPassword = caeditdnspage.getDomainEppPassowrd();
		System.out.println("Doamin epp Passowrd: " + strDomainPassword);
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed")|| strWorkflowStatus.equalsIgnoreCase("update star rating"));
		
		driver.quit();
		System.out.println("End Test: verifyDomainRegistrationInResellerPortal for "+strTld+ " domain  - COMPLETED");
		test.log(LogStatus.INFO, "Verify domain registration from DPS Reseller for "+strTld+ " domain - COMPLETED"); 		   
		
	}
	
	/*
	 * Test case to get the test data for autransfer 
	 */
	@Parameters({ "environment" })
	@Test
	public void getTestDataForautransfer2(String environment) throws Exception
	{
		System.out.println("Start Test: getTestDataForautransfer2 - STARTED");
		test.log(LogStatus.INFO, "Fetch the test data for autransfer2  - STARTED"); 	
		
		//Test Step 1: Get the domain available for transfer from database
		test.log(LogStatus.INFO, "Fetch the test data from the database - STARTED");
		DatabaseConnection.connectToDatabase();
		String testdomianName = DatabaseConnection.getTestdoaminForauTransfer();
		System.out.println("Domain Name: " + testdomianName);
		String testdata[]=testdomianName.split("\\.",2);
		strDomainName = testdata[0];
		System.out.println("Domain Name: " + strDomainName);
		strTld = "."+testdata[1];
		System.out.println("Domain tld: " + strTld);
		test.log(LogStatus.INFO, "Fetch the test data from the database - COMPLETED");
		
		//Test Step 2: Get the epp password of the domain
		test.log(LogStatus.INFO, "Fetch the epp password in console admin");
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		cadomainlevelpage=caheaderpage.searchDomainName(strDomainName+strTld);
		cadomainlevelpage = new CADomainLevelPage();
		csmuitabpage = cadomainlevelpage.clickloginAsClientLink();
		csmuidomainnamepage = csmuitabpage.clickDomainNameTab();
		strDomainPassword = csmuidomainnamepage.getDomainPassword();
		System.out.println("Doamin epp Passowrd: " + strDomainPassword);
		
		driver.quit();
		System.out.println("End Test: getTestDataForautransfer2 - COMPLETED");
		test.log(LogStatus.INFO, "Fetch the test data for autransfer2  - COMPLETED"); 	
		
	}
	
	/*
	 * Test case to transfer a domain from customer portal and verify Verify transferral2 workflow
	 * Workflow : transferral2 workflow
	 */
	@Parameters({ "environment" })
	@Test(dependsOnMethods = "verifyDomainRegistrationInResellerPortal")
	public void transferDomainFromCustomerPortalAndVerifytransferral2WF(String environment) throws Exception
	{
		strCustomerAccountReference ="MEL-6007";
		strCustomerPassword = "comein22";
		
    	System.out.println("Start Test: transferDomainFromCustomerPortalAndVerifytransferral2WF - STARTED");
		test.log(LogStatus.INFO, "Transfer Domain From CustomerPortal For ExistingCustomer - STARTED"); 	
	   
		// Test Step 1: Navigate to domain transfer URL
		initialization(environment, "oldcart_domainsearchurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		test.log(LogStatus.INFO, "Navigate to domain transfer page -STARTED ");
		nrgonlineorderpage.getDomainTransferUrl();
		
		// Test Step 2: Enter domain details and add domain to cart
		nrgdomaintransferpage = new NRGDomainTransferPage();
		nrgdomaintransferpage.setDomainNameAndTld(strDomainName,strTld);
		nrgdomaintransferpage.setDomainPassword(strDomainPassword);
		nrgadddomaintotransferpage = nrgdomaintransferpage.clickTransferButton();
		nrgaccountcontactpage = nrgadddomaintotransferpage.addDomainToCart();
		
		// Test Step 3: Login as returning customer
		nrgaccountcontactpage.setReturningCustomerContacts(strCustomerAccountReference,strCustomerPassword);
		nrgregistrantcontactpage=nrgaccountcontactpage.clickLoginButton();
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		// Test Step 4: Select the existing card
		test.log(LogStatus.INFO, "Tick on terms and conditions ");
		nrgbillingpage.tickTermsAndConditions();
        test.log(LogStatus.INFO, "Click on continue button on billing page ");
        nrgordercompletepage = nrgbillingpage.clickContinueButton();
        
        //Test Step 5: Verify if the order is completed, get workflow id and account reference.
        fetchRefrenceAndWorkflowId();
		driver.quit();
		
		//Test Step 6: Execute the transferral2 workflow in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
		String strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("transferral2");
		test.log(LogStatus.INFO, "The transferral2 workflow is in step " +strWorkflowStatus);
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("transfer requested") || strWorkflowStatus.equalsIgnoreCase("approved"));
		
		driver.quit();
		System.out.println("End Test: transferDomainFromCustomerPortalAndVerifytransferral2WF - COMPLETED");
		test.log(LogStatus.INFO, "Transfer Domain From CustomerPortal For ExistingCustomer - COMPLETED");
	}
	
	/*
	 * Test case to transfer a domain from customer portal and verify Verify icannTransfer2WF workflow
	 * Workflow : icannTransfer2 workflow
	 */
	@Parameters({ "environment" })
	@Test(dependsOnMethods = "verifyDomainRegistrationInResellerPortal")
	public void transferDomainFromCustomerPortalAndVerifyicannTransfer2WF(String environment) throws Exception
	{
		strCustomerAccountReference ="MEL-6007";
		strCustomerPassword = "comein22";
		
    	System.out.println("Start Test: transferDomainFromCustomerPortalAndVerifyicannTransfer2WF - STARTED");
		test.log(LogStatus.INFO, "Transfer Domain From CustomerPortal For ExistingCustomer - STARTED"); 	
	   
		// Test Step 1: Navigate to domain transfer URL
		initialization(environment, "oldcart_domainsearchurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		test.log(LogStatus.INFO, "Navigate to domain transfer page -STARTED ");
		nrgonlineorderpage.getDomainTransferUrl();
		
		// Test Step 2: Enter domain details and add domain to cart
		nrgdomaintransferpage = new NRGDomainTransferPage();
		nrgdomaintransferpage.setDomainNameAndTld(strDomainName,strTld);
		nrgdomaintransferpage.setDomainPassword(strDomainPassword);
		nrgadddomaintotransferpage = nrgdomaintransferpage.clickTransferButton();
		nrgaccountcontactpage = nrgadddomaintotransferpage.addDomainToCart();
		
		// Test Step 3: Login as returning customer
		nrgaccountcontactpage.setReturningCustomerContacts(strCustomerAccountReference,strCustomerPassword);
		nrgregistrantcontactpage=nrgaccountcontactpage.clickLoginButton();
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		// Test Step 4: Select the existing card
		test.log(LogStatus.INFO, "Tick on terms and conditions ");
		nrgbillingpage.tickTermsAndConditions();
        test.log(LogStatus.INFO, "Click on continue button on billing page ");
        nrgordercompletepage = nrgbillingpage.clickContinueButton();
        
        //Test Step 5: Verify if the order is completed, get workflow id and account reference.
        fetchRefrenceAndWorkflowId();
		driver.quit();
		
		//Test Step 6: Execute the icannTransfer2 workflow in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("icannTransfer2");
		if(strWorkflowStatus.equalsIgnoreCase("pending"))
		{
			caworkflowadminpage.processicannTransfer2WorkFlow(NRGOld_CommonFunctionality.strWorkflowId);
			caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("icannTransfer2");
			test.log(LogStatus.INFO, "The icannTransfer2 workflow is in step " +strWorkflowStatus);
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("approved"));
		}
		else
		{
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("approved"));
		}
	
		driver.quit();
		System.out.println("End Test: transferDomainFromCustomerPortalAndVerifyicannTransfer2WF  - STARTED");
		test.log(LogStatus.INFO, "Transfer Domain From CustomerPortal For ExistingCustomer - COMPLETED"); 		
	}
	
	/*
	 * Test case to transfer a domain from customer portal and verify Verify autransfer2 workflow
	 * Workflow : autransfer2 workflow
	 */
	@Parameters({ "environment" })
	@Test(dependsOnMethods = "getTestDataForautransfer2")
	public void transferDomainFromCustomerPortalAndVerifyautransfer2WF(String environment) throws Exception
	{
		strCustomerAccountReference ="MEL-6007";
		strCustomerPassword = "comein22";
		
    	System.out.println("Start Test: transferDomainFromCustomerPortalAndVerifyicannTransfer2WF - STARTED");
		test.log(LogStatus.INFO, "Transfer Domain From CustomerPortal For ExistingCustomer - STARTED"); 	
	   
		// Test Step 1: Navigate to domain transfer URL
		initialization(environment, "oldcart_domainsearchurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		test.log(LogStatus.INFO, "Navigate to domain transfer page -STARTED ");
		nrgonlineorderpage.getDomainTransferUrl();
		
		// Test Step 2: Enter domain details and add domain to cart
		nrgdomaintransferpage = new NRGDomainTransferPage();
		nrgdomaintransferpage.setDomainNameAndTld(strDomainName,strTld);
		nrgdomaintransferpage.setDomainPassword(strDomainPassword);
		nrgadddomaintotransferpage = nrgdomaintransferpage.clickTransferButton();
		nrgaccountcontactpage = nrgadddomaintotransferpage.addDomainToCart();
		
		// Test Step 3: Login as returning customer
		nrgaccountcontactpage.setReturningCustomerContacts(strCustomerAccountReference,strCustomerPassword);
		nrgregistrantcontactpage=nrgaccountcontactpage.clickLoginButton();
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		// Test Step 4: Select the existing card
		test.log(LogStatus.INFO, "Tick on terms and conditions ");
		nrgbillingpage.tickTermsAndConditions();
        test.log(LogStatus.INFO, "Click on continue button on billing page ");
        nrgordercompletepage = nrgbillingpage.clickContinueButton();
        
        //Test Step 5: Verify if the order is completed, get workflow id and account reference.
        fetchRefrenceAndWorkflowId();
		driver.quit();
		
		//Test Step 6: Execute the autransfer2 workflow in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("auTransfer2");
		test.log(LogStatus.INFO, "The icannTransfer2 workflow is in step " +strWorkflowStatus);
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("pending"));
		
		driver.quit();
		System.out.println("End Test: transferDomainFromCustomerPortalAndVerifyicannTransfer2WF  - COMPLETED");
		test.log(LogStatus.INFO, "Transfer Domain From CustomerPortal For ExistingCustomer - COMPLETED"); 		
	}
}
