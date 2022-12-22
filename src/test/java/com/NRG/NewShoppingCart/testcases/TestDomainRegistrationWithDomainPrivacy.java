package com.NRG.NewShoppingCart.testcases;

import com.base.TestBase;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.base.Environment;
import com.netregistrynewwebsite.pages.NRGNSAboutYouPage;
import com.netregistrynewwebsite.pages.NRGNSAddServicesToYourDomainPage;
import com.netregistrynewwebsite.pages.NRGNSDomainPrivacyPage;
import com.netregistrynewwebsite.pages.NRGNSEmailAndOffice365PackagesPage;
import com.netregistrynewwebsite.pages.NRGNSRegistrantContactPage;
import com.netregistrynewwebsite.pages.NRGNSReviewAndPaymentPage;
import com.netregistrynewwebsite.pages.NRGNSSearchAddDomainsPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class TestDomainRegistrationWithDomainPrivacy extends TestBase 
{
	// Netregistry New Shopping Cart Pages
	NRGNSAboutYouPage nrgnsaboutyoupage;
	NRGNSAddServicesToYourDomainPage nrgnsaddservicestoyourdomainpage;
	NRGNSDomainPrivacyPage nrgnsdomainprivacypage;
	NRGNSEmailAndOffice365PackagesPage nrgnsemailandoffice365packagespage;
	NRGNSRegistrantContactPage nrgnsregistrantcontactpage;
	NRGNSReviewAndPaymentPage nrgnsreviewandpaymentpage;
	NRGNSSearchAddDomainsPage nrgnssearchadddomainspage;
	
	TestUtil testUtil;
	static Environment testenvironment;
	public static ExtentTest logger;

	public TestDomainRegistrationWithDomainPrivacy()  
	{
		super();	
	}
	
	// Initialization (Test Data Creation and Assignment)
	String strDomainName = null;
	String strCardOwnerName = null;
	String strCardNumber = null;
	String strCardExpiryMonth = null;
	String strCardExpiryYear = null;
	String strCardSecurityCode = null;
	String strTld = null;
	String Domaininformation ="Have a business idea and reserving a domain for the future";
	String arrTlds[] = {".com"};  
	
	/*
	 * Test case to verify domain registration with Domain Privacy for New Customer Scenario Using New card
	 */		
	@Parameters({"environment"})
	@Test 
	public void verifyDomainRegistrationWithDomainPrivacyForNewCustomerUsingNewCard(String environment) throws Exception 
	{
		strCardOwnerName = "Test New Customer New Card";
		strCardNumber = "5555555555554444";
		strCardExpiryMonth = "10";
		strCardExpiryYear = "2026";
		strCardSecurityCode = "123"; 
		
	    // Iterating over an array of tld's (having domain privacy) to be tested
	    for (String tld : arrTlds) 
	    { 
    	    strTld = tld;
            DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
			Date d = new Date();
			strDomainName = "TestDomainPrivacy" + df.format(d);
			System.out.println("Start Test: verifyDomainRegistrationWithDomainPrivacyForNewCustomerUsingNewCard for " +strTld+ " domain -STARTED");
			test.log(LogStatus.INFO, "VerifyDomainRegistrationWithDomainPrivacyForNewCustomerUsingNewCard for " +strTld+ " domain -STARTED");
			
			// Test Step 1: Navigate to domain search page of new shopping cart and place an order for a test domain
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED");
			initialization(environment, "newcart_domainsearchurl_netregistry");
			nrgnssearchadddomainspage = new NRGNSSearchAddDomainsPage();
			nrgnssearchadddomainspage.setDomainNameAndTld(strDomainName, strTld);
			nrgnssearchadddomainspage.clickSearchButton();
			nrgnssearchadddomainspage.addDomainName(strDomainName, strTld);
			test.log(LogStatus.INFO, "Navigate to domain search page -COMPLETED");
			
			//Test Step 2: Add Domain privacy for tld's
		    test.log(LogStatus.INFO, "Navigate to domain privacy page -STARTED");
			nrgnsdomainprivacypage = nrgnssearchadddomainspage.clickContinueButton();
			testStepResultVerification(NRGNSDomainPrivacyPage.checkBox);
			nrgnsemailandoffice365packagespage = nrgnsdomainprivacypage.clickContinueButton();
			test.log(LogStatus.INFO, "Navigate to domain privacy page -COMPLETED");
		
			// Test Step 3: Navigate to Email hosting page
			test.log(LogStatus.INFO, "Process the order page -STARTED");
			nrgnsaboutyoupage = nrgnsemailandoffice365packagespage.clickContinueButton();
			testStepResultVerification(NRGNSAboutYouPage.loginButton);
			test.log(LogStatus.INFO, "Process the order page -COMPLETED");

			// Test Step 4: Input default customer details
			test.log(LogStatus.INFO, "Input default customer details -STARTED");
			nrgnsaboutyoupage.setDefaultBusinessCustomerDetails();
			nrgnsregistrantcontactpage = nrgnsaboutyoupage.clickContinueButton();
			nrgnsregistrantcontactpage.clickDomainInformation(Domaininformation);
			nrgnsregistrantcontactpage.clickRegistrantContact();
			testStepResultVerification(NRGNSRegistrantContactPage.continueButton);
			 nrgnsaddservicestoyourdomainpage = nrgnsregistrantcontactpage.clickContinueButton();
			test.log(LogStatus.INFO, "Input default customer details -COMPLETED");
			
			// Test Step 6: Navigate to Services page
			test.log(LogStatus.INFO, "Process the add services page -STARTED");
			nrgnsreviewandpaymentpage = nrgnsaddservicestoyourdomainpage.clickContinueButton();
			test.log(LogStatus.INFO, "Process the add services page -COMPLETED");

			// Test Step 7: Input new credit card details and complete the order
			test.log(LogStatus.INFO, "Input new credit card details -STARTED");
			nrgnsreviewandpaymentpage.setBTFormCreditCardDetails(strCardOwnerName, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
			nrgnsreviewandpaymentpage.tickTermsAndConditions();
			testStepResultVerification(NRGNSReviewAndPaymentPage.completeOrderButton);
			nrgnsreviewandpaymentpage.clickCompleteOrder();
			test.log(LogStatus.INFO, "Input new credit card details -COMPLETED");
			
			// Test Step 8: Verify if re-captcha challenge is displayed
			test.log(LogStatus.INFO, "Verify if recaptcha challenge -STARTED");
			Assert.assertTrue(nrgnsreviewandpaymentpage.isReCaptchaChallengeDisplayed(),"Recaptcha Challenge is not displayed");
			testStepResultVerification(NRGNSReviewAndPaymentPage.recaptchaChallenge);
			test.log(LogStatus.INFO, "Verify if recaptcha challenge -COMPLETED");

			driver.quit();
			test.log(LogStatus.INFO, "verifyDomainRegistrationWithDomainPrivacyForNewCustomerUsingNewCard for " +strTld+ " domain -COMPLETED");
			System.out.println("Start Test: verifyDomainRegistrationWithDomainPrivacyForNewCustomerUsingNewCard for " +strTld+ " domain -COMPLETED");
	    } 
	}
}
