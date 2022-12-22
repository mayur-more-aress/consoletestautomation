package com.TPPW.ResellerPortal.testcases;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.commonfunctionality.TPPW_CommonFunctionality;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.tppresellerportal.pages.TPPLoginPage;
import com.tppresellerportal.pages.TPPRegisterADomainPage;
import com.tppresellerportal.pages.TPPTabPage;
import com.util.TestUtil;

public class Register_Domain extends TPPW_CommonFunctionality {

	// Reseller portal [ages
	TPPLoginPage tppLoginPage;
	TPPTabPage tppTabPage;
	TPPRegisterADomainPage tppRegisterADomainPage;

	// Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	public static String registerdomain;

	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;
	String accountReference="TPP-60053";
	String password="comein22";
	String nameserver1="ns1.partnerconsole.net";
	String nameserver2="ns2.partnerconsole.net";


	public Register_Domain() {
		super();
	}

	@Parameters({ "environment", "namespace"})
	@Test
	public void verifyDomainRegistrationInResellerPortal(String environment, String namespace)
			throws Exception {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strWorkflowStatus = null;

		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);

		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal");
		System.out.println("Start Test: verifyDomainRegistrationInResellerPortal");
		initialization(environment, "resellerportalurl_tpp");
		loginToResellerPortal(accountReference,password);

		// Test Step 2: Navigate to Register Domain
		test.log(LogStatus.INFO, "Navigate to Domains then Register and search for a domain");
		tppTabPage=new TPPTabPage();
		tppTabPage.clickDomainsTab();
		tppRegisterADomainPage = tppTabPage.clickRegisterLink();

		// Test Step 3: Register a domain
		test.log(LogStatus.INFO, "Verify search result message");
		tppRegisterADomainPage.setDomainNameAndTld(strDomainName, "." + namespace);
		tppRegisterADomainPage.selectExistingCustomer();
		tppRegisterADomainPage.selectRegistranContact("James Cooper");

		switch (namespace) {
		case "com.au":
		case "net.au":
			System.out.println("This is the namespace " + namespace + ". Eligibility details is requied.");
			tppRegisterADomainPage.provideEligibilityDetailsForAu("ABN","54 109 565 095","ARQ GROUP WHOLESALE PTY LTD","Company");
			break;
		case "org.au":
			System.out.println("This is the namespace " + namespace + ". Eligibility details is requied.");
			tppRegisterADomainPage.provideEligibilityDetailsForAu("ABN","54 109 565 095","ARQ GROUP WHOLESALE PTY LTD","Non-profit Organisation");
			break;
		case "id.au":
			System.out.println("This is the namespace " + namespace + ". Eligibility details is requied.");
			tppRegisterADomainPage.provideEligibilityDetailsForIdAu("ARQ GROUP WHOLESALE PTY LTD","Citizen/Resident");
			break;
		case "asn.au":
			System.out.println("This is the namespace " + namespace + ". Eligibility details is requied.");
			tppRegisterADomainPage.provideEligibilityDetailsForAu("ABN","54 109 565 095","ARQ GROUP WHOLESALE PTY LTD","Incorporated Association");
			break;
		default:
			System.out.println("Au eligibility is not required for this namespace");
		}

		chooseNameServers(nameserver1,nameserver2);
		tppRegisterADomainPage=new TPPRegisterADomainPage();
		tppRegisterADomainPage.clickRegisterDomainButton();

		// Test Step 4: Get the Order Reference ID
		getWorkflowId();
		driver.quit();

		// Test Step 5: Process the domain registration order in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(TPPW_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.processDomainRegistration2Workflow(TPPW_CommonFunctionality.strWorkflowId, namespace);

		// Test Step 6: Verify if domain registration workflow is completed
		test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
		caworkflowadminpage = caheaderpage.searchWorkflow(TPPW_CommonFunctionality.strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed")
				|| strWorkflowStatus.equalsIgnoreCase("update star rating"));

		driver.quit();
		System.out.println("End Test: verifyDomainRegistrationInResellerPortal");

	}

	@Parameters({ "environment" })
	@Test
	public void verifyDomainRegistrationInResellerPortal(String environment)
			throws Exception {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strWorkflowStatus = null;
		String tld[] = {"com", "net", "com.au"};

		for(String tlds : tld) {
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);

		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal");
		System.out.println("Start Test: verifyDomainRegistrationInResellerPortal");
		initialization(environment, "resellerportalurl_tpp");
		loginToResellerPortal(accountReference,password);

		// Test Step 2: Navigate to Register Domain
		test.log(LogStatus.INFO, "Navigate to Domains then Register and search for a domain");
		tppTabPage=new TPPTabPage();
		tppTabPage.clickDomainsTab();
		tppRegisterADomainPage = tppTabPage.clickRegisterLink();

		// Test Step 3: Register a domain
		test.log(LogStatus.INFO, "Verify search result message");
		tppRegisterADomainPage.setDomainNameAndTld(strDomainName, "." + tlds);
		tppRegisterADomainPage.selectExistingCustomer();
		tppRegisterADomainPage.selectRegistranContact("James Cooper");

		if (tlds.contains(".au")) {
			System.out.println("This is the namespace " + tlds + ". Eligibility details is requied.");
			tppRegisterADomainPage.provideEligibilityDetailsForAu("ABN","54 109 565 095","ARQ GROUP WHOLESALE PTY LTD","Company");
		}

		chooseNameServers(nameserver1,nameserver2);
		tppRegisterADomainPage=new TPPRegisterADomainPage();
		tppRegisterADomainPage.clickRegisterDomainButton();

		// Test Step 4: Get the Order Reference ID
		getWorkflowId();
		driver.quit();
		
		// Test Step 5: Process the domain registration order in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(TPPW_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.processDomainRegistration2Workflow(TPPW_CommonFunctionality.strWorkflowId, tlds);

		// Test Step 6: Verify if domain registration workflow is completed
		test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
		caworkflowadminpage = caheaderpage.searchWorkflow(TPPW_CommonFunctionality.strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed")
				|| strWorkflowStatus.equalsIgnoreCase("update star rating"));

		registerdomain=strDomainName+tld; 
		System.out.println("Domain Name:"+registerdomain);
		driver.quit();
		System.out.println("End Test: verifyDomainRegistrationInResellerPortal");
		}
	}

}
