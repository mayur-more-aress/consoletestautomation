package com.MITDPS.CustomerPortal.testcases;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.commonfunctionality.MITDPS_CommonFunctionality;
import com.mitdpscustomerportal.pages.MITDPSAccountTabPage;
import com.mitdpscustomerportal.pages.MITDPSAllServicesPage;
import com.mitdpscustomerportal.pages.MITDPSBillingPage;
import com.mitdpscustomerportal.pages.MITDPSCreditCardsDetailsPage;
import com.mitdpscustomerportal.pages.MITDPSHeaderPage;
import com.mitdpscustomerportal.pages.MITDPSPrepaidAccountPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.tppcustomerportal.pages.TPPAccountContactPage;
import com.util.TestUtil;

public class CreditCard_Operations extends MITDPS_CommonFunctionality {

	// Netregistry shopping cart pages
	MITDPSAccountTabPage mitdpsaccounttabpage;
	MITDPSCreditCardsDetailsPage mitdpscreditcardsdetailspage;
	MITDPSBillingPage mitdpsbillingpage;
	TPPAccountContactPage tppaccountcontactpage;
	MITDPSHeaderPage mitdpsheaderpage;
	MITDPSAllServicesPage mitdpsallservicespage;
	MITDPSPrepaidAccountPage mitdpsprepaidaccountpage;
	
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

		strAccountReference = "MEL-6040";
		strPassword = "comein22";
		strCardOwner = "Test Mastercard";
		strCardType = "Visa";
		strCardNumber = "4005519200000004";
		strCardExpiryMonth = "05";
		strCardExpiryYear = "2022";
		strCardSecurityCode = "123";
		
		initialization(environment, "customerportalurl_mitdps");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		mitdpsaccounttabpage=new MITDPSAccountTabPage();
		mitdpsaccounttabpage.clickAccountTabLink();
		mitdpscreditcardsdetailspage = mitdpsaccounttabpage.clickEditCreditCardsOnFile();

		// Test Step 2: Add new credit card
		mitdpscreditcardsdetailspage.setQuestFormCreditCardDetails(strCardOwner,strCardType,strCardNumber,
				strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		mitdpsbillingpage = mitdpscreditcardsdetailspage.clickAddCreditCard();
		//mitdpscreditcardsdetailspage.isNewCreditCardAdded();
		driver.close();
	}
	
	@Parameters({"environment"})
	@Test
	public void testModifyExistingCreditCardInSMUI(String environment) throws InterruptedException {

		strAccountReference = "MEL-6040";
		strPassword = "comein22";
		strCardOwner = "Test Mastercard";
		strCardExpiryMonth = "05";
		strCardExpiryYear = "2023";

		initialization(environment, "customerportalurl_mitdps");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		mitdpsaccounttabpage=new MITDPSAccountTabPage();
		mitdpsaccounttabpage.clickAccountTabLink();
		mitdpscreditcardsdetailspage = mitdpsaccounttabpage.clickEditCreditCardsOnFile();

		//Test Step 2: Modify existing card details
		mitdpscreditcardsdetailspage.clickOnExistingCard(strCardOwner);
		mitdpscreditcardsdetailspage.modifyCreditCardDetailsBT(strCardExpiryMonth, strCardExpiryYear);
		Assert.assertEquals(mitdpscreditcardsdetailspage.getConfirmationMessage(),"The credit card has successfully been modified", mitdpscreditcardsdetailspage.getConfirmationMessage());
		driver.close();
	}	
	
	@Parameters({"environment"})
	@Test
	public void verifyUpdateSubscriptionInSMUI(String environment) throws InterruptedException {

		strAccountReference = "MEL-6040";
		strPassword = "comein22";

		// Test Step 1: Login to customer portal
		initialization(environment, "customerportalurl_mitdps");
		loginToCustomerPortal(strAccountReference,strPassword);
		mitdpsheaderpage=new MITDPSHeaderPage();
		mitdpsallservicespage=mitdpsheaderpage.clickOnAllServicesLink();
		// Test Step 2:Update subscription in SMUI
		mitdpsallservicespage.clickOnPaymentLink();
		mitdpsallservicespage.clickOnExistingCardRadioButton();
		mitdpsallservicespage.clickOnSelectCardButton();
		Assert.assertEquals(mitdpsallservicespage.getSuccessMessage(),"Billing details have been successfully updated", mitdpsallservicespage.getSuccessMessage());
		driver.close();
	}	
	@Parameters({ "environment" })
	@Test
	public void testDeleteCreditCardInSMUI(String environment) throws InterruptedException {
		
		strAccountReference = "MEL-6040";
		strPassword = "comein22";

		initialization(environment, "customerportalurl_mitdps");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		mitdpsaccounttabpage=new MITDPSAccountTabPage();
		mitdpsaccounttabpage.clickAccountTabLink();
		mitdpscreditcardsdetailspage = mitdpsaccounttabpage.clickEditCreditCardsOnFile();
		
		// Test Step 2: Delete the credit card
		mitdpscreditcardsdetailspage.deleteCreditCard();
		driver.close();

	}

   @Parameters({ "environment"})
	@Test
	public void testRechargePrepaidUsingExistingCardInSMUI(String environment) throws InterruptedException {
	    strAccountReference = "MEL-6040";
		strPassword = "comein22";
		strAmount = "10";
		initialization(environment, "customerportalurl_mitdps");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		mitdpsheaderpage=new MITDPSHeaderPage();
		mitdpsbillingpage = mitdpsheaderpage.clickBillingTab();
		mitdpsprepaidaccountpage = mitdpsbillingpage.clickEditPrepaidAccountLink();
		
		// Test Step 2: Recharge prepaid using the existing card
		mitdpsprepaidaccountpage.clickRechargeUsingCreditCard();
		mitdpsprepaidaccountpage.enterRechargeAmount(strAmount);
		mitdpsprepaidaccountpage.clickSubmitButton();
		driver.close();
	}
   
   @Parameters({ "environment"})
	@Test
	public void testRechargePrepaidUsingNewCardInSMUI(String environment)
			throws InterruptedException {

		strAccountReference = "MEL-6040";
		strPassword = "comein22";
		strCardOwner = "Test Mastercard";
		strCardType = "MasterCard";
		strCardNumber = "5454545454545454";
		strCardExpiryMonth = "02";
		strCardExpiryYear = "2022";
		strCardSecurityCode = "123";
		strAmount = "10";

		initialization(environment, "customerportalurl_mitdps");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		mitdpsheaderpage=new MITDPSHeaderPage();
		mitdpsbillingpage = mitdpsheaderpage.clickBillingTab();
		mitdpsprepaidaccountpage = mitdpsbillingpage.clickEditPrepaidAccountLink();
		
		// Test Step 2: Recharge prepaid using the new card
		mitdpsprepaidaccountpage.clickRechargeUsingCreditCard();
		mitdpsprepaidaccountpage.clickOnNewCreditCard();
		/*mitdpsprepaidaccountpage.setNewCreditCardDetailsBT(strCardOwner, strCardNumber, strCardExpiryMonth,
				strCardExpiryYear, strCardSecurityCode);*/
		mitdpsprepaidaccountpage.setNewCreditCardDetails(strCardOwner, strCardNumber, strCardExpiryMonth,
				strCardExpiryYear, strCardSecurityCode);
		mitdpsprepaidaccountpage.enterRechargeAmount(strAmount);
		mitdpsprepaidaccountpage.clickSubmitButton();
		driver.close();
	}
   @Parameters({ "environment"})
	@Test
	public void testMakeCreditCardDefaultInSMUI(String environment) throws InterruptedException {

		strAccountReference = "MEL-6040";
		strPassword = "comein22";
		strCardOwner = "Test Mastercard";
		initialization(environment, "customerportalurl_mitdps");
		// Test Step 1: Login to customer portal
		loginToCustomerPortal(strAccountReference,strPassword);
		mitdpsheaderpage=new MITDPSHeaderPage();
		mitdpsaccounttabpage=mitdpsheaderpage.clickAccountTab();
		mitdpscreditcardsdetailspage = mitdpsaccounttabpage.clickEditCreditCardsOnFile();

		// Test Step 2: To make this card default
		mitdpsaccounttabpage.makeCardDefault(strCardOwner);
		mitdpsaccounttabpage.verifyDefaultCreditCard();
		driver.close();
	}
	
}