package com.NRG.CustomerPortal.testcases;

import java.awt.AWTException;
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
import com.consolesmui.pages.CSMUIDomainNamePage;
import com.consolesmui.pages.CSMUITabPage;
import com.netregistryoldwebsite.pages.NRGAccountContactPage;
import com.netregistryoldwebsite.pages.NRGAddDomainPrivacyPage;
import com.netregistryoldwebsite.pages.NRGBillingPage;
import com.netregistryoldwebsite.pages.NRGDomainDetailsPage;
import com.netregistryoldwebsite.pages.NRGDomainSearchPage;
import com.netregistryoldwebsite.pages.NRGEligibilityDetailsPage;
import com.netregistryoldwebsite.pages.NRGHeaderPage;
import com.netregistryoldwebsite.pages.NRGHostingAndExtrasPage;
import com.netregistryoldwebsite.pages.NRGLoginPage;
import com.netregistryoldwebsite.pages.NRGOnlineOrderPage;
import com.netregistryoldwebsite.pages.NRGOrderCompletePage;
import com.netregistryoldwebsite.pages.NRGRegistrantContactPage;
import com.nrgcustomerportal.pages.NRGOrderTabPage;
import com.nrgcustomerportal.pages.NRGRegistrantNameChangePage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class RegistrantNameChange extends NRGOld_CommonFunctionality
{
	NRGLoginPage nrgloginpage;
	NRGDomainDetailsPage nrgdomaindetailspage;
	NRGBillingPage nrgbillingpage;
	NRGOrderCompletePage nrgordercompletepage;
	NRGOnlineOrderPage nrgonlineorderpage;
	NRGDomainSearchPage nrgdomainsearchpage;	
	NRGAddDomainPrivacyPage nrgadddomainprivacypage;
	NRGHostingAndExtrasPage nrghostingandextraspage;
	NRGAccountContactPage nrgaccountcontactpage;
	NRGRegistrantContactPage nrgregistrantcontactpage;
	NRGEligibilityDetailsPage nrgeligibilitydetailspage;
	CADomainLevelPage cadomainlevelpage;
	CSMUITabPage csmuitabpage;
	CSMUIDomainNamePage csmuidomainnamepage;
	NRGHeaderPage nrgheaderpage;
	NRGOrderTabPage nrgordertabpage;
	NRGRegistrantNameChangePage nrgregistrantnamechangepage;
	NRGOld_CommonFunctionality nrgcommonfunctionality;

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
		String strWorkflowStatus = null;
		//String tld[] = {".com", ".net", ".com.au", ".co.nz"};
		String tld[] = {".com.au"};
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleregression" + df.format(d);

		//Test Step 1: Login to shopping cart and place an order for domain registration 
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

			//Test Step 4: Login to console admin and process the domain registration2 workflow.
			loginToConsoleAdmin(environment);
			caheaderpage=new CAHeaderPage();
			caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
			caworkflowadminpage.processWF(NRGOld_CommonFunctionality.strWorkflowId, tlds);

			// Test Step 5: Verify if domain registration workflow is completed
			test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
			caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);
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
		accountReference = "ARQ-45";
		String strExistingCard="Visa Test Number: 4111********1111 Expiry: 01/2024";

		verifyDomainRegistration(environment);

		System.out.println("Start Test: verifyDomainRegistrantNameChange");
		test.log(LogStatus.INFO, "Change domain ownership From Customer Portal for " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_netregistry");

		// Test Step 1: Login to customer portal
		test.log(LogStatus.INFO, "Login to Customer portal - STARTED");
		nrgloginpage = new NRGLoginPage();
		nrgloginpage.setLoginDetails(accountReference, strPassword);
		nrgheaderpage = nrgloginpage.clickLoginButton();
		test.log(LogStatus.INFO, "Login to Reseller portal - COMPLETED");

		// Test Step 2: Click on order tab and click on domain ownership link
		test.log(LogStatus.INFO, "Click on order tab");
		nrgordertabpage=nrgheaderpage.clickOnOrderTab();
		test.log(LogStatus.INFO, "Click on domain ownership link");
		nrgregistrantnamechangepage=nrgordertabpage.clickOnDomainOwnershipLink();

		// Test Step 3: Enter the domain details and submit the details
		test.log(LogStatus.INFO, "Enter the domain name");
		nrgregistrantnamechangepage.enterDomainName(strRegisterDomain);
		test.log(LogStatus.INFO, "select the TLD");
		nrgregistrantnamechangepage.selectTld();
		test.log(LogStatus.INFO, "Enter the domain password");
		nrgregistrantnamechangepage.enterDomainPassword(strDomainPassword);
		test.log(LogStatus.INFO, "Click on continue button");
		nrgregistrantnamechangepage.clickOnContinueButton();
		test.log(LogStatus.INFO, "Click on Add to cart button");
		nrgregistrantcontactpage=nrgregistrantnamechangepage.clickOnAddSelectedDomainToCartButton();
		test.log(LogStatus.INFO, "Click  on continue button");
		nrgeligibilitydetailspage=nrgregistrantcontactpage.clickContinueButtonForEligibilityDetails();

		// Test Step 4: Enter the new eligibility details
		setEligibilityDetails("ABN","13080859721","NETREGISTRY PTY LTD","Company");

		//Test Step 5: Input credit card details and submit the order 
		selectExistingCard(strExistingCard);

		//Test Step 6:  Verify if the order is completed,get workflow id and account reference.
		fetchRefrenceAndWorkflowId();
		driver.quit();

		// Test Step 7: Process the domain registration order in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);

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
		accountReference = "ARQ-45";

		verifyDomainRegistration(environment);

		System.out.println("Start Test: verifyDomainRegistrantNameChange");
		test.log(LogStatus.INFO, "Change domain ownership From Customer Portal for " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_netregistry");

		// Test Step 1: Login to customer portal
		test.log(LogStatus.INFO, "Login to Customer portal - STARTED");
		nrgloginpage = new NRGLoginPage();
		nrgloginpage.setLoginDetails(accountReference, strPassword);
		nrgheaderpage = nrgloginpage.clickLoginButton();
		test.log(LogStatus.INFO, "Login to Reseller portal - COMPLETED");

		// Test Step 2: Click on order tab and click on domain ownership link
		test.log(LogStatus.INFO, "Click on order tab");
		nrgordertabpage=nrgheaderpage.clickOnOrderTab();
		test.log(LogStatus.INFO, "Click on domain ownership link");
		nrgregistrantnamechangepage=nrgordertabpage.clickOnDomainOwnershipLink();

		// Test Step 3: Enter the domain details and submit the details
		test.log(LogStatus.INFO, "Enter the domain name");
		nrgregistrantnamechangepage.enterDomainName(strRegisterDomain);
		test.log(LogStatus.INFO, "select the TLD");
		nrgregistrantnamechangepage.selectTld();
		test.log(LogStatus.INFO, "Enter the domain password");
		nrgregistrantnamechangepage.enterDomainPassword(strDomainPassword);
		test.log(LogStatus.INFO, "Click on continue button");
		nrgregistrantnamechangepage.clickOnContinueButton();
		test.log(LogStatus.INFO, "Click on Add to cart button");
		nrgregistrantcontactpage=nrgregistrantnamechangepage.clickOnAddSelectedDomainToCartButton();
		test.log(LogStatus.INFO, "Click  on continue button");
		nrgeligibilitydetailspage=nrgregistrantcontactpage.clickContinueButtonForEligibilityDetails();

		// Test Step 4: Enter the new eligibility details
		setEligibilityDetails("ABN","13080859721","NETREGISTRY PTY LTD","Company");

		//Test Step 5: Input credit card details and submit the order 
		createNewCreditCardDetailsforExistingCustomer("Test Console Regression","4111111111111111","11","2022","123");

		//Test Step 6:  Verify if the order is completed,get workflow id and account reference.
		fetchRefrenceAndWorkflowId();
		driver.quit();

		// Test Step 7: Process the domain registration order in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);

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
	public void verifyRegistrantNameChangewithNewCard(String environment) throws InterruptedException, AWTException
	{
		// Initialization (Test Data Creation and Assignment)
		strPassword = "comein22";
		accountReference = "ARQ-45";

		verifyDomainRegistration(environment);

		System.out.println("Start Test: verifyDomainRegistrantNameChange");
		test.log(LogStatus.INFO, "Change domain ownership From Customer Portal for " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_netregistry");

		// Test Step 1: Login to customer portal
		test.log(LogStatus.INFO, "Login to Customer portal - STARTED");
		nrgloginpage = new NRGLoginPage();
		nrgloginpage.setLoginDetails(accountReference, strPassword);
		nrgheaderpage = nrgloginpage.clickLoginButton();
		test.log(LogStatus.INFO, "Login to Reseller portal - COMPLETED");

		// Test Step 2: Click on order tab and click on domain ownership link
		test.log(LogStatus.INFO, "Click on order tab");
		nrgordertabpage=nrgheaderpage.clickOnOrderTab();
		test.log(LogStatus.INFO, "Click on domain ownership link");
		nrgregistrantnamechangepage=nrgordertabpage.clickOnDomainOwnershipLink();

		// Test Step 3: Enter the domain details and submit the details
		test.log(LogStatus.INFO, "Enter the domain name");
		nrgregistrantnamechangepage.enterDomainName(strRegisterDomain);
		test.log(LogStatus.INFO, "select the TLD");
		nrgregistrantnamechangepage.selectTld();
		test.log(LogStatus.INFO, "Enter the domain password");
		nrgregistrantnamechangepage.enterDomainPassword(strDomainPassword);
		test.log(LogStatus.INFO, "Click on continue button");
		nrgregistrantnamechangepage.clickOnContinueButton();
		test.log(LogStatus.INFO, "Click on Add to cart button");
		nrgregistrantcontactpage=nrgregistrantnamechangepage.clickOnAddSelectedDomainToCartButton();
		test.log(LogStatus.INFO, "Click  on continue button");
		nrgeligibilitydetailspage=nrgregistrantcontactpage.clickContinueButtonForEligibilityDetails();

		// Test Step 4: Enter the new eligibility details
		setEligibilityDetails("ABN","13080859721","NETREGISTRY PTY LTD","Company");

		//Test Step 5: Input credit card details and submit the order 
		createNewCreditCardDetailsforExistingCustomer("Test Console Regression","4111111111111111","11","2022","123");

		//Test Step 6:  Verify if the order is completed,get workflow id and account reference.
		driver.quit();
		System.out.println("End Test: verifyDomainRegistrantNameChange");
	}

	@Parameters({ "environment"})
	@Test
	public void verifyCompleteAuChangeOwnership(String environment) throws InterruptedException, AWTException
	{
		// Initialization (Test Data Creation and Assignment)
		strPassword = "comein22";
		accountReference = "ARQ-45";
		String strExistingCard="Visa Test Number: 4111********1111 Expiry: 01/2024";

		verifyDomainRegistration(environment);

		System.out.println("Start Test: verifyDomainRegistrantNameChange");
		test.log(LogStatus.INFO, "Change domain ownership From Customer Portal for " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_netregistry");

		// Test Step 1: Login to customer portal
		test.log(LogStatus.INFO, "Login to Customer portal - STARTED");
		nrgloginpage = new NRGLoginPage();
		nrgloginpage.setLoginDetails(accountReference, strPassword);
		nrgheaderpage = nrgloginpage.clickLoginButton();
		test.log(LogStatus.INFO, "Login to Reseller portal - COMPLETED");

		// Test Step 2: Click on order tab and click on domain ownership link
		test.log(LogStatus.INFO, "Click on order tab");
		nrgordertabpage=nrgheaderpage.clickOnOrderTab();
		test.log(LogStatus.INFO, "Click on domain ownership link");
		nrgregistrantnamechangepage=nrgordertabpage.clickOnDomainOwnershipLink();

		// Test Step 3: Enter the domain details and submit the details
		test.log(LogStatus.INFO, "Enter the domain name");
		nrgregistrantnamechangepage.enterDomainName(strRegisterDomain);
		test.log(LogStatus.INFO, "select the TLD");
		nrgregistrantnamechangepage.selectTld();
		test.log(LogStatus.INFO, "Enter the domain password");
		nrgregistrantnamechangepage.enterDomainPassword(strDomainPassword);
		test.log(LogStatus.INFO, "Click on continue button");
		nrgregistrantnamechangepage.clickOnContinueButton();
		test.log(LogStatus.INFO, "Click on Add to cart button");
		nrgregistrantcontactpage=nrgregistrantnamechangepage.clickOnAddSelectedDomainToCartButton();
		test.log(LogStatus.INFO, "Click  on continue button");
		nrgeligibilitydetailspage=nrgregistrantcontactpage.clickContinueButtonForEligibilityDetails();

		// Test Step 4: Enter the new eligibility details
		setEligibilityDetails("ABN","13080859721","NETREGISTRY PTY LTD","Company");

		//Test Step 5: Input credit card details and submit the order 
		selectExistingCard(strExistingCard);

		//Test Step 6:  Verify if the order is completed,get workflow id and account reference.
		fetchRefrenceAndWorkflowId();
		driver.quit();

		// Test Step 7: Process the domain registration order in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);

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
	public void verifyRegistrantNameChangewithPrepaidCredit(String environment) throws InterruptedException, AWTException
	{
		// Initialization (Test Data Creation and Assignment)
		strPassword = "comein22";
		accountReference = "ARQ-45";

		verifyDomainRegistration(environment);

		System.out.println("Start Test: verifyDomainRegistrantNameChange");
		test.log(LogStatus.INFO, "Change domain ownership From Customer Portal for " +strTld+ " - STARTED");
		initialization(environment, "customerportalurl_netregistry");

		// Test Step 1: Login to customer portal
		test.log(LogStatus.INFO, "Login to Customer portal - STARTED");
		nrgloginpage = new NRGLoginPage();
		nrgloginpage.setLoginDetails(accountReference, strPassword);
		nrgheaderpage = nrgloginpage.clickLoginButton();
		test.log(LogStatus.INFO, "Login to Reseller portal - COMPLETED");

		// Test Step 2: Click on order tab and click on domain ownership link
		test.log(LogStatus.INFO, "Click on order tab");
		nrgordertabpage=nrgheaderpage.clickOnOrderTab();
		test.log(LogStatus.INFO, "Click on domain ownership link");
		nrgregistrantnamechangepage=nrgordertabpage.clickOnDomainOwnershipLink();

		// Test Step 3: Enter the domain details and submit the details
		test.log(LogStatus.INFO, "Enter the domain name");
		nrgregistrantnamechangepage.enterDomainName(strRegisterDomain);
		test.log(LogStatus.INFO, "select the TLD");
		nrgregistrantnamechangepage.selectTld();
		test.log(LogStatus.INFO, "Enter the domain password");
		nrgregistrantnamechangepage.enterDomainPassword(strDomainPassword);
		test.log(LogStatus.INFO, "Click on continue button");
		nrgregistrantnamechangepage.clickOnContinueButton();
		test.log(LogStatus.INFO, "Click on Add to cart button");
		nrgregistrantcontactpage=nrgregistrantnamechangepage.clickOnAddSelectedDomainToCartButton();
		test.log(LogStatus.INFO, "Click  on continue button");
		nrgeligibilitydetailspage=nrgregistrantcontactpage.clickContinueButtonForEligibilityDetails();

		// Test Step 4: Enter the new eligibility details
		setEligibilityDetails("ABN","13080859721","NETREGISTRY PTY LTD","Company");

		//Test Step 5: Input credit card details and submit the order 
		selectPrepaidCredit("Prepaid credit");

		//Test Step 6:  Verify if the order is completed,get workflow id and account reference.
		test.log(LogStatus.INFO, "Verify if recaptcha is displayed on click of continue button ");
		Assert.assertTrue(nrgbillingpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
		test.log(LogStatus.INFO, "Verification of doamain registration - COMPLETED ");
		fetchRefrenceAndWorkflowId();
		driver.quit();

		// Test Step 7: Process the domain registration order in console admin
		loginToConsoleAdmin(environment);
		caheaderpage=new CAHeaderPage();
		caworkflowadminpage = caheaderpage.searchWorkflow(NRGOld_CommonFunctionality.strWorkflowId);

		// Test Step 8: Verify if registrantNameChange2 workflow is created or not
		test.log(LogStatus.INFO, "Verify registrant name change2 workflow is created");
		strWorkflowStatus=caworkflowadminpage.getWorkflowStatus("registrantNameChange2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("confirmation requested")
				|| strWorkflowStatus.equalsIgnoreCase("Waiting for client confirmation"));

		driver.quit();
		System.out.println("End Test: verifyDomainRegistrantNameChange");
	}
}

