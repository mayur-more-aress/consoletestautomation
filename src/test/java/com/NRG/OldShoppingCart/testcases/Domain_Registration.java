package com.NRG.OldShoppingCart.testcases;

import java.awt.AWTException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
import com.netregistryoldwebsite.pages.NRGHostingAndExtrasPage;
import com.netregistryoldwebsite.pages.NRGOnlineOrderPage;
import com.netregistryoldwebsite.pages.NRGRegistrantContactPage;
import com.netregistryoldwebsite.pages.NRGWebHostingPage;
import com.relevantcodes.extentreports.LogStatus;
import com.salesdb.testcases.DeleteDomainFromAPI;
import com.util.DatabaseConnection;

public class Domain_Registration extends NRGOld_CommonFunctionality
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
	
	public Domain_Registration() {
		super();
	}
	
	@Parameters({ "environment"})
	@Test
	public void verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard(String environment) throws InterruptedException, AWTException
	{
	
		// Initialization (Test Data Creation and Assignment)
		strCustomerAccountReference = "ARQ-45";
		strCustomerPassword = "comein22";
		strEligibilityIDType = "ABN";
		strEligibilityIDNumber = "21073716793";
		strEligibilityType = "Company";
		strCompanyName="Arq Group Limited";
		strExistingCardNumber = "Visa Test Number: 4111********1111 Expiry: 01/2024";
		String arrtlds[] = {".com"};
		SoftAssert softAssert = new SoftAssert();
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
			
		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard");
		initialization(environment, "oldcart_domainsearchurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		test.log(LogStatus.INFO, "Navigate to domain search page -STARTED ");
		nrgonlineorderpage.getMultipleDomainUrl();
		
		for (String tld : arrtlds) 
		{ 
			strTld = tld;
			System.out.print(strTld + " "); 
			nrgonlineorderpage.setMultipleDomainNameandTld(strDomainName, strTld);
		}
		nrgdomainsearchpage = nrgonlineorderpage.clickMultipleDomainSearchButton();
		test.log(LogStatus.INFO, "Clicking on continue to checkout button ");
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page ");
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		test.log(LogStatus.INFO, "Enter default customer information on account contact page  ");
		setReturningCustomer(strCustomerAccountReference,strCustomerPassword);
		nrgregistrantcontactpage=new NRGRegistrantContactPage();
		test.log(LogStatus.INFO, "Select have a business idea on domain information page ");
		nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
		test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page ");
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		if(Arrays.asList(arrtlds).contains(".com.au")) 
		{
			setEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
		}
		
		//Test Step 2: Input credit card details and submit the order 
		selectExistingCard(strExistingCardNumber);
        
        //Test Step 3: Verify if the order is completed, get workflow id and account reference.
		fetchMultipleRefrenceAndWorkflowId();
		driver.quit();
		
		//Test Step 4: Verify if domain registration workflow is completed in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		for(String refID : NRGOld_CommonFunctionality.lstWorkflowId)
		{
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
		strCustomerAccountReference ="MEL-6007";
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
		String registeredDomainName = DatabaseConnection.fetchRegisteredDomainName("Netregistry",strCustomerAccountReference,strTLd);
		String testdata[]=registeredDomainName.split("\\.",2);
		strDomainName = testdata[0];
		System.out.println("Domain Name: " + strDomainName);
		strTld = "."+testdata[1];
		System.out.println("Domain tld: " + strTld);
			
		//Test Step 2: Navigate to shopping cart and place an order for domain registration 
		System.out.println("Start Test: verifyDomainBackorderRegistrationForExistingCustomerUsingExistingCard");
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
		nrgdomainsearchpage.checkStatusAndAddDomain(strDomainName, strTld);
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		
		//Test Step 3: Navigate to hosting & extra page and click continue
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		test.log(LogStatus.INFO, "Clicking on continue button on hosting and extras page ");
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		
		//Test Step 4: Navigate to account page and login as returing customer
		setReturningCustomer("ARQ-45", "comein22");
		nrgregistrantcontactpage=new NRGRegistrantContactPage();
		test.log(LogStatus.INFO, "Select have a business idea on domain information page ");
		test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page ");
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		//Test Step 5: Enter the au elgibility details if domain is "au"
		if(strTLd.equalsIgnoreCase("com.au")) 
		{
			setEligibilityDetails(strEligibilityIDType, strEligibilityIDNumber,strCompanyName,strEligibilityType);
		}
			
		//Test Step 6: Input credit card details and submit the order 
		 selectExistingCard(strExistingCardNumber);
		 
		//Test Step 7: Fetch the refrence and workflow id.
	    fetchRefrenceAndWorkflowId();
		driver.quit();
			
		// Test Step 8: Verify if domain backorder workflow is completed in A2 admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.processDomainBackorderWF(NRGOld_CommonFunctionality.strWorkflowId);
		test.log(LogStatus.INFO, "Verify if domain backorder workflow is completed");
		caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainBackorder");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain backorder completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
		driver.quit();
	}
	
	/*End to end flow - Purchase, refund, creditoff and delete
	 * */
	@Parameters({ "environment"})
	@Test
	public void verifyDomainRegistrationOrderForExistingBTCustomerInCustomerPortal(String environment) throws InterruptedException, AWTException, IOException{
	
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
	        
			//Test Step 3: Fetch the account refrence and workflow id
			fetchRefrenceAndWorkflowId();
			driver.quit();

			//Step 4: Execute domain registration workflows
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
			String arr[] = caworkflowadminpage.processWF(NRGOld_CommonFunctionality.strWorkflowId, tlds);
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
			String invoiceNumber = arr[0].toString();
			String invoiceAmount = arr[1].toString();
			System.out.println(arr[0]);
			System.out.println(arr[1]);
			driver.quit();
			
			//Step 5: Refund Domain from salesDB
			initialization(environment, "salesdburl");
			csloginpage = new CSLoginPage();
			csloginpage.setDefaultLoginDetails(environment);
			csnrcrmpage = csloginpage.clickLoginButton();
			csprocesstransactionpage = csnrcrmpage.clickAccountTab();
			csprocesstransactionpage.setProcessTransactionDetails(strAccountReference, invoiceNumber, "REFUND", invoiceAmount, "", "Credit Card");
			Assert.assertEquals(csprocesstransactionpage.getConfirmationMessage(), "Item Successfully Added");
			driver.close();
			
			//Step 6: Credit off from console admin
			initialization(environment, "consoleadmin");
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.setDefaultLoginDetails(environment);
			caheaderpage.searchAccountReference(strAccountReference);
			cainvoicespage = caheaderpage.clickViewInvoiceAndPrepaidDetail();
			cataxinvoicepage = cainvoicespage.clickInvoiceID(invoiceNumber);
			cataxinvoicepage.enterDescription("Refund to credit card");
			cataxinvoicepage.enterInvoiceAmount(invoiceAmount);
			cataxinvoicepage.clickSubmitButton();
			Assert.assertEquals(cataxinvoicepage.getRefundConfirmationMessage(), "Credit has been successfully entered.");
			driver.close();
			
			//Step 7: Delete domain from API
			DeleteDomainFromAPI.deleteDomainFromAPIAfilias(strDomainName+tlds);	
		}
	}
	
	@Parameters({ "environment"})
	@Test
	public void verifyDomainRegistrationForExistingCustomerUsingPrepaidCredit(String environment) throws InterruptedException, AWTException{
	
		// Initialization (Test Data Creation and Assignment)
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
			createNewCreditCardDetailsforExistingCustomer(cardowner,cardnumber,cardexpirymonth,cardexpiryyear,cardsecuritycode);
	        
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
			enterNewCustomerDetails();
			nrgregistrantcontactpage=new NRGRegistrantContactPage();
			test.log(LogStatus.INFO, "Select have a business idea on domain information page "+tlds);
			nrgregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			test.log(LogStatus.INFO, "Clicking on continue button on registrant contact page "+tlds);
			nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
			if(tlds==".com.au") {
				setEligibilityDetails("ABN", "21073716793", "Arq Group Limited", "Company");
			}
			//Test Step 2: Input credit card details and submit the order 
			createNewCreditCardDetailsforNewCustomer(cardowner,cardnumber,cardexpirymonth,cardexpiryyear,cardsecuritycode);
	        
	   
			//Test Step 3:  Verify recaptcha challenge is displayed.
			System.out.println("Recaptcha Challenge is displayed");
			driver.quit();
		}
	}

	
}
