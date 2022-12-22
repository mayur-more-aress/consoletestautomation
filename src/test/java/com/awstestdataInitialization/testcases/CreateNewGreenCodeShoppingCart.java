package com.awstestdataInitialization.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
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
import com.netregistryoldwebsite.pages.NRGAccountContactPage;
import com.netregistryoldwebsite.pages.NRGAddDomainPrivacyPage;
import com.netregistryoldwebsite.pages.NRGAddExtrasPage;
import com.netregistryoldwebsite.pages.NRGAddHostingPage;
import com.netregistryoldwebsite.pages.NRGBillingPage;
import com.netregistryoldwebsite.pages.NRGDomainSearchPage;
import com.netregistryoldwebsite.pages.NRGEligibilityDetailsPage;
import com.netregistryoldwebsite.pages.NRGHostingAndExtrasPage;
import com.netregistryoldwebsite.pages.NRGOnlineOrderPage;
import com.netregistryoldwebsite.pages.NRGOrderCompletePage;
import com.netregistryoldwebsite.pages.NRGRegistrantContactPage;
import com.netregistryoldwebsite.pages.NRGWebHostingPage;
import com.relevantcodes.extentreports.LogStatus;

public class CreateNewGreenCodeShoppingCart extends TestBase
{
	public CreateNewGreenCodeShoppingCart() 
	{
		super();
	}
	
	//NRG shopping cart pages
	NRGOnlineOrderPage nrgonlineorderpage;
	NRGDomainSearchPage nrgdomainsearchpage;	
	NRGAddDomainPrivacyPage nrgadddomainprivacypage;
	NRGHostingAndExtrasPage nrghostingandextraspage;
	NRGWebHostingPage nrgwebhostingpage;
	NRGAddHostingPage nrgaddhostingpage;
	NRGAddExtrasPage nrgaddextraspage;
	NRGAccountContactPage nrgaccountcontactpage;
	NRGRegistrantContactPage nrgregistrantcontactpage;
	NRGBillingPage nrgbillingpage;
	NRGOrderCompletePage nrgordercompletepage;
	NRGEligibilityDetailsPage nrgeligibilitydetailspage;
	
	//MIT shopping cart pages
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
	
	// Initialization (Test Data Creation and Assignment)
	String strDomainName = null;
	String strTld = null;
	
	/*
	 * Test case to create a new greencode via NRG old Shopping cart
	 */
	
	@Parameters({"environment","noOfAccounts"})
	@Test
	public void createNewGreencodeInNRGViaShoppingCart(String environment,int noOfAccounts) throws InterruptedException
	{
		for(int i=0; i< noOfAccounts; i++)
		{
			// Initialization (Test Data Creation and Assignment)
			strTld = ".com";
			DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
			Date d = new Date();
			strDomainName = "TestCreateGreenCode" + df.format(d);
				
			System.out.println("Start Test: createNewGreencodeInNRGViaShoppingCart");
			
			//Test Step 1: Navigate to shopping cart and order a domain
			initialization(environment, "oldcart_domainsearchurl_netregistry");
			nrgonlineorderpage = new NRGOnlineOrderPage();
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED ");
			test.log(LogStatus.INFO, "Clearing default tld selections");
			nrgonlineorderpage.clearDefaultTldSelections();
			test.log(LogStatus.INFO, "Enter domain name and select Tld ");
			nrgonlineorderpage.setDomainNameAndTld(strDomainName, strTld);
			test.log(LogStatus.INFO, "Clicking on search button ");
			nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
			test.log(LogStatus.INFO, "Clicking on continue to checkout button ");
			nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
			nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
			
			//Test Step 2: Navigate to  hosting page and click continue button
			test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page ");
			nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
			
			//Test Step 3: Enter the new customer details
			test.log(LogStatus.INFO, "Enter default customer information on account contact page");
			nrgaccountcontactpage.setCustomerDefaultInformation();
			test.log(LogStatus.INFO, "Clicking on continue button on account contact page ");
			nrgregistrantcontactpage = nrgaccountcontactpage.clickContinueButton();	
			test.log(LogStatus.INFO, "Select have a business idea on domain information page");
			nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page");
			nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
			
			//Test Step 4: Input new credit card details and submit the order 
			test.log(LogStatus.INFO, "Entering credit card information ");
			nrgbillingpage.setBTFormCreditCardDetails("Test Console Regression", "4111111111111111", "11", "2022", "123");
			test.log(LogStatus.INFO, "Tick on terms and conditions ");
		    nrgbillingpage.tickTermsAndConditions();
		    test.log(LogStatus.INFO, "Click on continue button on billing page ");
		    nrgbillingpage.clickContinueButton();
		        
		    //Test Step 5: Verify if recaptcha challenge is displayed 
		    test.log(LogStatus.INFO, "Verify if recaptcha is displayed on click of continue button");
			Assert.assertTrue(nrgbillingpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
			test.log(LogStatus.INFO, "Verification of domain registration - COMPLETED ");
			System.out.println("Please verify the captcha manually !!!");
			test.log(LogStatus.INFO, "Please verify the captcha manually !!!");
			
			System.out.println("End Test: createNewGreencodeInNRGViaShoppingCart");
		}
	}
	
	/*
	 * Test case to create a new greencode via MIT old Shopping cart
	 */
	@Parameters({"environment","noOfAccounts"})
	@Test
	public void createNewGreencodeInMITViaShoppingCart(String environment,int noOfAccounts) throws InterruptedException
	{
		for(int i=0; i< noOfAccounts; i++)
		{
			// Initialization (Test Data Creation and Assignment)
			strTld = ".com";
			DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
			Date d = new Date();
			strDomainName = "TestCreateGreenCode" + df.format(d);
					
			System.out.println("Start Test: createNewGreencodeInMITViaShoppingCart");
			
			//Test Step 1: Navigate to shopping cart and order a domain
			initialization(environment, "oldcart_domainsearchurl_melbourneit");
			mitonlineorderpage = new MITOnlineOrderPage();
			mitonlineorderpage.clearDefaultTldSelections();
			mitonlineorderpage.setDomainNameAndTld(strDomainName, strTld);
			mitdomainsearchpage = mitonlineorderpage.clickNewDomainSearchButton();
			mitadddomainprivacypage = mitdomainsearchpage.clickContinueToCheckout();
			mithostingandextraspage= mitadddomainprivacypage.clickNoThanks();
			
			//Test Step 2: Navigate to  hosting page and click continue button
			test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page ");
			mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
			
			//Test Step 3: Enter the new customer details
			test.log(LogStatus.INFO, "Enter default customer information on account contact page");
			mitaccountcontactpage.setCustomerDefaultInformation();
			test.log(LogStatus.INFO, "Clicking on continue button on account contact page ");
			mitregistrantcontactpage = mitaccountcontactpage.clickContinueButton();	
			test.log(LogStatus.INFO, "Select have a business idea on domain information page");
			mitregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			mitbillingpage = mitregistrantcontactpage.clickContinueButton();
			
			//Test Step 4: Input new credit card details and submit the order 
			test.log(LogStatus.INFO, "Entering credit card information ");
			mitbillingpage.setBTFormCreditCardDetails("Test Console Regression", "4111111111111111", "11", "2019", "123");
			test.log(LogStatus.INFO, "Tick on terms and conditions ");
			mitbillingpage.tickTermsAndConditions();
			test.log(LogStatus.INFO, "Click on continue button on billing page ");
			mitbillingpage.clickContinueButton();
	        
			 //Test Step 5: Verify if recaptcha challenge is displayed 
		    test.log(LogStatus.INFO, "Verify if recaptcha is displayed on click of continue button");
			Assert.assertTrue(mitbillingpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
			test.log(LogStatus.INFO, "Verification of domain registration - COMPLETED ");
			System.out.println("Please verify the captcha manually !!!");
			test.log(LogStatus.INFO, "Please verify the captcha manually !!!");
			
			System.out.println("End Test: createNewGreencodeInMITViaShoppingCart");
		}
	}
}
