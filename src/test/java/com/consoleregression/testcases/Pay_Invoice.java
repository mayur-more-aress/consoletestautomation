package com.consoleregression.testcases;

import java.awt.AWTException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CAInvoicesPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CATaxInvoicePage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.consolesalesdb.pages.CSAUEligibilityPage;
import com.consolesalesdb.pages.CSCreateDomainWindowPage;
import com.consolesalesdb.pages.CSLoginPage;
import com.consolesalesdb.pages.CSNrCRMPage;
import com.consolesalesdb.pages.CSProcessTransactionPage;
import com.consolesalesdb.pages.CSRegistrantDetailsPage;
import com.consolesalesdb.pages.CSShowDomainServicesPage;
import com.consolesalesdb.pages.CSWorkflowNotificationPage;
import com.domainzwebsite.pages.DMZBillingPage;
import com.domainzwebsite.pages.DMZHeaderPage;
import com.domainzwebsite.pages.DMZLoginPage;
import com.mitdpscustomerportal.pages.MITDPSBillingPage;
import com.mitdpscustomerportal.pages.MITDPSHeaderPage;
import com.mitdpscustomerportal.pages.MITDPSLoginPage;
import com.netregistryoldwebsite.pages.NRGBillingPage;
import com.netregistryoldwebsite.pages.NRGHeaderPage;
import com.netregistryoldwebsite.pages.NRGLoginPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class Pay_Invoice extends TestBase
{
	//Console Sales DB Pages
	CSLoginPage csloginpage;
	CSNrCRMPage csnrcrmpage;
	CSCreateDomainWindowPage cscreatedomainwindowpage;
	CSRegistrantDetailsPage csregistrantdetailspage;
	CSShowDomainServicesPage csshowdomainservicespage;
	CSWorkflowNotificationPage csworkflownotificationpage;
	CSAUEligibilityPage csaueligibilitypage;
	CSProcessTransactionPage csprocesstransactionpage;
	
	//NRG SMUI Pages
	NRGLoginPage nrgloginpage;
	NRGHeaderPage nrgheaderpage;
	NRGBillingPage nrgbillingpage;
	
	//Domainz SMUI Pages
	DMZLoginPage dmzloginpage;
	DMZHeaderPage dmzheaderpage;
	DMZBillingPage dmzbillingpage;
	
	//MITDPS SMUI Pages
	MITDPSLoginPage mitdpsloginpage;
	MITDPSHeaderPage mitdpsheaderpage;
	MITDPSBillingPage mitdpsbillingpage;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	CATaxInvoicePage cataxinvoicepage;
	CAInvoicesPage cainvoicespage;
	
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public Pay_Invoice() 
	{
		super();
	}
	
	String strDomainName = null;
	String strWorkflowStatus = null;
	String strRegistrationPeriod = null;
	String strPhoneNumber = null;
	String strRegistrantDetails = null;
	String strRegistrantType = null;
	String strRegistrantNumber = null;
	String strWorkflowId ;
	String strPaymentMethod;
	String strInvoiceNumber;
	String strInvoiceAmount;
	String strtransactiontype;
	String transactionMethod;
	String strGreencode;
	String strexistingCard;
	String strcardexpirymonth = "01";
	String strcardowner = "Test Owner";
	String strcardexpiryyear = "22";	
	String strCardNumber = "5555555555554444";
	String arrTlds[] = {"com"};
	
	static String strGreenCode="ZAP-53";
	static String strVirtualization ="MITDPS";
	static  HashMap<String, String> virtualizationGreenCode  = new HashMap<String,String>(); 
	static { virtualizationGreenCode.put(strVirtualization,strGreenCode);}
	HashMap<String,String> invoiceDetails=new HashMap<String,String>();
   
	/*
	 * Test case to generate an unpaid invoice from sales db 
	 */
	@Parameters({ "environment" })
	@Test
	public void generate_UnpaidInvoice(String environment) throws InterruptedException 
	{
		
		// Initialization (Test Data Creation and Assignment)
		strRegistrationPeriod = "2";
		strPaymentMethod = "Invoice";
		strGreencode = virtualizationGreenCode.get(strVirtualization);
		strPhoneNumber = "+61.299340501";
		strRegistrantDetails = "Registrant details";
		strRegistrantType = "ABN";
		strRegistrantNumber = "21073716793";
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestPayInvoice" + df.format(d);
		System.out.println("Start Test: generate_UnpaidInvoice");
		test.log(LogStatus.INFO, "Generate Unpaid Invoice -STARTED");
		
		for(String tld : arrTlds) 
		{
			// Test Step 1: Login to salesdb and place an order for domain registration
		    initialization(environment, "salesdburl");
		    test.log(LogStatus.INFO, "Navigate to salesdb and place order for " +tld+ " domain - STARTED");
			csloginpage = new CSLoginPage();
			csloginpage.setDefaultLoginDetails(environment);
			csnrcrmpage = csloginpage.clickLoginButton();
			System.out.println("Greencode is :" +strGreencode);
			csnrcrmpage.setGreenCode(strGreencode);
			cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
			cscreatedomainwindowpage.setDomainDetails(strDomainName, tld, strRegistrationPeriod, strPaymentMethod);
			csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
			
			if(tld=="com.au") 
			{
				csaueligibilitypage = new CSAUEligibilityPage();
			    csaueligibilitypage.setContactAndEligibilityDetails(strRegistrantDetails, strPhoneNumber,strRegistrantType, strRegistrantNumber);
			}
			else 
			{
				csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
			}
			
			csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
			csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
			Assert.assertEquals(csworkflownotificationpage.getNotificationMessage(), "Services are successfully confirmed");
			strWorkflowId = csworkflownotificationpage.getWorkflowID();
			csworkflownotificationpage.clickOKButton();
			test.log(LogStatus.INFO, "Navigate to salesdb and place order for " +tld+ "  domain - COMPLETED");
			driver.quit();
	
			// Test Step 2: Process the domain registration order in console admin
			initialization(environment, "consoleadmin");
			test.log(LogStatus.INFO, "Navigate to consoleadmin and execute the workflow - STARTED");
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.setDefaultLoginDetails(environment);
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, tld);
			strInvoiceNumber = caworkflowadminpage.getInvoiceNumber();
			strInvoiceAmount = caworkflowadminpage.getUnpaidInvoiceAmount();
			invoiceDetails.put(strInvoiceNumber,strInvoiceAmount); 
			for(Map.Entry<String,String> invoicedetail:invoiceDetails.entrySet())
			{  
			 	System.out.println("Unpaid Invoice Number: " +invoicedetail.getKey()+ " Invoice Amount: "+invoicedetail.getValue());  
			}
			
			//Test step 3: Verify if domain registration workflow is completed
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("product workflow failed")
					|| strWorkflowStatus.equalsIgnoreCase("update star rating"));
			test.log(LogStatus.INFO, "Navigate to consoleadmin and execute the workflow - COMPLETED");
			driver.quit();
		}
		System.out.println("End Test: generate_UnpaidInvoice");
		test.log(LogStatus.INFO, "Generate Unpaid Invoice - COMPLETED");
	}
	
	/*
	 * Test case to pay an unpaid invoice from salesdb  
	 */
	@Parameters({ "environment" })
	@Test(dependsOnMethods = "generate_UnpaidInvoice")
	public void payInvoice_via_Salesdb(String environment) throws InterruptedException 
	{
		strtransactiontype = "PAYMENT";
		transactionMethod = "Credit Card";
		strCardNumber = "411111******1111 02/2021";
		System.out.println("Start Test: payInvoice_via_Salesdb");
		test.log(LogStatus.INFO, "Pay invoice from salesdb - STARTED");
		
		// Test step 1: Process the paymnet of unpaid invoice from salesdb
		for(Map.Entry<String,String> invoicedetail:invoiceDetails.entrySet())
		{  
		 	System.out.println("Invoice Number: " +invoicedetail.getKey()+ " Invoice Amount: "+invoicedetail.getValue());  
		 	strInvoiceNumber = (String) invoicedetail.getKey();
		 	strInvoiceAmount = (String) invoicedetail.getValue();
			test.log(LogStatus.INFO, "Pay invoice " +strInvoiceNumber +"from salesdb - STARTED");
			initialization(environment, "salesdburl");
			csloginpage = new CSLoginPage();
			csloginpage.setDefaultLoginDetails(environment);
			csnrcrmpage = csloginpage.clickLoginButton();
			csprocesstransactionpage = csnrcrmpage.clickAccountTab();
			csprocesstransactionpage.setProcessTransactionDetails(strGreencode, strInvoiceNumber, strtransactiontype, strInvoiceAmount, strCardNumber, transactionMethod);
			Assert.assertEquals(csprocesstransactionpage.getConfirmationMessage(), "Item Successfully Added"); 
			test.log(LogStatus.INFO, "Pay invoice " +strInvoiceNumber +"from salesdb - COMPLETED");
			driver.quit();
		}   
		
		System.out.println("End Test: payInvoice_via_Salesdb");
		test.log(LogStatus.INFO, "Pay invoice from salesdb - COMLPETED");
	}
	
	/*
	 * Test case to pay an unpaid invoice from console admin
	 */
	@Parameters({ "environment" })
	@Test(dependsOnMethods = "generate_UnpaidInvoice")
	public void paySingleInvoice_via_ConsoleAdmin(String environment) throws InterruptedException, AWTException
	{
		System.out.println("Start Test: payInvoice_via_ConsoleAdmin");
		test.log(LogStatus.INFO, "Pay invoice from console admin - STARTED");
		
        // Test step 1: Process the payment of unpaid invoice from console Admin
		for(Map.Entry<String,String> invoicedetail:invoiceDetails.entrySet())
		{
			System.out.println("Invoice Number: " +invoicedetail.getKey()+ " Invoice Amount: "+invoicedetail.getValue());  
		 	strInvoiceNumber =(String) invoicedetail.getKey();
		 	test.log(LogStatus.INFO, "Navigate to consoleadmin and execute the workflow - STARTED");
			initialization(environment, "consoleadmin");
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.setDefaultLoginDetails(environment);
			caheaderpage.searchAccountReference(strGreencode);
			cainvoicespage = caheaderpage.clickViewInvoiceAndPrepaidDetail();
			cataxinvoicepage = cainvoicespage.selectInvoiceNumber(strInvoiceNumber);
			if(virtualizationGreenCode.containsKey("Domainz"))
			{
				cataxinvoicepage.setBTCreditCardDetails(strcardowner, strCardNumber, strcardexpirymonth, strcardexpiryyear);				
			}
			else
			{
				cataxinvoicepage.setQuestCreditCardDetails(strcardowner, strCardNumber,strcardexpirymonth, strcardexpiryyear);
			}
			
			cataxinvoicepage.payInvoice();
			cataxinvoicepage.getInvoicePaymentConfirmation();
			test.log(LogStatus.PASS, "Invoice paid successfully :"+strInvoiceNumber);
			test.log(LogStatus.INFO, "Navigate to consoleadmin and execute the workflow - COMPLETED");
			driver.quit();
		}
		System.out.println("End Test: payInvoice_via_ConsoleAdmin");
		test.log(LogStatus.INFO, "Pay invoice from console admin - COMLPETED");	
	}
	
	/*
	 * Test case to pay an multiple unpaid invoice from console admin  
	 */
	@Parameters({ "environment" })
	@Test(dependsOnMethods = "generate_UnpaidInvoice")
	public void payMultipleInvoices_via_ConsoleAdmin(String environment) throws Exception
	{
		System.out.println("Start Test: payMultipleInvoices_via_ConsoleAdmin");
		test.log(LogStatus.INFO, "Pay multiple invoices from console admin - STARTED");
		
		// Test step 1: Process the payment of multiple unpaid invoice from console Admin
		test.log(LogStatus.INFO, "Navigate to consoleadmin and pay multiple invoices - STARTED");
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caheaderpage.searchAccountReference(strGreencode);
		cainvoicespage = caheaderpage.clickPayOutstandingInvoices();
		cataxinvoicepage=cainvoicespage.clickPayOutstandingInvoices();
		cataxinvoicepage.clearCheckBoxes();
		for(Map.Entry<String,String> invoicedetail:invoiceDetails.entrySet())
		{
			System.out.println("Invoice Number: " +invoicedetail.getKey());  
		 	strInvoiceNumber = (String) invoicedetail.getKey();
		 	cataxinvoicepage.selectMultipleInvoices(strInvoiceNumber);
		}
		if(virtualizationGreenCode.containsKey("Domainz"))
		{
			cataxinvoicepage.setBTCreditCardDetails(strcardowner, strCardNumber, strcardexpirymonth, strcardexpiryyear);
		}
		else
		{
			cataxinvoicepage.setQuestCreditCardDetails(strcardowner, strCardNumber,strcardexpirymonth, strcardexpiryyear);
		}
		cainvoicespage=cataxinvoicepage.clickPayInvoice();
		Assert.assertEquals(cainvoicespage.getPaymentSuccessfulmsg(), "Payment Accepted");
		test.log(LogStatus.INFO, "Navigate to consoleadmin and pay multiple invoices - COMPLETED");
		driver.quit();
		System.out.println("End Test: payMultipleInvoices_via_ConsoleAdmin");
		test.log(LogStatus.INFO, "Pay multilpe invoices from console admin - COMLPETED");	
	}
	
	/*
	 * Test case to pay an  unpaid invoice from NRG SMUI  
	 */
	@Parameters({ "environment" })
	@Test(dependsOnMethods = "generate_UnpaidInvoice")
	public void payInvoice_via_NRG_SMUI(String environment) throws InterruptedException, AWTException
	{	
		strexistingCard = "Visa Test Number: 4111********1111 Expiry: 02/2021";
		System.out.println("Start Test: payInvoice_via_NRG_SMUI");
		test.log(LogStatus.INFO, "Pay Invoice via NRG SMUI - STARTED");
		
		// Test step 1: Process the payment of unpaid invoice from SMUI
		for(Map.Entry<String,String> invoicedetail:invoiceDetails.entrySet())
		{
			System.out.println("Invoice Number: " +invoicedetail.getKey());  
		 	strInvoiceNumber = (String) invoicedetail.getKey();
		 	test.log(LogStatus.INFO, "Navigate to billing page and pay invoice: "+strInvoiceNumber+" - STARTED");
		 	initialization(environment, "customerportalurl_netregistry");
			nrgloginpage = new NRGLoginPage();
			nrgloginpage.setLoginDetails(strGreencode, "comein22");
			nrgheaderpage = nrgloginpage.clickLoginButton();
			nrgbillingpage = nrgheaderpage.clickBillingTab();
			nrgbillingpage.paySingleInvoice(strInvoiceNumber);
			nrgbillingpage.selectExisitingCardtoPayInvoice(strexistingCard);
			nrgbillingpage.payInvoiceButton();
			String confirmationMessage = nrgbillingpage.confirmInvoiceIsPaid();
			Assert.assertTrue(confirmationMessage.contains("has been accepted"));
			test.log(LogStatus.INFO, "Navigate to billing page and pay invoice: "+strInvoiceNumber+" - COMPLETED");
			driver.close(); 	
		}
		
		System.out.println("End Test: payInvoice_via_NRG_SMUI");
		test.log(LogStatus.INFO, "Pay Invoice via NRG SMUI - COMPLETED");
	}
	
	/*
	 * Test case to pay an  unpaid invoice from Domainz SMUI  
	 */
	@Parameters({ "environment" })
	@Test(dependsOnMethods = "generate_UnpaidInvoice")
	public void payInvoice_via_Domainz_SMUI(String environment) throws InterruptedException, AWTException
	{	
		strexistingCard = "Visa Test Number: 4111********1111 Expiry: 02/2021";
		System.out.println("Start Test: payInvoice_via_Domainz_SMUI");
		test.log(LogStatus.INFO, "Pay Invoice via Domainz SMUI - STARTED");
		
		// Test step 1: Process the payment of unpaid invoice from SMUI
		for(Map.Entry<String,String> invoicedetail:invoiceDetails.entrySet())
		{
			System.out.println("Invoice Number: " +invoicedetail.getKey());  
		 	strInvoiceNumber = (String) invoicedetail.getKey();
		 	test.log(LogStatus.INFO, "Navigate to billing page and pay invoice: "+strInvoiceNumber+" - STARTED");
		 	initialization(environment, "customerportalurl_domainz");
			dmzloginpage = new DMZLoginPage();
			dmzloginpage.setLoginDetails(strGreencode, "comein22");
			dmzheaderpage = dmzloginpage.clickLoginButton();
			dmzbillingpage = dmzheaderpage.clickBillingTab();
			dmzbillingpage.paySingleInvoice(strInvoiceNumber);
			dmzbillingpage.selectExisitingCardtoPayInvoice(strexistingCard);
			dmzbillingpage.payInvoiceButton();
			String confirmationMessage = dmzbillingpage.confirmInvoiceIsPaid();
			Assert.assertTrue(confirmationMessage.contains("has been accepted"));
			test.log(LogStatus.INFO, "Navigate to billing page and pay invoice: "+strInvoiceNumber+" - COMPLETED");
			driver.close();
		}
		
		System.out.println("End Test: payInvoice_via_Domainz_SMUI");
		test.log(LogStatus.INFO, "Pay Invoice via Domainz SMUI - COMPLETED");
	}
	
	/*
	 * Test case to pay an  unpaid invoice from DPS SMUI  
	 */
	@Parameters({ "environment" })
	@Test(dependsOnMethods = "generate_UnpaidInvoice")
	public void payInvoice_via_DPS_SMUI(String environment) throws InterruptedException, AWTException
	{
		strexistingCard = "Visa Number: 4111xxxxxxxx1111 Expiry: 02/23";
		System.out.println("Start Test: payInvoice_via_DPS_SMUI");
		test.log(LogStatus.INFO, "Pay Invoice via MITDPS SMUI - STARTED");
		
		// Test step 1: Process the payment of unpaid invoice from SMUI
		for(Map.Entry<String,String> invoicedetail:invoiceDetails.entrySet())
		{
			System.out.println("Invoice Number: " +invoicedetail.getKey());
		 	strInvoiceNumber =(String) invoicedetail.getKey();
		 	test.log(LogStatus.INFO, "Navigate to billing page and pay invoice: "+strInvoiceNumber+" - STARTED");
		 	initialization(environment, "customerportalurl_mitdps");
		 	mitdpsloginpage = new MITDPSLoginPage();
		 	mitdpsloginpage.setLoginDetails(strGreencode, "comein22");
		 	mitdpsheaderpage = mitdpsloginpage.clickLoginButton();
		 	mitdpsbillingpage = mitdpsheaderpage.clickBillingTab();
		 	mitdpsbillingpage.paySingleInvoice(strInvoiceNumber);
		 	mitdpsbillingpage.selectExisitingCardtoPayInvoice(strexistingCard);
		 	mitdpsbillingpage.payInvoiceButton();
			String confirmationMessage = mitdpsbillingpage.confirmInvoiceIsPaid();
			Assert.assertTrue(confirmationMessage.contains("has been accepted"));
			test.log(LogStatus.INFO, "Navigate to billing page and pay invoice: "+strInvoiceNumber+" - COMPLETED");
			driver.close();
	    }
		
		System.out.println("End Test: payInvoice_via_DPS_SMUI");
		test.log(LogStatus.INFO, "Pay Invoice via MITDPS SMUI - COMPLETED");
	}
	
}
