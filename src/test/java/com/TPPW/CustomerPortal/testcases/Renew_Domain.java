package com.TPPW.CustomerPortal.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.commonfunctionality.TPPW_CommonFunctionality;
import com.consoleadmin.pages.CADomainLevelPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.relevantcodes.extentreports.LogStatus;
import com.rrpproxypage.java.RRPDomainsPage;
import com.rrpproxypage.java.RRPMyDomainsPage;
import com.rrpproxypage.java.RRPProxyLoginPage;
import com.rrpproxypage.java.RRPTabPage;
import com.tppcustomerportal.pages.TPPAccountContactPage;
import com.tppcustomerportal.pages.TPPAddDomainPrivacyPage;
import com.tppcustomerportal.pages.TPPBillingPage;
import com.tppcustomerportal.pages.TPPDomainSearchPage;
import com.tppcustomerportal.pages.TPPHeaderPage;
import com.tppcustomerportal.pages.TPPHostingAndExtrasPage;
import com.tppcustomerportal.pages.TPPLoginPage;
import com.tppcustomerportal.pages.TPPOrderCompletePage;
import com.tppcustomerportal.pages.TPPOrderPage;
import com.tppcustomerportal.pages.TPPRegistrantContactPage;
import com.tppcustomerportal.pages.TPPRenewDomainPage;
import com.tppcustomerportal.pages.TPPSummaryOfAllDomainsPage;

public class Renew_Domain extends TPPW_CommonFunctionality {

	// Console pages
	TPPLoginPage tppLoginPage;
	TPPDomainSearchPage tppDomainSearchPage;
	TPPHeaderPage tppHeaderPage;
	TPPSummaryOfAllDomainsPage tppSummaryOfAllDomainsPage;
	TPPOrderPage tppOrderPage;
	TPPAddDomainPrivacyPage tppadddomainprivacypage;
	TPPHostingAndExtrasPage tpphostingandextraspage;
	TPPRenewDomainPage tppRenewDomainPage;
	TPPAccountContactPage tppaccountcontactpage;
	TPPRegistrantContactPage tppregistrantcontactpage;
	TPPBillingPage tppbillingpage;
	TPPOrderCompletePage tppordercompletepage;

	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	RRPProxyLoginPage rrpproxyloginpage;
	RRPDomainsPage rrpdomainspage;
	RRPTabPage rrptabpage;
	RRPMyDomainsPage rrpmydomainspage;
	CADomainLevelPage cadomainlevelpage;

	// Variables
	String expiryDateBeforeRenewal;
	String existingPaymentMethod;
	String renewedDomainName;
	String rrpStrUsername;
	String rrpStrPassword;
	String strDomainName;
	String strWorkflowId;
	String renewedWorkflowId;
	String strWorkflowStatus;
	Date expirtaionDateBeforeRenewal;
	Date renewedExpirtaionDate;

	boolean flag = true;

	public Renew_Domain() {
		super();
	}

	@Parameters({ "environment", "namespace", "accountReference" })
	@Test
	public void registerADomainInCustomerPortal(String environment, String namespace, String accountReference)
			throws Exception {

		String strExistingCard="Number: 4111********1111 Expiry: 08/2021";
		String accountrefrence="ARQ-45";
		String password="ARQ-45";

		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);

		// Test Step 1: Login to customer portal
		test.log(LogStatus.INFO, "Login to Customer portal");
		System.out.println("Start Test: registerADomainInCustomerPortal");
		initialization(environment, "customerportalurl_tpp");
		tppLoginPage = new TPPLoginPage();
		tppLoginPage.setLoginDetails(accountrefrence, password);
		tppHeaderPage = tppLoginPage.clickLoginButton();
		tppOrderPage = tppHeaderPage.clickOrderTab();

		// Test Step 2: Navigate to order page to register domain
		test.log(LogStatus.INFO, "Navigate to Domains then search ans register a domain");
		tppOrderPage.setDomainNameAndTld(strDomainName, "." + namespace);
		tppDomainSearchPage = tppOrderPage.clickNewDomainSearchButton();
		tppadddomainprivacypage = tppDomainSearchPage.clickContinueToCheckoutWithDomainPrivacy();
		tpphostingandextraspage = tppadddomainprivacypage.clickNoThanks();
		tppregistrantcontactpage = tpphostingandextraspage.clickContinueButtonWithoutAccountContact();
		tppbillingpage = tppregistrantcontactpage.clickContinueButton();

		// Test Step 3: Select existing credit card details and submit the order
		selectExistingCard(strExistingCard);

		// Test Step 4: Verify if order is completed and get the Order Reference ID
		fetchWorkflowId(); 
		driver.close();

		// Test Step 5: Process the domain registration order in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(TPPW_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.processDomainRegistration2Workflow(TPPW_CommonFunctionality.strWorkflowId, namespace);

		// Test Step 6: Verify if domain registration workflow is completed
		test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + "." + namespace);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed")
				|| strWorkflowStatus.equalsIgnoreCase("update star rating"));

		driver.quit();
		System.out.println("End Test: registerADomainInCustomerPortal");

	}

	@Parameters({ "environment", "namespace", "accountReference" })
	@Test(dependsOnMethods = { "registerADomainInCustomerPortal" })
	public void renewADomainInCustomerPortal(String environment, String namespace, String accountReference)
			throws Exception {

		String strExistingCard="Number: 4111********1111 Expiry: 08/2021";
		String accountrefrence="ARQ-45";
		String password="ARQ-45";
		
		// Test Step 1: Login to customer portal
		test.log(LogStatus.INFO, "Login to Customer portal");
		System.out.println("Start Test: renewADomainInCustomerPortal");
		initialization(environment, "customerportalurl_tpp");
		tppLoginPage = new TPPLoginPage();
		tppLoginPage.setLoginDetails(accountrefrence, password);
		tppHeaderPage = tppLoginPage.clickLoginButton();

		// Test Step 2: Navigate to all Domains then renew the domain
		test.log(LogStatus.INFO, "Navigate to all Domains then renew the domain");
		tppSummaryOfAllDomainsPage = tppHeaderPage.clickAllDomainsLink();
		expirtaionDateBeforeRenewal = tppSummaryOfAllDomainsPage.getCurrentExpirationDate(strDomainName + "." + namespace);
		tppSummaryOfAllDomainsPage.tickDomainNameCheckbox(strDomainName + "." + namespace);
		tppRenewDomainPage = tppSummaryOfAllDomainsPage.clickRenewSelectedButton();

		// Test Step 3: Select existing credit card details and submit the order
		selectExistingCard(strExistingCard);

		// Test Step 4: Get order ID
		test.log(LogStatus.INFO, "Verify if order is completed and get the order ID if it is");
		Assert.assertTrue(tppRenewDomainPage.isOrderComplete("Renewal job has successsfully been lodged"),
				"Order is not completed");
		renewedWorkflowId = tppRenewDomainPage.getOrderID();
		System.out.println("Reference ID:" + renewedWorkflowId);
		driver.quit();

		// Test Step 5: Process the domain renewal workflow in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(renewedWorkflowId);
		caworkflowadminpage.clickOnWorkflowId();
		caworkflowadminpage.processRenewal2Workflow();

		// Test Step 6: Verify if domain renewal workflow is completed
		test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
		caworkflowadminpage = caheaderpage.searchWorkflow(renewedWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("renewal2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("renewed"));
		driver.quit();
		System.out.println("End Test: renewADomainInCustomerPortal");

	}

	@Parameters({ "environment", "namespace", "accountReference" })
	@Test(dependsOnMethods = { "renewADomainInCustomerPortal" })
	public void checkExpirationDateOfRenewedDomain(String environment, String namespace, String accountReference)
			throws Exception {
		
		String accountrefrence="ARQ-45";
		String password="ARQ-45";
		
		// Test Step 1: Login to customer portal
		test.log(LogStatus.INFO, "Login to Customer portal");
		System.out.println("Start Test: checkExpirationDateOfRenewedDomain");
		initialization(environment, "customerportalurl_tpp");
		tppLoginPage = new TPPLoginPage();
		tppLoginPage.setLoginDetails(accountrefrence, password);
		tppHeaderPage = tppLoginPage.clickLoginButton();

		// Test Step 2: Navigate to all Domains then renew the domain
		test.log(LogStatus.INFO, "Navigate to all Domains then get the domain's new expiry date");
		tppSummaryOfAllDomainsPage = tppHeaderPage.clickAllDomainsLink();
		renewedExpirtaionDate = tppSummaryOfAllDomainsPage.getCurrentExpirationDate(strDomainName + "." + namespace);
		
		// Test Step 3: Verify that domain's new expiry date is 365 days more than the previous one
		Assert.assertEquals(tppSummaryOfAllDomainsPage.getDiffDays(renewedExpirtaionDate, expirtaionDateBeforeRenewal), 365);
		driver.quit();
		System.out.println("End Test: checkExpirationDateOfRenewedDomain");

	}

}
