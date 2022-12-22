package com.MIT.CustomerPortal.testcases;

import java.awt.AWTException;
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
import com.consolesmui.pages.CSMUIDomainNamePage;
import com.consolesmui.pages.CSMUITabPage;
import com.melbourneitwebsite.pages.MITAccountContactPage;
import com.melbourneitwebsite.pages.MITAddDomainPrivacyPage;
import com.melbourneitwebsite.pages.MITAddExtrasPage;
import com.melbourneitwebsite.pages.MITAddHostingPage;
import com.melbourneitwebsite.pages.MITBillingPage;
import com.melbourneitwebsite.pages.MITDomainSearchPage;
import com.melbourneitwebsite.pages.MITEligibilityDetailsPage;
import com.melbourneitwebsite.pages.MITHeaderPage;
import com.melbourneitwebsite.pages.MITHostingAndExtrasPage;
import com.melbourneitwebsite.pages.MITLoginPage;
import com.melbourneitwebsite.pages.MITOnlineOrderPage;
import com.melbourneitwebsite.pages.MITOrderCompletePage;
import com.melbourneitwebsite.pages.MITRegistrantContactPage;
import com.melbourneitwebsite.pages.MITWebHostingPage;
import com.mitcustomerportal.pages.MITOrderTabPage;
import com.mitcustomerportal.pages.MITRegistrantNameChangePage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class RegistrantNameChange extends MIT_CommonFunctionality
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
	MITRegistrantNameChangePage mitregistrantnamechangepage;
	MITOrderTabPage mitordertabpage;
	MITLoginPage mitloginpage;
	MITHeaderPage mitheaderpage;
	CADomainLevelPage cadomainlevelpage;
	CSMUITabPage csmuitabpage;
	CSMUIDomainNamePage csmuidomainnamepage;
	
	// Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	TestUtil testUtil;
	public static ExtentTest logger;
		
	String strPassword = null;
	String accountReference = null;
	String strDomainName = null;
	String strTld = null;
	boolean flag = true;
	String strRegisterDomain;
	String strDomainPassword;
	String strWorkflowStatus;
	
	public RegistrantNameChange() 
	{
		super();
	}

@Parameters({ "environment"})
@Test
public void verifyDomainRegistration(String environment) throws InterruptedException, AWTException{

	// Initialization (Test Data Creation and Assignment)
	String strDomainName = null;
	String strWorkflowStatus = null;
	String strEligibilityIDType="ABN";
	String strEligibilityIDNumber="21073716793";
	String strCompanyName="Arq Group Limited";
	String strEligibilityType="Company";
	String existingcardnumber="Test Visa Number: 4111xxxxxxxx1111 Expiry: 01/22";
	String accountrefrence="MEL-6005";
	String password="comein22";
	//String tld[] = {".com", ".net", ".com.au", ".co.nz"};
	String tld[] = {".com.au"};
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
		
		//Test Step 4: Login to console admin and process the domain registration2 workflow.
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
		caworkflowadminpage.processWF(MIT_CommonFunctionality.strWorkflowId, tlds);
	
		// Test Step 5: Verify if domain registration workflow is completed
		test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
		caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
		strRegisterDomain=strDomainName+tlds;  
		String testdata[]=strRegisterDomain.split("\\.",2);
		strRegisterDomain = testdata[0];
		System.out.println("Domain Name: " + strRegisterDomain);
		strTld = "."+testdata[1];
		System.out.println("Domain tld: " + strTld);
		
		//Test Step 6: Login as client as get domain password
		cadomainlevelpage=caheaderpage.searchDomainName(strRegisterDomain);
		cadomainlevelpage = new CADomainLevelPage();
		csmuitabpage = cadomainlevelpage.clickloginAsClientLink();
		csmuidomainnamepage = csmuitabpage.clickDomainNameTab();
		strDomainPassword = csmuidomainnamepage.getDomainPassword();
		System.out.println("Doamin Passowrd: " + strDomainPassword);
		driver.quit();
	 }
	}

@Parameters({ "environment"})
@Test
public void verifyRegistrantNameChangeWithExistingCard(String environment) throws InterruptedException, AWTException
{
	  // Initialization (Test Data Creation and Assignment)
			strPassword = "comein22";
			accountReference = "MEL-6005";
			String existingcardnumber="Test Visa Number: 4111xxxxxxxx1111 Expiry: 01/22";
			String strEligibilityIDType="ABN";
			String strEligibilityIDNumber="13080859721";
			String strCompanyName="NETREGISTRY PTY LTD";
			String strEligibilityType="Company";
			
			verifyDomainRegistration(environment);
	
			System.out.println("Start Test: verifyDomainRegistrantNameChange");
			test.log(LogStatus.INFO, "Change domain ownership From Customer Portal for " +strTld+ " - STARTED");
			initialization(environment, "customerportalurl_melbourneit");
			
			// Test Step 1: Login to customer portal
			logintoCustomerPortal(accountReference,strPassword);
			
			// Test Step 2: Click on order tab and click on domain ownership link
			test.log(LogStatus.INFO, "Click on order tab");
			mitheaderpage=new MITHeaderPage();
			mitordertabpage=mitheaderpage.clickOnOrderTab();
			test.log(LogStatus.INFO, "Click on domain ownership link");
			mitregistrantnamechangepage=mitordertabpage.clickOnDomainOwnershipLink();
			
			// Test Step 3: Enter the domain details and submit the details
			test.log(LogStatus.INFO, "Enter the domain name");
			mitregistrantnamechangepage.enterDomainName(strRegisterDomain);
			test.log(LogStatus.INFO, "select the TLD");
			mitregistrantnamechangepage.selectTld();
			test.log(LogStatus.INFO, "Enter the domain password");
			mitregistrantnamechangepage.enterDomainPassword(strDomainPassword);
			test.log(LogStatus.INFO, "Click on Go button");
			mitregistrantnamechangepage.clickOnGoButton();
			test.log(LogStatus.INFO, "Click on Add to cart button on Rsgistrant Name Change page");
			mitregistrantcontactpage=mitregistrantnamechangepage.clickOnAddSelectedDomainToCartButton();
			test.log(LogStatus.INFO, "Click  on continue button on Rsgistrant Name Change page");
			miteligibilitydetailspage=mitregistrantcontactpage.clickContinueButtonForEligibilityDetails();
			
			// Test Step 4: Enter the new eligibility details
			enterNewEligibiltyDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
			
			//Test Step 5: Input credit card details and submit the order 
			enterExistingCardDetails(existingcardnumber);
			
	       //Test Step 6:  Verify if the order is completed,get workflow id and account reference.
			 fetchWorkflowId();
			driver.quit();
			
			// Test Step 7: Process the domain registration order in console admin
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);

			// Test Step 8: Verify if registrantNameChange2 workflow is created or not
			test.log(LogStatus.INFO, "Verify registrant name change2 workflow is created");
			strWorkflowStatus=caworkflowadminpage.getWorkflowStatus("registrantNameChange2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("confirmation requested")
					|| strWorkflowStatus.equalsIgnoreCase("Waiting for client confirmation"));

			driver.quit();
			System.out.println("End Test: verifyDomainRegistrantNameChange");
			}

@Parameters({ "environment"})
@Test
public void verifyCompleteAuChangeOwnership(String environment) throws InterruptedException, AWTException
{
	  // Initialization (Test Data Creation and Assignment)
			strPassword = "comein22";
			accountReference = "MEL-6005";
			String existingcardnumber="Test Visa Number: 4111xxxxxxxx1111 Expiry: 01/22";
			String strEligibilityIDType="ABN";
			String strEligibilityIDNumber="13080859721";
			String strCompanyName="NETREGISTRY PTY LTD";
			String strEligibilityType="Company";
			
			verifyDomainRegistration(environment);
	
			System.out.println("Start Test: verifyDomainRegistrantNameChange");
			test.log(LogStatus.INFO, "Change domain ownership From Customer Portal for " +strTld+ " - STARTED");
			initialization(environment, "customerportalurl_melbourneit");
			
			// Test Step 1: Login to customer portal
			logintoCustomerPortal(accountReference,strPassword);
			
			// Test Step 2: Click on order tab and click on domain ownership link
			test.log(LogStatus.INFO, "Click on order tab");
			mitheaderpage=new MITHeaderPage();
			mitordertabpage=mitheaderpage.clickOnOrderTab();
			test.log(LogStatus.INFO, "Click on domain ownership link");
			mitregistrantnamechangepage=mitordertabpage.clickOnDomainOwnershipLink();
			
			// Test Step 3: Enter the domain details and submit the details
			test.log(LogStatus.INFO, "Enter the domain name");
			mitregistrantnamechangepage.enterDomainName(strRegisterDomain);
			test.log(LogStatus.INFO, "select the TLD");
			mitregistrantnamechangepage.selectTld();
			test.log(LogStatus.INFO, "Enter the domain password");
			mitregistrantnamechangepage.enterDomainPassword(strDomainPassword);
			test.log(LogStatus.INFO, "Click on Go button");
			mitregistrantnamechangepage.clickOnGoButton();
			test.log(LogStatus.INFO, "Click on Add to cart button on Rsgistrant Name Change page");
			mitregistrantcontactpage=mitregistrantnamechangepage.clickOnAddSelectedDomainToCartButton();
			test.log(LogStatus.INFO, "Click  on continue button on Rsgistrant Name Change page");
			miteligibilitydetailspage=mitregistrantcontactpage.clickContinueButtonForEligibilityDetails();
			
			// Test Step 4: Enter the new eligibility details
			enterNewEligibiltyDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
			
			//Test Step 5: Input credit card details and submit the order 
			enterExistingCardDetails(existingcardnumber);
			
	       //Test Step 6:  Verify if the order is completed,get workflow id and account reference.
			 fetchWorkflowId();
			driver.quit();
			
			// Test Step 7: Process the domain registration order in console admin
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);

			// Test Step 8: Verify if registrantNameChange2 workflow is created or not
			test.log(LogStatus.INFO, "Verify registrant name change2 workflow is created");
			strWorkflowStatus=caworkflowadminpage.getWorkflowStatus("registrantNameChange2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("confirmation requested")
					|| strWorkflowStatus.equalsIgnoreCase("Waiting for client confirmation"));

			driver.quit();
			System.out.println("End Test: verifyDomainRegistrantNameChange");
			}

@Parameters({ "environment"})
@Test
public void verifyRegistrantNameChangeWithNewCard(String environment) throws InterruptedException, AWTException
{
	  // Initialization (Test Data Creation and Assignment)
			strPassword = "comein22";
			accountReference = "MEL-6005";
			String cardName="Test Console Regression";
			String cardType="Visa";
			String cardNumber="4111111111111111";
			String expiryDate="11";
			String expiryMonth="2022";
			String cvvNumber="123";
			String strEligibilityIDType="ABN";
			String strEligibilityIDNumber="13080859721";
			String strCompanyName="NETREGISTRY PTY LTD";
			String strEligibilityType="Company";
			
			verifyDomainRegistration(environment);
	
			System.out.println("Start Test: verifyDomainRegistrantNameChange with New Card");
			test.log(LogStatus.INFO, "Change domain ownership From Customer Portal for " +strTld+ " - STARTED");
			initialization(environment, "customerportalurl_melbourneit");
			
			// Test Step 1: Login to customer portal
			logintoCustomerPortal(accountReference,strPassword);
			
			// Test Step 2: Click on order tab and click on domain ownership link
			test.log(LogStatus.INFO, "Click on order tab");
			mitheaderpage=new MITHeaderPage();
			mitordertabpage=mitheaderpage.clickOnOrderTab();
			test.log(LogStatus.INFO, "Click on domain ownership link");
			mitregistrantnamechangepage=mitordertabpage.clickOnDomainOwnershipLink();
			
			// Test Step 3: Enter the domain details and submit the details
			test.log(LogStatus.INFO, "Enter the domain name");
		    mitregistrantnamechangepage.enterDomainName(strRegisterDomain);
			test.log(LogStatus.INFO, "select the TLD");
			mitregistrantnamechangepage.selectTld();
			test.log(LogStatus.INFO, "Enter the domain password");
			mitregistrantnamechangepage.enterDomainPassword(strDomainPassword);
			test.log(LogStatus.INFO, "Click on Go button");
			mitregistrantnamechangepage.clickOnGoButton();
			test.log(LogStatus.INFO, "Click on Add to cart button on Rsgistrant Name Change page");
			mitregistrantcontactpage=mitregistrantnamechangepage.clickOnAddSelectedDomainToCartButton();
			test.log(LogStatus.INFO, "Click  on continue button on Rsgistrant Name Change page");
			miteligibilitydetailspage=mitregistrantcontactpage.clickContinueButtonForEligibilityDetails();
			
			// Test Step 4: Enter the new eligibility details
			enterNewEligibiltyDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
			
			//Test Step 5: Input credit card details and submit the order 
			enterNewCardDetails(cardName,cardType,cardNumber,expiryDate,expiryMonth,cvvNumber);
			
	       //Test Step 6:  Verify if the order is completed,get workflow id and account reference.
			fetchWorkflowId();
			driver.quit();
			
			// Test Step 7: Process the domain registration order in console admin
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);

			// Test Step 8: Verify if registrantNameChange2 workflow is created or not
			test.log(LogStatus.INFO, "Verify registrant name change2 workflow is created");
			strWorkflowStatus=caworkflowadminpage.getWorkflowStatus("registrantNameChange2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("confirmation requested")
					|| strWorkflowStatus.equalsIgnoreCase("Waiting for client confirmation"));

			driver.quit();
			System.out.println("End Test:  verifyDomainRegistrantNameChange with New Card");
			}

@Parameters({ "environment"})
@Test
public void verifyRegistrantNameChangeWithPrepaidCredit(String environment) throws InterruptedException, AWTException
{
	  // Initialization (Test Data Creation and Assignment)
			strPassword = "comein22";
			accountReference = "MEL-6005";
			String strEligibilityIDType="ABN";
			String strEligibilityIDNumber="13080859721";
			String strCompanyName="NETREGISTRY PTY LTD";
			String strEligibilityType="Company";
			
			verifyDomainRegistration(environment);
	
			System.out.println("Start Test: verifyDomainRegistrantNameChange");
			test.log(LogStatus.INFO, "Change domain ownership From Customer Portal for " +strTld+ " - STARTED");
			initialization(environment, "customerportalurl_melbourneit");
			
			// Test Step 1: Login to customer portal
			logintoCustomerPortal(accountReference,strPassword);
			
			// Test Step 2: Click on order tab and click on domain ownership link
			test.log(LogStatus.INFO, "Click on order tab");
			mitheaderpage=new MITHeaderPage();
			mitordertabpage=mitheaderpage.clickOnOrderTab();
			test.log(LogStatus.INFO, "Click on domain ownership link");
			mitregistrantnamechangepage=mitordertabpage.clickOnDomainOwnershipLink();
			
			// Test Step 3: Enter the domain details and submit the details
			test.log(LogStatus.INFO, "Enter the domain name");
			mitregistrantnamechangepage.enterDomainName(strRegisterDomain);
			test.log(LogStatus.INFO, "select the TLD");
			mitregistrantnamechangepage.selectTld();
			test.log(LogStatus.INFO, "Enter the domain password");
			mitregistrantnamechangepage.enterDomainPassword(strDomainPassword);
			test.log(LogStatus.INFO, "Click on Go button");
			mitregistrantnamechangepage.clickOnGoButton();
			test.log(LogStatus.INFO, "Click on Add to cart button on Rsgistrant Name Change page");
			mitregistrantcontactpage=mitregistrantnamechangepage.clickOnAddSelectedDomainToCartButton();
			test.log(LogStatus.INFO, "Click  on continue button on Rsgistrant Name Change page");
			miteligibilitydetailspage=mitregistrantcontactpage.clickContinueButtonForEligibilityDetails();
			
			// Test Step 4: Enter the new eligibility details
			enterNewEligibiltyDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
			
			//Test Step 5: Input credit card details and submit the order 
			enterExistingCardDetails("Prepaid credit");
			
	       //Test Step 6:  Verify if the order is completed,get workflow id and account reference.
			 fetchWorkflowId();
			driver.quit();
			
			// Test Step 7: Process the domain registration order in console admin
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(MIT_CommonFunctionality.strWorkflowId);

			// Test Step 8: Verify if registrantNameChange2 workflow is created or not
			test.log(LogStatus.INFO, "Verify registrant name change2 workflow is created");
			strWorkflowStatus=caworkflowadminpage.getWorkflowStatus("registrantNameChange2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("confirmation requested")
					|| strWorkflowStatus.equalsIgnoreCase("Waiting for client confirmation"));

			driver.quit();
			System.out.println("End Test: verifyDomainRegistrantNameChange");
			}
}
