package com.paymentgateway.uat.melbourneit.testcases;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.melbourneitwebsite.pages.MITAccountPage;
import com.melbourneitwebsite.pages.MITBillingPage;
import com.melbourneitwebsite.pages.MITCreditCardsDetailsPage;
import com.melbourneitwebsite.pages.MITHeaderPage;
import com.melbourneitwebsite.pages.MITLoginPage;
import com.melbourneitwebsite.pages.MITPrepaidAccountPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class RegressionSMUI extends TestBase {

	// Domainz shopping cart pages
	MITLoginPage MITloginpage;
	MITAccountPage MITaccountpage;
	MITBillingPage MITbillingpage;
	MITCreditCardsDetailsPage MITcreditcardsdetailspage;
	MITHeaderPage MITheaderpage;
	MITPrepaidAccountPage MITprepaidaccountpage;

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
	
	public RegressionSMUI() {
		super();
	}

	
	@Parameters({"environment", "paymentgateway"})
	@Test
	public void testAddNewCreditCardInSMUI(String environment, String paymentgateway) throws InterruptedException {

		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			strAccountReference = "DOM-1218";
			strPassword = "comein22";
			strCardOwner = "Test Mastercard";
			strCardType = "MasterCard";
			strCardNumber = "5454545454545454";
			strCardExpiryMonth = "02";
			strCardExpiryYear = "2020";
			strCardSecurityCode = "123";		
		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			strAccountReference = "MEL-6005";
			strPassword = "comein22";
			strCardOwner = "Test Mastercard";
			strCardType = "MasterCard";
			strCardNumber = "5555555555554444";
			strCardExpiryMonth = "05";
			strCardExpiryYear = "2020";
			strCardSecurityCode = "123";	
		}
		
		//Test Step 1: Login to customer portal
		initialization(environment, "customerportalurl_melbourneit");
		MITloginpage = new MITLoginPage();
		MITloginpage.setLoginDetails(strAccountReference, strPassword);
		MITheaderpage = MITloginpage.clickLoginButton();
		MITaccountpage = MITheaderpage.clickAccountTab();
		MITcreditcardsdetailspage = MITaccountpage.clickEditCreditCardsOnFile();
		
		//Test Step 2: Add new credit card
		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			//MITcreditcardsdetailspage.setQuestFormCreditCardDetails(strCardOwner, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			MITcreditcardsdetailspage.setBTFormCreditCardDetails(strCardOwner, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}

		MITbillingpage = MITcreditcardsdetailspage.clickAddCreditCard();
		Assert.assertTrue(MITbillingpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
		driver.close();
	}

	
	@Parameters({"environment", "paymentgateway"})
	@Test
	public void testModifyExistingCreditCardInSMUI(String environment, String paymentgateway) throws InterruptedException {

		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			strAccountReference = "DOM-1218";
			strPassword = "comein22";
			strCardExpiryMonth = "04";
			strCardExpiryYear = "2021";
		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			strAccountReference = "MEL-6005";
			strPassword = "comein22";
			strCardExpiryMonth = "04";
			strCardExpiryYear = "2021";
		}

		//Test Step 1: Login to customer portal
		initialization(environment, "customerportalurl_melbourneit");
		MITloginpage = new MITLoginPage();
		MITloginpage.setLoginDetails(strAccountReference, strPassword);
		MITheaderpage = MITloginpage.clickLoginButton();
		MITaccountpage = MITheaderpage.clickAccountTab();
		MITcreditcardsdetailspage = MITaccountpage.clickEditCreditCardsOnFile();

		// Modify existing card details
		//MITcreditcardsdetailspage.clickOnExistingCard();
		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			//MITcreditcardsdetailspage.modifyCreditCardDetails();
		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {			
			MITcreditcardsdetailspage.modifyCreditCardDetails(strCardExpiryMonth, strCardExpiryYear);
		}
		
		Assert.assertEquals(MITcreditcardsdetailspage.getConfirmationMessage(), "The credit card has successfully been modified", 
			MITcreditcardsdetailspage.getConfirmationMessage());	
		driver.close();
	}

	
	@Parameters({ "environment", "paymentgateway" })
	@Test
	public void testMakeCreditCardDefaultInSMUI(String environment, String paymentgateway) throws InterruptedException {
		
		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			strAccountReference = "DOM-1218";
			strPassword = "comein22";
		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			strAccountReference = "MEL-6005";
			strPassword = "comein22";
			strCardOwner = "Test Card - MasterCard";
		}

		initialization(environment, "customerportalurl_melbourneit");
		MITloginpage = new MITLoginPage();
		MITloginpage.setLoginDetails(strAccountReference, strPassword);
		MITheaderpage = MITloginpage.clickLoginButton();
		MITaccountpage = MITheaderpage.clickAccountTab();
		MITcreditcardsdetailspage = MITaccountpage.clickEditCreditCardsOnFile();
		MITaccountpage.makeCardDefault(strCardOwner);
		driver.close();
	}
	

	@Parameters({ "environment", "paymentgateway" })
	@Test
	public void testDeleteCreditCardInSMUI(String environment, String paymentgateway) throws InterruptedException {
	initialization(environment, "customerportalurl_domainz");
		MITloginpage = new MITLoginPage();
		MITaccountpage = new MITAccountPage();
    	MITbillingpage = new MITBillingPage();
		MITcreditcardsdetailspage = new MITCreditCardsDetailsPage();

		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			strAccountReference = "DOM-1218";
			strPassword = "comein22";
			MITloginpage.setLoginDetails(strAccountReference, strPassword);
		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			strAccountReference = "DOM-1311";
			strPassword = "comein22";
			MITloginpage.setLoginDetails(strAccountReference, strPassword);
		}
		MITheaderpage = MITloginpage.clickLoginButton();
		MITaccountpage = MITheaderpage.clickAccountTab();
		MITcreditcardsdetailspage = MITaccountpage.clickEditCreditCardsOnFile();
		MITcreditcardsdetailspage.deleteCreditCard();
		Assert.assertEquals(MITcreditcardsdetailspage.getConfirmationMessage(), "Credit card has been deleted", MITcreditcardsdetailspage.getConfirmationMessage());
		
		driver.close();

	}
	
	@Parameters({ "environment", "paymentgateway" })
	@Test
	public void testRechargePrepaidUsingExistingCardInSMUI(String environment, String paymentgateway) throws InterruptedException {

		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			strAccountReference = "DOM-1218";
			strPassword = "comein22";
			strCardOwner = "Test Mastercard";
			strCardType = "MasterCard";
			strCardNumber = "5454545454545454";
			strCardExpiryMonth = "02";
			strCardExpiryYear = "2020";
			strCardSecurityCode = "123";
			strAmount = "10";

		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			strAccountReference = "DOM-1311";
			strPassword = "comein22";
			strCardOwner = "Test Mastercard";
			strCardType = "MasterCard";
			strCardNumber = "5454545454545454";
			strCardExpiryMonth = "02";
			strCardExpiryYear = "2020";
			strCardSecurityCode = "123";
			strAmount = "10";			
		}
		
		initialization(environment, "customerportalurl_melbourneit");
		MITloginpage = new MITLoginPage();
		MITloginpage.setLoginDetails(strAccountReference, strPassword);
		MITheaderpage = MITloginpage.clickLoginButton();	
		MITbillingpage = MITheaderpage.clickBillingTab();
		MITprepaidaccountpage = MITbillingpage.clickEditPrepaidAccountLink();
		MITprepaidaccountpage.clickRechargeUsingCreditCard();
		MITprepaidaccountpage.enterRechargeAmount(strAmount);
		MITprepaidaccountpage.clickSubmitButton();

		driver.close();
	}

	@Parameters({ "environment", "paymentgateway" })
	@Test
	public void testRechargePrepaidUsingNewCardInSMUI(String environment, String paymentgateway)
			throws InterruptedException {

		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			strAccountReference = "DOM-1218";
			strPassword = "comein22";
			strCardOwner = "Test Mastercard";
			strCardType = "MasterCard";
			strCardNumber = "5454545454545454";
			strCardExpiryMonth = "02";
			strCardExpiryYear = "2020";
			strCardSecurityCode = "123";
			strAmount = "10";

		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			strAccountReference = "DOM-1311";
			strPassword = "comein22";
			strCardOwner = "Test Mastercard";
			strCardType = "MasterCard";
			strCardNumber = "5454545454545454";
			strCardExpiryMonth = "02";
			strCardExpiryYear = "2020";
			strCardSecurityCode = "123";
			strAmount = "10";			
		}
		
		initialization(environment, "customerportalurl_melbourneit");
		MITloginpage = new MITLoginPage();
		MITloginpage.setLoginDetails(strAccountReference, strPassword);
		MITheaderpage = MITloginpage.clickLoginButton();	
		MITbillingpage = MITheaderpage.clickBillingTab();
		MITprepaidaccountpage = MITbillingpage.clickEditPrepaidAccountLink();
		MITprepaidaccountpage.clickRechargeUsingCreditCard();

		MITprepaidaccountpage.clickOnNewCreditCard();
		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
		//	MITprepaidaccountpage.setNewCreditCardDetailsQuest(cardowner, cardnumber, cardexpirymonth, cardexpiryyear,
		//			cardsecuritycode);
		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			MITprepaidaccountpage.setNewCreditCardDetailsBT(strCardOwner, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		MITprepaidaccountpage.enterRechargeAmount(strAmount);
		MITprepaidaccountpage.clickSubmitButton();

		driver.close();

	}
	
	
	
	
	
	
}
