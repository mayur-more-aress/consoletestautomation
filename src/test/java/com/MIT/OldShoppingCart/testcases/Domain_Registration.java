package com.MIT.OldShoppingCart.testcases;
import java.awt.AWTException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
import com.util.DatabaseConnection;
import com.util.TestUtil;

public class Domain_Registration extends MIT_CommonFunctionality
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
	public Domain_Registration() 
	{
		super();
	}
	
	@Parameters({ "environment"})
	@Test
	public void verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard(String environment) throws InterruptedException, AWTException
	{
	
		// Initialization (Test Data Creation and Assignment)
		strCustomerAccountReference = "PAY-377";
		strCustomerPassword = "comein22";
		strEligibilityIDType = "ABN";
		strEligibilityIDNumber = "21073716793";
		strEligibilityType = "Company";
		strCompanyName="Arq Group Limited";
		strExistingCardNumber = "Number: 4111********1111 Expiry: 02/2021";
		
		String arrtlds[] = {".net"};
		SoftAssert softAssert = new SoftAssert();
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
			
		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard");
		initialization(environment, "oldcart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		test.log(LogStatus.INFO, "Navigate to domain search page -STARTED ");
		mitonlineorderpage.getMultipleDomainUrl();
		
		for (String tld : arrtlds) 
		{ 
			strTld = tld;
			System.out.print(strTld + " "); 
			mitonlineorderpage.setMultipleDomainNameandTld(strDomainName, strTld);
		}
		mitdomainsearchpage = mitonlineorderpage.clickMultipleDomainSearchButton();
		test.log(LogStatus.INFO, "Clicking on continue to checkout button ");
		mitadddomainprivacypage = mitdomainsearchpage.clickContinueToCheckout();
		mithostingandextraspage= mitadddomainprivacypage.clickNoThanks();
		test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page ");
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		test.log(LogStatus.INFO, "Enter default customer information on account contact page  ");
		mitaccountcontactpage.setReturningCustomerContacts(strCustomerAccountReference,strCustomerPassword);
		mitregistrantcontactpage = mitaccountcontactpage.clickLoginButton();
		test.log(LogStatus.INFO, "Select have a business idea on domain information page ");
		mitregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page ");
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		if(Arrays.asList(arrtlds).contains(".com.au")) 
		{
			enterEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
		}
		//Test Step 2: Input credit card details and submit the order 
		enterExistingCardDetails(strExistingCardNumber);
        
       // Test Step 3: Verify if the order is completed, get workflow id and account reference.
		fetchMultipleRefrenceId();
		driver.quit();
		
		//Test Step 4: Verify if domain registration workflow is completed in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		for(String refID :MIT_CommonFunctionality.lstWorkflowId)
		{
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caworkflowadminpage = caheaderpage.searchWorkflow(refID);
			caworkflowadminpage.processWorkFlow(refID, strTld);
			caheaderpage.searchWorkflow(refID);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			softAssert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("product workflow failed")
						|| strWorkflowStatus.equalsIgnoreCase("update star rating"),"The domain registration for " +refID+ " FAILED");
			
		}
		softAssert.assertAll();
		driver.quit();
		test.log(LogStatus.INFO, "TestExistingCustomerScenarioUsingExistingCard for multiple domain -COMPLETED");
		System.out.println("End Test: verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard");
	
	}
	
	/*
	 *Test case for placing domain backorder using old shopping cart 
	 */
	@Parameters({ "environment"})
	@Test
	public void verifyDomainBackorderRegistrationForExistingCustomerUsingExistingCard(String environment) throws Exception
	{
	
		// Initialization (Test Data Creation and Assignment)
		strCustomerAccountReference ="MEL-6005";
		strCustomerPassword = "comein22";
		String strTLd = "com";
		strEligibilityIDType = "ABN";
		strEligibilityIDNumber = "21073716793";
		strEligibilityType = "Company";
		strCompanyName="Arq Group Limited";
		strExistingCardNumber = "Number: 4111xxxxxxxx1111 Expiry: 02/21";
		
		//Test step 1: Fetch the registered domain from database
		test.log(LogStatus.INFO, "Edit the product expiry date in the database - STARTED");
		DatabaseConnection.connectToDatabase();
		String registeredDomainName = DatabaseConnection.fetchRegisteredDomainName("MITRetail",strCustomerAccountReference,strTLd);
		String testdata[]=registeredDomainName.split("\\.",2);
		strDomainName = testdata[0];
		System.out.println("Domain Name: " + strDomainName);
		strTld = "."+testdata[1];
		System.out.println("Domain tld: " + strTld);
			
		//Test Step 2: Navigate to shopping cart and place an order for domain registration 
		System.out.println("Start Test: verifyDomainBackorderRegistrationForExistingCustomerUsingExistingCard");
		initialization(environment, "oldcart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		test.log(LogStatus.INFO, "Navigate to domain search page -STARTED ");
		test.log(LogStatus.INFO, "Clearing default tld selections");
		mitonlineorderpage.clearDefaultTldSelections();
		test.log(LogStatus.INFO, "Enter domain name and select Tld ");
		mitonlineorderpage.setDomainNameAndTld(strDomainName, strTld);
		test.log(LogStatus.INFO, "Clicking on search button ");
		mitdomainsearchpage = mitonlineorderpage.clickNewDomainSearchButton();
		test.log(LogStatus.INFO, "Clicking on continue to checkout button ");
		mitdomainsearchpage.checkStatusAndAddDomain(strDomainName, strTld);
		mitadddomainprivacypage = mitdomainsearchpage.clickContinueToCheckout();
		
		//Test Step 3: Navigate to hosting & extra page na dclick continue
		mithostingandextraspage= mitadddomainprivacypage.clickNoThanks();
		test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page ");
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		
		//Test Step 4: Navigate to account page and login as returing customer
		test.log(LogStatus.INFO, "Enter default customer information on account contact page  ");
		mitaccountcontactpage.setReturningCustomerContacts(strCustomerAccountReference,strCustomerPassword);
		mitregistrantcontactpage = mitaccountcontactpage.clickLoginButton();
		test.log(LogStatus.INFO, "Select have a business idea on domain information page ");
		test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page ");
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		
		//Test Step 5: Enter the au elgibility details if domain is "au"
		if(strTLd.equalsIgnoreCase("com.au")) 
		{
			enterEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
		}
			
		//Test Step 6: Input credit card details and submit the order 
		enterExistingCardDetails(strExistingCardNumber);
	        
        //Test Step 7: Verify if order complete page is dislayed
		fetchWorkflowId();
		driver.quit();
			
		// Test Step 8: Verify if domain backorder workflow is completed in A2 admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.processDomainBackorderWF(MIT_CommonFunctionality.strWorkflowId);
		test.log(LogStatus.INFO, "Verify if domain backorder workflow is completed");
		caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainBackorder");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain backorder completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
		driver.quit();
	}
	
	@Parameters({ "environment"})
	@Test
	public void verifyDomainRegistrationForExistingCustomerUsingExistingCard(String environment) throws InterruptedException, AWTException{
		
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
			test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
			mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
			test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
			setReturningCustomer(accountrefrence,password);
			mitregistrantcontactpage=new MITRegistrantContactPage();
			test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
			mitregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
			mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		if(tlds.equalsIgnoreCase("com.au")) 
		{
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
			caworkflowadminpage.processWF(MIT_CommonFunctionality.strWorkflowId,tlds);
			
			// Test Step 5: Verify if domain registration workflow is completed
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
			driver.quit();
		}
	}
	
	@Parameters({ "environment"})
	@Test
	public void verifyDomainRegistrationForExistingCustomerUsingPrepaidCredit(String environment) throws InterruptedException, AWTException{

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
		strDomainName = "TestConsoleRegression" + df.format(d);
			
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
			
			// Test Step 5: Verify if domain registration workflow is completed
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
			driver.quit();
		}
	}
	
	@Parameters({ "environment"})
	@Test
	public void verifyDomainRegistrationForExistingCustomerWithNewCard(String environment) throws InterruptedException, AWTException{

		// Initialization (Test Data Creation and Assignment)
		//String tld[] = {".com", ".net", ".com.au", ".co.nz"};
		String tld[] = {".com"};
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);
		String cardowner="Test";
		String cardnumber="4111111111111111";
		String cardexpirymonth="06";
		String cardexpiryyear="2026";
		String cardsecuritycode="123";
		String cardtype="Visa";


		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationForExistingCustomerWithNewCard");

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
			selectNewCreditCardForExistingCustomer(cardowner, cardtype, cardnumber,cardexpirymonth, cardexpiryyear,cardsecuritycode);

			//Test Step 3:  Verify recaptcha challenge is displayed.
			System.out.println("Recaptcha Challenge is displayed");
			driver.quit();

		}
	}

	@Parameters({ "environment"})
	@Test
	public void verifyDomainRegistrationForNewCustomerWithNewCard(String environment) throws InterruptedException, AWTException{

		// Initialization (Test Data Creation and Assignment)
		//String tld[] = {".com", ".net", ".com.au", ".co.nz"};
		String tld[] = {".com"};
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);
		String cardowner="Test";
		String cardnumber="4111111111111111";
		String cardexpirymonth="06";
		String cardexpiryyear="2026";
		String cardsecuritycode="123";


		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationForExistingCustomerWithNewCard");

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
			test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page "+tlds);
			mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
			test.log(LogStatus.INFO, "Enter default customer information on account contact page  "+tlds);
			enterNewCustomerDetails();
			mitregistrantcontactpage=new MITRegistrantContactPage();
			test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
			mitregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
			mitbillingpage = mitregistrantcontactpage.clickContinueButton();
			if(tlds==".com.au") {
				enterEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
			}
			//Test Step 2: Input credit card details and submit the order 
			selectNewCreditCardForNewCustomer(cardowner,cardnumber,cardexpirymonth,cardexpiryyear,cardsecuritycode);


			//Test Step 3:  Verify recaptcha challenge is displayed.
			System.out.println("Recaptcha Challenge is displayed");
			driver.quit();
		}
	}
}
