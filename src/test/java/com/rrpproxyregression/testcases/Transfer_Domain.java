package com.rrpproxyregression.testcases;

import java.awt.AWTException;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.relevantcodes.extentreports.LogStatus;
import com.rrpproxypage.java.RRPDomainsPage;
import com.rrpproxypage.java.RRPIncomingTransfersPage;
import com.rrpproxypage.java.RRPProxyLoginPage;
import com.rrpproxypage.java.RRPTabPage;
import com.tppresellerportal.pages.TPPLoginPage;
import com.tppresellerportal.pages.TPPTabPage;
import com.tppresellerportal.pages.TPPTransferDomainsOrderCompletePage;
import com.tppresellerportal.pages.TPPTransferDomainsPage;

public class Transfer_Domain extends TestBase {

	//console tpp pages
		TPPLoginPage tpploginpage;
		TPPTabPage tpptabpage;
		TPPTransferDomainsPage tppdomaintransferpage;
		TPPTransferDomainsOrderCompletePage tpptransferdomainsordercompletepage;
		CALoginPage caloginpage;
		CAHeaderPage caheaderpage;
		CAWorkflowAdminPage caworkflowadminpage;
		RRPProxyLoginPage rrpproxyloginpage;
		RRPTabPage rrptabpage;
		RRPDomainsPage rrpdomainspage;
		RRPIncomingTransfersPage rrpincomingtransferspage;
		
		String strPassword = null;
		String domainPrefix = null;
		String authCode = null;
		String registrantContactInfo = null;
		String organisationName = null;
		String firstName = null;
		String lastName = null;
		String address = null;
		String cityName = null;
		String countryName = null;
		String stateName = null;
		String postCode = null;
		String phoneNumber = null;
		String emailAddress = null;
		String workflowId = null;
		String rrpStrUsername = null;
		String rrpStrPassword = null;
	
	public Transfer_Domain(){
		super();
	}
	
	@Parameters({ "environment", "namespace", "accountReference" })
	@Test
	public void verifySuccessfulDomainTransferOrder(String environment, String namespace, String accountReference) throws InterruptedException, IOException, AWTException {
		
		initialization(environment, "resellerportalurl_tpp");
		
		strPassword = "comein22";
		domainPrefix = "teststage200819";
		authCode = "Q5439#e94!Q21";
		registrantContactInfo = "Tim Coupland";
		organisationName = "TPP Reseller 1";
		firstName = "Tim";
		lastName = "Coupland";
		address = "Latrobestreet";
		cityName = "MELBOURNE";
		countryName = "AUSTRALIA";
		stateName = "VIC";
		postCode = "3000";
		phoneNumber = "+61.411222333";
		emailAddress = "john.liu@melbourneit.com.au";
		
		
		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal");
		System.out.println("Start test: verifyIfDomainAvailableForTransfer");
		tpploginpage = new TPPLoginPage();
		tpploginpage.setLoginDetails(accountReference, strPassword);
		tpptabpage = tpploginpage.clickLoginButton();
		
		// Test Step 2: Navigate to Transfer Domain Page
		test.log(LogStatus.INFO, "Navigate to Domains then Transfer and search for a domain");
		tpptabpage.clickDomainsTab();
		tppdomaintransferpage = tpptabpage.clickDomainTransferLink();
		tppdomaintransferpage.enterDomainPrefix(domainPrefix);
		tppdomaintransferpage.selectDomainNamespace("."+namespace);
		tppdomaintransferpage.enterAuthCode(authCode);
		tppdomaintransferpage.clickOnAddLink();
		
		// Test Step 3: Enter registrant contact info
		test.log(LogStatus.INFO, "Enter registrant contact information");
		tppdomaintransferpage.selectRegistrantContactInfo(registrantContactInfo);
		tppdomaintransferpage.enterOrganisationName(organisationName);
		tppdomaintransferpage.enterFirstName(firstName);
		tppdomaintransferpage.enterLastName(lastName);
		tppdomaintransferpage.enterAddress(address);
		tppdomaintransferpage.enterCityName(cityName);
		tppdomaintransferpage.selectCountry(countryName);
		tppdomaintransferpage.selectState(stateName);
		tppdomaintransferpage.enterPostalCode(postCode);
		tppdomaintransferpage.enterPhoneNumber(phoneNumber);
		tppdomaintransferpage.enterEmail(emailAddress);
		
		// Test Step 4: Complete transfer domain order
		test.log(LogStatus.INFO, "Complete domain transfer order and copy workflow ID");
		tppdomaintransferpage.checkTermsAndConditions();
		tpptransferdomainsordercompletepage = tppdomaintransferpage.clickOnTransferDomains();
		String domainName = tpptransferdomainsordercompletepage.getDomainNameWhichIsTransferred();
		Assert.assertEquals(domainName, domainPrefix+".com", "Domain Name Is Transferred");
		workflowId = tpptransferdomainsordercompletepage.getWorkflowIdOfTransferredDomain();
		driver.quit();
	}
	
	@Parameters({ "environment", "namespace" })
	@Test
	public void verifyDomainTransferWorkflowInConsoleAdmin(String environment, String namespace) throws InterruptedException, IOException, AWTException {
		
		initialization(environment, "consoleadmin");
		
		String strusername = "fnurani";
		String strpassword = "comein22";

		//Test Step 1: Login to console admin
		test.log(LogStatus.INFO, "Login to console admin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.login(strusername, strpassword);
		
		//Test Step 2: Search a workflow ID and verify workflow status
		test.log(LogStatus.INFO, "Search a workflow ID in console admin and verify workflow status");
		caworkflowadminpage = caheaderpage.searchWorkflow(workflowId);
		caworkflowadminpage.clickOnWorkflowId();
		String workflowType = caworkflowadminpage.verifyWorkflowType();
		if(namespace.equals("com") || namespace.equals("org")|| namespace.equals("info")){
			Assert.assertEquals(workflowType, "transferral2", "Workflow Type is transferral2");
			System.out.println("Workflow Type is transferral2");
		}else{
			Assert.assertEquals(workflowType, "icannTransfer2", "Workflow Type is icannTransfer2");
		}
		String workflowStatus = caworkflowadminpage.getWorkflowStatus();
		Assert.assertEquals(workflowStatus, "transfer requested", "Domain Name Is In Transfer Requested Step");
		caworkflowadminpage.cancelWorkflow();
		driver.quit();
	}
	
	@Parameters({ "environment", "namespace" })
	@Test
	public void verifyDomainTransferOrderInRRPPortal(String environment, String namespace) throws InterruptedException, IOException, AWTException {
		
		initialization(environment, "rrpproxy_tpp");
		
		rrpStrUsername = "gulliver";
		rrpStrPassword = "MDYe#5z<W6dk9";
		
		//Test Step 1: Login to RRPProxy portal
		test.log(LogStatus.INFO, "Login to RRPproxy portal");
		rrpproxyloginpage = new RRPProxyLoginPage();
		rrpproxyloginpage.setLoginDetails(rrpStrUsername, rrpStrPassword);
		rrptabpage = rrpproxyloginpage.clickLoginButton();
		
		//Test Step 2: Navigate to pending transfers page
		test.log(LogStatus.INFO, "Navigate to domains page and search a domain");
		rrpdomainspage = rrptabpage.clickOnDomainsLink();
		rrpincomingtransferspage = rrpdomainspage.clickOnPendingTransfers();
		rrpincomingtransferspage.enterDomainNameInSearchField(domainPrefix+"."+namespace);
		rrpincomingtransferspage.clickOnSearchButton();
		
		//Test Step 3: Verify the domain name
		test.log(LogStatus.INFO, "Validate the domain name which is in transfer initiated step");
		String domainNameInIntiatedTransfer = rrpincomingtransferspage.getDomainNameOfTransferIntiated();
		Assert.assertEquals(domainNameInIntiatedTransfer, domainPrefix+"."+namespace, "Domain name is in transfer initiated step");
		driver.quit();
	}
	
	@Parameters({ "environment", "namespace", "accountReference" })
	@Test
	public void verifyUnsuccessfulDomainTransferOrder(String environment, String namespace, String accountReference) throws InterruptedException, IOException, AWTException {
		
		initialization(environment, "resellerportalurl_tpp");
		
		strPassword = "comein22";
		domainPrefix = "mynis1hdom12a3ab4in";
		authCode = "8>Q%3)5m";
		registrantContactInfo = "Tim Coupland";
		organisationName = "TPP Reseller 1";
		firstName = "Tim";
		lastName = "Coupland";
		address = "Latrobestreet";
		cityName = "MELBOURNE";
		countryName = "AUSTRALIA";
		stateName = "VIC";
		postCode = "3000";
		phoneNumber = "+61.411222333";
		emailAddress = "john.liu@melbourneit.com.au";
		
		
		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal");
		System.out.println("Start test: verifyIfDomainAvailableForTransfer");
		tpploginpage = new TPPLoginPage();
		tpploginpage.setLoginDetails(accountReference, strPassword);
		tpptabpage = tpploginpage.clickLoginButton();
		
		// Test Step 2: Navigate to Transfer Domain Page
		test.log(LogStatus.INFO, "Navigate to Domains then Transfer and search for a domain");
		tpptabpage.clickDomainsTab();
		tppdomaintransferpage = tpptabpage.clickDomainTransferLink();
		tppdomaintransferpage.enterDomainPrefix(domainPrefix);
		tppdomaintransferpage.selectDomainNamespace("."+namespace);
		tppdomaintransferpage.enterAuthCode(authCode);
		tppdomaintransferpage.clickOnAddLink();
		
		// Test Step 3: Enter registrant contact info
		test.log(LogStatus.INFO, "Enter registrant contact information");
		tppdomaintransferpage.selectRegistrantContactInfo(registrantContactInfo);
		tppdomaintransferpage.enterOrganisationName(organisationName);
		tppdomaintransferpage.enterFirstName(firstName);
		tppdomaintransferpage.enterLastName(lastName);
		tppdomaintransferpage.enterAddress(address);
		tppdomaintransferpage.enterCityName(cityName);
		tppdomaintransferpage.selectCountry(countryName);
		tppdomaintransferpage.selectState(stateName);
		tppdomaintransferpage.enterPostalCode(postCode);
		tppdomaintransferpage.enterPhoneNumber(phoneNumber);
		tppdomaintransferpage.enterEmail(emailAddress);
		
		// Test Step 4: Complete transfer domain order
		test.log(LogStatus.INFO, "Complete domain transfer order");
		tppdomaintransferpage.checkTermsAndConditions();
		tpptransferdomainsordercompletepage = tppdomaintransferpage.clickOnTransferDomains();
		
		// Test Step 5: Verify error message
		test.log(LogStatus.INFO, "Verify error message for already transferred domain");
		String errorMessage = tpptransferdomainsordercompletepage.verifyErrorMessageForAlreadyTransferredDomain();
		if(namespace.equals("com") || namespace.equals("org")|| namespace.equals("info")){
		Assert.assertEquals(errorMessage, "There is already an outstanding transferral2 application for "+domainPrefix+"."+namespace+". "
				+ "You cannot submit another application until the outstanding one has been processed.", "Domain Name Is Transferred");
		}else{
		Assert.assertEquals(errorMessage, "There is already an outstanding icannTransfer2 application for "+domainPrefix+"."+namespace+". "
				+ "You cannot submit another application until the outstanding one has been processed.", "Domain Name Is Transferred");
		}
		driver.quit();
	}
}
