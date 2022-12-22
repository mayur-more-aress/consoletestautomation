package com.awstestdataInitialization.testcases;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.base.TestBase;
import com.consoleadmin.pages.CADomainLevelPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.relevantcodes.extentreports.LogStatus;
import com.tppresellerportal.pages.TPPBulkRegisterPage;
import com.tppresellerportal.pages.TPPLoginPage;
import com.tppresellerportal.pages.TPPRegisterADomainPage;
import com.tppresellerportal.pages.TPPTabPage;
import com.util.PropertyFileOperations;

public class ResellerPortal_TPP extends TestBase
{
	public ResellerPortal_TPP() 
	{
		super();
	}
	
	//TPP Reseller Portal Pages
	TPPBulkRegisterPage tppbulkregisterpage;
	TPPLoginPage tpploginpage;
	TPPRegisterADomainPage tppregisterdomainpage;
	TPPTabPage tpptabpage;
	TPPRegisterADomainPage tppRegisterADomainPage;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	CADomainLevelPage cadomainlevelpage;
	
	//Data Initialization
	String strCustomerAccountReference = null;
	String strCustomerPassword = "comein22";
	String strDomainName = null;
	String strTld = null;
	String strWorkflowStatus = null;
	String strEligibilityIDType = "ABN";
	String strEligibilityIDNumber ="54 109 565 095";
	String strEligibilityType = "Company";
	String strCompanyName ="ARQ GROUP WHOLESALE PTY LTD";
	String strAccountReference = null;
	ArrayList<String> lstDomainNames = null;
	ArrayList<String> lstWorkflowId = null;
	SoftAssert softAssert = new SoftAssert();
	String[] arrTldsForReg = {"com.au","com","net","org","biz","info","us"};
	String[] arrTldsForRenewal = {"com","net","org","biz","info","us"};
	DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
	Date d = new Date();
	
	/*
	 *Testcase : to verify Bulk domain registration from TPP Reseller portal 
	 *Scenario : Existing customer and Existing credit card
	 *Tlds to be register : "com", "net", "org", "biz", "info", "us", "com.au", "net.au", "nz"
	 */
	
	@Parameters({ "environment","numberofdomains"})
	@Test
	public void verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard(String environment, int numberOfDomains) throws InterruptedException, IOException
	{
		strCustomerAccountReference = PropertyFileOperations.readProperties("tpp_Greencode");
		strDomainName = "Bulkdomainreg" + df.format(d);
		
		System.out.println("Start Test: verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard");
		
		// Test Step 1: Login to Reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal");
		initialization(environment, "resellerportalurl_tpp");
		tpploginpage = new TPPLoginPage();
		tpploginpage.setLoginDetails(strCustomerAccountReference, strCustomerPassword);
		tpptabpage = tpploginpage.clickLoginButton();

		// Test Step 2: Navigate to Bulk Domain Register
		test.log(LogStatus.INFO, "Navigate to Bulk Domain Register");
		tppbulkregisterpage = tpptabpage.clickBulkRegisterLink();
		System.out.print("number of domains" +numberOfDomains);
	    for(int i= 0; i < numberOfDomains ; i++)
	    {
	    	strTld = arrTldsForReg[i % arrTldsForReg.length];
			System.out.print(strTld + " ");  
			lstDomainNames = tppbulkregisterpage.setMultipleDomainNameandTld(strDomainName.concat(Integer.toString(i)), strTld);
	    }
		tppbulkregisterpage.clickSearchButton();

		// Test Step 3: verify domain available for Registration
		test.log(LogStatus.INFO, "verify domain available for Registraion");
		int domainCount = 0;
		for(String domainName : lstDomainNames)
		{
			Assert.assertEquals(tppbulkregisterpage.getSearchAvailabilityMessage(domainName, domainCount), "Available","Available");
			domainCount++;
		}
		
		// Test Step 4: Select existing customer details
		test.log(LogStatus.INFO, "Enter exisiting customer details");
		tppRegisterADomainPage = new TPPRegisterADomainPage();
		tppRegisterADomainPage.selectExistingCustomer();
		tppRegisterADomainPage.selectRegistranContact("Tim Coupland");
		
		// Test Step 5: if array of tlds contains .au domains, enter au eligibility details
        if(Arrays.asList(arrTldsForReg).contains("com.au") || Arrays.asList(arrTldsForReg).contains("net.au"))
        {
        	tppRegisterADomainPage.provideEligibilityDetailsForAu(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
        }
        
        // Test Step 6: Enter name servers and click register
        test.log(LogStatus.INFO, "Enter nameservers and click register button");
		tppRegisterADomainPage.tickNameServerOptions("Choose your nameservers");
		tppRegisterADomainPage.inputNameServerFields("ns1.partnerconsole.net", "ns2.partnerconsole.net");
		tppRegisterADomainPage.tickTermsAndConditions();
		tppRegisterADomainPage.clickRegisterDomainButton();
		
		// Test Step 7: Verify if order is placed and fetch the orderid
		test.log(LogStatus.INFO, "Verify if the order is completed -STARTED");
		lstWorkflowId = tppRegisterADomainPage.getMultipleReferenceID();
		for(String refID : lstWorkflowId)
		{
			System.out.println("Reference ID:"+refID);
			test.log(LogStatus.INFO,"Reference ID:"+refID);
		}
		test.log(LogStatus.INFO, "Verify if the order is completed  -COMPLETED");
		driver.quit();
		
		//Test Step 8: Verify if domain registration workflow is completed in console admin
		test.log(LogStatus.INFO, "Login to admin and execute workflow");
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		for(String refID : lstWorkflowId)
		{
			caworkflowadminpage = caheaderpage.searchWorkflow(refID);
			caworkflowadminpage.processWorkFlow(refID, strTld);
			caheaderpage.searchWorkflow(refID);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			softAssert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("product workflow failed")
						|| strWorkflowStatus.equalsIgnoreCase("update star rating"),"The domain registration for " +refID+ " FAILED");
			
		}
		softAssert.assertAll();
		driver.quit();
		
		System.out.println("End Test: verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard");
	}
	
	/*
	 *Test case : to verify Bulk domain registration from DPS Reseller portal and generate Renewal WF from A2 admin
	 *Scenario : Existing customer and Existing credit card
	 *Tld's to be register : "com", "net", "org", "biz", "info", "us"
	 */
	@Parameters({ "environment","numberofdomains"})
	@Test
	public void generateARenewalWorkflowFromConsoleAdmin(String environment, int numberOfDomains) throws InterruptedException, IOException
	{
		strCustomerAccountReference = PropertyFileOperations.readProperties("tpp_Greencode");
		strDomainName = "Bulkdomainreg" + df.format(d);
		
		System.out.println("Start Test: verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard");
		
		// Test Step 1: Login to Reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal");
		initialization(environment, "resellerportalurl_tpp");
		tpploginpage = new TPPLoginPage();
		tpploginpage.setLoginDetails(strCustomerAccountReference, strCustomerPassword);
		tpptabpage = tpploginpage.clickLoginButton();

		// Test Step 2: Navigate to Bulk Domain Register
		test.log(LogStatus.INFO, "Navigate to Bulk Domain Register");
		tppbulkregisterpage = tpptabpage.clickBulkRegisterLink();
		System.out.print("number of domains" +numberOfDomains);
	    for(int i= 0; i < numberOfDomains ; i++)
	    {
	    	strTld = arrTldsForRenewal[i % arrTldsForRenewal.length];
			System.out.print(strTld + " ");  
			lstDomainNames = tppbulkregisterpage.setMultipleDomainNameandTld(strDomainName.concat(Integer.toString(i)), strTld);
	    }
		tppbulkregisterpage.clickSearchButton();

		// Test Step 3: verify domain available for Registration
		test.log(LogStatus.INFO, "verify domain available for Registraion");
		int domainCount = 0;
		for(String domainName : lstDomainNames)
		{
			Assert.assertEquals(tppbulkregisterpage.getSearchAvailabilityMessage(domainName, domainCount), "Available","Available");
			domainCount++;
		}
		
		// Test Step 4: Select existing customer details
		test.log(LogStatus.INFO, "Enter exisiting customer details");
		tppRegisterADomainPage = new TPPRegisterADomainPage();
		tppRegisterADomainPage.selectExistingCustomer();
		tppRegisterADomainPage.selectRegistranContact("Tim Coupland");
		
		// Test Step 5: if array of tlds contains .au domains, enter au eligibility details
        if(Arrays.asList(arrTldsForRenewal).contains("com.au") || Arrays.asList(arrTldsForRenewal).contains("net.au"))
        {
        	tppRegisterADomainPage.provideEligibilityDetailsForAu(strEligibilityIDType,strEligibilityIDNumber,strCompanyName,strEligibilityType);
        }
        
        // Test Step 6: Enter name servers and click register
        test.log(LogStatus.INFO, "Enter nameservers and click register button");
		tppRegisterADomainPage.tickNameServerOptions("Choose your nameservers");
		tppRegisterADomainPage.inputNameServerFields("ns1.partnerconsole.net", "ns2.partnerconsole.net");
		tppRegisterADomainPage.tickTermsAndConditions();
		tppRegisterADomainPage.clickRegisterDomainButton();
		
		// Test Step 7: Verify if order is placed and fetch the orderid
		test.log(LogStatus.INFO, "Verify if the order is completed -STARTED");
		lstWorkflowId = tppRegisterADomainPage.getMultipleReferenceID();
		for(String refID : lstWorkflowId)
		{
			System.out.println("Reference ID:"+refID);
			test.log(LogStatus.INFO,"Reference ID:"+refID);
		}
		test.log(LogStatus.INFO, "Verify if the order is completed  -COMPLETED");
		driver.quit();
		
		//Test Step 8: Verify if domain registration workflow is completed in console admin
		test.log(LogStatus.INFO, "Login to admin and execute workflow");
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		test.log(LogStatus.INFO, "Login to admin and execute workflow");
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		for(String refID : lstWorkflowId)
		{
			caworkflowadminpage = caheaderpage.searchWorkflow(refID);
			caworkflowadminpage.processWorkFlow(refID, strTld);
			caheaderpage.searchWorkflow(refID);
			strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
			softAssert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("product workflow failed")
					|| strWorkflowStatus.equalsIgnoreCase("update star rating"),"The domain registration for " +refID+ " FAILED");
			if(strWorkflowStatus.equalsIgnoreCase("domain registration completed") || strWorkflowStatus.equalsIgnoreCase("product workflow failed"))
			{
				String domainName = caworkflowadminpage.getEntityName();
				String testdata[]=domainName.split("\\.",2);
				strDomainName = testdata[0];
				System.out.println("Domain Name: " + strDomainName);
				strTld = "."+testdata[1];
				System.out.println("Domain tld: " + strTld);
				if(strTld.contains("au"))
				{
					System.out.println("Renewal workflows can't be generated for .au domains");
				}
				else
				{
					cadomainlevelpage = caheaderpage.searchDomain(strDomainName+strTld);
					cadomainlevelpage.clickGenerateRenewalWorkflow();
					test.log(LogStatus.INFO, "Search a workflow ID in console admin and verify workflow status");
					caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName+strTld);
					String workflowType = caworkflowadminpage.searchWorkflowType("renewal2");
					if(workflowType.equalsIgnoreCase("renewal2"))
					{
						System.out.println("Renewal workflow created for domain: "+strDomainName+strTld);
						test.log(LogStatus.INFO,"Renewal workflow created for domain: "+strDomainName+strTld);
					}
					softAssert.assertEquals(workflowType, "renewal2", "renewal2 workflow is not created");
				}
			}
			else
			{
				System.out.println("Domain registration failed for RefId: "+ refID+"Cant generate renewal workflow");
				test.log(LogStatus.INFO,"Domain registration failed for RefId: "+ refID+"Cant generate renewal workflow");
			}
		}
		softAssert.assertAll();
		driver.quit();
		
		System.out.println("End Test: verifyMultipleDomainRegistrationForExistingCustomerUsingExistingCard");
	}
}
