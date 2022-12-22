package com.MITDPS.ResellerPortal.testcases;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.commonfunctionality.MITDPS_CommonFunctionality;
import com.consoleadmin.pages.CADomainLevelPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.mitdpsresellerportal.pages.MITDPSLoginPage;
import com.mitdpsresellerportal.pages.MITDPSRegisterADomainPage;
import com.mitdpsresellerportal.pages.MITDPSRegistrantNameChangePage;
import com.mitdpsresellerportal.pages.MITDPSTabPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.DatabaseConnection;


public class Registrant_Name_Change extends MITDPS_CommonFunctionality {

	// Reseller portal [ages
	MITDPSLoginPage mitdpsLoginPage;
	MITDPSTabPage mitdpsTabPage;
	MITDPSRegistrantNameChangePage mitdpsregistrantnamechangePage;
	Register_Domain registerdomainPage;
	MITDPSRegisterADomainPage mitdpsRegisterADomainPage;
	// Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	String strAuWorkflowId;
	String strWorkflowStatus;
	String registerdomain;

	public static ExtentTest logger;
    
	public Registrant_Name_Change() {
		super();
	}

	@Parameters({ "environment" })
	@Test
	public void createTestDataForRegistrantChange(String environment)
			throws Exception {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strWorkflowStatus = null;
		//String tld[] = {".com,.net,.nz,.com.au"};
		String tld[] = {"com.au"};
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

		if (tlds.contains("com.au")) {
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

		registerdomain=strDomainName+"."+tlds; 
		System.out.println("Domain Name:"+registerdomain);
		driver.quit();
		System.out.println("End Test: verifyDomainRegistrationInResellerPortal");
		}
	}
	
	@Parameters({ "environment" })
	@Test(dependsOnMethods = "createTestDataForRegistrantChange")
	public void verifyDomainRegistrantNameChange(String environment)
			throws Exception {

		// Initialization (Test Data Creation and Assignment)
		String accountReference = "MEL-6040";
		String strPassword="comein22";
		
		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal");
		System.out.println("Start Test: verifyDomainRegistrantNameChange");
		initialization(environment, "resellerportalurl_mitdps");
		mitdpsTabPage=logintoResellerPortal(accountReference,strPassword);

		// Test Step 2: Navigate to Domains Tab and click on .Au Registrant Name Change Link
		test.log(LogStatus.INFO, "Navigate to Domains then .au Registrant Name Change Link");
		mitdpsTabPage.clickDomainsTab();
		mitdpsregistrantnamechangePage = mitdpsTabPage.clickAuNameChangeLink();

		// Test Step 3: Enter the Registered .Au domain
		test.log(LogStatus.INFO, "enter the domain Name and click on submit button");
		//mitdpsregistrantnamechangePage.enterAuDomainName(Register_Domain.registerdomain);
		mitdpsregistrantnamechangePage.enterAuDomainName(registerdomain);
		mitdpsregistrantnamechangePage.clickOnSubmitButton();
		
		// Test Step 4: Select the Existing sub account
		mitdpsregistrantnamechangePage.selectExistingCustomer("MEL-6040");

		// Test Step 5: Enter new .com.au Eligibility details and submit the details
		mitdpsregistrantnamechangePage.provideNewEligibilityDetailsForAu("ABN", "13080859721", "NETREGISTRY PTY LTD", "Company");
		mitdpsregistrantnamechangePage.tickIcertifyCheckbox();
		mitdpsregistrantnamechangePage.tickTermsAndConditionCheckbox();
		mitdpsregistrantnamechangePage.clickOnSubmitButtonForAuNameChange();
		// Test Step 6: Get the Reference ID
		test.log(LogStatus.INFO, "Get the refrence id");
		strAuWorkflowId = mitdpsregistrantnamechangePage.getAccountReferenceID();
		System.out.println("Reference ID:" + strAuWorkflowId);
		driver.quit();
		
		// Test Step 5: Process the domain registration order in console admin
		test.log(LogStatus.INFO, "Process the domain registration order in console admin");
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(strAuWorkflowId);

		// Test Step 6: Verify if registrantNameChange2 workflow is created or not
		test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
		strWorkflowStatus=caworkflowadminpage.getWorkflowStatus("registrantNameChange2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("confirmation requested")
				|| strWorkflowStatus.equalsIgnoreCase("Waiting for client confirmation"));

		driver.quit();
		System.out.println("End Test: verifyDomainRegistrantNameChange");
		}
	}

