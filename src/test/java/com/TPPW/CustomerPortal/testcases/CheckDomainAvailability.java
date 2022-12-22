package com.TPPW.CustomerPortal.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.tppcustomerportal.pages.TPPDomainSearchPage;
import com.tppcustomerportal.pages.TPPHeaderPage;
import com.tppcustomerportal.pages.TPPLoginPage;
import com.tppcustomerportal.pages.TPPOrderPage;
import com.util.TestUtil;

public class CheckDomainAvailability extends TestBase {

	TPPLoginPage tppLoginPage;
	TPPDomainSearchPage tppDomainSearchPage;
	TPPHeaderPage tppHeaderPage;
	TPPOrderPage tppOrderPage;

	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public CheckDomainAvailability() {
		super();
	}

	@Parameters({ "environment", "namespace", "accountReference" })
	@Test

	public void verifyDomainNameAvailableForRegistrationNotPremium(String environment, String namespace,
			String accountReference) throws Exception {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;

		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);

		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Customer portal");
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
		initialization(environment, "customerportalurl_tpp");
		tppLoginPage = new TPPLoginPage();
		tppLoginPage.setLoginDetails(accountReference, "comein22");
		tppHeaderPage = new TPPHeaderPage();
		tppDomainSearchPage = new TPPDomainSearchPage();
		tppHeaderPage = tppLoginPage.clickLoginButton();
		tppOrderPage = new TPPOrderPage();
		tppDomainSearchPage = new TPPDomainSearchPage();
		tppOrderPage = tppHeaderPage.clickOrderTab();

		// Test Step 2: Navigate to order page to register domain
		test.log(LogStatus.INFO, "Navigate to Domains then Register and search for a domain");

		tppOrderPage.setDomainNameAndTld(strDomainName, "." + namespace);
		tppDomainSearchPage = tppOrderPage.clickNewDomainSearchButton();

		// Test Step 3: Verify search result message
		test.log(LogStatus.INFO, "Verify search result message");
		Assert.assertTrue(tppDomainSearchPage.checkStatus(), "Available ");
		System.out.println("End Test: verifyDomainNameAvailableForRegistrationNotPremium");
		driver.quit();

	}

	@Parameters({ "environment", "namespace", "accountReference" })
	@Test

	public void verifyDomainNameNotAvailableForRegistration(String environment, String namespace,
			String accountReference) throws Exception {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		strDomainName = "testdomainreg23213";

		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Customer portal");
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
		initialization(environment, "customerportalurl_tpp");
		tppLoginPage = new TPPLoginPage();
		tppLoginPage.setLoginDetails(accountReference, "comein22");
		tppHeaderPage = new TPPHeaderPage();
		tppDomainSearchPage = new TPPDomainSearchPage();
		tppHeaderPage = tppLoginPage.clickLoginButton();
		tppOrderPage = new TPPOrderPage();
		tppDomainSearchPage = new TPPDomainSearchPage();
		tppOrderPage = tppHeaderPage.clickOrderTab();

		// Test Step 2: Navigate to order page to register domain
		test.log(LogStatus.INFO, "Navigate to Domains then Register and search for a domain");

		tppOrderPage.setDomainNameAndTld(strDomainName, "." + namespace);
		tppDomainSearchPage = tppOrderPage.clickNewDomainSearchButton();

		// Test Step 3: Verify search result message
		test.log(LogStatus.INFO, "Verify search result message");
		Assert.assertTrue(tppDomainSearchPage.checkStatus(), "Unavailable ");
		System.out.println("End Test: verifyDomainNameNotAvailableForRegistration");
		driver.quit();

	}

	@Parameters({ "environment", "namespace", "accountReference" })
	@Test

	public void verifyDomainNameAvailableForRegistrationAndPremiumn(String environment, String namespace,
			String accountReference) throws Exception {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		strDomainName = "lol";
		strTld = "sydney";

		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Customer portal");
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
		initialization(environment, "customerportalurl_tpp");
		tppLoginPage = new TPPLoginPage();
		tppLoginPage.setLoginDetails(accountReference, "comein22");
		tppHeaderPage = new TPPHeaderPage();
		tppDomainSearchPage = new TPPDomainSearchPage();
		tppHeaderPage = tppLoginPage.clickLoginButton();
		tppOrderPage = new TPPOrderPage();
		tppDomainSearchPage = new TPPDomainSearchPage();
		tppOrderPage = tppHeaderPage.clickOrderTab();

		// Test Step 2: Navigate to order page to register domain
		test.log(LogStatus.INFO, "Navigate to Domains then Register and search for a domain");

		tppOrderPage.setDomainNameAndTld(strDomainName, "." + strTld);
		tppDomainSearchPage = tppOrderPage.clickNewDomainSearchButton();

		// Test Step 3: Verify search result message
		test.log(LogStatus.INFO, "Verify search result message");
		Assert.assertTrue(tppDomainSearchPage.checkStatus(), "Premium Name - Contact us to secure ");
		System.out.println("End Test: verifyDomainNameAvailableForRegistrationAndPremiumn");
		driver.quit();

	}

}
