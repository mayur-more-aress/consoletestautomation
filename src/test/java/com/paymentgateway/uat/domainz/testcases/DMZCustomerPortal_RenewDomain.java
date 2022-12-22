package com.paymentgateway.uat.domainz.testcases;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.base.TestBase;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.domainzwebsite.pages.DMZBillingPage;
import com.domainzwebsite.pages.DMZDomainDetailsPage;
import com.domainzwebsite.pages.DMZHeaderPage;
import com.domainzwebsite.pages.DMZLoginPage;
import com.domainzwebsite.pages.DMZOrderCompletePage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class DMZCustomerPortal_RenewDomain extends TestBase
{
	//DMZ customer portal pages
	DMZLoginPage dmzloginpage;
	DMZHeaderPage dmzheaderpage;
	DMZDomainDetailsPage dmzdomaindetailspage;
	DMZBillingPage dmzbillingpage;
	DMZOrderCompletePage dmzordercompletepage;
	
	// Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	TestUtil testUtil;
	public static ExtentTest logger;
	
	String strPassword = null;
	String accountReference = null;
	String strDomainName = null;
	String accountId = null;
	String renewedWorkflowId = null;
	String workflowStatusAfterCompletionManually = null;
	String workflowStatusAfterCompletionBySchedular = null;
	String existingPaymentMethod = "Visa credit card";
	String strTld = null;
	boolean flag = true;
		
	public DMZCustomerPortal_RenewDomain() 
	{
		super();
	}
	
	/*
	 * Test case to register a domain and renew the domain from Domainz customer portal
	 * Test data : for .com use domain : test-com-domainrenewal01
	 * 			   for .au use domain : N/A
	 */
	@Parameters({ "environment" })
	@Test
	public void renewDomainFromCustomerPortal(String environment) throws Exception 
	{

		// Initialization (Test Data Creation and Assignment)
		strPassword = "comein22";
		accountReference = "PAY-375";
		strDomainName = "test-com-domainrenewal01";
		strTld = ".com";
		
		// Renew domain from customer portal
		System.out.println("Start Test: renewDomainFromCustomerPortal");
		test.log(LogStatus.INFO, "Renew Domain From Customer Portal for " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_domainz");
		
		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Customer portal - STARTED");
		dmzloginpage = new DMZLoginPage();
		dmzloginpage.setLoginDetails(accountReference, strPassword);
		dmzheaderpage = dmzloginpage.clickLoginButton();
		test.log(LogStatus.INFO, "Login to Reseller portal - COMPLETED");
		
		// Test Step 2: Navigate to Domain Details page and place an order for renew domain
		test.log(LogStatus.INFO, "Domain renewal completed -STARTED");
		dmzdomaindetailspage=dmzheaderpage.viewDomain(strDomainName+strTld);
		dmzbillingpage=dmzdomaindetailspage.cickRenewLink();
		dmzbillingpage.selectPaymentMethod(existingPaymentMethod);
		dmzbillingpage.tickAutoRenew();
		dmzbillingpage.tickTermsAndConditions();
		dmzordercompletepage = dmzbillingpage.clickPlaceYourOrder();
		accountId = dmzordercompletepage.getAccountReferenceID();
		renewedWorkflowId = dmzordercompletepage.getSingleReferenceID();
		System.out.println("Account Reference:" + accountId);
		System.out.println("Renewal Workflow Id:" + renewedWorkflowId);
		driver.quit();
		
		// Test Step 3: Login to console admin test.log(LogStatus.INFO,"Login to console admin");
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);

		// Test Step 4: Search a workflow ID and verify workflow status
		test.log(LogStatus.INFO, "Search a workflow ID in console admin and verify workflow status");
		caworkflowadminpage = caheaderpage.searchWorkflow(renewedWorkflowId);
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
		driver.close();
		
		test.log(LogStatus.INFO, "Renew Domain From Customer Portal for " +strTld+ " - COMPLETED");
		System.out.println("End Test: renewDomainFromCustomerPortal");
	}	
}
