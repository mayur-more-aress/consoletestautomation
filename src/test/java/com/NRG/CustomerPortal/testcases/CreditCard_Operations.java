package com.NRG.CustomerPortal.testcases;

import java.awt.AWTException;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.NRG.OldShoppingCart.testcases.DomainAndProduct_Order;
import com.commonfunctionality.NRGOld_CommonFunctionality;
import com.consoleadmin.pages.CAAccountReferencePage;
import com.consoleadmin.pages.CADomainLevelPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consolesmui.pages.CSMUITabPage;
import com.netregistryoldwebsite.pages.NRGAccountPage;
import com.netregistryoldwebsite.pages.NRGBillingPage;
import com.netregistryoldwebsite.pages.NRGCreditCardsDetailsPage;
import com.netregistryoldwebsite.pages.NRGHeaderPage;
import com.netregistryoldwebsite.pages.NRGLoginPage;
import com.netregistryoldwebsite.pages.NRGOnlineOrderPage;
import com.netregistryoldwebsite.pages.NRGPrepaidAccountPage;
import com.nrgcustomerportal.pages.NRGAllServicesPage;
import com.nrgcustomerportal.pages.NRGManageDomainsPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class CreditCard_Operations extends NRGOld_CommonFunctionality {

	// Netregistry shopping cart pages
	NRGLoginPage nrgloginpage;
	NRGAccountPage nrgaccountpage;
	NRGBillingPage nrgbillingpage;
	NRGCreditCardsDetailsPage nrgcreditcardsdetailspage;
	NRGHeaderPage nrgheaderpage;
	NRGPrepaidAccountPage nrgprepaidaccountpage;
	NRGAllServicesPage nrgallservicespage;
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	NRGManageDomainsPage nrgmanagedomainpage;
	CAAccountReferencePage caaccountrefrencepage;
	CADomainLevelPage cadomainlevelpage;
	CSMUITabPage csmuitabpage;
	DomainAndProduct_Order domainandproductorder;
	NRGOnlineOrderPage nrgonlineorderpage;
	
	TestUtil testUtil;
	public static ExtentTest logger;

	String strAccountReference = null;
	String strPassword = null;
	String strCardOwner = null;
	String strCardType = null;
	String strCardNumber = null;
	String strCardExpiryMonth = null;
	String strCardExpiryYear = null;
	String strCardSecurityCode = null;
	String strAmount = null;

	public CreditCard_Operations() {
		super();
	}
	
	@Parameters({"environment"})
	@Test
	public void testAddNewCreditCardInSMUI(String environment) throws InterruptedException {

		strAccountReference = "ARQ-45";
		strPassword = "comein22";
		strCardOwner = "Test Mastercard";
		strCardType = "MasterCard";
		strCardNumber = "5555555555554444";
		strCardExpiryMonth = "05";
		strCardExpiryYear = "2025";
		strCardSecurityCode = "123";
		
		initialization(environment, "customerportalurl_netregistry");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		nrgheaderpage=new NRGHeaderPage();
		nrgaccountpage = nrgheaderpage.clickAccountTab();
		nrgcreditcardsdetailspage = nrgaccountpage.clickEditCreditCardsOnFile();

		// Test Step 2: Add new credit card
		nrgcreditcardsdetailspage.setBTFormCreditCardDetails(strCardOwner, strCardNumber, strCardExpiryMonth,
				strCardExpiryYear, strCardSecurityCode);
		nrgbillingpage = nrgcreditcardsdetailspage.clickAddCreditCard();
		Assert.assertTrue(nrgbillingpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
		driver.close();
	}

	
	@Parameters({"environment"})
	@Test
	public void testModifyExistingCreditCardInSMUI(String environment) throws InterruptedException {

		strAccountReference = "ARQ-45";
		strPassword = "comein22";
		strCardOwner = "Test Mastercard";
		strCardExpiryMonth = "05";
		strCardExpiryYear = "2026";

		initialization(environment, "customerportalurl_netregistry");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		nrgheaderpage=new NRGHeaderPage();
		nrgaccountpage = nrgheaderpage.clickAccountTab();
		nrgcreditcardsdetailspage = nrgaccountpage.clickEditCreditCardsOnFile();

		// Test Step 2: Modify existing card details
		nrgcreditcardsdetailspage.clickOnExistingCard(strCardOwner);
		nrgcreditcardsdetailspage.modifyCreditCardDetailsBT(strCardExpiryMonth, strCardExpiryYear);
		Assert.assertEquals(nrgcreditcardsdetailspage.getConfirmationMessage(),"The credit card has successfully been modified", nrgcreditcardsdetailspage.getConfirmationMessage());
		driver.close();
	}

	
	@Parameters({ "environment"})
	@Test
	public void testMakeCreditCardDefaultInSMUI(String environment) throws InterruptedException {

		strAccountReference = "ARQ-45";
		strPassword = "comein22";
		strCardOwner = "Visa Test";
		initialization(environment, "customerportalurl_netregistry");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		nrgheaderpage=new NRGHeaderPage();
		nrgaccountpage = nrgheaderpage.clickAccountTab();
		nrgcreditcardsdetailspage = nrgaccountpage.clickEditCreditCardsOnFile();

		// Test Step 2: To make this card default
		nrgaccountpage.makeCardDefault(strCardOwner);
		nrgaccountpage.makeCardDefault("ORIGINAL DEFAULT CARD");
		driver.close();
	}
	@Parameters({ "environment"})
	@Test
	public void testMakeCreditCarddefaultInSMUI(String environment) throws InterruptedException {

		strAccountReference = "ARQ-45";
		strPassword = "comein22";
		strCardOwner = "Visa Test";
		initialization(environment, "customerportalurl_netregistry");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		nrgheaderpage=new NRGHeaderPage();
		nrgaccountpage = nrgheaderpage.clickAccountTab();
		nrgcreditcardsdetailspage = nrgaccountpage.clickEditCreditCardsOnFile();

		// Test Step 2: To make this card default
		//nrgaccountpage.makeCardDefault(strCardOwner);
		//nrgaccountpage.makeCardDefault("ORIGINAL DEFAULT CARD");
		driver.close();
	}

	@Parameters({ "environment" })
	@Test
	public void testDeleteCreditCardInSMUI(String environment) throws InterruptedException {
		
		String straccountreference = "ARQ-45";
		String strpassword = "comein22";
		initialization(environment, "customerportalurl_netregistry");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(straccountreference,strpassword);
		nrgheaderpage=new NRGHeaderPage();
		nrgaccountpage = nrgheaderpage.clickAccountTab();
		nrgcreditcardsdetailspage = nrgaccountpage.clickEditCreditCardsOnFile();
		
		// Test Step 2: Delete the credit card
		nrgcreditcardsdetailspage.deleteCreditCard();
		driver.close();

	}

	@Parameters({ "environment"})
	@Test
	public void testRechargePrepaidUsingExistingCardInSMUI(String environment) throws InterruptedException {

		strAccountReference = "ARQ-45";
		strPassword = "comein22";
		strAmount = "10";
		initialization(environment, "customerportalurl_netregistry");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		nrgheaderpage=new NRGHeaderPage();
		nrgbillingpage = nrgheaderpage.clickBillingTab();
		nrgprepaidaccountpage = nrgbillingpage.clickEditPrepaidAccountLink();
		
		// Test Step 2:Recharge prepaid using the existing card
		nrgprepaidaccountpage.clickRechargeUsingCreditCard();
		nrgprepaidaccountpage.enterRechargeAmount(strAmount);
		nrgprepaidaccountpage.clickSubmitButton();
		driver.close();
	}

	@Parameters({ "environment" })
	@Test
	public void testRechargePrepaidUsingNewCardInSMUI(String environment)
			throws InterruptedException {

		strAccountReference = "ARQ-45";
		strPassword = "comein22";
		strCardOwner = "Test Mastercard";
		strCardType = "MasterCard";
		strCardNumber = "5454545454545454";
		strCardExpiryMonth = "02";
		strCardExpiryYear = "2022";
		strCardSecurityCode = "123";
		strAmount = "10";

		initialization(environment, "customerportalurl_netregistry");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		nrgheaderpage=new NRGHeaderPage();
		nrgbillingpage = nrgheaderpage.clickBillingTab();
		nrgprepaidaccountpage = nrgbillingpage.clickEditPrepaidAccountLink();
		nrgprepaidaccountpage.clickRechargeUsingCreditCard();
		
		// Test Step 2:Recharge prepaid using the new card
		nrgprepaidaccountpage.clickOnNewCreditCard();
		nrgprepaidaccountpage.setNewCreditCardDetailsBT(strCardOwner, strCardNumber, strCardExpiryMonth,
				strCardExpiryYear, strCardSecurityCode);
		nrgprepaidaccountpage.enterRechargeAmount(strAmount);
		nrgprepaidaccountpage.clickSubmitButton();
		driver.close();
	}
	
	@Parameters({ "environment"})
	@Test
	public void verifyUpdateSubscriptionInSMUI(String environment)
			throws InterruptedException {

		strAccountReference = "ARQ-45";
		strPassword = "comein22";
	
		initialization(environment, "customerportalurl_netregistry");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		nrgheaderpage=new NRGHeaderPage();
		nrgallservicespage = nrgheaderpage.clickOnAllServicesLink();
		
		// Test Step 2: Update subscription in SMUI
		nrgallservicespage.clickOnPaymentLink();
		nrgallservicespage.clickOnExistingCardRadioButton();
		nrgallservicespage.clickOnSelectCardButton();
		Assert.assertEquals(nrgallservicespage.getSuccessMessage(),"Billing details have been successfully updated", nrgallservicespage.getSuccessMessage());
		driver.close();
	}	
	
	@Parameters({"environment"})
	@Test
	public void testLoginToCustomerPortal(String environment) throws InterruptedException {

		strAccountReference = "ARQ-45";
		strPassword = "comein22";
		
		initialization(environment, "customerportalurl_netregistry");
		
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		nrgheaderpage=new NRGHeaderPage();
		
		// Test Step 2: Verify the overview page is displayed or not
		Assert.assertEquals(nrgheaderpage.verifyOverviewPageisDisplayed(),"Overview", nrgheaderpage.verifyOverviewPageisDisplayed());
	    driver.quit();
	}
	
	@Parameters({ "environment"})
	@Test
	public void testLoginAsClient(String environment)
			throws InterruptedException, AWTException {

		String accountReference = "ARQ-45";
		// Test Step 1: Login to Console Admin and search for Account reference
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caheaderpage.searchAccountReference(accountReference);
		nrgmanagedomainpage=caheaderpage.clickOnLoginasClientLink();
	
		// Test Step 2: Verify the overview page is displayed or not
		Assert.assertEquals(nrgmanagedomainpage.verifyManageDomainsPageisDisplayed(),"Overview", nrgmanagedomainpage.verifyManageDomainsPageisDisplayed());
		driver.quit();
	}
	
	@Parameters({ "environment"})
	@Test
	public void testLoginAsClientDomainLevel(String environment)
			throws InterruptedException, AWTException {

		//Test Step 1: Register a domain
		verifyDomainRegistration(environment);
		
		// Test Step 1: Search the register domain name and login as domain level
		caheaderpage=new CAHeaderPage();
		cadomainlevelpage=caheaderpage.searchDomain(DomainAndProduct_Order.registerdomain);
		csmuitabpage=cadomainlevelpage.clickloginAsClientLink();
		
		// Test Step 2: Verify the overview page is displayed or not
		Assert.assertEquals(csmuitabpage.verifyDashboardPageisDisplayed(),"Dashboard", csmuitabpage.verifyDashboardPageisDisplayed());
		driver.quit();
	}
	
	//Register a domain
		public void verifyDomainRegistration(String environment) throws InterruptedException, AWTException
		{
			domainandproductorder=new DomainAndProduct_Order();
			domainandproductorder.verifyDomainRegistrationForExistingCustomerUsingPrepaidCredit(environment);
		}
		
		@Parameters({ "environment"})
		@Test
		public void testLoginToStore(String environment)
				throws InterruptedException, AWTException {

			String accountReference = "ARQ-45";
			// Test Step 1: Login to Console Admin and search for Account reference
			initialization(environment, "consoleadmin");
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
			caheaderpage.searchAccountReference(accountReference);
			nrgonlineorderpage=caheaderpage.clickonLoginasClientLink();
			nrgonlineorderpage.getStoreUrl();
			driver.quit();
		}
		}
