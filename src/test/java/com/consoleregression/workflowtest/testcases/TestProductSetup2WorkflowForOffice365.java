package com.consoleregression.workflowtest.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.netregistryoldwebsite.pages.NRGAccountContactPage;
import com.netregistryoldwebsite.pages.NRGAddDomainPrivacyPage;
import com.netregistryoldwebsite.pages.NRGAddExtrasPage;
import com.netregistryoldwebsite.pages.NRGAddHostingPage;
import com.netregistryoldwebsite.pages.NRGBillingPage;
import com.netregistryoldwebsite.pages.NRGDomainSearchPage;
import com.netregistryoldwebsite.pages.NRGHostingAndExtrasPage;
import com.netregistryoldwebsite.pages.NRGOnlineOrderAccountDetailsPage;
import com.netregistryoldwebsite.pages.NRGOnlineOrderPage;
import com.netregistryoldwebsite.pages.NRGOrderCompletePage;
import com.netregistryoldwebsite.pages.NRGRegistrantContactPage;
import com.netregistryoldwebsite.pages.NRGWebHostingPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class TestProductSetup2WorkflowForOffice365 extends TestBase {

	NRGOnlineOrderPage nrgonlineorderpage;
	NRGDomainSearchPage nrgdomainsearchpage;
	NRGAddDomainPrivacyPage nrgadddomainprivacypage;
	NRGHostingAndExtrasPage nrghostingandextraspage;
	NRGWebHostingPage nrgwebhostingpage;
	NRGAddHostingPage nrgaddhostingpage;
	NRGAddExtrasPage nrgaddextraspage;
	NRGAccountContactPage nrgaccountcontactpage;
	NRGRegistrantContactPage nrgregistrantcontactpage;
	NRGBillingPage nrgbillingpage;
	NRGOrderCompletePage nrgordercompletepage;
	NRGOnlineOrderAccountDetailsPage nrgonlineorderaccountdetailspage;

	// Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;

	TestUtil testUtil;
	public static ExtentTest logger;

	public TestProductSetup2WorkflowForOffice365() {
		super();
	}

	@Parameters({ "environment", "virtualization" })
	@Test
	public void testProductSetup2WorkflowForOffice365(String environment, String virtualization)
			throws InterruptedException {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strAccountReference = null;
		String strPassword = null;
		String strWorkflowId = null;
		String strPeriod = null;
		String strTypeOffice365 = null;
		String hostname = null;

		DateFormat df = new SimpleDateFormat("ddMMYYYY-hhmmss");
		Date d = new Date();
		strDomainName = "O365test" + df.format(d);

		if ((environment.equals("uat1")) && (virtualization.equals("netregistry"))) {
			strAccountReference = "TES-2168";
			strPassword = "comein22";
			strTld = "com";
			strPeriod = "1 Month";
			strTypeOffice365 = "o365Business";
		}

		// Test Step 1: Login to customer portal and place a domain registration order
		// for .com tld
		System.out.println("Start Test: testProductSetup2WorkflowForOffice365");
		initialization(environment, "customerportalurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.clearDefaultTldSelections();
		nrgonlineorderpage.setDomainNameAndTld(strDomainName, "." + strTld);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage = nrgadddomainprivacypage.clickNoThanks();

		// Test Step 2: Purchase an office 365 product. "selectOffice365" method only
		// accepts the following Office365 types:
		// o365EmailEssentials, o365BusinessEssentials, o365BusinessPremium or
		// o365Business products
		nrghostingandextraspage.clickEmailHostingRadioButton();
		nrghostingandextraspage.selectOffice365(strTypeOffice365, strPeriod);
		nrgaccountcontactpage = nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setReturningCustomerContacts(strAccountReference, strPassword);
		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
		nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();

		// Test Step 3: Select existing credit card details and submit the order
		nrgbillingpage.selectExistingCreditCardOption("Number: 5555********4444 Expiry: 03/2021");
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickContinueButton();

		// Test Step 4: Verify if order is completed
		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = nrgordercompletepage.getSingleReferenceID();
		strAccountReference = nrgordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);
		System.out.println("Reference ID[0]:" + strWorkflowId);
		driver.close();

		// Test Step 5: Process the domain registration order in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, strTld);

		// Test Step 6: Verify if domain registration workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + "." + strTld);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"),
				"domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));

		// Test Step 7: Process the productsetup2 workflow in console admin
		caworkflowadminpage.processProductSetup2();

		// Test Step 8: Verify if productsetup2 workflow is approved
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + "." + strTld);

		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "approved",
				caworkflowadminpage.getWorkflowStatus("productsetup2"));

		if ((environment.equals("uat1")) && (virtualization.equals("netregistry"))) {
			// Test Step 9: Verify if productSetupOffice365 workflow will fail in verify
			// domain step
			caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + "." + strTld);
			Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetupOffice365"), "Verify domain failed",
					caworkflowadminpage.getWorkflowStatus("o365Provisioning"));

			// Test Step 10: Retrieve TXT record via API call

			/*
			 * hostname = "https://cdn.stage.provisioning-api.melbourneit.com.au"; response
			 * = RestAssured.given().auth().preemptive().basic("test",
			 * "testpassword").when() .get(hostname + "/v1/pm/o365/domain/" + strDomainName
			 * + "." + strTld + "/verificationRecord");
			 * System.out.println("Status message: " + response.body().asString());
			 */
		}

		driver.quit();
		System.out.println("End Test: testProductSetup2WorkflowForOffice365");

	}

}
