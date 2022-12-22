package com.WC.CustomerPortal.testcases;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;
import com.webcentralwebsite.pages.WCAccountPage;
import com.webcentralwebsite.pages.WCBillingPage;
import com.webcentralwebsite.pages.WCCreditCardsDetailsPage;
import com.webcentralwebsite.pages.WCHeaderPage;
import com.webcentralwebsite.pages.WCLoginPage;
import com.webcentralwebsite.pages.WCPrepaidAccountPage;

public class WCCreditCardOperations extends TestBase {

	// Netregistry shopping cart pages
	WCLoginPage wcloginpage;
	WCAccountPage wcaccountpage;
	WCBillingPage wcbillingpage;
	WCCreditCardsDetailsPage wccreditcardsdetailspage;
	WCHeaderPage wcheaderpage;
	WCPrepaidAccountPage wcprepaidaccountpage;

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
 
	public WCCreditCardOperations() {
		super();
	}
	
	@Parameters({"environment"})
	@Test
	public void testAddNewCreditCardInSMUI(String environment) throws InterruptedException {

		strAccountReference = "ARQ-05";
		strPassword = "comein22";
		strCardOwner = "Test Mastercard";
		strCardType = "MasterCard";
		strCardNumber = "5555555555554444";
		strCardExpiryMonth = "05";
		strCardExpiryYear = "2022";
		strCardSecurityCode = "123";
		
		//String strAccountReference = CreateNewGreenCodeSalesDB.greenCode;
		System.out.println(strAccountReference);
		// Test Step 1: Login to customer portal
		initialization(environment, "customerportalurl_webcentral");
		wcloginpage = new WCLoginPage();
		//mitloginpage.setLoginDetails(CreateNewGreenCodeSalesDB.greenCode, strPassword);
		wcloginpage.setLoginDetails(strAccountReference, strPassword);
		wcheaderpage = wcloginpage.clickLoginButton();
		wcaccountpage = wcheaderpage.clickAccountTab();
		wccreditcardsdetailspage = wcaccountpage.clickEditCreditCardsOnFile();

		// Test Step 2: Add new credit card
		wccreditcardsdetailspage.setQuestFormCreditCardDetails(strCardOwner, strCardType, strCardNumber, strCardExpiryMonth,
				strCardExpiryYear, strCardSecurityCode);
		wccreditcardsdetailspage.clickAddCreditCard();
		String confirmationMessage = wccreditcardsdetailspage.getConfirmationMessage();
		Assert.assertEquals(confirmationMessage, "New credit card has been added");
		//Assert.assertTrue(wcbillingpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
		
		
		
		driver.close();
	}

	
	@Parameters({"environment"})
	@Test
	public void testModifyExistingCreditCardInSMUI(String environment) throws InterruptedException {

		strAccountReference = "ARQ-05";
		strPassword = "comein22";
		strCardOwner = "Test Master";
		strCardExpiryMonth = "04";
		strCardExpiryYear = "2024";
		strCardSecurityCode = "123";

		// Test Step 1: Login to customer portal
		initialization(environment, "customerportalurl_webcentral");
		wcloginpage = new WCLoginPage();
		wcloginpage.setLoginDetails(strAccountReference, strPassword);
		wcheaderpage = wcloginpage.clickLoginButton();
		wcaccountpage = wcheaderpage.clickAccountTab();
		wccreditcardsdetailspage = wcaccountpage.clickEditCreditCardsOnFile();

		// Modify existing card details
		wccreditcardsdetailspage.clickOnExistingCard(strCardOwner);
		wccreditcardsdetailspage.modifyCreditCardDetails(strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		Assert.assertEquals(wccreditcardsdetailspage.getConfirmationMessage(),"The credit card has successfully been modified", wccreditcardsdetailspage.getConfirmationMessage());
		driver.close();
	}

	
	@Parameters({ "environment"})
	@Test
	public void testMakeCreditCardDefaultInSMUI(String environment) throws InterruptedException {

		strAccountReference = "ARQ-05";
		strPassword = "comein22";
		strCardOwner = "Test Master";
		initialization(environment, "customerportalurl_webcentral");
		wcloginpage = new WCLoginPage();
		wcloginpage.setLoginDetails(strAccountReference, strPassword);
		wcheaderpage = wcloginpage.clickLoginButton();
		wcaccountpage = wcheaderpage.clickAccountTab();
		wccreditcardsdetailspage = wcaccountpage.clickEditCreditCardsOnFile();

		// To make this card default
		wcaccountpage.makeCardDefault(strCardOwner);

		// To return original default card
		wcaccountpage.makeCardDefault("ORIGINAL DEFAULT CARD");
		driver.close();
	}
	

	@Parameters({ "environment" })
	@Test
	public void testDeleteCreditCardInSMUI(String environment) throws InterruptedException {
		
		String straccountreference = "ARQ-05";
		String strpassword = "comein22";
		initialization(environment, "customerportalurl_webcentral");
		wcloginpage = new WCLoginPage();
		wcloginpage.setLoginDetails(straccountreference, strpassword);
		wcheaderpage = wcloginpage.clickLoginButton();
		wcaccountpage = wcheaderpage.clickAccountTab();
		wccreditcardsdetailspage = wcaccountpage.clickEditCreditCardsOnFile();
		wccreditcardsdetailspage.deleteCreditCard();
		driver.close();

	}

	@Parameters({ "environment"})
	@Test
	public void testRechargePrepaidUsingExistingCardInSMUI(String environment) throws InterruptedException {

		strAccountReference = "ARQ-05";
		strPassword = "comein22";
		strAmount = "10";
		initialization(environment, "customerportalurl_webcentral");
		wcloginpage = new WCLoginPage();
		wcloginpage.setLoginDetails(strAccountReference, strPassword);
		wcheaderpage = wcloginpage.clickLoginButton();
		wcbillingpage = wcheaderpage.clickBillingTab();
		wcprepaidaccountpage = wcbillingpage.clickEditPrepaidAccountLink();
		wcprepaidaccountpage.clickRechargeUsingCreditCard();
		wcprepaidaccountpage.enterRechargeAmount(strAmount);
		wcprepaidaccountpage.clickSubmitButton();
		driver.close();
	}

	@Parameters({ "environment" })
	@Test
	public void testRechargePrepaidUsingNewCardInSMUI(String environment)
			throws InterruptedException {

		strAccountReference = "ARQ-05";
		strPassword = "comein22";
		strCardOwner = "Test Mastercard";
		strCardType = "MasterCard";
		strCardNumber = "5454545454545454";
		strCardExpiryMonth = "02";
		strCardExpiryYear = "2022";
		strCardSecurityCode = "123";
		strAmount = "10";

		initialization(environment, "customerportalurl_webcentral");
		wcloginpage = new WCLoginPage();
		wcloginpage.setLoginDetails(strAccountReference, strPassword);
		wcheaderpage = wcloginpage.clickLoginButton();
		wcbillingpage = wcheaderpage.clickBillingTab();
		wcprepaidaccountpage = wcbillingpage.clickEditPrepaidAccountLink();
		wcprepaidaccountpage.clickRechargeUsingCreditCard();
		wcprepaidaccountpage.clickOnNewCreditCard();
		wcprepaidaccountpage.setNewCreditCardDetailsQuest(strCardOwner, strCardType, strCardNumber, strCardExpiryMonth,
				strCardExpiryYear, strCardSecurityCode);
		wcprepaidaccountpage.enterRechargeAmount(strAmount);
		wcprepaidaccountpage.clickSubmitButton();
		driver.close();
	}
		
}