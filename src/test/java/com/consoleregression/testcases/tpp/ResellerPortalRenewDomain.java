package com.consoleregression.testcases.tpp;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.base.TestBase;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.tppresellerportal.pages.TPPLoginPage;
import com.tppresellerportal.pages.TPPRenewDomainsCartPage;
import com.tppresellerportal.pages.TPPRenewDomainsOrderCompletePage;
import com.tppresellerportal.pages.TPPRenewDomainsPage;
import com.tppresellerportal.pages.TPPTabPage;
import com.util.TestUtil;

public class ResellerPortalRenewDomain extends TestBase 
{
	TPPLoginPage tpploginpage;
	TPPTabPage tpptabpage;
	TPPRenewDomainsPage tpprenewdomainspage;
	TPPRenewDomainsCartPage tpprenewdomainscartpage;
	TPPRenewDomainsOrderCompletePage tpprenewdomainsordercompletepage;

	// Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	TestUtil testUtil;
	public static ExtentTest logger;

	String strPassword = null;
	String accountReference = null;
	String strDomainName = null;
	String strTld = null;
	String expiryDateBeforeRenewal = null;
	String renewedDomainName = null;
	String renewedWorkflowId = null;
	String workflowStatusAfterCompletionManually = null;
	String workflowStatusAfterCompletionBySchedular = null;
	String existingPaymentMethod = "Prepaid credit: Current Balance:";
	boolean flag = true;

	public ResellerPortalRenewDomain() 
	{
		super();
	}
	
	/*
	 * Test case to register a domain and renew the domain from DPS reseller portal
	 * Test data : for .com use domain : test-com-domainrenewal01
	 * 			   for .net use domain : test-net-domainrenewal01
	 * 			   for .nz use domain : test-nz-domainrenewal01
	 * 			   for .au use domain : N/A
	 */
	@Parameters({ "environment" })
	@Test
	public void renewDomainFromResellerPortal(String environment) throws Exception 
	{
		// Initialization (Test Data Creation and Assignment)
		strPassword = "comein22";
		accountReference = "MEL-6038";
		strDomainName = "test-net-domainrenewal01";
        strTld = ".net";
        
        // Renew domain from reseller portal
        System.out.println("Start Test: renewDomainFromResellerPortal");
        test.log(LogStatus.INFO, "Renew Domain From Reseller Portal for " +strTld+ " - STARTED");
		initialization(environment, "resellerportalurl_tpp");
       
		// Test Step 1: Login to Reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal - STARTED");
		System.out.println("Start test: renewADomainFromResellerPortal");
		tpploginpage = new TPPLoginPage();
		tpploginpage.setLoginDetails(accountReference, strPassword);
		tpptabpage = tpploginpage.clickLoginButton();
		test.log(LogStatus.INFO, "Login to Reseller portal - COMPLETED");

		// Test Step 8: Navigate to Renew Domain Page
		test.log(LogStatus.INFO, "Domain renewal started -STARTED");
		test.log(LogStatus.INFO, "click on domains tab");
		tpptabpage.clickDomainsTab();
		test.log(LogStatus.INFO, "click on domainRenewal link  tab");
		tpprenewdomainspage = tpptabpage.clickDomainRenewLink();
		test.log(LogStatus.INFO, "enter the domain name and tld");
		tpprenewdomainspage.searchDomainNameToBeRenewed(strDomainName + strTld);
		test.log(LogStatus.INFO, "click on search button"+ strTld);
		tpprenewdomainspage.clickOnSearchButton();
		test.log(LogStatus.INFO, "select domain name checkbox"+ strTld);
		tpprenewdomainspage.selectDomainNameCheckbox();
		String domainNameSelectedForRenewal = tpprenewdomainspage.getDomainName();
		test.log(LogStatus.INFO, "click on add to domain renewal list"+ strTld);
		tpprenewdomainscartpage = tpprenewdomainspage.clickOnAddDomainsToList();

		// Test Step 9: Add domains to cart
		test.log(LogStatus.INFO, "Add Domains to cart");
		String domainNameToBeRenewed = tpprenewdomainscartpage.getDomainNameToBeRenewed();
		Assert.assertEquals(domainNameSelectedForRenewal, domainNameToBeRenewed,
				"Domain name is available for renewal");
		expiryDateBeforeRenewal = tpprenewdomainscartpage.getExpiryDateOfDomain();
		tpprenewdomainscartpage.checkTermsAndConditions();
		tpprenewdomainscartpage.selectPaymentMethod(existingPaymentMethod);
		tpprenewdomainsordercompletepage = tpprenewdomainscartpage.clickOnRenewDomain();

		// Test Step 10: copy the renewal workflow ID and domain
		test.log(LogStatus.INFO,"copy workflow ID");
		renewedDomainName = tpprenewdomainsordercompletepage.getRenewedDomainName();
		renewedWorkflowId = tpprenewdomainsordercompletepage.getWorkflowIdOfRenewedDomain();
		Assert.assertEquals(renewedDomainName, domainNameToBeRenewed, "Domain name not renewed");
		driver.quit();
		
		// Test Step 12: Login to console admin test.log(LogStatus.INFO,"Login to console admin");
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);

		// Test Step 13: Search a workflow ID and verify workflow status
		test.log(LogStatus.INFO, "Search a workflow ID in console admin and verify workflow status");
		caworkflowadminpage = caheaderpage.searchWorkflow(renewedWorkflowId);
		caworkflowadminpage.clickOnWorkflowId();
		String workflowType = caworkflowadminpage.verifyWorkflowType();
		Assert.assertEquals(workflowType, "renewal2", "renewal2 workflow is not created");

		// Test Step 14: Verify workflow status and perform the execution of workflow
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
		test.log(LogStatus.INFO, "Domain renewal started -COMPLETED");
		driver.close();
		
		test.log(LogStatus.INFO, "Renew Domain From Reseller Portal for " +strTld+ " - COMPLETED");
		System.out.println("End Test: renewDomainFromResellerPortal");
	}
}

