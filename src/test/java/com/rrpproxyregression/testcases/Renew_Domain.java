package com.rrpproxyregression.testcases;

import java.awt.AWTException;
import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.base.TestBase;
import com.consoleadmin.pages.CADomainLevelPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.relevantcodes.extentreports.LogStatus;
import com.rrpproxypage.java.RRPDomainsPage;
import com.rrpproxypage.java.RRPMyDomainsPage;
import com.rrpproxypage.java.RRPProxyLoginPage;
import com.rrpproxypage.java.RRPTabPage;
import com.tppresellerportal.pages.TPPLoginPage;
import com.tppresellerportal.pages.TPPRenewDomainsCartPage;
import com.tppresellerportal.pages.TPPRenewDomainsOrderCompletePage;
import com.tppresellerportal.pages.TPPRenewDomainsPage;
import com.tppresellerportal.pages.TPPTabPage;

public class Renew_Domain extends TestBase{

	//Console pages
	TPPLoginPage tpploginpage;
	TPPTabPage tpptabpage;
	TPPRenewDomainsPage tpprenewdomainspage;
	TPPRenewDomainsCartPage tpprenewdomainscartpage;
	TPPRenewDomainsOrderCompletePage tpprenewdomainsordercompletepage;
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	RRPProxyLoginPage rrpproxyloginpage;
	RRPDomainsPage rrpdomainspage;
	RRPTabPage rrptabpage;
	RRPMyDomainsPage rrpmydomainspage;
	CADomainLevelPage cadomainlevelpage;

	//Variables
	String domainPrefix;
	String strusername;
	String strPassword;
	String expiryDateBeforeRenewal;
	String existingPaymentMethod;
	String renewedDomainName;
	String renewedWorkflowId;
	String workflowStatusAfterCompletionManually;
	String workflowStatusAfterCompletionBySchedular;
	String rrpStrUsername;
	String rrpStrPassword;
	String strDomainName;
	boolean flag = true;

	public Renew_Domain(){
		super();
	}
	
	@Parameters({ "environment", "namespace", "accountReference" })
	@BeforeTest
	public void generateARenewalWorkflowFromConsoleAdmin(String environment, String namespace, String accountReference) throws InterruptedException{
				
		initialization(environment, "consoleadmin");
		
		if(namespace.equalsIgnoreCase("com")){
		strDomainName = "m4dev-201-test4";
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		cadomainlevelpage = caheaderpage.searchDomain(strDomainName+"."+namespace);
		cadomainlevelpage.clickGenerateRenewalWorkflow();
		driver.quit();
		}
		else{
			driver.quit();
		}
	}
	
	@Parameters({ "environment", "namespace", "accountReference" })
	@Test
	public void renewADomainFromResellerPortal(String environment, String namespace, String accountReference) throws InterruptedException{

		initialization(environment, "resellerportalurl_tpp");

		strPassword = "comein22";
		strDomainName = "m4dev-201-test4";
		//For ORG and INFO use domain: test-theconsole-96-test; for com use domain: m4dev-201-test4 
		existingPaymentMethod = "Prepaid credit: Current Balance:";

		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal");
		System.out.println("Start test: renewADomainFromResellerPortal");
		tpploginpage = new TPPLoginPage();
		tpploginpage.setLoginDetails(accountReference, strPassword);
		tpptabpage = tpploginpage.clickLoginButton();

		// Test Step 2: Navigate to Renew Domain Page
		test.log(LogStatus.INFO, "Navigate to Domains then Domain Renew page and select a domain for renewal");
		tpptabpage.clickDomainsTab();
		tpprenewdomainspage = tpptabpage.clickDomainRenewLink();
		tpprenewdomainspage.searchDomainNameToBeRenewed(strDomainName+"."+namespace);
		tpprenewdomainspage.clickOnSearchButton();
		tpprenewdomainspage.selectDomainNameCheckbox();
		String domainNameSelectedForRenewal = tpprenewdomainspage.getDomainName();
		tpprenewdomainscartpage = tpprenewdomainspage.clickOnAddDomainsToList();

		//Test Step 3: Add domains to cart
		test.log(LogStatus.INFO, "Add Domains to cart");
		String domainNameToBeRenewed = tpprenewdomainscartpage.getDomainNameToBeRenewed();
		Assert.assertEquals(domainNameSelectedForRenewal, domainNameToBeRenewed, "Domain name is available for renewal");
		expiryDateBeforeRenewal = tpprenewdomainscartpage.getExpiryDateOfDomain();
		tpprenewdomainscartpage.checkTermsAndConditions();
		tpprenewdomainscartpage.selectPaymentMethod(existingPaymentMethod);
		tpprenewdomainsordercompletepage = tpprenewdomainscartpage.clickOnRenewDomain();

		//Test Step 4: Verify the namespace of domain then Complete a renewal of domain and copy workflow ID
		test.log(LogStatus.INFO, "Verify the namespace of domain then Complete a renewal of domain and copy workflow ID");
		if(namespace.equalsIgnoreCase("info") || namespace.equalsIgnoreCase("org")){
			String errorMessage = tpprenewdomainsordercompletepage.getErrorMessageFromOrderCompletePage();
			Assert.assertEquals(errorMessage, "domain "+strDomainName+"."+namespace+" is not currently eligible for renewal. reason[domain status = null]");
			flag = false;
		}else{
			renewedDomainName = tpprenewdomainsordercompletepage.getRenewedDomainName();
			renewedWorkflowId = tpprenewdomainsordercompletepage.getWorkflowIdOfRenewedDomain();
			Assert.assertEquals(renewedDomainName, domainNameToBeRenewed, "Domain name not renewed");
		}
		driver.quit();
	}

	@Parameters({ "environment", "namespace", "accountReference" })
	@Test
	public void executeRenewal2WorkflowFromConsoleAdmin(String environment, String namespace, String accountReference) throws InterruptedException{
		if(flag){
			
		initialization(environment, "consoleadmin");

		//Test Step 1: Login to console admin
		test.log(LogStatus.INFO, "Login to console admin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);

		//Test Step 2: Search a workflow ID and verify workflow status
		test.log(LogStatus.INFO, "Search a workflow ID in console admin and verify workflow status");
		caworkflowadminpage = caheaderpage.searchWorkflow(renewedWorkflowId);
		caworkflowadminpage.clickOnWorkflowId();
		String workflowType = caworkflowadminpage.verifyWorkflowType();
		Assert.assertEquals(workflowType, "renewal2", "renewal2 workflow is not created");

		//Test Step 3: Verify workflow status and perform the execution of workflow
		test.log(LogStatus.INFO, "verify the workflow status and perform the execution of workflow");
		switch(environment){
		case "dev8":
			workflowStatusAfterCompletionManually = caworkflowadminpage.processRenewal2Workflow();
			Assert.assertEquals(workflowStatusAfterCompletionManually, "renewed", "renewal workflow not completed successfully by staff");

		case "uat2":
			workflowStatusAfterCompletionBySchedular = caworkflowadminpage.getWorkflowStatus();
			Assert.assertEquals(workflowStatusAfterCompletionBySchedular, "renewed", "renewal workflow not completed successfully by schedular");

		case "prod":
			workflowStatusAfterCompletionBySchedular = caworkflowadminpage.getWorkflowStatus();
			Assert.assertEquals(workflowStatusAfterCompletionBySchedular, "renewed", "renewal workflow not completed successfully by schedular");
		}
		driver.quit();
		}
	}

	@Parameters({ "environment", "namespace" })
	@Test(dependsOnMethods = "executeRenewal2WorkflowFromConsoleAdmin")
	public void verifyDomainExpiryDateAfterRenewalInRrpPortal(String environment, String namespace) throws InterruptedException, IOException, AWTException {
		if(flag){
			
		initialization(environment, "rrpproxy_tpp");

		rrpStrUsername = "gulliver";
		rrpStrPassword = "MDYe#5z<W6dk9";

		//Test Step 1: Login to RRPProxy portal
		test.log(LogStatus.INFO, "Login to RRPproxy portal");
		rrpproxyloginpage = new RRPProxyLoginPage();
		rrpproxyloginpage.setLoginDetails(rrpStrUsername, rrpStrPassword);
		rrptabpage = rrpproxyloginpage.clickLoginButton();

		//Test Step 2: Navigate to My Domains page and search a domain which is renewed
		test.log(LogStatus.INFO, "Navigate to domains page and search a domain");
		rrptabpage.clickOnDomainsLink();
		rrpmydomainspage = rrptabpage.clickOnSubMenuDomainsLink();
		rrpmydomainspage.enterADomainInSearchField(renewedDomainName);
		rrpmydomainspage.clickOnSearchButton();

		//Test Step 3: Verify the domain name and expiry date
		test.log(LogStatus.INFO, "Verify domain name and expiry date of domain");
		String renewedDomainNameInRrpPortal = rrpmydomainspage.getRenewedDomainName();
		Assert.assertEquals(renewedDomainNameInRrpPortal, renewedDomainName, "Domain name is verified successfully");
		String updatedExpiryDateOfDomainInRrpPortal = rrpmydomainspage.getExpiryDateOfRenewedDomain();
		Assert.assertNotEquals(updatedExpiryDateOfDomainInRrpPortal, expiryDateBeforeRenewal, "Domain expiry date has not updated successfully");
		driver.quit();
		}
	}

	@Parameters({ "environment", "namespace", "accountReference" })
	@Test
	public void renewADomainFromResellerPortalWhichIsAlreadyRenewed(String environment, String namespace, String accountReference) throws InterruptedException{
		if(flag){
		initialization(environment, "resellerportalurl_tpp");

		strPassword = "comein22";
		strDomainName = "m4dev-201-test4";
		existingPaymentMethod = "Prepaid credit: Current Balance:";

		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal");
		System.out.println("Start test: renewADomainFromResellerPortal");
		tpploginpage = new TPPLoginPage();
		tpploginpage.setLoginDetails(accountReference, strPassword);
		tpptabpage = tpploginpage.clickLoginButton();

		// Test Step 2: Navigate to Renew Domain Page
		test.log(LogStatus.INFO, "Navigate to Domains then Domain Renew page and select a domain for renewal");
		tpptabpage.clickDomainsTab();
		tpprenewdomainspage = tpptabpage.clickDomainRenewLink();
		tpprenewdomainspage.searchDomainNameToBeRenewed(strDomainName+"."+namespace);
		tpprenewdomainspage.clickOnSearchButton();
		tpprenewdomainspage.selectDomainNameCheckbox();
		String domainNameSelectedForRenewal = tpprenewdomainspage.getDomainName();
		tpprenewdomainscartpage = tpprenewdomainspage.clickOnAddDomainsToList();

		//Test Step 3: Add domains to cart
		test.log(LogStatus.INFO, "Add Domains to cart");
		String domainNameToBeRenewed = tpprenewdomainscartpage.getDomainNameToBeRenewed();
		Assert.assertEquals(domainNameSelectedForRenewal, domainNameToBeRenewed, "Domain name is available for renewal");
		expiryDateBeforeRenewal = tpprenewdomainscartpage.getExpiryDateOfDomain();
		tpprenewdomainscartpage.checkTermsAndConditions();
		tpprenewdomainscartpage.selectPaymentMethod(existingPaymentMethod);
		tpprenewdomainsordercompletepage = tpprenewdomainscartpage.clickOnRenewDomain();

		//Test Step 4: Try to renew a domain which is already renewed and verify error message
		test.log(LogStatus.INFO, "Try to renew a domain which is already renewed and verify error message");
		String errorMessage = tpprenewdomainsordercompletepage.getErrorMessageFromOrderCompletePage();
		Assert.assertEquals(errorMessage, "domain "+strDomainName+"."+namespace+" is not currently eligible for renewal. reason[domain status = null]");
		driver.quit();
		}
	}
}
