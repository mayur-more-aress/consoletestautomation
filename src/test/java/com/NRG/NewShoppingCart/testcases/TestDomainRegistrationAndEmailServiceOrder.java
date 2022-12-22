package com.NRG.NewShoppingCart.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.base.Environment;
import com.base.TestBase;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.netregistrynewwebsite.pages.NRGNSAboutYouPage;
import com.netregistrynewwebsite.pages.NRGNSAddServicesToYourDomainPage;
import com.netregistrynewwebsite.pages.NRGNSDomainPrivacyPage;
import com.netregistrynewwebsite.pages.NRGNSEligibilityDetailsPage;
import com.netregistrynewwebsite.pages.NRGNSEmailAndOffice365PackagesPage;
import com.netregistrynewwebsite.pages.NRGNSOffice365LicenseQuantityPage;
import com.netregistrynewwebsite.pages.NRGNSOrderCompletePage;
import com.netregistrynewwebsite.pages.NRGNSRegistrantContactPage;
import com.netregistrynewwebsite.pages.NRGNSReviewAndPaymentPage;
import com.netregistrynewwebsite.pages.NRGNSSearchAddDomainsPage;
import com.netregistrynewwebsite.pages.NRGNSSearchFieldPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class TestDomainRegistrationAndEmailServiceOrder extends TestBase 
{
	// Netregistry New Shopping Cart Pages
	NRGNSAboutYouPage nrgnsaboutyoupage;
	NRGNSAddServicesToYourDomainPage nrgnsaddservicestoyourdomainpage;
	NRGNSDomainPrivacyPage nrgnsdomainprivacypage;
	NRGNSEmailAndOffice365PackagesPage nrgnsemailandoffice365packagespage;
	NRGNSOffice365LicenseQuantityPage nrgnsoffice365licensequantitypage;
	NRGNSRegistrantContactPage nrgnsregistrantcontactpage;
	NRGNSReviewAndPaymentPage nrgnsreviewandpaymentpage;
	NRGNSSearchAddDomainsPage nrgnssearchadddomainspage;
	NRGNSSearchFieldPage nrgnssearchfieldpage;
	NRGNSOrderCompletePage nrgnsordercompletepage;
	NRGNSEligibilityDetailsPage nrgnseligibilitypage;
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;

	TestUtil testUtil;
	static Environment testenvironment;
	public static ExtentTest logger;

	public TestDomainRegistrationAndEmailServiceOrder()  
	{
		super();	
	}
	
	// Initialization (Test Data Creation and Assignment)
	String strDomainName = null;
	String strMaskedCardNumber = null;
	String strCustomerAccountReference = null;
	String strAccountReference = null;
	String strWorkflowId = null;
	String strCustomerPassword = null;
	String strEligibilityIDType = null;
	String strEligibilityIDNumber = null;
	String strEligibilityType = null;
	String strTld = null;
	String strEmailService = null;
	String strproductYear = null;
	String Domaininformation ="Have a business idea and reserving a domain for the future";
	String arrtTds[] = {".net"}; 
	String arrEmailServices[] = {"Email Essentials"};
	List<String> privacytlds = Arrays.asList(".com", ".net");
	
	/*
	 * Test case to verify domain registration with email service for
	 *  Existing Customer Scenario Using Existing card
	 */	
	@Parameters({ "environment"})
	@Test 
	public void verifyDomainRegistrationWithEmailServiceForExistingCustomerUsingExistingCard(String environment) throws Exception 
	{           
		strEligibilityIDType = "ABN";
		strEligibilityIDNumber = "13080859721";
		strEligibilityType = "Company";
		strMaskedCardNumber = "************4444"; 
		strCustomerAccountReference = "TES-2168";
		strCustomerPassword = "comein22";
		strproductYear = "1 Year";
		
	    // Iterating over an array of tld's to be tested
	    for (String tld : arrtTds) 
	    { 
	    	System.out.println("Start Test: verifyDomainRegistrationWithEmailServiceForExistingCustomerUsingExistingCard");
			test.log(LogStatus.INFO, "VerifyDomainRegistrationWithEmailServiceForExistingCustomerUsingExistingCard - STARTED");
	    	for(String emailservice : arrEmailServices)
	    	{
	    		strTld = tld;
	    		strEmailService = emailservice;
	    		System.out.print(strTld + " "); 
	            DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
				Date d = new Date();
				strDomainName = "O365test" + df.format(d);
				System.out.println("Start Test: VerifyDomainRegistrationWithEmailServiceForExistingCustomerUsingExistingCard for " +strTld+ " domain and " +strEmailService+ 
						" email service -STARTED");
				test.log(LogStatus.INFO, "VerifyDomainRegistrationWithEmailServiceForExistingCustomerUsingExistingCard for " +strTld+ " domain and" +strEmailService+ " "
						+ "email service -STARTED");
				
				// Test Step 1: Navigate to domain search page of new shopping cart and place an order for a test domain
				test.log(LogStatus.INFO, "Navigate to domain search page -STARTED");
				initialization(environment, "newcart_domainsearchurl_netregistry");
				nrgnssearchadddomainspage = new NRGNSSearchAddDomainsPage();
				nrgnssearchadddomainspage.setDomainNameAndTld(strDomainName, strTld);
				nrgnssearchadddomainspage.clickSearchButton();
				nrgnssearchadddomainspage.addDomainName(strDomainName, strTld);
				test.log(LogStatus.INFO, "Navigate to domain search page -COMPLETED");
				
				//Test Step 2: Domain privacy page for tld's
				if(privacytlds.contains(strTld))
				{
				    test.log(LogStatus.INFO, "Navigate to domain privacy page -STARTED");
					nrgnsdomainprivacypage = nrgnssearchadddomainspage.clickContinueButton();
					testStepResultVerification(NRGNSDomainPrivacyPage.checkBox);
					nrgnsdomainprivacypage.clickCheckBox();
					nrgnsemailandoffice365packagespage = nrgnsdomainprivacypage.clickContinueButton();
					test.log(LogStatus.INFO, "Navigate to domain privacy page -COMPLETED");
				}
				else 
				{
					nrgnsemailandoffice365packagespage = nrgnssearchadddomainspage.clickContinueButtonToEmailHosting();
				}
				
				// Test Step 3: Navigate to Email hosting page
				test.log(LogStatus.INFO, "Process the order page -STARTED");
				nrgnsemailandoffice365packagespage.addOffice365Product(strEmailService, strproductYear);
				nrgnsaboutyoupage = nrgnsemailandoffice365packagespage.clickContinueButton();
				testStepResultVerification(NRGNSAboutYouPage.loginButton);
				test.log(LogStatus.INFO, "Process the order page -COMPLETED");

				// Test Step 4: Login as returning or existing netregistry customer
				test.log(LogStatus.INFO, " Login as returning customer -STARTED");
				nrgnsaboutyoupage.setReturningCustomerContacts(strCustomerAccountReference, strCustomerPassword);
				nrgnsregistrantcontactpage = nrgnsaboutyoupage.clickLoginButton();
				nrgnsregistrantcontactpage.clickDomainInformation(Domaininformation);
				nrgnsregistrantcontactpage.clickRegistrantContact();
				testStepResultVerification(NRGNSRegistrantContactPage.continueButton);
				test.log(LogStatus.INFO, "Login as returning customer -COMPLETED");
				
				//Test Step 5: Check and enter .au eligibility details
				if(strTld.equals(".com.au"))
				{
				    test.log(LogStatus.INFO, "Navigate to add eligibility details page -STARTED");
					nrgnseligibilitypage = nrgnsregistrantcontactpage.clickContinueButtonToAuEligibility();
					nrgnseligibilitypage.setEligibilityDetails(strEligibilityIDType, strEligibilityIDNumber, strEligibilityType);
					nrgnseligibilitypage.tickCertifyDomainHasCloseAndSubstantialConnection();
					nrgnseligibilitypage.tickTermsAndConditions();
					nrgnsaddservicestoyourdomainpage = nrgnseligibilitypage.clickContinueButton();
					test.log(LogStatus.INFO, "Navigate to add eligibility details page -COMPLETED");
				}
				else
				{
					 nrgnsaddservicestoyourdomainpage = nrgnsregistrantcontactpage.clickContinueButton();
				}
				 
				// Test Step 6: Navigate to Services page
				test.log(LogStatus.INFO, "Process the add services page -STARTED");
				nrgnsreviewandpaymentpage = nrgnsaddservicestoyourdomainpage.clickContinueButton();
				test.log(LogStatus.INFO, "Process the add services page -COMPLETED");

				// Test Step 7: Select existing credit card and complete the order
				test.log(LogStatus.INFO, "Select existing credit card -STARTED");
				nrgnsreviewandpaymentpage.selectExistingCreditCard(strMaskedCardNumber);
				nrgnsreviewandpaymentpage.tickTermsAndConditions();
				testStepResultVerification(NRGNSReviewAndPaymentPage.completeOrderButton);
				nrgnsordercompletepage = nrgnsreviewandpaymentpage.clickCompleteOrder();
				test.log(LogStatus.INFO, "Select existing credit card  -COMPLETED");

				// Test Step 8: Verify if the order is completed, get workflow id and account reference.
				test.log(LogStatus.INFO, "Verify if the order is completed -STARTED");
				Assert.assertTrue(nrgnsordercompletepage.isOrderComplete(), "Order is not completed");
				strWorkflowId = nrgnsordercompletepage.getSingleReferenceID();
				testStepResultVerification(NRGNSOrderCompletePage.accountReferenceElement);
				test.log(LogStatus.INFO, "Verify if the order is completed  -COMPLETED");
				strAccountReference = nrgnsordercompletepage.getAccountReferenceID();
				System.out.println("Account Reference:" + strAccountReference);
				System.out.println("Reference ID[0]:" + strWorkflowId);
				driver.quit();

				// Test Step 9: Process the domain registration order in console admin
				initialization(environment, "consoleadmin");
				caloginpage = new CALoginPage();
				caheaderpage = caloginpage.setDefaultLoginDetails(environment);
				caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
				
				// Process and verify if domain registration workflow is completed in console admin
				caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, strTld);
				// Process the productsetup2 workflow and verify if productsetup2 workflow is approved in console admin
				caworkflowadminpage.processProductSetup2();
				caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + strTld);
				Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"),
						"domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));

				
				//caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + strTld);
				//Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "approved",caworkflowadminpage.getWorkflowStatus("productsetup2"));

				// Verify if productSetupOffice365 workflow will fail in Add/Verify O365 domain step
			/*	if (environment.equals("uat1") || environment.equals("dev2"))
				{
					caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + strTld);
					Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetupOffice365"), "Add domain failed",caworkflowadminpage.getWorkflowStatus("o365Provisioning"));				
				}
				driver.quit();*/
				
				System.out.println("End Test: VerifyDomainRegistrationWithEmailServiceForExistingCustomerUsingExistingCard for " +strTld+ " domain and " +strEmailService+ 
						" email service -COMPLETED");
				test.log(LogStatus.INFO, "VerifyDomainRegistrationWithEmailServiceForExistingCustomerUsingExistingCard for " +strTld+ " domain and" +strEmailService+ " "
						+ "email service -COMPLETED");
	    	}
	    	driver.quit();
	    	test.log(LogStatus.INFO, "VerifyDomainRegistrationWithEmailServiceForExistingCustomerUsingExistingCard - COMPLETED");
	    	System.out.println("End Test: verifyDomainRegistrationWithEmailServiceForExistingCustomerUsingExistingCard");
	    } 
	}
}
