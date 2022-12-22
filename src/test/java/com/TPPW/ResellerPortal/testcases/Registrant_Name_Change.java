package com.TPPW.ResellerPortal.testcases;
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
import com.tppresellerportal.pages.TPPRegistrantNameChangePage;
import com.tppresellerportal.pages.TPPTabPage;

public class Registrant_Name_Change extends TPPW_CommonFunctionality {

	// Reseller portal [ages
	TPPLoginPage tppLoginPage;
	TPPTabPage tppTabPage;
	TPPRegistrantNameChangePage tppregistrantnamechangePage;
	Register_Domain registerdomainPage;

	// Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	String strAuWorkflowId;
	String strWorkflowStatus;

	public static ExtentTest logger;
    
	public Registrant_Name_Change() {
		super();
	}

	@Parameters({ "environment" })
	@Test
	public void verifyDomainRegistrantNameChange(String environment)
			throws Exception {

		// Initialization (Test Data Creation and Assignment)
		String accountReference = "TPP-60053";
        String password="comein22";
        String strEligibilityIDType="ABN";
        String strEligibilityIDNumber="04222488881";
        String strCompanyName="NETREGISTRY PTY LTD";
        String strEligibilityType="Company";
        String strExistingCustomer="TPP-60053";
        
		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal");
		System.out.println("Start Test: verifyDomainRegistrantNameChange");
		initialization(environment, "resellerportalurl_mitdps");
		loginToResellerPortal(accountReference,password);

		// Test Step 2: Navigate to Domains Tab and click on .Au Registrant Name Change Link
		test.log(LogStatus.INFO, "Navigate to Domains then .au Registrant Name Change Link");
		tppTabPage=new TPPTabPage();
		tppTabPage.clickDomainsTab();
		tppregistrantnamechangePage = tppTabPage.clickAuNameChangeLink();

		// Test Step 3: Enter the Register .Au domain
		test.log(LogStatus.INFO, "enter the domain Name and click on submit button");
		tppregistrantnamechangePage.enterAuDomainName(Register_Domain.registerdomain);
		tppregistrantnamechangePage.clickOnSubmitButton();
		
		// Test Step 4: Select the Existing sub account
		tppregistrantnamechangePage.selectExistingCustomer(strExistingCustomer);

		// Test Step 5: Enter new .com.au Eligibility details and submit the details
		enterNewEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
		
		// Test Step 6: Get the Reference ID
		test.log(LogStatus.INFO, "Get the refrence id");
		strAuWorkflowId = tppregistrantnamechangePage.getAccountReferenceID();
		System.out.println("Reference ID:" + strAuWorkflowId);
		driver.quit();
		
		// Test Step 5: Process the domain registration order in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(strAuWorkflowId);

		// Test Step 6: Verify if registrantNameChange2 workflow is created or not
		test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("verify eligibility")
				|| strWorkflowStatus.equalsIgnoreCase("waiting registrar approval"));

		driver.quit();
		System.out.println("End Test: verifyDomainRegistrantNameChange");
		}
	}

