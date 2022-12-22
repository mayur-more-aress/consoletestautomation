package com.NRG.NewShoppingCart.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.Environment;
import com.base.TestBase;
import com.netregistrynewwebsite.pages.NRGNSAboutYouPage;
import com.netregistrynewwebsite.pages.NRGNSAddServicesToYourDomainPage;
import com.netregistrynewwebsite.pages.NRGNSDomainPrivacyPage;
import com.netregistrynewwebsite.pages.NRGNSEligibilityDetailsPage;
import com.netregistrynewwebsite.pages.NRGNSEmailAndOffice365PackagesPage;
import com.netregistrynewwebsite.pages.NRGNSOffice365LicenseQuantityPage;
import com.netregistrynewwebsite.pages.NRGNSOrderCompletePage;
import com.netregistrynewwebsite.pages.NRGNSRegistrantContactPage;
import com.netregistrynewwebsite.pages.NRGNSReviewAndPaymentPage;
import com.netregistrynewwebsite.pages.NRGNSSearchAddDomainsPage;
import com.netregistrynewwebsite.pages.NRGNSSearchFieldPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class TestNewCustomerScenarioUsingNewCard extends TestBase {

	// Netregistry New Shopping Cart Pages
	NRGNSAboutYouPage nrgnsaboutyoupage;
	NRGNSAddServicesToYourDomainPage nrgnsaddservicestoyourdomainpage;
	NRGNSDomainPrivacyPage nrgnsdomainprivacypage;
	NRGNSEmailAndOffice365PackagesPage nrgnsemailandoffice365packagespage;
	NRGNSOffice365LicenseQuantityPage nrgnsoffice365licensequantitypage;
	NRGNSRegistrantContactPage nrgnsregistrantcontactpage;
	NRGNSReviewAndPaymentPage nrgnsreviewandpaymentpage;
	NRGNSSearchAddDomainsPage nrgnssearchadddomainspage;
	NRGNSSearchFieldPage nrgnssearchfieldpage;
	NRGNSOrderCompletePage nrgnsordercompletepage;
	NRGNSEligibilityDetailsPage nrgnseligibilitydetailspage;

	TestUtil testUtil;
	static Environment testenvironment;
	public static ExtentTest logger;

	public TestNewCustomerScenarioUsingNewCard() {
		super();
	}

	@Parameters({ "environment" })
	@Test
	public void testNewCustomerScenarioUsingNewCard(String environment) throws Exception {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld[] = {".com"};
		String strCardOwnerName = "Test New Customer New Card";
		String strCardNumber = "5555555555554444";
		String strCardExpiryMonth = "02";
		String strCardExpiryYear = "2024";
		String strCardSecurityCode = "123";
		String Domaininformation ="Have a business idea and reserving a domain for the future";
	
		//for(int i=0;i<strTld.length;i++) 
		for(String tld: strTld){
			// Generate test domain name
			DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
			Date d = new Date();
			strDomainName = "TestNewCartRegression" + df.format(d);

			System.out.println("Start Test: testNewCustomerScenarioUsingNewCard "+tld);
			// Test Step 1: Navigate to domain search page of new shopping cart and place an
			// order for a test domain
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED");
			initialization(environment, "newcart_domainsearchurl_netregistry");
			nrgnssearchadddomainspage = new NRGNSSearchAddDomainsPage();
			nrgnssearchadddomainspage.setDomainNameAndTld(strDomainName, tld);
			nrgnssearchadddomainspage.clickSearchButton();
			nrgnssearchadddomainspage.addDomainName(strDomainName, tld);
			nrgnsdomainprivacypage = nrgnssearchadddomainspage.clickContinueButton();
			if(tld==".com"||tld==".net") {
			testStepResultVerification(NRGNSDomainPrivacyPage.checkBox);
			test.log(LogStatus.INFO, "Navigate to domain search page -COMPLETED");
			
			// Test Step 2: Process the order without any product included
			test.log(LogStatus.INFO, "Process the order page -STARTED");
			nrgnsdomainprivacypage.clickCheckBox();
			nrgnsemailandoffice365packagespage = nrgnsdomainprivacypage.clickContinueButton();
			}
			nrgnsemailandoffice365packagespage = new NRGNSEmailAndOffice365PackagesPage();
			nrgnsaboutyoupage = nrgnsemailandoffice365packagespage.clickContinueButton();
			testStepResultVerification(NRGNSAboutYouPage.loginButton);
			test.log(LogStatus.INFO, "Process the order page -COMPLETED");

			// Test Step 3: Input default customer details
			test.log(LogStatus.INFO, "Input default customer details -STARTED");
			nrgnsaboutyoupage.setDefaultBusinessCustomerDetails();
			nrgnsregistrantcontactpage = nrgnsaboutyoupage.clickContinueButton();
			nrgnsregistrantcontactpage.clickDomainInformation(Domaininformation);
			nrgnsregistrantcontactpage.clickRegistrantContact();
			testStepResultVerification(NRGNSRegistrantContactPage.continueButton);
			nrgnsaddservicestoyourdomainpage = nrgnsregistrantcontactpage.clickContinueButton();
			if(tld.contains(".com.au")) {
				nrgnseligibilitydetailspage = new NRGNSEligibilityDetailsPage();
				nrgnseligibilitydetailspage.setEligibilityDetails("ABN", "21073716793", "Company");
				nrgnseligibilitydetailspage.tickCertifyDomainHasCloseAndSubstantialConnection();
				nrgnseligibilitydetailspage.tickTermsAndConditions();
				nrgnseligibilitydetailspage.clickContinueButton();
			}
			
			nrgnsreviewandpaymentpage = nrgnsaddservicestoyourdomainpage.clickContinueButton();
			test.log(LogStatus.INFO, "Input default customer details -COMPLETED");

			// Test Step 4: Input customer credit card details and complete the order
			test.log(LogStatus.INFO, "Input customer credit card details  -STARTED");
			nrgnsreviewandpaymentpage.setBTFormCreditCardDetails(strCardOwnerName, strCardNumber, strCardExpiryMonth,
					strCardExpiryYear, strCardSecurityCode);
			nrgnsreviewandpaymentpage.tickTermsAndConditions();
			testStepResultVerification(NRGNSReviewAndPaymentPage.completeOrderButton);
			test.log(LogStatus.INFO, "Input customer credit card details -COMPLETED");

			// Test Step 5: Verify if recaptcha challenge is dislayed
			test.log(LogStatus.INFO, "Verify if recaptcha challenge is dislayed  -STARTED");
			nrgnsreviewandpaymentpage.clickCompleteOrder();
			Assert.assertTrue(nrgnsreviewandpaymentpage.isReCaptchaChallengeDisplayed(),
					"Recaptcha Challenge is not displayed");
			testStepResultVerification(NRGNSReviewAndPaymentPage.recaptchaChallenge);
			test.log(LogStatus.INFO, "Verify if recaptcha challenge is dislayed -COMPLETED "+tld);

			driver.quit();
			System.out.println("End Test: testNewCustomerScenarioUsingNewCard "+tld);
			
		}
	}
}
