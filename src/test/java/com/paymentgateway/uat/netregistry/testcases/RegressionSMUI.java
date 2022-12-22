package com.paymentgateway.uat.netregistry.testcases;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.netregistryoldwebsite.pages.NRGAccountPage;
import com.netregistryoldwebsite.pages.NRGBillingPage;
import com.netregistryoldwebsite.pages.NRGCreditCardsDetailsPage;
import com.netregistryoldwebsite.pages.NRGHeaderPage;
import com.netregistryoldwebsite.pages.NRGLoginPage;
import com.netregistryoldwebsite.pages.NRGPrepaidAccountPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class RegressionSMUI extends TestBase {

	// Netregistry shopping cart pages
	NRGLoginPage nrgloginpage;
	NRGAccountPage nrgaccountpage;
	NRGBillingPage nrgbillingpage;
	NRGCreditCardsDetailsPage nrgcreditcardsdetailspage;
	NRGHeaderPage nrgheaderpage;
	NRGPrepaidAccountPage nrgprepaidaccountpage;

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
			strAccountReference = "";
			strPassword = "";
			strCardOwner = "";
			strCardType = "";
			strCardNumber = "";
			strCardExpiryMonth = "";
			strCardExpiryYear = "";
			strCardSecurityCode = "";		
		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			strAccountReference = "NET-1218";
			strPassword = "comein22";
			strCardOwner = "Test Mastercard";
			strCardType = "MasterCard";
			strCardNumber = "5555555555554444";
			strCardExpiryMonth = "05";
			strCardExpiryYear = "2020";
			strCardSecurityCode = "123";	
		}
		
		//Test Step 1: Login to customer portal
		initialization(environment, "customerportalurl_netregistry");
		nrgloginpage = new NRGLoginPage();
		nrgloginpage.setLoginDetails(strAccountReference, strPassword);
		nrgheaderpage = nrgloginpage.clickLoginButton();
		nrgaccountpage = nrgheaderpage.clickAccountTab();
		nrgcreditcardsdetailspage = nrgaccountpage.clickEditCreditCardsOnFile();
		
		//Test Step 2: Add new credit card
		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			//dmzcreditcardsdetailspage.setQuestFormCreditCardDetails(strCardOwner, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			nrgcreditcardsdetailspage.setBTFormCreditCardDetails(strCardOwner, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}

		nrgbillingpage = nrgcreditcardsdetailspage.clickAddCreditCard();
		Assert.assertTrue(nrgbillingpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
		driver.close();
	}

	
	@Parameters({"environment", "paymentgateway"})
	@Test
	public void testModifyExistingCreditCardInSMUI(String environment, String paymentgateway) throws InterruptedException {

		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			strAccountReference = "";
			strPassword = "";
			strCardExpiryMonth = "";
			strCardExpiryYear = "";
		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			strAccountReference = "NET-1218";
			strPassword = "comein22";
			strCardOwner = "Test Master";
			strCardExpiryMonth = "04";
			strCardExpiryYear = "2021";	
		}

		//Test Step 1: Login to customer portal
		initialization(environment, "customerportalurl_netregistry");
		nrgloginpage = new NRGLoginPage();
		nrgloginpage.setLoginDetails(strAccountReference, strPassword);
		nrgheaderpage = nrgloginpage.clickLoginButton();
		nrgaccountpage = nrgheaderpage.clickAccountTab();
		nrgcreditcardsdetailspage = nrgaccountpage.clickEditCreditCardsOnFile();

		// Modify existing card details
		nrgcreditcardsdetailspage.clickOnExistingCard(strCardOwner);
		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			//dmzcreditcardsdetailspage.modifyCreditCardDetails();
		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {			
			nrgcreditcardsdetailspage.modifyCreditCardDetailsBT(strCardExpiryMonth, strCardExpiryYear);
		}
		
		Assert.assertEquals(nrgcreditcardsdetailspage.getConfirmationMessage(), "The credit card has successfully been modified", 
				nrgcreditcardsdetailspage.getConfirmationMessage());	
		driver.close();
	}

	
	@Parameters({ "environment", "paymentgateway" })
	@Test
	public void testMakeCreditCardDefaultInSMUI(String environment, String paymentgateway) throws InterruptedException {
		
		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			strAccountReference = "";
			strPassword = "";
		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			strAccountReference = "NET-1218";
			strPassword = "comein22";
			strCardOwner = "Test Master";
		}

		initialization(environment, "customerportalurl_netregistry");
		nrgloginpage = new NRGLoginPage();
		nrgloginpage.setLoginDetails(strAccountReference, strPassword);
		nrgheaderpage = nrgloginpage.clickLoginButton();
		nrgaccountpage = nrgheaderpage.clickAccountTab();
		nrgcreditcardsdetailspage = nrgaccountpage.clickEditCreditCardsOnFile();
		
		//To make this card default
		nrgaccountpage.makeCardDefault(strCardOwner);
		
		//To return original default card
		nrgaccountpage.makeCardDefault("ORIGINAL DEFAULT CARD");
		
		driver.close();
	}
	

//	@Parameters({ "environment", "paymentgateway" })
//	@Test
//	public void testDeleteCreditCardInSMUI(String environment, String paymentgateway) throws InterruptedException {
//		initialization(environment, "cartlogin");
//		dmzloginpage = new DMZLoginPage();
//		dmzaccountpage = new DMZAccountPage();
//		dmzbillingpage = new DMZBillingPage();
//		dmzcreditcardsdetailspage = new DMZCreditCardsDetailsPage();
//
//		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
//			straccountreference = "DOM-1218";
//			dmzloginpage.setLoginDetails(straccountreference, strpassword);
//		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
//			straccountreference = "DOM-1311";
//			dmzloginpage.setLoginDetails(straccountreference, strpassword);
//		}
//
//		dmzheaderpage = dmzloginpage.clickLoginButton();
//		dmzaccountpage = dmzheaderpage.clickAccountTab();
//		dmzcreditcardsdetailspage = dmzaccountpage.clickEditCreditCardsOnFile();
//		dmzcreditcardsdetailspage.deleteCreditCard();
//
//		driver.close();
//
//	}
	
	@Parameters({ "environment", "paymentgateway" })
	@Test
	public void testRechargePrepaidUsingExistingCardInSMUI(String environment, String paymentgateway) throws InterruptedException {

		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			strAccountReference = "";
			strPassword = "";
			strCardOwner = "";
			strCardType = "";
			strCardNumber = "";
			strCardExpiryMonth = "";
			strCardExpiryYear = "";
			strCardSecurityCode = "";
			strAmount = "";

		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			strAccountReference = "NET-1218";
			strPassword = "comein22";
			strCardOwner = "Test Mastercard";
			strCardType = "MasterCard";
			strCardNumber = "5454545454545454";
			strCardExpiryMonth = "02";
			strCardExpiryYear = "2020";
			strCardSecurityCode = "123";
			strAmount = "10";			
		}
		
		initialization(environment, "customerportalurl_netregistry");
		nrgloginpage = new NRGLoginPage();
		nrgloginpage.setLoginDetails(strAccountReference, strPassword);
		nrgheaderpage = nrgloginpage.clickLoginButton();	
		nrgbillingpage = nrgheaderpage.clickBillingTab();
		nrgprepaidaccountpage = nrgbillingpage.clickEditPrepaidAccountLink();
		nrgprepaidaccountpage.clickRechargeUsingCreditCard();
		nrgprepaidaccountpage.enterRechargeAmount(strAmount);
		nrgprepaidaccountpage.clickSubmitButton();

		driver.close();
	}

	@Parameters({ "environment", "paymentgateway" })
	@Test
	public void testRechargePrepaidUsingNewCardInSMUI(String environment, String paymentgateway)
			throws InterruptedException {

		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
			strAccountReference = "";
			strPassword = "";
			strCardOwner = "";
			strCardType = "";
			strCardNumber = "";
			strCardExpiryMonth = "";
			strCardExpiryYear = "";
			strCardSecurityCode = "";
			strAmount = "";

		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			strAccountReference = "NET-1218";
			strPassword = "comein22";
			strCardOwner = "Test Mastercard";
			strCardType = "MasterCard";
			strCardNumber = "5454545454545454";
			strCardExpiryMonth = "02";
			strCardExpiryYear = "2020";
			strCardSecurityCode = "123";
			strAmount = "10";			
		}
		
		initialization(environment, "customerportalurl_netregistry");
		nrgloginpage = new NRGLoginPage();
		nrgloginpage.setLoginDetails(strAccountReference, strPassword);
		nrgheaderpage = nrgloginpage.clickLoginButton();	
		nrgbillingpage = nrgheaderpage.clickBillingTab();
		nrgprepaidaccountpage = nrgbillingpage.clickEditPrepaidAccountLink();
		nrgprepaidaccountpage.clickRechargeUsingCreditCard();
		nrgprepaidaccountpage.clickOnNewCreditCard();
		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {	
			/* Set new credit card details in Quest*/
		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
			nrgprepaidaccountpage.setNewCreditCardDetailsBT(strCardOwner, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		nrgprepaidaccountpage.enterRechargeAmount(strAmount);
		nrgprepaidaccountpage.clickSubmitButton();

		driver.close();
	}
	
	
	
	
	
	
}
