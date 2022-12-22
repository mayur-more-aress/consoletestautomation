package com.MIT.CustomerPortal.testcases;

import java.awt.AWTException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.commonfunctionality.MIT_CommonFunctionality;
import com.consoleadmin.pages.CAAccountReferencePage;
import com.consoleadmin.pages.CADomainLevelPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consolesmui.pages.CSMUITabPage;
import com.melbourneitwebsite.pages.MITAccountPage;
import com.melbourneitwebsite.pages.MITBillingPage;
import com.melbourneitwebsite.pages.MITCreditCardsDetailsPage;
import com.melbourneitwebsite.pages.MITHeaderPage;
import com.melbourneitwebsite.pages.MITLoginPage;
import com.melbourneitwebsite.pages.MITOnlineOrderPage;
import com.melbourneitwebsite.pages.MITPrepaidAccountPage;
import com.mitcustomerportal.pages.MITAllServicesPage;
import com.mitcustomerportal.pages.MITManageDomainsPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class CreditCard_Operations extends MIT_CommonFunctionality {

	MITLoginPage mitloginpage;
	MITAccountPage mitaccountpage;
	MITBillingPage mitbillingpage;
	MITCreditCardsDetailsPage mitcreditcardsdetailspage;
	MITHeaderPage mitheaderpage;
	MITPrepaidAccountPage mitprepaidaccountpage;
	MITAllServicesPage mitallservicespage;
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	MITManageDomainsPage mitmanagedomainspage;
	CAAccountReferencePage caaccountrefrencepage;
	CADomainLevelPage cadomainlevelpage;
	CSMUITabPage csmuitabpage;
	MITOnlineOrderPage mitonlineorderpage;

	// objects
	@FindBy(how = How.LINK_TEXT, using = "Account")
	WebElement lnkAccount;

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

		strAccountReference = "MEL-6005";
		strPassword = "comein22";
		strCardOwner = "Test Mastercard";
		strCardType = "MasterCard";
		strCardNumber = "5555555555554444";
		strCardExpiryMonth = "05";
		strCardExpiryYear = "2022";
		strCardSecurityCode = "123";

		initialization(environment, "customerportalurl_melbourneit");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		mitheaderpage=new MITHeaderPage();
		mitaccountpage = mitheaderpage.clickAccountTab();
		mitcreditcardsdetailspage = mitaccountpage.clickEditCreditCardsOnFile();

		// Test Step 2: Add new credit card
		mitcreditcardsdetailspage.setQuestFormCreditCardDetails(strCardOwner, strCardType,strCardNumber, strCardExpiryMonth,
				strCardExpiryYear, strCardSecurityCode);
		mitbillingpage = mitcreditcardsdetailspage.clickAddCreditCard();
		driver.close();
	}


	@Parameters({"environment"})
	@Test
	public void testModifyExistingCreditCardInSMUI(String environment) throws InterruptedException {

		strAccountReference = "MEL-6005";
		strPassword = "comein22";
		strCardOwner = "Test Master";
		strCardExpiryMonth = "04";
		strCardExpiryYear = "2025";
		String strCvv="123";
		initialization(environment, "customerportalurl_melbourneit");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		mitheaderpage=new MITHeaderPage();
		mitaccountpage = mitheaderpage.clickAccountTab();
		mitcreditcardsdetailspage = mitaccountpage.clickEditCreditCardsOnFile();

		// Test Step 2: Modify existing card details
		mitcreditcardsdetailspage.clickOnExistingCard(strCardOwner);
		mitcreditcardsdetailspage.modifyCreditCardDetailsBT(strCardExpiryMonth, strCardExpiryYear,strCvv);
		Assert.assertEquals(mitcreditcardsdetailspage.getConfirmationMessage(),"The credit card has successfully been modified", mitcreditcardsdetailspage.getConfirmationMessage());
		driver.close();
	}

	@Parameters({ "environment" })
	@Test
	public void testDeleteCreditCardInSMUI(String environment) throws InterruptedException {

		String straccountreference = "MEL-6005";
		String strpassword = "comein22";
		initialization(environment, "customerportalurl_melbourneit");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(straccountreference,strpassword);
		mitheaderpage=new MITHeaderPage();
		mitaccountpage = mitheaderpage.clickAccountTab();
		mitcreditcardsdetailspage = mitaccountpage.clickEditCreditCardsOnFile();
		// Test Step 2: Delete the credit card
		//mitcreditcardsdetailspage.deleteCreditCard();
		driver.close();

	}

	@Parameters({ "environment"})
	@Test
	public void testMakeCreditCardDefaultInSMUI(String environment) throws InterruptedException {

		strAccountReference = "MEL-6005";
		strPassword = "comein22";
		strCardOwner = "Test Master";
		initialization(environment, "customerportalurl_melbourneit");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		mitheaderpage=new MITHeaderPage();
		mitaccountpage = mitheaderpage.clickAccountTab();
		mitcreditcardsdetailspage = mitaccountpage.clickEditCreditCardsOnFile();

		// Test Step 2: To make this card default
		mitaccountpage.makeCardDefault(strCardOwner);
		//mitaccountpage.makeCardDefault("ORIGINAL DEFAULT CARD");
		driver.close();
	}

	@Parameters({ "environment"})
	@Test
	public void testRechargePrepaidUsingExistingCardInSMUI(String environment) throws InterruptedException {

		strAccountReference = "MEL-6005";
		strPassword = "comein22";
		strAmount = "10";
		initialization(environment, "customerportalurl_melbourneit");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		mitheaderpage=new MITHeaderPage();
		mitbillingpage = mitheaderpage.clickBillingTab();
		mitprepaidaccountpage = mitbillingpage.clickEditPrepaidAccountLink();

		// Test Step 2: Recharge prepaid using the existing card
		mitprepaidaccountpage.clickRechargeUsingCreditCard();
		mitprepaidaccountpage.enterRechargeAmount(strAmount);
		mitprepaidaccountpage.clickSubmitButton();
		driver.close();
	}

	@Parameters({ "environment" })
	@Test
	public void testRechargePrepaidUsingNewCardInSMUI(String environment)
			throws InterruptedException {

		strAccountReference = "MEL-6005";
		strPassword = "comein22";
		strCardOwner = "Test Mastercard";
		strCardType = "MasterCard";
		strCardNumber = "5454545454545454";
		strCardExpiryMonth = "02";
		strCardExpiryYear = "2022";
		strCardSecurityCode = "123";
		strAmount = "10";

		initialization(environment, "customerportalurl_melbourneit");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		mitheaderpage=new MITHeaderPage();
		mitbillingpage = mitheaderpage.clickBillingTab();
		mitprepaidaccountpage = mitbillingpage.clickEditPrepaidAccountLink();
		mitprepaidaccountpage.clickRechargeUsingCreditCard();

		// Test Step 2: Recharge prepaid using the new card
		mitprepaidaccountpage.clickOnNewCreditCard();
		mitprepaidaccountpage.setNewCreditCardDetailsQuest(strCardOwner, strCardNumber, strCardExpiryMonth,
				strCardExpiryYear, strCardSecurityCode);
		mitprepaidaccountpage.enterRechargeAmount(strAmount);
		mitprepaidaccountpage.clickSubmitButton();
		driver.close();
	}

	@Parameters({ "environment"})
	@Test
	public void verifyUpdateSubscriptionInSMUI(String environment)
			throws InterruptedException {

		strAccountReference = "MEL-6005";
		strPassword = "comein22";

		initialization(environment, "customerportalurl_melbourneit");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		mitheaderpage=new MITHeaderPage();
		mitallservicespage = mitheaderpage.clickOnAllServicesLink();
		// Test Step 2: To make this card default
		mitallservicespage.clickOnPaymentLink();
		mitallservicespage.clickOnExistingCardRadioButton();
		mitallservicespage.clickOnSelectCardButton();
		Assert.assertEquals(mitallservicespage.getSuccessMessage(),"Billing details have been successfully updated", mitallservicespage.getSuccessMessage());
		driver.close();
	}
	@Parameters({"environment"})
	@Test
	public void testLoginToCustomerPortal(String environment) throws InterruptedException {

		strAccountReference = "MEL-6005";
		strPassword = "comein22";
		
		initialization(environment, "customerportalurl_melbourneit");
		
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		mitheaderpage=new MITHeaderPage();
		
		// Test Step 2: Verify the overview page is displayed or not
		Assert.assertEquals(mitheaderpage.verifyOverviewPageisDisplayed(),"Overview", mitheaderpage.verifyOverviewPageisDisplayed());
	    driver.quit();
	}
	
	@Parameters({ "environment"})
	@Test
	public void testLoginAsClient(String environment)
			throws InterruptedException, AWTException {

		String accountReference = "MEL-6005";
		// Test Step 1: Login to Console Admin and search for Account reference
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caheaderpage.searchAccountReference(accountReference);
		mitmanagedomainspage=caheaderpage.clickLoginasClientLink();
		Assert.assertEquals(mitmanagedomainspage.verifyManageDomainsPageisDisplayed(),"Overview", mitmanagedomainspage.verifyManageDomainsPageisDisplayed());
		 driver.quit();
	}
	@Parameters({ "environment"})
	@Test
	public void testLoginAsClientDomainLevel(String environment)
			throws InterruptedException, AWTException {

		String accountReference = "MEL-6005";
		String domainname="testconsole2412.com";
		
		// Test Step 1: Login to Console Admin and search for Account reference
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caaccountrefrencepage=caheaderpage.searchAccountReference(accountReference);
		caaccountrefrencepage.selectDomainName(domainname);
		cadomainlevelpage=caaccountrefrencepage.clickOnAdministartorButton();
		csmuitabpage=cadomainlevelpage.clickloginAsClientLink();
		
		// Test Step 2: Verify the overview page is displayed or not
		Assert.assertEquals(csmuitabpage.verifyDashboardPageisDisplayed(),"Dashboard", csmuitabpage.verifyDashboardPageisDisplayed());
		driver.quit();
}
	
	@Parameters({ "environment"})
	@Test
	public void testLoginAsClientdomainLevel(String environment)
			throws InterruptedException, AWTException {

		String domainname="testconsole2412.com";
		
		// Test Step 1: Login to Console Admin and search for Account reference
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		cadomainlevelpage=caheaderpage.searchDomainName(domainname);
		csmuitabpage=cadomainlevelpage.clickloginAsClientLink();
		
		// Test Step 2: Verify the overview page is displayed or not
		Assert.assertEquals(csmuitabpage.verifyDashboardPageisDisplayed(),"Dashboard", csmuitabpage.verifyDashboardPageisDisplayed());
		driver.quit();
}
	
	@Parameters({ "environment"})
	@Test
	public void testLoginToStore(String environment)
			throws InterruptedException, AWTException {

		String accountReference = "MEL-6005";
		// Test Step 1: Login to Console Admin and search for Account reference
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login("erwin.sukarna", "comein22");
		caheaderpage.searchAccountReference(accountReference);
		mitonlineorderpage=caheaderpage.clickOnLoginAsClient();
		mitonlineorderpage.getStoreUrl();
		driver.quit();
	}
}