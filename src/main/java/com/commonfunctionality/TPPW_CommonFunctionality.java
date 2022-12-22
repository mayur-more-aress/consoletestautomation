package com.commonfunctionality;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import com.base.TestBase;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.relevantcodes.extentreports.LogStatus;
import com.tpp.resellerapi.pages.ResellerApiPage;
import com.tppcustomerportal.pages.TPPBillingPage;
import com.tppcustomerportal.pages.TPPEligibilityDetailsPage;
import com.tppcustomerportal.pages.TPPHeaderPage;
import com.tppcustomerportal.pages.TPPOrderCompletePage;
import com.tppresellerportal.pages.TPPLoginPage;
import com.tppresellerportal.pages.TPPRegisterADomainPage;
import com.tppresellerportal.pages.TPPRegistrantNameChangePage;
import com.tppresellerportal.pages.TPPTabPage;

public class TPPW_CommonFunctionality extends TestBase {

	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	TPPLoginPage tppLoginPage;
	TPPHeaderPage tppHeaderPage;
	TPPRegisterADomainPage tppRegisterADomainPage;
	TPPTabPage tppTabPage;
	TPPRegistrantNameChangePage tppregistrantnamechangePage;
	TPPEligibilityDetailsPage tppeligibilitydetailspage;
	TPPOrderCompletePage tppordercompletepage;
	TPPBillingPage tppbillingpage;
	ResellerApiPage resellerapipage;
	String strResponseDomainRegistration;
	public static String strWorkflowId;
	String strResponseDomainRenewal;
	public static String strRenewWorkflowId;
	String strResponseDomainTransfer;
	public static String strTransferWorkflowId;
	String strResponseWhoisDomain;
	String strResponseDomainLock;
	String strResponseCreateNameServer;
	String strResponseChangeNameServer;
	String strResponseDeleteNameServer;
	String strResponseRegistrantNameChange;
	String strAccountReference=null;
	
	// Initializing Page Objects
	public TPPW_CommonFunctionality() {
		PageFactory.initElements(driver, this);
	}

	// Methods
   public void loginToConsoleAdmin(String environment) throws InterruptedException
   {
	    initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
   }
   
   public void loginToResellerPortal(String accountReference,String password) throws InterruptedException {
	   tppLoginPage = new TPPLoginPage();
		tppLoginPage.setLoginDetails(accountReference, "comein22");
		tppTabPage = tppLoginPage.clickLoginButton();
   }
   
public void chooseNameServers(String nameserver1, String nameserver2) throws InterruptedException
{
	tppRegisterADomainPage=new TPPRegisterADomainPage();
    tppRegisterADomainPage.tickNameServerOptions("Choose your nameservers");
	tppRegisterADomainPage.inputNameServerFields(nameserver1,nameserver2);
	tppRegisterADomainPage.tickTermsAndConditions();
}

public void enterNewEligibilityDetails(String strEligibilityIDType,String strEligibilityIDNumber, String strCompanyName,String strEligibilityType) throws InterruptedException
{
	tppregistrantnamechangePage=new TPPRegistrantNameChangePage();
	tppregistrantnamechangePage.provideNewEligibilityDetailsForAu(strEligibilityIDType, strEligibilityIDNumber, strCompanyName, strEligibilityType);
	tppregistrantnamechangePage.tickIcertifyCheckbox();
	tppregistrantnamechangePage.tickTermsAndConditionCheckbox();
}

public void enterEligibilityDetails(String strEligibilityIDType,String strEligibilityIDNumber, String strCompanyName,String strEligibilityType) throws InterruptedException
{
	tppeligibilitydetailspage = new TPPEligibilityDetailsPage();
	test.log(LogStatus.INFO, "Entering eligibility details ");
	tppeligibilitydetailspage.setEligibilityDetails(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
	test.log(LogStatus.INFO, "Tick on terms and conditions ");
	tppeligibilitydetailspage.tickCertifyDomainHasCloseAndSubstantialConnection();
	tppeligibilitydetailspage.tickTermsAndConditions();
	test.log(LogStatus.INFO, "Click on continue button on eligibility details page ");
	tppeligibilitydetailspage.clickContinueButton();
}
public void getDomainRegistrationWorkflowId() throws InterruptedException
{
	resellerapipage=new ResellerApiPage();
	strResponseDomainRegistration=resellerapipage.getResponseOfDomainRegistration();
	System.out.println("Domain Registration Response:"+strResponseDomainRegistration);
	Assert.assertTrue(strResponseDomainRegistration.contains("OK"));
	strWorkflowId=resellerapipage.getWorkflowId(strResponseDomainRegistration);
	System.out.println("Domain Registration WOrkflow Id:"+strWorkflowId);
}

public void getRenewalWorkflowId() throws InterruptedException
{
	resellerapipage=new ResellerApiPage();
	strResponseDomainRenewal=resellerapipage.getResponseOfRenewDomain();
	System.out.println("Domain Renewal Response:"+strResponseDomainRenewal);
	Assert.assertTrue(strResponseDomainRenewal.contains("OK"));
	strRenewWorkflowId=resellerapipage.getWorkflowId(strResponseDomainRenewal);
	System.out.println("Domain Renewal WOrkflow Id:"+strRenewWorkflowId);
}

public void getTransferWorkflowId() throws InterruptedException
{
	resellerapipage=new ResellerApiPage();
	strResponseDomainTransfer=resellerapipage.getResponseOfTransferDomain();
	System.out.println("Domain Transfer Response:"+strResponseDomainTransfer);
	Assert.assertTrue(strResponseDomainTransfer.contains("OK"));
	strTransferWorkflowId=resellerapipage.getWorkflowId(strResponseDomainTransfer);
	System.out.println("Domain Transfer WOrkflow Id:"+strTransferWorkflowId);
}

public void getWhoisDomainResponse() throws InterruptedException
{
	resellerapipage=new ResellerApiPage();
	strResponseWhoisDomain=resellerapipage.getResponseOfWhoisDomain();
	System.out.println("Whois Domain Response:"+strResponseWhoisDomain);
	Assert.assertTrue(strResponseWhoisDomain.contains("OK"));
	 resellerapipage.getWorkflowId(strResponseWhoisDomain);
}

public void getDomainLockAndUnlockResponse() throws InterruptedException
{
	resellerapipage=new ResellerApiPage();
	strResponseDomainLock=resellerapipage.getResponseOfDomainLock();
	System.out.println("Domain Lock Response:"+strResponseDomainLock);
	Assert.assertTrue(strResponseDomainLock.contains("OK"));
}

public void getCreateNameServerResponse() throws InterruptedException
{
	resellerapipage=new ResellerApiPage();
	strResponseCreateNameServer=resellerapipage.getResponseOfCreateNameServer();
	System.out.println("Create Name Sever Response:"+strResponseCreateNameServer);
	Assert.assertTrue(strResponseCreateNameServer.contains("OK"));
}

public void getChangeNameServerResponse() throws InterruptedException
{
	resellerapipage=new ResellerApiPage();
	strResponseChangeNameServer=resellerapipage.getResponseOfChangeNameServer();
	System.out.println("Change Name Sever Response:"+strResponseChangeNameServer);
	Assert.assertTrue(strResponseChangeNameServer.contains("OK"));
}

public void getDeleteNameServerResponse() throws InterruptedException
{
	resellerapipage=new ResellerApiPage();
	strResponseDeleteNameServer=resellerapipage.getResponseOfDeleteNameServer();
	System.out.println("Delete Name Sever Response:"+strResponseDeleteNameServer);
	Assert.assertTrue(strResponseDeleteNameServer.contains("OK"));
}

public void getRegistrantNameChangeWorkflowId() throws InterruptedException
{
	resellerapipage=new ResellerApiPage();
	strResponseRegistrantNameChange=resellerapipage.getResponseOfRegistrantNameChange();
	System.out.println("Registrant Name Change Response:"+strResponseRegistrantNameChange);
	Assert.assertTrue(strResponseRegistrantNameChange.contains("OK"));
	strWorkflowId=resellerapipage.getWorkflowId(strResponseRegistrantNameChange);
	System.out.println("Registrant Name Change  Workflow Id:"+strWorkflowId);
}

public void getWorkflowId()
{
	tppRegisterADomainPage=new TPPRegisterADomainPage();
	strWorkflowId = tppRegisterADomainPage.getSingleReferenceID();
	System.out.println("Reference ID:" + strWorkflowId);
}

public void selectExistingCard(String strExistingCard)
{
	tppbillingpage=new TPPBillingPage();
	tppbillingpage.selectExistingCreditCard(strExistingCard);	
	tppbillingpage.tickTermsAndConditions();
	tppordercompletepage = tppbillingpage.clickContinueButton();
}

public void fetchWorkflowId() throws InterruptedException
{
	tppordercompletepage=new TPPOrderCompletePage();
	Assert.assertTrue(tppordercompletepage.isOrderComplete(), "Order is not completed");
	strWorkflowId = tppordercompletepage.getSingleReferenceID();
	strAccountReference = tppordercompletepage.getAccountReferenceID();
	System.out.println("Account Reference:" + strAccountReference);	
	System.out.println("Reference ID[0]:" + strWorkflowId);	
}
}