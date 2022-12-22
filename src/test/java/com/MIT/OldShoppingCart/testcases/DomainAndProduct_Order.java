package com.MIT.OldShoppingCart.testcases;

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
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class DomainAndProduct_Order extends MIT_CommonFunctionality
{
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
		String tld[] = {".com"};
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);
		String accountrefrence="MEL-6005";
		String password="comein22";
		String strEligibilityIDType="ABN";
		String strEligibilityIDNumber="21073716793";
		String strCompanyName="Arq Group Limited";
		String strEligibilityType="Company";
		String existingcardnumber="Test Visa Number: 4111xxxxxxxx1111 Expiry: 01/22";
			
		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
			
		for(String tlds : tld){
			initialization(environment, "oldcart_domainsearchurl_melbourneit");
			mitonlineorderpage = new MITOnlineOrderPage();
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED "+tlds);
			test.log(LogStatus.INFO, "Clearing default tld selections");
			mitonlineorderpage.clearDefaultTldSelections();
			if(tlds==".co.nz") {
				mitonlineorderpage.selectNzTld();
			}
			enterDomainWithNoPrivacy(strDomainName,tlds);
			mithostingandextraspage=new MITHostingAndExtrasPage();
			test.log(LogStatus.INFO, "Select monthly Product"+tlds);
			mithostingandextraspage.clickEmailHostingRadioButton();
			//mithostingandextraspage.selectOffice365("Business Basic", "1 Month");
			mithostingandextraspage.addEmailHosting("1 x mailboxes, $6.98/mo");
		    test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
			mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
			test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
			setReturningCustomer(accountrefrence,password);
			mitregistrantcontactpage=new MITRegistrantContactPage();
			test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
			mitregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
			mitbillingpage = mitregistrantcontactpage.clickContinueButton();
			if(tlds==".com.au") {
				enterEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
			}
			//Test Step 2: Input credit card details and submit the order 
			enterExistingCardDetails(existingcardnumber);
	        
	        //Test Step 3: Verify if recaptcha challenge is dislayed 
			fetchWorkflowId();	
			driver.quit();
			
			//Test Step 4: Login to console admin
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
			caworkflowadminpage.processWF(MIT_CommonFunctionality.strWorkflowId, tlds);
			//caworkflowadminpage.processProductSetup2O365();
			caworkflowadminpage.processProductSetupWF();
			
			// Test Step 5: Verify if domain registration workflow is completed
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
			registerdomain=strDomainName+tlds; 
			System.out.println("Domain Name:"+registerdomain);
			driver.quit();
		}
	}
@Parameters({ "environment"})
@Test
public void verifyDomainRegistrationAndHostingServiceForExistingCustomerUsingPrepaidCredit(String environment) throws InterruptedException, AWTException{

	// Initialization (Test Data Creation and Assignment)
	String strDomainName = null;
	String strWorkflowStatus = null;
	String strEligibilityIDType="ABN";
	String strEligibilityIDNumber="21073716793";
	String strCompanyName="Arq Group Limited";
	String strEligibilityType="Company";
	//String tld[] = {".com", ".net", ".com.au", ".co.nz"};
	String tld[] = {".com"};
	DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
	Date d = new Date();
	strDomainName = "testconsoleregression" + df.format(d);
		
	//Test Step 1: Login to customer portal and place an order for domain registration 
	System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
		
	for(String tlds : tld){
		initialization(environment, "oldcart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		test.log(LogStatus.INFO, "Navigate to domain search page -STARTED "+tlds);
		test.log(LogStatus.INFO, "Clearing default tld selections");
		mitonlineorderpage.clearDefaultTldSelections();
		if(tlds==".co.nz") {
			mitonlineorderpage.selectNzTld();
		}
		enterDomainWithNoPrivacy(strDomainName,tlds);
		mithostingandextraspage=new MITHostingAndExtrasPage();
		test.log(LogStatus.INFO, "Select Web hosting service"+tlds);
		mithostingandextraspage.clickWebHostingRadioButton();
		test.log(LogStatus.INFO, "Select monthly Product"+tlds);
		//mithostingandextraspage.addCPanelStarter("1 Month");
		mithostingandextraspage.addCPanelStarter1Month("1 Month");
		test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
		mitaccountcontactpage.setReturningCustomerContacts("MEL-6005", "comein22");
		mitregistrantcontactpage = mitaccountcontactpage.clickLoginButton();
		test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
		mitregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		if(tlds==".com.au") {
			enterEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
		}
		//Test Step 2: Input credit card details and submit the order 
		  enterExistingCardDetails("Prepaid credit");
        
        //Test Step 3:  Verify if the order is completed,get workflow id and account reference.
		 fetchWorkflowId();	
		driver.quit();
		
		//Test Step 4: Login to console admin and process the domain registration2 and product setup2 workflow.
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.processWF(MIT_CommonFunctionality.strWorkflowId, tlds);
		caworkflowadminpage.processProductSetupWF();
		
		// Test Step 5: Verify if domain registration workflow is completed
		test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
		caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
		registerdomain=strDomainName+tlds; 
		System.out.println("Domain Name:"+registerdomain);
		driver.quit();
		driver.quit();
	}
}
@Parameters({ "environment"})
@Test
public void verifyDomainRegistrationAndDomainManagerAddOnForExistingCustomerUsingExistingCard(String environment) throws InterruptedException, AWTException{
	
	// Initialization (Test Data Creation and Assignment)
	String strDomainName = null;
	String strWorkflowStatus = null;
	String accountrefrence ="MEL-6005";
	String password="comein22";
	String strEligibilityIDType="ABN";
	String strEligibilityIDNumber="21073716793";
	String strCompanyName="Arq Group Limited";
	String strEligibilityType="Company";
	String existingcardnumber="Test Visa Number: 4111xxxxxxxx1111 Expiry: 01/22";
	
	//String tld[] = {".com", ".net", ".com.au", ".co.nz"};
	String tld[] = {".net"};
	DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
	Date d = new Date();
	strDomainName = "testconsoleregression" + df.format(d);
		
	//Test Step 1: Login to customer portal and place an order for domain registration 
	System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
		
	for(String tlds : tld){
		initialization(environment, "oldcart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		test.log(LogStatus.INFO, "Navigate to domain search page -STARTED "+tlds);
		test.log(LogStatus.INFO, "Clearing default tld selections");
		mitonlineorderpage.clearDefaultTldSelections();
		if(tlds==".co.nz") {
			mitonlineorderpage.selectNzTld();
		}
		enterDomainWithNoPrivacy(strDomainName,tlds);
		mithostingandextraspage=new MITHostingAndExtrasPage();
		test.log(LogStatus.INFO, "Clicking on domain manager hosting service"+tlds);
		mithostingandextraspage.clickWebAndEmailForwarderLink();
		test.log(LogStatus.INFO, "Select yearly Product"+tlds);
		mithostingandextraspage.addDomainManager("1 Year");
		test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
		setReturningCustomer(accountrefrence,password);
		mitregistrantcontactpage=new MITRegistrantContactPage();
		test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
		mitregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		if(tlds==".com.au") {
			enterEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
		}
		//Test Step 2: Input credit card details and submit the order 
		enterExistingCardDetails(existingcardnumber);
        
        //Test Step 3:  Verify if the order is completed,get workflow id and account reference.
		 fetchWorkflowId();	
		driver.quit();
		
		//Test Step 4: Login to console admin and process the domain registration2 and product setup2 workflow.
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.processWF(MIT_CommonFunctionality.strWorkflowId, tlds);
		caworkflowadminpage.processProductSetupWF();
		
		// Test Step 5: Verify if domain registration workflow is completed
		test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
		caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
		registerdomain=strDomainName+tlds; 
		System.out.println("Domain Name:"+registerdomain);
		driver.quit();
	}
}

@Parameters({ "environment"})
@Test
public void verifyDomainRegistrationAndDomainManagerAddOnForExistingCustomerUsingPrepaidCard(String environment) throws InterruptedException, AWTException{
	
	// Initialization (Test Data Creation and Assignment)
	String strDomainName = null;
	String strWorkflowStatus = null;
	String accountrefrence ="MEL-6005";
	String password="comein22";
	String strEligibilityIDType="ABN";
	String strEligibilityIDNumber="21073716793";
	String strCompanyName="Arq Group Limited";
	String strEligibilityType="Company";
	
	//String tld[] = {".com", ".net", ".com.au", ".co.nz"};
	String tld[] = {".net"};
	DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
	Date d = new Date();
	strDomainName = "testconsoleregression" + df.format(d);
		
	//Test Step 1: Login to customer portal and place an order for domain registration 
	System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
		
	for(String tlds : tld){
		initialization(environment, "oldcart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		test.log(LogStatus.INFO, "Navigate to domain search page -STARTED "+tlds);
		test.log(LogStatus.INFO, "Clearing default tld selections");
		mitonlineorderpage.clearDefaultTldSelections();
		if(tlds==".co.nz") {
			mitonlineorderpage.selectNzTld();
		}
		enterDomainWithNoPrivacy(strDomainName,tlds);
		mithostingandextraspage=new MITHostingAndExtrasPage();
		test.log(LogStatus.INFO, "Clicking on domain manager hosting service"+tlds);
		mithostingandextraspage.clickWebAndEmailForwarderLink();
		test.log(LogStatus.INFO, "Select yearly Product"+tlds);
		mithostingandextraspage.addDomainManager("1 Year");
		test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
		setReturningCustomer(accountrefrence,password);
		mitregistrantcontactpage=new MITRegistrantContactPage();
		test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
		mitregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		if(tlds==".com.au") {
			enterEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
		}
		//Test Step 2: Input credit card details and submit the order 
		 enterExistingCardDetails("Prepaid credit");
        
        //Test Step 3:  Verify if the order is completed,get workflow id and account reference.
		 fetchWorkflowId();	
		driver.quit();
		
		//Test Step 4: Login to console admin and process the domain registration2 and product setup2 workflow.
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.processWF(MIT_CommonFunctionality.strWorkflowId, tlds);
		caworkflowadminpage.processProductSetupWF();
		
		// Test Step 5: Verify if domain registration workflow is completed
		test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
		caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
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
	String accountrefrence ="MEL-6005";
	String password="comein22";
	String strEligibilityIDType="ABN";
	String strEligibilityIDNumber="21073716793";
	String strCompanyName="Arq Group Limited";
	String strEligibilityType="Company";
	String existingcardnumber="Test Visa Number: 4111xxxxxxxx1111 Expiry: 01/22";
	
	
	ArrayList<String> lstDomainNames = new ArrayList<String>();
	DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
	Date d = new Date();
	strDomainName = "TestConsoleRegression" + df.format(d);
	test.log(LogStatus.INFO, "verifyMultiDomainRegistrationAndWebHostingProduct  - STARTED");
	System.out.println("Start Test: verifyMultiDomainRegistrationAndWebHostingProduct - STARTED");
	
	
	//Test Step 1: Login to shopping cart  
			test.log(LogStatus.INFO, "NAvigate to shopping cart and place order");
			initialization(environment, "oldcart_domainsearchurl_melbourneit");
			mitonlineorderpage = new MITOnlineOrderPage();
			
			// Test Step 2: Navigate to Bulk Domain page and place order for multiple domains
			test.log(LogStatus.INFO, "Enter multiple domains to register");
			mitonlineorderpage.getMultipleDomainUrl();
	

			System.out.print("number of domains" +numberOfDomains);
		    for(int i= 0; i < numberOfDomains ; i++)
		    {
		    	strTld = arrTlds[i % arrTlds.length];
				System.out.print(strTld + " "); 
				mitonlineorderpage.setMultipleDomainNameandTld(strDomainName.concat(Integer.toString(i)), strTld);
				lstDomainNames.add(strDomainName.concat(Integer.toString(i))+strTld);
		    }
		    mitdomainsearchpage = mitonlineorderpage.clickMultipleDomainSearchButton();
	
	
	//Test Step 3: Domain privacy page for tld's
	if(Arrays.asList(arrTlds).contains(".com") || Arrays.asList(arrTlds).contains(".net"))
	{
		test.log(LogStatus.INFO, "Clicking on continue to checkout button ");
		mitadddomainprivacypage = mitdomainsearchpage.clickContinueToCheckout();
		mithostingandextraspage= mitadddomainprivacypage.clickNoThanks();
	}
	// Test Step 4: Navigate to Services page and add the service
	test.log(LogStatus.INFO, "Process the add services page -STARTED");
	for(String domainname : lstDomainNames)
	{
		System.out.println("List of domains registered" +lstDomainNames);
	    test.log(LogStatus.INFO, "Clicking on web hosting service");
	    mithostingandextraspage.selectDomainName(domainname);
	    mithostingandextraspage.clickWebHostingRadioButton();
	    test.log(LogStatus.INFO, "Select monthly Product");
	    //mithostingandextraspage.addCPanelStarter("1 Month");
	    mithostingandextraspage.addCPanelStarter1Month("1 Month");
	}
	test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page ");
	mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
	
	test.log(LogStatus.INFO, "Enter default customer information on account contact page");
	setReturningCustomer(accountrefrence,password);
	mitregistrantcontactpage=new MITRegistrantContactPage();
	test.log(LogStatus.INFO, "Select have a business idea on domain information page ");
	mitregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
	test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page ");
	mitbillingpage = mitregistrantcontactpage.clickContinueButton();
	if(Arrays.asList(arrTlds).contains(".com.au"))
	{
		enterEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
	}

	//Test Step 5: Input credit card details and submit the order 
	enterExistingCardDetails(existingcardnumber);
    
	// Test Step 6: Verify if the order is completed,get workflow id and account reference.
	fetchMultipleRefrenceId();
    driver.quit();
    
  //Test Step 7: Login to console admin and process the domain registration2 and product setup2 workflow.
	initialization(environment, "consoleadmin");
	caloginpage = new CALoginPage();
	caheaderpage = caloginpage.setDefaultLoginDetails(environment);
	for(String refID : MIT_CommonFunctionality.lstWorkflowId)
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

@Parameters({ "environment"})
@Test
public void verifyDomainRegistrationAndProductForExistingBTCustomerUsingExistingCard(String environment) throws InterruptedException, AWTException{

	// Initialization (Test Data Creation and Assignment)
	String strDomainName = null;
	String strWorkflowStatus = null;
	String accountrefrence ="MEL-6005";
	String password="comein22";
	String strEligibilityIDType="ABN";
	String strEligibilityIDNumber="21073716793";
	String strCompanyName="Arq Group Limited";
	String strEligibilityType="Company";
	String existingcardnumber="Test Visa Number: 4111xxxxxxxx1111 Expiry: 01/22";
	//String tld[] = {".com", ".net", ".com.au", ".co.nz"};
	String tld[] = {".com"};
	DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
	Date d = new Date();
	strDomainName = "testconsoleregression" + df.format(d);
		
	//Test Step 1: Login to customer portal and place an order for domain registration 
	System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
		
	for(String tlds : tld){
		initialization(environment, "oldcart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		test.log(LogStatus.INFO, "Navigate to domain search page -STARTED "+tlds);
		test.log(LogStatus.INFO, "Clearing default tld selections");
		mitonlineorderpage.clearDefaultTldSelections();
		if(tlds==".co.nz") {
			mitonlineorderpage.selectNzTld();
		}
		enterDomainWithNoPrivacy(strDomainName,tlds);
		mithostingandextraspage=new MITHostingAndExtrasPage();
		mithostingandextraspage.clickWebAndEmailForwarderLink();
		test.log(LogStatus.INFO, "Select yearly Product"+tlds);
		mithostingandextraspage.addDomainManager("1 Year");
		test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
		setReturningCustomer(accountrefrence,password);
		mitregistrantcontactpage=new MITRegistrantContactPage();
		test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
		mitregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		if(tlds==".com.au") {
			enterEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
		}
		//Test Step 2: Input credit card details and submit the order 
		enterExistingCardDetails(existingcardnumber);
        
        //Test Step 3: Get workflow and refrence id
		fetchWorkflowId();	
		driver.quit();
		
		 //Test Step 4:Login to console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.processWF(MIT_CommonFunctionality.strWorkflowId, tlds);
		caworkflowadminpage.processProductSetupWF();
		
		// Test Step 6: Verify if domain registration workflow is completed
		test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
		caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
		registerdomain=strDomainName+tlds; 
		System.out.println("Domain Name:"+registerdomain);
		driver.quit();
	}
}	
	
}
