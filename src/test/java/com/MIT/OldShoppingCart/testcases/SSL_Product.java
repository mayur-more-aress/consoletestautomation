package com.MIT.OldShoppingCart.testcases;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.commonfunctionality.MIT_CommonFunctionality;
import com.consoleadmin.pages.CADomainLevelPage;
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
import com.melbourneitwebsite.pages.MITOrderSSLPage;
import com.melbourneitwebsite.pages.MITRegistrantContactPage;
import com.melbourneitwebsite.pages.MITWebHostingPage;
import com.relevantcodes.extentreports.LogStatus;

public class SSL_Product extends MIT_CommonFunctionality
{
	//MIT Old shopping cart pages
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
	MITOrderSSLPage mitordersslpage;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	CADomainLevelPage cadomainlevelpage;
	
	String domainToVerifySSLError ;
	String strEligibilityIDType = "ABN";
   	String strEligibilityIDNumber = "21073716793";
   	String strEligibilityType = "Arq Group Limited";
   	String strCompanyName = "Company";
   	DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
	Date d = new Date();
	String strAccountRef = "MEL-6005";
	String strAccountPassword = "comein22";
	
	public SSL_Product() 
	{
		super();
	}
	
	/*
	 * Testcase : Order Domain and add VIRTUALHOST service line
	 * Domains : .com, .com.au, .net,.nz 
	 * Scenario : Existing customer and existing card
	 *  */
	
	@Parameters({ "environment" })
	@Test
	public void registerDomainAndAddVirtualHostServiceLine(String environment) throws InterruptedException
	{
		// Initialization (Test Data Creation and Assignment)
		
	   	String strProductName ="Business+ Cloud Hosting";
		String strDomainName = "TestSSLProduct" + df.format(d);
		String tld = ".com.au";
		String strExistingCard = "Number: 4111********1111 Expiry: 02/2021";
		
		//Test Step 1: Navigate to MIT old shopping cart and order domain
		System.out.println("Start Test: verifyDomainRegistrationOrder");
		initialization(environment, "oldcart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		mitonlineorderpage.clearDefaultTldSelections();
		mitonlineorderpage.selectShowAll();
		mitonlineorderpage.setDomainNameAndTld(strDomainName, tld);
		mitdomainsearchpage = mitonlineorderpage.clickNewDomainSearchButton();
		mitadddomainprivacypage = mitdomainsearchpage.clickContinueToCheckout();
		mithostingandextraspage= mitadddomainprivacypage.clickNoThanks();
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		mitaccountcontactpage.setReturningCustomerContacts(strAccountRef,strAccountPassword);
		mitregistrantcontactpage = mitaccountcontactpage.clickLoginButton();
		mitregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		if(tld.equalsIgnoreCase(".com.au")) 
		{
			enterEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
		}
		// Input credit card details and submit the order 
		enterExistingCardDetails(strExistingCard);
        
		//Fetch WFID and Account Ref ID
		fetchWorkflowId();	
		driver.quit();
		
		// Verify if domain registration workflow is completed
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.processWF(MIT_CommonFunctionality.strWorkflowId, tld);
		test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
		caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
		String strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
		
		//Test Step 2: Add VirtualHostLine service to domain
		cadomainlevelpage = caheaderpage.searchDomainName(strDomainName+tld);
		cadomainlevelpage.purchaseProduct(strProductName);
		domainToVerifySSLError = strDomainName+tld;
		driver.quit();
	}
	
	/*
	 * Testcase : verify the correct information ERROR is displayed in the Cart
	 * Product : SSL
	 * Scenario : Existing customer and existing card
	 * Pre-requisites = Domain HAS a VIRTUALHOST service line
	 *  */
	
	@Parameters({ "environment" })
	@Test(dependsOnMethods = { "registerDomainAndAddVirtualHostServiceLine" })
	public void verifyCorrectErrorInfoIsDisplayed(String environment) throws InterruptedException
	{
		// Initialization (Test Data Creation and Assignment)
		String strSSLProduct = "RapidSSL 128-bit";
		
		//Test Step 1: Navigate to MIT store and order SSL
		System.out.println("Start Test: verifyCorrectErrorInfoIsDisplayed");
		initialization(environment, "oldcart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		mitonlineorderpage.getOrderSSLUrl();
		mitordersslpage = new MITOrderSSLPage();
		test.log(LogStatus.INFO, "Purchase SSL Product");
		mitordersslpage.selectSSLProduct(strSSLProduct);
		
		//Test Step 2: Check whether correct SSL Info Error is Displayed
		mitordersslpage.enterdomainName(domainToVerifySSLError);
		test.log(LogStatus.INFO, "Verify coorect info error is displayed");
		Assert.assertTrue(mitordersslpage.isInfoErrorDisplayed());
		System.out.println("End Test: verifyCorrectErrorInfoIsDisplayed");
	}
	
	/*
	 * Testcase :  verify that the ordercertificate2 workflow is created
	 * Product :  Quick SSL Premium (Website Security)
	 * Scenario : Existing customer and existing card
	 * Pre-requisites = Domain DOES NOT have a VIRTUALHOST service line
	 *  */
	@Parameters({ "environment" })
	@Test
	public void purchaseSSLProduct(String environment) throws InterruptedException
	{
		// Initialization (Test Data Creation and Assignment)
		String strSSLProduct = "Geotrust QuickSSL Premium 128-bit";
		String strOrgUnit = "Testing Organization Unit";
		String strOrgName = "MelbourneIT";
		String strCity = "Melbourne";
		String strDomainName = "TestSSLProduct" + df.format(d);
		String tld = ".com";
		String strExistingCard = "Number: 4111********1111 Expiry: 01/22";
		String strAccountRef = "MEL-6005";
		String strAccountPassword = "comein22";
		
		//Test Step 1: Navigate to MIT store and select SSL and enter domain name
		System.out.println("Start Test: verifyCorrectErrorInfoIsDisplayed");
		initialization(environment, "oldcart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		mitonlineorderpage.getOrderSSLUrl();
		mitordersslpage = new MITOrderSSLPage();
		test.log(LogStatus.INFO, "Purchase SSL Product");
		mitordersslpage.selectSSLProduct(strSSLProduct);
		mitordersslpage.enterdomainName(strDomainName+tld);
		
		//Test Step 2:Enter certificate details and click proceed
		mitordersslpage.enterCertificateDetails(strOrgUnit, strOrgName, strCity);
		mitaccountcontactpage = mitordersslpage.clickproccedbtn();
		
		//Test Step 3:Login as existing customer and select existing credit card
		mitaccountcontactpage.setReturningCustomerContacts(strAccountRef,strAccountPassword);
		mitregistrantcontactpage = mitaccountcontactpage.clickLoginButton();
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		enterExistingCardDetails(strExistingCard);
		
		//Test Step 4: Fetch WFID and Account Ref ID
		fetchWorkflowId();	
		driver.quit();
		
		//Test Step 5: Verify if domain orderCertificate2 workflow is created
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
		test.log(LogStatus.INFO, "Verify if orderCertificate2  workflow is created");
		String strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("orderCertificate2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("pending"));
		driver.quit();	
	}
}
