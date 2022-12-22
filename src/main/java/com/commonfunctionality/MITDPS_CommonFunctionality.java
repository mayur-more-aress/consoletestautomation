package com.commonfunctionality;
import java.util.ArrayList;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.base.TestBase;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.melbourneitwebsite.pages.MITBillingPage;
import com.melbourneitwebsite.pages.MITEligibilityDetailsPage;
import com.melbourneitwebsite.pages.MITOrderCompletePage;
import com.mitdps.resellerapi.pages.ResellerApiPage;
import com.mitdpscustomerportal.pages.MITDPSCancelServicesPage;
import com.mitdpscustomerportal.pages.MITDPSHeaderPage;
import com.mitdpscustomerportal.pages.MITDPSLoginPage;
import com.mitdpsresellerportal.pages.MITDPSRegisterADomainPage;
import com.mitdpsresellerportal.pages.MITDPSTabPage;
import com.relevantcodes.extentreports.LogStatus;

public class MITDPS_CommonFunctionality extends TestBase {

	MITDPSHeaderPage mitdpsheaderpage;
	MITDPSLoginPage mitdpsLoginPage;
	ResellerApiPage resellerapipage;
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	MITOrderCompletePage mitordercompletepage;
	MITEligibilityDetailsPage miteligibilitydetailspage;
	MITBillingPage mitbillingpage;
	MITDPSCancelServicesPage mitdpscancelservicespage;
	MITDPSTabPage mitdpsTabPage;
	MITDPSRegisterADomainPage mitdpsRegisterADomainPage;
	
	
	public static String strWorkflowId ;
	public static String strRenewWorkflowId=null;
	public static String strAccountReference = null;
	public static String strTransferWorkflowId=null;
   public static ArrayList<String> lstWorkflowId = null;
   String strResponseDomain;
   String strResponseDomainRegistration;
   String strResponseDomainRenewal;
   String strResponseDomainTransfer;
   String strResponseWhoisDomain;
   String strResponseDomainLock;
   String strResponseCreateNameServer;
   String strResponseChangeNameServer;
  String strResponseDeleteNameServer;
  String strResponseRegistrantNameChange;
	
	// Initializing Page Objects
	public MITDPS_CommonFunctionality() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	
	
   
  /*Login to console admin*/
   public void loginToConsoleAdmin(String environment) throws InterruptedException
   {
	   initialization(environment, "consoleadmin"); 
		caloginpage = new CALoginPage();
		caheaderpage =caloginpage.setDefaultLoginDetails(environment);
   }
   
   public void fetchDomainRegistrationWorkflowId() throws InterruptedException
   {
	   resellerapipage=new ResellerApiPage();
		strResponseDomainRegistration=resellerapipage.getResponseOfDomainRegistration();
		System.out.println("Domain Registration Response:"+strResponseDomainRegistration);
		Assert.assertTrue(strResponseDomainRegistration.contains("OK"));
		strWorkflowId=resellerapipage.getWorkflowId(strResponseDomainRegistration);
		System.out.println("Domain Registration WOrkflow Id:"+strWorkflowId);
   }
   
   public void fetchRenewalWorkflowId() throws InterruptedException
   {
	   resellerapipage=new ResellerApiPage();
	   strResponseDomainRenewal=resellerapipage.getResponseOfRenewDomain();
		System.out.println("Domain Renewal Response:"+strResponseDomainRenewal);
		Assert.assertTrue(strResponseDomainRenewal.contains("OK"));
		strRenewWorkflowId=resellerapipage.getWorkflowId(strResponseDomainRenewal);
		System.out.println("Domain Renewal WOrkflow Id:"+strRenewWorkflowId);
   }
   
   public void fetchTransferWorkflowId() throws InterruptedException
   {
	   resellerapipage=new ResellerApiPage();
	   strResponseDomainTransfer=resellerapipage.getResponseOfTransferDomain();
		System.out.println("Domain Transfer Response:"+strResponseDomainTransfer);
		Assert.assertTrue(strResponseDomainTransfer.contains("OK"));
		strTransferWorkflowId=resellerapipage.getWorkflowId(strResponseDomainTransfer);
		System.out.println("Domain Transfer WOrkflow Id:"+strTransferWorkflowId);
   }
   
   public void fetchWhoisWorkflowId() throws InterruptedException
   {
	   resellerapipage=new ResellerApiPage();
	   strResponseWhoisDomain=resellerapipage.getResponseOfWhoisDomain();
		System.out.println("Whois Domain Response:"+strResponseWhoisDomain);
		Assert.assertTrue(strResponseWhoisDomain.contains("OK"));
	    resellerapipage.getWorkflowId(strResponseWhoisDomain);
   }
   
   public void fetchLockUnlockWorkflowId() throws InterruptedException
   {
	   resellerapipage=new ResellerApiPage();
	   strResponseDomainLock=resellerapipage.getResponseOfDomainLock();
		System.out.println("Domain Lock Response:"+strResponseDomainLock);
		Assert.assertTrue(strResponseDomainLock.contains("OK"));
   }
   
   public void fetchCreateNameServerWorkflowId() throws InterruptedException
   {
	   resellerapipage=new ResellerApiPage();
	   strResponseCreateNameServer=resellerapipage.getResponseOfCreateNameServer();
		System.out.println("Create Name Sever Response:"+strResponseCreateNameServer);
		Assert.assertTrue(strResponseCreateNameServer.contains("OK"));
   }
   
   public void fetchChangeNameServerWorkflowId()throws InterruptedException
   {
	   resellerapipage=new ResellerApiPage();
	   strResponseChangeNameServer=resellerapipage.getResponseOfChangeNameServer();
		System.out.println("Change Name Sever Response:"+strResponseChangeNameServer);
		Assert.assertTrue(strResponseChangeNameServer.contains("OK"));
   }
   
   public void fetchDeleteNameServerWorkflowId() throws InterruptedException
   {
	   resellerapipage=new ResellerApiPage();
	   strResponseDeleteNameServer=resellerapipage.getResponseOfDeleteNameServer();
		System.out.println("Delete Name Sever Response:"+strResponseDeleteNameServer);
		Assert.assertTrue(strResponseDeleteNameServer.contains("OK"));
   }
   
   public void fetchRegistrantNameChangeWorkflowId() throws InterruptedException
   {
	   resellerapipage=new ResellerApiPage();
	   strResponseRegistrantNameChange=resellerapipage.getResponseOfRegistrantNameChange();
		System.out.println("Registrant Name Change Response:"+strResponseRegistrantNameChange);
		Assert.assertTrue(strResponseRegistrantNameChange.contains("OK"));
		strWorkflowId=resellerapipage.getWorkflowId(strResponseRegistrantNameChange);
		System.out.println("Registrant Name Change  Workflow Id:"+strWorkflowId);
   }
   
   public void fetchWorkflowId() throws InterruptedException
   {
	   mitordercompletepage=new MITOrderCompletePage();
	   strWorkflowId = mitordercompletepage.getSingleReferenceID();
		strAccountReference = mitordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
   }
   
   /* To enter the au eligibility details*/
   public void setEligibilityDetails(String strEligibilityIDType,String strEligibilityIDNumber, String strCompanyName,String strEligibilityType) throws InterruptedException
   {
	   miteligibilitydetailspage = new MITEligibilityDetailsPage();
		test.log(LogStatus.INFO, "Entering eligibility details ");
		miteligibilitydetailspage.setEligibilityDetails("ABN", "21073716793", "Arq Group Limited", "Company");
		test.log(LogStatus.INFO, "Tick on terms and conditions ");
		miteligibilitydetailspage.tickCertifyDomainHasCloseAndSubstantialConnection();
		miteligibilitydetailspage.tickTermsAndConditions();
		test.log(LogStatus.INFO, "Click on continue button on eligibility details page ");
		miteligibilitydetailspage.clickContinueButton();
   }
   
   public void selectExistingCard(String strExistingCardNumber)
   {
	   mitbillingpage=new MITBillingPage();
	   test.log(LogStatus.INFO, "Entering credit card information ");
		mitbillingpage.selectExistingCreditCardOption(strExistingCardNumber);
		test.log(LogStatus.INFO, "Tick on terms and conditions ");
		mitbillingpage.tickTermsAndConditions();
		test.log(LogStatus.INFO, "Click on continue button on billing page ");
		mitordercompletepage = mitbillingpage.clickContinueButton();

   }
   
   public void getWorkflowId() throws InterruptedException
   {
	   mitdpscancelservicespage= new MITDPSCancelServicesPage();
	   strResponseDomain=mitdpscancelservicespage.getSingleReferenceID();
		System.out.println("Cancel services message:"+strResponseDomain);
		Assert.assertTrue(strResponseDomain.contains("Your reference number is"));
		strWorkflowId=mitdpscancelservicespage.getWorkflowId(strResponseDomain);
		System.out.println("Cancel service workflow Id:"+strWorkflowId);
   }
   
   public void loginToCustomerPortal(String accountReference, String strPassword) throws InterruptedException
   {
	   test.log(LogStatus.INFO, "Login to Customer portal - STARTED");
		mitdpsLoginPage = new MITDPSLoginPage();
		mitdpsLoginPage.setLoginDetails(accountReference, strPassword);
		mitdpsheaderpage = mitdpsLoginPage.clickLoginButton();
		test.log(LogStatus.INFO, "Login to Customer portal - COMPLETED");
   }
   
   public MITDPSTabPage logintoResellerPortal(String accountReference,String strPassword) throws InterruptedException
   {
	    mitdpsLoginPage = new MITDPSLoginPage();
		mitdpsLoginPage.setLoginDetails(accountReference, strPassword);
		return mitdpsTabPage = mitdpsLoginPage.clickOnLoginButton();
   }
   
   public void chooseNameServers(String nameserver1, String nameserver2) throws InterruptedException
   {
	   mitdpsRegisterADomainPage=new MITDPSRegisterADomainPage();
	   mitdpsRegisterADomainPage.tickNameServerOptions("Choose your nameservers");
		mitdpsRegisterADomainPage.inputNameServerFields(nameserver1,nameserver2);
		mitdpsRegisterADomainPage.tickTermsAndConditions();
		mitdpsRegisterADomainPage.clickRegisterDomainButton();
   }
   
   
   public void fetchRefrenceId() throws InterruptedException
   {
	   mitdpsRegisterADomainPage=new MITDPSRegisterADomainPage();
		test.log(LogStatus.INFO, "Verify if order is completed and get the reference ID if it is");
		strWorkflowId = mitdpsRegisterADomainPage.getSingleReferenceID();
		System.out.println("Reference ID:" + strWorkflowId);
   }
}