package com.MIT.OldShoppingCart.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.melbourneitwebsite.pages.MITDomainSearchPage;
import com.melbourneitwebsite.pages.MITOnlineOrderPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class CheckDomainAvailability extends TestBase
{
	MITOnlineOrderPage mitonlineorderpage;
	MITDomainSearchPage mitdomainsearchpage;
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;
	
	public CheckDomainAvailability() 
	{
		super();
	}
	
	// Initialization (Test Data Creation and Assignment)
	String strDomainName = null;
	String strTld = null;
	String arrtlds[] = {".com"};
	String arrPremiumTlds[] = {".sydney"};

	/*
	 * Test case to check domain is available for registration Using MIT old shopping cart
	*/	
	@Parameters({ "environment"})
	@Test 
	public void verifyDomainNameIsAvailableForRegistration(String environment) throws Exception 
	{
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		for (String tld : arrtlds) 
		{ 
		    strTld = tld;
		    System.out.println("Start Test: verifyDomainNameIsAvailableForRegistration");
			test.log(LogStatus.INFO, "verifyDomainNameIsAvailableForRegistration - STARTED");
			
			// Test Step 1: Navigate to domain search page of new shopping cart and place an order for a test domain
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED");
			initialization(environment, "oldcart_domainsearchurl_melbourneit");
			mitonlineorderpage = new MITOnlineOrderPage();
			mitonlineorderpage.clearDefaultTldSelections();
			mitonlineorderpage.selectShowAll();
			mitonlineorderpage.setDomainNameAndTld(strDomainName, strTld);
			mitdomainsearchpage = mitonlineorderpage.clickNewDomainSearchButton();
			test.log(LogStatus.INFO, "Navigate to domain search page -COMPLETED");
	
			// Test Step 2: Verify search result message
			test.log(LogStatus.INFO, "Verify search result message - STARTED");
			Assert.assertEquals(mitdomainsearchpage.checkDomainNameAvailabilty(strDomainName, strTld),"Available","The domain is available for registration");
			test.log(LogStatus.INFO, "The domain " +strDomainName+strTld+ " is available");
			test.log(LogStatus.INFO, "Verify search result message - COMPLETED");
			driver.quit();
			
			test.log(LogStatus.INFO, "verifyDomainNameIsAvailableForRegistration - COMPLETED");
	    	System.out.println("End Test: verifyDomainNameIsAvailableForRegistration");
		 }
	 }
	
	/*
	 * Test case to check domain is not available for registration Using MIT old shopping cart
	*/	
	@Parameters({"environment"})
	@Test
	public void verifyDomainNameIsNotAvailableForRegistration(String environment) throws Exception 
	{
		strDomainName = "test";
		for (String tld : arrtlds) 
		{ 
			strTld = tld;
		    System.out.println("Start Test: verifyDomainNameIsNotAvailableForRegistration");
			test.log(LogStatus.INFO, "verifyDomainNameIsNotAvailableForRegistration - STARTED");
			
			// Test Step 1: Navigate to domain search page of new shopping cart and place an order for a test domain
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED");
			initialization(environment, "oldcart_domainsearchurl_melbourneit");
			mitonlineorderpage = new MITOnlineOrderPage();
			mitonlineorderpage.clearDefaultTldSelections();
			mitonlineorderpage.selectShowAll();
			mitonlineorderpage.setDomainNameAndTld(strDomainName, strTld);
			mitdomainsearchpage = mitonlineorderpage.clickNewDomainSearchButton();
			test.log(LogStatus.INFO, "Navigate to domain search page -COMPLETED");
	
			// Test Step 2: Verify search result message
			test.log(LogStatus.INFO, "Verify search result message - STARTED");
			Assert.assertEquals(mitdomainsearchpage.checkDomainNameAvailabilty(strDomainName, strTld),"Unavailable","The domain is not available for registration");
			test.log(LogStatus.INFO, "The domain " +strDomainName+strTld+ " is not available");
			test.log(LogStatus.INFO, "Verify search result message - COMPLETED");
			driver.quit();
			
			test.log(LogStatus.INFO, "verifyDomainNameIsNotAvailableForRegistration - COMPLETED");
	    	System.out.println("End Test: verifyDomainNameIsNotAvailableForRegistration");	
		}
	}
	
	@Parameters({"environment"})
	@Test
	public void verifyDomainNameAvailableForRegistrationAndPremiumn(String environment) throws Exception 
	{
		strDomainName = "test";
		for (String tld : arrPremiumTlds) 
		{ 
			strTld = tld;
		    System.out.println("Start Test: verifyDomainNameAvailableForRegistrationAndPremiumn");
			test.log(LogStatus.INFO, "verifyDomainNameAvailableForRegistrationAndPremiumn - STARTED");
			
			// Test Step 1: Navigate to domain search page of new shopping cart and place an order for a test domain
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED");
			initialization(environment, "oldcart_domainsearchurl_melbourneit");
			mitonlineorderpage = new MITOnlineOrderPage();
			mitonlineorderpage.clearDefaultTldSelections();
			mitonlineorderpage.selectShowAll();
			mitonlineorderpage.setDomainNameAndTld(strDomainName, strTld);
			mitdomainsearchpage = mitonlineorderpage.clickNewDomainSearchButton();
			test.log(LogStatus.INFO, "Navigate to domain search page -COMPLETED");
	
			// Test Step 2: Verify search result message
			test.log(LogStatus.INFO, "Verify search result message - STARTED");
			Assert.assertEquals(mitdomainsearchpage.checkDomainNameAvailabilty(strDomainName, strTld),"Premium Name - Call 1300 793 248 to secure","The domain is premium");
			test.log(LogStatus.INFO, "The domain " +strDomainName+strTld+ " is premium");
			test.log(LogStatus.INFO, "Verify search result message - COMPLETED");
			driver.quit();
			
			test.log(LogStatus.INFO, "verifyDomainNameAvailableForRegistrationAndPremiumn - COMPLETED");
	    	System.out.println("End Test: verifyDomainNameAvailableForRegistrationAndPremiumn");	
		}

	}
}
