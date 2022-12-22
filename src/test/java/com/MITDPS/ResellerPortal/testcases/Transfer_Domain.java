package com.MITDPS.ResellerPortal.testcases;

import java.awt.AWTException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.commonfunctionality.MITDPS_CommonFunctionality;
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
import com.mitdpsresellerportal.pages.MITDPSTransferDomainsOrderCompletePage;
import com.mitdpsresellerportal.pages.MITDPSTransferDomainsPage;
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
import com.util.DatabaseConnection;
import com.util.TestUtil;

public class Transfer_Domain extends MITDPS_CommonFunctionality {

	// Reseller portal [ages
	MITDPSLoginPage mitdpsLoginPage;
	MITDPSTabPage mitdpsTabPage;
	MITDPSRegisterADomainPage mitdpsRegisterADomainPage;
	MITDPSTransferDomainsPage mitdpstransferdomainspage;
	MITDPSTransferDomainsOrderCompletePage mitdpstransferdomainsordercompletepage;
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
	CAEditDNSPage caeditdnspage;
	CSMUITabPage csmuitabpage;
	CSMUIDomainNamePage csmuidomainnamepage;

	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public Transfer_Domain() {
		super();
	}
	
	String strDomainName = null;
	String tld = null;
	String strTld;
	String strDomainPassword;
	String strRefrenceId;
	String strCardOwnerName = null;
	String strCardNumber = null;
    String strCardExpiryMonth = null;
    String strCardExpiryYear = null;
    String strCardSecurityCode = null;
    String strAccountReference = null;
	String strWorkflowStatus = null;
	String strCustomerAccountReference = null;
	String strCustomerPassword = null;
	String strEligibilityIDType = null;
	String strEligibilityIDNumber = null;
	String strEligibilityType = null;
	String strCompanyName = null;
	String strExistingCardNumber = null;
	String authCode = null;
	String transferWorkflowId = null;
	
	@Parameters({ "environment" })
	@Test
	public void verifyDomainTransferInResellerPortal(String environment) throws Exception {

		// Initialization (Test Data Creation and Assignment)
		 strAccountReference = "MEL-6040";
		 String strPassword="comein@22";
		 authCode = "m!p%jKjY3I";
		 strDomainName = "testdomainreg54656363";
		 tld = ".com";
	
		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal");
		System.out.println("Start Test: verifyDomainRegistrationInResellerPortal");
		initialization(environment, "resellerportalurl_mitdps");
		logintoResellerPortal(strAccountReference,strPassword);

		// Test Step 2: Navigate to Register Domain
		test.log(LogStatus.INFO, "Navigate to Domains then Register and search for a domain");
		mitdpsTabPage.clickDomainsTab();
		mitdpstransferdomainspage = mitdpsTabPage.clickDomainTransferLink();
		mitdpstransferdomainspage.enterDomainPrefix(strDomainName);
		mitdpstransferdomainspage.selectDomainNamespace(tld);
		mitdpstransferdomainspage.enterAuthCode(authCode);
		mitdpstransferdomainspage.clickOnAddLink();
		mitdpstransferdomainspage.waitforDomainOkStatus();
		mitdpstransferdomainspage.selectDomainCheckBox();
		
		// Test Step 3: Complete transfer domain order
		test.log(LogStatus.INFO, "Complete domain transfer order and copy workflow ID");
		mitdpstransferdomainspage.checkTermsAndConditions();
		mitdpstransferdomainsordercompletepage = mitdpstransferdomainspage.clickOnTransferDomains();
		String domainName = mitdpstransferdomainsordercompletepage.getDomainNameWhichIsTransferred();
		Assert.assertEquals(domainName, strDomainName+".com", "Domain Name Is Transferred");
		transferWorkflowId = mitdpstransferdomainsordercompletepage.getWorkflowIdOfTransferredDomain();
		System.out.println(transferWorkflowId);
		//driver.quit();
		
	}
	
	@Parameters({ "environment"})
	@Test
	public void verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard(String environment) throws InterruptedException, AWTException
	{
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strWorkflowId = null;
		String tld = ".com";
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
			
		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
		
			initialization(environment, "oldcart_domainsearchurl_melbourneit");
			nrgonlineorderpage = new NRGOnlineOrderPage();
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED "+tld);
			test.log(LogStatus.INFO, "Clearing default tld selections");
			nrgonlineorderpage.clearDefaultTldSelections();
			if(tld==".co.nz") {
				nrgonlineorderpage.selectNzTld();
			}
			test.log(LogStatus.INFO, "Enter domain name and select Tld "+tld);
			nrgonlineorderpage.setDomainNameAndTld(strDomainName, tld);
			test.log(LogStatus.INFO, "Clicking on search button "+tld);
			nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
			test.log(LogStatus.INFO, "Clicking on continue to checkout button "+tld);
			nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
			nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
			test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tld);
			nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
			test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tld);
			nrgaccountcontactpage.setReturningCustomerContacts("MEL-6005", "comein22");
			nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
			test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tld);
			nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tld);
			nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
			if(tld==".com.au") {
				nrgeligibilitydetailspage = new NRGEligibilityDetailsPage();
				test.log(LogStatus.INFO, "Entering eligibility details "+tld);
				nrgeligibilitydetailspage.setEligibilityDetails("ABN", "21073716793", "Arq Group Limited", "Company");
				test.log(LogStatus.INFO, "Tick on terms and conditions "+tld);
				nrgeligibilitydetailspage.tickCertifyDomainHasCloseAndSubstantialConnection();
				nrgeligibilitydetailspage.tickTermsAndConditions();
				test.log(LogStatus.INFO, "Click on continue button on eligibility details page "+tld);
				nrgeligibilitydetailspage.clickContinueButton();
			}
			//Test Step 2: Input credit card details and submit the order 
			nrgbillingpage.selectExistingCreditCardOption("Number: 4111********1111 Expiry: 02/2021");
			test.log(LogStatus.INFO, "Tick on terms and conditions "+tld);
	        nrgbillingpage.tickTermsAndConditions();
	        test.log(LogStatus.INFO, "Click on continue button on billing page "+tld);
	        nrgordercompletepage = nrgbillingpage.clickContinueButton();
	        strWorkflowId = nrgordercompletepage.getSingleReferenceID();
			strAccountReference = nrgordercompletepage.getAccountReferenceID();
			System.out.println("Account Reference:" + strAccountReference);	
			System.out.println("Reference ID[0]:" + strWorkflowId);	
			driver.quit();
			
			//Test step 3: Login to Console admin
			initialization(environment, "consoleadmin");
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.setDefaultLoginDetails(environment);
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			caworkflowadminpage.processWF(strWorkflowId, tld);
			
			// Test Step 4: Verify if domain registration workflow is completed
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caheaderpage.searchWorkflow(strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
			driver.quit();
			
			
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
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
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
	 * Test case to transfer a domain from Reseller portal and verify Verify autransfer2WF workflow
	 * Workflow : autransfer2WF
	 */
	@Parameters({ "environment" })
	@Test(dependsOnMethods = "getTestDataForautransfer2")
	public void transferDomainFromResellerPortalAndVerifyautransfer2WF(String environment) throws Exception
	{
		strCustomerAccountReference = "MEL-6040";
		strCustomerPassword = "comein22";
		
		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal - STARTED");
		initialization(environment, "resellerportalurl_mitdps");
		logintoResellerPortal(strAccountReference,strCustomerPassword);
				
		//Test Step 2: Navigate to Register Domain
		test.log(LogStatus.INFO, "Navigate to Domains then Register and search for a domain");
		mitdpsTabPage.clickDomainsTab();
		mitdpstransferdomainspage = mitdpsTabPage.clickDomainTransferLink();
		mitdpstransferdomainspage.enterDomainPrefix(strDomainName);
		mitdpstransferdomainspage.selectDomainNamespace(strTld);
		mitdpstransferdomainspage.enterAuthCode(strDomainPassword);
		mitdpstransferdomainspage.clickOnAddLink();
		mitdpstransferdomainspage.waitforDomainOkStatus();
		mitdpstransferdomainspage.selectDomainCheckBox();
		
		// Test Step 3: Complete transfer domain order
		test.log(LogStatus.INFO, "Complete domain transfer order and copy workflow ID");
		mitdpstransferdomainspage.checkTermsAndConditions();
		mitdpstransferdomainsordercompletepage = mitdpstransferdomainspage.clickOnTransferDomains();
		String domainName = mitdpstransferdomainsordercompletepage.getDomainNameWhichIsTransferred();
		Assert.assertEquals(domainName, strDomainName+".com", "Domain Name Is Transferred");
		strRefrenceId = mitdpstransferdomainsordercompletepage.getWorkflowIdOfTransferredDomain();
		System.out.println(strRefrenceId);
		driver.quit();
		
		//Test Step 4: Execute the autransfer2 workflow in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(strRefrenceId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("auTransfer2");
		test.log(LogStatus.INFO, "The autransfer2WF workflow is in step " +strWorkflowStatus);
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("pending"));
		

		//Test Step 5: Update tld_id in the database
		DatabaseConnection.connectToDatabase();
		DatabaseConnection.updateTldId(strDomainName+strTld);
		driver.quit();
	}
}
