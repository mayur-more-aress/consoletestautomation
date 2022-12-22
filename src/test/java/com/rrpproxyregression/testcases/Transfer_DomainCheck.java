package com.rrpproxyregression.testcases;

import java.awt.AWTException;
import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.base.TestBase;
import com.relevantcodes.extentreports.LogStatus;
import com.tppresellerportal.pages.TPPTransferDomainsPage;
import com.tppresellerportal.pages.TPPLoginPage;
import com.tppresellerportal.pages.TPPTabPage;

public class Transfer_DomainCheck extends TestBase {

	//console tpp pages
	TPPLoginPage tpploginpage;
	TPPTabPage tpptabpage;
	TPPTransferDomainsPage tppdomaintransferpage;
	
	String strPassword = null;
	String domainPrefix = null;
	String authCode = null;

	public Transfer_DomainCheck(){
		super();
	}
	
	@Parameters({ "environment", "namespace", "accountReference" })
	@Test
	public void verifyIfDomainAvailableForTransfer(String environment, String namespace, String accountReference) throws InterruptedException, IOException, AWTException {
		
		//Test Step 1: Login to tpp reseller portal
		initialization(environment, "resellerportalurl_tpp");
		
		strPassword = "comein22";
		domainPrefix = "test-domain-check48";
		authCode = "C076%h14$V90";
		
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
		
		// Test Step 3: Verify transfer domain status 
		test.log(LogStatus.INFO, "Verify domain search status");
		String statusOfDomain = tppdomaintransferpage.getDomainStatus();
		Assert.assertEquals(statusOfDomain, "Ok", "Domain Name Is Able To Be Transferred");
		System.out.println("End test: verifyIfDomainAvailableForTransfer");
		driver.close();
	}
	
	@Parameters({ "environment", "namespace", "accountReference" })
	@Test
	public void verifyIfDomainIsRegisteredAndAvailableForTransfer(String environment, String namespace, String accountReference) throws InterruptedException, IOException, AWTException {
		
		//Test Step 1: Login to tpp reseller portal
		initialization(environment, "resellerportalurl_tpp");
		
		strPassword = "comein22";
		domainPrefix = "test-domain-transfer-check48";
		authCode = "test240";
		
		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal");
		System.out.println("Start test: verifyIfDomainIsRegisteredAndAvailableForTransfer");
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
		
		// Test Step 3: Verify transfer domain status 
		test.log(LogStatus.INFO, "Verify domain search status");
		String statusOfDomain = tppdomaintransferpage.getDomainStatus();
		Assert.assertEquals(statusOfDomain, "Transfer is not allowed: Entity Not Found-545 - Entity reference not found [Object does not exist]",
				"Domain Is Not Registered");
		System.out.println("End test: verifyIfDomainIsRegisteredAndAvailableForTransfer");
		driver.close();
	}
	
	@Parameters({ "environment", "namespace", "accountReference" })
	@Test
	public void verifyIfDomainAuthCodeIsWrong(String environment, String namespace, String accountReference) throws InterruptedException, IOException, AWTException {
		
		//Test Step 1: Login to tpp reseller portal
		initialization(environment, "resellerportalurl_tpp");
		
		strPassword = "comein22";
		domainPrefix = "retspin-134-20180925105757";
		authCode = "X4496(q29?U1";
		
		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal");
		System.out.println("Start test: verifyIfDomainAuthCodeIsWrong");
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
		
		// Test Step 3: Verify transfer domain status 
		test.log(LogStatus.INFO, "Verify domain search status");
		String statusOfDomain = tppdomaintransferpage.getDomainStatus();
		Assert.assertEquals(statusOfDomain, "Transfer is not allowed: Unauthorized-531 - Authorization failed [Invalid authorization information]",
				"Domain Name Auth Code is Wrong");
		System.out.println("End test: verifyIfDomainAuthCodeIsWrong");
		driver.close();
	}
}
