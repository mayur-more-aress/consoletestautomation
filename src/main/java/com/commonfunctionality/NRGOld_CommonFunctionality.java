package com.commonfunctionality;
import java.util.ArrayList;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.base.TestBase;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.netregistryoldwebsite.pages.NRGAccountContactPage;
import com.netregistryoldwebsite.pages.NRGAddDomainPrivacyPage;
import com.netregistryoldwebsite.pages.NRGBillingPage;
import com.netregistryoldwebsite.pages.NRGDomainSearchPage;
import com.netregistryoldwebsite.pages.NRGEligibilityDetailsPage;
import com.netregistryoldwebsite.pages.NRGHeaderPage;
import com.netregistryoldwebsite.pages.NRGHostingAndExtrasPage;
import com.netregistryoldwebsite.pages.NRGLoginPage;
import com.netregistryoldwebsite.pages.NRGOnlineOrderPage;
import com.netregistryoldwebsite.pages.NRGOrderCompletePage;
import com.netregistryoldwebsite.pages.NRGRegistrantContactPage;
import com.nrgcustomerportal.pages.NRGCCancelServicesPage;
import com.relevantcodes.extentreports.LogStatus;

public class NRGOld_CommonFunctionality extends TestBase {

	NRGEligibilityDetailsPage nrgeligibilitydetailspage;
	NRGBillingPage nrgbillingpage;
	NRGOrderCompletePage nrgordercompletepage;
	NRGOnlineOrderPage nrgonlineorderpage;
	NRGDomainSearchPage nrgdomainsearchpage;	
	NRGAddDomainPrivacyPage nrgadddomainprivacypage;
	NRGHostingAndExtrasPage nrghostingandextraspage;
	NRGAccountContactPage nrgaccountcontactpage;
	NRGRegistrantContactPage nrgregistrantcontactpage;
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	NRGLoginPage nrgloginpage;
	NRGHeaderPage nrgheaderpage;
	NRGCCancelServicesPage nrgccancelservicespage;
	public static String strWorkflowId = null;
	public static String strAccountReference = null;
   public static ArrayList<String> lstWorkflowId = null;
   String strResponseDomain;
	
	// Initializing Page Objects
	public NRGOld_CommonFunctionality() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	
	/* To enter the au eligibility details*/
   public void setEligibilityDetails(String strEligibilityIDType,String strEligibilityIDNumber, String strCompanyName,String strEligibilityType) throws InterruptedException
   {
	   nrgeligibilitydetailspage = new NRGEligibilityDetailsPage();
		test.log(LogStatus.INFO, "Entering eligibility details ");
		nrgeligibilitydetailspage.setEligibilityDetails(strEligibilityIDType, strEligibilityIDNumber, strCompanyName, strEligibilityType);
		test.log(LogStatus.INFO, "Tick on terms and conditions ");
		nrgeligibilitydetailspage.tickCertifyDomainHasCloseAndSubstantialConnection();
		nrgeligibilitydetailspage.tickTermsAndConditions();
		test.log(LogStatus.INFO, "Click on continue button on eligibility details page ");
		nrgeligibilitydetailspage.clickContinueButton();
    
}
   /* To select existing credit card*/
   public void selectExistingCard(String strExistingCardNumber )
   {
	   nrgbillingpage=new NRGBillingPage();
	   test.log(LogStatus.INFO, "Entering credit card information ");
	   nrgbillingpage.selectExistingCreditCardOption(strExistingCardNumber);
		test.log(LogStatus.INFO, "Tick on terms and conditions ");
		nrgbillingpage.tickTermsAndConditions();
       test.log(LogStatus.INFO, "Click on continue button on billing page ");
       nrgordercompletepage = nrgbillingpage.clickContinueButton();
   }
   
   /* To select prepaid credit card*/
   public void selectPrepaidCredit(String Prepaidcredit)
   {
	   nrgbillingpage=new NRGBillingPage();
	   test.log(LogStatus.INFO, "Entering credit card information ");
	   nrgbillingpage.selectExistingCreditCardOption(Prepaidcredit);
		test.log(LogStatus.INFO, "Tick on terms and conditions ");
		nrgbillingpage.tickTermsAndConditions();
       test.log(LogStatus.INFO, "Click on continue button on billing page ");
       nrgordercompletepage = nrgbillingpage.clickContinueButton();
   }
   
   /* To create new credit card*/
   public void createNewCreditCard(String cardowner, String cardtype, String cardnumber,
			String cardexpirymonth, String cardexpiryyear, String cardsecuritycode)
   {
	   nrgbillingpage=new NRGBillingPage();
	   test.log(LogStatus.INFO, "Select the new credit card option and Entering credit card information ");
	   nrgbillingpage.selectNewCreditCardOption();
	   nrgbillingpage.setQuestFormCreditCardDetails(cardowner,cardtype,cardnumber,cardexpirymonth,cardexpiryyear,cardsecuritycode);
		test.log(LogStatus.INFO, "Tick on terms and conditions ");
		nrgbillingpage.tickTermsAndConditions();
       test.log(LogStatus.INFO, "Click on continue button on billing page ");
       nrgordercompletepage = nrgbillingpage.clickContinueButton();
   }
   
   public void createNewCreditCardDetailsforNewCustomer(String cardowner,String cardnumber,String cardexpirymonth, String cardexpiryyear, String cardsecuritycode) throws InterruptedException
   {
	   nrgbillingpage=new NRGBillingPage();
	   test.log(LogStatus.INFO, "Select the new credit card option and Entering credit card information ");
	   nrgbillingpage.setBTFormCreditCardDetails(cardowner,cardnumber,cardexpirymonth,cardexpiryyear,cardsecuritycode);
	   nrgbillingpage.tickTermsAndConditions();
	   nrgbillingpage.clickContinueButton();
   }
   
   public void createNewCreditCardDetailsforExistingCustomer(String cardowner,String cardnumber,String cardexpirymonth, String cardexpiryyear, String cardsecuritycode) throws InterruptedException
   {
	   nrgbillingpage=new NRGBillingPage();
	   test.log(LogStatus.INFO, "Select the new credit card option and Entering credit card information ");
	   nrgbillingpage.selectNewCreditCardOption();
	   nrgbillingpage.setBTFormCreditCardDetails(cardowner,cardnumber,cardexpirymonth,cardexpiryyear,cardsecuritycode);
	   nrgbillingpage.tickTermsAndConditions();
	   nrgbillingpage.clickContinueButton();
   }
   
   public void enterDomainwithNoPrivacy(String strDomainName, String tlds) throws InterruptedException
   {
	   nrgonlineorderpage=new NRGOnlineOrderPage();
	   test.log(LogStatus.INFO, "Enter domain name and select Tld ");
		nrgonlineorderpage.setDomainNameAndTld(strDomainName, tlds);
		test.log(LogStatus.INFO, "Clicking on search button ");
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		test.log(LogStatus.INFO, "Clicking on continue to checkout button ");
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
   }
   
   public void setReturningCustomer(String username, String password)
   {
	   nrgaccountcontactpage=new NRGAccountContactPage();
	   test.log(LogStatus.INFO, "Enter default customer information on account contact page");
		nrgaccountcontactpage.setReturningCustomerContacts(username,password);
		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
   }
   
   public void enterNewCustomerDetails() {
	   nrgaccountcontactpage=new NRGAccountContactPage();
	   nrgaccountcontactpage.setCustomerDefaultInformation();
		nrgregistrantcontactpage = nrgaccountcontactpage.clickContinueButton();
	   
   }
   
   public void setDefaultCustomer()
   {
	   nrgaccountcontactpage=new NRGAccountContactPage();
	   nrgaccountcontactpage.setCustomerDefaultInformation();
	   test.log(LogStatus.INFO, "Clicking on continue button on account contact page ");
		nrgregistrantcontactpage = nrgaccountcontactpage.clickContinueButton();	
   }
   
   public void fetchRefrenceAndWorkflowId() throws InterruptedException
   {
	    nrgordercompletepage=new NRGOrderCompletePage();
		strWorkflowId = nrgordercompletepage.getSingleReferenceID();
		strAccountReference = nrgordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
   }
   
   public void loginToConsoleAdmin(String environment) throws InterruptedException
   {
	   initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
   }
   
   public void fetchMultipleRefrenceAndWorkflowId() throws InterruptedException
   {
	   nrgordercompletepage=new NRGOrderCompletePage();
	   lstWorkflowId = nrgordercompletepage.getMultipleReferenceID();
		strAccountReference = nrgordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);
		for(String refID : lstWorkflowId)
		{
			System.out.println("Reference ID:" + refID);
		}
   }
   
   public void registerbackorderdomain(String strDomainName, String strTld) throws Exception
   {
	   nrgonlineorderpage=new NRGOnlineOrderPage();
	   test.log(LogStatus.INFO, "Enter domain name and select Tld ");
		nrgonlineorderpage.setDomainNameAndTld(strDomainName, strTld);
		test.log(LogStatus.INFO, "Clicking on search button ");
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		test.log(LogStatus.INFO, "Clicking on continue to checkout button ");
		nrgdomainsearchpage.checkStatusAndAddDomain(strDomainName, strTld);
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
   }
   
   public void loginToCustomerPortal(String accountReference,String strPassword) throws InterruptedException
   {
	   test.log(LogStatus.INFO, "Login to Customer portal - STARTED");
		nrgloginpage = new NRGLoginPage();
		nrgloginpage.setLoginDetails(accountReference, strPassword);
		nrgheaderpage = nrgloginpage.clickLoginButton();
		test.log(LogStatus.INFO, "Login to Customer portal - COMPLETED");
   }
   
   public void getWorkflowId() throws InterruptedException
   {
	   nrgccancelservicespage=new  NRGCCancelServicesPage() ;
	    strResponseDomain=nrgccancelservicespage.getSingleReferenceID();
		System.out.println("Cancel services message:"+strResponseDomain);
		Assert.assertTrue(strResponseDomain.contains("Your reference number is"));
		strWorkflowId=nrgccancelservicespage.getWorkflowId(strResponseDomain);
		System.out.println("Cancel service workflow Id:"+strWorkflowId);
   }
}