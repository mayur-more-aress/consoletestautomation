package com.MITDPS.ResellerPortal.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.commonfunctionality.MITDPS_CommonFunctionality;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.mitdpsresellerportal.pages.MITDPSLoginPage;
import com.mitdpsresellerportal.pages.MITDPSPurchaseOffice365ProductPage;
import com.mitdpsresellerportal.pages.MITDPSRegisterADomainPage;
import com.mitdpsresellerportal.pages.MITDPSTabPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class DomainAnd_ProductOrder extends MITDPS_CommonFunctionality {

	// Reseller portal [ages
	MITDPSLoginPage mitdpsLoginPage;
	MITDPSTabPage mitdpsTabPage;
	MITDPSRegisterADomainPage mitdpsRegisterADomainPage;
	MITDPSPurchaseOffice365ProductPage mitdpsOffice365productpage;

	// Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	public static String registerdomain;
	String strResponseDomain;

	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public DomainAnd_ProductOrder() {
		super();
	}

	@Parameters({ "environment" })
	@Test
	public void verifyDomainAndWebHostingProductRegistration(String environment)
			throws Exception {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strWorkflowStatus = null;
		String tld[] = {".com"};
		String accountReference = "MEL-6040";
		String strPassword="comein22";
		String hostingType="Cloud Basic 1 Month AU$29.00";
		String registrantContact="Qa Team";
		String nameserver1="ns1.partnerconsole.net";
		String nameserver2="ns2.partnerconsole.net";
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);

		for(String tlds : tld) {
			// Test Step 1: Login to reseller portal
			test.log(LogStatus.INFO, "Login to Reseller portal");
			System.out.println("Start Test: verifyDomainAndProductRegistrationInResellerPortal");
			initialization(environment, "resellerportalurl_mitdps");
			mitdpsTabPage = logintoResellerPortal(accountReference,strPassword);

			// Test Step 2: Navigate to Register Domain
			test.log(LogStatus.INFO, "Navigate to Domains then Register and search for a domain");
			mitdpsTabPage.clickDomainsTab();
			mitdpsRegisterADomainPage = mitdpsTabPage.clickRegisterLink();

			// Test Step 3: Register a domain
			test.log(LogStatus.INFO, "Verify search result message");
			mitdpsRegisterADomainPage.setDomainNameAndTld(strDomainName,tlds);
			mitdpsRegisterADomainPage.selectExistingCustomer();
			mitdpsRegisterADomainPage.selectRegistranContact(registrantContact);

			if (tlds.contains(".au")) {
				System.out.println("This is the namespace " + tlds + ". Eligibility details is requied.");
				mitdpsRegisterADomainPage.provideEligibilityDetailsForAu("ABN","54 109 565 095","ARQ GROUP WHOLESALE PTY LTD","Company");
			}

			mitdpsRegisterADomainPage.selectHosting(hostingType);
			chooseNameServers(nameserver1,nameserver2);

			// Test Step 4: Get the Order Reference ID
			fetchRefrenceId();
			driver.quit();

			// Test Step 5: Process the domain registration order in console admin
			test.log(LogStatus.INFO, "Process the domain registration order in console admin");
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strWorkflowId);
			caworkflowadminpage.processDomainRegistration2Workflow(MITDPS_CommonFunctionality.strWorkflowId, tlds);
			caworkflowadminpage.processProductSetupWF();

			// Test Step 6: Verify if domain registration workflow is completed
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caworkflowadminpage = caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed")
					|| strWorkflowStatus.equalsIgnoreCase("update star rating"));

			registerdomain=strDomainName+tlds; 
			System.out.println("Domain Name:"+registerdomain);
			driver.quit();
			System.out.println("End Test: verifyDomainAndProductRegistrationInResellerPortal");
		}
	}

	@Parameters({ "environment" })
	@Test
	public void verifyPurchaseOffice365Product(String environment) throws Exception
	{
		String strDomainName = null;
		String strWorkflowId = null;
		String strWorkflowStatus = null;
		String tld[] = {".com"};
		String accountReference = "MEL-6040";
		String strPassword="comein22";
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);
		String productName="1 x mailboxes, $19.95/mo";

		for(String tlds : tld) {
			// Test Step 1: Login to reseller portal
			test.log(LogStatus.INFO, "Login to Reseller portal");
			System.out.println("Start Test: verifyOffice365ProductRegistrationInResellerPortal");
			initialization(environment, "resellerportalurl_mitdps");
			logintoResellerPortal(accountReference,strPassword);

			// Test Step 2: Navigate to Register Domain
			test.log(LogStatus.INFO, "Navigate to Domains then Register and search for a domain");
			mitdpsOffice365productpage=new MITDPSPurchaseOffice365ProductPage();
			mitdpsOffice365productpage.clickOnPurchaseTab();
			mitdpsOffice365productpage.clickOnPurchaseOffice365ProductLink();
			mitdpsOffice365productpage.enterDomainName(strDomainName,tlds);
			mitdpsOffice365productpage.selectProductMicrosoftOffice365BusinessPremium(productName);
			mitdpsOffice365productpage.enterExistingCustomerGreenCode(accountReference);
			mitdpsOffice365productpage.tickTermsAndConditions();
			mitdpsOffice365productpage.clickOnOrder365ProductButton();
			String strResponseDomain=mitdpsOffice365productpage.getSingleReferenceID();
			System.out.println("The product purchase message:"+strResponseDomain);

			Assert.assertTrue(strResponseDomain.contains("Order reference"));
			strWorkflowId=mitdpsOffice365productpage.getWorkflowId(strResponseDomain);
			System.out.println("Product purchase workflow Id:"+strWorkflowId);
			driver.quit();

			// Test Step 5: Process the domain registration order in console admin
			test.log(LogStatus.INFO, "Process the domain registration order in console admin");
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			caworkflowadminpage.processProductSetup2Workflow(strWorkflowId);

			// Test Step 6: Verify if domain registration workflow is completed
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("productSetup2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("approved"));

			registerdomain=strDomainName+tlds; 
			System.out.println("Domain Name:"+registerdomain);
			driver.quit();
			System.out.println("End Test: verifyOffice365ProductRegistrationInResellerPortal");
		}

	}

	@Parameters({ "environment" })
	@Test
	public void verifyDomainAndDomainManagerProductRegistrationInResellerPortal(String environment)throws Exception {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strWorkflowStatus = null;
		String tld[] = {".com"};
		String accountReference = "MEL-6040";
		String strPassword="comein22";
		String nameserver1="ns1.partnerconsole.net";
		String nameserver2="ns2.partnerconsole.net";
		String hostingtype="Domain Manager 1 Month AU$9.95";
		String registrantContact="Qa Team";
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);

		for(String tlds : tld) {
			
			// Test Step 1: Login to reseller portal
			test.log(LogStatus.INFO, "Login to Reseller portal");
			System.out.println("Start Test: VerifyDomainAndDomainManagerProduct");
			initialization(environment, "resellerportalurl_mitdps");
			mitdpsTabPage = logintoResellerPortal(accountReference,strPassword);

			// Test Step 2: Navigate to Register Domain
			test.log(LogStatus.INFO, "Navigate to Domains then Register and search for a domain");
			mitdpsTabPage.clickDomainsTab();
			mitdpsRegisterADomainPage = mitdpsTabPage.clickRegisterLink();

			// Test Step 3: Register a domain
			test.log(LogStatus.INFO, "Verify search result message");
			mitdpsRegisterADomainPage.setDomainNameAndTld(strDomainName,tlds);
			mitdpsRegisterADomainPage.selectExistingCustomer();
			mitdpsRegisterADomainPage.selectRegistranContact(registrantContact);

			if (tlds.contains(".au")) {
				System.out.println("This is the namespace " + tlds + ". Eligibility details is requied.");
				mitdpsRegisterADomainPage.provideEligibilityDetailsForAu("ABN","54 109 565 095","ARQ GROUP WHOLESALE PTY LTD","Company");
			}

			mitdpsRegisterADomainPage.selectHosting(hostingtype);
			chooseNameServers(nameserver1,nameserver2);

			// Test Step 4: Get the Order Reference ID
			fetchRefrenceId();
			driver.quit();

			// Test Step 5: Process the domain registration order in console admin
			test.log(LogStatus.INFO, "Process the domain registration order in console admin");
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strWorkflowId);
			caworkflowadminpage.processDomainRegistration2Workflow(MITDPS_CommonFunctionality.strWorkflowId, tlds);
			caworkflowadminpage.processProductSetupWF();

			// Test Step 6: Verify if domain registration workflow is completed
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caworkflowadminpage = caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed")
					|| strWorkflowStatus.equalsIgnoreCase("update star rating"));

			registerdomain=strDomainName+tlds; 
			System.out.println("Domain Name:"+registerdomain);
			driver.quit();
			System.out.println("End Test:  VerifyDomainAndDomainManagerProduct");
		}
	}

}

