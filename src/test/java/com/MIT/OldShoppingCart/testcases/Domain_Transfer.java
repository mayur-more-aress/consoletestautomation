package com.MIT.OldShoppingCart.testcases;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.commonfunctionality.MIT_CommonFunctionality;
import com.consoleadmin.pages.CADomainLevelPage;
import com.consoleadmin.pages.CAEditDNSPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.consolesmui.pages.CSMUIDomainNamePage;
import com.consolesmui.pages.CSMUITabPage;
import com.melbourneitwebsite.pages.MITAccountContactPage;
import com.melbourneitwebsite.pages.MITAddDomainToTransferPage;
import com.melbourneitwebsite.pages.MITBillingPage;
import com.melbourneitwebsite.pages.MITDomainTransferPage;
import com.melbourneitwebsite.pages.MITEligibilityDetailsPage;
import com.melbourneitwebsite.pages.MITOnlineOrderPage;
import com.melbourneitwebsite.pages.MITOrderCompletePage;
import com.melbourneitwebsite.pages.MITRegistrantContactPage;
import com.mitdpsresellerportal.pages.MITDPSLoginPage;
import com.mitdpsresellerportal.pages.MITDPSRegisterADomainPage;
import com.mitdpsresellerportal.pages.MITDPSTabPage;
import com.relevantcodes.extentreports.LogStatus;
import com.util.DatabaseConnection;

public class Domain_Transfer extends MIT_CommonFunctionality
{
	public Domain_Transfer() 
	{
		super();
	}
	
	//MIT Customer portal pages
	MITOnlineOrderPage mitonlineorderpage;
	MITAccountContactPage mitaccountcontactpage;
	MITRegistrantContactPage mitregistrantcontactpage;
	MITBillingPage mitbillingpage;
	MITOrderCompletePage mitordercompletepage;
	MITEligibilityDetailsPage miteligibilitydetailspage;
	MITDomainTransferPage mitdomaintransferpage;
	MITAddDomainToTransferPage mitadddomaintotransferpage;
	
	// Reseller portal [ages
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
		
	// Initialization (Test Data Creation and Assignment)
	String strDomainName ;
	String strTld;
	String strDomainPassword;
	String strCustomerAccountReference;
	String strCustomerPassword;
	String strRefrenceId;
	String strAccountReference;
	String strExistingCardNumber;
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
		strTld = ".net";
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
		strCustomerAccountReference ="MEL-6005";
		strCustomerPassword = "comein22";
		strExistingCardNumber = "Number: 4111xxxxxxxx1111 Expiry: 01/22";
		
    	System.out.println("Start Test: transferDomainFromCustomerPortalAndVerifytransferral2WF for "+strTld+ " domain  - STARTED");
		test.log(LogStatus.INFO, "Transfer Domain From CustomerPortal For ExistingCustomer for "+strTld+ " domain - STARTED"); 	
	   
		// Test Step 1: Navigate to domain transfer URL
		initialization(environment, "oldcart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		test.log(LogStatus.INFO, "Navigate to domain transfer page -STARTED ");
		mitonlineorderpage.getDomainTransferUrl();
		
		// Test Step 2: Enter domain details and add domain to cart
		mitdomaintransferpage = new MITDomainTransferPage();
		mitdomaintransferpage.setDomainNameAndTld(strDomainName,strTld);
		mitdomaintransferpage.setDomainPassword(strDomainPassword);
		mitadddomaintotransferpage = mitdomaintransferpage.clickTransferButton();
		mitaccountcontactpage = mitadddomaintotransferpage.addDomainToCart();
		
		// Test Step 3: Login as returning customer
		mitaccountcontactpage.setReturningCustomerContacts(strCustomerAccountReference,strCustomerPassword);
		mitregistrantcontactpage=mitaccountcontactpage.clickLoginButton();
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		
		// Test Step 4: Select the existing card
		 enterExistingCardDetails(strExistingCardNumber);
        
        //Test Step 5: Verify if the order is completed, get workflow id and account reference.
		fetchWorkflowId();
		driver.quit();
		
		//Test Step 6: Execute the transferral2 workflow in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
		String strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("transferral2");
		test.log(LogStatus.INFO, "The transferral2 workflow is in step " +strWorkflowStatus);
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("transfer requested") || strWorkflowStatus.equalsIgnoreCase("approved"));
		
		
		driver.quit();
    	System.out.println("End Test: transferDomainFromCustomerPortalAndVerifytransferral2WF for "+strTld+ " domain  - COMPLETED");
		test.log(LogStatus.INFO, "Transfer Domain From CustomerPortal For ExistingCustomer for "+strTld+ " domain - COMPLETED"); 	
	}
	
	/*
	 * Test case to transfer a domain from customer portal and verify Verify icannTransfer2WF workflow
	 * Workflow : icannTransfer2 workflow
	 */
	@Parameters({ "environment" })
	@Test(dependsOnMethods = "verifyDomainRegistrationInResellerPortal")
	public void transferDomainFromCustomerPortalAndVerifyicannTransfer2WF(String environment) throws Exception
	{
		strCustomerAccountReference ="MEL-6005";
		strCustomerPassword = "comein22";
		strExistingCardNumber = "Number: 4111xxxxxxxx1111 Expiry: 01/22";
		
    	System.out.println("Start Test: transferDomainFromCustomerPortalAndVerifyicannTransfer2WF - STARTED");
		test.log(LogStatus.INFO, "Transfer Domain From CustomerPortal For ExistingCustomer - STARTED"); 	
	   
		// Test Step 1: Navigate to domain transfer URL
		initialization(environment, "oldcart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		test.log(LogStatus.INFO, "Navigate to domain transfer page -STARTED ");
		mitonlineorderpage.getDomainTransferUrl();
		
		// Test Step 2: Enter domain details and add domain to cart
		mitdomaintransferpage = new MITDomainTransferPage();
		mitdomaintransferpage.setDomainNameAndTld(strDomainName,strTld);
		mitdomaintransferpage.setDomainPassword(strDomainPassword);
		mitadddomaintotransferpage = mitdomaintransferpage.clickTransferButton();
		mitaccountcontactpage = mitadddomaintotransferpage.addDomainToCart();
		
		// Test Step 3: Login as returning customer
		mitaccountcontactpage.setReturningCustomerContacts(strCustomerAccountReference,strCustomerPassword);
		mitregistrantcontactpage=mitaccountcontactpage.clickLoginButton();
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		
		// Test Step 4: Select the existing card
		enterExistingCardDetails(strExistingCardNumber);
        
        //Test Step 5: Verify if the order is completed, get workflow id and account reference.
		 fetchWorkflowId();	
		driver.quit();
		
		//Test Step 6: Execute the icannTransfer2 workflow in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("icannTransfer2");
		if(strWorkflowStatus.equalsIgnoreCase("pending"))
		{
			caworkflowadminpage.processicannTransfer2WorkFlow(MIT_CommonFunctionality.strWorkflowId);
			caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("icannTransfer2");
			test.log(LogStatus.INFO, "The icannTransfer2 workflow is in step " +strWorkflowStatus);
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("approved"));
		}
		else
		{
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("approved"));
		}
	
		driver.quit();
		System.out.println("End Test: transferDomainFromCustomerPortalAndVerifyicannTransfer2WF - COMPLETED");
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
		strCustomerAccountReference ="MEL-6005";
		strCustomerPassword = "comein22";
		
    	System.out.println("Start Test: transferDomainFromCustomerPortalAndVerifyautransfer2WF - STARTED");
		test.log(LogStatus.INFO, "Transfer Domain From CustomerPortal For ExistingCustomer - STARTED"); 	
	   
		// Test Step 1: Navigate to domain transfer URL
		initialization(environment, "oldcart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		test.log(LogStatus.INFO, "Navigate to domain transfer page -STARTED ");
		mitonlineorderpage.getDomainTransferUrl();
		
		// Test Step 2: Enter domain details and add domain to cart
		mitdomaintransferpage = new MITDomainTransferPage();
		mitdomaintransferpage.setDomainNameAndTld(strDomainName,strTld);
		mitdomaintransferpage.setDomainPassword(strDomainPassword);
		mitadddomaintotransferpage = mitdomaintransferpage.clickTransferButton();
		mitaccountcontactpage = mitadddomaintotransferpage.addDomainToCart();
		
		// Test Step 3: Login as returning customer
		mitaccountcontactpage.setReturningCustomerContacts(strCustomerAccountReference,strCustomerPassword);
		mitregistrantcontactpage=mitaccountcontactpage.clickLoginButton();
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		
		// Test Step 4: Select the existing card
		enterExistingCardDetails(strExistingCardNumber);
        
        //Test Step 5: Verify if the order is completed, get workflow id and account reference.
		fetchWorkflowId();	
		driver.quit();
		
		//Test Step 6: Execute the autransfer2 workflow in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("auTransfer2");
		test.log(LogStatus.INFO, "The autransfer2 workflow is in step " +strWorkflowStatus);
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("pending"));

		//Test Step 7: Update tld_id in the database
		DatabaseConnection.connectToDatabase();
		DatabaseConnection.updateTldId(strDomainName+strTld);
			
		driver.quit();
		System.out.println("End Test: transferDomainFromCustomerPortalAndVerifyautransfer2WF - COMPLETED");
		test.log(LogStatus.INFO, "Transfer Domain From CustomerPortal For ExistingCustomer - COMPLETED"); 
	}
}
