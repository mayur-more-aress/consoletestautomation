package com.paymentgateway.uat.domainz.testcases;


import java.awt.AWTException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.domainzwebsite.pages.DMZAccountContactPage;
import com.domainzwebsite.pages.DMZAddDomainPrivacyPage;
import com.domainzwebsite.pages.DMZAddExtrasPage;
import com.domainzwebsite.pages.DMZAddHostingPage;
import com.domainzwebsite.pages.DMZBillingPage;
import com.domainzwebsite.pages.DMZDomainSearchPage;
import com.domainzwebsite.pages.DMZEligibilityDetailsPage;
import com.domainzwebsite.pages.DMZHeaderPage;
import com.domainzwebsite.pages.DMZHostingAndExtrasPage;
import com.domainzwebsite.pages.DMZLoginPage;
import com.domainzwebsite.pages.DMZOnlineOrderPage;
import com.domainzwebsite.pages.DMZOrderCompletePage;
import com.domainzwebsite.pages.DMZRegistrantContactPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class DMZCustomerPortalJourney extends TestBase{

	DMZOnlineOrderPage dmzonlineorderpage;
	DMZDomainSearchPage dmzdomainsearchpage;	
	DMZAddDomainPrivacyPage dmzadddomainprivacypage;
	DMZHostingAndExtrasPage dmzhostingandextraspage;
	DMZAddHostingPage dmzaddhostingpage;
	DMZAddExtrasPage dmzaddextraspage;
	DMZAccountContactPage dmzaccountcontactpage;
	DMZRegistrantContactPage dmzregistrantcontactpage;
	DMZBillingPage dmzbillingpage;
	DMZOrderCompletePage dmzordercompletepage;
	DMZEligibilityDetailsPage dmzeligibilitydetailspage;
	DMZLoginPage dmzloginpage;
	DMZHeaderPage dmzheaderpage;
	
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

	public DMZCustomerPortalJourney() {
		super();
	}

		
	@Parameters({ "environment"})
	@Test
	public void verifyDomainRegistrationOrderForNewBTCustomerUsingNewCardInCustomerPortal(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String tld[] = {".com", ".net", ".com.au"};
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
			
		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
		
		for(String tlds : tld){
			initialization(environment, "oldcart_domainsearchurl_domainz");
			dmzonlineorderpage = new DMZOnlineOrderPage();
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED "+tlds);
			test.log(LogStatus.INFO, "Clearing default tld selections");
			dmzonlineorderpage.clearDefaultTldSelections();
			test.log(LogStatus.INFO, "Enter domain name and select Tld "+tlds);
			dmzonlineorderpage.setDomainNameAndTld(strDomainName, tlds);
			test.log(LogStatus.INFO, "Clicking on search button "+tlds);
			dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
			test.log(LogStatus.INFO, "Clicking on continue to checkout button "+tlds);
			dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
			dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
			test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
			dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
			test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
			dmzaccountcontactpage.setCustomerDefaultInformation();
			test.log(LogStatus.INFO, "Clicking on continue button on account contact page "+tlds);
			dmzregistrantcontactpage = dmzaccountcontactpage.clickContinueButton();	
			test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
			dmzregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
			dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
			if(tlds==".com.au") {
				dmzeligibilitydetailspage = new DMZEligibilityDetailsPage();
				test.log(LogStatus.INFO, "Entering eligibility details "+tlds);
				dmzeligibilitydetailspage.setEligibilityDetails("ABN", "21073716793", "Arq Group Limited", "Company");
				test.log(LogStatus.INFO, "Tick on terms and conditions "+tlds);
				dmzeligibilitydetailspage.tickCertifyDomainHasCloseAndSubstantialConnection();
				dmzeligibilitydetailspage.tickTermsAndConditions();
				test.log(LogStatus.INFO, "Click on continue button on eligibility details page "+tlds);
				dmzeligibilitydetailspage.clickContinueButton();
			}
			//Test Step 2: Input credit card details and submit the order 
			test.log(LogStatus.INFO, "Entering credit card information "+tlds);
			dmzbillingpage.setBTFormCreditCardDetails("Test Console Regression", "4111111111111111", "11", "2022", "123");
			test.log(LogStatus.INFO, "Tick on terms and conditions "+tlds);
			dmzbillingpage.tickTermsAndConditions();
	        test.log(LogStatus.INFO, "Click on continue button on billing page "+tlds);
	        dmzbillingpage.clickContinueButton();
	        
	        //Test Step 3: Verify if recaptcha challenge is dislayed 
	        test.log(LogStatus.INFO, "Verify if recaptcha is displayed on click of continue button "+tlds);
			Assert.assertTrue(dmzbillingpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
			test.log(LogStatus.INFO, "Verification of doamain registration - COMPLETED "+tlds);
	        driver.quit();
		}
	}
	
	
	@Parameters({ "environment"})
	@Test
	public void verifyDomainRegistrationOrderForExistingBTCustomerUsingNewCardInCustomerPortal(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String tld[] = {".com", ".net", ".com.au"};
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
			
		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
		
		for(String tlds : tld){
			initialization(environment, "oldcart_domainsearchurl_domainz");
			dmzonlineorderpage = new DMZOnlineOrderPage();
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED "+tlds);
			test.log(LogStatus.INFO, "Clearing default tld selections");
			dmzonlineorderpage.clearDefaultTldSelections();
			test.log(LogStatus.INFO, "Enter domain name and select Tld "+tlds);
			dmzonlineorderpage.setDomainNameAndTld(strDomainName, tlds);
			test.log(LogStatus.INFO, "Clicking on search button "+tlds);
			dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
			test.log(LogStatus.INFO, "Clicking on continue to checkout button "+tlds);
			dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
			dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
			test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
			dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
			test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
			dmzaccountcontactpage.setReturningCustomerContacts("PAY-375", "comein22");
			dmzregistrantcontactpage = dmzaccountcontactpage.clickLoginButton();
			test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
			dmzregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
			dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
			if(tlds==".com.au") {
				dmzeligibilitydetailspage = new DMZEligibilityDetailsPage();
				test.log(LogStatus.INFO, "Entering eligibility details "+tlds);
				dmzeligibilitydetailspage.setEligibilityDetails("ABN", "21073716793", "Arq Group Limited", "Company");
				test.log(LogStatus.INFO, "Tick on terms and conditions "+tlds);
				dmzeligibilitydetailspage.tickCertifyDomainHasCloseAndSubstantialConnection();
				dmzeligibilitydetailspage.tickTermsAndConditions();
				test.log(LogStatus.INFO, "Click on continue button on eligibility details page "+tlds);
				dmzeligibilitydetailspage.clickContinueButton();
			}
			//Test Step 2: Input credit card details and submit the order 
			test.log(LogStatus.INFO, "Entering credit card information "+tlds);
			dmzbillingpage.selectNewCreditCardOption();
			dmzbillingpage.setBTFormCreditCardDetails("Test Console Regression", "4111111111111111", "11", "2022", "123");
			test.log(LogStatus.INFO, "Tick on terms and conditions "+tlds);
			dmzbillingpage.tickTermsAndConditions();
	        test.log(LogStatus.INFO, "Click on continue button on billing page "+tlds);
	        dmzbillingpage.clickContinueButton();
	        
	        //Test Step 3: Verify if recaptcha challenge is dislayed 
	        test.log(LogStatus.INFO, "Verify if recaptcha is displayed on click of continue button "+tlds);
			Assert.assertTrue(dmzbillingpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
			test.log(LogStatus.INFO, "Verification of doamain registration - COMPLETED "+tlds);
	        driver.quit();
		}
	}
	
	@Parameters({ "environment"})
	@Test
	public void verifyDomainRegistrationAndProductForExistingBTCustomerUsingExistingCard(String environment) throws InterruptedException, AWTException{
	
		// Initialization (Test Data Creation and Assignment)
		
		String strDomainName = null;
		String strWorkflowId = null;
		String strAccountReference = null;
		String strWorkflowStatus = null;
		//String tld[] = {".com", ".net", ".com.au", ".co.nz"};
		String tld[] = {".com"};
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
			
		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
			
		for(String tlds : tld){
			initialization(environment, "oldcart_domainsearchurl_domainz");
			dmzonlineorderpage = new DMZOnlineOrderPage();
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED "+tlds);
			test.log(LogStatus.INFO, "Clearing default tld selections");
			dmzonlineorderpage.clearDefaultTldSelections();
			if(tlds==".co.nz") {
				dmzonlineorderpage.selectNzTld();
			}
			test.log(LogStatus.INFO, "Enter domain name and select Tld "+tlds);
			dmzonlineorderpage.setDomainNameAndTld(strDomainName, tlds);
			test.log(LogStatus.INFO, "Clicking on search button "+tlds);
			dmzdomainsearchpage = dmzonlineorderpage.clickNewDomainSearchButton();
			test.log(LogStatus.INFO, "Clicking on continue to checkout button "+tlds);
			dmzadddomainprivacypage = dmzdomainsearchpage.clickContinueToCheckout();
			dmzhostingandextraspage= dmzadddomainprivacypage.clickNoThanks();
			test.log(LogStatus.INFO, "Select monthly Product"+tlds);
			dmzhostingandextraspage.clickHostingButton();
			//dmzhostingandextraspage.selectOffice365("o365EmailEssentials", "1 Month");
			test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
			dmzaccountcontactpage= dmzhostingandextraspage.clickContinueButton();
			test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
			dmzaccountcontactpage.setReturningCustomerContacts("PAY-375", "comein22");
			dmzregistrantcontactpage = dmzaccountcontactpage.clickLoginButton();
			test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
			dmzregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
			dmzbillingpage = dmzregistrantcontactpage.clickContinueButton();
			if(tlds==".com.au") {
				dmzeligibilitydetailspage = new DMZEligibilityDetailsPage();
				test.log(LogStatus.INFO, "Entering eligibility details "+tlds);
				dmzeligibilitydetailspage.setEligibilityDetails("ABN", "21073716793", "Arq Group Limited", "Company");
				test.log(LogStatus.INFO, "Tick on terms and conditions "+tlds);
				dmzeligibilitydetailspage.tickCertifyDomainHasCloseAndSubstantialConnection();
				dmzeligibilitydetailspage.tickTermsAndConditions();
				test.log(LogStatus.INFO, "Click on continue button on eligibility details page "+tlds);
				dmzeligibilitydetailspage.clickContinueButton();
			}
			//Test Step 2: Input credit card details and submit the order 
			test.log(LogStatus.INFO, "Entering credit card information "+tlds);
			dmzbillingpage.selectExistingCreditCardOption("Number: 4111********1111 Expiry: 02/2021");
			test.log(LogStatus.INFO, "Tick on terms and conditions "+tlds);
			dmzbillingpage.tickTermsAndConditions();
	        test.log(LogStatus.INFO, "Click on continue button on billing page "+tlds);
	        dmzordercompletepage = dmzbillingpage.clickContinueButton();
	        
	        //Test Step 3: Verify if recaptcha challenge is dislayed 
	      //  Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
			strWorkflowId = dmzordercompletepage.getSingleReferenceID();
			strAccountReference = dmzordercompletepage.getAccountReferenceID();
			System.out.println("Account Reference:" + strAccountReference);	
			System.out.println("Reference ID[0]:" + strWorkflowId);	
			driver.quit();
			
			initialization(environment, "consoleadmin");
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.setDefaultLoginDetails(environment);
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			String arr[] = caworkflowadminpage.processWF(strWorkflowId, tlds);
			caworkflowadminpage.processProductSetup2O365();
			// Test Step 6: Verify if domain registration workflow is completed
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caheaderpage.searchWorkflow(strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
//			String invoiceNumber = arr[0].toString();
//			String invoiceAmount = arr[1].toString();
//			System.out.println(arr[0]);
//			System.out.println(arr[1]);
			driver.quit();
		}
	}
}
