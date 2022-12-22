package com.paymentgateway.uat.melbourneit.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.commonfunctionality.MIT_CommonFunctionality;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.melbourneitwebsite.pages.MITAccountContactPage;
import com.melbourneitwebsite.pages.MITAddDomainPrivacyPage;
import com.melbourneitwebsite.pages.MITAddExtrasPage;
import com.melbourneitwebsite.pages.MITAddHostingPage;
import com.melbourneitwebsite.pages.MITBillingPage;
import com.melbourneitwebsite.pages.MITDomainSearchPage;
import com.melbourneitwebsite.pages.MITEligibilityDetailsPage;
import com.melbourneitwebsite.pages.MITHostingAndExtrasPage;
import com.melbourneitwebsite.pages.MITOnlineOrderPage;
import com.melbourneitwebsite.pages.MITOrderCompletePage;
import com.melbourneitwebsite.pages.MITRegistrantContactPage;
import com.melbourneitwebsite.pages.MITWebHostingPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class MITCustomerPortalJourney extends MIT_CommonFunctionality{

	MITOnlineOrderPage mitonlineorderpage;
	MITDomainSearchPage mitdomainsearchpage;	
	MITAddDomainPrivacyPage mitadddomainprivacypage;
	MITHostingAndExtrasPage mithostingandextraspage;
	MITWebHostingPage mitwebhostingpage;
	MITAddHostingPage mitaddhostingpage;
	MITAddExtrasPage mitaddextraspage;
	MITAccountContactPage mitaccountcontactpage;
	MITRegistrantContactPage mitregistrantcontactpage;
	MITBillingPage mitbillingpage;
	MITOrderCompletePage mitordercompletepage;
	MITEligibilityDetailsPage miteligibilitydetailspage;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;
	
	String strDomainName = null;
	String strTld = null;
	String strCardOwnerName = null;
	String strCardNumber = null;
    String strCardExpiryMonth = null;
    String strCardExpiryYear = null;
    String strCardSecurityCode = null;
    String strAccountReference = null;
   	String strWorkflowStatus = null;
   	String strCustomerAccountReference = null;
   	String strCustomerPassword = null;
   	String strEligibilityIDType = null;
   	String strEligibilityIDNumber = null;
   	String strEligibilityType = null;
   	String strCompanyName = null;
   	String strWorkflowId = null;
   	String strExistingCardNumber = null;
	
	public MITCustomerPortalJourney() {
		super();
	}

		
	@Parameters({ "environment" })
	@Test
	public void verifyDomainRegistrationOrderForNewBTCustomerUsingNewCardInCustomerPortal(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		String tld[] = {".com", ".net", ".com.au", ".co.nz"};
		String strEligibilityIDType="ABN";
		String strEligibilityIDNumber="21073716793";
		String strCompanyName="Arq Group Limited";
		String strEligibilityType="Company";
		String strCardOwnerName="Test Console Regression";
		String strCardNumber="4111111111111111";
		String strCardExpiryMonth="11";
		String strCardExpiryYear="2019";
		String strCardSecurityCode="123";
		
		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
		for(String tlds : tld){
		initialization(environment, "oldcart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		mitonlineorderpage.clearDefaultTldSelections();
		if(tlds==".co.nz") {
			mitonlineorderpage.selectNzTld();
		}
		enterDomainWithNoPrivacy(strDomainName,tlds);
		mithostingandextraspage=new MITHostingAndExtrasPage();
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		mitaccountcontactpage.setCustomerDefaultInformation();
		mitregistrantcontactpage = mitaccountcontactpage.clickContinueButton();	
		mitregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		if(tlds==".com.au") {
			 enterEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
		}
		//Test Step 2: Input credit card details and submit the order 
		selectNewCreditCardForNewCustomer(strCardOwnerName, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
        
        //Test Step 3: Verify if recaptcha challenge is dislayed 
		Assert.assertTrue(mitbillingpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
        driver.quit();
		}
	}
	
	@Parameters({ "environment" })
	@Test
	public void verifyDomainRegistrationOrderForExistingBTCustomerUsingNewCardInCustomerPortal(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		String tld[] = {".com", ".net", ".com.au", ".co.nz"};
		String accountrefrence="PAY-377";
		String password="comein22";
		String strEligibilityIDType="ABN";
		String strEligibilityIDNumber="21073716793";
		String strCompanyName="Arq Group Limited";
		String strEligibilityType="Company";
		String cardowner="Test";
		String cardnumber="4111111111111111";
		String cardexpirymonth="06";
		String cardexpiryyear="2026";
		String cardsecuritycode="123";
		String cardtype="Visa";
		
		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
		for(String tlds : tld){
		initialization(environment, "oldcart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		mitonlineorderpage.clearDefaultTldSelections();
		if(tlds==".co.nz") {
			mitonlineorderpage.selectNzTld();
		}
		mitonlineorderpage.setDomainNameAndTld(strDomainName, tlds);
		enterDomainWithNoPrivacy(strDomainName,tlds);
		mithostingandextraspage=new MITHostingAndExtrasPage();
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		setReturningCustomer(accountrefrence,password);
		mitregistrantcontactpage=new MITRegistrantContactPage();
		mitregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		if(tlds==".com.au") {
			enterEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
		}
		selectNewCreditCardForExistingCustomer(cardowner, cardtype, cardnumber,cardexpirymonth, cardexpiryyear,cardsecuritycode);
        //Test Step 3: Verify if recaptcha challenge is dislayed 
		Assert.assertTrue(mitbillingpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
        driver.quit();
		}
	}
		
	@Parameters({"environment", "tld"})	
	@Test
	public void verifyDomainandMultipleProductOrderForReturningBTCustomerUsingNewCardInCustomerPortal(String environment, String tld) throws InterruptedException{
		    
		// Initialization (Test Data Creation and Assignment)		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		strCardOwnerName = "Returning Customer";
		strCardNumber = "5555555555554444";
	    strCardExpiryMonth = "05";
	    strCardExpiryYear = "2028";
	    strCardSecurityCode = "331";
	    String accountrefrence="ARQ-04";
		String password="comein22";
		String cardowner="Test";
		String cardnumber="4111111111111111";
		String cardexpirymonth="06";
		String cardexpiryyear="2026";
		String cardsecuritycode="123";
		String cardtype="Visa";
			
		//Test Step 1: Login to customer portal and place an order for domain registration and domain privacy
		System.out.println("Start Test: verifyDomainandMultipleProductOrderForReturningCustomerInCustomerPortal");
		initialization(environment, "oldcart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		mitonlineorderpage.clearDefaultTldSelections();
		mitonlineorderpage.setDomainNameAndTld(strDomainName, tld);
		mitdomainsearchpage = mitonlineorderpage.clickNewDomainSearchButton();
		mitadddomainprivacypage = mitdomainsearchpage.clickContinueToCheckout();
		mithostingandextraspage= mitadddomainprivacypage.clickNoThanks();
		mithostingandextraspage.clickWebsiteAndHostingLink();
		mithostingandextraspage.addCPanelStarter1Month("1 Month");
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		setReturningCustomer(accountrefrence,password);
		mitregistrantcontactpage=new MITRegistrantContactPage();
		mitregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Input credit card details and submit the order 
		selectNewCreditCardForExistingCustomer(cardowner, cardtype, cardnumber,cardexpirymonth, cardexpiryyear,cardsecuritycode);
		
		//Test Step 3: Verify if recaptcha challenge is dislayed 
		Assert.assertTrue(mitbillingpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
		driver.quit();
		
	}
}
