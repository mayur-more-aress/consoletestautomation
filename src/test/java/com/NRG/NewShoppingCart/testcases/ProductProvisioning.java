package com.NRG.NewShoppingCart.testcases;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.netregistrynewwebsite.pages.NRGNSAddServicesToYourDomainPage;
import com.netregistrynewwebsite.pages.NRGNSEmailAndOffice365PackagesPage;
import com.netregistrynewwebsite.pages.NRGNSOrderCompletePage;
import com.netregistrynewwebsite.pages.NRGNSOrderPage;
import com.netregistrynewwebsite.pages.NRGNSRegistrantContactPage;
import com.netregistrynewwebsite.pages.NRGNSReviewAndPaymentPage;
import com.netregistryoldwebsite.pages.NRGHeaderPage;
import com.netregistryoldwebsite.pages.NRGLoginPage;
import com.relevantcodes.extentreports.LogStatus;
import com.util.DatabaseConnection;

public class ProductProvisioning extends TestBase
{
	public ProductProvisioning()  
	{
		super();	
	}
	
	//NRG Customer portal pages
	NRGLoginPage nrgloginpage;
	NRGHeaderPage nrgheaderpage;
	NRGNSOrderPage nrgnsorderpage;
	
	//NRG new shopping cart pages
	NRGNSEmailAndOffice365PackagesPage nrgnsemailandoffice365packagespage;
	NRGNSRegistrantContactPage nrgnsregistrantcontactpage;
	NRGNSAddServicesToYourDomainPage nrgnsaddservicestoyourdomainpage;
	NRGNSReviewAndPaymentPage nrgnsreviewandpaymentpage;
	NRGNSOrderCompletePage nrgnsordercompletepage;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	
	//test Data initialization
	String strDomainName = null;
	String strTld = null;
	String strPassword = null;
	String strCustomerAccountReference = null;
	String strCustomerPassword = null;
	String strAccountReference = null;
	String strRefrenceId = null;
	String strProductYear = null;
	String Domaininformation ="Have a business idea and reserving a domain for the future";
	
	
	/*
	 * Test case to purchase Web hosting on its own via new cart
	 * Product : Webhosting - Cpanel starter
	 * Scenario : Existing customer using prepaid credit
	 */
	@Parameters({ "environment"})
	@Test 
	public void verifyProductProvisioningUsingPrepaid(String environment) throws Exception 
	{
		// Initialization
		strCustomerAccountReference ="TES-2168";
		strPassword = "comein22";
		strProductYear = "1 Year";
		String strTLd = "com";
		
		//Test step 1: Fetch the registered domain from database
		test.log(LogStatus.INFO, "Edit the product expiry date in the database - STARTED");
		DatabaseConnection.connectToDatabase();
		String registeredDomainName = DatabaseConnection.fetchRegisteredDomainName("Netregistry",strCustomerAccountReference,strTLd);
		String testdata[]=registeredDomainName.split("\\.",2);
		strDomainName = testdata[0];
		System.out.println("Domain Name: " + strDomainName);
		strTld = "."+testdata[1];
		System.out.println("Domain tld: " + strTld);
		
		// Test Step 2: Login to customer portal
		initialization(environment, "customerportalurl_netregistry");
		test.log(LogStatus.INFO, "Login to Customer portal - STARTED");
		nrgloginpage = new NRGLoginPage();
		nrgloginpage.setLoginDetails(strCustomerAccountReference, strPassword);
		nrgheaderpage = nrgloginpage.clickLoginButton();
		test.log(LogStatus.INFO, "Login to Customer portal - COMPLETED");
		
		// Test Step 3: click order tab and select web hosting product
		test.log(LogStatus.INFO, "Order Web hosting - STARTED");
		nrgnsorderpage = nrgheaderpage.clickOrderTab();
		nrgnsorderpage.selectWebhosting(strProductYear);
		nrgnsemailandoffice365packagespage = nrgnsorderpage.enterDomainName(strDomainName+strTld);
		nrgnsregistrantcontactpage = nrgnsemailandoffice365packagespage.clickContinueButtonToRegContact();
		test.log(LogStatus.INFO, "Order Web hosting - COMPLETED");
		
		// Test Step 4: Navigate to email hosting page and click continue
		test.log(LogStatus.INFO, "Enter registrant contact info - STARTED");
		nrgnsregistrantcontactpage.clickDomainInformation(Domaininformation);
		nrgnsregistrantcontactpage.clickRegistrantContact();
		testStepResultVerification(NRGNSRegistrantContactPage.continueButton);
		nrgnsreviewandpaymentpage = nrgnsregistrantcontactpage.clickContinueButtonToPayment();
		test.log(LogStatus.INFO, "Enter registrant contact info - COMPLETED");
		
		// Test Step 5: Select prepaid account and complete the order
		test.log(LogStatus.INFO, "Select existing prepaid card -STARTED");
		nrgnsreviewandpaymentpage.selectPrepaidAccount();
		nrgnsreviewandpaymentpage.tickTermsAndConditions();
		testStepResultVerification(NRGNSReviewAndPaymentPage.completeOrderButton);
		nrgnsordercompletepage = nrgnsreviewandpaymentpage.clickCompleteOrder();
		test.log(LogStatus.INFO, "Select existing prepaid card  -COMPLETED");
		
		// Test Step 6: Verify if the order is completed, get workflow id and account reference.
		test.log(LogStatus.INFO, "Verify if the order is completed -STARTED");
		Assert.assertTrue(nrgnsordercompletepage.isOrderComplete(), "Order is not completed");
		strAccountReference = nrgnsordercompletepage.getAccountReferenceID();
		strRefrenceId = nrgnsordercompletepage.getSingleReferenceID();
		testStepResultVerification(NRGNSOrderCompletePage.accountReferenceElement);
		System.out.println("Account Reference:" + strAccountReference);
		System.out.println("Account Reference:" + strRefrenceId);
		test.log(LogStatus.INFO, "Verify if the order is completed -COMPLETED");
		driver.quit();
		
		// Test Step 7: Process and verify if productsetup2 workflow is approved in console admin
		test.log(LogStatus.INFO, "Login to A2 admin and execute WF - STARTED");
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		test.log(LogStatus.INFO, "Verify if domain registration worzkflow is completed");
		caworkflowadminpage = caheaderpage.searchWorkflow(strRefrenceId);
		if(caworkflowadminpage.getWorkflowStatus("productSetup2").equalsIgnoreCase("pending"))
		{
			caworkflowadminpage.processProductSetup2ByWFID(strRefrenceId);
		}
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + strTld);
		//Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "approved",caworkflowadminpage.getWorkflowStatus("productsetup2"));
		test.log(LogStatus.INFO, "Login to A2 admin and execute WF - COMPLETED");
		driver.quit();
	}
	
}
