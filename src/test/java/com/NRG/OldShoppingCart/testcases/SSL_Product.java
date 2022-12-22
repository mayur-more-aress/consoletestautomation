package com.NRG.OldShoppingCart.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.commonfunctionality.NRGOld_CommonFunctionality;
import com.consoleadmin.pages.CADomainLevelPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
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
import com.netregistryoldwebsite.pages.NRGOrderSSLProduct;
import com.netregistryoldwebsite.pages.NRGRegistrantContactPage;
import com.netregistryoldwebsite.pages.NRGWebHostingPage;
import com.relevantcodes.extentreports.LogStatus;

public class SSL_Product extends NRGOld_CommonFunctionality
{
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
	NRGOrderSSLProduct nrgordersslproduct;
	NRGOld_CommonFunctionality nrgcommonfunctionality;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	CADomainLevelPage cadomainlevelpage;
	
	String domainToVerifySSLError ;
	DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
	Date d = new Date();
	String strAccountRef = "ARQ-45";
	String strAccountPassword = "comein22";
	
	public SSL_Product() 
	{
		super();
	}
	
	/*
	 * Testcase : Order Domain and add VIRTUALHOST service line
	 * Tld's : .com, .com.au, .net,.nz 
	 * Scenario : Existing customer and existing card
	 *  */
	@Parameters({ "environment" })
	@Test
	public void registerDomainAndAddVirtualHostServiceLine(String environment) throws InterruptedException
	{
		// Initialization (Test Data Creation and Assignment)
		String strEligibilityIDType = "ABN";
	   	String strEligibilityIDNumber = "21073716793";
	   	String strEligibilityType = "Arq Group Limited";
	   	String strCompanyName = "Company";
	   	String strExistingCard = "Visa Test Number: 4111********1111 Expiry: 01/2024";
	   	String strProductName ="Business+ Cloud Hosting";
		
		String strDomainName = "TestSSLProduct" + df.format(d);
		String tld = ".com.au";
		
		
		//Test Step 1: Navigate to NRG old shopping cart and order domain
		System.out.println("Start Test: verifyDomainRegistrationOrder");
		initialization(environment, "oldcart_domainsearchurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.clearDefaultTldSelections();
		nrgonlineorderpage.setDomainNameAndTld(strDomainName, tld);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setReturningCustomerContacts(strAccountRef,strAccountPassword);
		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
		nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		if(tld.equalsIgnoreCase(".com.au")) 
		{
			setEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber, strEligibilityType,strCompanyName);
		}
		// Test Step 2:Input credit card details and submit the order 
		selectExistingCard(strExistingCard);
        
		//Test Step 3:Fetch WFID and Account Ref ID
	   fetchRefrenceAndWorkflowId();
		driver.quit();
		
		// Verify if domain registration workflow is completed
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.processWF(NRGOld_CommonFunctionality.strWorkflowId, tld);
		test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
		caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
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
		String strSSLProduct = "RapidSSL*";
		
		System.out.println("Start Test: verifyCorrectErrorInfoIsDisplayed");
		initialization(environment, "oldcart_domainsearchurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.getOrderSSLUrl();
		nrgordersslproduct = new NRGOrderSSLProduct();
		test.log(LogStatus.INFO, "Purchase SSL Product");
		nrgordersslproduct.selectSSLProduct(strSSLProduct);
		
		//Test Step 2: Check whether correct SSL Info Error is Displayed
		nrgordersslproduct.enterdomainName(domainToVerifySSLError);
		test.log(LogStatus.INFO, "Verify coorect info error is displayed");
		Assert.assertTrue(nrgordersslproduct.isInfoErrorDisplayed());
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
		String strSSLProduct = "QuickSSL Premium";
		String strOrgUnit = "Testing Organization Unit";
		String strOrgName = "Netregistry";
		String strCity = "Melbourne";
		String strDomainName = "TestSSLProduct" + df.format(d);
		String tld = ".com";
		String strExistingCard = "Visa Test Number: 4111********1111 Expiry: 01/2024";
		
		//Test Step 1: Navigate to MIT store and select SSL and enter domain name
		System.out.println("Start Test: verifyCorrectErrorInfoIsDisplayed");
		initialization(environment, "oldcart_domainsearchurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.getOrderSSLUrl();
		nrgordersslproduct = new NRGOrderSSLProduct();
		test.log(LogStatus.INFO, "Purchase SSL Product");
		nrgordersslproduct.selectSSLProduct(strSSLProduct);
		nrgordersslproduct.enterdomainName(strDomainName+tld);
		
		//Test Step 2:Enter certificate details and click proceed
		nrgordersslproduct.enterCertificateDetails(strOrgUnit, strOrgName, strCity);
		nrgaccountcontactpage = nrgordersslproduct.clickproccedbtn();
		
		//Test Step 3:Login as existing customer and select existing credit card
		nrgaccountcontactpage.setReturningCustomerContacts(strAccountRef,strAccountPassword);
		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		nrgbillingpage.selectExistingCreditCardOption(strExistingCard);
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickContinueButton();
		
		
		//Test Step 4: Fetch WFID and Account Ref ID
		fetchRefrenceAndWorkflowId();
		driver.quit();
		
		//Test Step 5: Verify if domain orderCertificate2 workflow is created
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
		test.log(LogStatus.INFO, "Verify if orderCertificate2  workflow is created");
		String strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("orderCertificate2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("pending"));
		driver.quit();	
	}
}
