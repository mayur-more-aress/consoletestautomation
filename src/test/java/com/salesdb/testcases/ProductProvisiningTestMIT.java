package com.salesdb.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;

import com.consoleadmin.pages.CADomainLevelPage;
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
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class ProductProvisiningTestMIT extends TestBase{
	//Console Sales DB Pages
	CSLoginPage csloginpage;
	CSNrCRMPage csnrcrmpage;
	CSCreateDomainWindowPage cscreatedomainwindowpage;
	CSRegistrantDetailsPage csregistrantdetailspage;
	CSShowDomainServicesPage csshowdomainservicespage;
	CSWorkflowNotificationPage csworkflownotificationpage;
	CSAUEligibilityPage csaueligibilitypage;
	CADomainLevelPage cadomainlevelpage;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;
	public static String registerdomain;

	public ProductProvisiningTestMIT() {
		super();
	}
	
	@Parameters({"environment", "obsidian"})
	@Test
	public void verify_Domain_and_RapidSSLOrder_InSalesDB(String environment, String obsidian) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strAddOnProductName = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = null;
		String strWorkflowStatus = null;
		String strProductName=null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);

		if (environment.equals("uat1")||environment.equals("uat2")) {
			strTld = "com";
			strRegistrationPeriod = "1 x Y";
			strGreenCode = "MEL-6007";
			strAddOnProductName = "RAPIDSSL-128-BIT";
			strPaymentMethod = "Visa: 4111xxxxxxxx1111 03/21";
			strRegistrantDetails = "QA Team";
			strProductName="Business+ Cloud Hosting";
		}
	
		//Test Step 1: Login to sales db and place an order for domain registration
		System.out.println("Start Test: verify_Domain_SalesDB_RapidSSL");
		initialization(environment, "salesdburl");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails(environment);
		csnrcrmpage = csloginpage.clickLoginButton();
		csnrcrmpage.setGreenCode(strGreenCode);
		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
		cscreatedomainwindowpage.setDomainDetails(strDomainName, strTld, strRegistrationPeriod,strPaymentMethod);
		csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
		csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
		csworkflownotificationpage = csnrcrmpage.clickConfirmDomain(strDomainName);
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
		registerdomain=strDomainName; 
		System.out.println("Domain Name:"+registerdomain);
		
		//Test Step 4: Setup the VIRTUALHOST service line
		cadomainlevelpage=caheaderpage.searchDomainName(registerdomain);
		cadomainlevelpage.purchaseProduct(strProductName);
		driver.quit();
		
		//Test Step 5: Login to sales db and purchase a product i.e.Rapid SSL
		initialization(environment, "salesdburl");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails(environment);
		csnrcrmpage = csloginpage.clickLoginButton();
		csnrcrmpage.setGreenCode(strGreenCode);
		
		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(registerdomain);
		csshowdomainservicespage.setAddOnProduct(strAddOnProductName);
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		Assert.assertEquals(csworkflownotificationpage.getNotificationMessage(), "Services are successfully confirmed");
		strWorkflowId = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.quit();
	
		//Test Step 6: Login to console admin and Process the product setup2 workflow in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
 	    caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processProductSetup2Workflow(strWorkflowId);
		
		//Test Step 7: Verify if productsetup2 workflow is approved and also sslprovisioning workflow is created
		caworkflowadminpage = caheaderpage.searchWorkflow(registerdomain);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("productSetup2");
		test.log(LogStatus.INFO, "The product setup workflow is in step " +strWorkflowStatus);
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("approved"));
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("sslprovisioning");
		test.log(LogStatus.INFO, "The sslprovisioning workflow is in step " +strWorkflowStatus);
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("ready to create email alias"));
		driver.quit();
		System.out.println("End Test: verify_Domain_SalesDB_RapidSSL");
	}
	
	
	@Parameters({"environment"})
	@Test
	public void verify_Domain_and_HostingService_Order_InSalesDB(String environment) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strAddOnProductName = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = null;
		String strWorkflowStatus = null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);

		if (environment.equals("uat1")||environment.equals("uat2")) {
			strTld = "com";
			strRegistrationPeriod = "1 x Y";
			strGreenCode = "MEL-6007";
			strPaymentMethod = "Prepaid credit:";
			strRegistrantDetails = "QA Team";
			strAddOnProductName="DFY-GROW-CP-BDL";
			
		}
	
		//Test Step 1: Login to sales db and place an order for domain registration and purchase web hosting service-i.e. Grow Website Addon - cPanel Hosting
		System.out.println("Start Test:verify_Domain_and_HostingService_Order_InSalesDB");
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
		csshowdomainservicespage.setAddOnProduct(strAddOnProductName);
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		Assert.assertEquals(csworkflownotificationpage.getNotificationMessage(), "Services are successfully confirmed");
		strWorkflowId = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.quit();
		
		//Test Step 2: Process the domain registration and product setup2 workflow in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, strTld);
		registerdomain=strDomainName; 
		System.out.println("Domain Name:"+registerdomain);
		caworkflowadminpage.processProductSetup2();
		
		//Test Step 3: Verify if domain registration and product setup2 workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(registerdomain);
		
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));	
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("productSetup2");
		test.log(LogStatus.INFO, "The product setup workflow is in step " +strWorkflowStatus);
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("approved"));
		driver.quit();
		System.out.println("End Test:verify_Domain_and_HostingService_Order_InSalesDB");
}
	@Parameters({"environment"})
	@Test
	public void verify_Domain_and_DIFM_Order_InSalesDB(String environment) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strAddOnProductName = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = null;
		String strWorkflowStatus = null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);

		if (environment.equals("uat1")||environment.equals("uat2")) {
			strTld = "net";
			strRegistrationPeriod = "1 x Y";
			strGreenCode = "MEL-6007";
			strPaymentMethod = "Invoice";
			strRegistrantDetails = "QA Team";
			strAddOnProductName="DIFM-ADD-ABKG";
			
		}
	
		//Test Step 1: Login to sales db and place an order for domain registration and purchase web hosting service-i.e. Grow Website Addon - cPanel Hosting
		System.out.println("Start Test:verify_Domain_and_DIFM_Order_InSalesDB");
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
		csshowdomainservicespage.setAddOnProduct(strAddOnProductName);
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		Assert.assertEquals(csworkflownotificationpage.getNotificationMessage(), "Services are successfully confirmed");
		strWorkflowId = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.quit();
		
		//Test Step 2: Process the domain registration and product setup2 workflow in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, strTld);
		registerdomain=strDomainName; 
		System.out.println("Domain Name:"+registerdomain);
		//caworkflowadminpage.processProductSetup2();
		
		//Test Step 3: Verify if domain registration and product setup2 workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(registerdomain);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));	
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("productSetup2");
		test.log(LogStatus.INFO, "The product setup workflow is in step " +strWorkflowStatus);
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("pending") || strWorkflowStatus.equalsIgnoreCase("waiting registrar approval"));
		driver.quit();
		System.out.println("End Test:verify_Domain_and_DIFM_Order_InSalesDB");
}
	
	@Parameters({"environment"})
	@Test
	public void verify_Domain_and_DomainManagerDomainPrivacy_Order_InSalesDB(String environment) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strAddOnProductName = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = null;
		String strWorkflowStatus = null;
		String strMajorProduct=null;
		String strProductPeriod=null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);

		if (environment.equals("uat1")||environment.equals("uat2")) {
			strGreenCode = "MEL-6005";
			strTld = "net";
			strRegistrationPeriod = "1 x Y";
			strProductPeriod="1 x Y AU$15.95[ AU$0 setup]";
			strMajorProduct="Domain Manager";
			strProductPeriod="1 x Y AU$40[ AU$0 setup]";
			strPaymentMethod = "Visa: 4111xxxxxxxx1111 01/22";
			strRegistrantDetails = "QA Team";
			strAddOnProductName="WHOIS-PRIV";
		}
	
		//Test Step 1: Login to sales db and place an order for domain registration and purchase web hosting service-i.e. Grow Website Addon - cPanel Hosting
		System.out.println("Start Test:verify_Domain_and_DomainManagerDomainPrivacy_Order_InSalesDB");
		initialization(environment, "salesdburl");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails(environment);
		csnrcrmpage = csloginpage.clickLoginButton();
		csnrcrmpage.setGreenCode(strGreenCode);
		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
		cscreatedomainwindowpage.setDomainandMajorProductDetails(strDomainName,strTld,strRegistrationPeriod,strMajorProduct,strProductPeriod, strPaymentMethod);
		csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
		csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
		csshowdomainservicespage.setAddOnProduct(strAddOnProductName);
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		Assert.assertEquals(csworkflownotificationpage.getNotificationMessage(), "Services are successfully confirmed");
		strWorkflowId = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.quit();	
		
		//Test Step 2: Process the domain registration and product setup2 workflow in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, strTld);
		caworkflowadminpage.processProductSetup2();
		
		//Test Step 3: Verify if domain registration and product setup2 workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));	
		registerdomain=strDomainName+"."+strTld; 
		System.out.println("Domain Name:"+registerdomain);
		driver.quit();
		System.out.println("End Test:verify_Domain_and_DomainManagerDomainPrivacy_Order_InSalesDB");
}
	
	@Parameters({"environment"})
	@Test
	public void verify_Domain_and_DomainPrivacy_Order_InSalesDB(String environment) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		strTld = "net";
		String strRegistrationPeriod = null;
		String strAddOnProductName = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = "14181304";
		String strWorkflowStatus = null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);

		if (environment.equals("uat1")||environment.equals("uat2")) {
			
			strRegistrationPeriod = "1 x Y";
			strGreenCode = "MEL-6005";
			strPaymentMethod = "Visa: 4111xxxxxxxx1111 01/22";
			strRegistrantDetails = "QA Team";
			strAddOnProductName="WHOIS-PRIV";
			
		}
	
		//Test Step 1: Login to sales db and place an order for domain registration and purchase web hosting service-i.e. Grow Website Addon - cPanel Hosting
		System.out.println("Start Test:verify_Domain_and_DomainPrivacy_Order_InSalesDB");
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
		csshowdomainservicespage.setAddOnProduct(strAddOnProductName);
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		Assert.assertEquals(csworkflownotificationpage.getNotificationMessage(), "Services are successfully confirmed");
		strWorkflowId = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.quit();
		
		//Test Step 2: Process the domain registration and product setup2 workflow in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, strTld);
		caworkflowadminpage.processProductSetup2();
		
		//Test Step 3: Verify if domain registration and product setup2 workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));	
		registerdomain=strDomainName+"."+strTld;
		System.out.println("Domain Name:"+registerdomain);
		driver.quit();
		System.out.println("End Test:verify_Domain_and_DomainPrivacy_Order_InSalesDB");
}
	
	@Parameters({"environment"})
	@Test
	public void verify_Domain_and_ComponentProduct_InSalesDB(String environment) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = null;
		String strWorkflowStatus = null;
		String strMajorProduct=null;
		String strProductPeriod=null;
		String strAddOnProductName=null;

		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);

		if (environment.equals("uat1")||environment.equals("uat2")) {
			strTld = "com";
			strRegistrationPeriod = "1 x Y";
			strGreenCode = "MEL-6005";
			strPaymentMethod = "Visa: 4111xxxxxxxx1111 01/22";
			strRegistrantDetails = "QA Team";
			strMajorProduct="cPanel Business";
			strProductPeriod="1 x M";
			strAddOnProductName="WHOIS-PRIV";
		}

		//Test Step 1: Login to sales db and place an order for domain registration
		System.out.println("Start Test: verify_Domain_and_ComponentProduct_InSalesDB");
		initialization(environment, "salesdburl");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails(environment);
		csnrcrmpage = csloginpage.clickLoginButton();
		csnrcrmpage.setGreenCode(strGreenCode);
		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
		cscreatedomainwindowpage.setDomainandMajorProductDetails(strDomainName,strTld,strRegistrationPeriod,strMajorProduct,strProductPeriod, strPaymentMethod);
		csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
		csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
		csshowdomainservicespage.setAddOnProduct(strAddOnProductName);
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		Assert.assertEquals(csworkflownotificationpage.getNotificationMessage(), "Services are successfully confirmed");
		strWorkflowId = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.quit();

		//Test Step 2: Process the domain registration and product setup2 workflow in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, strTld);
		caworkflowadminpage.processProductSetup2();
		registerdomain=strDomainName+"."+strTld; 
		System.out.println("Domain Name:"+registerdomain);

		//Test Step 3: Verify if domain registration and product setup2 workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(registerdomain);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));	
		driver.quit();
		System.out.println("End Test:verify_Domain_and_ComponentProduct_InSalesDB");
	}

	@Parameters({"environment"})
	@Test
	public void verify_Domain_and_SolutionProduct_InSalesDB(String environment) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = null;
		String strWorkflowStatus = null;
		String strMajorProduct=null;
		String strProductPeriod=null;
		String strAddOnProductName=null;

		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);

		if (environment.equals("uat1")||environment.equals("uat2")) {
			strTld = "net";
			strRegistrationPeriod = "1 x Y";
			strGreenCode = "MEL-6007";
			//strGreenCode = "MEL-6005";
			strPaymentMethod = "Visa: 4111xxxxxxxx1111 01/25";
			//strPaymentMethod = "Visa: 4111xxxxxxxx1111 01/22";
			strRegistrantDetails = "QA Team";
			strMajorProduct="DIFM Plus cPanel";
			strProductPeriod="1 x M";
			strAddOnProductName="WHOIS-PRIV";
		}

		//Test Step 1: Login to sales db and place an order for domain registration
		System.out.println("Start Test: verify_Domain_and_ComponentProduct_InSalesDB");
		initialization(environment, "salesdburl");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails(environment);
		csnrcrmpage = csloginpage.clickLoginButton();
		csnrcrmpage.setGreenCode(strGreenCode);
		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
		cscreatedomainwindowpage.setDomainandMajorProductDetails(strDomainName,strTld,strRegistrationPeriod,strMajorProduct,strProductPeriod, strPaymentMethod);
		csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
		csshowdomainservicespage.setAddOnProduct(strAddOnProductName);
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		Assert.assertEquals(csworkflownotificationpage.getNotificationMessage(), "Services are successfully confirmed");
		strWorkflowId = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.quit();

		//Test Step 2: Process the domain registration and product setup2 workflow in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, strTld);
		caworkflowadminpage.processProductSetup2();
		registerdomain=strDomainName+"."+strTld; 
		System.out.println("Domain Name:"+registerdomain);

		//Test Step 3: Verify if domain registration and product setup2 workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(registerdomain);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));	
		driver.quit();
		System.out.println("End Test:verify_Domain_and_ComponentProduct_InSalesDB");
	}
	
	@Parameters({"environment"})
	@Test
	public void verify_Domain_and_DomainBack_Order_InSalesDB(String environment) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strAddOnProductName = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = null;
		String strWorkflowStatus = null;

		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);

		if (environment.equals("uat1")||environment.equals("uat2")) {
			strTld = "net";
			strRegistrationPeriod = "1 x Y";
			strGreenCode = "ARQ-45";
			//strGreenCode = "MEL-6005";
			strPaymentMethod = "Visa: 411111******1111 01/2024";
			//strPaymentMethod = "Visa: 4111xxxxxxxx1111 01/22";
			strRegistrantDetails = "QA Team";
			strAddOnProductName="DOM-BACK-ORDER";

		}

		//Test Step 1: Login to sales db and place an order for domain registration and purchase web hosting service-i.e. Grow Website Addon - cPanel Hosting
		System.out.println("Start verify_Domain_and_DomainBack_Order_InSalesDB");
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
		csshowdomainservicespage.setAddOnProduct(strAddOnProductName);
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		Assert.assertEquals(csworkflownotificationpage.getNotificationMessage(), "Services are successfully confirmed");
		strWorkflowId = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.quit();

		//Test Step 2: Process the domain registration and product setup2 workflow in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, strTld);
		registerdomain=strDomainName+"."+strTld;
		System.out.println("Domain Name:"+registerdomain);
		caworkflowadminpage.processProductSetup2();

		//Test Step 3: Verify if domain registration and product setup2 workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(registerdomain);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));	
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("productSetup2");
		test.log(LogStatus.INFO, "The product setup workflow is in step " +strWorkflowStatus);
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("approved") || strWorkflowStatus.equalsIgnoreCase("not an email migration order"));
		driver.quit();
		System.out.println("End Test: verify_Domain_and_DomainBack_Order_InSalesDB");
	}

}	

