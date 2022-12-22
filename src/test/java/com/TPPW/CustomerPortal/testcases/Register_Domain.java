package com.TPPW.CustomerPortal.testcases;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.commonfunctionality.TPPW_CommonFunctionality;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.tppcustomerportal.pages.TPPAccountContactPage;
import com.tppcustomerportal.pages.TPPAddDomainPrivacyPage;
import com.tppcustomerportal.pages.TPPBillingPage;
import com.tppcustomerportal.pages.TPPDomainSearchPage;
import com.tppcustomerportal.pages.TPPEligibilityDetailsPage;
import com.tppcustomerportal.pages.TPPHeaderPage;
import com.tppcustomerportal.pages.TPPHostingAndExtrasPage;
import com.tppcustomerportal.pages.TPPLoginPage;
import com.tppcustomerportal.pages.TPPOnlineOrderPage;
import com.tppcustomerportal.pages.TPPOrderCompletePage;
import com.tppcustomerportal.pages.TPPOrderPage;
import com.tppcustomerportal.pages.TPPRegistrantContactPage;
import com.util.TestUtil;

public class Register_Domain extends TPPW_CommonFunctionality {

	
	TPPOnlineOrderPage tpponlineorderpage;
	TPPDomainSearchPage tppdomainsearchpage;	
	TPPLoginPage tpploginpage;
	TPPHeaderPage tppheaderpage;
	TPPOrderPage tpporderpage;
	TPPAddDomainPrivacyPage tppadddomainprivacypage;
	TPPHostingAndExtrasPage tpphostingandextraspage;
	TPPAccountContactPage tppaccountcontactpage;
	TPPRegistrantContactPage tppregistrantcontactpage;
	TPPBillingPage tppbillingpage;
	TPPOrderCompletePage tppordercompletepage;
	TPPEligibilityDetailsPage tppeligibilitydetailspage;
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	
	TestUtil testUtil;
	public static ExtentTest logger;
	
	public Register_Domain() {
		super();
	}
	
	@Parameters({ "environment" })
	@Test
	public void verifyDomainRegistrationInCustomerPortal(String environment) throws Exception 
	{
		
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String tld[]= {"com", "net", "com.au"};
		String accountReference = "TPP-60053";
		String password="comein22";
		String strEligibilityIDType="ABN";
	    String strEligibilityIDNumber="21073716793";
	    String strCompanyName="Arq Group Limited";
	    String strEligibilityType="Company";
	    String strExistingCard="Number: 4111********1111 Expiry: 03/2021";
		
		for(String tlds : tld) {
		DateFormat df = new SimpleDateFormat("ddMMYYYY-hhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		
		//Test Step 1: Login to customer portal and place a domain registration order for .com tld
		System.out.println("Start Test: testDomainRegistration2WorkflowForCom");
		initialization(environment, "customerportalurl_tpp");
		
		tpploginpage = new TPPLoginPage();
		tpploginpage.setLoginDetails(accountReference, password);
		tppheaderpage = tpploginpage.clickLoginButton();
		tpporderpage = tppheaderpage.clickOrderTab();
		tpporderpage.setDomainNameAndTld(strDomainName, "." + tlds);
		tppdomainsearchpage = tpporderpage.clickNewDomainSearchButton();

		tppadddomainprivacypage = tppdomainsearchpage.clickContinueToCheckoutWithDomainPrivacy();
		tpphostingandextraspage= tppadddomainprivacypage.clickNoThanks();
		tppregistrantcontactpage = tpphostingandextraspage.clickContinueButtonWithoutAccountContact();
		tppbillingpage = tppregistrantcontactpage.clickContinueButton();
		if(tlds=="com.au") {
			enterEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
		}
		//Test Step 2: Select existing credit card details and submit the order 
		selectExistingCard(strExistingCard);

		//Test Step 3: Verify if order is completed
		fetchWorkflowId(); 
		driver.close();
		
		//Test Step 4: Process the domain registration order in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(TPPW_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.processDomainRegistration2Workflow(TPPW_CommonFunctionality.strWorkflowId, tlds);
						
		//Test Step 5: Verify if domain registration workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(TPPW_CommonFunctionality.strWorkflowId);
		
		driver.close();
		System.out.println("End Test: testDomainRegistration2WorkflowForCom");
				
		}	
}
}
