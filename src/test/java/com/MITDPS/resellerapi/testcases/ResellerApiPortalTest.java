package com.MITDPS.resellerapi.testcases;
import java.awt.AWTException;
import java.io.IOException;
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
import com.mitdps.resellerapi.pages.ResellerApiPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.DatabaseConnection;
import com.util.PropertyFileOperations;

public class ResellerApiPortalTest extends MITDPS_CommonFunctionality{

	ResellerApiPage resellerapipage;

	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
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
	CADomainLevelPage cadomainlevelpage;
	CAEditDNSPage caeditdnspage;
	CSMUITabPage csmuitabpage;
	CSMUIDomainNamePage csmuidomainnamepage;
	
	public static ExtentTest logger;
	String strResponse;
	String strContact;
	String strDomainName=null;
	String strDomainPeriod=null;
	String strWorkflowStatus;
	String strResponseDomainRegistration;
	String strWorkflowId;
	String registerdomain;
	String strResponseDomainRenewal;
	String workflowStatusAfterCompletionManually = null;
	String workflowStatusAfterCompletionBySchedular = null;
	String strTld = null;
	String strRenewWorkflowId;
	String strRegisterDomain;
	String strResponseDomainTransfer;
	String strTransferWorkflowId;
	
	String strDomainPassword;
	String strAuDomainName;
	String strResponseWhoisDomain;
	String strResponseDomainLock;
	String strResponseCreateNameServer;
	String strResponseChangeNameServer;
	String strResponseDeleteNameServer;
	String strResponseRegistrantNameChange;

	public ResellerApiPortalTest() {
		super();
	}

	/* Test Case to create the session */
	@Parameters({ "environment"})
	@Test
	public void verifyCreateSession(String environment) throws InterruptedException, IOException{

		//Initialization (Test Data Creation and Assignment)
		String strAccountNumber = PropertyFileOperations.readApiProperty("strAccountNumber");
		String strUserld =  PropertyFileOperations.readApiProperty("strUserld");
		String strPassword =  PropertyFileOperations.readApiProperty("strPassword");

		initialization(environment, "resellerapiportalurl_mitdps");
		resellerapipage = new ResellerApiPage();
		test.log(LogStatus.INFO, "Navigate to reseller api page");

		//Test Step 1: Enter the Authentication details 
		test.log(LogStatus.INFO, "Entering the account number");
		resellerapipage.enterAccountNumber(strAccountNumber);
		test.log(LogStatus.INFO, "Entering the User Id");
		resellerapipage.enterUserID(strUserld);
		test.log(LogStatus.INFO, "Entering the Password");
		resellerapipage.enterPassword(strPassword);

		//Test Step 2: Submit the Authentication details 
		test.log(LogStatus.INFO, "Clicking on submit button");
		resellerapipage.clickSubmitButton();

		//Test Step 3: Verify that session is created
		test.log(LogStatus.INFO, "Verify that session id is created");
		resellerapipage.verifyStatus();
		Thread.sleep(3000);
	}

	/* Test Case to create the contact details */
	@Parameters({ "environment"})
	@Test
	public void verifyCreateConatctDetails(String environment) throws InterruptedException, IOException {

		//Initialization (Test Data Creation and Assignment)
		String strOrgnisationName = PropertyFileOperations.readApiProperty("strOrgnisationName");
		String strFirstName= PropertyFileOperations.readApiProperty("strFirstName");
		String strLastName=  PropertyFileOperations.readApiProperty("strLastName");
		String strAddress1= PropertyFileOperations.readApiProperty("strAddress1");
		String strCity= PropertyFileOperations.readApiProperty("strCity");
		String strRegion= PropertyFileOperations.readApiProperty("strRegion");
		String strPostalCode= PropertyFileOperations.readApiProperty("strPostalCode");
		String strPhoneCountryCode= PropertyFileOperations.readApiProperty("strPhoneCountryCode");
		String strPhoneAreaCode= PropertyFileOperations.readApiProperty("strPhoneAreaCode");
		String strPhoneNumber= PropertyFileOperations.readApiProperty("strPhoneNumber");
		String strEmail= PropertyFileOperations.readApiProperty("strEmail");

		//Test Step 1 : Create Session ID
		verifyCreateSession(environment);

		//Test Step 2 : Enter the Contact Details
		test.log(LogStatus.INFO, "Entering the organisation name");
		resellerapipage.enterOrganisationName(strOrgnisationName);
		test.log(LogStatus.INFO, "Entering the first name");
		resellerapipage.enterFirstName(strFirstName);
		test.log(LogStatus.INFO, "Entering the last name");
		resellerapipage.enterLastName(strLastName);
		test.log(LogStatus.INFO, "Entering the address1");
		resellerapipage.enterAddress1(strAddress1);
		test.log(LogStatus.INFO, "Entering the city");
		resellerapipage.enterCity(strCity);
		test.log(LogStatus.INFO, "Entering the region");
		resellerapipage.enterRegion(strRegion);
		test.log(LogStatus.INFO, "Entering the postal code");
		resellerapipage.enterPostalCode(strPostalCode);
		test.log(LogStatus.INFO, "Entering the phone");
		resellerapipage.enterPhone(strPhoneCountryCode, strPhoneAreaCode, strPhoneNumber);
		test.log(LogStatus.INFO, "Entering the email");
		resellerapipage.enterEmail(strEmail);

		//Test Step 3 : Submit the Contact details 
		resellerapipage.clickSubmitButtonToSubmitContactDetails();
		strResponse=resellerapipage.getResponse();
		System.out.println("Response" +strResponse);

		//Test Step4 : Verify that contact Id is created
		Assert.assertTrue(strResponse.contains("OK"));
		strContact=resellerapipage.getWorkflowId(strResponse);
		System.out.println("Contact Id" +strContact);

	}

	/* Test Case to register the domain */
	@Parameters({ "environment"})
	@Test
	public void verifyDomainRegistration(String environment) throws InterruptedException, IOException {

		//Initialization (Test Data Creation and Assignment)
		String arrtld[] = {".com.au"};
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "Testresellerapi" + df.format(d);
		String strDomainPeriod="2";
		String strAccountId=PropertyFileOperations.readApiProperty("strAccountId");
		String strRegistrantName=PropertyFileOperations.readApiProperty("strRegistrantName");
		String strEligibiltyName=PropertyFileOperations.readApiProperty("strEligibiltyName");
		String strEligibilityId=PropertyFileOperations.readApiProperty("strEligibilityId");
		String strEligibiltyReason=PropertyFileOperations.readApiProperty("strEligibiltyReason");
		for(String tld : arrtld)
		{

			verifyCreateConatctDetails(environment);
			//Test Step 1 : Enter the Domain Details
			test.log(LogStatus.INFO, "Entering the domain name");
			resellerapipage.enterDomain(strDomainName+tld);
			test.log(LogStatus.INFO, "Entering the domain period");
			resellerapipage.enterPeriod(strDomainPeriod);
			test.log(LogStatus.INFO, "Select Account Option");
			resellerapipage.selectAccountOptionToRegisterDomain();
			test.log(LogStatus.INFO, "Entering the account Id");
			resellerapipage.enterAccountID(strAccountId);
			test.log(LogStatus.INFO, "Entering the owner contact id");
			resellerapipage.enterOwnerContactID(strContact);
			test.log(LogStatus.INFO, "Entering the administration contact id");
			resellerapipage.enterAdministrationContactID(strContact);
			test.log(LogStatus.INFO, "Entering the technical contact id");
			resellerapipage.enterTechnicalContactID(strContact);
			test.log(LogStatus.INFO, "Entering the billing contact id");
			resellerapipage.enterBillingContactID(strContact);
			test.log(LogStatus.INFO, "Entering the Host name");
			resellerapipage.enterHost("ns1.partnerconsole.net", "ns2.partnerconsole.net");

			//Test Step 2: Enter the au elgibility details if domain is "au"
			if(tld==".com.au") 
			{
				test.log(LogStatus.INFO, "Entering the eligibilty details");
				resellerapipage.enterEligibilityDetails(strRegistrantName, strContact, strEligibiltyName, strEligibilityId, strEligibiltyReason);
			}

			//Test Step 3 :  Submit the Domain details
			test.log(LogStatus.INFO, "Clicking on submit button to register the domain");
			resellerapipage.clickSubmitButtonToRegisterDomain();

			//Test Step 4 : Verify that workflow Id is created
			fetchDomainRegistrationWorkflowId();
			driver.quit();

			//Test Step 5: Verify if domain registration workflow is completed in console
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage =caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strWorkflowId);
			caworkflowadminpage.processWorkFlow(MITDPS_CommonFunctionality.strWorkflowId, tld);
			test.log(LogStatus.INFO,"Verify if domain registration workflow is completed" );
			caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strWorkflowId);
			strWorkflowStatus=caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed")||strWorkflowStatus.equalsIgnoreCase("update star rating")); 
			registerdomain=strDomainName+tld; 
			System.out.println("Domain Name:"+registerdomain);
			driver.quit();
		}
	}

	/* Test Case to renew the domain */
	@Parameters({ "environment"})
	@Test
	public void verifyDomainRenewal(String environment) throws InterruptedException, IOException{

		//Initialization (Test Data Creation and Assignment)
		String strDomainPeriod=PropertyFileOperations.readApiProperty("strDomainPeriod");

		//verifyDomainRegistration(environment);
		verifyCreateSession(environment);

		//Test Step 1: Enter the domain details for renew
		test.log(LogStatus.INFO, "Entering the registered domain name for renew");
		resellerapipage.enterDomainName(registerdomain);
		test.log(LogStatus.INFO, "Entering the domain period for renew");
		resellerapipage.enterPeriodForRenewDomain(strDomainPeriod);

		//Test Step 2 :  Submit the Domain details for renew
		test.log(LogStatus.INFO, "Clicking on submit button to renew the domain");
		resellerapipage.clickSubmitButtonToRenewDomain();

		//Test Step 4 : Verify that workflow Id is created
		fetchRenewalWorkflowId();
		driver.quit();

		// Test Step 3: Login to console admin test.log(LogStatus.INFO,"Login to console admin");
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();

		// Test Step 4: Search a workflow ID and verify workflow status
		test.log(LogStatus.INFO, "Search a workflow ID in console admin and verify workflow status");
		caworkflowadminpage = caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strRenewWorkflowId);
		caworkflowadminpage.clickOnWorkflowId();
		String workflowType = caworkflowadminpage.verifyWorkflowType();
		Assert.assertEquals(workflowType, "renewal2", "renewal2 workflow is not created");

		// Test Step 5: Verify workflow status and perform the execution of workflow
		test.log(LogStatus.INFO, "verify the workflow status and perform the execution of workflow");
		switch (environment) 
		{
		case "dev8":
			workflowStatusAfterCompletionManually = caworkflowadminpage.processRenewal2Workflow();
			Assert.assertEquals(workflowStatusAfterCompletionManually, "renewed","renewal workflow not completed successfully by staff");

		case "uat1":
			workflowStatusAfterCompletionBySchedular = caworkflowadminpage.getWorkflowStatus();
			Assert.assertEquals(workflowStatusAfterCompletionBySchedular, "renewed","renewal workflow not completed successfully by schedular");

		case "prod":
			workflowStatusAfterCompletionBySchedular = caworkflowadminpage.getWorkflowStatus();
			Assert.assertEquals(workflowStatusAfterCompletionBySchedular, "renewed","renewal workflow not completed successfully by schedular");
		}
		test.log(LogStatus.INFO, "Domain renewal completed -COMPLETED");
		driver.quit();
	}

	/* Test Case to transfer the domain */
	@Parameters({ "environment"})
	@Test
	public void verifyDomainTransferAu(String environment) throws Exception{

		//Initialization (Test Data Creation and Assignment)
		String strAccountId=PropertyFileOperations.readApiProperty("strAccountId");

		getTestDataForautransfer2(environment);
		verifyCreateConatctDetails(environment);


		//Test Step 1: Enter the domain details for transfer the domain
		test.log(LogStatus.INFO, "Entering the registered domain name for transfer");
		resellerapipage.enterDomainNameToTransfer(strAuDomainName);
		test.log(LogStatus.INFO, "Entering the domain password");
		resellerapipage.enterDomainPassword(strDomainPassword);
		test.log(LogStatus.INFO, "Select account Option for transfer");
		resellerapipage.selectAccountOptionToTransfer();
		test.log(LogStatus.INFO, "Entering the account Id for transfer the domain");
		resellerapipage.enterAccountIDForTransfer(strAccountId);
		test.log(LogStatus.INFO, "Entering the owner contact id for transfer the domain");
		resellerapipage.enterOwnerContactIDForTransfer(strContact);
		test.log(LogStatus.INFO, "Entering the administration contact id for transfer the domain");
		resellerapipage.enterAdministrationContactIDForTransfer(strContact);
		test.log(LogStatus.INFO, "Entering the technical contact id for transfer the domain");
		resellerapipage.enterTechnicalContactIDForTransfer(strContact);
		test.log(LogStatus.INFO, "Entering the billing contact id for transfer the domain");
		resellerapipage.enterBillingContactIDForTransfer(strContact);

		//Test Step 2 :  Submit the Domain details for transfer
		test.log(LogStatus.INFO, "Clicking on submit button to transfer the domain");
		resellerapipage.clickSubmitButtonToTransferDomain();

		//Test Step 3 : Verify that workflow Id is created
		fetchTransferWorkflowId();
		driver.quit();

		//Test Step 6: Execute the autransfer2 workflow in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strTransferWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("auTransfer2");
		test.log(LogStatus.INFO, "The icannTransfer2 workflow is in step " +strWorkflowStatus);
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("pending"));
		DatabaseConnection.connectToDatabase();
		DatabaseConnection.updateTldId(strAuDomainName);
		driver.quit();
	}
	@Parameters({ "environment"})
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
		strAuDomainName=strDomainName+strTld;
		cadomainlevelpage = new CADomainLevelPage();
		csmuitabpage = cadomainlevelpage.clickloginAsClientLink();
		csmuidomainnamepage = csmuitabpage.clickDomainNameTab();
		strDomainPassword = csmuidomainnamepage.getDomainPassword();
		System.out.println("Doamin epp Passowrd: " + strDomainPassword);

		driver.quit();
		System.out.println("End Test: getTestDataForautransfer2 - COMPLETED");
		test.log(LogStatus.INFO, "Fetch the test data for autransfer2  - COMPLETED"); 	

	}

	@Parameters({ "environment"})
	@Test
	public void getNonAuTestData(String environment) throws InterruptedException, AWTException, IOException{

		// Initialization (Test Data Creation and Assignment)
		String strExistingCardNumber="Number: 4111xxxxxxxx1111 Expiry: 01/22";
		String strDomainName = null;
		String strWorkflowStatus = null;
		String tld[] = {".net"};
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);

		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");

		for(String tlds : tld){
			initialization(environment, "oldcart_domainsearchurl_netregistry");
			mitonlineorderpage = new MITOnlineOrderPage();
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED "+tlds);
			test.log(LogStatus.INFO, "Clearing default tld selections");
			mitonlineorderpage.clearDefaultTldSelections();
			test.log(LogStatus.INFO, "Enter domain name and select Tld "+tlds);
			mitonlineorderpage.setDomainNameAndTld(strDomainName,tlds);
			test.log(LogStatus.INFO, "Clicking on search button "+tlds);
			mitdomainsearchpage = mitonlineorderpage.clickNewDomainSearchButton();
			test.log(LogStatus.INFO, "Clicking on continue to checkout button "+tlds);
			mitadddomainprivacypage = mitdomainsearchpage.clickContinueToCheckout();
			mithostingandextraspage= mitadddomainprivacypage.clickNoThanks();
			test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
			mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
			test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
			mitaccountcontactpage.setReturningCustomerContacts("MEL-6007", "comein22");
			mitregistrantcontactpage = mitaccountcontactpage.clickLoginButton();
			test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
			mitregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
			mitbillingpage = mitregistrantcontactpage.clickContinueButton();
			if(tlds==".com.au") {
				setEligibilityDetails("ABN", "21073716793", "Arq Group Limited", "Company");
			}
			//Test Step 2: Input credit card details and submit the order 
			selectExistingCard(strExistingCardNumber);

			//Test Step 3: Verify if recaptcha challenge is dislayed 
			fetchWorkflowId();
			driver.quit();

			//Step 4: Execute domain registration workflows
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strWorkflowId);
			caworkflowadminpage.processWF(MITDPS_CommonFunctionality.strWorkflowId, tlds);
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caworkflowadminpage = caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
			registerdomain=strDomainName+tlds; 
			System.out.println("Domain Name:"+registerdomain);
			cadomainlevelpage=caheaderpage.searchDomainName(registerdomain);
			caeditdnspage = cadomainlevelpage.clickEditDNSLink();
			strDomainPassword = caeditdnspage.getDomainEppPassowrd();
			System.out.println("Doamin epp Passowrd: " + strDomainPassword);
			driver.quit();
		}
	}

	/* Test Case to transfer the Non Au domain */
	@Parameters({ "environment"})
	@Test
	public void verifyDomainTransferNonAu(String environment) throws InterruptedException, IOException, AWTException{

		//Initialization (Test Data Creation and Assignment)
		String strDomainPeriod=PropertyFileOperations.readApiProperty("strDomainPeriod");
		String strAccountId=PropertyFileOperations.readApiProperty("strAccountId");

		//Test Step 1: Get the test data for NOn Au transfer
		//getNonAuTestData(environment);

		//Test Step 2: Enter the domain details for transfer the domain
		verifyCreateConatctDetails(environment);
		test.log(LogStatus.INFO, "Entering the registered domain name for transfer");
		resellerapipage.enterDomainPassword(registerdomain);
		test.log(LogStatus.INFO, "Entering the domain period for transfer");
		resellerapipage.enterPeriodForTransferDomain(strDomainPeriod);
		test.log(LogStatus.INFO, "Entering the domain password");
		resellerapipage.enterDomainPassword(strDomainPassword);
		test.log(LogStatus.INFO, "Select account Option for transfer");
		resellerapipage.selectAccountOptionToTransfer();
		test.log(LogStatus.INFO, "Entering the account Id for transfer the domain");
		resellerapipage.enterAccountIDForTransfer(strAccountId);
		test.log(LogStatus.INFO, "Entering the owner contact id for transfer the domain");
		resellerapipage.enterOwnerContactIDForTransfer(strContact);
		test.log(LogStatus.INFO, "Entering the administration contact id for transfer the domain");
		resellerapipage.enterAdministrationContactIDForTransfer(strContact);
		test.log(LogStatus.INFO, "Entering the technical contact id for transfer the domain");
		resellerapipage.enterTechnicalContactIDForTransfer(strContact);
		test.log(LogStatus.INFO, "Entering the billing contact id for transfer the domain");
		resellerapipage.enterBillingContactIDForTransfer(strContact);

		//Test Step 3 :  Submit the Domain details for transfer
		test.log(LogStatus.INFO, "Clicking on submit button to transfer the domain");
		resellerapipage.clickSubmitButtonToTransferDomain();

		//Test Step 4 : Verify that workflow Id is created
		fetchTransferWorkflowId();
		driver.quit();

		//Test Step 5: Execute the transferral2 workflow in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strTransferWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("transferral2");
		if(strWorkflowStatus.equalsIgnoreCase("transfer requested"))
		{
			caworkflowadminpage.processtransferral2WorkFlow(MITDPS_CommonFunctionality.strTransferWorkflowId);
		}
		else
		{
			//Status is Domain Resolution Failed
			caworkflowadminpage.processTransferl2StatusWorkflow(MITDPS_CommonFunctionality.strTransferWorkflowId);
		}
		caworkflowadminpage = caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strTransferWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("transferral2");
		test.log(LogStatus.INFO, "The transferral2 workflow is in step " +strWorkflowStatus);
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("approved"));
		driver.quit();    
	}	

	/* Test Case to whois domain */
	@Parameters({ "environment"})
	@Test
	public void verifyWhoisDomain(String environment) throws InterruptedException, IOException, AWTException{

		//Initialization (Test Data Creation and Assignment)
		//Test Step 1: Register the domain
		//verifyDomainRegistration(environment);

		//Test Step 2: Create the session
		//verifyCreateSession(environment);

		//Test Step 3: Enter the domain Name
		test.log(LogStatus.INFO, "Entering the registered domain name");
		resellerapipage.enterDomainNameForWhois(registerdomain);
		
		//Test Step 4: Submit the domain details
		test.log(LogStatus.INFO, "Submit the domain details");
		resellerapipage.clickSubmitButtonOfWhoisDomain();

		//Test Step 4 : Verify that Response is generated or not.
		fetchWhoisWorkflowId();
		//driver.quit();
	}	

	/* Test Case for Domain Lock */
	@Parameters({ "environment"})
	@Test
	public void verifyDomainLockandUnlock(String environment) throws InterruptedException, IOException, AWTException{

		//Initialization (Test Data Creation and Assignment)

		//Test Step 1: Register the domain
		//verifyDomainRegistration(environment);

		//Test Step 2: Create the session
		verifyCreateSession(environment);

		//Test Step 3: Enter the domain Name
		test.log(LogStatus.INFO, "Entering the registered domain name");
		resellerapipage.enterDomainLockName(registerdomain);

		//Test Step 4: Submit the domain details
		test.log(LogStatus.INFO, "Submit the domain details");
		resellerapipage.clickSubmitButtonOfDomainLock();
		strResponseDomainLock=resellerapipage.getResponseOfDomainLock();
		System.out.println("Domain Lock Response:"+strResponseDomainLock);

		//Test Step 5 : Verify that Response is generated or not.
		Assert.assertTrue(strResponseDomainLock.contains("OK"));

		//Test Step 6: Enter the domain Name
		test.log(LogStatus.INFO, "Entering the registered domain name");
		resellerapipage.enterDomainUnLockName(registerdomain);
		//Test Step 7: Select domain unlock option from dropdown
		test.log(LogStatus.INFO, "Select  domain unlock option from dropdown");
		resellerapipage.selectDomainUnlock();
		//Test Step 8: Submit the domain details
		test.log(LogStatus.INFO, "Submit the domain details");
		resellerapipage.clickSubmitButtonOfDomainLock();
		

		//Test Step 5 : Verify that Response is generated or not.
		fetchLockUnlockWorkflowId();

	}	

	/* Test Case for Create Name Server */
	@Parameters({ "environment"})
	@Test
	public void verifyCreateNameServer(String environment) throws InterruptedException, IOException, AWTException{

		//Initialization (Test Data Creation and Assignment)
		String strNameServerPrefix=PropertyFileOperations.readApiProperty("strNameServerPrefix");
		String strNameServerIP=PropertyFileOperations.readApiProperty("strNameServerIP");

		//Test Step 1: Register the domain
		//verifyDomainRegistration(environment);

		//Test Step 2: Create the session
		verifyCreateSession(environment);

		//Test Step 3: Enter the domain details
		test.log(LogStatus.INFO, "Entering the registered domain name");
		resellerapipage.enterDomainNameForCreateNameSrver(registerdomain);
		test.log(LogStatus.INFO, "Entering the name server prefix");
		resellerapipage.enterNameServerPrefixForCreateNameSrver(strNameServerPrefix);
		test.log(LogStatus.INFO, "Entering the name server ip address");
		resellerapipage.enterNameServerIPAddressForCreateNameSrver(strNameServerIP);

		//Test Step 4: Submit the domain details
		test.log(LogStatus.INFO, "Submit the domain details");
		resellerapipage.clickSubmitButtonOfCreateNameServer();
		
		//Test Step 5 : Verify that Response is generated or not.
		fetchCreateNameServerWorkflowId();
		verifyChangeNameServer(environment);
		verifyDeleteNameServer(environment);
	}	

	/* Test Case for Change Name Server */
	@Parameters({ "environment"})
	@Test
	public void verifyChangeNameServer(String environment) throws InterruptedException, IOException, AWTException{

		//Initialization (Test Data Creation and Assignment)
		String strNameServerPrefix=PropertyFileOperations.readApiProperty("strNameServerPrefix");
		String strNameServerIPAddress=PropertyFileOperations.readApiProperty("strNameServerIPAddress");

		//Test Step 1: Register the domain
		//verifyDomainRegistration(environment);

		//Test Step 2: Create the session
	   // verifyCreateSession(environment);

		//Test Step 3: Enter the domain details
		test.log(LogStatus.INFO, "Entering the registered domain name");
		resellerapipage.enterDomainNameForChangeNameSrver(registerdomain);
		test.log(LogStatus.INFO, "Entering the name server prefix");
		resellerapipage.enterNameServerPrefixForChangeNameSrver(strNameServerPrefix);
		test.log(LogStatus.INFO, "Entering the name server ip address");
		resellerapipage.enterNameServerIPAddressForChangeNameSrver(strNameServerIPAddress);

		//Test Step 4: Submit the domain details
		test.log(LogStatus.INFO, "Submit the domain details");
		resellerapipage.clickSubmitButtonOfChangeNameServer();

		//Test Step 4 : Verify that Response is generated or not.
		fetchChangeNameServerWorkflowId();
	}	

	/* Test Case for Delete Name Server */
	@Parameters({ "environment"})
	@Test
	public void verifyDeleteNameServer(String environment) throws InterruptedException, IOException, AWTException{

		//Initialization (Test Data Creation and Assignment)
		String strNameServerPrefix=PropertyFileOperations.readApiProperty("strNameServerPrefix");

	   //Test Step 1: Register the domain
		//verifyDomainRegistration(environment);

		//Test Step 2: Create the session
		//verifyCreateSession(environment);

		//Test Step 3: Enter the domain details
		test.log(LogStatus.INFO, "Entering the registered domain name");
		resellerapipage.enterDomainNameForDeleteNameSrver(registerdomain);
		test.log(LogStatus.INFO, "Entering the name server prefix");
		resellerapipage.enterNameServerPrefixForDeleteNameSrver(strNameServerPrefix);

		//Test Step 4: Submit the domain details
		test.log(LogStatus.INFO, "Submit the domain details");
		resellerapipage.clickSubmitButtonOfDeleteNameServer();

		//Test Step 4 : Verify that Response is generated or not.
		fetchDeleteNameServerWorkflowId();
	}	
	
	/* Test Case for registrant name change */
	@Parameters({ "environment"})
	@Test
	public void verifyRegistrantNameChange(String environment) throws InterruptedException, IOException, AWTException{

		//Initialization (Test Data Creation and Assignment)
		//String strDomainNameForNameChange = PropertyFileOperations.readApiProperty("strDomainNameForNameChange");
		
		//Test Step 1: Register the domain
		verifyDomainRegistration(environment);
		verifyCreateConatctDetails(environment);
		//Test Step 2: Enter the domain name and new eligibility details
		test.log(LogStatus.INFO, "Entering the registered domain name");
		resellerapipage.enterDomainNameForRegistrantNameChange(registerdomain);
		test.log(LogStatus.INFO, "Entering the new eligibilty details");
		resellerapipage.enterNewEligibilityDetails("QA Team",strContact,"NETREGISTRY PTY LTD","13080859721","Test");
		
		//Test Step 3: Submit the details
		test.log(LogStatus.INFO, "Submit the domain details");
		resellerapipage.clickSubmitButtonOfRegistrantNameChange();
		
		//Test Step 4 : Verify that response is generated with order id
		fetchRegistrantNameChangeWorkflowId();
		driver.quit();
		
		//Test Step 5: Login to console admin and search the workflow
		test.log(LogStatus.INFO, "Process the domain registration order in console admin");
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strWorkflowId);

		// Test Step 6: Verify if registrantNameChange2 workflow is created or not
		test.log(LogStatus.INFO, "Verify if registrant name change2 workflow is created");
		strWorkflowStatus=caworkflowadminpage.getWorkflowStatus("registrantNameChange2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("verify eligibility")|| strWorkflowStatus.equalsIgnoreCase("waiting registrar approval"));
		driver.quit();
		System.out.println("End Test: verifyDomainRegistrantNameChange");
	}	
}
