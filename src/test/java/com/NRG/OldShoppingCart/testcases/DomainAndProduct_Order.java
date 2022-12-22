package com.NRG.OldShoppingCart.testcases;

import java.awt.AWTException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.commonfunctionality.NRGOld_CommonFunctionality;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CAInvoicesPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CATaxInvoicePage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.consolesalesdb.pages.CSCreateDomainWindowPage;
import com.consolesalesdb.pages.CSLoginPage;
import com.consolesalesdb.pages.CSNrCRMPage;
import com.consolesalesdb.pages.CSProcessTransactionPage;
import com.consolesalesdb.pages.CSRegistrantDetailsPage;
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

public class DomainAndProduct_Order extends NRGOld_CommonFunctionality
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
	NRGOld_CommonFunctionality nrgcommonfunctionality;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;	
	CAInvoicesPage cainvoicespage;
	CATaxInvoicePage cataxinvoicepage;
	
	CSLoginPage csloginpage;
	CSNrCRMPage csnrcrmpage;
	CSCreateDomainWindowPage cscreatedomainwindowpage;
	CSRegistrantDetailsPage csregistrantdetailspage;
	CSProcessTransactionPage csprocesstransactionpage;
	
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
	String strServiceProductYear = null;
	String strService = null;
	String strServiceProduct = null;
	String Domaininformation ="Have a business idea and reserving a domain for the future";
	String arrTlds[] = {".com",".net"}; 
	List<String> privacytlds = Arrays.asList(".com", ".net");
	ArrayList<String> lstWorkflowId = null;
	ArrayList<String> lstDomainNames = null;
	SoftAssert softAssert = new SoftAssert();
	int numberOfDomains=2;
	public static String registerdomain;
	
	public DomainAndProduct_Order() 
	{
		super();
	}
	
	@Parameters({ "environment"})
	@Test
	public void verifyDomainRegistrationAndEmailProductForExistingBTCustomerUsingExistingCard(String environment) throws InterruptedException, AWTException{
	
		// Initialization (Test Data Creation and Assignment)
		
		String strDomainName = null;
		String strWorkflowStatus = null;
		//String tld[] = {".com", ".net", ".com.au", ".co.nz"};
		String tld[] = {".com.au"};
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);
			
		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
			
		for(String tlds : tld){
			initialization(environment, "oldcart_domainsearchurl_netregistry");
			nrgonlineorderpage = new NRGOnlineOrderPage();
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED "+tlds);
			test.log(LogStatus.INFO, "Clearing default tld selections");
			nrgonlineorderpage.clearDefaultTldSelections();
			if(tlds==".co.nz") {
				nrgonlineorderpage.selectNzTld();
			}
			enterDomainwithNoPrivacy(strDomainName,tlds);
			nrghostingandextraspage=new NRGHostingAndExtrasPage();
			test.log(LogStatus.INFO, "Select monthly Product"+tlds);
			nrghostingandextraspage.clickEmailHostingRadioButton();
			nrghostingandextraspage.selectOffice365("o365EmailEssentials", "1 Month");
			test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
			nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
			test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
			setReturningCustomer("ARQ-45", "comein22");
			nrgregistrantcontactpage=new NRGRegistrantContactPage();
			test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
			nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
			nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
			if(tlds==".com.au") {
				setEligibilityDetails("ABN", "21073716793", "Arq Group Limited", "Company");
			}
			//Test Step 2: Input credit card details and submit the order 
			selectExistingCard("Visa Test Number: 4111********1111 Expiry: 01/2024");
	        
	        //Test Step 3: Verify if recaptcha challenge is dislayed 
			fetchRefrenceAndWorkflowId();
			driver.quit();
			
			//Test Step 4: Login to console admin and execute the workflow
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
			caworkflowadminpage.processWF(NRGOld_CommonFunctionality.strWorkflowId, tlds);
			caworkflowadminpage.processProductSetup2O365();
			
			// Test Step 5: Verify if domain registration workflow is completed
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
			registerdomain=strDomainName+tlds; 
			System.out.println("Domain Name:"+registerdomain);
			driver.quit();
		}
	}

	@Parameters({ "environment"})
	@Test
	public void verifyDomainRegistrationAndProductForExistingBTCustomerUsingPrepaidCredit(String environment) throws InterruptedException, AWTException
	{
	
		// Initialization (Test Data Creation and Assignment)
		
		String strDomainName = null;
		String strWorkflowStatus = null;
		//String tld[] = {".com", ".net", ".com.au", ".co.nz"};
		String tld[] = {".com"};
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
			
		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
			
		for(String tlds : tld)
		{
			initialization(environment, "oldcart_domainsearchurl_netregistry");
			nrgonlineorderpage = new NRGOnlineOrderPage();
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED "+tlds);
			test.log(LogStatus.INFO, "Clearing default tld selections");
			nrgonlineorderpage.clearDefaultTldSelections();
			if(tlds==".co.nz") 
			{
				nrgonlineorderpage.selectNzTld();
			}
			enterDomainwithNoPrivacy(strDomainName,tlds);
			nrghostingandextraspage=new NRGHostingAndExtrasPage();
			nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
			test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
			setReturningCustomer("ARQ-45", "comein22");
			nrgregistrantcontactpage=new NRGRegistrantContactPage();
			test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
			nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
			nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
			if(tlds==".com.au") {
				setEligibilityDetails("ABN", "21073716793", "Arq Group Limited", "Company");
			}
			//Test Step 2: Input credit card details and submit the order 
			selectPrepaidCredit("Prepaid credit");
	        
	        //Test Step 3: Verify if recaptcha challenge is dislayed 
			fetchRefrenceAndWorkflowId();
			driver.quit();
			
			//Test Step 4: Login to console admin and execute the workflow
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
			caworkflowadminpage.processWF(NRGOld_CommonFunctionality.strWorkflowId, tlds);
			
			// Test Step 5: Verify if domain registration workflow is completed
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
			driver.quit();
		}
	}
	
	@Parameters({ "environment"})
	@Test
	public void verifyDomainRegistrationAndHostingServiceForExistingCustomerUsingPrepaidCredit(String environment) throws InterruptedException, AWTException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strWorkflowStatus = null;
		//String tld[] = {".com", ".net", ".com.au", ".co.nz"};
		String tld[] = {".com"};
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);
			
		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
			
		for(String tlds : tld){
			initialization(environment, "oldcart_domainsearchurl_netregistry");
			nrgonlineorderpage = new NRGOnlineOrderPage();
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED "+tlds);
			test.log(LogStatus.INFO, "Clearing default tld selections");
			nrgonlineorderpage.clearDefaultTldSelections();
			if(tlds==".co.nz") {
				nrgonlineorderpage.selectNzTld();
			}
			enterDomainwithNoPrivacy(strDomainName,tlds);
			nrghostingandextraspage=new NRGHostingAndExtrasPage();
			test.log(LogStatus.INFO, "Select Web hosting service"+tlds);
			nrghostingandextraspage.clickWebHostingRadioButton();
			test.log(LogStatus.INFO, "Select monthly Product"+tlds);
			nrghostingandextraspage.addCPanelStarter1Month("1 Month");
			test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
			nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
			test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
			setReturningCustomer("ARQ-45","comein22");
			nrgregistrantcontactpage=new NRGRegistrantContactPage();
			test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
			nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
			nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
			if(tlds==".com.au") {
				setEligibilityDetails("ABN", "21073716793", "Arq Group Limited", "Company");
			}
			//Test Step 2: Input credit card details and submit the order 
			selectPrepaidCredit("Prepaid credit");
	        
	        //Test Step 3:  Verify if the order is completed,get workflow id and account reference.
			fetchRefrenceAndWorkflowId();
			driver.quit();
			
			//Test Step 4: Login to console admin and process the domain registration2 and product setup2 workflow.
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
			caworkflowadminpage.processWF(NRGOld_CommonFunctionality.strWorkflowId, tlds);
			caworkflowadminpage.processProductSetupWF();
			
			// Test Step 5: Verify if domain registration workflow is completed
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
			registerdomain=strDomainName+tlds; 
			System.out.println("Domain Name:"+registerdomain);
			driver.quit();
		}
	}
	
	@Parameters({ "environment"})
	@Test
	public void verifyDomainRegistrationAndDomainManagerAddOnForExistingCustomerUsingExistingCard(String environment) throws InterruptedException, AWTException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strWorkflowStatus = null;
		//String tld[] = {".com", ".net", ".com.au", ".co.nz"};
		String tld[] = {".net"};
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);
			
		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
			
		for(String tlds : tld){
			initialization(environment, "oldcart_domainsearchurl_netregistry");
			nrgonlineorderpage = new NRGOnlineOrderPage();
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED "+tlds);
			test.log(LogStatus.INFO, "Clearing default tld selections");
			nrgonlineorderpage.clearDefaultTldSelections();
			if(tlds==".co.nz") {
				nrgonlineorderpage.selectNzTld();
			}
			enterDomainwithNoPrivacy(strDomainName,tlds);
			nrghostingandextraspage=new NRGHostingAndExtrasPage();
			test.log(LogStatus.INFO, "Clicking on domain manager hosting service"+tlds);
			nrghostingandextraspage.clickDomainManagerRadioButton();
			test.log(LogStatus.INFO, "Select yearly Product"+tlds);
			nrghostingandextraspage.addDomainManager("1 Year");
			test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
			nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
			test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
			setReturningCustomer("ARQ-45", "comein22");
			nrgregistrantcontactpage=new NRGRegistrantContactPage();
			test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
			nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
			nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
			if(tlds==".com.au") {
				setEligibilityDetails("ABN", "21073716793", "Arq Group Limited", "Company");
			}
			//Test Step 2: Input credit card details and submit the order 
			selectExistingCard("Visa Test Number: 4111********1111 Expiry: 01/2024");
	        
	        //Test Step 3:  Verify if the order is completed,get workflow id and account reference.
			 fetchRefrenceAndWorkflowId();
			driver.quit();
			
			//Test Step 4: Login to console admin and process the domain registration2 and product setup2 workflow.
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
			caworkflowadminpage.processWF(NRGOld_CommonFunctionality.strWorkflowId, tlds);
			caworkflowadminpage.processProductSetupWF();
			
			// Test Step 5: Verify if domain registration workflow is completed
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
			registerdomain=strDomainName+tlds; 
			System.out.println("Domain Name:"+registerdomain);
			driver.quit();
		}
	}
	
	@Parameters({ "environment"})
	@Test 
	public void verifyMultiDomainRegistrationAndWebHostingProduct(String environment) throws Exception 
	{  
		strService ="Web Hosting";
		strServiceProduct ="cPanel Starter";
		strServiceProductYear = "Monthly";
		Domaininformation = "About to build a website";
		
		ArrayList<String> lstDomainNames = new ArrayList<String>();
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		test.log(LogStatus.INFO, "verifyMultiDomainRegistrationAndWebHostingProduct  - STARTED");
    	System.out.println("Start Test: verifyMultiDomainRegistrationAndWebHostingProduct - STARTED");
		
    	
    	//Test Step 1: Login to shopping cart  
    			test.log(LogStatus.INFO, "NAvigate to shopping cart and place order");
    			initialization(environment, "oldcart_domainsearchurl_netregistry");
    			nrgonlineorderpage = new NRGOnlineOrderPage();
    			
    			// Test Step 2: Navigate to Bulk Domain page and place order for multiple domains
    			test.log(LogStatus.INFO, "Enter multiple domains to register");
    			nrgonlineorderpage.getMultipleDomainUrl();
		

    			System.out.print("number of domains" +numberOfDomains);
    		    for(int i= 0; i < numberOfDomains ; i++)
    		    {
    		    	strTld = arrTlds[i % arrTlds.length];
    				System.out.print(strTld + " "); 
    				nrgonlineorderpage.setMultipleDomainNameandTld(strDomainName.concat(Integer.toString(i)), strTld);
    				lstDomainNames.add(strDomainName.concat(Integer.toString(i))+strTld);
    		    }
    			nrgdomainsearchpage = nrgonlineorderpage.clickMultipleDomainSearchButton();
		
		
		//Test Step 3: Domain privacy page for tld's
		if(Arrays.asList(arrTlds).contains(".com") || Arrays.asList(arrTlds).contains(".net"))
		{
			test.log(LogStatus.INFO, "Clicking on continue to checkout button ");
			nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
			nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		}
		// Test Step 4: Navigate to Services page and add the service
		test.log(LogStatus.INFO, "Process the add services page -STARTED");
		for(String domainname : lstDomainNames)
		{
			System.out.println("List of domains registered" +lstDomainNames);
		    test.log(LogStatus.INFO, "Clicking on web hosting service");
		    nrghostingandextraspage.selectDomainName(domainname);
		    nrghostingandextraspage.clickWebHostingRadioButton();
		    test.log(LogStatus.INFO, "Select monthly Product");
		    nrghostingandextraspage.addCPanelStarter1Month("1 Month");
		}
		test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page ");
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		
		test.log(LogStatus.INFO, "Enter default customer information on account contact page");
		nrgaccountcontactpage.setReturningCustomerContacts("ARQ-45", "comein22");
		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
		test.log(LogStatus.INFO, "Select have a business idea on domain information page ");
		nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page ");
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		if(Arrays.asList(arrTlds).contains(".com.au"))
		{
			setEligibilityDetails("ABN", "21073716793", "Arq Group Limited", "Company");
		}

		//Test Step 5: Input credit card details and submit the order 
		selectExistingCard("Visa Test Number: 4111********1111 Expiry: 01/2024");
        
		// Test Step 6: Verify if the order is completed,get workflow id and account reference.
		fetchMultipleRefrenceAndWorkflowId();
	    driver.quit();
	    
	  //Test Step 7: Login to console admin and process the domain registration2 and product setup2 workflow.
	    loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		for(String refID : NRGOld_CommonFunctionality.lstWorkflowId)
		{
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caworkflowadminpage = caheaderpage.searchWorkflow(refID);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			
			// Process and verify if domain registration workflow is completed and productsetup2 workflow is approved in console admin
			if(strWorkflowStatus.equalsIgnoreCase("register domain failed"))
			{
				System.out.println("domainregistration2 WF is failed for Id :" +refID);
			}
			else
			{
				caworkflowadminpage.processMultipleWF(refID);
				caworkflowadminpage.processProductSetup2();
				caworkflowadminpage = caheaderpage.searchWorkflow(refID);
				strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			}
			softAssert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("product workflow failed")
					|| strWorkflowStatus.equalsIgnoreCase("update star rating"),"The domain registration for " +refID+ " FAILED");
		}
    	
		softAssert.assertAll();
		driver.quit();
    	test.log(LogStatus.INFO, "verifyMultiDomainRegistrationAndWebHostingProduct for "+strTld+ " domain and "
				+strServiceProduct+ " product - COMPLETED");
    	System.out.println("Start Test: verifyMultiDomainRegistrationAndWebHostingProduct for "+strTld+ " domain and " 
		        +strServiceProduct+ " product - COMPLETED");
	}
	
	/*End to end flow - Purchase, refund, creditoff and delete
	 * */
	@Parameters({ "environment"})
	@Test
	public void verifyDomainRegistrationAndProductForExistingCustomerUsingExistingCard(String environment) throws InterruptedException, AWTException{
	
		// Initialization (Test Data Creation and Assignment)
		
		String strDomainName = null;
		String strWorkflowStatus = null;
		//String tld[] = {".com", ".net", ".com.au", ".co.nz"};
		String tld[] = {".com.au"};
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
			
		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
			
		for(String tlds : tld){
			initialization(environment, "oldcart_domainsearchurl_netregistry");
			nrgonlineorderpage = new NRGOnlineOrderPage();
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED "+tlds);
			test.log(LogStatus.INFO, "Clearing default tld selections");
			nrgonlineorderpage.clearDefaultTldSelections();
			if(tlds==".co.nz") {
				nrgonlineorderpage.selectNzTld();
			}
			enterDomainwithNoPrivacy(strDomainName,tlds);
			nrghostingandextraspage=new NRGHostingAndExtrasPage();
			test.log(LogStatus.INFO, "Select monthly Product"+tlds);
			nrghostingandextraspage.clickEmailHostingRadioButton();
			nrghostingandextraspage.selectOffice365("o365EmailEssentials", "1 Month");
			test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
			nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
			test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
			setReturningCustomer("ARQ-45", "comein22");
			nrgregistrantcontactpage=new NRGRegistrantContactPage();
			test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
			nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
			nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
			if(tlds==".com.au") {
				setEligibilityDetails("ABN", "21073716793", "Arq Group Limited", "Company");
			}
			//Test Step 2: Input credit card details and submit the order 
			selectExistingCard("Visa Test Number: 4111********1111 Expiry: 01/2024");
	        
	        //Test Step 3:  Fetch the refence and workflow id.
			fetchRefrenceAndWorkflowId();
			driver.quit();
			
			//Test Step 4: FLogin to console admin and process the workflow.
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
			String arr[] = caworkflowadminpage.processWF(NRGOld_CommonFunctionality.strWorkflowId, tlds);
			String invoiceNumber = arr[0].toString().trim();
			String invoiceAmount = arr[1].toString().trim();
			System.out.println(arr[0]);
			System.out.println(arr[1]);
			String productAmount = caworkflowadminpage.processProductSetup2O365();
			System.out.println(productAmount);
			
			// Test Step 5: Verify if domain registration workflow is completed
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caheaderpage.searchWorkflow(strWorkflowId); 
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
			driver.quit();
			
			//Step 6: Refund Domain from salesDB
			initialization(environment, "salesdburl");
			csloginpage = new CSLoginPage();
			csloginpage.setDefaultLoginDetails(environment);
			csnrcrmpage = csloginpage.clickLoginButton();
			csprocesstransactionpage = csnrcrmpage.clickAccountTab();
			System.out.println(invoiceAmount);
			System.out.println(productAmount);
			//String refundAmount = csprocesstransactionpage.addDomainAndProductAmount(invoiceAmount, productAmount);
			csprocesstransactionpage.setProcessTransactionDetails(NRGOld_CommonFunctionality.strAccountReference, invoiceNumber, "REFUND", invoiceAmount, "", "Credit Card");
			Assert.assertEquals(csprocesstransactionpage.getConfirmationMessage(), "Item Successfully Added");
			driver.close();
			
			//Step 7: Credit off from console admin
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caheaderpage.searchAccountReference(NRGOld_CommonFunctionality.strAccountReference);
			cainvoicespage = caheaderpage.clickViewInvoiceAndPrepaidDetail();
			cataxinvoicepage = cainvoicespage.clickInvoiceID(invoiceNumber);
			cataxinvoicepage.creditOffProduct(productAmount);
			cataxinvoicepage.creditOffDomain(invoiceAmount, productAmount);
			Assert.assertEquals(cataxinvoicepage.getRefundConfirmationMessage(), "Credit has been successfully entered.");
			driver.close();
			
			//Step 8: Delete domain from API
			//DeleteDomainFromAPI.deleteDomainFromAPIAfilias(strDomainName+tlds);	
		}
	}
	
	@Parameters({ "environment"})
	@Test
	public void verifyDomainRegistrationForExistingCustomerUsingPrepaidCredit(String environment) throws InterruptedException, AWTException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strWorkflowStatus = null;
		//String tld[] = {".com", ".net", ".com.au", ".co.nz"};
		String tld[] = {".com"};
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);
			
		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
			
		for(String tlds : tld){
			initialization(environment, "oldcart_domainsearchurl_netregistry");
			nrgonlineorderpage = new NRGOnlineOrderPage();
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED "+tlds);
			test.log(LogStatus.INFO, "Clearing default tld selections");
			nrgonlineorderpage.clearDefaultTldSelections();
			if(tlds==".co.nz") {
				nrgonlineorderpage.selectNzTld();
			}
			enterDomainwithNoPrivacy(strDomainName,tlds);
			nrghostingandextraspage=new NRGHostingAndExtrasPage();
			test.log(LogStatus.INFO, "Select Web hosting service"+tlds);
			nrghostingandextraspage.clickWebHostingRadioButton();
			test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
			nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
			test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
			setReturningCustomer("ARQ-45","comein22");
			nrgregistrantcontactpage=new NRGRegistrantContactPage();
			test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
			nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
			nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
			if(tlds==".com.au") {
				setEligibilityDetails("ABN", "21073716793", "Arq Group Limited", "Company");
			}
			//Test Step 2: Input credit card details and submit the order 
			selectPrepaidCredit("Prepaid credit");
	        
	        //Test Step 3:  Verify if the order is completed,get workflow id and account reference.
			fetchRefrenceAndWorkflowId();
			driver.quit();
			
			//Test Step 4: Login to console admin and process the domain registration2 and product setup2 workflow.
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
			caworkflowadminpage.processWF(NRGOld_CommonFunctionality.strWorkflowId, tlds);
			
			// Test Step 5: Verify if domain registration workflow is completed
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
			registerdomain=strDomainName+tlds; 
			System.out.println("Domain Name:"+registerdomain);
		}
	}
}
