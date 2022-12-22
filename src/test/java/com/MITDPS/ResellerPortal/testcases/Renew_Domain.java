package com.MITDPS.ResellerPortal.testcases;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.commonfunctionality.MITDPS_CommonFunctionality;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.mitdpsresellerportal.pages.MITDPSLoginPage;
import com.mitdpsresellerportal.pages.MITDPSRenewDomainsCartPage;
import com.mitdpsresellerportal.pages.MITDPSRenewDomainsOrderCompletePage;
import com.mitdpsresellerportal.pages.MITDPSRenewDomainsPage;
import com.mitdpsresellerportal.pages.MITDPSTabPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class Renew_Domain extends MITDPS_CommonFunctionality
{
	MITDPSLoginPage mitdpsloginpage;
	MITDPSTabPage mitdpstabpage;
	MITDPSRenewDomainsPage mitdpsrenewdomainspage;
	MITDPSRenewDomainsCartPage mitdpsrenewdomainscartpage;
	MITDPSRenewDomainsOrderCompletePage mitdpsrenewdomainsordercompletepage;

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
	String renewedDomainName = null;
	String renewedWorkflowId = null;
	String workflowStatusAfterCompletionManually = null;
	String workflowStatusAfterCompletionBySchedular = null;
	String existingPaymentMethod = "Prepaid credit: Current Balance:";
	String strTld = null;
	boolean flag = true;

	public Renew_Domain() 
	{
		super();
	}

	/*
	 * Test case to register a domain and renew the domain from DPS reseller portal
	 * Test data : for .com use domain : test-com-renewdomain01
	 * 			   for .net use domain : test-net-renewdomain01
	 * 			   for .nz use domain : testconsoleregression27082020085123
	 * 			   for .au use domain : N/A
	 */
	@Parameters({ "environment" })
	@Test
	public void renewDomainFromResellerPortal(String environment) throws Exception 
	{

		// Initialization (Test Data Creation and Assignment)
		strPassword = "comein22";
		accountReference = "MEL-6040";
		strDomainName = "testconsoleregression26032021105701";
		strTld = ".com";
		
		// Renew domain from reseller portal
		System.out.println("Start Test: renewDomainFromResellerPortal");
		test.log(LogStatus.INFO, "Renew Domain From Reseller Portal for " +strTld+ " - STARTED");
		initialization(environment, "resellerportalurl_mitdps");
        
		// Test Step 1: Login to reseller portal
		mitdpstabpage = logintoResellerPortal(accountReference,strPassword);
		
		// Test Step 2: Navigate to Renew Domain Page
		test.log(LogStatus.INFO, "Domain renewal started -STARTED");
		test.log(LogStatus.INFO, "click on domains tab");
		mitdpstabpage.clickDomainsTab();
		test.log(LogStatus.INFO, "click on domainRenewal link  tab");
		mitdpsrenewdomainspage = mitdpstabpage.clickDomainRenewLink();
		test.log(LogStatus.INFO, "enter the domain name and tld");
		mitdpsrenewdomainspage.searchDomainNameToBeRenewed(strDomainName + strTld);
		test.log(LogStatus.INFO, "click on search button");
		mitdpsrenewdomainspage.clickOnSearchButton();
		test.log(LogStatus.INFO, "select domain name checkbox");
		mitdpsrenewdomainspage.selectDomainNameCheckbox();
		String domainNameSelectedForRenewal = mitdpsrenewdomainspage.getDomainName();
		test.log(LogStatus.INFO, "click on add to domain renewal list");
		mitdpsrenewdomainscartpage = mitdpsrenewdomainspage.clickOnAddDomainsToList();

		// Test Step 3: Add domains to cart
		test.log(LogStatus.INFO, "Add Domains to cart");
		String domainNameToBeRenewed = mitdpsrenewdomainscartpage.getDomainNameToBeRenewed();
		Assert.assertEquals(domainNameSelectedForRenewal, domainNameToBeRenewed,"Domain name is available for renewal");
		expiryDateBeforeRenewal = mitdpsrenewdomainscartpage.getExpiryDateOfDomain();
		mitdpsrenewdomainscartpage.checkTermsAndConditions();
		//mitdpsrenewdomainscartpage.selectPaymentMethod(existingPaymentMethod);
		mitdpsrenewdomainsordercompletepage = mitdpsrenewdomainscartpage.clickOnRenewDomain();

		// Test Step 4: Verify the namespace of domain then Complete a renewal of domain and copy workflow ID
		test.log(LogStatus.INFO,
				"Verify the namespace of domain then Complete a renewal of domain and copy workflow ID");
		if (strTld.equalsIgnoreCase("info") || strTld.equalsIgnoreCase("org")) 
		{
			String errorMessage = mitdpsrenewdomainsordercompletepage.getErrorMessageFromOrderCompletePage();
			Assert.assertEquals(errorMessage, "domain " + strDomainName+strTld+ " is not currently eligible for renewal. reason[domain status = null]");
			flag = false;
		} else 
		{
			renewedDomainName = mitdpsrenewdomainsordercompletepage.getRenewedDomainName();
			renewedWorkflowId = mitdpsrenewdomainsordercompletepage.getWorkflowIdOfRenewedDomain();
			Assert.assertEquals(renewedDomainName, domainNameToBeRenewed, "Domain name not renewed");
		}
		driver.quit();
		
		// Test Step 5: Login to console admin test.log(LogStatus.INFO,"Login to console admin");
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();

		// Test Step 6: Search a workflow ID and verify workflow status
		test.log(LogStatus.INFO, "Search a workflow ID in console admin and verify workflow status");
		caworkflowadminpage = caheaderpage.searchWorkflow(renewedWorkflowId);
		caworkflowadminpage.clickOnWorkflowId();
		String workflowType = caworkflowadminpage.verifyWorkflowType();
		Assert.assertEquals(workflowType, "renewal2", "renewal2 workflow is not created");

		// Test Step 7: Verify workflow status and perform the execution of workflow
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
		
		test.log(LogStatus.INFO, "Renew Domain From Reseller Portal for " +strTld+ " - COMPLETED");
		System.out.println("End Test: renewDomainFromResellerPortal");
	}	
}

