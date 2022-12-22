package com.salesdb.testcases;
import java.awt.AWTException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.base.TestBase;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAViewCreditCardsPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.consolesalesdb.pages.CSAUEligibilityPage;
import com.consolesalesdb.pages.CSCreateDomainWindowPage;
import com.consolesalesdb.pages.CSLoginPage;
import com.consolesalesdb.pages.CSNrCRMPage;
import com.consolesalesdb.pages.CSProcessTransactionPage;
import com.consolesalesdb.pages.CSRegistrantDetailsPage;
import com.consolesalesdb.pages.CSShowDomainServicesPage;
import com.consolesalesdb.pages.CSWorkflowNotificationPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class CreditCardOperation extends TestBase{

	
	//Console Sales DB Pages
	CSLoginPage csloginpage;
	CSNrCRMPage csnrcrmpage;
	CSCreateDomainWindowPage cscreatedomainwindowpage;
	CSRegistrantDetailsPage csregistrantdetailspage;
	CSShowDomainServicesPage csshowdomainservicespage;
	CSWorkflowNotificationPage csworkflownotificationpage;
	CSAUEligibilityPage csaueligibilitypage;
	CSProcessTransactionPage csprocesstransactionpage;
	CAViewCreditCardsPage caviewcreditdetailspage;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;

	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public CreditCardOperation() {
		super();
	}
	

	@Parameters({ "environment" })
	@Test
	public void verify_addCreditCard_InSalesDB(String environment) throws InterruptedException, AWTException {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strWorkflowId = null;
		String strWorkflowStatus = null;
		String strTld[] = {"net"};
		String strRegistrationPeriod = "2";
		String strGreenCode = "MEL-6040";
		String strPaymentMethod = "Invoice";
		String strPhoneNumber = "+61.299340501";
		String strRegistrantDetails = "QA Team";
		String strRegistrantType = "ABN";
		String strRegistrantNumber = "21073716793";
		
		String strinvoicenumber = "12276768";
		String strtransactiontype = "PAYMENT";
		String stramount = "49.0";
		String strcardnumber = null;
		String strPaymentType = "Credit Card on file or new card";
		
		String cardholdername="DemoTest";
		String cardnumber="4111111111111111";
		String cardexpiry="0626";
        String cardtype="Visa";

		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);

		// Test Step 1: Login to sales db and place an order for domain registration
	System.out.println("Start Test: verify_addCreditCard_InSalesDB");
		for(String tld : strTld) {
			initialization(environment, "salesdburl");
			csloginpage = new CSLoginPage();
			csloginpage.setDefaultLoginDetails(environment);
			csnrcrmpage = csloginpage.clickLoginButton();
			csnrcrmpage.setGreenCode(strGreenCode);
			cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
			cscreatedomainwindowpage.setDomainDetails(strDomainName, tld, strRegistrationPeriod, strPaymentMethod);
			csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
			
			if(tld=="com.au") {
				csaueligibilitypage = new CSAUEligibilityPage();
			csaueligibilitypage.setContactAndEligibilityDetails(strRegistrantDetails, strPhoneNumber,
					strRegistrantType, strRegistrantNumber);
			}else {
				csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
			}
			
			csworkflownotificationpage = csnrcrmpage.clickConfirmDomain(strDomainName);
			strWorkflowId = csworkflownotificationpage.getWorkflowID();
			csworkflownotificationpage.clickOKButton();
			driver.quit();

		// Test Step 2: Process the domain registration order in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, tld);
		
		// Test Step 3: Verify if domain registration workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("update star rating"));
		driver.quit();
		
	    initialization(environment, "salesdburl");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails(environment);
		csnrcrmpage = csloginpage.clickLoginButton();
		csprocesstransactionpage = csnrcrmpage.clickAccountTab();
		csprocesstransactionpage.setProcessTransactiondetails(strGreenCode,strinvoicenumber, strtransactiontype, stramount,  strcardnumber, strPaymentType, cardholdername,  cardnumber,  cardexpiry,  cardtype);
		
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		Thread.sleep(5000);
		caheaderpage.searchAccountReference(strGreenCode);
		caheaderpage.clickViewBillingAccountLink();
		driver.quit();
		}
	}
}
	

