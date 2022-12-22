package com.MIT.CustomerPortal.testcases;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.commonfunctionality.MIT_CommonFunctionality;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.melbourneitwebsite.pages.MITBillingPage;
import com.melbourneitwebsite.pages.MITDomainDetailsPage;
import com.melbourneitwebsite.pages.MITHeaderPage;
import com.melbourneitwebsite.pages.MITLoginPage;
import com.melbourneitwebsite.pages.MITOrderCompletePage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class RenewDomain extends MIT_CommonFunctionality
{
	//MIT customer portal pages
	MITLoginPage mitloginpage;
	MITHeaderPage mitheaderpage;
	MITDomainDetailsPage mitdomiandetailspage;
	MITBillingPage mitbillingpage;
	MITOrderCompletePage mitordercompletepage;
	
	// Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	TestUtil testUtil;
	public static ExtentTest logger;
	
	String strPassword = null;
	String accountReference = null;
	String strDomainName = null;
	String expiryDateBeforeRenewal = null;
	String accountId = null;
	String renewedWorkflowId = null;
	String workflowStatusAfterCompletionManually = null;
	String workflowStatusAfterCompletionBySchedular = null;
	String existingPaymentMethod = "Prepaid credit: Current Balance:";
	String strTld = null;
	boolean flag = true;
	
	public RenewDomain() 
	{
		super();
	}
	
	/*
	 * Test case to register a domain and renew the domain from MIT customer portal
	 * Test data : for .com use domain : test-com-renewdomain01
	 * 			   for .au use domain : N/A
	 */
	@Parameters({ "environment" })
	@Test
	public void renewDomainFromCustomerPortal(String environment) throws Exception 
	{

		// Initialization (Test Data Creation and Assignment)
		strPassword = "comein22";
		accountReference = "MEL-6005";
		strDomainName = "testdomainrenewal253";
		strTld = ".com";
		
		// Renew domain from customer portal
		System.out.println("Start Test: renewDomainFromCustomerPortal");
		test.log(LogStatus.INFO, "Renew Domain From Customer Portal for " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_melbourneit");
		
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(accountReference,strPassword);
		
		// Test Step 2: Navigate to Domain Details page and place an order for renew domain
		mitheaderpage=new MITHeaderPage();
		test.log(LogStatus.INFO, "Domain renewal completed -STARTED");
		mitdomiandetailspage=mitheaderpage.viewDomain(strDomainName+strTld);
		mitbillingpage=mitdomiandetailspage.cickRenewLink();
		mitbillingpage.selectPrepaidPaymentMethod(existingPaymentMethod);
		mitbillingpage.tickAutoRenew();
		mitbillingpage.tickTermsAndConditions();
		mitordercompletepage = mitbillingpage.clickPlaceYourOrder();
		/*fetchWorkflowId();
		driver.quit();
		
		// Test Step 3: Login to console admin test.log(LogStatus.INFO,"Login to console admin");
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();

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
		System.out.println("End Test: renewDomainFromCustomerPortal");*/
		driver.close();
	}	
}
