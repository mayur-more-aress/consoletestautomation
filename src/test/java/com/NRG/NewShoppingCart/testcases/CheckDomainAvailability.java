package com.NRG.NewShoppingCart.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.base.Environment;
import com.base.TestBase;
import com.netregistrynewwebsite.pages.NRGNSSearchAddDomainsPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class CheckDomainAvailability extends TestBase
{
	// Netregistry New Shopping Cart Pages
	NRGNSSearchAddDomainsPage nrgnssearchadddomainspage;
	
	TestUtil testUtil;
	static Environment testenvironment;
	public static ExtentTest logger;

	public CheckDomainAvailability()  
	{
		super();	
	}
	
	// Initialization (Test Data Creation and Assignment)
	String strDomainName = null;
	String strTld = null;
	String arrtlds[] = {".com"};  
	
	/*
	 * Test case to check domain is available for registration Using New cart
	 */		
	@Parameters({ "environment"})
	@Test 
	public void verifyDomainNameIsAvailableForRegistration(String environment) throws Exception 
	{
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestCheckDomain" + df.format(d);
		for (String tld : arrtlds) 
		{ 
		    strTld = tld;
		    System.out.println("Start Test: verifyDomainNameIsAvailableForRegistration");
			test.log(LogStatus.INFO, "verifyDomainNameIsAvailableForRegistration - STARTED");
			
			// Test Step 1: Navigate to domain search page of new shopping cart and place an order for a test domain
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED");
			initialization(environment, "newcart_domainsearchurl_netregistry");
			nrgnssearchadddomainspage = new NRGNSSearchAddDomainsPage();
			nrgnssearchadddomainspage.setDomainNameAndTld(strDomainName, strTld);
			nrgnssearchadddomainspage.clickSearchButton();
			nrgnssearchadddomainspage.addDomainName(strDomainName, strTld);
			test.log(LogStatus.INFO, "Navigate to domain search page -COMPLETED");
	
			// Test Step 2: Verify search result message
			test.log(LogStatus.INFO, "Verify search result message - STARTED");
			Assert.assertEquals(nrgnssearchadddomainspage.checkDomainNameAvailabilty(strDomainName, tld),"The domain is available","The domain is avilable for registration");
			test.log(LogStatus.INFO, "Verify search result message - COMPLETED");
			driver.quit();
			
			test.log(LogStatus.INFO, "verifyDomainNameIsAvailableForRegistration - COMPLETED");
	    	System.out.println("End Test: verifyDomainNameIsAvailableForRegistration");	    	
		 }
	 }
		
	/*
	 * Test case to check domain is not available for registration Using New cart
	 */	
	@Parameters({ "environment"})
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
			initialization(environment, "newcart_domainsearchurl_netregistry");
			nrgnssearchadddomainspage = new NRGNSSearchAddDomainsPage();
			nrgnssearchadddomainspage.setDomainNameAndTld(strDomainName, strTld);
			nrgnssearchadddomainspage.clickSearchButton();
			nrgnssearchadddomainspage.addDomainName(strDomainName, strTld);
			test.log(LogStatus.INFO, "Navigate to domain search page -COMPLETED");
			
			// Test Step 2: Verify search result message
			test.log(LogStatus.INFO, "Verify search result message - STARTED");
			Assert.assertEquals(nrgnssearchadddomainspage.checkDomainNameAvailabilty(strDomainName, tld),"The domain is not available",
					"The domain is not avilable for registration");
			test.log(LogStatus.INFO, "Verify search result message - COMPLETED");
			driver.quit();
			
			test.log(LogStatus.INFO, "verifyDomainNameIsNotAvailableForRegistration - COMPLETED");
	    	System.out.println("End Test: verifyDomainNameIsNotAvailableForRegistration");	
		}	
	}
}
