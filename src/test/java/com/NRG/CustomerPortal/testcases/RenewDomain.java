package com.NRG.CustomerPortal.testcases;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.commonfunctionality.NRGOld_CommonFunctionality;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.netregistryoldwebsite.pages.NRGBillingPage;
import com.netregistryoldwebsite.pages.NRGDomainDetailsPage;
import com.netregistryoldwebsite.pages.NRGHeaderPage;
import com.netregistryoldwebsite.pages.NRGLoginPage;
import com.netregistryoldwebsite.pages.NRGOrderCompletePage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class RenewDomain extends NRGOld_CommonFunctionality
{
	NRGLoginPage nrgloginpage;
	NRGHeaderPage nrgheaderpage;
	NRGDomainDetailsPage nrgdomaindetailspage;
	NRGBillingPage nrgbillingpage;
	NRGOrderCompletePage nrgordercompletepage;
	NRGOld_CommonFunctionality nrgcommonfunctionality;
	
	
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
	 * Test case to register a domain and renew the domain from NRG customer portal
	 * Test data : for .com use domain : test-renewdomain001
	 * 			   for .au use domain : N/A
	 */
	@Parameters({ "environment" })
	@Test
	public void renewDomainFromCustomerPortal(String environment) throws Exception 
	{

		// Initialization (Test Data Creation and Assignment)
		strPassword = "comein22";
		accountReference = "ARQ-45";
		//strDomainName = "test-renewdomain001";
		strDomainName="testrenewdomain1421";
		strTld = ".net";
		
		// Renew domain from customer portal
		System.out.println("Start Test: renewDomainFromCustomerPortal");
		test.log(LogStatus.INFO, "Renew Domain From Customer Portal for " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_netregistry");
		
		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Customer portal - STARTED");
		nrgloginpage = new NRGLoginPage();
		nrgloginpage.setLoginDetails(accountReference, strPassword);
		nrgheaderpage = nrgloginpage.clickLoginButton();
		test.log(LogStatus.INFO, "Login to Reseller portal - COMPLETED");
		
		// Test Step 2: Navigate to Domain Details page and place an order for renew domain
		test.log(LogStatus.INFO, "Domain renewal completed -STARTED");
		nrgdomaindetailspage=nrgheaderpage.viewDomain(strDomainName+strTld);
		nrgbillingpage=nrgdomaindetailspage.cickRenewLink();
		nrgbillingpage.selectPrepaidPaymentMethod(existingPaymentMethod);
		nrgbillingpage.tickAutoRenew();
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickPlaceYourOrder();
		fetchRefrenceAndWorkflowId();
		driver.quit();
				
		// Test Step 3: Login to console admin test.log(LogStatus.INFO,"Login to console admin");
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();

 		// Test Step 4: Search a workflow ID and verify workflow status
 		test.log(LogStatus.INFO, "Search a workflow ID in console admin and verify workflow status");
 		caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
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
