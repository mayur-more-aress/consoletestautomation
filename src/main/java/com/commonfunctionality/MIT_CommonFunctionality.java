package com.commonfunctionality;
import java.util.ArrayList;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.base.TestBase;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.melbourneitwebsite.pages.MITAccountContactPage;
import com.melbourneitwebsite.pages.MITAddDomainPrivacyPage;
import com.melbourneitwebsite.pages.MITBillingPage;
import com.melbourneitwebsite.pages.MITDomainSearchPage;
import com.melbourneitwebsite.pages.MITEligibilityDetailsPage;
import com.melbourneitwebsite.pages.MITHeaderPage;
import com.melbourneitwebsite.pages.MITHostingAndExtrasPage;
import com.melbourneitwebsite.pages.MITLoginPage;
import com.melbourneitwebsite.pages.MITOnlineOrderPage;
import com.melbourneitwebsite.pages.MITOrderCompletePage;
import com.melbourneitwebsite.pages.MITRegistrantContactPage;
import com.mitcustomerportal.pages.MITCCancelServicesPage;
import com.relevantcodes.extentreports.LogStatus;

public class MIT_CommonFunctionality extends TestBase {

	MITLoginPage mitloginpage;
	MITHeaderPage mitheaderpage;
	MITCCancelServicesPage mitccancelservicespage;
	MITEligibilityDetailsPage miteligibilitydetailspage;	
	MITBillingPage mitbillingpage;
	MITOrderCompletePage mitordercompletepage;
	MITAccountContactPage mitaccountcontactpage;
	MITRegistrantContactPage mitregistrantcontactpage;
	MITOnlineOrderPage mitonlineorderpage;
	MITDomainSearchPage mitdomainsearchpage;	
	MITAddDomainPrivacyPage mitadddomainprivacypage;
	MITHostingAndExtrasPage mithostingandextraspage;
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	public static String strWorkflowId = null;
	public static String strAccountReference = null;
   public static ArrayList<String> lstWorkflowId = null;
   String strResponseDomain;
	
	// Initializing Page Objects
	public MIT_CommonFunctionality() {
		PageFactory.initElements(driver, this);
	}

	// Methods
   
   public void loginToConsoleAdmin(String environment) throws InterruptedException
   {
	   initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
   }
   
   public void loginToCustomerPortal(String accountReference,String strPassword) throws InterruptedException
   {
	   test.log(LogStatus.INFO, "Login to Customer portal - STARTED");
		mitloginpage = new MITLoginPage();
		mitloginpage.setLoginDetails(accountReference, strPassword);
		mitheaderpage = mitloginpage.clickLoginButton();
		test.log(LogStatus.INFO, "Login to Customer portal - COMPLETED");
   }
   
   public void getWorkflowId() throws InterruptedException
   {
	   mitccancelservicespage=new MITCCancelServicesPage();
	   strResponseDomain=mitccancelservicespage.getSingleReferenceID();
		System.out.println("Cancel services message:"+strResponseDomain);
		Assert.assertTrue(strResponseDomain.contains("Your reference number is"));
		strWorkflowId=mitccancelservicespage.getWorkflowId(strResponseDomain);
		System.out.println("Cancel service workflow Id:"+strWorkflowId);
   }
   
   public void logintoCustomerPortal(String accountReference,String strPassword) throws InterruptedException
   {
	   test.log(LogStatus.INFO, "Login to Customer portal - STARTED");
		mitloginpage = new MITLoginPage();
		mitloginpage.setLoginDetails(accountReference, strPassword);
		mitheaderpage = mitloginpage.clickLoginButton();
		test.log(LogStatus.INFO, "Login to Reseller portal - COMPLETED");
   }
   
   public void enterNewEligibiltyDetails(String strEligibilityIDType,String strEligibilityIDNumber, String strCompanyName,String strEligibilityType) throws InterruptedException
   {
	   miteligibilitydetailspage=new MITEligibilityDetailsPage();
	   test.log(LogStatus.INFO, "Entering eligibility details ");
		miteligibilitydetailspage.setEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
		test.log(LogStatus.INFO, "Tick on terms and conditions ");
		miteligibilitydetailspage.tickCertifyDomainHasCloseAndSubstantialConnection();
		miteligibilitydetailspage.tickTermsAndConditions();
		test.log(LogStatus.INFO, "Click on continue button on eligibility details page");
		miteligibilitydetailspage.clickContinueButton();
   }
   
   public void enterNewCardDetails(String cardName,String cardType,String cardNumber, String expiryDate,String expiryMonth,String cvvNumber)
   {
	   mitbillingpage=new MITBillingPage();
	   test.log(LogStatus.INFO, "Select the new credit card option and Entering credit card information ");
		mitbillingpage.selectNewCreditCardOption();
		mitbillingpage.setQuestFormCreditCardDetails(cardName,cardType,cardNumber,expiryDate,expiryMonth,cvvNumber);
		test.log(LogStatus.INFO, "Tick on terms and conditions ");
		mitbillingpage.tickTermsAndConditions();
      test.log(LogStatus.INFO, "Click on continue button on billing page ");
      mitordercompletepage = mitbillingpage.clickContinueButton();
   }
   
   public void fetchWorkflowId() throws InterruptedException
   {
	   mitordercompletepage=new MITOrderCompletePage();
	   strWorkflowId = mitordercompletepage.getSingleReferenceID();
		strAccountReference = mitordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);
		System.out.println("Reference ID[0]:" + strWorkflowId);	
   }
   
   public void enterExistingCardDetails(String existingcardnumber)
   {
	   mitbillingpage=new MITBillingPage();
	   test.log(LogStatus.INFO, "Entering credit card information ");
		mitbillingpage.selectExistingCreditCardOption(existingcardnumber);
		test.log(LogStatus.INFO, "Tick on terms and conditions ");
		mitbillingpage.tickTermsAndConditions();
      test.log(LogStatus.INFO, "Click on continue button on billing page ");
      mitordercompletepage = mitbillingpage.clickContinueButton();
   }
   
   public void enterEligibilityDetails(String strEligibilityIDType,String strEligibilityIDNumber, String strCompanyName,String strEligibilityType) throws InterruptedException
   {
	   miteligibilitydetailspage=new MITEligibilityDetailsPage();
	   test.log(LogStatus.INFO, "Entering eligibility details ");
		miteligibilitydetailspage.setEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
		test.log(LogStatus.INFO, "Tick on terms and conditions ");
		miteligibilitydetailspage.tickCertifyDomainHasCloseAndSubstantialConnection();
		miteligibilitydetailspage.tickTermsAndConditions();
		test.log(LogStatus.INFO, "Click on continue button on eligibility details page");
		miteligibilitydetailspage.clickContinueButton();
   }
   
   public void setReturningCustomer(String accountrefrence,String password) throws InterruptedException
   {
	   mitaccountcontactpage=new MITAccountContactPage();
	   mitaccountcontactpage.setReturningCustomerContacts(accountrefrence,password);
		mitregistrantcontactpage = mitaccountcontactpage.clickLoginButton();
   }
   
   public void enterNewCustomerDetails() {
	   mitaccountcontactpage=new MITAccountContactPage();
	   mitaccountcontactpage.setCustomerDefaultInformation();
	   mitregistrantcontactpage = mitaccountcontactpage.clickContinueButton();
	   
   }
   public void enterDomainWithNoPrivacy(String strDomainName, String tlds) throws InterruptedException
   {
	   mitonlineorderpage=new MITOnlineOrderPage();
	   test.log(LogStatus.INFO, "Enter domain name and select Tld "+tlds);
		mitonlineorderpage.setDomainNameAndTld(strDomainName, tlds);
		test.log(LogStatus.INFO, "Clicking on search button "+tlds);
		mitdomainsearchpage = mitonlineorderpage.clickNewDomainSearchButton();
		test.log(LogStatus.INFO, "Clicking on continue to checkout button "+tlds);
		mitadddomainprivacypage = mitdomainsearchpage.clickContinueToCheckout();
		mithostingandextraspage= mitadddomainprivacypage.clickNoThanks();
   }
   
   public void selectNewCreditCardForNewCustomer(String strCardOwnerName,String strCardNumber,String strCardExpiryMonth,String strCardExpiryYear,String strCardSecurityCode) throws InterruptedException
   {
	   mitbillingpage=new MITBillingPage();
	    mitbillingpage.setBTFormCreditCardDetails(strCardOwnerName, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
	    mitbillingpage.tickTermsAndConditions();
	    mitbillingpage.clickContinueButton();
   }
   
   public void selectNewCreditCardForExistingCustomer(String cardowner,String cardtype,String cardnumber,String cardexpirymonth,String cardexpiryyear,String cardsecuritycode) throws InterruptedException
   {
	   mitbillingpage=new MITBillingPage();
	   mitbillingpage.selectNewCreditCardOption();
	    mitbillingpage.setQuestFormCreditCardDetails(cardowner, cardtype, cardnumber,cardexpirymonth, cardexpiryyear,cardsecuritycode);
	    mitbillingpage.tickTermsAndConditions();
	    mitbillingpage.clickContinueButton();
   }
   
   public void fetchMultipleRefrenceId() throws InterruptedException
   {
	   lstWorkflowId = mitordercompletepage.getMultipleReferenceID();
		test.log(LogStatus.INFO, "Verify if the order is completed  -COMPLETED");
		strAccountReference = mitordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);
		for(String refID : lstWorkflowId)
		{
			System.out.println("Reference ID:" + refID);
		}
   }
}