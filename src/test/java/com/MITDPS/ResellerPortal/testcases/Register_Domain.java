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
import com.mitdpsresellerportal.pages.MITDPSRegisterADomainPage;
import com.mitdpsresellerportal.pages.MITDPSTabPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class Register_Domain extends MITDPS_CommonFunctionality {

	// Reseller portal [ages
	MITDPSLoginPage mitdpsLoginPage;
	MITDPSTabPage mitdpsTabPage;
	MITDPSRegisterADomainPage mitdpsRegisterADomainPage;

	// Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	public static String registerdomain;

	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public Register_Domain() {
		super();
	}

	@Parameters({ "environment" })
	@Test
	public void verifyDomainRegistrationInResellerPortal(String environment)
			throws Exception {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strWorkflowStatus = null;
		String tld[] = {"com"};
		String accountReference = "MEL-6040";
		String strPassword="comein22";
		String nameserver1="ns1.partnerconsole.net";
		String nameserver2="ns2.partnerconsole.net";
		String registrantContact="Qa Team";

		for(String tlds : tld) {
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);

		// Test Step 1: Login to reseller portal
		
		test.log(LogStatus.INFO, "Login to Reseller portal");
		System.out.println("Start Test: verifyDomainRegistrationInResellerPortal");
		initialization(environment, "resellerportalurl_mitdps");
		mitdpsTabPage = logintoResellerPortal(accountReference,strPassword);

		// Test Step 2: Navigate to Register Domain
		test.log(LogStatus.INFO, "Navigate to Domains then Register and search for a domain");
		mitdpsTabPage.clickDomainsTab();
		mitdpsRegisterADomainPage = mitdpsTabPage.clickRegisterLink();

		// Test Step 3: Register a domain
		test.log(LogStatus.INFO, "Verify search result message");
		mitdpsRegisterADomainPage.setDomainNameAndTld(strDomainName, "." + tlds);
		mitdpsRegisterADomainPage.selectExistingCustomer();
		mitdpsRegisterADomainPage.selectRegistranContact(registrantContact);

		if (tlds.contains(".au")) {
			System.out.println("This is the namespace " + tlds + ". Eligibility details is requied.");
			mitdpsRegisterADomainPage.provideEligibilityDetailsForAu("ABN","54 109 565 095","ARQ GROUP WHOLESALE PTY LTD","Company");
		}

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

		// Test Step 6: Verify if domain registration workflow is completed
		test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
		caworkflowadminpage = caheaderpage.searchWorkflow(MITDPS_CommonFunctionality.strWorkflowId);
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
