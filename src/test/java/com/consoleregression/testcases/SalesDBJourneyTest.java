package com.consoleregression.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.braintree.pages.BTFoundTransactionPage;
import com.braintree.pages.BTLoginPage;
import com.braintree.pages.BTMainTabPage;
import com.braintree.pages.BTTransactionsSearchPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.consolesalesdb.pages.CSAUEligibilityPage;
import com.consolesalesdb.pages.CSCreateDomainWindowPage;
import com.consolesalesdb.pages.CSLoginPage;
import com.consolesalesdb.pages.CSNrCRMPage;
import com.consolesalesdb.pages.CSRegistrantDetailsPage;
import com.consolesalesdb.pages.CSShowDomainServicesPage;
import com.consolesalesdb.pages.CSWorkflowNotificationPage;
import com.domainzwebsite.pages.DMZAccountPage;
import com.domainzwebsite.pages.DMZHeaderPage;
import com.domainzwebsite.pages.DMZLoginPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class SalesDBJourneyTest extends TestBase{

	
	//Console Sales DB Pages
	CSLoginPage csloginpage;
	CSNrCRMPage csnrcrmpage;
	CSCreateDomainWindowPage cscreatedomainwindowpage;
	CSRegistrantDetailsPage csregistrantdetailspage;
	CSShowDomainServicesPage csshowdomainservicespage;
	CSWorkflowNotificationPage csworkflownotificationpage;
	CSAUEligibilityPage csaueligibilitypage;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	
	//Braintree Pages
	BTLoginPage btloginpage;
	BTMainTabPage btmaintabpage;
	BTTransactionsSearchPage bttransactionssearchpage;
	BTFoundTransactionPage btfoundtransactionpage;
	
	//Domainz Cart/Portal Pages
	DMZLoginPage dmzloginpage;
	DMZHeaderPage dmzheaderpage;
	DMZAccountPage dmzaccountpage;
	
	
	
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public SalesDBJourneyTest() {
		super();
	}
	

	@Parameters({"environment", "obsidian"})
	@Test
	public void verify_ComAuDomain_Order_InSalesDB (String environment, String obsidian) throws InterruptedException{
		
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = null;
		String strPhoneNumber = null;
		String strRegistrantType = null;
		String strRegistrantNumber = null;
		String strWorkflowStatus = null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		
		if (environment.equals("")) {
			strTld = "com.au";
			strRegistrationPeriod = "2";
			strGreenCode = "FAZ-20";
			strPaymentMethod = "Visa: 4111xxxxxxxx1111 05/24";	
			strPhoneNumber = "+61.299340501";
			strRegistrantDetails = "TPP";
			strRegistrantType = "ABN";
			strRegistrantNumber = "21073716793";
		}
		else if (environment.equals("uat2") || environment.equals("uat1") ) {
			strTld = "com.au";
			strRegistrationPeriod = "2";
			strGreenCode = "TPP-60053";
			strPhoneNumber = "+61.299340501";
			strPaymentMethod ="Invoice";// "Resellers Default: Visa: 411111******1111";
			strRegistrantDetails = "TPP";
			strRegistrantType = "ABN";
			strRegistrantNumber = "21073716793";		
		}
		
		//Test Step 1: Login to sales db and place an order for domain registration
		System.out.println("Start Test: verify_ComAuDomain_Order_InSalesDB");

		initialization(environment, "salesdburl");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails(environment);
		csnrcrmpage = csloginpage.clickLoginButton();
		csnrcrmpage.setGreenCode(strGreenCode);
		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
		cscreatedomainwindowpage.setDomainDetails(strDomainName, strTld, strRegistrationPeriod, strPaymentMethod);
		
		System.out.println("Method: assign values to aueligibilitypage");
		csaueligibilitypage = csnrcrmpage.clickUpdateDetails(strDomainName, "Update Details");
		csnrcrmpage = csaueligibilitypage.setContactAndEligibilityDetails(strRegistrantDetails,strPhoneNumber, strRegistrantType, strRegistrantNumber);
		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		strWorkflowId = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.quit();
		
		//Test Step 2: Process the domain registration order in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		
		if (obsidian.equals("enabled")) {
			
			//Wait for workflow to be processed
			Thread.sleep(150000);
		}
		else {
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, strTld);
		}
		
		//Test Step 3: Verify if domain registration workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));	
	
		driver.quit();
		System.out.println("End Test: verify_ComAuDomain_Order_InSalesDB");
		
	}
	
	@Parameters({"environment", "obsidian"})
	@Test
	public void verify_NetDomain_and_DIFM_Order_InSalesDB (String environment, String obsidian) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = null;
		String strWorkflowStatus = null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);

		if (environment.equals("uat1")||environment.equals("uat2")) {
			strTld = "net";
			strRegistrationPeriod = "1 x Y";
			strGreenCode = "NET-1214";
			strPaymentMethod = "Visa: 411111******1111 11/2022";
			strRegistrantDetails = "Netregistry";
		}
	
		//Test Step 1: Login to sales db and place an order for domain registration and a single product (e.g. Done For You Website)
		System.out.println("Start Test: verify_NetDomain_and_DIFM_Order_InSalesDB");
		initialization(environment, "salesdburl");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails(environment);
		csnrcrmpage = csloginpage.clickLoginButton();
		csnrcrmpage.setGreenCode(strGreenCode);
		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
		cscreatedomainwindowpage.setDomainDetails(strDomainName, strTld, strRegistrationPeriod,strPaymentMethod);
		csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
		csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		
		Assert.assertEquals(csworkflownotificationpage.getNotificationMessage(), "Services are successfully confirmed");
		strWorkflowId = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.quit();
		
		//Test Step 2: Process the domain registration workflow in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		
		if (obsidian.equals("enabled")) {
			
			//Wait for workflow to be processed
			Thread.sleep(150000);
		}
		else {
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, strTld);
		}
		
		//Test Step 3: Verify if domain registration workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));	
	
		//Test Step 4: Process the productsetup2 workflow in console admin
		/*caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + "." + strTld);
		caworkflowadminpage.processProductSetup2();
		
		//Test Step 5: Verify if productsetup2 workflow is approved
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + "." + strTld);
		if (caworkflowadminpage.getWorkflowStatus("productSetup2") != "halting current workflow") {
			//Added refresh page to update current workflow status
			caworkflowadminpage.refreshWorkflowAdminPage();	   
		}
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "halting current workflow", 
			caworkflowadminpage.getWorkflowStatus("productsetup2"));*/

		driver.quit();
		System.out.println("End Test: verify_NetDomain_and_DIFM_Order_InSalesDB");
	}
	
	
	@Parameters({"environment", "obsidian"})
	@Test
	public void verify_ComDomain_and_BasicCloudHostingOrder_InSalesDB (String environment, String obsidian) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strMajorProduct = null;
		String strProductPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = null;
		String strWorkflowStatus = null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);

		if (environment.equals("uat1")||environment.equals("uat2")) {
			strTld = "com";
			strRegistrationPeriod = "1 x Y";
			strGreenCode = "NET-1218";
			strMajorProduct = "Basic Cloud Hosting";
			strProductPeriod = "1 x M";
			strPaymentMethod = "MasterCard: 545454******5454";
			strRegistrantDetails = "Netregistry";
		}
	
		//Test Step 1: Login to sales db and place an order for domain registration and a single product (e.g. Basic Cloud Hosting)
		System.out.println("Start Test: verify_ComDomain_and_BasicCloudHostingOrder_InSalesDB");
		initialization(environment, "salesdburl");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails(environment);
		csnrcrmpage = csloginpage.clickLoginButton();
		csnrcrmpage.setGreenCode(strGreenCode);
		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
		cscreatedomainwindowpage.setDomainandMajorProductDetails(strDomainName, strTld, strRegistrationPeriod, strMajorProduct, strProductPeriod, strPaymentMethod);
		csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
		csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		
		Assert.assertEquals(csworkflownotificationpage.getNotificationMessage(), "Services are successfully confirmed");
		strWorkflowId = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.quit();
		
		//Test Step 2: Process the domain registration workflow in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);

		if (obsidian.equals("enabled")) {
			
			//Wait for workflow to be processed
			Thread.sleep(150000);
		}
		else {
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, strTld);
		}
		
		//Test Step 3: Verify if domain registration workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));	
		
		//Test Step 4: Process the productsetup2 workflow in console admin
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + "." + strTld);
		caworkflowadminpage.processProductSetup2();
		
		//Test Step 5: Verify if productsetup2 workflow is approved
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + "." + strTld);
		if (caworkflowadminpage.getWorkflowStatus("productSetup2") != "approved") {	
			//Added refresh page to update current workflow status
			caworkflowadminpage.refreshWorkflowAdminPage();	    	        
   	    }
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "approved", 
			caworkflowadminpage.getWorkflowStatus("productsetup2"));
		
		driver.quit();
		System.out.println("End Test: verify_ComDomain_and_BasicCloudHostingOrder_InSalesDB");
	}
	
	
	@Parameters({"environment", "obsidian"})
	@Test
	public void verify_NzDomain_Order_InSalesDB (String environment, String obsidian) throws InterruptedException{
		
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = null;
		String strWorkflowStatus = null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		
		if (environment.equals("uat1")||environment.equals("uat2")) {
			strTld = "nz";
			strRegistrationPeriod = "2";
			strGreenCode = "PAY-207";
			strPaymentMethod = "American Express: 371449******8431 08/2026";
			strRegistrantDetails = "MelbourneIT";
		}
		
		//Test Step 1: Login to sales db and place an order for domain registration
		System.out.println("Start Test: verify_NzDomain_Order_InSalesDB");

		initialization(environment, "salesdburl");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails(environment);
		csnrcrmpage = csloginpage.clickLoginButton();
		csnrcrmpage.setGreenCode(strGreenCode);
		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
		cscreatedomainwindowpage.setDomainDetails(strDomainName, strTld, strRegistrationPeriod, strPaymentMethod);
		csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
		csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		strWorkflowId = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.quit();
		
		//Test Step 2: Process the domain registration order in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);		
		
		if (obsidian.equals("enabled")) {
			
			//Wait for workflow to be processed
			Thread.sleep(150000);
		}
		else {
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, strTld);
		}

		//Test Step 3: Verify if domain registration workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));	
		
		driver.quit();
		System.out.println("End Test: verify_NzDomain_Order_InSalesDB");
		
	}
	
}
	

