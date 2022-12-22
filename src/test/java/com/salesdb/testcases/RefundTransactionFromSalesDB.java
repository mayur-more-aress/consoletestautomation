package com.salesdb.testcases;

import java.awt.AWTException;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
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
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class RefundTransactionFromSalesDB extends TestBase{
	
	CSLoginPage csloginpage;
	CSNrCRMPage csnrcrmpage;
	CSCreateDomainWindowPage cscreatedomainwindowpage;
	CSRegistrantDetailsPage csregistrantdetailspage;
	CSProcessTransactionPage csprocesstransactionpage;
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	CAInvoicesPage cainvoicespage;
	CATaxInvoicePage cataxinvoicepage;
	
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;
			
	public RefundTransactionFromSalesDB() {
		super();
	}
	
	@Parameters({"environment"})
	@Test
	public void refundTransactionToCreditCardFromSalesDB(String environment) throws InterruptedException, AWTException {
		String strgreencode = "ARQ-45";
		String strinvoicenumber = "14938788";
		String strtransactiontype = "REFUND";
		String stramount = "26.45";
		String strcardnumber = null;
		String transactionType = "Credit Card";
		String confirmationMessage = "Item Successfully Added";
	
	initialization(environment, "salesdburl");
	csloginpage = new CSLoginPage();
	csloginpage.setDefaultLoginDetails(environment);
	csnrcrmpage = csloginpage.clickLoginButton();
	csprocesstransactionpage = csnrcrmpage.clickAccountTab();
	csprocesstransactionpage.setProcessTransactionDetails(strgreencode, strinvoicenumber, strtransactiontype, stramount, strcardnumber, transactionType);
	Assert.assertEquals(csprocesstransactionpage.getConfirmationMessage(), confirmationMessage);
	driver.close();
	}
	
	@Parameters({"environment"})
	@Test
	public void refundTransactionToPrepaidCreditFromSalesDB(String environment) throws InterruptedException, AWTException {
		String strgreencode = "MEL-6005";
		String strinvoicenumber = "14938887";
		String strtransactiontype = "REFUND";
		String stramount = "70.0";
		String strcardnumber = null;
		String transactionType = "Prepaid Credit";
		String confirmationMessage = "Item Successfully Added";
	
	initialization(environment, "salesdburl");
	csloginpage = new CSLoginPage();
	csloginpage.setDefaultLoginDetails(environment);
	csnrcrmpage = csloginpage.clickLoginButton();
	csprocesstransactionpage = csnrcrmpage.clickAccountTab();
	csprocesstransactionpage.setProcessTransactionDetails(strgreencode, strinvoicenumber, strtransactiontype, stramount, strcardnumber, transactionType);
	Assert.assertEquals(csprocesstransactionpage.getConfirmationMessage(), confirmationMessage);
	driver.close();
	}
	
	@Parameters({"environment"})
	@Test
	public void refundTransactionFromConsoleAdmin(String environment) throws InterruptedException, AWTException {
		String strgreencode = "MEL-6005";
		String strinvoicenumber = "14938887";
		String stramount = "70.0";
		String confirmationMessage = "Credit has been successfully entered.";
	
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		Thread.sleep(5000);
		caheaderpage.searchAccountReference(strgreencode);
		cainvoicespage = caheaderpage.clickViewInvoiceAndPrepaidDetail();
		cataxinvoicepage = cainvoicespage.clickInvoiceID(strinvoicenumber);
		cataxinvoicepage.enterDescription("Refund to Prepaid");
		cataxinvoicepage.enterInvoiceAmount(stramount);
		cataxinvoicepage.clickSubmitButton();
		Assert.assertEquals(cataxinvoicepage.getRefundConfirmationMessage(), confirmationMessage);
		driver.close();
	}
	
}
