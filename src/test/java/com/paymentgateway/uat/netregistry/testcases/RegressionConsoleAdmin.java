package com.paymentgateway.uat.netregistry.testcases;

import java.awt.AWTException;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.consoleadmin.pages.CAAccountReferencePage;
import com.consoleadmin.pages.CADomainLevelPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CAInvoicesPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAPrepaidCreidtPage;
import com.consoleadmin.pages.CAViewCreditCardsPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class RegressionConsoleAdmin extends TestBase {

	// Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	CAAccountReferencePage caaccountreferencepage;
	CADomainLevelPage cadomainlevelpage;
	CAViewCreditCardsPage caviewcreditcardspage;
	CAInvoicesPage cainvoicespage;
	CAPrepaidCreidtPage caprepaidcreditpage;

	TestUtil testUtil;
	public static ExtentTest logger;

	public String strVirtualization = "Netregistry";
	public String strAccountReference = null;
	public String strExpiryMonth = null;
	public String strExpiryYear = null;

	public RegressionConsoleAdmin() {
		super();
	}

	@Parameters({ "environment", "paymentgateway" })
	@Test
	public void testViewBillingInConsoleAdmin(String environment, String paymentgateway)
			throws InterruptedException, IOException, AWTException {

		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			strAccountReference = "MEL-6007";
		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			strAccountReference = "NET-1218";
		}

		// Test Step 1: Login to console admin, navigate to account reference page then
		// click view billing accounts
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caviewcreditcardspage = caaccountreferencepage.clickViewBillingAccounts();

		// Test Step 2: Verify if user can view billing in console admin
		// TestUtil.takeScreenshotAtEndOfTest(paymentgateway + strVirtualization +
		// "PGTest07");
		Assert.assertTrue(caviewcreditcardspage.isViewCreditcardsPageDisplayed(),
				"View Creditcards Page is not displayed");
	}

	@Parameters({ "environment", "paymentgateway" })
	@Test
	public void testUpdateExpiryInConsoleAdmin(String environment, String paymentgateway)
			throws InterruptedException, IOException {

		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			strExpiryMonth = "01";
			strExpiryYear = "22";
		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			strExpiryMonth = "01";
			strExpiryYear = "24";
		}

		// Test Step 1: Update expiry date and verify if a successful confirmation
		// message will be shown.
		caviewcreditcardspage.updateExpiryDate(strExpiryMonth, strExpiryYear);

		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			Assert.assertEquals(
					caviewcreditcardspage.getUpdateExpiryConfirmation(), "Credit card: Visa: 4111xxxxxxxx1111 "
							+ strExpiryMonth + "/" + strExpiryYear + " expiry date updated.",
					"Updated Expiry Sucessfully");
		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			Assert.assertEquals(
					caviewcreditcardspage.getUpdateExpiryConfirmation(), "Credit card: MasterCard: 545454******5454 "
							+ strExpiryMonth + "/20" + strExpiryYear + " expiry date updated.",
					"Updated Expiry Sucessfully");
		}

		// TestUtil.takeScreenshotAtEndOfTest(paymentgateway + strVirtualization +
		// "PGTest08");
		driver.close();
	}

	@Parameters({ "environment", "paymentgateway" })
	@Test
	public void testRechargePrepaidInConsoleAdminUsingExistingCard(String environment, String paymentgateway)
			throws InterruptedException, AWTException {

		String strAccountReference = "MEL-6007";
		initialization(environment, "consoleadmin");
		// Test Step 1: Login to Console Admin and search for Account reference
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caheaderpage.searchAccountReference(strAccountReference);

		// Test Step 2: Navigate to View Invoice and Prepaid > Prepaid Account Details
		cainvoicespage = caheaderpage.clickViewInvoiceAndPrepaidDetail();
		caprepaidcreditpage = cainvoicespage.clickPrepaidAccountDetails();

		// Test Step 3: Select existing credit card and purchase a credit
		caprepaidcreditpage.selectExistingCreditCard();
		caprepaidcreditpage.enterAmount("20");
		caprepaidcreditpage.clickPurchaseCredit();
		caprepaidcreditpage.confirmPurchase();

		// Test Step 3: Verify successful purchased
		Thread.sleep(20);
		Assert.assertEquals(caprepaidcreditpage.getSuccessPurchasedMessage(), "Credit purchased successfully");
		driver.quit();
	}

	@Parameters({ "environment", "paymentgateway" })
	@Test
	public void testRechargePrepaidInConsoleAdminUsingNewCard(String environment, String paymentgateway)
			throws InterruptedException, AWTException {
		String straccountreference = "MEL-6007";
		initialization(environment, "consoleadmin");
		// Test Step 1: Login to Console Admin and search for Account reference
		caloginpage = new CALoginPage();
		caviewcreditcardspage = new CAViewCreditCardsPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caheaderpage = new CAHeaderPage();
		caheaderpage.searchAccountReference(straccountreference);
		cainvoicespage = new CAInvoicesPage();

		// Test Step 2: Navigate to View Invoice and Prepaid > Prepaid Account Details
		cainvoicespage = caheaderpage.clickViewInvoiceAndPrepaidDetail();
		caprepaidcreditpage = cainvoicespage.clickPrepaidAccountDetails();

		// Test Step 3: Select new credit card and purchase a credit
		caprepaidcreditpage.setCreditCardDetails("John Doe", "Visa", "4111 1111 1111 1111", "03", "23");
		caprepaidcreditpage.enterAmount("20");
		caprepaidcreditpage.clickPurchaseCredit();
		caprepaidcreditpage.confirmPurchase();

		// Test Step 3: Verify successful purchased
		Assert.assertEquals(caprepaidcreditpage.getSuccessPurchasedMessage(), "Credit purchased successfully");
		driver.quit();

	}

	@Parameters({ "environment", "paymentgateway" })
	@Test
	public void testViewBillingUpdateExpiryInConsoleAdmin(String environment, String paymentgateway)
			throws InterruptedException, IOException, AWTException {

		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			strAccountReference = "MEL-6007";
		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			strAccountReference = "NET-1218";
		}

		// Test Step 1: Login to console admin, navigate to account reference page then click view billing accounts
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caviewcreditcardspage = caaccountreferencepage.clickViewBillingAccounts();

		// Test Step 2: Verify if user can view billing in console admin
		Assert.assertTrue(caviewcreditcardspage.isViewCreditcardsPageDisplayed(),
				"View Creditcards Page is not displayed");

		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			strExpiryMonth = "01";
			strExpiryYear = "22";
		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			strExpiryMonth = "01";
			strExpiryYear = "24";
		}
		// Test Step 3: Update expiry date and verify if a successful confirmation
		caviewcreditcardspage.updateExpiryDate(strExpiryMonth, strExpiryYear);

		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			Assert.assertEquals(
					caviewcreditcardspage.getUpdateExpiryConfirmation(), "Credit card: Visa: 4111xxxxxxxx1111 "
							+ strExpiryMonth + "/" + strExpiryYear + " expiry date updated.",
					"Updated Expiry Sucessfully");
		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			Assert.assertEquals(
					caviewcreditcardspage.getUpdateExpiryConfirmation(), "Credit card: MasterCard: 545454******5454 "
							+ strExpiryMonth + "/20" + strExpiryYear + " expiry date updated.",
					"Updated Expiry Sucessfully");
			driver.quit();
		}
	}

	@Parameters({ "environment" })
	@Test
	public void testViewBillingAccountDetailsInConsoleAdmin(String environment)
			throws InterruptedException, IOException, AWTException {

		strAccountReference="ARQ-45";
		// Test Step 1: Login to console admin, navigate to account reference page then click view billing accounts
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caviewcreditcardspage = caaccountreferencepage.clickViewBillingAccounts();

		// Test Step 2: Verify if user can view billing in console admin
		Assert.assertTrue(caviewcreditcardspage.isViewCreditcardsPageDisplayed(),"View Creditcards Page is not displayed");
		driver.quit();
	}

}
