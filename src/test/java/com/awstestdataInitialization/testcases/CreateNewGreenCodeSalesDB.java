package com.awstestdataInitialization.testcases;

import java.awt.AWTException;
import java.io.IOException;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.consoleadmin.pages.CAAccountReferencePage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CAInvoicesPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAPrepaidCreidtPage;
import com.consolesalesdb.pages.CSAUEligibilityPage;
import com.consolesalesdb.pages.CSCreateDomainWindowPage;
import com.consolesalesdb.pages.CSCreateNewGreenCodePage;
import com.consolesalesdb.pages.CSLoginPage;
import com.consolesalesdb.pages.CSNrCRMPage;
import com.consolesalesdb.pages.CSRegistrantDetailsPage;
import com.consolesalesdb.pages.CSShowDomainServicesPage;
import com.consolesalesdb.pages.CSWorkflowNotificationPage;
import com.netregistryoldwebsite.pages.NRGAccountPage;
import com.netregistryoldwebsite.pages.NRGBillingPage;
import com.netregistryoldwebsite.pages.NRGCreditCardsDetailsPage;
import com.netregistryoldwebsite.pages.NRGHeaderPage;
import com.netregistryoldwebsite.pages.NRGLoginPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.PropertyFileOperations;
import com.util.TestUtil;

public class CreateNewGreenCodeSalesDB extends TestBase{
	
	CSLoginPage csloginpage;
	CSNrCRMPage csnrcrmpage;
	CSCreateDomainWindowPage cscreatedomainwindowpage;
	CSRegistrantDetailsPage csregistrantdetailspage;
	CSShowDomainServicesPage csshowdomainservicespage;
	CSWorkflowNotificationPage csworkflownotificationpage;
	CSAUEligibilityPage csaueligibilitypage;
	CSCreateNewGreenCodePage cscreatenewgreencodepage;
	NRGLoginPage nrgloginpage;
	NRGHeaderPage nrgheaderpage;
	NRGAccountPage nrgaccountpage;
	NRGCreditCardsDetailsPage nrgcreditcardsdetailspage;
	NRGBillingPage nrgbillingpage;
	
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAAccountReferencePage caaccountreferencepage;
	CAInvoicesPage caInvoicesPage;
	CAPrepaidCreidtPage caPrepaidCreidtPage;
	
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;
	
	public static String greenCode= null;
	
	public CreateNewGreenCodeSalesDB() {
		super();
	}
	
	@Parameters({"environment", "virtualization", "noOfAccounts"})
	@Test
	public void createNewGreencodeInSalesDB(String environment, String virtualization, int noOfAccounts) throws InterruptedException, AWTException, IOException {
		String OrgName= "ARQ Group";
		String Telephone="+6.123456789";
		String State="NSW";
		String Country="Australia";
		String City="Melbourne";
		String PinCode="3200";
		String Address="Test address";
		String Source = "TV Advert";
		String Industry= "Business - Accommodation";
		String Email="testing@melbourneit.com.au";
		String LastName="Test Lastname";
		String SecondaryEmail = "testing@melbourneit.com.au";
		String Salutation="Mr";
		String FirstName="Test Firstname";
		String Title="Mr";
		String strPassword = "comein22";
	
	// Step 1: Create a new greencode from sales DB
	initialization(environment, "salesdburl");
	csloginpage = new CSLoginPage();
	csloginpage.setDefaultLoginDetails(environment);
	csnrcrmpage = csloginpage.clickLoginButton();
	for(int i=0;i<noOfAccounts;i++) {
	cscreatenewgreencodepage = csnrcrmpage.clickNewGreencodeButton();
	cscreatenewgreencodepage.setGreencodeDetails(OrgName, Address, Country, City, State, PinCode, Telephone, Source, Industry, virtualization);
	cscreatenewgreencodepage.setDefaultContactDetails(Salutation, Title, FirstName, LastName, Email, SecondaryEmail);
	cscreatenewgreencodepage.createGreencodeButton();
	cscreatenewgreencodepage.clickSecondCreateButton();
	greenCode = cscreatenewgreencodepage.getGreencode();
	if(virtualization.equalsIgnoreCase("Netregistry"))
	{
		PropertyFileOperations.writeProperties("nrg_Greencode", greenCode);
	}
	else if(virtualization.equalsIgnoreCase("MelbourneIT"))
	{
		PropertyFileOperations.writeProperties("mit_Greencode", greenCode);
	}
	else 
	{
		System.out.println("Invalid virtualization");
	}
	System.out.println(greenCode);
	driver.close();
	}
	// Step 2: Update password from console admin for the newly created greencode 
	initialization(environment, "consoleadmin");
	caloginpage = new CALoginPage();
	caheaderpage = caloginpage.setDefaultLoginDetails(environment);
	caaccountreferencepage = caheaderpage.searchAccountReference(greenCode);
	caaccountreferencepage.updatePassword(strPassword);
	
	//Step 3: Create a prepaid account for the greencode
	caInvoicesPage = caaccountreferencepage.clickPayOutstandingInvoices();
	caInvoicesPage.clickCreatePrepaidCreditAccount();
	driver.close();
	}
	
	@Parameters({"environment", "noOfAccounts"})
	@Test
	public void createNewTPPResellerGreencodeInSalesDB(String environment, int noOfAccounts) throws InterruptedException, AWTException {
	String strResellerData = "Nm:ARQ-TestAccount\r\n" + 
			"Addr1:-\r\n" + 
			"Addr2:\r\n" + 
			"Addr3:\r\n" + 
			"City:BRISBANE\r\n" + 
			"Cntry:AUSTRALIA\r\n" + 
			"PCde:4031\r\n" + 
			"Tel:-+6.123456789\r\n" + 
			"Comments:\r\n" + 
			"ACN:\r\n" + 
			"Virtualisation_id:10\r\n" + 
			"State:QLD\r\n" + 
			"FirstName:Tim\r\n" + 
			"LastName:Coupland\r\n" + 
			"Title:\r\n" + 
			"MrMiss:\r\n" + 
			"Email:testing@melbourneit.com.au\r\n" + 
			"Currency:AUD\r\n" + 
			"Send Pricing Guide:No\r\n" + 
			"Send API:No\r\n" + 
			"Send WhiteLabel:No";
	// Step 1: Create a new greencode from sales DB
	for(int i=0;i<noOfAccounts;i++) {
	initialization(environment, "salesdburl");
	csloginpage = new CSLoginPage();
	csloginpage.setDefaultLoginDetails(environment);
	csnrcrmpage = csloginpage.clickLoginButton();
	cscreatenewgreencodepage = csnrcrmpage.clickNewGreencodeButton();
	cscreatenewgreencodepage.clickCreateReseller();
	cscreatenewgreencodepage.enterResellerData(strResellerData);
	cscreatenewgreencodepage.createGreencodeButton();
	driver.close();
	}
	}
	
	@Parameters({"environment", "noOfAccounts"})
	@Test
	public void createNewDPSResellerGreencodeInSalesDB(String environment, int noOfAccounts) throws InterruptedException, AWTException {
	String strResellerData = "Nm:ARQ-TestAccount\r\n" + 
			"Addr1:-\r\n" + 
			"Addr2:\r\n" + 
			"Addr3:\r\n" + 
			"City:BRISBANE\r\n" + 
			"Cntry:AUSTRALIA\r\n" + 
			"PCde:4031\r\n" + 
			"Tel:-\r\n" + 
			"Comments:\r\n" + 
			"ACN:\r\n" + 
			"Virtualisation_id:17\r\n" + 
			"State:QLD\r\n" + 
			"FirstName:Tim\r\n" + 
			"LastName:Coupland\r\n" + 
			"Title:\r\n" + 
			"MrMiss:\r\n" + 
			"Email:testing@melbourneit.com.au\r\n" + 
			"Currency:AUD\r\n" + 
			"Send Pricing Guide:No\r\n" + 
			"Send API:No\r\n" + 
			"Send WhiteLabel:No";
	// Step 1: Create a new greencode from sales DB
	initialization(environment, "salesdburl");
	for(int i=0;i<noOfAccounts;i++) {
	csloginpage = new CSLoginPage();
	csloginpage.setDefaultLoginDetails(environment);
	csnrcrmpage = csloginpage.clickLoginButton();
	cscreatenewgreencodepage = csnrcrmpage.clickNewGreencodeButton();
	cscreatenewgreencodepage.clickCreateReseller();
	cscreatenewgreencodepage.enterResellerData(strResellerData);
	cscreatenewgreencodepage.createGreencodeButton();
	driver.close();
	}
	}
	
	
}